package mcp2221.gpio

enum class Gp3Designation(val value: Int) {
    /**
     * GPIO operation
     */
    Gpio(0),

    /**
     * Dedicated function operation (LED_I2C)
     */
    LedI2c(1),

    /**
     * Alternate Function 0 (ADC3)
     */
    Adc3(2),

    /**
     * Alternate Function 1 (DAC2)
     */
    Dac2(3),
    ;

    companion object {
        private val map = Gp3Designation.entries.associateBy(Gp3Designation::value)
        operator fun get(value: Int) = map[value]
    }
}