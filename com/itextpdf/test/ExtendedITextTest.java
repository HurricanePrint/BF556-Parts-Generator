/*    */ package com.itextpdf.test;
/*    */ 
/*    */ import org.junit.After;
/*    */ import org.junit.Before;
/*    */ import org.junit.Rule;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class ExtendedITextTest
/*    */   extends ITextTest
/*    */ {
/*    */   @Rule
/* 57 */   public LogListener logListener = new LogListener();
/*    */   
/*    */   @Before
/*    */   public void beforeTestMethodAction() {}
/*    */   
/*    */   @After
/*    */   public void afterTestMethodAction() {}
/*    */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/test/ExtendedITextTest.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */