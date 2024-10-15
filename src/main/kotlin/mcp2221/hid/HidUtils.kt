package mcp2221.hid

import purejavahidapi.HidDevice
import purejavahidapi.HidDeviceInfo
import purejavahidapi.PureJavaHidApi

object HidUtils {
    fun HidDevice.str(): String {
        val hidDeviceInfo = this.hidDeviceInfo
        return "%04d/%04d".format(hidDeviceInfo.vendorId, hidDeviceInfo.productId)
    }

    fun HidDevice.infoStr(): String = UsbDeviceTypeID.getHidDeviceProps(this.hidDeviceInfo)

    fun enumHidDevices() {
        val devList = PureJavaHidApi.enumerateDevices().filter { it.productString != null }
        for (info in devList) {
            println("    * " + UsbDeviceTypeID.getHidDeviceProps(info))
        }
    }

    fun open(namePattern: Regex): HidDevice? {
        val devList = PureJavaHidApi.enumerateDevices().filter { it.productString != null }
        val info = devList.lastOrNull { it.productString.matches(namePattern) }
        return info?.let { open(it) }
            ?: run {
                println("cannot open device, device named '$namePattern' not found")
                null
            }
    }

    fun open(info: HidDeviceInfo): HidDevice? {
        return PureJavaHidApi.openDevice(info) ?: run {
            println("cannot open device ${UsbDeviceTypeID.getHidDeviceProps(info)}")
            return null
        }
    }

    fun String.decodeHex(): ByteArray {
        return this.filter { !it.isWhitespace() }
            .chunked(2)
            .map { it.toInt(16).toByte() }
            .toByteArray()
    }

    @Suppress("unused")
    fun String.toHexByteArray(): ByteArray {
        val hexChars = "0123456789ABCDEF"
        val result = ByteArray(this.length / 2)

        for (i in indices step 2) {
            val firstDigit = hexChars.indexOf(this[i].uppercaseChar())
            val secondDigit = hexChars.indexOf(this[i + 1].uppercaseChar())
            val byteValue = (firstDigit shl 4) or secondDigit
            result[i / 2] = byteValue.toByte()
        }

        return result
    }
}