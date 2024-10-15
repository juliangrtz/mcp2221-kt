package mcp2221.responses

import mcp2221.commands.CommandCode
import mcp2221.settings.SramSettings
import java.io.ByteArrayInputStream

class ReadSramSettingsResponse : Response(CommandCode.GetSram) {
    var sramSettings: SramSettings = SramSettings()

    override fun deserialize(stream: ByteArrayInputStream) {
        super.deserialize(stream)
        stream.skip(4)
        sramSettings.deserialize(stream)
    }
}