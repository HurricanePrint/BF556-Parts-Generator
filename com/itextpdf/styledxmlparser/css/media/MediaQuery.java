/*     */ package com.itextpdf.styledxmlparser.css.media;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.Objects;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class MediaQuery
/*     */ {
/*     */   private boolean only;
/*     */   private boolean not;
/*     */   private String type;
/*     */   private List<MediaExpression> expressions;
/*     */   
/*     */   MediaQuery(String type, List<MediaExpression> expressions, boolean only, boolean not) {
/*  83 */     this.type = type;
/*  84 */     this.expressions = new ArrayList<>(expressions);
/*  85 */     this.only = only;
/*  86 */     this.not = not;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean matches(MediaDeviceDescription deviceDescription) {
/*  96 */     boolean typeMatches = (this.type == null || MediaType.ALL.equals(this.type) || Objects.equals(this.type, deviceDescription.getType()));
/*     */     
/*  98 */     boolean matchesExpressions = true;
/*  99 */     for (MediaExpression expression : this.expressions) {
/* 100 */       if (!expression.matches(deviceDescription)) {
/* 101 */         matchesExpressions = false;
/*     */         
/*     */         break;
/*     */       } 
/*     */     } 
/* 106 */     boolean expressionResult = (typeMatches && matchesExpressions);
/* 107 */     if (this.not) {
/* 108 */       expressionResult = !expressionResult;
/*     */     }
/*     */     
/* 111 */     return expressionResult;
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/styledxmlparser/css/media/MediaQuery.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */