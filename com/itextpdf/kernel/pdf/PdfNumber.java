/*     */ package com.itextpdf.kernel.pdf;
/*     */ 
/*     */ import com.itextpdf.io.source.ByteUtils;
/*     */ import java.nio.charset.StandardCharsets;
/*     */ import org.slf4j.Logger;
/*     */ import org.slf4j.LoggerFactory;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class PdfNumber
/*     */   extends PdfPrimitiveObject
/*     */ {
/*     */   private static final long serialVersionUID = -250799718574024246L;
/*     */   private double value;
/*     */   private boolean isDouble;
/*     */   private boolean changed = false;
/*     */   
/*     */   public PdfNumber(double value) {
/*  63 */     setValue(value);
/*     */   }
/*     */ 
/*     */   
/*     */   public PdfNumber(int value) {
/*  68 */     setValue(value);
/*     */   }
/*     */   
/*     */   public PdfNumber(byte[] content) {
/*  72 */     super(content);
/*  73 */     this.isDouble = true;
/*  74 */     this.value = Double.NaN;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private PdfNumber() {}
/*     */ 
/*     */   
/*     */   public byte getType() {
/*  83 */     return 8;
/*     */   }
/*     */   
/*     */   public double getValue() {
/*  87 */     if (Double.isNaN(this.value))
/*  88 */       generateValue(); 
/*  89 */     return this.value;
/*     */   }
/*     */   
/*     */   public double doubleValue() {
/*  93 */     return getValue();
/*     */   }
/*     */   
/*     */   public float floatValue() {
/*  97 */     return (float)getValue();
/*     */   }
/*     */   
/*     */   public long longValue() {
/* 101 */     return (long)getValue();
/*     */   }
/*     */   
/*     */   public int intValue() {
/* 105 */     return (int)getValue();
/*     */   }
/*     */   
/*     */   public void setValue(int value) {
/* 109 */     this.value = value;
/* 110 */     this.isDouble = false;
/* 111 */     this.content = null;
/* 112 */     this.changed = true;
/*     */   }
/*     */   
/*     */   public void setValue(double value) {
/* 116 */     this.value = value;
/* 117 */     this.isDouble = true;
/* 118 */     this.content = null;
/*     */   }
/*     */   
/*     */   public void increment() {
/* 122 */     setValue(++this.value);
/*     */   }
/*     */   
/*     */   public void decrement() {
/* 126 */     setValue(--this.value);
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 131 */     if (this.content != null)
/* 132 */       return new String(this.content, StandardCharsets.ISO_8859_1); 
/* 133 */     if (this.isDouble) {
/* 134 */       return new String(ByteUtils.getIsoBytes(getValue()), StandardCharsets.ISO_8859_1);
/*     */     }
/* 136 */     return new String(ByteUtils.getIsoBytes(intValue()), StandardCharsets.ISO_8859_1);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean equals(Object o) {
/* 142 */     return (this == o || (o != null && 
/* 143 */       getClass() == o.getClass() && Double.compare(((PdfNumber)o).value, this.value) == 0));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean hasDecimalPoint() {
/* 151 */     return toString().contains(".");
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 156 */     if (this.changed) {
/*     */       
/* 158 */       Logger logger = LoggerFactory.getLogger(PdfReader.class);
/* 159 */       logger.warn("Calculate hashcode for modified PdfNumber.");
/* 160 */       this.changed = false;
/*     */     } 
/* 162 */     long hash = Double.doubleToLongBits(this.value);
/* 163 */     return (int)(hash ^ hash >>> 32L);
/*     */   }
/*     */ 
/*     */   
/*     */   protected PdfObject newInstance() {
/* 168 */     return new PdfNumber();
/*     */   }
/*     */   
/*     */   protected boolean isDoubleNumber() {
/* 172 */     return this.isDouble;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void generateContent() {
/* 177 */     if (this.isDouble) {
/* 178 */       this.content = ByteUtils.getIsoBytes(this.value);
/*     */     } else {
/* 180 */       this.content = ByteUtils.getIsoBytes((int)this.value);
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void generateValue() {
/*     */     try {
/* 186 */       this.value = Double.parseDouble(new String(this.content, StandardCharsets.ISO_8859_1));
/* 187 */     } catch (NumberFormatException e) {
/* 188 */       this.value = Double.NaN;
/*     */     } 
/* 190 */     this.isDouble = true;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void copyContent(PdfObject from, PdfDocument document) {
/* 195 */     super.copyContent(from, document);
/* 196 */     PdfNumber number = (PdfNumber)from;
/* 197 */     this.value = number.value;
/* 198 */     this.isDouble = number.isDouble;
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/kernel/pdf/PdfNumber.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */