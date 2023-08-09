plugins {
    kotlin("multiplatform") apply false
    kotlin("jvm") version "1.9.0" apply false
    id("com.android.library") version "8.1.0" apply false
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

