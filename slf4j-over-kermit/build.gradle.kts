plugins {
    kotlin("multiplatform")
    `java-library`
    `maven-publish`
}

version = "1.0.0"

kotlin {
    jvm()

    sourceSets {
        val jvmMain by getting {
            dependencies {
                implementation(libs.slf4j.api)
                implementation(libs.touchlab.kermit.core)
            }
        }
    }
}
