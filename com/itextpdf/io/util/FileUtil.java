/*     */ package com.itextpdf.io.util;
/*     */ 
/*     */ import java.io.BufferedOutputStream;
/*     */ import java.io.File;
/*     */ import java.io.FileFilter;
/*     */ import java.io.FileNotFoundException;
/*     */ import java.io.FileOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ import java.io.OutputStreamWriter;
/*     */ import java.io.PrintWriter;
/*     */ import java.io.RandomAccessFile;
/*     */ import java.io.UnsupportedEncodingException;
/*     */ import java.net.MalformedURLException;
/*     */ import java.net.URISyntaxException;
/*     */ import java.net.URL;
/*     */ import java.nio.file.Files;
/*     */ import java.nio.file.Paths;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.Comparator;
/*     */ import java.util.List;
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
/*     */ public final class FileUtil
/*     */ {
/*     */   public static String getFontsDir() {
/*     */     try {
/*  83 */       String winDir = System.getenv("windir");
/*  84 */       String fileSeparator = System.getProperty("file.separator");
/*  85 */       return winDir + fileSeparator + "fonts";
/*  86 */     } catch (SecurityException e) {
/*  87 */       LoggerFactory.getLogger(FileUtil.class)
/*  88 */         .warn("Can't access System.getenv(\"windir\") to load fonts. Please, add RuntimePermission for getenv.windir.");
/*  89 */       return null;
/*     */     } 
/*     */   }
/*     */   
/*     */   public static boolean fileExists(String path) {
/*  94 */     if (path != null) {
/*  95 */       File f = new File(path);
/*  96 */       return (f.exists() && f.isFile());
/*     */     } 
/*  98 */     return false;
/*     */   }
/*     */   
/*     */   public static boolean directoryExists(String path) {
/* 102 */     if (path != null) {
/* 103 */       File f = new File(path);
/* 104 */       return (f.exists() && f.isDirectory());
/*     */     } 
/* 106 */     return false;
/*     */   }
/*     */   
/*     */   public static String[] listFilesInDirectory(String path, boolean recursive) {
/* 110 */     if (path != null) {
/* 111 */       File root = new File(path);
/* 112 */       if (root.exists() && root.isDirectory()) {
/* 113 */         File[] files = root.listFiles();
/* 114 */         if (files != null) {
/*     */           
/* 116 */           Arrays.sort(files, new CaseSensitiveFileComparator());
/* 117 */           List<String> list = new ArrayList<>();
/* 118 */           for (File file : files) {
/* 119 */             if (file.isDirectory() && recursive) {
/* 120 */               listAllFiles(file.getAbsolutePath(), list);
/*     */             } else {
/* 122 */               list.add(file.getAbsolutePath());
/*     */             } 
/*     */           } 
/* 125 */           return list.<String>toArray(new String[list.size()]);
/*     */         } 
/*     */       } 
/*     */     } 
/* 129 */     return null;
/*     */   }
/*     */   
/*     */   public static File[] listFilesInDirectoryByFilter(String outPath, FileFilter fileFilter) {
/* 133 */     File[] result = null;
/* 134 */     if (outPath != null && !outPath.isEmpty()) {
/* 135 */       result = (new File(outPath)).listFiles(fileFilter);
/*     */     }
/* 137 */     if (result != null)
/*     */     {
/* 139 */       Arrays.sort(result, new CaseSensitiveFileComparator());
/*     */     }
/* 141 */     return result;
/*     */   }
/*     */   
/*     */   private static void listAllFiles(String dir, List<String> list) {
/* 145 */     File[] files = (new File(dir)).listFiles();
/* 146 */     if (files != null) {
/*     */       
/* 148 */       Arrays.sort(files, new CaseSensitiveFileComparator());
/* 149 */       for (File file : files) {
/* 150 */         if (file.isDirectory()) {
/* 151 */           listAllFiles(file.getAbsolutePath(), list);
/*     */         } else {
/* 153 */           list.add(file.getAbsolutePath());
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public static PrintWriter createPrintWriter(OutputStream output, String encoding) throws UnsupportedEncodingException {
/* 160 */     return new PrintWriter(new OutputStreamWriter(output, encoding));
/*     */   }
/*     */   
/*     */   public static OutputStream getBufferedOutputStream(String filename) throws FileNotFoundException {
/* 164 */     return new BufferedOutputStream(new FileOutputStream(filename));
/*     */   }
/*     */   
/*     */   public static OutputStream wrapWithBufferedOutputStream(OutputStream outputStream) {
/* 168 */     if (outputStream instanceof java.io.ByteArrayOutputStream || outputStream instanceof BufferedOutputStream) {
/* 169 */       return outputStream;
/*     */     }
/* 171 */     return new BufferedOutputStream(outputStream);
/*     */   }
/*     */ 
/*     */   
/*     */   public static File createTempFile(String path) throws IOException {
/* 176 */     File tempFile = new File(path);
/* 177 */     if (tempFile.isDirectory()) {
/* 178 */       tempFile = File.createTempFile("pdf", null, tempFile);
/*     */     }
/* 180 */     return tempFile;
/*     */   }
/*     */   
/*     */   public static FileOutputStream getFileOutputStream(File tempFile) throws FileNotFoundException {
/* 184 */     return new FileOutputStream(tempFile);
/*     */   }
/*     */   
/*     */   public static InputStream getInputStreamForFile(String path) throws IOException {
/* 188 */     return Files.newInputStream(Paths.get(path, new String[0]), new java.nio.file.OpenOption[0]);
/*     */   }
/*     */   
/*     */   public static RandomAccessFile getRandomAccessFile(File tempFile) throws FileNotFoundException {
/* 192 */     return new RandomAccessFile(tempFile, "rw");
/*     */   }
/*     */   
/*     */   public static void createDirectories(String outPath) {
/* 196 */     (new File(outPath)).mkdirs();
/*     */   }
/*     */   
/*     */   @Deprecated
/*     */   public static String getParentDirectory(String file) {
/* 201 */     return (new File(file)).getParent();
/*     */   }
/*     */   
/*     */   public static String getParentDirectory(File file) throws MalformedURLException {
/* 205 */     return (file != null) ? Paths.get(file.getParent(), new String[0]).toUri().toURL().toExternalForm() : "";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean deleteFile(File file) {
/* 213 */     return file.delete();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String parentDirectory(URL url) throws URISyntaxException {
/* 223 */     return url.toURI().resolve(".").toString();
/*     */   }
/*     */   
/*     */   private static class CaseSensitiveFileComparator
/*     */     implements Comparator<File> {
/*     */     public int compare(File f1, File f2) {
/* 229 */       return f1.getPath().compareTo(f2.getPath());
/*     */     }
/*     */     
/*     */     private CaseSensitiveFileComparator() {}
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/io/util/FileUtil.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */