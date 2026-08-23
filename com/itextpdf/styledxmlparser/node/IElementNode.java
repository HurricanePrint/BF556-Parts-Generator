package com.itextpdf.styledxmlparser.node;

import java.util.List;
import java.util.Map;

public interface IElementNode extends INode, IStylesContainer {
  String name();
  
  IAttributes getAttributes();
  
  String getAttribute(String paramString);
  
  List<Map<String, String>> getAdditionalHtmlStyles();
  
  void addAdditionalHtmlStyles(Map<String, String> paramMap);
  
  String getLang();
}


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/styledxmlparser/node/IElementNode.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */