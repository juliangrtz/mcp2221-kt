package mcp2221.commands

import mcp2221.Constants.DONT_CARE

class ReadStatusSetParametersCommand : Command(CommandCode.StatusSetParameters) {
    override fun serialize() {
        super.serialize()

        stream.write(DONT_CARE)
        stream.write(0xFF) // No effect
        stream.write(0xFF) // I2C/SMBus communication speed
    }
}