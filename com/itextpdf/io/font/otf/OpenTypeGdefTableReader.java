/*     */ package com.itextpdf.io.font.otf;
/*     */ 
/*     */ import com.itextpdf.io.source.RandomAccessFileOrArray;
/*     */ import java.io.IOException;
/*     */ import java.io.Serializable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class OpenTypeGdefTableReader
/*     */   implements Serializable
/*     */ {
/*     */   static final int FLAG_IGNORE_BASE = 2;
/*     */   static final int FLAG_IGNORE_LIGATURE = 4;
/*     */   static final int FLAG_IGNORE_MARK = 8;
/*     */   private static final long serialVersionUID = 1564505797329158035L;
/*     */   private final int tableLocation;
/*     */   private final RandomAccessFileOrArray rf;
/*     */   private OtfClass glyphClass;
/*     */   private OtfClass markAttachmentClass;
/*     */   
/*     */   public OpenTypeGdefTableReader(RandomAccessFileOrArray rf, int tableLocation) {
/*  64 */     this.rf = rf;
/*  65 */     this.tableLocation = tableLocation;
/*     */   }
/*     */   
/*     */   public void readTable() throws IOException {
/*  69 */     if (this.tableLocation > 0) {
/*  70 */       this.rf.seek(this.tableLocation);
/*     */       
/*  72 */       this.rf.readUnsignedInt();
/*  73 */       int glyphClassDefOffset = this.rf.readUnsignedShort();
/*     */       
/*  75 */       this.rf.readUnsignedShort();
/*     */       
/*  77 */       this.rf.readUnsignedShort();
/*  78 */       int markAttachClassDefOffset = this.rf.readUnsignedShort();
/*  79 */       if (glyphClassDefOffset > 0) {
/*  80 */         this.glyphClass = OtfClass.create(this.rf, glyphClassDefOffset + this.tableLocation);
/*     */       }
/*  82 */       if (markAttachClassDefOffset > 0) {
/*  83 */         this.markAttachmentClass = OtfClass.create(this.rf, markAttachClassDefOffset + this.tableLocation);
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   public boolean isSkip(int glyph, int flag) {
/*  89 */     if (this.glyphClass != null && (flag & 0xE) != 0) {
/*  90 */       int cla = this.glyphClass.getOtfClass(glyph);
/*  91 */       if (cla == 1 && (flag & 0x2) != 0) {
/*  92 */         return true;
/*     */       }
/*  94 */       if (cla == 3 && (flag & 0x8) != 0) {
/*  95 */         return true;
/*     */       }
/*  97 */       if (cla == 2 && (flag & 0x4) != 0) {
/*  98 */         return true;
/*     */       }
/*     */     } 
/* 101 */     int markAttachmentType = flag >> 8;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 106 */     if (markAttachmentType != 0 && this.glyphClass != null) {
/* 107 */       int currentGlyphClass = this.glyphClass.getOtfClass(glyph);
/*     */       
/* 109 */       int glyphMarkAttachmentClass = (this.markAttachmentClass != null) ? this.markAttachmentClass.getOtfClass(glyph) : 0;
/* 110 */       return (currentGlyphClass == 3 && glyphMarkAttachmentClass != markAttachmentType);
/*     */     } 
/* 112 */     return false;
/*     */   }
/*     */   
/*     */   public OtfClass getGlyphClassTable() {
/* 116 */     return this.glyphClass;
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/io/font/otf/OpenTypeGdefTableReader.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */