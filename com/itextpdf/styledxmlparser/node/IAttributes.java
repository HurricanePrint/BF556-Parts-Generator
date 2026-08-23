package com.itextpdf.styledxmlparser.node;

public interface IAttributes extends Iterable<IAttribute> {
  String getAttribute(String paramString);
  
  void setAttribute(String paramString1, String paramString2);
  
  int size();
}


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/styledxmlparser/node/IAttributes.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */