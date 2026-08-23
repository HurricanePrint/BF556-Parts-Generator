package com.itextpdf.svg.renderers;

import com.itextpdf.svg.MarkerVertexType;
import com.itextpdf.svg.renderers.impl.MarkerSvgNodeRenderer;

public interface IMarkerCapable {
  void drawMarker(SvgDrawContext paramSvgDrawContext, MarkerVertexType paramMarkerVertexType);
  
  double getAutoOrientAngle(MarkerSvgNodeRenderer paramMarkerSvgNodeRenderer, boolean paramBoolean);
}


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/svg/renderers/IMarkerCapable.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */