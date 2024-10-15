package mcp2221

import mcp2221.i2c.I2cAddress
import mcp2221.i2c.I2cAddressSize
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class I2CTest {
    @Test
    fun testSevenBitAddress() {
        val address = I2cAddress(0x08.toUInt())

        assertEquals(listOf(0x10.toByte()), address.writeAddress)
        assertEquals(listOf(0x11.toByte()), address.readAddress)
    }

    @Test
    fun testSevenBitAddressException() {
        assertThrows<IllegalArgumentException> {
            I2cAddress(0x07.toUInt())
        }
    }

    @Test
    fun testTenBitAddress() {
        val testData = listOf(
            Triple(0x318u, 0xF6.toByte(), 0x18.toByte()),
            Triple(0x218u, 0xF4.toByte(), 0x18.toByte()),
            Triple(0x118u, 0xF2.toByte(), 0x18.toByte()),
            Triple(0x020u, 0xF0.toByte(), 0x20.toByte())
        )

        for ((address, msb, lsb) in testData) {
            val i2cAddress = I2cAddress(address, I2cAddressSize.TenBit)
            assertEquals(listOf(msb, lsb), i2cAddress.writeAddress)
            assertEquals(listOf((msb + 1).toByte(), lsb), i2cAddress.readAddress)
        }
    }

    @Test
    fun testTenBitAddressException() {
        assertThrows<IllegalArgumentException> {
            I2cAddress(0x4FF.toUInt(), I2cAddressSize.TenBit)
        }
    }
}