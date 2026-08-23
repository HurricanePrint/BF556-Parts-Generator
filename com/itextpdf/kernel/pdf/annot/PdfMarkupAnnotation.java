/*     */ package com.itextpdf.kernel.pdf.annot;
/*     */ 
/*     */ import com.itextpdf.kernel.geom.Rectangle;
/*     */ import com.itextpdf.kernel.pdf.PdfDictionary;
/*     */ import com.itextpdf.kernel.pdf.PdfName;
/*     */ import com.itextpdf.kernel.pdf.PdfNumber;
/*     */ import com.itextpdf.kernel.pdf.PdfObject;
/*     */ import com.itextpdf.kernel.pdf.PdfString;
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
/*     */ public abstract class PdfMarkupAnnotation
/*     */   extends PdfAnnotation
/*     */ {
/*     */   private static final long serialVersionUID = 239280278775576458L;
/*  67 */   protected PdfAnnotation inReplyTo = null;
/*  68 */   protected PdfPopupAnnotation popup = null;
/*     */   
/*     */   protected PdfMarkupAnnotation(Rectangle rect) {
/*  71 */     super(rect);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected PdfMarkupAnnotation(PdfDictionary pdfObject) {
/*  82 */     super(pdfObject);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PdfString getText() {
/*  92 */     return ((PdfDictionary)getPdfObject()).getAsString(PdfName.T);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PdfMarkupAnnotation setText(PdfString text) {
/* 102 */     return (PdfMarkupAnnotation)put(PdfName.T, (PdfObject)text);
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
/*     */   public PdfNumber getOpacity() {
/* 115 */     return ((PdfDictionary)getPdfObject()).getAsNumber(PdfName.CA);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PdfMarkupAnnotation setOpacity(PdfNumber ca) {
/* 126 */     return (PdfMarkupAnnotation)put(PdfName.CA, (PdfObject)ca);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PdfObject getRichText() {
/* 136 */     return ((PdfDictionary)getPdfObject()).get(PdfName.RC);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PdfMarkupAnnotation setRichText(PdfObject richText) {
/* 146 */     return (PdfMarkupAnnotation)put(PdfName.RC, richText);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PdfString getCreationDate() {
/* 154 */     return ((PdfDictionary)getPdfObject()).getAsString(PdfName.CreationDate);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PdfMarkupAnnotation setCreationDate(PdfString creationDate) {
/* 164 */     return (PdfMarkupAnnotation)put(PdfName.CreationDate, (PdfObject)creationDate);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PdfDictionary getInReplyToObject() {
/* 175 */     return ((PdfDictionary)getPdfObject()).getAsDictionary(PdfName.IRT);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PdfAnnotation getInReplyTo() {
/* 186 */     if (this.inReplyTo == null) {
/* 187 */       this.inReplyTo = makeAnnotation((PdfObject)getInReplyToObject());
/*     */     }
/* 189 */     return this.inReplyTo;
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
/*     */   public PdfMarkupAnnotation setInReplyTo(PdfAnnotation inReplyTo) {
/* 201 */     this.inReplyTo = inReplyTo;
/* 202 */     return (PdfMarkupAnnotation)put(PdfName.IRT, inReplyTo.getPdfObject());
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
/*     */   public PdfMarkupAnnotation setPopup(PdfPopupAnnotation popup) {
/* 214 */     this.popup = popup;
/* 215 */     popup.setParent(this);
/* 216 */     return (PdfMarkupAnnotation)put(PdfName.Popup, popup.getPdfObject());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PdfDictionary getPopupObject() {
/* 225 */     return ((PdfDictionary)getPdfObject()).getAsDictionary(PdfName.Popup);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PdfPopupAnnotation getPopup() {
/* 236 */     if (this.popup == null) {
/* 237 */       PdfDictionary popupObject = getPopupObject();
/* 238 */       if (popupObject != null) {
/* 239 */         PdfAnnotation annotation = makeAnnotation((PdfObject)popupObject);
/* 240 */         if (!(annotation instanceof PdfPopupAnnotation)) {
/* 241 */           Logger logger = LoggerFactory.getLogger(PdfMarkupAnnotation.class);
/* 242 */           logger.warn("Popup entry in the markup annotations refers not to the annotation with Popup subtype.");
/* 243 */           return null;
/*     */         } 
/* 245 */         this.popup = (PdfPopupAnnotation)annotation;
/*     */       } 
/*     */     } 
/* 248 */     return this.popup;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PdfString getSubject() {
/* 256 */     return ((PdfDictionary)getPdfObject()).getAsString(PdfName.Subj);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PdfMarkupAnnotation setSubject(PdfString subject) {
/* 265 */     return (PdfMarkupAnnotation)put(PdfName.Subj, (PdfObject)subject);
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
/*     */   public PdfName getReplyType() {
/* 281 */     return ((PdfDictionary)getPdfObject()).getAsName(PdfName.RT);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PdfMarkupAnnotation setReplyType(PdfName replyType) {
/* 291 */     return (PdfMarkupAnnotation)put(PdfName.RT, (PdfObject)replyType);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PdfName getIntent() {
/* 300 */     return ((PdfDictionary)getPdfObject()).getAsName(PdfName.IT);
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
/*     */   public PdfMarkupAnnotation setIntent(PdfName intent) {
/* 316 */     return (PdfMarkupAnnotation)put(PdfName.IT, (PdfObject)intent);
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
/*     */   public PdfDictionary getExternalData() {
/* 332 */     return ((PdfDictionary)getPdfObject()).getAsDictionary(PdfName.ExData);
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
/*     */   @Deprecated
/*     */   public PdfMarkupAnnotation setExternalData(PdfName exData) {
/* 351 */     return (PdfMarkupAnnotation)put(PdfName.ExData, (PdfObject)exData);
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
/*     */   public PdfMarkupAnnotation setExternalData(PdfDictionary exData) {
/* 368 */     return (PdfMarkupAnnotation)put(PdfName.ExData, (PdfObject)exData);
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/kernel/pdf/annot/PdfMarkupAnnotation.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */