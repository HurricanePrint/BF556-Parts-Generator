/*     */ package com.itextpdf.kernel;
/*     */ 
/*     */ import java.lang.reflect.InvocationTargetException;
/*     */ import java.lang.reflect.Method;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class Version
/*     */ {
/*  60 */   private static final Object staticLock = new Object();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static final String AGPL = " (AGPL-version)";
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  70 */   private static volatile Version version = null;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static final String iTextProductName = "iText®";
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static final String release = "7.1.13";
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static final String producerLine = "iText® 7.1.13 ©2000-2020 iText Group NV";
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final VersionInfo info;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean expired;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public Version() {
/* 102 */     this.info = new VersionInfo("iText®", "7.1.13", "iText® 7.1.13 ©2000-2020 iText Group NV", null);
/*     */   }
/*     */   
/*     */   Version(VersionInfo info, boolean expired) {
/* 106 */     this.info = info;
/* 107 */     this.expired = expired;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Version getInstance() {
/*     */     Version localVersion;
/* 118 */     synchronized (staticLock) {
/* 119 */       if (version != null) {
/*     */         try {
/* 121 */           licenseScheduledCheck();
/* 122 */         } catch (Exception e) {
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 127 */           atomicSetVersion(initAGPLVersion(e, null));
/*     */         } 
/* 129 */         return version;
/*     */       } 
/*     */     } 
/*     */     
/* 133 */     String key = null;
/*     */     try {
/* 135 */       String coreVersion = "7.1.13";
/* 136 */       String[] info = getLicenseeInfoFromLicenseKey(coreVersion);
/* 137 */       if (info != null) {
/* 138 */         if (info[3] != null && info[3].trim().length() > 0) {
/* 139 */           key = info[3];
/*     */         } else {
/* 141 */           key = "Trial version ";
/* 142 */           if (info[5] == null) {
/* 143 */             key = key + "unauthorised";
/*     */           } else {
/* 145 */             key = key + info[5];
/*     */           } 
/*     */         } 
/*     */         
/* 149 */         if (info.length > 6 && 
/* 150 */           info[6] != null && info[6].trim().length() > 0)
/*     */         {
/* 152 */           checkLicenseVersion(coreVersion, info[6]);
/*     */         }
/*     */ 
/*     */         
/* 156 */         if (info[4] != null && info[4].trim().length() > 0) {
/* 157 */           localVersion = initVersion(info[4], key, false);
/* 158 */         } else if (info[2] != null && info[2].trim().length() > 0) {
/* 159 */           localVersion = initDefaultLicensedVersion(info[2], key);
/* 160 */         } else if (info[0] != null && info[0].trim().length() > 0) {
/*     */ 
/*     */ 
/*     */           
/* 164 */           localVersion = initDefaultLicensedVersion(info[0], key);
/*     */         } else {
/* 166 */           localVersion = initAGPLVersion(null, key);
/*     */         } 
/*     */       } else {
/* 169 */         localVersion = initAGPLVersion(null, key);
/*     */       }
/*     */     
/* 172 */     } catch (LicenseVersionException lve) {
/*     */       
/* 174 */       throw lve;
/* 175 */     } catch (ClassNotFoundException cnfe) {
/*     */       
/* 177 */       localVersion = initAGPLVersion(null, key);
/* 178 */     } catch (Exception e) {
/*     */       
/* 180 */       if (e.getCause() != null && e.getCause().getMessage().equals("License file not loaded.") && 
/* 181 */         isiText5licenseLoaded()) {
/* 182 */         throw new LicenseVersionException("No iText7 License is loaded but an iText5 license is loaded.");
/*     */       }
/*     */       
/* 185 */       localVersion = initAGPLVersion(e.getCause(), key);
/*     */     } 
/* 187 */     return atomicSetVersion(localVersion);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean isAGPLVersion() {
/* 195 */     return getInstance().isAGPL();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean isExpired() {
/* 203 */     return (getInstance()).expired;
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
/* 214 */     return this.info.getProduct();
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
/* 225 */     return this.info.getRelease();
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
/* 237 */     return this.info.getVersion();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getKey() {
/* 246 */     return this.info.getKey();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public VersionInfo getInfo() {
/* 255 */     return this.info;
/*     */   }
/*     */   
/*     */   static String[] parseVersionString(String version) {
/* 259 */     String splitRegex = "\\.";
/* 260 */     String[] split = version.split(splitRegex);
/*     */     
/* 262 */     if (split.length == 0) {
/* 263 */       throw new LicenseVersionException("Version string is empty and cannot be parsed.");
/*     */     }
/*     */ 
/*     */     
/* 267 */     String major = split[0];
/*     */     
/* 269 */     String minor = "0";
/* 270 */     if (split.length > 1) {
/* 271 */       minor = split[1].substring(0);
/*     */     }
/*     */     
/* 274 */     if (!isVersionNumeric(major)) {
/* 275 */       throw new LicenseVersionException("Major version is not numeric");
/*     */     }
/* 277 */     if (!isVersionNumeric(minor)) {
/* 278 */       throw new LicenseVersionException("Minor version is not numeric");
/*     */     }
/* 280 */     return new String[] { major, minor };
/*     */   }
/*     */   
/*     */   static boolean isVersionNumeric(String version) {
/*     */     try {
/* 285 */       int value = Integer.parseInt(version);
/*     */       
/* 287 */       return (value >= 0 && !version.contains("+"));
/* 288 */     } catch (NumberFormatException e) {
/* 289 */       return false;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   boolean isAGPL() {
/* 299 */     return (getVersion().indexOf(" (AGPL-version)") > 0);
/*     */   }
/*     */   
/*     */   private static Version initDefaultLicensedVersion(String ownerName, String key) {
/* 303 */     String producer = "iText® 7.1.13 ©2000-2020 iText Group NV (" + ownerName;
/* 304 */     if (!key.toLowerCase().startsWith("trial")) {
/* 305 */       producer = producer + "; licensed version)";
/*     */     } else {
/* 307 */       producer = producer + "; " + key + ")";
/*     */     } 
/* 309 */     return initVersion(producer, key, false);
/*     */   }
/*     */   
/*     */   private static Version initAGPLVersion(Throwable cause, String key) {
/* 313 */     String producer = "iText® 7.1.13 ©2000-2020 iText Group NV (AGPL-version)";
/*     */     
/* 315 */     boolean expired = (cause != null && cause.getMessage() != null && cause.getMessage().contains("expired"));
/*     */     
/* 317 */     return initVersion(producer, key, expired);
/*     */   }
/*     */   
/*     */   private static Version initVersion(String producer, String key, boolean expired) {
/* 321 */     return new Version(new VersionInfo("iText®", "7.1.13", producer, key), expired);
/*     */   }
/*     */   
/*     */   private static Class<?> getLicenseKeyClass() throws ClassNotFoundException {
/* 325 */     String licenseKeyClassFullName = "com.itextpdf.licensekey.LicenseKey";
/* 326 */     return getClassFromLicenseKey(licenseKeyClassFullName);
/*     */   }
/*     */   
/*     */   private static Class<?> getClassFromLicenseKey(String classFullName) throws ClassNotFoundException {
/* 330 */     return Class.forName(classFullName);
/*     */   }
/*     */   
/*     */   private static void checkLicenseVersion(String coreVersionString, String licenseVersionString) {
/* 334 */     String[] coreVersions = parseVersionString(coreVersionString);
/* 335 */     String[] licenseVersions = parseVersionString(licenseVersionString);
/*     */     
/* 337 */     int coreMajor = Integer.parseInt(coreVersions[0]);
/* 338 */     int coreMinor = Integer.parseInt(coreVersions[1]);
/*     */     
/* 340 */     int licenseMajor = Integer.parseInt(licenseVersions[0]);
/* 341 */     int licenseMinor = Integer.parseInt(licenseVersions[1]);
/*     */     
/* 343 */     if (licenseMajor < coreMajor) {
/* 344 */       throw (new LicenseVersionException("The major version of the license ({0}) is lower than the major version ({1}) of the Core library.")).setMessageParams(new Object[] { Integer.valueOf(licenseMajor), Integer.valueOf(coreMajor) });
/*     */     }
/* 346 */     if (licenseMajor > coreMajor) {
/* 347 */       throw (new LicenseVersionException("The major version of the license ({0}) is higher than the major version ({1}) of the Core library.")).setMessageParams(new Object[] { Integer.valueOf(licenseMajor), Integer.valueOf(coreMajor) });
/*     */     }
/*     */ 
/*     */     
/* 351 */     if (licenseMinor < coreMinor) {
/* 352 */       throw (new LicenseVersionException("The minor version of the license ({0}) is lower than the minor version ({1}) of the Core library.")).setMessageParams(new Object[] { Integer.valueOf(licenseMinor), Integer.valueOf(coreMinor) });
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private static String[] getLicenseeInfoFromLicenseKey(String validatorKey) throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InstantiationException, InvocationTargetException {
/* 358 */     String licenseeInfoMethodName = "getLicenseeInfoForVersion";
/* 359 */     Class<?> klass = getLicenseKeyClass();
/* 360 */     if (klass != null) {
/* 361 */       Class[] cArg = { String.class };
/* 362 */       Method m = klass.getMethod(licenseeInfoMethodName, cArg);
/* 363 */       Object[] args = { validatorKey };
/* 364 */       String[] info = (String[])m.invoke(klass.newInstance(), args);
/* 365 */       return info;
/*     */     } 
/* 367 */     return null;
/*     */   }
/*     */   
/*     */   private static boolean isiText5licenseLoaded() {
/* 371 */     String validatorKey5 = "5";
/* 372 */     boolean result = false;
/*     */     try {
/* 374 */       String[] info = getLicenseeInfoFromLicenseKey(validatorKey5);
/* 375 */       result = true;
/* 376 */     } catch (Exception exception) {}
/*     */ 
/*     */     
/* 379 */     return result;
/*     */   }
/*     */   
/*     */   private static Version atomicSetVersion(Version newVersion) {
/* 383 */     synchronized (staticLock) {
/* 384 */       version = newVersion;
/* 385 */       return version;
/*     */     } 
/*     */   }
/*     */   
/*     */   private static void licenseScheduledCheck() {
/* 390 */     if (version.isAGPL()) {
/*     */       return;
/*     */     }
/*     */     
/* 394 */     String licenseKeyProductFullName = "com.itextpdf.licensekey.LicenseKeyProduct";
/* 395 */     String checkLicenseKeyMethodName = "scheduledCheck";
/*     */     try {
/* 397 */       Class<?> licenseKeyClass = getLicenseKeyClass();
/* 398 */       Class<?> licenseKeyProductClass = getClassFromLicenseKey(licenseKeyProductFullName);
/*     */       
/* 400 */       Class[] cArg = { licenseKeyProductClass };
/* 401 */       Method method = licenseKeyClass.getMethod(checkLicenseKeyMethodName, cArg);
/* 402 */       method.invoke(null, new Object[] { null });
/* 403 */     } catch (Exception e) {
/* 404 */       throw new RuntimeException(e.getMessage(), e);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/kernel/Version.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */