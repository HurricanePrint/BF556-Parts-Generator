/*     */ package com.itextpdf.layout.property;
/*     */ 
/*     */ import com.itextpdf.io.util.MessageFormatUtil;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class UnitValue
/*     */ {
/*     */   public static final int POINT = 1;
/*     */   public static final int PERCENT = 2;
/*     */   protected int unitType;
/*     */   protected float value;
/*     */   
/*     */   public UnitValue(int unitType, float value) {
/*  64 */     this.unitType = unitType;
/*  65 */     assert !Float.isNaN(value);
/*  66 */     this.value = value;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public UnitValue(UnitValue unitValue) {
/*  75 */     this(unitValue.unitType, unitValue.value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static UnitValue createPointValue(float value) {
/*  84 */     return new UnitValue(1, value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static UnitValue createPercentValue(float value) {
/*  93 */     return new UnitValue(2, value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static UnitValue[] createPercentArray(float[] values) {
/* 103 */     UnitValue[] resultArray = new UnitValue[values.length];
/* 104 */     float sum = 0.0F;
/* 105 */     for (float val : values) sum += val; 
/* 106 */     for (int i = 0; i < values.length; i++) {
/* 107 */       resultArray[i] = createPercentValue(100.0F * values[i] / sum);
/*     */     }
/* 109 */     return resultArray;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static UnitValue[] createPercentArray(int size) {
/* 119 */     UnitValue[] resultArray = new UnitValue[size];
/* 120 */     for (int i = 0; i < size; i++) {
/* 121 */       resultArray[i] = createPercentValue(100.0F / size);
/*     */     }
/* 123 */     return resultArray;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static UnitValue[] createPointArray(float[] values) {
/* 132 */     UnitValue[] resultArray = new UnitValue[values.length];
/* 133 */     for (int i = 0; i < values.length; i++) {
/* 134 */       resultArray[i] = createPointValue(values[i]);
/*     */     }
/* 136 */     return resultArray;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getUnitType() {
/* 144 */     return this.unitType;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setUnitType(int unitType) {
/* 152 */     this.unitType = unitType;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getValue() {
/* 160 */     return this.value;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setValue(float value) {
/* 168 */     assert !Float.isNaN(value);
/* 169 */     this.value = value;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isPointValue() {
/* 177 */     return (this.unitType == 1);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isPercentValue() {
/* 185 */     return (this.unitType == 2);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/* 190 */     if (getClass() != obj.getClass()) {
/* 191 */       return false;
/*     */     }
/* 193 */     UnitValue other = (UnitValue)obj;
/* 194 */     return (Integer.compare(this.unitType, other.unitType) == 0 && Float.compare(this.value, other.value) == 0);
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 199 */     int hash = 7;
/* 200 */     hash = 71 * hash + this.unitType;
/* 201 */     hash = 71 * hash + Float.floatToIntBits(this.value);
/* 202 */     return hash;
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 207 */     return MessageFormatUtil.format((this.unitType == 2) ? "{0}%" : "{0}pt", new Object[] { Float.valueOf(this.value) });
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/layout/property/UnitValue.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */