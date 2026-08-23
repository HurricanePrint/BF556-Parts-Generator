/*    */ package com.itextpdf.signatures;
/*    */ 
/*    */ import com.itextpdf.kernel.PdfException;
/*    */ import java.io.File;
/*    */ import java.io.FileInputStream;
/*    */ import java.security.KeyStore;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class KeyStoreUtil
/*    */ {
/*    */   public static KeyStore loadCacertsKeyStore(String provider) {
/* 62 */     File file = new File(System.getProperty("java.home"), "lib");
/* 63 */     file = new File(file, "security");
/* 64 */     file = new File(file, "cacerts");
/* 65 */     FileInputStream fin = null; try {
/*    */       KeyStore k;
/* 67 */       fin = new FileInputStream(file);
/*    */       
/* 69 */       if (provider == null) {
/* 70 */         k = KeyStore.getInstance("JKS");
/*    */       } else {
/* 72 */         k = KeyStore.getInstance("JKS", provider);
/* 73 */       }  k.load(fin, null);
/* 74 */       return k;
/*    */     }
/* 76 */     catch (Exception e) {
/* 77 */       throw new PdfException(e);
/*    */     } finally {
/*    */       
/* 80 */       try { if (fin != null) fin.close();  } catch (Exception exception) {}
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static KeyStore loadCacertsKeyStore() {
/* 90 */     return loadCacertsKeyStore(null);
/*    */   }
/*    */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/signatures/KeyStoreUtil.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */