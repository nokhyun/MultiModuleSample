package com.nokhyun.samplestructure

import com.nokhyun.samplestructure.pattern.adapter.PDFPrinterService
import com.nokhyun.samplestructure.pattern.adapter.PDFTOPNGPrintAdapter
import com.nokhyun.samplestructure.pattern.adapter.PrinterData
import junit.framework.TestCase.assertEquals
import org.junit.Test

class AdapterTest {

    private val pdfPrinterService = PDFPrinterService()

    @Test
    fun `Adpater_테스트`() {
        val pdfToPngPrintAdapter = PDFTOPNGPrintAdapter(pdfPrinterService)

        val result = pdfToPngPrintAdapter.print(PrinterData(title = "Hello", text = "PDF"))
        assertEquals("PNG File", result)
    }
}