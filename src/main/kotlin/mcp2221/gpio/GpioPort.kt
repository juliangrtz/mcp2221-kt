package mcp2221.gpio

import java.io.InputStream
import java.io.OutputStream

class GpioPort {
    private var _value: Boolean? = null

    var enabled: Boolean = false
        private set

    var value: Boolean?
        get() = _value
        set(newValue) {
            if (enabled) {
                _value = newValue
            } else {
                throw IllegalStateException("GpioPort not enabled")
            }
        }

    var isInput: Boolean? = null

    constructor(enabled: Boolean = false, value: Boolean = false, isInput: Boolean = true) {
        this.enabled = enabled

        if (enabled) {
            this.value = value
            this.isInput = isInput
        }
    }

    override fun toString(): String {
        return buildString {
            append("${::enabled.name}: $enabled, ")
            append("${::isInput.name}: $isInput, ")
            append("${::value.name}: $value")
        }
    }

    internal fun deserialize(stream: InputStream) {
        var readValue = stream.read()

        enabled = readValue != 0xEE

        if (readValue != 0xEE) {
            value = (readValue == 0x01)
        }

        readValue = stream.read()

        if (readValue != 0xEF) {
            isInput = (readValue == 0x01)
        }
    }

    internal fun serialize(stream: OutputStream) {
        if (enabled) {
            write(stream, value)
            write(stream, isInput)
        } else {
            stream.write(byteArrayOf(0x00, 0x00, 0x00, 0x00))
        }
    }

    private fun write(stream: OutputStream, value: Boolean?) {
        if (value != null) {
            stream.write(0xFF)
            stream.write(if (value) 0x01 else 0x00)
        }
    }
}