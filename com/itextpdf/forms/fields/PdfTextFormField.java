/*     */ package com.itextpdf.forms.fields;
/*     */ 
/*     */ import com.itextpdf.kernel.pdf.PdfDictionary;
/*     */ import com.itextpdf.kernel.pdf.PdfDocument;
/*     */ import com.itextpdf.kernel.pdf.PdfName;
/*     */ import com.itextpdf.kernel.pdf.PdfNumber;
/*     */ import com.itextpdf.kernel.pdf.PdfObject;
/*     */ import com.itextpdf.kernel.pdf.annot.PdfWidgetAnnotation;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class PdfTextFormField
/*     */   extends PdfFormField
/*     */ {
/*  57 */   public static final int FF_FILE_SELECT = makeFieldFlag(21);
/*  58 */   public static final int FF_DO_NOT_SPELL_CHECK = makeFieldFlag(23);
/*  59 */   public static final int FF_DO_NOT_SCROLL = makeFieldFlag(24);
/*  60 */   public static final int FF_COMB = makeFieldFlag(25);
/*  61 */   public static final int FF_RICH_TEXT = makeFieldFlag(26);
/*     */   
/*     */   protected PdfTextFormField(PdfDocument pdfDocument) {
/*  64 */     super(pdfDocument);
/*     */   }
/*     */   
/*     */   protected PdfTextFormField(PdfWidgetAnnotation widget, PdfDocument pdfDocument) {
/*  68 */     super(widget, pdfDocument);
/*     */   }
/*     */   
/*     */   protected PdfTextFormField(PdfDictionary pdfObject) {
/*  72 */     super(pdfObject);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PdfName getFormType() {
/*  82 */     return PdfName.Tx;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PdfTextFormField setMultiline(boolean multiline) {
/*  91 */     return (PdfTextFormField)setFieldFlag(FF_MULTILINE, multiline);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PdfTextFormField setPassword(boolean password) {
/* 101 */     return (PdfTextFormField)setFieldFlag(FF_PASSWORD, password);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isFileSelect() {
/* 110 */     return getFieldFlag(FF_FILE_SELECT);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PdfTextFormField setFileSelect(boolean fileSelect) {
/* 120 */     return (PdfTextFormField)setFieldFlag(FF_FILE_SELECT, fileSelect);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isSpellCheck() {
/* 128 */     return !getFieldFlag(FF_DO_NOT_SPELL_CHECK);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PdfTextFormField setSpellCheck(boolean spellCheck) {
/* 137 */     return (PdfTextFormField)setFieldFlag(FF_DO_NOT_SPELL_CHECK, !spellCheck);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isScroll() {
/* 147 */     return !getFieldFlag(FF_DO_NOT_SCROLL);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PdfTextFormField setScroll(boolean scroll) {
/* 158 */     return (PdfTextFormField)setFieldFlag(FF_DO_NOT_SCROLL, !scroll);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isComb() {
/* 169 */     return getFieldFlag(FF_COMB);
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
/*     */   public PdfTextFormField setComb(boolean comb) {
/* 181 */     return (PdfTextFormField)setFieldFlag(FF_COMB, comb);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isRichText() {
/* 190 */     return getFieldFlag(FF_RICH_TEXT);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PdfTextFormField setRichText(boolean richText) {
/* 200 */     return (PdfTextFormField)setFieldFlag(FF_RICH_TEXT, richText);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMaxLen() {
/* 209 */     PdfNumber maxLenEntry = ((PdfDictionary)getPdfObject()).getAsNumber(PdfName.MaxLen);
/* 210 */     if (maxLenEntry != null) {
/* 211 */       return maxLenEntry.intValue();
/*     */     }
/* 213 */     PdfDictionary parent = getParent();
/*     */     
/* 215 */     if (parent != null) {
/* 216 */       return (new PdfTextFormField(parent)).getMaxLen();
/*     */     }
/* 218 */     return 0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PdfTextFormField setMaxLen(int maxLen) {
/* 229 */     put(PdfName.MaxLen, (PdfObject)new PdfNumber(maxLen));
/* 230 */     if (getFieldFlag(FF_COMB))
/* 231 */       regenerateField(); 
/* 232 */     return this;
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/forms/fields/PdfTextFormField.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */