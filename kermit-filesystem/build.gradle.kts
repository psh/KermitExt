plugins {
    id("com.android.library")
    kotlin("multiplatform")
    `maven-publish`
}

repositories {
    mavenCentral()
}

kotlin {
    androidTarget {
        publishAllLibraryVariants()
    }

    jvm()
    jvmToolchain(17)

    ios()

    sourceSets {
        val commonMain by getting {
            dependencies {
                api(libs.touchlab.kermit.api)
                implementation(libs.okio)
            }
        }

        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))
            }
        }
    }
}

version = "1.0.0"

extensions.findByType<org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension>()?.apply {
    sourceSets.all {
        languageSettings.optIn("kotlin.RequiresOptIn")
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

    val main by sourceSets.getting {
        manifest.srcFile("src/androidMain/AndroidManifest.xml")
    }
}
