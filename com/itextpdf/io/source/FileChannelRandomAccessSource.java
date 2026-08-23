/*     */ package com.itextpdf.io.source;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.nio.channels.FileChannel;
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
/*     */ public class FileChannelRandomAccessSource
/*     */   implements IRandomAccessSource
/*     */ {
/*     */   private final FileChannel channel;
/*     */   private final MappedChannelRandomAccessSource source;
/*     */   
/*     */   public FileChannelRandomAccessSource(FileChannel channel) throws IOException {
/*  74 */     this.channel = channel;
/*  75 */     if (channel.size() == 0L)
/*  76 */       throw new IOException("File size is 0 bytes"); 
/*  77 */     this.source = new MappedChannelRandomAccessSource(channel, 0L, channel.size());
/*  78 */     this.source.open();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void close() throws IOException {
/*     */     try {
/*  88 */       this.source.close();
/*     */     } finally {
/*     */       try {
/*  91 */         this.channel.close();
/*  92 */       } catch (Exception ex) {
/*  93 */         Logger logger = LoggerFactory.getLogger(FileChannelRandomAccessSource.class);
/*  94 */         logger.error("Closing of the file channel this source is based on failed.", ex);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int get(long position) throws IOException {
/* 103 */     return this.source.get(position);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int get(long position, byte[] bytes, int off, int len) throws IOException {
/* 110 */     return this.source.get(position, bytes, off, len);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long length() {
/* 117 */     return this.source.length();
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/io/source/FileChannelRandomAccessSource.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */