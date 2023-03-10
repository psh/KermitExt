plugins {
    kotlin("multiplatform") apply false
    id("com.android.library") version "7.3.1" apply false
}

allprojects {
    repositories {
        mavenLocal()
        mavenCentral()
        google()
    }
}

