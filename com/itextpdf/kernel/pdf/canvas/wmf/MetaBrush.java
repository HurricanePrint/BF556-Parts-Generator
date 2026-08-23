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
/*     */ 
/*     */ public class MetaBrush
/*     */   extends MetaObject
/*     */ {
/*     */   public static final int BS_SOLID = 0;
/*     */   public static final int BS_NULL = 1;
/*     */   public static final int BS_HATCHED = 2;
/*     */   public static final int BS_PATTERN = 3;
/*     */   public static final int BS_DIBPATTERN = 5;
/*     */   public static final int HS_HORIZONTAL = 0;
/*     */   public static final int HS_VERTICAL = 1;
/*     */   public static final int HS_FDIAGONAL = 2;
/*     */   public static final int HS_BDIAGONAL = 3;
/*     */   public static final int HS_CROSS = 4;
/*     */   public static final int HS_DIAGCROSS = 5;
/*  69 */   int style = 0;
/*     */   int hatch;
/*  71 */   Color color = ColorConstants.WHITE;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public MetaBrush() {
/*  77 */     super(2);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void init(InputMeta in) throws IOException {
/*  87 */     this.style = in.readWord();
/*  88 */     this.color = in.readColor();
/*  89 */     this.hatch = in.readWord();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getStyle() {
/*  98 */     return this.style;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getHatch() {
/* 106 */     return this.hatch;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Color getColor() {
/* 114 */     return this.color;
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/kernel/pdf/canvas/wmf/MetaBrush.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */