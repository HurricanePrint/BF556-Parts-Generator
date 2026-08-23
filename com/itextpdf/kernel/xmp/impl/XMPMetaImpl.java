/*      */ package com.itextpdf.kernel.xmp.impl;
/*      */ 
/*      */ import com.itextpdf.kernel.xmp.XMPConst;
/*      */ import com.itextpdf.kernel.xmp.XMPDateTime;
/*      */ import com.itextpdf.kernel.xmp.XMPException;
/*      */ import com.itextpdf.kernel.xmp.XMPIterator;
/*      */ import com.itextpdf.kernel.xmp.XMPMeta;
/*      */ import com.itextpdf.kernel.xmp.XMPPathFactory;
/*      */ import com.itextpdf.kernel.xmp.XMPUtils;
/*      */ import com.itextpdf.kernel.xmp.impl.xpath.XMPPath;
/*      */ import com.itextpdf.kernel.xmp.impl.xpath.XMPPathParser;
/*      */ import com.itextpdf.kernel.xmp.options.IteratorOptions;
/*      */ import com.itextpdf.kernel.xmp.options.ParseOptions;
/*      */ import com.itextpdf.kernel.xmp.options.PropertyOptions;
/*      */ import com.itextpdf.kernel.xmp.properties.XMPProperty;
/*      */ import java.util.Calendar;
/*      */ import java.util.Iterator;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class XMPMetaImpl
/*      */   implements XMPConst, XMPMeta
/*      */ {
/*      */   private static final int VALUE_STRING = 0;
/*      */   private static final int VALUE_BOOLEAN = 1;
/*      */   private static final int VALUE_INTEGER = 2;
/*      */   private static final int VALUE_LONG = 3;
/*      */   private static final int VALUE_DOUBLE = 4;
/*      */   private static final int VALUE_DATE = 5;
/*      */   private static final int VALUE_CALENDAR = 6;
/*      */   private static final int VALUE_BASE64 = 7;
/*      */   private XMPNode tree;
/*   79 */   private String packetHeader = null;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public XMPMetaImpl() {
/*   88 */     this.tree = new XMPNode(null, null, null);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public XMPMetaImpl(XMPNode tree) {
/*  101 */     this.tree = tree;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void appendArrayItem(String schemaNS, String arrayName, PropertyOptions arrayOptions, String itemValue, PropertyOptions itemOptions) throws XMPException {
/*  107 */     ParameterAsserts.assertSchemaNS(schemaNS);
/*  108 */     ParameterAsserts.assertArrayName(arrayName);
/*      */     
/*  110 */     if (arrayOptions == null)
/*      */     {
/*  112 */       arrayOptions = new PropertyOptions();
/*      */     }
/*  114 */     if (!arrayOptions.isOnlyArrayOptions())
/*      */     {
/*  116 */       throw new XMPException("Only array form flags allowed for arrayOptions", 103);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*  121 */     arrayOptions = XMPNodeUtils.verifySetOptions(arrayOptions, null);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  127 */     XMPPath arrayPath = XMPPathParser.expandXPath(schemaNS, arrayName);
/*      */ 
/*      */ 
/*      */     
/*  131 */     XMPNode arrayNode = XMPNodeUtils.findNode(this.tree, arrayPath, false, null);
/*      */     
/*  133 */     if (arrayNode != null) {
/*      */ 
/*      */ 
/*      */       
/*  137 */       if (!arrayNode.getOptions().isArray())
/*      */       {
/*  139 */         throw new XMPException("The named property is not an array", 102);
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       }
/*      */ 
/*      */ 
/*      */     
/*      */     }
/*  149 */     else if (arrayOptions.isArray()) {
/*      */       
/*  151 */       arrayNode = XMPNodeUtils.findNode(this.tree, arrayPath, true, arrayOptions);
/*  152 */       if (arrayNode == null)
/*      */       {
/*  154 */         throw new XMPException("Failure creating array node", 102);
/*      */       
/*      */       }
/*      */     }
/*      */     else {
/*      */       
/*  160 */       throw new XMPException("Explicit arrayOptions required to create new array", 103);
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  165 */     doSetArrayItem(arrayNode, -1, itemValue, itemOptions, true);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void appendArrayItem(String schemaNS, String arrayName, String itemValue) throws XMPException {
/*  171 */     appendArrayItem(schemaNS, arrayName, null, itemValue, null);
/*      */   }
/*      */ 
/*      */   
/*      */   public int countArrayItems(String schemaNS, String arrayName) throws XMPException {
/*  176 */     ParameterAsserts.assertSchemaNS(schemaNS);
/*  177 */     ParameterAsserts.assertArrayName(arrayName);
/*      */     
/*  179 */     XMPPath arrayPath = XMPPathParser.expandXPath(schemaNS, arrayName);
/*  180 */     XMPNode arrayNode = XMPNodeUtils.findNode(this.tree, arrayPath, false, null);
/*      */     
/*  182 */     if (arrayNode == null)
/*      */     {
/*  184 */       return 0;
/*      */     }
/*      */     
/*  187 */     if (arrayNode.getOptions().isArray())
/*      */     {
/*  189 */       return arrayNode.getChildrenLength();
/*      */     }
/*      */ 
/*      */     
/*  193 */     throw new XMPException("The named property is not an array", 102);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void deleteArrayItem(String schemaNS, String arrayName, int itemIndex) {
/*      */     try {
/*  202 */       ParameterAsserts.assertSchemaNS(schemaNS);
/*  203 */       ParameterAsserts.assertArrayName(arrayName);
/*      */       
/*  205 */       String itemPath = XMPPathFactory.composeArrayItemPath(arrayName, itemIndex);
/*  206 */       deleteProperty(schemaNS, itemPath);
/*      */     }
/*  208 */     catch (XMPException xMPException) {}
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void deleteProperty(String schemaNS, String propName) {
/*      */     try {
/*  219 */       ParameterAsserts.assertSchemaNS(schemaNS);
/*  220 */       ParameterAsserts.assertPropName(propName);
/*      */       
/*  222 */       XMPPath expPath = XMPPathParser.expandXPath(schemaNS, propName);
/*      */       
/*  224 */       XMPNode propNode = XMPNodeUtils.findNode(this.tree, expPath, false, null);
/*  225 */       if (propNode != null)
/*      */       {
/*  227 */         XMPNodeUtils.deleteNode(propNode);
/*      */       }
/*      */     }
/*  230 */     catch (XMPException xMPException) {}
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void deleteQualifier(String schemaNS, String propName, String qualNS, String qualName) {
/*      */     try {
/*  242 */       ParameterAsserts.assertSchemaNS(schemaNS);
/*  243 */       ParameterAsserts.assertPropName(propName);
/*      */       
/*  245 */       String qualPath = propName + XMPPathFactory.composeQualifierPath(qualNS, qualName);
/*  246 */       deleteProperty(schemaNS, qualPath);
/*      */     }
/*  248 */     catch (XMPException xMPException) {}
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void deleteStructField(String schemaNS, String structName, String fieldNS, String fieldName) {
/*      */     try {
/*  261 */       ParameterAsserts.assertSchemaNS(schemaNS);
/*  262 */       ParameterAsserts.assertStructName(structName);
/*      */ 
/*      */       
/*  265 */       String fieldPath = structName + XMPPathFactory.composeStructFieldPath(fieldNS, fieldName);
/*  266 */       deleteProperty(schemaNS, fieldPath);
/*      */     }
/*  268 */     catch (XMPException xMPException) {}
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean doesPropertyExist(String schemaNS, String propName) {
/*      */     try {
/*  279 */       ParameterAsserts.assertSchemaNS(schemaNS);
/*  280 */       ParameterAsserts.assertPropName(propName);
/*      */       
/*  282 */       XMPPath expPath = XMPPathParser.expandXPath(schemaNS, propName);
/*  283 */       XMPNode propNode = XMPNodeUtils.findNode(this.tree, expPath, false, null);
/*  284 */       return (propNode != null);
/*      */     }
/*  286 */     catch (XMPException e) {
/*      */       
/*  288 */       return false;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean doesArrayItemExist(String schemaNS, String arrayName, int itemIndex) {
/*      */     try {
/*  297 */       ParameterAsserts.assertSchemaNS(schemaNS);
/*  298 */       ParameterAsserts.assertArrayName(arrayName);
/*      */       
/*  300 */       String path = XMPPathFactory.composeArrayItemPath(arrayName, itemIndex);
/*  301 */       return doesPropertyExist(schemaNS, path);
/*      */     }
/*  303 */     catch (XMPException e) {
/*      */       
/*  305 */       return false;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean doesStructFieldExist(String schemaNS, String structName, String fieldNS, String fieldName) {
/*      */     try {
/*  316 */       ParameterAsserts.assertSchemaNS(schemaNS);
/*  317 */       ParameterAsserts.assertStructName(structName);
/*      */       
/*  319 */       String path = XMPPathFactory.composeStructFieldPath(fieldNS, fieldName);
/*  320 */       return doesPropertyExist(schemaNS, structName + path);
/*      */     }
/*  322 */     catch (XMPException e) {
/*      */       
/*  324 */       return false;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean doesQualifierExist(String schemaNS, String propName, String qualNS, String qualName) {
/*      */     try {
/*  335 */       ParameterAsserts.assertSchemaNS(schemaNS);
/*  336 */       ParameterAsserts.assertPropName(propName);
/*      */       
/*  338 */       String path = XMPPathFactory.composeQualifierPath(qualNS, qualName);
/*  339 */       return doesPropertyExist(schemaNS, propName + path);
/*      */     }
/*  341 */     catch (XMPException e) {
/*      */       
/*  343 */       return false;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public XMPProperty getArrayItem(String schemaNS, String arrayName, int itemIndex) throws XMPException {
/*  351 */     ParameterAsserts.assertSchemaNS(schemaNS);
/*  352 */     ParameterAsserts.assertArrayName(arrayName);
/*      */     
/*  354 */     String itemPath = XMPPathFactory.composeArrayItemPath(arrayName, itemIndex);
/*  355 */     return getProperty(schemaNS, itemPath);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public XMPProperty getLocalizedText(String schemaNS, String altTextName, String genericLang, String specificLang) throws XMPException {
/*  362 */     ParameterAsserts.assertSchemaNS(schemaNS);
/*  363 */     ParameterAsserts.assertArrayName(altTextName);
/*  364 */     ParameterAsserts.assertSpecificLang(specificLang);
/*      */     
/*  366 */     genericLang = (genericLang != null) ? Utils.normalizeLangValue(genericLang) : null;
/*  367 */     specificLang = Utils.normalizeLangValue(specificLang);
/*      */     
/*  369 */     XMPPath arrayPath = XMPPathParser.expandXPath(schemaNS, altTextName);
/*  370 */     XMPNode arrayNode = XMPNodeUtils.findNode(this.tree, arrayPath, false, null);
/*  371 */     if (arrayNode == null)
/*      */     {
/*  373 */       return null;
/*      */     }
/*      */     
/*  376 */     Object[] result = XMPNodeUtils.chooseLocalizedText(arrayNode, genericLang, specificLang);
/*  377 */     int match = ((Integer)result[0]).intValue();
/*  378 */     final XMPNode itemNode = (XMPNode)result[1];
/*      */     
/*  380 */     if (match != 0)
/*      */     {
/*  382 */       return new XMPProperty()
/*      */         {
/*      */           public String getValue()
/*      */           {
/*  386 */             return itemNode.getValue();
/*      */           }
/*      */ 
/*      */ 
/*      */           
/*      */           public PropertyOptions getOptions() {
/*  392 */             return itemNode.getOptions();
/*      */           }
/*      */ 
/*      */ 
/*      */           
/*      */           public String getLanguage() {
/*  398 */             return itemNode.getQualifier(1).getValue();
/*      */           }
/*      */ 
/*      */ 
/*      */           
/*      */           public String toString() {
/*  404 */             return itemNode.getValue();
/*      */           }
/*      */         };
/*      */     }
/*      */ 
/*      */     
/*  410 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setLocalizedText(String schemaNS, String altTextName, String genericLang, String specificLang, String itemValue, PropertyOptions options) throws XMPException {
/*      */     Iterator<XMPNode> iterator1;
/*  418 */     ParameterAsserts.assertSchemaNS(schemaNS);
/*  419 */     ParameterAsserts.assertArrayName(altTextName);
/*  420 */     ParameterAsserts.assertSpecificLang(specificLang);
/*      */     
/*  422 */     genericLang = (genericLang != null) ? Utils.normalizeLangValue(genericLang) : null;
/*  423 */     specificLang = Utils.normalizeLangValue(specificLang);
/*      */     
/*  425 */     XMPPath arrayPath = XMPPathParser.expandXPath(schemaNS, altTextName);
/*      */ 
/*      */     
/*  428 */     XMPNode arrayNode = XMPNodeUtils.findNode(this.tree, arrayPath, true, new PropertyOptions(7680));
/*      */ 
/*      */ 
/*      */     
/*  432 */     if (arrayNode == null)
/*      */     {
/*  434 */       throw new XMPException("Failed to find or create array node", 102);
/*      */     }
/*  436 */     if (!arrayNode.getOptions().isArrayAltText())
/*      */     {
/*  438 */       if (!arrayNode.hasChildren() && arrayNode.getOptions().isArrayAlternate()) {
/*      */         
/*  440 */         arrayNode.getOptions().setArrayAltText(true);
/*      */       }
/*      */       else {
/*      */         
/*  444 */         throw new XMPException("Specified property is no alt-text array", 102);
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*  450 */     boolean haveXDefault = false;
/*  451 */     XMPNode xdItem = null;
/*      */     
/*  453 */     for (Iterator<XMPNode> it = arrayNode.iterateChildren(); it.hasNext(); ) {
/*      */       
/*  455 */       XMPNode currItem = it.next();
/*  456 */       if (!currItem.hasQualifier() || 
/*  457 */         !"xml:lang".equals(currItem.getQualifier(1).getName()))
/*      */       {
/*  459 */         throw new XMPException("Language qualifier must be first", 102);
/*      */       }
/*  461 */       if ("x-default".equals(currItem.getQualifier(1).getValue())) {
/*      */         
/*  463 */         xdItem = currItem;
/*  464 */         haveXDefault = true;
/*      */         
/*      */         break;
/*      */       } 
/*      */     } 
/*      */     
/*  470 */     if (xdItem != null && arrayNode.getChildrenLength() > 1) {
/*      */       
/*  472 */       arrayNode.removeChild(xdItem);
/*  473 */       arrayNode.addChild(1, xdItem);
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  479 */     Object[] result = XMPNodeUtils.chooseLocalizedText(arrayNode, genericLang, specificLang);
/*  480 */     int match = ((Integer)result[0]).intValue();
/*  481 */     XMPNode itemNode = (XMPNode)result[1];
/*      */     
/*  483 */     boolean specificXDefault = "x-default".equals(specificLang);
/*      */     
/*  485 */     switch (match) {
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       case 0:
/*  491 */         XMPNodeUtils.appendLangItem(arrayNode, "x-default", itemValue);
/*  492 */         haveXDefault = true;
/*  493 */         if (!specificXDefault)
/*      */         {
/*  495 */           XMPNodeUtils.appendLangItem(arrayNode, specificLang, itemValue);
/*      */         }
/*      */         break;
/*      */ 
/*      */       
/*      */       case 1:
/*  501 */         if (!specificXDefault) {
/*      */ 
/*      */ 
/*      */           
/*  505 */           if (haveXDefault && xdItem != itemNode && xdItem != null && xdItem
/*  506 */             .getValue().equals(itemNode.getValue()))
/*      */           {
/*  508 */             xdItem.setValue(itemValue);
/*      */           }
/*      */           
/*  511 */           itemNode.setValue(itemValue);
/*      */           
/*      */           break;
/*      */         } 
/*      */         
/*  516 */         assert haveXDefault && xdItem == itemNode;
/*  517 */         for (iterator1 = arrayNode.iterateChildren(); iterator1.hasNext(); ) {
/*      */           
/*  519 */           XMPNode currItem = iterator1.next();
/*  520 */           if (currItem == xdItem || 
/*  521 */             !currItem.getValue().equals((xdItem != null) ? xdItem
/*  522 */               .getValue() : null)) {
/*      */             continue;
/*      */           }
/*      */           
/*  526 */           currItem.setValue(itemValue);
/*      */         } 
/*      */         
/*  529 */         if (xdItem != null)
/*      */         {
/*  531 */           xdItem.setValue(itemValue);
/*      */         }
/*      */         break;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       case 2:
/*  540 */         if (haveXDefault && xdItem != itemNode && xdItem != null && xdItem
/*  541 */           .getValue().equals(itemNode.getValue()))
/*      */         {
/*  543 */           xdItem.setValue(itemValue);
/*      */         }
/*  545 */         itemNode.setValue(itemValue);
/*      */         break;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       case 3:
/*  553 */         XMPNodeUtils.appendLangItem(arrayNode, specificLang, itemValue);
/*  554 */         if (specificXDefault)
/*      */         {
/*  556 */           haveXDefault = true;
/*      */         }
/*      */         break;
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       case 4:
/*  564 */         if (xdItem != null && arrayNode.getChildrenLength() == 1)
/*      */         {
/*  566 */           xdItem.setValue(itemValue);
/*      */         }
/*  568 */         XMPNodeUtils.appendLangItem(arrayNode, specificLang, itemValue);
/*      */         break;
/*      */ 
/*      */ 
/*      */       
/*      */       case 5:
/*  574 */         XMPNodeUtils.appendLangItem(arrayNode, specificLang, itemValue);
/*  575 */         if (specificXDefault)
/*      */         {
/*  577 */           haveXDefault = true;
/*      */         }
/*      */         break;
/*      */ 
/*      */       
/*      */       default:
/*  583 */         throw new XMPException("Unexpected result from ChooseLocalizedText", 9);
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  589 */     if (!haveXDefault && arrayNode.getChildrenLength() == 1)
/*      */     {
/*  591 */       XMPNodeUtils.appendLangItem(arrayNode, "x-default", itemValue);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setLocalizedText(String schemaNS, String altTextName, String genericLang, String specificLang, String itemValue) throws XMPException {
/*  599 */     setLocalizedText(schemaNS, altTextName, genericLang, specificLang, itemValue, null);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public XMPProperty getProperty(String schemaNS, String propName) throws XMPException {
/*  605 */     return getProperty(schemaNS, propName, 0);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected XMPProperty getProperty(String schemaNS, String propName, int valueType) throws XMPException {
/*  630 */     ParameterAsserts.assertSchemaNS(schemaNS);
/*  631 */     ParameterAsserts.assertPropName(propName);
/*      */     
/*  633 */     XMPPath expPath = XMPPathParser.expandXPath(schemaNS, propName);
/*  634 */     final XMPNode propNode = XMPNodeUtils.findNode(this.tree, expPath, false, null);
/*      */     
/*  636 */     if (propNode != null) {
/*      */       
/*  638 */       if (valueType != 0 && propNode.getOptions().isCompositeProperty())
/*      */       {
/*  640 */         throw new XMPException("Property must be simple when a value type is requested", 102);
/*      */       }
/*      */ 
/*      */       
/*  644 */       final Object value = evaluateNodeValue(valueType, propNode);
/*      */       
/*  646 */       return new XMPProperty()
/*      */         {
/*      */           public String getValue()
/*      */           {
/*  650 */             return (value != null) ? value.toString() : null;
/*      */           }
/*      */ 
/*      */ 
/*      */           
/*      */           public PropertyOptions getOptions() {
/*  656 */             return propNode.getOptions();
/*      */           }
/*      */ 
/*      */ 
/*      */           
/*      */           public String getLanguage() {
/*  662 */             return null;
/*      */           }
/*      */ 
/*      */ 
/*      */           
/*      */           public String toString() {
/*  668 */             return value.toString();
/*      */           }
/*      */         };
/*      */     } 
/*      */ 
/*      */     
/*  674 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected Object getPropertyObject(String schemaNS, String propName, int valueType) throws XMPException {
/*  697 */     ParameterAsserts.assertSchemaNS(schemaNS);
/*  698 */     ParameterAsserts.assertPropName(propName);
/*      */     
/*  700 */     XMPPath expPath = XMPPathParser.expandXPath(schemaNS, propName);
/*  701 */     XMPNode propNode = XMPNodeUtils.findNode(this.tree, expPath, false, null);
/*      */     
/*  703 */     if (propNode != null) {
/*      */       
/*  705 */       if (valueType != 0 && propNode.getOptions().isCompositeProperty())
/*      */       {
/*  707 */         throw new XMPException("Property must be simple when a value type is requested", 102);
/*      */       }
/*      */ 
/*      */       
/*  711 */       return evaluateNodeValue(valueType, propNode);
/*      */     } 
/*      */ 
/*      */     
/*  715 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Boolean getPropertyBoolean(String schemaNS, String propName) throws XMPException {
/*  722 */     return (Boolean)getPropertyObject(schemaNS, propName, 1);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setPropertyBoolean(String schemaNS, String propName, boolean propValue, PropertyOptions options) throws XMPException {
/*  729 */     setProperty(schemaNS, propName, propValue ? "True" : "False", options);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setPropertyBoolean(String schemaNS, String propName, boolean propValue) throws XMPException {
/*  736 */     setProperty(schemaNS, propName, propValue ? "True" : "False", null);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public Integer getPropertyInteger(String schemaNS, String propName) throws XMPException {
/*  742 */     return (Integer)getPropertyObject(schemaNS, propName, 2);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setPropertyInteger(String schemaNS, String propName, int propValue, PropertyOptions options) throws XMPException {
/*  749 */     setProperty(schemaNS, propName, new Integer(propValue), options);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setPropertyInteger(String schemaNS, String propName, int propValue) throws XMPException {
/*  756 */     setProperty(schemaNS, propName, new Integer(propValue), null);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public Long getPropertyLong(String schemaNS, String propName) throws XMPException {
/*  762 */     return (Long)getPropertyObject(schemaNS, propName, 3);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setPropertyLong(String schemaNS, String propName, long propValue, PropertyOptions options) throws XMPException {
/*  769 */     setProperty(schemaNS, propName, new Long(propValue), options);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setPropertyLong(String schemaNS, String propName, long propValue) throws XMPException {
/*  776 */     setProperty(schemaNS, propName, new Long(propValue), null);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public Double getPropertyDouble(String schemaNS, String propName) throws XMPException {
/*  782 */     return (Double)getPropertyObject(schemaNS, propName, 4);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setPropertyDouble(String schemaNS, String propName, double propValue, PropertyOptions options) throws XMPException {
/*  789 */     setProperty(schemaNS, propName, new Double(propValue), options);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setPropertyDouble(String schemaNS, String propName, double propValue) throws XMPException {
/*  796 */     setProperty(schemaNS, propName, new Double(propValue), null);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public XMPDateTime getPropertyDate(String schemaNS, String propName) throws XMPException {
/*  802 */     return (XMPDateTime)getPropertyObject(schemaNS, propName, 5);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setPropertyDate(String schemaNS, String propName, XMPDateTime propValue, PropertyOptions options) throws XMPException {
/*  809 */     setProperty(schemaNS, propName, propValue, options);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setPropertyDate(String schemaNS, String propName, XMPDateTime propValue) throws XMPException {
/*  816 */     setProperty(schemaNS, propName, propValue, null);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public Calendar getPropertyCalendar(String schemaNS, String propName) throws XMPException {
/*  822 */     return (Calendar)getPropertyObject(schemaNS, propName, 6);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setPropertyCalendar(String schemaNS, String propName, Calendar propValue, PropertyOptions options) throws XMPException {
/*  829 */     setProperty(schemaNS, propName, propValue, options);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setPropertyCalendar(String schemaNS, String propName, Calendar propValue) throws XMPException {
/*  836 */     setProperty(schemaNS, propName, propValue, null);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public byte[] getPropertyBase64(String schemaNS, String propName) throws XMPException {
/*  842 */     return (byte[])getPropertyObject(schemaNS, propName, 7);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public String getPropertyString(String schemaNS, String propName) throws XMPException {
/*  848 */     return (String)getPropertyObject(schemaNS, propName, 0);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setPropertyBase64(String schemaNS, String propName, byte[] propValue, PropertyOptions options) throws XMPException {
/*  855 */     setProperty(schemaNS, propName, propValue, options);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setPropertyBase64(String schemaNS, String propName, byte[] propValue) throws XMPException {
/*  862 */     setProperty(schemaNS, propName, propValue, null);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public XMPProperty getQualifier(String schemaNS, String propName, String qualNS, String qualName) throws XMPException {
/*  870 */     ParameterAsserts.assertSchemaNS(schemaNS);
/*  871 */     ParameterAsserts.assertPropName(propName);
/*      */     
/*  873 */     String qualPath = propName + XMPPathFactory.composeQualifierPath(qualNS, qualName);
/*  874 */     return getProperty(schemaNS, qualPath);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public XMPProperty getStructField(String schemaNS, String structName, String fieldNS, String fieldName) throws XMPException {
/*  882 */     ParameterAsserts.assertSchemaNS(schemaNS);
/*  883 */     ParameterAsserts.assertStructName(structName);
/*      */     
/*  885 */     String fieldPath = structName + XMPPathFactory.composeStructFieldPath(fieldNS, fieldName);
/*  886 */     return getProperty(schemaNS, fieldPath);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public XMPIterator iterator() throws XMPException {
/*  892 */     return iterator(null, null, null);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public XMPIterator iterator(IteratorOptions options) throws XMPException {
/*  898 */     return iterator(null, null, options);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public XMPIterator iterator(String schemaNS, String propName, IteratorOptions options) throws XMPException {
/*  905 */     return new XMPIteratorImpl(this, schemaNS, propName, options);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setArrayItem(String schemaNS, String arrayName, int itemIndex, String itemValue, PropertyOptions options) throws XMPException {
/*  912 */     ParameterAsserts.assertSchemaNS(schemaNS);
/*  913 */     ParameterAsserts.assertArrayName(arrayName);
/*      */ 
/*      */     
/*  916 */     XMPPath arrayPath = XMPPathParser.expandXPath(schemaNS, arrayName);
/*  917 */     XMPNode arrayNode = XMPNodeUtils.findNode(this.tree, arrayPath, false, null);
/*      */     
/*  919 */     if (arrayNode != null) {
/*      */       
/*  921 */       doSetArrayItem(arrayNode, itemIndex, itemValue, options, false);
/*      */     }
/*      */     else {
/*      */       
/*  925 */       throw new XMPException("Specified array does not exist", 102);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setArrayItem(String schemaNS, String arrayName, int itemIndex, String itemValue) throws XMPException {
/*  933 */     setArrayItem(schemaNS, arrayName, itemIndex, itemValue, null);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void insertArrayItem(String schemaNS, String arrayName, int itemIndex, String itemValue, PropertyOptions options) throws XMPException {
/*  940 */     ParameterAsserts.assertSchemaNS(schemaNS);
/*  941 */     ParameterAsserts.assertArrayName(arrayName);
/*      */ 
/*      */     
/*  944 */     XMPPath arrayPath = XMPPathParser.expandXPath(schemaNS, arrayName);
/*  945 */     XMPNode arrayNode = XMPNodeUtils.findNode(this.tree, arrayPath, false, null);
/*      */     
/*  947 */     if (arrayNode != null) {
/*      */       
/*  949 */       doSetArrayItem(arrayNode, itemIndex, itemValue, options, true);
/*      */     }
/*      */     else {
/*      */       
/*  953 */       throw new XMPException("Specified array does not exist", 102);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void insertArrayItem(String schemaNS, String arrayName, int itemIndex, String itemValue) throws XMPException {
/*  961 */     insertArrayItem(schemaNS, arrayName, itemIndex, itemValue, null);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setProperty(String schemaNS, String propName, Object propValue, PropertyOptions options) throws XMPException {
/*  968 */     ParameterAsserts.assertSchemaNS(schemaNS);
/*  969 */     ParameterAsserts.assertPropName(propName);
/*      */     
/*  971 */     options = XMPNodeUtils.verifySetOptions(options, propValue);
/*      */     
/*  973 */     XMPPath expPath = XMPPathParser.expandXPath(schemaNS, propName);
/*      */     
/*  975 */     XMPNode propNode = XMPNodeUtils.findNode(this.tree, expPath, true, options);
/*  976 */     if (propNode != null) {
/*      */       
/*  978 */       setNode(propNode, propValue, options, false);
/*      */     }
/*      */     else {
/*      */       
/*  982 */       throw new XMPException("Specified property does not exist", 102);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void setProperty(String schemaNS, String propName, Object propValue) throws XMPException {
/*  989 */     setProperty(schemaNS, propName, propValue, null);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setQualifier(String schemaNS, String propName, String qualNS, String qualName, String qualValue, PropertyOptions options) throws XMPException {
/*  996 */     ParameterAsserts.assertSchemaNS(schemaNS);
/*  997 */     ParameterAsserts.assertPropName(propName);
/*      */     
/*  999 */     if (!doesPropertyExist(schemaNS, propName))
/*      */     {
/* 1001 */       throw new XMPException("Specified property does not exist!", 102);
/*      */     }
/*      */     
/* 1004 */     String qualPath = propName + XMPPathFactory.composeQualifierPath(qualNS, qualName);
/* 1005 */     setProperty(schemaNS, qualPath, qualValue, options);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setQualifier(String schemaNS, String propName, String qualNS, String qualName, String qualValue) throws XMPException {
/* 1012 */     setQualifier(schemaNS, propName, qualNS, qualName, qualValue, null);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setStructField(String schemaNS, String structName, String fieldNS, String fieldName, String fieldValue, PropertyOptions options) throws XMPException {
/* 1020 */     ParameterAsserts.assertSchemaNS(schemaNS);
/* 1021 */     ParameterAsserts.assertStructName(structName);
/*      */     
/* 1023 */     String fieldPath = structName + XMPPathFactory.composeStructFieldPath(fieldNS, fieldName);
/* 1024 */     setProperty(schemaNS, fieldPath, fieldValue, options);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setStructField(String schemaNS, String structName, String fieldNS, String fieldName, String fieldValue) throws XMPException {
/* 1031 */     setStructField(schemaNS, structName, fieldNS, fieldName, fieldValue, null);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public String getObjectName() {
/* 1037 */     return (this.tree.getName() != null) ? this.tree.getName() : "";
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void setObjectName(String name) {
/* 1043 */     this.tree.setName(name);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public String getPacketHeader() {
/* 1049 */     return this.packetHeader;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setPacketHeader(String packetHeader) {
/* 1059 */     this.packetHeader = packetHeader;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Object clone() {
/* 1070 */     XMPNode clonedTree = (XMPNode)this.tree.clone();
/* 1071 */     return new XMPMetaImpl(clonedTree);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String dumpObject() {
/* 1078 */     return getRoot().dumpNode(true);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void sort() {
/* 1084 */     this.tree.sort();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void normalize(ParseOptions options) throws XMPException {
/* 1090 */     if (options == null)
/*      */     {
/* 1092 */       options = new ParseOptions();
/*      */     }
/* 1094 */     XMPNormalizer.process(this, options);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public XMPNode getRoot() {
/* 1103 */     return this.tree;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void doSetArrayItem(XMPNode arrayNode, int itemIndex, String itemValue, PropertyOptions itemOptions, boolean insert) throws XMPException {
/* 1129 */     XMPNode itemNode = new XMPNode("[]", null);
/* 1130 */     itemOptions = XMPNodeUtils.verifySetOptions(itemOptions, itemValue);
/*      */ 
/*      */ 
/*      */     
/* 1134 */     int maxIndex = insert ? (arrayNode.getChildrenLength() + 1) : arrayNode.getChildrenLength();
/* 1135 */     if (itemIndex == -1)
/*      */     {
/* 1137 */       itemIndex = maxIndex;
/*      */     }
/*      */     
/* 1140 */     if (1 <= itemIndex && itemIndex <= maxIndex) {
/*      */       
/* 1142 */       if (!insert)
/*      */       {
/* 1144 */         arrayNode.removeChild(itemIndex);
/*      */       }
/* 1146 */       arrayNode.addChild(itemIndex, itemNode);
/* 1147 */       setNode(itemNode, itemValue, itemOptions, false);
/*      */     }
/*      */     else {
/*      */       
/* 1151 */       throw new XMPException("Array index out of bounds", 104);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   void setNode(XMPNode node, Object value, PropertyOptions newOptions, boolean deleteExisting) throws XMPException {
/* 1172 */     if (deleteExisting)
/*      */     {
/* 1174 */       node.clear();
/*      */     }
/*      */ 
/*      */     
/* 1178 */     node.getOptions().mergeWith(newOptions);
/*      */     
/* 1180 */     if (!node.getOptions().isCompositeProperty()) {
/*      */ 
/*      */       
/* 1183 */       XMPNodeUtils.setNodeValue(node, value);
/*      */     }
/*      */     else {
/*      */       
/* 1187 */       if (value != null && value.toString().length() > 0)
/*      */       {
/* 1189 */         throw new XMPException("Composite nodes can't have values", 102);
/*      */       }
/*      */       
/* 1192 */       node.removeChildren();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private Object evaluateNodeValue(int valueType, XMPNode propNode) throws XMPException {
/*      */     XMPDateTime dt;
/* 1213 */     String rawValue = propNode.getValue();
/* 1214 */     switch (valueType)
/*      */     
/*      */     { case 1:
/* 1217 */         value = Boolean.valueOf(XMPUtils.convertToBoolean(rawValue));
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1247 */         return value;case 2: value = Integer.valueOf(XMPUtils.convertToInteger(rawValue)); return value;case 3: value = Long.valueOf(XMPUtils.convertToLong(rawValue)); return value;case 4: value = Double.valueOf(XMPUtils.convertToDouble(rawValue)); return value;case 5: value = XMPUtils.convertToDate(rawValue); return value;case 6: dt = XMPUtils.convertToDate(rawValue); value = dt.getCalendar(); return value;case 7: value = XMPUtils.decodeBase64(rawValue); return value; }  Object value = (rawValue != null || propNode.getOptions().isCompositeProperty()) ? rawValue : ""; return value;
/*      */   }
/*      */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/kernel/xmp/impl/XMPMetaImpl.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */