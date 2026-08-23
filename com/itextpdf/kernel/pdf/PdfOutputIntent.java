/*     */ package com.itextpdf.kernel.pdf;
/*     */ 
/*     */ import com.itextpdf.kernel.pdf.colorspace.PdfCieBasedCs;
/*     */ import java.io.InputStream;
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
/*     */ public class PdfOutputIntent
/*     */   extends PdfObjectWrapper<PdfDictionary>
/*     */ {
/*     */   private static final long serialVersionUID = -3814334679568337730L;
/*     */   
/*     */   public PdfOutputIntent(String outputConditionIdentifier, String outputCondition, String registryName, String info, InputStream iccStream) {
/*  75 */     super(new PdfDictionary());
/*  76 */     setOutputIntentSubtype(PdfName.GTS_PDFA1);
/*  77 */     getPdfObject().put(PdfName.Type, PdfName.OutputIntent);
/*  78 */     if (outputCondition != null)
/*  79 */       setOutputCondition(outputCondition); 
/*  80 */     if (outputConditionIdentifier != null)
/*  81 */       setOutputConditionIdentifier(outputConditionIdentifier); 
/*  82 */     if (registryName != null)
/*  83 */       setRegistryName(registryName); 
/*  84 */     if (info != null)
/*  85 */       setInfo(info); 
/*  86 */     if (iccStream != null) {
/*  87 */       setDestOutputProfile(iccStream);
/*     */     }
/*     */   }
/*     */   
/*     */   public PdfOutputIntent(PdfDictionary outputIntentDict) {
/*  92 */     super(outputIntentDict);
/*     */   }
/*     */   
/*     */   public PdfStream getDestOutputProfile() {
/*  96 */     return getPdfObject().getAsStream(PdfName.DestOutputProfile);
/*     */   }
/*     */   
/*     */   public void setDestOutputProfile(InputStream iccStream) {
/* 100 */     PdfStream stream = PdfCieBasedCs.IccBased.getIccProfileStream(iccStream);
/* 101 */     getPdfObject().put(PdfName.DestOutputProfile, stream);
/*     */   }
/*     */   
/*     */   public PdfString getInfo() {
/* 105 */     return getPdfObject().getAsString(PdfName.Info);
/*     */   }
/*     */   
/*     */   public void setInfo(String info) {
/* 109 */     getPdfObject().put(PdfName.Info, new PdfString(info));
/*     */   }
/*     */   
/*     */   public PdfString getRegistryName() {
/* 113 */     return getPdfObject().getAsString(PdfName.RegistryName);
/*     */   }
/*     */   
/*     */   public void setRegistryName(String registryName) {
/* 117 */     getPdfObject().put(PdfName.RegistryName, new PdfString(registryName));
/*     */   }
/*     */   
/*     */   public PdfString getOutputConditionIdentifier() {
/* 121 */     return getPdfObject().getAsString(PdfName.OutputConditionIdentifier);
/*     */   }
/*     */   
/*     */   public void setOutputConditionIdentifier(String outputConditionIdentifier) {
/* 125 */     getPdfObject().put(PdfName.OutputConditionIdentifier, new PdfString(outputConditionIdentifier));
/*     */   }
/*     */   
/*     */   public PdfString getOutputCondition() {
/* 129 */     return getPdfObject().getAsString(PdfName.OutputCondition);
/*     */   }
/*     */   
/*     */   public void setOutputCondition(String outputCondition) {
/* 133 */     getPdfObject().put(PdfName.OutputCondition, new PdfString(outputCondition));
/*     */   }
/*     */   
/*     */   public PdfName getOutputIntentSubtype() {
/* 137 */     return getPdfObject().getAsName(PdfName.S);
/*     */   }
/*     */   
/*     */   public void setOutputIntentSubtype(PdfName subtype) {
/* 141 */     getPdfObject().put(PdfName.S, subtype);
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean isWrappedObjectMustBeIndirect() {
/* 146 */     return false;
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/kernel/pdf/PdfOutputIntent.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */