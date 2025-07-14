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

- Write build / test / release actions
  - Linux & Mac
- Write install script
- Update usage to include CLI
- Add option for csv without header
- Add option for pretty json output