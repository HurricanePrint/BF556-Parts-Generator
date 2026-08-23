/*     */ package com.itextpdf.styledxmlparser.jsoup.nodes;
/*     */ 
/*     */ import com.itextpdf.styledxmlparser.jsoup.SerializationException;
/*     */ import com.itextpdf.styledxmlparser.jsoup.parser.Parser;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.nio.charset.CharsetEncoder;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import java.util.MissingResourceException;
/*     */ import java.util.Properties;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Entities
/*     */ {
/*     */   private static final Map<String, Character> full;
/*     */   private static final Map<Character, String> xhtmlByVal;
/*     */   private static final Map<String, Character> base;
/*     */   private static final Map<Character, String> baseByVal;
/*     */   private static final Map<Character, String> fullByVal;
/*     */   
/*     */   public static class EscapeMode
/*     */   {
/*  67 */     public static final EscapeMode xhtml = new EscapeMode(Entities.xhtmlByVal, "xhtml");
/*     */ 
/*     */ 
/*     */     
/*  71 */     public static final EscapeMode base = new EscapeMode(Entities.baseByVal, "base");
/*     */ 
/*     */ 
/*     */     
/*  75 */     public static final EscapeMode extended = new EscapeMode(Entities.fullByVal, "extended");
/*     */     
/*  77 */     private static Map<String, EscapeMode> nameValueMap = new HashMap<>(); private Map<Character, String> map;
/*     */     
/*     */     public static EscapeMode valueOf(String name) {
/*  80 */       return nameValueMap.get(name);
/*     */     }
/*     */     private String name;
/*     */     static {
/*  84 */       nameValueMap.put(xhtml.name, xhtml);
/*  85 */       nameValueMap.put(base.name, base);
/*  86 */       nameValueMap.put(extended.name, extended);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private EscapeMode(Map<Character, String> map, String name) {
/*  93 */       this.map = map;
/*  94 */       this.name = name;
/*     */     }
/*     */     
/*     */     public Map<Character, String> getMap() {
/*  98 */       return this.map;
/*     */     }
/*     */     
/*     */     public String name() {
/* 102 */       return this.name;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean isNamedEntity(String name) {
/* 122 */     return full.containsKey(name);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean isBaseNamedEntity(String name) {
/* 133 */     return base.containsKey(name);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Character getCharacterByName(String name) {
/* 143 */     return full.get(name);
/*     */   }
/*     */   
/*     */   static String escape(String string, Document.OutputSettings out) {
/* 147 */     StringBuilder accum = new StringBuilder(string.length() * 2);
/*     */     try {
/* 149 */       escape(accum, string, out, false, false, false);
/* 150 */     } catch (IOException e) {
/* 151 */       throw new SerializationException(e);
/*     */     } 
/* 153 */     return accum.toString();
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static void escape(Appendable accum, String str, Document.OutputSettings outputSettings, boolean inAttribute, boolean normaliseWhite, boolean stripLeadingWhite) throws IOException {
/*     */     // Byte code:
/*     */     //   0: iconst_0
/*     */     //   1: istore #6
/*     */     //   3: iconst_0
/*     */     //   4: istore #7
/*     */     //   6: aload_2
/*     */     //   7: invokevirtual escapeMode : ()Lcom/itextpdf/styledxmlparser/jsoup/nodes/Entities$EscapeMode;
/*     */     //   10: astore #8
/*     */     //   12: aload_2
/*     */     //   13: invokevirtual encoder : ()Ljava/nio/charset/CharsetEncoder;
/*     */     //   16: astore #9
/*     */     //   18: aload_2
/*     */     //   19: invokevirtual charset : ()Ljava/nio/charset/Charset;
/*     */     //   22: invokevirtual name : ()Ljava/lang/String;
/*     */     //   25: invokestatic getCoreCharsetByName : (Ljava/lang/String;)Lcom/itextpdf/styledxmlparser/jsoup/nodes/Entities$CoreCharset;
/*     */     //   28: astore #10
/*     */     //   30: aload #8
/*     */     //   32: invokevirtual getMap : ()Ljava/util/Map;
/*     */     //   35: astore #11
/*     */     //   37: aload_1
/*     */     //   38: invokevirtual length : ()I
/*     */     //   41: istore #12
/*     */     //   43: iconst_0
/*     */     //   44: istore #14
/*     */     //   46: iload #14
/*     */     //   48: iload #12
/*     */     //   50: if_icmpge -> 494
/*     */     //   53: aload_1
/*     */     //   54: iload #14
/*     */     //   56: invokevirtual codePointAt : (I)I
/*     */     //   59: istore #13
/*     */     //   61: iload #4
/*     */     //   63: ifeq -> 113
/*     */     //   66: iload #13
/*     */     //   68: invokestatic isWhitespace : (I)Z
/*     */     //   71: ifeq -> 107
/*     */     //   74: iload #5
/*     */     //   76: ifeq -> 84
/*     */     //   79: iload #7
/*     */     //   81: ifeq -> 481
/*     */     //   84: iload #6
/*     */     //   86: ifeq -> 92
/*     */     //   89: goto -> 481
/*     */     //   92: aload_0
/*     */     //   93: bipush #32
/*     */     //   95: invokeinterface append : (C)Ljava/lang/Appendable;
/*     */     //   100: pop
/*     */     //   101: iconst_1
/*     */     //   102: istore #6
/*     */     //   104: goto -> 481
/*     */     //   107: iconst_0
/*     */     //   108: istore #6
/*     */     //   110: iconst_1
/*     */     //   111: istore #7
/*     */     //   113: iload #13
/*     */     //   115: ldc 65536
/*     */     //   117: if_icmpge -> 419
/*     */     //   120: iload #13
/*     */     //   122: i2c
/*     */     //   123: istore #15
/*     */     //   125: iload #15
/*     */     //   127: lookupswitch default -> 312, 34 -> 284, 38 -> 176, 60 -> 220, 62 -> 256, 160 -> 188
/*     */     //   176: aload_0
/*     */     //   177: ldc '&amp;'
/*     */     //   179: invokeinterface append : (Ljava/lang/CharSequence;)Ljava/lang/Appendable;
/*     */     //   184: pop
/*     */     //   185: goto -> 416
/*     */     //   188: aload #8
/*     */     //   190: getstatic com/itextpdf/styledxmlparser/jsoup/nodes/Entities$EscapeMode.xhtml : Lcom/itextpdf/styledxmlparser/jsoup/nodes/Entities$EscapeMode;
/*     */     //   193: if_acmpeq -> 208
/*     */     //   196: aload_0
/*     */     //   197: ldc '&nbsp;'
/*     */     //   199: invokeinterface append : (Ljava/lang/CharSequence;)Ljava/lang/Appendable;
/*     */     //   204: pop
/*     */     //   205: goto -> 416
/*     */     //   208: aload_0
/*     */     //   209: ldc '&#xa0;'
/*     */     //   211: invokeinterface append : (Ljava/lang/CharSequence;)Ljava/lang/Appendable;
/*     */     //   216: pop
/*     */     //   217: goto -> 416
/*     */     //   220: iload_3
/*     */     //   221: ifeq -> 232
/*     */     //   224: aload #8
/*     */     //   226: getstatic com/itextpdf/styledxmlparser/jsoup/nodes/Entities$EscapeMode.xhtml : Lcom/itextpdf/styledxmlparser/jsoup/nodes/Entities$EscapeMode;
/*     */     //   229: if_acmpne -> 244
/*     */     //   232: aload_0
/*     */     //   233: ldc '&lt;'
/*     */     //   235: invokeinterface append : (Ljava/lang/CharSequence;)Ljava/lang/Appendable;
/*     */     //   240: pop
/*     */     //   241: goto -> 416
/*     */     //   244: aload_0
/*     */     //   245: iload #15
/*     */     //   247: invokeinterface append : (C)Ljava/lang/Appendable;
/*     */     //   252: pop
/*     */     //   253: goto -> 416
/*     */     //   256: iload_3
/*     */     //   257: ifne -> 272
/*     */     //   260: aload_0
/*     */     //   261: ldc '&gt;'
/*     */     //   263: invokeinterface append : (Ljava/lang/CharSequence;)Ljava/lang/Appendable;
/*     */     //   268: pop
/*     */     //   269: goto -> 416
/*     */     //   272: aload_0
/*     */     //   273: iload #15
/*     */     //   275: invokeinterface append : (C)Ljava/lang/Appendable;
/*     */     //   280: pop
/*     */     //   281: goto -> 416
/*     */     //   284: iload_3
/*     */     //   285: ifeq -> 300
/*     */     //   288: aload_0
/*     */     //   289: ldc '&quot;'
/*     */     //   291: invokeinterface append : (Ljava/lang/CharSequence;)Ljava/lang/Appendable;
/*     */     //   296: pop
/*     */     //   297: goto -> 416
/*     */     //   300: aload_0
/*     */     //   301: iload #15
/*     */     //   303: invokeinterface append : (C)Ljava/lang/Appendable;
/*     */     //   308: pop
/*     */     //   309: goto -> 416
/*     */     //   312: aload #10
/*     */     //   314: iload #15
/*     */     //   316: aload #9
/*     */     //   318: invokestatic canEncode : (Lcom/itextpdf/styledxmlparser/jsoup/nodes/Entities$CoreCharset;CLjava/nio/charset/CharsetEncoder;)Z
/*     */     //   321: ifeq -> 336
/*     */     //   324: aload_0
/*     */     //   325: iload #15
/*     */     //   327: invokeinterface append : (C)Ljava/lang/Appendable;
/*     */     //   332: pop
/*     */     //   333: goto -> 416
/*     */     //   336: aload #11
/*     */     //   338: iload #15
/*     */     //   340: invokestatic valueOf : (C)Ljava/lang/Character;
/*     */     //   343: invokeinterface containsKey : (Ljava/lang/Object;)Z
/*     */     //   348: ifeq -> 390
/*     */     //   351: aload_0
/*     */     //   352: bipush #38
/*     */     //   354: invokeinterface append : (C)Ljava/lang/Appendable;
/*     */     //   359: aload #11
/*     */     //   361: iload #15
/*     */     //   363: invokestatic valueOf : (C)Ljava/lang/Character;
/*     */     //   366: invokeinterface get : (Ljava/lang/Object;)Ljava/lang/Object;
/*     */     //   371: checkcast java/lang/CharSequence
/*     */     //   374: invokeinterface append : (Ljava/lang/CharSequence;)Ljava/lang/Appendable;
/*     */     //   379: bipush #59
/*     */     //   381: invokeinterface append : (C)Ljava/lang/Appendable;
/*     */     //   386: pop
/*     */     //   387: goto -> 416
/*     */     //   390: aload_0
/*     */     //   391: ldc '&#x'
/*     */     //   393: invokeinterface append : (Ljava/lang/CharSequence;)Ljava/lang/Appendable;
/*     */     //   398: iload #13
/*     */     //   400: invokestatic toHexString : (I)Ljava/lang/String;
/*     */     //   403: invokeinterface append : (Ljava/lang/CharSequence;)Ljava/lang/Appendable;
/*     */     //   408: bipush #59
/*     */     //   410: invokeinterface append : (C)Ljava/lang/Appendable;
/*     */     //   415: pop
/*     */     //   416: goto -> 481
/*     */     //   419: new java/lang/String
/*     */     //   422: dup
/*     */     //   423: iload #13
/*     */     //   425: invokestatic toChars : (I)[C
/*     */     //   428: invokespecial <init> : ([C)V
/*     */     //   431: astore #15
/*     */     //   433: aload #9
/*     */     //   435: aload #15
/*     */     //   437: invokevirtual canEncode : (Ljava/lang/CharSequence;)Z
/*     */     //   440: ifeq -> 455
/*     */     //   443: aload_0
/*     */     //   444: aload #15
/*     */     //   446: invokeinterface append : (Ljava/lang/CharSequence;)Ljava/lang/Appendable;
/*     */     //   451: pop
/*     */     //   452: goto -> 481
/*     */     //   455: aload_0
/*     */     //   456: ldc '&#x'
/*     */     //   458: invokeinterface append : (Ljava/lang/CharSequence;)Ljava/lang/Appendable;
/*     */     //   463: iload #13
/*     */     //   465: invokestatic toHexString : (I)Ljava/lang/String;
/*     */     //   468: invokeinterface append : (Ljava/lang/CharSequence;)Ljava/lang/Appendable;
/*     */     //   473: bipush #59
/*     */     //   475: invokeinterface append : (C)Ljava/lang/Appendable;
/*     */     //   480: pop
/*     */     //   481: iload #14
/*     */     //   483: iload #13
/*     */     //   485: invokestatic charCount : (I)I
/*     */     //   488: iadd
/*     */     //   489: istore #14
/*     */     //   491: goto -> 46
/*     */     //   494: return
/*     */     // Line number table:
/*     */     //   Java source line number -> byte code offset
/*     */     //   #160	-> 0
/*     */     //   #161	-> 3
/*     */     //   #162	-> 6
/*     */     //   #163	-> 12
/*     */     //   #164	-> 18
/*     */     //   #165	-> 30
/*     */     //   #166	-> 37
/*     */     //   #169	-> 43
/*     */     //   #170	-> 53
/*     */     //   #172	-> 61
/*     */     //   #173	-> 66
/*     */     //   #174	-> 74
/*     */     //   #175	-> 89
/*     */     //   #176	-> 92
/*     */     //   #177	-> 101
/*     */     //   #178	-> 104
/*     */     //   #180	-> 107
/*     */     //   #181	-> 110
/*     */     //   #185	-> 113
/*     */     //   #186	-> 120
/*     */     //   #188	-> 125
/*     */     //   #190	-> 176
/*     */     //   #191	-> 185
/*     */     //   #193	-> 188
/*     */     //   #194	-> 196
/*     */     //   #196	-> 208
/*     */     //   #197	-> 217
/*     */     //   #200	-> 220
/*     */     //   #201	-> 232
/*     */     //   #203	-> 244
/*     */     //   #204	-> 253
/*     */     //   #206	-> 256
/*     */     //   #207	-> 260
/*     */     //   #209	-> 272
/*     */     //   #210	-> 281
/*     */     //   #212	-> 284
/*     */     //   #213	-> 288
/*     */     //   #215	-> 300
/*     */     //   #216	-> 309
/*     */     //   #218	-> 312
/*     */     //   #219	-> 324
/*     */     //   #220	-> 336
/*     */     //   #221	-> 351
/*     */     //   #223	-> 390
/*     */     //   #225	-> 416
/*     */     //   #226	-> 419
/*     */     //   #227	-> 433
/*     */     //   #228	-> 443
/*     */     //   #230	-> 455
/*     */     //   #169	-> 481
/*     */     //   #233	-> 494
/*     */     // Local variable table:
/*     */     //   start	length	slot	name	descriptor
/*     */     //   125	291	15	c	C
/*     */     //   433	48	15	c	Ljava/lang/String;
/*     */     //   61	433	13	codePoint	I
/*     */     //   46	448	14	offset	I
/*     */     //   0	495	0	accum	Ljava/lang/Appendable;
/*     */     //   0	495	1	str	Ljava/lang/String;
/*     */     //   0	495	2	outputSettings	Lcom/itextpdf/styledxmlparser/jsoup/nodes/Document$OutputSettings;
/*     */     //   0	495	3	inAttribute	Z
/*     */     //   0	495	4	normaliseWhite	Z
/*     */     //   0	495	5	stripLeadingWhite	Z
/*     */     //   3	492	6	lastWasWhite	Z
/*     */     //   6	489	7	reachedNonWhite	Z
/*     */     //   12	483	8	escapeMode	Lcom/itextpdf/styledxmlparser/jsoup/nodes/Entities$EscapeMode;
/*     */     //   18	477	9	encoder	Ljava/nio/charset/CharsetEncoder;
/*     */     //   30	465	10	coreCharset	Lcom/itextpdf/styledxmlparser/jsoup/nodes/Entities$CoreCharset;
/*     */     //   37	458	11	map	Ljava/util/Map;
/*     */     //   43	452	12	length	I
/*     */     // Local variable type table:
/*     */     //   start	length	slot	name	signature
/*     */     //   37	458	11	map	Ljava/util/Map<Ljava/lang/Character;Ljava/lang/String;>;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static String unescape(String string) {
/* 236 */     return unescape(string, false);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static String unescape(String string, boolean strict) {
/* 247 */     return Parser.unescapeEntities(string, strict);
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
/*     */ 
/*     */ 
/*     */   
/*     */   private static boolean canEncode(CoreCharset charset, char c, CharsetEncoder fallback) {
/* 266 */     switch (charset) {
/*     */       case ascii:
/* 268 */         return (c < '');
/*     */       
/*     */       case utf:
/* 271 */         return true;
/*     */     } 
/* 273 */     return fallback.canEncode(c);
/*     */   }
/*     */   
/*     */   private enum CoreCharset
/*     */   {
/* 278 */     ascii, utf, fallback;
/*     */   }
/*     */   
/*     */   private static CoreCharset getCoreCharsetByName(String name) {
/* 282 */     if (name.equals("US-ASCII"))
/* 283 */       return CoreCharset.ascii; 
/* 284 */     if (name.startsWith("UTF-"))
/* 285 */       return CoreCharset.utf; 
/* 286 */     return CoreCharset.fallback;
/*     */   }
/*     */ 
/*     */   
/* 290 */   private static final Object[][] xhtmlArray = new Object[][] { { "quot", 
/* 291 */         Integer.valueOf(34) }, { "amp", 
/* 292 */         Integer.valueOf(38) }, { "lt", 
/* 293 */         Integer.valueOf(60) }, { "gt", 
/* 294 */         Integer.valueOf(62) } };
/*     */ 
/*     */   
/*     */   static {
/* 298 */     xhtmlByVal = new HashMap<>();
/* 299 */     base = loadEntities("entities-base.properties");
/* 300 */     baseByVal = toCharacterKey(base);
/* 301 */     full = loadEntities("entities-full.properties");
/* 302 */     fullByVal = toCharacterKey(full);
/*     */     
/* 304 */     for (Object[] entity : xhtmlArray) {
/* 305 */       char c = (char)((Integer)entity[1]).intValue();
/* 306 */       xhtmlByVal.put(Character.valueOf(c), (String)entity[0]);
/*     */     } 
/*     */   }
/*     */   
/*     */   private static Map<String, Character> loadEntities(String filename) {
/* 311 */     Properties properties = new Properties();
/* 312 */     Map<String, Character> entities = new HashMap<>();
/*     */     try {
/* 314 */       InputStream in = Entities.class.getResourceAsStream(filename);
/* 315 */       properties.load(in);
/* 316 */       in.close();
/* 317 */     } catch (IOException e) {
/* 318 */       throw new MissingResourceException("Error loading entities resource: " + e.getMessage(), "Entities", filename);
/*     */     } 
/*     */     
/* 321 */     for (Object name : properties.keySet()) {
/* 322 */       Character val = Character.valueOf((char)Integer.parseInt(properties.getProperty((String)name), 16));
/* 323 */       entities.put((String)name, val);
/*     */     } 
/* 325 */     return entities;
/*     */   }
/*     */   
/*     */   private static Map<Character, String> toCharacterKey(Map<String, Character> inMap) {
/* 329 */     Map<Character, String> outMap = new HashMap<>();
/* 330 */     for (Map.Entry<String, Character> entry : inMap.entrySet()) {
/* 331 */       char character = ((Character)entry.getValue()).charValue();
/* 332 */       String name = entry.getKey();
/*     */       
/* 334 */       if (outMap.containsKey(Character.valueOf(character))) {
/*     */         
/* 336 */         if (name.toLowerCase().equals(name))
/* 337 */           outMap.put(Character.valueOf(character), name);  continue;
/*     */       } 
/* 339 */       outMap.put(Character.valueOf(character), name);
/*     */     } 
/*     */     
/* 342 */     return outMap;
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/styledxmlparser/jsoup/nodes/Entities.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */