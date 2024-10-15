package mcp2221.settings

abstract class Settings {
    companion object {
        const val MAX_USB_MA_VALUE = 500
    }

    var _powerRequestMa: Int = 0

    /**
     * The clock duty cycle.
     */
    var clockDutyCycle: ClockDutyCycle? = null

    /**
     * The current clock out divider value.
     */
    var clockDivider: ClockOutDivider? = null

    /**
     * DAC Reference Vrm voltage option.
     */
    var dacRefVrm: VrmRef? = null

    /**
     * DAC reference option.
     */
    var dacRefOption: DacRefOption? = null

    /**
     * Power up DAC Output Value.
     */
    var dacOutput: Byte = 0

    /**
     * ADC Reference Vrm voltage option.
     */
    var adcRefVrm: VrmRef? = null

    /**
     * ADC reference option.
     */
    var adcRefOption: AdcRefOption? = null

    /**
     * If set, the interrupt detection flag will be set when a negative edge occurs.
     */
    var interruptNegativeEdge: Boolean = false

    /**
     * If set, the interrupt detection flag will be set when a positive edge occurs.
     */
    var interruptPositiveEdge: Boolean = false

    /**
     * The requested mA value during the USB enumeration.
     */
    open var powerRequestMa: Int
        get() = _powerRequestMa * 2
        protected set(value) {
            if (value > MAX_USB_MA_VALUE) {
                throw IllegalArgumentException("Value must be less than $MAX_USB_MA_VALUE")
            }
            _powerRequestMa = value / 2
        }
}