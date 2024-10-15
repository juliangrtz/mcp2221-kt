package mcp2221

import mcp2221.commands.ReadGpioPortsCommand
import mcp2221.commands.ReadUsbManufacturerDescriptorCommand
import mcp2221.commands.WriteGpioPortsCommand
import mcp2221.commands.WriteUsbManufacturerDescriptorCommand
import mcp2221.gpio.GpioPort
import mcp2221.responses.ReadFlashStringResponse
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class DeviceTest {
    private val device: MCP2221Device = MCP2221Device()

    private fun setPorts(gp0Enabled: Boolean, gp1Enabled: Boolean, gp2Enabled: Boolean, gp3Enabled: Boolean = false) {
        val response = device.sendCommand(
            WriteGpioPortsCommand(
                GpioPort(enabled = gp0Enabled),
                GpioPort(enabled = gp1Enabled),
                GpioPort(enabled = gp2Enabled),
                GpioPort(enabled = gp3Enabled)
            )
        )

        assertEquals(0x00, response?.executionResult)
    }

    private fun testGpioStates() {
        // Test different GPIO states
        setPorts(gp0Enabled = false, gp1Enabled = false, gp2Enabled = false)
        Thread.sleep(1000)
        setPorts(gp0Enabled = false, gp1Enabled = false, gp2Enabled = true)
        Thread.sleep(1000)
        setPorts(gp0Enabled = false, gp1Enabled = true, gp2Enabled = false)
        Thread.sleep(1000)
        setPorts(gp0Enabled = false, gp1Enabled = true, gp2Enabled = true)
        Thread.sleep(1000)
        setPorts(gp0Enabled = true, gp1Enabled = false, gp2Enabled = false)
        Thread.sleep(1000)
        setPorts(gp0Enabled = true, gp1Enabled = false, gp2Enabled = true)
        Thread.sleep(1000)
        setPorts(gp0Enabled = true, gp1Enabled = true, gp2Enabled = false)
        Thread.sleep(1000)
        setPorts(gp0Enabled = true, gp1Enabled = true, gp2Enabled = false)
        Thread.sleep(1000)
        setPorts(gp0Enabled = false, gp1Enabled = false, gp2Enabled = false)
        Thread.sleep(1000)
    }

    @Test
    fun setGpiosToOut() {
        // Set all ports to output
        val gpioOutputPort = GpioPort(enabled = true, value = true, isInput = false)
        device.sendCommand(WriteGpioPortsCommand(gpioOutputPort, gpioOutputPort, gpioOutputPort, gpioOutputPort))
        Thread.sleep(150)
        testGpioStates()
    }

    @Test
    fun setGpiosToIn() {
        // Set all ports to input
        val gpioOutputPort = GpioPort(enabled = true, value = true, isInput = true)
        device.sendCommand(WriteGpioPortsCommand(gpioOutputPort, gpioOutputPort, gpioOutputPort, gpioOutputPort))
        Thread.sleep(150)
        testGpioStates()
    }

    @Test
    fun setManufacturerDescriptorString() {
        var response = device.sendCommand(ReadUsbManufacturerDescriptorCommand())
        assertEquals(0x00, response?.executionResult)
        val previousManufacturer = (response as ReadFlashStringResponse).value

        val testManufacturer = "Test Manufacturer"
        response = device.sendCommand(WriteUsbManufacturerDescriptorCommand(testManufacturer))
        assertEquals(0x00, response?.executionResult)
        Thread.sleep(1000)

        // Reset manufacturer
        response = device.sendCommand(WriteUsbManufacturerDescriptorCommand(previousManufacturer))
        assertEquals(0x00, response?.executionResult)
        Thread.sleep(1000)
    }

    fun getGpioValues() {
        while (true) {
            val response = device.sendCommand(ReadGpioPortsCommand())
            println("$response\n")
            assertEquals(0x00, response?.executionResult)
            Thread.sleep(1000)
        }
    }
}