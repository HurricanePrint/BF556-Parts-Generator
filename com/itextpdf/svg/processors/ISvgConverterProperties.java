package com.itextpdf.svg.processors;

import com.itextpdf.layout.font.FontProvider;
import com.itextpdf.styledxmlparser.css.media.MediaDeviceDescription;
import com.itextpdf.svg.renderers.factories.ISvgNodeRendererFactory;

public interface ISvgConverterProperties {
  ISvgNodeRendererFactory getRendererFactory();
  
  FontProvider getFontProvider();
  
  String getCharset();
  
  String getBaseUri();
  
  MediaDeviceDescription getMediaDeviceDescription();
}


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/svg/processors/ISvgConverterProperties.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */