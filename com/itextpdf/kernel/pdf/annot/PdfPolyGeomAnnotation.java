/*     */ package com.itextpdf.kernel.pdf.annot;
/*     */ 
/*     */ import com.itextpdf.kernel.colors.Color;
/*     */ import com.itextpdf.kernel.geom.Rectangle;
/*     */ import com.itextpdf.kernel.pdf.PdfArray;
/*     */ import com.itextpdf.kernel.pdf.PdfDictionary;
/*     */ import com.itextpdf.kernel.pdf.PdfName;
/*     */ import com.itextpdf.kernel.pdf.PdfObject;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class PdfPolyGeomAnnotation
/*     */   extends PdfMarkupAnnotation
/*     */ {
/*     */   private static final long serialVersionUID = -9038993253308315792L;
/*     */   @Deprecated
/*  66 */   public static final PdfName Polygon = PdfName.Polygon;
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*  71 */   public static final PdfName PolyLine = PdfName.PolyLine;
/*     */   
/*     */   PdfPolyGeomAnnotation(Rectangle rect, float[] vertices) {
/*  74 */     super(rect);
/*  75 */     setVertices(vertices);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected PdfPolyGeomAnnotation(PdfDictionary pdfObject) {
/*  86 */     super(pdfObject);
/*     */   }
/*     */   
/*     */   public static PdfPolyGeomAnnotation createPolygon(Rectangle rect, float[] vertices) {
/*  90 */     return new PdfPolygonAnnotation(rect, vertices);
/*     */   }
/*     */   
/*     */   public static PdfPolyGeomAnnotation createPolyLine(Rectangle rect, float[] vertices) {
/*  94 */     return new PdfPolylineAnnotation(rect, vertices);
/*     */   }
/*     */   
/*     */   public PdfArray getVertices() {
/*  98 */     return ((PdfDictionary)getPdfObject()).getAsArray(PdfName.Vertices);
/*     */   }
/*     */   
/*     */   public PdfPolyGeomAnnotation setVertices(PdfArray vertices) {
/* 102 */     if (((PdfDictionary)getPdfObject()).containsKey(PdfName.Path)) {
/* 103 */       LoggerFactory.getLogger(getClass()).warn("Path key is present. Vertices will be ignored");
/*     */     }
/* 105 */     return (PdfPolyGeomAnnotation)put(PdfName.Vertices, (PdfObject)vertices);
/*     */   }
/*     */   
/*     */   public PdfPolyGeomAnnotation setVertices(float[] vertices) {
/* 109 */     if (((PdfDictionary)getPdfObject()).containsKey(PdfName.Path)) {
/* 110 */       LoggerFactory.getLogger(getClass()).warn("Path key is present. Vertices will be ignored");
/*     */     }
/* 112 */     return (PdfPolyGeomAnnotation)put(PdfName.Vertices, (PdfObject)new PdfArray(vertices));
/*     */   }
/*     */   
/*     */   public PdfArray getLineEndingStyles() {
/* 116 */     return ((PdfDictionary)getPdfObject()).getAsArray(PdfName.LE);
/*     */   }
/*     */   
/*     */   public PdfPolyGeomAnnotation setLineEndingStyles(PdfArray lineEndingStyles) {
/* 120 */     return (PdfPolyGeomAnnotation)put(PdfName.LE, (PdfObject)lineEndingStyles);
/*     */   }
/*     */   
/*     */   public PdfDictionary getMeasure() {
/* 124 */     return ((PdfDictionary)getPdfObject()).getAsDictionary(PdfName.Measure);
/*     */   }
/*     */   
/*     */   public PdfPolyGeomAnnotation setMeasure(PdfDictionary measure) {
/* 128 */     return (PdfPolyGeomAnnotation)put(PdfName.Measure, (PdfObject)measure);
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
/*     */ 
/*     */   
/*     */   public PdfArray getPath() {
/* 145 */     return ((PdfDictionary)getPdfObject()).getAsArray(PdfName.Path);
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
/*     */ 
/*     */ 
/*     */   
/*     */   public PdfPolyGeomAnnotation setPath(PdfArray path) {
/* 163 */     if (((PdfDictionary)getPdfObject()).containsKey(PdfName.Vertices)) {
/* 164 */       LoggerFactory.getLogger(getClass()).error("If Path key is set, Vertices key shall not be present. Remove Vertices key before setting Path");
/*     */     }
/* 166 */     return (PdfPolyGeomAnnotation)put(PdfName.Path, (PdfObject)path);
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
/*     */   public PdfDictionary getBorderStyle() {
/* 178 */     return ((PdfDictionary)getPdfObject()).getAsDictionary(PdfName.BS);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PdfPolyGeomAnnotation setBorderStyle(PdfDictionary borderStyle) {
/* 189 */     return (PdfPolyGeomAnnotation)put(PdfName.BS, (PdfObject)borderStyle);
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
/*     */ 
/*     */ 
/*     */   
/*     */   public PdfPolyGeomAnnotation setBorderStyle(PdfName style) {
/* 207 */     return setBorderStyle(BorderStyleUtil.setStyle(getBorderStyle(), style));
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
/*     */   public PdfPolyGeomAnnotation setDashPattern(PdfArray dashPattern) {
/* 219 */     return setBorderStyle(BorderStyleUtil.setDashPattern(getBorderStyle(), dashPattern));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PdfDictionary getBorderEffect() {
/* 227 */     return ((PdfDictionary)getPdfObject()).getAsDictionary(PdfName.BE);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PdfPolyGeomAnnotation setBorderEffect(PdfDictionary borderEffect) {
/* 237 */     return (PdfPolyGeomAnnotation)put(PdfName.BE, (PdfObject)borderEffect);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Color getInteriorColor() {
/* 247 */     return InteriorColorUtil.parseInteriorColor(((PdfDictionary)getPdfObject()).getAsArray(PdfName.IC));
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
/*     */   public PdfPolyGeomAnnotation setInteriorColor(PdfArray interiorColor) {
/* 260 */     return (PdfPolyGeomAnnotation)put(PdfName.IC, (PdfObject)interiorColor);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PdfPolyGeomAnnotation setInteriorColor(float[] interiorColor) {
/* 271 */     return setInteriorColor(new PdfArray(interiorColor));
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/kernel/pdf/annot/PdfPolyGeomAnnotation.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */