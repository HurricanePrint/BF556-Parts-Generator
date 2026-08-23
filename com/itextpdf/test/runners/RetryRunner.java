/*     */ package com.itextpdf.test.runners;
/*     */ 
/*     */ import org.junit.AssumptionViolatedException;
/*     */ import org.junit.Ignore;
/*     */ import org.junit.internal.AssumptionViolatedException;
/*     */ import org.junit.internal.runners.model.EachTestNotifier;
/*     */ import org.junit.runner.Description;
/*     */ import org.junit.runner.notification.RunNotifier;
/*     */ import org.junit.runner.notification.StoppedByUserException;
/*     */ import org.junit.runners.BlockJUnit4ClassRunner;
/*     */ import org.junit.runners.model.FrameworkMethod;
/*     */ import org.junit.runners.model.InitializationError;
/*     */ import org.junit.runners.model.Statement;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class RetryRunner
/*     */   extends BlockJUnit4ClassRunner
/*     */ {
/*  61 */   private final int retryCount = 3;
/*  62 */   private int failedAttempts = 0;
/*     */   
/*     */   public RetryRunner(Class<?> klass) throws InitializationError {
/*  65 */     super(klass);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void run(RunNotifier notifier) {
/*  73 */     EachTestNotifier eachTestNotifier = new EachTestNotifier(notifier, getDescription());
/*  74 */     Statement statement = classBlock(notifier);
/*     */     try {
/*  76 */       statement.evaluate();
/*  77 */     } catch (AssumptionViolatedException ave) {
/*  78 */       eachTestNotifier.fireTestIgnored();
/*  79 */     } catch (StoppedByUserException sue) {
/*  80 */       throw sue;
/*  81 */     } catch (Throwable throwable) {
/*  82 */       retry(eachTestNotifier, statement, throwable);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void runChild(FrameworkMethod method, RunNotifier notifier) {
/*  93 */     Description description = describeChild(method);
/*  94 */     if (method.getAnnotation(Ignore.class) != null) {
/*  95 */       notifier.fireTestIgnored(description);
/*     */     } else {
/*  97 */       runTestUnit(methodBlock(method), description, notifier);
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
/*     */   
/*     */   protected final void runTestUnit(Statement statement, Description description, RunNotifier notifier) {
/* 110 */     this.failedAttempts = 0;
/* 111 */     EachTestNotifier eachNotifier = new EachTestNotifier(notifier, description);
/* 112 */     eachNotifier.fireTestStarted();
/*     */     try {
/* 114 */       statement.evaluate();
/* 115 */     } catch (AssumptionViolatedException avee) {
/* 116 */       eachNotifier.addFailedAssumption((AssumptionViolatedException)avee);
/* 117 */     } catch (Throwable e) {
/* 118 */       retry(eachNotifier, statement, e);
/*     */     } finally {
/* 120 */       eachNotifier.fireTestFinished();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void retry(EachTestNotifier notifier, Statement statement, Throwable currentThrowable) {
/* 131 */     Throwable caughtThrowable = currentThrowable;
/* 132 */     while (3 > this.failedAttempts) {
/*     */       try {
/* 134 */         statement.evaluate();
/*     */         break;
/* 136 */       } catch (Throwable e) {
/* 137 */         System.out.println("Test Failed on attempt #" + (this.failedAttempts + 1));
/* 138 */         this.failedAttempts++;
/* 139 */         caughtThrowable = e;
/*     */       } 
/*     */     } 
/* 142 */     notifier.addFailure(caughtThrowable);
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/test/runners/RetryRunner.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */