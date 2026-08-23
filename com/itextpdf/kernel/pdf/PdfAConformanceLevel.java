/*     */ package com.itextpdf.kernel.pdf;
/*     */ 
/*     */ import com.itextpdf.kernel.xmp.XMPException;
/*     */ import com.itextpdf.kernel.xmp.XMPMeta;
/*     */ import com.itextpdf.kernel.xmp.properties.XMPProperty;
/*     */ import java.io.Serializable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class PdfAConformanceLevel
/*     */   implements Serializable
/*     */ {
/*  58 */   public static final PdfAConformanceLevel PDF_A_1A = new PdfAConformanceLevel("1", "A");
/*  59 */   public static final PdfAConformanceLevel PDF_A_1B = new PdfAConformanceLevel("1", "B");
/*  60 */   public static final PdfAConformanceLevel PDF_A_2A = new PdfAConformanceLevel("2", "A");
/*  61 */   public static final PdfAConformanceLevel PDF_A_2B = new PdfAConformanceLevel("2", "B");
/*  62 */   public static final PdfAConformanceLevel PDF_A_2U = new PdfAConformanceLevel("2", "U");
/*  63 */   public static final PdfAConformanceLevel PDF_A_3A = new PdfAConformanceLevel("3", "A");
/*  64 */   public static final PdfAConformanceLevel PDF_A_3B = new PdfAConformanceLevel("3", "B");
/*  65 */   public static final PdfAConformanceLevel PDF_A_3U = new PdfAConformanceLevel("3", "U");
/*     */   
/*     */   private static final long serialVersionUID = 1481878095812910587L;
/*     */   private final String conformance;
/*     */   private final String part;
/*     */   
/*     */   private PdfAConformanceLevel(String part, String conformance) {
/*  72 */     this.conformance = conformance;
/*  73 */     this.part = part;
/*     */   }
/*     */   
/*     */   public String getConformance() {
/*  77 */     return this.conformance;
/*     */   }
/*     */   
/*     */   public String getPart() {
/*  81 */     return this.part;
/*     */   }
/*     */   
/*     */   public static PdfAConformanceLevel getConformanceLevel(String part, String conformance) {
/*  85 */     String lowLetter = conformance.toUpperCase();
/*  86 */     boolean aLevel = "A".equals(lowLetter);
/*  87 */     boolean bLevel = "B".equals(lowLetter);
/*  88 */     boolean uLevel = "U".equals(lowLetter);
/*     */     
/*  90 */     switch (part) {
/*     */       case "1":
/*  92 */         if (aLevel)
/*  93 */           return PDF_A_1A; 
/*  94 */         if (bLevel)
/*  95 */           return PDF_A_1B; 
/*     */         break;
/*     */       case "2":
/*  98 */         if (aLevel)
/*  99 */           return PDF_A_2A; 
/* 100 */         if (bLevel)
/* 101 */           return PDF_A_2B; 
/* 102 */         if (uLevel)
/* 103 */           return PDF_A_2U; 
/*     */         break;
/*     */       case "3":
/* 106 */         if (aLevel)
/* 107 */           return PDF_A_3A; 
/* 108 */         if (bLevel)
/* 109 */           return PDF_A_3B; 
/* 110 */         if (uLevel)
/* 111 */           return PDF_A_3U; 
/*     */         break;
/*     */     } 
/* 114 */     return null;
/*     */   }
/*     */   
/*     */   public static PdfAConformanceLevel getConformanceLevel(XMPMeta meta) {
/* 118 */     XMPProperty conformanceXmpProperty = null;
/* 119 */     XMPProperty partXmpProperty = null;
/*     */     try {
/* 121 */       conformanceXmpProperty = meta.getProperty("http://www.aiim.org/pdfa/ns/id/", "conformance");
/* 122 */       partXmpProperty = meta.getProperty("http://www.aiim.org/pdfa/ns/id/", "part");
/* 123 */     } catch (XMPException xMPException) {}
/*     */     
/* 125 */     if (conformanceXmpProperty == null || partXmpProperty == null) {
/* 126 */       return null;
/*     */     }
/* 128 */     String conformance = conformanceXmpProperty.getValue();
/* 129 */     String part = partXmpProperty.getValue();
/* 130 */     return getConformanceLevel(part, conformance);
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/kernel/pdf/PdfAConformanceLevel.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */