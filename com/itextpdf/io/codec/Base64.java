/*      */ package com.itextpdf.io.codec;
/*      */ 
/*      */ import java.io.BufferedInputStream;
/*      */ import java.io.BufferedOutputStream;
/*      */ import java.io.ByteArrayInputStream;
/*      */ import java.io.ByteArrayOutputStream;
/*      */ import java.io.File;
/*      */ import java.io.FileInputStream;
/*      */ import java.io.FileOutputStream;
/*      */ import java.io.FilterInputStream;
/*      */ import java.io.FilterOutputStream;
/*      */ import java.io.IOException;
/*      */ import java.io.ObjectInputStream;
/*      */ import java.io.ObjectOutputStream;
/*      */ import java.io.Serializable;
/*      */ import java.io.UnsupportedEncodingException;
/*      */ import java.util.zip.GZIPInputStream;
/*      */ import java.util.zip.GZIPOutputStream;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class Base64
/*      */ {
/*      */   public static final int NO_OPTIONS = 0;
/*      */   public static final int ENCODE = 1;
/*      */   public static final int DECODE = 0;
/*      */   public static final int GZIP = 2;
/*      */   public static final int DONT_BREAK_LINES = 8;
/*      */   public static final int URL_SAFE = 16;
/*      */   public static final int ORDERED = 32;
/*      */   private static final int MAX_LINE_LENGTH = 76;
/*      */   private static final byte EQUALS_SIGN = 61;
/*      */   private static final byte NEW_LINE = 10;
/*      */   private static final String PREFERRED_ENCODING = "UTF-8";
/*      */   private static final byte WHITE_SPACE_ENC = -5;
/*      */   private static final byte EQUALS_SIGN_ENC = -1;
/*  158 */   private static final byte[] _STANDARD_ALPHABET = new byte[] { 65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75, 76, 77, 78, 79, 80, 81, 82, 83, 84, 85, 86, 87, 88, 89, 90, 97, 98, 99, 100, 101, 102, 103, 104, 105, 106, 107, 108, 109, 110, 111, 112, 113, 114, 115, 116, 117, 118, 119, 120, 121, 122, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 43, 47 };
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  177 */   private static final byte[] _STANDARD_DECODABET = new byte[] { -9, -9, -9, -9, -9, -9, -9, -9, -9, -5, -5, -9, -9, -5, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -5, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, 62, -9, -9, -9, 63, 52, 53, 54, 55, 56, 57, 58, 59, 60, 61, -9, -9, -9, -1, -9, -9, -9, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, -9, -9, -9, -9, -9, -9, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, -9, -9, -9, -9 };
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  220 */   private static final byte[] _URL_SAFE_ALPHABET = new byte[] { 65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75, 76, 77, 78, 79, 80, 81, 82, 83, 84, 85, 86, 87, 88, 89, 90, 97, 98, 99, 100, 101, 102, 103, 104, 105, 106, 107, 108, 109, 110, 111, 112, 113, 114, 115, 116, 117, 118, 119, 120, 121, 122, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 45, 95 };
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  237 */   private static final byte[] _URL_SAFE_DECODABET = new byte[] { -9, -9, -9, -9, -9, -9, -9, -9, -9, -5, -5, -9, -9, -5, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -5, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, 62, -9, -9, 52, 53, 54, 55, 56, 57, 58, 59, 60, 61, -9, -9, -9, -1, -9, -9, -9, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, -9, -9, -9, -9, 63, -9, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, -9, -9, -9, -9 };
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  284 */   private static final byte[] _ORDERED_ALPHABET = new byte[] { 45, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75, 76, 77, 78, 79, 80, 81, 82, 83, 84, 85, 86, 87, 88, 89, 90, 95, 97, 98, 99, 100, 101, 102, 103, 104, 105, 106, 107, 108, 109, 110, 111, 112, 113, 114, 115, 116, 117, 118, 119, 120, 121, 122 };
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  303 */   private static final byte[] _ORDERED_DECODABET = new byte[] { -9, -9, -9, -9, -9, -9, -9, -9, -9, -5, -5, -9, -9, -5, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -5, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, 0, -9, -9, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, -9, -9, -9, -1, -9, -9, -9, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, -9, -9, -9, -9, 37, -9, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 58, 59, 60, 61, 62, 63, -9, -9, -9, -9 };
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static byte[] getAlphabet(int options) {
/*  354 */     if ((options & 0x10) == 16) return _URL_SAFE_ALPHABET; 
/*  355 */     if ((options & 0x20) == 32) return _ORDERED_ALPHABET; 
/*  356 */     return _STANDARD_ALPHABET;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static byte[] getDecodabet(int options) {
/*  369 */     if ((options & 0x10) == 16) return _URL_SAFE_DECODABET; 
/*  370 */     if ((options & 0x20) == 32) return _ORDERED_DECODABET; 
/*  371 */     return _STANDARD_DECODABET;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static void usage(String msg) {
/*  414 */     System.err.println(msg);
/*  415 */     System.err.println("Usage: java Base64 -e|-d inputfile outputfile");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static byte[] encode3to4(byte[] b4, byte[] threeBytes, int numSigBytes, int options) {
/*  438 */     encode3to4(threeBytes, 0, numSigBytes, b4, 0, options);
/*  439 */     return b4;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static byte[] encode3to4(byte[] source, int srcOffset, int numSigBytes, byte[] destination, int destOffset, int options) {
/*  470 */     byte[] ALPHABET = getAlphabet(options);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  483 */     int inBuff = ((numSigBytes > 0) ? (source[srcOffset] << 24 >>> 8) : 0) | ((numSigBytes > 1) ? (source[srcOffset + 1] << 24 >>> 16) : 0) | ((numSigBytes > 2) ? (source[srcOffset + 2] << 24 >>> 24) : 0);
/*      */ 
/*      */ 
/*      */     
/*  487 */     switch (numSigBytes) {
/*      */       case 3:
/*  489 */         destination[destOffset] = ALPHABET[inBuff >>> 18];
/*  490 */         destination[destOffset + 1] = ALPHABET[inBuff >>> 12 & 0x3F];
/*  491 */         destination[destOffset + 2] = ALPHABET[inBuff >>> 6 & 0x3F];
/*  492 */         destination[destOffset + 3] = ALPHABET[inBuff & 0x3F];
/*  493 */         return destination;
/*      */       
/*      */       case 2:
/*  496 */         destination[destOffset] = ALPHABET[inBuff >>> 18];
/*  497 */         destination[destOffset + 1] = ALPHABET[inBuff >>> 12 & 0x3F];
/*  498 */         destination[destOffset + 2] = ALPHABET[inBuff >>> 6 & 0x3F];
/*  499 */         destination[destOffset + 3] = 61;
/*  500 */         return destination;
/*      */       
/*      */       case 1:
/*  503 */         destination[destOffset] = ALPHABET[inBuff >>> 18];
/*  504 */         destination[destOffset + 1] = ALPHABET[inBuff >>> 12 & 0x3F];
/*  505 */         destination[destOffset + 2] = 61;
/*  506 */         destination[destOffset + 3] = 61;
/*  507 */         return destination;
/*      */     } 
/*      */     
/*  510 */     return destination;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static String encodeObject(Serializable serializableObject) {
/*  527 */     return encodeObject(serializableObject, 0);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static String encodeObject(Serializable serializableObject, int options) {
/*  556 */     ByteArrayOutputStream baos = null;
/*  557 */     java.io.OutputStream b64os = null;
/*  558 */     ObjectOutputStream oos = null;
/*  559 */     GZIPOutputStream gzos = null;
/*      */ 
/*      */     
/*  562 */     int gzip = options & 0x2;
/*  563 */     int dontBreakLines = options & 0x8;
/*      */ 
/*      */     
/*      */     try {
/*  567 */       baos = new ByteArrayOutputStream();
/*  568 */       b64os = new OutputStream(baos, 0x1 | options);
/*      */ 
/*      */       
/*  571 */       if (gzip == 2) {
/*  572 */         gzos = new GZIPOutputStream(b64os);
/*  573 */         oos = new ObjectOutputStream(gzos);
/*      */       } else {
/*      */         
/*  576 */         oos = new ObjectOutputStream(b64os);
/*      */       } 
/*  578 */       oos.writeObject(serializableObject);
/*      */     }
/*  580 */     catch (IOException e) {
/*  581 */       e.printStackTrace();
/*  582 */       return null;
/*      */     } finally {
/*      */       
/*      */       try {
/*  586 */         oos.close();
/*  587 */       } catch (Exception exception) {}
/*      */       
/*      */       try {
/*  590 */         gzos.close();
/*  591 */       } catch (Exception exception) {}
/*      */       
/*      */       try {
/*  594 */         b64os.close();
/*  595 */       } catch (Exception exception) {}
/*      */       
/*      */       try {
/*  598 */         baos.close();
/*  599 */       } catch (Exception exception) {}
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*      */     try {
/*  605 */       return new String(baos.toByteArray(), "UTF-8");
/*      */     }
/*  607 */     catch (UnsupportedEncodingException uue) {
/*  608 */       return new String(baos.toByteArray());
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static String encodeBytes(byte[] source) {
/*  623 */     return encodeBytes(source, 0, source.length, 0);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static String encodeBytes(byte[] source, int options) {
/*  648 */     return encodeBytes(source, 0, source.length, options);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static String encodeBytes(byte[] source, int off, int len) {
/*  663 */     return encodeBytes(source, off, len, 0);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static String encodeBytes(byte[] source, int off, int len, int options) {
/*  692 */     int dontBreakLines = options & 0x8;
/*  693 */     int gzip = options & 0x2;
/*      */ 
/*      */     
/*  696 */     if (gzip == 2) {
/*  697 */       ByteArrayOutputStream baos = null;
/*  698 */       GZIPOutputStream gzos = null;
/*  699 */       OutputStream b64os = null;
/*      */ 
/*      */ 
/*      */       
/*      */       try {
/*  704 */         baos = new ByteArrayOutputStream();
/*  705 */         b64os = new OutputStream(baos, 0x1 | options);
/*  706 */         gzos = new GZIPOutputStream(b64os);
/*      */         
/*  708 */         gzos.write(source, off, len);
/*  709 */         gzos.close();
/*      */       }
/*  711 */       catch (IOException iOException) {
/*  712 */         iOException.printStackTrace();
/*  713 */         return null;
/*      */       } finally {
/*      */         
/*      */         try {
/*  717 */           gzos.close();
/*  718 */         } catch (Exception exception) {}
/*      */         
/*      */         try {
/*  721 */           b64os.close();
/*  722 */         } catch (Exception exception) {}
/*      */         
/*      */         try {
/*  725 */           baos.close();
/*  726 */         } catch (Exception exception) {}
/*      */       } 
/*      */ 
/*      */ 
/*      */       
/*      */       try {
/*  732 */         return new String(baos.toByteArray(), "UTF-8");
/*      */       }
/*  734 */       catch (UnsupportedEncodingException uue) {
/*  735 */         return new String(baos.toByteArray());
/*      */       } 
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  742 */     boolean breakLines = (dontBreakLines == 0);
/*      */     
/*  744 */     int len43 = len * 4 / 3;
/*  745 */     byte[] outBuff = new byte[len43 + ((len % 3 > 0) ? 4 : 0) + (breakLines ? (len43 / 76) : 0)];
/*      */ 
/*      */     
/*  748 */     int d = 0;
/*  749 */     int e = 0;
/*  750 */     int len2 = len - 2;
/*  751 */     int lineLength = 0;
/*  752 */     for (; d < len2; d += 3, e += 4) {
/*  753 */       encode3to4(source, d + off, 3, outBuff, e, options);
/*      */       
/*  755 */       lineLength += 4;
/*  756 */       if (breakLines && lineLength == 76) {
/*  757 */         outBuff[e + 4] = 10;
/*  758 */         e++;
/*  759 */         lineLength = 0;
/*      */       } 
/*      */     } 
/*      */     
/*  763 */     if (d < len) {
/*  764 */       encode3to4(source, d + off, len - d, outBuff, e, options);
/*  765 */       e += 4;
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*      */     try {
/*  771 */       return new String(outBuff, 0, e, "UTF-8");
/*      */     }
/*  773 */     catch (UnsupportedEncodingException uue) {
/*  774 */       return new String(outBuff, 0, e);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static int decode4to3(byte[] source, int srcOffset, byte[] destination, int destOffset, int options) {
/*  814 */     byte[] DECODABET = getDecodabet(options);
/*      */ 
/*      */     
/*  817 */     if (source[srcOffset + 2] == 61) {
/*      */ 
/*      */ 
/*      */       
/*  821 */       int outBuff = (DECODABET[source[srcOffset]] & 0xFF) << 18 | (DECODABET[source[srcOffset + 1]] & 0xFF) << 12;
/*      */ 
/*      */       
/*  824 */       destination[destOffset] = (byte)(outBuff >>> 16);
/*  825 */       return 1;
/*      */     } 
/*      */ 
/*      */     
/*  829 */     if (source[srcOffset + 3] == 61) {
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  834 */       int outBuff = (DECODABET[source[srcOffset]] & 0xFF) << 18 | (DECODABET[source[srcOffset + 1]] & 0xFF) << 12 | (DECODABET[source[srcOffset + 2]] & 0xFF) << 6;
/*      */ 
/*      */ 
/*      */       
/*  838 */       destination[destOffset] = (byte)(outBuff >>> 16);
/*  839 */       destination[destOffset + 1] = (byte)(outBuff >>> 8);
/*  840 */       return 2;
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     try {
/*  851 */       int outBuff = (DECODABET[source[srcOffset]] & 0xFF) << 18 | (DECODABET[source[srcOffset + 1]] & 0xFF) << 12 | (DECODABET[source[srcOffset + 2]] & 0xFF) << 6 | DECODABET[source[srcOffset + 3]] & 0xFF;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  857 */       destination[destOffset] = (byte)(outBuff >> 16);
/*  858 */       destination[destOffset + 1] = (byte)(outBuff >> 8);
/*  859 */       destination[destOffset + 2] = (byte)outBuff;
/*      */       
/*  861 */       return 3;
/*  862 */     } catch (Exception e) {
/*  863 */       System.out.println("" + source[srcOffset] + ": " + DECODABET[source[srcOffset]]);
/*  864 */       System.out.println("" + source[srcOffset + 1] + ": " + DECODABET[source[srcOffset + 1]]);
/*  865 */       System.out.println("" + source[srcOffset + 2] + ": " + DECODABET[source[srcOffset + 2]]);
/*  866 */       System.out.println("" + source[srcOffset + 3] + ": " + DECODABET[source[srcOffset + 3]]);
/*  867 */       return -1;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static byte[] decode(byte[] source, int off, int len, int options) {
/*  886 */     byte[] DECODABET = getDecodabet(options);
/*      */     
/*  888 */     int len34 = len * 3 / 4;
/*  889 */     byte[] outBuff = new byte[len34];
/*  890 */     int outBuffPosn = 0;
/*      */     
/*  892 */     byte[] b4 = new byte[4];
/*  893 */     int b4Posn = 0;
/*  894 */     int i = 0;
/*  895 */     byte sbiCrop = 0;
/*  896 */     byte sbiDecode = 0;
/*  897 */     for (i = off; i < off + len; i++) {
/*  898 */       sbiCrop = (byte)(source[i] & Byte.MAX_VALUE);
/*  899 */       sbiDecode = DECODABET[sbiCrop];
/*      */       
/*  901 */       if (sbiDecode >= -5) {
/*      */         
/*  903 */         if (sbiDecode >= -1) {
/*  904 */           b4[b4Posn++] = sbiCrop;
/*  905 */           if (b4Posn > 3) {
/*  906 */             outBuffPosn += decode4to3(b4, 0, outBuff, outBuffPosn, options);
/*  907 */             b4Posn = 0;
/*      */ 
/*      */             
/*  910 */             if (sbiCrop == 61) {
/*      */               break;
/*      */             }
/*      */           }
/*      */         
/*      */         } 
/*      */       } else {
/*      */         
/*  918 */         System.err.println("Bad Base64 input character at " + i + ": " + source[i] + "(decimal)");
/*  919 */         return null;
/*      */       } 
/*      */     } 
/*      */     
/*  923 */     byte[] out = new byte[outBuffPosn];
/*  924 */     System.arraycopy(outBuff, 0, out, 0, outBuffPosn);
/*  925 */     return out;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static byte[] decode(String s) {
/*  938 */     return decode(s, 0);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static byte[] decode(String s, int options) {
/*      */     try {
/*  954 */       bytes = s.getBytes("UTF-8");
/*      */     }
/*  956 */     catch (UnsupportedEncodingException uee) {
/*  957 */       bytes = s.getBytes();
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  962 */     byte[] bytes = decode(bytes, 0, bytes.length, options);
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  967 */     if (bytes != null && bytes.length >= 4) {
/*      */       
/*  969 */       int head = bytes[0] & 0xFF | bytes[1] << 8 & 0xFF00;
/*  970 */       if (35615 == head) {
/*  971 */         ByteArrayInputStream bais = null;
/*  972 */         GZIPInputStream gzis = null;
/*  973 */         ByteArrayOutputStream baos = null;
/*  974 */         byte[] buffer = new byte[2048];
/*  975 */         int length = 0;
/*      */         
/*      */         try {
/*  978 */           baos = new ByteArrayOutputStream();
/*  979 */           bais = new ByteArrayInputStream(bytes);
/*  980 */           gzis = new GZIPInputStream(bais);
/*      */           
/*  982 */           while ((length = gzis.read(buffer)) >= 0) {
/*  983 */             baos.write(buffer, 0, length);
/*      */           }
/*      */ 
/*      */           
/*  987 */           bytes = baos.toByteArray();
/*      */         
/*      */         }
/*  990 */         catch (IOException iOException) {
/*      */ 
/*      */         
/*      */         } finally {
/*      */           try {
/*  995 */             baos.close();
/*  996 */           } catch (Exception exception) {}
/*      */           
/*      */           try {
/*  999 */             gzis.close();
/* 1000 */           } catch (Exception exception) {}
/*      */           
/*      */           try {
/* 1003 */             bais.close();
/* 1004 */           } catch (Exception exception) {}
/*      */         } 
/*      */       } 
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 1011 */     return bytes;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Object decodeToObject(String encodedObject) {
/* 1025 */     byte[] objBytes = decode(encodedObject);
/*      */     
/* 1027 */     ByteArrayInputStream bais = null;
/* 1028 */     ObjectInputStream ois = null;
/* 1029 */     Object obj = null;
/*      */     
/*      */     try {
/* 1032 */       bais = new ByteArrayInputStream(objBytes);
/* 1033 */       ois = new ObjectInputStream(bais);
/*      */       
/* 1035 */       obj = ois.readObject();
/*      */     }
/* 1037 */     catch (IOException e) {
/* 1038 */       e.printStackTrace();
/*      */     }
/* 1040 */     catch (ClassNotFoundException e) {
/* 1041 */       e.printStackTrace();
/*      */     } finally {
/*      */       
/*      */       try {
/* 1045 */         bais.close();
/* 1046 */       } catch (Exception exception) {}
/*      */       
/*      */       try {
/* 1049 */         ois.close();
/* 1050 */       } catch (Exception exception) {}
/*      */     } 
/*      */ 
/*      */     
/* 1054 */     return obj;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean encodeToFile(byte[] dataToEncode, String filename) {
/* 1067 */     boolean success = false;
/* 1068 */     OutputStream bos = null;
/*      */     try {
/* 1070 */       bos = new OutputStream(new FileOutputStream(filename), 1);
/*      */       
/* 1072 */       bos.write(dataToEncode);
/* 1073 */       success = true;
/*      */     }
/* 1075 */     catch (IOException e) {
/*      */       
/* 1077 */       success = false;
/*      */     } finally {
/*      */       
/*      */       try {
/* 1081 */         bos.close();
/* 1082 */       } catch (Exception exception) {}
/*      */     } 
/*      */ 
/*      */     
/* 1086 */     return success;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean decodeToFile(String dataToDecode, String filename) {
/* 1099 */     boolean success = false;
/* 1100 */     OutputStream bos = null;
/*      */     try {
/* 1102 */       bos = new OutputStream(new FileOutputStream(filename), 0);
/*      */       
/* 1104 */       bos.write(dataToDecode.getBytes("UTF-8"));
/* 1105 */       success = true;
/*      */     }
/* 1107 */     catch (IOException e) {
/* 1108 */       success = false;
/*      */     } finally {
/*      */       
/*      */       try {
/* 1112 */         bos.close();
/* 1113 */       } catch (Exception exception) {}
/*      */     } 
/*      */ 
/*      */     
/* 1117 */     return success;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static byte[] decodeFromFile(String filename) {
/* 1130 */     byte[] decodedData = null;
/* 1131 */     InputStream bis = null;
/*      */     
/*      */     try {
/* 1134 */       File file = new File(filename);
/* 1135 */       byte[] buffer = null;
/* 1136 */       int length = 0;
/* 1137 */       int numBytes = 0;
/*      */ 
/*      */       
/* 1140 */       if (file.length() > 2147483647L) {
/* 1141 */         System.err.println("File is too big for this convenience method (" + file.length() + " bytes).");
/* 1142 */         return null;
/*      */       } 
/* 1144 */       buffer = new byte[(int)file.length()];
/*      */ 
/*      */       
/* 1147 */       bis = new InputStream(new BufferedInputStream(new FileInputStream(file)), 0);
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1152 */       while ((numBytes = bis.read(buffer, length, 4096)) >= 0) {
/* 1153 */         length += numBytes;
/*      */       }
/*      */       
/* 1156 */       decodedData = new byte[length];
/* 1157 */       System.arraycopy(buffer, 0, decodedData, 0, length);
/*      */     
/*      */     }
/* 1160 */     catch (IOException e) {
/* 1161 */       System.err.println("Error decoding from file " + filename);
/*      */     } finally {
/*      */       
/* 1164 */       if (null != bis) {
/*      */         try {
/* 1166 */           bis.close();
/* 1167 */         } catch (Exception exception) {}
/*      */       }
/*      */     } 
/*      */ 
/*      */     
/* 1172 */     return decodedData;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static String encodeFromFile(String filename) {
/* 1185 */     String encodedData = null;
/* 1186 */     InputStream bis = null;
/*      */     
/*      */     try {
/* 1189 */       File file = new File(filename);
/* 1190 */       byte[] buffer = new byte[Math.max((int)(file.length() * 1.4D), 40)];
/* 1191 */       int length = 0;
/* 1192 */       int numBytes = 0;
/*      */ 
/*      */       
/* 1195 */       bis = new InputStream(new BufferedInputStream(new FileInputStream(file)), 1);
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1200 */       while ((numBytes = bis.read(buffer, length, 4096)) >= 0) {
/* 1201 */         length += numBytes;
/*      */       }
/*      */       
/* 1204 */       encodedData = new String(buffer, 0, length, "UTF-8");
/*      */     
/*      */     }
/* 1207 */     catch (IOException e) {
/* 1208 */       System.err.println("Error encoding from file " + filename);
/*      */     } finally {
/*      */       
/*      */       try {
/* 1212 */         bis.close();
/* 1213 */       } catch (Exception exception) {}
/*      */     } 
/*      */ 
/*      */     
/* 1217 */     return encodedData;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void encodeFileToFile(String infile, String outfile) {
/* 1228 */     String encoded = encodeFromFile(infile);
/* 1229 */     java.io.OutputStream out = null;
/*      */     try {
/* 1231 */       out = new BufferedOutputStream(new FileOutputStream(outfile));
/*      */       
/* 1233 */       out.write(encoded.getBytes("US-ASCII"));
/*      */     }
/* 1235 */     catch (IOException ex) {
/* 1236 */       ex.printStackTrace();
/*      */     } finally {
/*      */       
/*      */       try {
/* 1240 */         out.close();
/* 1241 */       } catch (Exception exception) {}
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void decodeFileToFile(String infile, String outfile) {
/* 1255 */     byte[] decoded = decodeFromFile(infile);
/* 1256 */     java.io.OutputStream out = null;
/*      */     try {
/* 1258 */       out = new BufferedOutputStream(new FileOutputStream(outfile));
/*      */       
/* 1260 */       out.write(decoded);
/*      */     }
/* 1262 */     catch (IOException ex) {
/* 1263 */       ex.printStackTrace();
/*      */     } finally {
/*      */       
/*      */       try {
/* 1267 */         out.close();
/* 1268 */       } catch (Exception exception) {}
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static class InputStream
/*      */     extends FilterInputStream
/*      */   {
/*      */     private boolean encode;
/*      */ 
/*      */     
/*      */     private int position;
/*      */ 
/*      */     
/*      */     private byte[] buffer;
/*      */ 
/*      */     
/*      */     private int bufferLength;
/*      */ 
/*      */     
/*      */     private int numSigBytes;
/*      */ 
/*      */     
/*      */     private int lineLength;
/*      */ 
/*      */     
/*      */     private boolean breakLines;
/*      */     
/*      */     private int options;
/*      */     
/*      */     private byte[] alphabet;
/*      */     
/*      */     private byte[] decodabet;
/*      */ 
/*      */     
/*      */     public InputStream(java.io.InputStream in) {
/* 1305 */       this(in, 0);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public InputStream(java.io.InputStream in, int options) {
/* 1330 */       super(in);
/* 1331 */       this.breakLines = ((options & 0x8) != 8);
/* 1332 */       this.encode = ((options & 0x1) == 1);
/* 1333 */       this.bufferLength = this.encode ? 4 : 3;
/* 1334 */       this.buffer = new byte[this.bufferLength];
/* 1335 */       this.position = -1;
/* 1336 */       this.lineLength = 0;
/* 1337 */       this.options = options;
/* 1338 */       this.alphabet = Base64.getAlphabet(options);
/* 1339 */       this.decodabet = Base64.getDecodabet(options);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public int read() throws IOException {
/* 1351 */       if (this.position < 0) {
/* 1352 */         if (this.encode) {
/* 1353 */           byte[] b3 = new byte[3];
/* 1354 */           int numBinaryBytes = 0;
/* 1355 */           for (int i = 0; i < 3; i++) {
/*      */             try {
/* 1357 */               int j = this.in.read();
/*      */ 
/*      */               
/* 1360 */               if (j >= 0) {
/* 1361 */                 b3[i] = (byte)j;
/* 1362 */                 numBinaryBytes++;
/*      */               }
/*      */             
/*      */             }
/* 1366 */             catch (IOException e) {
/*      */               
/* 1368 */               if (i == 0) {
/* 1369 */                 throw e;
/*      */               }
/*      */             } 
/*      */           } 
/*      */           
/* 1374 */           if (numBinaryBytes > 0) {
/* 1375 */             Base64.encode3to4(b3, 0, numBinaryBytes, this.buffer, 0, this.options);
/* 1376 */             this.position = 0;
/* 1377 */             this.numSigBytes = 4;
/*      */           } else {
/*      */             
/* 1380 */             return -1;
/*      */           }
/*      */         
/*      */         }
/*      */         else {
/*      */           
/* 1386 */           byte[] b4 = new byte[4];
/* 1387 */           int i = 0;
/* 1388 */           for (i = 0; i < 4; i++) {
/*      */             
/* 1390 */             int j = 0;
/*      */             do {
/* 1392 */               j = this.in.read();
/*      */             }
/* 1394 */             while (j >= 0 && this.decodabet[j & 0x7F] <= -5);
/*      */             
/* 1396 */             if (j < 0) {
/*      */               break;
/*      */             }
/* 1399 */             b4[i] = (byte)j;
/*      */           } 
/*      */           
/* 1402 */           if (i == 4) {
/* 1403 */             this.numSigBytes = Base64.decode4to3(b4, 0, this.buffer, 0, this.options);
/* 1404 */             this.position = 0;
/*      */           } else {
/* 1406 */             if (i == 0) {
/* 1407 */               return -1;
/*      */             }
/*      */ 
/*      */             
/* 1411 */             throw new IOException("improperly.padded.base64.input");
/*      */           } 
/*      */         } 
/*      */       }
/*      */ 
/*      */       
/* 1417 */       assert this.position >= 0;
/*      */       
/* 1419 */       if (this.position >= this.numSigBytes) {
/* 1420 */         return -1;
/*      */       }
/* 1422 */       if (this.encode && this.breakLines && this.lineLength >= 76) {
/* 1423 */         this.lineLength = 0;
/* 1424 */         return 10;
/*      */       } 
/*      */       
/* 1427 */       this.lineLength++;
/*      */ 
/*      */ 
/*      */       
/* 1431 */       int b = this.buffer[this.position++];
/*      */       
/* 1433 */       if (this.position >= this.bufferLength) {
/* 1434 */         this.position = -1;
/*      */       }
/* 1436 */       return b & 0xFF;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public int read(byte[] dest, int off, int len) throws IOException {
/*      */       int i;
/* 1457 */       for (i = 0; i < len; i++) {
/* 1458 */         int b = read();
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1463 */         if (b >= 0)
/* 1464 */         { dest[off + i] = (byte)b; }
/* 1465 */         else { if (i == 0)
/* 1466 */             return -1; 
/*      */           break; }
/*      */       
/*      */       } 
/* 1470 */       return i;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static class OutputStream
/*      */     extends FilterOutputStream
/*      */   {
/*      */     private boolean encode;
/*      */ 
/*      */     
/*      */     private int position;
/*      */ 
/*      */     
/*      */     private byte[] buffer;
/*      */ 
/*      */     
/*      */     private int bufferLength;
/*      */ 
/*      */     
/*      */     private int lineLength;
/*      */ 
/*      */     
/*      */     private boolean breakLines;
/*      */ 
/*      */     
/*      */     private byte[] b4;
/*      */ 
/*      */     
/*      */     private boolean suspendEncoding;
/*      */ 
/*      */     
/*      */     private int options;
/*      */     
/*      */     private byte[] alphabet;
/*      */     
/*      */     private byte[] decodabet;
/*      */ 
/*      */     
/*      */     public OutputStream(java.io.OutputStream out) {
/* 1511 */       this(out, 1);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public OutputStream(java.io.OutputStream out, int options) {
/* 1536 */       super(out);
/* 1537 */       this.breakLines = ((options & 0x8) != 8);
/* 1538 */       this.encode = ((options & 0x1) == 1);
/* 1539 */       this.bufferLength = this.encode ? 3 : 4;
/* 1540 */       this.buffer = new byte[this.bufferLength];
/* 1541 */       this.position = 0;
/* 1542 */       this.lineLength = 0;
/* 1543 */       this.suspendEncoding = false;
/* 1544 */       this.b4 = new byte[4];
/* 1545 */       this.options = options;
/* 1546 */       this.alphabet = Base64.getAlphabet(options);
/* 1547 */       this.decodabet = Base64.getDecodabet(options);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void write(int theByte) throws IOException {
/* 1565 */       if (this.suspendEncoding) {
/* 1566 */         this.out.write(theByte);
/*      */         
/*      */         return;
/*      */       } 
/*      */       
/* 1571 */       if (this.encode) {
/* 1572 */         this.buffer[this.position++] = (byte)theByte;
/* 1573 */         if (this.position >= this.bufferLength)
/*      */         {
/* 1575 */           this.out.write(Base64.encode3to4(this.b4, this.buffer, this.bufferLength, this.options));
/*      */           
/* 1577 */           this.lineLength += 4;
/* 1578 */           if (this.breakLines && this.lineLength >= 76) {
/* 1579 */             this.out.write(10);
/* 1580 */             this.lineLength = 0;
/*      */           } 
/*      */           
/* 1583 */           this.position = 0;
/*      */ 
/*      */         
/*      */         }
/*      */ 
/*      */       
/*      */       }
/* 1590 */       else if (this.decodabet[theByte & 0x7F] > -5) {
/* 1591 */         this.buffer[this.position++] = (byte)theByte;
/* 1592 */         if (this.position >= this.bufferLength)
/*      */         {
/* 1594 */           int len = Base64.decode4to3(this.buffer, 0, this.b4, 0, this.options);
/* 1595 */           this.out.write(this.b4, 0, len);
/*      */           
/* 1597 */           this.position = 0;
/*      */         }
/*      */       
/* 1600 */       } else if (this.decodabet[theByte & 0x7F] != -5) {
/* 1601 */         throw new IOException("invalid.character.in.base64.data");
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void write(byte[] theBytes, int off, int len) throws IOException {
/* 1618 */       if (this.suspendEncoding) {
/* 1619 */         this.out.write(theBytes, off, len);
/*      */         
/*      */         return;
/*      */       } 
/* 1623 */       for (int i = 0; i < len; i++) {
/* 1624 */         write(theBytes[off + i]);
/*      */       }
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void flushBase64() throws IOException {
/* 1636 */       if (this.position > 0) {
/* 1637 */         if (this.encode) {
/* 1638 */           this.out.write(Base64.encode3to4(this.b4, this.buffer, this.position, this.options));
/* 1639 */           this.position = 0;
/*      */         } else {
/*      */           
/* 1642 */           throw new IOException("base64.input.not.properly.padded");
/*      */         } 
/*      */       }
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void close() throws IOException {
/* 1656 */       flushBase64();
/*      */ 
/*      */ 
/*      */       
/* 1660 */       super.close();
/*      */       
/* 1662 */       this.buffer = null;
/* 1663 */       this.out = null;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void suspendEncoding() throws IOException {
/* 1677 */       flushBase64();
/* 1678 */       this.suspendEncoding = true;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void resumeEncoding() {
/* 1690 */       this.suspendEncoding = false;
/*      */     }
/*      */   }
/*      */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/io/codec/Base64.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */