/*     */ package com.itextpdf.styledxmlparser.jsoup.select;
/*     */ 
/*     */ import com.itextpdf.io.util.MessageFormatUtil;
/*     */ import com.itextpdf.styledxmlparser.jsoup.helper.Validate;
/*     */ import com.itextpdf.styledxmlparser.jsoup.nodes.Element;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.IdentityHashMap;
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
/*     */ public class Selector
/*     */ {
/*     */   private final Evaluator evaluator;
/*     */   private final Element root;
/*     */   
/*     */   private Selector(String query, Element root) {
/* 120 */     Validate.notNull(query);
/* 121 */     query = query.trim();
/* 122 */     Validate.notEmpty(query);
/* 123 */     Validate.notNull(root);
/*     */     
/* 125 */     this.evaluator = QueryParser.parse(query);
/*     */     
/* 127 */     this.root = root;
/*     */   }
/*     */   
/*     */   private Selector(Evaluator evaluator, Element root) {
/* 131 */     Validate.notNull(evaluator);
/* 132 */     Validate.notNull(root);
/*     */     
/* 134 */     this.evaluator = evaluator;
/* 135 */     this.root = root;
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
/*     */   public static Elements select(String query, Element root) {
/* 147 */     return (new Selector(query, root)).select();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Elements select(Evaluator evaluator, Element root) {
/* 158 */     return (new Selector(evaluator, root)).select();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Elements select(String query, Iterable<Element> roots) {
/* 169 */     Validate.notEmpty(query);
/* 170 */     Validate.notNull(roots);
/* 171 */     Evaluator evaluator = QueryParser.parse(query);
/* 172 */     ArrayList<Element> elements = new ArrayList<>();
/* 173 */     IdentityHashMap<Element, Boolean> seenElements = new IdentityHashMap<>();
/*     */ 
/*     */     
/* 176 */     for (Element root : roots) {
/* 177 */       Elements found = select(evaluator, root);
/* 178 */       for (Element el : found) {
/* 179 */         if (!seenElements.containsKey(el)) {
/* 180 */           elements.add(el);
/* 181 */           seenElements.put(el, Boolean.TRUE);
/*     */         } 
/*     */       } 
/*     */     } 
/* 185 */     return new Elements(elements);
/*     */   }
/*     */   
/*     */   private Elements select() {
/* 189 */     return Collector.collect(this.evaluator, this.root);
/*     */   }
/*     */ 
/*     */   
/*     */   static Elements filterOut(Collection<Element> elements, Collection<Element> outs) {
/* 194 */     Elements output = new Elements();
/* 195 */     for (Element el : elements) {
/* 196 */       boolean found = false;
/* 197 */       for (Element out : outs) {
/* 198 */         if (el.equals(out)) {
/* 199 */           found = true;
/*     */           break;
/*     */         } 
/*     */       } 
/* 203 */       if (!found)
/* 204 */         output.add(el); 
/*     */     } 
/* 206 */     return output;
/*     */   }
/*     */   
/*     */   public static class SelectorParseException extends IllegalStateException {
/*     */     public SelectorParseException(String msg, Object... params) {
/* 211 */       super(MessageFormatUtil.format(msg, params));
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/styledxmlparser/jsoup/select/Selector.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */