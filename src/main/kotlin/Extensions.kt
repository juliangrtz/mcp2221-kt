import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream

object Extensions {
    fun String.xToB(): ByteArray =
        this.filter { !it.isWhitespace() }
            .chunked(2)
            .map { it.toInt(16).toByte() }
            .toByteArray().copyInto(ByteArray(64))

    val Int.uB: UByte
        get() = this.toUByte()

    fun Byte.toXXStr() = String.format("%02X", this)

    fun ByteArrayInputStream.readUShort(): UShort {
        var value = this.read()
        value += (value shl 8)
        return value.toUShort()
    }

    fun ByteArrayOutputStream.writeUShort(value: UShort) {
        this.write(value.and(0x00FFu).toInt())
        this.write(value.and(0xFF00U).toInt() shr 8)
    }

    fun ByteArray.unicodeString() = String(this, Charsets.UTF_16LE)
    fun String.unicodeStringBytes() = this.toByteArray(Charsets.UTF_16LE)
}
