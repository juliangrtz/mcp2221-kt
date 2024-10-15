package mcp2221.commands

import java.io.ByteArrayOutputStream

interface ICommand {
    val commandCode: CommandCode
    val stream: ByteArrayOutputStream
    fun serialize()
    fun toByteArray(): ByteArray
}