package mcp2221.commands

class ResetCommand : Command(CommandCode.Reset) {
    override fun serialize() {
        super.serialize()

        stream.write(0xAB)
        stream.write(0xCD)
        stream.write(0xEF)
    }
}