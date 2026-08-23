/*     */ package com.itextpdf.io.font;
/*     */ 
/*     */ import com.itextpdf.io.util.IntHashtable;
/*     */ import com.itextpdf.io.util.ResourceUtil;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.util.HashMap;
/*     */ import java.util.HashSet;
/*     */ import java.util.Map;
/*     */ import java.util.Properties;
/*     */ import java.util.Set;
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
/*     */ public class CidFontProperties
/*     */ {
/*  60 */   private static final Map<String, Map<String, Object>> allFonts = new HashMap<>();
/*  61 */   private static final Map<String, Set<String>> registryNames = new HashMap<>();
/*     */   
/*     */   static {
/*     */     try {
/*  65 */       loadRegistry();
/*  66 */       for (String font : registryNames.get("fonts")) {
/*  67 */         allFonts.put(font, readFontProperties(font));
/*     */       }
/*  69 */     } catch (Exception exception) {}
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean isCidFont(String fontName, String enc) {
/*  78 */     if (!registryNames.containsKey("fonts"))
/*  79 */       return false; 
/*  80 */     if (!((Set)registryNames.get("fonts")).contains(fontName))
/*  81 */       return false; 
/*  82 */     if (enc.equals("Identity-H") || enc.equals("Identity-V"))
/*  83 */       return true; 
/*  84 */     String registry = (String)((Map)allFonts.get(fontName)).get("Registry");
/*  85 */     Set<String> encodings = registryNames.get(registry);
/*  86 */     return (encodings != null && encodings.contains(enc));
/*     */   }
/*     */   
/*     */   public static String getCompatibleFont(String enc) {
/*  90 */     for (Map.Entry<String, Set<String>> e : registryNames.entrySet()) {
/*  91 */       if (((Set)e.getValue()).contains(enc)) {
/*  92 */         String registry = e.getKey();
/*  93 */         for (Map.Entry<String, Map<String, Object>> e1 : allFonts.entrySet()) {
/*  94 */           if (registry.equals(((Map)e1.getValue()).get("Registry")))
/*  95 */             return e1.getKey(); 
/*     */         } 
/*     */       } 
/*     */     } 
/*  99 */     return null;
/*     */   }
/*     */   
/*     */   public static Map<String, Map<String, Object>> getAllFonts() {
/* 103 */     return allFonts;
/*     */   }
/*     */   
/*     */   public static Map<String, Set<String>> getRegistryNames() {
/* 107 */     return registryNames;
/*     */   }
/*     */   
/*     */   private static void loadRegistry() throws IOException {
/* 111 */     InputStream resource = ResourceUtil.getResourceStream("com/itextpdf/io/font/cmap/cjk_registry.properties");
/* 112 */     Properties p = new Properties();
/* 113 */     p.load(resource);
/* 114 */     resource.close();
/* 115 */     for (Object key : p.keySet()) {
/* 116 */       String value = p.getProperty((String)key);
/* 117 */       String[] sp = value.split(" ");
/* 118 */       Set<String> hs = new HashSet<>();
/* 119 */       for (String s : sp) {
/* 120 */         if (s.length() > 0)
/* 121 */           hs.add(s); 
/*     */       } 
/* 123 */       registryNames.put((String)key, hs);
/*     */     } 
/*     */   }
/*     */   
/*     */   private static Map<String, Object> readFontProperties(String name) throws IOException {
/* 128 */     name = name + ".properties";
/* 129 */     InputStream resource = ResourceUtil.getResourceStream("com/itextpdf/io/font/cmap/" + name);
/* 130 */     Properties p = new Properties();
/* 131 */     p.load(resource);
/* 132 */     resource.close();
/* 133 */     IntHashtable W = createMetric(p.getProperty("W"));
/* 134 */     p.remove("W");
/* 135 */     IntHashtable W2 = createMetric(p.getProperty("W2"));
/* 136 */     p.remove("W2");
/* 137 */     Map<String, Object> map = new HashMap<>();
/* 138 */     for (Object obj : p.keySet()) {
/* 139 */       map.put((String)obj, p.getProperty((String)obj));
/*     */     }
/* 141 */     map.put("W", W);
/* 142 */     map.put("W2", W2);
/* 143 */     return map;
/*     */   }
/*     */   
/*     */   private static IntHashtable createMetric(String s) {
/* 147 */     IntHashtable h = new IntHashtable();
/* 148 */     StringTokenizer tk = new StringTokenizer(s);
/* 149 */     while (tk.hasMoreTokens()) {
/* 150 */       int n1 = Integer.parseInt(tk.nextToken());
/* 151 */       h.put(n1, Integer.parseInt(tk.nextToken()));
/*     */     } 
/* 153 */     return h;
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/io/font/CidFontProperties.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */