/*     */ package com.itextpdf.kernel.pdf.tagging;
/*     */ 
/*     */ import com.itextpdf.kernel.pdf.PdfBoolean;
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
/*     */ public class PdfUserProperty
/*     */   extends PdfObjectWrapper<PdfDictionary>
/*     */ {
/*     */   private static final long serialVersionUID = -347021704725128837L;
/*     */   
/*     */   public enum ValueType
/*     */   {
/*  58 */     UNKNOWN,
/*  59 */     TEXT,
/*  60 */     NUMBER,
/*  61 */     BOOLEAN;
/*     */   }
/*     */   
/*     */   public PdfUserProperty(PdfDictionary pdfObject) {
/*  65 */     super((PdfObject)pdfObject);
/*     */   }
/*     */   
/*     */   public PdfUserProperty(String name, String value) {
/*  69 */     super((PdfObject)new PdfDictionary());
/*  70 */     setName(name);
/*  71 */     setValue(value);
/*     */   }
/*     */   
/*     */   public PdfUserProperty(String name, int value) {
/*  75 */     super((PdfObject)new PdfDictionary());
/*  76 */     setName(name);
/*  77 */     setValue(value);
/*     */   }
/*     */   
/*     */   public PdfUserProperty(String name, float value) {
/*  81 */     super((PdfObject)new PdfDictionary());
/*  82 */     setName(name);
/*  83 */     setValue(value);
/*     */   }
/*     */   
/*     */   public PdfUserProperty(String name, boolean value) {
/*  87 */     super((PdfObject)new PdfDictionary());
/*  88 */     setName(name);
/*  89 */     setValue(value);
/*     */   }
/*     */   
/*     */   public String getName() {
/*  93 */     return ((PdfDictionary)getPdfObject()).getAsString(PdfName.N).toUnicodeString();
/*     */   }
/*     */   
/*     */   public PdfUserProperty setName(String name) {
/*  97 */     ((PdfDictionary)getPdfObject()).put(PdfName.N, (PdfObject)new PdfString(name, "UnicodeBig"));
/*  98 */     return this;
/*     */   }
/*     */   
/*     */   public ValueType getValueType() {
/* 102 */     PdfObject valObj = ((PdfDictionary)getPdfObject()).get(PdfName.V);
/* 103 */     if (valObj == null) {
/* 104 */       return ValueType.UNKNOWN;
/*     */     }
/* 106 */     switch (valObj.getType()) {
/*     */       case 2:
/* 108 */         return ValueType.BOOLEAN;
/*     */       case 8:
/* 110 */         return ValueType.NUMBER;
/*     */       case 10:
/* 112 */         return ValueType.TEXT;
/*     */     } 
/* 114 */     return ValueType.UNKNOWN;
/*     */   }
/*     */ 
/*     */   
/*     */   public PdfUserProperty setValue(String value) {
/* 119 */     ((PdfDictionary)getPdfObject()).put(PdfName.V, (PdfObject)new PdfString(value, "UnicodeBig"));
/* 120 */     return this;
/*     */   }
/*     */   
/*     */   public PdfUserProperty setValue(int value) {
/* 124 */     ((PdfDictionary)getPdfObject()).put(PdfName.V, (PdfObject)new PdfNumber(value));
/* 125 */     return this;
/*     */   }
/*     */   
/*     */   public PdfUserProperty setValue(float value) {
/* 129 */     ((PdfDictionary)getPdfObject()).put(PdfName.V, (PdfObject)new PdfNumber(value));
/* 130 */     return this;
/*     */   }
/*     */   
/*     */   public PdfUserProperty setValue(boolean value) {
/* 134 */     ((PdfDictionary)getPdfObject()).put(PdfName.V, (PdfObject)new PdfBoolean(value));
/* 135 */     return this;
/*     */   }
/*     */   
/*     */   public String getValueAsText() {
/* 139 */     PdfString str = ((PdfDictionary)getPdfObject()).getAsString(PdfName.V);
/* 140 */     return (str != null) ? str.toUnicodeString() : null;
/*     */   }
/*     */   
/*     */   public Float getValueAsFloat() {
/* 144 */     PdfNumber num = ((PdfDictionary)getPdfObject()).getAsNumber(PdfName.V);
/* 145 */     return (num != null) ? Float.valueOf(num.floatValue()) : (Float)null;
/*     */   }
/*     */   
/*     */   public Boolean getValueAsBool() {
/* 149 */     return ((PdfDictionary)getPdfObject()).getAsBool(PdfName.V);
/*     */   }
/*     */ 
/*     */   
/*     */   public String getValueFormattedRepresentation() {
/* 154 */     PdfString f = ((PdfDictionary)getPdfObject()).getAsString(PdfName.F);
/* 155 */     return (f != null) ? f.toUnicodeString() : null;
/*     */   }
/*     */   
/*     */   public PdfUserProperty setValueFormattedRepresentation(String formattedRepresentation) {
/* 159 */     ((PdfDictionary)getPdfObject()).put(PdfName.F, (PdfObject)new PdfString(formattedRepresentation, "UnicodeBig"));
/* 160 */     return this;
/*     */   }
/*     */   
/*     */   public Boolean isHidden() {
/* 164 */     return ((PdfDictionary)getPdfObject()).getAsBool(PdfName.H);
/*     */   }
/*     */   
/*     */   public PdfUserProperty setHidden(boolean isHidden) {
/* 168 */     ((PdfDictionary)getPdfObject()).put(PdfName.H, (PdfObject)new PdfBoolean(isHidden));
/* 169 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean isWrappedObjectMustBeIndirect() {
/* 174 */     return false;
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/kernel/pdf/tagging/PdfUserProperty.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */