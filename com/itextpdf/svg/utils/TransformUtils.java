/*     */ package com.itextpdf.svg.utils;
/*     */ 
/*     */ import com.itextpdf.kernel.geom.AffineTransform;
/*     */ import com.itextpdf.styledxmlparser.css.util.CssUtils;
/*     */ import com.itextpdf.svg.exceptions.SvgProcessingException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.StringTokenizer;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class TransformUtils
/*     */ {
/*     */   private static final String MATRIX = "MATRIX";
/*     */   private static final String ROTATE = "ROTATE";
/*     */   private static final String SCALE = "SCALE";
/*     */   private static final String SKEWX = "SKEWX";
/*     */   private static final String SKEWY = "SKEWY";
/*     */   private static final String TRANSLATE = "TRANSLATE";
/*     */   
/*     */   public static AffineTransform parseTransform(String transform) {
/* 133 */     if (transform == null) {
/* 134 */       throw new SvgProcessingException("The transformation value is null.");
/*     */     }
/*     */     
/* 137 */     if (transform.isEmpty()) {
/* 138 */       throw new SvgProcessingException("The transformation value is empty.");
/*     */     }
/*     */     
/* 141 */     AffineTransform matrix = new AffineTransform();
/*     */     
/* 143 */     List<String> listWithTransformations = splitString(transform);
/*     */     
/* 145 */     for (String transformation : listWithTransformations) {
/* 146 */       AffineTransform newMatrix = transformationStringToMatrix(transformation);
/*     */       
/* 148 */       if (newMatrix != null) {
/* 149 */         matrix.concatenate(newMatrix);
/*     */       }
/*     */     } 
/*     */     
/* 153 */     return matrix;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static List<String> splitString(String transform) {
/* 164 */     ArrayList<String> list = new ArrayList<>();
/* 165 */     StringTokenizer tokenizer = new StringTokenizer(transform, ")", false);
/*     */     
/* 167 */     while (tokenizer.hasMoreTokens()) {
/* 168 */       String trim = tokenizer.nextToken().trim();
/*     */       
/* 170 */       if (trim != null && !trim.isEmpty()) {
/* 171 */         list.add(trim + ")");
/*     */       }
/*     */     } 
/*     */     
/* 175 */     return list;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static AffineTransform transformationStringToMatrix(String transformation) {
/* 185 */     String name = getNameFromString(transformation).toUpperCase();
/*     */     
/* 187 */     if (name.isEmpty()) {
/* 188 */       throw new SvgProcessingException("Transformation declaration is not formed correctly.");
/*     */     }
/* 190 */     switch (name) {
/*     */       case "MATRIX":
/* 192 */         return createMatrixTransformation(getValuesFromTransformationString(transformation));
/*     */       case "TRANSLATE":
/* 194 */         return createTranslateTransformation(getValuesFromTransformationString(transformation));
/*     */       case "SCALE":
/* 196 */         return createScaleTransformation(getValuesFromTransformationString(transformation));
/*     */       case "ROTATE":
/* 198 */         return createRotationTransformation(getValuesFromTransformationString(transformation));
/*     */       case "SKEWX":
/* 200 */         return createSkewXTransformation(getValuesFromTransformationString(transformation));
/*     */       case "SKEWY":
/* 202 */         return createSkewYTransformation(getValuesFromTransformationString(transformation));
/*     */     } 
/* 204 */     throw new SvgProcessingException("Unsupported type of transformation.");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static AffineTransform createSkewYTransformation(List<String> values) {
/* 215 */     if (values.size() != 1) {
/* 216 */       throw new SvgProcessingException("Transformation doesn't contain the right number of values.");
/*     */     }
/*     */     
/* 219 */     double tan = Math.tan(Math.toRadians(CssUtils.parseFloat(values.get(0)).floatValue()));
/*     */ 
/*     */     
/* 222 */     return new AffineTransform(1.0D, tan, 0.0D, 1.0D, 0.0D, 0.0D);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static AffineTransform createSkewXTransformation(List<String> values) {
/* 232 */     if (values.size() != 1) {
/* 233 */       throw new SvgProcessingException("Transformation doesn't contain the right number of values.");
/*     */     }
/*     */     
/* 236 */     double tan = Math.tan(Math.toRadians(CssUtils.parseFloat(values.get(0)).floatValue()));
/*     */ 
/*     */     
/* 239 */     return new AffineTransform(1.0D, 0.0D, tan, 1.0D, 0.0D, 0.0D);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static AffineTransform createRotationTransformation(List<String> values) {
/* 249 */     if (values.size() != 1 && values.size() != 3) {
/* 250 */       throw new SvgProcessingException("Transformation doesn't contain the right number of values.");
/*     */     }
/*     */     
/* 253 */     double angle = Math.toRadians(CssUtils.parseFloat(values.get(0)).floatValue());
/*     */     
/* 255 */     if (values.size() == 3) {
/* 256 */       float centerX = CssUtils.parseAbsoluteLength(values.get(1));
/* 257 */       float centerY = CssUtils.parseAbsoluteLength(values.get(2));
/* 258 */       return AffineTransform.getRotateInstance(angle, centerX, centerY);
/*     */     } 
/*     */     
/* 261 */     return AffineTransform.getRotateInstance(angle);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static AffineTransform createScaleTransformation(List<String> values) {
/* 271 */     if (values.size() == 0 || values.size() > 2) {
/* 272 */       throw new SvgProcessingException("Transformation doesn't contain the right number of values.");
/*     */     }
/*     */     
/* 275 */     float scaleX = CssUtils.parseRelativeValue(values.get(0), 1.0F);
/* 276 */     float scaleY = (values.size() == 2) ? CssUtils.parseRelativeValue(values.get(1), 1.0F) : scaleX;
/*     */     
/* 278 */     return AffineTransform.getScaleInstance(scaleX, scaleY);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static AffineTransform createTranslateTransformation(List<String> values) {
/* 288 */     if (values.size() == 0 || values.size() > 2) {
/* 289 */       throw new SvgProcessingException("Transformation doesn't contain the right number of values.");
/*     */     }
/*     */     
/* 292 */     float translateX = CssUtils.parseAbsoluteLength(values.get(0));
/* 293 */     float translateY = (values.size() == 2) ? CssUtils.parseAbsoluteLength(values.get(1)) : 0.0F;
/*     */     
/* 295 */     return AffineTransform.getTranslateInstance(translateX, translateY);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static AffineTransform createMatrixTransformation(List<String> values) {
/* 305 */     if (values.size() != 6) {
/* 306 */       throw new SvgProcessingException("Transformation doesn't contain the right number of values.");
/*     */     }
/*     */     
/* 309 */     float a = Float.parseFloat(values.get(0));
/* 310 */     float b = Float.parseFloat(values.get(1));
/* 311 */     float c = Float.parseFloat(values.get(2));
/* 312 */     float d = Float.parseFloat(values.get(3));
/* 313 */     float e = CssUtils.parseAbsoluteLength(values.get(4));
/* 314 */     float f = CssUtils.parseAbsoluteLength(values.get(5));
/*     */     
/* 316 */     return new AffineTransform(a, b, c, d, e, f);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static String getNameFromString(String transformation) {
/* 326 */     int indexOfParenthesis = transformation.indexOf("(");
/*     */     
/* 328 */     if (indexOfParenthesis == -1) {
/* 329 */       throw new SvgProcessingException("Transformation declaration is not formed correctly.");
/*     */     }
/*     */     
/* 332 */     return transformation.substring(0, transformation.indexOf("("));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static List<String> getValuesFromTransformationString(String transformation) {
/* 342 */     String numbers = transformation.substring(transformation.indexOf('(') + 1, transformation.indexOf(')'));
/*     */     
/* 344 */     return SvgCssUtils.splitValueList(numbers);
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/svg/utils/TransformUtils.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */