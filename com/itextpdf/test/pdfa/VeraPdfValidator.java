/*     */ package com.itextpdf.test.pdfa;
/*     */ 
/*     */ import java.io.File;
/*     */ import java.io.FileOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.util.Collections;
/*     */ import java.util.EnumSet;
/*     */ import org.verapdf.features.FeatureExtractorConfig;
/*     */ import org.verapdf.metadata.fixer.MetadataFixerConfig;
/*     */ import org.verapdf.pdfa.VeraGreenfieldFoundryProvider;
/*     */ import org.verapdf.pdfa.validation.validators.ValidatorConfig;
/*     */ import org.verapdf.processor.BatchProcessor;
/*     */ import org.verapdf.processor.FormatOption;
/*     */ import org.verapdf.processor.ProcessorConfig;
/*     */ import org.verapdf.processor.ProcessorFactory;
/*     */ import org.verapdf.processor.TaskType;
/*     */ import org.verapdf.processor.plugins.PluginsCollectionConfig;
/*     */ import org.verapdf.processor.reports.BatchSummary;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class VeraPdfValidator
/*     */ {
/*     */   public String validate(String filePath) {
/*  68 */     String errorMessage = null;
/*     */     
/*     */     try {
/*  71 */       File xmlReport = new File(filePath.substring(0, filePath.length() - ".pdf".length()) + ".xml");
/*  72 */       VeraGreenfieldFoundryProvider.initialise();
/*     */ 
/*     */       
/*  75 */       ProcessorConfig customProfile = ProcessorFactory.defaultConfig();
/*  76 */       FeatureExtractorConfig featuresConfig = customProfile.getFeatureConfig();
/*  77 */       ValidatorConfig valConfig = customProfile.getValidatorConfig();
/*  78 */       PluginsCollectionConfig plugConfig = customProfile.getPluginsCollectionConfig();
/*  79 */       MetadataFixerConfig metaConfig = customProfile.getFixerConfig();
/*  80 */       ProcessorConfig resultConfig = ProcessorFactory.fromValues(valConfig, featuresConfig, plugConfig, metaConfig, 
/*  81 */           EnumSet.of(TaskType.VALIDATE));
/*     */ 
/*     */       
/*  84 */       BatchProcessor processor = ProcessorFactory.fileBatchProcessor(resultConfig);
/*     */       
/*  86 */       BatchSummary summary = processor.process(Collections.singletonList(new File(filePath)), 
/*  87 */           ProcessorFactory.getHandler(FormatOption.XML, true, new FileOutputStream(
/*  88 */               String.valueOf(xmlReport)), 125, false));
/*     */       
/*  90 */       String xmlReportPath = "file://" + xmlReport.toURI().normalize().getPath();
/*     */       
/*  92 */       if (summary.getFailedParsingJobs() != 0) {
/*  93 */         errorMessage = "An error occurred while parsing current file. See report:  " + xmlReportPath;
/*  94 */       } else if (summary.getFailedEncryptedJobs() != 0) {
/*  95 */         errorMessage = "VeraPDF execution failed - specified file is encrypted. See report:  " + xmlReportPath;
/*  96 */       } else if (summary.getValidationSummary().getNonCompliantPdfaCount() != 0) {
/*  97 */         errorMessage = "VeraPDF verification failed. See verification results:  " + xmlReportPath;
/*     */       } else {
/*  99 */         System.out.println("VeraPDF verification finished. See verification report: " + xmlReportPath);
/*     */       } 
/* 101 */     } catch (IOException|org.verapdf.core.VeraPDFException exc) {
/* 102 */       errorMessage = "VeraPDF execution failed:\n" + exc.getMessage();
/*     */     } 
/*     */     
/* 105 */     return errorMessage;
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/test/pdfa/VeraPdfValidator.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */