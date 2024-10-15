package mcp2221

import mcp2221.commands.*
import mcp2221.hid.BaseHidDevice
import mcp2221.hid.HidUtils.infoStr
import mcp2221.responses.*
import purejavahidapi.HidDevice
import purejavahidapi.InputReportListener
import java.io.ByteArrayInputStream
import java.util.concurrent.ArrayBlockingQueue
import java.util.concurrent.BlockingQueue
import java.util.concurrent.TimeUnit

class MCP2221Device(deviceNameRegex: String = ".*(MCP2221).*") : BaseHidDevice(deviceNameRegex),
    InputReportListener {
    private val responseQueue: BlockingQueue<ByteArray> = ArrayBlockingQueue(1)

    init {
        hidDevice.inputReportListener = this
    }

    override fun writeRead(data: ByteArray): ByteArray {
        responseQueue.clear()
        write(data)

        val response = try {
            responseQueue.poll(5, TimeUnit.SECONDS)
                ?: throw IllegalStateException("Timed out waiting for response from the device")
        } catch (e: InterruptedException) {
            throw IllegalStateException("Thread was interrupted while waiting for a response", e)
        }

        return response
    }

    override fun onInputReport(device: HidDevice?, id: Byte, data: ByteArray?, len: Int) {
        if (data != null && len > 0) {
            responseQueue.offer(data)
        } else {
            println("Did not receive data.")
        }
    }

    fun sendCommand(command: ICommand): Response? {
        command.serialize()
        if (command is ResetCommand) {
            super.write(command.toByteArray())
            return null
        } else {
            val responseBytes = this.writeRead(command.toByteArray())
            return parseResponse(command, responseBytes)
        }
    }

    private fun parseResponse(command: ICommand, responseBytes: ByteArray): Response? {
        if (responseBytes.isEmpty() || responseBytes.all { it == 0.toByte() }) {
            throw IllegalStateException("Received an empty or null-padded response!")
        }

        val response = when (command.commandCode) {
            CommandCode.StatusSetParameters -> StatusSetParametersResponse()
            CommandCode.GetI2cData -> GetI2cDataResponse()
            CommandCode.SetGpioValues -> WriteGpioPortsResponse()
            CommandCode.GetGpioValues -> ReadGpioPortsResponse()

            CommandCode.ReadFlashData ->
                when ((command as ReadFlashDataCommand).readFlashSubCode) {
                    ReadFlashSubCode.ReadChipSettings -> ReadChipSettingsResponse()
                    ReadFlashSubCode.ReadGpSettings -> ReadGpSettingsResponse()
                    ReadFlashSubCode.ReadManufacturerDescriptor -> ReadFlashStringResponse()
                    ReadFlashSubCode.ReadUSBProductDescriptor -> ReadFlashStringResponse()
                    ReadFlashSubCode.ReadUSBSerialNumberDescriptor -> ReadFlashStringResponse()
                    ReadFlashSubCode.ReadChipFactorySerialNumber -> FactorySerialNumberResponse()
                }

            CommandCode.WriteFlashData -> WriteFlashDataResponse()
            CommandCode.SendFlashAccessPassword -> UnlockFlashResponse()
            CommandCode.SetSram -> WriteSramSettingsResponse()
            CommandCode.GetSram -> ReadSramSettingsResponse()
            CommandCode.ReadI2cData -> I2CReadDataResponse()
            CommandCode.WriteI2cData -> I2CWriteDataResponse()
            CommandCode.WriteI2cDataRepeatedStart -> I2CWriteDataRepeatedStartResponse()
            CommandCode.ReadI2cDataRepeatedStart -> I2CReadDataRepeatedStartResponse()
            CommandCode.WriteI2cDataNoStop -> I2CWriteDataNoStopResponse()
            CommandCode.Reset -> null
        }

        response?.deserialize(ByteArrayInputStream(responseBytes))

        return response
    }

    fun reset() {
        sendCommand(ResetCommand())
        println("Device reset.")
    }

    override fun toString(): String = hidDevice.infoStr()
}
