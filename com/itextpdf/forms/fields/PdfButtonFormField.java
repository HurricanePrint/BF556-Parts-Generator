/*     */ package com.itextpdf.forms.fields;
/*     */ 
/*     */ import com.itextpdf.io.codec.Base64;
/*     */ import com.itextpdf.io.util.StreamUtil;
/*     */ import com.itextpdf.kernel.pdf.PdfDictionary;
/*     */ import com.itextpdf.kernel.pdf.PdfDocument;
/*     */ import com.itextpdf.kernel.pdf.PdfName;
/*     */ import com.itextpdf.kernel.pdf.annot.PdfWidgetAnnotation;
/*     */ import com.itextpdf.kernel.pdf.xobject.PdfFormXObject;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class PdfButtonFormField
/*     */   extends PdfFormField
/*     */ {
/*  66 */   public static final int FF_NO_TOGGLE_TO_OFF = makeFieldFlag(15);
/*  67 */   public static final int FF_RADIO = makeFieldFlag(16);
/*  68 */   public static final int FF_PUSH_BUTTON = makeFieldFlag(17);
/*  69 */   public static final int FF_RADIOS_IN_UNISON = makeFieldFlag(26);
/*     */   
/*     */   protected PdfButtonFormField(PdfDocument pdfDocument) {
/*  72 */     super(pdfDocument);
/*     */   }
/*     */   
/*     */   protected PdfButtonFormField(PdfWidgetAnnotation widget, PdfDocument pdfDocument) {
/*  76 */     super(widget, pdfDocument);
/*     */   }
/*     */   
/*     */   protected PdfButtonFormField(PdfDictionary pdfObject) {
/*  80 */     super(pdfObject);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PdfName getFormType() {
/*  90 */     return PdfName.Btn;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isRadio() {
/*  99 */     return getFieldFlag(FF_RADIO);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PdfButtonFormField setRadio(boolean radio) {
/* 109 */     return (PdfButtonFormField)setFieldFlag(FF_RADIO, radio);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isToggleOff() {
/* 119 */     return !getFieldFlag(FF_NO_TOGGLE_TO_OFF);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PdfButtonFormField setToggleOff(boolean toggleOff) {
/* 129 */     return (PdfButtonFormField)setFieldFlag(FF_NO_TOGGLE_TO_OFF, !toggleOff);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isPushButton() {
/* 137 */     return getFieldFlag(FF_PUSH_BUTTON);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PdfButtonFormField setPushButton(boolean pushButton) {
/* 146 */     return (PdfButtonFormField)setFieldFlag(FF_PUSH_BUTTON, pushButton);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isRadiosInUnison() {
/* 157 */     return getFieldFlag(FF_RADIOS_IN_UNISON);
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
/*     */   public PdfButtonFormField setRadiosInUnison(boolean radiosInUnison) {
/* 169 */     return (PdfButtonFormField)setFieldFlag(FF_RADIOS_IN_UNISON, radiosInUnison);
/*     */   }
/*     */   
/*     */   public PdfButtonFormField setImage(String image) throws IOException {
/* 173 */     InputStream is = new FileInputStream(image);
/* 174 */     String str = Base64.encodeBytes(StreamUtil.inputStreamToArray(is));
/* 175 */     return (PdfButtonFormField)setValue(str);
/*     */   }
/*     */   
/*     */   public PdfButtonFormField setImageAsForm(PdfFormXObject form) {
/* 179 */     this.form = form;
/* 180 */     regenerateField();
/* 181 */     return this;
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/forms/fields/PdfButtonFormField.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */