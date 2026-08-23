package com.itextpdf.svg.processors;

import com.itextpdf.layout.font.FontProvider;
import com.itextpdf.layout.font.FontSet;
import com.itextpdf.svg.renderers.ISvgNodeRenderer;
import java.util.Map;

public interface ISvgProcessorResult {
  Map<String, ISvgNodeRenderer> getNamedObjects();
  
  ISvgNodeRenderer getRootRenderer();
  
  FontProvider getFontProvider();
  
  FontSet getTempFonts();
}


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/svg/processors/ISvgProcessorResult.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */