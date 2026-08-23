/*     */ package com.itextpdf.layout.hyphenation;
/*     */ 
/*     */ import com.itextpdf.io.util.ResourceUtil;
/*     */ import java.io.File;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import org.slf4j.Logger;
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
/*     */ public final class Hyphenator
/*     */ {
/*     */   private static final char SOFT_HYPHEN = '­';
/*  42 */   private static final Object staticLock = new Object();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  47 */   private static Logger log = LoggerFactory.getLogger(Hyphenator.class);
/*     */ 
/*     */   
/*     */   private static HyphenationTreeCache hTreeCache;
/*     */ 
/*     */   
/*     */   private static List<String> additionalHyphenationFileDirectories;
/*     */ 
/*     */   
/*     */   protected String lang;
/*     */   
/*     */   protected String country;
/*     */   
/*     */   int leftMin;
/*     */   
/*     */   int rightMin;
/*     */   
/*     */   Map<String, String> hyphPathNames;
/*     */ 
/*     */   
/*     */   public Hyphenator(String lang, String country, int leftMin, int rightMin) {
/*  68 */     this.lang = lang;
/*  69 */     this.country = country;
/*  70 */     this.leftMin = leftMin;
/*  71 */     this.rightMin = rightMin;
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
/*     */   public Hyphenator(String lang, String country, int leftMin, int rightMin, Map<String, String> hyphPathNames) {
/*  84 */     this(lang, country, leftMin, rightMin);
/*  85 */     this.hyphPathNames = hyphPathNames;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void registerAdditionalHyphenationFileDirectory(String directory) {
/*  94 */     synchronized (staticLock) {
/*  95 */       if (additionalHyphenationFileDirectories == null) {
/*  96 */         additionalHyphenationFileDirectories = new ArrayList<>();
/*     */       }
/*  98 */       additionalHyphenationFileDirectories.add(directory);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static HyphenationTreeCache getHyphenationTreeCache() {
/* 108 */     synchronized (staticLock) {
/* 109 */       if (hTreeCache == null) {
/* 110 */         hTreeCache = new HyphenationTreeCache();
/*     */       }
/*     */     } 
/* 113 */     return hTreeCache;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void clearHyphenationTreeCache() {
/* 120 */     synchronized (staticLock) {
/* 121 */       hTreeCache = new HyphenationTreeCache();
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
/*     */   
/*     */   public static HyphenationTree getHyphenationTree(String lang, String country, Map<String, String> hyphPathNames) {
/* 136 */     String llccKey = HyphenationTreeCache.constructLlccKey(lang, country);
/* 137 */     HyphenationTreeCache cache = getHyphenationTreeCache();
/*     */ 
/*     */     
/* 140 */     if (cache.isMissing(llccKey)) {
/* 141 */       return null;
/*     */     }
/*     */     
/* 144 */     HyphenationTree hTree = getHyphenationTree2(lang, country, hyphPathNames);
/*     */ 
/*     */     
/* 147 */     if (hTree == null && country != null && !country.equals("none")) {
/* 148 */       String llKey = HyphenationTreeCache.constructLlccKey(lang, null);
/* 149 */       if (!cache.isMissing(llKey)) {
/* 150 */         hTree = getHyphenationTree2(lang, null, hyphPathNames);
/* 151 */         if (hTree != null && log.isDebugEnabled()) {
/* 152 */           log.debug("Couldn't find hyphenation pattern for lang=\"" + lang + "\",country=\"" + country + "\". Using general language pattern for lang=\"" + lang + "\" instead.");
/*     */         }
/*     */ 
/*     */ 
/*     */         
/* 157 */         if (hTree == null) {
/*     */           
/* 159 */           cache.noteMissing(llKey);
/*     */         } else {
/*     */           
/* 162 */           cache.cache(llccKey, hTree);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 167 */     if (hTree == null) {
/*     */       
/* 169 */       cache.noteMissing(llccKey);
/* 170 */       log.error("Couldn't find hyphenation pattern for lang=\"" + lang + "\"" + ((country != null && 
/*     */           
/* 172 */           !country.equals("none")) ? (",country=\"" + country + "\"") : "") + ".");
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 178 */     return hTree;
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
/*     */   public static HyphenationTree getHyphenationTree2(String lang, String country, Map<String, String> hyphPathNames) {
/* 190 */     String llccKey = HyphenationTreeCache.constructLlccKey(lang, country);
/* 191 */     HyphenationTreeCache cache = getHyphenationTreeCache();
/*     */ 
/*     */ 
/*     */     
/* 195 */     HyphenationTree hTree = getHyphenationTreeCache().getHyphenationTree(lang, country);
/* 196 */     if (hTree != null) {
/* 197 */       return hTree;
/*     */     }
/*     */     
/* 200 */     String key = HyphenationTreeCache.constructUserKey(lang, country, hyphPathNames);
/* 201 */     if (key == null) {
/* 202 */       key = llccKey;
/*     */     }
/*     */     
/* 205 */     if (additionalHyphenationFileDirectories != null) {
/* 206 */       for (String dir : additionalHyphenationFileDirectories) {
/* 207 */         hTree = getHyphenationTree(dir, key);
/* 208 */         if (hTree != null) {
/*     */           break;
/*     */         }
/*     */       } 
/*     */     }
/*     */     
/* 214 */     if (hTree == null) {
/*     */       
/* 216 */       InputStream defaultHyphenationResourceStream = ResourceUtil.getResourceStream("com/itextpdf/hyph/" + key + ".xml");
/* 217 */       if (defaultHyphenationResourceStream != null) {
/* 218 */         hTree = getHyphenationTree(defaultHyphenationResourceStream, key);
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 223 */     if (hTree != null) {
/* 224 */       cache.cache(llccKey, hTree);
/*     */     }
/*     */     
/* 227 */     return hTree;
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
/*     */   public static HyphenationTree getHyphenationTree(String searchDirectory, String key) {
/* 239 */     String name = key + ".xml";
/*     */     try {
/* 241 */       InputStream fis = new FileInputStream(searchDirectory + File.separator + name);
/* 242 */       return getHyphenationTree(fis, name);
/* 243 */     } catch (IOException ioe) {
/* 244 */       if (log.isDebugEnabled()) {
/* 245 */         log.debug("I/O problem while trying to load " + name + ": " + ioe.getMessage());
/*     */       }
/* 247 */       return null;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static HyphenationTree getHyphenationTree(InputStream in, String name) {
/*     */     HyphenationTree hTree;
/* 259 */     if (in == null) {
/* 260 */       return null;
/*     */     }
/*     */     
/*     */     try {
/* 264 */       hTree = new HyphenationTree();
/* 265 */       hTree.loadPatterns(in, name);
/*     */     }
/* 267 */     catch (HyphenationException ex) {
/* 268 */       log.error("Can't load user patterns from XML file " + name + ": " + ex.getMessage());
/* 269 */       return null;
/*     */     } finally {
/*     */       
/*     */       try {
/* 273 */         in.close();
/* 274 */       } catch (Exception exception) {}
/*     */     } 
/* 276 */     return hTree;
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
/*     */ 
/*     */ 
/*     */   
/*     */   public static Hyphenation hyphenate(String lang, String country, Map<String, String> hyphPathNames, String word, int leftMin, int rightMin) {
/* 292 */     if (wordContainsSoftHyphens(word)) {
/* 293 */       return hyphenateBasedOnSoftHyphens(word, leftMin, rightMin);
/*     */     }
/* 295 */     HyphenationTree hTree = null;
/* 296 */     if (lang != null) {
/* 297 */       hTree = getHyphenationTree(lang, country, hyphPathNames);
/*     */     }
/* 299 */     return (hTree != null) ? hTree.hyphenate(word, leftMin, rightMin) : null;
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
/*     */ 
/*     */   
/*     */   public static Hyphenation hyphenate(String lang, String country, String word, int leftMin, int rightMin) {
/* 314 */     return hyphenate(lang, country, null, word, leftMin, rightMin);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Hyphenation hyphenate(String word) {
/* 324 */     return hyphenate(this.lang, this.country, this.hyphPathNames, word, this.leftMin, this.rightMin);
/*     */   }
/*     */   
/*     */   private static boolean wordContainsSoftHyphens(String word) {
/* 328 */     return (word.indexOf('­') >= 0);
/*     */   }
/*     */   
/*     */   private static Hyphenation hyphenateBasedOnSoftHyphens(String word, int leftMin, int rightMin) {
/* 332 */     List<Integer> softHyphens = new ArrayList<>();
/* 333 */     int lastSoftHyphenIndex = -1;
/*     */     int curSoftHyphenIndex;
/* 335 */     while ((curSoftHyphenIndex = word.indexOf('­', lastSoftHyphenIndex + 1)) > 0) {
/* 336 */       softHyphens.add(Integer.valueOf(curSoftHyphenIndex));
/* 337 */       lastSoftHyphenIndex = curSoftHyphenIndex;
/*     */     } 
/* 339 */     int leftInd = 0, rightInd = softHyphens.size() - 1;
/* 340 */     while (leftInd < softHyphens.size() && word.substring(0, ((Integer)softHyphens.get(leftInd)).intValue()).replace(String.valueOf('­'), "").length() < leftMin) {
/* 341 */       leftInd++;
/*     */     }
/* 343 */     while (rightInd >= 0 && word.substring(((Integer)softHyphens.get(rightInd)).intValue() + 1).replace(String.valueOf('­'), "").length() < rightMin) {
/* 344 */       rightInd--;
/*     */     }
/* 346 */     if (leftInd <= rightInd) {
/* 347 */       int[] hyphenationPoints = new int[rightInd - leftInd + 1];
/* 348 */       for (int i = leftInd; i <= rightInd; i++) {
/* 349 */         hyphenationPoints[i - leftInd] = ((Integer)softHyphens.get(i)).intValue();
/*     */       }
/* 351 */       return new Hyphenation(word, hyphenationPoints);
/*     */     } 
/* 353 */     return null;
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/layout/hyphenation/Hyphenator.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */