/*    */ package com.itextpdf.styledxmlparser.jsoup;
/*    */ 
/*    */ import java.io.File;
/*    */ import java.io.FileNotFoundException;
/*    */ import java.io.RandomAccessFile;
/*    */ import java.nio.charset.Charset;
/*    */ import java.util.regex.Matcher;
/*    */ import java.util.regex.Pattern;
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
/*    */ public class PortUtil
/*    */ {
/*    */   public static final String escapedSingleBracket = "''";
/*    */   public static final String signedNumberFormat = ",number,+#;-#";
/*    */   
/*    */   public static boolean hasMatch(Pattern pattern, String input) {
/* 58 */     return pattern.matcher(input).find();
/*    */   }
/*    */   
/*    */   public static boolean charsetIsSupported(String charsetName) {
/*    */     try {
/* 63 */       return Charset.isSupported(charsetName);
/* 64 */     } catch (IllegalArgumentException e) {
/* 65 */       return false;
/*    */     } 
/*    */   }
/*    */   
/*    */   public static RandomAccessFile getReadOnlyRandomAccesFile(File file) throws FileNotFoundException {
/* 70 */     return new RandomAccessFile(file, "r");
/*    */   }
/*    */   
/*    */   public static boolean isSuccessful(Matcher m) {
/* 74 */     return m.find();
/*    */   }
/*    */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/styledxmlparser/jsoup/PortUtil.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */