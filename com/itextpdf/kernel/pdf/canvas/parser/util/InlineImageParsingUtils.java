/*     */ package com.itextpdf.kernel.pdf.canvas.parser.util;
/*     */ 
/*     */ import com.itextpdf.io.source.PdfTokenizer;
/*     */ import com.itextpdf.kernel.PdfException;
/*     */ import com.itextpdf.kernel.pdf.PdfArray;
/*     */ import com.itextpdf.kernel.pdf.PdfDictionary;
/*     */ import com.itextpdf.kernel.pdf.PdfName;
/*     */ import com.itextpdf.kernel.pdf.PdfNumber;
/*     */ import com.itextpdf.kernel.pdf.PdfObject;
/*     */ import com.itextpdf.kernel.pdf.PdfReader;
/*     */ import com.itextpdf.kernel.pdf.PdfStream;
/*     */ import com.itextpdf.kernel.pdf.filters.DoNothingFilter;
/*     */ import com.itextpdf.kernel.pdf.filters.FilterHandlers;
/*     */ import com.itextpdf.kernel.pdf.filters.FlateDecodeStrictFilter;
/*     */ import com.itextpdf.kernel.pdf.filters.IFilterHandler;
/*     */ import java.io.ByteArrayOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.Serializable;
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
/*     */ 
/*     */ 
/*     */ public final class InlineImageParsingUtils
/*     */ {
/*  71 */   private static final byte[] EI = new byte[] { 69, 73 };
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static class InlineImageParseException
/*     */     extends PdfException
/*     */     implements Serializable
/*     */   {
/*     */     private static final long serialVersionUID = 233760879000268548L;
/*     */ 
/*     */ 
/*     */     
/*     */     public InlineImageParseException(String message) {
/*  85 */       super(message);
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
/*     */ 
/*     */   
/* 106 */   private static final Map<PdfName, PdfName> inlineImageEntryAbbreviationMap = new HashMap<>();
/*     */   
/*     */   static {
/* 109 */     inlineImageEntryAbbreviationMap.put(PdfName.BitsPerComponent, PdfName.BitsPerComponent);
/* 110 */     inlineImageEntryAbbreviationMap.put(PdfName.ColorSpace, PdfName.ColorSpace);
/* 111 */     inlineImageEntryAbbreviationMap.put(PdfName.Decode, PdfName.Decode);
/* 112 */     inlineImageEntryAbbreviationMap.put(PdfName.DecodeParms, PdfName.DecodeParms);
/* 113 */     inlineImageEntryAbbreviationMap.put(PdfName.Filter, PdfName.Filter);
/* 114 */     inlineImageEntryAbbreviationMap.put(PdfName.Height, PdfName.Height);
/* 115 */     inlineImageEntryAbbreviationMap.put(PdfName.ImageMask, PdfName.ImageMask);
/* 116 */     inlineImageEntryAbbreviationMap.put(PdfName.Intent, PdfName.Intent);
/* 117 */     inlineImageEntryAbbreviationMap.put(PdfName.Interpolate, PdfName.Interpolate);
/* 118 */     inlineImageEntryAbbreviationMap.put(PdfName.Width, PdfName.Width);
/*     */ 
/*     */     
/* 121 */     inlineImageEntryAbbreviationMap.put(new PdfName("BPC"), PdfName.BitsPerComponent);
/* 122 */     inlineImageEntryAbbreviationMap.put(new PdfName("CS"), PdfName.ColorSpace);
/* 123 */     inlineImageEntryAbbreviationMap.put(new PdfName("D"), PdfName.Decode);
/* 124 */     inlineImageEntryAbbreviationMap.put(new PdfName("DP"), PdfName.DecodeParms);
/* 125 */     inlineImageEntryAbbreviationMap.put(new PdfName("F"), PdfName.Filter);
/* 126 */     inlineImageEntryAbbreviationMap.put(new PdfName("H"), PdfName.Height);
/* 127 */     inlineImageEntryAbbreviationMap.put(new PdfName("IM"), PdfName.ImageMask);
/* 128 */     inlineImageEntryAbbreviationMap.put(new PdfName("I"), PdfName.Interpolate);
/* 129 */     inlineImageEntryAbbreviationMap.put(new PdfName("W"), PdfName.Width);
/*     */   }
/*     */   
/* 132 */   private static final Map<PdfName, PdfName> inlineImageColorSpaceAbbreviationMap = new HashMap<>();
/*     */   static {
/* 134 */     inlineImageColorSpaceAbbreviationMap.put(new PdfName("G"), PdfName.DeviceGray);
/* 135 */     inlineImageColorSpaceAbbreviationMap.put(new PdfName("RGB"), PdfName.DeviceRGB);
/* 136 */     inlineImageColorSpaceAbbreviationMap.put(new PdfName("CMYK"), PdfName.DeviceCMYK);
/* 137 */     inlineImageColorSpaceAbbreviationMap.put(new PdfName("I"), PdfName.Indexed);
/*     */   }
/*     */   
/* 140 */   private static final Map<PdfName, PdfName> inlineImageFilterAbbreviationMap = new HashMap<>();
/*     */   static {
/* 142 */     inlineImageFilterAbbreviationMap.put(new PdfName("AHx"), PdfName.ASCIIHexDecode);
/* 143 */     inlineImageFilterAbbreviationMap.put(new PdfName("A85"), PdfName.ASCII85Decode);
/* 144 */     inlineImageFilterAbbreviationMap.put(new PdfName("LZW"), PdfName.LZWDecode);
/* 145 */     inlineImageFilterAbbreviationMap.put(new PdfName("Fl"), PdfName.FlateDecode);
/* 146 */     inlineImageFilterAbbreviationMap.put(new PdfName("RL"), PdfName.RunLengthDecode);
/* 147 */     inlineImageFilterAbbreviationMap.put(new PdfName("CCF"), PdfName.CCITTFaxDecode);
/* 148 */     inlineImageFilterAbbreviationMap.put(new PdfName("DCT"), PdfName.DCTDecode);
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
/*     */   public static PdfStream parse(PdfCanvasParser ps, PdfDictionary colorSpaceDic) throws IOException {
/* 162 */     PdfDictionary inlineImageDict = parseDictionary(ps);
/* 163 */     byte[] samples = parseSamples(inlineImageDict, colorSpaceDic, ps);
/* 164 */     PdfStream inlineImageAsStreamObject = new PdfStream(samples);
/* 165 */     inlineImageAsStreamObject.putAll(inlineImageDict);
/* 166 */     return inlineImageAsStreamObject;
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
/*     */   private static PdfDictionary parseDictionary(PdfCanvasParser ps) throws IOException {
/* 179 */     PdfDictionary dict = new PdfDictionary();
/*     */     
/* 181 */     for (PdfObject key = ps.readObject(); key != null && !"ID".equals(key.toString()); key = ps.readObject()) {
/* 182 */       PdfObject value = ps.readObject();
/* 183 */       PdfName resolvedKey = inlineImageEntryAbbreviationMap.get(key);
/* 184 */       if (resolvedKey == null) {
/* 185 */         resolvedKey = (PdfName)key;
/*     */       }
/* 187 */       dict.put(resolvedKey, getAlternateValue(resolvedKey, value));
/*     */     } 
/*     */     
/* 190 */     int ch = ps.getTokeniser().read();
/* 191 */     if (!PdfTokenizer.isWhitespace(ch)) {
/* 192 */       throw (new InlineImageParseException("Unexpected character {0} found after ID in inline image.")).setMessageParams(new Object[] { Integer.valueOf(ch) });
/*     */     }
/* 194 */     return dict;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static PdfObject getAlternateValue(PdfName key, PdfObject value) {
/* 205 */     if (key == PdfName.Filter) {
/* 206 */       if (value instanceof PdfName) {
/* 207 */         PdfName altValue = inlineImageFilterAbbreviationMap.get(value);
/* 208 */         if (altValue != null) {
/* 209 */           return (PdfObject)altValue;
/*     */         }
/* 211 */       } else if (value instanceof PdfArray) {
/* 212 */         PdfArray array = (PdfArray)value;
/* 213 */         PdfArray altArray = new PdfArray();
/* 214 */         int count = array.size();
/* 215 */         for (int i = 0; i < count; i++) {
/* 216 */           altArray.add(getAlternateValue(key, array.get(i)));
/*     */         }
/* 218 */         return (PdfObject)altArray;
/*     */       } 
/* 220 */     } else if (key == PdfName.ColorSpace && value instanceof PdfName) {
/* 221 */       PdfName altValue = inlineImageColorSpaceAbbreviationMap.get(value);
/* 222 */       if (altValue != null) {
/* 223 */         return (PdfObject)altValue;
/*     */       }
/*     */     } 
/* 226 */     return value;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static int getComponentsPerPixel(PdfName colorSpaceName, PdfDictionary colorSpaceDic) {
/* 234 */     if (colorSpaceName == null)
/* 235 */       return 1; 
/* 236 */     if (colorSpaceName.equals(PdfName.DeviceGray))
/* 237 */       return 1; 
/* 238 */     if (colorSpaceName.equals(PdfName.DeviceRGB))
/* 239 */       return 3; 
/* 240 */     if (colorSpaceName.equals(PdfName.DeviceCMYK)) {
/* 241 */       return 4;
/*     */     }
/* 243 */     if (colorSpaceDic != null) {
/* 244 */       PdfArray colorSpace = colorSpaceDic.getAsArray(colorSpaceName);
/* 245 */       if (colorSpace != null) {
/* 246 */         if (PdfName.Indexed.equals(colorSpace.getAsName(0))) {
/* 247 */           return 1;
/*     */         }
/*     */       } else {
/* 250 */         PdfName tempName = colorSpaceDic.getAsName(colorSpaceName);
/* 251 */         if (tempName != null) {
/* 252 */           return getComponentsPerPixel(tempName, colorSpaceDic);
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 257 */     throw (new InlineImageParseException("Unexpected ColorSpace: {0}.")).setMessageParams(new Object[] { colorSpaceName });
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
/*     */   private static int computeBytesPerRow(PdfDictionary imageDictionary, PdfDictionary colorSpaceDic) {
/* 269 */     PdfNumber wObj = imageDictionary.getAsNumber(PdfName.Width);
/* 270 */     PdfNumber bpcObj = imageDictionary.getAsNumber(PdfName.BitsPerComponent);
/* 271 */     int cpp = getComponentsPerPixel(imageDictionary.getAsName(PdfName.ColorSpace), colorSpaceDic);
/*     */     
/* 273 */     int w = wObj.intValue();
/* 274 */     int bpc = (bpcObj != null) ? bpcObj.intValue() : 1;
/*     */     
/* 276 */     return (w * bpc * cpp + 7) / 8;
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
/*     */   private static byte[] parseUnfilteredSamples(PdfDictionary imageDictionary, PdfDictionary colorSpaceDic, PdfCanvasParser ps) throws IOException {
/* 293 */     if (imageDictionary.containsKey(PdfName.Filter)) {
/* 294 */       throw new IllegalArgumentException("Dictionary contains filters");
/*     */     }
/* 296 */     PdfNumber h = imageDictionary.getAsNumber(PdfName.Height);
/*     */     
/* 298 */     int bytesToRead = computeBytesPerRow(imageDictionary, colorSpaceDic) * h.intValue();
/* 299 */     byte[] bytes = new byte[bytesToRead];
/* 300 */     PdfTokenizer tokeniser = ps.getTokeniser();
/*     */ 
/*     */     
/* 303 */     int shouldBeWhiteSpace = tokeniser.read();
/*     */ 
/*     */     
/* 306 */     int startIndex = 0;
/* 307 */     if (!PdfTokenizer.isWhitespace(shouldBeWhiteSpace) || shouldBeWhiteSpace == 0) {
/*     */       
/* 309 */       bytes[0] = (byte)shouldBeWhiteSpace;
/* 310 */       startIndex++;
/*     */     } 
/* 312 */     for (int i = startIndex; i < bytesToRead; i++) {
/* 313 */       int ch = tokeniser.read();
/* 314 */       if (ch == -1) {
/* 315 */         throw new InlineImageParseException("End of content stream reached before end of image data.");
/*     */       }
/* 317 */       bytes[i] = (byte)ch;
/*     */     } 
/* 319 */     PdfObject ei = ps.readObject();
/* 320 */     if (!"EI".equals(ei.toString())) {
/*     */ 
/*     */       
/* 323 */       PdfObject ei2 = ps.readObject();
/* 324 */       if (!"EI".equals(ei2.toString()))
/* 325 */         throw new InlineImageParseException("Operator EI not found after the end of image data."); 
/*     */     } 
/* 327 */     return bytes;
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
/*     */   private static byte[] parseSamples(PdfDictionary imageDictionary, PdfDictionary colorSpaceDic, PdfCanvasParser ps) throws IOException {
/* 344 */     if (!imageDictionary.containsKey(PdfName.Filter) && imageColorSpaceIsKnown(imageDictionary, colorSpaceDic)) {
/* 345 */       return parseUnfilteredSamples(imageDictionary, colorSpaceDic, ps);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 351 */     ByteArrayOutputStream baos = new ByteArrayOutputStream();
/*     */     
/* 353 */     int found = 0;
/* 354 */     PdfTokenizer tokeniser = ps.getTokeniser(); int ch;
/* 355 */     while ((ch = tokeniser.read()) != -1) {
/* 356 */       if (ch == 69) {
/*     */         
/* 358 */         baos.write(EI, 0, found);
/*     */         
/* 360 */         found = 1; continue;
/* 361 */       }  if (found == 1 && ch == 73) {
/*     */         
/* 363 */         found = 2; continue;
/*     */       } 
/* 365 */       if (found == 2 && PdfTokenizer.isWhitespace(ch)) {
/* 366 */         byte[] tmp = baos.toByteArray();
/* 367 */         if (inlineImageStreamBytesAreComplete(tmp, imageDictionary)) {
/* 368 */           return tmp;
/*     */         }
/*     */       } 
/*     */       
/* 372 */       baos.write(EI, 0, found);
/* 373 */       baos.write(ch);
/* 374 */       found = 0;
/*     */     } 
/*     */ 
/*     */     
/* 378 */     throw new InlineImageParseException("Cannot find image data or EI.");
/*     */   }
/*     */   
/*     */   private static boolean imageColorSpaceIsKnown(PdfDictionary imageDictionary, PdfDictionary colorSpaceDic) {
/* 382 */     PdfName cs = imageDictionary.getAsName(PdfName.ColorSpace);
/* 383 */     if (cs == null || cs.equals(PdfName.DeviceGray) || cs.equals(PdfName.DeviceRGB) || cs.equals(PdfName.DeviceCMYK)) {
/* 384 */       return true;
/*     */     }
/* 386 */     return (colorSpaceDic != null && colorSpaceDic.containsKey(cs));
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
/*     */   private static boolean inlineImageStreamBytesAreComplete(byte[] samples, PdfDictionary imageDictionary) {
/*     */     try {
/* 401 */       Map<PdfName, IFilterHandler> filters = new HashMap<>(FilterHandlers.getDefaultFilterHandlers());
/* 402 */       filters.put(PdfName.JBIG2Decode, new DoNothingFilter());
/* 403 */       filters.put(PdfName.FlateDecode, new FlateDecodeStrictFilter());
/* 404 */       PdfReader.decodeBytes(samples, imageDictionary, filters);
/* 405 */     } catch (Exception ex) {
/* 406 */       return false;
/*     */     } 
/* 408 */     return true;
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/kernel/pdf/canvas/parser/util/InlineImageParsingUtils.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */