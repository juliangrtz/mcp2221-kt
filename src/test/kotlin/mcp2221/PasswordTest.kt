package mcp2221

import org.junit.jupiter.api.Assertions.assertArrayEquals
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class PasswordTest {

    @Test
    fun tooLongPassword() {
        val bytes = listOf(0xAA, 0xBB, 0xCC, 0xDD, 0xEE, 0xFF, 0xEE, 0xDD, 0xCC, 0xBB).map { it.toByte() }.toByteArray()

        assertThrows<IllegalArgumentException> {
            Password(bytes)
        }
    }

    @Test
    fun nonHexCharacterPassword() {
        val nonHexStr = "0xDEADBEEFY"

        assertThrows<IllegalArgumentException> {
            Password(nonHexStr)
        }
    }

    @Test
    fun defaultPassword() {
        val default = Password.DefaultPassword
        assertEquals("0000000000000000", default.value)
        assertArrayEquals(byteArrayOf(0, 0, 0, 0, 0, 0, 0, 0), default.bytes)
    }

    @Test
    fun bytesToString() {
        val bytes = listOf(0xAA, 0xBB, 0xCC, 0xDD, 0xEE, 0xFF).map { it.toByte() }.toByteArray()

        val password = Password(bytes)

        assertEquals("AABBCCDDEEFF", password.value)
        assertArrayEquals(bytes.copyOf(Password.PASSWORD_LENGTH), password.bytes)
    }

    @Test
    fun stringToBytes() {
        val str = "0xDEADBEEF"

        val password = Password(str)

        assertEquals("0xDEADBEEF", password.value)

        val expected =
            listOf(0xDE, 0xAD, 0xBE, 0xEF)
                .map { it.toByte() }
                .reversed()
                .toByteArray()
                .copyOf(Password.PASSWORD_LENGTH)
        assertArrayEquals(expected, password.bytes)
    }

    @Test
    fun hexStringWithoutPrefix() {
        val str = "DEADBEEF"

        val password = Password(str)

        assertEquals("DEADBEEF", password.value)

        val expected =
            listOf(0xDE, 0xAD, 0xBE, 0xEF)
                .map { it.toByte() }
                .reversed()
                .toByteArray()
                .copyOf(Password.PASSWORD_LENGTH)
        assertArrayEquals(expected, password.bytes)
    }

    @Test
    fun minimumLengthPassword() {
        val bytes = listOf(0x00).map { it.toByte() }.toByteArray()

        val password = Password(bytes)

        assertEquals("00", password.value)
        assertArrayEquals(bytes.copyOf(Password.PASSWORD_LENGTH), password.bytes)
    }

    @Test
    fun emptyStringPassword() {
        assertThrows<IllegalArgumentException> {
            Password("")
        }
    }

    @Test
    fun zeroValuePassword() {
        val str = "0x0"

        val password = Password(str)

        assertEquals("0x0", password.value)

        val expected = byteArrayOf(0).copyOf(Password.PASSWORD_LENGTH)
        assertArrayEquals(expected, password.bytes)
    }

    @Test
    fun nullCharacterHandling() {
        val nullStr = "0x00000000"

        val password = Password(nullStr)

        assertEquals("0x00000000", password.value)
        assertArrayEquals(byteArrayOf(0, 0, 0, 0).copyOf(Password.PASSWORD_LENGTH), password.bytes)
    }

    @Test
    fun maxLengthBoundary() {
        val maxLengthStr = "0xFFFFFFFFFFFFFFFF"

        val password = Password(maxLengthStr)

        assertEquals("0xFFFFFFFFFFFFFFFF", password.value)
        assertArrayEquals(
            listOf(0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF).map { it.toByte() }.toByteArray(),
            password.bytes
        )
    }

    @Test
    fun testPasswordEquality() {
        val password1 = Password("0xAABBCCDD")
        val password2 = Password("AABBCCDD")

        assertEquals(password1.value.removePrefix("0x"), password2.value)
        assertArrayEquals(password1.bytes, password2.bytes)
    }
}