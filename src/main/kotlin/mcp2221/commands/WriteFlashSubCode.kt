package mcp2221.commands

enum class WriteFlashSubCode(val subCode: Int) {
    // Write Chip Settings – it will write the MCP2221 flash settings
    WriteChipSettings(0x00),

    // Write GP Settings – it will write the MCP2221 flash GP settings
    WriteGpSettings(0x01),

    // Write USB Manufacturer Descriptor String – writes the USB Manufacturer
    // String Descriptor used during the USB enumeration
    WriteManufacturerDescriptor(0x02),

    // Write USB Product Descriptor String– writes the USB Product String
    // Descriptor used during the USB enumeration
    WriteUSBProductDescriptor(0x03),

    // Write USB Serial Number Descriptor String – writes the USB Serial
    // Number String Descriptor that is used during USB enumeration.
    // This serial number can be changed by the user through a specific USB HID command.
    WriteUSBSerialNumberDescriptor(0x04),

    ;

    companion object {
        private val map = entries.associateBy(WriteFlashSubCode::subCode)
        operator fun get(value: Int) = map[value]
        operator fun get(value: UByte) = map[value.toInt()]
    }
}