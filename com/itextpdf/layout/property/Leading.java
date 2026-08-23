/*     */ package com.itextpdf.layout.property;
/*     */ 
/*     */ import java.util.Objects;
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
/*     */ public class Leading
/*     */ {
/*     */   public static final int FIXED = 1;
/*     */   public static final int MULTIPLIED = 2;
/*     */   protected int type;
/*     */   protected float value;
/*     */   
/*     */   public Leading(int type, float value) {
/*  81 */     this.type = type;
/*  82 */     this.value = value;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getType() {
/*  91 */     return this.type;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getValue() {
/*  99 */     return this.value;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/* 104 */     return (getClass() == obj.getClass() && this.type == ((Leading)obj).type && this.value == ((Leading)obj).value);
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 109 */     return Objects.hash(new Object[] { Integer.valueOf(this.type), Float.valueOf(this.value) });
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/layout/property/Leading.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */