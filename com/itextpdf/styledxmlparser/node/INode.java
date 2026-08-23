package com.itextpdf.styledxmlparser.node;

import java.util.List;

public interface INode {
  List<INode> childNodes();
  
  void addChild(INode paramINode);
  
  INode parentNode();
}


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/styledxmlparser/node/INode.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */