/*     */ package com.itextpdf.styledxmlparser.css.resolve;
/*     */ 
/*     */ import com.itextpdf.io.util.MessageFormatUtil;
/*     */ import java.util.HashMap;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class CssDefaults
/*     */ {
/*  60 */   private static final Map<String, String> defaultValues = new HashMap<>();
/*     */   
/*     */   static {
/*  63 */     defaultValues.put("color", "black");
/*  64 */     defaultValues.put("opacity", "1");
/*     */     
/*  66 */     defaultValues.put("background-attachment", "scroll");
/*  67 */     defaultValues.put("background-blend-mode", "normal");
/*  68 */     defaultValues.put("background-color", "transparent");
/*  69 */     defaultValues.put("background-image", "none");
/*  70 */     defaultValues.put("background-position", "0% 0%");
/*  71 */     defaultValues.put("background-position-x", "0%");
/*  72 */     defaultValues.put("background-position-y", "0%");
/*  73 */     defaultValues.put("background-repeat", "repeat");
/*  74 */     defaultValues.put("background-clip", "border-box");
/*  75 */     defaultValues.put("background-origin", "padding-box");
/*  76 */     defaultValues.put("background-size", "auto");
/*     */     
/*  78 */     defaultValues.put("border-bottom-color", "currentcolor");
/*  79 */     defaultValues.put("border-left-color", "currentcolor");
/*  80 */     defaultValues.put("border-right-color", "currentcolor");
/*  81 */     defaultValues.put("border-top-color", "currentcolor");
/*  82 */     defaultValues.put("border-bottom-style", "none");
/*  83 */     defaultValues.put("border-left-style", "none");
/*  84 */     defaultValues.put("border-right-style", "none");
/*  85 */     defaultValues.put("border-top-style", "none");
/*  86 */     defaultValues.put("border-bottom-width", "medium");
/*  87 */     defaultValues.put("border-left-width", "medium");
/*  88 */     defaultValues.put("border-right-width", "medium");
/*  89 */     defaultValues.put("border-top-width", "medium");
/*  90 */     defaultValues.put("border-width", "medium");
/*  91 */     defaultValues.put("border-image", "none");
/*     */     
/*  93 */     defaultValues.put("border-radius", "0");
/*  94 */     defaultValues.put("border-bottom-left-radius", "0");
/*  95 */     defaultValues.put("border-bottom-right-radius", "0");
/*  96 */     defaultValues.put("border-top-left-radius", "0");
/*  97 */     defaultValues.put("border-top-right-radius", "0");
/*     */     
/*  99 */     defaultValues.put("box-shadow", "none");
/*     */     
/* 101 */     defaultValues.put("float", "none");
/* 102 */     defaultValues.put("font-family", "times");
/* 103 */     defaultValues.put("font-size", "medium");
/* 104 */     defaultValues.put("font-style", "normal");
/* 105 */     defaultValues.put("font-variant", "normal");
/* 106 */     defaultValues.put("font-weight", "normal");
/*     */     
/* 108 */     defaultValues.put("height", "auto");
/* 109 */     defaultValues.put("hyphens", "manual");
/*     */     
/* 111 */     defaultValues.put("line-height", "normal");
/* 112 */     defaultValues.put("list-style-type", "disc");
/* 113 */     defaultValues.put("list-style-image", "none");
/* 114 */     defaultValues.put("list-style-position", "outside");
/*     */     
/* 116 */     defaultValues.put("margin-bottom", "0");
/* 117 */     defaultValues.put("margin-left", "0");
/* 118 */     defaultValues.put("margin-right", "0");
/* 119 */     defaultValues.put("margin-top", "0");
/*     */     
/* 121 */     defaultValues.put("min-height", "0");
/*     */     
/* 123 */     defaultValues.put("outline-color", "currentcolor");
/* 124 */     defaultValues.put("outline-style", "none");
/* 125 */     defaultValues.put("outline-width", "medium");
/*     */     
/* 127 */     defaultValues.put("padding-bottom", "0");
/* 128 */     defaultValues.put("padding-left", "0");
/* 129 */     defaultValues.put("padding-right", "0");
/* 130 */     defaultValues.put("padding-top", "0");
/*     */     
/* 132 */     defaultValues.put("page-break-after", "auto");
/* 133 */     defaultValues.put("page-break-before", "auto");
/* 134 */     defaultValues.put("page-break-inside", "auto");
/*     */     
/* 136 */     defaultValues.put("position", "static");
/*     */     
/* 138 */     defaultValues.put("quotes", "\"\\00ab\" \"\\00bb\"");
/*     */     
/* 140 */     defaultValues.put("text-align", "start");
/* 141 */     defaultValues.put("text-decoration", "none");
/* 142 */     defaultValues.put("text-decoration-line", "none");
/* 143 */     defaultValues.put("text-decoration-style", "solid");
/* 144 */     defaultValues.put("text-decoration-color", "currentcolor");
/* 145 */     defaultValues.put("text-transform", "none");
/*     */     
/* 147 */     defaultValues.put("white-space", "normal");
/* 148 */     defaultValues.put("width", "auto");
/*     */     
/* 150 */     defaultValues.put("orphans", "2");
/* 151 */     defaultValues.put("widows", "2");
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
/*     */   public static String getDefaultValue(String property) {
/* 163 */     String defaultVal = defaultValues.get(property);
/* 164 */     if (defaultVal == null) {
/* 165 */       Logger logger = LoggerFactory.getLogger(CssDefaults.class);
/* 166 */       logger.error(MessageFormatUtil.format("Default value of the css property \"{0}\" is unknown.", new Object[] { property }));
/*     */     } 
/* 168 */     return defaultVal;
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/styledxmlparser/css/resolve/CssDefaults.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */