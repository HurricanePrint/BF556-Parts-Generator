/*     */ package com.itextpdf.styledxmlparser.css.resolve.shorthand.impl;
/*     */ 
/*     */ import com.itextpdf.io.util.MessageFormatUtil;
/*     */ import com.itextpdf.styledxmlparser.css.CssDeclaration;
/*     */ import com.itextpdf.styledxmlparser.css.resolve.shorthand.IShorthandResolver;
/*     */ import com.itextpdf.styledxmlparser.css.util.CssUtils;
/*     */ import com.itextpdf.styledxmlparser.css.validate.CssDeclarationValidationMaster;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
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
/*     */ public class BackgroundPositionShorthandResolver
/*     */   implements IShorthandResolver
/*     */ {
/*  46 */   private static final Logger LOGGER = LoggerFactory.getLogger(BackgroundPositionShorthandResolver.class);
/*     */ 
/*     */   
/*     */   private static final int POSITION_VALUES_MAX_COUNT = 2;
/*     */ 
/*     */ 
/*     */   
/*     */   public List<CssDeclaration> resolveShorthand(String shorthandExpression) {
/*  54 */     if (CssUtils.isInitialOrInheritOrUnset(shorthandExpression)) {
/*  55 */       return Arrays.asList(new CssDeclaration[] { new CssDeclaration("background-position-x", shorthandExpression), new CssDeclaration("background-position-y", shorthandExpression) });
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*  60 */     if (shorthandExpression.trim().isEmpty()) {
/*  61 */       LOGGER.error(MessageFormatUtil.format("{0} shorthand property cannot be empty.", new Object[] { "background-position" }));
/*     */       
/*  63 */       return new ArrayList<>();
/*     */     } 
/*     */     
/*  66 */     List<List<String>> propsList = CssUtils.extractShorthandProperties(shorthandExpression);
/*  67 */     Map<String, String> resolvedProps = new HashMap<>();
/*     */     
/*  69 */     Map<String, String> values = new HashMap<>();
/*  70 */     for (List<String> props : propsList) {
/*  71 */       if (props.isEmpty()) {
/*  72 */         LOGGER.error(MessageFormatUtil.format("{0} shorthand property cannot be empty.", new Object[] { "background-position" }));
/*     */         
/*  74 */         return new ArrayList<>();
/*     */       } 
/*  76 */       if (!parsePositionShorthand(props, values)) {
/*  77 */         LOGGER.error(MessageFormatUtil.format("Invalid css property declaration: {0}", new Object[] { shorthandExpression }));
/*     */         
/*  79 */         return new ArrayList<>();
/*     */       } 
/*     */       
/*  82 */       updateValue(resolvedProps, values, "background-position-x");
/*  83 */       updateValue(resolvedProps, values, "background-position-y");
/*  84 */       values.clear();
/*     */     } 
/*  86 */     if (!checkProperty(resolvedProps, "background-position-x") || 
/*  87 */       !checkProperty(resolvedProps, "background-position-y")) {
/*  88 */       return new ArrayList<>();
/*     */     }
/*     */     
/*  91 */     return Arrays.asList(new CssDeclaration[] { new CssDeclaration("background-position-x", resolvedProps
/*     */             
/*  93 */             .get("background-position-x")), new CssDeclaration("background-position-y", resolvedProps
/*     */             
/*  95 */             .get("background-position-y")) });
/*     */   }
/*     */ 
/*     */   
/*     */   private static boolean checkProperty(Map<String, String> resolvedProps, String key) {
/* 100 */     if (!CssDeclarationValidationMaster.checkDeclaration(new CssDeclaration(key, resolvedProps.get(key)))) {
/* 101 */       LOGGER.error(MessageFormatUtil.format("Invalid css property declaration: {0}", new Object[] { resolvedProps
/* 102 */               .get(key) }));
/* 103 */       return false;
/*     */     } 
/* 105 */     return true;
/*     */   }
/*     */   
/*     */   private static void updateValue(Map<String, String> resolvedProps, Map<String, String> values, String key) {
/* 109 */     if (values.get(key) == null) {
/* 110 */       if (resolvedProps.get(key) == null) {
/* 111 */         resolvedProps.put(key, "center");
/*     */       } else {
/* 113 */         resolvedProps.put(key, (String)resolvedProps.get(key) + "," + "center");
/*     */       }
/*     */     
/* 116 */     } else if (resolvedProps.get(key) == null) {
/* 117 */       resolvedProps.put(key, values.get(key));
/*     */     } else {
/* 119 */       resolvedProps.put(key, (String)resolvedProps.get(key) + "," + (String)values.get(key));
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private static boolean parsePositionShorthand(List<String> valuesToParse, Map<String, String> parsedValues) {
/* 125 */     for (String positionValue : valuesToParse) {
/* 126 */       if (!parseNonNumericValue(positionValue, parsedValues)) {
/* 127 */         return false;
/*     */       }
/*     */     } 
/* 130 */     for (int i = 0; i < valuesToParse.size(); i++) {
/* 131 */       if (typeOfValue(valuesToParse.get(i)) == BackgroundPositionType.NUMERIC && 
/* 132 */         !parseNumericValue(i, valuesToParse, parsedValues)) {
/* 133 */         return false;
/*     */       }
/*     */     } 
/* 136 */     return true;
/*     */   }
/*     */   
/*     */   private static boolean parseNumericValue(int i, List<String> positionValues, Map<String, String> values) {
/* 140 */     if (values.get("background-position-x") == null || values
/* 141 */       .get("background-position-y") == null) {
/* 142 */       return parseShortNumericValue(i, positionValues, values, positionValues.get(i));
/*     */     }
/* 144 */     if (i == 0) {
/* 145 */       return false;
/*     */     }
/* 147 */     return parseLargeNumericValue(positionValues.get(i - 1), values, positionValues.get(i));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static boolean parseShortNumericValue(int i, List<String> positionValues, Map<String, String> values, String value) {
/* 153 */     if (positionValues.size() > 2) {
/* 154 */       return false;
/*     */     }
/* 156 */     if (values.get("background-position-x") == null) {
/* 157 */       if (i != 0) {
/* 158 */         return false;
/*     */       }
/* 160 */       values.put("background-position-x", value);
/* 161 */       return true;
/*     */     } 
/* 163 */     if (i == 0) {
/* 164 */       if (typeOfValue(positionValues.get(i + 1)) == BackgroundPositionType.CENTER) {
/* 165 */         values.put("background-position-x", value);
/* 166 */         values.put("background-position-y", "center");
/* 167 */         return true;
/*     */       } 
/* 169 */       return false;
/*     */     } 
/* 171 */     values.put("background-position-y", value);
/* 172 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   private static boolean parseLargeNumericValue(String prevValue, Map<String, String> values, String value) {
/* 177 */     if (typeOfValue(prevValue) == BackgroundPositionType.HORIZONTAL_POSITION) {
/* 178 */       values.put("background-position-x", (String)values
/* 179 */           .get("background-position-x") + " " + value);
/* 180 */       return true;
/*     */     } 
/* 182 */     if (typeOfValue(prevValue) == BackgroundPositionType.VERTICAL_POSITION) {
/* 183 */       values.put("background-position-y", (String)values
/* 184 */           .get("background-position-y") + " " + value);
/* 185 */       return true;
/*     */     } 
/* 187 */     return false;
/*     */   }
/*     */   
/*     */   private static boolean parseNonNumericValue(String positionValue, Map<String, String> values) {
/* 191 */     switch (typeOfValue(positionValue)) {
/*     */       case HORIZONTAL_POSITION:
/* 193 */         return parseHorizontal(positionValue, values);
/*     */       case VERTICAL_POSITION:
/* 195 */         return parseVertical(positionValue, values);
/*     */       case CENTER:
/* 197 */         return parseCenter(positionValue, values);
/*     */     } 
/* 199 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   private static boolean parseHorizontal(String positionValue, Map<String, String> values) {
/* 204 */     if (values.get("background-position-x") == null) {
/* 205 */       values.put("background-position-x", positionValue);
/* 206 */       return true;
/*     */     } 
/* 208 */     if ("center".equals(values.get("background-position-x")) && values
/* 209 */       .get("background-position-y") == null) {
/* 210 */       values.put("background-position-x", positionValue);
/* 211 */       values.put("background-position-y", "center");
/* 212 */       return true;
/*     */     } 
/* 214 */     return false;
/*     */   }
/*     */   
/*     */   private static boolean parseVertical(String positionValue, Map<String, String> values) {
/* 218 */     if (values.get("background-position-y") == null) {
/* 219 */       values.put("background-position-y", positionValue);
/* 220 */       return true;
/*     */     } 
/* 222 */     return false;
/*     */   }
/*     */   
/*     */   private static boolean parseCenter(String positionValue, Map<String, String> values) {
/* 226 */     if (values.get("background-position-x") == null) {
/* 227 */       values.put("background-position-x", positionValue);
/* 228 */       return true;
/*     */     } 
/* 230 */     if (values.get("background-position-y") == null) {
/* 231 */       values.put("background-position-y", positionValue);
/* 232 */       return true;
/*     */     } 
/* 234 */     return false;
/*     */   }
/*     */   
/*     */   private static BackgroundPositionType typeOfValue(String value) {
/* 238 */     if ("left".equals(value) || "right".equals(value)) {
/* 239 */       return BackgroundPositionType.HORIZONTAL_POSITION;
/*     */     }
/* 241 */     if ("top".equals(value) || "bottom".equals(value)) {
/* 242 */       return BackgroundPositionType.VERTICAL_POSITION;
/*     */     }
/* 244 */     if ("center".equals(value)) {
/* 245 */       return BackgroundPositionType.CENTER;
/*     */     }
/* 247 */     return BackgroundPositionType.NUMERIC;
/*     */   }
/*     */   
/*     */   private enum BackgroundPositionType {
/* 251 */     NUMERIC,
/* 252 */     HORIZONTAL_POSITION,
/* 253 */     VERTICAL_POSITION,
/* 254 */     CENTER;
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/styledxmlparser/css/resolve/shorthand/impl/BackgroundPositionShorthandResolver.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */