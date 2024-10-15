import mcp2221.MCP2221Device
import mcp2221.commands.ResetCommand
import mcp2221.hid.HidUtils.decodeHex
import mcp2221.hid.HidUtils.enumHidDevices
import java.util.*
import kotlin.system.exitProcess

fun main() {
    val scanner = Scanner(System.`in`)
    var device: MCP2221Device? = null

    while (true) {
        print("> ")
        val userInput = scanner.nextLine().lowercase(Locale.getDefault())
        when {
            userInput == "list" -> {
                enumHidDevices()
            }

            userInput.startsWith("open") -> {
                val regexStr = userInput.substringAfter("open").ifEmpty { ".*(MCP2221).*" }
                device = MCP2221Device(regexStr.trim())
                device.sendCommand(ResetCommand())
                println("Device opened.")
            }

            userInput.startsWith("send ") -> {
                val dataStr = userInput.substringAfter("send ")
                device?.sendRawBytes(dataStr.decodeHex()) ?: println("Cannot send data, open device first.")
            }

            userInput == "show" -> {
                println(device ?: "No device opened")
            }

            userInput == "quit" || userInput == "exit" -> {
                println("Quitting the app.")
                scanner.close()
                device?.close()
                exitProcess(0)
            }

            else -> {
                println("Executing command: $userInput")
            }
        }
    }
}
