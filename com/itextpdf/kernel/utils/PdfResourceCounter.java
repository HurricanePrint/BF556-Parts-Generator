/*     */ package com.itextpdf.kernel.utils;
/*     */ 
/*     */ import com.itextpdf.kernel.pdf.PdfArray;
/*     */ import com.itextpdf.kernel.pdf.PdfDictionary;
/*     */ import com.itextpdf.kernel.pdf.PdfIndirectReference;
/*     */ import com.itextpdf.kernel.pdf.PdfName;
/*     */ import com.itextpdf.kernel.pdf.PdfObject;
/*     */ import com.itextpdf.kernel.pdf.PdfOutputStream;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
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
/*     */ public class PdfResourceCounter
/*     */ {
/*     */   private Map<Integer, PdfObject> resources;
/*     */   
/*     */   public PdfResourceCounter(PdfObject obj) {
/*  75 */     this.resources = new HashMap<>();
/*  76 */     process(obj);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected final void process(PdfObject obj) {
/*  86 */     PdfIndirectReference ref = obj.getIndirectReference();
/*  87 */     if (ref == null) {
/*  88 */       loopOver(obj);
/*  89 */     } else if (!this.resources.containsKey(Integer.valueOf(ref.getObjNumber()))) {
/*  90 */       this.resources.put(Integer.valueOf(ref.getObjNumber()), obj);
/*  91 */       loopOver(obj);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected final void loopOver(PdfObject obj) {
/*     */     PdfArray array;
/*     */     int i;
/*     */     PdfDictionary dict;
/* 102 */     switch (obj.getType()) {
/*     */       case 1:
/* 104 */         array = (PdfArray)obj;
/*     */         
/* 106 */         for (i = 0; i < array.size(); i++) {
/* 107 */           process(array.get(i));
/*     */         }
/*     */         break;
/*     */       
/*     */       case 3:
/*     */       case 9:
/* 113 */         dict = (PdfDictionary)obj;
/*     */         
/* 115 */         if (PdfName.Pages.equals(dict.get(PdfName.Type))) {
/*     */           break;
/*     */         }
/*     */         
/* 119 */         for (PdfName name : dict.keySet()) {
/* 120 */           process(dict.get(name));
/*     */         }
/*     */         break;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Map<Integer, PdfObject> getResources() {
/* 133 */     return this.resources;
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
/*     */   public long getLength(Map<Integer, PdfObject> res) {
/* 146 */     long length = 0L;
/*     */     
/* 148 */     for (Iterator<Integer> iterator = this.resources.keySet().iterator(); iterator.hasNext(); ) { int ref = ((Integer)iterator.next()).intValue();
/* 149 */       if (res != null && res.containsKey(Integer.valueOf(ref))) {
/*     */         continue;
/*     */       }
/*     */       
/* 153 */       PdfOutputStream os = new PdfOutputStream(new IdleOutputStream());
/*     */       
/* 155 */       os.write(((PdfObject)this.resources.get(Integer.valueOf(ref))).clone());
/* 156 */       length += os.getCurrentPos(); }
/*     */ 
/*     */     
/* 159 */     return length;
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/kernel/utils/PdfResourceCounter.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */