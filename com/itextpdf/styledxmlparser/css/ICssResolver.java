package com.itextpdf.styledxmlparser.css;

import com.itextpdf.styledxmlparser.css.resolve.AbstractCssContext;
import com.itextpdf.styledxmlparser.node.INode;
import java.util.Map;

public interface ICssResolver {
  Map<String, String> resolveStyles(INode paramINode, AbstractCssContext paramAbstractCssContext);
}


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/styledxmlparser/css/ICssResolver.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */