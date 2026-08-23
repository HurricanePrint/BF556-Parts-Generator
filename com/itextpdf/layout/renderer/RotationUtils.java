/*     */ package com.itextpdf.layout.renderer;
/*     */ 
/*     */ import com.itextpdf.kernel.geom.Rectangle;
/*     */ import com.itextpdf.layout.layout.LayoutArea;
/*     */ import com.itextpdf.layout.layout.LayoutContext;
/*     */ import com.itextpdf.layout.layout.LayoutResult;
/*     */ import com.itextpdf.layout.minmaxwidth.MinMaxWidth;
/*     */ import com.itextpdf.layout.minmaxwidth.MinMaxWidthUtils;
/*     */ import com.itextpdf.layout.minmaxwidth.RotationMinMaxWidth;
/*     */ import java.util.HashMap;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ final class RotationUtils
/*     */ {
/*     */   public static MinMaxWidth countRotationMinMaxWidth(MinMaxWidth minMaxWidth, AbstractRenderer renderer) {
/*  73 */     PropertiesBackup backup = new PropertiesBackup(renderer);
/*  74 */     Float rotation = backup.storeFloatProperty(55);
/*  75 */     if (rotation != null) {
/*  76 */       float angle = rotation.floatValue();
/*     */       
/*  78 */       float layoutWidth = minMaxWidth.getMaxWidth() + MinMaxWidthUtils.getEps();
/*  79 */       LayoutResult layoutResult = renderer.layout(new LayoutContext(new LayoutArea(1, new Rectangle(layoutWidth, 1000000.0F))));
/*  80 */       if (layoutResult.getOccupiedArea() != null) {
/*  81 */         Rectangle layoutBBox = layoutResult.getOccupiedArea().getBBox();
/*  82 */         if (MinMaxWidthUtils.isEqual(minMaxWidth.getMinWidth(), minMaxWidth.getMaxWidth())) {
/*  83 */           backup.restoreProperty(55);
/*  84 */           float rotatedWidth = (float)RotationMinMaxWidth.calculateRotatedWidth(layoutBBox, angle);
/*  85 */           return new MinMaxWidth(rotatedWidth, rotatedWidth, 0.0F);
/*     */         } 
/*  87 */         double area = (layoutResult.getOccupiedArea().getBBox().getWidth() * layoutResult.getOccupiedArea().getBBox().getHeight());
/*  88 */         RotationMinMaxWidth rotationMinMaxWidth = RotationMinMaxWidth.calculate(angle, area, minMaxWidth);
/*  89 */         Float rotatedMinWidth = getLayoutRotatedWidth(renderer, (float)rotationMinMaxWidth.getMinWidthOrigin(), layoutBBox, angle);
/*  90 */         if (rotatedMinWidth != null) {
/*  91 */           if (rotatedMinWidth.floatValue() > rotationMinMaxWidth.getMaxWidth()) {
/*  92 */             rotationMinMaxWidth.setChildrenMinWidth(rotatedMinWidth.floatValue());
/*  93 */             Float rotatedMaxWidth = getLayoutRotatedWidth(renderer, (float)rotationMinMaxWidth.getMaxWidthOrigin(), layoutBBox, angle);
/*  94 */             if (rotatedMaxWidth != null && rotatedMaxWidth.floatValue() > rotatedMinWidth.floatValue()) {
/*  95 */               rotationMinMaxWidth.setChildrenMaxWidth(rotatedMaxWidth.floatValue());
/*     */             } else {
/*  97 */               rotationMinMaxWidth.setChildrenMaxWidth(rotatedMinWidth.floatValue());
/*     */             } 
/*     */           } else {
/* 100 */             rotationMinMaxWidth.setChildrenMinWidth(rotatedMinWidth.floatValue());
/*     */           } 
/* 102 */           backup.restoreProperty(55);
/* 103 */           return (MinMaxWidth)rotationMinMaxWidth;
/*     */         } 
/*     */       } 
/*     */     } 
/* 107 */     backup.restoreProperty(55);
/* 108 */     return minMaxWidth;
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
/*     */   public static Float retrieveRotatedLayoutWidth(float availableWidth, AbstractRenderer renderer) {
/* 123 */     PropertiesBackup backup = new PropertiesBackup(renderer);
/* 124 */     Float rotation = backup.storeFloatProperty(55);
/* 125 */     if (rotation != null && renderer.getProperty(77) == null) {
/* 126 */       float angle = rotation.floatValue();
/* 127 */       backup.storeProperty(27);
/* 128 */       backup.storeProperty(85);
/* 129 */       backup.storeProperty(84);
/* 130 */       MinMaxWidth minMaxWidth = renderer.getMinMaxWidth();
/*     */       
/* 132 */       float length = (minMaxWidth.getMaxWidth() + minMaxWidth.getMinWidth()) / 2.0F + MinMaxWidthUtils.getEps();
/* 133 */       LayoutResult layoutResult = renderer.layout(new LayoutContext(new LayoutArea(1, new Rectangle(length, 1000000.0F))));
/* 134 */       backup.restoreProperty(27);
/* 135 */       backup.restoreProperty(85);
/* 136 */       backup.restoreProperty(84);
/*     */       
/* 138 */       Rectangle additions = new Rectangle(0.0F, 0.0F);
/* 139 */       renderer.applyPaddings(additions, true);
/* 140 */       renderer.applyBorderBox(additions, true);
/* 141 */       renderer.applyMargins(additions, true);
/*     */       
/* 143 */       if (layoutResult.getOccupiedArea() != null) {
/* 144 */         double area = (layoutResult.getOccupiedArea().getBBox().getWidth() * layoutResult.getOccupiedArea().getBBox().getHeight());
/* 145 */         RotationMinMaxWidth result = RotationMinMaxWidth.calculate(angle, area, minMaxWidth, availableWidth);
/* 146 */         if (result != null) {
/* 147 */           backup.restoreProperty(55);
/* 148 */           if (result.getMaxWidthHeight() > result.getMinWidthHeight()) {
/* 149 */             return Float.valueOf((float)(result.getMinWidthOrigin() - additions.getWidth() + MinMaxWidthUtils.getEps()));
/*     */           }
/* 151 */           return Float.valueOf((float)(result.getMaxWidthOrigin() - additions.getWidth() + MinMaxWidthUtils.getEps()));
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 156 */     backup.restoreProperty(55);
/* 157 */     return renderer.retrieveWidth(availableWidth);
/*     */   }
/*     */ 
/*     */   
/*     */   private static Float getLayoutRotatedWidth(AbstractRenderer renderer, float availableWidth, Rectangle previousBBox, double angle) {
/* 162 */     if (MinMaxWidthUtils.isEqual(availableWidth, previousBBox.getWidth())) {
/* 163 */       return Float.valueOf((float)RotationMinMaxWidth.calculateRotatedWidth(previousBBox, angle));
/*     */     }
/* 165 */     LayoutResult result = renderer.layout(new LayoutContext(new LayoutArea(1, new Rectangle(availableWidth + MinMaxWidthUtils.getEps(), 1000000.0F))));
/* 166 */     if (result.getOccupiedArea() != null) {
/* 167 */       return Float.valueOf((float)RotationMinMaxWidth.calculateRotatedWidth(result.getOccupiedArea().getBBox(), angle));
/*     */     }
/* 169 */     return null;
/*     */   }
/*     */   
/*     */   private static class PropertiesBackup
/*     */   {
/*     */     private AbstractRenderer renderer;
/* 175 */     private HashMap<Integer, PropertyBackup> propertiesBackup = new HashMap<>();
/*     */     
/*     */     public PropertiesBackup(AbstractRenderer renderer) {
/* 178 */       this.renderer = renderer;
/*     */     }
/*     */ 
/*     */     
/*     */     public Float storeFloatProperty(int property) {
/* 183 */       Float value = this.renderer.getPropertyAsFloat(property);
/* 184 */       if (value != null) {
/* 185 */         this.propertiesBackup.put(Integer.valueOf(property), new PropertyBackup(value, this.renderer.hasOwnProperty(property)));
/* 186 */         this.renderer.setProperty(property, (Object)null);
/*     */       } 
/* 188 */       return value;
/*     */     }
/*     */     
/*     */     public <T> T storeProperty(int property) {
/* 192 */       T value = this.renderer.getProperty(property);
/* 193 */       if (value != null) {
/* 194 */         this.propertiesBackup.put(Integer.valueOf(property), new PropertyBackup(value, this.renderer.hasOwnProperty(property)));
/* 195 */         this.renderer.setProperty(property, (Object)null);
/*     */       } 
/* 197 */       return value;
/*     */     }
/*     */     
/*     */     public void restoreProperty(int property) {
/* 201 */       PropertyBackup backup = this.propertiesBackup.remove(Integer.valueOf(property));
/* 202 */       if (backup != null)
/* 203 */         if (backup.isOwnedByRender()) {
/* 204 */           this.renderer.setProperty(property, backup.getValue());
/*     */         } else {
/* 206 */           this.renderer.deleteOwnProperty(property);
/*     */         }  
/*     */     }
/*     */     
/*     */     private static class PropertyBackup
/*     */     {
/*     */       private Object propertyValue;
/*     */       private boolean isOwnedByRender;
/*     */       
/*     */       public PropertyBackup(Object propertyValue, boolean isOwnedByRender) {
/* 216 */         this.propertyValue = propertyValue;
/* 217 */         this.isOwnedByRender = isOwnedByRender;
/*     */       }
/*     */       
/*     */       public Object getValue() {
/* 221 */         return this.propertyValue;
/*     */       }
/*     */       
/*     */       public boolean isOwnedByRender() {
/* 225 */         return this.isOwnedByRender;
/*     */       }
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/layout/renderer/RotationUtils.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */