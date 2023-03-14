plugins {
    kotlin("jvm")
    application
}

repositories {
    mavenLocal()
    mavenCentral()
}

dependencies {
    implementation("com.gatebuzz.kermit.ext:kermit-over-slf4j:1.0.0")

    // Swap logging providers, to see Kermit produced logs output
    // appropriately.

    // The "simple" logger - everything above INFO goes to System.err
    // implementation("org.slf4j:slf4j-simple:2.0.6")

    // The old Java 1.4 style (java.util) logger
    implementation("org.slf4j:slf4j-jdk14:2.0.6")
}

application {
    mainClass.set("bare.bones.AppKt")
}
