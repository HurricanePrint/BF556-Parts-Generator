/*     */ package com.itextpdf.layout.element;
/*     */ 
/*     */ import com.itextpdf.kernel.pdf.tagutils.AccessibilityProperties;
/*     */ import com.itextpdf.kernel.pdf.tagutils.DefaultAccessibilityProperties;
/*     */ import com.itextpdf.layout.property.ListNumberingType;
/*     */ import com.itextpdf.layout.property.ListSymbolAlignment;
/*     */ import com.itextpdf.layout.property.ListSymbolPosition;
/*     */ import com.itextpdf.layout.renderer.IRenderer;
/*     */ import com.itextpdf.layout.renderer.ListRenderer;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class List
/*     */   extends BlockElement<List>
/*     */ {
/*     */   public static final String DEFAULT_LIST_SYMBOL = "- ";
/*     */   protected DefaultAccessibilityProperties tagProperties;
/*     */   
/*     */   public List() {}
/*     */   
/*     */   public List(ListNumberingType listNumberingType) {
/*  82 */     setListSymbol(listNumberingType);
/*     */   }
/*     */ 
/*     */   
/*     */   public <T1> T1 getDefaultProperty(int property) {
/*  87 */     switch (property) {
/*     */       case 37:
/*  89 */         return (T1)new Text("- ");
/*     */       case 41:
/*  91 */         return (T1)"";
/*     */       case 42:
/*  93 */         return (T1)". ";
/*     */       case 83:
/*  95 */         return (T1)ListSymbolPosition.DEFAULT;
/*     */     } 
/*  97 */     return super.getDefaultProperty(property);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List add(ListItem listItem) {
/* 108 */     this.childElements.add(listItem);
/* 109 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List add(String text) {
/* 119 */     return add(new ListItem(text));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List setItemStartIndex(int start) {
/* 129 */     setProperty(36, Integer.valueOf(start));
/* 130 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List setListSymbol(String symbol) {
/* 141 */     return setListSymbol(new Text(symbol));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List setListSymbol(Text text) {
/* 152 */     setProperty(37, text);
/* 153 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List setListSymbol(Image image) {
/* 164 */     setProperty(37, image);
/* 165 */     return this;
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
/*     */   public List setListSymbol(ListNumberingType listNumberingType) {
/* 177 */     if (listNumberingType == ListNumberingType.ZAPF_DINGBATS_1 || listNumberingType == ListNumberingType.ZAPF_DINGBATS_2 || listNumberingType == ListNumberingType.ZAPF_DINGBATS_3 || listNumberingType == ListNumberingType.ZAPF_DINGBATS_4)
/*     */     {
/* 179 */       setPostSymbolText(" ");
/*     */     }
/* 181 */     setProperty(37, listNumberingType);
/* 182 */     return this;
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
/*     */ 
/*     */   
/*     */   public List setListSymbolAlignment(ListSymbolAlignment alignment) {
/* 199 */     setProperty(38, alignment);
/* 200 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Float getSymbolIndent() {
/* 209 */     return (Float)getProperty(39);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List setSymbolIndent(float symbolIndent) {
/* 219 */     setProperty(39, Float.valueOf(symbolIndent));
/* 220 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getPostSymbolText() {
/* 229 */     return (String)getProperty(42);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setPostSymbolText(String postSymbolText) {
/* 238 */     setProperty(42, postSymbolText);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getPreSymbolText() {
/* 247 */     return (String)getProperty(41);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setPreSymbolText(String preSymbolText) {
/* 256 */     setProperty(41, preSymbolText);
/*     */   }
/*     */ 
/*     */   
/*     */   public AccessibilityProperties getAccessibilityProperties() {
/* 261 */     if (this.tagProperties == null) {
/* 262 */       this.tagProperties = new DefaultAccessibilityProperties("L");
/*     */     }
/* 264 */     return (AccessibilityProperties)this.tagProperties;
/*     */   }
/*     */ 
/*     */   
/*     */   protected IRenderer makeNewRenderer() {
/* 269 */     return (IRenderer)new ListRenderer(this);
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/layout/element/List.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */