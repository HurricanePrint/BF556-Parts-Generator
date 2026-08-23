/*     */ package com.itextpdf.kernel.pdf.annot;
/*     */ 
/*     */ import com.itextpdf.kernel.geom.Rectangle;
/*     */ import com.itextpdf.kernel.pdf.PdfArray;
/*     */ import com.itextpdf.kernel.pdf.PdfDictionary;
/*     */ import com.itextpdf.kernel.pdf.PdfName;
/*     */ import com.itextpdf.kernel.pdf.PdfObject;
/*     */ import com.itextpdf.kernel.pdf.action.PdfAction;
/*     */ import com.itextpdf.kernel.pdf.navigation.PdfDestination;
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
/*     */ public class PdfLinkAnnotation
/*     */   extends PdfAnnotation
/*     */ {
/*     */   private static final long serialVersionUID = 5795613340575331536L;
/*  61 */   private static final Logger logger = LoggerFactory.getLogger(PdfLinkAnnotation.class);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  66 */   public static final PdfName None = PdfName.N;
/*  67 */   public static final PdfName Invert = PdfName.I;
/*  68 */   public static final PdfName Outline = PdfName.O;
/*  69 */   public static final PdfName Push = PdfName.P;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected PdfLinkAnnotation(PdfDictionary pdfObject) {
/*  79 */     super(pdfObject);
/*     */   }
/*     */   
/*     */   public PdfLinkAnnotation(Rectangle rect) {
/*  83 */     super(rect);
/*     */   }
/*     */   
/*     */   public PdfName getSubtype() {
/*  87 */     return PdfName.Link;
/*     */   }
/*     */   
/*     */   public PdfObject getDestinationObject() {
/*  91 */     return ((PdfDictionary)getPdfObject()).get(PdfName.Dest);
/*     */   }
/*     */   
/*     */   public PdfLinkAnnotation setDestination(PdfObject destination) {
/*  95 */     if (((PdfDictionary)getPdfObject()).containsKey(PdfName.A)) {
/*  96 */       ((PdfDictionary)getPdfObject()).remove(PdfName.A);
/*  97 */       logger.warn("Destinations are not permitted for link annotations that already have actions. The old action will be removed.");
/*     */     } 
/*  99 */     if (destination.isArray() && ((PdfArray)destination).get(0).isNumber())
/* 100 */       LoggerFactory.getLogger(PdfLinkAnnotation.class).warn("When destination's not associated with a Remote or Embedded Go-To action, it shall specify page dictionary instead of page number. Otherwise destination might be considered invalid"); 
/* 101 */     return (PdfLinkAnnotation)put(PdfName.Dest, destination);
/*     */   }
/*     */   
/*     */   public PdfLinkAnnotation setDestination(PdfDestination destination) {
/* 105 */     return setDestination(destination.getPdfObject());
/*     */   }
/*     */   
/*     */   public PdfLinkAnnotation removeDestination() {
/* 109 */     ((PdfDictionary)getPdfObject()).remove(PdfName.Dest);
/* 110 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PdfDictionary getAction() {
/* 119 */     return ((PdfDictionary)getPdfObject()).getAsDictionary(PdfName.A);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PdfLinkAnnotation setAction(PdfDictionary action) {
/* 129 */     return (PdfLinkAnnotation)put(PdfName.A, (PdfObject)action);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PdfLinkAnnotation setAction(PdfAction action) {
/* 138 */     if (getDestinationObject() != null) {
/* 139 */       removeDestination();
/* 140 */       logger.warn("Action was set for a link annotation containing destination. The old destination will be cleared.");
/*     */     } 
/* 142 */     return (PdfLinkAnnotation)put(PdfName.A, action.getPdfObject());
/*     */   }
/*     */   
/*     */   public PdfLinkAnnotation removeAction() {
/* 146 */     ((PdfDictionary)getPdfObject()).remove(PdfName.A);
/* 147 */     return this;
/*     */   }
/*     */   
/*     */   public PdfName getHighlightMode() {
/* 151 */     return ((PdfDictionary)getPdfObject()).getAsName(PdfName.H);
/*     */   }
/*     */   
/*     */   public PdfLinkAnnotation setHighlightMode(PdfName hlMode) {
/* 155 */     return (PdfLinkAnnotation)put(PdfName.H, (PdfObject)hlMode);
/*     */   }
/*     */   
/*     */   public PdfDictionary getUriActionObject() {
/* 159 */     return ((PdfDictionary)getPdfObject()).getAsDictionary(PdfName.PA);
/*     */   }
/*     */   
/*     */   public PdfLinkAnnotation setUriAction(PdfDictionary action) {
/* 163 */     return (PdfLinkAnnotation)put(PdfName.PA, (PdfObject)action);
/*     */   }
/*     */   
/*     */   public PdfLinkAnnotation setUriAction(PdfAction action) {
/* 167 */     return (PdfLinkAnnotation)put(PdfName.PA, action.getPdfObject());
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
/*     */   public PdfArray getQuadPoints() {
/* 179 */     return ((PdfDictionary)getPdfObject()).getAsArray(PdfName.QuadPoints);
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
/*     */   public PdfLinkAnnotation setQuadPoints(PdfArray quadPoints) {
/* 191 */     return (PdfLinkAnnotation)put(PdfName.QuadPoints, (PdfObject)quadPoints);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PdfDictionary getBorderStyle() {
/* 202 */     return ((PdfDictionary)getPdfObject()).getAsDictionary(PdfName.BS);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PdfLinkAnnotation setBorderStyle(PdfDictionary borderStyle) {
/* 213 */     return (PdfLinkAnnotation)put(PdfName.BS, (PdfObject)borderStyle);
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
/*     */   public PdfLinkAnnotation setBorderStyle(PdfName style) {
/* 231 */     return setBorderStyle(BorderStyleUtil.setStyle(getBorderStyle(), style));
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
/*     */   public PdfLinkAnnotation setDashPattern(PdfArray dashPattern) {
/* 243 */     return setBorderStyle(BorderStyleUtil.setDashPattern(getBorderStyle(), dashPattern));
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/kernel/pdf/annot/PdfLinkAnnotation.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */