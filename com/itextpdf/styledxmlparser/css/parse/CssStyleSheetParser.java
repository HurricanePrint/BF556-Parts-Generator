/*     */ package com.itextpdf.styledxmlparser.css.parse;
/*     */ 
/*     */ import com.itextpdf.styledxmlparser.PortUtil;
/*     */ import com.itextpdf.styledxmlparser.css.CssStyleSheet;
/*     */ import com.itextpdf.styledxmlparser.css.parse.syntax.CssParserStateController;
/*     */ import java.io.ByteArrayInputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.InputStreamReader;
/*     */ import java.io.Reader;
/*     */ import java.nio.charset.StandardCharsets;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class CssStyleSheetParser
/*     */ {
/*     */   public static CssStyleSheet parse(InputStream stream, String baseUrl) throws IOException {
/*  78 */     CssParserStateController controller = new CssParserStateController(baseUrl);
/*  79 */     Reader br = PortUtil.wrapInBufferedReader(new InputStreamReader(stream, StandardCharsets.UTF_8));
/*  80 */     char[] buffer = new char[8192];
/*     */     int length;
/*  82 */     while ((length = br.read(buffer, 0, buffer.length)) > 0) {
/*  83 */       for (int i = 0; i < length; i++) {
/*  84 */         controller.process(buffer[i]);
/*     */       }
/*     */     } 
/*  87 */     return controller.getParsingResult();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static CssStyleSheet parse(InputStream stream) throws IOException {
/*  98 */     return parse(stream, (String)null);
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
/*     */   public static CssStyleSheet parse(String data, String baseUrl) {
/* 110 */     ByteArrayInputStream stream = new ByteArrayInputStream(data.getBytes(StandardCharsets.UTF_8));
/*     */     try {
/* 112 */       return parse(stream, baseUrl);
/* 113 */     } catch (IOException exc) {
/* 114 */       return null;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static CssStyleSheet parse(String data) {
/* 125 */     return parse(data, (String)null);
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/styledxmlparser/css/parse/CssStyleSheetParser.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */