package mcp2221.hid

interface IHidDevice {
    fun write(data: ByteArray)
    fun writeRead(data: ByteArray): ByteArray
}