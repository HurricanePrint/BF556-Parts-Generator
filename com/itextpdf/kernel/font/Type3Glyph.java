/*     */ package com.itextpdf.kernel.font;
/*     */ 
/*     */ import com.itextpdf.io.image.ImageData;
/*     */ import com.itextpdf.io.source.ByteUtils;
/*     */ import com.itextpdf.kernel.PdfException;
/*     */ import com.itextpdf.kernel.pdf.PdfDocument;
/*     */ import com.itextpdf.kernel.pdf.PdfOutputStream;
/*     */ import com.itextpdf.kernel.pdf.PdfStream;
/*     */ import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
/*     */ import com.itextpdf.kernel.pdf.xobject.PdfXObject;
/*     */ import java.nio.charset.StandardCharsets;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class Type3Glyph
/*     */   extends PdfCanvas
/*     */ {
/*     */   private static final String D_0_STR = "d0\n";
/*     */   private static final String D_1_STR = "d1\n";
/*  63 */   private static final byte[] d0 = ByteUtils.getIsoBytes("d0\n");
/*  64 */   private static final byte[] d1 = ByteUtils.getIsoBytes("d1\n");
/*     */   
/*     */   private static final long serialVersionUID = 5811604071799271336L;
/*     */   
/*     */   private float wx;
/*     */   
/*     */   private float llx;
/*     */   
/*     */   private float lly;
/*     */   
/*     */   private float urx;
/*     */   
/*     */   private float ury;
/*     */   private boolean isColor = false;
/*     */   
/*     */   Type3Glyph(PdfDocument pdfDocument, float wx, float llx, float lly, float urx, float ury, boolean isColor) {
/*  80 */     super((PdfStream)(new PdfStream()).makeIndirect(pdfDocument), null, pdfDocument);
/*  81 */     writeMetrics(wx, llx, lly, urx, ury, isColor);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   Type3Glyph(PdfStream pdfStream, PdfDocument document) {
/*  91 */     super(pdfStream, null, document);
/*  92 */     if (pdfStream.getBytes() != null) {
/*  93 */       fillBBFromBytes(pdfStream.getBytes());
/*     */     }
/*     */   }
/*     */   
/*     */   public float getWx() {
/*  98 */     return this.wx;
/*     */   }
/*     */   
/*     */   public float getLlx() {
/* 102 */     return this.llx;
/*     */   }
/*     */   
/*     */   public float getLly() {
/* 106 */     return this.lly;
/*     */   }
/*     */   
/*     */   public float getUrx() {
/* 110 */     return this.urx;
/*     */   }
/*     */   
/*     */   public float getUry() {
/* 114 */     return this.ury;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isColor() {
/* 123 */     return this.isColor;
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
/*     */   private void writeMetrics(float wx, float llx, float lly, float urx, float ury, boolean isColor) {
/* 142 */     this.isColor = isColor;
/* 143 */     this.wx = wx;
/*     */     
/* 145 */     this.llx = llx;
/* 146 */     this.lly = lly;
/* 147 */     this.urx = urx;
/* 148 */     this.ury = ury;
/*     */     
/* 150 */     if (isColor) {
/* 151 */       ((PdfOutputStream)((PdfOutputStream)((PdfOutputStream)((PdfOutputStream)this.contentStream.getOutputStream()
/* 152 */         .writeFloat(wx))
/* 153 */         .writeSpace())
/*     */         
/* 155 */         .writeFloat(0.0F))
/* 156 */         .writeSpace())
/* 157 */         .writeBytes(d0);
/*     */     } else {
/* 159 */       ((PdfOutputStream)((PdfOutputStream)((PdfOutputStream)((PdfOutputStream)((PdfOutputStream)((PdfOutputStream)((PdfOutputStream)((PdfOutputStream)((PdfOutputStream)((PdfOutputStream)((PdfOutputStream)((PdfOutputStream)this.contentStream.getOutputStream()
/* 160 */         .writeFloat(wx))
/* 161 */         .writeSpace())
/*     */         
/* 163 */         .writeFloat(0.0F))
/* 164 */         .writeSpace())
/* 165 */         .writeFloat(llx))
/* 166 */         .writeSpace())
/* 167 */         .writeFloat(lly))
/* 168 */         .writeSpace())
/* 169 */         .writeFloat(urx))
/* 170 */         .writeSpace())
/* 171 */         .writeFloat(ury))
/* 172 */         .writeSpace())
/* 173 */         .writeBytes(d1);
/*     */     } 
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
/*     */   public PdfXObject addImage(ImageData image, float a, float b, float c, float d, float e, float f, boolean inlineImage) {
/* 193 */     if (!this.isColor && (!image.isMask() || (image.getBpc() != 1 && image.getBpc() <= 255))) {
/* 194 */       throw new PdfException("Not colorized type3 fonts accept only mask images.");
/*     */     }
/* 196 */     return super.addImage(image, a, b, c, d, e, f, inlineImage);
/*     */   }
/*     */   
/*     */   private void fillBBFromBytes(byte[] bytes) {
/* 200 */     String str = new String(bytes, StandardCharsets.ISO_8859_1);
/* 201 */     int d0Pos = str.indexOf("d0\n");
/* 202 */     int d1Pos = str.indexOf("d1\n");
/* 203 */     if (d0Pos != -1) {
/* 204 */       this.isColor = true;
/* 205 */       String[] bbArray = str.substring(0, d0Pos - 1).split(" ");
/* 206 */       if (bbArray.length == 2)
/* 207 */         this.wx = Float.parseFloat(bbArray[0]); 
/* 208 */     } else if (d1Pos != -1) {
/* 209 */       this.isColor = false;
/* 210 */       String[] bbArray = str.substring(0, d1Pos - 1).split(" ");
/* 211 */       if (bbArray.length == 6) {
/* 212 */         this.wx = Float.parseFloat(bbArray[0]);
/* 213 */         this.llx = Float.parseFloat(bbArray[2]);
/* 214 */         this.lly = Float.parseFloat(bbArray[3]);
/* 215 */         this.urx = Float.parseFloat(bbArray[4]);
/* 216 */         this.ury = Float.parseFloat(bbArray[5]);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/kernel/font/Type3Glyph.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */