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
/*     */ public class PdfBoolean
/*     */   extends PdfPrimitiveObject
/*     */ {
/*     */   private static final long serialVersionUID = -1363839858135046832L;
/*  52 */   public static final PdfBoolean TRUE = new PdfBoolean(true, true);
/*  53 */   public static final PdfBoolean FALSE = new PdfBoolean(false, true);
/*     */   
/*  55 */   private static final byte[] True = ByteUtils.getIsoBytes("true");
/*  56 */   private static final byte[] False = ByteUtils.getIsoBytes("false");
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean value;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PdfBoolean(boolean value) {
/*  66 */     this(value, false);
/*     */   }
/*     */   
/*     */   private PdfBoolean(boolean value, boolean directOnly) {
/*  70 */     super(directOnly);
/*  71 */     this.value = value;
/*     */   }
/*     */ 
/*     */   
/*     */   private PdfBoolean() {}
/*     */ 
/*     */   
/*     */   public boolean getValue() {
/*  79 */     return this.value;
/*     */   }
/*     */   
/*     */   public byte getType() {
/*  83 */     return 2;
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/*  88 */     return this.value ? "true" : "false";
/*     */   }
/*     */ 
/*     */   
/*     */   protected void generateContent() {
/*  93 */     this.content = this.value ? True : False;
/*     */   }
/*     */ 
/*     */   
/*     */   protected PdfObject newInstance() {
/*  98 */     return new PdfBoolean();
/*     */   }
/*     */ 
/*     */   
/*     */   protected void copyContent(PdfObject from, PdfDocument document) {
/* 103 */     super.copyContent(from, document);
/* 104 */     PdfBoolean bool = (PdfBoolean)from;
/* 105 */     this.value = bool.value;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/* 110 */     return (this == obj || (obj != null && 
/* 111 */       getClass() == obj.getClass() && this.value == ((PdfBoolean)obj).value));
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 116 */     return this.value ? 1 : 0;
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
/*     */   public static PdfBoolean valueOf(boolean value) {
/* 128 */     return value ? TRUE : FALSE;
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/kernel/pdf/PdfBoolean.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */