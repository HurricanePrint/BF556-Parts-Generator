package com.itextpdf.layout.renderer;

import com.itextpdf.layout.IPropertyContainer;
import com.itextpdf.layout.layout.LayoutArea;
import com.itextpdf.layout.layout.LayoutContext;
import com.itextpdf.layout.layout.LayoutResult;
import java.util.List;

public interface IRenderer extends IPropertyContainer {
  void addChild(IRenderer paramIRenderer);
  
  LayoutResult layout(LayoutContext paramLayoutContext);
  
  void draw(DrawContext paramDrawContext);
  
  LayoutArea getOccupiedArea();
  
  <T1> T1 getProperty(int paramInt, T1 paramT1);
  
  IRenderer setParent(IRenderer paramIRenderer);
  
  IRenderer getParent();
  
  IPropertyContainer getModelElement();
  
  List<IRenderer> getChildRenderers();
  
  boolean isFlushed();
  
  void move(float paramFloat1, float paramFloat2);
  
  IRenderer getNextRenderer();
}


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/layout/renderer/IRenderer.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */