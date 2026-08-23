/*     */ package com.itextpdf.layout.renderer;
/*     */ 
/*     */ import com.itextpdf.io.font.FontProgram;
/*     */ import com.itextpdf.io.font.TrueTypeFont;
/*     */ import com.itextpdf.io.font.otf.Glyph;
/*     */ import com.itextpdf.io.font.otf.GlyphLine;
/*     */ import com.itextpdf.io.util.MessageFormatUtil;
/*     */ import com.itextpdf.kernel.font.PdfFont;
/*     */ import com.itextpdf.layout.property.BaseDirection;
/*     */ import java.lang.reflect.AccessibleObject;
/*     */ import java.lang.reflect.Constructor;
/*     */ import java.lang.reflect.Method;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.Collection;
/*     */ import java.util.HashMap;
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
/*     */ public final class TypographyUtils
/*     */ {
/*  69 */   private static final Logger logger = LoggerFactory.getLogger(TypographyUtils.class);
/*     */   
/*     */   private static final String TYPOGRAPHY_PACKAGE = "com.itextpdf.typography.";
/*     */   
/*     */   private static final String SHAPER = "shaping.Shaper";
/*     */   
/*     */   private static final String BIDI_CHARACTER_MAP = "bidi.BidiCharacterMap";
/*     */   
/*     */   private static final String BIDI_BRACKET_MAP = "bidi.BidiBracketMap";
/*     */   
/*     */   private static final String BIDI_ALGORITHM = "bidi.BidiAlgorithm";
/*     */   
/*     */   private static final String WORD_WRAPPER = "WordWrapper";
/*     */   private static final String APPLY_OTF_SCRIPT = "applyOtfScript";
/*     */   private static final String APPLY_KERNING = "applyKerning";
/*     */   private static final String GET_SUPPORTED_SCRIPTS = "getSupportedScripts";
/*     */   private static final String GET_POSSIBLE_BREAKS = "getPossibleBreaks";
/*     */   private static final String GET_CHARACTER_TYPES = "getCharacterTypes";
/*     */   private static final String GET_BRACKET_TYPES = "getBracketTypes";
/*     */   private static final String GET_BRACKET_VALUES = "getBracketValues";
/*     */   private static final String GET_PAIRED_BRACKET = "getPairedBracket";
/*     */   private static final String GET_LEVELS = "getLevels";
/*     */   private static final String COMPUTE_REORDERING = "computeReordering";
/*     */   private static final String INVERSE_REORDERING = "inverseReordering";
/*     */   private static final Collection<Character.UnicodeScript> SUPPORTED_SCRIPTS;
/*     */   private static final boolean TYPOGRAPHY_MODULE_INITIALIZED;
/*  95 */   private static Map<String, Class<?>> cachedClasses = new HashMap<>();
/*  96 */   private static Map<TypographyMethodSignature, AccessibleObject> cachedMethods = new HashMap<>();
/*     */   
/*     */   private static final String typographyNotFoundException = "Cannot find pdfCalligraph module, which was implicitly required by one of the layout properties";
/*     */   
/*     */   static {
/* 101 */     boolean moduleFound = false;
/*     */     try {
/* 103 */       Class<?> type = getTypographyClass("com.itextpdf.typography.shaping.Shaper");
/* 104 */       if (type != null) {
/* 105 */         moduleFound = true;
/*     */       }
/* 107 */     } catch (ClassNotFoundException classNotFoundException) {}
/*     */     
/* 109 */     Collection<Character.UnicodeScript> supportedScripts = null;
/* 110 */     if (moduleFound) {
/*     */       try {
/* 112 */         supportedScripts = (Collection<Character.UnicodeScript>)callMethod("com.itextpdf.typography.shaping.Shaper", "getSupportedScripts", new Class[0], new Object[0]);
/* 113 */       } catch (Exception e) {
/* 114 */         supportedScripts = null;
/* 115 */         logger.error(e.getMessage());
/*     */       } 
/*     */     }
/* 118 */     moduleFound = (supportedScripts != null);
/* 119 */     if (!moduleFound) {
/* 120 */       cachedClasses.clear();
/* 121 */       cachedMethods.clear();
/*     */     } 
/* 123 */     TYPOGRAPHY_MODULE_INITIALIZED = moduleFound;
/* 124 */     SUPPORTED_SCRIPTS = supportedScripts;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean isPdfCalligraphAvailable() {
/* 135 */     return TYPOGRAPHY_MODULE_INITIALIZED;
/*     */   }
/*     */   
/*     */   static void applyOtfScript(FontProgram fontProgram, GlyphLine text, Character.UnicodeScript script, Object typographyConfig) {
/* 139 */     if (!TYPOGRAPHY_MODULE_INITIALIZED) {
/* 140 */       logger.warn("Cannot find pdfCalligraph module, which was implicitly required by one of the layout properties");
/*     */     } else {
/* 142 */       callMethod("com.itextpdf.typography.shaping.Shaper", "applyOtfScript", new Class[] { TrueTypeFont.class, GlyphLine.class, Character.UnicodeScript.class, Object.class }, new Object[] { fontProgram, text, script, typographyConfig });
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   static void applyKerning(FontProgram fontProgram, GlyphLine text) {
/* 148 */     if (!TYPOGRAPHY_MODULE_INITIALIZED) {
/* 149 */       logger.warn("Cannot find pdfCalligraph module, which was implicitly required by one of the layout properties");
/*     */     } else {
/* 151 */       callMethod("com.itextpdf.typography.shaping.Shaper", "applyKerning", new Class[] { FontProgram.class, GlyphLine.class }, new Object[] { fontProgram, text });
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   static byte[] getBidiLevels(BaseDirection baseDirection, int[] unicodeIds) {
/* 158 */     if (!TYPOGRAPHY_MODULE_INITIALIZED) {
/* 159 */       logger.warn("Cannot find pdfCalligraph module, which was implicitly required by one of the layout properties");
/*     */     } else {
/*     */       byte direction;
/* 162 */       switch (baseDirection) {
/*     */         case LEFT_TO_RIGHT:
/* 164 */           direction = 0;
/*     */           break;
/*     */         case RIGHT_TO_LEFT:
/* 167 */           direction = 1;
/*     */           break;
/*     */         
/*     */         default:
/* 171 */           direction = 2;
/*     */           break;
/*     */       } 
/*     */       
/* 175 */       int len = unicodeIds.length;
/* 176 */       byte[] types = (byte[])callMethod("com.itextpdf.typography.bidi.BidiCharacterMap", "getCharacterTypes", new Class[] { int[].class, int.class, int.class }, new Object[] { unicodeIds, 
/* 177 */             Integer.valueOf(0), Integer.valueOf(len) });
/*     */       
/* 179 */       byte[] pairTypes = (byte[])callMethod("com.itextpdf.typography.bidi.BidiBracketMap", "getBracketTypes", new Class[] { int[].class, int.class, int.class }, new Object[] { unicodeIds, 
/* 180 */             Integer.valueOf(0), Integer.valueOf(len) });
/*     */       
/* 182 */       int[] pairValues = (int[])callMethod("com.itextpdf.typography.bidi.BidiBracketMap", "getBracketValues", new Class[] { int[].class, int.class, int.class }, new Object[] { unicodeIds, 
/* 183 */             Integer.valueOf(0), Integer.valueOf(len) });
/*     */       
/* 185 */       Object bidiReorder = callConstructor("com.itextpdf.typography.bidi.BidiAlgorithm", new Class[] { byte[].class, byte[].class, int[].class, byte.class }, new Object[] { types, pairTypes, pairValues, 
/* 186 */             Byte.valueOf(direction) });
/*     */       
/* 188 */       return (byte[])callMethod("com.itextpdf.typography.bidi.BidiAlgorithm", "getLevels", bidiReorder, new Class[] { int[].class }, new Object[] { { len } });
/*     */     } 
/*     */ 
/*     */     
/* 192 */     return null;
/*     */   }
/*     */   
/*     */   static int[] reorderLine(List<LineRenderer.RendererGlyph> line, byte[] lineLevels, byte[] levels) {
/* 196 */     if (!TYPOGRAPHY_MODULE_INITIALIZED) {
/* 197 */       logger.warn("Cannot find pdfCalligraph module, which was implicitly required by one of the layout properties");
/*     */     } else {
/* 199 */       if (levels == null) {
/* 200 */         return null;
/*     */       }
/* 202 */       int[] reorder = (int[])callMethod("com.itextpdf.typography.bidi.BidiAlgorithm", "computeReordering", new Class[] { byte[].class }, new Object[] { lineLevels });
/*     */ 
/*     */       
/* 205 */       int[] inverseReorder = (int[])callMethod("com.itextpdf.typography.bidi.BidiAlgorithm", "inverseReordering", new Class[] { int[].class }, new Object[] { reorder });
/*     */       
/* 207 */       List<LineRenderer.RendererGlyph> reorderedLine = new ArrayList<>(lineLevels.length); int i;
/* 208 */       for (i = 0; i < line.size(); i++) {
/* 209 */         reorderedLine.add(line.get(reorder[i]));
/*     */ 
/*     */         
/* 212 */         if (levels[reorder[i]] % 2 == 1 && 
/* 213 */           ((LineRenderer.RendererGlyph)reorderedLine.get(i)).glyph.hasValidUnicode()) {
/* 214 */           int unicode = ((LineRenderer.RendererGlyph)reorderedLine.get(i)).glyph.getUnicode();
/* 215 */           int pairedBracket = ((Integer)callMethod("com.itextpdf.typography.bidi.BidiBracketMap", "getPairedBracket", new Class[] { int.class }, new Object[] {
/* 216 */                 Integer.valueOf(unicode)
/*     */               })).intValue();
/* 218 */           if (pairedBracket != unicode) {
/* 219 */             PdfFont font = ((LineRenderer.RendererGlyph)reorderedLine.get(i)).renderer.getPropertyAsFont(20);
/* 220 */             reorderedLine.set(i, new LineRenderer.RendererGlyph(font.getGlyph(pairedBracket), ((LineRenderer.RendererGlyph)reorderedLine.get(i)).renderer));
/*     */           } 
/*     */         } 
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 227 */       for (i = 0; i < reorderedLine.size(); i++) {
/* 228 */         Glyph glyph = ((LineRenderer.RendererGlyph)reorderedLine.get(i)).glyph;
/* 229 */         if (glyph.hasPlacement()) {
/* 230 */           int oldAnchor = reorder[i] + glyph.getAnchorDelta();
/* 231 */           int newPos = inverseReorder[oldAnchor];
/* 232 */           int newAnchorDelta = newPos - i;
/* 233 */           glyph.setAnchorDelta((short)newAnchorDelta);
/*     */         } 
/*     */       } 
/*     */       
/* 237 */       line.clear();
/* 238 */       line.addAll(reorderedLine);
/* 239 */       return reorder;
/*     */     } 
/* 241 */     return null;
/*     */   }
/*     */   
/*     */   static Collection<Character.UnicodeScript> getSupportedScripts() {
/* 245 */     if (!TYPOGRAPHY_MODULE_INITIALIZED) {
/* 246 */       logger.warn("Cannot find pdfCalligraph module, which was implicitly required by one of the layout properties");
/* 247 */       return null;
/*     */     } 
/* 249 */     return SUPPORTED_SCRIPTS;
/*     */   }
/*     */ 
/*     */   
/*     */   static Collection<Character.UnicodeScript> getSupportedScripts(Object typographyConfig) {
/* 254 */     if (!TYPOGRAPHY_MODULE_INITIALIZED) {
/* 255 */       logger.warn("Cannot find pdfCalligraph module, which was implicitly required by one of the layout properties");
/* 256 */       return null;
/*     */     } 
/* 258 */     return (Collection<Character.UnicodeScript>)callMethod("com.itextpdf.typography.shaping.Shaper", "getSupportedScripts", null, new Class[] { Object.class }, new Object[] { typographyConfig });
/*     */   }
/*     */ 
/*     */   
/*     */   static List<Integer> getPossibleBreaks(String str) {
/* 263 */     return (List<Integer>)callMethod("com.itextpdf.typography.WordWrapper", "getPossibleBreaks", new Class[] { String.class }, new Object[] { str });
/*     */   }
/*     */   
/*     */   private static Object callMethod(String className, String methodName, Class[] parameterTypes, Object... args) {
/* 267 */     return callMethod(className, methodName, null, parameterTypes, args);
/*     */   }
/*     */   
/*     */   private static Object callMethod(String className, String methodName, Object target, Class[] parameterTypes, Object... args) {
/*     */     try {
/* 272 */       Method method = findMethod(className, methodName, parameterTypes);
/* 273 */       return method.invoke(target, args);
/* 274 */     } catch (NoSuchMethodException e) {
/* 275 */       logger.warn(MessageFormatUtil.format("Cannot find method {0} for class {1}", new Object[] { methodName, className }));
/* 276 */     } catch (ClassNotFoundException e) {
/* 277 */       logger.warn(MessageFormatUtil.format("Cannot find class {0}", new Object[] { className }));
/* 278 */     } catch (IllegalArgumentException e) {
/* 279 */       logger.warn(MessageFormatUtil.format("Illegal arguments passed to {0}#{1} method call: {2}", new Object[] { className, methodName, e.getMessage() }));
/* 280 */     } catch (Exception e) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 290 */       throw new RuntimeException(e.toString(), e);
/*     */     } 
/* 292 */     return null;
/*     */   }
/*     */   
/*     */   private static Object callConstructor(String className, Class[] parameterTypes, Object... args) {
/*     */     try {
/* 297 */       Constructor<?> constructor = findConstructor(className, parameterTypes);
/* 298 */       return constructor.newInstance(args);
/* 299 */     } catch (NoSuchMethodException e) {
/* 300 */       logger.warn(MessageFormatUtil.format("Cannot find constructor for class {0}", new Object[] { className }));
/* 301 */     } catch (ClassNotFoundException e) {
/* 302 */       logger.warn(MessageFormatUtil.format("Cannot find class {0}", new Object[] { className }));
/* 303 */     } catch (Exception exc) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 313 */       throw new RuntimeException(exc.toString(), exc);
/*     */     } 
/* 315 */     return null;
/*     */   }
/*     */   
/*     */   private static Method findMethod(String className, String methodName, Class[] parameterTypes) throws NoSuchMethodException, ClassNotFoundException {
/* 319 */     TypographyMethodSignature tm = new TypographyMethodSignature(className, parameterTypes, methodName);
/* 320 */     Method m = (Method)cachedMethods.get(tm);
/* 321 */     if (m == null) {
/* 322 */       m = findClass(className).getMethod(methodName, parameterTypes);
/* 323 */       cachedMethods.put(tm, m);
/*     */     } 
/* 325 */     return m;
/*     */   }
/*     */   
/*     */   private static Constructor<?> findConstructor(String className, Class[] parameterTypes) throws NoSuchMethodException, ClassNotFoundException {
/* 329 */     TypographyMethodSignature tc = new TypographyMethodSignature(className, parameterTypes);
/* 330 */     Constructor<?> c = (Constructor)cachedMethods.get(tc);
/* 331 */     if (c == null) {
/* 332 */       c = findClass(className).getConstructor(parameterTypes);
/* 333 */       cachedMethods.put(tc, c);
/*     */     } 
/* 335 */     return c;
/*     */   }
/*     */   
/*     */   private static Class<?> findClass(String className) throws ClassNotFoundException {
/* 339 */     Class<?> c = cachedClasses.get(className);
/* 340 */     if (c == null) {
/* 341 */       c = getTypographyClass(className);
/* 342 */       cachedClasses.put(className, c);
/*     */     } 
/* 344 */     return c;
/*     */   }
/*     */   
/*     */   private static Class<?> getTypographyClass(String typographyClassName) throws ClassNotFoundException {
/* 348 */     return Class.forName(typographyClassName);
/*     */   }
/*     */   
/*     */   private static class TypographyMethodSignature {
/*     */     protected final String className;
/*     */     protected Class[] parameterTypes;
/*     */     private final String methodName;
/*     */     
/*     */     TypographyMethodSignature(String className, Class[] parameterTypes) {
/* 357 */       this(className, parameterTypes, null);
/*     */     }
/*     */     
/*     */     TypographyMethodSignature(String className, Class[] parameterTypes, String methodName) {
/* 361 */       this.methodName = methodName;
/* 362 */       this.className = className;
/* 363 */       this.parameterTypes = parameterTypes;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean equals(Object o) {
/* 368 */       if (this == o) return true; 
/* 369 */       if (o == null || getClass() != o.getClass()) return false;
/*     */       
/* 371 */       TypographyMethodSignature that = (TypographyMethodSignature)o;
/*     */       
/* 373 */       if (!this.className.equals(that.className)) return false; 
/* 374 */       if (!Arrays.equals((Object[])this.parameterTypes, (Object[])that.parameterTypes)) return false; 
/* 375 */       return (this.methodName != null) ? this.methodName.equals(that.methodName) : ((that.methodName == null));
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public int hashCode() {
/* 381 */       int result = this.className.hashCode();
/* 382 */       result = 31 * result + Arrays.hashCode((Object[])this.parameterTypes);
/* 383 */       result = 31 * result + ((this.methodName != null) ? this.methodName.hashCode() : 0);
/* 384 */       return result;
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/layout/renderer/TypographyUtils.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */