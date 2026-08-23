package com.itextpdf.kernel.pdf.canvas.parser;

import com.itextpdf.kernel.pdf.PdfLiteral;
import com.itextpdf.kernel.pdf.PdfObject;
import java.util.List;

public interface IContentOperator {
  void invoke(PdfCanvasProcessor paramPdfCanvasProcessor, PdfLiteral paramPdfLiteral, List<PdfObject> paramList);
}


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/kernel/pdf/canvas/parser/IContentOperator.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */