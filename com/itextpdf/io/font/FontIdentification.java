/*    */ package com.itextpdf.io.font;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ public class FontIdentification
/*    */   implements Serializable
/*    */ {
/*    */   private static final long serialVersionUID = -6017656004487895604L;
/*    */   private String ttfVersion;
/*    */   private String ttfUniqueId;
/*    */   private Integer type1Xuid;
/*    */   private String panose;
/*    */   
/*    */   public String getTtfVersion() {
/* 62 */     return this.ttfVersion;
/*    */   }
/*    */   
/*    */   public String getTtfUniqueId() {
/* 66 */     return this.ttfUniqueId;
/*    */   }
/*    */   
/*    */   public Integer getType1Xuid() {
/* 70 */     return this.type1Xuid;
/*    */   }
/*    */   
/*    */   public String getPanose() {
/* 74 */     return this.panose;
/*    */   }
/*    */   
/*    */   protected void setTtfVersion(String ttfVersion) {
/* 78 */     this.ttfVersion = ttfVersion;
/*    */   }
/*    */   
/*    */   protected void setTtfUniqueId(String ttfUniqueId) {
/* 82 */     this.ttfUniqueId = ttfUniqueId;
/*    */   }
/*    */   
/*    */   protected void setType1Xuid(Integer type1Xuid) {
/* 86 */     this.type1Xuid = type1Xuid;
/*    */   }
/*    */   
/*    */   protected void setPanose(byte[] panose) {
/* 90 */     this.panose = new String(panose);
/*    */   }
/*    */   
/*    */   protected void setPanose(String panose) {
/* 94 */     this.panose = panose;
/*    */   }
/*    */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/io/font/FontIdentification.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */