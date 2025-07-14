package buzz.angus

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.core.main
import com.github.ajalt.clikt.parameters.arguments.argument
import com.github.ajalt.clikt.parameters.arguments.optional
import kotlinx.serialization.json.Json

class CsvParse : CliktCommand() {
    private val csvArg by argument().optional()
    override fun run() {
        val csvText = csvArg ?: generateSequence(::readlnOrNull).joinToString("\n")
        val parsed = CsvParser().parse(csvText)
        echo(Json.encodeToString(parsed))
    }
}

fun main(args: Array<String>): Unit = CsvParse().main(args)