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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ProductInfo
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 2410734474798313936L;
/*     */   private String name;
/*     */   private int major;
/*     */   private int minor;
/*     */   private int patch;
/*     */   private boolean snapshot;
/*     */   
/*     */   public ProductInfo(String name, int major, int minor, int patch, boolean snapshot) {
/*  71 */     this.name = name;
/*  72 */     this.major = major;
/*  73 */     this.minor = minor;
/*  74 */     this.patch = patch;
/*  75 */     this.snapshot = snapshot;
/*     */   }
/*     */   
/*     */   public String getName() {
/*  79 */     return this.name;
/*     */   }
/*     */   
/*     */   public int getMajor() {
/*  83 */     return this.major;
/*     */   }
/*     */   
/*     */   public int getMinor() {
/*  87 */     return this.minor;
/*     */   }
/*     */   
/*     */   public int getPatch() {
/*  91 */     return this.patch;
/*     */   }
/*     */   
/*     */   public boolean isSnapshot() {
/*  95 */     return this.snapshot;
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 100 */     return this.name + "-" + this.major + "." + this.minor + "." + this.patch + (this.snapshot ? "-SNAPSHOT" : "");
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/kernel/ProductInfo.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */