package com.itextpdf.layout.element;

import com.itextpdf.layout.IPropertyContainer;
import com.itextpdf.layout.renderer.IRenderer;

public interface IElement extends IPropertyContainer {
  void setNextRenderer(IRenderer paramIRenderer);
  
  IRenderer getRenderer();
  
  IRenderer createRendererSubTree();
}


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/layout/element/IElement.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */