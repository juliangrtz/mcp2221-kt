package mcp2221.commands

class WriteUsbSerialNumberCommand(productDescriptor: String) :
    WriteFlashDataStringCommand(productDescriptor, WriteFlashSubCode.WriteUSBSerialNumberDescriptor)