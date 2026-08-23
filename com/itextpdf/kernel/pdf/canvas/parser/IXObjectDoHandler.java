package com.itextpdf.kernel.pdf.canvas.parser;

import com.itextpdf.kernel.pdf.PdfName;
import com.itextpdf.kernel.pdf.PdfStream;
import com.itextpdf.kernel.pdf.canvas.CanvasTag;
import java.util.Stack;

public interface IXObjectDoHandler {
  void handleXObject(PdfCanvasProcessor paramPdfCanvasProcessor, Stack<CanvasTag> paramStack, PdfStream paramPdfStream, PdfName paramPdfName);
}


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/kernel/pdf/canvas/parser/IXObjectDoHandler.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */