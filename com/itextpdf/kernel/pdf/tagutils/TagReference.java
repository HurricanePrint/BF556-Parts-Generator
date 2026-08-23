/*    */ package com.itextpdf.kernel.pdf.tagutils;
/*    */ 
/*    */ import com.itextpdf.kernel.pdf.PdfDictionary;
/*    */ import com.itextpdf.kernel.pdf.PdfName;
/*    */ import com.itextpdf.kernel.pdf.PdfObject;
/*    */ import com.itextpdf.kernel.pdf.tagging.PdfStructElem;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class TagReference
/*    */ {
/*    */   protected TagTreePointer tagPointer;
/*    */   protected int insertIndex;
/*    */   protected PdfStructElem referencedTag;
/*    */   protected PdfName role;
/*    */   protected PdfDictionary properties;
/*    */   
/*    */   protected TagReference(PdfStructElem referencedTag, TagTreePointer tagPointer, int insertIndex) {
/* 60 */     this.role = referencedTag.getRole();
/* 61 */     this.referencedTag = referencedTag;
/* 62 */     this.tagPointer = tagPointer;
/* 63 */     this.insertIndex = insertIndex;
/*    */   }
/*    */   
/*    */   public PdfName getRole() {
/* 67 */     return this.role;
/*    */   }
/*    */   
/*    */   public int createNextMcid() {
/* 71 */     return this.tagPointer.createNextMcidForStructElem(this.referencedTag, this.insertIndex);
/*    */   }
/*    */   
/*    */   public TagReference addProperty(PdfName name, PdfObject value) {
/* 75 */     if (this.properties == null) {
/* 76 */       this.properties = new PdfDictionary();
/*    */     }
/*    */     
/* 79 */     this.properties.put(name, value);
/* 80 */     return this;
/*    */   }
/*    */   
/*    */   public TagReference removeProperty(PdfName name) {
/* 84 */     if (this.properties != null) {
/* 85 */       this.properties.remove(name);
/*    */     }
/* 87 */     return this;
/*    */   }
/*    */   
/*    */   public PdfObject getProperty(PdfName name) {
/* 91 */     if (this.properties == null) {
/* 92 */       return null;
/*    */     }
/* 94 */     return this.properties.get(name);
/*    */   }
/*    */   
/*    */   public PdfDictionary getProperties() {
/* 98 */     return this.properties;
/*    */   }
/*    */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/kernel/pdf/tagutils/TagReference.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */