/*     */ package com.itextpdf.styledxmlparser.css;
/*     */ 
/*     */ import com.itextpdf.layout.font.Range;
/*     */ import com.itextpdf.styledxmlparser.css.util.CssUtils;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class CssFontFaceRule
/*     */   extends CssNestedAtRule
/*     */ {
/*     */   private List<CssDeclaration> properties;
/*     */   
/*     */   public CssFontFaceRule() {
/*  65 */     this("");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public CssFontFaceRule(String ruleParameters) {
/*  76 */     super("font-face", ruleParameters);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List<CssDeclaration> getProperties() {
/*  85 */     return new ArrayList<>(this.properties);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addBodyCssDeclarations(List<CssDeclaration> cssDeclarations) {
/*  93 */     this.properties = new ArrayList<>(cssDeclarations);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 101 */     StringBuilder sb = new StringBuilder();
/* 102 */     sb.append("@").append(getRuleName()).append(" {").append("\n");
/* 103 */     for (CssDeclaration declaration : this.properties) {
/* 104 */       sb.append("    ");
/* 105 */       sb.append(declaration);
/* 106 */       sb.append(";\n");
/*     */     } 
/* 108 */     sb.append("}");
/* 109 */     return sb.toString();
/*     */   }
/*     */   
/*     */   public Range resolveUnicodeRange() {
/* 113 */     Range range = null;
/* 114 */     for (CssDeclaration descriptor : getProperties()) {
/* 115 */       if ("unicode-range".equals(descriptor.getProperty())) {
/* 116 */         range = CssUtils.parseUnicodeRange(descriptor.getExpression());
/*     */       }
/*     */     } 
/* 119 */     return range;
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/styledxmlparser/css/CssFontFaceRule.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */