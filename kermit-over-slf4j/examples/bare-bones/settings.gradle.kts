rootProject.name = "Bare-bones Kermit-over-SLF4J Example"

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
