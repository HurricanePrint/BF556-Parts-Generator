package com.itextpdf.kernel.pdf.canvas.draw;

import com.itextpdf.kernel.colors.Color;
import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;

public interface ILineDrawer {
  void draw(PdfCanvas paramPdfCanvas, Rectangle paramRectangle);
  
  float getLineWidth();
  
  void setLineWidth(float paramFloat);
  
  Color getColor();
  
  void setColor(Color paramColor);
}


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/kernel/pdf/canvas/draw/ILineDrawer.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */