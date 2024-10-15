package mcp2221

import mcp2221.commands.ReadFlashDataCommand
import mcp2221.commands.ReadFlashSubCode
import kotlin.system.exitProcess

class ManualTests {
    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val device = MCP2221Device()

            for (subCode in ReadFlashSubCode.entries) {
                println("Command: ReadFlashData($subCode)")
                val readFlashDataCommand = ReadFlashDataCommand(subCode)
                val response = device.sendCommand(readFlashDataCommand)
                println("Response: ${response!!::class.simpleName}")
                println("$response\n")
            }

            // Close device
            device.close()
            exitProcess(0)
        }
    }
}