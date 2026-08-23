package com.itextpdf.kernel.font;

import com.itextpdf.kernel.pdf.PdfName;
import com.itextpdf.kernel.pdf.PdfStream;

interface IDocFontProgram {
  PdfStream getFontFile();
  
  PdfName getFontFileName();
  
  PdfName getSubtype();
}


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/kernel/font/IDocFontProgram.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */