package mcp2221.commands

enum class CommandCode(
    val code: Int
) {
    StatusSetParameters(0x10),
    GetI2cData(0x40),
    SetGpioValues(0x50),
    GetGpioValues(0x51),
    ReadFlashData(0xB0),
    WriteFlashData(0xB1),
    SendFlashAccessPassword(0xB2),
    Reset(0x70),
    SetSram(0x60),
    GetSram(0x61),
    ReadI2cData(0x91),
    WriteI2cData(0x90),
    WriteI2cDataRepeatedStart(0x92),
    ReadI2cDataRepeatedStart(0x93),
    WriteI2cDataNoStop(0x94),
    ;

    companion object {
        private val map = entries.associateBy(CommandCode::code)
        operator fun get(value: Int) = map[value]
        operator fun get(value: UByte) = map[value.toInt()]
    }
}