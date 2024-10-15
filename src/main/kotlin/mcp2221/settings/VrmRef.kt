package mcp2221.settings

enum class VrmRef(val value: Int) {
    /**
     * Reference voltage is 4.096V (only if VDD is above this voltage).
     */
    Vrm4096V(0b11),

    /**
     * Reference voltage is 2.048V.
     */
    Vrm2048V(0b10),

    /**
     * Reference voltage is 1.024V.
     */
    Vrm1024V(0b01),

    /**
     * Reference voltage is off.
     */
    VrmOff(0)
}