/*     */ package com.itextpdf.kernel.geom;
/*     */ 
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
/*     */ public class PageSize
/*     */   extends Rectangle
/*     */   implements Cloneable, Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 485375591249386160L;
/*  52 */   public static PageSize A0 = new PageSize(2384.0F, 3370.0F);
/*  53 */   public static PageSize A1 = new PageSize(1684.0F, 2384.0F);
/*  54 */   public static PageSize A2 = new PageSize(1190.0F, 1684.0F);
/*  55 */   public static PageSize A3 = new PageSize(842.0F, 1190.0F);
/*  56 */   public static PageSize A4 = new PageSize(595.0F, 842.0F);
/*  57 */   public static PageSize A5 = new PageSize(420.0F, 595.0F);
/*  58 */   public static PageSize A6 = new PageSize(298.0F, 420.0F);
/*  59 */   public static PageSize A7 = new PageSize(210.0F, 298.0F);
/*  60 */   public static PageSize A8 = new PageSize(148.0F, 210.0F);
/*  61 */   public static PageSize A9 = new PageSize(105.0F, 547.0F);
/*  62 */   public static PageSize A10 = new PageSize(74.0F, 105.0F);
/*     */   
/*  64 */   public static PageSize B0 = new PageSize(2834.0F, 4008.0F);
/*  65 */   public static PageSize B1 = new PageSize(2004.0F, 2834.0F);
/*  66 */   public static PageSize B2 = new PageSize(1417.0F, 2004.0F);
/*  67 */   public static PageSize B3 = new PageSize(1000.0F, 1417.0F);
/*  68 */   public static PageSize B4 = new PageSize(708.0F, 1000.0F);
/*  69 */   public static PageSize B5 = new PageSize(498.0F, 708.0F);
/*  70 */   public static PageSize B6 = new PageSize(354.0F, 498.0F);
/*  71 */   public static PageSize B7 = new PageSize(249.0F, 354.0F);
/*  72 */   public static PageSize B8 = new PageSize(175.0F, 249.0F);
/*  73 */   public static PageSize B9 = new PageSize(124.0F, 175.0F);
/*  74 */   public static PageSize B10 = new PageSize(88.0F, 124.0F);
/*     */   
/*  76 */   public static PageSize LETTER = new PageSize(612.0F, 792.0F);
/*  77 */   public static PageSize LEGAL = new PageSize(612.0F, 1008.0F);
/*  78 */   public static PageSize TABLOID = new PageSize(792.0F, 1224.0F);
/*  79 */   public static PageSize LEDGER = new PageSize(1224.0F, 792.0F);
/*  80 */   public static PageSize EXECUTIVE = new PageSize(522.0F, 756.0F);
/*     */   
/*  82 */   public static PageSize Default = A4;
/*     */   
/*     */   public PageSize(float width, float height) {
/*  85 */     super(0.0F, 0.0F, width, height);
/*     */   }
/*     */ 
/*     */   
/*     */   public PageSize(Rectangle box) {
/*  90 */     super(box.getX(), box.getY(), box.getWidth(), box.getHeight());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PageSize rotate() {
/*  99 */     return new PageSize(this.height, this.width);
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
/*     */   public Rectangle clone() {
/* 113 */     return super.clone();
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/kernel/geom/PageSize.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */