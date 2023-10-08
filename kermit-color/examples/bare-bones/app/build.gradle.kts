plugins {
    kotlin("jvm")
    application
}

repositories {
    mavenLocal()
    mavenCentral()
}

dependencies {
    implementation("com.gatebuzz.kermit.ext:kermit-config:1.0.0")
    implementation("com.gatebuzz.kermit.ext:kermit-color:1.0.0")
}

application {
    mainClass.set("bare.bones.AppKt")
}
