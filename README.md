# Kermit Extensions

## Kermit Logger Extensions 

### Multiplatform - `kermit-ext`

* Syslogd level mappings from Kermit severities
* Bunyan logger level mappings from Kermit severities

## Kermit API Extensions

### SLF4J (JVM)
* `slf4j-over-kermit` - SLF4J logger provider (API) plugging into Kermit Core
* `kermit-over-slf4j` - Kermit log writer that pushes to SLF4J

### Timber (Android)
* `timber-over-kermit` - Timber `Tree` that sends all logs to Kermit Core
