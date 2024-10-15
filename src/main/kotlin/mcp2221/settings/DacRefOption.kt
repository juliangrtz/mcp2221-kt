package mcp2221.settings

enum class DacRefOption(val value: Int) {
    /**
     * DAC reference output is DAC VRM voltage selection.
     */
    Vrm(1),

    /**
     * DAC reference output is VDD (factory default).
     */
    VDD(0)
}