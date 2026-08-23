package com.itextpdf.kernel.pdf;

import com.itextpdf.kernel.geom.PageSize;

public interface IPdfPageFactory {
  PdfPage createPdfPage(PdfDictionary paramPdfDictionary);
  
  PdfPage createPdfPage(PdfDocument paramPdfDocument, PageSize paramPageSize);
}


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/kernel/pdf/IPdfPageFactory.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */