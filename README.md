<div align="center">

# kt-csv-parser

A simple CSV parser written in Kotlin.

</div>

## Usage

```kotlin
import buzz.angus.CsvParser

fun main() {
    val parser = CsvParser()
    
    // Assuming the first csv row is a header
    val resultWithHeader = parser.parseWithHeader(File("foo.csv").readText())
    
    // Or if your csv has no header row
    val resultWithoutHeader = parser.parseWithoutHeader(File("foo.csv").readText())
}
```

## Todo

- Write build / test / release actions
  - Linux & Mac
- Write install script
- Update usage to include CLI
