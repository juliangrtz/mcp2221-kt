# mcp2221a-kt

This is a Kotlin library allowing you to communicate with [MCP2221](https://www.microchip.com/en-us/product/MCP2221) and [MCP2221A](https://www.microchip.com/en-us/product/MCP2221A) devices over USB. It supports all of the operations described in [the data sheet](docs/data_sheet.pdf).

## Usage example

```kotlin
import mcp2221.MCP2221Device
import mcp2221.commands.ResetCommand

fun resetDevice() {
    val device = MCP2221Device(".*MCP2221.*")
    // Device is now opened and ready to use.
    
    device.reset()
    // or alternatively:
    device.sendCommand(ResetCommand())
    
    // Output:
    // Device reset.
}
```

## Roadmap

* improve testing
* improve error handling
* add listener functionality to observe GPIO statuses
* create GUI similar to the [utility tool by MicroChip](https://ww1.microchip.com/downloads/aemDocuments/documents/APID/ProductDocuments/SoftwareLibraries/Firmware/MCP2221Utility.zip)

## Acknowledgements

The code is based on [this .NET imnplementation](https://github.com/DerekGn/MCP2221IO) by DerekGn.

## License

cf. [LICENSE](LICENSE)
