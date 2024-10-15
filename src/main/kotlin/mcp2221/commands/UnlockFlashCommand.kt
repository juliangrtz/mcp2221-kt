package mcp2221.commands

import mcp2221.Password

class UnlockFlashCommand(private val password: Password) : Command(CommandCode.SendFlashAccessPassword) {
    override fun serialize() {
        super.serialize()
        stream.write(password.bytes)
    }
}