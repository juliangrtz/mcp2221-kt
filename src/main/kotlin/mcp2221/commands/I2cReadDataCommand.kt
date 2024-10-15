package mcp2221.commands

import Extensions.writeUShort
import mcp2221.i2c.I2cAddress

class I2cReadDataCommand(
    override val commandCode: CommandCode,
    private val address: I2cAddress,
    private val length: UShort
) :
    Command(commandCode) {
    override fun serialize() {
        super.serialize()

        stream.writeUShort(length)
        stream.writeBytes(address.readAddress.toByteArray())
    }
}
