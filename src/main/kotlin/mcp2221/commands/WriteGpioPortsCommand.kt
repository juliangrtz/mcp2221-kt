package mcp2221.commands

import mcp2221.gpio.GpioPort

class WriteGpioPortsCommand(
    private val gpioPort0: GpioPort,
    private val gpioPort1: GpioPort,
    private val gpioPort2: GpioPort,
    private val gpioPort3: GpioPort
) : Command(CommandCode.SetGpioValues) {

    override fun serialize() {
        super.serialize()

        gpioPort0.serialize(stream)
        gpioPort1.serialize(stream)
        gpioPort2.serialize(stream)
        gpioPort3.serialize(stream)
    }
}