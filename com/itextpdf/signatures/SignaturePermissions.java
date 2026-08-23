/*     */ package com.itextpdf.signatures;
/*     */ 
/*     */ import com.itextpdf.kernel.pdf.PdfArray;
/*     */ import com.itextpdf.kernel.pdf.PdfDictionary;
/*     */ import com.itextpdf.kernel.pdf.PdfName;
/*     */ import com.itextpdf.kernel.pdf.PdfNumber;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SignaturePermissions
/*     */ {
/*     */   public class FieldLock
/*     */   {
/*     */     PdfName action;
/*     */     PdfArray fields;
/*     */     
/*     */     public FieldLock(PdfName action, PdfArray fields) {
/*  71 */       this.action = action;
/*  72 */       this.fields = fields;
/*     */     }
/*     */     public PdfName getAction() {
/*  75 */       return this.action;
/*     */     } public PdfArray getFields() {
/*  77 */       return this.fields;
/*     */     }
/*     */     public String toString() {
/*  80 */       return this.action.toString() + ((this.fields == null) ? "" : this.fields.toString());
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   boolean certification = false;
/*     */   
/*     */   boolean fillInAllowed = true;
/*     */   
/*     */   boolean annotationsAllowed = true;
/*     */   
/*  91 */   List<FieldLock> fieldLocks = new ArrayList<>();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SignaturePermissions(PdfDictionary sigDict, SignaturePermissions previous) {
/*  99 */     if (previous != null) {
/* 100 */       this.annotationsAllowed &= previous.isAnnotationsAllowed();
/* 101 */       this.fillInAllowed &= previous.isFillInAllowed();
/* 102 */       this.fieldLocks.addAll(previous.getFieldLocks());
/*     */     } 
/* 104 */     PdfArray ref = sigDict.getAsArray(PdfName.Reference);
/* 105 */     if (ref != null) {
/* 106 */       for (int i = 0; i < ref.size(); i++) {
/* 107 */         PdfDictionary dict = ref.getAsDictionary(i);
/* 108 */         PdfDictionary params = dict.getAsDictionary(PdfName.TransformParams);
/* 109 */         if (PdfName.DocMDP.equals(dict.getAsName(PdfName.TransformMethod))) {
/* 110 */           this.certification = true;
/*     */         }
/* 112 */         PdfName action = params.getAsName(PdfName.Action);
/* 113 */         if (action != null) {
/* 114 */           this.fieldLocks.add(new FieldLock(action, params.getAsArray(PdfName.Fields)));
/*     */         }
/* 116 */         PdfNumber p = params.getAsNumber(PdfName.P);
/* 117 */         if (p != null)
/*     */         {
/* 119 */           switch (p.intValue()) {
/*     */ 
/*     */             
/*     */             case 1:
/* 123 */               this.fillInAllowed &= 0x0;
/*     */             case 2:
/* 125 */               this.annotationsAllowed &= 0x0;
/*     */               break;
/*     */           } 
/*     */         }
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isCertification() {
/* 136 */     return this.certification;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isFillInAllowed() {
/* 143 */     return this.fillInAllowed;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isAnnotationsAllowed() {
/* 150 */     return this.annotationsAllowed;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List<FieldLock> getFieldLocks() {
/* 157 */     return this.fieldLocks;
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/signatures/SignaturePermissions.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */