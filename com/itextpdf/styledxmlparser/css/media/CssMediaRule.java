/*     */ package com.itextpdf.styledxmlparser.css.media;
/*     */ 
/*     */ import com.itextpdf.styledxmlparser.css.CssNestedAtRule;
/*     */ import com.itextpdf.styledxmlparser.css.CssRuleSet;
/*     */ import com.itextpdf.styledxmlparser.css.CssStatement;
/*     */ import com.itextpdf.styledxmlparser.node.INode;
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
/*     */ 
/*     */ public class CssMediaRule
/*     */   extends CssNestedAtRule
/*     */ {
/*     */   private List<MediaQuery> mediaQueries;
/*     */   
/*     */   public CssMediaRule(String ruleParameters) {
/*  68 */     super("media", ruleParameters);
/*  69 */     this.mediaQueries = MediaQueryParser.parseMediaQueries(ruleParameters);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List<CssRuleSet> getCssRuleSets(INode element, MediaDeviceDescription deviceDescription) {
/*  77 */     List<CssRuleSet> result = new ArrayList<>();
/*  78 */     for (MediaQuery mediaQuery : this.mediaQueries) {
/*  79 */       if (mediaQuery.matches(deviceDescription)) {
/*  80 */         for (CssStatement childStatement : this.body) {
/*  81 */           result.addAll(childStatement.getCssRuleSets(element, deviceDescription));
/*     */         }
/*     */         break;
/*     */       } 
/*     */     } 
/*  86 */     return result;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean matchMediaDevice(MediaDeviceDescription deviceDescription) {
/*  96 */     for (MediaQuery mediaQuery : this.mediaQueries) {
/*  97 */       if (mediaQuery.matches(deviceDescription)) {
/*  98 */         return true;
/*     */       }
/*     */     } 
/* 101 */     return false;
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/styledxmlparser/css/media/CssMediaRule.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */