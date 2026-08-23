/*     */ package com.itextpdf.styledxmlparser.css.util;
/*     */ 
/*     */ import com.itextpdf.io.util.MessageFormatUtil;
/*     */ import com.itextpdf.kernel.colors.WebColors;
/*     */ import com.itextpdf.layout.font.Range;
/*     */ import com.itextpdf.layout.font.RangeBuilder;
/*     */ import com.itextpdf.layout.property.BlendMode;
/*     */ import com.itextpdf.layout.property.UnitValue;
/*     */ import com.itextpdf.styledxmlparser.css.CommonCssConstants;
/*     */ import com.itextpdf.styledxmlparser.css.parse.CssDeclarationValueTokenizer;
/*     */ import com.itextpdf.styledxmlparser.exceptions.StyledXMLParserException;
/*     */ import com.itextpdf.styledxmlparser.node.IElementNode;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import org.slf4j.Logger;
/*     */ import org.slf4j.LoggerFactory;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class CssUtils
/*     */ {
/*  72 */   private static final String[] ANGLE_MEASUREMENTS_VALUES = new String[] { "deg", "grad", "rad" };
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  77 */   private static final String[] RELATIVE_MEASUREMENTS_VALUES = new String[] { "%", "em", "ex", "rem" };
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  82 */   private static final String[] FONT_RELATIVE_MEASUREMENTS_VALUES = new String[] { "em", "ex", "rem" };
/*     */   
/*     */   private static final float EPSILON = 1.0E-6F;
/*     */   
/*  86 */   private static final Logger logger = LoggerFactory.getLogger(CssUtils.class);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static List<String> splitStringWithComma(String value) {
/* 101 */     if (value == null) {
/* 102 */       return new ArrayList<>();
/*     */     }
/* 104 */     List<String> resultList = new ArrayList<>();
/* 105 */     int lastComma = 0;
/* 106 */     int notClosedBrackets = 0;
/* 107 */     for (int i = 0; i < value.length(); i++) {
/* 108 */       if (value.charAt(i) == ',' && notClosedBrackets == 0) {
/* 109 */         resultList.add(value.substring(lastComma, i));
/* 110 */         lastComma = i + 1;
/*     */       } 
/* 112 */       if (value.charAt(i) == '(') {
/* 113 */         notClosedBrackets++;
/*     */       }
/* 115 */       if (value.charAt(i) == ')') {
/* 116 */         notClosedBrackets--;
/* 117 */         notClosedBrackets = Math.max(notClosedBrackets, 0);
/*     */       } 
/*     */     } 
/* 120 */     String lastToken = value.substring(lastComma);
/* 121 */     if (!lastToken.isEmpty()) {
/* 122 */       resultList.add(lastToken);
/*     */     }
/* 124 */     return resultList;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static BlendMode parseBlendMode(String cssValue) {
/* 135 */     if (cssValue == null) {
/* 136 */       return BlendMode.NORMAL;
/*     */     }
/*     */     
/* 139 */     switch (cssValue) {
/*     */       case "multiply":
/* 141 */         return BlendMode.MULTIPLY;
/*     */       case "screen":
/* 143 */         return BlendMode.SCREEN;
/*     */       case "overlay":
/* 145 */         return BlendMode.OVERLAY;
/*     */       case "darken":
/* 147 */         return BlendMode.DARKEN;
/*     */       case "lighten":
/* 149 */         return BlendMode.LIGHTEN;
/*     */       case "color-dodge":
/* 151 */         return BlendMode.COLOR_DODGE;
/*     */       case "color-burn":
/* 153 */         return BlendMode.COLOR_BURN;
/*     */       case "hard-light":
/* 155 */         return BlendMode.HARD_LIGHT;
/*     */       case "soft-light":
/* 157 */         return BlendMode.SOFT_LIGHT;
/*     */       case "difference":
/* 159 */         return BlendMode.DIFFERENCE;
/*     */       case "exclusion":
/* 161 */         return BlendMode.EXCLUSION;
/*     */       case "hue":
/* 163 */         return BlendMode.HUE;
/*     */       case "saturation":
/* 165 */         return BlendMode.SATURATION;
/*     */       case "color":
/* 167 */         return BlendMode.COLOR;
/*     */       case "luminosity":
/* 169 */         return BlendMode.LUMINOSITY;
/*     */     } 
/*     */     
/* 172 */     return BlendMode.NORMAL;
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
/*     */   public static List<List<String>> extractShorthandProperties(String str) {
/* 184 */     List<List<String>> result = new ArrayList<>();
/* 185 */     List<String> currentLayer = new ArrayList<>();
/* 186 */     CssDeclarationValueTokenizer tokenizer = new CssDeclarationValueTokenizer(str);
/*     */     
/* 188 */     CssDeclarationValueTokenizer.Token currentToken = tokenizer.getNextValidToken();
/* 189 */     while (currentToken != null) {
/* 190 */       if (currentToken.getType() == CssDeclarationValueTokenizer.TokenType.COMMA) {
/* 191 */         result.add(currentLayer);
/* 192 */         currentLayer = new ArrayList<>();
/*     */       } else {
/* 194 */         currentLayer.add(currentToken.getValue());
/*     */       } 
/* 196 */       currentToken = tokenizer.getNextValidToken();
/*     */     } 
/* 198 */     result.add(currentLayer);
/*     */     
/* 200 */     return result;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String normalizeCssProperty(String str) {
/* 210 */     return (str == null) ? null : CssPropertyNormalizer.normalize(str);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String removeDoubleSpacesAndTrim(String str) {
/* 220 */     String[] parts = str.split("\\s");
/* 221 */     StringBuilder sb = new StringBuilder();
/* 222 */     for (String part : parts) {
/* 223 */       if (part.length() > 0) {
/* 224 */         if (sb.length() != 0) {
/* 225 */           sb.append(" ");
/*     */         }
/* 227 */         sb.append(part);
/*     */       } 
/*     */     } 
/* 230 */     return sb.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Integer parseInteger(String str) {
/* 240 */     if (str == null) {
/* 241 */       return null;
/*     */     }
/*     */     try {
/* 244 */       return Integer.valueOf(str);
/* 245 */     } catch (NumberFormatException exc) {
/* 246 */       return null;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Float parseFloat(String str) {
/* 257 */     if (str == null) {
/* 258 */       return null;
/*     */     }
/*     */     try {
/* 261 */       return Float.valueOf(str);
/* 262 */     } catch (NumberFormatException exc) {
/* 263 */       return null;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Double parseDouble(String str) {
/* 274 */     if (str == null) {
/* 275 */       return null;
/*     */     }
/*     */     try {
/* 278 */       return Double.valueOf(str);
/* 279 */     } catch (NumberFormatException exc) {
/* 280 */       return null;
/*     */     } 
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
/*     */   public static float parseAngle(String angle, String defaultMetric) {
/* 293 */     int pos = determinePositionBetweenValueAndUnit(angle);
/*     */     
/* 295 */     if (pos == 0) {
/* 296 */       if (angle == null) {
/* 297 */         angle = "null";
/*     */       }
/* 299 */       throw new StyledXMLParserException(MessageFormatUtil.format("The passed value (@{0}) is not a number", new Object[] { angle }));
/*     */     } 
/*     */     
/* 302 */     float floatValue = Float.parseFloat(angle.substring(0, pos));
/* 303 */     String unit = angle.substring(pos);
/*     */ 
/*     */     
/* 306 */     if (unit.startsWith("deg") || (unit.equals("") && "deg"
/* 307 */       .equals(defaultMetric))) {
/* 308 */       return 3.1415927F * floatValue / 180.0F;
/*     */     }
/*     */     
/* 311 */     if (unit.startsWith("grad") || (unit.equals("") && "grad"
/* 312 */       .equals(defaultMetric))) {
/* 313 */       return 3.1415927F * floatValue / 200.0F;
/*     */     }
/*     */     
/* 316 */     if (unit.startsWith("rad") || (unit.equals("") && "rad"
/* 317 */       .equals(defaultMetric))) {
/* 318 */       return floatValue;
/*     */     }
/*     */     
/* 321 */     logger.error(
/* 322 */         MessageFormatUtil.format("Unknown metric angle parsed: \"{0}\".", new Object[] { unit.equals("") ? defaultMetric : unit }));
/* 323 */     return floatValue;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static float parseAngle(String angle) {
/* 334 */     return parseAngle(angle, "deg");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int[] parseAspectRatio(String str) {
/* 344 */     int indexOfSlash = str.indexOf('/');
/*     */     try {
/* 346 */       int first = Integer.parseInt(str.substring(0, indexOfSlash));
/* 347 */       int second = Integer.parseInt(str.substring(indexOfSlash + 1));
/* 348 */       return new int[] { first, second };
/* 349 */     } catch (NumberFormatException|NullPointerException exc) {
/* 350 */       return null;
/*     */     } 
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
/*     */   public static float parseAbsoluteLength(String length, String defaultMetric) {
/* 366 */     int pos = determinePositionBetweenValueAndUnit(length);
/*     */     
/* 368 */     if (pos == 0) {
/* 369 */       if (length == null) {
/* 370 */         length = "null";
/*     */       }
/* 372 */       throw new StyledXMLParserException(MessageFormatUtil.format("The passed value (@{0}) is not a number", new Object[] { length }));
/*     */     } 
/*     */ 
/*     */     
/* 376 */     double f = Double.parseDouble(length.substring(0, pos));
/* 377 */     String unit = length.substring(pos);
/*     */ 
/*     */     
/* 380 */     if (unit.startsWith("pt") || (unit.equals("") && defaultMetric.equals("pt"))) {
/* 381 */       return (float)f;
/*     */     }
/*     */     
/* 384 */     if (unit.startsWith("in") || (unit.equals("") && defaultMetric
/* 385 */       .equals("in"))) {
/* 386 */       return (float)(f * 72.0D);
/*     */     }
/*     */     
/* 389 */     if (unit.startsWith("cm") || (unit.equals("") && defaultMetric
/* 390 */       .equals("cm"))) {
/* 391 */       return (float)(f / 2.54D * 72.0D);
/*     */     }
/*     */     
/* 394 */     if (unit.startsWith("q") || (unit.equals("") && defaultMetric
/* 395 */       .equals("q"))) {
/* 396 */       return (float)(f / 2.54D * 72.0D / 40.0D);
/*     */     }
/*     */     
/* 399 */     if (unit.startsWith("mm") || (unit.equals("") && defaultMetric
/* 400 */       .equals("mm"))) {
/* 401 */       return (float)(f / 25.4D * 72.0D);
/*     */     }
/*     */     
/* 404 */     if (unit.startsWith("pc") || (unit.equals("") && defaultMetric
/* 405 */       .equals("pc"))) {
/* 406 */       return (float)(f * 12.0D);
/*     */     }
/*     */     
/* 409 */     if (unit.startsWith("px") || (unit.equals("") && defaultMetric
/* 410 */       .equals("px"))) {
/* 411 */       return (float)(f * 0.75D);
/*     */     }
/*     */     
/* 414 */     logger.error(MessageFormatUtil.format("Unknown absolute metric length parsed \"{0}\".", new Object[] {
/* 415 */             unit.equals("") ? defaultMetric : unit }));
/* 416 */     return (float)f;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static float parseAbsoluteLength(String length) {
/* 426 */     return parseAbsoluteLength(length, "px");
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
/*     */   public static float parseRelativeValue(String relativeValue, float baseValue) {
/* 438 */     int pos = determinePositionBetweenValueAndUnit(relativeValue);
/* 439 */     if (pos == 0) {
/* 440 */       return 0.0F;
/*     */     }
/*     */     
/* 443 */     double f = Double.parseDouble(relativeValue.substring(0, pos));
/* 444 */     String unit = relativeValue.substring(pos);
/* 445 */     if (unit.startsWith("%")) {
/* 446 */       f = baseValue * f / 100.0D;
/* 447 */     } else if (unit.startsWith("em") || unit.startsWith("rem")) {
/* 448 */       f = baseValue * f;
/* 449 */     } else if (unit.startsWith("ex")) {
/* 450 */       f = baseValue * f / 2.0D;
/*     */     } 
/* 452 */     return (float)f;
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
/*     */   public static UnitValue parseLengthValueToPt(String value, float emValue, float remValue) {
/* 469 */     if (isMetricValue(value) || isNumericValue(value))
/* 470 */       return new UnitValue(1, parseAbsoluteLength(value)); 
/* 471 */     if (value != null && value.endsWith("%"))
/* 472 */       return new UnitValue(2, Float.parseFloat(value.substring(0, value.length() - 1))); 
/* 473 */     if (isRemValue(value))
/* 474 */       return new UnitValue(1, parseRelativeValue(value, remValue)); 
/* 475 */     if (isRelativeValue(value)) {
/* 476 */       return new UnitValue(1, parseRelativeValue(value, emValue));
/*     */     }
/* 478 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean isValidNumericValue(String value) {
/* 488 */     if (value == null || value.contains(" ")) {
/* 489 */       return false;
/*     */     }
/* 491 */     return (isRelativeValue(value) || isMetricValue(value) || isNumericValue(value));
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
/*     */   public static float parseAbsoluteFontSize(String fontSizeValue, String defaultMetric) {
/* 506 */     if (null != fontSizeValue && CommonCssConstants.FONT_ABSOLUTE_SIZE_KEYWORDS_VALUES.containsKey(fontSizeValue)) {
/* 507 */       fontSizeValue = (String)CommonCssConstants.FONT_ABSOLUTE_SIZE_KEYWORDS_VALUES.get(fontSizeValue);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 513 */       return parseAbsoluteLength(fontSizeValue, defaultMetric);
/* 514 */     } catch (StyledXMLParserException sxpe) {
/* 515 */       return 0.0F;
/*     */     } 
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
/*     */   public static float parseAbsoluteFontSize(String fontSizeValue) {
/* 528 */     return parseAbsoluteFontSize(fontSizeValue, "px");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static float parseRelativeFontSize(String relativeFontSizeValue, float baseValue) {
/* 539 */     if ("smaller".equals(relativeFontSizeValue))
/* 540 */       return (float)(baseValue / 1.2D); 
/* 541 */     if ("larger".equals(relativeFontSizeValue)) {
/* 542 */       return (float)(baseValue * 1.2D);
/*     */     }
/* 544 */     return parseRelativeValue(relativeFontSizeValue, baseValue);
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
/*     */   public static UnitValue[] parseSpecificCornerBorderRadius(String specificBorderRadius, float emValue, float remValue) {
/* 556 */     if (null == specificBorderRadius) {
/* 557 */       return null;
/*     */     }
/* 559 */     UnitValue[] cornerRadii = new UnitValue[2];
/* 560 */     String[] props = specificBorderRadius.split("\\s+");
/* 561 */     cornerRadii[0] = parseLengthValueToPt(props[0], emValue, remValue);
/* 562 */     cornerRadii[1] = (2 == props.length) ? parseLengthValueToPt(props[1], emValue, remValue) : cornerRadii[0];
/*     */     
/* 564 */     return cornerRadii;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static float parseResolution(String resolutionStr) {
/* 574 */     int pos = determinePositionBetweenValueAndUnit(resolutionStr);
/* 575 */     if (pos == 0) {
/* 576 */       return 0.0F;
/*     */     }
/* 578 */     double f = Double.parseDouble(resolutionStr.substring(0, pos));
/* 579 */     String unit = resolutionStr.substring(pos);
/* 580 */     if (unit.startsWith("dpcm")) {
/* 581 */       f *= 2.54D;
/* 582 */     } else if (unit.startsWith("dppx")) {
/* 583 */       f *= 96.0D;
/* 584 */     } else if (!unit.startsWith("dpi")) {
/* 585 */       throw new StyledXMLParserException("Resolution value unit should be either dpi, dppx or dpcm!");
/*     */     } 
/*     */     
/* 588 */     return (float)f;
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
/*     */   public static int determinePositionBetweenValueAndUnit(String string) {
/* 603 */     if (string == null) {
/* 604 */       return 0;
/*     */     }
/* 606 */     int pos = 0;
/* 607 */     while (pos < string.length() && (
/* 608 */       string.charAt(pos) == '+' || string
/* 609 */       .charAt(pos) == '-' || string
/* 610 */       .charAt(pos) == '.' || 
/* 611 */       isDigit(string.charAt(pos)) || 
/* 612 */       isExponentNotation(string, pos))) {
/* 613 */       pos++;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 618 */     return pos;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean isMetricValue(String value) {
/* 628 */     if (value == null) {
/* 629 */       return false;
/*     */     }
/* 631 */     for (String metricPostfix : CommonCssConstants.METRIC_MEASUREMENTS_VALUES) {
/* 632 */       if (value.endsWith(metricPostfix) && isNumericValue(value
/* 633 */           .substring(0, value.length() - metricPostfix.length()).trim())) {
/* 634 */         return true;
/*     */       }
/*     */     } 
/* 637 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean isAngleValue(String value) {
/* 647 */     if (value == null) {
/* 648 */       return false;
/*     */     }
/* 650 */     for (String metricPostfix : ANGLE_MEASUREMENTS_VALUES) {
/* 651 */       if (value.endsWith(metricPostfix) && isNumericValue(value
/* 652 */           .substring(0, value.length() - metricPostfix.length()).trim())) {
/* 653 */         return true;
/*     */       }
/*     */     } 
/* 656 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean isRelativeValue(String value) {
/* 666 */     if (value == null) {
/* 667 */       return false;
/*     */     }
/* 669 */     for (String relativePostfix : RELATIVE_MEASUREMENTS_VALUES) {
/* 670 */       if (value.endsWith(relativePostfix) && isNumericValue(value
/* 671 */           .substring(0, value.length() - relativePostfix.length()).trim())) {
/* 672 */         return true;
/*     */       }
/*     */     } 
/* 675 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean isFontRelativeValue(String value) {
/* 685 */     if (value == null) {
/* 686 */       return false;
/*     */     }
/* 688 */     for (String relativePostfix : FONT_RELATIVE_MEASUREMENTS_VALUES) {
/* 689 */       if (value.endsWith(relativePostfix) && isNumericValue(value
/* 690 */           .substring(0, value.length() - relativePostfix.length()).trim())) {
/* 691 */         return true;
/*     */       }
/*     */     } 
/* 694 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean isPercentageValue(String value) {
/* 704 */     return (value != null && value.endsWith("%") && isNumericValue(value
/* 705 */         .substring(0, value.length() - "%".length()).trim()));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean isRemValue(String value) {
/* 715 */     return (value != null && value.endsWith("rem") && isNumericValue(value
/* 716 */         .substring(0, value.length() - "rem".length()).trim()));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean isEmValue(String value) {
/* 726 */     return (value != null && value.endsWith("em") && isNumericValue(value
/* 727 */         .substring(0, value.length() - "em".length()).trim()));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean isExValue(String value) {
/* 737 */     return (value != null && value.endsWith("ex") && isNumericValue(value
/* 738 */         .substring(0, value.length() - "ex".length()).trim()));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean isNumericValue(String value) {
/* 749 */     return (value != null && (value.matches("^[-+]?\\d\\d*\\.\\d*$") || value
/* 750 */       .matches("^[-+]?\\d\\d*$") || value
/* 751 */       .matches("^[-+]?\\.\\d\\d*$")));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String extractUrl(String url) {
/* 761 */     String str = null;
/* 762 */     if (url.startsWith("url")) {
/* 763 */       String urlString = url.substring(3).trim().replace("(", "").replace(")", "").trim();
/* 764 */       if (urlString.startsWith("'") && urlString.endsWith("'")) {
/* 765 */         str = urlString.substring(urlString.indexOf("'") + 1, urlString.lastIndexOf("'"));
/* 766 */       } else if (urlString.startsWith("\"") && urlString.endsWith("\"")) {
/* 767 */         str = urlString.substring(urlString.indexOf('"') + 1, urlString.lastIndexOf('"'));
/*     */       } else {
/* 769 */         str = urlString;
/*     */       } 
/*     */     } else {
/*     */       
/* 773 */       str = url;
/*     */     } 
/* 775 */     return str;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean isBase64Data(String data) {
/* 785 */     return data.matches("^data:([^\\s]*);base64,([^\\s]*)");
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
/*     */   public static int findNextUnescapedChar(String source, char ch, int startIndex) {
/* 797 */     int symbolPos = source.indexOf(ch, startIndex);
/* 798 */     if (symbolPos == -1) {
/* 799 */       return -1;
/*     */     }
/* 801 */     int afterNoneEscapePos = symbolPos;
/* 802 */     while (afterNoneEscapePos > 0 && source.charAt(afterNoneEscapePos - 1) == '\\') {
/* 803 */       afterNoneEscapePos--;
/*     */     }
/* 805 */     return ((symbolPos - afterNoneEscapePos) % 2 == 0) ? symbolPos : findNextUnescapedChar(source, ch, symbolPos + 1);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean isColorProperty(String value) {
/* 815 */     return (value.startsWith("rgb(") || value.startsWith("rgba(") || value.startsWith("#") || WebColors.NAMES
/* 816 */       .containsKey(value.toLowerCase()) || "transparent".equals(value));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean compareFloats(double d1, double d2) {
/* 827 */     return (Math.abs(d1 - d2) < 9.999999974752427E-7D);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean compareFloats(float f1, float f2) {
/* 838 */     return (Math.abs(f1 - f2) < 1.0E-6F);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static float[] parseRgbaColor(String colorValue) {
/* 848 */     float[] rgbaColor = WebColors.getRGBAColor(colorValue);
/* 849 */     if (rgbaColor == null) {
/* 850 */       logger.error(MessageFormatUtil.format("Color \"{0}\" was not parsed. It has invalid value. Defaulting to black color.", new Object[] { colorValue }));
/* 851 */       rgbaColor = new float[] { 0.0F, 0.0F, 0.0F, 1.0F };
/*     */     } 
/* 853 */     return rgbaColor;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Range parseUnicodeRange(String unicodeRange) {
/* 863 */     String[] ranges = unicodeRange.split(",");
/* 864 */     RangeBuilder builder = new RangeBuilder();
/* 865 */     for (String range : ranges) {
/* 866 */       if (!addRange(builder, range)) {
/* 867 */         return null;
/*     */       }
/*     */     } 
/* 870 */     return builder.create();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static float convertPtsToPx(float pts) {
/* 880 */     return pts / 0.75F;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static double convertPtsToPx(double pts) {
/* 890 */     return pts / 0.75D;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static float convertPxToPts(float px) {
/* 900 */     return px * 0.75F;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static double convertPxToPts(double px) {
/* 910 */     return px * 0.75D;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean isStyleSheetLink(IElementNode headChildElement) {
/* 920 */     return ("link".equals(headChildElement.name()) && "stylesheet"
/*     */       
/* 922 */       .equals(headChildElement.getAttribute("rel")));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean isInitialOrInheritOrUnset(String value) {
/* 932 */     return ("initial".equals(value) || "inherit"
/* 933 */       .equals(value) || "unset"
/* 934 */       .equals(value));
/*     */   }
/*     */   
/*     */   private static boolean addRange(RangeBuilder builder, String range) {
/* 938 */     range = range.trim();
/* 939 */     if (range.matches("[uU]\\+[0-9a-fA-F?]{1,6}(-[0-9a-fA-F]{1,6})?")) {
/* 940 */       String[] parts = range.substring(2, range.length()).split("-");
/* 941 */       if (1 == parts.length) {
/* 942 */         if (parts[0].contains("?")) {
/* 943 */           return addRange(builder, parts[0].replace('?', '0'), parts[0].replace('?', 'F'));
/*     */         }
/* 945 */         return addRange(builder, parts[0], parts[0]);
/*     */       } 
/*     */       
/* 948 */       return addRange(builder, parts[0], parts[1]);
/*     */     } 
/*     */     
/* 951 */     return false;
/*     */   }
/*     */   
/*     */   private static boolean addRange(RangeBuilder builder, String left, String right) {
/* 955 */     int l = Integer.parseInt(left, 16);
/* 956 */     int r = Integer.parseInt(right, 16);
/* 957 */     if (l > r || r > 1114111) {
/* 958 */       return false;
/*     */     }
/* 960 */     builder.addRange(l, r);
/* 961 */     return true;
/*     */   }
/*     */   
/*     */   private static boolean isDigit(char ch) {
/* 965 */     return (ch >= '0' && ch <= '9');
/*     */   }
/*     */   
/*     */   private static boolean isExponentNotation(String s, int index) {
/* 969 */     return (index < s.length() && s.charAt(index) == 'e' && ((index + 1 < s
/*     */       
/* 971 */       .length() && isDigit(s.charAt(index + 1))) || (index + 2 < s
/*     */       
/* 973 */       .length() && (s.charAt(index + 1) == '-' || s.charAt(index + 1) == '+') && isDigit(s.charAt(index + 2)))));
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/styledxmlparser/css/util/CssUtils.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */