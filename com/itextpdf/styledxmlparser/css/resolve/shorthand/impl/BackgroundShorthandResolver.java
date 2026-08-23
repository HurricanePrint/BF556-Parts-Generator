/*     */ package com.itextpdf.styledxmlparser.css.resolve.shorthand.impl;
/*     */ 
/*     */ import com.itextpdf.io.util.MessageFormatUtil;
/*     */ import com.itextpdf.styledxmlparser.css.CssDeclaration;
/*     */ import com.itextpdf.styledxmlparser.css.resolve.CssDefaults;
/*     */ import com.itextpdf.styledxmlparser.css.resolve.shorthand.IShorthandResolver;
/*     */ import com.itextpdf.styledxmlparser.css.resolve.shorthand.ShorthandResolverFactory;
/*     */ import com.itextpdf.styledxmlparser.css.util.CssBackgroundUtils;
/*     */ import com.itextpdf.styledxmlparser.css.util.CssUtils;
/*     */ import com.itextpdf.styledxmlparser.css.validate.CssDeclarationValidationMaster;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.HashMap;
/*     */ import java.util.HashSet;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
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
/*     */ public class BackgroundShorthandResolver
/*     */   implements IShorthandResolver
/*     */ {
/*  73 */   private static final Logger LOGGER = LoggerFactory.getLogger(BackgroundShorthandResolver.class);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List<CssDeclaration> resolveShorthand(String shorthandExpression) {
/*  84 */     if (CssUtils.isInitialOrInheritOrUnset(shorthandExpression)) {
/*  85 */       return Arrays.asList(new CssDeclaration[] { new CssDeclaration("background-color", shorthandExpression), new CssDeclaration("background-image", shorthandExpression), new CssDeclaration("background-position", shorthandExpression), new CssDeclaration("background-size", shorthandExpression), new CssDeclaration("background-repeat", shorthandExpression), new CssDeclaration("background-origin", shorthandExpression), new CssDeclaration("background-clip", shorthandExpression), new CssDeclaration("background-attachment", shorthandExpression) });
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  96 */     if (shorthandExpression.trim().isEmpty()) {
/*  97 */       LOGGER.error(MessageFormatUtil.format("{0} shorthand property cannot be empty.", new Object[] { "background" }));
/*     */       
/*  99 */       return new ArrayList<>();
/*     */     } 
/*     */     
/* 102 */     List<List<String>> propsList = CssUtils.extractShorthandProperties(shorthandExpression);
/*     */     
/* 104 */     Map<CssBackgroundUtils.BackgroundPropertyType, String> resolvedProps = new HashMap<>();
/* 105 */     fillMapWithPropertiesTypes(resolvedProps);
/* 106 */     for (List<String> props : propsList) {
/* 107 */       if (!processProperties(props, resolvedProps)) {
/* 108 */         return new ArrayList<>();
/*     */       }
/*     */     } 
/* 111 */     if (resolvedProps.get(CssBackgroundUtils.BackgroundPropertyType.BACKGROUND_COLOR) == null) {
/* 112 */       resolvedProps.put(CssBackgroundUtils.BackgroundPropertyType.BACKGROUND_COLOR, "transparent");
/*     */     }
/*     */     
/* 115 */     if (!checkProperties(resolvedProps)) {
/* 116 */       return new ArrayList<>();
/*     */     }
/*     */     
/* 119 */     return Arrays.asList(new CssDeclaration[] { new CssDeclaration(
/* 120 */             CssBackgroundUtils.getBackgroundPropertyNameFromType(CssBackgroundUtils.BackgroundPropertyType.BACKGROUND_COLOR), resolvedProps
/*     */             
/* 122 */             .get(CssBackgroundUtils.BackgroundPropertyType.BACKGROUND_COLOR)), new CssDeclaration(
/* 123 */             CssBackgroundUtils.getBackgroundPropertyNameFromType(CssBackgroundUtils.BackgroundPropertyType.BACKGROUND_IMAGE), resolvedProps
/*     */             
/* 125 */             .get(CssBackgroundUtils.BackgroundPropertyType.BACKGROUND_IMAGE)), new CssDeclaration(
/* 126 */             CssBackgroundUtils.getBackgroundPropertyNameFromType(CssBackgroundUtils.BackgroundPropertyType.BACKGROUND_POSITION), resolvedProps
/*     */             
/* 128 */             .get(CssBackgroundUtils.BackgroundPropertyType.BACKGROUND_POSITION)), new CssDeclaration(
/* 129 */             CssBackgroundUtils.getBackgroundPropertyNameFromType(CssBackgroundUtils.BackgroundPropertyType.BACKGROUND_SIZE), resolvedProps
/*     */             
/* 131 */             .get(CssBackgroundUtils.BackgroundPropertyType.BACKGROUND_SIZE)), new CssDeclaration(
/* 132 */             CssBackgroundUtils.getBackgroundPropertyNameFromType(CssBackgroundUtils.BackgroundPropertyType.BACKGROUND_REPEAT), resolvedProps
/*     */             
/* 134 */             .get(CssBackgroundUtils.BackgroundPropertyType.BACKGROUND_REPEAT)), new CssDeclaration(
/* 135 */             CssBackgroundUtils.getBackgroundPropertyNameFromType(CssBackgroundUtils.BackgroundPropertyType.BACKGROUND_ORIGIN), resolvedProps
/*     */             
/* 137 */             .get(CssBackgroundUtils.BackgroundPropertyType.BACKGROUND_ORIGIN)), new CssDeclaration(
/* 138 */             CssBackgroundUtils.getBackgroundPropertyNameFromType(CssBackgroundUtils.BackgroundPropertyType.BACKGROUND_CLIP), resolvedProps
/*     */             
/* 140 */             .get(CssBackgroundUtils.BackgroundPropertyType.BACKGROUND_CLIP)), new CssDeclaration(
/* 141 */             CssBackgroundUtils.getBackgroundPropertyNameFromType(CssBackgroundUtils.BackgroundPropertyType.BACKGROUND_ATTACHMENT), resolvedProps
/*     */             
/* 143 */             .get(CssBackgroundUtils.BackgroundPropertyType.BACKGROUND_ATTACHMENT)) });
/*     */   }
/*     */ 
/*     */   
/*     */   private static boolean checkProperties(Map<CssBackgroundUtils.BackgroundPropertyType, String> resolvedProps) {
/* 148 */     for (Map.Entry<CssBackgroundUtils.BackgroundPropertyType, String> property : resolvedProps.entrySet()) {
/* 149 */       if (!CssDeclarationValidationMaster.checkDeclaration(new CssDeclaration(
/* 150 */             CssBackgroundUtils.getBackgroundPropertyNameFromType(property.getKey()), property.getValue()))) {
/* 151 */         LOGGER.error(MessageFormatUtil.format("Invalid css property declaration: {0}", new Object[] { property
/* 152 */                 .getValue() }));
/* 153 */         return false;
/*     */       } 
/*     */       
/* 156 */       IShorthandResolver resolver = ShorthandResolverFactory.getShorthandResolver(CssBackgroundUtils.getBackgroundPropertyNameFromType(property.getKey()));
/* 157 */       if (resolver != null && resolver.resolveShorthand(property.getValue()).isEmpty()) {
/* 158 */         return false;
/*     */       }
/*     */     } 
/* 161 */     return true;
/*     */   }
/*     */   
/*     */   private static void removeSpacesAroundSlash(List<String> props) {
/* 165 */     for (int i = 0; i < props.size(); i++) {
/* 166 */       if ("/".equals(props.get(i))) {
/* 167 */         if (i != 0 && i != props.size() - 1) {
/* 168 */           String property = (String)props.get(i - 1) + (String)props.get(i) + (String)props.get(i + 1);
/* 169 */           props.set(i + 1, property);
/* 170 */           props.remove(i);
/* 171 */           props.remove(i - 1);
/*     */         } 
/*     */         return;
/*     */       } 
/* 175 */       if (((String)props.get(i)).startsWith("/")) {
/* 176 */         if (i != 0) {
/* 177 */           String property = (String)props.get(i - 1) + (String)props.get(i);
/* 178 */           props.set(i, property);
/* 179 */           props.remove(i - 1);
/*     */         } 
/*     */         return;
/*     */       } 
/* 183 */       if (((String)props.get(i)).endsWith("/")) {
/* 184 */         if (i != props.size() - 1) {
/* 185 */           String property = (String)props.get(i) + (String)props.get(i + 1);
/* 186 */           props.set(i + 1, property);
/* 187 */           props.remove(i);
/*     */         } 
/*     */         return;
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private static void fillMapWithPropertiesTypes(Map<CssBackgroundUtils.BackgroundPropertyType, String> resolvedProps) {
/* 196 */     resolvedProps.put(CssBackgroundUtils.BackgroundPropertyType.BACKGROUND_COLOR, null);
/* 197 */     resolvedProps.put(CssBackgroundUtils.BackgroundPropertyType.BACKGROUND_IMAGE, null);
/* 198 */     resolvedProps.put(CssBackgroundUtils.BackgroundPropertyType.BACKGROUND_POSITION, null);
/* 199 */     resolvedProps.put(CssBackgroundUtils.BackgroundPropertyType.BACKGROUND_SIZE, null);
/* 200 */     resolvedProps.put(CssBackgroundUtils.BackgroundPropertyType.BACKGROUND_REPEAT, null);
/* 201 */     resolvedProps.put(CssBackgroundUtils.BackgroundPropertyType.BACKGROUND_ORIGIN, null);
/* 202 */     resolvedProps.put(CssBackgroundUtils.BackgroundPropertyType.BACKGROUND_CLIP, null);
/* 203 */     resolvedProps.put(CssBackgroundUtils.BackgroundPropertyType.BACKGROUND_ATTACHMENT, null);
/*     */   }
/*     */ 
/*     */   
/*     */   private static boolean processProperties(List<String> props, Map<CssBackgroundUtils.BackgroundPropertyType, String> resolvedProps) {
/* 208 */     if (props.isEmpty()) {
/* 209 */       LOGGER.error(MessageFormatUtil.format("{0} shorthand property cannot be empty.", new Object[] { "background" }));
/*     */       
/* 211 */       return false;
/*     */     } 
/* 213 */     if (resolvedProps.get(CssBackgroundUtils.BackgroundPropertyType.BACKGROUND_COLOR) != null) {
/* 214 */       LOGGER.error("Only the last background can include a background color.");
/* 215 */       return false;
/*     */     } 
/* 217 */     removeSpacesAroundSlash(props);
/* 218 */     Set<CssBackgroundUtils.BackgroundPropertyType> usedTypes = new HashSet<>();
/* 219 */     if (processAllSpecifiedProperties(props, resolvedProps, usedTypes)) {
/* 220 */       fillNotProcessedProperties(resolvedProps, usedTypes);
/* 221 */       return true;
/*     */     } 
/* 223 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static boolean processAllSpecifiedProperties(List<String> props, Map<CssBackgroundUtils.BackgroundPropertyType, String> resolvedProps, Set<CssBackgroundUtils.BackgroundPropertyType> usedTypes) {
/* 230 */     List<String> boxValues = new ArrayList<>();
/* 231 */     boolean slashEncountered = false;
/* 232 */     boolean propertyProcessedCorrectly = true;
/* 233 */     for (String value : props) {
/* 234 */       int slashCharInd = value.indexOf('/');
/* 235 */       if (slashCharInd > 0 && slashCharInd < value.length() - 1 && !slashEncountered && !value.contains("url(")) {
/* 236 */         slashEncountered = true;
/* 237 */         propertyProcessedCorrectly = processValueWithSlash(value, slashCharInd, resolvedProps, usedTypes);
/*     */       } else {
/* 239 */         CssBackgroundUtils.BackgroundPropertyType type = CssBackgroundUtils.resolveBackgroundPropertyType(value);
/* 240 */         if (CssBackgroundUtils.BackgroundPropertyType.BACKGROUND_ORIGIN_OR_CLIP == type) {
/* 241 */           boxValues.add(value);
/*     */         } else {
/* 243 */           propertyProcessedCorrectly = putPropertyBasedOnType(changePropertyType(type, slashEncountered), value, resolvedProps, usedTypes);
/*     */         } 
/*     */       } 
/*     */       
/* 247 */       if (!propertyProcessedCorrectly) {
/* 248 */         return false;
/*     */       }
/*     */     } 
/* 251 */     return addBackgroundClipAndBackgroundOriginBoxValues(boxValues, resolvedProps, usedTypes);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static boolean addBackgroundClipAndBackgroundOriginBoxValues(List<String> boxValues, Map<CssBackgroundUtils.BackgroundPropertyType, String> resolvedProps, Set<CssBackgroundUtils.BackgroundPropertyType> usedTypes) {
/* 257 */     if (boxValues.size() == 1)
/* 258 */       return putPropertyBasedOnType(CssBackgroundUtils.BackgroundPropertyType.BACKGROUND_CLIP, boxValues
/* 259 */           .get(0), resolvedProps, usedTypes); 
/* 260 */     if (boxValues.size() >= 2) {
/* 261 */       for (int i = 0; i < 2; i++) {
/* 262 */         CssBackgroundUtils.BackgroundPropertyType type = (i == 0) ? CssBackgroundUtils.BackgroundPropertyType.BACKGROUND_ORIGIN : CssBackgroundUtils.BackgroundPropertyType.BACKGROUND_CLIP;
/*     */         
/* 264 */         if (!putPropertyBasedOnType(type, boxValues.get(i), resolvedProps, usedTypes)) {
/* 265 */           return false;
/*     */         }
/*     */       } 
/*     */     }
/* 269 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static boolean processValueWithSlash(String value, int slashCharInd, Map<CssBackgroundUtils.BackgroundPropertyType, String> resolvedProps, Set<CssBackgroundUtils.BackgroundPropertyType> usedTypes) {
/* 275 */     String value1 = value.substring(0, slashCharInd);
/*     */     
/* 277 */     CssBackgroundUtils.BackgroundPropertyType typeBeforeSlash = changePropertyType(CssBackgroundUtils.resolveBackgroundPropertyType(value1), false);
/* 278 */     if (typeBeforeSlash != CssBackgroundUtils.BackgroundPropertyType.BACKGROUND_POSITION && typeBeforeSlash != CssBackgroundUtils.BackgroundPropertyType.BACKGROUND_POSITION_OR_SIZE) {
/*     */       
/* 280 */       LOGGER.error(MessageFormatUtil.format("Unknown {0} property: \"{1}\".", new Object[] { "background-position", value1 }));
/*     */       
/* 282 */       return false;
/*     */     } 
/*     */     
/* 285 */     String value2 = value.substring(slashCharInd + 1);
/*     */     
/* 287 */     CssBackgroundUtils.BackgroundPropertyType typeAfterSlash = changePropertyType(CssBackgroundUtils.resolveBackgroundPropertyType(value2), true);
/* 288 */     if (typeAfterSlash != CssBackgroundUtils.BackgroundPropertyType.BACKGROUND_SIZE && typeAfterSlash != CssBackgroundUtils.BackgroundPropertyType.BACKGROUND_POSITION_OR_SIZE) {
/*     */       
/* 290 */       LOGGER.error(MessageFormatUtil.format("Unknown {0} property: \"{1}\".", new Object[] { "background-size", value2 }));
/*     */       
/* 292 */       return false;
/*     */     } 
/*     */     
/* 295 */     return (putPropertyBasedOnType(typeBeforeSlash, value1, resolvedProps, usedTypes) && 
/* 296 */       putPropertyBasedOnType(typeAfterSlash, value2, resolvedProps, usedTypes));
/*     */   }
/*     */ 
/*     */   
/*     */   private static void fillNotProcessedProperties(Map<CssBackgroundUtils.BackgroundPropertyType, String> resolvedProps, Set<CssBackgroundUtils.BackgroundPropertyType> usedTypes) {
/* 301 */     for (CssBackgroundUtils.BackgroundPropertyType type : new ArrayList(resolvedProps.keySet())) {
/* 302 */       if (!usedTypes.contains(type) && type != CssBackgroundUtils.BackgroundPropertyType.BACKGROUND_COLOR) {
/* 303 */         if (resolvedProps.get(type) == null) {
/* 304 */           resolvedProps.put(type, 
/* 305 */               CssDefaults.getDefaultValue(CssBackgroundUtils.getBackgroundPropertyNameFromType(type))); continue;
/*     */         } 
/* 307 */         resolvedProps.put(type, (String)resolvedProps.get(type) + "," + 
/* 308 */             CssDefaults.getDefaultValue(CssBackgroundUtils.getBackgroundPropertyNameFromType(type)));
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static CssBackgroundUtils.BackgroundPropertyType changePropertyType(CssBackgroundUtils.BackgroundPropertyType propertyType, boolean slashEncountered) {
/* 317 */     if (propertyType == CssBackgroundUtils.BackgroundPropertyType.BACKGROUND_POSITION_X || propertyType == CssBackgroundUtils.BackgroundPropertyType.BACKGROUND_POSITION_Y)
/*     */     {
/* 319 */       propertyType = CssBackgroundUtils.BackgroundPropertyType.BACKGROUND_POSITION;
/*     */     }
/* 321 */     if (propertyType == CssBackgroundUtils.BackgroundPropertyType.BACKGROUND_POSITION_OR_SIZE) {
/* 322 */       return slashEncountered ? CssBackgroundUtils.BackgroundPropertyType.BACKGROUND_SIZE : CssBackgroundUtils.BackgroundPropertyType.BACKGROUND_POSITION;
/*     */     }
/*     */     
/* 325 */     if (propertyType == CssBackgroundUtils.BackgroundPropertyType.BACKGROUND_SIZE && !slashEncountered) {
/* 326 */       return CssBackgroundUtils.BackgroundPropertyType.UNDEFINED;
/*     */     }
/* 328 */     if (propertyType == CssBackgroundUtils.BackgroundPropertyType.BACKGROUND_POSITION && slashEncountered) {
/* 329 */       return CssBackgroundUtils.BackgroundPropertyType.UNDEFINED;
/*     */     }
/* 331 */     return propertyType;
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
/*     */   private static boolean putPropertyBasedOnType(CssBackgroundUtils.BackgroundPropertyType type, String value, Map<CssBackgroundUtils.BackgroundPropertyType, String> resolvedProps, Set<CssBackgroundUtils.BackgroundPropertyType> usedTypes) {
/* 346 */     if (type == CssBackgroundUtils.BackgroundPropertyType.UNDEFINED) {
/* 347 */       LOGGER.error(MessageFormatUtil.format("Was not able to define one of the background CSS shorthand properties: {0}", new Object[] { value }));
/*     */       
/* 349 */       return false;
/*     */     } 
/*     */     
/* 352 */     if (resolvedProps.get(type) == null) {
/* 353 */       resolvedProps.put(type, value);
/* 354 */     } else if (usedTypes.contains(type)) {
/* 355 */       resolvedProps.put(type, (String)resolvedProps.get(type) + " " + value);
/*     */     } else {
/* 357 */       resolvedProps.put(type, (String)resolvedProps.get(type) + "," + value);
/*     */     } 
/* 359 */     usedTypes.add(type);
/* 360 */     return true;
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/styledxmlparser/css/resolve/shorthand/impl/BackgroundShorthandResolver.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */