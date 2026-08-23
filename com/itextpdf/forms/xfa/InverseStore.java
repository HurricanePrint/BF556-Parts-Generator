/*    */ package com.itextpdf.forms.xfa;
/*    */ 
/*    */ import java.util.ArrayList;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ class InverseStore
/*    */ {
/* 54 */   protected List<String> part = new ArrayList<>();
/* 55 */   protected List<Object> follow = new ArrayList();
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getDefaultName() {
/* 64 */     InverseStore store = this;
/*    */     while (true) {
/* 66 */       Object obj = store.follow.get(0);
/* 67 */       if (obj instanceof String)
/* 68 */         return (String)obj; 
/* 69 */       store = (InverseStore)obj;
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean isSimilar(String name) {
/* 83 */     int idx = name.indexOf('[');
/* 84 */     name = name.substring(0, idx + 1);
/* 85 */     for (int k = 0; k < this.part.size(); k++) {
/* 86 */       if (((String)this.part.get(k)).startsWith(name))
/* 87 */         return true; 
/*    */     } 
/* 89 */     return false;
/*    */   }
/*    */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/forms/xfa/InverseStore.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */