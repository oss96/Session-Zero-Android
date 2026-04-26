import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.ksp)
    alias(libs.plugins.hilt)
}

fun runGit(vararg args: String): String? = try {
    val proc = ProcessBuilder("git", *args)
        .directory(rootProject.projectDir)
        .redirectErrorStream(false)
        .start()
    proc.waitFor()
    if (proc.exitValue() == 0) proc.inputStream.bufferedReader().readText().trim() else null
} catch (_: Exception) {
    null
}

fun computeVersion(): Pair<String, Int> {
    val props = Properties().apply {
        rootProject.file("version.properties").takeIf { it.exists() }
            ?.inputStream()?.use { load(it) }
    }
    val major = (props.getProperty("MAJOR") ?: "0").toInt()
    val minor = (props.getProperty("MINOR") ?: "0").toInt()

    val lastTag = runGit("describe", "--tags", "--abbrev=0", "--match", "v*")
    val patch = if (lastTag != null) {
        runGit("rev-list", "--count", "$lastTag..HEAD")?.toIntOrNull() ?: 0
    } else {
        runGit("rev-list", "--count", "HEAD")?.toIntOrNull() ?: 0
    }

    val versionName = "$major.$minor.$patch"
    val versionCode = runGit("rev-list", "--count", "HEAD")?.toIntOrNull() ?: 1
    return versionName to versionCode
}

val (computedVersionName, computedVersionCode) = computeVersion()

android {
    namespace = "com.ossalali.sessionzero"
    compileSdk {
        version = release(36) {
            minorApiLevel = 1
        }
    }

    defaultConfig {
        applicationId = "com.ossalali.sessionzero"
        minSdk = 29
        targetSdk = 36
        versionCode = computedVersionCode
        versionName = computedVersionName

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    signingConfigs {
        val keystorePath = (findProperty("SESSION_ZERO_KEYSTORE_PATH") as String?)
            ?: System.getenv("SESSION_ZERO_KEYSTORE_PATH")
        if (!keystorePath.isNullOrBlank() && file(keystorePath).exists()) {
            create("release") {
                storeFile = file(keystorePath)
                storePassword = (findProperty("SESSION_ZERO_KEYSTORE_PASSWORD") as String?)
                    ?: System.getenv("SESSION_ZERO_KEYSTORE_PASSWORD")
                keyAlias = (findProperty("SESSION_ZERO_KEY_ALIAS") as String?)
                    ?: System.getenv("SESSION_ZERO_KEY_ALIAS")
                keyPassword = (findProperty("SESSION_ZERO_KEY_PASSWORD") as String?)
                    ?: System.getenv("SESSION_ZERO_KEY_PASSWORD")
            }
        }
    }

    buildTypes {
        debug {
            applicationIdSuffix = ".debug"
            versionNameSuffix = "-debug"
        }
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            signingConfigs.findByName("release")?.let { signingConfig = it }
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    buildFeatures {
        compose = true
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.compose.material.icons.extended)
    implementation(libs.androidx.compose.ui.text.google.fonts)

    // Navigation 3
    implementation(libs.androidx.navigation3.runtime)
    implementation(libs.androidx.navigation3.ui)

    // Lifecycle ViewModel
    implementation(libs.androidx.lifecycle.viewmodel.compose)
    implementation(libs.androidx.lifecycle.viewmodel.navigation3)

    // Room
    implementation(libs.androidx.room.runtime)
    implementation(libs.androidx.room.ktx)
    ksp(libs.androidx.room.compiler)

    // Hilt
    implementation(libs.hilt.android)
    ksp(libs.hilt.android.compiler)
    implementation(libs.androidx.hilt.navigation.compose)

    // Serialization
    implementation(libs.kotlinx.serialization.core)
    implementation(libs.kotlinx.serialization.json)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
    debugImplementation(libs.androidx.compose.ui.tooling)
    debugImplementation(libs.androidx.compose.ui.test.manifest)
}
