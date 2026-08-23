/*     */ package com.itextpdf.io.source;
/*     */ 
/*     */ import com.itextpdf.io.util.MessageFormatUtil;
/*     */ import com.itextpdf.io.util.ResourceUtil;
/*     */ import com.itextpdf.io.util.StreamUtil;
/*     */ import java.io.File;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.RandomAccessFile;
/*     */ import java.io.Serializable;
/*     */ import java.net.URL;
/*     */ import java.nio.channels.FileChannel;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class RandomAccessSourceFactory
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = -8958482579413233761L;
/*     */   private boolean forceRead = false;
/*     */   private boolean usePlainRandomAccess = false;
/*     */   private boolean exclusivelyLockFile = false;
/*     */   
/*     */   public RandomAccessSourceFactory setForceRead(boolean forceRead) {
/*  93 */     this.forceRead = forceRead;
/*  94 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public RandomAccessSourceFactory setUsePlainRandomAccess(boolean usePlainRandomAccess) {
/* 103 */     this.usePlainRandomAccess = usePlainRandomAccess;
/* 104 */     return this;
/*     */   }
/*     */   
/*     */   public RandomAccessSourceFactory setExclusivelyLockFile(boolean exclusivelyLockFile) {
/* 108 */     this.exclusivelyLockFile = exclusivelyLockFile;
/* 109 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IRandomAccessSource createSource(byte[] data) {
/* 118 */     return new ArrayRandomAccessSource(data);
/*     */   }
/*     */   
/*     */   public IRandomAccessSource createSource(RandomAccessFile raf) throws IOException {
/* 122 */     return new RAFRandomAccessSource(raf);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IRandomAccessSource createSource(URL url) throws IOException {
/* 133 */     InputStream stream = url.openStream();
/*     */     try {
/* 135 */       return createSource(stream);
/*     */     } finally {
/*     */       
/*     */       try {
/* 139 */         stream.close();
/* 140 */       } catch (IOException iOException) {}
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
/*     */   public IRandomAccessSource createSource(InputStream inputStream) throws IOException {
/* 152 */     return createSource(StreamUtil.inputStreamToArray(inputStream));
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
/*     */   public IRandomAccessSource createBestSource(String filename) throws IOException {
/* 167 */     File file = new File(filename);
/* 168 */     if (!file.canRead()) {
/* 169 */       if (filename.startsWith("file:/") || filename
/* 170 */         .startsWith("http://") || filename
/* 171 */         .startsWith("https://") || filename
/* 172 */         .startsWith("jar:") || filename
/* 173 */         .startsWith("wsjar:") || filename
/* 174 */         .startsWith("wsjar:") || filename
/* 175 */         .startsWith("vfszip:")) {
/* 176 */         return createSource(new URL(filename));
/*     */       }
/* 178 */       return createByReadingToMemory(filename);
/*     */     } 
/*     */ 
/*     */     
/* 182 */     if (this.forceRead) {
/* 183 */       return createByReadingToMemory(new FileInputStream(filename));
/*     */     }
/*     */     
/* 186 */     String openMode = this.exclusivelyLockFile ? "rw" : "r";
/*     */     
/* 188 */     RandomAccessFile raf = new RandomAccessFile(file, openMode);
/* 189 */     if (this.exclusivelyLockFile) {
/* 190 */       raf.getChannel().lock();
/*     */     }
/*     */     
/* 193 */     if (this.usePlainRandomAccess) {
/* 194 */       return new RAFRandomAccessSource(raf);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 201 */       if (raf.length() <= 0L) {
/* 202 */         return new RAFRandomAccessSource(raf);
/*     */       }
/*     */ 
/*     */       
/*     */       try {
/* 207 */         return createBestSource(raf.getChannel());
/* 208 */       } catch (IOException e) {
/* 209 */         if (exceptionIsMapFailureException(e)) {
/* 210 */           return new RAFRandomAccessSource(raf);
/*     */         }
/* 212 */         throw e;
/*     */       } 
/* 214 */     } catch (Exception e) {
/*     */ 
/*     */       
/*     */       try {
/* 218 */         raf.close();
/* 219 */       } catch (IOException iOException) {}
/* 220 */       throw e;
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
/*     */   public IRandomAccessSource createBestSource(FileChannel channel) throws IOException {
/* 237 */     if (channel.size() <= 67108864L) {
/* 238 */       return new GetBufferedRandomAccessSource(new FileChannelRandomAccessSource(channel));
/*     */     }
/* 240 */     return new GetBufferedRandomAccessSource(new PagedChannelRandomAccessSource(channel));
/*     */   }
/*     */ 
/*     */   
/*     */   public IRandomAccessSource createRanged(IRandomAccessSource source, long[] ranges) throws IOException {
/* 245 */     IRandomAccessSource[] sources = new IRandomAccessSource[ranges.length / 2];
/* 246 */     for (int i = 0; i < ranges.length; i += 2) {
/* 247 */       sources[i / 2] = new WindowRandomAccessSource(source, ranges[i], ranges[i + 1]);
/*     */     }
/* 249 */     return new GroupedRandomAccessSource(sources);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private IRandomAccessSource createByReadingToMemory(String filename) throws IOException {
/* 259 */     InputStream stream = ResourceUtil.getResourceStream(filename);
/* 260 */     if (stream == null) {
/* 261 */       throw new IOException(MessageFormatUtil.format("{0} not found as file or resource.", new Object[] { filename }));
/*     */     }
/* 263 */     return createByReadingToMemory(stream);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private IRandomAccessSource createByReadingToMemory(InputStream stream) throws IOException {
/*     */     try {
/* 274 */       return new ArrayRandomAccessSource(StreamUtil.inputStreamToArray(stream));
/*     */     } finally {
/*     */       
/*     */       try {
/* 278 */         stream.close();
/* 279 */       } catch (IOException iOException) {}
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
/*     */   private static boolean exceptionIsMapFailureException(IOException e) {
/* 292 */     if (e.getMessage() != null && e.getMessage().contains("Map failed"))
/* 293 */       return true; 
/* 294 */     return false;
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/io/source/RandomAccessSourceFactory.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */