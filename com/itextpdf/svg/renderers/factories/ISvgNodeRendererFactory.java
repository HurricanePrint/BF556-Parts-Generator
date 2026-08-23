package com.itextpdf.svg.renderers.factories;

import com.itextpdf.styledxmlparser.node.IElementNode;
import com.itextpdf.svg.renderers.ISvgNodeRenderer;

public interface ISvgNodeRendererFactory {
  ISvgNodeRenderer createSvgNodeRendererForTag(IElementNode paramIElementNode, ISvgNodeRenderer paramISvgNodeRenderer);
  
  boolean isTagIgnored(IElementNode paramIElementNode);
}


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/svg/renderers/factories/ISvgNodeRendererFactory.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */