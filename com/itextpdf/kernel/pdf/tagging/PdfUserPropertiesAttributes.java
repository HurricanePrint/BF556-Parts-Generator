/*    */ package com.itextpdf.kernel.pdf.tagging;
/*    */ 
/*    */ import com.itextpdf.kernel.pdf.PdfArray;
/*    */ import com.itextpdf.kernel.pdf.PdfDictionary;
/*    */ import com.itextpdf.kernel.pdf.PdfName;
/*    */ import com.itextpdf.kernel.pdf.PdfObject;
/*    */ import java.util.List;
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
/*    */ public class PdfUserPropertiesAttributes
/*    */   extends PdfStructureAttributes
/*    */ {
/*    */   private static final long serialVersionUID = -3680551925943527773L;
/*    */   
/*    */   public PdfUserPropertiesAttributes(PdfDictionary attributesDict) {
/* 54 */     super(attributesDict);
/*    */   }
/*    */   
/*    */   public PdfUserPropertiesAttributes() {
/* 58 */     super(new PdfDictionary());
/* 59 */     ((PdfDictionary)getPdfObject()).put(PdfName.O, (PdfObject)PdfName.UserProperties);
/* 60 */     ((PdfDictionary)getPdfObject()).put(PdfName.P, (PdfObject)new PdfArray());
/*    */   }
/*    */   
/*    */   public PdfUserPropertiesAttributes(List<PdfUserProperty> userProperties) {
/* 64 */     this();
/* 65 */     PdfArray arr = ((PdfDictionary)getPdfObject()).getAsArray(PdfName.P);
/* 66 */     for (PdfUserProperty userProperty : userProperties) {
/* 67 */       arr.add(userProperty.getPdfObject());
/*    */     }
/*    */   }
/*    */   
/*    */   public PdfUserPropertiesAttributes addUserProperty(PdfUserProperty userProperty) {
/* 72 */     ((PdfDictionary)getPdfObject()).getAsArray(PdfName.P).add(userProperty.getPdfObject());
/* 73 */     setModified();
/* 74 */     return this;
/*    */   }
/*    */   
/*    */   public PdfUserProperty getUserProperty(int i) {
/* 78 */     PdfDictionary propDict = ((PdfDictionary)getPdfObject()).getAsArray(PdfName.P).getAsDictionary(i);
/* 79 */     if (propDict == null) {
/* 80 */       return null;
/*    */     }
/* 82 */     return new PdfUserProperty(propDict);
/*    */   }
/*    */   
/*    */   public PdfUserPropertiesAttributes removeUserProperty(int i) {
/* 86 */     ((PdfDictionary)getPdfObject()).getAsArray(PdfName.P).remove(i);
/* 87 */     return this;
/*    */   }
/*    */   
/*    */   public int getNumberOfUserProperties() {
/* 91 */     return ((PdfDictionary)getPdfObject()).getAsArray(PdfName.P).size();
/*    */   }
/*    */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/kernel/pdf/tagging/PdfUserPropertiesAttributes.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */