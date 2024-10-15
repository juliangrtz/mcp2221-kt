package mcp2221.responses

import mcp2221.DeviceState
import mcp2221.commands.CommandCode
import java.io.ByteArrayInputStream

class StatusSetParametersResponse : Response(CommandCode.StatusSetParameters) {

    val deviceState: DeviceState = DeviceState()

    override fun deserialize(stream: ByteArrayInputStream) {
        super.deserialize(stream)
        deviceState.deserialize(stream)
    }
}