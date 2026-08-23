/*    */ package com.itextpdf.kernel.pdf;
/*    */ 
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
/*    */ public class StampingProperties
/*    */   extends DocumentProperties
/*    */   implements Serializable
/*    */ {
/*    */   private static final long serialVersionUID = 6108082513101777457L;
/*    */   protected boolean appendMode = false;
/*    */   protected boolean preserveEncryption = false;
/*    */   
/*    */   public StampingProperties() {}
/*    */   
/*    */   public StampingProperties(StampingProperties other) {
/* 59 */     super(other);
/* 60 */     this.appendMode = other.appendMode;
/* 61 */     this.preserveEncryption = other.preserveEncryption;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public StampingProperties useAppendMode() {
/* 69 */     this.appendMode = true;
/* 70 */     return this;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public StampingProperties preserveEncryption() {
/* 79 */     this.preserveEncryption = true;
/* 80 */     return this;
/*    */   }
/*    */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/kernel/pdf/StampingProperties.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */