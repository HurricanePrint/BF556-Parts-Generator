/*     */ package com.itextpdf.forms.xfa;
/*     */ 
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Stack;
/*     */ import org.w3c.dom.Node;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class Xml2Som
/*     */ {
/*     */   protected List<String> order;
/*     */   protected Map<String, Node> name2Node;
/*     */   protected Map<String, InverseStore> inverseSearch;
/*     */   protected Stack<String> stack;
/*     */   protected int anform;
/*     */   
/*     */   public static String escapeSom(String s) {
/*  84 */     if (s == null)
/*  85 */       return ""; 
/*  86 */     int idx = s.indexOf('.');
/*  87 */     if (idx < 0)
/*  88 */       return s; 
/*  89 */     StringBuilder sb = new StringBuilder();
/*  90 */     int last = 0;
/*  91 */     while (idx >= 0) {
/*  92 */       sb.append(s.substring(last, idx));
/*  93 */       sb.append('\\');
/*  94 */       last = idx;
/*  95 */       idx = s.indexOf('.', idx + 1);
/*     */     } 
/*  97 */     sb.append(s.substring(last));
/*  98 */     return sb.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String unescapeSom(String s) {
/* 108 */     int idx = s.indexOf('\\');
/* 109 */     if (idx < 0)
/* 110 */       return s; 
/* 111 */     StringBuilder sb = new StringBuilder();
/* 112 */     int last = 0;
/* 113 */     while (idx >= 0) {
/* 114 */       sb.append(s.substring(last, idx));
/* 115 */       last = idx + 1;
/* 116 */       idx = s.indexOf('\\', idx + 1);
/*     */     } 
/* 118 */     sb.append(s.substring(last));
/* 119 */     return sb.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected String printStack() {
/* 129 */     if (this.stack.size() == 0) {
/* 130 */       return "";
/*     */     }
/* 132 */     StringBuilder s = new StringBuilder();
/* 133 */     for (int k = 0; k < this.stack.size(); k++)
/* 134 */       s.append('.').append(this.stack.get(k)); 
/* 135 */     return s.substring(1);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String getShortName(String s) {
/* 145 */     int idx = s.indexOf(".#subform[");
/* 146 */     if (idx < 0)
/* 147 */       return s; 
/* 148 */     int last = 0;
/* 149 */     StringBuilder sb = new StringBuilder();
/* 150 */     while (idx >= 0) {
/* 151 */       sb.append(s.substring(last, idx));
/* 152 */       idx = s.indexOf("]", idx + 10);
/* 153 */       if (idx < 0)
/* 154 */         return sb.toString(); 
/* 155 */       last = idx + 1;
/* 156 */       idx = s.indexOf(".#subform[", last);
/*     */     } 
/* 158 */     sb.append(s.substring(last));
/* 159 */     return sb.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void inverseSearchAdd(String unstack) {
/* 168 */     inverseSearchAdd(this.inverseSearch, this.stack, unstack);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void inverseSearchAdd(Map<String, InverseStore> inverseSearch, Stack<String> stack, String unstack) {
/* 179 */     String last = stack.peek();
/* 180 */     InverseStore store = inverseSearch.get(last);
/* 181 */     if (store == null) {
/* 182 */       store = new InverseStore();
/* 183 */       inverseSearch.put(last, store);
/*     */     } 
/* 185 */     for (int k = stack.size() - 2; k >= 0; k--) {
/* 186 */       InverseStore store2; last = stack.get(k);
/*     */       
/* 188 */       int idx = store.part.indexOf(last);
/* 189 */       if (idx < 0) {
/* 190 */         store.part.add(last);
/* 191 */         store2 = new InverseStore();
/* 192 */         store.follow.add(store2);
/*     */       } else {
/* 194 */         store2 = (InverseStore)store.follow.get(idx);
/* 195 */       }  store = store2;
/*     */     } 
/* 197 */     store.part.add("");
/* 198 */     store.follow.add(unstack);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String inverseSearchGlobal(List<String> parts) {
/* 208 */     if (parts.size() == 0) {
/* 209 */       return null;
/*     */     }
/* 211 */     InverseStore store = this.inverseSearch.get(parts.get(parts.size() - 1));
/* 212 */     if (store == null)
/* 213 */       return null; 
/* 214 */     for (int k = parts.size() - 2; k >= 0; k--) {
/* 215 */       String part = parts.get(k);
/* 216 */       int idx = store.part.indexOf(part);
/* 217 */       if (idx < 0) {
/* 218 */         if (store.isSimilar(part))
/* 219 */           return null; 
/* 220 */         return store.getDefaultName();
/*     */       } 
/* 222 */       store = (InverseStore)store.follow.get(idx);
/*     */     } 
/* 224 */     return store.getDefaultName();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Stack<String> splitParts(String name) {
/* 234 */     while (name.startsWith("."))
/* 235 */       name = name.substring(1); 
/* 236 */     Stack<String> parts = new Stack<>();
/* 237 */     int last = 0;
/* 238 */     int pos = 0;
/*     */     
/*     */     while (true) {
/* 241 */       pos = last;
/*     */       while (true) {
/* 243 */         pos = name.indexOf('.', pos);
/* 244 */         if (pos < 0)
/*     */           break; 
/* 246 */         if (name.charAt(pos - 1) == '\\') {
/* 247 */           pos++; continue;
/*     */         } 
/*     */         break;
/*     */       } 
/* 251 */       if (pos < 0)
/*     */         break; 
/* 253 */       String str = name.substring(last, pos);
/* 254 */       if (!str.endsWith("]"))
/* 255 */         str = str + "[0]"; 
/* 256 */       parts.add(str);
/* 257 */       last = pos + 1;
/*     */     } 
/* 259 */     String part = name.substring(last);
/* 260 */     if (!part.endsWith("]"))
/* 261 */       part = part + "[0]"; 
/* 262 */     parts.add(part);
/* 263 */     return parts;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List<String> getOrder() {
/* 272 */     return this.order;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setOrder(List<String> order) {
/* 281 */     this.order = order;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Map<String, Node> getName2Node() {
/* 290 */     return this.name2Node;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setName2Node(Map<String, Node> name2Node) {
/* 299 */     this.name2Node = name2Node;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Map<String, InverseStore> getInverseSearch() {
/* 308 */     return this.inverseSearch;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setInverseSearch(Map<String, InverseStore> inverseSearch) {
/* 317 */     this.inverseSearch = inverseSearch;
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/forms/xfa/Xml2Som.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */