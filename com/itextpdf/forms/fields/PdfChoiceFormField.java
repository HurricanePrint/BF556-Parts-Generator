/*     */ package com.itextpdf.forms.fields;
/*     */ 
/*     */ import com.itextpdf.io.util.MessageFormatUtil;
/*     */ import com.itextpdf.kernel.pdf.PdfArray;
/*     */ import com.itextpdf.kernel.pdf.PdfDictionary;
/*     */ import com.itextpdf.kernel.pdf.PdfDocument;
/*     */ import com.itextpdf.kernel.pdf.PdfName;
/*     */ import com.itextpdf.kernel.pdf.PdfNumber;
/*     */ import com.itextpdf.kernel.pdf.PdfObject;
/*     */ import com.itextpdf.kernel.pdf.PdfString;
/*     */ import com.itextpdf.kernel.pdf.annot.PdfWidgetAnnotation;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
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
/*     */ 
/*     */ public class PdfChoiceFormField
/*     */   extends PdfFormField
/*     */ {
/*  72 */   public static final int FF_COMBO = makeFieldFlag(18);
/*  73 */   public static final int FF_EDIT = makeFieldFlag(19);
/*  74 */   public static final int FF_SORT = makeFieldFlag(20);
/*  75 */   public static final int FF_MULTI_SELECT = makeFieldFlag(22);
/*  76 */   public static final int FF_DO_NOT_SPELL_CHECK = makeFieldFlag(23);
/*  77 */   public static final int FF_COMMIT_ON_SEL_CHANGE = makeFieldFlag(27);
/*     */   
/*     */   protected PdfChoiceFormField(PdfDocument pdfDocument) {
/*  80 */     super(pdfDocument);
/*     */   }
/*     */   
/*     */   protected PdfChoiceFormField(PdfWidgetAnnotation widget, PdfDocument pdfDocument) {
/*  84 */     super(widget, pdfDocument);
/*     */   }
/*     */   
/*     */   protected PdfChoiceFormField(PdfDictionary pdfObject) {
/*  88 */     super(pdfObject);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PdfName getFormType() {
/*  98 */     return PdfName.Ch;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PdfChoiceFormField setTopIndex(int index) {
/* 108 */     put(PdfName.TI, (PdfObject)new PdfNumber(index));
/* 109 */     regenerateField();
/* 110 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PdfNumber getTopIndex() {
/* 118 */     return ((PdfDictionary)getPdfObject()).getAsNumber(PdfName.TI);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PdfChoiceFormField setIndices(PdfArray indices) {
/* 128 */     return (PdfChoiceFormField)put(PdfName.I, (PdfObject)indices);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PdfChoiceFormField setListSelected(String[] optionValues) {
/* 138 */     return setListSelected(optionValues, true);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PdfChoiceFormField setListSelected(String[] optionValues, boolean generateAppearance) {
/* 149 */     if (optionValues.length > 1 && !isMultiSelect()) {
/* 150 */       Logger logger = LoggerFactory.getLogger(getClass());
/* 151 */       logger.warn("Multiple values were set to a field that does not have MultiSelect flag set.");
/*     */     } 
/* 153 */     PdfArray options = getOptions();
/* 154 */     PdfArray indices = new PdfArray();
/* 155 */     PdfArray values = new PdfArray();
/* 156 */     List<String> optionsToUnicodeNames = optionsToUnicodeNames();
/* 157 */     for (String element : optionValues) {
/* 158 */       if (element != null)
/*     */       {
/*     */         
/* 161 */         if (optionsToUnicodeNames.contains(element)) {
/* 162 */           int index = optionsToUnicodeNames.indexOf(element);
/* 163 */           indices.add((PdfObject)new PdfNumber(index));
/* 164 */           PdfObject optByIndex = options.get(index);
/* 165 */           values.add(optByIndex.isString() ? optByIndex : ((PdfArray)optByIndex).get(1));
/*     */         } else {
/* 167 */           if (!isCombo() || !isEdit()) {
/* 168 */             Logger logger = LoggerFactory.getLogger(getClass());
/* 169 */             logger.warn(
/* 170 */                 MessageFormatUtil.format("Value \"{0}\" is not contained in /Opt array of field \"{1}\".", new Object[] {
/* 171 */                     element, getFieldName() }));
/*     */           } 
/* 173 */           values.add((PdfObject)new PdfString(element, "UnicodeBig"));
/*     */         }  } 
/*     */     } 
/* 176 */     if (indices.size() > 0) {
/* 177 */       setIndices(indices);
/*     */     } else {
/* 179 */       remove(PdfName.I);
/*     */     } 
/* 181 */     if (values.size() == 1) {
/* 182 */       put(PdfName.V, values.get(0));
/*     */     } else {
/* 184 */       put(PdfName.V, (PdfObject)values);
/*     */     } 
/*     */     
/* 187 */     if (generateAppearance) {
/* 188 */       regenerateField();
/*     */     }
/* 190 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PdfChoiceFormField setListSelected(int[] optionNumbers) {
/* 200 */     if (optionNumbers.length > 1 && !isMultiSelect()) {
/* 201 */       Logger logger = LoggerFactory.getLogger(getClass());
/* 202 */       logger.warn("Multiple values were set to a field that does not have MultiSelect flag set.");
/*     */     } 
/* 204 */     PdfArray indices = new PdfArray();
/* 205 */     PdfArray values = new PdfArray();
/* 206 */     PdfArray options = getOptions();
/* 207 */     for (int number : optionNumbers) {
/* 208 */       if (number >= 0 && number < options.size()) {
/* 209 */         indices.add((PdfObject)new PdfNumber(number));
/* 210 */         PdfObject option = options.get(number);
/* 211 */         if (option.isString()) {
/* 212 */           values.add(option);
/* 213 */         } else if (option.isArray()) {
/* 214 */           values.add(((PdfArray)option).get(0));
/*     */         } 
/*     */       } 
/*     */     } 
/* 218 */     if (indices.size() > 0) {
/* 219 */       setIndices(indices);
/* 220 */       if (values.size() == 1) {
/* 221 */         put(PdfName.V, values.get(0));
/*     */       } else {
/* 223 */         put(PdfName.V, (PdfObject)values);
/*     */       } 
/*     */     } else {
/* 226 */       remove(PdfName.I);
/* 227 */       remove(PdfName.V);
/*     */     } 
/* 229 */     regenerateField();
/* 230 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PdfArray getIndices() {
/* 239 */     return ((PdfDictionary)getPdfObject()).getAsArray(PdfName.I);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PdfChoiceFormField setCombo(boolean combo) {
/* 248 */     return (PdfChoiceFormField)setFieldFlag(FF_COMBO, combo);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isCombo() {
/* 257 */     return getFieldFlag(FF_COMBO);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PdfChoiceFormField setEdit(boolean edit) {
/* 268 */     return (PdfChoiceFormField)setFieldFlag(FF_EDIT, edit);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isEdit() {
/* 278 */     return getFieldFlag(FF_EDIT);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PdfChoiceFormField setSort(boolean sort) {
/* 288 */     return (PdfChoiceFormField)setFieldFlag(FF_SORT, sort);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isSort() {
/* 297 */     return getFieldFlag(FF_SORT);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PdfChoiceFormField setMultiSelect(boolean multiSelect) {
/* 307 */     return (PdfChoiceFormField)setFieldFlag(FF_MULTI_SELECT, multiSelect);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isMultiSelect() {
/* 315 */     return getFieldFlag(FF_MULTI_SELECT);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PdfChoiceFormField setSpellCheck(boolean spellCheck) {
/* 324 */     return (PdfChoiceFormField)setFieldFlag(FF_DO_NOT_SPELL_CHECK, !spellCheck);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isSpellCheck() {
/* 332 */     return !getFieldFlag(FF_DO_NOT_SPELL_CHECK);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PdfChoiceFormField setCommitOnSelChange(boolean commitOnSelChange) {
/* 341 */     return (PdfChoiceFormField)setFieldFlag(FF_COMMIT_ON_SEL_CHANGE, commitOnSelChange);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isCommitOnSelChange() {
/* 349 */     return getFieldFlag(FF_COMMIT_ON_SEL_CHANGE);
/*     */   }
/*     */   
/*     */   private List<String> optionsToUnicodeNames() {
/* 353 */     PdfArray options = getOptions();
/* 354 */     List<String> optionsToUnicodeNames = new ArrayList<>(options.size());
/* 355 */     for (int index = 0; index < options.size(); index++) {
/* 356 */       PdfObject option = options.get(index);
/* 357 */       PdfString value = null;
/* 358 */       if (option.isString()) {
/* 359 */         value = (PdfString)option;
/* 360 */       } else if (option.isArray()) {
/* 361 */         value = (PdfString)((PdfArray)option).get(1);
/*     */       } 
/* 363 */       optionsToUnicodeNames.add((value != null) ? value.toUnicodeString() : null);
/*     */     } 
/* 365 */     return optionsToUnicodeNames;
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/forms/fields/PdfChoiceFormField.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */