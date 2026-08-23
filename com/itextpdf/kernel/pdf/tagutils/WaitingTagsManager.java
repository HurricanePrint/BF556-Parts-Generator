/*     */ package com.itextpdf.kernel.pdf.tagutils;
/*     */ 
/*     */ import com.itextpdf.kernel.pdf.PdfDictionary;
/*     */ import com.itextpdf.kernel.pdf.tagging.IStructureNode;
/*     */ import com.itextpdf.kernel.pdf.tagging.PdfStructElem;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class WaitingTagsManager
/*     */ {
/*  68 */   private Map<Object, PdfStructElem> associatedObjToWaitingTag = new HashMap<>();
/*  69 */   private Map<PdfDictionary, Object> waitingTagToAssociatedObj = new HashMap<>();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object assignWaitingState(TagTreePointer pointerToTag, Object associatedObj) {
/*  83 */     if (associatedObj == null) throw new IllegalArgumentException("Passed associated object can not be null."); 
/*  84 */     return saveAssociatedObjectForWaitingTag(associatedObj, pointerToTag.getCurrentStructElem());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isObjectAssociatedWithWaitingTag(Object obj) {
/*  93 */     if (obj == null) throw new IllegalArgumentException("Passed associated object can not be null."); 
/*  94 */     return this.associatedObjToWaitingTag.containsKey(obj);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean tryMovePointerToWaitingTag(TagTreePointer tagPointer, Object associatedObject) {
/* 107 */     if (associatedObject == null) return false;
/*     */     
/* 109 */     PdfStructElem waitingStructElem = this.associatedObjToWaitingTag.get(associatedObject);
/* 110 */     if (waitingStructElem != null) {
/* 111 */       tagPointer.setCurrentStructElem(waitingStructElem);
/* 112 */       return true;
/*     */     } 
/* 114 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean removeWaitingState(Object associatedObject) {
/* 139 */     if (associatedObject != null) {
/* 140 */       PdfStructElem structElem = this.associatedObjToWaitingTag.remove(associatedObject);
/* 141 */       removeWaitingStateAndFlushIfParentFlushed(structElem);
/* 142 */       return (structElem != null);
/*     */     } 
/* 144 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void removeAllWaitingStates() {
/* 154 */     for (PdfStructElem structElem : this.associatedObjToWaitingTag.values()) {
/* 155 */       removeWaitingStateAndFlushIfParentFlushed(structElem);
/*     */     }
/* 157 */     this.associatedObjToWaitingTag.clear();
/*     */   }
/*     */   
/*     */   PdfStructElem getStructForObj(Object associatedObj) {
/* 161 */     return this.associatedObjToWaitingTag.get(associatedObj);
/*     */   }
/*     */   
/*     */   Object getObjForStructDict(PdfDictionary structDict) {
/* 165 */     return this.waitingTagToAssociatedObj.get(structDict);
/*     */   }
/*     */   
/*     */   Object saveAssociatedObjectForWaitingTag(Object associatedObj, PdfStructElem structElem) {
/* 169 */     this.associatedObjToWaitingTag.put(associatedObj, structElem);
/* 170 */     return this.waitingTagToAssociatedObj.put(structElem.getPdfObject(), associatedObj);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   IStructureNode flushTag(PdfStructElem tagStruct) {
/* 177 */     Object associatedObj = this.waitingTagToAssociatedObj.remove(tagStruct.getPdfObject());
/* 178 */     if (associatedObj != null) {
/* 179 */       this.associatedObjToWaitingTag.remove(associatedObj);
/*     */     }
/*     */     
/* 182 */     IStructureNode parent = tagStruct.getParent();
/* 183 */     flushStructElementAndItKids(tagStruct);
/* 184 */     return parent;
/*     */   }
/*     */   
/*     */   private void flushStructElementAndItKids(PdfStructElem elem) {
/* 188 */     if (this.waitingTagToAssociatedObj.containsKey(elem.getPdfObject())) {
/*     */       return;
/*     */     }
/*     */     
/* 192 */     for (IStructureNode kid : elem.getKids()) {
/* 193 */       if (kid instanceof PdfStructElem) {
/* 194 */         flushStructElementAndItKids((PdfStructElem)kid);
/*     */       }
/*     */     } 
/* 197 */     elem.flush();
/*     */   }
/*     */   
/*     */   private void removeWaitingStateAndFlushIfParentFlushed(PdfStructElem structElem) {
/* 201 */     if (structElem != null) {
/* 202 */       this.waitingTagToAssociatedObj.remove(structElem.getPdfObject());
/* 203 */       IStructureNode parent = structElem.getParent();
/* 204 */       if (parent instanceof PdfStructElem && ((PdfStructElem)parent).isFlushed())
/* 205 */         flushStructElementAndItKids(structElem); 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/kernel/pdf/tagutils/WaitingTagsManager.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */