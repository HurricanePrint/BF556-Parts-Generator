/*     */ package com.itextpdf.io.source;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.NotSerializableException;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.ObjectOutputStream;
/*     */ import java.io.Serializable;
/*     */ import java.lang.reflect.Method;
/*     */ import java.nio.BufferUnderflowException;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.security.AccessController;
/*     */ import java.security.PrivilegedAction;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ class ByteBufferRandomAccessSource
/*     */   implements IRandomAccessSource, Serializable
/*     */ {
/*     */   private static final long serialVersionUID = -1477190062876186034L;
/*     */   private transient ByteBuffer byteBuffer;
/*     */   private byte[] bufferMirror;
/*     */   public static final boolean UNMAP_SUPPORTED;
/*     */   private static final BufferCleaner CLEANER;
/*     */   
/*     */   public ByteBufferRandomAccessSource(ByteBuffer byteBuffer) {
/*  79 */     this.byteBuffer = byteBuffer;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int get(long position) throws IOException {
/*  90 */     if (position > 2147483647L) {
/*  91 */       throw new IllegalArgumentException("Position must be less than Integer.MAX_VALUE");
/*     */     }
/*     */     try {
/*  94 */       if (position >= this.byteBuffer.limit())
/*  95 */         return -1; 
/*  96 */       byte b = this.byteBuffer.get((int)position);
/*  97 */       return b & 0xFF;
/*  98 */     } catch (BufferUnderflowException e) {
/*     */       
/* 100 */       return -1;
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
/*     */   public int get(long position, byte[] bytes, int off, int len) throws IOException {
/* 112 */     if (position > 2147483647L) {
/* 113 */       throw new IllegalArgumentException("Position must be less than Integer.MAX_VALUE");
/*     */     }
/* 115 */     if (position >= this.byteBuffer.limit()) {
/* 116 */       return -1;
/*     */     }
/*     */     
/* 119 */     this.byteBuffer.position((int)position);
/* 120 */     int bytesFromThisBuffer = Math.min(len, this.byteBuffer.remaining());
/* 121 */     this.byteBuffer.get(bytes, off, bytesFromThisBuffer);
/*     */     
/* 123 */     return bytesFromThisBuffer;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long length() {
/* 131 */     return this.byteBuffer.limit();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void close() throws IOException {
/* 139 */     clean(this.byteBuffer);
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
/*     */   static {
/* 154 */     Object hack = AccessController.doPrivileged(new PrivilegedAction() {
/*     */           public Object run() {
/* 156 */             return BufferCleaner.unmapHackImpl();
/*     */           }
/*     */         });
/* 159 */     if (hack instanceof BufferCleaner) {
/* 160 */       CLEANER = (BufferCleaner)hack;
/* 161 */       UNMAP_SUPPORTED = true;
/*     */     } else {
/* 163 */       CLEANER = null;
/* 164 */       UNMAP_SUPPORTED = false;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static boolean clean(final ByteBuffer buffer) {
/* 175 */     if (buffer == null || !buffer.isDirect()) {
/* 176 */       return false;
/*     */     }
/* 178 */     Boolean b = AccessController.<Boolean>doPrivileged(new PrivilegedAction<Boolean>() {
/*     */           public Boolean run() {
/* 180 */             Boolean success = Boolean.FALSE;
/*     */             
/*     */             try {
/* 183 */               if (ByteBufferRandomAccessSource.UNMAP_SUPPORTED) {
/* 184 */                 ByteBufferRandomAccessSource.CLEANER.freeBuffer(buffer.toString(), buffer);
/*     */               } else {
/*     */                 
/* 187 */                 Method getCleanerMethod = buffer.getClass().getMethod("cleaner", (Class[])null);
/* 188 */                 getCleanerMethod.setAccessible(true);
/* 189 */                 Object cleaner = getCleanerMethod.invoke(buffer, (Object[])null);
/* 190 */                 Method clean = cleaner.getClass().getMethod("clean", (Class[])null);
/* 191 */                 clean.invoke(cleaner, (Object[])null);
/*     */               } 
/* 193 */               success = Boolean.TRUE;
/* 194 */             } catch (Exception e) {
/*     */               
/* 196 */               Logger logger = LoggerFactory.getLogger(ByteBufferRandomAccessSource.class);
/* 197 */               logger.debug(e.getMessage());
/*     */             } 
/* 199 */             return success;
/*     */           }
/*     */         });
/*     */     
/* 203 */     return b.booleanValue();
/*     */   }
/*     */   
/*     */   private void writeObject(ObjectOutputStream out) throws IOException {
/* 207 */     if (this.byteBuffer != null && this.byteBuffer.hasArray())
/* 208 */       throw new NotSerializableException(this.byteBuffer.getClass().toString()); 
/* 209 */     if (this.byteBuffer != null) {
/* 210 */       this.bufferMirror = this.byteBuffer.array();
/*     */     }
/* 212 */     out.defaultWriteObject();
/*     */   }
/*     */   
/*     */   private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
/* 216 */     in.defaultReadObject();
/* 217 */     if (this.bufferMirror != null) {
/* 218 */       this.byteBuffer = ByteBuffer.wrap(this.bufferMirror);
/* 219 */       this.bufferMirror = null;
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/io/source/ByteBufferRandomAccessSource.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */