/*     */ package com.itextpdf.kernel.pdf.canvas.parser.data;
/*     */ 
/*     */ import com.itextpdf.kernel.geom.Matrix;
/*     */ import com.itextpdf.kernel.geom.Vector;
/*     */ import com.itextpdf.kernel.pdf.PdfDictionary;
/*     */ import com.itextpdf.kernel.pdf.PdfName;
/*     */ import com.itextpdf.kernel.pdf.PdfStream;
/*     */ import com.itextpdf.kernel.pdf.canvas.CanvasGraphicsState;
/*     */ import com.itextpdf.kernel.pdf.canvas.CanvasTag;
/*     */ import com.itextpdf.kernel.pdf.xobject.PdfImageXObject;
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
/*     */ public class ImageRenderInfo
/*     */   extends AbstractRenderInfo
/*     */ {
/*     */   private Matrix ctm;
/*     */   private PdfImageXObject image;
/*     */   private PdfDictionary colorSpaceDictionary;
/*     */   private boolean isInline;
/*     */   private PdfName resourceName;
/*     */   private List<CanvasTag> canvasTagHierarchy;
/*     */   
/*     */   public ImageRenderInfo(Stack<CanvasTag> canvasTagHierarchy, CanvasGraphicsState gs, Matrix ctm, PdfStream imageStream, PdfName resourceName, PdfDictionary colorSpaceDictionary, boolean isInline) {
/*  94 */     super(gs);
/*  95 */     this.canvasTagHierarchy = Collections.unmodifiableList(new ArrayList<>(canvasTagHierarchy));
/*  96 */     this.resourceName = resourceName;
/*  97 */     this.ctm = ctm;
/*  98 */     this.image = new PdfImageXObject(imageStream);
/*  99 */     this.colorSpaceDictionary = colorSpaceDictionary;
/* 100 */     this.isInline = isInline;
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
/*     */   public PdfImageXObject getImage() {
/* 114 */     return this.image;
/*     */   }
/*     */   
/*     */   public PdfName getImageResourceName() {
/* 118 */     return this.resourceName;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Vector getStartPoint() {
/* 125 */     return (new Vector(0.0F, 0.0F, 1.0F)).cross(this.ctm);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Matrix getImageCtm() {
/* 132 */     return this.ctm;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getArea() {
/* 140 */     return this.ctm.getDeterminant();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isInline() {
/* 147 */     return this.isInline;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PdfDictionary getColorSpaceDictionary() {
/* 154 */     return this.colorSpaceDictionary;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List<CanvasTag> getCanvasTagHierarchy() {
/* 163 */     return this.canvasTagHierarchy;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMcid() {
/* 172 */     for (CanvasTag tag : this.canvasTagHierarchy) {
/* 173 */       if (tag.hasMcid()) {
/* 174 */         return tag.getMcid();
/*     */       }
/*     */     } 
/* 177 */     return -1;
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
/* 188 */     return hasMcid(mcid, false);
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
/* 200 */     if (checkTheTopmostLevelOnly) {
/* 201 */       if (this.canvasTagHierarchy != null) {
/* 202 */         int infoMcid = getMcid();
/* 203 */         return (infoMcid != -1 && infoMcid == mcid);
/*     */       } 
/*     */     } else {
/* 206 */       for (CanvasTag tag : this.canvasTagHierarchy) {
/* 207 */         if (tag.hasMcid() && 
/* 208 */           tag.getMcid() == mcid)
/* 209 */           return true; 
/*     */       } 
/*     */     } 
/* 212 */     return false;
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/kernel/pdf/canvas/parser/data/ImageRenderInfo.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */