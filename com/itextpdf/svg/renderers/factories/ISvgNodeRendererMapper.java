package com.itextpdf.svg.renderers.factories;

import com.itextpdf.svg.renderers.ISvgNodeRenderer;
import java.util.Collection;
import java.util.Map;

@Deprecated
public interface ISvgNodeRendererMapper {
  Map<String, Class<? extends ISvgNodeRenderer>> getMapping();
  
  Collection<String> getIgnoredTags();
}


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/svg/renderers/factories/ISvgNodeRendererMapper.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */