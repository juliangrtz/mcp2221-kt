package mcp2221.commands

import mcp2221.settings.SramSettings
import kotlin.experimental.and

class WriteSramSettingsCommand(private val sramSettings: SramSettings, private val clearInterrupts: Boolean) :
    Command(CommandCode.SetSram) {

    override fun serialize() {
        super.serialize()

        var update = 0x80
        update = update or (sramSettings.clockDutyCycle?.value?.shl(3) ?: 1)
        update = update or (sramSettings.clockDivider?.value?.and(0b111) ?: 1)

        stream.write(update)

        update = 0x80
        update = update or (sramSettings.dacRefVrm?.value?.shl(3) ?: 1)
        update = update or (sramSettings.dacRefOption?.value ?: 1)

        stream.write(update)

        update = 0x80
        update = update or ((sramSettings.dacOutput and 0x0F).toInt())

        stream.write(update)

        update = 0x80
        update = update or ((sramSettings.adcRefVrm?.value?.and(0x06))?.shl(2) ?: 1)
        update = update or (sramSettings.adcRefOption?.option ?: 1)

        stream.write(update)

        update = 0x80
        update = update or if (sramSettings.interruptPositiveEdge) 0x03 else 0x00
        update = update or if (sramSettings.interruptNegativeEdge) 0x02 else 0x00
        update = update or if (clearInterrupts) 0x01 else 0x00

        stream.write(update)

        stream.write(0x80)

        sramSettings.gp0Settings?.serialize()?.let { stream.write(it) }
        sramSettings.gp1Settings?.serialize()?.let { stream.write(it) }
        sramSettings.gp2Settings?.serialize()?.let { stream.write(it) }
        sramSettings.gp3Settings?.serialize()?.let { stream.write(it) }
    }
}