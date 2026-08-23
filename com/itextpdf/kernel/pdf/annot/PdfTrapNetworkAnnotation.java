/*     */ package com.itextpdf.kernel.pdf.annot;
/*     */ 
/*     */ import com.itextpdf.kernel.PdfException;
/*     */ import com.itextpdf.kernel.font.PdfFont;
/*     */ import com.itextpdf.kernel.geom.Rectangle;
/*     */ import com.itextpdf.kernel.pdf.PdfArray;
/*     */ import com.itextpdf.kernel.pdf.PdfDate;
/*     */ import com.itextpdf.kernel.pdf.PdfDictionary;
/*     */ import com.itextpdf.kernel.pdf.PdfName;
/*     */ import com.itextpdf.kernel.pdf.PdfObject;
/*     */ import com.itextpdf.kernel.pdf.PdfString;
/*     */ import com.itextpdf.kernel.pdf.xobject.PdfFormXObject;
/*     */ import java.util.List;
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
/*     */ public class PdfTrapNetworkAnnotation
/*     */   extends PdfAnnotation
/*     */ {
/*     */   private static final long serialVersionUID = 5118904991630303608L;
/*     */   
/*     */   public PdfTrapNetworkAnnotation(Rectangle rect, PdfFormXObject appearanceStream) {
/*  84 */     super(rect);
/*  85 */     if (appearanceStream.getProcessColorModel() == null) {
/*  86 */       throw new PdfException("Process color model must be set in appearance stream for Trap Network annotation!");
/*     */     }
/*  88 */     setNormalAppearance((PdfDictionary)appearanceStream.getPdfObject());
/*  89 */     setFlags(68);
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
/*     */   protected PdfTrapNetworkAnnotation(PdfDictionary pdfObject) {
/* 102 */     super(pdfObject);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PdfName getSubtype() {
/* 110 */     return PdfName.TrapNet;
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
/*     */   public PdfTrapNetworkAnnotation setLastModified(PdfDate lastModified) {
/* 123 */     return (PdfTrapNetworkAnnotation)put(PdfName.LastModified, lastModified.getPdfObject());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PdfString getLastModified() {
/* 133 */     return ((PdfDictionary)getPdfObject()).getAsString(PdfName.LastModified);
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
/*     */   public PdfTrapNetworkAnnotation setVersion(PdfArray version) {
/* 154 */     return (PdfTrapNetworkAnnotation)put(PdfName.Version, (PdfObject)version);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PdfArray getVersion() {
/* 165 */     return ((PdfDictionary)getPdfObject()).getAsArray(PdfName.Version);
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
/*     */   public PdfTrapNetworkAnnotation setAnnotStates(PdfArray annotStates) {
/* 181 */     return (PdfTrapNetworkAnnotation)put(PdfName.AnnotStates, (PdfObject)annotStates);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PdfArray getAnnotStates() {
/* 191 */     return ((PdfDictionary)getPdfObject()).getAsArray(PdfName.AnnotStates);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PdfTrapNetworkAnnotation setFauxedFonts(PdfArray fauxedFonts) {
/* 202 */     return (PdfTrapNetworkAnnotation)put(PdfName.FontFauxing, (PdfObject)fauxedFonts);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PdfTrapNetworkAnnotation setFauxedFonts(List<PdfFont> fauxedFonts) {
/* 213 */     PdfArray arr = new PdfArray();
/* 214 */     for (PdfFont f : fauxedFonts)
/* 215 */       arr.add(f.getPdfObject()); 
/* 216 */     return setFauxedFonts(arr);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PdfArray getFauxedFonts() {
/* 226 */     return ((PdfDictionary)getPdfObject()).getAsArray(PdfName.FontFauxing);
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/kernel/pdf/annot/PdfTrapNetworkAnnotation.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */