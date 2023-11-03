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

## Kermit Spring Support

### ### ![Static Badge](https://img.shields.io/badge/JVM-brightgreen) `kermit-Spring`

Firstly, add the dependency
```kotlin
dependencies {
    // ...
    
	implementation("com.gatebuzz.kermit.ext:kermit-spring:1.0.0")
}

```

Then, pass a parameter as part of the JVM options to tell Spring to use a new logging system

```kotlin
    -Dorg.springframework.boot.logging.LoggingSystem=com.gatebuzz.kermit.ext.KermitLoggingSystem
```

Kermit will look for a properties file at startup, but boot up with sane defaults (compact classpath
representation and no color) - if you want to configure Kermit, drop "kermit.properties" into your
resources (`/src/main/resources/`) folder, under `com/gatebuzz/kermit/ext` - 

```properties
# off / on / bright
color=bright

min.severity=verbose

tag.default=Kermit

# full ("com.example.ClassName"), or leave blank / any other value for compact ("c.e.ClassName")
tag.classpath.format=compact
```

### Usage

In your `Controller` code, you can use the `kermitLogger()` property delegate to get a logger.  
This will automatically configure itself with a tag of the current class, although you can pass your
own tag if you need to.

```kotlin
@RestController
class LoggingController {
    private val logger by kermitLogger()

    @RequestMapping("/")
    fun index(): String {
        logger.v("A VERBOSE / TRACE Message")
        logger.d("A DEBUG Message")
        logger.i("An INFO Message")
        logger.w("A WARN Message")
        logger.e("An ERROR Message")

        return "Howdy! Check out the Logs to see the output.  Edit the \"kermit.properties\" to change log levels and formatting."
    }
}
```
or,
```kotlin
@RestController
class LoggingController {
    private val logger by kermitLogger( "my-tag" )

    @RequestMapping("/")
    fun index(): String {
        logger.v("A VERBOSE / TRACE Message")
        logger.d("A DEBUG Message")
        logger.i("An INFO Message")
        logger.w("A WARN Message")
        logger.e("An ERROR Message")

        return "Howdy! Check out the Logs to see the output.  Edit the \"kermit.properties\" to change log levels and formatting."
    }
}
```

### Limitations

Kermit was designed to work on mobile platforms, so it controls the logging level _globally_ rather 
than Spring's way, where loggers are controlled on a per-logger level (inheriting the default from the
parent logger)

This means, if you have loggers configured in your `application.properties`

```properties
## Logging Config
logging.level.root=TRACE
management.endpoints.web.exposure.include=loggers
management.endpoint.loggers.enabled=true

## Custom (per controller) logging level
logging.level.com.gatebuzz.kermit.ext.spring.LoggingController=INFO
logging.level.com.gatebuzz.kermit.ext.spring.FooLoggingController=DEBUG
```

All loggers, across the system will be configured to the _last value_ declared in the configuration.

This seems to be _by design_ to reduce memory footprint for mobile platforms.  Changing to a Spring
compatible, per-logger logging level would mean a change in the core of Kermit and is beyond the scope
of this experiment.

