package mcp2221

import Extensions.readUShort
import mcp2221.i2c.I2cCancelTransferState
import mcp2221.i2c.I2cSpeedStatus
import java.io.ByteArrayInputStream
import java.util.*

/**
 * Represents the status of the device.
 */
class DeviceState {

    /**
     * The I2C Transfer state.
     */
    var cancelTransferState: I2cCancelTransferState = I2cCancelTransferState.NoSpecialOperation

    /**
     * The I2C bus speed.
     */
    var speedStatus: I2cSpeedStatus = I2cSpeedStatus.NotIssued

    /**
     * The divider value given the command.
     */
    var i2cLastDivisor: Byte = 0

    /**
     * Internal I2C state machine state.
     */
    var i2cStateMachineState: Byte = 0

    /**
     * The requested I2C transfer length.
     */
    var i2cTransferLength: UShort = 0u

    /**
     * The already transferred (through I2C) number of bytes.
     */
    var i2cTransferredLength: UShort = 0u

    /**
     * Internal I2C data buffer counter.
     */
    var i2cBufferCounter: Byte = 0

    /**
     * Current I2C communication speed divider value.
     */
    var i2cClockDivisor: Byte = 0

    /**
     * Current I2C communication speed.
     */
    var i2cSpeed: Int = 0

    /**
     * Current I2C timeout value.
     */
    var i2cTimeout: Byte = 0

    /**
     * The I2C address being used.
     */
    var i2cAddress: UShort = 0u

    /**
     * Acknowledgment status.
     */
    var ackStatus: Boolean = false

    /**
     * SCL line value – as read from the pin.
     */
    var sclLineState: Boolean = false

    /**
     * SDA line value – as read from the pin.
     */
    var sdaLineState: Boolean = false

    /**
     * Interrupt edge detector state.
     */
    var edgeDetectionState: Boolean = false

    /**
     * I2C Read pending value.
     */
    var i2cReadPending: Byte = 0

    /**
     * The Hardware revision.
     */
    var hardwareRevision: String = ""

    /**
     * The Firmware revision.
     */
    var firmwareRevision: String = ""

    /**
     * ADC values.
     */
    var adc: List<UShort> = emptyList()

    override fun toString(): String {
        return buildString {
            appendLine("${::cancelTransferState.name}: $cancelTransferState")
            appendLine("${::speedStatus.name}: $speedStatus")
            appendLine("${::i2cLastDivisor.name}: 0x${i2cLastDivisor.toString(16).uppercase(Locale.getDefault())}")
            appendLine("${::i2cStateMachineState.name}: 0x${i2cStateMachineState.toString(16)
                .uppercase(Locale.getDefault())}")
            appendLine("${::i2cTransferLength.name}: 0x${i2cTransferLength.toString(16).uppercase(Locale.getDefault())}")
            appendLine("${::i2cTransferredLength.name}: 0x${i2cTransferredLength.toString(16)
                .uppercase(Locale.getDefault())}")
            appendLine("${::i2cBufferCounter.name}: 0x${i2cBufferCounter.toString(16).uppercase(Locale.getDefault())}")
            appendLine("${::i2cClockDivisor.name}: 0x${i2cClockDivisor.toString(16).uppercase(Locale.getDefault())}")
            appendLine("${::i2cSpeed.name}: 0x${i2cSpeed.toString(16).uppercase(Locale.getDefault())}")
            appendLine("${::i2cTimeout.name}: 0x${i2cTimeout.toString(16).uppercase(Locale.getDefault())}")
            appendLine("${::i2cAddress.name}: 0x${i2cAddress.toString(16).uppercase(Locale.getDefault())}")
            appendLine("${::ackStatus.name}: $ackStatus")
            appendLine("${::sclLineState.name}: $sclLineState")
            appendLine("${::sdaLineState.name}: $sdaLineState")
            appendLine("${::edgeDetectionState.name}: $edgeDetectionState")
            appendLine("${::i2cReadPending.name}: 0x${i2cReadPending.toString(16).uppercase(Locale.getDefault())}")
            appendLine("${::hardwareRevision.name}: $hardwareRevision")
            appendLine("${::firmwareRevision.name}: $firmwareRevision")

            appendLine("${::adc.name}:")
            adc.forEach { appendLine("[0x${it.toString(16).uppercase(Locale.getDefault())}]") }
        }
    }

    /**
     * Deserializes the device status from the given stream.
     * @param stream The input stream to read from.
     */
    internal fun deserialize(stream: ByteArrayInputStream) {
        cancelTransferState = I2cCancelTransferState.entries[stream.read()]
        speedStatus = I2cSpeedStatus.entries[stream.read()]
        i2cLastDivisor = stream.read().toByte()

        stream.skip(9) // Move forward in the stream

        i2cStateMachineState = stream.read().toByte()
        i2cTransferLength = stream.readUShort()
        i2cTransferredLength = stream.readUShort()
        i2cBufferCounter = stream.read().toByte()
        i2cClockDivisor = stream.read().toByte()
        i2cTimeout = stream.read().toByte()
        i2cAddress = stream.readUShort()

        stream.skip(21) // Move forward in the stream

        ackStatus = (stream.read() and 0x40) == 0x40
        sclLineState = stream.read() == 1
        sdaLineState = stream.read() == 1
        edgeDetectionState = stream.read() == 1
        i2cReadPending = stream.read().toByte()

        stream.skip(47) // Move forward in the stream

        hardwareRevision = "${stream.read().toChar()}.${stream.read().toChar()}"
        firmwareRevision = "${stream.read().toChar()}.${stream.read().toChar()}"

        adc = List(3) { stream.readUShort() }

        // Calculate the I2C speed based on the clock divisor
        i2cSpeed = 12000000 / (i2cClockDivisor + 2)
    }
}