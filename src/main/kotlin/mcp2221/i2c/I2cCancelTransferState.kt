package mcp2221.i2c

enum class I2cCancelTransferState(val value: Int) {
    /**
     * No special operation (i.e., Cancel current I2C/SMBus transfer).
     */
    NoSpecialOperation(0x00),

    /**
     * The current I2C/SMBus transfer was marked for
     * cancellation. The actual I2C/SMBus transfer cancellation
     * and bus release will need some time (a few hundred
     * microseconds, depending on the communication speed
     * initially chosen for the canceled transfer).
     */
    MarkedForCancellation(0x10),

    /**
     * The I2C engine (inside MCP2221) was already in Idle mode.
     * The cancellation command had no effect.
     */
    EngineIdle(0x11),
    ;
}