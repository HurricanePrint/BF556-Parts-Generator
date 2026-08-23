/*     */ package com.itextpdf.forms.xfdf;
/*     */ 
/*     */ import com.itextpdf.forms.PdfAcroForm;
/*     */ import com.itextpdf.forms.fields.PdfFormField;
/*     */ import com.itextpdf.io.util.MessageFormatUtil;
/*     */ import com.itextpdf.kernel.geom.Rectangle;
/*     */ import com.itextpdf.kernel.pdf.PdfDocument;
/*     */ import com.itextpdf.kernel.pdf.PdfName;
/*     */ import com.itextpdf.kernel.pdf.PdfString;
/*     */ import com.itextpdf.kernel.pdf.annot.PdfAnnotation;
/*     */ import com.itextpdf.kernel.pdf.annot.PdfCircleAnnotation;
/*     */ import com.itextpdf.kernel.pdf.annot.PdfFreeTextAnnotation;
/*     */ import com.itextpdf.kernel.pdf.annot.PdfMarkupAnnotation;
/*     */ import com.itextpdf.kernel.pdf.annot.PdfPolyGeomAnnotation;
/*     */ import com.itextpdf.kernel.pdf.annot.PdfSquareAnnotation;
/*     */ import com.itextpdf.kernel.pdf.annot.PdfStampAnnotation;
/*     */ import com.itextpdf.kernel.pdf.annot.PdfTextAnnotation;
/*     */ import com.itextpdf.kernel.pdf.annot.PdfTextMarkupAnnotation;
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
/*     */ class XfdfReader
/*     */ {
/*  70 */   private static Logger logger = LoggerFactory.getLogger(XfdfReader.class);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void mergeXfdfIntoPdf(XfdfObject xfdfObject, PdfDocument pdfDocument, String pdfDocumentName) {
/*  80 */     if (xfdfObject.getF() != null && xfdfObject.getF().getHref() != null) {
/*  81 */       if (pdfDocumentName.equalsIgnoreCase(xfdfObject.getF().getHref())) {
/*  82 */         logger.info("Xfdf href and pdf name are equal. Continue merge");
/*     */       } else {
/*  84 */         logger.warn("Xfdf href attribute and pdf document name are different!");
/*     */       } 
/*     */     } else {
/*  87 */       logger.warn("Xfdf no f object to compare.");
/*     */     } 
/*     */ 
/*     */     
/*  91 */     PdfAcroForm form = PdfAcroForm.getAcroForm(pdfDocument, false);
/*  92 */     if (form != null) {
/*  93 */       mergeFields(xfdfObject.getFields(), form);
/*  94 */       mergeAnnotations(xfdfObject.getAnnots(), pdfDocument);
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
/*     */   private void mergeFields(FieldsObject fieldsObject, PdfAcroForm form) {
/* 106 */     if (fieldsObject != null && fieldsObject.getFieldList() != null && !fieldsObject.getFieldList().isEmpty()) {
/*     */       
/* 108 */       Map<String, PdfFormField> formFields = form.getFormFields();
/*     */       
/* 110 */       for (FieldObject xfdfField : fieldsObject.getFieldList()) {
/* 111 */         String name = xfdfField.getName();
/* 112 */         if (formFields.get(name) != null && xfdfField.getValue() != null) {
/* 113 */           ((PdfFormField)formFields.get(name)).setValue(xfdfField.getValue()); continue;
/*     */         } 
/* 115 */         logger.error("Xfdf no such field in pdf document!");
/*     */       } 
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
/*     */   private void mergeAnnotations(AnnotsObject annotsObject, PdfDocument pdfDocument) {
/* 128 */     List<AnnotObject> annotList = null;
/* 129 */     if (annotsObject != null) {
/* 130 */       annotList = annotsObject.getAnnotsList();
/*     */     }
/*     */     
/* 133 */     if (annotList != null && !annotList.isEmpty()) {
/* 134 */       for (AnnotObject annot : annotList) {
/* 135 */         addAnnotationToPdf(annot, pdfDocument);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   private void addCommonAnnotationAttributes(PdfAnnotation annotation, AnnotObject annotObject) {
/* 141 */     annotation.setFlags(XfdfObjectUtils.convertFlagsFromString(annotObject.getAttributeValue("flags")));
/* 142 */     annotation.setColor(XfdfObjectUtils.convertColorFloatsFromString(annotObject.getAttributeValue("color")));
/* 143 */     annotation.setDate(new PdfString(annotObject.getAttributeValue("date")));
/* 144 */     annotation.setName(new PdfString(annotObject.getAttributeValue("name")));
/* 145 */     annotation.setTitle(new PdfString(annotObject.getAttributeValue("title")));
/*     */   }
/*     */   
/*     */   private void addMarkupAnnotationAttributes(PdfMarkupAnnotation annotation, AnnotObject annotObject) {
/* 149 */     annotation.setCreationDate(new PdfString(annotObject.getAttributeValue("creationdate")));
/* 150 */     annotation.setSubject(new PdfString(annotObject.getAttributeValue("subject")));
/*     */   }
/*     */   
/*     */   private void addAnnotationToPdf(AnnotObject annotObject, PdfDocument pdfDocument) {
/* 154 */     String annotName = annotObject.getName();
/* 155 */     if (annotName != null) {
/* 156 */       PdfTextAnnotation pdfTextAnnotation; PdfTextMarkupAnnotation pdfHighLightAnnotation, pdfUnderlineAnnotation, pdfStrikeoutAnnotation, pdfSquigglyAnnotation; PdfCircleAnnotation pdfCircleAnnotation; PdfSquareAnnotation pdfSquareAnnotation; Rectangle rect; float[] vertices; PdfPolyGeomAnnotation polygonAnnotation; Rectangle polylineRect; float[] polylineVertices; PdfPolyGeomAnnotation polylineAnnotation; switch (annotName) {
/*     */         
/*     */         case "text":
/* 159 */           pdfTextAnnotation = new PdfTextAnnotation(XfdfObjectUtils.convertRectFromString(annotObject.getAttributeValue("rect")));
/* 160 */           addCommonAnnotationAttributes((PdfAnnotation)pdfTextAnnotation, annotObject);
/* 161 */           addMarkupAnnotationAttributes((PdfMarkupAnnotation)pdfTextAnnotation, annotObject);
/*     */           
/* 163 */           pdfTextAnnotation.setIconName(new PdfName(annotObject.getAttributeValue("icon")));
/* 164 */           if (annotObject.getAttributeValue("state") != null) {
/* 165 */             pdfTextAnnotation.setState(new PdfString(annotObject.getAttributeValue("state")));
/*     */           }
/* 167 */           if (annotObject.getAttributeValue("statemodel") != null) {
/* 168 */             pdfTextAnnotation.setStateModel(new PdfString(annotObject.getAttributeValue("statemodel")));
/*     */           }
/*     */           
/* 171 */           pdfDocument.getPage(Integer.parseInt(annotObject.getAttributeValue("page")))
/* 172 */             .addAnnotation((PdfAnnotation)pdfTextAnnotation);
/*     */           return;
/*     */         
/*     */         case "highlight":
/* 176 */           pdfHighLightAnnotation = new PdfTextMarkupAnnotation(XfdfObjectUtils.convertRectFromString(annotObject.getAttributeValue("rect")), PdfName.Highlight, XfdfObjectUtils.convertQuadPointsFromCoordsString(annotObject.getAttributeValue("coords")));
/*     */           
/* 178 */           addCommonAnnotationAttributes((PdfAnnotation)pdfHighLightAnnotation, annotObject);
/* 179 */           addMarkupAnnotationAttributes((PdfMarkupAnnotation)pdfHighLightAnnotation, annotObject);
/*     */           
/* 181 */           pdfDocument.getPage(Integer.parseInt(annotObject.getAttribute("page").getValue()))
/* 182 */             .addAnnotation((PdfAnnotation)pdfHighLightAnnotation);
/*     */           return;
/*     */         
/*     */         case "underline":
/* 186 */           pdfUnderlineAnnotation = new PdfTextMarkupAnnotation(XfdfObjectUtils.convertRectFromString(annotObject.getAttributeValue("rect")), PdfName.Underline, XfdfObjectUtils.convertQuadPointsFromCoordsString(annotObject.getAttributeValue("coords")));
/*     */           
/* 188 */           addCommonAnnotationAttributes((PdfAnnotation)pdfUnderlineAnnotation, annotObject);
/* 189 */           addMarkupAnnotationAttributes((PdfMarkupAnnotation)pdfUnderlineAnnotation, annotObject);
/*     */           
/* 191 */           pdfDocument.getPage(Integer.parseInt(annotObject.getAttribute("page").getValue()))
/* 192 */             .addAnnotation((PdfAnnotation)pdfUnderlineAnnotation);
/*     */           return;
/*     */         
/*     */         case "strikeout":
/* 196 */           pdfStrikeoutAnnotation = new PdfTextMarkupAnnotation(XfdfObjectUtils.convertRectFromString(annotObject.getAttributeValue("rect")), PdfName.StrikeOut, XfdfObjectUtils.convertQuadPointsFromCoordsString(annotObject.getAttributeValue("coords")));
/*     */           
/* 198 */           addCommonAnnotationAttributes((PdfAnnotation)pdfStrikeoutAnnotation, annotObject);
/* 199 */           addMarkupAnnotationAttributes((PdfMarkupAnnotation)pdfStrikeoutAnnotation, annotObject);
/*     */           
/* 201 */           pdfDocument.getPage(Integer.parseInt(annotObject.getAttribute("page").getValue()))
/* 202 */             .addAnnotation((PdfAnnotation)pdfStrikeoutAnnotation);
/*     */           return;
/*     */         
/*     */         case "squiggly":
/* 206 */           pdfSquigglyAnnotation = new PdfTextMarkupAnnotation(XfdfObjectUtils.convertRectFromString(annotObject.getAttributeValue("rect")), PdfName.Squiggly, XfdfObjectUtils.convertQuadPointsFromCoordsString(annotObject.getAttributeValue("coords")));
/*     */           
/* 208 */           addCommonAnnotationAttributes((PdfAnnotation)pdfSquigglyAnnotation, annotObject);
/* 209 */           addMarkupAnnotationAttributes((PdfMarkupAnnotation)pdfSquigglyAnnotation, annotObject);
/*     */           
/* 211 */           pdfDocument.getPage(Integer.parseInt(annotObject.getAttribute("page").getValue()))
/* 212 */             .addAnnotation((PdfAnnotation)pdfSquigglyAnnotation);
/*     */           return;
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*     */         case "circle":
/* 219 */           pdfCircleAnnotation = new PdfCircleAnnotation(XfdfObjectUtils.convertRectFromString(annotObject.getAttributeValue("rect")));
/*     */           
/* 221 */           addCommonAnnotationAttributes((PdfAnnotation)pdfCircleAnnotation, annotObject);
/* 222 */           addMarkupAnnotationAttributes((PdfMarkupAnnotation)pdfCircleAnnotation, annotObject);
/*     */           
/* 224 */           if (annotObject.getAttributeValue("fringe") != null) {
/* 225 */             pdfCircleAnnotation.setRectangleDifferences(XfdfObjectUtils.convertFringeFromString(annotObject.getAttributeValue("fringe")));
/*     */           }
/*     */           
/* 228 */           pdfDocument.getPage(Integer.parseInt(annotObject.getAttribute("page").getValue()))
/* 229 */             .addAnnotation((PdfAnnotation)pdfCircleAnnotation);
/*     */           return;
/*     */         case "square":
/* 232 */           pdfSquareAnnotation = new PdfSquareAnnotation(XfdfObjectUtils.convertRectFromString(annotObject.getAttributeValue("rect")));
/*     */           
/* 234 */           addCommonAnnotationAttributes((PdfAnnotation)pdfSquareAnnotation, annotObject);
/* 235 */           addMarkupAnnotationAttributes((PdfMarkupAnnotation)pdfSquareAnnotation, annotObject);
/*     */           
/* 237 */           if (annotObject.getAttributeValue("fringe") != null) {
/* 238 */             pdfSquareAnnotation.setRectangleDifferences(XfdfObjectUtils.convertFringeFromString(annotObject.getAttributeValue("fringe")));
/*     */           }
/*     */           
/* 241 */           pdfDocument.getPage(Integer.parseInt(annotObject.getAttribute("page").getValue()))
/* 242 */             .addAnnotation((PdfAnnotation)pdfSquareAnnotation);
/*     */           return;
/*     */         
/*     */         case "polygon":
/* 246 */           rect = XfdfObjectUtils.convertRectFromString(annotObject.getAttributeValue("rect"));
/* 247 */           vertices = XfdfObjectUtils.convertVerticesFromString(annotObject.getVertices());
/* 248 */           polygonAnnotation = PdfPolyGeomAnnotation.createPolygon(rect, vertices);
/*     */           
/* 250 */           addCommonAnnotationAttributes((PdfAnnotation)polygonAnnotation, annotObject);
/* 251 */           addMarkupAnnotationAttributes((PdfMarkupAnnotation)polygonAnnotation, annotObject);
/*     */           
/* 253 */           pdfDocument.getPage(Integer.parseInt(annotObject.getAttribute("page").getValue()))
/* 254 */             .addAnnotation((PdfAnnotation)polygonAnnotation);
/*     */           return;
/*     */         case "polyline":
/* 257 */           polylineRect = XfdfObjectUtils.convertRectFromString(annotObject.getAttributeValue("rect"));
/* 258 */           polylineVertices = XfdfObjectUtils.convertVerticesFromString(annotObject.getVertices());
/* 259 */           polylineAnnotation = PdfPolyGeomAnnotation.createPolyLine(polylineRect, polylineVertices);
/*     */           
/* 261 */           addCommonAnnotationAttributes((PdfAnnotation)polylineAnnotation, annotObject);
/* 262 */           addMarkupAnnotationAttributes((PdfMarkupAnnotation)polylineAnnotation, annotObject);
/*     */           
/* 264 */           pdfDocument.getPage(Integer.parseInt(annotObject.getAttribute("page").getValue()))
/* 265 */             .addAnnotation((PdfAnnotation)polylineAnnotation);
/*     */           return;
/*     */         case "stamp":
/* 268 */           pdfDocument.getPage(Integer.parseInt(annotObject.getAttribute("page").getValue()))
/* 269 */             .addAnnotation((PdfAnnotation)new PdfStampAnnotation(XfdfObjectUtils.convertRectFromString(annotObject.getAttributeValue("rect"))));
/*     */           return;
/*     */         
/*     */         case "freetext":
/* 273 */           pdfDocument.getPage(Integer.parseInt(annotObject.getAttribute("page").getValue()))
/* 274 */             .addAnnotation((PdfAnnotation)new PdfFreeTextAnnotation(XfdfObjectUtils.convertRectFromString(annotObject.getAttributeValue("rect")), annotObject
/* 275 */                 .getContents()));
/*     */           return;
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 282 */       logger.warn(MessageFormatUtil.format("Xfdf annotation \"{0}\" is not supported", new Object[] { annotName }));
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/forms/xfdf/XfdfReader.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */