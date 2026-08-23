/*     */ package com.itextpdf.kernel.xmp.impl;
/*     */ 
/*     */ import com.itextpdf.kernel.xmp.XMPConst;
/*     */ import com.itextpdf.kernel.xmp.XMPException;
/*     */ import com.itextpdf.kernel.xmp.XMPSchemaRegistry;
/*     */ import com.itextpdf.kernel.xmp.options.AliasOptions;
/*     */ import com.itextpdf.kernel.xmp.properties.XMPAliasInfo;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.TreeMap;
/*     */ import java.util.regex.Pattern;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class XMPSchemaRegistryImpl
/*     */   implements XMPConst, XMPSchemaRegistry
/*     */ {
/*  58 */   private Map namespaceToPrefixMap = new HashMap<>();
/*     */ 
/*     */   
/*  61 */   private Map prefixToNamespaceMap = new HashMap<>();
/*     */ 
/*     */ 
/*     */   
/*  65 */   private Map aliasMap = new HashMap<>();
/*     */   
/*  67 */   private Pattern p = Pattern.compile("[/*?\\[\\]]");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public XMPSchemaRegistryImpl() {
/*     */     try {
/*  78 */       registerStandardNamespaces();
/*  79 */       registerStandardAliases();
/*     */     }
/*  81 */     catch (XMPException e) {
/*     */       
/*  83 */       throw new RuntimeException("The XMPSchemaRegistry cannot be initialized!");
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized String registerNamespace(String namespaceURI, String suggestedPrefix) throws XMPException {
/*  98 */     ParameterAsserts.assertSchemaNS(namespaceURI);
/*  99 */     ParameterAsserts.assertPrefix(suggestedPrefix);
/*     */     
/* 101 */     if (suggestedPrefix.charAt(suggestedPrefix.length() - 1) != ':')
/*     */     {
/* 103 */       suggestedPrefix = suggestedPrefix + ':';
/*     */     }
/*     */     
/* 106 */     if (!Utils.isXMLNameNS(suggestedPrefix.substring(0, suggestedPrefix
/* 107 */           .length() - 1)))
/*     */     {
/* 109 */       throw new XMPException("The prefix is a bad XML name", 201);
/*     */     }
/*     */     
/* 112 */     String registeredPrefix = (String)this.namespaceToPrefixMap.get(namespaceURI);
/* 113 */     String registeredNS = (String)this.prefixToNamespaceMap.get(suggestedPrefix);
/* 114 */     if (registeredPrefix != null)
/*     */     {
/*     */       
/* 117 */       return registeredPrefix;
/*     */     }
/*     */ 
/*     */     
/* 121 */     if (registeredNS != null) {
/*     */ 
/*     */ 
/*     */       
/* 125 */       String generatedPrefix = suggestedPrefix;
/* 126 */       for (int i = 1; this.prefixToNamespaceMap.containsKey(generatedPrefix); i++)
/*     */       {
/*     */         
/* 129 */         generatedPrefix = suggestedPrefix.substring(0, suggestedPrefix.length() - 1) + "_" + i + "_:";
/*     */       }
/*     */       
/* 132 */       suggestedPrefix = generatedPrefix;
/*     */     } 
/* 134 */     this.prefixToNamespaceMap.put(suggestedPrefix, namespaceURI);
/* 135 */     this.namespaceToPrefixMap.put(namespaceURI, suggestedPrefix);
/*     */ 
/*     */     
/* 138 */     return suggestedPrefix;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized void deleteNamespace(String namespaceURI) {
/* 148 */     String prefixToDelete = getNamespacePrefix(namespaceURI);
/* 149 */     if (prefixToDelete != null) {
/*     */       
/* 151 */       this.namespaceToPrefixMap.remove(namespaceURI);
/* 152 */       this.prefixToNamespaceMap.remove(prefixToDelete);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized String getNamespacePrefix(String namespaceURI) {
/* 162 */     return (String)this.namespaceToPrefixMap.get(namespaceURI);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized String getNamespaceURI(String namespacePrefix) {
/* 171 */     if (namespacePrefix != null && !namespacePrefix.endsWith(":"))
/*     */     {
/* 173 */       namespacePrefix = namespacePrefix + ":";
/*     */     }
/* 175 */     return (String)this.prefixToNamespaceMap.get(namespacePrefix);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized Map getNamespaces() {
/* 184 */     return Collections.unmodifiableMap(new TreeMap<>(this.namespaceToPrefixMap));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized Map getPrefixes() {
/* 193 */     return Collections.unmodifiableMap(new TreeMap<>(this.prefixToNamespaceMap));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void registerStandardNamespaces() throws XMPException {
/* 207 */     registerNamespace("http://www.w3.org/XML/1998/namespace", "xml");
/* 208 */     registerNamespace("http://www.w3.org/1999/02/22-rdf-syntax-ns#", "rdf");
/* 209 */     registerNamespace("http://purl.org/dc/elements/1.1/", "dc");
/* 210 */     registerNamespace("http://iptc.org/std/Iptc4xmpCore/1.0/xmlns/", "Iptc4xmpCore");
/* 211 */     registerNamespace("http://iptc.org/std/Iptc4xmpExt/2008-02-29/", "Iptc4xmpExt");
/* 212 */     registerNamespace("http://ns.adobe.com/DICOM/", "DICOM");
/* 213 */     registerNamespace("http://ns.useplus.org/ldf/xmp/1.0/", "plus");
/*     */ 
/*     */     
/* 216 */     registerNamespace("adobe:ns:meta/", "x");
/* 217 */     registerNamespace("http://ns.adobe.com/iX/1.0/", "iX");
/*     */     
/* 219 */     registerNamespace("http://ns.adobe.com/xap/1.0/", "xmp");
/* 220 */     registerNamespace("http://ns.adobe.com/xap/1.0/rights/", "xmpRights");
/* 221 */     registerNamespace("http://ns.adobe.com/xap/1.0/mm/", "xmpMM");
/* 222 */     registerNamespace("http://ns.adobe.com/xap/1.0/bj/", "xmpBJ");
/* 223 */     registerNamespace("http://ns.adobe.com/xmp/note/", "xmpNote");
/*     */     
/* 225 */     registerNamespace("http://ns.adobe.com/pdf/1.3/", "pdf");
/* 226 */     registerNamespace("http://ns.adobe.com/pdfx/1.3/", "pdfx");
/* 227 */     registerNamespace("http://www.npes.org/pdfx/ns/id/", "pdfxid");
/* 228 */     registerNamespace("http://www.aiim.org/pdfa/ns/schema#", "pdfaSchema");
/* 229 */     registerNamespace("http://www.aiim.org/pdfa/ns/property#", "pdfaProperty");
/* 230 */     registerNamespace("http://www.aiim.org/pdfa/ns/type#", "pdfaType");
/* 231 */     registerNamespace("http://www.aiim.org/pdfa/ns/field#", "pdfaField");
/* 232 */     registerNamespace("http://www.aiim.org/pdfa/ns/id/", "pdfaid");
/* 233 */     registerNamespace("http://www.aiim.org/pdfua/ns/id/", "pdfuaid");
/* 234 */     registerNamespace("http://www.aiim.org/pdfa/ns/extension/", "pdfaExtension");
/* 235 */     registerNamespace("http://ns.adobe.com/photoshop/1.0/", "photoshop");
/* 236 */     registerNamespace("http://ns.adobe.com/album/1.0/", "album");
/* 237 */     registerNamespace("http://ns.adobe.com/exif/1.0/", "exif");
/* 238 */     registerNamespace("http://cipa.jp/exif/1.0/", "exifEX");
/* 239 */     registerNamespace("http://ns.adobe.com/exif/1.0/aux/", "aux");
/* 240 */     registerNamespace("http://ns.adobe.com/tiff/1.0/", "tiff");
/* 241 */     registerNamespace("http://ns.adobe.com/png/1.0/", "png");
/* 242 */     registerNamespace("http://ns.adobe.com/jpeg/1.0/", "jpeg");
/* 243 */     registerNamespace("http://ns.adobe.com/jp2k/1.0/", "jp2k");
/* 244 */     registerNamespace("http://ns.adobe.com/camera-raw-settings/1.0/", "crs");
/* 245 */     registerNamespace("http://ns.adobe.com/StockPhoto/1.0/", "bmsp");
/* 246 */     registerNamespace("http://ns.adobe.com/creatorAtom/1.0/", "creatorAtom");
/* 247 */     registerNamespace("http://ns.adobe.com/asf/1.0/", "asf");
/* 248 */     registerNamespace("http://ns.adobe.com/xmp/wav/1.0/", "wav");
/* 249 */     registerNamespace("http://ns.adobe.com/bwf/bext/1.0/", "bext");
/* 250 */     registerNamespace("http://ns.adobe.com/riff/info/", "riffinfo");
/* 251 */     registerNamespace("http://ns.adobe.com/xmp/1.0/Script/", "xmpScript");
/* 252 */     registerNamespace("http://ns.adobe.com/TransformXMP/", "txmp");
/* 253 */     registerNamespace("http://ns.adobe.com/swf/1.0/", "swf");
/*     */ 
/*     */     
/* 256 */     registerNamespace("http://ns.adobe.com/xmp/1.0/DynamicMedia/", "xmpDM");
/* 257 */     registerNamespace("http://ns.adobe.com/xmp/transient/1.0/", "xmpx");
/*     */ 
/*     */     
/* 260 */     registerNamespace("http://ns.adobe.com/xap/1.0/t/", "xmpT");
/* 261 */     registerNamespace("http://ns.adobe.com/xap/1.0/t/pg/", "xmpTPg");
/* 262 */     registerNamespace("http://ns.adobe.com/xap/1.0/g/", "xmpG");
/* 263 */     registerNamespace("http://ns.adobe.com/xap/1.0/g/img/", "xmpGImg");
/* 264 */     registerNamespace("http://ns.adobe.com/xap/1.0/sType/Font#", "stFnt");
/* 265 */     registerNamespace("http://ns.adobe.com/xap/1.0/sType/Dimensions#", "stDim");
/* 266 */     registerNamespace("http://ns.adobe.com/xap/1.0/sType/ResourceEvent#", "stEvt");
/* 267 */     registerNamespace("http://ns.adobe.com/xap/1.0/sType/ResourceRef#", "stRef");
/* 268 */     registerNamespace("http://ns.adobe.com/xap/1.0/sType/Version#", "stVer");
/* 269 */     registerNamespace("http://ns.adobe.com/xap/1.0/sType/Job#", "stJob");
/* 270 */     registerNamespace("http://ns.adobe.com/xap/1.0/sType/ManifestItem#", "stMfs");
/* 271 */     registerNamespace("http://ns.adobe.com/xmp/Identifier/qual/1.0/", "xmpidq");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized XMPAliasInfo resolveAlias(String aliasNS, String aliasProp) {
/* 285 */     String aliasPrefix = getNamespacePrefix(aliasNS);
/* 286 */     if (aliasPrefix == null)
/*     */     {
/* 288 */       return null;
/*     */     }
/*     */     
/* 291 */     return (XMPAliasInfo)this.aliasMap.get(aliasPrefix + aliasProp);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized XMPAliasInfo findAlias(String qname) {
/* 300 */     return (XMPAliasInfo)this.aliasMap.get(qname);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized XMPAliasInfo[] findAliases(String aliasNS) {
/* 309 */     String prefix = getNamespacePrefix(aliasNS);
/* 310 */     List<XMPAliasInfo> result = new ArrayList<>();
/* 311 */     if (prefix != null)
/*     */     {
/* 313 */       for (Object key : this.aliasMap.keySet()) {
/* 314 */         String qname = (String)key;
/* 315 */         if (qname.startsWith(prefix)) {
/* 316 */           result.add(findAlias(qname));
/*     */         }
/*     */       } 
/*     */     }
/*     */     
/* 321 */     return result.<XMPAliasInfo>toArray(new XMPAliasInfo[result.size()]);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   synchronized void registerAlias(String aliasNS, String aliasProp, final String actualNS, final String actualProp, AliasOptions aliasForm) throws XMPException {
/* 364 */     ParameterAsserts.assertSchemaNS(aliasNS);
/* 365 */     ParameterAsserts.assertPropName(aliasProp);
/* 366 */     ParameterAsserts.assertSchemaNS(actualNS);
/* 367 */     ParameterAsserts.assertPropName(actualProp);
/*     */ 
/*     */     
/* 370 */     if (aliasForm != null) {  }
/*     */     else {  }
/* 372 */      final AliasOptions aliasOpts = new AliasOptions();
/*     */ 
/*     */     
/* 375 */     if (this.p.matcher(aliasProp).find() || this.p.matcher(actualProp).find())
/*     */     {
/* 377 */       throw new XMPException("Alias and actual property names must be simple", 102);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 382 */     String aliasPrefix = getNamespacePrefix(aliasNS);
/* 383 */     final String actualPrefix = getNamespacePrefix(actualNS);
/* 384 */     if (aliasPrefix == null)
/*     */     {
/* 386 */       throw new XMPException("Alias namespace is not registered", 101);
/*     */     }
/* 388 */     if (actualPrefix == null)
/*     */     {
/* 390 */       throw new XMPException("Actual namespace is not registered", 101);
/*     */     }
/*     */ 
/*     */     
/* 394 */     String key = aliasPrefix + aliasProp;
/*     */ 
/*     */     
/* 397 */     if (this.aliasMap.containsKey(key))
/*     */     {
/* 399 */       throw new XMPException("Alias is already existing", 4);
/*     */     }
/* 401 */     if (this.aliasMap.containsKey(actualPrefix + actualProp))
/*     */     {
/* 403 */       throw new XMPException("Actual property is already an alias, use the base property", 4);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 408 */     XMPAliasInfo aliasInfo = new XMPAliasInfo()
/*     */       {
/*     */ 
/*     */ 
/*     */         
/*     */         public String getNamespace()
/*     */         {
/* 415 */           return actualNS;
/*     */         }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*     */         public String getPrefix() {
/* 423 */           return actualPrefix;
/*     */         }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*     */         public String getPropName() {
/* 431 */           return actualProp;
/*     */         }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*     */         public AliasOptions getAliasForm() {
/* 439 */           return aliasOpts;
/*     */         }
/*     */ 
/*     */         
/*     */         public String toString() {
/* 444 */           return actualPrefix + actualProp + " NS(" + actualNS + "), FORM (" + 
/* 445 */             getAliasForm() + ")";
/*     */         }
/*     */       };
/*     */     
/* 449 */     this.aliasMap.put(key, aliasInfo);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized Map getAliases() {
/* 458 */     return Collections.unmodifiableMap(new TreeMap<>(this.aliasMap));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void registerStandardAliases() throws XMPException {
/* 470 */     AliasOptions aliasToArrayOrdered = (new AliasOptions()).setArrayOrdered(true);
/* 471 */     AliasOptions aliasToArrayAltText = (new AliasOptions()).setArrayAltText(true);
/*     */ 
/*     */ 
/*     */     
/* 475 */     registerAlias("http://ns.adobe.com/xap/1.0/", "Author", "http://purl.org/dc/elements/1.1/", "creator", aliasToArrayOrdered);
/* 476 */     registerAlias("http://ns.adobe.com/xap/1.0/", "Authors", "http://purl.org/dc/elements/1.1/", "creator", null);
/* 477 */     registerAlias("http://ns.adobe.com/xap/1.0/", "Description", "http://purl.org/dc/elements/1.1/", "description", null);
/* 478 */     registerAlias("http://ns.adobe.com/xap/1.0/", "Format", "http://purl.org/dc/elements/1.1/", "format", null);
/* 479 */     registerAlias("http://ns.adobe.com/xap/1.0/", "Keywords", "http://purl.org/dc/elements/1.1/", "subject", null);
/* 480 */     registerAlias("http://ns.adobe.com/xap/1.0/", "Locale", "http://purl.org/dc/elements/1.1/", "language", null);
/* 481 */     registerAlias("http://ns.adobe.com/xap/1.0/", "Title", "http://purl.org/dc/elements/1.1/", "title", null);
/* 482 */     registerAlias("http://ns.adobe.com/xap/1.0/rights/", "Copyright", "http://purl.org/dc/elements/1.1/", "rights", null);
/*     */ 
/*     */     
/* 485 */     registerAlias("http://ns.adobe.com/pdf/1.3/", "Author", "http://purl.org/dc/elements/1.1/", "creator", aliasToArrayOrdered);
/* 486 */     registerAlias("http://ns.adobe.com/pdf/1.3/", "BaseURL", "http://ns.adobe.com/xap/1.0/", "BaseURL", null);
/* 487 */     registerAlias("http://ns.adobe.com/pdf/1.3/", "CreationDate", "http://ns.adobe.com/xap/1.0/", "CreateDate", null);
/* 488 */     registerAlias("http://ns.adobe.com/pdf/1.3/", "Creator", "http://ns.adobe.com/xap/1.0/", "CreatorTool", null);
/* 489 */     registerAlias("http://ns.adobe.com/pdf/1.3/", "ModDate", "http://ns.adobe.com/xap/1.0/", "ModifyDate", null);
/* 490 */     registerAlias("http://ns.adobe.com/pdf/1.3/", "Subject", "http://purl.org/dc/elements/1.1/", "description", aliasToArrayAltText);
/* 491 */     registerAlias("http://ns.adobe.com/pdf/1.3/", "Title", "http://purl.org/dc/elements/1.1/", "title", aliasToArrayAltText);
/*     */ 
/*     */     
/* 494 */     registerAlias("http://ns.adobe.com/photoshop/1.0/", "Author", "http://purl.org/dc/elements/1.1/", "creator", aliasToArrayOrdered);
/* 495 */     registerAlias("http://ns.adobe.com/photoshop/1.0/", "Caption", "http://purl.org/dc/elements/1.1/", "description", aliasToArrayAltText);
/* 496 */     registerAlias("http://ns.adobe.com/photoshop/1.0/", "Copyright", "http://purl.org/dc/elements/1.1/", "rights", aliasToArrayAltText);
/* 497 */     registerAlias("http://ns.adobe.com/photoshop/1.0/", "Keywords", "http://purl.org/dc/elements/1.1/", "subject", null);
/* 498 */     registerAlias("http://ns.adobe.com/photoshop/1.0/", "Marked", "http://ns.adobe.com/xap/1.0/rights/", "Marked", null);
/* 499 */     registerAlias("http://ns.adobe.com/photoshop/1.0/", "Title", "http://purl.org/dc/elements/1.1/", "title", aliasToArrayAltText);
/* 500 */     registerAlias("http://ns.adobe.com/photoshop/1.0/", "WebStatement", "http://ns.adobe.com/xap/1.0/rights/", "WebStatement", null);
/*     */ 
/*     */     
/* 503 */     registerAlias("http://ns.adobe.com/tiff/1.0/", "Artist", "http://purl.org/dc/elements/1.1/", "creator", aliasToArrayOrdered);
/* 504 */     registerAlias("http://ns.adobe.com/tiff/1.0/", "Copyright", "http://purl.org/dc/elements/1.1/", "rights", null);
/* 505 */     registerAlias("http://ns.adobe.com/tiff/1.0/", "DateTime", "http://ns.adobe.com/xap/1.0/", "ModifyDate", null);
/* 506 */     registerAlias("http://ns.adobe.com/tiff/1.0/", "ImageDescription", "http://purl.org/dc/elements/1.1/", "description", null);
/* 507 */     registerAlias("http://ns.adobe.com/tiff/1.0/", "Software", "http://ns.adobe.com/xap/1.0/", "CreatorTool", null);
/*     */ 
/*     */     
/* 510 */     registerAlias("http://ns.adobe.com/png/1.0/", "Author", "http://purl.org/dc/elements/1.1/", "creator", aliasToArrayOrdered);
/* 511 */     registerAlias("http://ns.adobe.com/png/1.0/", "Copyright", "http://purl.org/dc/elements/1.1/", "rights", aliasToArrayAltText);
/* 512 */     registerAlias("http://ns.adobe.com/png/1.0/", "CreationTime", "http://ns.adobe.com/xap/1.0/", "CreateDate", null);
/* 513 */     registerAlias("http://ns.adobe.com/png/1.0/", "Description", "http://purl.org/dc/elements/1.1/", "description", aliasToArrayAltText);
/* 514 */     registerAlias("http://ns.adobe.com/png/1.0/", "ModificationTime", "http://ns.adobe.com/xap/1.0/", "ModifyDate", null);
/* 515 */     registerAlias("http://ns.adobe.com/png/1.0/", "Software", "http://ns.adobe.com/xap/1.0/", "CreatorTool", null);
/* 516 */     registerAlias("http://ns.adobe.com/png/1.0/", "Title", "http://purl.org/dc/elements/1.1/", "title", aliasToArrayAltText);
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/kernel/xmp/impl/XMPSchemaRegistryImpl.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */