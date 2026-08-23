/*     */ package com.itextpdf.test;
/*     */ 
/*     */ import java.io.File;
/*     */ import java.lang.reflect.Field;
/*     */ import java.lang.reflect.InvocationTargetException;
/*     */ import java.lang.reflect.Method;
/*     */ import java.nio.file.Paths;
/*     */ import java.text.MessageFormat;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.List;
/*     */ import org.junit.Assert;
/*     */ import org.junit.Assume;
/*     */ import org.junit.runner.RunWith;
/*     */ import org.junit.runners.Parameterized;
/*     */ import org.junit.runners.Parameterized.Parameter;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @RunWith(Parameterized.class)
/*     */ public abstract class WrappedSamplesRunner
/*     */ {
/*     */   @Parameter
/*     */   public RunnerParams sampleClassParams;
/*     */   protected Class<?> sampleClass;
/*     */   private String errorMessage;
/*     */   
/*     */   public static Collection<Object[]> generateTestsList() {
/*  70 */     return generateTestsList((new RunnerSearchConfig()).addPackageToRunnerSearchPath(""));
/*     */   }
/*     */   public static Collection<Object[]> generateTestsList(RunnerSearchConfig searchConfig) {
/*  73 */     List<Object[]> params = new ArrayList();
/*  74 */     for (String searchPath : searchConfig.getSearchPackages()) {
/*  75 */       File classesFolder = Paths.get("target/classes", new String[] { searchPath.replace(".", "/") }).toFile();
/*  76 */       File testClassesFolder = Paths.get("target/test-classes", new String[] { searchPath.replace(".", "/") }).toFile();
/*  77 */       if (!searchPath.isEmpty()) searchPath = searchPath + "."; 
/*  78 */       List<RunnerParams> samplesParamsList = getClassNamesRecursively(classesFolder, searchPath, searchConfig);
/*  79 */       samplesParamsList.addAll(getClassNamesRecursively(testClassesFolder, searchPath, searchConfig));
/*  80 */       for (RunnerParams sampleParam : samplesParamsList) {
/*  81 */         params.add(new RunnerParams[] { sampleParam });
/*     */       } 
/*     */     } 
/*  84 */     for (String className : searchConfig.getSearchClasses()) {
/*  85 */       params.add(new RunnerParams[] { checkIfTestAndCreateParams(className, searchConfig) });
/*     */     } 
/*     */     
/*  88 */     return params;
/*     */   }
/*     */   
/*     */   public void runSamples() throws Exception {
/*  92 */     Assume.assumeTrue(this.sampleClassParams.ignoreMessage, (this.sampleClassParams.ignoreMessage == null));
/*     */     
/*  94 */     initClass();
/*  95 */     System.out.println("Starting test " + this.sampleClassParams);
/*     */     
/*  97 */     runMain();
/*     */     
/*  99 */     String dest = getDest();
/* 100 */     String cmp = getCmpPdf(dest);
/* 101 */     if (dest == null || dest.isEmpty()) {
/* 102 */       throw new IllegalArgumentException("Can't verify results, DEST field must not be empty!");
/*     */     }
/*     */     
/* 105 */     String outPath = getOutPath(dest);
/* 106 */     (new File(outPath)).mkdirs();
/*     */     
/* 108 */     System.out.println("Test executed successfully, comparing results...");
/* 109 */     comparePdf(outPath, dest, cmp);
/*     */     
/* 111 */     if (this.errorMessage != null)
/* 112 */       Assert.fail(this.errorMessage); 
/* 113 */     System.out.println("Test complete.");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected abstract void comparePdf(String paramString1, String paramString2, String paramString3) throws Exception;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected String getDest() {
/* 130 */     return getStringField(this.sampleClass, "DEST");
/*     */   }
/*     */   
/*     */   protected String getCmpPdf(String dest) {
/* 134 */     if (dest == null)
/* 135 */       return null; 
/* 136 */     int i = dest.lastIndexOf("/");
/* 137 */     return "./cmpfiles/" + dest.substring(8, i + 1) + "cmp_" + dest.substring(i + 1);
/*     */   }
/*     */   
/*     */   protected String getOutPath(String dest) {
/* 141 */     return "./target/" + (new File(dest)).getParent();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected static String getStringField(Class<?> c, String name) {
/*     */     try {
/* 153 */       Field field = c.getField(name);
/* 154 */       if (field == null)
/* 155 */         return null; 
/* 156 */       Object obj = field.get(null);
/* 157 */       if (obj == null || !(obj instanceof String))
/* 158 */         return null; 
/* 159 */       return (String)obj;
/*     */     }
/* 161 */     catch (Exception e) {
/* 162 */       return null;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void initClass() {
/* 170 */     if (this.sampleClass == null) {
/*     */       try {
/* 172 */         this.sampleClass = Class.forName(this.sampleClassParams.className);
/* 173 */       } catch (ClassNotFoundException e) {
/* 174 */         throw new RuntimeException(this.sampleClassParams.className + " not found");
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void addError(String error) {
/* 184 */     if (error != null && error.length() > 0) {
/* 185 */       if (this.errorMessage == null) {
/* 186 */         this.errorMessage = "";
/*     */       } else {
/* 188 */         this.errorMessage += "\n";
/*     */       } 
/* 190 */       this.errorMessage += error;
/*     */     } 
/*     */   }
/*     */   
/*     */   private void runMain() throws IllegalAccessException, InvocationTargetException {
/* 195 */     Method mainMethod = getMain(this.sampleClass);
/* 196 */     if (mainMethod == null) {
/* 197 */       throw new IllegalArgumentException("Class must have main method.");
/*     */     }
/* 199 */     mainMethod.invoke(null, new Object[] { null });
/*     */   }
/*     */   
/*     */   private static Method getMain(Class<?> c) {
/*     */     try {
/* 204 */       return c.getDeclaredMethod("main", new Class[] { String[].class });
/* 205 */     } catch (NoSuchMethodException e) {
/* 206 */       return null;
/*     */     } 
/*     */   }
/*     */   
/*     */   private static List<RunnerParams> getClassNamesRecursively(File path, String currentFullName, RunnerSearchConfig searchConfig) {
/* 211 */     List<RunnerParams> runnerParams = new ArrayList<>();
/* 212 */     File[] files = path.listFiles();
/* 213 */     if (files == null) {
/* 214 */       return runnerParams;
/*     */     }
/* 216 */     for (File file : files) {
/* 217 */       if (file.isDirectory()) {
/* 218 */         String[] splitted = file.getAbsolutePath().replace("\\", "/").split("/");
/* 219 */         String packageName = splitted[splitted.length - 1];
/* 220 */         runnerParams.addAll(getClassNamesRecursively(file, currentFullName + packageName + ".", searchConfig));
/*     */       } else {
/* 222 */         String fileName = file.getName();
/* 223 */         if (fileName.endsWith(".class") && !fileName.contains("$")) {
/* 224 */           String className = currentFullName + fileName.replace(".class", "");
/* 225 */           RunnerParams params = checkIfTestAndCreateParams(className, searchConfig);
/* 226 */           if (params != null) {
/* 227 */             runnerParams.add(params);
/*     */           }
/*     */         } 
/*     */       } 
/*     */     } 
/* 232 */     return runnerParams;
/*     */   }
/*     */   
/*     */   private static RunnerParams checkIfTestAndCreateParams(String className, RunnerSearchConfig searchConfig) {
/* 236 */     if (isIgnoredClassOrPackage(className, searchConfig)) {
/* 237 */       return null;
/*     */     }
/*     */ 
/*     */     
/*     */     try {
/* 242 */       Class<?> c = Class.forName(className);
/* 243 */     } catch (ClassNotFoundException e) {
/* 244 */       throw new RuntimeException(MessageFormat.format("Cannot find class {0}", new Object[] { className }), e);
/*     */     } 
/*     */     
/* 247 */     RunnerParams params = new RunnerParams();
/* 248 */     params.className = className;
/*     */     
/* 250 */     return params;
/*     */   }
/*     */   
/*     */   private static boolean isIgnoredClassOrPackage(String fullName, RunnerSearchConfig searchConfig) {
/* 254 */     for (String ignoredPath : searchConfig.getIgnoredPaths()) {
/* 255 */       File currentFile = getFileByLocation("target/classes", ignoredPath);
/*     */       
/* 257 */       if (currentFile == null) {
/* 258 */         currentFile = getFileByLocation("target/test-classes", ignoredPath);
/*     */       }
/*     */       
/* 261 */       if (currentFile != null && ((
/* 262 */         currentFile.isDirectory() && fullName.contains(ignoredPath)) || (currentFile
/* 263 */         .isFile() && fullName.equals(ignoredPath)))) {
/* 264 */         return true;
/*     */       }
/*     */     } 
/*     */     
/* 268 */     return false;
/*     */   }
/*     */   
/*     */   private static File getFileByLocation(String targetSubDirectory, String filePath) {
/* 272 */     File currentFile = Paths.get(targetSubDirectory, new String[] { filePath.replace(".", "/") }).toFile();
/* 273 */     if (currentFile.exists()) {
/* 274 */       return currentFile;
/*     */     }
/*     */     
/* 277 */     currentFile = Paths.get(targetSubDirectory, new String[] { filePath.replace(".", "/") + ".class" }).toFile();
/* 278 */     if (currentFile.exists()) {
/* 279 */       return currentFile;
/*     */     }
/*     */     
/* 282 */     return null;
/*     */   }
/*     */   
/*     */   private static class RunnerParams { String className;
/*     */     String ignoreMessage;
/*     */     
/*     */     private RunnerParams() {}
/*     */     
/*     */     public String toString() {
/* 291 */       return this.className;
/*     */     } }
/*     */ 
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/test/WrappedSamplesRunner.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */