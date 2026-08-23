/*     */ package com.itextpdf.layout.renderer;
/*     */ 
/*     */ import com.itextpdf.kernel.pdf.tagging.StandardNamespaces;
/*     */ import java.util.HashSet;
/*     */ import java.util.Set;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class AccessibleTypes
/*     */ {
/*  75 */   static int Unknown = 0;
/*  76 */   static int Grouping = 1;
/*  77 */   static int BlockLevel = 2;
/*  78 */   static int InlineLevel = 3;
/*  79 */   static int Illustration = 4;
/*     */   
/*  81 */   static Set<String> groupingRoles = new HashSet<>();
/*  82 */   static Set<String> blockLevelRoles = new HashSet<>();
/*  83 */   static Set<String> inlineLevelRoles = new HashSet<>();
/*  84 */   static Set<String> illustrationRoles = new HashSet<>();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static {
/*  98 */     groupingRoles.add("Part");
/*  99 */     groupingRoles.add("Art");
/* 100 */     groupingRoles.add("Sect");
/* 101 */     groupingRoles.add("Div");
/* 102 */     groupingRoles.add("BlockQuote");
/* 103 */     groupingRoles.add("Caption");
/* 104 */     groupingRoles.add("TOC");
/* 105 */     groupingRoles.add("TOCI");
/* 106 */     groupingRoles.add("Index");
/* 107 */     groupingRoles.add("NonStruct");
/* 108 */     groupingRoles.add("Private");
/* 109 */     groupingRoles.add("Aside");
/*     */     
/* 111 */     blockLevelRoles.add("P");
/* 112 */     blockLevelRoles.add("H");
/* 113 */     blockLevelRoles.add("H1");
/* 114 */     blockLevelRoles.add("H2");
/* 115 */     blockLevelRoles.add("H3");
/* 116 */     blockLevelRoles.add("H4");
/* 117 */     blockLevelRoles.add("H5");
/* 118 */     blockLevelRoles.add("H6");
/*     */     
/* 120 */     blockLevelRoles.add("L");
/* 121 */     blockLevelRoles.add("Lbl");
/* 122 */     blockLevelRoles.add("LI");
/* 123 */     blockLevelRoles.add("LBody");
/* 124 */     blockLevelRoles.add("Table");
/* 125 */     blockLevelRoles.add("TR");
/* 126 */     blockLevelRoles.add("TH");
/* 127 */     blockLevelRoles.add("TD");
/* 128 */     blockLevelRoles.add("Title");
/* 129 */     blockLevelRoles.add("FENote");
/* 130 */     blockLevelRoles.add("Sub");
/* 131 */     blockLevelRoles.add("Caption");
/*     */     
/* 133 */     inlineLevelRoles.add("Span");
/* 134 */     inlineLevelRoles.add("Quote");
/* 135 */     inlineLevelRoles.add("Note");
/* 136 */     inlineLevelRoles.add("Reference");
/* 137 */     inlineLevelRoles.add("BibEntry");
/* 138 */     inlineLevelRoles.add("Code");
/* 139 */     inlineLevelRoles.add("Link");
/* 140 */     inlineLevelRoles.add("Annot");
/* 141 */     inlineLevelRoles.add("Ruby");
/* 142 */     inlineLevelRoles.add("Warichu");
/* 143 */     inlineLevelRoles.add("RB");
/* 144 */     inlineLevelRoles.add("RT");
/* 145 */     inlineLevelRoles.add("RP");
/* 146 */     inlineLevelRoles.add("WT");
/* 147 */     inlineLevelRoles.add("WP");
/* 148 */     inlineLevelRoles.add("Em");
/* 149 */     inlineLevelRoles.add("Strong");
/*     */     
/* 151 */     illustrationRoles.add("Figure");
/* 152 */     illustrationRoles.add("Formula");
/* 153 */     illustrationRoles.add("Form");
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
/*     */   static int identifyType(String role) {
/* 168 */     if (groupingRoles.contains(role))
/* 169 */       return Grouping; 
/* 170 */     if (blockLevelRoles.contains(role) || StandardNamespaces.isHnRole(role))
/* 171 */       return BlockLevel; 
/* 172 */     if (inlineLevelRoles.contains(role))
/* 173 */       return InlineLevel; 
/* 174 */     if (illustrationRoles.contains(role)) {
/* 175 */       return Illustration;
/*     */     }
/*     */     
/* 178 */     return Unknown;
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/layout/renderer/AccessibleTypes.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */