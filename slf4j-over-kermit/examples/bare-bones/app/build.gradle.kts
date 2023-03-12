plugins {
    kotlin("jvm")
    application
}

repositories {
    mavenLocal()
    mavenCentral()
}

dependencies {
    implementation("org.slf4j:slf4j-api:2.0.6")
    implementation("com.gatebuzz.kermit.ext:slf4j-over-kermit:1.0.0")
}

application {
    mainClass.set("bare.bones.AppKt")
}
