/*    */ package com.itextpdf.io.font.otf;
/*    */ 
/*    */ import com.itextpdf.io.source.RandomAccessFileOrArray;
/*    */ import java.io.IOException;
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
/*    */ public class GlyphPositioningTableReader
/*    */   extends OpenTypeFontTableReader
/*    */ {
/*    */   private static final long serialVersionUID = 7437245788115628787L;
/*    */   
/*    */   public GlyphPositioningTableReader(RandomAccessFileOrArray rf, int gposTableLocation, OpenTypeGdefTableReader gdef, Map<Integer, Glyph> indexGlyphMap, int unitsPerEm) throws IOException {
/* 63 */     super(rf, gposTableLocation, gdef, indexGlyphMap, unitsPerEm);
/* 64 */     startReadingTable();
/*    */   }
/*    */ 
/*    */   
/*    */   protected OpenTableLookup readLookupTable(int lookupType, int lookupFlag, int[] subTableLocations) throws IOException {
/* 69 */     if (lookupType == 9) {
/* 70 */       for (int k = 0; k < subTableLocations.length; k++) {
/* 71 */         int location = subTableLocations[k];
/* 72 */         this.rf.seek(location);
/* 73 */         this.rf.readUnsignedShort();
/* 74 */         lookupType = this.rf.readUnsignedShort();
/* 75 */         location += this.rf.readInt();
/* 76 */         subTableLocations[k] = location;
/*    */       } 
/*    */     }
/* 79 */     switch (lookupType) {
/*    */       case 1:
/* 81 */         return new GposLookupType1(this, lookupFlag, subTableLocations);
/*    */       case 2:
/* 83 */         return new GposLookupType2(this, lookupFlag, subTableLocations);
/*    */       case 4:
/* 85 */         return new GposLookupType4(this, lookupFlag, subTableLocations);
/*    */       case 5:
/* 87 */         return new GposLookupType5(this, lookupFlag, subTableLocations);
/*    */       case 6:
/* 89 */         return new GposLookupType6(this, lookupFlag, subTableLocations);
/*    */       case 7:
/* 91 */         return new GposLookupType7(this, lookupFlag, subTableLocations);
/*    */     } 
/* 93 */     return null;
/*    */   }
/*    */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/io/font/otf/GlyphPositioningTableReader.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */