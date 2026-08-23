/*     */ package com.itextpdf.io.font;
/*     */ 
/*     */ import com.itextpdf.io.IOException;
/*     */ import com.itextpdf.io.font.cmap.AbstractCMap;
/*     */ import com.itextpdf.io.font.cmap.CMapByteCid;
/*     */ import com.itextpdf.io.font.cmap.CMapCidByte;
/*     */ import com.itextpdf.io.font.cmap.CMapCidUni;
/*     */ import com.itextpdf.io.font.cmap.CMapLocationResource;
/*     */ import com.itextpdf.io.font.cmap.CMapParser;
/*     */ import com.itextpdf.io.font.cmap.CMapUniCid;
/*     */ import com.itextpdf.io.font.cmap.ICMapLocation;
/*     */ import com.itextpdf.io.util.IntHashtable;
/*     */ import com.itextpdf.io.util.ResourceUtil;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.util.HashMap;
/*     */ import java.util.HashSet;
/*     */ import java.util.LinkedHashMap;
/*     */ import java.util.Map;
/*     */ import java.util.Properties;
/*     */ import java.util.Set;
/*     */ import java.util.StringTokenizer;
/*     */ import java.util.concurrent.ConcurrentHashMap;
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
/*     */ public class FontCache
/*     */ {
/*  69 */   private static final Map<String, Map<String, Object>> allCidFonts = new LinkedHashMap<>();
/*  70 */   private static final Map<String, Set<String>> registryNames = new HashMap<>();
/*     */   
/*     */   private static final String CJK_REGISTRY_FILENAME = "cjk_registry.properties";
/*     */   
/*     */   private static final String FONTS_PROP = "fonts";
/*     */   private static final String REGISTRY_PROP = "Registry";
/*     */   private static final String W_PROP = "W";
/*     */   private static final String W2_PROP = "W2";
/*  78 */   private static Map<FontCacheKey, FontProgram> fontCache = new ConcurrentHashMap<>();
/*     */   
/*     */   static {
/*     */     try {
/*  82 */       loadRegistry();
/*  83 */       for (String font : registryNames.get("fonts")) {
/*  84 */         allCidFonts.put(font, readFontProperties(font));
/*     */       }
/*  86 */     } catch (Exception exception) {}
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected static boolean isPredefinedCidFont(String fontName) {
/*  97 */     if (!registryNames.containsKey("fonts"))
/*  98 */       return false; 
/*  99 */     if (!((Set)registryNames.get("fonts")).contains(fontName)) {
/* 100 */       return false;
/*     */     }
/* 102 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String getCompatibleCidFont(String cmap) {
/* 111 */     for (Map.Entry<String, Set<String>> e : registryNames.entrySet()) {
/* 112 */       if (((Set)e.getValue()).contains(cmap)) {
/* 113 */         String registry = e.getKey();
/* 114 */         for (Map.Entry<String, Map<String, Object>> e1 : allCidFonts.entrySet()) {
/* 115 */           if (registry.equals(((Map)e1.getValue()).get("Registry")))
/* 116 */             return e1.getKey(); 
/*     */         } 
/*     */       } 
/*     */     } 
/* 120 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Set<String> getCompatibleCmaps(String fontName) {
/* 130 */     Map<String, Object> cidFonts = getAllPredefinedCidFonts().get(fontName);
/* 131 */     if (cidFonts == null) {
/* 132 */       return null;
/*     */     }
/* 134 */     String registry = (String)cidFonts.get("Registry");
/* 135 */     return registryNames.get(registry);
/*     */   }
/*     */   
/*     */   public static Map<String, Map<String, Object>> getAllPredefinedCidFonts() {
/* 139 */     return allCidFonts;
/*     */   }
/*     */   
/*     */   public static Map<String, Set<String>> getRegistryNames() {
/* 143 */     return registryNames;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static CMapCidUni getCid2UniCmap(String uniMap) {
/* 152 */     CMapCidUni cidUni = new CMapCidUni();
/* 153 */     return parseCmap(uniMap, cidUni);
/*     */   }
/*     */   
/*     */   public static CMapUniCid getUni2CidCmap(String uniMap) {
/* 157 */     CMapUniCid uniCid = new CMapUniCid();
/* 158 */     return parseCmap(uniMap, uniCid);
/*     */   }
/*     */   
/*     */   public static CMapByteCid getByte2CidCmap(String cmap) {
/* 162 */     CMapByteCid uniCid = new CMapByteCid();
/* 163 */     return parseCmap(cmap, uniCid);
/*     */   }
/*     */   
/*     */   public static CMapCidByte getCid2Byte(String cmap) {
/* 167 */     CMapCidByte cidByte = new CMapCidByte();
/* 168 */     return parseCmap(cmap, cidByte);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void clearSavedFonts() {
/* 178 */     fontCache.clear();
/*     */   }
/*     */   
/*     */   public static FontProgram getFont(String fontName) {
/* 182 */     return fontCache.get(FontCacheKey.create(fontName));
/*     */   }
/*     */   
/*     */   static FontProgram getFont(FontCacheKey key) {
/* 186 */     return fontCache.get(key);
/*     */   }
/*     */   
/*     */   public static FontProgram saveFont(FontProgram font, String fontName) {
/* 190 */     return saveFont(font, FontCacheKey.create(fontName));
/*     */   }
/*     */   
/*     */   static FontProgram saveFont(FontProgram font, FontCacheKey key) {
/* 194 */     FontProgram fontFound = fontCache.get(key);
/* 195 */     if (fontFound != null) {
/* 196 */       return fontFound;
/*     */     }
/* 198 */     fontCache.put(key, font);
/* 199 */     return font;
/*     */   }
/*     */   
/*     */   private static void loadRegistry() throws IOException {
/* 203 */     InputStream resource = ResourceUtil.getResourceStream("com/itextpdf/io/font/cmap/cjk_registry.properties");
/*     */     try {
/* 205 */       Properties p = new Properties();
/* 206 */       p.load(resource);
/*     */       
/* 208 */       for (Map.Entry<Object, Object> entry : p.entrySet()) {
/* 209 */         String value = (String)entry.getValue();
/* 210 */         String[] splitValue = value.split(" ");
/* 211 */         Set<String> set = new HashSet<>();
/*     */         
/* 213 */         for (String s : splitValue) {
/* 214 */           if (s.length() != 0) {
/* 215 */             set.add(s);
/*     */           }
/*     */         } 
/*     */         
/* 219 */         registryNames.put((String)entry.getKey(), set);
/*     */       } 
/*     */     } finally {
/* 222 */       if (resource != null) {
/* 223 */         resource.close();
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   private static Map<String, Object> readFontProperties(String name) throws IOException {
/* 229 */     InputStream resource = ResourceUtil.getResourceStream("com/itextpdf/io/font/cmap/" + name + ".properties");
/*     */     
/*     */     try {
/* 232 */       Properties p = new Properties();
/* 233 */       p.load(resource);
/*     */       
/* 235 */       Map<String, Object> fontProperties = new HashMap<>();
/* 236 */       for (Map.Entry<Object, Object> entry : p.entrySet()) {
/* 237 */         fontProperties.put((String)entry.getKey(), entry.getValue());
/*     */       }
/* 239 */       fontProperties.put("W", createMetric((String)fontProperties.get("W")));
/* 240 */       fontProperties.put("W2", createMetric((String)fontProperties.get("W2")));
/*     */       
/* 242 */       return fontProperties;
/*     */     } finally {
/* 244 */       if (resource != null) {
/* 245 */         resource.close();
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   private static IntHashtable createMetric(String s) {
/* 251 */     IntHashtable h = new IntHashtable();
/* 252 */     StringTokenizer tk = new StringTokenizer(s);
/*     */     
/* 254 */     while (tk.hasMoreTokens()) {
/* 255 */       int n1 = Integer.parseInt(tk.nextToken());
/* 256 */       h.put(n1, Integer.parseInt(tk.nextToken()));
/*     */     } 
/*     */     
/* 259 */     return h;
/*     */   }
/*     */   
/*     */   private static <T extends AbstractCMap> T parseCmap(String name, T cmap) {
/*     */     try {
/* 264 */       CMapParser.parseCid(name, (AbstractCMap)cmap, (ICMapLocation)new CMapLocationResource());
/* 265 */     } catch (IOException e) {
/* 266 */       throw new IOException("I/O exception.", e);
/*     */     } 
/* 268 */     return cmap;
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/io/font/FontCache.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */