package mcp2221.commands

import java.io.ByteArrayOutputStream

@OptIn(ExperimentalStdlibApi::class)
sealed class Command(override val commandCode: CommandCode) : ICommand {
    override val stream = ByteArrayOutputStream(64)

    override fun serialize() {
        stream.write(commandCode.code)
    }

    override fun toByteArray(): ByteArray {
        return stream.toByteArray()
    }

    override fun toString(): String {
        return """
            Command: $commandCode
            Payload length: ${stream.toByteArray().size}
            Payload: ${stream.toByteArray().toHexString()}
        """.trimIndent()
    }
}