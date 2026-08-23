package com.itextpdf.svg.renderers;

import java.util.List;

public interface IBranchSvgNodeRenderer extends ISvgNodeRenderer {
  void addChild(ISvgNodeRenderer paramISvgNodeRenderer);
  
  List<ISvgNodeRenderer> getChildren();
}


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/svg/renderers/IBranchSvgNodeRenderer.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */