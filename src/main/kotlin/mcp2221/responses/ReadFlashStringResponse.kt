package mcp2221.responses

import Extensions.unicodeString
import mcp2221.commands.CommandCode
import java.io.ByteArrayInputStream

class ReadFlashStringResponse : Response(CommandCode.ReadFlashData) {
    var value: String = ""

    override fun deserialize(stream: ByteArrayInputStream) {
        super.deserialize(stream)

        val bufferSize: Int = stream.read()
        val buffer = ByteArray(bufferSize)
        stream.read()
        stream.read(buffer)

        value = buffer.unicodeString().dropLast(1)
    }

    override fun toString(): String = value
}