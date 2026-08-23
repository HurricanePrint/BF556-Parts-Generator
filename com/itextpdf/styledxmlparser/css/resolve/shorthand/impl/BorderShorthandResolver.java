/*    */ package com.itextpdf.styledxmlparser.css.resolve.shorthand.impl;
/*    */ 
/*    */ import com.itextpdf.io.util.MessageFormatUtil;
/*    */ import com.itextpdf.styledxmlparser.css.CssDeclaration;
/*    */ import com.itextpdf.styledxmlparser.css.resolve.shorthand.IShorthandResolver;
/*    */ import com.itextpdf.styledxmlparser.css.resolve.shorthand.ShorthandResolverFactory;
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ import org.slf4j.Logger;
/*    */ import org.slf4j.LoggerFactory;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class BorderShorthandResolver
/*    */   extends AbstractBorderShorthandResolver
/*    */ {
/*    */   protected String getPrefix() {
/* 66 */     return "border";
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public List<CssDeclaration> resolveShorthand(String shorthandExpression) {
/* 74 */     List<CssDeclaration> preResolvedProps = super.resolveShorthand(shorthandExpression);
/* 75 */     List<CssDeclaration> resolvedProps = new ArrayList<>();
/* 76 */     for (CssDeclaration prop : preResolvedProps) {
/* 77 */       IShorthandResolver shorthandResolver = ShorthandResolverFactory.getShorthandResolver(prop.getProperty());
/* 78 */       if (shorthandResolver != null) {
/* 79 */         resolvedProps.addAll(shorthandResolver.resolveShorthand(prop.getExpression())); continue;
/*    */       } 
/* 81 */       Logger logger = LoggerFactory.getLogger(BorderShorthandResolver.class);
/* 82 */       logger.error(MessageFormatUtil.format("Cannot find a shorthand resolver for the \"{0}\" property. Expected border-width, border-style or border-color properties.", new Object[] { prop
/* 83 */               .getProperty() }));
/*    */     } 
/*    */     
/* 86 */     return resolvedProps;
/*    */   }
/*    */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/styledxmlparser/css/resolve/shorthand/impl/BorderShorthandResolver.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */