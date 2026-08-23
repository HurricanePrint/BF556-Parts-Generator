/*    */ package com.itextpdf.kernel.pdf;
/*    */ 
/*    */ import com.itextpdf.kernel.ProductInfo;
/*    */ import java.io.Serializable;
/*    */ import java.util.Collection;
/*    */ import java.util.HashSet;
/*    */ import java.util.Set;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class FingerPrint
/*    */   implements Serializable
/*    */ {
/*    */   private static final long serialVersionUID = 1378019250639368423L;
/* 67 */   private Set<ProductInfo> productInfoSet = new HashSet<>();
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean registerProduct(ProductInfo productInfo) {
/* 77 */     int initialSize = this.productInfoSet.size();
/* 78 */     this.productInfoSet.add(productInfo);
/* 79 */     return (initialSize != this.productInfoSet.size());
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Collection<ProductInfo> getProducts() {
/* 88 */     return this.productInfoSet;
/*    */   }
/*    */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/kernel/pdf/FingerPrint.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */