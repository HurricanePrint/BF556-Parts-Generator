package com.itextpdf.layout.element;

import com.itextpdf.layout.Document;

public interface ILargeElement extends IElement {
  boolean isComplete();
  
  void complete();
  
  void flush();
  
  void flushContent();
  
  void setDocument(Document paramDocument);
}


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/layout/element/ILargeElement.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */