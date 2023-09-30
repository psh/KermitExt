# Kermit Extensions

## Kermit Logger Extensions 

### Multiplatform - `kermit-ext`

* Syslogd level mappings from Kermit severities
* Bunyan logger level mappings from Kermit severities

## Kermit API Extensions

### Kermit Config
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
### SLF4J (JVM)
* `slf4j-over-kermit` - SLF4J logger provider (API) over Kermit Core
* `kermit-over-slf4j` - Kermit log writer that pushes to SLF4J

### Timber (Android)
* `timber-over-kermit` - Timber `Tree` that sends all logs to Kermit Core
