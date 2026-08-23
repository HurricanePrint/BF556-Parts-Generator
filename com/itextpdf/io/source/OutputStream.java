/*     */ package com.itextpdf.io.source;
/*     */ 
/*     */ import com.itextpdf.io.IOException;
/*     */ import java.io.IOException;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.ObjectOutputStream;
/*     */ import java.io.OutputStream;
/*     */ import java.io.Serializable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class OutputStream<T extends OutputStream>
/*     */   extends OutputStream
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = -5337390096148526418L;
/*  55 */   private final ByteBuffer numBuffer = new ByteBuffer(32);
/*     */   
/*  57 */   protected OutputStream outputStream = null;
/*  58 */   protected long currentPos = 0L;
/*     */   protected boolean closeStream = true;
/*     */   
/*     */   public static boolean getHighPrecision() {
/*  62 */     return ByteUtils.HighPrecision;
/*     */   }
/*     */   
/*     */   public static void setHighPrecision(boolean value) {
/*  66 */     ByteUtils.HighPrecision = value;
/*     */   }
/*     */ 
/*     */   
/*     */   public OutputStream(OutputStream outputStream) {
/*  71 */     this.outputStream = outputStream;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected OutputStream() {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void write(int b) throws IOException {
/*  83 */     this.outputStream.write(b);
/*  84 */     this.currentPos++;
/*     */   }
/*     */ 
/*     */   
/*     */   public void write(byte[] b) throws IOException {
/*  89 */     this.outputStream.write(b);
/*  90 */     this.currentPos += b.length;
/*     */   }
/*     */ 
/*     */   
/*     */   public void write(byte[] b, int off, int len) throws IOException {
/*  95 */     this.outputStream.write(b, off, len);
/*  96 */     this.currentPos += len;
/*     */   }
/*     */   
/*     */   public void writeByte(byte value) {
/*     */     try {
/* 101 */       write(value);
/* 102 */     } catch (IOException e) {
/* 103 */       throw new IOException("Cannot write byte.", e);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void flush() throws IOException {
/* 109 */     this.outputStream.flush();
/*     */   }
/*     */ 
/*     */   
/*     */   public void close() throws IOException {
/* 114 */     if (this.closeStream)
/* 115 */       this.outputStream.close(); 
/*     */   }
/*     */   
/*     */   public T writeLong(long value) {
/*     */     try {
/* 120 */       ByteUtils.getIsoBytes(value, this.numBuffer.reset());
/* 121 */       write(this.numBuffer.getInternalBuffer(), this.numBuffer.capacity() - this.numBuffer.size(), this.numBuffer.size());
/* 122 */       return (T)this;
/* 123 */     } catch (IOException e) {
/* 124 */       throw new IOException("Cannot write int number.", e);
/*     */     } 
/*     */   }
/*     */   
/*     */   public T writeInteger(int value) {
/*     */     try {
/* 130 */       ByteUtils.getIsoBytes(value, this.numBuffer.reset());
/* 131 */       write(this.numBuffer.getInternalBuffer(), this.numBuffer.capacity() - this.numBuffer.size(), this.numBuffer.size());
/* 132 */       return (T)this;
/* 133 */     } catch (IOException e) {
/* 134 */       throw new IOException("Cannot write int number.", e);
/*     */     } 
/*     */   }
/*     */   
/*     */   public T writeFloat(float value) {
/* 139 */     return writeFloat(value, ByteUtils.HighPrecision);
/*     */   }
/*     */   
/*     */   public T writeFloat(float value, boolean highPrecision) {
/* 143 */     return writeDouble(value, highPrecision);
/*     */   }
/*     */   
/*     */   public T writeFloats(float[] value) {
/* 147 */     for (int i = 0; i < value.length; i++) {
/* 148 */       writeFloat(value[i]);
/* 149 */       if (i < value.length - 1)
/* 150 */         writeSpace(); 
/*     */     } 
/* 152 */     return (T)this;
/*     */   }
/*     */   
/*     */   public T writeDouble(double value) {
/* 156 */     return writeDouble(value, ByteUtils.HighPrecision);
/*     */   }
/*     */   
/*     */   public T writeDouble(double value, boolean highPrecision) {
/*     */     try {
/* 161 */       ByteUtils.getIsoBytes(value, this.numBuffer.reset(), highPrecision);
/* 162 */       write(this.numBuffer.getInternalBuffer(), this.numBuffer.capacity() - this.numBuffer.size(), this.numBuffer.size());
/* 163 */       return (T)this;
/* 164 */     } catch (IOException e) {
/* 165 */       throw new IOException("Cannot write float number.", e);
/*     */     } 
/*     */   }
/*     */   
/*     */   public T writeByte(int value) {
/*     */     try {
/* 171 */       write(value);
/* 172 */       return (T)this;
/* 173 */     } catch (IOException e) {
/* 174 */       throw new IOException("Cannot write byte.", e);
/*     */     } 
/*     */   }
/*     */   
/*     */   public T writeSpace() {
/* 179 */     return writeByte(32);
/*     */   }
/*     */   
/*     */   public T writeNewLine() {
/* 183 */     return writeByte(10);
/*     */   }
/*     */   
/*     */   public T writeString(String value) {
/* 187 */     return writeBytes(ByteUtils.getIsoBytes(value));
/*     */   }
/*     */   
/*     */   public T writeBytes(byte[] b) {
/*     */     try {
/* 192 */       write(b);
/* 193 */       return (T)this;
/* 194 */     } catch (IOException e) {
/* 195 */       throw new IOException("Cannot write bytes.", e);
/*     */     } 
/*     */   }
/*     */   
/*     */   public T writeBytes(byte[] b, int off, int len) {
/*     */     try {
/* 201 */       write(b, off, len);
/* 202 */       return (T)this;
/* 203 */     } catch (IOException e) {
/* 204 */       throw new IOException("Cannot write bytes.", e);
/*     */     } 
/*     */   }
/*     */   
/*     */   public long getCurrentPos() {
/* 209 */     return this.currentPos;
/*     */   }
/*     */   
/*     */   public OutputStream getOutputStream() {
/* 213 */     return this.outputStream;
/*     */   }
/*     */   
/*     */   public boolean isCloseStream() {
/* 217 */     return this.closeStream;
/*     */   }
/*     */   
/*     */   public void setCloseStream(boolean closeStream) {
/* 221 */     this.closeStream = closeStream;
/*     */   }
/*     */   
/*     */   public void assignBytes(byte[] bytes, int count) {
/* 225 */     if (this.outputStream instanceof ByteArrayOutputStream) {
/* 226 */       ((ByteArrayOutputStream)this.outputStream).assignBytes(bytes, count);
/* 227 */       this.currentPos = count;
/*     */     } else {
/* 229 */       throw new IOException("Bytes can be assigned to ByteArrayOutputStream only.");
/*     */     } 
/*     */   }
/*     */   public void reset() {
/* 233 */     if (this.outputStream instanceof ByteArrayOutputStream) {
/* 234 */       ((ByteArrayOutputStream)this.outputStream).reset();
/* 235 */       this.currentPos = 0L;
/*     */     } else {
/* 237 */       throw new IOException("Bytes can be reset in ByteArrayOutputStream only.");
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
/* 248 */     in.defaultReadObject();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void writeObject(ObjectOutputStream out) throws IOException {
/* 258 */     OutputStream tempOutputStream = this.outputStream;
/* 259 */     this.outputStream = null;
/* 260 */     out.defaultWriteObject();
/* 261 */     this.outputStream = tempOutputStream;
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/io/source/OutputStream.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */