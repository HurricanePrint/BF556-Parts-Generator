/*      */ package com.itextpdf.styledxmlparser.css;
/*      */ 
/*      */ import java.util.Arrays;
/*      */ import java.util.Collections;
/*      */ import java.util.HashMap;
/*      */ import java.util.HashSet;
/*      */ import java.util.Map;
/*      */ import java.util.Set;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class CommonCssConstants
/*      */ {
/*      */   public static final String BACKGROUND = "background";
/*      */   public static final String BACKGROUND_ATTACHMENT = "background-attachment";
/*      */   public static final String BACKGROUND_BLEND_MODE = "background-blend-mode";
/*      */   public static final String BACKGROUND_CLIP = "background-clip";
/*      */   public static final String BACKGROUND_COLOR = "background-color";
/*      */   public static final String BACKGROUND_IMAGE = "background-image";
/*      */   public static final String BACKGROUND_ORIGIN = "background-origin";
/*      */   public static final String BACKGROUND_POSITION = "background-position";
/*      */   public static final String BACKGROUND_POSITION_X = "background-position-x";
/*      */   public static final String BACKGROUND_POSITION_Y = "background-position-y";
/*      */   public static final String BACKGROUND_REPEAT = "background-repeat";
/*      */   public static final String BACKGROUND_SIZE = "background-size";
/*      */   public static final String BORDER = "border";
/*      */   public static final String BORDER_BOTTOM = "border-bottom";
/*      */   public static final String BORDER_BOTTOM_COLOR = "border-bottom-color";
/*      */   public static final String BORDER_BOTTOM_LEFT_RADIUS = "border-bottom-left-radius";
/*      */   public static final String BORDER_BOTTOM_RIGHT_RADIUS = "border-bottom-right-radius";
/*      */   public static final String BORDER_BOTTOM_STYLE = "border-bottom-style";
/*      */   public static final String BORDER_BOTTOM_WIDTH = "border-bottom-width";
/*      */   public static final String BORDER_COLLAPSE = "border-collapse";
/*      */   public static final String BORDER_COLOR = "border-color";
/*      */   public static final String BORDER_IMAGE = "border-image";
/*      */   public static final String BORDER_LEFT = "border-left";
/*      */   public static final String BORDER_LEFT_COLOR = "border-left-color";
/*      */   public static final String BORDER_LEFT_STYLE = "border-left-style";
/*      */   public static final String BORDER_LEFT_WIDTH = "border-left-width";
/*      */   public static final String BORDER_RADIUS = "border-radius";
/*      */   public static final String BORDER_RIGHT = "border-right";
/*      */   public static final String BORDER_RIGHT_COLOR = "border-right-color";
/*      */   public static final String BORDER_RIGHT_STYLE = "border-right-style";
/*      */   public static final String BORDER_RIGHT_WIDTH = "border-right-width";
/*      */   public static final String BORDER_SPACING = "border-spacing";
/*      */   public static final String BORDER_STYLE = "border-style";
/*      */   public static final String BORDER_TOP = "border-top";
/*      */   public static final String BORDER_TOP_COLOR = "border-top-color";
/*      */   public static final String BORDER_TOP_LEFT_RADIUS = "border-top-left-radius";
/*      */   public static final String BORDER_TOP_RIGHT_RADIUS = "border-top-right-radius";
/*      */   
/*      */   static {
/*   59 */     Map<String, String> keywordValues = new HashMap<>();
/*   60 */     keywordValues.put("xx-small", "9px");
/*   61 */     keywordValues.put("x-small", "10px");
/*   62 */     keywordValues.put("small", "13px");
/*   63 */     keywordValues.put("medium", "16px");
/*   64 */     keywordValues.put("large", "18px");
/*   65 */     keywordValues.put("x-large", "24px");
/*   66 */     keywordValues.put("xx-large", "32px");
/*   67 */     FONT_ABSOLUTE_SIZE_KEYWORDS_VALUES = Collections.unmodifiableMap(keywordValues);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1303 */     BACKGROUND_SIZE_VALUES = Collections.unmodifiableSet(new HashSet<>(
/* 1304 */           Arrays.asList(new String[] { "auto", "cover", "contain" })));
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1309 */     BACKGROUND_ORIGIN_OR_CLIP_VALUES = Collections.unmodifiableSet(new HashSet<>(
/* 1310 */           Arrays.asList(new String[] { "padding-box", "border-box", "content-box" })));
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1315 */     BACKGROUND_REPEAT_VALUES = Collections.unmodifiableSet(new HashSet<>(
/* 1316 */           Arrays.asList(new String[] { "repeat", "no-repeat", "repeat-x", "repeat-y", "round", "space" })));
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1321 */     BACKGROUND_ATTACHMENT_VALUES = Collections.unmodifiableSet(new HashSet<>(
/* 1322 */           Arrays.asList(new String[] { "fixed", "scroll", "local" })));
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1327 */     BACKGROUND_POSITION_VALUES = Collections.unmodifiableSet(new HashSet<>(
/* 1328 */           Arrays.asList(new String[] { "left", "center", "bottom", "top", "right" })));
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1333 */     BACKGROUND_POSITION_X_VALUES = Collections.unmodifiableSet(new HashSet<>(
/* 1334 */           Arrays.asList(new String[] { "left", "center", "right" })));
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1339 */     BACKGROUND_POSITION_Y_VALUES = Collections.unmodifiableSet(new HashSet<>(
/* 1340 */           Arrays.asList(new String[] { "center", "bottom", "top" })));
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1345 */     BORDER_WIDTH_VALUES = Collections.unmodifiableSet(new HashSet<>(
/* 1346 */           Arrays.asList(new String[] { "thin", "medium", "thick" })));
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1351 */     BORDER_STYLE_VALUES = Collections.unmodifiableSet(new HashSet<>(
/* 1352 */           Arrays.asList(new String[] { "none", "hidden", "dotted", "dashed", "solid", "double", "groove", "ridge", "inset", "outset" })));
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1357 */     BLEND_MODE_VALUES = Collections.unmodifiableSet(new HashSet<>(
/* 1358 */           Arrays.asList(new String[] { 
/*      */               "normal", "multiply", "screen", "overlay", "darken", "lighten", "color-dodge", "color-burn", "hard-light", "soft-light", 
/*      */               "difference", "exclusion", "hue", "saturation", "color", "luminosity" })));
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1370 */     METRIC_MEASUREMENTS_VALUES = new String[] { "px", "in", "cm", "mm", "pc", "pt", "q" };
/*      */   }
/*      */   
/*      */   public static final String BORDER_TOP_STYLE = "border-top-style";
/*      */   public static final String BORDER_TOP_WIDTH = "border-top-width";
/*      */   public static final String BORDER_WIDTH = "border-width";
/*      */   public static final String BOX_SHADOW = "box-shadow";
/*      */   public static final String CAPTION_SIDE = "caption-side";
/*      */   public static final String COLOR = "color";
/*      */   public static final String COLOR_DODGE = "color-dodge";
/*      */   public static final String COLOR_BURN = "color-burn";
/*      */   public static final String DARKEN = "darken";
/*      */   public static final String DIFFERENCE = "difference";
/*      */   public static final String DIRECTION = "direction";
/*      */   public static final String DISPLAY = "display";
/*      */   public static final String EMPTY_CELLS = "empty-cells";
/*      */   public static final String EXCLUSION = "exclusion";
/*      */   public static final String FLOAT = "float";
/*      */   public static final String FONT = "font";
/*      */   public static final String FONT_FAMILY = "font-family";
/*      */   public static final String FONT_FEATURE_SETTINGS = "font-feature-settings";
/*      */   public static final String FONT_KERNING = "font-kerning";
/*      */   public static final String FONT_LANGUAGE_OVERRIDE = "font-language-override";
/*      */   public static final String FONT_SIZE = "font-size";
/*      */   public static final String FONT_SIZE_ADJUST = "font-size-adjust";
/*      */   public static final String FONT_STRETCH = "font-stretch";
/*      */   public static final String FONT_STYLE = "font-style";
/*      */   public static final String FONT_SYNTHESIS = "font-synthesis";
/*      */   public static final String FONT_VARIANT = "font-variant";
/*      */   public static final String FONT_VARIANT_ALTERNATES = "font-variant-alternates";
/*      */   public static final String FONT_VARIANT_CAPS = "font-variant-caps";
/*      */   public static final String FONT_VARIANT_EAST_ASIAN = "font-variant-east-asian";
/*      */   public static final String FONT_VARIANT_LIGATURES = "font-variant-ligatures";
/*      */   public static final String FONT_VARIANT_NUMERIC = "font-variant-numeric";
/*      */   public static final String FONT_VARIANT_POSITION = "font-variant-position";
/*      */   public static final String FONT_WEIGHT = "font-weight";
/*      */   public static final String HANGING_PUNCTUATION = "hanging-punctuation";
/*      */   public static final String HARD_LIGHT = "hard-light";
/*      */   public static final String HUE = "hue";
/*      */   public static final String HYPHENS = "hyphens";
/*      */   public static final String INLINE_BLOCK = "inline-block";
/*      */   public static final String LETTER_SPACING = "letter-spacing";
/*      */   public static final String LINE_HEIGHT = "line-height";
/*      */   public static final String LIST_STYLE = "list-style";
/*      */   public static final String LIST_STYLE_IMAGE = "list-style-image";
/*      */   public static final String LIST_STYLE_POSITION = "list-style-position";
/*      */   public static final String LIST_STYLE_TYPE = "list-style-type";
/*      */   public static final String MARGIN = "margin";
/*      */   public static final String MARGIN_BOTTOM = "margin-bottom";
/*      */   public static final String MARGIN_LEFT = "margin-left";
/*      */   public static final String MARGIN_RIGHT = "margin-right";
/*      */   public static final String MARGIN_TOP = "margin-top";
/*      */   public static final String MIN_HEIGHT = "min-height";
/*      */   public static final String MULTIPLY = "multiply";
/*      */   public static final String OPACITY = "opacity";
/*      */   public static final String ORPHANS = "orphans";
/*      */   public static final String OUTLINE = "outline";
/*      */   public static final String OUTLINE_COLOR = "outline-color";
/*      */   public static final String OUTLINE_STYLE = "outline-style";
/*      */   public static final String OUTLINE_WIDTH = "outline-width";
/*      */   public static final String OVERFLOW_WRAP = "overflow-wrap";
/*      */   public static final String OVERFLOW = "overflow";
/*      */   public static final String PADDING = "padding";
/*      */   public static final String PADDING_BOTTOM = "padding-bottom";
/*      */   public static final String PADDING_LEFT = "padding-left";
/*      */   public static final String PADDING_RIGHT = "padding-right";
/*      */   public static final String PADDING_TOP = "padding-top";
/*      */   public static final String PAGE_BREAK_AFTER = "page-break-after";
/*      */   public static final String PAGE_BREAK_BEFORE = "page-break-before";
/*      */   public static final String PAGE_BREAK_INSIDE = "page-break-inside";
/*      */   public static final String POSITION = "position";
/*      */   public static final String QUOTES = "quotes";
/*      */   public static final String TAB_SIZE = "tab-size";
/*      */   public static final String TEXT_ALIGN = "text-align";
/*      */   public static final String TEXT_ALIGN_LAST = "text-align-last";
/*      */   public static final String TEXT_COMBINE_UPRIGHT = "text-combine-upright";
/*      */   public static final String TEXT_DECORATION = "text-decoration";
/*      */   public static final String TEXT_DECORATION_LINE = "text-decoration-line";
/*      */   public static final String TEXT_DECORATION_STYLE = "text-decoration-style";
/*      */   public static final String TEXT_DECORATION_COLOR = "text-decoration-color";
/*      */   public static final String TEXT_INDENT = "text-indent";
/*      */   public static final String TEXT_JUSTIFY = "text-justify";
/*      */   public static final String TEXT_ORIENTATION = "text-orientation";
/*      */   public static final String TEXT_SHADOW = "text-shadow";
/*      */   public static final String TEXT_TRANSFORM = "text-transform";
/*      */   public static final String TEXT_UNDERLINE_POSITION = "text-underline-position";
/*      */   public static final String TRANSFORM = "transform";
/*      */   public static final String UNICODE_BIDI = "unicode-bidi";
/*      */   public static final String VISIBILITY = "visibility";
/*      */   public static final String WHITE_SPACE = "white-space";
/*      */   public static final String WIDOWS = "widows";
/*      */   public static final String WIDTH = "width";
/*      */   public static final String HEIGHT = "height";
/*      */   public static final String WORDWRAP = "word-wrap";
/*      */   public static final String WORD_BREAK = "word-break";
/*      */   public static final String WORD_SPACING = "word-spacing";
/*      */   public static final String WRITING_MODE = "writing-mode";
/*      */   public static final String ALWAYS = "always";
/*      */   public static final String ARMENIAN = "armenian";
/*      */   public static final String AVOID = "avoid";
/*      */   public static final String AUTO = "auto";
/*      */   public static final String BLINK = "blink";
/*      */   public static final String BOLD = "bold";
/*      */   public static final String BOLDER = "bolder";
/*      */   public static final String BORDER_BOX = "border-box";
/*      */   public static final String BOTTOM = "bottom";
/*      */   public static final String CAPTION = "caption";
/*      */   public static final String CENTER = "center";
/*      */   public static final String CIRCLE = "circle";
/*      */   public static final String CJK_IDEOGRAPHIC = "cjk-ideographic";
/*      */   public static final String CLOSE_QUOTE = "close-quote";
/*      */   public static final String CONTAIN = "contain";
/*      */   public static final String CONTENT_BOX = "content-box";
/*      */   public static final String COVER = "cover";
/*      */   public static final String CURRENTCOLOR = "currentcolor";
/*      */   public static final String DASHED = "dashed";
/*      */   public static final String DECIMAL = "decimal";
/*      */   public static final String DECIMAL_LEADING_ZERO = "decimal-leading-zero";
/*      */   public static final String DEG = "deg";
/*      */   public static final String DISC = "disc";
/*      */   public static final String DOTTED = "dotted";
/*      */   public static final String DOUBLE = "double";
/*      */   public static final String FIXED = "fixed";
/*      */   public static final String GEORGIAN = "georgian";
/*      */   public static final String GRAD = "grad";
/*      */   public static final String GROOVE = "groove";
/*      */   public static final String HEBREW = "hebrew";
/*      */   public static final String HIDDEN = "hidden";
/*      */   public static final String HIRAGANA = "hiragana";
/*      */   public static final String HIRAGANA_IROHA = "hiragana-iroha";
/*      */   public static final String ICON = "icon";
/*      */   public static final String INHERIT = "inherit";
/*      */   public static final String INITIAL = "initial";
/*      */   public static final String INSET = "inset";
/*      */   public static final String INSIDE = "inside";
/*      */   public static final String ITALIC = "italic";
/*      */   public static final String LARGE = "large";
/*      */   public static final String LARGER = "larger";
/*      */   public static final String LEFT = "left";
/*      */   public static final String LIGHTEN = "lighten";
/*      */   public static final String LIGHTER = "lighter";
/*      */   public static final String LINE_THROUGH = "line-through";
/*      */   public static final String LOCAL = "local";
/*      */   public static final String LOWER_ALPHA = "lower-alpha";
/*      */   public static final String LOWER_GREEK = "lower-greek";
/*      */   public static final String LOWER_LATIN = "lower-latin";
/*      */   public static final String LOWER_ROMAN = "lower-roman";
/*      */   public static final String LUMINOSITY = "luminosity";
/*      */   public static final String MANUAL = "manual";
/*      */   public static final String MATRIX = "matrix";
/*      */   public static final String MEDIUM = "medium";
/*      */   public static final String MENU = "menu";
/*      */   public static final String MESSAGE_BOX = "message-box";
/*      */   public static final String NO_OPEN_QUOTE = "no-open-quote";
/*      */   public static final String NO_CLOSE_QUOTE = "no-close-quote";
/*      */   public static final String NO_REPEAT = "no-repeat";
/*      */   public static final String NONE = "none";
/*      */   public static final String NORMAL = "normal";
/*      */   public static final String OBLIQUE = "oblique";
/*      */   public static final String OPEN_QUOTE = "open-quote";
/*      */   public static final String OUTSIDE = "outside";
/*      */   public static final String OUTSET = "outset";
/*      */   public static final String OVERLAY = "overlay";
/*      */   public static final String OVERLINE = "overline";
/*      */   public static final String PADDING_BOX = "padding-box";
/*      */   public static final String RAD = "rad";
/*      */   public static final String REPEAT = "repeat";
/*      */   public static final String REPEAT_X = "repeat-x";
/*      */   public static final String REPEAT_Y = "repeat-y";
/*      */   public static final String RIDGE = "ridge";
/*      */   public static final String RIGHT = "right";
/*      */   public static final String ROTATE = "rotate";
/*      */   public static final String ROUND = "round";
/*      */   public static final String SATURATION = "saturation";
/*      */   public static final String SCALE = "scale";
/*      */   public static final String SCALE_X = "scalex";
/*      */   public static final String SCALE_Y = "scaley";
/*      */   public static final String SCREEN = "screen";
/*      */   public static final String SCROLL = "scroll";
/*      */   public static final String SKEW = "skew";
/*      */   public static final String SKEW_X = "skewx";
/*      */   public static final String SKEW_Y = "skewy";
/*      */   public static final String SMALL = "small";
/*      */   public static final String SMALL_CAPS = "small-caps";
/*      */   public static final String SMALL_CAPTION = "small-caption";
/*      */   public static final String SMALLER = "smaller";
/*      */   public static final String SOFT_LIGHT = "soft-light";
/*      */   public static final String SOLID = "solid";
/*      */   public static final String SPACE = "space";
/*      */   public static final String SQUARE = "square";
/*      */   public static final String START = "start";
/*      */   public static final String STATIC = "static";
/*      */   public static final String STATUS_BAR = "status-bar";
/*      */   public static final String THICK = "thick";
/*      */   public static final String THIN = "thin";
/*      */   public static final String TOP = "top";
/*      */   public static final String TRANSLATE = "translate";
/*      */   public static final String TRANSLATE_X = "translatex";
/*      */   public static final String TRANSLATE_Y = "translatey";
/*      */   public static final String TRANSPARENT = "transparent";
/*      */   public static final String UNDEFINED_NAME = "undefined";
/*      */   public static final String UNDERLINE = "underline";
/*      */   public static final String UNSET = "unset";
/*      */   public static final String UPPER_ALPHA = "upper-alpha";
/*      */   public static final String UPPER_LATIN = "upper-latin";
/*      */   public static final String UPPER_ROMAN = "upper-roman";
/*      */   public static final String VISIBLE = "visible";
/*      */   public static final String WAVY = "wavy";
/*      */   public static final String X_LARGE = "x-large";
/*      */   public static final String X_SMALL = "x-small";
/*      */   public static final String XX_LARGE = "xx-large";
/*      */   public static final String XX_SMALL = "xx-small";
/*      */   public static final Set<String> BACKGROUND_SIZE_VALUES;
/*      */   public static final Set<String> BACKGROUND_ORIGIN_OR_CLIP_VALUES;
/*      */   public static final Set<String> BACKGROUND_REPEAT_VALUES;
/*      */   public static final Set<String> BACKGROUND_ATTACHMENT_VALUES;
/*      */   public static final Set<String> BACKGROUND_POSITION_VALUES;
/*      */   public static final Set<String> BACKGROUND_POSITION_X_VALUES;
/*      */   public static final Set<String> BACKGROUND_POSITION_Y_VALUES;
/*      */   public static final Set<String> BORDER_WIDTH_VALUES;
/*      */   public static final Set<String> BORDER_STYLE_VALUES;
/*      */   public static final Set<String> BLEND_MODE_VALUES;
/*      */   public static final Map<String, String> FONT_ABSOLUTE_SIZE_KEYWORDS_VALUES;
/*      */   public static final String[] METRIC_MEASUREMENTS_VALUES;
/*      */   public static final String ACTIVE = "active";
/*      */   public static final String CHECKED = "checked";
/*      */   public static final String DISABLED = "disabled";
/*      */   public static final String EMPTY = "empty";
/*      */   public static final String ENABLED = "enabled";
/*      */   public static final String FIRST_CHILD = "first-child";
/*      */   public static final String FIRST_OF_TYPE = "first-of-type";
/*      */   public static final String FOCUS = "focus";
/*      */   public static final String HOVER = "hover";
/*      */   public static final String IN_RANGE = "in-range";
/*      */   public static final String INVALID = "invalid";
/*      */   public static final String LANG = "lang";
/*      */   public static final String LAST_CHILD = "last-child";
/*      */   public static final String LAST_OF_TYPE = "last-of-type";
/*      */   public static final String LINK = "link";
/*      */   public static final String NTH_CHILD = "nth-child";
/*      */   public static final String NOT = "not";
/*      */   public static final String NTH_LAST_CHILD = "nth-last-child";
/*      */   public static final String NTH_LAST_OF_TYPE = "nth-last-of-type";
/*      */   public static final String NTH_OF_TYPE = "nth-of-type";
/*      */   public static final String ONLY_OF_TYPE = "only-of-type";
/*      */   public static final String ONLY_CHILD = "only-child";
/*      */   public static final String OPTIONAL = "optional";
/*      */   public static final String OUT_OF_RANGE = "out-of-range";
/*      */   public static final String READ_ONLY = "read-only";
/*      */   public static final String READ_WRITE = "read-write";
/*      */   public static final String REQUIRED = "required";
/*      */   public static final String ROOT = "root";
/*      */   public static final String TARGET = "target";
/*      */   public static final String VALID = "valid";
/*      */   public static final String VISITED = "visited";
/*      */   public static final String CM = "cm";
/*      */   public static final String EM = "em";
/*      */   public static final String EX = "ex";
/*      */   public static final String IN = "in";
/*      */   public static final String MM = "mm";
/*      */   public static final String PC = "pc";
/*      */   public static final String PERCENTAGE = "%";
/*      */   public static final String PT = "pt";
/*      */   public static final String PX = "px";
/*      */   public static final String REM = "rem";
/*      */   public static final String Q = "q";
/*      */   public static final String DPCM = "dpcm";
/*      */   public static final String DPI = "dpi";
/*      */   public static final String DPPX = "dppx";
/*      */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/styledxmlparser/css/CommonCssConstants.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */