package com.itextpdf.io.font;

public interface IExtraEncoding {
  byte[] charToByte(String paramString1, String paramString2);
  
  byte[] charToByte(char paramChar, String paramString);
  
  String byteToChar(byte[] paramArrayOfbyte, String paramString);
}


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/io/font/IExtraEncoding.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */