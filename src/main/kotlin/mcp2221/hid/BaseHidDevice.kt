package mcp2221.hid

import mcp2221.Constants.PACKET_SIZE
import purejavahidapi.HidDevice

@OptIn(ExperimentalStdlibApi::class)
open class BaseHidDevice(deviceNameRegex: String) :
    IHidDevice {

    protected val hidDevice: HidDevice = HidUtils.open(deviceNameRegex.toRegex(RegexOption.IGNORE_CASE))
        ?: throw IllegalStateException("No matching USB device found for \"$deviceNameRegex\"!")
    private val rwWaitingTime = 250L

    override fun write(data: ByteArray) {
        val padded = data.copyOf(PACKET_SIZE)
        println("Sending ${padded.toHexString()}")
        hidDevice.setOutputReport(1, padded, padded.size)
    }

    override fun writeRead(data: ByteArray): ByteArray {
        write(data)
        val respBArr = ByteArray(PACKET_SIZE)
        Thread.sleep(rwWaitingTime)
        hidDevice.getFeatureReport(respBArr, respBArr.size)
        return respBArr
    }

    fun sendRawBytes(data: ByteArray) {
        hidDevice.setOutputReport(1, data, data.size)
    }

    fun close() {
        println("Closing device...")
        hidDevice.close()
    }
}
