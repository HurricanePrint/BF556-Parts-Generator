/*     */ package com.itextpdf.kernel.pdf.canvas.parser.data;
/*     */ 
/*     */ import com.itextpdf.kernel.colors.Color;
/*     */ import com.itextpdf.kernel.geom.Matrix;
/*     */ import com.itextpdf.kernel.geom.Path;
/*     */ import com.itextpdf.kernel.pdf.PdfArray;
/*     */ import com.itextpdf.kernel.pdf.canvas.CanvasGraphicsState;
/*     */ import com.itextpdf.kernel.pdf.canvas.CanvasTag;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ import java.util.Stack;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class PathRenderInfo
/*     */   extends AbstractRenderInfo
/*     */ {
/*     */   public static final int NO_OP = 0;
/*     */   public static final int STROKE = 1;
/*     */   public static final int FILL = 2;
/*     */   private Path path;
/*     */   private int operation;
/*     */   private int rule;
/*     */   private boolean isClip;
/*     */   private int clippingRule;
/*     */   private List<CanvasTag> canvasTagHierarchy;
/*     */   
/*     */   public PathRenderInfo(Stack<CanvasTag> canvasTagHierarchy, CanvasGraphicsState gs, Path path, int operation, int rule, boolean isClip, int clipRule) {
/* 101 */     super(gs);
/* 102 */     this.canvasTagHierarchy = Collections.unmodifiableList(new ArrayList<>(canvasTagHierarchy));
/* 103 */     this.path = path;
/* 104 */     this.operation = operation;
/* 105 */     this.rule = rule;
/* 106 */     this.isClip = isClip;
/* 107 */     this.clippingRule = clipRule;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PathRenderInfo(Stack<CanvasTag> canvasTagHierarchy, CanvasGraphicsState gs, Path path, int operation) {
/* 118 */     this(canvasTagHierarchy, gs, path, operation, 1, false, 1);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Path getPath() {
/* 125 */     return this.path;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getOperation() {
/* 133 */     return this.operation;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getRule() {
/* 140 */     return this.rule;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isPathModifiesClippingPath() {
/* 147 */     return this.isClip;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getClippingRule() {
/* 154 */     return this.clippingRule;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Matrix getCtm() {
/* 161 */     checkGraphicsState();
/* 162 */     return this.gs.getCtm();
/*     */   }
/*     */   
/*     */   public float getLineWidth() {
/* 166 */     checkGraphicsState();
/* 167 */     return this.gs.getLineWidth();
/*     */   }
/*     */   
/*     */   public int getLineCapStyle() {
/* 171 */     checkGraphicsState();
/* 172 */     return this.gs.getLineCapStyle();
/*     */   }
/*     */   
/*     */   public int getLineJoinStyle() {
/* 176 */     checkGraphicsState();
/* 177 */     return this.gs.getLineJoinStyle();
/*     */   }
/*     */   
/*     */   public float getMiterLimit() {
/* 181 */     checkGraphicsState();
/* 182 */     return this.gs.getMiterLimit();
/*     */   }
/*     */   
/*     */   public PdfArray getLineDashPattern() {
/* 186 */     checkGraphicsState();
/* 187 */     return this.gs.getDashPattern();
/*     */   }
/*     */   
/*     */   public Color getStrokeColor() {
/* 191 */     checkGraphicsState();
/* 192 */     return this.gs.getStrokeColor();
/*     */   }
/*     */   
/*     */   public Color getFillColor() {
/* 196 */     checkGraphicsState();
/* 197 */     return this.gs.getFillColor();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List<CanvasTag> getCanvasTagHierarchy() {
/* 206 */     return this.canvasTagHierarchy;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMcid() {
/* 215 */     for (CanvasTag tag : this.canvasTagHierarchy) {
/* 216 */       if (tag.hasMcid()) {
/* 217 */         return tag.getMcid();
/*     */       }
/*     */     } 
/* 220 */     return -1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean hasMcid(int mcid) {
/* 231 */     return hasMcid(mcid, false);
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
/*     */   public boolean hasMcid(int mcid, boolean checkTheTopmostLevelOnly) {
/* 243 */     if (checkTheTopmostLevelOnly) {
/* 244 */       if (this.canvasTagHierarchy != null) {
/* 245 */         int infoMcid = getMcid();
/* 246 */         return (infoMcid != -1 && infoMcid == mcid);
/*     */       } 
/*     */     } else {
/* 249 */       for (CanvasTag tag : this.canvasTagHierarchy) {
/* 250 */         if (tag.hasMcid() && 
/* 251 */           tag.getMcid() == mcid)
/* 252 */           return true; 
/*     */       } 
/*     */     } 
/* 255 */     return false;
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/kernel/pdf/canvas/parser/data/PathRenderInfo.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */