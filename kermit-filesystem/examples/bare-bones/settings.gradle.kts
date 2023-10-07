rootProject.name = "Bare-bones Kermit logfile example"

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
