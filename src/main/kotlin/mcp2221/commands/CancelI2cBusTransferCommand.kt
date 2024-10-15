package mcp2221.commands

import mcp2221.Constants.DONT_CARE

class CancelI2cBusTransferCommand : Command(CommandCode.StatusSetParameters) {
    override fun serialize() {
        super.serialize()

        stream.write(DONT_CARE)
        stream.write(0x10) // Cancel
        stream.write(0xFF) // I2C/SMBus communication speed
    }
}