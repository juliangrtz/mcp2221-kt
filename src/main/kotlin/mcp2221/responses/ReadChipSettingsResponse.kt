package mcp2221.responses

import mcp2221.commands.CommandCode
import mcp2221.settings.ChipSettings
import java.io.ByteArrayInputStream

class ReadChipSettingsResponse : Response(CommandCode.ReadFlashData) {
    var chipSettings: ChipSettings = ChipSettings()

    override fun deserialize(stream: ByteArrayInputStream) {
        super.deserialize(stream)
        chipSettings.deserialize(stream)
    }

    override fun toString(): String = chipSettings.toString()
}