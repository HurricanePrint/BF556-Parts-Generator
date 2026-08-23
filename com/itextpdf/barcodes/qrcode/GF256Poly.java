/*     */ package com.itextpdf.barcodes.qrcode;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ final class GF256Poly
/*     */ {
/*     */   private final GF256 field;
/*     */   private final int[] coefficients;
/*     */   
/*     */   GF256Poly(GF256 field, int[] coefficients) {
/*  70 */     if (coefficients == null || coefficients.length == 0) {
/*  71 */       throw new IllegalArgumentException();
/*     */     }
/*  73 */     this.field = field;
/*  74 */     int coefficientsLength = coefficients.length;
/*  75 */     if (coefficientsLength > 1 && coefficients[0] == 0) {
/*     */       
/*  77 */       int firstNonZero = 1;
/*  78 */       while (firstNonZero < coefficientsLength && coefficients[firstNonZero] == 0) {
/*  79 */         firstNonZero++;
/*     */       }
/*  81 */       if (firstNonZero == coefficientsLength) {
/*  82 */         this.coefficients = (field.getZero()).coefficients;
/*     */       } else {
/*  84 */         this.coefficients = new int[coefficientsLength - firstNonZero];
/*  85 */         System.arraycopy(coefficients, firstNonZero, this.coefficients, 0, this.coefficients.length);
/*     */       
/*     */       }
/*     */     
/*     */     }
/*     */     else {
/*     */       
/*  92 */       this.coefficients = coefficients;
/*     */     } 
/*     */   }
/*     */   
/*     */   int[] getCoefficients() {
/*  97 */     return this.coefficients;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   int getDegree() {
/* 104 */     return this.coefficients.length - 1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   boolean isZero() {
/* 111 */     return (this.coefficients[0] == 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   int getCoefficient(int degree) {
/* 118 */     return this.coefficients[this.coefficients.length - 1 - degree];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   int evaluateAt(int a) {
/* 125 */     if (a == 0)
/*     */     {
/* 127 */       return getCoefficient(0);
/*     */     }
/* 129 */     int size = this.coefficients.length;
/* 130 */     if (a == 1) {
/*     */       
/* 132 */       int j = 0;
/* 133 */       for (int k = 0; k < size; k++) {
/* 134 */         j = GF256.addOrSubtract(j, this.coefficients[k]);
/*     */       }
/* 136 */       return j;
/*     */     } 
/* 138 */     int result = this.coefficients[0];
/* 139 */     for (int i = 1; i < size; i++) {
/* 140 */       result = GF256.addOrSubtract(this.field.multiply(a, result), this.coefficients[i]);
/*     */     }
/* 142 */     return result;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   GF256Poly addOrSubtract(GF256Poly other) {
/* 152 */     if (!this.field.equals(other.field)) {
/* 153 */       throw new IllegalArgumentException("GF256Polys do not have same GF256 field");
/*     */     }
/* 155 */     if (isZero()) {
/* 156 */       return other;
/*     */     }
/* 158 */     if (other.isZero()) {
/* 159 */       return this;
/*     */     }
/*     */     
/* 162 */     int[] smallerCoefficients = this.coefficients;
/* 163 */     int[] largerCoefficients = other.coefficients;
/* 164 */     if (smallerCoefficients.length > largerCoefficients.length) {
/* 165 */       int[] temp = smallerCoefficients;
/* 166 */       smallerCoefficients = largerCoefficients;
/* 167 */       largerCoefficients = temp;
/*     */     } 
/* 169 */     int[] sumDiff = new int[largerCoefficients.length];
/* 170 */     int lengthDiff = largerCoefficients.length - smallerCoefficients.length;
/*     */     
/* 172 */     System.arraycopy(largerCoefficients, 0, sumDiff, 0, lengthDiff);
/*     */     
/* 174 */     for (int i = lengthDiff; i < largerCoefficients.length; i++) {
/* 175 */       sumDiff[i] = GF256.addOrSubtract(smallerCoefficients[i - lengthDiff], largerCoefficients[i]);
/*     */     }
/*     */     
/* 178 */     return new GF256Poly(this.field, sumDiff);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   GF256Poly multiply(GF256Poly other) {
/* 187 */     if (!this.field.equals(other.field)) {
/* 188 */       throw new IllegalArgumentException("GF256Polys do not have same GF256 field");
/*     */     }
/* 190 */     if (isZero() || other.isZero()) {
/* 191 */       return this.field.getZero();
/*     */     }
/* 193 */     int[] aCoefficients = this.coefficients;
/* 194 */     int aLength = aCoefficients.length;
/* 195 */     int[] bCoefficients = other.coefficients;
/* 196 */     int bLength = bCoefficients.length;
/* 197 */     int[] product = new int[aLength + bLength - 1];
/* 198 */     for (int i = 0; i < aLength; i++) {
/* 199 */       int aCoeff = aCoefficients[i];
/* 200 */       for (int j = 0; j < bLength; j++) {
/* 201 */         product[i + j] = GF256.addOrSubtract(product[i + j], this.field
/* 202 */             .multiply(aCoeff, bCoefficients[j]));
/*     */       }
/*     */     } 
/* 205 */     return new GF256Poly(this.field, product);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   GF256Poly multiply(int scalar) {
/* 214 */     if (scalar == 0) {
/* 215 */       return this.field.getZero();
/*     */     }
/* 217 */     if (scalar == 1) {
/* 218 */       return this;
/*     */     }
/* 220 */     int size = this.coefficients.length;
/* 221 */     int[] product = new int[size];
/* 222 */     for (int i = 0; i < size; i++) {
/* 223 */       product[i] = this.field.multiply(this.coefficients[i], scalar);
/*     */     }
/* 225 */     return new GF256Poly(this.field, product);
/*     */   }
/*     */   
/*     */   GF256Poly multiplyByMonomial(int degree, int coefficient) {
/* 229 */     if (degree < 0) {
/* 230 */       throw new IllegalArgumentException();
/*     */     }
/* 232 */     if (coefficient == 0) {
/* 233 */       return this.field.getZero();
/*     */     }
/* 235 */     int size = this.coefficients.length;
/* 236 */     int[] product = new int[size + degree];
/* 237 */     for (int i = 0; i < size; i++) {
/* 238 */       product[i] = this.field.multiply(this.coefficients[i], coefficient);
/*     */     }
/* 240 */     return new GF256Poly(this.field, product);
/*     */   }
/*     */   
/*     */   GF256Poly[] divide(GF256Poly other) {
/* 244 */     if (!this.field.equals(other.field)) {
/* 245 */       throw new IllegalArgumentException("GF256Polys do not have same GF256 field");
/*     */     }
/* 247 */     if (other.isZero()) {
/* 248 */       throw new IllegalArgumentException("Divide by 0");
/*     */     }
/*     */     
/* 251 */     GF256Poly quotient = this.field.getZero();
/* 252 */     GF256Poly remainder = this;
/*     */     
/* 254 */     int denominatorLeadingTerm = other.getCoefficient(other.getDegree());
/* 255 */     int inverseDenominatorLeadingTerm = this.field.inverse(denominatorLeadingTerm);
/*     */     
/* 257 */     while (remainder.getDegree() >= other.getDegree() && !remainder.isZero()) {
/* 258 */       int degreeDifference = remainder.getDegree() - other.getDegree();
/* 259 */       int scale = this.field.multiply(remainder.getCoefficient(remainder.getDegree()), inverseDenominatorLeadingTerm);
/* 260 */       GF256Poly term = other.multiplyByMonomial(degreeDifference, scale);
/* 261 */       GF256Poly iterationQuotient = this.field.buildMonomial(degreeDifference, scale);
/* 262 */       quotient = quotient.addOrSubtract(iterationQuotient);
/* 263 */       remainder = remainder.addOrSubtract(term);
/*     */     } 
/*     */     
/* 266 */     return new GF256Poly[] { quotient, remainder };
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 273 */     StringBuffer result = new StringBuffer(8 * getDegree());
/* 274 */     for (int degree = getDegree(); degree >= 0; degree--) {
/* 275 */       int coefficient = getCoefficient(degree);
/* 276 */       if (coefficient != 0) {
/* 277 */         if (coefficient < 0) {
/* 278 */           result.append(" - ");
/* 279 */           coefficient = -coefficient;
/*     */         }
/* 281 */         else if (result.length() > 0) {
/* 282 */           result.append(" + ");
/*     */         } 
/*     */         
/* 285 */         if (degree == 0 || coefficient != 1) {
/* 286 */           int alphaPower = this.field.log(coefficient);
/* 287 */           if (alphaPower == 0) {
/* 288 */             result.append('1');
/* 289 */           } else if (alphaPower == 1) {
/* 290 */             result.append('a');
/*     */           } else {
/* 292 */             result.append("a^");
/* 293 */             result.append(alphaPower);
/*     */           } 
/*     */         } 
/* 296 */         if (degree != 0) {
/* 297 */           if (degree == 1) {
/* 298 */             result.append('x');
/*     */           } else {
/* 300 */             result.append("x^");
/* 301 */             result.append(degree);
/*     */           } 
/*     */         }
/*     */       } 
/*     */     } 
/* 306 */     return result.toString();
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/barcodes/qrcode/GF256Poly.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */