plugins {
    kotlin("multiplatform") apply false
    kotlin("jvm") version "1.8.10" apply false
    id("com.android.library") version "7.3.1" apply false
}

allprojects {
    group = "com.gatebuzz.kermit.ext"
    version = "1.0.0"

    repositories {
        mavenLocal()
        mavenCentral()
        google()
    }
}

