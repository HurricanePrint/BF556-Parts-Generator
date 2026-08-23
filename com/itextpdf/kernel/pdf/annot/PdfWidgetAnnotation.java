/*     */ package com.itextpdf.kernel.pdf.annot;
/*     */ 
/*     */ import com.itextpdf.kernel.geom.Rectangle;
/*     */ import com.itextpdf.kernel.pdf.PdfArray;
/*     */ import com.itextpdf.kernel.pdf.PdfDictionary;
/*     */ import com.itextpdf.kernel.pdf.PdfName;
/*     */ import com.itextpdf.kernel.pdf.PdfNumber;
/*     */ import com.itextpdf.kernel.pdf.PdfObject;
/*     */ import com.itextpdf.kernel.pdf.action.PdfAction;
/*     */ import java.util.HashSet;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class PdfWidgetAnnotation
/*     */   extends PdfAnnotation
/*     */ {
/*     */   private static final long serialVersionUID = 9013938639824707088L;
/*     */   public static final int HIDDEN = 1;
/*     */   public static final int VISIBLE_BUT_DOES_NOT_PRINT = 2;
/*     */   public static final int HIDDEN_BUT_PRINTABLE = 3;
/*     */   public static final int VISIBLE = 4;
/*     */   private HashSet<PdfName> widgetEntries;
/*     */   
/*     */   public PdfWidgetAnnotation(Rectangle rect) {
/*  65 */     super(rect);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  79 */     this.widgetEntries = new HashSet<>();
/*     */ 
/*     */     
/*  82 */     this.widgetEntries.add(PdfName.Subtype);
/*  83 */     this.widgetEntries.add(PdfName.Type);
/*  84 */     this.widgetEntries.add(PdfName.Rect);
/*  85 */     this.widgetEntries.add(PdfName.Contents);
/*  86 */     this.widgetEntries.add(PdfName.P);
/*  87 */     this.widgetEntries.add(PdfName.NM);
/*  88 */     this.widgetEntries.add(PdfName.M);
/*  89 */     this.widgetEntries.add(PdfName.F);
/*  90 */     this.widgetEntries.add(PdfName.AP);
/*  91 */     this.widgetEntries.add(PdfName.AS);
/*  92 */     this.widgetEntries.add(PdfName.Border);
/*  93 */     this.widgetEntries.add(PdfName.C);
/*  94 */     this.widgetEntries.add(PdfName.StructParent);
/*  95 */     this.widgetEntries.add(PdfName.OC);
/*  96 */     this.widgetEntries.add(PdfName.H);
/*  97 */     this.widgetEntries.add(PdfName.MK);
/*  98 */     this.widgetEntries.add(PdfName.A);
/*  99 */     this.widgetEntries.add(PdfName.AA);
/* 100 */     this.widgetEntries.add(PdfName.BS); } protected PdfWidgetAnnotation(PdfDictionary pdfObject) { super(pdfObject); this.widgetEntries = new HashSet<>(); this.widgetEntries.add(PdfName.Subtype); this.widgetEntries.add(PdfName.Type); this.widgetEntries.add(PdfName.Rect); this.widgetEntries.add(PdfName.Contents); this.widgetEntries.add(PdfName.P); this.widgetEntries.add(PdfName.NM); this.widgetEntries.add(PdfName.M); this.widgetEntries.add(PdfName.F); this.widgetEntries.add(PdfName.AP); this.widgetEntries.add(PdfName.AS); this.widgetEntries.add(PdfName.Border); this.widgetEntries.add(PdfName.C); this.widgetEntries.add(PdfName.StructParent); this.widgetEntries.add(PdfName.OC); this.widgetEntries.add(PdfName.H); this.widgetEntries.add(PdfName.MK); this.widgetEntries.add(PdfName.A); this.widgetEntries.add(PdfName.AA); this.widgetEntries.add(PdfName.BS); }
/*     */ 
/*     */ 
/*     */   
/*     */   public PdfName getSubtype() {
/* 105 */     return PdfName.Widget;
/*     */   }
/*     */   
/*     */   public PdfWidgetAnnotation setParent(PdfObject parent) {
/* 109 */     return (PdfWidgetAnnotation)put(PdfName.Parent, parent);
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
/*     */   public PdfWidgetAnnotation setHighlightMode(PdfName mode) {
/* 125 */     return (PdfWidgetAnnotation)put(PdfName.H, (PdfObject)mode);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PdfName getHighlightMode() {
/* 133 */     return ((PdfDictionary)getPdfObject()).getAsName(PdfName.H);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void releaseFormFieldFromWidgetAnnotation() {
/* 140 */     PdfDictionary annotDict = (PdfDictionary)getPdfObject();
/* 141 */     for (PdfName entry : this.widgetEntries) {
/* 142 */       annotDict.remove(entry);
/*     */     }
/* 144 */     PdfDictionary parent = annotDict.getAsDictionary(PdfName.Parent);
/* 145 */     if (parent != null && annotDict.size() == 1) {
/* 146 */       PdfArray kids = parent.getAsArray(PdfName.Kids);
/* 147 */       kids.remove((PdfObject)annotDict);
/* 148 */       if (kids.size() == 0) {
/* 149 */         parent.remove(PdfName.Kids);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PdfWidgetAnnotation setVisibility(int visibility) {
/* 160 */     switch (visibility) {
/*     */       case 1:
/* 162 */         ((PdfDictionary)getPdfObject()).put(PdfName.F, (PdfObject)new PdfNumber(6));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       case 2:
/* 174 */         return this;
/*     */       case 3:
/*     */         ((PdfDictionary)getPdfObject()).put(PdfName.F, (PdfObject)new PdfNumber(36));
/*     */     } 
/*     */     ((PdfDictionary)getPdfObject()).put(PdfName.F, (PdfObject)new PdfNumber(4));
/*     */   }
/*     */ 
/*     */   
/*     */   public PdfDictionary getAction() {
/* 183 */     return ((PdfDictionary)getPdfObject()).getAsDictionary(PdfName.A);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PdfWidgetAnnotation setAction(PdfAction action) {
/* 192 */     return (PdfWidgetAnnotation)put(PdfName.A, action.getPdfObject());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PdfDictionary getAdditionalAction() {
/* 202 */     return ((PdfDictionary)getPdfObject()).getAsDictionary(PdfName.AA);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PdfWidgetAnnotation setAdditionalAction(PdfName key, PdfAction action) {
/* 213 */     PdfAction.setAdditionalAction(this, key, action);
/* 214 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PdfDictionary getAppearanceCharacteristics() {
/* 224 */     return ((PdfDictionary)getPdfObject()).getAsDictionary(PdfName.MK);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PdfWidgetAnnotation setAppearanceCharacteristics(PdfDictionary characteristics) {
/* 235 */     return (PdfWidgetAnnotation)put(PdfName.MK, (PdfObject)characteristics);
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
/*     */   public PdfDictionary getBorderStyle() {
/* 247 */     return ((PdfDictionary)getPdfObject()).getAsDictionary(PdfName.BS);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PdfWidgetAnnotation setBorderStyle(PdfDictionary borderStyle) {
/* 258 */     return (PdfWidgetAnnotation)put(PdfName.BS, (PdfObject)borderStyle);
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
/*     */   public PdfWidgetAnnotation setBorderStyle(PdfName style) {
/* 276 */     return setBorderStyle(BorderStyleUtil.setStyle(getBorderStyle(), style));
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
/*     */   public PdfWidgetAnnotation setDashPattern(PdfArray dashPattern) {
/* 288 */     return setBorderStyle(BorderStyleUtil.setDashPattern(getBorderStyle(), dashPattern));
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/kernel/pdf/annot/PdfWidgetAnnotation.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */