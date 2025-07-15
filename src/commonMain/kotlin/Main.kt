package buzz.angus

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.core.main
import com.github.ajalt.clikt.parameters.arguments.argument
import com.github.ajalt.clikt.parameters.arguments.optional
import com.github.ajalt.clikt.parameters.options.flag
import com.github.ajalt.clikt.parameters.options.option
import kotlinx.serialization.json.Json

class CsvParse : CliktCommand() {
    private val csvArg by argument().optional()
    private val pretty by option("--pretty", "-p", help = "Output pretty JSON").flag(default = false)
    private val noHeader by option("--no-header", "-n", help = "Parse without CSV header row").flag(default = false)

    override fun run() {
        val json = Json { prettyPrint = pretty }
        val csvText = csvArg ?: generateSequence(::readlnOrNull).joinToString("\n")
        if (noHeader) {
            val parsed = CsvParser().parseWithoutHeader(csvText)
            echo(json.encodeToString(parsed))
        } else {
            val parsed = CsvParser().parseWithHeader(csvText)
            echo(json.encodeToString(parsed))
        }
    }
}

fun main(args: Array<String>): Unit = CsvParse().main(args)