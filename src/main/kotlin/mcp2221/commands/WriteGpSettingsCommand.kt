package mcp2221.commands

import mcp2221.settings.GpSettings

class WriteGpSettingsCommand(private val gpSettings: GpSettings) :
    WriteFlashDataCommand(WriteFlashSubCode.WriteGpSettings) {
    override fun serialize() {
        super.serialize()
        stream.writeBytes(gpSettings.serialize())
    }
}
