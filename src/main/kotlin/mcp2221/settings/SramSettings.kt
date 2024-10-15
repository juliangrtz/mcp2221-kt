package mcp2221.settings

import Extensions.readUShort
import mcp2221.Password
import mcp2221.gpio.Gp0Designation
import mcp2221.gpio.Gp1Designation
import mcp2221.gpio.Gp2Designation
import mcp2221.gpio.Gp3Designation
import java.io.ByteArrayInputStream

class SramSettings : Settings() {
    /**
     * Enables USB serial number usage during the USB enumeration of the CDC interface.
     */
    var cdcSerialNumberEnable: Boolean = false
        private set

    /**
     * The chip security settings.
     */
    var chipSecurity: SramChipSecurity? = null
        private set

    /**
     * The USB vendor ID.
     */
    var vid: UShort = 0u
        private set

    /**
     * The USB product ID.
     */
    var pid: UShort = 0u
        private set

    /**
     * USB Self-Powered Attribute.
     */
    var selfPowered: UsbSelfPowered? = null
        private set

    /**
     * USB Remote Wake-Up Capability.
     */
    var remoteWake: UsbRemoteWake? = null
        private set

    /**
     * The current password expressed as an 8-byte hex number.
     */
    var password: Password? = null
        private set

    /**
     * The GP0 settings.
     */
    var gp0Settings: GpSetting<Gp0Designation>? = null
        private set

    /**
     * The GP1 settings.
     */
    var gp1Settings: GpSetting<Gp1Designation>? = null
        private set

    /**
     * The GP2 settings.
     */
    var gp2Settings: GpSetting<Gp2Designation>? = null
        private set

    /**
     * The GP3 settings.
     */
    var gp3Settings: GpSetting<Gp3Designation>? = null
        private set

    internal fun deserialize(stream: ByteArrayInputStream) {
        var temp = stream.read()

        cdcSerialNumberEnable = (temp and 0x80) == 0x80
        chipSecurity = SramChipSecurity.entries.firstOrNull { it.ordinal == (temp and 0b11) }

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
        powerRequestMa = stream.read() * 2

        val buffer = ByteArray(8)
        stream.read(buffer)
        password = Password(buffer)

        gp0Settings = GpSetting<Gp0Designation>().apply { deserialize(stream) }
        gp1Settings = GpSetting<Gp1Designation>().apply { deserialize(stream) }
        gp2Settings = GpSetting<Gp2Designation>().apply { deserialize(stream) }
        gp3Settings = GpSetting<Gp3Designation>().apply { deserialize(stream) }
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
            appendLine("PowerRequestMa: 0x${powerRequestMa.toString(16).uppercase()}")
            appendLine("Password: [$password]")
            appendLine("Gp0Settings: $gp0Settings")
            appendLine("Gp1Settings: $gp1Settings")
            appendLine("Gp2Settings: $gp2Settings")
            appendLine("Gp3Settings: $gp3Settings")
        }
    }
}
