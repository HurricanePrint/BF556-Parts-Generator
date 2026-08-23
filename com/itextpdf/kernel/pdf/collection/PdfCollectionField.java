/*     */ package com.itextpdf.kernel.pdf.collection;
/*     */ 
/*     */ import com.itextpdf.kernel.PdfException;
/*     */ import com.itextpdf.kernel.pdf.PdfBoolean;
/*     */ import com.itextpdf.kernel.pdf.PdfDate;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class PdfCollectionField
/*     */   extends PdfObjectWrapper<PdfDictionary>
/*     */ {
/*     */   private static final long serialVersionUID = 4766153544105870238L;
/*     */   public static final int TEXT = 0;
/*     */   public static final int DATE = 1;
/*     */   public static final int NUMBER = 2;
/*     */   public static final int FILENAME = 3;
/*     */   public static final int DESC = 4;
/*     */   public static final int MODDATE = 5;
/*     */   public static final int CREATIONDATE = 6;
/*     */   public static final int SIZE = 7;
/*     */   protected int subType;
/*     */   
/*     */   protected PdfCollectionField(PdfDictionary pdfObject) {
/*  97 */     super((PdfObject)pdfObject);
/*  98 */     String subType = pdfObject.getAsName(PdfName.Subtype).getValue();
/*  99 */     switch (subType) {
/*     */       case "D":
/* 101 */         this.subType = 1;
/*     */         return;
/*     */       case "N":
/* 104 */         this.subType = 2;
/*     */         return;
/*     */       case "F":
/* 107 */         this.subType = 3;
/*     */         return;
/*     */       case "Desc":
/* 110 */         this.subType = 4;
/*     */         return;
/*     */       case "ModDate":
/* 113 */         this.subType = 5;
/*     */         return;
/*     */       case "CreationDate":
/* 116 */         this.subType = 6;
/*     */         return;
/*     */       case "Size":
/* 119 */         this.subType = 7;
/*     */         return;
/*     */     } 
/* 122 */     this.subType = 0;
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
/*     */   public PdfCollectionField(String name, int subType) {
/* 134 */     super((PdfObject)new PdfDictionary());
/* 135 */     ((PdfDictionary)getPdfObject()).put(PdfName.N, (PdfObject)new PdfString(name));
/* 136 */     this.subType = subType;
/* 137 */     switch (subType) {
/*     */       default:
/* 139 */         ((PdfDictionary)getPdfObject()).put(PdfName.Subtype, (PdfObject)PdfName.S);
/*     */         return;
/*     */       case 1:
/* 142 */         ((PdfDictionary)getPdfObject()).put(PdfName.Subtype, (PdfObject)PdfName.D);
/*     */         return;
/*     */       case 2:
/* 145 */         ((PdfDictionary)getPdfObject()).put(PdfName.Subtype, (PdfObject)PdfName.N);
/*     */         return;
/*     */       case 3:
/* 148 */         ((PdfDictionary)getPdfObject()).put(PdfName.Subtype, (PdfObject)PdfName.F);
/*     */         return;
/*     */       case 4:
/* 151 */         ((PdfDictionary)getPdfObject()).put(PdfName.Subtype, (PdfObject)PdfName.Desc);
/*     */         return;
/*     */       case 5:
/* 154 */         ((PdfDictionary)getPdfObject()).put(PdfName.Subtype, (PdfObject)PdfName.ModDate);
/*     */         return;
/*     */       case 6:
/* 157 */         ((PdfDictionary)getPdfObject()).put(PdfName.Subtype, (PdfObject)PdfName.CreationDate); return;
/*     */       case 7:
/*     */         break;
/* 160 */     }  ((PdfDictionary)getPdfObject()).put(PdfName.Subtype, (PdfObject)PdfName.Size);
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
/*     */   public PdfCollectionField setOrder(int order) {
/* 172 */     ((PdfDictionary)getPdfObject()).put(PdfName.O, (PdfObject)new PdfNumber(order));
/* 173 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PdfNumber getOrder() {
/* 182 */     return ((PdfDictionary)getPdfObject()).getAsNumber(PdfName.O);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PdfCollectionField setVisibility(boolean visible) {
/* 192 */     ((PdfDictionary)getPdfObject()).put(PdfName.V, (PdfObject)PdfBoolean.valueOf(visible));
/* 193 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PdfBoolean getVisibility() {
/* 202 */     return ((PdfDictionary)getPdfObject()).getAsBoolean(PdfName.V);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PdfCollectionField setEditable(boolean editable) {
/* 212 */     ((PdfDictionary)getPdfObject()).put(PdfName.E, (PdfObject)PdfBoolean.valueOf(editable));
/* 213 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PdfBoolean getEditable() {
/* 223 */     return ((PdfDictionary)getPdfObject()).getAsBoolean(PdfName.E);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PdfObject getValue(String value) {
/* 233 */     switch (this.subType) {
/*     */       case 0:
/* 235 */         return (PdfObject)new PdfString(value);
/*     */       case 1:
/* 237 */         return (new PdfDate(PdfDate.decode(value))).getPdfObject();
/*     */       case 2:
/* 239 */         return (PdfObject)new PdfNumber(Double.parseDouble(value.trim()));
/*     */     } 
/* 241 */     throw (new PdfException("{0} is not an acceptable value for the field {1}.")).setMessageParams(new Object[] { value, ((PdfDictionary)getPdfObject()).getAsString(PdfName.N).getValue() });
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean isWrappedObjectMustBeIndirect() {
/* 246 */     return false;
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/kernel/pdf/collection/PdfCollectionField.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */