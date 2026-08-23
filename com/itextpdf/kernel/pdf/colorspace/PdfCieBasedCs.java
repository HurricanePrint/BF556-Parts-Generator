/*     */ package com.itextpdf.kernel.pdf.colorspace;
/*     */ 
/*     */ import com.itextpdf.io.colors.IccProfile;
/*     */ import com.itextpdf.kernel.PdfException;
/*     */ import com.itextpdf.kernel.pdf.PdfArray;
/*     */ import com.itextpdf.kernel.pdf.PdfDictionary;
/*     */ import com.itextpdf.kernel.pdf.PdfName;
/*     */ import com.itextpdf.kernel.pdf.PdfNumber;
/*     */ import com.itextpdf.kernel.pdf.PdfObject;
/*     */ import com.itextpdf.kernel.pdf.PdfStream;
/*     */ import java.io.InputStream;
/*     */ import java.util.ArrayList;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class PdfCieBasedCs
/*     */   extends PdfColorSpace
/*     */ {
/*     */   private static final long serialVersionUID = 7803780450619297557L;
/*     */   
/*     */   public void flush() {
/*  72 */     super.flush();
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean isWrappedObjectMustBeIndirect() {
/*  77 */     return true;
/*     */   }
/*     */   
/*     */   protected PdfCieBasedCs(PdfArray pdfObject) {
/*  81 */     super((PdfObject)pdfObject);
/*     */   }
/*     */   
/*     */   public static class CalGray
/*     */     extends PdfCieBasedCs {
/*     */     private static final long serialVersionUID = -3974274460820215173L;
/*     */     
/*     */     public CalGray(PdfArray pdfObject) {
/*  89 */       super(pdfObject);
/*     */     }
/*     */     
/*     */     public CalGray(float[] whitePoint) {
/*  93 */       this(getInitialPdfArray());
/*  94 */       if (whitePoint == null || whitePoint.length != 3)
/*  95 */         throw new PdfException("White point is incorrectly specified.", this); 
/*  96 */       PdfDictionary d = ((PdfArray)getPdfObject()).getAsDictionary(1);
/*  97 */       d.put(PdfName.WhitePoint, (PdfObject)new PdfArray(whitePoint));
/*     */     }
/*     */     
/*     */     public CalGray(float[] whitePoint, float[] blackPoint, float gamma) {
/* 101 */       this(whitePoint);
/* 102 */       PdfDictionary d = ((PdfArray)getPdfObject()).getAsDictionary(1);
/* 103 */       if (blackPoint != null)
/* 104 */         d.put(PdfName.BlackPoint, (PdfObject)new PdfArray(blackPoint)); 
/* 105 */       if (gamma != Float.NaN) {
/* 106 */         d.put(PdfName.Gamma, (PdfObject)new PdfNumber(gamma));
/*     */       }
/*     */     }
/*     */     
/*     */     public int getNumberOfComponents() {
/* 111 */       return 1;
/*     */     }
/*     */     
/*     */     private static PdfArray getInitialPdfArray() {
/* 115 */       ArrayList<PdfObject> tempArray = new ArrayList<>(2);
/* 116 */       tempArray.add(PdfName.CalGray);
/* 117 */       tempArray.add(new PdfDictionary());
/* 118 */       return new PdfArray(tempArray);
/*     */     }
/*     */   }
/*     */   
/*     */   public static class CalRgb
/*     */     extends PdfCieBasedCs {
/*     */     private static final long serialVersionUID = -2926074370411556426L;
/*     */     
/*     */     public CalRgb(PdfArray pdfObject) {
/* 127 */       super(pdfObject);
/*     */     }
/*     */     
/*     */     public CalRgb(float[] whitePoint) {
/* 131 */       this(getInitialPdfArray());
/* 132 */       if (whitePoint == null || whitePoint.length != 3)
/* 133 */         throw new PdfException("White point is incorrectly specified.", this); 
/* 134 */       PdfDictionary d = ((PdfArray)getPdfObject()).getAsDictionary(1);
/* 135 */       d.put(PdfName.WhitePoint, (PdfObject)new PdfArray(whitePoint));
/*     */     }
/*     */     
/*     */     public CalRgb(float[] whitePoint, float[] blackPoint, float[] gamma, float[] matrix) {
/* 139 */       this(whitePoint);
/* 140 */       PdfDictionary d = ((PdfArray)getPdfObject()).getAsDictionary(1);
/* 141 */       if (blackPoint != null)
/* 142 */         d.put(PdfName.BlackPoint, (PdfObject)new PdfArray(blackPoint)); 
/* 143 */       if (gamma != null)
/* 144 */         d.put(PdfName.Gamma, (PdfObject)new PdfArray(gamma)); 
/* 145 */       if (matrix != null) {
/* 146 */         d.put(PdfName.Matrix, (PdfObject)new PdfArray(matrix));
/*     */       }
/*     */     }
/*     */     
/*     */     public int getNumberOfComponents() {
/* 151 */       return 3;
/*     */     }
/*     */     
/*     */     private static PdfArray getInitialPdfArray() {
/* 155 */       ArrayList<PdfObject> tempArray = new ArrayList<>(2);
/* 156 */       tempArray.add(PdfName.CalRGB);
/* 157 */       tempArray.add(new PdfDictionary());
/* 158 */       return new PdfArray(tempArray);
/*     */     }
/*     */   }
/*     */   
/*     */   public static class Lab
/*     */     extends PdfCieBasedCs {
/*     */     private static final long serialVersionUID = 7067722970343880433L;
/*     */     
/*     */     public Lab(PdfArray pdfObject) {
/* 167 */       super(pdfObject);
/*     */     }
/*     */     
/*     */     public Lab(float[] whitePoint) {
/* 171 */       this(getInitialPdfArray());
/* 172 */       if (whitePoint == null || whitePoint.length != 3)
/* 173 */         throw new PdfException("White point is incorrectly specified.", this); 
/* 174 */       PdfDictionary d = ((PdfArray)getPdfObject()).getAsDictionary(1);
/* 175 */       d.put(PdfName.WhitePoint, (PdfObject)new PdfArray(whitePoint));
/*     */     }
/*     */     
/*     */     public Lab(float[] whitePoint, float[] blackPoint, float[] range) {
/* 179 */       this(whitePoint);
/* 180 */       PdfDictionary d = ((PdfArray)getPdfObject()).getAsDictionary(1);
/* 181 */       if (blackPoint != null)
/* 182 */         d.put(PdfName.BlackPoint, (PdfObject)new PdfArray(blackPoint)); 
/* 183 */       if (range != null) {
/* 184 */         d.put(PdfName.Range, (PdfObject)new PdfArray(range));
/*     */       }
/*     */     }
/*     */     
/*     */     public int getNumberOfComponents() {
/* 189 */       return 3;
/*     */     }
/*     */     
/*     */     private static PdfArray getInitialPdfArray() {
/* 193 */       ArrayList<PdfObject> tempArray = new ArrayList<>(2);
/* 194 */       tempArray.add(PdfName.Lab);
/* 195 */       tempArray.add(new PdfDictionary());
/* 196 */       return new PdfArray(tempArray);
/*     */     }
/*     */   }
/*     */   
/*     */   public static class IccBased
/*     */     extends PdfCieBasedCs {
/*     */     private static final long serialVersionUID = 3265273715107224067L;
/*     */     
/*     */     public IccBased(PdfArray pdfObject) {
/* 205 */       super(pdfObject);
/*     */     }
/*     */ 
/*     */     
/*     */     public IccBased(InputStream iccStream) {
/* 210 */       this(getInitialPdfArray(iccStream, null));
/*     */     }
/*     */     
/*     */     public IccBased(InputStream iccStream, float[] range) {
/* 214 */       this(getInitialPdfArray(iccStream, range));
/*     */     }
/*     */ 
/*     */     
/*     */     public int getNumberOfComponents() {
/* 219 */       return ((PdfArray)getPdfObject()).getAsStream(1).getAsInt(PdfName.N).intValue();
/*     */     }
/*     */     
/*     */     public static PdfStream getIccProfileStream(InputStream iccStream) {
/* 223 */       IccProfile iccProfile = IccProfile.getInstance(iccStream);
/* 224 */       return getIccProfileStream(iccProfile);
/*     */     }
/*     */     
/*     */     public static PdfStream getIccProfileStream(InputStream iccStream, float[] range) {
/* 228 */       IccProfile iccProfile = IccProfile.getInstance(iccStream);
/* 229 */       return getIccProfileStream(iccProfile, range);
/*     */     }
/*     */     
/*     */     public static PdfStream getIccProfileStream(IccProfile iccProfile) {
/* 233 */       PdfStream stream = new PdfStream(iccProfile.getData());
/* 234 */       stream.put(PdfName.N, (PdfObject)new PdfNumber(iccProfile.getNumComponents()));
/* 235 */       switch (iccProfile.getNumComponents()) {
/*     */         case 1:
/* 237 */           stream.put(PdfName.Alternate, (PdfObject)PdfName.DeviceGray);
/*     */           break;
/*     */         case 3:
/* 240 */           stream.put(PdfName.Alternate, (PdfObject)PdfName.DeviceRGB);
/*     */           break;
/*     */         case 4:
/* 243 */           stream.put(PdfName.Alternate, (PdfObject)PdfName.DeviceCMYK);
/*     */           break;
/*     */       } 
/*     */ 
/*     */       
/* 248 */       return stream;
/*     */     }
/*     */     
/*     */     public static PdfStream getIccProfileStream(IccProfile iccProfile, float[] range) {
/* 252 */       PdfStream stream = getIccProfileStream(iccProfile);
/* 253 */       stream.put(PdfName.Range, (PdfObject)new PdfArray(range));
/* 254 */       return stream;
/*     */     }
/*     */     
/*     */     private static PdfArray getInitialPdfArray(InputStream iccStream, float[] range) {
/* 258 */       ArrayList<PdfObject> tempArray = new ArrayList<>(2);
/* 259 */       tempArray.add(PdfName.ICCBased);
/* 260 */       tempArray.add((range == null) ? getIccProfileStream(iccStream) : getIccProfileStream(iccStream, range));
/* 261 */       return new PdfArray(tempArray);
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/kernel/pdf/colorspace/PdfCieBasedCs.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */