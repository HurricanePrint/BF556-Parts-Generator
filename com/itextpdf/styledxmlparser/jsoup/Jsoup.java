/*     */ package com.itextpdf.styledxmlparser.jsoup;
/*     */ 
/*     */ import com.itextpdf.styledxmlparser.jsoup.helper.DataUtil;
/*     */ import com.itextpdf.styledxmlparser.jsoup.nodes.Document;
/*     */ import com.itextpdf.styledxmlparser.jsoup.parser.Parser;
/*     */ import com.itextpdf.styledxmlparser.jsoup.safety.Cleaner;
/*     */ import com.itextpdf.styledxmlparser.jsoup.safety.Whitelist;
/*     */ import java.io.File;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Jsoup
/*     */ {
/*     */   public static Document parse(String html, String baseUri) {
/*  71 */     return Parser.parse(html, baseUri);
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
/*     */   public static Document parse(String html, String baseUri, Parser parser) {
/*  86 */     return parser.parseInput(html, baseUri);
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
/*     */   public static Document parse(String html) {
/*  99 */     return Parser.parse(html, "");
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
/*     */   public static Document parseXML(String xml, String baseUri) {
/* 111 */     return Parser.parseXml(xml, baseUri);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Document parseXML(String xml) {
/* 121 */     return Parser.parseXml(xml, "");
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
/*     */   public static Document parseXML(InputStream in, String charsetName, String baseUri) throws IOException {
/* 135 */     return parse(in, charsetName, baseUri, Parser.xmlParser());
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
/*     */   public static Document parseXML(InputStream in, String charsetName) throws IOException {
/* 148 */     return parseXML(in, charsetName, "");
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
/*     */   public static Document parse(File in, String charsetName, String baseUri) throws IOException {
/* 163 */     return DataUtil.load(in, charsetName, baseUri);
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
/*     */   public static Document parse(File in, String charsetName) throws IOException {
/* 178 */     return DataUtil.load(in, charsetName, in.getAbsolutePath());
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
/*     */   public static Document parse(InputStream in, String charsetName, String baseUri) throws IOException {
/* 193 */     return DataUtil.load(in, charsetName, baseUri);
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
/*     */   
/*     */   public static Document parse(InputStream in, String charsetName, String baseUri, Parser parser) throws IOException {
/* 210 */     return DataUtil.load(in, charsetName, baseUri, parser);
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
/*     */   public static Document parseBodyFragment(String bodyHtml, String baseUri) {
/* 223 */     return Parser.parseBodyFragment(bodyHtml, baseUri);
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
/*     */   public static Document parseBodyFragment(String bodyHtml) {
/* 235 */     return Parser.parseBodyFragment(bodyHtml, "");
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
/*     */   public static String clean(String bodyHtml, String baseUri, Whitelist whitelist) {
/* 250 */     Document dirty = parseBodyFragment(bodyHtml, baseUri);
/* 251 */     Cleaner cleaner = new Cleaner(whitelist);
/* 252 */     Document clean = cleaner.clean(dirty);
/* 253 */     return clean.body().html();
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
/*     */   public static String clean(String bodyHtml, Whitelist whitelist) {
/* 267 */     return clean(bodyHtml, "", whitelist);
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
/*     */   public static String clean(String bodyHtml, String baseUri, Whitelist whitelist, Document.OutputSettings outputSettings) {
/* 283 */     Document dirty = parseBodyFragment(bodyHtml, baseUri);
/* 284 */     Cleaner cleaner = new Cleaner(whitelist);
/* 285 */     Document clean = cleaner.clean(dirty);
/* 286 */     clean.outputSettings(outputSettings);
/* 287 */     return clean.body().html();
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
/*     */   public static boolean isValid(String bodyHtml, Whitelist whitelist) {
/* 299 */     Document dirty = parseBodyFragment(bodyHtml, "");
/* 300 */     Cleaner cleaner = new Cleaner(whitelist);
/* 301 */     return cleaner.isValid(dirty);
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/styledxmlparser/jsoup/Jsoup.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */