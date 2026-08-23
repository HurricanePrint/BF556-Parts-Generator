/*    */ package com.itextpdf.kernel.pdf;
/*    */ 
/*    */ import com.itextpdf.kernel.counter.event.IMetaInfo;
/*    */ import java.io.Serializable;
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
/*    */ public class DocumentProperties
/*    */   implements Serializable
/*    */ {
/*    */   private static final long serialVersionUID = -6625621282242153134L;
/* 54 */   protected IMetaInfo metaInfo = null;
/*    */ 
/*    */   
/*    */   public DocumentProperties() {}
/*    */   
/*    */   public DocumentProperties(DocumentProperties other) {
/* 60 */     this.metaInfo = other.metaInfo;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public DocumentProperties setEventCountingMetaInfo(IMetaInfo metaInfo) {
/* 71 */     this.metaInfo = metaInfo;
/* 72 */     return this;
/*    */   }
/*    */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/kernel/pdf/DocumentProperties.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */