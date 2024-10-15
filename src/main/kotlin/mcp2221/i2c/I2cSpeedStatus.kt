package mcp2221.i2c

enum class I2cSpeedStatus(val value: Int) {
    /**
     * No Set I2C/SMBus communication speed was issued.
     */
    NotIssued(0x00),

    /**
     * The new I2C/SMBus communication speed is now considered.
     */
    Set(0x20),

    /**
     * The I2C/SMBus communication speed was not set (e.g., I2C transfer in progress).
     */
    NotSet(0x21),
    ;
}