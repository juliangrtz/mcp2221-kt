package mcp2221.settings

enum class UsbSelfPowered(val value: Int) {
    /**
     * Chip will enumerate on the USB bus as being self-powered.
     */
    SelfPowered(1),

    /**
     * Chip will enumerate on the USB bus as being USB-bus powered (factory default).
     */
    BusPowered(0)
}