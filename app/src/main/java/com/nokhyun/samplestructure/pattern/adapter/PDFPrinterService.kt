package com.nokhyun.samplestructure.pattern.adapter

typealias PDF = PrinterData

// 기존 기능
class PDFPrinterService {
    fun print(data: PrinterData): PDF {
        return data
    }
}

// PDF -> PNG 로 변경하는 Adapter
class PDFTOPNGPrintAdapter(
    private val pdfPrinterService: PDFPrinterService,
) : PNGPrinter {

    private val pngPrinter: PNGPrinter = PNGPrintService()

    override fun print(data: PrinterData): PNG {
        val pdf = pdfPrinterService.print(data)
        return pngPrinter.print(pdf)
    }
}

class PNGPrintService: PNGPrinter {
    override fun print(data: PrinterData): PNG = data.toPNG()

    private fun PrinterData.toPNG() = "PNG File"
}