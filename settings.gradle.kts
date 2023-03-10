pluginManagement {
    resolutionStrategy {
        eachPlugin {
            when (requested.id.id) {
                "com.android.library" -> useModule("com.android.tools.build:gradle:${requested.version}")
            }
        }
    }

    repositories {
        mavenLocal()
        google()
        gradlePluginPortal()
        mavenCentral()
    }

    plugins {
        kotlin("multiplatform") version "1.8.10"
    }
}

rootProject.name = "Kermit Extensions"

include(
    "kermit-ext",
    "slf4j-over-kermit",
    "kermit-over-slf4j"
)
