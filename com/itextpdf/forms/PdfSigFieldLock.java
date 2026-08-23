/*     */ package com.itextpdf.forms;
/*     */ 
/*     */ import com.itextpdf.kernel.pdf.PdfArray;
/*     */ import com.itextpdf.kernel.pdf.PdfDictionary;
/*     */ import com.itextpdf.kernel.pdf.PdfName;
/*     */ import com.itextpdf.kernel.pdf.PdfNumber;
/*     */ import com.itextpdf.kernel.pdf.PdfObject;
/*     */ import com.itextpdf.kernel.pdf.PdfObjectWrapper;
/*     */ import com.itextpdf.kernel.pdf.PdfString;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class PdfSigFieldLock
/*     */   extends PdfObjectWrapper<PdfDictionary>
/*     */ {
/*     */   public PdfSigFieldLock() {
/*  64 */     this(new PdfDictionary());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PdfSigFieldLock(PdfDictionary dict) {
/*  73 */     super((PdfObject)dict);
/*  74 */     ((PdfDictionary)getPdfObject()).put(PdfName.Type, (PdfObject)PdfName.SigFieldLock);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PdfSigFieldLock setDocumentPermissions(LockPermissions permissions) {
/*  85 */     ((PdfDictionary)getPdfObject()).put(PdfName.P, (PdfObject)getLockPermission(permissions));
/*  86 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PdfSigFieldLock setFieldLock(LockAction action, String... fields) {
/*  97 */     PdfArray fieldsArray = new PdfArray();
/*  98 */     for (String field : fields) {
/*  99 */       fieldsArray.add((PdfObject)new PdfString(field));
/*     */     }
/* 101 */     ((PdfDictionary)getPdfObject()).put(PdfName.Action, (PdfObject)getLockActionValue(action));
/* 102 */     ((PdfDictionary)getPdfObject()).put(PdfName.Fields, (PdfObject)fieldsArray);
/* 103 */     return this;
/*     */   }
/*     */   
/*     */   public static PdfName getLockActionValue(LockAction action) {
/* 107 */     switch (action) {
/*     */       case NO_CHANGES_ALLOWED:
/* 109 */         return PdfName.All;
/*     */       case FORM_FILLING:
/* 111 */         return PdfName.Include;
/*     */       case FORM_FILLING_AND_ANNOTATION:
/* 113 */         return PdfName.Exclude;
/*     */     } 
/* 115 */     return PdfName.All;
/*     */   }
/*     */ 
/*     */   
/*     */   public static PdfNumber getLockPermission(LockPermissions permissions) {
/* 120 */     switch (permissions) {
/*     */       case NO_CHANGES_ALLOWED:
/* 122 */         return new PdfNumber(1);
/*     */       case FORM_FILLING:
/* 124 */         return new PdfNumber(2);
/*     */       case FORM_FILLING_AND_ANNOTATION:
/* 126 */         return new PdfNumber(3);
/*     */     } 
/* 128 */     return new PdfNumber(0);
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
/*     */   public enum LockAction
/*     */   {
/* 143 */     ALL, INCLUDE, EXCLUDE;
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
/*     */   public enum LockPermissions
/*     */   {
/* 161 */     NO_CHANGES_ALLOWED, FORM_FILLING, FORM_FILLING_AND_ANNOTATION;
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean isWrappedObjectMustBeIndirect() {
/* 166 */     return true;
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/forms/PdfSigFieldLock.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */