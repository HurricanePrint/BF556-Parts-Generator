/*    */ package com.itextpdf.io.source;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import java.lang.reflect.Field;
/*    */ import java.lang.reflect.Method;
/*    */ import java.nio.ByteBuffer;
/*    */ import java.security.AccessController;
/*    */ import java.security.PrivilegedAction;
/*    */ import java.util.Objects;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ class BufferCleaner
/*    */ {
/*    */   Class<?> unmappableBufferClass;
/*    */   final Method method;
/*    */   final Object theUnsafe;
/*    */   
/*    */   BufferCleaner(Class<?> unmappableBufferClass, Method method, Object theUnsafe) {
/* 39 */     this.unmappableBufferClass = unmappableBufferClass;
/* 40 */     this.method = method;
/* 41 */     this.theUnsafe = theUnsafe;
/*    */   }
/*    */   
/*    */   void freeBuffer(String resourceDescription, final ByteBuffer buffer) throws IOException {
/* 45 */     assert Objects.equals(void.class, this.method.getReturnType());
/* 46 */     assert (this.method.getParameterTypes()).length == 1;
/* 47 */     assert Objects.equals(ByteBuffer.class, this.method.getParameterTypes()[0]);
/* 48 */     if (!buffer.isDirect()) {
/* 49 */       throw new IllegalArgumentException("unmapping only works with direct buffers");
/*    */     }
/* 51 */     if (!this.unmappableBufferClass.isInstance(buffer)) {
/* 52 */       throw new IllegalArgumentException("buffer is not an instance of " + this.unmappableBufferClass.getName());
/*    */     }
/* 54 */     Throwable error = AccessController.<Throwable>doPrivileged(new PrivilegedAction<Throwable>() {
/*    */           public Throwable run() {
/*    */             try {
/* 57 */               BufferCleaner.this.method.invoke(BufferCleaner.this.theUnsafe, new Object[] { this.val$buffer });
/* 58 */               return null;
/* 59 */             } catch (IllegalAccessException|java.lang.reflect.InvocationTargetException e) {
/* 60 */               return e;
/*    */             } 
/*    */           }
/*    */         });
/* 64 */     if (error != null) {
/* 65 */       throw new IOException("Unable to unmap the mapped buffer: " + resourceDescription, error);
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   static Object unmapHackImpl() {
/*    */     try {
/* 72 */       Class<?> unsafeClass = Class.forName("sun.misc.Unsafe");
/* 73 */       Method method = unsafeClass.getDeclaredMethod("invokeCleaner", new Class[] { ByteBuffer.class });
/* 74 */       Field f = unsafeClass.getDeclaredField("theUnsafe");
/* 75 */       f.setAccessible(true);
/* 76 */       Object theUnsafe = f.get(null);
/* 77 */       return new BufferCleaner(ByteBuffer.class, method, theUnsafe);
/* 78 */     } catch (Exception e) {
/* 79 */       return e.getMessage();
/*    */     } 
/*    */   }
/*    */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/io/source/BufferCleaner.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */