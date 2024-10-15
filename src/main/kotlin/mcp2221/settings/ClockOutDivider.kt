package mcp2221.settings

enum class ClockOutDivider(val value: Int) {
    /**
     * 375 kHz clock output.
     */
    Clock375kHz(0b111),

    /**
     * 750 kHz clock output.
     */
    Clock750kHz(0b110),

    /**
     * 1.5 MHz clock output.
     */
    Clock1_5MHz(0b101),

    /**
     * 3 MHz clock output.
     */
    Clock3MHz(0b100),

    /**
     * 6 MHz clock output.
     */
    Clock6MHz(0b011),

    /**
     * 12 MHz clock output (factory default).
     */
    Clock12MHz(0b010),

    /**
     * 24 MHz clock output.
     */
    Clock24Mhz(0b001),

    /**
     * Reserved.
     */
    Reserved(0)
}