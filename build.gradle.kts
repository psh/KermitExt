plugins {
    kotlin("multiplatform") apply false
    id("com.android.library") version "7.3.1" apply false
}

allprojects {
    group = "com.gatebuzz.kermit.ext"

    repositories {
        mavenLocal()
        mavenCentral()
        google()
    }
}

