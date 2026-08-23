/*     */ package com.itextpdf.layout.property;
/*     */ 
/*     */ import com.itextpdf.kernel.geom.AffineTransform;
/*     */ import java.util.ArrayList;
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
/*     */ public class Transform
/*     */ {
/*     */   private List<SingleTransform> multipleTransform;
/*     */   
/*     */   public Transform(int length) {
/*  62 */     this.multipleTransform = new ArrayList<>(length);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addSingleTransform(SingleTransform singleTransform) {
/*  71 */     this.multipleTransform.add(singleTransform);
/*     */   }
/*     */   
/*     */   private List<SingleTransform> getMultipleTransform() {
/*  75 */     return this.multipleTransform;
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
/*     */ 
/*     */   
/*     */   public static AffineTransform getAffineTransform(Transform t, float width, float height) {
/*  89 */     List<SingleTransform> multipleTransform = t.getMultipleTransform();
/*  90 */     AffineTransform affineTransform = new AffineTransform();
/*  91 */     for (int k = multipleTransform.size() - 1; k >= 0; k--) {
/*  92 */       SingleTransform transform = multipleTransform.get(k);
/*  93 */       float[] floats = new float[6]; int i;
/*  94 */       for (i = 0; i < 4; i++)
/*  95 */         floats[i] = transform.getFloats()[i]; 
/*  96 */       for (i = 4; i < 6; i++)
/*  97 */         floats[i] = (transform.getUnitValues()[i - 4].getUnitType() == 1) ? transform
/*  98 */           .getUnitValues()[i - 4].getValue() : (transform.getUnitValues()[i - 4].getValue() / 100.0F * ((i == 4) ? width : height)); 
/*  99 */       affineTransform.preConcatenate(new AffineTransform(floats));
/*     */     } 
/* 101 */     return affineTransform;
/*     */   }
/*     */ 
/*     */   
/*     */   public static class SingleTransform
/*     */   {
/*     */     private float a;
/*     */     private float b;
/*     */     private float c;
/*     */     private float d;
/*     */     private UnitValue tx;
/*     */     private UnitValue ty;
/*     */     
/*     */     public SingleTransform() {
/* 115 */       this.a = 1.0F;
/* 116 */       this.b = 0.0F;
/* 117 */       this.c = 0.0F;
/* 118 */       this.d = 1.0F;
/* 119 */       this.tx = new UnitValue(1, 0.0F);
/* 120 */       this.ty = new UnitValue(1, 0.0F);
/*     */     }
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
/*     */     public SingleTransform(float a, float b, float c, float d, UnitValue tx, UnitValue ty) {
/* 134 */       this.a = a;
/* 135 */       this.b = b;
/* 136 */       this.c = c;
/* 137 */       this.d = d;
/* 138 */       this.tx = tx;
/* 139 */       this.ty = ty;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public float[] getFloats() {
/* 148 */       return new float[] { this.a, this.b, this.c, this.d };
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public UnitValue[] getUnitValues() {
/* 157 */       return new UnitValue[] { this.tx, this.ty };
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/layout/property/Transform.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */