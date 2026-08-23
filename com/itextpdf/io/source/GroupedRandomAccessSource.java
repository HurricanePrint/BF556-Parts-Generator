/*     */ package com.itextpdf.io.source;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.Serializable;
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
/*     */ class GroupedRandomAccessSource
/*     */   implements IRandomAccessSource, Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 3417070797788862099L;
/*     */   private final SourceEntry[] sources;
/*     */   private SourceEntry currentSourceEntry;
/*     */   private final long size;
/*     */   
/*     */   public GroupedRandomAccessSource(IRandomAccessSource[] sources) throws IOException {
/*  80 */     this.sources = new SourceEntry[sources.length];
/*     */     
/*  82 */     long totalSize = 0L;
/*  83 */     for (int i = 0; i < sources.length; i++) {
/*  84 */       this.sources[i] = new SourceEntry(i, sources[i], totalSize);
/*  85 */       totalSize += sources[i].length();
/*     */     } 
/*  87 */     this.size = totalSize;
/*  88 */     this.currentSourceEntry = this.sources[sources.length - 1];
/*  89 */     sourceInUse(this.currentSourceEntry.source);
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
/*     */   protected int getStartingSourceIndex(long offset) {
/* 103 */     if (offset >= this.currentSourceEntry.firstByte) {
/* 104 */       return this.currentSourceEntry.index;
/*     */     }
/* 106 */     return 0;
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
/*     */   private SourceEntry getSourceEntryForOffset(long offset) throws IOException {
/* 118 */     if (offset >= this.size) {
/* 119 */       return null;
/*     */     }
/* 121 */     if (offset >= this.currentSourceEntry.firstByte && offset <= this.currentSourceEntry.lastByte) {
/* 122 */       return this.currentSourceEntry;
/*     */     }
/*     */     
/* 125 */     sourceReleased(this.currentSourceEntry.source);
/*     */     
/* 127 */     int startAt = getStartingSourceIndex(offset);
/* 128 */     for (int i = startAt; i < this.sources.length; i++) {
/* 129 */       if (offset >= (this.sources[i]).firstByte && offset <= (this.sources[i]).lastByte) {
/* 130 */         this.currentSourceEntry = this.sources[i];
/* 131 */         sourceInUse(this.currentSourceEntry.source);
/* 132 */         return this.currentSourceEntry;
/*     */       } 
/*     */     } 
/* 135 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void sourceReleased(IRandomAccessSource source) throws IOException {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void sourceInUse(IRandomAccessSource source) throws IOException {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int get(long position) throws IOException {
/* 162 */     SourceEntry entry = getSourceEntryForOffset(position);
/*     */ 
/*     */     
/* 165 */     if (entry == null) {
/* 166 */       return -1;
/*     */     }
/* 168 */     return entry.source.get(entry.offsetN(position));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int get(long position, byte[] bytes, int off, int len) throws IOException {
/* 175 */     SourceEntry entry = getSourceEntryForOffset(position);
/*     */ 
/*     */     
/* 178 */     if (entry == null) {
/* 179 */       return -1;
/*     */     }
/* 181 */     long offN = entry.offsetN(position);
/*     */     
/* 183 */     int remaining = len;
/*     */     
/* 185 */     while (remaining > 0) {
/*     */ 
/*     */       
/* 188 */       if (entry == null)
/*     */         break; 
/* 190 */       if (offN > entry.source.length()) {
/*     */         break;
/*     */       }
/* 193 */       int count = entry.source.get(offN, bytes, off, remaining);
/* 194 */       if (count == -1) {
/*     */         break;
/*     */       }
/* 197 */       off += count;
/* 198 */       position += count;
/* 199 */       remaining -= count;
/*     */       
/* 201 */       offN = 0L;
/* 202 */       entry = getSourceEntryForOffset(position);
/*     */     } 
/* 204 */     return (remaining == len) ? -1 : (len - remaining);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long length() {
/* 212 */     return this.size;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void close() throws IOException {
/* 221 */     IOException firstThrownIOExc = null;
/* 222 */     for (SourceEntry entry : this.sources) {
/*     */       try {
/* 224 */         entry.source.close();
/* 225 */       } catch (IOException ex) {
/* 226 */         if (firstThrownIOExc == null) {
/* 227 */           firstThrownIOExc = ex;
/*     */         } else {
/* 229 */           Logger logger = LoggerFactory.getLogger(GroupedRandomAccessSource.class);
/* 230 */           logger.error("Closing of one of the grouped sources failed.", ex);
/*     */         } 
/* 232 */       } catch (Exception ex) {
/* 233 */         Logger logger = LoggerFactory.getLogger(GroupedRandomAccessSource.class);
/* 234 */         logger.error("Closing of one of the grouped sources failed.", ex);
/*     */       } 
/*     */     } 
/* 237 */     if (firstThrownIOExc != null) {
/* 238 */       throw firstThrownIOExc;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static class SourceEntry
/*     */     implements Serializable
/*     */   {
/*     */     private static final long serialVersionUID = 924305549309252826L;
/*     */ 
/*     */ 
/*     */     
/*     */     final IRandomAccessSource source;
/*     */ 
/*     */ 
/*     */     
/*     */     final long firstByte;
/*     */ 
/*     */ 
/*     */     
/*     */     final long lastByte;
/*     */ 
/*     */ 
/*     */     
/*     */     final int index;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public SourceEntry(int index, IRandomAccessSource source, long offset) {
/* 271 */       this.index = index;
/* 272 */       this.source = source;
/* 273 */       this.firstByte = offset;
/* 274 */       this.lastByte = offset + source.length() - 1L;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public long offsetN(long absoluteOffset) {
/* 283 */       return absoluteOffset - this.firstByte;
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/io/source/GroupedRandomAccessSource.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */