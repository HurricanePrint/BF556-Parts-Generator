/*    */ package com.itextpdf.io.font.otf;
/*    */ 
/*    */ import java.io.Serializable;
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
/*    */ public class GposAnchor
/*    */   implements Serializable
/*    */ {
/*    */   private static final long serialVersionUID = 7153858421411686094L;
/*    */   public int XCoordinate;
/*    */   public int YCoordinate;
/*    */   
/*    */   public GposAnchor() {}
/*    */   
/*    */   public GposAnchor(GposAnchor other) {
/* 57 */     this.XCoordinate = other.XCoordinate;
/* 58 */     this.YCoordinate = other.YCoordinate;
/*    */   }
/*    */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/io/font/otf/GposAnchor.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */