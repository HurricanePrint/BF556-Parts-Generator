/*     */ package com.itextpdf.layout.element;
/*     */ 
/*     */ import com.itextpdf.kernel.pdf.tagutils.AccessibilityProperties;
/*     */ import com.itextpdf.kernel.pdf.tagutils.DefaultAccessibilityProperties;
/*     */ import com.itextpdf.layout.property.Leading;
/*     */ import com.itextpdf.layout.property.ParagraphOrphansControl;
/*     */ import com.itextpdf.layout.property.ParagraphWidowsControl;
/*     */ import com.itextpdf.layout.property.UnitValue;
/*     */ import com.itextpdf.layout.renderer.IRenderer;
/*     */ import com.itextpdf.layout.renderer.ParagraphRenderer;
/*     */ import java.util.Arrays;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.TreeMap;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Paragraph
/*     */   extends BlockElement<Paragraph>
/*     */ {
/*     */   protected DefaultAccessibilityProperties tagProperties;
/*     */   
/*     */   public Paragraph() {}
/*     */   
/*     */   public Paragraph(String text) {
/*  82 */     this(new Text(text));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Paragraph(Text text) {
/*  91 */     add(text);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Paragraph add(String text) {
/* 101 */     return add(new Text(text));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Paragraph add(ILeafElement element) {
/* 111 */     this.childElements.add(element);
/* 112 */     return this;
/*     */   }
/*     */   
/*     */   public Paragraph add(IBlockElement element) {
/* 116 */     this.childElements.add(element);
/* 117 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public <T2 extends ILeafElement> Paragraph addAll(List<T2> elements) {
/* 128 */     for (ILeafElement element : elements) {
/* 129 */       add(element);
/*     */     }
/* 131 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Paragraph addTabStops(TabStop... tabStops) {
/* 142 */     addTabStopsAsProperty(Arrays.asList(tabStops));
/* 143 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Paragraph addTabStops(List<TabStop> tabStops) {
/* 154 */     addTabStopsAsProperty(tabStops);
/* 155 */     return this;
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
/*     */   public Paragraph removeTabStop(float tabStopPosition) {
/* 167 */     Map<Float, TabStop> tabStops = (Map<Float, TabStop>)getProperty(69);
/* 168 */     if (tabStops != null) {
/* 169 */       tabStops.remove(Float.valueOf(tabStopPosition));
/*     */     }
/* 171 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public <T1> T1 getDefaultProperty(int property) {
/* 176 */     switch (property) {
/*     */       case 33:
/* 178 */         return (T1)new Leading(2, (this.childElements.size() == 1 && this.childElements.get(0) instanceof Image) ? 1.0F : 1.35F);
/*     */       case 18:
/* 180 */         return (T1)Float.valueOf(0.0F);
/*     */       case 43:
/*     */       case 46:
/* 183 */         return (T1)UnitValue.createPointValue(4.0F);
/*     */       case 67:
/* 185 */         return (T1)Float.valueOf(50.0F);
/*     */     } 
/* 187 */     return super.getDefaultProperty(property);
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
/*     */   public Paragraph setFirstLineIndent(float indent) {
/* 199 */     setProperty(18, Float.valueOf(indent));
/* 200 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Paragraph setOrphansControl(ParagraphOrphansControl orphansControl) {
/* 210 */     setProperty(121, orphansControl);
/* 211 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Paragraph setWidowsControl(ParagraphWidowsControl widowsControl) {
/* 221 */     setProperty(122, widowsControl);
/* 222 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Paragraph setFixedLeading(float leading) {
/* 233 */     setProperty(33, new Leading(1, leading));
/* 234 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Paragraph setMultipliedLeading(float leading) {
/* 245 */     setProperty(33, new Leading(2, leading));
/* 246 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public AccessibilityProperties getAccessibilityProperties() {
/* 251 */     if (this.tagProperties == null) {
/* 252 */       this.tagProperties = new DefaultAccessibilityProperties("P");
/*     */     }
/* 254 */     return (AccessibilityProperties)this.tagProperties;
/*     */   }
/*     */ 
/*     */   
/*     */   protected IRenderer makeNewRenderer() {
/* 259 */     return (IRenderer)new ParagraphRenderer(this);
/*     */   }
/*     */   
/*     */   private void addTabStopsAsProperty(List<TabStop> newTabStops) {
/* 263 */     Map<Float, TabStop> tabStops = (Map<Float, TabStop>)getProperty(69);
/* 264 */     if (tabStops == null) {
/* 265 */       tabStops = new TreeMap<>();
/* 266 */       setProperty(69, tabStops);
/*     */     } 
/* 268 */     for (TabStop tabStop : newTabStops)
/* 269 */       tabStops.put(Float.valueOf(tabStop.getTabPosition()), tabStop); 
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/layout/element/Paragraph.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */