package mcp2221.commands

class WriteUsbProductDescriptorCommand(productDescriptor: String) :
    WriteFlashDataStringCommand(productDescriptor, WriteFlashSubCode.WriteUSBProductDescriptor)