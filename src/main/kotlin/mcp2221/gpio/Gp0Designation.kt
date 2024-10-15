package mcp2221.gpio

enum class Gp0Designation(val value: Int) {
    /**
     * GPIO operation
     */
    Gpio(0),

    /**
     * Dedicated function operation (SSPND)
     */
    Sspnd(1),

    /**
     * Alternate Function 0
     */
    LedURx(2),
    ;

    companion object {
        private val map = Gp0Designation.entries.associateBy(Gp0Designation::value)
        operator fun get(value: Int) = map[value]
    }
}