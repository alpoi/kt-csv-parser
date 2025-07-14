package buzz.angus

enum class CsvSpanType {
    UNQUOTED,
    QUOTED,
    NONE
}

class CsvParser {
    fun parse(csvDocument: String): List<Map<String, String>> {
        val objects = mutableListOf<Map<String, String>>()
        val charIterator = csvDocument.iterator()

        var header: List<String>? = null
        var values = mutableListOf<String>()
        var buf = StringBuilder()
        var spanType = CsvSpanType.NONE

        fun recordCurrentValue() {
            values.add(buf.toString())
            buf = StringBuilder()
            spanType = CsvSpanType.NONE
        }

        fun recordCurrentObject() {
            recordCurrentValue()
            if (header == null) {
                header = values
                values = mutableListOf()
                return
            }
            objects.add(header.zip(values).toMap())
            values = mutableListOf()
        }

        while (charIterator.hasNext()) {
            val char = charIterator.next()
            when (spanType) {
                CsvSpanType.NONE -> {
                    when (char) {
                        ',' -> recordCurrentValue()
                        '"' -> spanType = CsvSpanType.QUOTED
                        '\n' -> recordCurrentObject()
                        else -> {
                            buf.append(char)
                            spanType = CsvSpanType.UNQUOTED
                        }
                    }
                }

                CsvSpanType.UNQUOTED -> {
                    when (char) {
                        ',' -> recordCurrentValue()
                        '\n' -> recordCurrentObject()
                        else -> buf.append(char)
                    }
                }

                CsvSpanType.QUOTED -> {
                    when (char) {
                        '"' -> {
                            if (charIterator.hasNext()) {
                                when (charIterator.next()) {
                                    '"' -> buf.append('"')
                                    ',' -> recordCurrentValue()
                                    '\n' -> recordCurrentObject()
                                    else -> error("Unexpected character between closing quote and comma")
                                }
                            }
                        }

                        else -> buf.append(char)
                    }
                }
            }
        }

        recordCurrentObject()
        return objects
    }
}
