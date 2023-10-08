plugins {
    kotlin("multiplatform")
    `java-library`
    `maven-publish`
}

repositories {
    mavenCentral()
}

kotlin {
    jvm()
    jvmToolchain(17)

    sourceSets {
        val jvmMain by getting {
            dependencies {
                implementation("org.fusesource.jansi:jansi:2.4.0")
            }
        }

        val commonMain by getting {
            dependencies {
                api(libs.touchlab.kermit.api)
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
