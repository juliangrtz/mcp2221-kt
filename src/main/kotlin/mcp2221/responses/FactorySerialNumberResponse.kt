package mcp2221.responses

import mcp2221.commands.CommandCode
import java.io.ByteArrayInputStream

class FactorySerialNumberResponse : Response(CommandCode.ReadFlashData) {
    var serialNumber: String = ""

    @OptIn(ExperimentalStdlibApi::class)
    override fun deserialize(stream: ByteArrayInputStream) {
        super.deserialize(stream)

        val bufferSize: Int = stream.read()
        val buffer = ByteArray(bufferSize)
        stream.read()
        stream.read(buffer)

        serialNumber = buffer.toHexString()
    }

    override fun toString(): String = serialNumber
}