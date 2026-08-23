/*    */ package com.itextpdf.test;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class RunnerSearchConfig
/*    */ {
/* 51 */   private List<String> searchPackages = new ArrayList<>();
/* 52 */   private List<String> searchClasses = new ArrayList<>();
/* 53 */   private List<String> ignoredPaths = new ArrayList<>();
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public RunnerSearchConfig addPackageToRunnerSearchPath(String fullName) {
/* 61 */     this.searchPackages.add(fullName);
/* 62 */     return this;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public RunnerSearchConfig addClassToRunnerSearchPath(String fullName) {
/* 71 */     this.searchClasses.add(fullName);
/* 72 */     return this;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public RunnerSearchConfig ignorePackageOrClass(String name) {
/* 82 */     this.ignoredPaths.add(name);
/* 83 */     return this;
/*    */   }
/*    */   
/* 86 */   public List<String> getSearchPackages() { return this.searchPackages; }
/* 87 */   public List<String> getSearchClasses() { return this.searchClasses; } public List<String> getIgnoredPaths() {
/* 88 */     return this.ignoredPaths;
/*    */   }
/*    */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/test/RunnerSearchConfig.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */