/*    */ package com.itextpdf.layout.hyphenation;
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
/*    */ 
/*    */ public class Hyphen
/*    */   implements Serializable
/*    */ {
/*    */   private static final long serialVersionUID = 8989909741110279085L;
/*    */   public String preBreak;
/*    */   public String noBreak;
/*    */   public String postBreak;
/*    */   
/*    */   Hyphen(String pre, String no, String post) {
/* 58 */     this.preBreak = pre;
/* 59 */     this.noBreak = no;
/* 60 */     this.postBreak = post;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   Hyphen(String pre) {
/* 68 */     this.preBreak = pre;
/* 69 */     this.noBreak = null;
/* 70 */     this.postBreak = null;
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 75 */     if (this.noBreak == null && this.postBreak == null && this.preBreak != null && this.preBreak
/*    */ 
/*    */       
/* 78 */       .equals("-")) {
/* 79 */       return "-";
/*    */     }
/* 81 */     StringBuffer res = new StringBuffer("{");
/* 82 */     res.append(this.preBreak);
/* 83 */     res.append("}{");
/* 84 */     res.append(this.postBreak);
/* 85 */     res.append("}{");
/* 86 */     res.append(this.noBreak);
/* 87 */     res.append('}');
/* 88 */     return res.toString();
/*    */   }
/*    */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/layout/hyphenation/Hyphen.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */