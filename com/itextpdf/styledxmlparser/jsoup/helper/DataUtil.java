/*     */ package com.itextpdf.styledxmlparser.jsoup.helper;
/*     */ 
/*     */ import com.itextpdf.styledxmlparser.jsoup.PortUtil;
/*     */ import com.itextpdf.styledxmlparser.jsoup.nodes.Document;
/*     */ import com.itextpdf.styledxmlparser.jsoup.nodes.Element;
/*     */ import com.itextpdf.styledxmlparser.jsoup.nodes.XmlDeclaration;
/*     */ import com.itextpdf.styledxmlparser.jsoup.parser.Parser;
/*     */ import java.io.ByteArrayOutputStream;
/*     */ import java.io.File;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ import java.io.RandomAccessFile;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.nio.charset.Charset;
/*     */ import java.util.Random;
/*     */ import java.util.regex.Matcher;
/*     */ import java.util.regex.Pattern;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class DataUtil
/*     */ {
/*  69 */   private static final Pattern charsetPattern = Pattern.compile("(?i)\\bcharset=\\s*(?:\"|')?([^\\s,;\"']*)");
/*     */   static final String defaultCharset = "UTF-8";
/*     */   private static final int bufferSize = 131072;
/*     */   private static final int UNICODE_BOM = 65279;
/*  73 */   private static final char[] mimeBoundaryChars = "-_1234567890abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ"
/*  74 */     .toCharArray();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static final int boundaryLength = 32;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Document load(File in, String charsetName, String baseUri) throws IOException {
/*  88 */     ByteBuffer byteData = readFileToByteBuffer(in);
/*  89 */     return parseByteData(byteData, charsetName, baseUri, Parser.htmlParser());
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
/*     */   public static Document load(InputStream in, String charsetName, String baseUri) throws IOException {
/* 101 */     ByteBuffer byteData = readToByteBuffer(in);
/* 102 */     return parseByteData(byteData, charsetName, baseUri, Parser.htmlParser());
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
/*     */   public static Document load(InputStream in, String charsetName, String baseUri, Parser parser) throws IOException {
/* 115 */     ByteBuffer byteData = readToByteBuffer(in);
/* 116 */     return parseByteData(byteData, charsetName, baseUri, parser);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static void crossStreams(InputStream in, OutputStream out) throws IOException {
/* 126 */     byte[] buffer = new byte[131072];
/*     */     int len;
/* 128 */     while ((len = in.read(buffer)) != -1) {
/* 129 */       out.write(buffer, 0, len);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static Document parseByteData(ByteBuffer byteData, String charsetName, String baseUri, Parser parser) {
/*     */     String docData;
/* 138 */     Document doc = null;
/*     */ 
/*     */     
/* 141 */     charsetName = detectCharsetFromBom(byteData, charsetName);
/*     */     
/* 143 */     if (charsetName == null) {
/*     */       
/* 145 */       docData = Charset.forName("UTF-8").decode(byteData).toString();
/* 146 */       doc = parser.parseInput(docData, baseUri);
/* 147 */       Element meta = doc.select("meta[http-equiv=content-type], meta[charset]").first();
/* 148 */       String foundCharset = null;
/* 149 */       if (meta != null) {
/* 150 */         if (meta.hasAttr("http-equiv")) {
/* 151 */           foundCharset = getCharsetFromContentType(meta.attr("content"));
/*     */         }
/* 153 */         if (foundCharset == null && meta.hasAttr("charset")) {
/* 154 */           foundCharset = meta.attr("charset");
/*     */         }
/*     */       } 
/*     */       
/* 158 */       if (foundCharset == null && doc.childNode(0) instanceof XmlDeclaration) {
/* 159 */         XmlDeclaration prolog = (XmlDeclaration)doc.childNode(0);
/* 160 */         if (prolog.name().equals("xml")) {
/* 161 */           foundCharset = prolog.attr("encoding");
/*     */         }
/*     */       } 
/* 164 */       foundCharset = validateCharset(foundCharset);
/*     */       
/* 166 */       if (foundCharset != null && !foundCharset.equals("UTF-8")) {
/* 167 */         foundCharset = foundCharset.trim().replaceAll("[\"']", "");
/* 168 */         charsetName = foundCharset;
/* 169 */         byteData.rewind();
/* 170 */         docData = Charset.forName(foundCharset).decode(byteData).toString();
/* 171 */         doc = null;
/*     */       } 
/*     */     } else {
/* 174 */       Validate.notEmpty(charsetName, "Must set charset arg to character set of file to parse. Set to null to attempt to detect from HTML");
/* 175 */       docData = Charset.forName(charsetName).decode(byteData).toString();
/*     */     } 
/* 177 */     if (doc == null) {
/* 178 */       doc = parser.parseInput(docData, baseUri);
/* 179 */       doc.outputSettings().charset(charsetName);
/*     */     } 
/* 181 */     return doc;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static ByteBuffer readToByteBuffer(InputStream inStream, int maxSize) throws IOException {
/* 192 */     Validate.isTrue((maxSize >= 0), "maxSize must be 0 (unlimited) or larger");
/* 193 */     boolean capped = (maxSize > 0);
/* 194 */     byte[] buffer = new byte[131072];
/* 195 */     ByteArrayOutputStream outStream = new ByteArrayOutputStream(131072);
/*     */     
/* 197 */     int remaining = maxSize;
/*     */     
/*     */     while (true) {
/* 200 */       int read = inStream.read(buffer);
/* 201 */       if (read == -1)
/* 202 */         break;  if (capped) {
/* 203 */         if (read > remaining) {
/* 204 */           outStream.write(buffer, 0, remaining);
/*     */           break;
/*     */         } 
/* 207 */         remaining -= read;
/*     */       } 
/* 209 */       outStream.write(buffer, 0, read);
/*     */     } 
/* 211 */     return ByteBuffer.wrap(outStream.toByteArray());
/*     */   }
/*     */   
/*     */   static ByteBuffer readToByteBuffer(InputStream inStream) throws IOException {
/* 215 */     return readToByteBuffer(inStream, 0);
/*     */   }
/*     */   
/*     */   static ByteBuffer readFileToByteBuffer(File file) throws IOException {
/* 219 */     RandomAccessFile randomAccessFile = null;
/*     */     try {
/* 221 */       randomAccessFile = PortUtil.getReadOnlyRandomAccesFile(file);
/* 222 */       byte[] bytes = new byte[(int)randomAccessFile.length()];
/* 223 */       randomAccessFile.readFully(bytes);
/* 224 */       return ByteBuffer.wrap(bytes);
/*     */     } finally {
/* 226 */       if (randomAccessFile != null)
/* 227 */         randomAccessFile.close(); 
/*     */     } 
/*     */   }
/*     */   
/*     */   static ByteBuffer emptyByteBuffer() {
/* 232 */     return ByteBuffer.allocate(0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static String getCharsetFromContentType(String contentType) {
/* 242 */     if (contentType == null) return null; 
/* 243 */     Matcher m = charsetPattern.matcher(contentType);
/* 244 */     if (PortUtil.isSuccessful(m)) {
/* 245 */       String charset = m.group(1).trim();
/* 246 */       charset = charset.replace("charset=", "");
/* 247 */       return validateCharset(charset);
/*     */     } 
/* 249 */     return null;
/*     */   }
/*     */   
/*     */   private static String validateCharset(String cs) {
/* 253 */     if (cs == null || cs.length() == 0) return null; 
/* 254 */     cs = cs.trim().replaceAll("[\"']", "");
/* 255 */     if (PortUtil.charsetIsSupported(cs)) return cs; 
/* 256 */     StringBuilder upperCase = new StringBuilder();
/* 257 */     for (int i = 0; i < cs.length(); i++) {
/* 258 */       upperCase.append(Character.toUpperCase(cs.charAt(i)));
/*     */     }
/* 260 */     cs = upperCase.toString();
/* 261 */     if (PortUtil.charsetIsSupported(cs)) return cs;
/*     */     
/* 263 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static String mimeBoundary() {
/* 270 */     StringBuilder mime = new StringBuilder(32);
/* 271 */     Random rand = new Random();
/* 272 */     for (int i = 0; i < 32; i++) {
/* 273 */       mime.append(mimeBoundaryChars[rand.nextInt(mimeBoundaryChars.length)]);
/*     */     }
/* 275 */     return mime.toString();
/*     */   }
/*     */   
/*     */   private static String detectCharsetFromBom(ByteBuffer byteData, String charsetName) {
/* 279 */     byteData.mark();
/* 280 */     byte[] bom = new byte[4];
/* 281 */     if (byteData.remaining() >= bom.length) {
/* 282 */       byteData.get(bom);
/* 283 */       byteData.rewind();
/*     */     } 
/* 285 */     if ((bom[0] == 0 && bom[1] == 0 && bom[2] == -2 && bom[3] == -1) || (bom[0] == -1 && bom[1] == -2 && bom[2] == 0 && bom[3] == 0)) {
/*     */       
/* 287 */       charsetName = "UTF-32";
/* 288 */     } else if ((bom[0] == -2 && bom[1] == -1) || (bom[0] == -1 && bom[1] == -2)) {
/*     */       
/* 290 */       charsetName = "UTF-16";
/* 291 */     } else if (bom[0] == -17 && bom[1] == -69 && bom[2] == -65) {
/* 292 */       charsetName = "UTF-8";
/* 293 */       byteData.position(3);
/*     */     } 
/* 295 */     return charsetName;
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/styledxmlparser/jsoup/helper/DataUtil.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */