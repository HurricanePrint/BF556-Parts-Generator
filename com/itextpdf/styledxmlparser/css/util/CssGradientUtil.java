/*     */ package com.itextpdf.styledxmlparser.css.util;
/*     */ 
/*     */ import com.itextpdf.io.util.MessageFormatUtil;
/*     */ import com.itextpdf.kernel.colors.gradients.AbstractLinearGradientBuilder;
/*     */ import com.itextpdf.kernel.colors.gradients.GradientColorStop;
/*     */ import com.itextpdf.kernel.colors.gradients.GradientSpreadMethod;
/*     */ import com.itextpdf.kernel.colors.gradients.StrategyBasedLinearGradientBuilder;
/*     */ import com.itextpdf.layout.property.UnitValue;
/*     */ import com.itextpdf.styledxmlparser.css.parse.CssDeclarationValueTokenizer;
/*     */ import com.itextpdf.styledxmlparser.exceptions.StyledXMLParserException;
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
/*     */ public final class CssGradientUtil
/*     */ {
/*     */   private static final String LINEAR_GRADIENT_FUNCTION_SUFFIX = "linear-gradient(";
/*     */   private static final String REPEATING_LINEAR_GRADIENT_FUNCTION_SUFFIX = "repeating-linear-gradient(";
/*     */   
/*     */   public static boolean isCssLinearGradientValue(String cssValue) {
/*  64 */     if (cssValue == null) {
/*  65 */       return false;
/*     */     }
/*  67 */     String normalizedValue = cssValue.toLowerCase().trim();
/*  68 */     return (normalizedValue.endsWith(")") && (normalizedValue
/*  69 */       .startsWith("linear-gradient(") || normalizedValue
/*  70 */       .startsWith("repeating-linear-gradient(")));
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
/*     */   public static StrategyBasedLinearGradientBuilder parseCssLinearGradient(String cssGradientValue, float emValue, float remValue) {
/*  87 */     if (isCssLinearGradientValue(cssGradientValue)) {
/*  88 */       cssGradientValue = cssGradientValue.toLowerCase().trim();
/*  89 */       boolean isRepeating = false;
/*  90 */       String argumentsPart = null;
/*  91 */       if (cssGradientValue.startsWith("linear-gradient(")) {
/*  92 */         argumentsPart = cssGradientValue.substring("linear-gradient("
/*  93 */             .length(), cssGradientValue.length() - 1);
/*  94 */         isRepeating = false;
/*  95 */       } else if (cssGradientValue.startsWith("repeating-linear-gradient(")) {
/*  96 */         argumentsPart = cssGradientValue.substring("repeating-linear-gradient("
/*  97 */             .length(), cssGradientValue.length() - 1);
/*  98 */         isRepeating = true;
/*     */       } 
/*     */       
/* 101 */       if (argumentsPart != null) {
/* 102 */         List<String> argumentsList = new ArrayList<>();
/* 103 */         StringBuilder buff = new StringBuilder();
/* 104 */         CssDeclarationValueTokenizer tokenizer = new CssDeclarationValueTokenizer(argumentsPart);
/*     */         CssDeclarationValueTokenizer.Token nextToken;
/* 106 */         while ((nextToken = tokenizer.getNextValidToken()) != null) {
/* 107 */           if (nextToken.getType() == CssDeclarationValueTokenizer.TokenType.COMMA) {
/* 108 */             if (buff.length() != 0) {
/* 109 */               argumentsList.add(buff.toString().trim());
/* 110 */               buff = new StringBuilder();
/*     */             }  continue;
/*     */           } 
/* 113 */           buff.append(" ").append(nextToken.getValue());
/*     */         } 
/*     */         
/* 116 */         if (buff.length() != 0) {
/* 117 */           argumentsList.add(buff.toString().trim());
/*     */         }
/* 119 */         if (argumentsList.isEmpty()) {
/* 120 */           throw new StyledXMLParserException(MessageFormatUtil.format("Invalid gradient function arguments list: {0}", new Object[] { cssGradientValue }));
/*     */         }
/*     */         
/* 123 */         return parseCssLinearGradient(argumentsList, isRepeating, emValue, remValue);
/*     */       } 
/*     */     } 
/* 126 */     return null;
/*     */   }
/*     */   
/*     */   private static StrategyBasedLinearGradientBuilder parseCssLinearGradient(List<String> argumentsList, boolean isRepeating, float emValue, float remValue) {
/*     */     int colorStopListStartIndex;
/* 131 */     StrategyBasedLinearGradientBuilder builder = new StrategyBasedLinearGradientBuilder();
/*     */     
/* 133 */     GradientSpreadMethod gradientSpreadMethod = isRepeating ? GradientSpreadMethod.REPEAT : GradientSpreadMethod.PAD;
/* 134 */     builder.setSpreadMethod(gradientSpreadMethod);
/*     */ 
/*     */     
/* 137 */     String firstArgument = argumentsList.get(0);
/* 138 */     if (CssUtils.isAngleValue(firstArgument)) {
/* 139 */       double radAngle = CssUtils.parseAngle(firstArgument);
/*     */       
/* 141 */       builder.setGradientDirectionAsCentralRotationAngle(-radAngle);
/* 142 */       colorStopListStartIndex = 1;
/* 143 */     } else if (firstArgument.startsWith("to ")) {
/* 144 */       StrategyBasedLinearGradientBuilder.GradientStrategy gradientStrategy = parseDirection(firstArgument);
/* 145 */       builder.setGradientDirectionAsStrategy(gradientStrategy);
/* 146 */       colorStopListStartIndex = 1;
/*     */     } else {
/*     */       
/* 149 */       builder.setGradientDirectionAsStrategy(StrategyBasedLinearGradientBuilder.GradientStrategy.TO_BOTTOM);
/* 150 */       colorStopListStartIndex = 0;
/*     */     } 
/*     */     
/* 153 */     addStopColors((AbstractLinearGradientBuilder)builder, argumentsList, colorStopListStartIndex, emValue, remValue);
/*     */     
/* 155 */     return builder;
/*     */   }
/*     */ 
/*     */   
/*     */   private static void addStopColors(AbstractLinearGradientBuilder builder, List<String> argumentsList, int stopsStartIndex, float emValue, float remValue) {
/* 160 */     GradientColorStop lastCreatedStopColor = null;
/* 161 */     int lastStopIndex = argumentsList.size() - 1;
/* 162 */     for (int i = stopsStartIndex; i <= lastStopIndex; i++) {
/* 163 */       String argument = argumentsList.get(i);
/* 164 */       List<String> elementsList = new ArrayList<>();
/* 165 */       CssDeclarationValueTokenizer tokenizer = new CssDeclarationValueTokenizer(argument);
/*     */       CssDeclarationValueTokenizer.Token nextToken;
/* 167 */       while ((nextToken = tokenizer.getNextValidToken()) != null) {
/* 168 */         elementsList.add(nextToken.getValue());
/*     */       }
/*     */       
/* 171 */       if (elementsList.isEmpty() || elementsList.size() > 3) {
/* 172 */         throw new StyledXMLParserException(
/* 173 */             MessageFormatUtil.format("Invalid color stop value: {0}", new Object[] { argument }));
/*     */       }
/* 175 */       if (CssUtils.isColorProperty(elementsList.get(0))) {
/* 176 */         float[] rgba = CssUtils.parseRgbaColor(elementsList.get(0));
/* 177 */         if (elementsList.size() == 1) {
/* 178 */           UnitValue offset = (i == stopsStartIndex) ? new UnitValue(2, 0.0F) : ((i == lastStopIndex) ? new UnitValue(2, 100.0F) : null);
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 183 */           lastCreatedStopColor = createStopColor(rgba, offset);
/* 184 */           builder.addColorStop(lastCreatedStopColor);
/*     */         } else {
/* 186 */           for (int j = 1; j < elementsList.size(); j++) {
/* 187 */             if (CssUtils.isNumericValue(elementsList.get(j)))
/*     */             {
/*     */               
/* 190 */               throw new StyledXMLParserException(
/* 191 */                   MessageFormatUtil.format("Invalid color stop value: {0}", new Object[] { argument }));
/*     */             }
/* 193 */             UnitValue offset = CssUtils.parseLengthValueToPt(elementsList.get(j), emValue, remValue);
/* 194 */             if (offset == null) {
/* 195 */               throw new StyledXMLParserException(
/* 196 */                   MessageFormatUtil.format("Invalid color stop value: {0}", new Object[] { argument }));
/*     */             }
/* 198 */             lastCreatedStopColor = createStopColor(rgba, offset);
/* 199 */             builder.addColorStop(lastCreatedStopColor);
/*     */           } 
/*     */         } 
/*     */       } else {
/*     */         
/* 204 */         if (elementsList.size() != 1 || lastCreatedStopColor == null || lastCreatedStopColor
/*     */           
/* 206 */           .getHintOffsetType() != GradientColorStop.HintOffsetType.NONE || i == lastStopIndex)
/*     */         {
/*     */ 
/*     */           
/* 210 */           throw new StyledXMLParserException(
/* 211 */               MessageFormatUtil.format("Invalid color stop value: {0}", new Object[] { argument }));
/*     */         }
/* 213 */         UnitValue hint = CssUtils.parseLengthValueToPt(elementsList.get(0), emValue, remValue);
/* 214 */         if (hint == null) {
/* 215 */           throw new StyledXMLParserException(
/* 216 */               MessageFormatUtil.format("Invalid color stop value: {0}", new Object[] { argument }));
/*     */         }
/* 218 */         if (hint.getUnitType() == 2) {
/* 219 */           lastCreatedStopColor.setHint((hint.getValue() / 100.0F), GradientColorStop.HintOffsetType.RELATIVE_ON_GRADIENT);
/*     */         } else {
/* 221 */           lastCreatedStopColor.setHint(hint.getValue(), GradientColorStop.HintOffsetType.ABSOLUTE_ON_GRADIENT);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private static StrategyBasedLinearGradientBuilder.GradientStrategy parseDirection(String argument) {
/* 228 */     String[] elementsList = argument.split("\\s+");
/* 229 */     if (elementsList.length < 2) {
/* 230 */       throw new StyledXMLParserException(
/* 231 */           MessageFormatUtil.format("Invalid direction string: {0}", new Object[] { argument }));
/*     */     }
/* 233 */     int topCount = 0;
/* 234 */     int bottomCount = 0;
/* 235 */     int leftCount = 0;
/* 236 */     int rightCount = 0;
/* 237 */     for (int i = 1; i < elementsList.length; i++) {
/* 238 */       if ("top".equals(elementsList[i])) {
/* 239 */         topCount++;
/* 240 */       } else if ("bottom".equals(elementsList[i])) {
/* 241 */         bottomCount++;
/* 242 */       } else if ("left".equals(elementsList[i])) {
/* 243 */         leftCount++;
/* 244 */       } else if ("right".equals(elementsList[i])) {
/* 245 */         rightCount++;
/*     */       } else {
/* 247 */         throw new StyledXMLParserException(
/* 248 */             MessageFormatUtil.format("Invalid direction string: {0}", new Object[] { argument }));
/*     */       } 
/*     */     } 
/*     */     
/* 252 */     if (topCount == 1 && bottomCount == 0) {
/* 253 */       if (leftCount == 1 && rightCount == 0)
/* 254 */         return StrategyBasedLinearGradientBuilder.GradientStrategy.TO_TOP_LEFT; 
/* 255 */       if (leftCount == 0 && rightCount == 1)
/* 256 */         return StrategyBasedLinearGradientBuilder.GradientStrategy.TO_TOP_RIGHT; 
/* 257 */       if (leftCount == 0 && rightCount == 0) {
/* 258 */         return StrategyBasedLinearGradientBuilder.GradientStrategy.TO_TOP;
/*     */       }
/* 260 */     } else if (topCount == 0 && bottomCount == 1) {
/* 261 */       if (leftCount == 1 && rightCount == 0)
/* 262 */         return StrategyBasedLinearGradientBuilder.GradientStrategy.TO_BOTTOM_LEFT; 
/* 263 */       if (leftCount == 0 && rightCount == 1)
/* 264 */         return StrategyBasedLinearGradientBuilder.GradientStrategy.TO_BOTTOM_RIGHT; 
/* 265 */       if (leftCount == 0 && rightCount == 0) {
/* 266 */         return StrategyBasedLinearGradientBuilder.GradientStrategy.TO_BOTTOM;
/*     */       }
/* 268 */     } else if (topCount == 0 && bottomCount == 0) {
/* 269 */       if (leftCount == 1 && rightCount == 0)
/* 270 */         return StrategyBasedLinearGradientBuilder.GradientStrategy.TO_LEFT; 
/* 271 */       if (leftCount == 0 && rightCount == 1) {
/* 272 */         return StrategyBasedLinearGradientBuilder.GradientStrategy.TO_RIGHT;
/*     */       }
/*     */     } 
/* 275 */     throw new StyledXMLParserException(
/* 276 */         MessageFormatUtil.format("Invalid direction string: {0}", new Object[] { argument }));
/*     */   }
/*     */   
/*     */   private static GradientColorStop createStopColor(float[] rgba, UnitValue offset) {
/*     */     GradientColorStop.OffsetType offsetType;
/*     */     double offsetValue;
/* 282 */     if (offset == null) {
/* 283 */       offsetType = GradientColorStop.OffsetType.AUTO;
/* 284 */       offsetValue = 0.0D;
/* 285 */     } else if (offset.getUnitType() == 1) {
/* 286 */       offsetType = GradientColorStop.OffsetType.ABSOLUTE;
/* 287 */       offsetValue = offset.getValue();
/*     */     } else {
/* 289 */       offsetType = GradientColorStop.OffsetType.RELATIVE;
/* 290 */       offsetValue = (offset.getValue() / 100.0F);
/*     */     } 
/*     */ 
/*     */     
/* 294 */     return new GradientColorStop(rgba, offsetValue, offsetType);
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/styledxmlparser/css/util/CssGradientUtil.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */