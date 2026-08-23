/*     */ package com.itextpdf.layout;
/*     */ 
/*     */ import com.itextpdf.kernel.font.PdfFont;
/*     */ import com.itextpdf.kernel.font.PdfFontFactory;
/*     */ import com.itextpdf.kernel.pdf.PdfDocument;
/*     */ import com.itextpdf.layout.element.Div;
/*     */ import com.itextpdf.layout.element.IBlockElement;
/*     */ import com.itextpdf.layout.element.IElement;
/*     */ import com.itextpdf.layout.element.Image;
/*     */ import com.itextpdf.layout.element.Paragraph;
/*     */ import com.itextpdf.layout.font.FontProvider;
/*     */ import com.itextpdf.layout.property.FontKerning;
/*     */ import com.itextpdf.layout.property.HorizontalAlignment;
/*     */ import com.itextpdf.layout.property.TextAlignment;
/*     */ import com.itextpdf.layout.property.UnitValue;
/*     */ import com.itextpdf.layout.property.VerticalAlignment;
/*     */ import com.itextpdf.layout.renderer.IRenderer;
/*     */ import com.itextpdf.layout.renderer.RootRenderer;
/*     */ import com.itextpdf.layout.splitting.DefaultSplitCharacters;
/*     */ import com.itextpdf.layout.splitting.ISplitCharacters;
/*     */ import com.itextpdf.layout.tagging.LayoutTaggingHelper;
/*     */ import java.io.Closeable;
/*     */ import java.io.IOException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
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
/*     */ 
/*     */ public abstract class RootElement<T extends IPropertyContainer>
/*     */   extends ElementPropertyContainer<T>
/*     */   implements Closeable
/*     */ {
/*     */   protected boolean immediateFlush = true;
/*     */   protected PdfDocument pdfDocument;
/*  87 */   protected List<IElement> childElements = new ArrayList<>();
/*     */ 
/*     */   
/*     */   protected PdfFont defaultFont;
/*     */ 
/*     */   
/*     */   protected FontProvider defaultFontProvider;
/*     */ 
/*     */   
/*     */   protected ISplitCharacters defaultSplitCharacters;
/*     */ 
/*     */   
/*     */   protected RootRenderer rootRenderer;
/*     */   
/*     */   private LayoutTaggingHelper defaultLayoutTaggingHelper;
/*     */ 
/*     */   
/*     */   public T add(IBlockElement element) {
/* 105 */     this.childElements.add(element);
/* 106 */     createAndAddRendererSubTree((IElement)element);
/* 107 */     if (this.immediateFlush) {
/* 108 */       this.childElements.remove(this.childElements.size() - 1);
/*     */     }
/* 110 */     return (T)this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public T add(Image image) {
/* 121 */     this.childElements.add(image);
/* 122 */     createAndAddRendererSubTree((IElement)image);
/* 123 */     if (this.immediateFlush) {
/* 124 */       this.childElements.remove(this.childElements.size() - 1);
/*     */     }
/* 126 */     return (T)this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public FontProvider getFontProvider() {
/* 135 */     Object fontProvider = getProperty(91);
/* 136 */     if (fontProvider instanceof FontProvider) {
/* 137 */       return (FontProvider)fontProvider;
/*     */     }
/* 139 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setFontProvider(FontProvider fontProvider) {
/* 149 */     setProperty(91, fontProvider);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean hasProperty(int property) {
/* 154 */     return hasOwnProperty(property);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean hasOwnProperty(int property) {
/* 159 */     return this.properties.containsKey(Integer.valueOf(property));
/*     */   }
/*     */ 
/*     */   
/*     */   public <T1> T1 getProperty(int property) {
/* 164 */     return getOwnProperty(property);
/*     */   }
/*     */ 
/*     */   
/*     */   public <T1> T1 getOwnProperty(int property) {
/* 169 */     return (T1)this.properties.get(Integer.valueOf(property));
/*     */   }
/*     */ 
/*     */   
/*     */   public <T1> T1 getDefaultProperty(int property) {
/*     */     try {
/* 175 */       switch (property) {
/*     */         case 20:
/* 177 */           if (this.defaultFont == null) {
/* 178 */             this.defaultFont = PdfFontFactory.createFont();
/*     */           }
/* 180 */           return (T1)this.defaultFont;
/*     */         case 91:
/* 182 */           if (this.defaultFontProvider == null) {
/* 183 */             this.defaultFontProvider = new FontProvider();
/*     */           }
/* 185 */           return (T1)this.defaultFontProvider;
/*     */         case 62:
/* 187 */           if (this.defaultSplitCharacters == null) {
/* 188 */             this.defaultSplitCharacters = (ISplitCharacters)new DefaultSplitCharacters();
/*     */           }
/* 190 */           return (T1)this.defaultSplitCharacters;
/*     */         case 24:
/* 192 */           return (T1)UnitValue.createPointValue(12.0F);
/*     */         case 108:
/* 194 */           return (T1)initTaggingHelperIfNeeded();
/*     */         case 71:
/* 196 */           return (T1)Integer.valueOf(0);
/*     */         case 72:
/* 198 */           return (T1)Float.valueOf(0.0F);
/*     */         case 61:
/* 200 */           return (T1)Float.valueOf(0.75F);
/*     */       } 
/* 202 */       return null;
/*     */     }
/* 204 */     catch (IOException exc) {
/* 205 */       throw new RuntimeException(exc.toString(), exc);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void deleteOwnProperty(int property) {
/* 211 */     this.properties.remove(Integer.valueOf(property));
/*     */   }
/*     */ 
/*     */   
/*     */   public void setProperty(int property, Object value) {
/* 216 */     this.properties.put(Integer.valueOf(property), value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public RootRenderer getRenderer() {
/* 227 */     return ensureRootRendererNotNull();
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
/*     */   public T showTextAligned(String text, float x, float y, TextAlignment textAlign) {
/* 240 */     return showTextAligned(text, x, y, textAlign, 0.0F);
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
/*     */   public T showTextAligned(String text, float x, float y, TextAlignment textAlign, float angle) {
/* 254 */     return showTextAligned(text, x, y, textAlign, VerticalAlignment.BOTTOM, angle);
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
/*     */   public T showTextAligned(String text, float x, float y, TextAlignment textAlign, VerticalAlignment vertAlign, float angle) {
/* 269 */     Paragraph p = (Paragraph)(new Paragraph(text)).setMultipliedLeading(1.0F).setMargin(0.0F);
/* 270 */     return showTextAligned(p, x, y, this.pdfDocument.getNumberOfPages(), textAlign, vertAlign, angle);
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
/*     */   public T showTextAlignedKerned(String text, float x, float y, TextAlignment textAlign, VerticalAlignment vertAlign, float radAngle) {
/* 285 */     Paragraph p = (Paragraph)((Paragraph)(new Paragraph(text)).setMultipliedLeading(1.0F).setMargin(0.0F)).setFontKerning(FontKerning.YES);
/* 286 */     return showTextAligned(p, x, y, this.pdfDocument.getNumberOfPages(), textAlign, vertAlign, radAngle);
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
/*     */   public T showTextAligned(Paragraph p, float x, float y, TextAlignment textAlign) {
/* 300 */     return showTextAligned(p, x, y, this.pdfDocument.getNumberOfPages(), textAlign, VerticalAlignment.BOTTOM, 0.0F);
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
/*     */   public T showTextAligned(Paragraph p, float x, float y, TextAlignment textAlign, VerticalAlignment vertAlign) {
/* 315 */     return showTextAligned(p, x, y, this.pdfDocument.getNumberOfPages(), textAlign, vertAlign, 0.0F);
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
/*     */   public T showTextAligned(Paragraph p, float x, float y, int pageNumber, TextAlignment textAlign, VerticalAlignment vertAlign, float radAngle) {
/* 332 */     Div div = new Div();
/* 333 */     ((Div)div.setTextAlignment(textAlign)).setVerticalAlignment(vertAlign);
/* 334 */     if (radAngle != 0.0F) {
/* 335 */       div.setRotationAngle(radAngle);
/*     */     }
/* 337 */     div.setProperty(58, Float.valueOf(x));
/* 338 */     div.setProperty(59, Float.valueOf(y));
/*     */     
/* 340 */     float divSize = 5000.0F;
/* 341 */     float divX = x, divY = y;
/* 342 */     if (textAlign == TextAlignment.CENTER) {
/* 343 */       divX = x - divSize / 2.0F;
/* 344 */       p.setHorizontalAlignment(HorizontalAlignment.CENTER);
/* 345 */     } else if (textAlign == TextAlignment.RIGHT) {
/* 346 */       divX = x - divSize;
/* 347 */       p.setHorizontalAlignment(HorizontalAlignment.RIGHT);
/*     */     } 
/*     */     
/* 350 */     if (vertAlign == VerticalAlignment.MIDDLE) {
/* 351 */       divY = y - divSize / 2.0F;
/* 352 */     } else if (vertAlign == VerticalAlignment.TOP) {
/* 353 */       divY = y - divSize;
/*     */     } 
/*     */     
/* 356 */     if (pageNumber == 0)
/* 357 */       pageNumber = 1; 
/* 358 */     ((Div)div.setFixedPosition(pageNumber, divX, divY, divSize)).setMinHeight(divSize);
/* 359 */     if (p.getProperty(33) == null) {
/* 360 */       p.setMultipliedLeading(1.0F);
/*     */     }
/* 362 */     div.add((IBlockElement)p.setMargins(0.0F, 0.0F, 0.0F, 0.0F));
/* 363 */     div.getAccessibilityProperties().setRole("Artifact");
/* 364 */     add((IBlockElement)div);
/*     */     
/* 366 */     return (T)this;
/*     */   }
/*     */   
/*     */   protected abstract RootRenderer ensureRootRendererNotNull();
/*     */   
/*     */   protected void createAndAddRendererSubTree(IElement element) {
/* 372 */     IRenderer rendererSubTreeRoot = element.createRendererSubTree();
/* 373 */     LayoutTaggingHelper taggingHelper = initTaggingHelperIfNeeded();
/* 374 */     if (taggingHelper != null) {
/* 375 */       taggingHelper.addKidsHint(this.pdfDocument.getTagStructureContext().getAutoTaggingPointer(), Collections.singletonList(rendererSubTreeRoot));
/*     */     }
/* 377 */     ensureRootRendererNotNull().addChild(rendererSubTreeRoot);
/*     */   }
/*     */   
/*     */   private LayoutTaggingHelper initTaggingHelperIfNeeded() {
/* 381 */     return (this.defaultLayoutTaggingHelper == null && this.pdfDocument.isTagged()) ? (this.defaultLayoutTaggingHelper = new LayoutTaggingHelper(this.pdfDocument, this.immediateFlush)) : this.defaultLayoutTaggingHelper;
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/layout/RootElement.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */