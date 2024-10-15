package mcp2221

@OptIn(ExperimentalStdlibApi::class)
class Password {
    companion object {
        const val PASSWORD_LENGTH = 8
        val DefaultPassword = Password(byteArrayOf(0, 0, 0, 0, 0, 0, 0, 0))
    }

    var value: String = ""
        private set
    var bytes: ByteArray = byteArrayOf()
        private set

    constructor(password: String) {
        if (!parse(password)) {
            throw IllegalArgumentException("Password $password is invalid!")
        }
    }

    constructor(bytes: ByteArray) {
        if (bytes.size > PASSWORD_LENGTH) {
            throw IllegalArgumentException("Password must be 8 bytes long!")
        }

        this.bytes = bytes.copyOf(PASSWORD_LENGTH)
        value = bytes.toHexString(HexFormat.UpperCase).replace("-", "")
    }

    private fun parse(password: String): Boolean {
        val sanitizedPassword = password.replace("0x", "", ignoreCase = true)
        val parsingSucceeded: Boolean
        var parsedValue: ULong? = sanitizedPassword.toULongOrNull(16)

        if (parsedValue != null) {
            parsingSucceeded = true
        } else {
            parsedValue = sanitizedPassword.toULongOrNull()
            parsingSucceeded = parsedValue != null
        }

        if (parsingSucceeded) {
            bytes = parsedValue!!.toByteArray()
            value = password
        }

        return parsingSucceeded
    }

    private fun ULong.toByteArray(): ByteArray {
        return ByteArray(8) { i -> ((this shr (i * 8)) and 0xFFu).toByte() }
    }
}
