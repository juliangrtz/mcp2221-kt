package mcp2221.settings

enum class UsbRemoteWake(val value: Int) {
    /**
     * Chip will enumerate on the USB bus as being able to wake-up the USB host
     */
    Enabled(1),

    /**
     * Chip will enumerate as not being capable of remote wake-up of the USB host (factory default)
     */
    Disabled(0)
}