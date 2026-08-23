/*     */ package com.itextpdf.kernel.pdf.canvas;
/*     */ 
/*     */ import com.itextpdf.kernel.pdf.PdfDictionary;
/*     */ import com.itextpdf.kernel.pdf.PdfName;
/*     */ import com.itextpdf.kernel.pdf.PdfNumber;
/*     */ import com.itextpdf.kernel.pdf.PdfObject;
/*     */ import com.itextpdf.kernel.pdf.PdfString;
/*     */ import com.itextpdf.kernel.pdf.tagging.PdfMcr;
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
/*     */ public class CanvasTag
/*     */ {
/*     */   protected PdfName role;
/*     */   protected PdfDictionary properties;
/*     */   
/*     */   public CanvasTag(PdfName role) {
/*  81 */     this.role = role;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public CanvasTag(PdfName role, int mcid) {
/*  92 */     this.role = role;
/*  93 */     addProperty(PdfName.MCID, (PdfObject)new PdfNumber(mcid));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public CanvasTag(PdfMcr mcr) {
/* 103 */     this(mcr.getRole(), mcr.getMcid());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PdfName getRole() {
/* 112 */     return this.role;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMcid() {
/* 122 */     int mcid = -1;
/* 123 */     if (this.properties != null) {
/* 124 */       mcid = this.properties.getAsInt(PdfName.MCID).intValue();
/*     */     }
/* 126 */     if (mcid == -1) {
/* 127 */       throw new IllegalStateException("CanvasTag has no MCID");
/*     */     }
/* 129 */     return mcid;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean hasMcid() {
/* 137 */     return (this.properties != null && this.properties.containsKey(PdfName.MCID));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public CanvasTag setProperties(PdfDictionary properties) {
/* 147 */     this.properties = properties;
/* 148 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public CanvasTag addProperty(PdfName name, PdfObject value) {
/* 159 */     ensurePropertiesInit();
/* 160 */     this.properties.put(name, value);
/* 161 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public CanvasTag removeProperty(PdfName name) {
/* 171 */     if (this.properties != null) {
/* 172 */       this.properties.remove(name);
/*     */     }
/* 174 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PdfObject getProperty(PdfName name) {
/* 184 */     if (this.properties == null) {
/* 185 */       return null;
/*     */     }
/* 187 */     return this.properties.get(name);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PdfDictionary getProperties() {
/* 196 */     return this.properties;
/*     */   }
/*     */   
/*     */   public String getActualText() {
/* 200 */     return getPropertyAsString(PdfName.ActualText);
/*     */   }
/*     */   
/*     */   public String getExpansionText() {
/* 204 */     return getPropertyAsString(PdfName.E);
/*     */   }
/*     */   
/*     */   private String getPropertyAsString(PdfName name) {
/* 208 */     PdfString text = this.properties.getAsString(name);
/* 209 */     String result = null;
/* 210 */     if (text != null) {
/* 211 */       result = text.toUnicodeString();
/*     */     }
/* 213 */     return result;
/*     */   }
/*     */   
/*     */   private void ensurePropertiesInit() {
/* 217 */     if (this.properties == null)
/* 218 */       this.properties = new PdfDictionary(); 
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/kernel/pdf/canvas/CanvasTag.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */