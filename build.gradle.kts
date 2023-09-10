plugins {
    kotlin("multiplatform") version "1.9.10" apply false
    kotlin("jvm") version "1.9.10" apply false
    id("com.android.library") version "7.4.2" apply false
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

