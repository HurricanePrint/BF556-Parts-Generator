/*     */ package com.itextpdf.kernel.pdf;
/*     */ 
/*     */ import com.itextpdf.io.util.MessageFormatUtil;
/*     */ import java.io.Serializable;
/*     */ import java.util.ArrayList;
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
/*     */ public class PdfVersion
/*     */   implements Comparable<PdfVersion>, Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 6168855906667968169L;
/*  58 */   private static final List<PdfVersion> values = new ArrayList<>();
/*     */   
/*  60 */   public static final PdfVersion PDF_1_0 = createPdfVersion(1, 0);
/*  61 */   public static final PdfVersion PDF_1_1 = createPdfVersion(1, 1);
/*  62 */   public static final PdfVersion PDF_1_2 = createPdfVersion(1, 2);
/*  63 */   public static final PdfVersion PDF_1_3 = createPdfVersion(1, 3);
/*  64 */   public static final PdfVersion PDF_1_4 = createPdfVersion(1, 4);
/*  65 */   public static final PdfVersion PDF_1_5 = createPdfVersion(1, 5);
/*  66 */   public static final PdfVersion PDF_1_6 = createPdfVersion(1, 6);
/*  67 */   public static final PdfVersion PDF_1_7 = createPdfVersion(1, 7);
/*  68 */   public static final PdfVersion PDF_2_0 = createPdfVersion(2, 0);
/*     */ 
/*     */   
/*     */   private int major;
/*     */ 
/*     */   
/*     */   private int minor;
/*     */ 
/*     */ 
/*     */   
/*     */   private PdfVersion(int major, int minor) {
/*  79 */     this.major = major;
/*  80 */     this.minor = minor;
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/*  85 */     return MessageFormatUtil.format("PDF-{0}.{1}", new Object[] { Integer.valueOf(this.major), Integer.valueOf(this.minor) });
/*     */   }
/*     */   
/*     */   public PdfName toPdfName() {
/*  89 */     return new PdfName(MessageFormatUtil.format("{0}.{1}", new Object[] { Integer.valueOf(this.major), Integer.valueOf(this.minor) }));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static PdfVersion fromString(String value) {
/* 100 */     for (PdfVersion version : values) {
/* 101 */       if (version.toString().equals(value)) {
/* 102 */         return version;
/*     */       }
/*     */     } 
/* 105 */     throw new IllegalArgumentException("The provided pdf version was not found.");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static PdfVersion fromPdfName(PdfName name) {
/* 116 */     for (PdfVersion version : values) {
/* 117 */       if (version.toPdfName().equals(name)) {
/* 118 */         return version;
/*     */       }
/*     */     } 
/* 121 */     throw new IllegalArgumentException("The provided pdf version was not found.");
/*     */   }
/*     */ 
/*     */   
/*     */   public int compareTo(PdfVersion o) {
/* 126 */     int majorResult = Integer.compare(this.major, o.major);
/* 127 */     if (majorResult != 0) {
/* 128 */       return majorResult;
/*     */     }
/* 130 */     return Integer.compare(this.minor, o.minor);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/* 136 */     return (getClass() == obj.getClass() && compareTo((PdfVersion)obj) == 0);
/*     */   }
/*     */   
/*     */   private static PdfVersion createPdfVersion(int major, int minor) {
/* 140 */     PdfVersion pdfVersion = new PdfVersion(major, minor);
/* 141 */     values.add(pdfVersion);
/* 142 */     return pdfVersion;
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/kernel/pdf/PdfVersion.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */