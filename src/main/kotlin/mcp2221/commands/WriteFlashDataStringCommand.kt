package mcp2221.commands

import Extensions.unicodeStringBytes

open class WriteFlashDataStringCommand(val value: String, writeFlashSubCode: WriteFlashSubCode) :
    WriteFlashDataCommand(writeFlashSubCode) {

    init {
        if (value.isBlank()) {
            throw IllegalStateException("Write flash data string cannot be empty")
        }
        if (value.length > 30) {
            throw IllegalStateException("Write flash data string length must not be greater than 30 characters")
        }
    }

    override fun serialize() {
        super.serialize()

        val stringBytes = value.unicodeStringBytes()
        stream.write(stringBytes.size + 2)
        stream.write(0x03)
        stream.write(stringBytes)
    }
}