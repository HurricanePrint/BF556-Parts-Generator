package com.itextpdf.svg.renderers.impl;

import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.svg.renderers.ISvgNodeRenderer;

public interface ISvgTextNodeRenderer extends ISvgNodeRenderer {
  float getTextContentLength(float paramFloat, PdfFont paramPdfFont);
  
  float[] getRelativeTranslation();
  
  boolean containsRelativeMove();
  
  boolean containsAbsolutePositionChange();
  
  float[][] getAbsolutePositionChanges();
}


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/svg/renderers/impl/ISvgTextNodeRenderer.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */