/*    */ package com.itextpdf.layout.tagging;
/*    */ 
/*    */ import com.itextpdf.kernel.pdf.PdfDocument;
/*    */ import com.itextpdf.kernel.pdf.tagutils.TagTreePointer;
/*    */ import com.itextpdf.kernel.pdf.tagutils.WaitingTagsManager;
/*    */ import java.util.HashSet;
/*    */ import java.util.Set;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ class TableTaggingPriorToOneFiveVersionRule
/*    */   implements ITaggingRule
/*    */ {
/* 53 */   private Set<TaggingHintKey> finishForbidden = new HashSet<>();
/*    */ 
/*    */   
/*    */   public boolean onTagFinish(LayoutTaggingHelper taggingHelper, TaggingHintKey taggingHintKey) {
/* 57 */     if (taggingHintKey.getAccessibleElement() != null) {
/* 58 */       String role = taggingHintKey.getAccessibleElement().getAccessibilityProperties().getRole();
/* 59 */       if ("THead".equals(role) || "TFoot".equals(role)) {
/* 60 */         this.finishForbidden.add(taggingHintKey);
/* 61 */         return false;
/*    */       } 
/*    */     } 
/*    */     
/* 65 */     for (TaggingHintKey hint : taggingHelper.getAccessibleKidsHint(taggingHintKey)) {
/* 66 */       String role = hint.getAccessibleElement().getAccessibilityProperties().getRole();
/* 67 */       if ("TBody".equals(role) || "THead".equals(role) || "TFoot".equals(role))
/*    */       {
/* 69 */         removeTagUnavailableInPriorToOneDotFivePdf(hint, taggingHelper);
/*    */       }
/*    */     } 
/* 72 */     return true;
/*    */   }
/*    */   
/*    */   private void removeTagUnavailableInPriorToOneDotFivePdf(TaggingHintKey taggingHintKey, LayoutTaggingHelper taggingHelper) {
/* 76 */     taggingHelper.replaceKidHint(taggingHintKey, taggingHelper.getAccessibleKidsHint(taggingHintKey));
/* 77 */     PdfDocument pdfDocument = taggingHelper.getPdfDocument();
/* 78 */     WaitingTagsManager waitingTagsManager = pdfDocument.getTagStructureContext().getWaitingTagsManager();
/* 79 */     TagTreePointer tagPointer = new TagTreePointer(pdfDocument);
/* 80 */     if (waitingTagsManager.tryMovePointerToWaitingTag(tagPointer, taggingHintKey)) {
/* 81 */       waitingTagsManager.removeWaitingState(taggingHintKey);
/* 82 */       tagPointer.removeTag();
/*    */     } 
/* 84 */     if (this.finishForbidden.remove(taggingHintKey))
/* 85 */       taggingHintKey.setFinished(); 
/*    */   }
/*    */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/layout/tagging/TableTaggingPriorToOneFiveVersionRule.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */