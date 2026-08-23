/*     */ package com.itextpdf.io.font.cmap;
/*     */ 
/*     */ import com.itextpdf.io.util.IntHashtable;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
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
/*     */ public class CMapCidByte
/*     */   extends AbstractCMap
/*     */ {
/*     */   private static final long serialVersionUID = 4956059671207068672L;
/*  59 */   private Map<Integer, byte[]> map = (Map)new HashMap<>();
/*  60 */   private final byte[] EMPTY = new byte[0];
/*  61 */   private List<byte[]> codeSpaceRanges = (List)new ArrayList<>();
/*     */ 
/*     */   
/*     */   void addChar(String mark, CMapObject code) {
/*  65 */     if (code.isNumber()) {
/*  66 */       byte[] ser = decodeStringToByte(mark);
/*  67 */       this.map.put(Integer.valueOf(((Integer)code.getValue()).intValue()), ser);
/*     */     } 
/*     */   }
/*     */   
/*     */   public byte[] lookup(int cid) {
/*  72 */     byte[] ser = this.map.get(Integer.valueOf(cid));
/*  73 */     if (ser == null) {
/*  74 */       return this.EMPTY;
/*     */     }
/*  76 */     return ser;
/*     */   }
/*     */ 
/*     */   
/*     */   public IntHashtable getReversMap() {
/*  81 */     IntHashtable code2cid = new IntHashtable(this.map.size());
/*  82 */     for (Iterator<Integer> iterator = this.map.keySet().iterator(); iterator.hasNext(); ) { int cid = ((Integer)iterator.next()).intValue();
/*  83 */       byte[] bytes = this.map.get(Integer.valueOf(cid));
/*  84 */       int byteCode = 0;
/*  85 */       for (byte b : bytes) {
/*  86 */         byteCode <<= 8;
/*  87 */         byteCode += b & 0xFF;
/*     */       } 
/*  89 */       code2cid.put(byteCode, cid); }
/*     */     
/*  91 */     return code2cid;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List<byte[]> getCodeSpaceRanges() {
/* 101 */     return this.codeSpaceRanges;
/*     */   }
/*     */ 
/*     */   
/*     */   void addCodeSpaceRange(byte[] low, byte[] high) {
/* 106 */     this.codeSpaceRanges.add(low);
/* 107 */     this.codeSpaceRanges.add(high);
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/io/font/cmap/CMapCidByte.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */