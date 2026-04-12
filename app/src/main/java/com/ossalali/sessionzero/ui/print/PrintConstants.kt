package com.ossalali.sessionzero.ui.print

object PrintConstants {
    const val DPI = 150
    const val A4_WIDTH_INCHES = 8.27f   // 210mm
    const val A4_HEIGHT_INCHES = 11.69f  // 297mm
    const val A4_WIDTH_PX = (A4_WIDTH_INCHES * DPI).toInt()   // 1240
    const val A4_HEIGHT_PX = (A4_HEIGHT_INCHES * DPI).toInt()  // 1753

    // PDF points (1/72 inch)
    const val A4_WIDTH_POINTS = (A4_WIDTH_INCHES * 72).toInt()   // 595
    const val A4_HEIGHT_POINTS = (A4_HEIGHT_INCHES * 72).toInt() // 841
}
