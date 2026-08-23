package com.itextpdf.styledxmlparser.css.selector;

import com.itextpdf.styledxmlparser.node.INode;

public interface ICssSelector {
  int calculateSpecificity();
  
  boolean matches(INode paramINode);
}


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/styledxmlparser/css/selector/ICssSelector.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */