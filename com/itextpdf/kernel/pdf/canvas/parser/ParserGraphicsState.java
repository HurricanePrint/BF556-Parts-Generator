/*     */ package com.itextpdf.kernel.pdf.canvas.parser;
/*     */ 
/*     */ import com.itextpdf.kernel.geom.Matrix;
/*     */ import com.itextpdf.kernel.geom.Path;
/*     */ import com.itextpdf.kernel.geom.ShapeTransformUtil;
/*     */ import com.itextpdf.kernel.pdf.canvas.CanvasGraphicsState;
/*     */ import com.itextpdf.kernel.pdf.canvas.parser.clipper.ClipperBridge;
/*     */ import com.itextpdf.kernel.pdf.canvas.parser.clipper.DefaultClipper;
/*     */ import com.itextpdf.kernel.pdf.canvas.parser.clipper.IClipper;
/*     */ import com.itextpdf.kernel.pdf.canvas.parser.clipper.PolyTree;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ParserGraphicsState
/*     */   extends CanvasGraphicsState
/*     */ {
/*     */   private static final long serialVersionUID = 5402909016194922120L;
/*     */   private Path clippingPath;
/*     */   
/*     */   ParserGraphicsState() {}
/*     */   
/*     */   ParserGraphicsState(ParserGraphicsState source) {
/*  79 */     super(source);
/*     */     
/*  81 */     if (source.clippingPath != null) {
/*  82 */       this.clippingPath = new Path(source.clippingPath);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void updateCtm(Matrix newCtm) {
/*  88 */     super.updateCtm(newCtm);
/*  89 */     if (this.clippingPath != null) {
/*  90 */       transformClippingPath(newCtm);
/*     */     }
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
/*     */   
/*     */   public void clip(Path path, int fillingRule) {
/* 106 */     if (this.clippingPath == null || this.clippingPath.isEmpty()) {
/*     */       return;
/*     */     }
/*     */     
/* 110 */     Path pathCopy = new Path(path);
/* 111 */     pathCopy.closeAllSubpaths();
/*     */     
/* 113 */     DefaultClipper defaultClipper = new DefaultClipper();
/* 114 */     ClipperBridge.addPath((IClipper)defaultClipper, this.clippingPath, IClipper.PolyType.SUBJECT);
/* 115 */     ClipperBridge.addPath((IClipper)defaultClipper, pathCopy, IClipper.PolyType.CLIP);
/*     */     
/* 117 */     PolyTree resultTree = new PolyTree();
/* 118 */     defaultClipper.execute(IClipper.ClipType.INTERSECTION, resultTree, IClipper.PolyFillType.NON_ZERO, ClipperBridge.getFillType(fillingRule));
/*     */     
/* 120 */     this.clippingPath = ClipperBridge.convertToPath(resultTree);
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
/*     */   public Path getClippingPath() {
/* 132 */     return this.clippingPath;
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
/*     */   public void setClippingPath(Path clippingPath) {
/* 144 */     Path pathCopy = new Path(clippingPath);
/* 145 */     pathCopy.closeAllSubpaths();
/* 146 */     this.clippingPath = pathCopy;
/*     */   }
/*     */   
/*     */   private void transformClippingPath(Matrix newCtm) {
/* 150 */     this.clippingPath = ShapeTransformUtil.transformPath(this.clippingPath, newCtm);
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/kernel/pdf/canvas/parser/ParserGraphicsState.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */