/*    */ package com.itextpdf.forms.xfa;
/*    */ 
/*    */ import java.util.Collection;
/*    */ import java.util.HashMap;
/*    */ import java.util.Map;
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
/*    */ class AcroFieldsSearch
/*    */   extends Xml2Som
/*    */ {
/* 63 */   private Map<String, String> acroShort2LongName = new HashMap<>(); public AcroFieldsSearch(Collection<String> items) {
/* 64 */     for (String itemName : items) {
/* 65 */       String itemShort = getShortName(itemName);
/* 66 */       this.acroShort2LongName.put(itemShort, itemName);
/* 67 */       inverseSearchAdd(this.inverseSearch, splitParts(itemShort), itemName);
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Map<String, String> getAcroShort2LongName() {
/* 78 */     return this.acroShort2LongName;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void setAcroShort2LongName(Map<String, String> acroShort2LongName) {
/* 88 */     this.acroShort2LongName = acroShort2LongName;
/*    */   }
/*    */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/forms/xfa/AcroFieldsSearch.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */