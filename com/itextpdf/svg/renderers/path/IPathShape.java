package com.itextpdf.svg.renderers.path;

import com.itextpdf.kernel.geom.Point;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;

public interface IPathShape {
  void draw(PdfCanvas paramPdfCanvas);
  
  void setCoordinates(String[] paramArrayOfString, Point paramPoint);
  
  Point getEndingPoint();
  
  boolean isRelative();
}


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/svg/renderers/path/IPathShape.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */