/*     */ package com.itextpdf.kernel.pdf.xobject;
/*     */ 
/*     */ import com.itextpdf.kernel.geom.Rectangle;
/*     */ import com.itextpdf.kernel.pdf.PdfArray;
/*     */ import com.itextpdf.kernel.pdf.PdfDictionary;
/*     */ import com.itextpdf.kernel.pdf.PdfName;
/*     */ import com.itextpdf.kernel.pdf.PdfObject;
/*     */ import com.itextpdf.kernel.pdf.PdfObjectWrapper;
/*     */ import com.itextpdf.kernel.pdf.PdfStream;
/*     */ import com.itextpdf.kernel.pdf.filespec.PdfFileSpec;
/*     */ import com.itextpdf.kernel.pdf.layer.IPdfOCG;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class PdfXObject
/*     */   extends PdfObjectWrapper<PdfStream>
/*     */ {
/*     */   private static final long serialVersionUID = -480702872582809954L;
/*     */   
/*     */   protected PdfXObject(PdfStream pdfObject) {
/*  72 */     super((PdfObject)pdfObject);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static PdfXObject makeXObject(PdfStream stream) {
/*  83 */     if (PdfName.Form.equals(stream.getAsName(PdfName.Subtype)))
/*  84 */       return new PdfFormXObject(stream); 
/*  85 */     if (PdfName.Image.equals(stream.getAsName(PdfName.Subtype))) {
/*  86 */       return new PdfImageXObject(stream);
/*     */     }
/*  88 */     throw new UnsupportedOperationException("Unsupported XObject type.");
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
/*     */   
/*     */   public static Rectangle calculateProportionallyFitRectangleWithWidth(PdfXObject xObject, float x, float y, float width) {
/* 107 */     if (xObject instanceof PdfFormXObject) {
/* 108 */       PdfFormXObject formXObject = (PdfFormXObject)xObject;
/* 109 */       Rectangle bBox = PdfFormXObject.calculateBBoxMultipliedByMatrix(formXObject);
/* 110 */       return new Rectangle(x, y, width, width / bBox.getWidth() * bBox.getHeight());
/* 111 */     }  if (xObject instanceof PdfImageXObject) {
/* 112 */       PdfImageXObject imageXObject = (PdfImageXObject)xObject;
/* 113 */       return new Rectangle(x, y, width, width / imageXObject.getWidth() * imageXObject.getHeight());
/*     */     } 
/* 115 */     throw new IllegalArgumentException("PdfFormXObject or PdfImageXObject expected.");
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
/*     */   
/*     */   public static Rectangle calculateProportionallyFitRectangleWithHeight(PdfXObject xObject, float x, float y, float height) {
/* 134 */     if (xObject instanceof PdfFormXObject) {
/* 135 */       PdfFormXObject formXObject = (PdfFormXObject)xObject;
/* 136 */       Rectangle bBox = PdfFormXObject.calculateBBoxMultipliedByMatrix(formXObject);
/* 137 */       return new Rectangle(x, y, height / bBox.getHeight() * bBox.getWidth(), height);
/* 138 */     }  if (xObject instanceof PdfImageXObject) {
/* 139 */       PdfImageXObject imageXObject = (PdfImageXObject)xObject;
/* 140 */       return new Rectangle(x, y, height / imageXObject.getHeight() * imageXObject.getWidth(), height);
/*     */     } 
/* 142 */     throw new IllegalArgumentException("PdfFormXObject or PdfImageXObject expected.");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setLayer(IPdfOCG layer) {
/* 152 */     ((PdfStream)getPdfObject()).put(PdfName.OC, (PdfObject)layer.getIndirectReference());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getWidth() {
/* 161 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getHeight() {
/* 170 */     throw new UnsupportedOperationException();
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
/*     */   public void addAssociatedFile(PdfFileSpec fs) {
/* 183 */     if (null == ((PdfDictionary)fs.getPdfObject()).get(PdfName.AFRelationship)) {
/* 184 */       Logger logger = LoggerFactory.getLogger(PdfXObject.class);
/* 185 */       logger.error("For associated files their associated file specification dictionaries shall include the AFRelationship key.");
/*     */     } 
/* 187 */     PdfArray afArray = ((PdfStream)getPdfObject()).getAsArray(PdfName.AF);
/* 188 */     if (afArray == null) {
/* 189 */       afArray = new PdfArray();
/* 190 */       ((PdfStream)getPdfObject()).put(PdfName.AF, (PdfObject)afArray);
/*     */     } 
/* 192 */     afArray.add(fs.getPdfObject());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PdfArray getAssociatedFiles(boolean create) {
/* 202 */     PdfArray afArray = ((PdfStream)getPdfObject()).getAsArray(PdfName.AF);
/* 203 */     if (afArray == null && create) {
/* 204 */       afArray = new PdfArray();
/* 205 */       ((PdfStream)getPdfObject()).put(PdfName.AF, (PdfObject)afArray);
/*     */     } 
/* 207 */     return afArray;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean isWrappedObjectMustBeIndirect() {
/* 216 */     return true;
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/kernel/pdf/xobject/PdfXObject.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */