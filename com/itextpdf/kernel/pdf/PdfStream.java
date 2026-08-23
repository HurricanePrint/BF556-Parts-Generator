/*     */ package com.itextpdf.kernel.pdf;
/*     */ 
/*     */ import com.itextpdf.io.source.ByteArrayOutputStream;
/*     */ import com.itextpdf.kernel.PdfException;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.ObjectOutputStream;
/*     */ import java.io.OutputStream;
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
/*     */ public class PdfStream
/*     */   extends PdfDictionary
/*     */ {
/*     */   private static final long serialVersionUID = -8259929152054328141L;
/*     */   protected int compressionLevel;
/*     */   protected PdfOutputStream outputStream;
/*     */   private InputStream inputStream;
/*     */   private long offset;
/*  68 */   private int length = -1;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PdfStream(byte[] bytes, int compressionLevel) {
/*  78 */     setState((short)64);
/*  79 */     this.compressionLevel = compressionLevel;
/*  80 */     if (bytes != null && bytes.length > 0) {
/*  81 */       this.outputStream = new PdfOutputStream((OutputStream)new ByteArrayOutputStream(bytes.length));
/*  82 */       this.outputStream.writeBytes(bytes);
/*     */     } else {
/*  84 */       this.outputStream = new PdfOutputStream((OutputStream)new ByteArrayOutputStream());
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PdfStream(byte[] bytes) {
/*  94 */     this(bytes, -2147483648);
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
/*     */   public PdfStream(PdfDocument doc, InputStream inputStream, int compressionLevel) {
/* 116 */     if (doc == null) {
/* 117 */       throw new PdfException("Cannot create pdfstream by InputStream without PdfDocument.");
/*     */     }
/* 119 */     makeIndirect(doc);
/* 120 */     if (inputStream == null) {
/* 121 */       throw new IllegalArgumentException("The input stream in PdfStream constructor can not be null.");
/*     */     }
/* 123 */     this.inputStream = inputStream;
/* 124 */     this.compressionLevel = compressionLevel;
/* 125 */     put(PdfName.Length, (new PdfNumber(-1)).makeIndirect(doc));
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
/*     */   public PdfStream(PdfDocument doc, InputStream inputStream) {
/* 144 */     this(doc, inputStream, -2147483648);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PdfStream(int compressionLevel) {
/* 153 */     this((byte[])null, compressionLevel);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PdfStream() {
/* 160 */     this((byte[])null);
/*     */   }
/*     */   
/*     */   protected PdfStream(OutputStream outputStream) {
/* 164 */     this.outputStream = new PdfOutputStream(outputStream);
/* 165 */     this.compressionLevel = Integer.MIN_VALUE;
/* 166 */     setState((short)64);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   PdfStream(long offset, PdfDictionary keys) {
/* 172 */     this.compressionLevel = Integer.MIN_VALUE;
/* 173 */     this.offset = offset;
/* 174 */     putAll(keys);
/* 175 */     PdfNumber length = getAsNumber(PdfName.Length);
/* 176 */     if (length == null) {
/* 177 */       this.length = 0;
/*     */     } else {
/* 179 */       this.length = length.intValue();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PdfOutputStream getOutputStream() {
/* 189 */     return this.outputStream;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getCompressionLevel() {
/* 199 */     return this.compressionLevel;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setCompressionLevel(int compressionLevel) {
/* 209 */     this.compressionLevel = compressionLevel;
/*     */   }
/*     */ 
/*     */   
/*     */   public byte getType() {
/* 214 */     return 9;
/*     */   }
/*     */   
/*     */   public int getLength() {
/* 218 */     return this.length;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public byte[] getBytes() {
/* 229 */     return getBytes(true);
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
/*     */   public byte[] getBytes(boolean decoded) {
/* 241 */     if (isFlushed()) {
/* 242 */       throw new PdfException("Cannot operate with the flushed PdfStream.");
/*     */     }
/* 244 */     if (this.inputStream != null) {
/* 245 */       LoggerFactory.getLogger(PdfStream.class).warn("PdfStream was created by InputStream.getBytes() always returns null in this case");
/*     */       
/* 247 */       return null;
/*     */     } 
/* 249 */     byte[] bytes = null;
/* 250 */     if (this.outputStream != null && this.outputStream.getOutputStream() != null) {
/* 251 */       assert this.outputStream.getOutputStream() instanceof ByteArrayOutputStream : "Invalid OutputStream: ByteArrayByteArrayOutputStream expected";
/*     */       
/*     */       try {
/* 254 */         this.outputStream.getOutputStream().flush();
/* 255 */         bytes = ((ByteArrayOutputStream)this.outputStream.getOutputStream()).toByteArray();
/* 256 */         if (decoded && containsKey(PdfName.Filter)) {
/* 257 */           bytes = PdfReader.decodeBytes(bytes, this);
/*     */         }
/* 259 */       } catch (IOException ioe) {
/* 260 */         throw new PdfException("Cannot get PdfStream bytes.", ioe, this);
/*     */       } 
/* 262 */     } else if (getIndirectReference() != null) {
/*     */ 
/*     */       
/* 265 */       PdfReader reader = getIndirectReference().getReader();
/* 266 */       if (reader != null) {
/*     */         try {
/* 268 */           bytes = reader.readStreamBytes(this, decoded);
/* 269 */         } catch (IOException ioe) {
/* 270 */           throw new PdfException("Cannot get PdfStream bytes.", ioe, this);
/*     */         } 
/*     */       }
/*     */     } 
/* 274 */     return bytes;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setData(byte[] bytes) {
/* 284 */     setData(bytes, false);
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
/*     */   public void setData(byte[] bytes, boolean append) {
/* 300 */     if (isFlushed()) {
/* 301 */       throw new PdfException("Cannot operate with the flushed PdfStream.");
/*     */     }
/* 303 */     if (this.inputStream != null) {
/* 304 */       throw new PdfException("Cannot set data to PdfStream which was created by InputStream.");
/*     */     }
/*     */     
/* 307 */     boolean outputStreamIsUninitialized = (this.outputStream == null);
/* 308 */     if (outputStreamIsUninitialized) {
/* 309 */       this.outputStream = new PdfOutputStream((OutputStream)new ByteArrayOutputStream());
/*     */     }
/*     */     
/* 312 */     if (append) {
/* 313 */       if ((outputStreamIsUninitialized && getIndirectReference() != null && getIndirectReference().getReader() != null) || (!outputStreamIsUninitialized && 
/* 314 */         containsKey(PdfName.Filter))) {
/*     */         byte[] oldBytes;
/*     */ 
/*     */ 
/*     */         
/*     */         try {
/* 320 */           oldBytes = getBytes();
/* 321 */         } catch (PdfException ex) {
/* 322 */           throw new PdfException("Cannot read a stream in order to append new bytes.", ex);
/*     */         } 
/* 324 */         this.outputStream.assignBytes(oldBytes, oldBytes.length);
/*     */       } 
/*     */       
/* 327 */       if (bytes != null) {
/* 328 */         this.outputStream.writeBytes(bytes);
/*     */       }
/*     */     }
/* 331 */     else if (bytes != null) {
/* 332 */       this.outputStream.assignBytes(bytes, bytes.length);
/*     */     } else {
/* 334 */       this.outputStream.reset();
/*     */     } 
/*     */ 
/*     */     
/* 338 */     this.offset = 0L;
/*     */ 
/*     */     
/* 341 */     remove(PdfName.Filter);
/* 342 */     remove(PdfName.DecodeParms);
/*     */   }
/*     */ 
/*     */   
/*     */   protected PdfObject newInstance() {
/* 347 */     return new PdfStream();
/*     */   }
/*     */   
/*     */   protected long getOffset() {
/* 351 */     return this.offset;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void updateLength(int length) {
/* 361 */     this.length = length;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void copyContent(PdfObject from, PdfDocument document) {
/* 366 */     super.copyContent(from, document);
/* 367 */     PdfStream stream = (PdfStream)from;
/* 368 */     assert this.inputStream == null : "Try to copy the PdfStream that has been just created.";
/* 369 */     byte[] bytes = stream.getBytes(false);
/*     */     try {
/* 371 */       this.outputStream.write(bytes);
/* 372 */     } catch (IOException ioe) {
/* 373 */       throw new PdfException("Cannot copy object content.", ioe, stream);
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void initOutputStream(OutputStream stream) {
/* 378 */     if (getOutputStream() == null && this.inputStream == null) {
/* 379 */       this.outputStream = new PdfOutputStream((stream != null) ? stream : (OutputStream)new ByteArrayOutputStream());
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void releaseContent() {
/* 386 */     super.releaseContent();
/*     */     try {
/* 388 */       if (this.outputStream != null) {
/* 389 */         this.outputStream.close();
/* 390 */         this.outputStream = null;
/*     */       } 
/* 392 */     } catch (IOException e) {
/* 393 */       throw new PdfException("I/O exception.", e);
/*     */     } 
/*     */   }
/*     */   
/*     */   protected InputStream getInputStream() {
/* 398 */     return this.inputStream;
/*     */   }
/*     */   
/*     */   private void writeObject(ObjectOutputStream out) throws IOException {
/* 402 */     if (this.inputStream == null || this.inputStream instanceof java.io.Serializable) {
/* 403 */       out.defaultWriteObject();
/*     */     } else {
/* 405 */       InputStream backup = this.inputStream;
/* 406 */       this.inputStream = null;
/* 407 */       LoggerFactory.getLogger(getClass()).warn("PdfStream contains not null input stream. It's content will be lost in serialized object.");
/* 408 */       this.inputStream = backup;
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/kernel/pdf/PdfStream.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */