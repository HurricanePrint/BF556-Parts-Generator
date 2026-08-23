package com.itextpdf.kernel.pdf.filters;

import com.itextpdf.kernel.pdf.PdfDictionary;
import com.itextpdf.kernel.pdf.PdfName;
import com.itextpdf.kernel.pdf.PdfObject;

public interface IFilterHandler {
  byte[] decode(byte[] paramArrayOfbyte, PdfName paramPdfName, PdfObject paramPdfObject, PdfDictionary paramPdfDictionary);
}


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/kernel/pdf/filters/IFilterHandler.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */