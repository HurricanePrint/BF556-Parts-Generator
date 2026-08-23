/*     */ package com.itextpdf.io.util;
/*     */ 
/*     */ import java.io.BufferedReader;
/*     */ import java.io.File;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStreamReader;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ import java.util.regex.Matcher;
/*     */ import java.util.regex.Pattern;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class SystemUtil
/*     */ {
/*     */   private static final String SPLIT_REGEX = "((\".+?\"|[^'\\s]|'.+?')+)\\s*";
/*     */   
/*     */   @Deprecated
/*     */   public static long getSystemTimeMillis() {
/*  68 */     return System.currentTimeMillis();
/*     */   }
/*     */   
/*     */   public static long getTimeBasedSeed() {
/*  72 */     return System.currentTimeMillis();
/*     */   }
/*     */   
/*     */   public static int getTimeBasedIntSeed() {
/*  76 */     return (int)System.currentTimeMillis();
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
/*     */   public static long getRelativeTimeMillis() {
/*  88 */     return System.currentTimeMillis();
/*     */   }
/*     */   
/*     */   public static long getFreeMemory() {
/*  92 */     return Runtime.getRuntime().freeMemory();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String getPropertyOrEnvironmentVariable(String name) {
/* 102 */     String s = System.getProperty(name);
/* 103 */     if (s == null) {
/* 104 */       s = System.getenv(name);
/*     */     }
/* 106 */     return s;
/*     */   }
/*     */   
/*     */   public static boolean runProcessAndWait(String exec, String params) throws IOException, InterruptedException {
/* 110 */     return runProcessAndWait(exec, params, null);
/*     */   }
/*     */   
/*     */   public static boolean runProcessAndWait(String exec, String params, String workingDirPath) throws IOException, InterruptedException {
/* 114 */     return (runProcessAndGetExitCode(exec, params, workingDirPath) == 0);
/*     */   }
/*     */   
/*     */   public static int runProcessAndGetExitCode(String exec, String params) throws IOException, InterruptedException {
/* 118 */     return runProcessAndGetExitCode(exec, params, null);
/*     */   }
/*     */   
/*     */   public static int runProcessAndGetExitCode(String exec, String params, String workingDirPath) throws IOException, InterruptedException {
/* 122 */     Process p = runProcess(exec, params, workingDirPath);
/* 123 */     System.out.println(getProcessOutput(p));
/* 124 */     return p.waitFor();
/*     */   }
/*     */   
/*     */   public static String runProcessAndGetOutput(String command, String params) throws IOException {
/* 128 */     return getProcessOutput(runProcess(command, params, null));
/*     */   }
/*     */   
/*     */   public static StringBuilder runProcessAndCollectErrors(String execPath, String params) throws IOException {
/* 132 */     return printProcessErrorsOutput(runProcess(execPath, params, null));
/*     */   }
/*     */   
/*     */   static Process runProcess(String execPath, String params, String workingDirPath) throws IOException {
/* 136 */     List<String> cmdList = prepareProcessArguments(execPath, params);
/* 137 */     String[] cmdArray = cmdList.<String>toArray(new String[cmdList.size()]);
/* 138 */     if (workingDirPath != null) {
/* 139 */       File workingDir = new File(workingDirPath);
/* 140 */       return Runtime.getRuntime().exec(cmdArray, (String[])null, workingDir);
/*     */     } 
/* 142 */     return Runtime.getRuntime().exec(cmdArray);
/*     */   }
/*     */ 
/*     */   
/*     */   static List<String> prepareProcessArguments(String exec, String params) {
/*     */     List<String> cmdList;
/* 148 */     if ((new File(exec)).exists()) {
/* 149 */       cmdList = new ArrayList<>(Collections.singletonList(exec));
/*     */     } else {
/* 151 */       cmdList = new ArrayList<>(splitIntoProcessArguments(exec));
/*     */     } 
/* 153 */     cmdList.addAll(splitIntoProcessArguments(params));
/* 154 */     return cmdList;
/*     */   }
/*     */   
/*     */   static List<String> splitIntoProcessArguments(String line) {
/* 158 */     List<String> list = new ArrayList<>();
/* 159 */     Matcher m = Pattern.compile("((\".+?\"|[^'\\s]|'.+?')+)\\s*").matcher(line);
/* 160 */     while (m.find()) {
/* 161 */       list.add(m.group(1).replace("'", "").replace("\"", "").trim());
/*     */     }
/* 163 */     return list;
/*     */   }
/*     */   
/*     */   static String getProcessOutput(Process p) throws IOException {
/* 167 */     BufferedReader bri = new BufferedReader(new InputStreamReader(p.getInputStream()));
/* 168 */     BufferedReader bre = new BufferedReader(new InputStreamReader(p.getErrorStream()));
/*     */     
/* 170 */     StringBuilder result = new StringBuilder(); String line;
/* 171 */     while ((line = bri.readLine()) != null) {
/* 172 */       result.append(line);
/*     */     }
/* 174 */     bri.close();
/* 175 */     if (result.length() > 0) {
/* 176 */       result.append('\n');
/*     */     }
/* 178 */     while ((line = bre.readLine()) != null) {
/* 179 */       result.append(line);
/*     */     }
/* 181 */     bre.close();
/* 182 */     return result.toString();
/*     */   }
/*     */   
/*     */   static StringBuilder printProcessErrorsOutput(Process p) throws IOException {
/* 186 */     StringBuilder builder = new StringBuilder();
/* 187 */     BufferedReader bre = new BufferedReader(new InputStreamReader(p.getErrorStream()));
/*     */     String line;
/* 189 */     while ((line = bre.readLine()) != null) {
/* 190 */       System.out.println(line);
/* 191 */       builder.append(line);
/*     */     } 
/* 193 */     bre.close();
/* 194 */     return builder;
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/io/util/SystemUtil.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */