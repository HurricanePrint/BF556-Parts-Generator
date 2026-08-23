/*     */ package com.itextpdf.styledxmlparser.jsoup.select;
/*     */ 
/*     */ import com.itextpdf.io.util.MessageFormatUtil;
/*     */ import com.itextpdf.styledxmlparser.jsoup.nodes.Element;
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
/*     */ abstract class StructuralEvaluator
/*     */   extends Evaluator
/*     */ {
/*     */   Evaluator evaluator;
/*     */   
/*     */   static class Root
/*     */     extends Evaluator
/*     */   {
/*     */     public boolean matches(Element root, Element element) {
/*  57 */       return (root == element);
/*     */     }
/*     */   }
/*     */   
/*     */   static class Has extends StructuralEvaluator {
/*     */     public Has(Evaluator evaluator) {
/*  63 */       this.evaluator = evaluator;
/*     */     }
/*     */     
/*     */     public boolean matches(Element root, Element element) {
/*  67 */       for (Element e : element.getAllElements()) {
/*  68 */         if (e != element && this.evaluator.matches(root, e))
/*  69 */           return true; 
/*     */       } 
/*  71 */       return false;
/*     */     }
/*     */ 
/*     */     
/*     */     public String toString() {
/*  76 */       return MessageFormatUtil.format(":has({0})", new Object[] { this.evaluator });
/*     */     }
/*     */   }
/*     */   
/*     */   static class Not extends StructuralEvaluator {
/*     */     public Not(Evaluator evaluator) {
/*  82 */       this.evaluator = evaluator;
/*     */     }
/*     */     
/*     */     public boolean matches(Element root, Element node) {
/*  86 */       return !this.evaluator.matches(root, node);
/*     */     }
/*     */ 
/*     */     
/*     */     public String toString() {
/*  91 */       return MessageFormatUtil.format(":not{0}", new Object[] { this.evaluator });
/*     */     }
/*     */   }
/*     */   
/*     */   static class Parent extends StructuralEvaluator {
/*     */     public Parent(Evaluator evaluator) {
/*  97 */       this.evaluator = evaluator;
/*     */     }
/*     */     
/*     */     public boolean matches(Element root, Element element) {
/* 101 */       if (root == element) {
/* 102 */         return false;
/*     */       }
/* 104 */       Element parent = (Element)element.parent();
/*     */       while (true) {
/* 106 */         if (this.evaluator.matches(root, parent))
/* 107 */           return true; 
/* 108 */         if (parent == root)
/*     */           break; 
/* 110 */         parent = (Element)parent.parent();
/*     */       } 
/* 112 */       return false;
/*     */     }
/*     */ 
/*     */     
/*     */     public String toString() {
/* 117 */       return MessageFormatUtil.format(":parent{0}", new Object[] { this.evaluator });
/*     */     }
/*     */   }
/*     */   
/*     */   static class ImmediateParent extends StructuralEvaluator {
/*     */     public ImmediateParent(Evaluator evaluator) {
/* 123 */       this.evaluator = evaluator;
/*     */     }
/*     */     
/*     */     public boolean matches(Element root, Element element) {
/* 127 */       if (root == element) {
/* 128 */         return false;
/*     */       }
/* 130 */       Element parent = (Element)element.parent();
/* 131 */       return (parent != null && this.evaluator.matches(root, parent));
/*     */     }
/*     */ 
/*     */     
/*     */     public String toString() {
/* 136 */       return MessageFormatUtil.format(":ImmediateParent{0}", new Object[] { this.evaluator });
/*     */     }
/*     */   }
/*     */   
/*     */   static class PreviousSibling extends StructuralEvaluator {
/*     */     public PreviousSibling(Evaluator evaluator) {
/* 142 */       this.evaluator = evaluator;
/*     */     }
/*     */     
/*     */     public boolean matches(Element root, Element element) {
/* 146 */       if (root == element) {
/* 147 */         return false;
/*     */       }
/* 149 */       Element prev = element.previousElementSibling();
/*     */       
/* 151 */       while (prev != null) {
/* 152 */         if (this.evaluator.matches(root, prev)) {
/* 153 */           return true;
/*     */         }
/* 155 */         prev = prev.previousElementSibling();
/*     */       } 
/* 157 */       return false;
/*     */     }
/*     */ 
/*     */     
/*     */     public String toString() {
/* 162 */       return MessageFormatUtil.format(":prev*{0}", new Object[] { this.evaluator });
/*     */     }
/*     */   }
/*     */   
/*     */   static class ImmediatePreviousSibling extends StructuralEvaluator {
/*     */     public ImmediatePreviousSibling(Evaluator evaluator) {
/* 168 */       this.evaluator = evaluator;
/*     */     }
/*     */     
/*     */     public boolean matches(Element root, Element element) {
/* 172 */       if (root == element) {
/* 173 */         return false;
/*     */       }
/* 175 */       Element prev = element.previousElementSibling();
/* 176 */       return (prev != null && this.evaluator.matches(root, prev));
/*     */     }
/*     */ 
/*     */     
/*     */     public String toString() {
/* 181 */       return MessageFormatUtil.format(":prev{0}", new Object[] { this.evaluator });
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/styledxmlparser/jsoup/select/StructuralEvaluator.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */