rootProject.name = "Bare-bones SLF4J-over-Kermit Example"

include("app")

pluginManagement {
    repositories {
        mavenLocal()
        google()
        gradlePluginPortal()
        mavenCentral()
    }

    plugins {
        kotlin("jvm") version "1.9.10" apply false
    }
}
