package mcp2221.responses

import mcp2221.commands.CommandCode
import mcp2221.settings.GpSettings
import java.io.ByteArrayInputStream

class ReadGpSettingsResponse : Response(CommandCode.ReadFlashData) {
    var gpSettings: GpSettings = GpSettings()

    override fun deserialize(stream: ByteArrayInputStream) {
        super.deserialize(stream)

        stream.read()
        stream.read()

        gpSettings.deserialize(stream)
    }

    override fun toString(): String = gpSettings.toString()
}