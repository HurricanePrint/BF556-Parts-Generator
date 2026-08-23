/*     */ package com.itextpdf.test;
/*     */ 
/*     */ import java.io.ByteArrayOutputStream;
/*     */ import java.io.File;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.IOException;
/*     */ import java.lang.reflect.Field;
/*     */ import java.util.concurrent.TimeUnit;
/*     */ import org.junit.Rule;
/*     */ import org.junit.rules.Timeout;
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
/*     */ public abstract class ITextTest
/*     */ {
/*  66 */   protected final Logger LOGGER = LoggerFactory.getLogger(getClass());
/*     */   
/*     */   @Rule
/*  69 */   public Timeout testTimeout = getTestTimeout();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void createDestinationFolder(String path) {
/*  77 */     File fpath = new File(path);
/*  78 */     fpath.mkdirs();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void createOrClearDestinationFolder(String path) {
/*  87 */     File fpath = new File(path);
/*  88 */     fpath.mkdirs();
/*  89 */     deleteDirectoryContents(path, false);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void deleteDirectory(String path) {
/*  97 */     deleteDirectoryContents(path, true);
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
/*     */   public static void removeCryptographyRestrictions() {
/*     */     try {
/* 115 */       Field field = Class.forName("javax.crypto.JceSecurity").getDeclaredField("isRestricted");
/* 116 */       if (field.isAccessible()) {
/*     */         return;
/*     */       }
/*     */ 
/*     */       
/* 121 */       Field modifiersField = Field.class.getDeclaredField("modifiers");
/* 122 */       modifiersField.setAccessible(true);
/* 123 */       modifiersField.setInt(field, field.getModifiers() & 0xFFFFFFEF);
/* 124 */       modifiersField.setAccessible(false);
/*     */       
/* 126 */       field.setAccessible(true);
/* 127 */       if (field.getBoolean(null)) {
/* 128 */         field.set(null, Boolean.FALSE);
/*     */       } else {
/* 130 */         field.setAccessible(false);
/*     */       } 
/* 132 */     } catch (Exception ex) {
/* 133 */       ex.printStackTrace();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void restoreCryptographyRestrictions() {
/*     */     try {
/* 144 */       Field field = Class.forName("javax.crypto.JceSecurity").getDeclaredField("isRestricted");
/* 145 */       if (field.isAccessible()) {
/* 146 */         field.set(null, Boolean.TRUE);
/* 147 */         field.setAccessible(false);
/*     */       } 
/* 149 */     } catch (Exception ex) {
/* 150 */       ex.printStackTrace();
/*     */     } 
/*     */   }
/*     */   
/*     */   public static void printOutCmpPdfNameAndDir(String out, String cmp) {
/* 155 */     printPathToConsole(out, "Out pdf: ");
/* 156 */     printPathToConsole(cmp, "Cmp pdf: ");
/* 157 */     System.out.println();
/* 158 */     printPathToConsole((new File(out)).getParent(), "Out file folder: ");
/* 159 */     printPathToConsole((new File(cmp)).getParent(), "Cmp file folder: ");
/*     */   }
/*     */   
/*     */   public static void printOutputPdfNameAndDir(String pdfName) {
/* 163 */     printPathToConsole(pdfName, "Output PDF: ");
/* 164 */     printPathToConsole((new File(pdfName)).getParent(), "Output PDF folder: ");
/*     */   }
/*     */   
/*     */   public static void printPathToConsole(String path, String comment) {
/* 168 */     System.out.println(comment + "file://" + (new File(path)).toURI().normalize().getPath());
/*     */   }
/*     */   
/*     */   protected Timeout getTestTimeout() {
/* 172 */     return new Timeout(5L, TimeUnit.MINUTES);
/*     */   }
/*     */   
/*     */   protected byte[] readFile(String filename) throws IOException {
/* 176 */     FileInputStream input = new FileInputStream(filename);
/* 177 */     ByteArrayOutputStream output = new ByteArrayOutputStream();
/* 178 */     byte[] buffer = new byte[8192];
/*     */     int read;
/* 180 */     while ((read = input.read(buffer)) != -1) {
/* 181 */       output.write(buffer, 0, read);
/*     */     }
/* 183 */     input.close();
/* 184 */     return output.toByteArray();
/*     */   }
/*     */   
/*     */   protected String createStringByEscaped(byte[] bytes) {
/* 188 */     String[] chars = (new String(bytes)).substring(1).split("#");
/* 189 */     StringBuilder buf = new StringBuilder(chars.length);
/* 190 */     for (String ch : chars) {
/* 191 */       if (ch.length() != 0) {
/* 192 */         Integer b = Integer.valueOf(Integer.parseInt(ch, 16));
/* 193 */         buf.append((char)b.intValue());
/*     */       } 
/* 195 */     }  return buf.toString();
/*     */   }
/*     */   
/*     */   private static void deleteDirectoryContents(String path, boolean removeParentDirectory) {
/* 199 */     File file = new File(path);
/* 200 */     if (file.exists() && file.listFiles() != null) {
/* 201 */       for (File f : file.listFiles()) {
/* 202 */         if (f.isDirectory()) {
/* 203 */           deleteDirectoryContents(f.getPath(), false);
/* 204 */           f.delete();
/*     */         } else {
/* 206 */           f.delete();
/*     */         } 
/*     */       } 
/* 209 */       if (removeParentDirectory)
/* 210 */         file.delete(); 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/test/ITextTest.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */