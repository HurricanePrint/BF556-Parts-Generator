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
/*     */ public class BackgroundPosition
/*     */ {
/*  45 */   private UnitValue xShift = new UnitValue(1, 0.0F);
/*  46 */   private UnitValue yShift = new UnitValue(1, 0.0F);
/*  47 */   private PositionX positionX = PositionX.LEFT;
/*  48 */   private PositionY positionY = PositionY.TOP;
/*     */ 
/*     */   
/*     */   private static final double EPS = 9.999999747378752E-5D;
/*     */ 
/*     */   
/*     */   private static final int FULL_VALUE = 100;
/*     */   
/*     */   private static final int HALF_VALUE = 50;
/*     */ 
/*     */   
/*     */   public void calculatePositionValues(float fullWidth, float fullHeight, UnitValue outXValue, UnitValue outYValue) {
/*  60 */     int posMultiplier = parsePositionXToUnitValueAndReturnMultiplier(outXValue);
/*  61 */     if (posMultiplier == 0 && this.xShift != null && Math.abs(this.xShift.getValue()) > 9.999999747378752E-5D) {
/*  62 */       outXValue.setValue(0.0F);
/*     */     } else {
/*  64 */       outXValue.setValue(
/*  65 */           calculateValue(outXValue, fullWidth) + calculateValue(this.xShift, fullWidth) * posMultiplier);
/*     */     } 
/*  67 */     outXValue.setUnitType(1);
/*     */     
/*  69 */     posMultiplier = parsePositionYToUnitValueAndReturnMultiplier(outYValue);
/*  70 */     if (posMultiplier == 0 && this.yShift != null && Math.abs(this.yShift.getValue()) > 9.999999747378752E-5D) {
/*  71 */       outYValue.setValue(0.0F);
/*     */     } else {
/*  73 */       outYValue.setValue(
/*  74 */           calculateValue(outYValue, fullHeight) + calculateValue(this.yShift, fullHeight) * posMultiplier);
/*     */     } 
/*  76 */     outYValue.setUnitType(1);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PositionX getPositionX() {
/*  85 */     return this.positionX;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BackgroundPosition setPositionX(PositionX xPosition) {
/*  95 */     this.positionX = xPosition;
/*  96 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PositionY getPositionY() {
/* 105 */     return this.positionY;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BackgroundPosition setPositionY(PositionY yPosition) {
/* 115 */     this.positionY = yPosition;
/* 116 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public UnitValue getXShift() {
/* 125 */     return this.xShift;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BackgroundPosition setXShift(UnitValue xShift) {
/* 135 */     this.xShift = xShift;
/* 136 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public UnitValue getYShift() {
/* 145 */     return this.yShift;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BackgroundPosition setYShift(UnitValue yShift) {
/* 155 */     this.yShift = yShift;
/* 156 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean equals(Object o) {
/* 166 */     if (this == o) {
/* 167 */       return true;
/*     */     }
/* 169 */     if (o == null || getClass() != o.getClass()) {
/* 170 */       return false;
/*     */     }
/* 172 */     BackgroundPosition position = (BackgroundPosition)o;
/* 173 */     return (Objects.equals(this.positionX, position.positionX) && 
/* 174 */       Objects.equals(this.positionY, position.positionY) && 
/* 175 */       Objects.equals(this.xShift, position.xShift) && 
/* 176 */       Objects.equals(this.yShift, position.yShift));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 186 */     return Objects.hash(new Object[] { Integer.valueOf(this.positionX.ordinal()), Integer.valueOf(this.positionY.ordinal()), this.xShift, this.yShift });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private int parsePositionXToUnitValueAndReturnMultiplier(UnitValue outValue) {
/* 196 */     outValue.setUnitType(2);
/* 197 */     switch (this.positionX) {
/*     */       case TOP:
/* 199 */         outValue.setValue(0.0F);
/* 200 */         return 1;
/*     */       case BOTTOM:
/* 202 */         outValue.setValue(100.0F);
/* 203 */         return -1;
/*     */       case CENTER:
/* 205 */         outValue.setValue(50.0F);
/* 206 */         return 0;
/*     */     } 
/* 208 */     return 0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private int parsePositionYToUnitValueAndReturnMultiplier(UnitValue outValue) {
/* 219 */     outValue.setUnitType(2);
/* 220 */     switch (this.positionY) {
/*     */       case TOP:
/* 222 */         outValue.setValue(0.0F);
/* 223 */         return 1;
/*     */       case BOTTOM:
/* 225 */         outValue.setValue(100.0F);
/* 226 */         return -1;
/*     */       case CENTER:
/* 228 */         outValue.setValue(50.0F);
/* 229 */         return 0;
/*     */     } 
/* 231 */     return 0;
/*     */   }
/*     */ 
/*     */   
/*     */   private static float calculateValue(UnitValue value, float fullValue) {
/* 236 */     if (value == null) {
/* 237 */       return 0.0F;
/*     */     }
/* 239 */     return value.isPercentValue() ? (value.getValue() / 100.0F * fullValue) : value.getValue();
/*     */   }
/*     */   
/*     */   public enum PositionX {
/* 243 */     LEFT,
/* 244 */     RIGHT,
/* 245 */     CENTER;
/*     */   }
/*     */   
/*     */   public enum PositionY {
/* 249 */     TOP,
/* 250 */     BOTTOM,
/* 251 */     CENTER;
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/layout/property/BackgroundPosition.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */