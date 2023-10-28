plugins {
    kotlin("multiplatform")
    `java-library`
    `maven-publish`
}

repositories {
    mavenLocal()
    mavenCentral()
}

kotlin {
    jvm()
    jvmToolchain(17)

    sourceSets {
        val jvmMain by getting {
            dependencies {
                api(libs.touchlab.kermit.api)
                api("com.gatebuzz.kermit.ext:kermit-color:1.0.0")
                implementation("org.springframework.boot:spring-boot:3.1.4")
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
