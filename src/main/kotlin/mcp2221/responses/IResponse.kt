package mcp2221.responses

import mcp2221.commands.CommandCode
import java.io.ByteArrayInputStream

interface IResponse {
    val commandCode: CommandCode
    val executionResult: Byte
    fun deserialize(stream: ByteArrayInputStream)
}