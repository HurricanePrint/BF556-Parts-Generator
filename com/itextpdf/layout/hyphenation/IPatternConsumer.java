package com.itextpdf.layout.hyphenation;

import java.util.List;

public interface IPatternConsumer {
  void addClass(String paramString);
  
  void addException(String paramString, List paramList);
  
  void addPattern(String paramString1, String paramString2);
}


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/layout/hyphenation/IPatternConsumer.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */