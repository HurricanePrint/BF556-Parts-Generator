/*     */ package com.itextpdf.io.source;
/*     */ 
/*     */ import com.itextpdf.io.IOException;
/*     */ import com.itextpdf.io.util.MessageFormatUtil;
/*     */ import java.io.Closeable;
/*     */ import java.io.IOException;
/*     */ import java.io.Serializable;
/*     */ import java.util.Arrays;
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
/*     */ public class PdfTokenizer
/*     */   implements Closeable, Serializable
/*     */ {
/*     */   private static final long serialVersionUID = -2949864233416670521L;
/*     */   
/*     */   public enum TokenType
/*     */   {
/*  61 */     Number,
/*  62 */     String,
/*  63 */     Name,
/*  64 */     Comment,
/*  65 */     StartArray,
/*  66 */     EndArray,
/*  67 */     StartDic,
/*  68 */     EndDic,
/*  69 */     Ref,
/*  70 */     Obj,
/*  71 */     EndObj,
/*  72 */     Other,
/*  73 */     EndOfFile;
/*     */   }
/*     */   
/*  76 */   public static final boolean[] delims = new boolean[] { 
/*     */       true, true, false, false, false, false, false, false, false, false, 
/*     */       true, true, false, true, true, false, false, false, false, false, 
/*     */       false, false, false, false, false, false, false, false, false, false, 
/*     */       false, false, false, true, false, false, false, false, true, false, 
/*     */       false, true, true, false, false, false, false, false, true, false, 
/*     */       false, false, false, false, false, false, false, false, false, false, 
/*     */       false, true, false, true, false, false, false, false, false, false, 
/*     */       false, false, false, false, false, false, false, false, false, false, 
/*     */       false, false, false, false, false, false, false, false, false, false, 
/*     */       false, false, true, false, true, false, false, false, false, false, 
/*     */       false, false, false, false, false, false, false, false, false, false, 
/*     */       false, false, false, false, false, false, false, false, false, false, 
/*     */       false, false, false, false, false, false, false, false, false, false, 
/*     */       false, false, false, false, false, false, false, false, false, false, 
/*     */       false, false, false, false, false, false, false, false, false, false, 
/*     */       false, false, false, false, false, false, false, false, false, false, 
/*     */       false, false, false, false, false, false, false, false, false, false, 
/*     */       false, false, false, false, false, false, false, false, false, false, 
/*     */       false, false, false, false, false, false, false, false, false, false, 
/*     */       false, false, false, false, false, false, false, false, false, false, 
/*     */       false, false, false, false, false, false, false, false, false, false, 
/*     */       false, false, false, false, false, false, false, false, false, false, 
/*     */       false, false, false, false, false, false, false, false, false, false, 
/*     */       false, false, false, false, false, false, false, false, false, false, 
/*     */       false, false, false, false, false, false, false, false, false, false, 
/*     */       false, false, false, false, false, false, false };
/*     */ 
/*     */   
/* 105 */   public static final byte[] Obj = ByteUtils.getIsoBytes("obj");
/* 106 */   public static final byte[] R = ByteUtils.getIsoBytes("R");
/* 107 */   public static final byte[] Xref = ByteUtils.getIsoBytes("xref");
/* 108 */   public static final byte[] Startxref = ByteUtils.getIsoBytes("startxref");
/* 109 */   public static final byte[] Stream = ByteUtils.getIsoBytes("stream");
/* 110 */   public static final byte[] Trailer = ByteUtils.getIsoBytes("trailer");
/* 111 */   public static final byte[] N = ByteUtils.getIsoBytes("n");
/* 112 */   public static final byte[] F = ByteUtils.getIsoBytes("f");
/* 113 */   public static final byte[] Null = ByteUtils.getIsoBytes("null");
/* 114 */   public static final byte[] True = ByteUtils.getIsoBytes("true");
/* 115 */   public static final byte[] False = ByteUtils.getIsoBytes("false");
/*     */ 
/*     */   
/*     */   protected TokenType type;
/*     */ 
/*     */   
/*     */   protected int reference;
/*     */ 
/*     */   
/*     */   protected int generation;
/*     */ 
/*     */   
/*     */   protected boolean hexString;
/*     */   
/*     */   protected ByteBuffer outBuf;
/*     */   
/*     */   private final RandomAccessFileOrArray file;
/*     */   
/*     */   private boolean closeStream = true;
/*     */ 
/*     */   
/*     */   public PdfTokenizer(RandomAccessFileOrArray file) {
/* 137 */     this.file = file;
/* 138 */     this.outBuf = new ByteBuffer();
/*     */   }
/*     */   
/*     */   public void seek(long pos) throws IOException {
/* 142 */     this.file.seek(pos);
/*     */   }
/*     */   
/*     */   public void readFully(byte[] bytes) throws IOException {
/* 146 */     this.file.readFully(bytes);
/*     */   }
/*     */   
/*     */   public long getPosition() throws IOException {
/* 150 */     return this.file.getPosition();
/*     */   }
/*     */   
/*     */   public void close() throws IOException {
/* 154 */     if (this.closeStream)
/* 155 */       this.file.close(); 
/*     */   }
/*     */   
/*     */   public long length() throws IOException {
/* 159 */     return this.file.length();
/*     */   }
/*     */   
/*     */   public int read() throws IOException {
/* 163 */     return this.file.read();
/*     */   }
/*     */   
/*     */   public String readString(int size) throws IOException {
/* 167 */     StringBuilder buf = new StringBuilder();
/*     */     
/* 169 */     while (size-- > 0) {
/* 170 */       int ch = read();
/* 171 */       if (ch == -1)
/*     */         break; 
/* 173 */       buf.append((char)ch);
/*     */     } 
/* 175 */     return buf.toString();
/*     */   }
/*     */   
/*     */   public TokenType getTokenType() {
/* 179 */     return this.type;
/*     */   }
/*     */   
/*     */   public byte[] getByteContent() {
/* 183 */     return this.outBuf.toByteArray();
/*     */   }
/*     */   
/*     */   public String getStringValue() {
/* 187 */     return new String(this.outBuf.getInternalBuffer(), 0, this.outBuf.size());
/*     */   }
/*     */   
/*     */   public byte[] getDecodedStringContent() {
/* 191 */     return decodeStringContent(this.outBuf.getInternalBuffer(), 0, this.outBuf.size() - 1, isHexString());
/*     */   }
/*     */   
/*     */   public boolean tokenValueEqualsTo(byte[] cmp) {
/* 195 */     if (cmp == null) {
/* 196 */       return false;
/*     */     }
/* 198 */     int size = cmp.length;
/* 199 */     if (this.outBuf.size() != size) {
/* 200 */       return false;
/*     */     }
/* 202 */     for (int i = 0; i < size; i++) {
/* 203 */       if (cmp[i] != this.outBuf.getInternalBuffer()[i])
/* 204 */         return false; 
/* 205 */     }  return true;
/*     */   }
/*     */   
/*     */   public int getObjNr() {
/* 209 */     return this.reference;
/*     */   }
/*     */   
/*     */   public int getGenNr() {
/* 213 */     return this.generation;
/*     */   }
/*     */   
/*     */   public void backOnePosition(int ch) {
/* 217 */     if (ch != -1)
/* 218 */       this.file.pushBack((byte)ch); 
/*     */   }
/*     */   
/*     */   public int getHeaderOffset() throws IOException {
/* 222 */     String str = readString(1024);
/* 223 */     int idx = str.indexOf("%PDF-");
/* 224 */     if (idx < 0) {
/* 225 */       idx = str.indexOf("%FDF-");
/* 226 */       if (idx < 0) {
/* 227 */         throw new IOException("PDF header not found.", this);
/*     */       }
/*     */     } 
/* 230 */     return idx;
/*     */   }
/*     */   
/*     */   public String checkPdfHeader() throws IOException {
/* 234 */     this.file.seek(0L);
/* 235 */     String str = readString(1024);
/* 236 */     int idx = str.indexOf("%PDF-");
/* 237 */     if (idx != 0)
/* 238 */       throw new IOException("PDF header not found.", this); 
/* 239 */     return str.substring(idx + 1, idx + 8);
/*     */   }
/*     */   
/*     */   public void checkFdfHeader() throws IOException {
/* 243 */     this.file.seek(0L);
/* 244 */     String str = readString(1024);
/* 245 */     int idx = str.indexOf("%FDF-");
/* 246 */     if (idx != 0)
/* 247 */       throw new IOException("FDF startxref not found.", this); 
/*     */   }
/*     */   
/*     */   public long getStartxref() throws IOException {
/* 251 */     int arrLength = 1024;
/* 252 */     long fileLength = this.file.length();
/* 253 */     long pos = fileLength - arrLength;
/* 254 */     if (pos < 1L) pos = 1L; 
/* 255 */     while (pos > 0L) {
/* 256 */       this.file.seek(pos);
/* 257 */       String str = readString(arrLength);
/* 258 */       int idx = str.lastIndexOf("startxref");
/* 259 */       if (idx >= 0) return pos + idx;
/*     */       
/* 261 */       pos = pos - arrLength + 9L;
/*     */     } 
/* 263 */     throw new IOException("PDF startxref not found.", this);
/*     */   }
/*     */   
/*     */   public void nextValidToken() throws IOException {
/* 267 */     int level = 0;
/* 268 */     byte[] n1 = null;
/* 269 */     byte[] n2 = null;
/* 270 */     long ptr = 0L;
/* 271 */     while (nextToken()) {
/* 272 */       if (this.type == TokenType.Comment)
/*     */         continue; 
/* 274 */       switch (level) {
/*     */         case 0:
/* 276 */           if (this.type != TokenType.Number)
/*     */             return; 
/* 278 */           ptr = this.file.getPosition();
/* 279 */           n1 = getByteContent();
/* 280 */           level++;
/*     */ 
/*     */         
/*     */         case 1:
/* 284 */           if (this.type != TokenType.Number) {
/* 285 */             this.file.seek(ptr);
/* 286 */             this.type = TokenType.Number;
/* 287 */             this.outBuf.reset().append(n1);
/*     */             return;
/*     */           } 
/* 290 */           n2 = getByteContent();
/* 291 */           level++;
/*     */ 
/*     */         
/*     */         case 2:
/* 295 */           if (this.type == TokenType.Other) {
/* 296 */             if (tokenValueEqualsTo(R)) {
/* 297 */               assert n2 != null;
/* 298 */               this.type = TokenType.Ref;
/*     */               try {
/* 300 */                 this.reference = Integer.parseInt(new String(n1));
/* 301 */                 this.generation = Integer.parseInt(new String(n2));
/* 302 */               } catch (Exception ex) {
/*     */ 
/*     */                 
/* 305 */                 Logger logger = LoggerFactory.getLogger(PdfTokenizer.class);
/* 306 */                 logger.error(MessageFormatUtil.format("Invalid indirect reference {0} {1} R", new Object[] { new String(n1), new String(n2) }));
/* 307 */                 this.reference = -1;
/* 308 */                 this.generation = 0;
/*     */               }  return;
/*     */             } 
/* 311 */             if (tokenValueEqualsTo(Obj)) {
/* 312 */               assert n2 != null;
/* 313 */               this.type = TokenType.Obj;
/* 314 */               this.reference = Integer.parseInt(new String(n1));
/* 315 */               this.generation = Integer.parseInt(new String(n2));
/*     */               return;
/*     */             } 
/*     */           } 
/* 319 */           this.file.seek(ptr);
/* 320 */           this.type = TokenType.Number;
/* 321 */           this.outBuf.reset().append(n1);
/*     */           return;
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     } 
/* 329 */     if (level == 1) {
/* 330 */       this.type = TokenType.Number;
/* 331 */       this.outBuf.reset().append(n1);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean nextToken() throws IOException {
/*     */     // Byte code:
/*     */     //   0: aload_0
/*     */     //   1: getfield outBuf : Lcom/itextpdf/io/source/ByteBuffer;
/*     */     //   4: invokevirtual reset : ()Lcom/itextpdf/io/source/ByteBuffer;
/*     */     //   7: pop
/*     */     //   8: aload_0
/*     */     //   9: getfield file : Lcom/itextpdf/io/source/RandomAccessFileOrArray;
/*     */     //   12: invokevirtual read : ()I
/*     */     //   15: istore_1
/*     */     //   16: iload_1
/*     */     //   17: iconst_m1
/*     */     //   18: if_icmpeq -> 28
/*     */     //   21: iload_1
/*     */     //   22: invokestatic isWhitespace : (I)Z
/*     */     //   25: ifne -> 8
/*     */     //   28: iload_1
/*     */     //   29: iconst_m1
/*     */     //   30: if_icmpne -> 42
/*     */     //   33: aload_0
/*     */     //   34: getstatic com/itextpdf/io/source/PdfTokenizer$TokenType.EndOfFile : Lcom/itextpdf/io/source/PdfTokenizer$TokenType;
/*     */     //   37: putfield type : Lcom/itextpdf/io/source/PdfTokenizer$TokenType;
/*     */     //   40: iconst_0
/*     */     //   41: ireturn
/*     */     //   42: iload_1
/*     */     //   43: lookupswitch default -> 539, 37 -> 384, 40 -> 419, 47 -> 128, 60 -> 210, 62 -> 176, 91 -> 108, 93 -> 118
/*     */     //   108: aload_0
/*     */     //   109: getstatic com/itextpdf/io/source/PdfTokenizer$TokenType.StartArray : Lcom/itextpdf/io/source/PdfTokenizer$TokenType;
/*     */     //   112: putfield type : Lcom/itextpdf/io/source/PdfTokenizer$TokenType;
/*     */     //   115: goto -> 821
/*     */     //   118: aload_0
/*     */     //   119: getstatic com/itextpdf/io/source/PdfTokenizer$TokenType.EndArray : Lcom/itextpdf/io/source/PdfTokenizer$TokenType;
/*     */     //   122: putfield type : Lcom/itextpdf/io/source/PdfTokenizer$TokenType;
/*     */     //   125: goto -> 821
/*     */     //   128: aload_0
/*     */     //   129: getstatic com/itextpdf/io/source/PdfTokenizer$TokenType.Name : Lcom/itextpdf/io/source/PdfTokenizer$TokenType;
/*     */     //   132: putfield type : Lcom/itextpdf/io/source/PdfTokenizer$TokenType;
/*     */     //   135: aload_0
/*     */     //   136: getfield file : Lcom/itextpdf/io/source/RandomAccessFileOrArray;
/*     */     //   139: invokevirtual read : ()I
/*     */     //   142: istore_1
/*     */     //   143: getstatic com/itextpdf/io/source/PdfTokenizer.delims : [Z
/*     */     //   146: iload_1
/*     */     //   147: iconst_1
/*     */     //   148: iadd
/*     */     //   149: baload
/*     */     //   150: ifeq -> 156
/*     */     //   153: goto -> 168
/*     */     //   156: aload_0
/*     */     //   157: getfield outBuf : Lcom/itextpdf/io/source/ByteBuffer;
/*     */     //   160: iload_1
/*     */     //   161: invokevirtual append : (I)Lcom/itextpdf/io/source/ByteBuffer;
/*     */     //   164: pop
/*     */     //   165: goto -> 135
/*     */     //   168: aload_0
/*     */     //   169: iload_1
/*     */     //   170: invokevirtual backOnePosition : (I)V
/*     */     //   173: goto -> 821
/*     */     //   176: aload_0
/*     */     //   177: getfield file : Lcom/itextpdf/io/source/RandomAccessFileOrArray;
/*     */     //   180: invokevirtual read : ()I
/*     */     //   183: istore_1
/*     */     //   184: iload_1
/*     */     //   185: bipush #62
/*     */     //   187: if_icmpeq -> 200
/*     */     //   190: aload_0
/*     */     //   191: ldc ''>' not expected.'
/*     */     //   193: iconst_0
/*     */     //   194: anewarray java/lang/Object
/*     */     //   197: invokevirtual throwError : (Ljava/lang/String;[Ljava/lang/Object;)V
/*     */     //   200: aload_0
/*     */     //   201: getstatic com/itextpdf/io/source/PdfTokenizer$TokenType.EndDic : Lcom/itextpdf/io/source/PdfTokenizer$TokenType;
/*     */     //   204: putfield type : Lcom/itextpdf/io/source/PdfTokenizer$TokenType;
/*     */     //   207: goto -> 821
/*     */     //   210: aload_0
/*     */     //   211: getfield file : Lcom/itextpdf/io/source/RandomAccessFileOrArray;
/*     */     //   214: invokevirtual read : ()I
/*     */     //   217: istore_2
/*     */     //   218: iload_2
/*     */     //   219: bipush #60
/*     */     //   221: if_icmpne -> 234
/*     */     //   224: aload_0
/*     */     //   225: getstatic com/itextpdf/io/source/PdfTokenizer$TokenType.StartDic : Lcom/itextpdf/io/source/PdfTokenizer$TokenType;
/*     */     //   228: putfield type : Lcom/itextpdf/io/source/PdfTokenizer$TokenType;
/*     */     //   231: goto -> 821
/*     */     //   234: aload_0
/*     */     //   235: getstatic com/itextpdf/io/source/PdfTokenizer$TokenType.String : Lcom/itextpdf/io/source/PdfTokenizer$TokenType;
/*     */     //   238: putfield type : Lcom/itextpdf/io/source/PdfTokenizer$TokenType;
/*     */     //   241: aload_0
/*     */     //   242: iconst_1
/*     */     //   243: putfield hexString : Z
/*     */     //   246: iconst_0
/*     */     //   247: istore_3
/*     */     //   248: iload_2
/*     */     //   249: invokestatic isWhitespace : (I)Z
/*     */     //   252: ifeq -> 266
/*     */     //   255: aload_0
/*     */     //   256: getfield file : Lcom/itextpdf/io/source/RandomAccessFileOrArray;
/*     */     //   259: invokevirtual read : ()I
/*     */     //   262: istore_2
/*     */     //   263: goto -> 248
/*     */     //   266: iload_2
/*     */     //   267: bipush #62
/*     */     //   269: if_icmpne -> 275
/*     */     //   272: goto -> 363
/*     */     //   275: aload_0
/*     */     //   276: getfield outBuf : Lcom/itextpdf/io/source/ByteBuffer;
/*     */     //   279: iload_2
/*     */     //   280: invokevirtual append : (I)Lcom/itextpdf/io/source/ByteBuffer;
/*     */     //   283: pop
/*     */     //   284: iload_2
/*     */     //   285: invokestatic getHex : (I)I
/*     */     //   288: istore_2
/*     */     //   289: iload_2
/*     */     //   290: ifge -> 296
/*     */     //   293: goto -> 363
/*     */     //   296: aload_0
/*     */     //   297: getfield file : Lcom/itextpdf/io/source/RandomAccessFileOrArray;
/*     */     //   300: invokevirtual read : ()I
/*     */     //   303: istore_3
/*     */     //   304: iload_3
/*     */     //   305: invokestatic isWhitespace : (I)Z
/*     */     //   308: ifeq -> 322
/*     */     //   311: aload_0
/*     */     //   312: getfield file : Lcom/itextpdf/io/source/RandomAccessFileOrArray;
/*     */     //   315: invokevirtual read : ()I
/*     */     //   318: istore_3
/*     */     //   319: goto -> 304
/*     */     //   322: iload_3
/*     */     //   323: bipush #62
/*     */     //   325: if_icmpne -> 331
/*     */     //   328: goto -> 363
/*     */     //   331: aload_0
/*     */     //   332: getfield outBuf : Lcom/itextpdf/io/source/ByteBuffer;
/*     */     //   335: iload_3
/*     */     //   336: invokevirtual append : (I)Lcom/itextpdf/io/source/ByteBuffer;
/*     */     //   339: pop
/*     */     //   340: iload_3
/*     */     //   341: invokestatic getHex : (I)I
/*     */     //   344: istore_3
/*     */     //   345: iload_3
/*     */     //   346: ifge -> 352
/*     */     //   349: goto -> 363
/*     */     //   352: aload_0
/*     */     //   353: getfield file : Lcom/itextpdf/io/source/RandomAccessFileOrArray;
/*     */     //   356: invokevirtual read : ()I
/*     */     //   359: istore_2
/*     */     //   360: goto -> 248
/*     */     //   363: iload_2
/*     */     //   364: iflt -> 371
/*     */     //   367: iload_3
/*     */     //   368: ifge -> 821
/*     */     //   371: aload_0
/*     */     //   372: ldc 'Error reading string.'
/*     */     //   374: iconst_0
/*     */     //   375: anewarray java/lang/Object
/*     */     //   378: invokevirtual throwError : (Ljava/lang/String;[Ljava/lang/Object;)V
/*     */     //   381: goto -> 821
/*     */     //   384: aload_0
/*     */     //   385: getstatic com/itextpdf/io/source/PdfTokenizer$TokenType.Comment : Lcom/itextpdf/io/source/PdfTokenizer$TokenType;
/*     */     //   388: putfield type : Lcom/itextpdf/io/source/PdfTokenizer$TokenType;
/*     */     //   391: aload_0
/*     */     //   392: getfield file : Lcom/itextpdf/io/source/RandomAccessFileOrArray;
/*     */     //   395: invokevirtual read : ()I
/*     */     //   398: istore_1
/*     */     //   399: iload_1
/*     */     //   400: iconst_m1
/*     */     //   401: if_icmpeq -> 821
/*     */     //   404: iload_1
/*     */     //   405: bipush #13
/*     */     //   407: if_icmpeq -> 821
/*     */     //   410: iload_1
/*     */     //   411: bipush #10
/*     */     //   413: if_icmpne -> 391
/*     */     //   416: goto -> 821
/*     */     //   419: aload_0
/*     */     //   420: getstatic com/itextpdf/io/source/PdfTokenizer$TokenType.String : Lcom/itextpdf/io/source/PdfTokenizer$TokenType;
/*     */     //   423: putfield type : Lcom/itextpdf/io/source/PdfTokenizer$TokenType;
/*     */     //   426: aload_0
/*     */     //   427: iconst_0
/*     */     //   428: putfield hexString : Z
/*     */     //   431: iconst_0
/*     */     //   432: istore_2
/*     */     //   433: aload_0
/*     */     //   434: getfield file : Lcom/itextpdf/io/source/RandomAccessFileOrArray;
/*     */     //   437: invokevirtual read : ()I
/*     */     //   440: istore_1
/*     */     //   441: iload_1
/*     */     //   442: iconst_m1
/*     */     //   443: if_icmpne -> 449
/*     */     //   446: goto -> 521
/*     */     //   449: iload_1
/*     */     //   450: bipush #40
/*     */     //   452: if_icmpne -> 461
/*     */     //   455: iinc #2, 1
/*     */     //   458: goto -> 509
/*     */     //   461: iload_1
/*     */     //   462: bipush #41
/*     */     //   464: if_icmpne -> 478
/*     */     //   467: iinc #2, -1
/*     */     //   470: iload_2
/*     */     //   471: iconst_m1
/*     */     //   472: if_icmpne -> 509
/*     */     //   475: goto -> 521
/*     */     //   478: iload_1
/*     */     //   479: bipush #92
/*     */     //   481: if_icmpne -> 509
/*     */     //   484: aload_0
/*     */     //   485: getfield outBuf : Lcom/itextpdf/io/source/ByteBuffer;
/*     */     //   488: bipush #92
/*     */     //   490: invokevirtual append : (I)Lcom/itextpdf/io/source/ByteBuffer;
/*     */     //   493: pop
/*     */     //   494: aload_0
/*     */     //   495: getfield file : Lcom/itextpdf/io/source/RandomAccessFileOrArray;
/*     */     //   498: invokevirtual read : ()I
/*     */     //   501: istore_1
/*     */     //   502: iload_1
/*     */     //   503: ifge -> 509
/*     */     //   506: goto -> 521
/*     */     //   509: aload_0
/*     */     //   510: getfield outBuf : Lcom/itextpdf/io/source/ByteBuffer;
/*     */     //   513: iload_1
/*     */     //   514: invokevirtual append : (I)Lcom/itextpdf/io/source/ByteBuffer;
/*     */     //   517: pop
/*     */     //   518: goto -> 433
/*     */     //   521: iload_1
/*     */     //   522: iconst_m1
/*     */     //   523: if_icmpne -> 821
/*     */     //   526: aload_0
/*     */     //   527: ldc 'Error reading string.'
/*     */     //   529: iconst_0
/*     */     //   530: anewarray java/lang/Object
/*     */     //   533: invokevirtual throwError : (Ljava/lang/String;[Ljava/lang/Object;)V
/*     */     //   536: goto -> 821
/*     */     //   539: iload_1
/*     */     //   540: bipush #45
/*     */     //   542: if_icmpeq -> 569
/*     */     //   545: iload_1
/*     */     //   546: bipush #43
/*     */     //   548: if_icmpeq -> 569
/*     */     //   551: iload_1
/*     */     //   552: bipush #46
/*     */     //   554: if_icmpeq -> 569
/*     */     //   557: iload_1
/*     */     //   558: bipush #48
/*     */     //   560: if_icmplt -> 777
/*     */     //   563: iload_1
/*     */     //   564: bipush #57
/*     */     //   566: if_icmpgt -> 777
/*     */     //   569: aload_0
/*     */     //   570: getstatic com/itextpdf/io/source/PdfTokenizer$TokenType.Number : Lcom/itextpdf/io/source/PdfTokenizer$TokenType;
/*     */     //   573: putfield type : Lcom/itextpdf/io/source/PdfTokenizer$TokenType;
/*     */     //   576: iconst_0
/*     */     //   577: istore_2
/*     */     //   578: iconst_0
/*     */     //   579: istore_3
/*     */     //   580: iload_1
/*     */     //   581: bipush #45
/*     */     //   583: if_icmpne -> 616
/*     */     //   586: iinc #3, 1
/*     */     //   589: aload_0
/*     */     //   590: getfield file : Lcom/itextpdf/io/source/RandomAccessFileOrArray;
/*     */     //   593: invokevirtual read : ()I
/*     */     //   596: istore_1
/*     */     //   597: iload_1
/*     */     //   598: bipush #45
/*     */     //   600: if_icmpeq -> 586
/*     */     //   603: aload_0
/*     */     //   604: getfield outBuf : Lcom/itextpdf/io/source/ByteBuffer;
/*     */     //   607: bipush #45
/*     */     //   609: invokevirtual append : (I)Lcom/itextpdf/io/source/ByteBuffer;
/*     */     //   612: pop
/*     */     //   613: goto -> 633
/*     */     //   616: aload_0
/*     */     //   617: getfield outBuf : Lcom/itextpdf/io/source/ByteBuffer;
/*     */     //   620: iload_1
/*     */     //   621: invokevirtual append : (I)Lcom/itextpdf/io/source/ByteBuffer;
/*     */     //   624: pop
/*     */     //   625: aload_0
/*     */     //   626: getfield file : Lcom/itextpdf/io/source/RandomAccessFileOrArray;
/*     */     //   629: invokevirtual read : ()I
/*     */     //   632: istore_1
/*     */     //   633: iload_1
/*     */     //   634: bipush #48
/*     */     //   636: if_icmplt -> 665
/*     */     //   639: iload_1
/*     */     //   640: bipush #57
/*     */     //   642: if_icmpgt -> 665
/*     */     //   645: aload_0
/*     */     //   646: getfield outBuf : Lcom/itextpdf/io/source/ByteBuffer;
/*     */     //   649: iload_1
/*     */     //   650: invokevirtual append : (I)Lcom/itextpdf/io/source/ByteBuffer;
/*     */     //   653: pop
/*     */     //   654: aload_0
/*     */     //   655: getfield file : Lcom/itextpdf/io/source/RandomAccessFileOrArray;
/*     */     //   658: invokevirtual read : ()I
/*     */     //   661: istore_1
/*     */     //   662: goto -> 633
/*     */     //   665: iload_1
/*     */     //   666: bipush #46
/*     */     //   668: if_icmpne -> 747
/*     */     //   671: iconst_1
/*     */     //   672: istore_2
/*     */     //   673: aload_0
/*     */     //   674: getfield outBuf : Lcom/itextpdf/io/source/ByteBuffer;
/*     */     //   677: iload_1
/*     */     //   678: invokevirtual append : (I)Lcom/itextpdf/io/source/ByteBuffer;
/*     */     //   681: pop
/*     */     //   682: aload_0
/*     */     //   683: getfield file : Lcom/itextpdf/io/source/RandomAccessFileOrArray;
/*     */     //   686: invokevirtual read : ()I
/*     */     //   689: istore_1
/*     */     //   690: iconst_0
/*     */     //   691: istore #4
/*     */     //   693: iload_1
/*     */     //   694: bipush #45
/*     */     //   696: if_icmpne -> 710
/*     */     //   699: iinc #4, 1
/*     */     //   702: aload_0
/*     */     //   703: getfield file : Lcom/itextpdf/io/source/RandomAccessFileOrArray;
/*     */     //   706: invokevirtual read : ()I
/*     */     //   709: istore_1
/*     */     //   710: iload_1
/*     */     //   711: bipush #48
/*     */     //   713: if_icmplt -> 747
/*     */     //   716: iload_1
/*     */     //   717: bipush #57
/*     */     //   719: if_icmpgt -> 747
/*     */     //   722: iload #4
/*     */     //   724: ifne -> 736
/*     */     //   727: aload_0
/*     */     //   728: getfield outBuf : Lcom/itextpdf/io/source/ByteBuffer;
/*     */     //   731: iload_1
/*     */     //   732: invokevirtual append : (I)Lcom/itextpdf/io/source/ByteBuffer;
/*     */     //   735: pop
/*     */     //   736: aload_0
/*     */     //   737: getfield file : Lcom/itextpdf/io/source/RandomAccessFileOrArray;
/*     */     //   740: invokevirtual read : ()I
/*     */     //   743: istore_1
/*     */     //   744: goto -> 710
/*     */     //   747: iload_3
/*     */     //   748: iconst_1
/*     */     //   749: if_icmple -> 774
/*     */     //   752: iload_2
/*     */     //   753: ifne -> 774
/*     */     //   756: aload_0
/*     */     //   757: getfield outBuf : Lcom/itextpdf/io/source/ByteBuffer;
/*     */     //   760: invokevirtual reset : ()Lcom/itextpdf/io/source/ByteBuffer;
/*     */     //   763: pop
/*     */     //   764: aload_0
/*     */     //   765: getfield outBuf : Lcom/itextpdf/io/source/ByteBuffer;
/*     */     //   768: bipush #48
/*     */     //   770: invokevirtual append : (I)Lcom/itextpdf/io/source/ByteBuffer;
/*     */     //   773: pop
/*     */     //   774: goto -> 811
/*     */     //   777: aload_0
/*     */     //   778: getstatic com/itextpdf/io/source/PdfTokenizer$TokenType.Other : Lcom/itextpdf/io/source/PdfTokenizer$TokenType;
/*     */     //   781: putfield type : Lcom/itextpdf/io/source/PdfTokenizer$TokenType;
/*     */     //   784: aload_0
/*     */     //   785: getfield outBuf : Lcom/itextpdf/io/source/ByteBuffer;
/*     */     //   788: iload_1
/*     */     //   789: invokevirtual append : (I)Lcom/itextpdf/io/source/ByteBuffer;
/*     */     //   792: pop
/*     */     //   793: aload_0
/*     */     //   794: getfield file : Lcom/itextpdf/io/source/RandomAccessFileOrArray;
/*     */     //   797: invokevirtual read : ()I
/*     */     //   800: istore_1
/*     */     //   801: getstatic com/itextpdf/io/source/PdfTokenizer.delims : [Z
/*     */     //   804: iload_1
/*     */     //   805: iconst_1
/*     */     //   806: iadd
/*     */     //   807: baload
/*     */     //   808: ifeq -> 784
/*     */     //   811: iload_1
/*     */     //   812: iconst_m1
/*     */     //   813: if_icmpeq -> 821
/*     */     //   816: aload_0
/*     */     //   817: iload_1
/*     */     //   818: invokevirtual backOnePosition : (I)V
/*     */     //   821: iconst_1
/*     */     //   822: ireturn
/*     */     // Line number table:
/*     */     //   Java source line number -> byte code offset
/*     */     //   #341	-> 0
/*     */     //   #343	-> 8
/*     */     //   #344	-> 16
/*     */     //   #345	-> 28
/*     */     //   #346	-> 33
/*     */     //   #347	-> 40
/*     */     //   #349	-> 42
/*     */     //   #351	-> 108
/*     */     //   #352	-> 115
/*     */     //   #355	-> 118
/*     */     //   #356	-> 125
/*     */     //   #359	-> 128
/*     */     //   #361	-> 135
/*     */     //   #362	-> 143
/*     */     //   #363	-> 153
/*     */     //   #364	-> 156
/*     */     //   #366	-> 168
/*     */     //   #367	-> 173
/*     */     //   #370	-> 176
/*     */     //   #371	-> 184
/*     */     //   #372	-> 190
/*     */     //   #373	-> 200
/*     */     //   #374	-> 207
/*     */     //   #377	-> 210
/*     */     //   #378	-> 218
/*     */     //   #379	-> 224
/*     */     //   #380	-> 231
/*     */     //   #382	-> 234
/*     */     //   #383	-> 241
/*     */     //   #384	-> 246
/*     */     //   #386	-> 248
/*     */     //   #387	-> 255
/*     */     //   #388	-> 266
/*     */     //   #389	-> 272
/*     */     //   #390	-> 275
/*     */     //   #391	-> 284
/*     */     //   #392	-> 289
/*     */     //   #393	-> 293
/*     */     //   #394	-> 296
/*     */     //   #395	-> 304
/*     */     //   #396	-> 311
/*     */     //   #397	-> 322
/*     */     //   #398	-> 328
/*     */     //   #400	-> 331
/*     */     //   #401	-> 340
/*     */     //   #402	-> 345
/*     */     //   #403	-> 349
/*     */     //   #404	-> 352
/*     */     //   #406	-> 363
/*     */     //   #407	-> 371
/*     */     //   #411	-> 384
/*     */     //   #413	-> 391
/*     */     //   #414	-> 399
/*     */     //   #415	-> 416
/*     */     //   #418	-> 419
/*     */     //   #419	-> 426
/*     */     //   #420	-> 431
/*     */     //   #422	-> 433
/*     */     //   #423	-> 441
/*     */     //   #424	-> 446
/*     */     //   #425	-> 449
/*     */     //   #426	-> 455
/*     */     //   #427	-> 461
/*     */     //   #428	-> 467
/*     */     //   #429	-> 470
/*     */     //   #430	-> 475
/*     */     //   #431	-> 478
/*     */     //   #432	-> 484
/*     */     //   #433	-> 494
/*     */     //   #434	-> 502
/*     */     //   #435	-> 506
/*     */     //   #437	-> 509
/*     */     //   #439	-> 521
/*     */     //   #440	-> 526
/*     */     //   #444	-> 539
/*     */     //   #445	-> 569
/*     */     //   #446	-> 576
/*     */     //   #447	-> 578
/*     */     //   #448	-> 580
/*     */     //   #451	-> 586
/*     */     //   #452	-> 589
/*     */     //   #453	-> 597
/*     */     //   #454	-> 603
/*     */     //   #456	-> 616
/*     */     //   #459	-> 625
/*     */     //   #461	-> 633
/*     */     //   #462	-> 645
/*     */     //   #463	-> 654
/*     */     //   #466	-> 665
/*     */     //   #467	-> 671
/*     */     //   #468	-> 673
/*     */     //   #469	-> 682
/*     */     //   #473	-> 690
/*     */     //   #474	-> 693
/*     */     //   #475	-> 699
/*     */     //   #476	-> 702
/*     */     //   #478	-> 710
/*     */     //   #479	-> 722
/*     */     //   #480	-> 727
/*     */     //   #482	-> 736
/*     */     //   #486	-> 747
/*     */     //   #489	-> 756
/*     */     //   #490	-> 764
/*     */     //   #492	-> 774
/*     */     //   #493	-> 777
/*     */     //   #495	-> 784
/*     */     //   #496	-> 793
/*     */     //   #497	-> 801
/*     */     //   #499	-> 811
/*     */     //   #500	-> 816
/*     */     //   #504	-> 821
/*     */     // Local variable table:
/*     */     //   start	length	slot	name	descriptor
/*     */     //   218	166	2	v1	I
/*     */     //   248	136	3	v2	I
/*     */     //   433	106	2	nesting	I
/*     */     //   693	54	4	numberOfMinusesAfterDot	I
/*     */     //   578	196	2	isReal	Z
/*     */     //   580	194	3	numberOfMinuses	I
/*     */     //   0	823	0	this	Lcom/itextpdf/io/source/PdfTokenizer;
/*     */     //   16	807	1	ch	I
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long getLongValue() {
/* 508 */     return Long.parseLong(getStringValue());
/*     */   }
/*     */   
/*     */   public int getIntValue() {
/* 512 */     return Integer.parseInt(getStringValue());
/*     */   }
/*     */   
/*     */   public boolean isHexString() {
/* 516 */     return this.hexString;
/*     */   }
/*     */   
/*     */   public boolean isCloseStream() {
/* 520 */     return this.closeStream;
/*     */   }
/*     */   
/*     */   public void setCloseStream(boolean closeStream) {
/* 524 */     this.closeStream = closeStream;
/*     */   }
/*     */   
/*     */   public RandomAccessFileOrArray getSafeFile() {
/* 528 */     return this.file.createView();
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
/*     */   protected static byte[] decodeStringContent(byte[] content, int from, int to, boolean hexWriting) {
/* 545 */     ByteBuffer buffer = new ByteBuffer(to - from + 1);
/*     */ 
/*     */     
/* 548 */     if (hexWriting) {
/* 549 */       for (int i = from; i <= to; ) {
/* 550 */         int v1 = ByteBuffer.getHex(content[i++]);
/* 551 */         if (i > to) {
/* 552 */           buffer.append(v1 << 4);
/*     */           break;
/*     */         } 
/* 555 */         int v2 = content[i++];
/* 556 */         v2 = ByteBuffer.getHex(v2);
/* 557 */         buffer.append((v1 << 4) + v2);
/*     */       }
/*     */     
/*     */     } else {
/*     */       
/* 562 */       for (int i = from; i <= to; ) {
/* 563 */         int ch = content[i++];
/* 564 */         if (ch == 92) {
/* 565 */           int octal; boolean lineBreak = false;
/* 566 */           ch = content[i++];
/* 567 */           switch (ch) {
/*     */             case 110:
/* 569 */               ch = 10;
/*     */               break;
/*     */             case 114:
/* 572 */               ch = 13;
/*     */               break;
/*     */             case 116:
/* 575 */               ch = 9;
/*     */               break;
/*     */             case 98:
/* 578 */               ch = 8;
/*     */               break;
/*     */             case 102:
/* 581 */               ch = 12;
/*     */               break;
/*     */             case 40:
/*     */             case 41:
/*     */             case 92:
/*     */               break;
/*     */             case 13:
/* 588 */               lineBreak = true;
/* 589 */               if (i <= to && content[i++] != 10) {
/* 590 */                 i--;
/*     */               }
/*     */               break;
/*     */             case 10:
/* 594 */               lineBreak = true;
/*     */               break;
/*     */             default:
/* 597 */               if (ch < 48 || ch > 55) {
/*     */                 break;
/*     */               }
/* 600 */               octal = ch - 48;
/* 601 */               ch = content[i++];
/* 602 */               if (ch < 48 || ch > 55) {
/* 603 */                 i--;
/* 604 */                 ch = octal;
/*     */                 break;
/*     */               } 
/* 607 */               octal = (octal << 3) + ch - 48;
/* 608 */               ch = content[i++];
/* 609 */               if (ch < 48 || ch > 55) {
/* 610 */                 i--;
/* 611 */                 ch = octal;
/*     */                 break;
/*     */               } 
/* 614 */               octal = (octal << 3) + ch - 48;
/* 615 */               ch = octal & 0xFF;
/*     */               break;
/*     */           } 
/*     */           
/* 619 */           if (lineBreak)
/*     */             continue; 
/* 621 */         } else if (ch == 13) {
/*     */           
/* 623 */           ch = 10;
/* 624 */           if (i <= to && content[i++] != 10) {
/* 625 */             i--;
/*     */           }
/*     */         } 
/* 628 */         buffer.append(ch);
/*     */       } 
/*     */     } 
/* 631 */     return buffer.toByteArray();
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
/*     */   public static byte[] decodeStringContent(byte[] content, boolean hexWriting) {
/* 646 */     return decodeStringContent(content, 0, content.length - 1, hexWriting);
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
/*     */   public static boolean isWhitespace(int ch) {
/* 658 */     return isWhitespace(ch, true);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected static boolean isWhitespace(int ch, boolean isWhitespace) {
/* 669 */     return ((isWhitespace && ch == 0) || ch == 9 || ch == 10 || ch == 12 || ch == 13 || ch == 32);
/*     */   }
/*     */   
/*     */   protected static boolean isDelimiter(int ch) {
/* 673 */     return (ch == 40 || ch == 41 || ch == 60 || ch == 62 || ch == 91 || ch == 93 || ch == 47 || ch == 37);
/*     */   }
/*     */   
/*     */   protected static boolean isDelimiterWhitespace(int ch) {
/* 677 */     return delims[ch + 1];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void throwError(String error, Object... messageParams) {
/*     */     try {
/* 688 */       throw (new IOException("Error at file pointer {0}.", (new IOException(error)).setMessageParams(messageParams)))
/* 689 */         .setMessageParams(new Object[] { Long.valueOf(this.file.getPosition()) });
/* 690 */     } catch (IOException e) {
/* 691 */       throw (new IOException("Error at file pointer {0}.", (new IOException(error)).setMessageParams(messageParams)))
/* 692 */         .setMessageParams(new Object[] { error, "no position" });
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean checkTrailer(ByteBuffer line) {
/* 702 */     if (Trailer.length > line.size())
/* 703 */       return false; 
/* 704 */     for (int i = 0; i < Trailer.length; i++) {
/* 705 */       if (Trailer[i] != line.get(i))
/* 706 */         return false; 
/*     */     } 
/* 708 */     return true;
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
/*     */   public boolean readLineSegment(ByteBuffer buffer) throws IOException {
/* 723 */     return readLineSegment(buffer, true);
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
/*     */   public boolean readLineSegment(ByteBuffer buffer, boolean isNullWhitespace) throws IOException {
/* 739 */     boolean eol = false;
/*     */     
/*     */     int c;
/*     */     
/* 743 */     while (isWhitespace(c = read(), isNullWhitespace));
/*     */     
/* 745 */     boolean prevWasWhitespace = false;
/* 746 */     while (!eol) {
/* 747 */       long cur; switch (c) {
/*     */         case -1:
/*     */         case 10:
/* 750 */           eol = true;
/*     */           break;
/*     */         case 13:
/* 753 */           eol = true;
/* 754 */           cur = getPosition();
/* 755 */           if (read() != 10) {
/* 756 */             seek(cur);
/*     */           }
/*     */           break;
/*     */         case 9:
/*     */         case 12:
/*     */         case 32:
/* 762 */           if (prevWasWhitespace)
/*     */             break; 
/* 764 */           prevWasWhitespace = true;
/* 765 */           buffer.append((byte)c);
/*     */           break;
/*     */         default:
/* 768 */           prevWasWhitespace = false;
/* 769 */           buffer.append((byte)c);
/*     */           break;
/*     */       } 
/*     */       
/* 773 */       if (eol || buffer.size() == buffer.capacity()) {
/* 774 */         eol = true; continue;
/*     */       } 
/* 776 */       c = read();
/*     */     } 
/*     */     
/* 779 */     if (buffer.size() == buffer.capacity()) {
/* 780 */       eol = false;
/* 781 */       while (!eol) {
/* 782 */         long cur; switch (c = read()) {
/*     */           case -1:
/*     */           case 10:
/* 785 */             eol = true;
/*     */           
/*     */           case 13:
/* 788 */             eol = true;
/* 789 */             cur = getPosition();
/* 790 */             if (read() != 10) {
/* 791 */               seek(cur);
/*     */             }
/*     */         } 
/*     */       
/*     */       } 
/*     */     } 
/* 797 */     return (c != -1 || !buffer.isEmpty());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int[] checkObjectStart(PdfTokenizer lineTokenizer) {
/*     */     try {
/* 807 */       lineTokenizer.seek(0L);
/* 808 */       if (!lineTokenizer.nextToken() || lineTokenizer.getTokenType() != TokenType.Number)
/* 809 */         return null; 
/* 810 */       int num = lineTokenizer.getIntValue();
/* 811 */       if (!lineTokenizer.nextToken() || lineTokenizer.getTokenType() != TokenType.Number)
/* 812 */         return null; 
/* 813 */       int gen = lineTokenizer.getIntValue();
/* 814 */       if (!lineTokenizer.nextToken())
/* 815 */         return null; 
/* 816 */       if (!Arrays.equals(Obj, lineTokenizer.getByteContent()))
/* 817 */         return null; 
/* 818 */       return new int[] { num, gen };
/* 819 */     } catch (Exception exception) {
/*     */ 
/*     */       
/* 822 */       return null;
/*     */     } 
/*     */   }
/*     */   
/*     */   @Deprecated
/*     */   protected static class ReusableRandomAccessSource
/*     */     implements IRandomAccessSource
/*     */   {
/*     */     private ByteBuffer buffer;
/*     */     
/*     */     public ReusableRandomAccessSource(ByteBuffer buffer) {
/* 833 */       if (buffer == null) throw new IllegalArgumentException("Passed byte buffer can not be null."); 
/* 834 */       this.buffer = buffer;
/*     */     }
/*     */ 
/*     */     
/*     */     public int get(long offset) {
/* 839 */       if (offset >= this.buffer.size()) return -1; 
/* 840 */       return 0xFF & this.buffer.getInternalBuffer()[(int)offset];
/*     */     }
/*     */ 
/*     */     
/*     */     public int get(long offset, byte[] bytes, int off, int len) {
/* 845 */       if (this.buffer == null) throw new IllegalStateException("Already closed");
/*     */       
/* 847 */       if (offset >= this.buffer.size()) {
/* 848 */         return -1;
/*     */       }
/* 850 */       if (offset + len > this.buffer.size()) {
/* 851 */         len = (int)(this.buffer.size() - offset);
/*     */       }
/* 853 */       System.arraycopy(this.buffer.getInternalBuffer(), (int)offset, bytes, off, len);
/*     */       
/* 855 */       return len;
/*     */     }
/*     */ 
/*     */     
/*     */     public long length() {
/* 860 */       return this.buffer.size();
/*     */     }
/*     */ 
/*     */     
/*     */     public void close() throws IOException {
/* 865 */       this.buffer = null;
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/io/source/PdfTokenizer.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */