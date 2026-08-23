/*     */ package com.itextpdf.kernel.pdf.annot;
/*     */ 
/*     */ import com.itextpdf.kernel.geom.Rectangle;
/*     */ import com.itextpdf.kernel.pdf.PdfDictionary;
/*     */ import com.itextpdf.kernel.pdf.PdfDocument;
/*     */ import com.itextpdf.kernel.pdf.PdfName;
/*     */ import com.itextpdf.kernel.pdf.PdfNumber;
/*     */ import com.itextpdf.kernel.pdf.PdfObject;
/*     */ import com.itextpdf.kernel.pdf.PdfStream;
/*     */ import java.io.BufferedInputStream;
/*     */ import java.io.IOException;
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
/*     */ public class PdfSoundAnnotation
/*     */   extends PdfMarkupAnnotation
/*     */ {
/*     */   private static final long serialVersionUID = -2319779211858842136L;
/*     */   
/*     */   public PdfSoundAnnotation(Rectangle rect, PdfStream sound) {
/*  73 */     super(rect);
/*  74 */     put(PdfName.Sound, (PdfObject)sound);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected PdfSoundAnnotation(PdfDictionary pdfObject) {
/*  85 */     super(pdfObject);
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
/*     */   public PdfSoundAnnotation(PdfDocument document, Rectangle rect, InputStream soundStream, float sampleRate, PdfName encoding, int channels, int sampleSizeInBits) throws IOException {
/* 101 */     super(rect);
/* 102 */     PdfStream sound = new PdfStream(document, correctWavFile(soundStream));
/* 103 */     sound.put(PdfName.R, (PdfObject)new PdfNumber(sampleRate));
/* 104 */     sound.put(PdfName.E, (PdfObject)encoding);
/* 105 */     sound.put(PdfName.B, (PdfObject)new PdfNumber(sampleSizeInBits));
/* 106 */     sound.put(PdfName.C, (PdfObject)new PdfNumber(channels));
/* 107 */     put(PdfName.Sound, (PdfObject)sound);
/*     */   }
/*     */ 
/*     */   
/*     */   public PdfName getSubtype() {
/* 112 */     return PdfName.Sound;
/*     */   }
/*     */   
/*     */   public PdfStream getSound() {
/* 116 */     return ((PdfDictionary)getPdfObject()).getAsStream(PdfName.Sound);
/*     */   }
/*     */   
/*     */   private static InputStream correctWavFile(InputStream is) throws IOException {
/* 120 */     String header = "";
/* 121 */     InputStream bufferedIn = new BufferedInputStream(is);
/* 122 */     bufferedIn.mark(0);
/* 123 */     for (int i = 0; i < 4; i++) {
/* 124 */       header = header + (char)bufferedIn.read();
/*     */     }
/* 126 */     bufferedIn.reset();
/* 127 */     if ("RIFF".equals(header)) {
/* 128 */       bufferedIn.read();
/*     */     }
/* 130 */     return bufferedIn;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PdfName getIconName() {
/* 139 */     return ((PdfDictionary)getPdfObject()).getAsName(PdfName.Name);
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
/*     */   public PdfSoundAnnotation setIconName(PdfName name) {
/* 154 */     return (PdfSoundAnnotation)put(PdfName.Name, (PdfObject)name);
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/kernel/pdf/annot/PdfSoundAnnotation.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */