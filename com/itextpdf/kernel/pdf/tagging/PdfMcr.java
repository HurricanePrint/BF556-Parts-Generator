/*     */ package com.itextpdf.kernel.pdf.tagging;
/*     */ 
/*     */ import com.itextpdf.kernel.pdf.PdfDictionary;
/*     */ import com.itextpdf.kernel.pdf.PdfIndirectReference;
/*     */ import com.itextpdf.kernel.pdf.PdfName;
/*     */ import com.itextpdf.kernel.pdf.PdfObject;
/*     */ import com.itextpdf.kernel.pdf.PdfObjectWrapper;
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
/*     */ public abstract class PdfMcr
/*     */   extends PdfObjectWrapper<PdfObject>
/*     */   implements IStructureNode
/*     */ {
/*     */   private static final long serialVersionUID = -6453225665665080940L;
/*     */   protected PdfStructElem parent;
/*     */   
/*     */   protected PdfMcr(PdfObject pdfObject, PdfStructElem parent) {
/*  64 */     super(pdfObject);
/*  65 */     this.parent = parent;
/*     */   }
/*     */   
/*     */   public abstract int getMcid();
/*     */   
/*     */   public PdfDictionary getPageObject() {
/*  71 */     PdfObject pageObject = getPageIndirectReference().getRefersTo();
/*  72 */     if (pageObject instanceof PdfDictionary) {
/*  73 */       return (PdfDictionary)pageObject;
/*     */     }
/*  75 */     return null;
/*     */   }
/*     */   
/*     */   public PdfIndirectReference getPageIndirectReference() {
/*  79 */     PdfObject page = null;
/*  80 */     if (getPdfObject() instanceof PdfDictionary) {
/*  81 */       page = ((PdfDictionary)getPdfObject()).get(PdfName.Pg, false);
/*     */     }
/*  83 */     if (page == null) {
/*  84 */       page = ((PdfDictionary)this.parent.getPdfObject()).get(PdfName.Pg, false);
/*     */     }
/*  86 */     if (page instanceof PdfIndirectReference)
/*  87 */       return (PdfIndirectReference)page; 
/*  88 */     if (page instanceof PdfDictionary) {
/*  89 */       return page.getIndirectReference();
/*     */     }
/*  91 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public PdfName getRole() {
/*  96 */     return this.parent.getRole();
/*     */   }
/*     */ 
/*     */   
/*     */   public IStructureNode getParent() {
/* 101 */     return this.parent;
/*     */   }
/*     */ 
/*     */   
/*     */   public List<IStructureNode> getKids() {
/* 106 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean isWrappedObjectMustBeIndirect() {
/* 111 */     return false;
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/kernel/pdf/tagging/PdfMcr.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */