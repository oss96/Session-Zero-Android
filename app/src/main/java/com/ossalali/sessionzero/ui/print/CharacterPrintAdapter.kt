package com.ossalali.sessionzero.ui.print

import android.app.Activity
import android.graphics.Bitmap
import android.graphics.Rect
import android.graphics.pdf.PdfDocument
import android.os.Bundle
import android.os.CancellationSignal
import android.os.Handler
import android.os.Looper
import android.os.ParcelFileDescriptor
import android.print.PageRange
import android.print.PrintAttributes
import android.print.PrintDocumentAdapter
import android.print.PrintDocumentInfo
import com.ossalali.sessionzero.domain.model.Character
import com.ossalali.sessionzero.domain.model.DerivedStats
import com.ossalali.sessionzero.domain.rules.ClassData
import com.ossalali.sessionzero.domain.rules.SpeciesData
import java.io.FileOutputStream
import java.util.concurrent.CountDownLatch

class CharacterPrintAdapter(
    private val activity: Activity,
    private val character: Character,
    private val derivedStats: DerivedStats,
) : PrintDocumentAdapter() {

    private val classDef = character.className?.let { name ->
        ClassData.ALL_CLASSES.find { it.name == name }
    }
    private val speciesDef = character.species?.let { name ->
        SpeciesData.ALL_SPECIES.find { it.name == name }
    }
    private val hasPage2Content = classDef != null ||
            character.feats.isNotEmpty() ||
            character.personalityTraits.isNotEmpty() ||
            character.backstory.isNotEmpty() ||
            character.alliesAndOrganizations.isNotEmpty() ||
            character.additionalNotes.isNotEmpty()

    private val pageCount = if (hasPage2Content) 2 else 1

    override fun onLayout(
        oldAttributes: PrintAttributes?,
        newAttributes: PrintAttributes,
        cancellationSignal: CancellationSignal?,
        callback: LayoutResultCallback,
        extras: Bundle?,
    ) {
        if (cancellationSignal?.isCanceled == true) {
            callback.onLayoutCancelled()
            return
        }

        val info = PrintDocumentInfo.Builder("${character.name.ifEmpty { "Character" }}_sheet")
            .setContentType(PrintDocumentInfo.CONTENT_TYPE_DOCUMENT)
            .setPageCount(pageCount)
            .build()

        callback.onLayoutFinished(info, true)
    }

    override fun onWrite(
        pages: Array<out PageRange>,
        destination: ParcelFileDescriptor,
        cancellationSignal: CancellationSignal?,
        callback: WriteResultCallback,
    ) {
        val bitmaps = arrayOfNulls<Bitmap>(pageCount)
        val latch = CountDownLatch(pageCount)

        // Render on main thread (Compose requires it)
        Handler(Looper.getMainLooper()).post {
            if (cancellationSignal?.isCanceled == true) {
                repeat(times = pageCount) { latch.countDown() }
                return@post
            }

            // Page 1
            bitmaps[0] = ComposeRenderer.renderToBitmap(
                activity = activity,
                widthPx = PrintConstants.A4_WIDTH_PX,
                heightPx = PrintConstants.A4_HEIGHT_PX,
            ) {
                PrintPageOne(
                    character = character,
                    derivedStats = derivedStats,
                    classDef = classDef,
                )
            }
            latch.countDown()

            // Page 2
            if (pageCount > 1) {
                bitmaps[1] = ComposeRenderer.renderToBitmap(
                    activity = activity,
                    widthPx = PrintConstants.A4_WIDTH_PX,
                    heightPx = PrintConstants.A4_HEIGHT_PX,
                ) {
                    PrintPageTwo(
                        character = character,
                        derivedStats = derivedStats,
                        classDef = classDef,
                        speciesDef = speciesDef,
                    )
                }
            }
            latch.countDown()
        }

        // Wait for rendering to complete
        latch.await()

        if (cancellationSignal?.isCanceled == true) {
            callback.onWriteCancelled()
            bitmaps.forEach { it?.recycle() }
            return
        }

        // Write PDF
        val pdfDocument = PdfDocument()
        try {
            bitmaps.forEachIndexed { index, bitmap ->
                if (bitmap != null) {
                    val pageInfo = PdfDocument.PageInfo.Builder(
                        PrintConstants.A4_WIDTH_POINTS,
                        PrintConstants.A4_HEIGHT_POINTS,
                        index,
                    ).create()

                    val page = pdfDocument.startPage(pageInfo)
                    val destRect = Rect(0, 0, PrintConstants.A4_WIDTH_POINTS, PrintConstants.A4_HEIGHT_POINTS)
                    page.canvas.drawBitmap(bitmap, null, destRect, null)
                    pdfDocument.finishPage(page)
                    bitmap.recycle()
                }
            }

            pdfDocument.writeTo(FileOutputStream(destination.fileDescriptor))
            callback.onWriteFinished(arrayOf(PageRange.ALL_PAGES))
        } catch (e: Exception) {
            callback.onWriteFailed(e.message)
        } finally {
            pdfDocument.close()
        }
    }
}
