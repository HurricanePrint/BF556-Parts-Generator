/*     */ package com.itextpdf.kernel.pdf.canvas.wmf;
/*     */ 
/*     */ import com.itextpdf.kernel.colors.Color;
/*     */ import com.itextpdf.kernel.colors.ColorConstants;
/*     */ import java.io.IOException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class MetaPen
/*     */   extends MetaObject
/*     */ {
/*     */   public static final int PS_SOLID = 0;
/*     */   public static final int PS_DASH = 1;
/*     */   public static final int PS_DOT = 2;
/*     */   public static final int PS_DASHDOT = 3;
/*     */   public static final int PS_DASHDOTDOT = 4;
/*     */   public static final int PS_NULL = 5;
/*     */   public static final int PS_INSIDEFRAME = 6;
/*  64 */   int style = 0;
/*  65 */   int penWidth = 1;
/*  66 */   Color color = ColorConstants.BLACK;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public MetaPen() {
/*  72 */     super(1);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void init(InputMeta in) throws IOException {
/*  82 */     this.style = in.readWord();
/*  83 */     this.penWidth = in.readShort();
/*  84 */     in.readWord();
/*  85 */     this.color = in.readColor();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getStyle() {
/*  94 */     return this.style;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getPenWidth() {
/* 103 */     return this.penWidth;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Color getColor() {
/* 112 */     return this.color;
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/kernel/pdf/canvas/wmf/MetaPen.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */