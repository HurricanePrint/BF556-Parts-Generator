package com.itextpdf.svg.renderers;

import java.util.Map;

public interface ISvgNodeRenderer {
  void setParent(ISvgNodeRenderer paramISvgNodeRenderer);
  
  ISvgNodeRenderer getParent();
  
  void draw(SvgDrawContext paramSvgDrawContext);
  
  void setAttributesAndStyles(Map<String, String> paramMap);
  
  String getAttribute(String paramString);
  
  void setAttribute(String paramString1, String paramString2);
  
  Map<String, String> getAttributeMapCopy();
  
  ISvgNodeRenderer createDeepCopy();
}


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/svg/renderers/ISvgNodeRenderer.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */