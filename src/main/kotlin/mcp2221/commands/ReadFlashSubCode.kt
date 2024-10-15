package mcp2221.commands

enum class ReadFlashSubCode(val subCode: Int) {
    // Read Chip Settings – it will read the MCP2221 flash settings
    ReadChipSettings(0x00),

    // Read GP Settings – it will read the MCP2221 flash GP settings
    ReadGpSettings(0x01),

    // Read USB Manufacturer Descriptor String – reads the USB Manufacturer
    // String Descriptor used during the USB enumeration
    ReadManufacturerDescriptor(0x02),

    // Read USB Product Descriptor String– reads the USB Product String
    // Descriptor used during the USB enumeration
    ReadUSBProductDescriptor(0x03),

    // Read USB Serial Number Descriptor String – reads the USB Serial
    // Number String Descriptor that is used during USB enumeration.
    // This serial number can be changed by the user through a specific USB HID command.
    ReadUSBSerialNumberDescriptor(0x04),

    // Read Chip Factory Serial Number – reads the factory-set serial number. This serial number cannot be changed.
    ReadChipFactorySerialNumber(0x05),
    ;

    companion object {
        private val map = entries.associateBy(ReadFlashSubCode::subCode)
        operator fun get(value: Int) = map[value]
        operator fun get(value: UByte) = map[value.toInt()]
    }
}