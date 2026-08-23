/*     */ package com.itextpdf.kernel.pdf;
/*     */ 
/*     */ import com.itextpdf.io.font.PdfEncodings;
/*     */ import com.itextpdf.io.source.ByteBuffer;
/*     */ import com.itextpdf.io.source.PdfTokenizer;
/*     */ import com.itextpdf.io.util.StreamUtil;
/*     */ import java.nio.charset.StandardCharsets;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class PdfString
/*     */   extends PdfPrimitiveObject
/*     */ {
/*     */   private static final long serialVersionUID = 390789504287887010L;
/*     */   protected String value;
/*     */   protected String encoding;
/*     */   protected boolean hexWriting = false;
/*     */   private int decryptInfoNum;
/*     */   private int decryptInfoGen;
/*     */   private PdfEncryption decryption;
/*     */   
/*     */   public PdfString(String value, String encoding) {
/*  84 */     assert value != null;
/*  85 */     this.value = value;
/*  86 */     this.encoding = encoding;
/*     */   }
/*     */   
/*     */   public PdfString(String value) {
/*  90 */     this(value, (String)null);
/*     */   }
/*     */ 
/*     */   
/*     */   public PdfString(byte[] content) {
/*  95 */     if (content != null && content.length > 0) {
/*  96 */       StringBuilder str = new StringBuilder(content.length);
/*  97 */       for (byte b : content) {
/*  98 */         str.append((char)(b & 0xFF));
/*     */       }
/* 100 */       this.value = str.toString();
/*     */     } else {
/* 102 */       this.value = "";
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected PdfString(byte[] content, boolean hexWriting) {
/* 113 */     super(content);
/* 114 */     this.hexWriting = hexWriting;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private PdfString() {}
/*     */ 
/*     */   
/*     */   public byte getType() {
/* 123 */     return 10;
/*     */   }
/*     */   
/*     */   public boolean isHexWriting() {
/* 127 */     return this.hexWriting;
/*     */   }
/*     */   
/*     */   public PdfString setHexWriting(boolean hexWriting) {
/* 131 */     if (this.value == null) {
/* 132 */       generateValue();
/*     */     }
/* 134 */     this.content = null;
/* 135 */     this.hexWriting = hexWriting;
/* 136 */     return this;
/*     */   }
/*     */   
/*     */   public String getValue() {
/* 140 */     if (this.value == null)
/* 141 */       generateValue(); 
/* 142 */     return this.value;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getEncoding() {
/* 151 */     return this.encoding;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toUnicodeString() {
/* 161 */     if (this.encoding != null && this.encoding.length() != 0) {
/* 162 */       return getValue();
/*     */     }
/* 164 */     if (this.content == null) {
/* 165 */       generateContent();
/*     */     }
/* 167 */     byte[] b = decodeContent();
/* 168 */     if (b.length >= 2 && b[0] == -2 && b[1] == -1)
/* 169 */       return PdfEncodings.convertToString(b, "UnicodeBig"); 
/* 170 */     if (b.length >= 3 && b[0] == -17 && b[1] == -69 && b[2] == -65) {
/* 171 */       return PdfEncodings.convertToString(b, "UTF-8");
/*     */     }
/* 173 */     return PdfEncodings.convertToString(b, "PDF");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public byte[] getValueBytes() {
/* 184 */     if (this.value == null)
/* 185 */       generateValue(); 
/* 186 */     if (this.encoding != null && "UnicodeBig".equals(this.encoding) && PdfEncodings.isPdfDocEncoding(this.value)) {
/* 187 */       return PdfEncodings.convertToBytes(this.value, "PDF");
/*     */     }
/* 189 */     return PdfEncodings.convertToBytes(this.value, this.encoding);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object o) {
/* 194 */     if (this == o)
/* 195 */       return true; 
/* 196 */     if (o == null || getClass() != o.getClass())
/* 197 */       return false; 
/* 198 */     PdfString that = (PdfString)o;
/* 199 */     String v1 = getValue();
/* 200 */     String v2 = that.getValue();
/* 201 */     if (v1 != null && v1.equals(v2)) {
/* 202 */       String e1 = getEncoding();
/* 203 */       String e2 = that.getEncoding();
/* 204 */       if ((e1 == null && e2 == null) || (e1 != null && e1
/* 205 */         .equals(e2))) {
/* 206 */         return true;
/*     */       }
/*     */     } 
/* 209 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 214 */     if (this.value == null) {
/* 215 */       return new String(decodeContent(), StandardCharsets.ISO_8859_1);
/*     */     }
/* 217 */     return getValue();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 223 */     String v = getValue();
/* 224 */     String e = getEncoding();
/* 225 */     int result = (v != null) ? v.hashCode() : 0;
/* 226 */     return 31 * result + ((e != null) ? e.hashCode() : 0);
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
/*     */   public void markAsUnencryptedObject() {
/* 239 */     setState((short)512);
/*     */   }
/*     */   
/*     */   void setDecryption(int decryptInfoNum, int decryptInfoGen, PdfEncryption decryption) {
/* 243 */     this.decryptInfoNum = decryptInfoNum;
/* 244 */     this.decryptInfoGen = decryptInfoGen;
/* 245 */     this.decryption = decryption;
/*     */   }
/*     */   
/*     */   protected void generateValue() {
/* 249 */     assert this.content != null : "No byte[] content to generate value";
/* 250 */     this.value = PdfEncodings.convertToString(decodeContent(), null);
/* 251 */     if (this.decryption != null) {
/* 252 */       this.decryption = null;
/* 253 */       this.content = null;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected void generateContent() {
/* 259 */     this.content = encodeBytes(getValueBytes());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean encrypt(PdfEncryption encrypt) {
/* 269 */     if (checkState((short)512)) {
/* 270 */       return false;
/*     */     }
/* 272 */     if (encrypt != this.decryption) {
/* 273 */       if (this.decryption != null) {
/* 274 */         generateValue();
/*     */       }
/* 276 */       if (encrypt != null && !encrypt.isEmbeddedFilesOnly()) {
/* 277 */         byte[] b = encrypt.encryptByteArray(getValueBytes());
/* 278 */         this.content = encodeBytes(b);
/* 279 */         return true;
/*     */       } 
/*     */     } 
/* 282 */     return false;
/*     */   }
/*     */   
/*     */   protected byte[] decodeContent() {
/* 286 */     byte[] decodedBytes = PdfTokenizer.decodeStringContent(this.content, this.hexWriting);
/* 287 */     if (this.decryption != null && !checkState((short)512)) {
/* 288 */       this.decryption.setHashKeyForNextObject(this.decryptInfoNum, this.decryptInfoGen);
/* 289 */       decodedBytes = this.decryption.decryptByteArray(decodedBytes);
/*     */     } 
/* 291 */     return decodedBytes;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected byte[] encodeBytes(byte[] bytes) {
/* 302 */     if (this.hexWriting) {
/* 303 */       ByteBuffer byteBuffer = new ByteBuffer(bytes.length * 2);
/* 304 */       for (byte b : bytes) {
/* 305 */         byteBuffer.appendHex(b);
/*     */       }
/* 307 */       return byteBuffer.getInternalBuffer();
/*     */     } 
/* 309 */     ByteBuffer buf = StreamUtil.createBufferedEscapedString(bytes);
/* 310 */     return buf.toByteArray(1, buf.size() - 2);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected PdfObject newInstance() {
/* 316 */     return new PdfString();
/*     */   }
/*     */ 
/*     */   
/*     */   protected void copyContent(PdfObject from, PdfDocument document) {
/* 321 */     super.copyContent(from, document);
/* 322 */     PdfString string = (PdfString)from;
/* 323 */     this.value = string.value;
/* 324 */     this.hexWriting = string.hexWriting;
/* 325 */     this.decryption = string.decryption;
/* 326 */     this.decryptInfoNum = string.decryptInfoNum;
/* 327 */     this.decryptInfoGen = string.decryptInfoGen;
/* 328 */     this.encoding = string.encoding;
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/kernel/pdf/PdfString.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */