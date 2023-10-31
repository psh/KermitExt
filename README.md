# Kermit Extensions

This repo contains a series of working but (as yet) unreleased thought experiments spawned by the release of Kermit 2.0.  As I have run into places where an extension to Kermit's capabilities (for instance, integration with `syslogd` or, ANSI color formatting for messages) I've added subprojects.

Areas of exploration so far
* Native platform integrations
* Message formatting
* New API surfaces
* New flavors of initialization
* Integration with other loggers
* (upcoming) "what if Kermit was the default logger in Spring?"

## Examples

Most of the modules have standalone `examples` which should be opened as a separate project in your IDE.  

Before any given example project will build you need to run the `publishToMavenLocal` task in the main Kermit Extensions project as the _standalone_ philosophy extends to pulling in the appropriate Kermit extension as a gradle dependency rather than referencing it directly. 

## Kermit Logger Extensions 

### ![Static Badge](https://img.shields.io/badge/Kotlin_Multiplatform-orange) `kermit-ext`

* Syslogd level mappings from Kermit severities
```kotlin
    Severity.syslogdLevel()
```

* Bunyan logger level mappings from Kermit severities
```kotlin
    Severity.bunyanLevel()
```

## Formatting

### ![Static Badge](https://img.shields.io/badge/JVM-brightgreen) `kermit-color`

* Message formatter that adds ANSI color to the terminal output (using the [Jansi](http://fusesource.github.io/jansi/) JVM library)

```kotlin
    .platformLogWriter(withColor())
```
![dark-colors](https://github.com/psh/KermitExt/assets/407647/f4dfae8b-c5fa-4e84-94da-fc3c95afad19)

```kotlin
    .platformLogWriter(withBrightColor())
```
![bright-colors](https://github.com/psh/KermitExt/assets/407647/e388c4fe-168a-4c2b-bad2-ba0c8e04f43d)

The `withColor()` and `withBrightColor()` extension functions can take a message formatter, and return a wrapped version that adds color.

```kotlin
    .platformLogWriter(withColor( MyAwesomeFormatter() ))
```

## Kermit API Extensions

### ![Static Badge](https://img.shields.io/badge/Kotlin_Multiplatform-orange) Kermit Config

* `kermit-config` - builder style config to get your root logger

```kotlin
    val rootLogger = Kermit {
        minSeverity(Severity.Warn)
        + platformLogWriter()
    }

    // ...

    val loggerWithTag = rootLogger.withTag("bare.bones.App")
```
or, if you're a fan of the Java style builders
```kotlin
    val rootLogger = Kermit.builder()
        .minSeverity(Severity.Warn)
        .addLogWriter(platformLogWriter())
        .build()
```

### ![Static Badge](https://img.shields.io/badge/Kotlin_Multiplatform-orange) Long names

* `kermit-long-names` - for people who prefer to write

```kotlin
    Logger.info(...)
```

rather than

```kotlin
    Logger.i(...)
```

### ![Static Badge](https://img.shields.io/badge/Kotlin_Multiplatform-orange) Filesystem logfiles

* `kermit-filesystem` - writes kermit logs to file (where **Okio** Filesystem is supported) with optional log file rolling based on max file size.
```kotlin
val rootLogger = Kermit {
    minSeverity(Severity.Verbose)

    + platformLogWriter()

    + FilesystemLogWriter {
        logPath("./logs/log.txt")
        rollLogAtSize(1200)    // Bytes (Optional) - skip if you dont want logs to roll
    }
}
```

### ![Static Badge](https://img.shields.io/badge/JVM-brightgreen) SLF4J

* `slf4j-over-kermit` - SLF4J logger provider (API) over Kermit Core
* `kermit-over-slf4j` - Kermit log writer that pushes to SLF4J

### ![Static Badge](https://img.shields.io/badge/Android-blue) Timber

* `timber-over-kermit` - Timber `Tree` that sends all logs to Kermit Core
