package mcp2221.responses

import Extensions.toXXStr
import mcp2221.commands.CommandCode
import java.io.ByteArrayInputStream

sealed class Response(override val commandCode: CommandCode) : IResponse {
    final override var executionResult: Byte = 0xFF.toByte()
        private set

    /**
     * Deserialize the response from the stream.
     */
    override fun deserialize(stream: ByteArrayInputStream) {
        val responseCode = stream.read()
        if (responseCode != commandCode.code) {
            throw IllegalStateException(
                "Unexpected response code. Expected: [0x${
                    commandCode.code.toByte().toXXStr()
                }] Actual: [0x${responseCode.toByte().toXXStr()}]"
            )
        }

        executionResult = stream.read().toByte()
    }
}