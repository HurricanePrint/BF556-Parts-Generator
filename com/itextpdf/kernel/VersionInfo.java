/*     */ package com.itextpdf.kernel;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class VersionInfo
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 1514128839876564529L;
/*     */   private final String productName;
/*     */   private final String releaseNumber;
/*     */   private final String producerLine;
/*     */   private final String licenseKey;
/*     */   
/*     */   public VersionInfo(String productName, String releaseNumber, String producerLine, String licenseKey) {
/*  58 */     this.productName = productName;
/*  59 */     this.releaseNumber = releaseNumber;
/*  60 */     this.producerLine = producerLine;
/*  61 */     this.licenseKey = licenseKey;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getProduct() {
/*  72 */     return this.productName;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getRelease() {
/*  83 */     return this.releaseNumber;
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
/*     */   public String getVersion() {
/*  95 */     return this.producerLine;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getKey() {
/* 104 */     return this.licenseKey;
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/kernel/VersionInfo.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */