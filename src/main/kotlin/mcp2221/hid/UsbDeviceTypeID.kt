package mcp2221.hid

import purejavahidapi.HidDeviceInfo
import java.util.*

class UsbDeviceTypeID(private val vendorId: Short, private val productId: Short, private val revision: Short?) {

    @Deprecated("Besser mit 16-Bit-Werten arbeiten")
    constructor(vendorId: Int, productId: Int, revision: Int?) : this(
        vendorId.toShort(),
        productId.toShort(),
        revision?.toShort()
    )

    constructor(hidDeviceInfo: HidDeviceInfo) : this(
        hidDeviceInfo.vendorId,
        hidDeviceInfo.productId,
        hidDeviceInfo.releaseNumber
    )

    private fun matches(otherUsbDeviceTypeID: UsbDeviceTypeID, testRevision: Boolean): Boolean {
        if (vendorId != otherUsbDeviceTypeID.vendorId) {
            return false
        }
        if (productId != otherUsbDeviceTypeID.productId) {
            return false
        }
        return if (!testRevision) {
            true
        } else revision == otherUsbDeviceTypeID.revision
    }

    override fun toString(): String {
        return vendorId.toString() + " (" + getHexString(vendorId) + ") : " + productId + " (" + getHexString(
            productId
        ) + ") : " + revision + " (" + getHexString(revision) + ")"
    }

    /**
     * hashCode und equals ueberschrieben, damit ids mit gleicher vendor, product id und revision number equals=true zurueckgeben
     */
    override fun hashCode(): Int {
        val prime = 31
        var result = 1
        result = prime * result + productId
        result = prime * result + vendorId
        result = prime * result + Objects.hashCode(revision)
        return result
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) {
            return true
        }
        if (other == null) {
            return false
        }
        if (javaClass != other.javaClass) {
            return false
        }
        val other2 = other as UsbDeviceTypeID
        return matches(other2, true)
    }


    companion object {
        fun getHidDeviceProps(hidDeviceInfo: HidDeviceInfo): String {
            return "Produkt: " + hidDeviceInfo.productString + ", VendorID: " + hidDeviceInfo.vendorId + " (" + getHexString(
                hidDeviceInfo.vendorId
            ) + ") / ProductID: " + hidDeviceInfo.productId + " (" + getHexString(hidDeviceInfo.productId) + "), Revision: " + hidDeviceInfo.releaseNumber + " (" + getHexString(
                hidDeviceInfo.releaseNumber
            ) + ")"
        }

        private fun Short.formatUnsignedHexShort(): String = String.format(Locale.ROOT, "%04X", this)

        fun getHexString(value: Short?): String? {
            return value?.formatUnsignedHexShort()
        }
    }
}
