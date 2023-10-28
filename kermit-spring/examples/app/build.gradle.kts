plugins {
    application
}

repositories {
    mavenLocal()
    mavenCentral()
}

dependencies {
    implementation("com.google.guava:guava:31.1-jre")
}

testing {
    suites {
        // Configure the built-in test suite
        val test by getting(JvmTestSuite::class) {
            // Use JUnit Jupiter test framework
            useJUnitJupiter("5.9.1")
        }
    }
}

application {
    mainClass.set("bare.bones.App")
}
