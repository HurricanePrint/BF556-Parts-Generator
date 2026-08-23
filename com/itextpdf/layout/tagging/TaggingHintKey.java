/*    */ package com.itextpdf.layout.tagging;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class TaggingHintKey
/*    */ {
/*    */   private IAccessibleElement elem;
/*    */   private boolean isArtifact;
/*    */   private boolean isFinished;
/*    */   private String overriddenRole;
/*    */   private boolean elementBasedFinishingOnly;
/*    */   
/*    */   TaggingHintKey(IAccessibleElement elem, boolean createdElementBased) {
/* 53 */     this.elem = elem;
/* 54 */     this.elementBasedFinishingOnly = createdElementBased;
/*    */   }
/*    */   
/*    */   public IAccessibleElement getAccessibleElement() {
/* 58 */     return this.elem;
/*    */   }
/*    */   
/*    */   boolean isFinished() {
/* 62 */     return this.isFinished;
/*    */   }
/*    */   
/*    */   void setFinished() {
/* 66 */     this.isFinished = true;
/*    */   }
/*    */   
/*    */   boolean isArtifact() {
/* 70 */     return this.isArtifact;
/*    */   }
/*    */   
/*    */   void setArtifact() {
/* 74 */     this.isArtifact = true;
/*    */   }
/*    */   
/*    */   String getOverriddenRole() {
/* 78 */     return this.overriddenRole;
/*    */   }
/*    */   
/*    */   void setOverriddenRole(String overriddenRole) {
/* 82 */     this.overriddenRole = overriddenRole;
/*    */   }
/*    */   
/*    */   boolean isElementBasedFinishingOnly() {
/* 86 */     return this.elementBasedFinishingOnly;
/*    */   }
/*    */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/layout/tagging/TaggingHintKey.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */