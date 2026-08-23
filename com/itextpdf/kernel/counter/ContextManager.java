/*     */ package com.itextpdf.kernel.counter;
/*     */ 
/*     */ import com.itextpdf.kernel.counter.context.GenericContext;
/*     */ import com.itextpdf.kernel.counter.context.IContext;
/*     */ import java.util.Arrays;
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import java.util.Comparator;
/*     */ import java.util.SortedMap;
/*     */ import java.util.TreeMap;
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
/*     */ public class ContextManager
/*     */ {
/*  61 */   private static final ContextManager instance = new ContextManager();
/*     */   
/*  63 */   private final SortedMap<String, IContext> contextMappings = new TreeMap<>(new LengthComparator());
/*     */   
/*     */   private ContextManager() {
/*  66 */     registerGenericContext(Arrays.asList(new String[] { "com.itextpdf.io", "com.itextpdf.kernel", "com.itextpdf.layout", "com.itextpdf.barcodes", "com.itextpdf.pdfa", "com.itextpdf.signatures", "com.itextpdf.forms", "com.itextpdf.styledxmlparser", "com.itextpdf.svg"
/*     */           },
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*  75 */         ), Collections.singletonList("com.itextpdf"));
/*  76 */     registerGenericContext(Collections.singletonList("com.itextpdf.pdfdebug"), 
/*  77 */         Collections.singletonList("com.itextpdf.pdfdebug"));
/*  78 */     registerGenericContext(Collections.singletonList("com.itextpdf.html2pdf"), 
/*  79 */         Collections.singletonList("com.itextpdf.html2pdf"));
/*  80 */     registerGenericContext(Collections.singletonList("com.itextpdf.zugferd"), 
/*  81 */         Collections.singletonList("com.itextpdf.zugferd"));
/*  82 */     registerGenericContext(Collections.singletonList("com.itextpdf.pdfcleanup"), 
/*  83 */         Collections.singletonList("com.itextpdf.pdfcleanup"));
/*  84 */     registerGenericContext(Collections.singletonList("com.itextpdf.pdfocr.tesseract4"), 
/*  85 */         Collections.singletonList("com.itextpdf.pdfocr.tesseract4"));
/*  86 */     registerGenericContext(Collections.singletonList("com.itextpdf.pdfocr"), Collections.emptyList());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ContextManager getInstance() {
/*  95 */     return instance;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IContext getContext(Class<?> clazz) {
/* 106 */     return (clazz != null) ? getContext(clazz.getName()) : null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IContext getContext(String className) {
/* 117 */     return getNamespaceMapping(getRecognisedNamespace(className));
/*     */   }
/*     */   
/*     */   String getRecognisedNamespace(String className) {
/* 121 */     if (className != null)
/*     */     {
/*     */ 
/*     */       
/* 125 */       for (String namespace : this.contextMappings.keySet()) {
/*     */         
/* 127 */         if (className.toLowerCase().startsWith(namespace)) {
/* 128 */           return namespace;
/*     */         }
/*     */       } 
/*     */     }
/* 132 */     return null;
/*     */   }
/*     */   
/*     */   private IContext getNamespaceMapping(String namespace) {
/* 136 */     if (namespace != null) {
/* 137 */       return this.contextMappings.get(namespace);
/*     */     }
/* 139 */     return null;
/*     */   }
/*     */   
/*     */   private void registerGenericContext(Collection<String> namespaces, Collection<String> eventIds) {
/* 143 */     GenericContext context = new GenericContext(eventIds);
/* 144 */     for (String namespace : namespaces)
/*     */     {
/* 146 */       registerContext(namespace.toLowerCase(), (IContext)context);
/*     */     }
/*     */   }
/*     */   
/*     */   private void registerContext(String namespace, IContext context) {
/* 151 */     this.contextMappings.put(namespace, context);
/*     */   }
/*     */   
/*     */   private static class LengthComparator
/*     */     implements Comparator<String> {
/*     */     public int compare(String o1, String o2) {
/* 157 */       int lengthComparison = -Integer.compare(o1.length(), o2.length());
/* 158 */       if (0 != lengthComparison) {
/* 159 */         return lengthComparison;
/*     */       }
/* 161 */       return o1.compareTo(o2);
/*     */     }
/*     */     
/*     */     private LengthComparator() {}
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/kernel/counter/ContextManager.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */