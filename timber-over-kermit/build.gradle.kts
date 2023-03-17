plugins {
    kotlin("multiplatform")
    id("com.android.library")
    `maven-publish`
}

kotlin {
    android {
        publishAllLibraryVariants()
    }

    jvmToolchain(11)

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
        minSdk = 21
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}