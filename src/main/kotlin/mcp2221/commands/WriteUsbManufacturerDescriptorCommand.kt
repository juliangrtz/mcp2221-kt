package mcp2221.commands

class WriteUsbManufacturerDescriptorCommand(productDescriptor: String) :
    WriteFlashDataStringCommand(productDescriptor, WriteFlashSubCode.WriteManufacturerDescriptor)