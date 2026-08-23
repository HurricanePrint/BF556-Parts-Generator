package com.itextpdf.kernel.xmp.properties;

import com.itextpdf.kernel.xmp.options.PropertyOptions;

public interface XMPPropertyInfo extends XMPProperty {
  String getNamespace();
  
  String getPath();
  
  String getValue();
  
  PropertyOptions getOptions();
}


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/kernel/xmp/properties/XMPPropertyInfo.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */