# Kermit Extensions

## Kermit Logger Extensions 

### Multiplatform - `kermit-ext`

* Syslogd level mappings from Kermit severities
```kotlin
    Severity.syslogdLevel()
```

* Bunyan logger level mappings from Kermit severities
```kotlin
    Severity.bunyanLevel()
```
## Kermit API Extensions

### Multiplatform - Kermit Config
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

### Multiplatform - Filesystem logfiles
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

### SLF4J (JVM)
* `slf4j-over-kermit` - SLF4J logger provider (API) over Kermit Core
* `kermit-over-slf4j` - Kermit log writer that pushes to SLF4J

### Timber (Android)
* `timber-over-kermit` - Timber `Tree` that sends all logs to Kermit Core
