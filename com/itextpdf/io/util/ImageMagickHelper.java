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
/*     */ public class ImageMagickHelper
/*     */ {
/*     */   public static final String MAGICK_COMPARE_ENVIRONMENT_VARIABLE = "ITEXT_MAGICK_COMPARE_EXEC";
/*     */   @Deprecated
/*     */   static final String MAGICK_COMPARE_ENVIRONMENT_VARIABLE_LEGACY = "compareExec";
/*     */   static final String MAGICK_COMPARE_KEYWORD = "ImageMagick Studio LLC";
/*     */   private String compareExec;
/*     */   
/*     */   public ImageMagickHelper() {
/*  64 */     this(null);
/*     */   }
/*     */   
/*     */   public ImageMagickHelper(String newCompareExec) {
/*  68 */     this.compareExec = newCompareExec;
/*  69 */     if (this.compareExec == null) {
/*  70 */       this.compareExec = SystemUtil.getPropertyOrEnvironmentVariable("ITEXT_MAGICK_COMPARE_EXEC");
/*  71 */       if (this.compareExec == null) {
/*  72 */         this.compareExec = SystemUtil.getPropertyOrEnvironmentVariable("compareExec");
/*     */       }
/*     */     } 
/*     */     
/*  76 */     if (!CliCommandUtil.isVersionCommandExecutable(this.compareExec, "ImageMagick Studio LLC")) {
/*  77 */       throw new IllegalArgumentException("ImageMagick comparison command specified incorrectly. Set the ITEXT_MAGICK_COMPARE_EXEC environment variable with the CLI command which can run the ImageMagic comparison. See BUILDING.MD in the root of the repository for more details.");
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
/*  89 */     return this.compareExec;
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
/*     */   public boolean runImageMagickImageCompare(String outImageFilePath, String cmpImageFilePath, String diffImageName) throws IOException, InterruptedException {
/* 104 */     return runImageMagickImageCompare(outImageFilePath, cmpImageFilePath, diffImageName, null);
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
/*     */   
/*     */   public boolean runImageMagickImageCompare(String outImageFilePath, String cmpImageFilePath, String diffImageName, String fuzzValue) throws IOException, InterruptedException {
/* 122 */     fuzzValue = (fuzzValue == null) ? "" : " -metric AE -fuzz <fuzzValue>%".replace("<fuzzValue>", fuzzValue);
/*     */     
/* 124 */     StringBuilder currCompareParams = new StringBuilder();
/* 125 */     currCompareParams
/* 126 */       .append(fuzzValue).append(" '")
/* 127 */       .append(outImageFilePath).append("' '")
/* 128 */       .append(cmpImageFilePath).append("' '")
/* 129 */       .append(diffImageName).append("'");
/* 130 */     return SystemUtil.runProcessAndWait(this.compareExec, currCompareParams.toString());
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/io/util/ImageMagickHelper.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */