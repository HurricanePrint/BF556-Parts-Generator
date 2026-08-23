/*     */ package com.itextpdf.layout.tagging;
/*     */ 
/*     */ import com.itextpdf.layout.element.Cell;
/*     */ import com.itextpdf.layout.element.Table;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ import java.util.Map;
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
/*     */ class TableTaggingRule
/*     */   implements ITaggingRule
/*     */ {
/*     */   public boolean onTagFinish(LayoutTaggingHelper taggingHelper, TaggingHintKey tableHintKey) {
/*  57 */     List<TaggingHintKey> kidKeys = taggingHelper.getAccessibleKidsHint(tableHintKey);
/*     */     
/*  59 */     Map<Integer, TreeMap<Integer, TaggingHintKey>> tableTags = new TreeMap<>();
/*  60 */     List<TaggingHintKey> tableCellTagsUnindexed = new ArrayList<>();
/*  61 */     List<TaggingHintKey> nonCellKids = new ArrayList<>();
/*  62 */     for (TaggingHintKey kidKey : kidKeys) {
/*  63 */       if ("TD".equals(kidKey.getAccessibleElement().getAccessibilityProperties().getRole()) || "TH"
/*  64 */         .equals(kidKey.getAccessibleElement().getAccessibilityProperties().getRole())) {
/*  65 */         if (kidKey.getAccessibleElement() instanceof Cell) {
/*  66 */           Cell cell = (Cell)kidKey.getAccessibleElement();
/*  67 */           int rowInd = cell.getRow();
/*  68 */           int colInd = cell.getCol();
/*  69 */           TreeMap<Integer, TaggingHintKey> rowTags = tableTags.get(Integer.valueOf(rowInd));
/*  70 */           if (rowTags == null) {
/*  71 */             rowTags = new TreeMap<>();
/*  72 */             tableTags.put(Integer.valueOf(rowInd), rowTags);
/*     */           } 
/*  74 */           rowTags.put(Integer.valueOf(colInd), kidKey); continue;
/*     */         } 
/*  76 */         tableCellTagsUnindexed.add(kidKey);
/*     */         
/*     */         continue;
/*     */       } 
/*  80 */       nonCellKids.add(kidKey);
/*     */     } 
/*     */ 
/*     */     
/*  84 */     boolean createTBody = true;
/*  85 */     if (tableHintKey.getAccessibleElement() instanceof Table) {
/*  86 */       Table modelElement = (Table)tableHintKey.getAccessibleElement();
/*     */       
/*  88 */       createTBody = ((modelElement.getHeader() != null && !modelElement.isSkipFirstHeader()) || (modelElement.getFooter() != null && !modelElement.isSkipLastFooter()));
/*     */     } 
/*  90 */     TaggingDummyElement tbodyTag = null;
/*  91 */     tbodyTag = new TaggingDummyElement(createTBody ? "TBody" : null);
/*     */     
/*  93 */     for (TaggingHintKey nonCellKid : nonCellKids) {
/*  94 */       String kidRole = nonCellKid.getAccessibleElement().getAccessibilityProperties().getRole();
/*  95 */       if (!"THead".equals(kidRole) && !"TFoot".equals(kidRole)) {
/*  96 */         taggingHelper.moveKidHint(nonCellKid, tableHintKey);
/*     */       }
/*     */     } 
/*  99 */     for (TaggingHintKey nonCellKid : nonCellKids) {
/* 100 */       String kidRole = nonCellKid.getAccessibleElement().getAccessibilityProperties().getRole();
/* 101 */       if ("THead".equals(kidRole)) {
/* 102 */         taggingHelper.moveKidHint(nonCellKid, tableHintKey);
/*     */       }
/*     */     } 
/* 105 */     taggingHelper.addKidsHint(tableHintKey, Collections.singletonList(LayoutTaggingHelper.getOrCreateHintKey(tbodyTag)), -1);
/* 106 */     for (TaggingHintKey nonCellKid : nonCellKids) {
/* 107 */       String kidRole = nonCellKid.getAccessibleElement().getAccessibilityProperties().getRole();
/* 108 */       if ("TFoot".equals(kidRole)) {
/* 109 */         taggingHelper.moveKidHint(nonCellKid, tableHintKey);
/*     */       }
/*     */     } 
/*     */     
/* 113 */     for (TreeMap<Integer, TaggingHintKey> rowTags : tableTags.values()) {
/* 114 */       TaggingDummyElement row = new TaggingDummyElement("TR");
/* 115 */       TaggingHintKey rowTagHint = LayoutTaggingHelper.getOrCreateHintKey(row);
/* 116 */       for (TaggingHintKey cellTagHint : rowTags.values()) {
/* 117 */         taggingHelper.moveKidHint(cellTagHint, rowTagHint);
/*     */       }
/* 119 */       if (tableCellTagsUnindexed != null) {
/* 120 */         for (TaggingHintKey cellTagHint : tableCellTagsUnindexed) {
/* 121 */           taggingHelper.moveKidHint(cellTagHint, rowTagHint);
/*     */         }
/* 123 */         tableCellTagsUnindexed = null;
/*     */       } 
/* 125 */       taggingHelper.addKidsHint(tbodyTag, Collections.singletonList(row), -1);
/*     */     } 
/*     */     
/* 128 */     return true;
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/layout/tagging/TableTaggingRule.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */