/*     */ package com.itextpdf.pdfa.checker;
/*     */ 
/*     */ import com.itextpdf.kernel.pdf.PdfAConformanceLevel;
/*     */ import com.itextpdf.kernel.pdf.PdfDictionary;
/*     */ import com.itextpdf.kernel.pdf.PdfName;
/*     */ import com.itextpdf.kernel.pdf.PdfObject;
/*     */ import com.itextpdf.kernel.pdf.PdfStream;
/*     */ import com.itextpdf.pdfa.PdfAConformanceException;
/*     */ import java.util.Arrays;
/*     */ import java.util.HashSet;
/*     */ import java.util.Set;
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
/*     */ public class PdfA3Checker
/*     */   extends PdfA2Checker
/*     */ {
/*  68 */   protected static final Set<PdfName> allowedAFRelationships = new HashSet<>(Arrays.asList(new PdfName[] { PdfName.Source, PdfName.Data, PdfName.Alternative, PdfName.Supplement, PdfName.Unspecified }));
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static final long serialVersionUID = 6280825718658124941L;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PdfA3Checker(PdfAConformanceLevel conformanceLevel) {
/*  80 */     super(conformanceLevel);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void checkFileSpec(PdfDictionary fileSpec) {
/*  85 */     PdfName relationship = fileSpec.getAsName(PdfName.AFRelationship);
/*  86 */     if (relationship == null || !allowedAFRelationships.contains(relationship)) {
/*  87 */       throw new PdfAConformanceException("File specification dictionary shall contain one of the predefined afrelationship keys");
/*     */     }
/*     */     
/*  90 */     if (fileSpec.containsKey(PdfName.EF)) {
/*  91 */       if (!fileSpec.containsKey(PdfName.F) || !fileSpec.containsKey(PdfName.UF) || !fileSpec.containsKey(PdfName.Desc)) {
/*  92 */         throw new PdfAConformanceException("File specification dictionary shall contain f key and uf key");
/*     */       }
/*     */ 
/*     */       
/*  96 */       PdfDictionary ef = fileSpec.getAsDictionary(PdfName.EF);
/*  97 */       PdfStream embeddedFile = ef.getAsStream(PdfName.F);
/*  98 */       if (embeddedFile == null) {
/*  99 */         throw new PdfAConformanceException("Ef key of file specification dictionary shall contain dictionary with valid f key");
/*     */       }
/*     */       
/* 102 */       if (!embeddedFile.containsKey(PdfName.Subtype)) {
/* 103 */         throw new PdfAConformanceException("Mime type shall be specified using the subtype key of the file specification stream dictionary");
/*     */       }
/*     */       
/* 106 */       if (embeddedFile.containsKey(PdfName.Params)) {
/* 107 */         PdfObject params = embeddedFile.get(PdfName.Params);
/* 108 */         if (!params.isDictionary()) {
/* 109 */           throw new PdfAConformanceException("Embedded file shall contain params key with dictionary as value");
/*     */         }
/* 111 */         if (((PdfDictionary)params).getAsString(PdfName.ModDate) == null) {
/* 112 */           throw new PdfAConformanceException("Embedded file shall contain params key with valid moddate key");
/*     */         }
/*     */       } else {
/* 115 */         Logger logger = LoggerFactory.getLogger(PdfAChecker.class);
/* 116 */         logger.warn("Embedded file should contain params key ");
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/pdfa/checker/PdfA3Checker.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */