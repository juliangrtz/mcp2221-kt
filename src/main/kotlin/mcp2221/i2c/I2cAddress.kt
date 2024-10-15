package mcp2221.i2c

class I2cAddress(address: UInt, private val size: I2cAddressSize = I2cAddressSize.SevenBit) {
    companion object {
        const val SEVEN_BIT_RANGE_LOWER: UInt = 0x07u
        const val SEVEN_BIT_RANGE_UPPER: UInt = 0x78u
        const val TEN_BIT_RANGE_UPPER: UInt = 0x03FFu
    }

    var value: UInt = 0u
        private set

    var readAddress: List<Byte> = ArrayList()
    var writeAddress: List<Byte> = ArrayList()

    init {
        when (size) {
            I2cAddressSize.SevenBit -> {
                if (address <= SEVEN_BIT_RANGE_LOWER || address >= SEVEN_BIT_RANGE_UPPER) {
                    throw IllegalArgumentException(
                        "SevenBit address must be between 0x${
                            SEVEN_BIT_RANGE_LOWER.toString(
                                16
                            )
                        } and 0x${SEVEN_BIT_RANGE_UPPER.toString(16)}"
                    )
                }
            }

            I2cAddressSize.TenBit -> {
                if (address > TEN_BIT_RANGE_UPPER) {
                    throw IllegalArgumentException(
                        "TenBit address must be between 0x00 and 0x${
                            TEN_BIT_RANGE_UPPER.toString(
                                16
                            )
                        }"
                    )
                }
            }
        }

        calculateAddress(address, size)
    }

    override fun toString(): String {
        return "Value: 0x${value.toString(16).uppercase()} Size: $size"
    }

    private fun calculateAddress(address: UInt, size: I2cAddressSize) {
        value = address
        when (size) {
            I2cAddressSize.SevenBit -> {
                val baseAddress = (address shl 1).toByte()
                writeAddress = listOf(baseAddress)
                readAddress = listOf((baseAddress + 1).toByte())
            }

            I2cAddressSize.TenBit -> {
                val msb = (((address or 0xF800u) and 0xFB00u) shr 7).toByte()
                val lsb = (address and 0x00FFu).toByte()
                writeAddress = listOf(msb, lsb)
                readAddress = listOf((msb + 1).toByte(), lsb)
            }
        }
    }
}