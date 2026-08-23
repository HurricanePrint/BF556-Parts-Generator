/*     */ package com.itextpdf.kernel.pdf;
/*     */ 
/*     */ import com.itextpdf.io.source.ByteUtils;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class PdfNull
/*     */   extends PdfPrimitiveObject
/*     */ {
/*     */   private static final long serialVersionUID = 7789114018630038033L;
/*  55 */   public static final PdfNull PDF_NULL = new PdfNull(true);
/*  56 */   private static final byte[] NullContent = ByteUtils.getIsoBytes("null");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PdfNull() {}
/*     */ 
/*     */ 
/*     */   
/*     */   private PdfNull(boolean directOnly) {
/*  66 */     super(directOnly);
/*     */   }
/*     */ 
/*     */   
/*     */   public byte getType() {
/*  71 */     return 7;
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/*  76 */     return "null";
/*     */   }
/*     */ 
/*     */   
/*     */   protected void generateContent() {
/*  81 */     this.content = NullContent;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected PdfObject newInstance() {
/*  87 */     return new PdfNull();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void copyContent(PdfObject from, PdfDocument document) {}
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/*  97 */     return (this == obj || (obj != null && getClass() == obj.getClass()));
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 102 */     return 0;
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/kernel/pdf/PdfNull.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */