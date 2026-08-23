/*     */ package com.itextpdf.kernel.geom;
/*     */ 
/*     */ import java.io.Serializable;
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
/*     */ public class Matrix
/*     */   implements Serializable
/*     */ {
/*     */   public static final int I11 = 0;
/*     */   public static final int I12 = 1;
/*     */   public static final int I13 = 2;
/*     */   public static final int I21 = 3;
/*     */   public static final int I22 = 4;
/*     */   public static final int I23 = 5;
/*     */   public static final int I31 = 6;
/*     */   public static final int I32 = 7;
/*     */   public static final int I33 = 8;
/*     */   private static final long serialVersionUID = 7434885566068528477L;
/*  81 */   private final float[] vals = new float[] { 1.0F, 0.0F, 0.0F, 0.0F, 1.0F, 0.0F, 0.0F, 0.0F, 1.0F };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Matrix() {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Matrix(float tx, float ty) {
/*  99 */     this.vals[6] = tx;
/* 100 */     this.vals[7] = ty;
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
/*     */   public Matrix(float e11, float e12, float e13, float e21, float e22, float e23, float e31, float e32, float e33) {
/* 116 */     this.vals[0] = e11;
/* 117 */     this.vals[1] = e12;
/* 118 */     this.vals[2] = e13;
/* 119 */     this.vals[3] = e21;
/* 120 */     this.vals[4] = e22;
/* 121 */     this.vals[5] = e23;
/* 122 */     this.vals[6] = e31;
/* 123 */     this.vals[7] = e32;
/* 124 */     this.vals[8] = e33;
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
/*     */   public Matrix(float a, float b, float c, float d, float e, float f) {
/* 139 */     this.vals[0] = a;
/* 140 */     this.vals[1] = b;
/* 141 */     this.vals[2] = 0.0F;
/* 142 */     this.vals[3] = c;
/* 143 */     this.vals[4] = d;
/* 144 */     this.vals[5] = 0.0F;
/* 145 */     this.vals[6] = e;
/* 146 */     this.vals[7] = f;
/* 147 */     this.vals[8] = 1.0F;
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
/*     */   public float get(int index) {
/* 162 */     return this.vals[index];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Matrix multiply(Matrix by) {
/* 172 */     Matrix rslt = new Matrix();
/*     */     
/* 174 */     float[] a = this.vals;
/* 175 */     float[] b = by.vals;
/* 176 */     float[] c = rslt.vals;
/*     */     
/* 178 */     c[0] = a[0] * b[0] + a[1] * b[3] + a[2] * b[6];
/* 179 */     c[1] = a[0] * b[1] + a[1] * b[4] + a[2] * b[7];
/* 180 */     c[2] = a[0] * b[2] + a[1] * b[5] + a[2] * b[8];
/* 181 */     c[3] = a[3] * b[0] + a[4] * b[3] + a[5] * b[6];
/* 182 */     c[4] = a[3] * b[1] + a[4] * b[4] + a[5] * b[7];
/* 183 */     c[5] = a[3] * b[2] + a[4] * b[5] + a[5] * b[8];
/* 184 */     c[6] = a[6] * b[0] + a[7] * b[3] + a[8] * b[6];
/* 185 */     c[7] = a[6] * b[1] + a[7] * b[4] + a[8] * b[7];
/* 186 */     c[8] = a[6] * b[2] + a[7] * b[5] + a[8] * b[8];
/*     */     
/* 188 */     return rslt;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Matrix add(Matrix arg) {
/* 197 */     Matrix rslt = new Matrix();
/*     */     
/* 199 */     float[] a = this.vals;
/* 200 */     float[] b = arg.vals;
/* 201 */     float[] c = rslt.vals;
/*     */     
/* 203 */     c[0] = a[0] + b[0];
/* 204 */     c[1] = a[1] + b[1];
/* 205 */     c[2] = a[2] + b[2];
/* 206 */     c[3] = a[3] + b[3];
/* 207 */     c[4] = a[4] + b[4];
/* 208 */     c[5] = a[5] + b[5];
/* 209 */     c[6] = a[6] + b[6];
/* 210 */     c[7] = a[7] + b[7];
/* 211 */     c[8] = a[8] + b[8];
/*     */ 
/*     */     
/* 214 */     return rslt;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Matrix subtract(Matrix arg) {
/* 223 */     Matrix rslt = new Matrix();
/*     */     
/* 225 */     float[] a = this.vals;
/* 226 */     float[] b = arg.vals;
/* 227 */     float[] c = rslt.vals;
/*     */     
/* 229 */     c[0] = a[0] - b[0];
/* 230 */     c[1] = a[1] - b[1];
/* 231 */     c[2] = a[2] - b[2];
/* 232 */     c[3] = a[3] - b[3];
/* 233 */     c[4] = a[4] - b[4];
/* 234 */     c[5] = a[5] - b[5];
/* 235 */     c[6] = a[6] - b[6];
/* 236 */     c[7] = a[7] - b[7];
/* 237 */     c[8] = a[8] - b[8];
/*     */     
/* 239 */     return rslt;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getDeterminant() {
/* 250 */     return this.vals[0] * this.vals[4] * this.vals[8] + this.vals[1] * this.vals[5] * this.vals[6] + this.vals[2] * this.vals[3] * this.vals[7] - this.vals[0] * this.vals[5] * this.vals[7] - this.vals[1] * this.vals[3] * this.vals[8] - this.vals[2] * this.vals[4] * this.vals[6];
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
/*     */   public boolean equals(Object obj) {
/* 266 */     if (!(obj instanceof Matrix)) {
/* 267 */       return false;
/*     */     }
/* 269 */     return Arrays.equals(this.vals, ((Matrix)obj).vals);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 279 */     return Arrays.hashCode(this.vals);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 289 */     return this.vals[0] + "\t" + this.vals[1] + "\t" + this.vals[2] + "\n" + this.vals[3] + "\t" + this.vals[4] + "\t" + this.vals[5] + "\n" + this.vals[6] + "\t" + this.vals[7] + "\t" + this.vals[8];
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/kernel/geom/Matrix.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */