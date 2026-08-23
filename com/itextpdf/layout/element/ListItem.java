/*     */ package com.itextpdf.layout.element;
/*     */ 
/*     */ import com.itextpdf.kernel.pdf.tagutils.AccessibilityProperties;
/*     */ import com.itextpdf.kernel.pdf.tagutils.DefaultAccessibilityProperties;
/*     */ import com.itextpdf.layout.property.ListNumberingType;
/*     */ import com.itextpdf.layout.property.ListSymbolPosition;
/*     */ import com.itextpdf.layout.renderer.IRenderer;
/*     */ import com.itextpdf.layout.renderer.ListItemRenderer;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ListItem
/*     */   extends Div
/*     */ {
/*     */   public ListItem() {}
/*     */   
/*     */   public ListItem(String text) {
/*  74 */     this();
/*  75 */     add((new Paragraph(text)).setMarginTop(0.0F).setMarginBottom(0.0F));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ListItem setListSymbolOrdinalValue(int ordinalValue) {
/*  85 */     setProperty(120, Integer.valueOf(ordinalValue));
/*  86 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ListItem(Image image) {
/*  95 */     this();
/*  96 */     add(image);
/*     */   }
/*     */ 
/*     */   
/*     */   public <T1> T1 getDefaultProperty(int property) {
/* 101 */     switch (property) {
/*     */       case 83:
/* 103 */         return (T1)ListSymbolPosition.DEFAULT;
/*     */     } 
/* 105 */     return (T1)super.getDefaultProperty(property);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ListItem setListSymbol(String symbol) {
/* 116 */     return setListSymbol(new Text(symbol));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ListItem setListSymbol(Text text) {
/* 126 */     setProperty(37, text);
/* 127 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ListItem setListSymbol(Image image) {
/* 137 */     setProperty(37, image);
/* 138 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ListItem setListSymbol(ListNumberingType listNumberingType) {
/* 149 */     if (listNumberingType == ListNumberingType.ZAPF_DINGBATS_1 || listNumberingType == ListNumberingType.ZAPF_DINGBATS_2 || listNumberingType == ListNumberingType.ZAPF_DINGBATS_3 || listNumberingType == ListNumberingType.ZAPF_DINGBATS_4)
/*     */     {
/* 151 */       setProperty(42, " ");
/*     */     }
/* 153 */     setProperty(37, listNumberingType);
/* 154 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public AccessibilityProperties getAccessibilityProperties() {
/* 159 */     if (this.tagProperties == null) {
/* 160 */       this.tagProperties = new DefaultAccessibilityProperties("LBody");
/*     */     }
/* 162 */     return (AccessibilityProperties)this.tagProperties;
/*     */   }
/*     */ 
/*     */   
/*     */   protected IRenderer makeNewRenderer() {
/* 167 */     return (IRenderer)new ListItemRenderer(this);
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/layout/element/ListItem.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */