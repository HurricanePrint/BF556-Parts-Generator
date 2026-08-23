/*    */ package com.itextpdf.io.font.otf;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import java.util.HashMap;
/*    */ import java.util.List;
/*    */ import java.util.Map;
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
/*    */ public class GposLookupType1
/*    */   extends OpenTableLookup
/*    */ {
/*    */   private static final long serialVersionUID = 4562279115440679363L;
/* 37 */   private Map<Integer, GposValueRecord> valueRecordMap = new HashMap<>();
/*    */ 
/*    */   
/*    */   public GposLookupType1(OpenTypeFontTableReader openReader, int lookupFlag, int[] subTableLocations) throws IOException {
/* 41 */     super(openReader, lookupFlag, subTableLocations);
/* 42 */     readSubTables();
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean transformOne(GlyphLine line) {
/* 47 */     if (line.idx >= line.end) {
/* 48 */       return false;
/*    */     }
/* 50 */     if (this.openReader.isSkip(line.get(line.idx).getCode(), this.lookupFlag)) {
/* 51 */       line.idx++;
/* 52 */       return false;
/*    */     } 
/* 54 */     int glyphCode = line.get(line.idx).getCode();
/* 55 */     boolean positionApplied = false;
/* 56 */     GposValueRecord valueRecord = this.valueRecordMap.get(Integer.valueOf(glyphCode));
/* 57 */     if (valueRecord != null) {
/* 58 */       Glyph newGlyph = new Glyph(line.get(line.idx));
/* 59 */       newGlyph.xAdvance = (short)(newGlyph.xAdvance + (short)valueRecord.XAdvance);
/* 60 */       newGlyph.yAdvance = (short)(newGlyph.yAdvance + (short)valueRecord.YAdvance);
/* 61 */       line.set(line.idx, newGlyph);
/* 62 */       positionApplied = true;
/*    */     } 
/* 64 */     line.idx++;
/* 65 */     return positionApplied;
/*    */   }
/*    */ 
/*    */   
/*    */   protected void readSubTable(int subTableLocation) throws IOException {
/* 70 */     this.openReader.rf.seek(subTableLocation);
/* 71 */     this.openReader.rf.readShort();
/* 72 */     int coverage = this.openReader.rf.readUnsignedShort();
/* 73 */     int valueFormat = this.openReader.rf.readUnsignedShort();
/* 74 */     GposValueRecord valueRecord = OtfReadCommon.readGposValueRecord(this.openReader, valueFormat);
/* 75 */     List<Integer> coverageGlyphIds = this.openReader.readCoverageFormat(subTableLocation + coverage);
/* 76 */     for (Integer glyphId : coverageGlyphIds)
/* 77 */       this.valueRecordMap.put(Integer.valueOf(glyphId.intValue()), valueRecord); 
/*    */   }
/*    */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/io/font/otf/GposLookupType1.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */