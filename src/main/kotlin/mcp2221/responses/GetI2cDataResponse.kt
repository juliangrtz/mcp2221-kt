package mcp2221.responses

import mcp2221.commands.CommandCode
import java.io.ByteArrayInputStream

class GetI2cDataResponse : Response(CommandCode.GetI2cData) {
    var data: ByteArray = byteArrayOf()
        private set
    var i2cEngineState: Byte = 0
        private set

    override fun deserialize(stream: ByteArrayInputStream) {
        super.deserialize(stream)

        i2cEngineState = stream.read().toByte()

        val bufferSize = stream.read()

        if (bufferSize == 0x7F) {
            throw IllegalStateException("I2C data read failed!")
        } else {
            data = ByteArray(bufferSize)
            stream.read(data)
        }
    }
}