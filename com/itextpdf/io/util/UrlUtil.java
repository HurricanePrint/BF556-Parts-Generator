/*     */ package com.itextpdf.io.util;
/*     */ 
/*     */ import java.io.File;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.net.MalformedURLException;
/*     */ import java.net.URI;
/*     */ import java.net.URL;
/*     */ import java.net.URLConnection;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class UrlUtil
/*     */ {
/*     */   public static URL toURL(String filename) throws MalformedURLException {
/*     */     URL url;
/*     */     try {
/*  75 */       url = new URL(filename);
/*  76 */     } catch (MalformedURLException e) {
/*  77 */       url = (new File(filename)).toURI().toURL();
/*     */     } 
/*  79 */     return url;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static URI toNormalizedURI(String filename) {
/*  88 */     return toNormalizedURI(new File(filename));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static URI toNormalizedURI(File file) {
/*  97 */     return file.toURI().normalize();
/*     */   }
/*     */   
/*     */   public static InputStream openStream(URL url) throws IOException {
/* 101 */     return url.openStream();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static URL getFinalURL(URL initialUrl) throws IOException {
/* 111 */     URL finalUrl = null;
/* 112 */     URL nextUrl = initialUrl;
/* 113 */     while (nextUrl != null) {
/* 114 */       finalUrl = nextUrl;
/* 115 */       URLConnection connection = finalUrl.openConnection();
/* 116 */       String location = connection.getHeaderField("location");
/*     */       
/* 118 */       connection.getInputStream().close();
/* 119 */       nextUrl = (location != null) ? new URL(location) : null;
/*     */     } 
/* 121 */     return finalUrl;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String getFileUriString(String filename) throws MalformedURLException {
/* 130 */     return (new File(filename)).toURI().toURL().toExternalForm();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String getNormalizedFileUriString(String filename) {
/* 139 */     return "file://" + toNormalizedURI(filename).getPath();
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/io/util/UrlUtil.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */