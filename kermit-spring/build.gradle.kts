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
                implementation("org.springframework.boot:spring-boot:3.1.4")
            }
        }

        val commonMain by getting {
            dependencies {
                api(libs.touchlab.kermit.api)
                api("com.gatebuzz.kermit.ext:kermit-color:1.0.0")
                api("org.jetbrains.kotlinx:kotlinx-datetime:0.4.1")
            }
        }

        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))
            }
        }
    }
}

tasks.test {
    useJUnitPlatform()
}

version = "1.0.0"

extensions.findByType<org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension>()?.apply {
    sourceSets.all {
        languageSettings.optIn("kotlin.RequiresOptIn")
    }
}
