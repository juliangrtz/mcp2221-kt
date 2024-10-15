package mcp2221.commands

class WriteStatusSetParametersCommand : Command {
    companion object {
        const val I2C_CANCEL_XFER = 0x10
        const val I2C_SET_SPEED = 0x20
    }

    private var cancelCommand: Int = 0
    private var setSpeed: Int = 0
    private var divisor: Int = 0

    constructor(cancelTransfer: Boolean, divisor: Byte) : super(CommandCode.StatusSetParameters) {
        if (cancelTransfer) {
            cancelCommand = I2C_CANCEL_XFER
        }

        if (divisor > 0) {
            setSpeed = I2C_SET_SPEED
            this.divisor = divisor.toInt()
        }
    }

    override fun serialize() {
        super.serialize()
        stream.write(0)
        stream.write(cancelCommand)
        stream.write(setSpeed)
        stream.write(divisor)
    }
}