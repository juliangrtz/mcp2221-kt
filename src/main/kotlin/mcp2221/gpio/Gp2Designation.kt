package mcp2221.gpio

enum class Gp2Designation(val value: Int) {
    /**
     * GPIO operation
     */
    Gpio(0),

    /**
     * Dedicated function operation (USB)
     */
    Usb(1),

    /**
     * Alternate Function 0 (ADC2)
     */
    Adc2(2),

    /**
     * Alternate Function 1 (DAC1)
     */
    Dac1(3),
    ;

    companion object {
        private val map = Gp2Designation.entries.associateBy(Gp2Designation::value)
        operator fun get(value: Int) = map[value]
    }
}