package com.itextpdf.layout;

public interface IPropertyContainer {
  boolean hasProperty(int paramInt);
  
  boolean hasOwnProperty(int paramInt);
  
  <T1> T1 getProperty(int paramInt);
  
  <T1> T1 getOwnProperty(int paramInt);
  
  <T1> T1 getDefaultProperty(int paramInt);
  
  void setProperty(int paramInt, Object paramObject);
  
  void deleteOwnProperty(int paramInt);
}


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/layout/IPropertyContainer.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */