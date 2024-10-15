package mcp2221.settings

enum class SramChipSecurity(val securityLevel: Int) {
    /**
     * Permanently locked.
     */
    PermanentlyLocked(0b10),

    /**
     * Password protected.
     */
    PasswordProtection(0b01),

    /**
     * Unsecured.
     */
    Unprotected(0b00)
}