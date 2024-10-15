package mcp2221.settings

enum class ClockDutyCycle(val value: Int) {
    /**
     * Duty cycle 75% (75% of one clock period is logic ‘1’ and 25% of one clock period is logic ‘0’).
     */
    DutyCycle75(0b11),

    /**
     * Duty cycle 50% (50% of one clock period is logic ‘1’ and 50% of one clock period is logic ‘0’) (factory default).
     */
    DutyCycle50(0b10),

    /**
     * Duty cycle 25% (25% of one clock period is logic ‘1’ and 75% of one clock period is logic ‘0’).
     */
    DutyCycle25(0b01),

    /**
     * Duty cycle 0% (100% of one clock period is logic ‘0’).
     */
    DutyCycle0(0b00)
}