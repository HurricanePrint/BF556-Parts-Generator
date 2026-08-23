/*     */ package com.itextpdf.svg.renderers.path.impl;
/*     */ 
/*     */ import com.itextpdf.kernel.geom.Point;
/*     */ import com.itextpdf.kernel.geom.Rectangle;
/*     */ import com.itextpdf.styledxmlparser.css.util.CssUtils;
/*     */ import com.itextpdf.svg.renderers.path.IPathShape;
/*     */ import java.util.Map;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class AbstractPathShape
/*     */   implements IPathShape
/*     */ {
/*     */   protected Map<String, String> properties;
/*     */   protected boolean relative;
/*     */   protected final IOperatorConverter copier;
/*     */   protected String[] coordinates;
/*     */   
/*     */   public AbstractPathShape() {
/*  71 */     this(false);
/*     */   }
/*     */   
/*     */   public AbstractPathShape(boolean relative) {
/*  75 */     this(relative, new DefaultOperatorConverter());
/*     */   }
/*     */   
/*     */   public AbstractPathShape(boolean relative, IOperatorConverter copier) {
/*  79 */     this.relative = relative;
/*  80 */     this.copier = copier;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isRelative() {
/*  85 */     return this.relative;
/*     */   }
/*     */   
/*     */   protected Point createPoint(String coordX, String coordY) {
/*  89 */     return new Point(CssUtils.parseDouble(coordX).doubleValue(), CssUtils.parseDouble(coordY).doubleValue());
/*     */   }
/*     */ 
/*     */   
/*     */   public Point getEndingPoint() {
/*  94 */     return createPoint(this.coordinates[this.coordinates.length - 2], this.coordinates[this.coordinates.length - 1]);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Rectangle getPathShapeRectangle(Point lastPoint) {
/* 105 */     return new Rectangle((float)CssUtils.convertPxToPts(getEndingPoint().getX()), 
/* 106 */         (float)CssUtils.convertPxToPts(getEndingPoint().getY()), 0.0F, 0.0F);
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/svg/renderers/path/impl/AbstractPathShape.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */