/*     */ package com.itextpdf.styledxmlparser.resolver.resource;
/*     */ 
/*     */ import com.itextpdf.io.util.MessageFormatUtil;
/*     */ import com.itextpdf.io.util.StreamUtil;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.net.URL;
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
/*     */ public class DefaultResourceRetriever
/*     */   implements IResourceRetriever
/*     */ {
/*  42 */   private static final Logger logger = LoggerFactory.getLogger(DefaultResourceRetriever.class);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  51 */   private long resourceSizeByteLimit = Long.MAX_VALUE;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long getResourceSizeByteLimit() {
/*  62 */     return this.resourceSizeByteLimit;
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
/*     */   public IResourceRetriever setResourceSizeByteLimit(long resourceSizeByteLimit) {
/*  74 */     this.resourceSizeByteLimit = resourceSizeByteLimit;
/*  75 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public InputStream getInputStreamByUrl(URL url) throws IOException {
/*  86 */     if (!urlFilter(url)) {
/*  87 */       logger.warn(MessageFormatUtil.format("Resource with given URL ({0}) was filtered out.", new Object[] { url }));
/*  88 */       return null;
/*     */     } 
/*  90 */     return new LimitedInputStream(url.openStream(), this.resourceSizeByteLimit);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public byte[] getByteArrayByUrl(URL url) throws IOException {
/* 101 */     try (InputStream stream = getInputStreamByUrl(url)) {
/* 102 */       if (stream == null) {
/* 103 */         return null;
/*     */       }
/*     */       
/* 106 */       return StreamUtil.inputStreamToArray(stream);
/* 107 */     } catch (ReadingByteLimitException ex) {
/* 108 */       logger.warn(MessageFormatUtil.format("Unable to retrieve resource with given URL ({0}) and resource size byte limit ({1}).", new Object[] { url, 
/* 109 */               Long.valueOf(this.resourceSizeByteLimit) }));
/*     */       
/* 111 */       return null;
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
/*     */   protected boolean urlFilter(URL url) {
/* 123 */     return true;
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/styledxmlparser/resolver/resource/DefaultResourceRetriever.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */