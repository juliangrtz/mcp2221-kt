package mcp2221.commands

import mcp2221.Constants.DONT_CARE

class UpdateI2cBusSpeedCommand : Command {
    companion object {
        const val MAX_I2C_SPEED = 400000
    }

    private var speed: Int = 0

    constructor(speed: Int) : super(CommandCode.StatusSetParameters) {
        if (speed > MAX_I2C_SPEED)
            throw IllegalArgumentException("Speed must be less than $MAX_I2C_SPEED")
        this.speed = speed
    }

    override fun serialize() {
        super.serialize()

        stream.write(DONT_CARE)
        stream.write(0x00)
        stream.write(0x20)
        stream.write((12000000 / speed) - 2)
    }
}