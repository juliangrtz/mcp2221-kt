package mcp2221.settings

import mcp2221.gpio.Gp0Designation
import mcp2221.gpio.Gp1Designation
import mcp2221.gpio.Gp2Designation
import mcp2221.gpio.Gp3Designation
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream

class GpSettings {
    var Gp0PowerUpSetting: GpSetting<Gp0Designation> = GpSetting()
        internal set
    var Gp1PowerUpSetting: GpSetting<Gp1Designation> = GpSetting()
        internal set
    var Gp2PowerUpSetting: GpSetting<Gp2Designation> = GpSetting()
        internal set
    var Gp3PowerUpSetting: GpSetting<Gp3Designation> = GpSetting()
        internal set

    internal fun deserialize(stream: ByteArrayInputStream) {
        Gp0PowerUpSetting.deserialize(stream, Gp0Designation::class.java)
        Gp1PowerUpSetting.deserialize(stream, Gp1Designation::class.java)
        Gp2PowerUpSetting.deserialize(stream, Gp2Designation::class.java)
        Gp3PowerUpSetting.deserialize(stream, Gp3Designation::class.java)
    }

    internal fun serialize(): ByteArray {
        val out = ByteArrayOutputStream()
        out.write(Gp0PowerUpSetting.serialize())
        out.write(Gp1PowerUpSetting.serialize())
        out.write(Gp2PowerUpSetting.serialize())
        out.write(Gp3PowerUpSetting.serialize())
        return out.toByteArray()
    }

    override fun toString(): String {
        return """
            Gp0: $Gp0PowerUpSetting
            Gp1: $Gp1PowerUpSetting
            Gp2: $Gp2PowerUpSetting
            Gp3: $Gp3PowerUpSetting
        """.trimIndent()
    }
}