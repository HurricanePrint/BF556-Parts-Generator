package com.itextpdf.io.font.otf;

import java.io.Serializable;

public class ScriptRecord implements Serializable {
  private static final long serialVersionUID = 1670929244968728679L;
  
  public String tag;
  
  public LanguageRecord defaultLanguage;
  
  public LanguageRecord[] languages;
}


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/io/font/otf/ScriptRecord.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */