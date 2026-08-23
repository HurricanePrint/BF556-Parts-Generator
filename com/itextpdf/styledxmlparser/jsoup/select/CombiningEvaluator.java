/*     */ package com.itextpdf.styledxmlparser.jsoup.select;
/*     */ 
/*     */ import com.itextpdf.io.util.MessageFormatUtil;
/*     */ import com.itextpdf.styledxmlparser.jsoup.helper.StringUtil;
/*     */ import com.itextpdf.styledxmlparser.jsoup.nodes.Element;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.Collection;
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
/*     */ abstract class CombiningEvaluator
/*     */   extends Evaluator
/*     */ {
/*     */   final ArrayList<Evaluator> evaluators;
/*  58 */   int num = 0;
/*     */ 
/*     */   
/*     */   CombiningEvaluator() {
/*  62 */     this.evaluators = new ArrayList<>();
/*     */   }
/*     */   
/*     */   CombiningEvaluator(Collection<Evaluator> evaluators) {
/*  66 */     this();
/*  67 */     this.evaluators.addAll(evaluators);
/*  68 */     updateNumEvaluators();
/*     */   }
/*     */   
/*     */   Evaluator rightMostEvaluator() {
/*  72 */     return (this.num > 0) ? this.evaluators.get(this.num - 1) : null;
/*     */   }
/*     */   
/*     */   void replaceRightMostEvaluator(Evaluator replacement) {
/*  76 */     this.evaluators.set(this.num - 1, replacement);
/*     */   }
/*     */ 
/*     */   
/*     */   void updateNumEvaluators() {
/*  81 */     this.num = this.evaluators.size();
/*     */   }
/*     */   
/*     */   static final class And extends CombiningEvaluator {
/*     */     And(Collection<Evaluator> evaluators) {
/*  86 */       super(evaluators);
/*     */     }
/*     */     
/*     */     And(Evaluator... evaluators) {
/*  90 */       this(Arrays.asList(evaluators));
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean matches(Element root, Element node) {
/*  95 */       for (int i = 0; i < this.num; i++) {
/*  96 */         Evaluator s = this.evaluators.get(i);
/*  97 */         if (!s.matches(root, node))
/*  98 */           return false; 
/*     */       } 
/* 100 */       return true;
/*     */     }
/*     */ 
/*     */     
/*     */     public String toString() {
/* 105 */       return StringUtil.join(this.evaluators, " ");
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static final class Or
/*     */     extends CombiningEvaluator
/*     */   {
/*     */     Or(Collection<Evaluator> evaluators) {
/* 116 */       if (this.num > 1) {
/* 117 */         this.evaluators.add(new CombiningEvaluator.And(evaluators));
/*     */       } else {
/* 119 */         this.evaluators.addAll(evaluators);
/* 120 */       }  updateNumEvaluators();
/*     */     }
/*     */ 
/*     */     
/*     */     Or() {}
/*     */ 
/*     */     
/*     */     public void add(Evaluator e) {
/* 128 */       this.evaluators.add(e);
/* 129 */       updateNumEvaluators();
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean matches(Element root, Element node) {
/* 134 */       for (int i = 0; i < this.num; i++) {
/* 135 */         Evaluator s = this.evaluators.get(i);
/* 136 */         if (s.matches(root, node))
/* 137 */           return true; 
/*     */       } 
/* 139 */       return false;
/*     */     }
/*     */ 
/*     */     
/*     */     public String toString() {
/* 144 */       return MessageFormatUtil.format(":or{0}", new Object[] { this.evaluators });
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/styledxmlparser/jsoup/select/CombiningEvaluator.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */