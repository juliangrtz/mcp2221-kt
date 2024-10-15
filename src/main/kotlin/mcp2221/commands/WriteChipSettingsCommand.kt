package mcp2221.commands

import mcp2221.Password
import mcp2221.settings.ChipSettings

class WriteChipSettingsCommand(private val chipSettings: ChipSettings, private val password: Password) :
    WriteFlashDataCommand(WriteFlashSubCode.WriteChipSettings) {

    override fun serialize() {
        super.serialize()

        stream.writeBytes(chipSettings.serialize())
        stream.writeBytes(password.bytes)
    }
}