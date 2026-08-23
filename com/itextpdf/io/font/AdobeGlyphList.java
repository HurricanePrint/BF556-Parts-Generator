/*     */ package com.itextpdf.io.font;
/*     */ 
/*     */ import com.itextpdf.io.util.ResourceUtil;
/*     */ import java.io.ByteArrayOutputStream;
/*     */ import java.io.InputStream;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import java.util.StringTokenizer;
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
/*     */ public class AdobeGlyphList
/*     */ {
/*  57 */   private static Map<Integer, String> unicode2names = new HashMap<>();
/*  58 */   private static Map<String, Integer> names2unicode = new HashMap<>();
/*     */   
/*     */   static {
/*  61 */     InputStream resource = null;
/*     */     try {
/*  63 */       resource = ResourceUtil.getResourceStream("com/itextpdf/io/font/AdobeGlyphList.txt");
/*  64 */       if (resource == null) {
/*  65 */         throw new Exception("com/itextpdf/io/font/AdobeGlyphList.txt not found as resource.");
/*     */       }
/*  67 */       byte[] buf = new byte[1024];
/*  68 */       ByteArrayOutputStream stream = new ByteArrayOutputStream();
/*     */       while (true) {
/*  70 */         int size = resource.read(buf);
/*  71 */         if (size < 0) {
/*     */           break;
/*     */         }
/*  74 */         stream.write(buf, 0, size);
/*     */       } 
/*  76 */       resource.close();
/*  77 */       resource = null;
/*  78 */       String s = PdfEncodings.convertToString(stream.toByteArray(), null);
/*  79 */       StringTokenizer tk = new StringTokenizer(s, "\r\n");
/*  80 */       while (tk.hasMoreTokens()) {
/*  81 */         String line = tk.nextToken();
/*  82 */         if (line.startsWith("#")) {
/*     */           continue;
/*     */         }
/*  85 */         StringTokenizer t2 = new StringTokenizer(line, " ;\r\n\t\f");
/*  86 */         if (!t2.hasMoreTokens()) {
/*     */           continue;
/*     */         }
/*  89 */         String name = t2.nextToken();
/*  90 */         if (!t2.hasMoreTokens()) {
/*     */           continue;
/*     */         }
/*  93 */         String hex = t2.nextToken();
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*  98 */         if (t2.hasMoreTokens()) {
/*     */           continue;
/*     */         }
/* 101 */         int num = Integer.parseInt(hex, 16);
/* 102 */         unicode2names.put(Integer.valueOf(num), name);
/* 103 */         names2unicode.put(name, Integer.valueOf(num));
/*     */       } 
/* 105 */     } catch (Exception e) {
/* 106 */       System.err.println("AdobeGlyphList.txt loading error: " + e.getMessage());
/*     */     } finally {
/* 108 */       if (resource != null) {
/*     */         try {
/* 110 */           resource.close();
/* 111 */         } catch (Exception exception) {}
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static int nameToUnicode(String name) {
/* 119 */     int v = -1;
/* 120 */     if (names2unicode.containsKey(name)) {
/* 121 */       v = ((Integer)names2unicode.get(name)).intValue();
/*     */     }
/* 123 */     if (v == -1 && name.length() == 7 && name.toLowerCase().startsWith("uni")) {
/*     */       try {
/* 125 */         return Integer.parseInt(name.substring(3), 16);
/* 126 */       } catch (Exception exception) {}
/*     */     }
/*     */     
/* 129 */     return v;
/*     */   }
/*     */   
/*     */   public static String unicodeToName(int num) {
/* 133 */     return unicode2names.get(Integer.valueOf(num));
/*     */   }
/*     */   
/*     */   public static int getNameToUnicodeLength() {
/* 137 */     return names2unicode.size();
/*     */   }
/*     */   
/*     */   public static int getUnicodeToNameLength() {
/* 141 */     return unicode2names.size();
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/io/font/AdobeGlyphList.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */