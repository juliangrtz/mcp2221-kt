package mcp2221.settings

enum class ChipSecurity(val securityLevel: Int) {
    Unprotected(0b00),
    PasswordProtection(0b01),
    PermanentlyLockedA(0b11),
    PermanentlyLockedB(0b10),
}