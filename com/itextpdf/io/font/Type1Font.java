/*     */ package com.itextpdf.io.font;
/*     */ 
/*     */ import com.itextpdf.io.IOException;
/*     */ import com.itextpdf.io.font.constants.FontWeights;
/*     */ import com.itextpdf.io.font.constants.StandardFonts;
/*     */ import com.itextpdf.io.font.otf.Glyph;
/*     */ import com.itextpdf.io.source.RandomAccessFileOrArray;
/*     */ import java.io.IOException;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import java.util.Objects;
/*     */ import java.util.StringTokenizer;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Type1Font
/*     */   extends FontProgram
/*     */ {
/*     */   private static final long serialVersionUID = -1078208220942939920L;
/*     */   private Type1Parser fontParser;
/*     */   private String characterSet;
/*  72 */   private Map<Long, Integer> kernPairs = new HashMap<>();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  77 */   private static final int[] PFB_TYPES = new int[] { 1, 2, 1 };
/*     */   
/*     */   private byte[] fontStreamBytes;
/*     */   private int[] fontStreamLengths;
/*     */   
/*     */   protected static Type1Font createStandardFont(String name) throws IOException {
/*  83 */     if (StandardFonts.isStandardFont(name)) {
/*  84 */       return new Type1Font(name, null, null, null);
/*     */     }
/*  86 */     throw (new IOException("{0} is not a standard type1 font.")).setMessageParams(new Object[] { name });
/*     */   }
/*     */ 
/*     */   
/*     */   protected Type1Font() {
/*  91 */     this.fontNames = new FontNames();
/*     */   }
/*     */   
/*     */   protected Type1Font(String metricsPath, String binaryPath, byte[] afm, byte[] pfb) throws IOException {
/*  95 */     this();
/*     */     
/*  97 */     this.fontParser = new Type1Parser(metricsPath, binaryPath, afm, pfb);
/*  98 */     process();
/*     */   }
/*     */   
/*     */   protected Type1Font(String baseFont) {
/* 102 */     this();
/* 103 */     getFontNames().setFontName(baseFont);
/*     */   }
/*     */   
/*     */   public boolean isBuiltInFont() {
/* 107 */     return (this.fontParser != null && this.fontParser.isBuiltInFont());
/*     */   }
/*     */ 
/*     */   
/*     */   public int getPdfFontFlags() {
/* 112 */     int flags = 0;
/* 113 */     if (this.fontMetrics.isFixedPitch()) {
/* 114 */       flags |= 0x1;
/*     */     }
/* 116 */     flags |= isFontSpecific() ? 4 : 32;
/* 117 */     if (this.fontMetrics.getItalicAngle() < 0.0F) {
/* 118 */       flags |= 0x40;
/*     */     }
/* 120 */     if (this.fontNames.getFontName().contains("Caps") || this.fontNames.getFontName().endsWith("SC")) {
/* 121 */       flags |= 0x20000;
/*     */     }
/* 123 */     if (this.fontNames.isBold() || this.fontNames.getFontWeight() > 500) {
/* 124 */       flags |= 0x40000;
/*     */     }
/* 126 */     return flags;
/*     */   }
/*     */   
/*     */   public String getCharacterSet() {
/* 130 */     return this.characterSet;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean hasKernPairs() {
/* 140 */     return (this.kernPairs.size() > 0);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getKerning(Glyph first, Glyph second) {
/* 145 */     if (first.hasValidUnicode() && second.hasValidUnicode()) {
/* 146 */       long record = (first.getUnicode() << 32L) + second.getUnicode();
/* 147 */       if (this.kernPairs.containsKey(Long.valueOf(record))) {
/* 148 */         return ((Integer)this.kernPairs.get(Long.valueOf(record))).intValue();
/*     */       }
/* 150 */       return 0;
/*     */     } 
/*     */     
/* 153 */     return 0;
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
/*     */   public boolean setKerning(int first, int second, int kern) {
/* 165 */     long record = (first << 32L) + second;
/* 166 */     this.kernPairs.put(Long.valueOf(record), Integer.valueOf(kern));
/* 167 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Glyph getGlyph(String name) {
/* 176 */     int unicode = AdobeGlyphList.nameToUnicode(name);
/* 177 */     if (unicode != -1) {
/* 178 */       return getGlyph(unicode);
/*     */     }
/* 180 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public byte[] getFontStreamBytes() {
/* 185 */     if (this.fontParser.isBuiltInFont())
/* 186 */       return null; 
/* 187 */     if (this.fontStreamBytes != null)
/* 188 */       return this.fontStreamBytes; 
/* 189 */     RandomAccessFileOrArray raf = null;
/*     */     try {
/* 191 */       raf = this.fontParser.getPostscriptBinary();
/* 192 */       int fileLength = (int)raf.length();
/* 193 */       this.fontStreamBytes = new byte[fileLength - 18];
/* 194 */       this.fontStreamLengths = new int[3];
/* 195 */       int bytePtr = 0;
/* 196 */       for (int k = 0; k < 3; k++) {
/* 197 */         if (raf.read() != 128) {
/* 198 */           Logger logger = LoggerFactory.getLogger(Type1Font.class);
/* 199 */           logger.error("Start marker is missing in the pfb file");
/* 200 */           return null;
/*     */         } 
/* 202 */         if (raf.read() != PFB_TYPES[k]) {
/* 203 */           Logger logger = LoggerFactory.getLogger(Type1Font.class);
/* 204 */           logger.error("incorrect.segment.type.in.pfb.file");
/* 205 */           return null;
/*     */         } 
/* 207 */         int size = raf.read();
/* 208 */         size += raf.read() << 8;
/* 209 */         size += raf.read() << 16;
/* 210 */         size += raf.read() << 24;
/* 211 */         this.fontStreamLengths[k] = size;
/* 212 */         while (size != 0) {
/* 213 */           int got = raf.read(this.fontStreamBytes, bytePtr, size);
/* 214 */           if (got < 0) {
/* 215 */             Logger logger = LoggerFactory.getLogger(Type1Font.class);
/* 216 */             logger.error("premature.end.in.pfb.file");
/* 217 */             return null;
/*     */           } 
/* 219 */           bytePtr += got;
/* 220 */           size -= got;
/*     */         } 
/*     */       } 
/* 223 */       return this.fontStreamBytes;
/* 224 */     } catch (Exception e) {
/* 225 */       Logger logger = LoggerFactory.getLogger(Type1Font.class);
/* 226 */       logger.error("type1.font.file.exception");
/* 227 */       return null;
/*     */     } finally {
/* 229 */       if (raf != null) {
/*     */         try {
/* 231 */           raf.close();
/* 232 */         } catch (Exception exception) {}
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public int[] getFontStreamLengths() {
/* 239 */     return this.fontStreamLengths;
/*     */   }
/*     */   
/*     */   public boolean isBuiltWith(String fontProgram) {
/* 243 */     return Objects.equals(this.fontParser.getAfmPath(), fontProgram);
/*     */   }
/*     */   
/*     */   protected void process() throws IOException {
/* 247 */     RandomAccessFileOrArray raf = this.fontParser.getMetricsFile();
/*     */     
/* 249 */     boolean startKernPairs = false; String line;
/* 250 */     while (!startKernPairs && (line = raf.readLine()) != null) {
/* 251 */       String fullName, familyName; int llx, lly, urx, ury; StringTokenizer tok = new StringTokenizer(line, " ,\n\r\t\f");
/* 252 */       if (!tok.hasMoreTokens())
/*     */         continue; 
/* 254 */       String ident = tok.nextToken();
/* 255 */       switch (ident) {
/*     */         case "FontName":
/* 257 */           this.fontNames.setFontName(tok.nextToken("ÿ").substring(1));
/*     */         
/*     */         case "FullName":
/* 260 */           fullName = tok.nextToken("ÿ").substring(1);
/* 261 */           this.fontNames.setFullName(new String[][] { { "", "", "", fullName } });
/*     */         
/*     */         case "FamilyName":
/* 264 */           familyName = tok.nextToken("ÿ").substring(1);
/* 265 */           this.fontNames.setFamilyName(new String[][] { { "", "", "", familyName } });
/*     */         
/*     */         case "Weight":
/* 268 */           this.fontNames.setFontWeight(FontWeights.fromType1FontWeight(tok.nextToken("ÿ").substring(1)));
/*     */         
/*     */         case "ItalicAngle":
/* 271 */           this.fontMetrics.setItalicAngle(Float.parseFloat(tok.nextToken()));
/*     */         
/*     */         case "IsFixedPitch":
/* 274 */           this.fontMetrics.setIsFixedPitch(tok.nextToken().equals("true"));
/*     */         
/*     */         case "CharacterSet":
/* 277 */           this.characterSet = tok.nextToken("ÿ").substring(1);
/*     */         
/*     */         case "FontBBox":
/* 280 */           llx = (int)Float.parseFloat(tok.nextToken());
/* 281 */           lly = (int)Float.parseFloat(tok.nextToken());
/* 282 */           urx = (int)Float.parseFloat(tok.nextToken());
/* 283 */           ury = (int)Float.parseFloat(tok.nextToken());
/* 284 */           this.fontMetrics.setBbox(llx, lly, urx, ury);
/*     */         
/*     */         case "UnderlinePosition":
/* 287 */           this.fontMetrics.setUnderlinePosition((int)Float.parseFloat(tok.nextToken()));
/*     */         
/*     */         case "UnderlineThickness":
/* 290 */           this.fontMetrics.setUnderlineThickness((int)Float.parseFloat(tok.nextToken()));
/*     */         
/*     */         case "EncodingScheme":
/* 293 */           this.encodingScheme = tok.nextToken("ÿ").substring(1).trim();
/*     */         
/*     */         case "CapHeight":
/* 296 */           this.fontMetrics.setCapHeight((int)Float.parseFloat(tok.nextToken()));
/*     */         
/*     */         case "XHeight":
/* 299 */           this.fontMetrics.setXHeight((int)Float.parseFloat(tok.nextToken()));
/*     */         
/*     */         case "Ascender":
/* 302 */           this.fontMetrics.setTypoAscender((int)Float.parseFloat(tok.nextToken()));
/*     */         
/*     */         case "Descender":
/* 305 */           this.fontMetrics.setTypoDescender((int)Float.parseFloat(tok.nextToken()));
/*     */         
/*     */         case "StdHW":
/* 308 */           this.fontMetrics.setStemH((int)Float.parseFloat(tok.nextToken()));
/*     */         
/*     */         case "StdVW":
/* 311 */           this.fontMetrics.setStemV((int)Float.parseFloat(tok.nextToken()));
/*     */         
/*     */         case "StartCharMetrics":
/* 314 */           startKernPairs = true;
/*     */       } 
/*     */     
/*     */     } 
/* 318 */     if (!startKernPairs) {
/* 319 */       String metricsPath = this.fontParser.getAfmPath();
/* 320 */       if (metricsPath != null) {
/* 321 */         throw (new IOException("startcharmetrics is missing in {0}.")).setMessageParams(new Object[] { metricsPath });
/*     */       }
/* 323 */       throw new IOException("startcharmetrics is missing in the metrics file.");
/*     */     } 
/*     */     
/* 326 */     this.avgWidth = 0;
/* 327 */     int widthCount = 0;
/* 328 */     while ((line = raf.readLine()) != null) {
/* 329 */       StringTokenizer tok = new StringTokenizer(line);
/* 330 */       if (!tok.hasMoreTokens()) {
/*     */         continue;
/*     */       }
/* 333 */       String ident = tok.nextToken();
/* 334 */       if (ident.equals("EndCharMetrics")) {
/* 335 */         startKernPairs = false;
/*     */         break;
/*     */       } 
/* 338 */       int C = -1;
/* 339 */       int WX = 250;
/* 340 */       String N = "";
/* 341 */       int[] B = null;
/* 342 */       tok = new StringTokenizer(line, ";");
/* 343 */       while (tok.hasMoreTokens()) {
/* 344 */         StringTokenizer tokc = new StringTokenizer(tok.nextToken());
/* 345 */         if (!tokc.hasMoreTokens()) {
/*     */           continue;
/*     */         }
/* 348 */         ident = tokc.nextToken();
/* 349 */         switch (ident) {
/*     */           case "C":
/* 351 */             C = Integer.parseInt(tokc.nextToken());
/*     */           
/*     */           case "WX":
/* 354 */             WX = (int)Float.parseFloat(tokc.nextToken());
/*     */           
/*     */           case "N":
/* 357 */             N = tokc.nextToken();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/*     */           case "B":
/* 364 */             B = new int[] { Integer.parseInt(tokc.nextToken()), Integer.parseInt(tokc.nextToken()), Integer.parseInt(tokc.nextToken()), Integer.parseInt(tokc.nextToken()) };
/*     */         } 
/*     */ 
/*     */       
/*     */       } 
/* 369 */       int unicode = AdobeGlyphList.nameToUnicode(N);
/* 370 */       Glyph glyph = new Glyph(C, WX, unicode, B);
/* 371 */       if (C >= 0) {
/* 372 */         this.codeToGlyph.put(Integer.valueOf(C), glyph);
/*     */       }
/* 374 */       if (unicode != -1) {
/* 375 */         this.unicodeToGlyph.put(Integer.valueOf(unicode), glyph);
/*     */       }
/* 377 */       this.avgWidth += WX;
/* 378 */       widthCount++;
/*     */     } 
/* 380 */     if (widthCount != 0) {
/* 381 */       this.avgWidth /= widthCount;
/*     */     }
/* 383 */     if (startKernPairs) {
/* 384 */       String metricsPath = this.fontParser.getAfmPath();
/* 385 */       if (metricsPath != null) {
/* 386 */         throw (new IOException("endcharmetrics is missing in {0}.")).setMessageParams(new Object[] { metricsPath });
/*     */       }
/* 388 */       throw new IOException("endcharmetrics is missing in the metrics file.");
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 395 */     if (!this.unicodeToGlyph.containsKey(Integer.valueOf(160))) {
/* 396 */       Glyph space = this.unicodeToGlyph.get(Integer.valueOf(32));
/* 397 */       if (space != null) {
/* 398 */         this.unicodeToGlyph.put(Integer.valueOf(160), new Glyph(space.getCode(), space.getWidth(), 160, space.getBbox()));
/*     */       }
/*     */     } 
/* 401 */     boolean endOfMetrics = false;
/* 402 */     while ((line = raf.readLine()) != null) {
/* 403 */       StringTokenizer tok = new StringTokenizer(line);
/* 404 */       if (!tok.hasMoreTokens()) {
/*     */         continue;
/*     */       }
/* 407 */       String ident = tok.nextToken();
/* 408 */       if (ident.equals("EndFontMetrics")) {
/* 409 */         endOfMetrics = true; break;
/*     */       } 
/* 411 */       if (ident.equals("StartKernPairs")) {
/* 412 */         startKernPairs = true;
/*     */         break;
/*     */       } 
/*     */     } 
/* 416 */     if (startKernPairs) {
/* 417 */       while ((line = raf.readLine()) != null) {
/* 418 */         StringTokenizer tok = new StringTokenizer(line);
/* 419 */         if (!tok.hasMoreTokens()) {
/*     */           continue;
/*     */         }
/* 422 */         String ident = tok.nextToken();
/* 423 */         if (ident.equals("KPX")) {
/* 424 */           String first = tok.nextToken();
/* 425 */           String second = tok.nextToken();
/* 426 */           Integer width = Integer.valueOf((int)Float.parseFloat(tok.nextToken()));
/*     */           
/* 428 */           int firstUni = AdobeGlyphList.nameToUnicode(first);
/* 429 */           int secondUni = AdobeGlyphList.nameToUnicode(second);
/*     */           
/* 431 */           if (firstUni != -1 && secondUni != -1) {
/* 432 */             long record = (firstUni << 32L) + secondUni;
/* 433 */             this.kernPairs.put(Long.valueOf(record), width);
/*     */           }  continue;
/* 435 */         }  if (ident.equals("EndKernPairs")) {
/* 436 */           startKernPairs = false;
/*     */           break;
/*     */         } 
/*     */       } 
/* 440 */     } else if (!endOfMetrics) {
/* 441 */       String metricsPath = this.fontParser.getAfmPath();
/* 442 */       if (metricsPath != null) {
/* 443 */         throw (new IOException("endfontmetrics is missing in {0}.")).setMessageParams(new Object[] { metricsPath });
/*     */       }
/* 445 */       throw new IOException("endfontmetrics is missing in the metrics file.");
/*     */     } 
/*     */ 
/*     */     
/* 449 */     if (startKernPairs) {
/* 450 */       String metricsPath = this.fontParser.getAfmPath();
/* 451 */       if (metricsPath != null) {
/* 452 */         throw (new IOException("endkernpairs is missing in {0}.")).setMessageParams(new Object[] { metricsPath });
/*     */       }
/* 454 */       throw new IOException("endkernpairs is missing in the metrics file.");
/*     */     } 
/*     */     
/* 457 */     raf.close();
/*     */     
/* 459 */     this.isFontSpecific = (!this.encodingScheme.equals("AdobeStandardEncoding") && !this.encodingScheme.equals("StandardEncoding"));
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/io/font/Type1Font.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */