/*     */ package com.itextpdf.styledxmlparser.css.validate.impl.datatype;
/*     */ 
/*     */ import com.itextpdf.styledxmlparser.css.validate.ICssDataTypeValidator;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class CssTransformValidator
/*     */   implements ICssDataTypeValidator
/*     */ {
/*     */   public boolean isValid(String objectString) {
/*  59 */     if ("none".equals(objectString))
/*  60 */       return true; 
/*  61 */     String[] components = objectString.split("\\)");
/*  62 */     for (String component : components) {
/*  63 */       if (!isValidComponent(component))
/*  64 */         return false; 
/*  65 */     }  return true;
/*     */   }
/*     */   private boolean isValidComponent(String objectString) {
/*     */     String function;
/*     */     String args;
/*  70 */     if (!"none".equals(objectString) && objectString.indexOf('(') > 0) {
/*  71 */       function = objectString.substring(0, objectString.indexOf('(')).trim();
/*  72 */       args = objectString.substring(objectString.indexOf('(') + 1);
/*     */     } else {
/*  74 */       return false;
/*     */     } 
/*  76 */     if ("matrix".equals(function) || "scale".equals(function) || "scalex"
/*  77 */       .equals(function) || "scaley".equals(function)) {
/*  78 */       String[] arg = args.split(",");
/*  79 */       if ((arg.length == 6 && "matrix".equals(function)) || ((arg.length == 1 || arg.length == 2) && "scale"
/*  80 */         .equals(function)) || (arg.length == 1 && ("scalex"
/*  81 */         .equals(function) || "scaley".equals(function)))) {
/*  82 */         int i = 0;
/*  83 */         for (; i < arg.length; i++) {
/*     */           try {
/*  85 */             Float.parseFloat(arg[i].trim());
/*  86 */           } catch (NumberFormatException exc) {
/*  87 */             return false;
/*     */           } 
/*     */         } 
/*  90 */         if (i == arg.length)
/*  91 */           return true; 
/*     */       } 
/*  93 */       return false;
/*  94 */     }  if ("translate".equals(function) || "translatex"
/*  95 */       .equals(function) || "translatey".equals(function)) {
/*  96 */       String[] arg = args.split(",");
/*  97 */       if (arg.length == 1 || (arg.length == 2 && "translate".equals(function))) {
/*  98 */         for (String a : arg) {
/*  99 */           if (!isValidForTranslate(a))
/* 100 */             return false; 
/* 101 */         }  return true;
/*     */       } 
/* 103 */       return false;
/* 104 */     }  if ("rotate".equals(function)) {
/*     */       try {
/* 106 */         float value = Float.parseFloat(args);
/* 107 */         if (value == 0.0F)
/* 108 */           return true; 
/* 109 */       } catch (NumberFormatException numberFormatException) {}
/*     */       
/* 111 */       int deg = args.indexOf('d');
/* 112 */       int rad = args.indexOf('r');
/* 113 */       if ((deg > 0 && args.substring(deg).equals("deg")) || (rad > 0 && args.substring(rad).equals("rad"))) {
/*     */         try {
/* 115 */           Double.parseDouble(args.substring(0, (deg > 0) ? deg : rad));
/* 116 */         } catch (NumberFormatException exc) {
/* 117 */           return false;
/*     */         } 
/* 119 */         return true;
/*     */       } 
/* 121 */       return false;
/* 122 */     }  if ("skew".equals(function) || "skewx"
/* 123 */       .equals(function) || "skewy".equals(function)) {
/* 124 */       String[] arg = args.split(",");
/* 125 */       if (arg.length == 1 || (arg.length == 2 && "skew".equals(function))) {
/* 126 */         for (int k = 0; k < arg.length; k++) {
/*     */           try {
/* 128 */             float value = Float.parseFloat(arg[k]);
/* 129 */             if (value != 0.0F)
/* 130 */               return false; 
/* 131 */           } catch (NumberFormatException numberFormatException) {}
/*     */           
/* 133 */           int deg = arg[k].indexOf('d');
/* 134 */           int rad = arg[k].indexOf('r');
/* 135 */           if (deg < 0 && rad < 0)
/* 136 */             return false; 
/* 137 */           if ((deg > 0 && !arg[k].substring(deg).equals("deg") && rad < 0) || (rad > 0 && !arg[k].substring(rad).equals("rad")))
/* 138 */             return false; 
/*     */           try {
/* 140 */             Float.parseFloat(arg[k].trim().substring(0, (rad > 0) ? rad : deg));
/* 141 */           } catch (NumberFormatException exc) {
/* 142 */             return false;
/*     */           } 
/*     */         } 
/* 145 */         return true;
/*     */       } 
/*     */     } 
/* 148 */     return false;
/*     */   }
/*     */   
/*     */   private static boolean isValidForTranslate(String string) {
/* 152 */     if (string == null)
/* 153 */       return false; 
/* 154 */     int pos = 0;
/* 155 */     while (pos < string.length() && (
/* 156 */       string.charAt(pos) == '+' || string
/* 157 */       .charAt(pos) == '-' || string
/* 158 */       .charAt(pos) == '.' || (string
/* 159 */       .charAt(pos) >= '0' && string.charAt(pos) <= '9'))) {
/* 160 */       pos++;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 165 */     if (pos > 0) {
/*     */       try {
/* 167 */         Float.parseFloat(string.substring(0, pos));
/* 168 */       } catch (NumberFormatException exc) {
/* 169 */         return false;
/*     */       } 
/* 171 */       return (Float.parseFloat(string.substring(0, pos)) == 0.0F || string.substring(pos).equals("pt") || string.substring(pos).equals("in") || string
/* 172 */         .substring(pos).equals("cm") || string.substring(pos).equals("q") || string
/* 173 */         .substring(pos).equals("mm") || string.substring(pos).equals("pc") || string
/* 174 */         .substring(pos).equals("px") || string.substring(pos).equals("%"));
/*     */     } 
/* 176 */     return false;
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/styledxmlparser/css/validate/impl/datatype/CssTransformValidator.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */