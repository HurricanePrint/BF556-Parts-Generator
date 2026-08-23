/*     */ package com.itextpdf.kernel.pdf;
/*     */ 
/*     */ import com.itextpdf.io.font.PdfEncodings;
/*     */ import java.nio.charset.StandardCharsets;
/*     */ import java.util.Arrays;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class PdfLiteral
/*     */   extends PdfPrimitiveObject
/*     */ {
/*     */   private static final long serialVersionUID = -770215611509192403L;
/*     */   private long position;
/*     */   
/*     */   public PdfLiteral(byte[] content) {
/*  58 */     super(true);
/*  59 */     this.content = content;
/*     */   }
/*     */   
/*     */   public PdfLiteral(int size) {
/*  63 */     this(new byte[size]);
/*  64 */     Arrays.fill(this.content, (byte)32);
/*     */   }
/*     */   
/*     */   public PdfLiteral(String content) {
/*  68 */     this(PdfEncodings.convertToBytes(content, null));
/*     */   }
/*     */   
/*     */   private PdfLiteral() {
/*  72 */     this((byte[])null);
/*     */   }
/*     */ 
/*     */   
/*     */   public byte getType() {
/*  77 */     return 4;
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/*  82 */     if (this.content != null) {
/*  83 */       return new String(this.content, StandardCharsets.ISO_8859_1);
/*     */     }
/*  85 */     return "";
/*     */   }
/*     */ 
/*     */   
/*     */   public long getPosition() {
/*  90 */     return this.position;
/*     */   }
/*     */   
/*     */   public void setPosition(long position) {
/*  94 */     this.position = position;
/*     */   }
/*     */   
/*     */   public int getBytesCount() {
/*  98 */     return this.content.length;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void generateContent() {}
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean equals(Object o) {
/* 108 */     return (this == o || (o != null && 
/* 109 */       getClass() == o.getClass() && Arrays.equals(this.content, ((PdfLiteral)o).content)));
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 114 */     return (this.content == null) ? 0 : Arrays.hashCode(this.content);
/*     */   }
/*     */ 
/*     */   
/*     */   protected PdfObject newInstance() {
/* 119 */     return new PdfLiteral();
/*     */   }
/*     */ 
/*     */   
/*     */   protected void copyContent(PdfObject from, PdfDocument document) {
/* 124 */     super.copyContent(from, document);
/* 125 */     PdfLiteral literal = (PdfLiteral)from;
/* 126 */     this.content = literal.getInternalContent();
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/kernel/pdf/PdfLiteral.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */