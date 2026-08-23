/*     */ package com.itextpdf.kernel;
/*     */ 
/*     */ import com.itextpdf.io.util.MessageFormatUtil;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class LicenseVersionException
/*     */   extends RuntimeException
/*     */ {
/*     */   public static final String NO_I_TEXT7_LICENSE_IS_LOADED_BUT_AN_I_TEXT5_LICENSE_IS_LOADED = "No iText7 License is loaded but an iText5 license is loaded.";
/*     */   public static final String THE_MAJOR_VERSION_OF_THE_LICENSE_0_IS_LOWER_THAN_THE_MAJOR_VERSION_1_OF_THE_CORE_LIBRARY = "The major version of the license ({0}) is lower than the major version ({1}) of the Core library.";
/*     */   public static final String THE_MAJOR_VERSION_OF_THE_LICENSE_0_IS_HIGHER_THAN_THE_MAJOR_VERSION_1_OF_THE_CORE_LIBRARY = "The major version of the license ({0}) is higher than the major version ({1}) of the Core library.";
/*     */   public static final String THE_MINOR_VERSION_OF_THE_LICENSE_0_IS_LOWER_THAN_THE_MINOR_VERSION_1_OF_THE_CORE_LIBRARY = "The minor version of the license ({0}) is lower than the minor version ({1}) of the Core library.";
/*     */   public static final String THE_MINOR_VERSION_OF_THE_LICENSE_0_IS_HIGHER_THAN_THE_MINOR_VERSION_1_OF_THE_CORE_LIBRARY = "The minor version of the license ({0}) is higher than the minor version ({1}) of the Core library.";
/*     */   public static final String VERSION_STRING_IS_EMPTY_AND_CANNOT_BE_PARSED = "Version string is empty and cannot be parsed.";
/*     */   public static final String MAJOR_VERSION_IS_NOT_NUMERIC = "Major version is not numeric";
/*     */   public static final String MINOR_VERSION_IS_NOT_NUMERIC = "Minor version is not numeric";
/*     */   public static final String UNKNOWN_EXCEPTION_WHEN_CHECKING_LICENSE_VERSION = "Unknown Exception when checking License version";
/*     */   public static final String LICENSE_FILE_NOT_LOADED = "License file not loaded.";
/*     */   protected Object object;
/*     */   private List<Object> messageParams;
/*     */   
/*     */   public LicenseVersionException(String message) {
/*  82 */     super(message);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public LicenseVersionException(Throwable cause) {
/*  91 */     this("Unknown Exception when checking License version", cause);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public LicenseVersionException(String message, Object obj) {
/* 101 */     this(message);
/* 102 */     this.object = obj;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public LicenseVersionException(String message, Throwable cause) {
/* 112 */     super(message, cause);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public LicenseVersionException(String message, Throwable cause, Object obj) {
/* 123 */     this(message, cause);
/* 124 */     this.object = obj;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getMessage() {
/* 129 */     if (this.messageParams == null || this.messageParams.size() == 0) {
/* 130 */       return super.getMessage();
/*     */     }
/* 132 */     return MessageFormatUtil.format(super.getMessage(), getMessageParams());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public LicenseVersionException setMessageParams(Object... messageParams) {
/* 143 */     this.messageParams = new ArrayList();
/* 144 */     Collections.addAll(this.messageParams, messageParams);
/* 145 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected Object[] getMessageParams() {
/* 154 */     Object[] parameters = new Object[this.messageParams.size()];
/* 155 */     for (int i = 0; i < this.messageParams.size(); i++) {
/* 156 */       parameters[i] = this.messageParams.get(i);
/*     */     }
/* 158 */     return parameters;
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/kernel/LicenseVersionException.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */