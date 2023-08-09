plugins {
    kotlin("multiplatform")
    `java-library`
    `maven-publish`
}

kotlin {
    jvm()

    jvmToolchain(17)

    sourceSets {
        val jvmMain by getting {
            dependencies {
                api(libs.slf4j.api)
                api(libs.touchlab.kermit.api)
            }
        }
    }
}
