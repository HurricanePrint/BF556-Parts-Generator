/*    */ package com.itextpdf.kernel.xmp.impl;
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
/*    */ public class QName
/*    */ {
/*    */   private String prefix;
/*    */   private String localName;
/*    */   
/*    */   public QName(String qname) {
/* 50 */     int colon = qname.indexOf(':');
/*    */     
/* 52 */     if (colon >= 0) {
/*    */       
/* 54 */       this.prefix = qname.substring(0, colon);
/* 55 */       this.localName = qname.substring(colon + 1);
/*    */     }
/*    */     else {
/*    */       
/* 59 */       this.prefix = "";
/* 60 */       this.localName = qname;
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public QName(String prefix, String localName) {
/* 71 */     this.prefix = prefix;
/* 72 */     this.localName = localName;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean hasPrefix() {
/* 81 */     return (this.prefix != null && this.prefix.length() > 0);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getLocalName() {
/* 90 */     return this.localName;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getPrefix() {
/* 99 */     return this.prefix;
/*    */   }
/*    */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/kernel/xmp/impl/QName.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */