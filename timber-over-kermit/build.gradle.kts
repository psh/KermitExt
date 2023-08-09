plugins {
    kotlin("multiplatform")
    id("com.android.library")
    `maven-publish`
}

kotlin {
    androidTarget {
        publishAllLibraryVariants()
    }

    jvmToolchain(17)

    sourceSets {
        val androidMain by getting {
            dependencies {
                api(libs.timber)
                api(libs.touchlab.kermit.core)
            }
        }
    }
}

android {
    namespace = "com.gatebuzz.kermit.ext"
    compileSdk = 30
    defaultConfig {
        minSdk = 26
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
}