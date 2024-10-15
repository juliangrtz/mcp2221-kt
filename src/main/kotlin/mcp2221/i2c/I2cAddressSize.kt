package mcp2221.i2c

enum class I2cAddressSize {
    SevenBit,
    TenBit
    ;

    val byteSize: Int
        get() = if (this == SevenBit) 1 else 2
}