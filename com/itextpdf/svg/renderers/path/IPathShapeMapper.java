package com.itextpdf.svg.renderers.path;

import java.util.Map;

public interface IPathShapeMapper {
  Map<String, IPathShape> getMapping();
  
  Map<String, Integer> getArgumentCount();
}


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/svg/renderers/path/IPathShapeMapper.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */