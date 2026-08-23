/*     */ package com.itextpdf.forms.xfa;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import java.util.Stack;
/*     */ import org.w3c.dom.Document;
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
/*     */ class Xml2SomDatasets
/*     */   extends Xml2Som
/*     */ {
/*     */   public Xml2SomDatasets(Node n) {
/*  64 */     this.order = new ArrayList<>();
/*  65 */     this.name2Node = new HashMap<>();
/*  66 */     this.stack = new Stack<>();
/*  67 */     this.anform = 0;
/*  68 */     this.inverseSearch = new HashMap<>();
/*  69 */     processDatasetsInternal(n);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Node insertNode(Node n, String shortName) {
/*  80 */     Stack<String> localStack = splitParts(shortName);
/*  81 */     Document doc = n.getOwnerDocument();
/*  82 */     Node n2 = null;
/*  83 */     n = n.getFirstChild();
/*  84 */     while (n.getNodeType() != 1)
/*  85 */       n = n.getNextSibling(); 
/*  86 */     for (int k = 0; k < localStack.size(); k++) {
/*  87 */       String part = localStack.get(k);
/*  88 */       int idx = part.lastIndexOf('[');
/*  89 */       String name = part.substring(0, idx);
/*  90 */       idx = Integer.parseInt(part.substring(idx + 1, part.length() - 1));
/*  91 */       int found = -1;
/*  92 */       for (n2 = n.getFirstChild(); n2 != null; n2 = n2.getNextSibling()) {
/*  93 */         if (n2.getNodeType() == 1) {
/*  94 */           String s = escapeSom(n2.getLocalName());
/*     */           
/*  96 */           found++;
/*  97 */           if (s.equals(name) && found == idx) {
/*     */             break;
/*     */           }
/*     */         } 
/*     */       } 
/* 102 */       for (; found < idx; found++) {
/* 103 */         n2 = doc.createElementNS(null, name);
/* 104 */         n2 = n.appendChild(n2);
/* 105 */         Node attr = doc.createAttributeNS("http://www.xfa.org/schema/xfa-data/1.0/", "dataNode");
/* 106 */         attr.setNodeValue("dataGroup");
/* 107 */         n2.getAttributes().setNamedItemNS(attr);
/*     */       } 
/* 109 */       n = n2;
/*     */     } 
/* 111 */     inverseSearchAdd(this.inverseSearch, localStack, shortName);
/* 112 */     this.name2Node.put(shortName, n2);
/* 113 */     this.order.add(shortName);
/* 114 */     return n2;
/*     */   }
/*     */   
/*     */   private static boolean hasChildren(Node n) {
/* 118 */     Node dataNodeN = n.getAttributes().getNamedItemNS("http://www.xfa.org/schema/xfa-data/1.0/", "dataNode");
/* 119 */     if (dataNodeN != null) {
/* 120 */       String dataNode = dataNodeN.getNodeValue();
/* 121 */       if ("dataGroup".equals(dataNode))
/* 122 */         return true; 
/* 123 */       if ("dataValue".equals(dataNode))
/* 124 */         return false; 
/*     */     } 
/* 126 */     if (!n.hasChildNodes())
/* 127 */       return false; 
/* 128 */     Node n2 = n.getFirstChild();
/* 129 */     while (n2 != null) {
/* 130 */       if (n2.getNodeType() == 1) {
/* 131 */         return true;
/*     */       }
/* 133 */       n2 = n2.getNextSibling();
/*     */     } 
/* 135 */     return false;
/*     */   }
/*     */   
/*     */   private void processDatasetsInternal(Node n) {
/* 139 */     if (n != null) {
/* 140 */       Map<String, Integer> ss = new HashMap<>();
/* 141 */       Node n2 = n.getFirstChild();
/* 142 */       while (n2 != null) {
/* 143 */         if (n2.getNodeType() == 1) {
/* 144 */           String s = escapeSom(n2.getLocalName());
/* 145 */           Integer i = ss.get(s);
/* 146 */           if (i == null) {
/* 147 */             i = Integer.valueOf(0);
/*     */           } else {
/* 149 */             i = Integer.valueOf(i.intValue() + 1);
/* 150 */           }  ss.put(s, i);
/* 151 */           this.stack.push(String.format("%s[%s]", new Object[] { s, i.toString() }));
/* 152 */           if (hasChildren(n2)) {
/* 153 */             processDatasetsInternal(n2);
/*     */           }
/* 155 */           String unstack = printStack();
/* 156 */           this.order.add(unstack);
/* 157 */           inverseSearchAdd(unstack);
/* 158 */           this.name2Node.put(unstack, n2);
/* 159 */           this.stack.pop();
/*     */         } 
/* 161 */         n2 = n2.getNextSibling();
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/forms/xfa/Xml2SomDatasets.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */