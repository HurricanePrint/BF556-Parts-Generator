/*    */ package com.itextpdf.io.font.otf;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import java.io.Serializable;
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class OpenTypeFeature
/*    */   implements Serializable
/*    */ {
/*    */   private static final long serialVersionUID = 1484564408822091202L;
/*    */   private OpenTypeFontTableReader openTypeReader;
/*    */   private List<FeatureRecord> records;
/*    */   
/*    */   public OpenTypeFeature(OpenTypeFontTableReader openTypeReader, int locationFeatureTable) throws IOException {
/* 56 */     this.openTypeReader = openTypeReader;
/* 57 */     this.records = new ArrayList<>();
/* 58 */     openTypeReader.rf.seek(locationFeatureTable);
/* 59 */     TagAndLocation[] tagsLocs = openTypeReader.readTagAndLocations(locationFeatureTable);
/* 60 */     for (TagAndLocation tagLoc : tagsLocs) {
/*    */       
/* 62 */       openTypeReader.rf.seek(tagLoc.location + 2L);
/* 63 */       int lookupCount = openTypeReader.rf.readUnsignedShort();
/* 64 */       FeatureRecord rec = new FeatureRecord();
/* 65 */       rec.tag = tagLoc.tag;
/* 66 */       rec.lookups = openTypeReader.readUShortArray(lookupCount);
/* 67 */       this.records.add(rec);
/*    */     } 
/*    */   }
/*    */   
/*    */   public List<FeatureRecord> getRecords() {
/* 72 */     return this.records;
/*    */   }
/*    */   
/*    */   public FeatureRecord getRecord(int idx) {
/* 76 */     if (idx < 0 || idx >= this.records.size())
/* 77 */       return null; 
/* 78 */     return this.records.get(idx);
/*    */   }
/*    */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/io/font/otf/OpenTypeFeature.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */