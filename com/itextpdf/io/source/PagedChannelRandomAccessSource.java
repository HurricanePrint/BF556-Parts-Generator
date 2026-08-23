/*     */ package com.itextpdf.io.source;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.NotSerializableException;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.ObjectOutputStream;
/*     */ import java.nio.channels.FileChannel;
/*     */ import java.util.Iterator;
/*     */ import java.util.LinkedList;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class PagedChannelRandomAccessSource
/*     */   extends GroupedRandomAccessSource
/*     */   implements IRandomAccessSource
/*     */ {
/*     */   public static final int DEFAULT_TOTAL_BUFSIZE = 67108864;
/*     */   public static final int DEFAULT_MAX_OPEN_BUFFERS = 16;
/*     */   private static final long serialVersionUID = 4297575388315637274L;
/*     */   private final int bufferSize;
/*     */   private final FileChannel channel;
/*     */   private final MRU<IRandomAccessSource> mru;
/*     */   
/*     */   public PagedChannelRandomAccessSource(FileChannel channel) throws IOException {
/*  93 */     this(channel, 67108864, 16);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PagedChannelRandomAccessSource(FileChannel channel, int totalBufferSize, int maxOpenBuffers) throws IOException {
/* 104 */     super(buildSources(channel, totalBufferSize / maxOpenBuffers));
/* 105 */     this.channel = channel;
/* 106 */     this.bufferSize = totalBufferSize / maxOpenBuffers;
/* 107 */     this.mru = new MRU<>(maxOpenBuffers);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static IRandomAccessSource[] buildSources(FileChannel channel, int bufferSize) throws IOException {
/* 118 */     long size = channel.size();
/* 119 */     if (size <= 0L) {
/* 120 */       throw new IOException("File size must be greater than zero");
/*     */     }
/* 122 */     int bufferCount = (int)(size / bufferSize) + ((size % bufferSize == 0L) ? 0 : 1);
/*     */     
/* 124 */     MappedChannelRandomAccessSource[] sources = new MappedChannelRandomAccessSource[bufferCount];
/* 125 */     for (int i = 0; i < bufferCount; i++) {
/* 126 */       long pageOffset = i * bufferSize;
/* 127 */       long pageLength = Math.min(size - pageOffset, bufferSize);
/* 128 */       sources[i] = new MappedChannelRandomAccessSource(channel, pageOffset, pageLength);
/*     */     } 
/* 130 */     return (IRandomAccessSource[])sources;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected int getStartingSourceIndex(long offset) {
/* 138 */     return (int)(offset / this.bufferSize);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void sourceReleased(IRandomAccessSource source) throws IOException {
/* 147 */     IRandomAccessSource old = this.mru.enqueue(source);
/* 148 */     if (old != null) {
/* 149 */       old.close();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void sourceInUse(IRandomAccessSource source) throws IOException {
/* 158 */     ((MappedChannelRandomAccessSource)source).open();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void close() throws IOException {
/*     */     try {
/* 168 */       super.close();
/*     */     } finally {
/*     */       try {
/* 171 */         this.channel.close();
/* 172 */       } catch (Exception ex) {
/* 173 */         Logger logger = LoggerFactory.getLogger(PagedChannelRandomAccessSource.class);
/* 174 */         logger.error("Closing of the file channel this source is based on failed.", ex);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private void writeObject(ObjectOutputStream out) throws IOException {
/* 180 */     throw new NotSerializableException(getClass().toString());
/*     */   }
/*     */   
/*     */   private void readObject(ObjectInputStream in) throws IOException {
/* 184 */     throw new NotSerializableException(getClass().toString());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static class MRU<E>
/*     */   {
/*     */     private final int limit;
/*     */ 
/*     */ 
/*     */     
/* 196 */     private LinkedList<E> queue = new LinkedList<>();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public MRU(int limit) {
/* 203 */       this.limit = limit;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public E enqueue(E newElement) {
/* 213 */       if (this.queue.size() > 0 && this.queue.getFirst() == newElement) {
/* 214 */         return null;
/*     */       }
/* 216 */       for (Iterator<E> it = this.queue.iterator(); it.hasNext(); ) {
/* 217 */         E element = it.next();
/* 218 */         if (newElement == element) {
/* 219 */           it.remove();
/* 220 */           this.queue.addFirst(newElement);
/* 221 */           return null;
/*     */         } 
/*     */       } 
/* 224 */       this.queue.addFirst(newElement);
/*     */       
/* 226 */       if (this.queue.size() > this.limit) {
/* 227 */         return this.queue.removeLast();
/*     */       }
/* 229 */       return null;
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/io/source/PagedChannelRandomAccessSource.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */