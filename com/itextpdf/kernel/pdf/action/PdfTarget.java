/*     */ package com.itextpdf.kernel.pdf.action;
/*     */ 
/*     */ import com.itextpdf.kernel.PdfException;
/*     */ import com.itextpdf.kernel.pdf.PdfArray;
/*     */ import com.itextpdf.kernel.pdf.PdfDictionary;
/*     */ import com.itextpdf.kernel.pdf.PdfDocument;
/*     */ import com.itextpdf.kernel.pdf.PdfName;
/*     */ import com.itextpdf.kernel.pdf.PdfNameTree;
/*     */ import com.itextpdf.kernel.pdf.PdfNumber;
/*     */ import com.itextpdf.kernel.pdf.PdfObject;
/*     */ import com.itextpdf.kernel.pdf.PdfObjectWrapper;
/*     */ import com.itextpdf.kernel.pdf.PdfPage;
/*     */ import com.itextpdf.kernel.pdf.PdfString;
/*     */ import com.itextpdf.kernel.pdf.annot.PdfAnnotation;
/*     */ import com.itextpdf.kernel.pdf.annot.PdfFileAttachmentAnnotation;
/*     */ import java.util.List;
/*     */ import java.util.Map;
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
/*     */ public class PdfTarget
/*     */   extends PdfObjectWrapper<PdfDictionary>
/*     */ {
/*     */   private static final long serialVersionUID = -5814265943827690509L;
/*     */   
/*     */   private PdfTarget(PdfDictionary pdfObject) {
/*  77 */     super((PdfObject)pdfObject);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static PdfTarget create(PdfDictionary pdfObject) {
/*  87 */     return new PdfTarget(pdfObject);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static PdfTarget create(PdfName r) {
/*  98 */     PdfTarget pdfTarget = new PdfTarget(new PdfDictionary());
/*  99 */     pdfTarget.put(PdfName.R, (PdfObject)r);
/* 100 */     return pdfTarget;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static PdfTarget createParentTarget() {
/* 109 */     return create(PdfName.P);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static PdfTarget createChildTarget() {
/* 118 */     return create(PdfName.C);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static PdfTarget createChildTarget(String embeddedFileName) {
/* 128 */     return create(PdfName.C)
/* 129 */       .put(PdfName.N, (PdfObject)new PdfString(embeddedFileName));
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
/*     */   public static PdfTarget createChildTarget(String namedDestination, String annotationIdentifier) {
/* 141 */     return create(PdfName.C)
/* 142 */       .put(PdfName.P, (PdfObject)new PdfString(namedDestination))
/* 143 */       .put(PdfName.A, (PdfObject)new PdfString(annotationIdentifier));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static PdfTarget createChildTarget(int pageNumber, int annotationIndex) {
/* 154 */     return create(PdfName.C)
/* 155 */       .put(PdfName.P, (PdfObject)new PdfNumber(pageNumber - 1))
/* 156 */       .put(PdfName.A, (PdfObject)new PdfNumber(annotationIndex));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PdfTarget setName(String name) {
/* 167 */     return put(PdfName.N, (PdfObject)new PdfString(name));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getName() {
/* 177 */     return ((PdfDictionary)getPdfObject()).getAsString(PdfName.N).toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PdfTarget setAnnotation(PdfFileAttachmentAnnotation pdfAnnotation, PdfDocument pdfDocument) {
/* 188 */     PdfPage page = pdfAnnotation.getPage();
/* 189 */     if (null == page) {
/* 190 */       throw new PdfException("Annotation shall have reference to page.");
/*     */     }
/* 192 */     put(PdfName.P, (PdfObject)new PdfNumber(pdfDocument.getPageNumber(page) - 1));
/* 193 */     int indexOfAnnotation = -1;
/* 194 */     List<PdfAnnotation> annots = page.getAnnotations();
/* 195 */     for (int i = 0; i < annots.size(); i++) {
/* 196 */       if (annots.get(i) != null && ((PdfDictionary)pdfAnnotation
/* 197 */         .getPdfObject()).equals(((PdfAnnotation)annots.get(i)).getPdfObject())) {
/* 198 */         indexOfAnnotation = i;
/*     */         break;
/*     */       } 
/*     */     } 
/* 202 */     put(PdfName.A, (PdfObject)new PdfNumber(indexOfAnnotation));
/*     */     
/* 204 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PdfFileAttachmentAnnotation getAnnotation(PdfDocument pdfDocument) {
/* 214 */     PdfObject pValue = ((PdfDictionary)getPdfObject()).get(PdfName.P);
/* 215 */     PdfPage page = null;
/* 216 */     if (pValue instanceof PdfNumber) {
/*     */       
/* 218 */       page = pdfDocument.getPage(((PdfNumber)pValue).intValue() + 1);
/* 219 */     } else if (pValue instanceof PdfString) {
/* 220 */       PdfNameTree destsTree = pdfDocument.getCatalog().getNameTree(PdfName.Dests);
/* 221 */       Map<String, PdfObject> dests = destsTree.getNames();
/* 222 */       PdfArray pdfArray = (PdfArray)dests.get(((PdfString)pValue).getValue());
/* 223 */       if (null != pdfArray) {
/* 224 */         if (pdfArray.get(0) instanceof PdfNumber) {
/* 225 */           page = pdfDocument.getPage(((PdfNumber)pdfArray.get(0)).intValue());
/*     */         } else {
/* 227 */           page = pdfDocument.getPage((PdfDictionary)pdfArray.get(0));
/*     */         } 
/*     */       }
/*     */     } 
/*     */     
/* 232 */     List<PdfAnnotation> pageAnnotations = null;
/* 233 */     if (null != page) {
/* 234 */       pageAnnotations = page.getAnnotations();
/*     */     }
/* 236 */     PdfObject aValue = ((PdfDictionary)getPdfObject()).get(PdfName.A);
/* 237 */     PdfFileAttachmentAnnotation resultAnnotation = null;
/* 238 */     if (null != pageAnnotations) {
/* 239 */       if (aValue instanceof PdfNumber) {
/* 240 */         resultAnnotation = (PdfFileAttachmentAnnotation)pageAnnotations.get(((PdfNumber)aValue).intValue());
/* 241 */       } else if (aValue instanceof PdfString) {
/* 242 */         for (PdfAnnotation annotation : pageAnnotations) {
/* 243 */           if (aValue.equals(annotation.getName())) {
/* 244 */             resultAnnotation = (PdfFileAttachmentAnnotation)annotation;
/*     */             break;
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     }
/* 250 */     if (null == resultAnnotation) {
/* 251 */       Logger logger = LoggerFactory.getLogger(PdfTarget.class);
/* 252 */       logger.error("Some fields in target dictionary are not set or incorrect. Null will be returned.");
/*     */     } 
/* 254 */     return resultAnnotation;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PdfTarget setTarget(PdfTarget target) {
/* 265 */     return put(PdfName.T, target.getPdfObject());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PdfTarget getTarget() {
/* 275 */     PdfDictionary targetDictObject = ((PdfDictionary)getPdfObject()).getAsDictionary(PdfName.T);
/* 276 */     return (targetDictObject != null) ? new PdfTarget(targetDictObject) : null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PdfTarget put(PdfName key, PdfObject value) {
/* 287 */     ((PdfDictionary)getPdfObject()).put(key, value);
/* 288 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean isWrappedObjectMustBeIndirect() {
/* 296 */     return false;
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/kernel/pdf/action/PdfTarget.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */