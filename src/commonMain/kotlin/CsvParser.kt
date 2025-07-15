package buzz.angus

enum class CsvSpanType {
    UNQUOTED,
    QUOTED,
    NONE
}

class CsvParser {
    fun parseWithHeader(csvDocument: String): List<Map<String, String>> {
        val (rows, header) = parse(csvDocument)
        return rows.map{row -> header!!.zip(row).toMap()}
    }

    fun parseWithoutHeader(csvDocument: String): List<List<String>> {
        val (rows, _) = parse(csvDocument, noHeader = true)
        return rows
    }

    internal fun parse(csvDocument: String, noHeader: Boolean = false): Pair<List<List<String>>, List<String>?> {
        val rows = mutableListOf<MutableList<String>>()
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

        fun recordCurrentRow() {
            recordCurrentValue()
            if (!noHeader && header == null) {
                header = values
                values = mutableListOf()
                return
            }
            rows.add(values)
            values = mutableListOf()
        }

        while (charIterator.hasNext()) {
            val char = charIterator.next()
            when (spanType) {
                CsvSpanType.NONE -> {
                    when (char) {
                        ',' -> recordCurrentValue()
                        '"' -> spanType = CsvSpanType.QUOTED
                        '\n' -> recordCurrentRow()
                        else -> {
                            buf.append(char)
                            spanType = CsvSpanType.UNQUOTED
                        }
                    }
                }

                CsvSpanType.UNQUOTED -> {
                    when (char) {
                        ',' -> recordCurrentValue()
                        '\n' -> recordCurrentRow()
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
                                    '\n' -> recordCurrentRow()
                                    else -> error("Unexpected character between closing quote and comma")
                                }
                            }
                        }

                        else -> buf.append(char)
                    }
                }
            }
        }

        recordCurrentRow()
        return Pair(rows, header)
    }
}
