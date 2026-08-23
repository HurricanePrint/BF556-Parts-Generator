/*     */ package com.itextpdf.io.colors;
/*     */ 
/*     */ import com.itextpdf.io.IOException;
/*     */ import com.itextpdf.io.source.RandomAccessFileOrArray;
/*     */ import com.itextpdf.io.source.RandomAccessSourceFactory;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.Serializable;
/*     */ import java.io.UnsupportedEncodingException;
/*     */ import java.util.HashMap;
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
/*     */ public class IccProfile
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = -7466035855770591929L;
/*     */   protected byte[] data;
/*     */   protected int numComponents;
/*  64 */   private static Map<String, Integer> cstags = new HashMap<>();
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
/*     */   public static IccProfile getInstance(byte[] data, int numComponents) {
/*  79 */     if (data.length < 128 || data[36] != 97 || data[37] != 99 || data[38] != 115 || data[39] != 112)
/*     */     {
/*  81 */       throw new IOException("Invalid ICC profile."); } 
/*  82 */     IccProfile icc = new IccProfile();
/*  83 */     icc.data = data;
/*     */     
/*  85 */     Integer cs = getIccNumberOfComponents(data);
/*  86 */     int nc = (cs == null) ? 0 : cs.intValue();
/*  87 */     icc.numComponents = nc;
/*     */     
/*  89 */     if (nc != numComponents) {
/*  90 */       throw (new IOException("ICC profile contains {0} components, while the image data contains {1} components.")).setMessageParams(new Object[] { Integer.valueOf(nc), Integer.valueOf(numComponents) });
/*     */     }
/*  92 */     return icc;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static IccProfile getInstance(byte[] data) {
/* 103 */     Integer cs = getIccNumberOfComponents(data);
/* 104 */     int numComponents = (cs == null) ? 0 : cs.intValue();
/* 105 */     return getInstance(data, numComponents);
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
/*     */   public static IccProfile getInstance(RandomAccessFileOrArray file) {
/*     */     try {
/* 118 */       byte[] head = new byte[128];
/* 119 */       int remain = head.length;
/* 120 */       int ptr = 0;
/* 121 */       while (remain > 0) {
/* 122 */         int n = file.read(head, ptr, remain);
/* 123 */         if (n < 0)
/* 124 */           throw new IOException("Invalid ICC profile."); 
/* 125 */         remain -= n;
/* 126 */         ptr += n;
/*     */       } 
/* 128 */       if (head[36] != 97 || head[37] != 99 || head[38] != 115 || head[39] != 112)
/*     */       {
/* 130 */         throw new IOException("Invalid ICC profile.");
/*     */       }
/* 132 */       remain = (head[0] & 0xFF) << 24 | (head[1] & 0xFF) << 16 | (head[2] & 0xFF) << 8 | head[3] & 0xFF;
/*     */       
/* 134 */       byte[] icc = new byte[remain];
/* 135 */       System.arraycopy(head, 0, icc, 0, head.length);
/* 136 */       remain -= head.length;
/* 137 */       ptr = head.length;
/* 138 */       while (remain > 0) {
/* 139 */         int n = file.read(icc, ptr, remain);
/* 140 */         if (n < 0) {
/* 141 */           throw new IOException("Invalid ICC profile.");
/*     */         }
/* 143 */         remain -= n;
/* 144 */         ptr += n;
/*     */       } 
/* 146 */       return getInstance(icc);
/* 147 */     } catch (Exception ex) {
/* 148 */       throw new IOException("Invalid ICC profile.", ex);
/*     */     } 
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
/*     */   public static IccProfile getInstance(InputStream stream) {
/*     */     RandomAccessFileOrArray raf;
/*     */     try {
/* 164 */       raf = new RandomAccessFileOrArray((new RandomAccessSourceFactory()).createSource(stream));
/* 165 */     } catch (IOException e) {
/* 166 */       throw new IOException("Invalid ICC profile.", e);
/*     */     } 
/* 168 */     return getInstance(raf);
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
/*     */   public static IccProfile getInstance(String filename) {
/*     */     RandomAccessFileOrArray raf;
/*     */     try {
/* 182 */       raf = new RandomAccessFileOrArray((new RandomAccessSourceFactory()).createBestSource(filename));
/* 183 */     } catch (IOException e) {
/* 184 */       throw new IOException("Invalid ICC profile.", e);
/*     */     } 
/* 186 */     return getInstance(raf);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String getIccColorSpaceName(byte[] data) {
/*     */     String colorSpace;
/*     */     try {
/* 199 */       colorSpace = new String(data, 16, 4, "US-ASCII");
/* 200 */     } catch (UnsupportedEncodingException e) {
/* 201 */       throw new IOException("Invalid ICC profile.", e);
/*     */     } 
/* 203 */     return colorSpace;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String getIccDeviceClass(byte[] data) {
/*     */     String deviceClass;
/*     */     try {
/* 216 */       deviceClass = new String(data, 12, 4, "US-ASCII");
/* 217 */     } catch (UnsupportedEncodingException e) {
/* 218 */       throw new IOException("Invalid ICC profile.", e);
/*     */     } 
/* 220 */     return deviceClass;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Integer getIccNumberOfComponents(byte[] data) {
/* 230 */     return cstags.get(getIccColorSpaceName(data));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public byte[] getData() {
/* 239 */     return this.data;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getNumComponents() {
/* 248 */     return this.numComponents;
/*     */   }
/*     */   
/*     */   static {
/* 252 */     cstags.put("XYZ ", Integer.valueOf(3));
/* 253 */     cstags.put("Lab ", Integer.valueOf(3));
/* 254 */     cstags.put("Luv ", Integer.valueOf(3));
/* 255 */     cstags.put("YCbr", Integer.valueOf(3));
/* 256 */     cstags.put("Yxy ", Integer.valueOf(3));
/* 257 */     cstags.put("RGB ", Integer.valueOf(3));
/* 258 */     cstags.put("GRAY", Integer.valueOf(1));
/* 259 */     cstags.put("HSV ", Integer.valueOf(3));
/* 260 */     cstags.put("HLS ", Integer.valueOf(3));
/* 261 */     cstags.put("CMYK", Integer.valueOf(4));
/* 262 */     cstags.put("CMY ", Integer.valueOf(3));
/* 263 */     cstags.put("2CLR", Integer.valueOf(2));
/* 264 */     cstags.put("3CLR", Integer.valueOf(3));
/* 265 */     cstags.put("4CLR", Integer.valueOf(4));
/* 266 */     cstags.put("5CLR", Integer.valueOf(5));
/* 267 */     cstags.put("6CLR", Integer.valueOf(6));
/* 268 */     cstags.put("7CLR", Integer.valueOf(7));
/* 269 */     cstags.put("8CLR", Integer.valueOf(8));
/* 270 */     cstags.put("9CLR", Integer.valueOf(9));
/* 271 */     cstags.put("ACLR", Integer.valueOf(10));
/* 272 */     cstags.put("BCLR", Integer.valueOf(11));
/* 273 */     cstags.put("CCLR", Integer.valueOf(12));
/* 274 */     cstags.put("DCLR", Integer.valueOf(13));
/* 275 */     cstags.put("ECLR", Integer.valueOf(14));
/* 276 */     cstags.put("FCLR", Integer.valueOf(15));
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/io/colors/IccProfile.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */