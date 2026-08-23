/*     */ package com.itextpdf.kernel.pdf;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ import java.util.Arrays;
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
/*     */ public class PdfNumTree
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 2636796232945164670L;
/*     */   private static final int NODE_SIZE = 40;
/*     */   private PdfCatalog catalog;
/*  59 */   private Map<Integer, PdfObject> items = new HashMap<>();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private PdfName treeType;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PdfNumTree(PdfCatalog catalog, PdfName treeType) {
/*  70 */     this.treeType = treeType;
/*  71 */     this.catalog = catalog;
/*     */   }
/*     */ 
/*     */   
/*     */   public Map<Integer, PdfObject> getNumbers() {
/*  76 */     if (this.items.size() > 0) {
/*  77 */       return this.items;
/*     */     }
/*     */     
/*  80 */     PdfDictionary numbers = null;
/*  81 */     if (this.treeType.equals(PdfName.PageLabels)) {
/*  82 */       numbers = this.catalog.getPdfObject().getAsDictionary(PdfName.PageLabels);
/*  83 */     } else if (this.treeType.equals(PdfName.ParentTree)) {
/*  84 */       PdfDictionary structTreeRoot = this.catalog.getPdfObject().getAsDictionary(PdfName.StructTreeRoot);
/*  85 */       if (structTreeRoot != null) {
/*  86 */         numbers = structTreeRoot.getAsDictionary(PdfName.ParentTree);
/*     */       }
/*     */     } 
/*     */     
/*  90 */     if (numbers != null) {
/*  91 */       readTree(numbers);
/*     */     }
/*     */     
/*  94 */     return this.items;
/*     */   }
/*     */   public void addEntry(int key, PdfObject value) {
/*  97 */     this.items.put(new Integer(key), value);
/*     */   }
/*     */   public PdfDictionary buildTree() {
/* 100 */     Integer[] numbers = new Integer[this.items.size()];
/* 101 */     numbers = (Integer[])this.items.keySet().toArray((Object[])numbers);
/* 102 */     Arrays.sort((Object[])numbers);
/* 103 */     if (numbers.length <= 40) {
/* 104 */       PdfDictionary dic = new PdfDictionary();
/* 105 */       PdfArray ar = new PdfArray();
/* 106 */       for (int k = 0; k < numbers.length; k++) {
/* 107 */         ar.add(new PdfNumber(numbers[k].intValue()));
/* 108 */         ar.add(this.items.get(numbers[k]));
/*     */       } 
/* 110 */       dic.put(PdfName.Nums, ar);
/* 111 */       return dic;
/*     */     } 
/* 113 */     int skip = 40;
/* 114 */     PdfDictionary[] kids = new PdfDictionary[(numbers.length + 40 - 1) / 40];
/* 115 */     for (int i = 0; i < kids.length; i++) {
/* 116 */       int offset = i * 40;
/* 117 */       int end = Math.min(offset + 40, numbers.length);
/* 118 */       PdfDictionary dic = new PdfDictionary();
/* 119 */       PdfArray arr = new PdfArray();
/* 120 */       arr.add(new PdfNumber(numbers[offset].intValue()));
/* 121 */       arr.add(new PdfNumber(numbers[end - 1].intValue()));
/* 122 */       dic.put(PdfName.Limits, arr);
/* 123 */       arr = new PdfArray();
/* 124 */       for (; offset < end; offset++) {
/* 125 */         arr.add(new PdfNumber(numbers[offset].intValue()));
/* 126 */         arr.add(this.items.get(numbers[offset]));
/*     */       } 
/* 128 */       dic.put(PdfName.Nums, arr);
/* 129 */       dic.makeIndirect(this.catalog.getDocument());
/* 130 */       kids[i] = dic;
/*     */     } 
/* 132 */     int top = kids.length;
/*     */     while (true) {
/* 134 */       if (top <= 40) {
/* 135 */         PdfArray arr = new PdfArray();
/* 136 */         for (int j = 0; j < top; j++)
/* 137 */           arr.add(kids[j]); 
/* 138 */         PdfDictionary dic = new PdfDictionary();
/* 139 */         dic.put(PdfName.Kids, arr);
/* 140 */         return dic;
/*     */       } 
/* 142 */       skip *= 40;
/* 143 */       int tt = (numbers.length + skip - 1) / skip;
/* 144 */       for (int k = 0; k < tt; k++) {
/* 145 */         int offset = k * 40;
/* 146 */         int end = Math.min(offset + 40, top);
/* 147 */         PdfDictionary dic = (PdfDictionary)(new PdfDictionary()).makeIndirect(this.catalog.getDocument());
/* 148 */         PdfArray arr = new PdfArray();
/* 149 */         arr.add(new PdfNumber(numbers[k * skip].intValue()));
/* 150 */         arr.add(new PdfNumber(numbers[Math.min((k + 1) * skip, numbers.length) - 1].intValue()));
/* 151 */         dic.put(PdfName.Limits, arr);
/* 152 */         arr = new PdfArray();
/* 153 */         for (; offset < end; offset++) {
/* 154 */           arr.add(kids[offset]);
/*     */         }
/* 156 */         dic.put(PdfName.Kids, arr);
/* 157 */         kids[k] = dic;
/*     */       } 
/* 159 */       top = tt;
/*     */     } 
/*     */   }
/*     */   
/*     */   private void readTree(PdfDictionary dictionary) {
/* 164 */     if (dictionary != null) {
/* 165 */       iterateItems(dictionary, null);
/*     */     }
/*     */   }
/*     */   
/*     */   private PdfNumber iterateItems(PdfDictionary dictionary, PdfNumber leftOver) {
/* 170 */     PdfArray nums = dictionary.getAsArray(PdfName.Nums);
/* 171 */     if (nums != null) {
/* 172 */       for (int k = 0; k < nums.size(); k++) {
/*     */         PdfNumber number;
/* 174 */         if (leftOver == null) {
/* 175 */           number = nums.getAsNumber(k++);
/*     */         } else {
/* 177 */           number = leftOver;
/* 178 */           leftOver = null;
/*     */         } 
/* 180 */         if (k < nums.size()) {
/* 181 */           this.items.put(Integer.valueOf(number.intValue()), nums.get(k));
/*     */         } else {
/* 183 */           return number;
/*     */         } 
/*     */       } 
/* 186 */     } else if ((nums = dictionary.getAsArray(PdfName.Kids)) != null) {
/* 187 */       for (int k = 0; k < nums.size(); k++) {
/* 188 */         PdfDictionary kid = nums.getAsDictionary(k);
/* 189 */         leftOver = iterateItems(kid, leftOver);
/*     */       } 
/*     */     } 
/* 192 */     return null;
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/kernel/pdf/PdfNumTree.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */