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
/*    */ 
/*    */ public class GlyphSubstitutionTableReader
/*    */   extends OpenTypeFontTableReader
/*    */ {
/*    */   private static final long serialVersionUID = -6971081733980429442L;
/*    */   
/*    */   public GlyphSubstitutionTableReader(RandomAccessFileOrArray rf, int gsubTableLocation, OpenTypeGdefTableReader gdef, Map<Integer, Glyph> indexGlyphMap, int unitsPerEm) throws IOException {
/* 64 */     super(rf, gsubTableLocation, gdef, indexGlyphMap, unitsPerEm);
/* 65 */     startReadingTable();
/*    */   }
/*    */ 
/*    */   
/*    */   protected OpenTableLookup readLookupTable(int lookupType, int lookupFlag, int[] subTableLocations) throws IOException {
/* 70 */     if (lookupType == 7) {
/* 71 */       for (int k = 0; k < subTableLocations.length; k++) {
/* 72 */         int location = subTableLocations[k];
/* 73 */         this.rf.seek(location);
/* 74 */         this.rf.readUnsignedShort();
/* 75 */         lookupType = this.rf.readUnsignedShort();
/* 76 */         location += this.rf.readInt();
/* 77 */         subTableLocations[k] = location;
/*    */       } 
/*    */     }
/* 80 */     switch (lookupType) {
/*    */       case 1:
/* 82 */         return new GsubLookupType1(this, lookupFlag, subTableLocations);
/*    */       case 2:
/* 84 */         return new GsubLookupType2(this, lookupFlag, subTableLocations);
/*    */       case 3:
/* 86 */         return new GsubLookupType3(this, lookupFlag, subTableLocations);
/*    */       case 4:
/* 88 */         return new GsubLookupType4(this, lookupFlag, subTableLocations);
/*    */       case 5:
/* 90 */         return new GsubLookupType5(this, lookupFlag, subTableLocations);
/*    */       case 6:
/* 92 */         return new GsubLookupType6(this, lookupFlag, subTableLocations);
/*    */     } 
/* 94 */     return null;
/*    */   }
/*    */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/io/font/otf/GlyphSubstitutionTableReader.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */