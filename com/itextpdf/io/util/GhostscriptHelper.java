/*     */ package com.itextpdf.io.util;
/*     */ 
/*     */ import java.io.IOException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class GhostscriptHelper
/*     */ {
/*     */   public static final String GHOSTSCRIPT_ENVIRONMENT_VARIABLE = "ITEXT_GS_EXEC";
/*     */   @Deprecated
/*     */   static final String GHOSTSCRIPT_ENVIRONMENT_VARIABLE_LEGACY = "gsExec";
/*     */   static final String GHOSTSCRIPT_KEYWORD = "GPL Ghostscript";
/*     */   private static final String GHOSTSCRIPT_PARAMS = " -dSAFER -dNOPAUSE -dBATCH -sDEVICE=png16m -r150 {0} -sOutputFile=\"{1}\" \"{2}\"";
/*     */   private String gsExec;
/*     */   
/*     */   public GhostscriptHelper() {
/*  66 */     this(null);
/*     */   }
/*     */   
/*     */   public GhostscriptHelper(String newGsExec) {
/*  70 */     this.gsExec = newGsExec;
/*  71 */     if (this.gsExec == null) {
/*  72 */       this.gsExec = SystemUtil.getPropertyOrEnvironmentVariable("ITEXT_GS_EXEC");
/*     */       
/*  74 */       if (this.gsExec == null) {
/*  75 */         this.gsExec = SystemUtil.getPropertyOrEnvironmentVariable("gsExec");
/*     */       }
/*     */     } 
/*     */     
/*  79 */     if (!CliCommandUtil.isVersionCommandExecutable(this.gsExec, "GPL Ghostscript")) {
/*  80 */       throw new IllegalArgumentException("Ghostscript command is not specified or specified incorrectly. Set the ITEXT_GS_EXEC environment variable to a CLI command that can run the Ghostscript application. See BUILDING.MD in the root of the repository for more details.");
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
/*     */   public String getCliExecutionCommand() {
/*  92 */     return this.gsExec;
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
/*     */   public void runGhostScriptImageGeneration(String pdf, String outDir, String image) throws IOException, InterruptedException {
/* 105 */     runGhostScriptImageGeneration(pdf, outDir, image, null);
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
/*     */   public void runGhostScriptImageGeneration(String pdf, String outDir, String image, String pageList) throws IOException, InterruptedException {
/* 122 */     if (!FileUtil.directoryExists(outDir)) {
/* 123 */       throw new IllegalArgumentException("Cannot open output directory for <filename>".replace("<filename>", pdf));
/*     */     }
/*     */     
/* 126 */     pageList = (pageList == null) ? "" : "-sPageList=<pagelist>".replace("<pagelist>", pageList);
/*     */     
/* 128 */     String currGsParams = MessageFormatUtil.format(" -dSAFER -dNOPAUSE -dBATCH -sDEVICE=png16m -r150 {0} -sOutputFile=\"{1}\" \"{2}\"", new Object[] { pageList, outDir + image, pdf });
/* 129 */     if (!SystemUtil.runProcessAndWait(this.gsExec, currGsParams)) {
/* 130 */       throw new GhostscriptExecutionException("GhostScript failed for <filename>".replace("<filename>", pdf));
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
/*     */   public static class GhostscriptExecutionException
/*     */     extends RuntimeException
/*     */   {
/*     */     public GhostscriptExecutionException(String msg) {
/* 145 */       super(msg);
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/io/util/GhostscriptHelper.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */