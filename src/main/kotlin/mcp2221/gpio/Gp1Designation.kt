package mcp2221.gpio

enum class Gp1Designation(val value: Int) {
    /**
     * GPIO operation
     */
    Gpio(0),

    /**
     * Dedicated function operation (clock output)
     */
    ClockOut(1),

    /**
     * Alternate Function 0 (ADC1)
     */
    Adc1(2),

    /**
     * Alternate Function 1 (LED_UTx)
     */
    LedUTx(3),

    /**
     * Alternate Function 2 (Interrupt detection)
     */
    Ioc(4),
    ;

    companion object {
        private val map = Gp1Designation.entries.associateBy(Gp1Designation::value)
        operator fun get(value: Int) = map[value]
    }
}