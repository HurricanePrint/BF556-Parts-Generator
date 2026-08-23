package com.itextpdf.kernel.xmp;

import com.itextpdf.kernel.xmp.properties.XMPAliasInfo;
import java.util.Map;

public interface XMPSchemaRegistry {
  String registerNamespace(String paramString1, String paramString2) throws XMPException;
  
  String getNamespacePrefix(String paramString);
  
  String getNamespaceURI(String paramString);
  
  Map getNamespaces();
  
  Map getPrefixes();
  
  void deleteNamespace(String paramString);
  
  XMPAliasInfo resolveAlias(String paramString1, String paramString2);
  
  XMPAliasInfo[] findAliases(String paramString);
  
  XMPAliasInfo findAlias(String paramString);
  
  Map getAliases();
}


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/kernel/xmp/XMPSchemaRegistry.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */