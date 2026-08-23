/*    */ package com.itextpdf.kernel.pdf.filters;
/*    */ 
/*    */ import com.itextpdf.kernel.pdf.PdfName;
/*    */ import java.util.Collections;
/*    */ import java.util.HashMap;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class FilterHandlers
/*    */ {
/*    */   private static final Map<PdfName, IFilterHandler> defaults;
/*    */   
/*    */   static {
/* 67 */     Map<PdfName, IFilterHandler> map = new HashMap<>();
/*    */     
/* 69 */     map.put(PdfName.FlateDecode, new FlateDecodeFilter());
/* 70 */     map.put(PdfName.Fl, new FlateDecodeFilter());
/* 71 */     map.put(PdfName.ASCIIHexDecode, new ASCIIHexDecodeFilter());
/* 72 */     map.put(PdfName.AHx, new ASCIIHexDecodeFilter());
/* 73 */     map.put(PdfName.ASCII85Decode, new ASCII85DecodeFilter());
/* 74 */     map.put(PdfName.A85, new ASCII85DecodeFilter());
/* 75 */     map.put(PdfName.LZWDecode, new LZWDecodeFilter());
/* 76 */     map.put(PdfName.CCITTFaxDecode, new CCITTFaxDecodeFilter());
/* 77 */     map.put(PdfName.Crypt, new DoNothingFilter());
/* 78 */     map.put(PdfName.RunLengthDecode, new RunLengthDecodeFilter());
/* 79 */     map.put(PdfName.DCTDecode, new DctDecodeFilter());
/* 80 */     map.put(PdfName.JPXDecode, new JpxDecodeFilter());
/*    */     
/* 82 */     defaults = Collections.unmodifiableMap(map);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static Map<PdfName, IFilterHandler> getDefaultFilterHandlers() {
/* 89 */     return defaults;
/*    */   }
/*    */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/kernel/pdf/filters/FilterHandlers.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */