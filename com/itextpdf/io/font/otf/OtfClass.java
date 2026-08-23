/*     */ package com.itextpdf.io.font.otf;
/*     */ 
/*     */ import com.itextpdf.io.source.RandomAccessFileOrArray;
/*     */ import com.itextpdf.io.util.IntHashtable;
/*     */ import com.itextpdf.io.util.MessageFormatUtil;
/*     */ import java.io.IOException;
/*     */ import java.io.Serializable;
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
/*     */ public class OtfClass
/*     */   implements Serializable
/*     */ {
/*     */   public static final int GLYPH_BASE = 1;
/*     */   public static final int GLYPH_LIGATURE = 2;
/*     */   public static final int GLYPH_MARK = 3;
/*     */   private static final long serialVersionUID = -7584495836452964728L;
/*  64 */   private IntHashtable mapClass = new IntHashtable();
/*     */   
/*     */   private OtfClass(RandomAccessFileOrArray rf, int classLocation) throws IOException {
/*  67 */     rf.seek(classLocation);
/*  68 */     int classFormat = rf.readUnsignedShort();
/*  69 */     if (classFormat == 1) {
/*  70 */       int startGlyph = rf.readUnsignedShort();
/*  71 */       int glyphCount = rf.readUnsignedShort();
/*  72 */       int endGlyph = startGlyph + glyphCount;
/*  73 */       for (int k = startGlyph; k < endGlyph; k++) {
/*  74 */         int cl = rf.readUnsignedShort();
/*  75 */         this.mapClass.put(k, cl);
/*     */       } 
/*  77 */     } else if (classFormat == 2) {
/*  78 */       int classRangeCount = rf.readUnsignedShort();
/*  79 */       for (int k = 0; k < classRangeCount; k++) {
/*  80 */         int glyphStart = rf.readUnsignedShort();
/*  81 */         int glyphEnd = rf.readUnsignedShort();
/*  82 */         int cl = rf.readUnsignedShort();
/*  83 */         for (; glyphStart <= glyphEnd; glyphStart++) {
/*  84 */           this.mapClass.put(glyphStart, cl);
/*     */         }
/*     */       } 
/*     */     } else {
/*  88 */       throw new IOException("Invalid class format " + classFormat);
/*     */     } 
/*     */   }
/*     */   
/*     */   public static OtfClass create(RandomAccessFileOrArray rf, int classLocation) {
/*     */     OtfClass otfClass;
/*     */     try {
/*  95 */       otfClass = new OtfClass(rf, classLocation);
/*  96 */     } catch (IOException e) {
/*  97 */       Logger logger = LoggerFactory.getLogger(OtfClass.class);
/*  98 */       logger.error(MessageFormatUtil.format("OpenType GDEF table error: {0}", new Object[] { e.getMessage() }));
/*  99 */       otfClass = null;
/*     */     } 
/* 101 */     return otfClass;
/*     */   }
/*     */   
/*     */   public int getOtfClass(int glyph) {
/* 105 */     return this.mapClass.get(glyph);
/*     */   }
/*     */   
/*     */   public boolean isMarkOtfClass(int glyph) {
/* 109 */     return (hasClass(glyph) && getOtfClass(glyph) == 3);
/*     */   }
/*     */   
/*     */   public boolean hasClass(int glyph) {
/* 113 */     return this.mapClass.containsKey(glyph);
/*     */   }
/*     */   
/*     */   public int getOtfClass(int glyph, boolean strict) {
/* 117 */     if (strict) {
/* 118 */       if (this.mapClass.containsKey(glyph)) {
/* 119 */         return this.mapClass.get(glyph);
/*     */       }
/* 121 */       return -1;
/*     */     } 
/*     */     
/* 124 */     return this.mapClass.get(glyph);
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/io/font/otf/OtfClass.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */