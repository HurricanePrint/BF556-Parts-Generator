/*     */ package com.itextpdf.kernel.pdf.tagging;
/*     */ 
/*     */ import com.itextpdf.kernel.pdf.PdfDictionary;
/*     */ import com.itextpdf.kernel.pdf.PdfName;
/*     */ import com.itextpdf.kernel.pdf.PdfNumber;
/*     */ import com.itextpdf.kernel.pdf.PdfObject;
/*     */ import com.itextpdf.kernel.pdf.PdfObjectWrapper;
/*     */ import com.itextpdf.kernel.pdf.PdfString;
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
/*     */ 
/*     */ 
/*     */ public class PdfStructureAttributes
/*     */   extends PdfObjectWrapper<PdfDictionary>
/*     */ {
/*     */   private static final long serialVersionUID = 3972284224659975750L;
/*     */   
/*     */   public PdfStructureAttributes(PdfDictionary attributesDict) {
/*  57 */     super((PdfObject)attributesDict);
/*     */   }
/*     */   
/*     */   public PdfStructureAttributes(String owner) {
/*  61 */     super((PdfObject)new PdfDictionary());
/*  62 */     ((PdfDictionary)getPdfObject()).put(PdfName.O, (PdfObject)PdfStructTreeRoot.convertRoleToPdfName(owner));
/*     */   }
/*     */   
/*     */   public PdfStructureAttributes(PdfNamespace namespace) {
/*  66 */     super((PdfObject)new PdfDictionary());
/*  67 */     ((PdfDictionary)getPdfObject()).put(PdfName.O, (PdfObject)PdfName.NSO);
/*  68 */     ((PdfDictionary)getPdfObject()).put(PdfName.NS, namespace.getPdfObject());
/*     */   }
/*     */   
/*     */   public PdfStructureAttributes addEnumAttribute(String attributeName, String attributeValue) {
/*  72 */     PdfName name = PdfStructTreeRoot.convertRoleToPdfName(attributeName);
/*  73 */     ((PdfDictionary)getPdfObject()).put(name, (PdfObject)new PdfName(attributeValue));
/*  74 */     setModified();
/*  75 */     return this;
/*     */   }
/*     */   
/*     */   public PdfStructureAttributes addTextAttribute(String attributeName, String attributeValue) {
/*  79 */     PdfName name = PdfStructTreeRoot.convertRoleToPdfName(attributeName);
/*  80 */     ((PdfDictionary)getPdfObject()).put(name, (PdfObject)new PdfString(attributeValue, "UnicodeBig"));
/*  81 */     setModified();
/*  82 */     return this;
/*     */   }
/*     */   
/*     */   public PdfStructureAttributes addIntAttribute(String attributeName, int attributeValue) {
/*  86 */     PdfName name = PdfStructTreeRoot.convertRoleToPdfName(attributeName);
/*  87 */     ((PdfDictionary)getPdfObject()).put(name, (PdfObject)new PdfNumber(attributeValue));
/*  88 */     setModified();
/*  89 */     return this;
/*     */   }
/*     */   
/*     */   public PdfStructureAttributes addFloatAttribute(String attributeName, float attributeValue) {
/*  93 */     PdfName name = PdfStructTreeRoot.convertRoleToPdfName(attributeName);
/*  94 */     ((PdfDictionary)getPdfObject()).put(name, (PdfObject)new PdfNumber(attributeValue));
/*  95 */     setModified();
/*  96 */     return this;
/*     */   }
/*     */   
/*     */   public String getAttributeAsEnum(String attributeName) {
/* 100 */     PdfName name = PdfStructTreeRoot.convertRoleToPdfName(attributeName);
/* 101 */     PdfName attrVal = ((PdfDictionary)getPdfObject()).getAsName(name);
/* 102 */     return (attrVal != null) ? attrVal.getValue() : null;
/*     */   }
/*     */   
/*     */   public String getAttributeAsText(String attributeName) {
/* 106 */     PdfName name = PdfStructTreeRoot.convertRoleToPdfName(attributeName);
/* 107 */     PdfString attrVal = ((PdfDictionary)getPdfObject()).getAsString(name);
/* 108 */     return (attrVal != null) ? attrVal.toUnicodeString() : null;
/*     */   }
/*     */   
/*     */   public Integer getAttributeAsInt(String attributeName) {
/* 112 */     PdfName name = PdfStructTreeRoot.convertRoleToPdfName(attributeName);
/* 113 */     PdfNumber attrVal = ((PdfDictionary)getPdfObject()).getAsNumber(name);
/* 114 */     return (attrVal != null) ? Integer.valueOf(attrVal.intValue()) : (Integer)null;
/*     */   }
/*     */   
/*     */   public Float getAttributeAsFloat(String attributeName) {
/* 118 */     PdfName name = PdfStructTreeRoot.convertRoleToPdfName(attributeName);
/* 119 */     PdfNumber attrVal = ((PdfDictionary)getPdfObject()).getAsNumber(name);
/* 120 */     return (attrVal != null) ? Float.valueOf(attrVal.floatValue()) : (Float)null;
/*     */   }
/*     */   
/*     */   public PdfStructureAttributes removeAttribute(String attributeName) {
/* 124 */     PdfName name = PdfStructTreeRoot.convertRoleToPdfName(attributeName);
/* 125 */     ((PdfDictionary)getPdfObject()).remove(name);
/* 126 */     setModified();
/* 127 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean isWrappedObjectMustBeIndirect() {
/* 132 */     return false;
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/kernel/pdf/tagging/PdfStructureAttributes.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */