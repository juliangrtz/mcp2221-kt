package mcp2221.commands

open class WriteFlashDataCommand(private val writeFlashSubCode: WriteFlashSubCode) :
    Command(CommandCode.WriteFlashData) {
    override fun serialize() {
        super.serialize()
        stream.write(writeFlashSubCode.subCode)
    }
}