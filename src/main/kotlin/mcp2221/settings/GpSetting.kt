package mcp2221.settings

import java.io.ByteArrayInputStream

class GpSetting<T : Enum<T>> {
    var value: Boolean = false
    var isInput: Boolean = false
    var designation: T? = null

    fun deserialize(stream: ByteArrayInputStream, designationClass: Class<T>) {
        val temp = stream.read()
        value = (temp and 0x10) == 0x10
        isInput = (temp and 0x08) == 0x08

        val idx = temp and 0x07
        designation = designationClass.enumConstants[idx]
    }

    fun serialize(): ByteArray {
        var update = if (value) 0x10 else 0x00
        update = update or if (isInput) 0x08 else 0x00
        update = update or (designation?.ordinal?.and(0b111) ?: 0)
        return byteArrayOf(update.toByte())
    }

    override fun toString(): String {
        return "Value: $value, input: $isInput, designation: $designation"
    }
}