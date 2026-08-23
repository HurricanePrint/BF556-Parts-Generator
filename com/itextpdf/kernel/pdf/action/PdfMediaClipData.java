/*     */ package com.itextpdf.kernel.pdf.action;
/*     */ 
/*     */ import com.itextpdf.io.util.MessageFormatUtil;
/*     */ import com.itextpdf.kernel.pdf.PdfDictionary;
/*     */ import com.itextpdf.kernel.pdf.PdfName;
/*     */ import com.itextpdf.kernel.pdf.PdfObject;
/*     */ import com.itextpdf.kernel.pdf.PdfObjectWrapper;
/*     */ import com.itextpdf.kernel.pdf.PdfString;
/*     */ import com.itextpdf.kernel.pdf.filespec.PdfFileSpec;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class PdfMediaClipData
/*     */   extends PdfObjectWrapper<PdfDictionary>
/*     */ {
/*     */   private static final long serialVersionUID = -7030377585169961523L;
/*  61 */   private static final PdfString TEMPACCESS = new PdfString("TEMPACCESS");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PdfMediaClipData(PdfDictionary pdfObject) {
/*  69 */     super((PdfObject)pdfObject);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PdfMediaClipData(String file, PdfFileSpec fs, String mimeType) {
/*  80 */     this(new PdfDictionary());
/*  81 */     PdfDictionary dic = new PdfDictionary();
/*  82 */     markObjectAsIndirect((PdfObject)dic);
/*  83 */     dic.put(PdfName.TF, (PdfObject)TEMPACCESS);
/*  84 */     ((PdfDictionary)getPdfObject()).put(PdfName.Type, (PdfObject)PdfName.MediaClip);
/*  85 */     ((PdfDictionary)getPdfObject()).put(PdfName.S, (PdfObject)PdfName.MCD);
/*  86 */     ((PdfDictionary)getPdfObject()).put(PdfName.N, (PdfObject)new PdfString(MessageFormatUtil.format("Media clip for {0}", new Object[] { file })));
/*  87 */     ((PdfDictionary)getPdfObject()).put(PdfName.CT, (PdfObject)new PdfString(mimeType));
/*  88 */     ((PdfDictionary)getPdfObject()).put(PdfName.P, (PdfObject)dic);
/*  89 */     ((PdfDictionary)getPdfObject()).put(PdfName.D, fs.getPdfObject());
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
/*     */   public void flush() {
/* 101 */     super.flush();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean isWrappedObjectMustBeIndirect() {
/* 109 */     return true;
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/kernel/pdf/action/PdfMediaClipData.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */