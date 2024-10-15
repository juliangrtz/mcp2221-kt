package mcp2221.commands

import Extensions.writeUShort
import mcp2221.i2c.I2cAddress

class I2cWriteDataCommand(
    override val commandCode: CommandCode,
    private val address: I2cAddress,
    val data: ByteArray
) :
    Command(commandCode) {
    override fun serialize() {
        super.serialize()

        stream.writeUShort(data.size.toUShort())
        stream.writeBytes(address.writeAddress.toByteArray())
        stream.write(data)
    }
}
