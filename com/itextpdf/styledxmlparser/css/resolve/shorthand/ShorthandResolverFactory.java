/*     */ package com.itextpdf.styledxmlparser.css.resolve.shorthand;
/*     */ 
/*     */ import com.itextpdf.styledxmlparser.css.resolve.shorthand.impl.BackgroundPositionShorthandResolver;
/*     */ import com.itextpdf.styledxmlparser.css.resolve.shorthand.impl.BackgroundShorthandResolver;
/*     */ import com.itextpdf.styledxmlparser.css.resolve.shorthand.impl.BorderBottomShorthandResolver;
/*     */ import com.itextpdf.styledxmlparser.css.resolve.shorthand.impl.BorderColorShorthandResolver;
/*     */ import com.itextpdf.styledxmlparser.css.resolve.shorthand.impl.BorderLeftShorthandResolver;
/*     */ import com.itextpdf.styledxmlparser.css.resolve.shorthand.impl.BorderRadiusShorthandResolver;
/*     */ import com.itextpdf.styledxmlparser.css.resolve.shorthand.impl.BorderRightShorthandResolver;
/*     */ import com.itextpdf.styledxmlparser.css.resolve.shorthand.impl.BorderShorthandResolver;
/*     */ import com.itextpdf.styledxmlparser.css.resolve.shorthand.impl.BorderStyleShorthandResolver;
/*     */ import com.itextpdf.styledxmlparser.css.resolve.shorthand.impl.BorderTopShorthandResolver;
/*     */ import com.itextpdf.styledxmlparser.css.resolve.shorthand.impl.BorderWidthShorthandResolver;
/*     */ import com.itextpdf.styledxmlparser.css.resolve.shorthand.impl.FontShorthandResolver;
/*     */ import com.itextpdf.styledxmlparser.css.resolve.shorthand.impl.ListStyleShorthandResolver;
/*     */ import com.itextpdf.styledxmlparser.css.resolve.shorthand.impl.MarginShorthandResolver;
/*     */ import com.itextpdf.styledxmlparser.css.resolve.shorthand.impl.OutlineShorthandResolver;
/*     */ import com.itextpdf.styledxmlparser.css.resolve.shorthand.impl.PaddingShorthandResolver;
/*     */ import com.itextpdf.styledxmlparser.css.resolve.shorthand.impl.TextDecorationShorthandResolver;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ShorthandResolverFactory
/*     */ {
/*  77 */   private static final Map<String, IShorthandResolver> shorthandResolvers = new HashMap<>(); static {
/*  78 */     shorthandResolvers.put("background", new BackgroundShorthandResolver());
/*  79 */     shorthandResolvers.put("background-position", new BackgroundPositionShorthandResolver());
/*  80 */     shorthandResolvers.put("border", new BorderShorthandResolver());
/*  81 */     shorthandResolvers.put("border-bottom", new BorderBottomShorthandResolver());
/*  82 */     shorthandResolvers.put("border-color", new BorderColorShorthandResolver());
/*  83 */     shorthandResolvers.put("border-left", new BorderLeftShorthandResolver());
/*  84 */     shorthandResolvers.put("border-radius", new BorderRadiusShorthandResolver());
/*  85 */     shorthandResolvers.put("border-right", new BorderRightShorthandResolver());
/*  86 */     shorthandResolvers.put("border-style", new BorderStyleShorthandResolver());
/*  87 */     shorthandResolvers.put("border-top", new BorderTopShorthandResolver());
/*  88 */     shorthandResolvers.put("border-width", new BorderWidthShorthandResolver());
/*  89 */     shorthandResolvers.put("font", new FontShorthandResolver());
/*  90 */     shorthandResolvers.put("list-style", new ListStyleShorthandResolver());
/*  91 */     shorthandResolvers.put("margin", new MarginShorthandResolver());
/*  92 */     shorthandResolvers.put("outline", new OutlineShorthandResolver());
/*  93 */     shorthandResolvers.put("padding", new PaddingShorthandResolver());
/*  94 */     shorthandResolvers.put("text-decoration", new TextDecorationShorthandResolver());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static IShorthandResolver getShorthandResolver(String shorthandProperty) {
/* 104 */     return shorthandResolvers.get(shorthandProperty);
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/styledxmlparser/css/resolve/shorthand/ShorthandResolverFactory.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */