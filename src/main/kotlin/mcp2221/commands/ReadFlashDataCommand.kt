package mcp2221.commands

open class ReadFlashDataCommand(val readFlashSubCode: ReadFlashSubCode) : Command(CommandCode.ReadFlashData) {
    override fun serialize() {
        super.serialize()
        stream.write(readFlashSubCode.subCode)
    }
}