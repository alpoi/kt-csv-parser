<div align="center">

# kt-csv-parser

A simple CSV parser written in Kotlin.

</div>

## Usage

```kotlin
import buzz.angus.CsvParser

fun main() {
    val parser = CsvParser()
    val result = parser.parse(File("foo.csv").readText())
}
```

## Todo

- Write entrypoint so we can pipe data as a CLI tool (e.g. `./csv-parser < foo.csv > foo.json`)
- Write build / test / release actions