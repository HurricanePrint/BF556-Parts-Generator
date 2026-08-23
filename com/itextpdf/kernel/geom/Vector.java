/*     */ package com.itextpdf.kernel.geom;
/*     */ 
/*     */ import java.util.Arrays;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Vector
/*     */ {
/*     */   public static final int I1 = 0;
/*     */   public static final int I2 = 1;
/*     */   public static final int I3 = 2;
/*  74 */   private final float[] vals = new float[] { 0.0F, 0.0F, 0.0F };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Vector(float x, float y, float z) {
/*  84 */     this.vals[0] = x;
/*  85 */     this.vals[1] = y;
/*  86 */     this.vals[2] = z;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float get(int index) {
/*  96 */     return this.vals[index];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Vector cross(Matrix by) {
/* 106 */     float x = this.vals[0] * by.get(0) + this.vals[1] * by.get(3) + this.vals[2] * by.get(6);
/* 107 */     float y = this.vals[0] * by.get(1) + this.vals[1] * by.get(4) + this.vals[2] * by.get(7);
/* 108 */     float z = this.vals[0] * by.get(2) + this.vals[1] * by.get(5) + this.vals[2] * by.get(8);
/*     */     
/* 110 */     return new Vector(x, y, z);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Vector subtract(Vector v) {
/* 120 */     float x = this.vals[0] - v.vals[0];
/* 121 */     float y = this.vals[1] - v.vals[1];
/* 122 */     float z = this.vals[2] - v.vals[2];
/*     */     
/* 124 */     return new Vector(x, y, z);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Vector cross(Vector with) {
/* 134 */     float x = this.vals[1] * with.vals[2] - this.vals[2] * with.vals[1];
/* 135 */     float y = this.vals[2] * with.vals[0] - this.vals[0] * with.vals[2];
/* 136 */     float z = this.vals[0] * with.vals[1] - this.vals[1] * with.vals[0];
/*     */     
/* 138 */     return new Vector(x, y, z);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Vector normalize() {
/* 147 */     float l = length();
/* 148 */     float x = this.vals[0] / l;
/* 149 */     float y = this.vals[1] / l;
/* 150 */     float z = this.vals[2] / l;
/* 151 */     return new Vector(x, y, z);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Vector multiply(float by) {
/* 161 */     float x = this.vals[0] * by;
/* 162 */     float y = this.vals[1] * by;
/* 163 */     float z = this.vals[2] * by;
/* 164 */     return new Vector(x, y, z);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float dot(Vector with) {
/* 174 */     return this.vals[0] * with.vals[0] + this.vals[1] * with.vals[1] + this.vals[2] * with.vals[2];
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
/*     */ 
/*     */ 
/*     */   
/*     */   public float length() {
/* 191 */     return (float)Math.sqrt(lengthSquared());
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
/*     */   public float lengthSquared() {
/* 205 */     return this.vals[0] * this.vals[0] + this.vals[1] * this.vals[1] + this.vals[2] * this.vals[2];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 213 */     return this.vals[0] + "," + this.vals[1] + "," + this.vals[2];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 221 */     int prime = 31;
/* 222 */     int result = 1;
/* 223 */     result = 31 * result + Arrays.hashCode(this.vals);
/* 224 */     return result;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/* 232 */     if (this == obj) {
/* 233 */       return true;
/*     */     }
/* 235 */     if (obj == null) {
/* 236 */       return false;
/*     */     }
/* 238 */     if (getClass() != obj.getClass()) {
/* 239 */       return false;
/*     */     }
/* 241 */     Vector other = (Vector)obj;
/* 242 */     if (!Arrays.equals(this.vals, other.vals)) {
/* 243 */       return false;
/*     */     }
/* 245 */     return true;
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/kernel/geom/Vector.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */