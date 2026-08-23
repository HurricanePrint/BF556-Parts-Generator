/*    */ package com.itextpdf.io.util;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class CliCommandUtil
/*    */ {
/*    */   public static boolean isVersionCommandExecutable(String command, String versionText) {
/* 61 */     if (command == null || versionText == null) {
/* 62 */       return false;
/*    */     }
/*    */     
/*    */     try {
/* 66 */       String result = SystemUtil.runProcessAndGetOutput(command, "-version");
/* 67 */       return result.contains(versionText);
/* 68 */     } catch (Exception e) {
/* 69 */       return false;
/*    */     } 
/*    */   }
/*    */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/io/util/CliCommandUtil.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */