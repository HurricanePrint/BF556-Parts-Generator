/*     */ package com.itextpdf.kernel.pdf.xobject;
/*     */ 
/*     */ import com.itextpdf.io.image.ImageData;
/*     */ import com.itextpdf.kernel.PdfException;
/*     */ import com.itextpdf.kernel.geom.Matrix;
/*     */ import com.itextpdf.kernel.geom.Rectangle;
/*     */ import com.itextpdf.kernel.geom.Vector;
/*     */ import com.itextpdf.kernel.pdf.PdfArray;
/*     */ import com.itextpdf.kernel.pdf.PdfDictionary;
/*     */ import com.itextpdf.kernel.pdf.PdfDocument;
/*     */ import com.itextpdf.kernel.pdf.PdfName;
/*     */ import com.itextpdf.kernel.pdf.PdfObject;
/*     */ import com.itextpdf.kernel.pdf.PdfPage;
/*     */ import com.itextpdf.kernel.pdf.PdfResources;
/*     */ import com.itextpdf.kernel.pdf.PdfStream;
/*     */ import com.itextpdf.kernel.pdf.PdfString;
/*     */ import com.itextpdf.kernel.pdf.canvas.wmf.WmfImageData;
/*     */ import com.itextpdf.kernel.pdf.canvas.wmf.WmfImageHelper;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class PdfFormXObject
/*     */   extends PdfXObject
/*     */ {
/*     */   private static final long serialVersionUID = 467500482711722178L;
/*  68 */   protected PdfResources resources = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PdfFormXObject(Rectangle bBox) {
/*  76 */     super(new PdfStream());
/*  77 */     ((PdfStream)getPdfObject()).put(PdfName.Type, (PdfObject)PdfName.XObject);
/*  78 */     ((PdfStream)getPdfObject()).put(PdfName.Subtype, (PdfObject)PdfName.Form);
/*  79 */     if (bBox != null) {
/*  80 */       ((PdfStream)getPdfObject()).put(PdfName.BBox, (PdfObject)new PdfArray(bBox));
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
/*     */   public PdfFormXObject(PdfStream pdfStream) {
/*  92 */     super(pdfStream);
/*  93 */     if (!((PdfStream)getPdfObject()).containsKey(PdfName.Subtype)) {
/*  94 */       ((PdfStream)getPdfObject()).put(PdfName.Subtype, (PdfObject)PdfName.Form);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PdfFormXObject(PdfPage page) {
/* 105 */     this(page.getCropBox());
/* 106 */     ((PdfStream)getPdfObject()).getOutputStream().writeBytes(page.getContentBytes());
/* 107 */     this.resources = new PdfResources((PdfDictionary)((PdfDictionary)page.getResources().getPdfObject()).clone());
/* 108 */     ((PdfStream)getPdfObject()).put(PdfName.Resources, this.resources.getPdfObject());
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
/*     */   public PdfFormXObject(WmfImageData image, PdfDocument pdfDocument) {
/* 120 */     this((PdfStream)(new WmfImageHelper((ImageData)image)).createFormXObject(pdfDocument).getPdfObject());
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
/*     */   public static Rectangle calculateBBoxMultipliedByMatrix(PdfFormXObject form) {
/*     */     float[] matrixArray;
/* 133 */     PdfArray pdfArrayBBox = ((PdfStream)form.getPdfObject()).getAsArray(PdfName.BBox);
/* 134 */     if (pdfArrayBBox == null) {
/* 135 */       throw new PdfException("PdfFormXObject has invalid BBox.");
/*     */     }
/* 137 */     float[] bBoxArray = pdfArrayBBox.toFloatArray();
/* 138 */     PdfArray pdfArrayMatrix = ((PdfStream)form.getPdfObject()).getAsArray(PdfName.Matrix);
/*     */     
/* 140 */     if (pdfArrayMatrix == null) {
/* 141 */       matrixArray = new float[] { 1.0F, 0.0F, 0.0F, 1.0F, 0.0F, 0.0F };
/*     */     } else {
/* 143 */       matrixArray = pdfArrayMatrix.toFloatArray();
/*     */     } 
/* 145 */     Matrix matrix = new Matrix(matrixArray[0], matrixArray[1], matrixArray[2], matrixArray[3], matrixArray[4], matrixArray[5]);
/* 146 */     Vector bBoxMin = new Vector(bBoxArray[0], bBoxArray[1], 1.0F);
/* 147 */     Vector bBoxMax = new Vector(bBoxArray[2], bBoxArray[3], 1.0F);
/*     */     
/* 149 */     Vector bBoxMinByMatrix = bBoxMin.cross(matrix);
/* 150 */     Vector bBoxMaxByMatrix = bBoxMax.cross(matrix);
/* 151 */     float width = bBoxMaxByMatrix.get(0) - bBoxMinByMatrix.get(0);
/* 152 */     float height = bBoxMaxByMatrix.get(1) - bBoxMinByMatrix.get(1);
/*     */     
/* 154 */     return new Rectangle(bBoxMinByMatrix.get(0), bBoxMinByMatrix.get(1), width, height);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PdfResources getResources() {
/* 164 */     if (this.resources == null) {
/* 165 */       PdfDictionary resourcesDict = ((PdfStream)getPdfObject()).getAsDictionary(PdfName.Resources);
/* 166 */       if (resourcesDict == null) {
/* 167 */         resourcesDict = new PdfDictionary();
/* 168 */         ((PdfStream)getPdfObject()).put(PdfName.Resources, (PdfObject)resourcesDict);
/*     */       } 
/* 170 */       this.resources = new PdfResources(resourcesDict);
/*     */     } 
/* 172 */     return this.resources;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PdfArray getBBox() {
/* 181 */     return ((PdfStream)getPdfObject()).getAsArray(PdfName.BBox);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PdfFormXObject setBBox(PdfArray bBox) {
/* 191 */     return put(PdfName.BBox, (PdfObject)bBox);
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
/*     */   public PdfFormXObject setGroup(PdfTransparencyGroup transparency) {
/* 204 */     return put(PdfName.Group, transparency.getPdfObject());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getWidth() {
/* 214 */     return (getBBox() == null) ? 0.0F : (getBBox().getAsNumber(2).floatValue() - getBBox().getAsNumber(0).floatValue());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getHeight() {
/* 224 */     return (getBBox() == null) ? 0.0F : (getBBox().getAsNumber(3).floatValue() - getBBox().getAsNumber(1).floatValue());
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
/*     */   public void flush() {
/* 236 */     this.resources = null;
/* 237 */     if (((PdfStream)getPdfObject()).get(PdfName.BBox) == null) {
/* 238 */       throw new PdfException("Form XObject must have BBox.");
/*     */     }
/* 240 */     super.flush();
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
/*     */   public PdfFormXObject setProcessColorModel(PdfName model) {
/* 254 */     return put(PdfName.PCM, (PdfObject)model);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PdfName getProcessColorModel() {
/* 265 */     return ((PdfStream)getPdfObject()).getAsName(PdfName.PCM);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PdfFormXObject setSeparationColorNames(PdfArray colorNames) {
/* 276 */     return put(PdfName.SeparationColorNames, (PdfObject)colorNames);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PdfArray getSeparationColorNames() {
/* 285 */     return ((PdfStream)getPdfObject()).getAsArray(PdfName.SeparationColorNames);
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
/*     */   public PdfFormXObject setTrapRegions(PdfArray regions) {
/* 297 */     return put(PdfName.TrapRegions, (PdfObject)regions);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PdfArray getTrapRegions() {
/* 308 */     return ((PdfStream)getPdfObject()).getAsArray(PdfName.TrapRegions);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PdfFormXObject setTrapStyles(PdfString trapStyles) {
/* 319 */     return put(PdfName.TrapStyles, (PdfObject)trapStyles);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PdfString getTrapStyles() {
/* 329 */     return ((PdfStream)getPdfObject()).getAsString(PdfName.TrapStyles);
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
/*     */   public PdfFormXObject setMarkStyle(PdfString markStyle) {
/* 341 */     return put(PdfName.MarkStyle, (PdfObject)markStyle);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PdfString getMarkStyle() {
/* 350 */     return ((PdfStream)getPdfObject()).getAsString(PdfName.MarkStyle);
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
/*     */   public PdfFormXObject put(PdfName key, PdfObject value) {
/* 362 */     ((PdfStream)getPdfObject()).put(key, value);
/* 363 */     setModified();
/* 364 */     return this;
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/kernel/pdf/xobject/PdfFormXObject.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */