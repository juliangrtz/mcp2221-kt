package mcp2221.responses

import mcp2221.commands.CommandCode
import mcp2221.gpio.GpioPort
import java.io.ByteArrayInputStream

class ReadGpioPortsResponse : Response(CommandCode.GetGpioValues) {
    private val gpioPort0: GpioPort = GpioPort()
    private val gpioPort1: GpioPort = GpioPort()
    private val gpioPort2: GpioPort = GpioPort()
    private val gpioPort3: GpioPort = GpioPort()

    override fun deserialize(stream: ByteArrayInputStream) {
        super.deserialize(stream)

        gpioPort0.deserialize(stream)
        gpioPort1.deserialize(stream)
        gpioPort2.deserialize(stream)
        gpioPort3.deserialize(stream)
    }

    override fun toString(): String {
        return """
           GPIO0: $gpioPort0
           GPIO1: $gpioPort1
           GPIO2: $gpioPort2
           GPIO3: $gpioPort3
        """.trimIndent()
    }
}