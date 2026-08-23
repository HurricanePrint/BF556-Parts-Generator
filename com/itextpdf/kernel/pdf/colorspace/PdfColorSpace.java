/*     */ package com.itextpdf.kernel.pdf.colorspace;
/*     */ 
/*     */ import com.itextpdf.kernel.pdf.PdfArray;
/*     */ import com.itextpdf.kernel.pdf.PdfIndirectReference;
/*     */ import com.itextpdf.kernel.pdf.PdfName;
/*     */ import com.itextpdf.kernel.pdf.PdfObject;
/*     */ import com.itextpdf.kernel.pdf.PdfObjectWrapper;
/*     */ import java.util.Arrays;
/*     */ import java.util.HashSet;
/*     */ import java.util.Set;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class PdfColorSpace
/*     */   extends PdfObjectWrapper<PdfObject>
/*     */ {
/*  61 */   public static final Set<PdfName> directColorSpaces = new HashSet<>(Arrays.asList(new PdfName[] { PdfName.DeviceGray, PdfName.DeviceRGB, PdfName.DeviceCMYK, PdfName.Pattern }));
/*     */   
/*     */   private static final long serialVersionUID = 2553991039779429813L;
/*     */   
/*     */   protected PdfColorSpace(PdfObject pdfObject) {
/*  66 */     super(pdfObject);
/*     */   }
/*     */   
/*     */   public abstract int getNumberOfComponents();
/*     */   
/*     */   public static PdfColorSpace makeColorSpace(PdfObject pdfObject) {
/*  72 */     if (pdfObject.isIndirectReference())
/*  73 */       pdfObject = ((PdfIndirectReference)pdfObject).getRefersTo(); 
/*  74 */     if (pdfObject.isArray() && ((PdfArray)pdfObject).size() == 1)
/*  75 */       pdfObject = ((PdfArray)pdfObject).get(0); 
/*  76 */     if (PdfName.DeviceGray.equals(pdfObject))
/*  77 */       return new PdfDeviceCs.Gray(); 
/*  78 */     if (PdfName.DeviceRGB.equals(pdfObject))
/*  79 */       return new PdfDeviceCs.Rgb(); 
/*  80 */     if (PdfName.DeviceCMYK.equals(pdfObject))
/*  81 */       return new PdfDeviceCs.Cmyk(); 
/*  82 */     if (PdfName.Pattern.equals(pdfObject))
/*  83 */       return new PdfSpecialCs.Pattern(); 
/*  84 */     if (pdfObject.isArray()) {
/*  85 */       PdfArray array = (PdfArray)pdfObject;
/*  86 */       PdfName csType = array.getAsName(0);
/*  87 */       if (PdfName.CalGray.equals(csType))
/*  88 */         return new PdfCieBasedCs.CalGray(array); 
/*  89 */       if (PdfName.CalRGB.equals(csType))
/*  90 */         return new PdfCieBasedCs.CalRgb(array); 
/*  91 */       if (PdfName.Lab.equals(csType))
/*  92 */         return new PdfCieBasedCs.Lab(array); 
/*  93 */       if (PdfName.ICCBased.equals(csType))
/*  94 */         return new PdfCieBasedCs.IccBased(array); 
/*  95 */       if (PdfName.Indexed.equals(csType))
/*  96 */         return new PdfSpecialCs.Indexed(array); 
/*  97 */       if (PdfName.Separation.equals(csType))
/*  98 */         return new PdfSpecialCs.Separation(array); 
/*  99 */       if (PdfName.DeviceN.equals(csType))
/*     */       {
/* 101 */         return (array.size() == 4) ? new PdfSpecialCs.DeviceN(array) : new PdfSpecialCs.NChannel(array); } 
/* 102 */       if (PdfName.Pattern.equals(csType))
/* 103 */         return new PdfSpecialCs.UncoloredTilingPattern(array); 
/*     */     } 
/* 105 */     return null;
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/kernel/pdf/colorspace/PdfColorSpace.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */