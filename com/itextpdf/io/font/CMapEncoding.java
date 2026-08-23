/*     */ package com.itextpdf.io.font;
/*     */ 
/*     */ import com.itextpdf.io.font.cmap.AbstractCMap;
/*     */ import com.itextpdf.io.font.cmap.CMapCidByte;
/*     */ import com.itextpdf.io.font.cmap.CMapCidUni;
/*     */ import com.itextpdf.io.font.cmap.CMapLocationFromBytes;
/*     */ import com.itextpdf.io.font.cmap.CMapParser;
/*     */ import com.itextpdf.io.font.cmap.ICMapLocation;
/*     */ import com.itextpdf.io.source.ByteBuffer;
/*     */ import com.itextpdf.io.util.IntHashtable;
/*     */ import java.io.IOException;
/*     */ import java.io.Serializable;
/*     */ import java.util.Arrays;
/*     */ import java.util.List;
/*     */ import java.util.Objects;
/*     */ import org.slf4j.LoggerFactory;
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
/*     */ public class CMapEncoding
/*     */   implements Serializable
/*     */ {
/*  63 */   private static final List<byte[]> IDENTITY_H_V_CODESPACE_RANGES = (List)Arrays.asList(new byte[][] { { 0, 0 }, { -1, -1 } });
/*     */ 
/*     */   
/*     */   private static final long serialVersionUID = 2418291066110642993L;
/*     */   
/*     */   private String cmap;
/*     */   
/*     */   private String uniMap;
/*     */   
/*     */   private boolean isDirect;
/*     */   
/*     */   private CMapCidUni cid2Uni;
/*     */   
/*     */   private CMapCidByte cid2Code;
/*     */   
/*     */   private IntHashtable code2Cid;
/*     */   
/*     */   private List<byte[]> codeSpaceRanges;
/*     */ 
/*     */   
/*     */   public CMapEncoding(String cmap) {
/*  84 */     this.cmap = cmap;
/*  85 */     if (cmap.equals("Identity-H") || cmap.equals("Identity-V")) {
/*  86 */       this.isDirect = true;
/*     */     }
/*     */ 
/*     */     
/*  90 */     this.codeSpaceRanges = IDENTITY_H_V_CODESPACE_RANGES;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public CMapEncoding(String cmap, String uniMap) {
/*  99 */     this.cmap = cmap;
/* 100 */     this.uniMap = uniMap;
/* 101 */     if (cmap.equals("Identity-H") || cmap.equals("Identity-V")) {
/* 102 */       this.cid2Uni = FontCache.getCid2UniCmap(uniMap);
/* 103 */       this.isDirect = true;
/* 104 */       this.codeSpaceRanges = IDENTITY_H_V_CODESPACE_RANGES;
/*     */     } else {
/* 106 */       this.cid2Code = FontCache.getCid2Byte(cmap);
/* 107 */       this.code2Cid = this.cid2Code.getReversMap();
/* 108 */       this.codeSpaceRanges = this.cid2Code.getCodeSpaceRanges();
/*     */     } 
/*     */   }
/*     */   
/*     */   public CMapEncoding(String cmap, byte[] cmapBytes) {
/* 113 */     this.cmap = cmap;
/* 114 */     this.cid2Code = new CMapCidByte();
/*     */     try {
/* 116 */       CMapParser.parseCid(cmap, (AbstractCMap)this.cid2Code, (ICMapLocation)new CMapLocationFromBytes(cmapBytes));
/* 117 */       this.code2Cid = this.cid2Code.getReversMap();
/* 118 */       this.codeSpaceRanges = this.cid2Code.getCodeSpaceRanges();
/* 119 */     } catch (IOException e) {
/* 120 */       LoggerFactory.getLogger(getClass()).error("Failed to parse encoding stream.");
/*     */     } 
/*     */   }
/*     */   
/*     */   public boolean isDirect() {
/* 125 */     return this.isDirect;
/*     */   }
/*     */   
/*     */   public boolean hasUniMap() {
/* 129 */     return (this.uniMap != null && this.uniMap.length() > 0);
/*     */   }
/*     */   
/*     */   public String getRegistry() {
/* 133 */     if (isDirect()) {
/* 134 */       return "Adobe";
/*     */     }
/* 136 */     return this.cid2Code.getRegistry();
/*     */   }
/*     */ 
/*     */   
/*     */   public String getOrdering() {
/* 141 */     if (isDirect()) {
/* 142 */       return "Identity";
/*     */     }
/* 144 */     return this.cid2Code.getOrdering();
/*     */   }
/*     */ 
/*     */   
/*     */   public int getSupplement() {
/* 149 */     if (isDirect()) {
/* 150 */       return 0;
/*     */     }
/* 152 */     return this.cid2Code.getSupplement();
/*     */   }
/*     */ 
/*     */   
/*     */   public String getUniMapName() {
/* 157 */     return this.uniMap;
/*     */   }
/*     */   
/*     */   public String getCmapName() {
/* 161 */     return this.cmap;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isBuiltWith(String cmap) {
/* 171 */     return Objects.equals(cmap, this.cmap);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public int getCmapCode(int cid) {
/* 182 */     if (this.isDirect) {
/* 183 */       return cid;
/*     */     }
/* 185 */     return toInteger(this.cid2Code.lookup(cid));
/*     */   }
/*     */ 
/*     */   
/*     */   public byte[] getCmapBytes(int cid) {
/* 190 */     int length = getCmapBytesLength(cid);
/* 191 */     byte[] result = new byte[length];
/* 192 */     fillCmapBytes(cid, result, 0);
/* 193 */     return result;
/*     */   }
/*     */   
/*     */   public int fillCmapBytes(int cid, byte[] array, int offset) {
/* 197 */     if (this.isDirect) {
/* 198 */       array[offset++] = (byte)((cid & 0xFF00) >> 8);
/* 199 */       array[offset++] = (byte)(cid & 0xFF);
/*     */     } else {
/* 201 */       byte[] bytes = this.cid2Code.lookup(cid);
/* 202 */       for (int i = 0; i < bytes.length; i++) {
/* 203 */         array[offset++] = bytes[i];
/*     */       }
/*     */     } 
/* 206 */     return offset;
/*     */   }
/*     */   
/*     */   public void fillCmapBytes(int cid, ByteBuffer buffer) {
/* 210 */     if (this.isDirect) {
/* 211 */       buffer.append((byte)((cid & 0xFF00) >> 8));
/* 212 */       buffer.append((byte)(cid & 0xFF));
/*     */     } else {
/* 214 */       byte[] bytes = this.cid2Code.lookup(cid);
/* 215 */       buffer.append(bytes);
/*     */     } 
/*     */   }
/*     */   
/*     */   public int getCmapBytesLength(int cid) {
/* 220 */     if (this.isDirect) {
/* 221 */       return 2;
/*     */     }
/* 223 */     return (this.cid2Code.lookup(cid)).length;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getCidCode(int cmapCode) {
/* 228 */     if (this.isDirect) {
/* 229 */       return cmapCode;
/*     */     }
/* 231 */     return this.code2Cid.get(cmapCode);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean containsCodeInCodeSpaceRange(int code, int length) {
/* 236 */     for (int i = 0; i < this.codeSpaceRanges.size(); i += 2) {
/* 237 */       if (length == ((byte[])this.codeSpaceRanges.get(i)).length) {
/* 238 */         int mask = 255;
/* 239 */         int totalShift = 0;
/* 240 */         byte[] low = this.codeSpaceRanges.get(i);
/* 241 */         byte[] high = this.codeSpaceRanges.get(i + 1);
/* 242 */         boolean fitsIntoRange = true;
/* 243 */         for (int ind = length - 1; ind >= 0; ind--, totalShift += 8, mask <<= 8) {
/* 244 */           int actualByteValue = (code & mask) >> totalShift;
/* 245 */           if (actualByteValue < (0xFF & low[ind]) || actualByteValue > (0xFF & high[ind])) {
/* 246 */             fitsIntoRange = false;
/*     */           }
/*     */         } 
/* 249 */         if (fitsIntoRange) {
/* 250 */           return true;
/*     */         }
/*     */       } 
/*     */     } 
/* 254 */     return false;
/*     */   }
/*     */   
/*     */   private static int toInteger(byte[] bytes) {
/* 258 */     int result = 0;
/* 259 */     for (byte b : bytes) {
/* 260 */       result <<= 8;
/* 261 */       result += b & 0xFF;
/*     */     } 
/* 263 */     return result;
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/io/font/CMapEncoding.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */