package com.itextpdf.kernel.pdf.canvas.parser.listener;

import com.itextpdf.kernel.geom.Rectangle;

public interface IPdfTextLocation {
  Rectangle getRectangle();
  
  String getText();
  
  int getPageNumber();
}


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/kernel/pdf/canvas/parser/listener/IPdfTextLocation.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */