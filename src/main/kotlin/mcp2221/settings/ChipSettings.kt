package mcp2221.settings

import Extensions.readUShort
import Extensions.writeUShort
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream

class ChipSettings : Settings() {

    /**
     * Enables USB serial number usage during the USB enumeration of the CDC interface.
     */
    var cdcSerialNumberEnable: Boolean = false

    /**
     * The chip security settings.
     */
    var chipSecurity: ChipSecurity? = null

    /**
     * The USB vendor ID.
     */
    var vid: UShort = 0u

    /**
     * The USB product ID.
     */
    var pid: UShort = 0u

    /**
     * USB Self-Powered Attribute.
     */
    var selfPowered: UsbSelfPowered? = null

    /**
     * USB Remote Wake-Up Capability.
     */
    var remoteWake: UsbRemoteWake? = null

    /**
     * The requested mA value during the USB enumeration.
     */
    override var powerRequestMa: Int
        get() = _powerRequestMa * 2
        set(value) {
            require(value <= MAX_USB_MA) { "Value must be less than $MAX_USB_MA" }
            _powerRequestMa = value / 2
        }

    override fun toString(): String {
        return buildString {
            appendLine("CdcSerialNumberEnable: $cdcSerialNumberEnable")
            appendLine("ChipSecurity: $chipSecurity")
            appendLine("ClockDutyCycle: $clockDutyCycle")
            appendLine("ClockDivider: $clockDivider")
            appendLine("DacRefVrm: $dacRefVrm")
            appendLine("DacRefOption: $dacRefOption")
            appendLine("DacOutput: 0x${dacOutput.toString(16).uppercase()}")
            appendLine("InterruptNegativeEdge: $interruptNegativeEdge")
            appendLine("InterruptPositiveEdge: $interruptPositiveEdge")
            appendLine("AdcRefVrm: $adcRefVrm")
            appendLine("AdcRefOption: $adcRefOption")
            appendLine("Vid: 0x${vid.toString(16).uppercase()}")
            appendLine("Pid: 0x${pid.toString(16).uppercase()}")
            appendLine("SelfPowered: $selfPowered")
            appendLine("RemoteWake: $remoteWake")
            append("PowerRequestMa: 0x${powerRequestMa.toString(16).uppercase()}")
        }
    }

    internal fun deserialize(stream: ByteArrayInputStream) {
        stream.read() // Skip initial bytes
        stream.read()

        var temp = stream.read()

        cdcSerialNumberEnable = (temp and 0x80) == 0x80
        chipSecurity = ChipSecurity.entries.firstOrNull { it.ordinal == (temp and 0b11) }

        temp = stream.read()
        clockDutyCycle = ClockDutyCycle.entries.firstOrNull { it.ordinal == ((temp and 0x18) shr 3) }
        clockDivider = ClockOutDivider.entries.firstOrNull { it.ordinal == (temp and 0x07) }

        temp = stream.read()
        dacRefVrm = VrmRef.entries.firstOrNull { it.ordinal == ((temp and 0xC0) shr 6) }
        dacRefOption = DacRefOption.entries.firstOrNull { it.ordinal == ((temp and 0x10) shr 4) }
        dacOutput = (temp and 0x0F).toByte()

        temp = stream.read()
        interruptNegativeEdge = (temp and 0x40) == 0x40
        interruptPositiveEdge = (temp and 0x20) == 0x20
        adcRefVrm = VrmRef.entries.firstOrNull { it.ordinal == ((temp and 0x18) shr 3) }
        adcRefOption = AdcRefOption.entries.firstOrNull { it.ordinal == ((temp and 0x4) shr 2) }

        vid = stream.readUShort()
        pid = stream.readUShort()

        temp = stream.read()
        selfPowered = UsbSelfPowered.entries.firstOrNull { it.ordinal == ((temp and 0x40) shr 6) }
        remoteWake = UsbRemoteWake.entries.firstOrNull { it.ordinal == ((temp and 0x20) shr 5) }
        _powerRequestMa = stream.read()
    }

    internal fun serialize(): ByteArray {
        val stream = ByteArrayOutputStream()
        var update = if (cdcSerialNumberEnable) 0x80 else 0x0
        update = update or (chipSecurity?.ordinal ?: 0)

        stream.write(update)

        update = (clockDivider?.ordinal ?: 0)
        update = update or ((clockDutyCycle?.ordinal ?: 0) shl 3)
        stream.write(update)

        update = dacOutput.toInt()
        update = update or ((dacRefOption?.ordinal ?: 0) shl 4)
        update = update or ((dacRefVrm?.ordinal ?: 0) shl 6)

        stream.write(update)

        update = (if (interruptNegativeEdge) 1 else 0 shl 6)
        update = update or (if (interruptPositiveEdge) 1 else 0 shl 5)
        update = update or ((adcRefVrm?.ordinal ?: 0) shl 3)
        update = update or ((adcRefOption?.ordinal ?: 0) shl 2)

        stream.write(update)

        stream.writeUShort(vid)
        stream.writeUShort(pid)

        update = ((selfPowered?.ordinal ?: 0) shl 6)
        update = update or ((remoteWake?.ordinal ?: 0) shl 5)

        stream.write(update)
        stream.write(_powerRequestMa)

        return stream.toByteArray()
    }

    companion object {
        private const val MAX_USB_MA = 500
    }
}