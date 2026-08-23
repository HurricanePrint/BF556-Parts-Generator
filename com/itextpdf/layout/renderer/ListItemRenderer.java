/*     */ package com.itextpdf.layout.renderer;
/*     */ 
/*     */ import com.itextpdf.io.util.MessageFormatUtil;
/*     */ import com.itextpdf.kernel.font.PdfFont;
/*     */ import com.itextpdf.layout.IPropertyContainer;
/*     */ import com.itextpdf.layout.element.Div;
/*     */ import com.itextpdf.layout.element.ListItem;
/*     */ import com.itextpdf.layout.element.Paragraph;
/*     */ import com.itextpdf.layout.layout.LayoutContext;
/*     */ import com.itextpdf.layout.layout.LayoutResult;
/*     */ import com.itextpdf.layout.property.BaseDirection;
/*     */ import com.itextpdf.layout.property.ListSymbolAlignment;
/*     */ import com.itextpdf.layout.property.ListSymbolPosition;
/*     */ import com.itextpdf.layout.property.UnitValue;
/*     */ import com.itextpdf.layout.tagging.LayoutTaggingHelper;
/*     */ import com.itextpdf.layout.tagging.TaggingDummyElement;
/*     */ import com.itextpdf.layout.tagging.TaggingHintKey;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ import org.slf4j.Logger;
/*     */ import org.slf4j.LoggerFactory;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ListItemRenderer
/*     */   extends DivRenderer
/*     */ {
/*     */   protected IRenderer symbolRenderer;
/*     */   protected float symbolAreaWidth;
/*     */   private boolean symbolAddedInside;
/*     */   
/*     */   public ListItemRenderer(ListItem modelElement) {
/*  79 */     super((Div)modelElement);
/*     */   }
/*     */   
/*     */   public void addSymbolRenderer(IRenderer symbolRenderer, float symbolAreaWidth) {
/*  83 */     this.symbolRenderer = symbolRenderer;
/*  84 */     this.symbolAreaWidth = symbolAreaWidth;
/*     */   }
/*     */ 
/*     */   
/*     */   public LayoutResult layout(LayoutContext layoutContext) {
/*  89 */     if (this.symbolRenderer != null && getProperty(27) == null && !isListSymbolEmpty(this.symbolRenderer)) {
/*  90 */       float[] ascenderDescender = calculateAscenderDescender();
/*  91 */       float minHeight = Math.max(this.symbolRenderer.getOccupiedArea().getBBox().getHeight(), ascenderDescender[0] - ascenderDescender[1]);
/*  92 */       updateMinHeight(UnitValue.createPointValue(minHeight));
/*     */     } 
/*  94 */     applyListSymbolPosition();
/*  95 */     LayoutResult result = super.layout(layoutContext);
/*  96 */     if (2 == result.getStatus()) {
/*  97 */       result.getOverflowRenderer().deleteOwnProperty(85);
/*     */     }
/*  99 */     return result;
/*     */   }
/*     */ 
/*     */   
/*     */   public void draw(DrawContext drawContext) {
/* 104 */     if (this.occupiedArea == null) {
/* 105 */       Logger logger = LoggerFactory.getLogger(ListItemRenderer.class);
/* 106 */       logger.error(MessageFormatUtil.format("Occupied area has not been initialized. {0}", new Object[] { "Drawing won't be performed." }));
/*     */       return;
/*     */     } 
/* 109 */     if (drawContext.isTaggingEnabled()) {
/* 110 */       LayoutTaggingHelper taggingHelper = getProperty(108);
/* 111 */       if (taggingHelper != null) {
/* 112 */         if (this.symbolRenderer != null) {
/* 113 */           LayoutTaggingHelper.addTreeHints(taggingHelper, this.symbolRenderer);
/*     */         }
/* 115 */         if (taggingHelper.isArtifact(this)) {
/* 116 */           taggingHelper.markArtifactHint(this.symbolRenderer);
/*     */         } else {
/* 118 */           TaggingHintKey hintKey = LayoutTaggingHelper.getHintKey(this);
/* 119 */           TaggingHintKey parentHint = taggingHelper.getAccessibleParentHint(hintKey);
/* 120 */           if (parentHint != null && !"LI".equals(parentHint.getAccessibleElement().getAccessibilityProperties().getRole())) {
/* 121 */             TaggingDummyElement listItemIntermediate = new TaggingDummyElement("LI");
/* 122 */             List<TaggingHintKey> intermediateKid = Collections.singletonList(LayoutTaggingHelper.getOrCreateHintKey((IPropertyContainer)listItemIntermediate));
/* 123 */             taggingHelper.replaceKidHint(hintKey, intermediateKid);
/* 124 */             if (this.symbolRenderer != null) {
/* 125 */               taggingHelper.addKidsHint((IPropertyContainer)listItemIntermediate, Collections.singletonList(this.symbolRenderer));
/*     */             }
/* 127 */             taggingHelper.addKidsHint((IPropertyContainer)listItemIntermediate, Collections.singletonList(this));
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 133 */     super.draw(drawContext);
/*     */ 
/*     */     
/* 136 */     if (this.symbolRenderer != null && !this.symbolAddedInside) {
/* 137 */       boolean isRtl = BaseDirection.RIGHT_TO_LEFT.equals(getProperty(7));
/* 138 */       this.symbolRenderer.setParent(this);
/* 139 */       float x = isRtl ? this.occupiedArea.getBBox().getRight() : this.occupiedArea.getBBox().getLeft();
/* 140 */       ListSymbolPosition symbolPosition = (ListSymbolPosition)ListRenderer.getListItemOrListProperty(this, this.parent, 83);
/* 141 */       if (symbolPosition != ListSymbolPosition.DEFAULT) {
/* 142 */         Float symbolIndent = getPropertyAsFloat(39);
/* 143 */         if (isRtl) {
/* 144 */           x += this.symbolAreaWidth + ((symbolIndent == null) ? 0.0F : symbolIndent.floatValue());
/*     */         } else {
/* 146 */           x -= this.symbolAreaWidth + ((symbolIndent == null) ? 0.0F : symbolIndent.floatValue());
/*     */         } 
/* 148 */         if (symbolPosition == ListSymbolPosition.OUTSIDE) {
/* 149 */           if (isRtl) {
/* 150 */             UnitValue marginRightUV = getPropertyAsUnitValue(45);
/* 151 */             if (!marginRightUV.isPointValue()) {
/* 152 */               Logger logger = LoggerFactory.getLogger(ListItemRenderer.class);
/* 153 */               logger.error(MessageFormatUtil.format("Property {0} in percents is not supported", new Object[] { Integer.valueOf(45) }));
/*     */             } 
/* 155 */             x -= marginRightUV.getValue();
/*     */           } else {
/* 157 */             UnitValue marginLeftUV = getPropertyAsUnitValue(44);
/* 158 */             if (!marginLeftUV.isPointValue()) {
/* 159 */               Logger logger = LoggerFactory.getLogger(ListItemRenderer.class);
/* 160 */               logger.error(MessageFormatUtil.format("Property {0} in percents is not supported", new Object[] { Integer.valueOf(44) }));
/*     */             } 
/* 162 */             x += marginLeftUV.getValue();
/*     */           } 
/*     */         }
/*     */       } 
/* 166 */       applyMargins(this.occupiedArea.getBBox(), false);
/* 167 */       applyBorderBox(this.occupiedArea.getBBox(), false);
/* 168 */       if (this.childRenderers.size() > 0) {
/* 169 */         Float yLine = null;
/* 170 */         for (int i = 0; i < this.childRenderers.size(); i++) {
/* 171 */           if (((IRenderer)this.childRenderers.get(i)).getOccupiedArea().getBBox().getHeight() > 0.0F) {
/* 172 */             yLine = ((AbstractRenderer)this.childRenderers.get(i)).getFirstYLineRecursively();
/* 173 */             if (yLine != null) {
/*     */               break;
/*     */             }
/*     */           } 
/*     */         } 
/* 178 */         if (yLine != null) {
/* 179 */           if (this.symbolRenderer instanceof LineRenderer) {
/* 180 */             this.symbolRenderer.move(0.0F, yLine.floatValue() - ((LineRenderer)this.symbolRenderer).getYLine());
/*     */           } else {
/* 182 */             this.symbolRenderer.move(0.0F, yLine.floatValue() - this.symbolRenderer.getOccupiedArea().getBBox().getY());
/*     */           } 
/*     */         } else {
/* 185 */           this.symbolRenderer.move(0.0F, this.occupiedArea.getBBox().getY() + this.occupiedArea.getBBox().getHeight() - this.symbolRenderer
/* 186 */               .getOccupiedArea().getBBox().getY() + this.symbolRenderer.getOccupiedArea().getBBox().getHeight());
/*     */         }
/*     */       
/* 189 */       } else if (this.symbolRenderer instanceof TextRenderer) {
/* 190 */         ((TextRenderer)this.symbolRenderer).moveYLineTo(this.occupiedArea.getBBox().getY() + this.occupiedArea.getBBox().getHeight() - calculateAscenderDescender()[0]);
/*     */       } else {
/* 192 */         this.symbolRenderer.move(0.0F, this.occupiedArea.getBBox().getY() + this.occupiedArea.getBBox().getHeight() - this.symbolRenderer
/* 193 */             .getOccupiedArea().getBBox().getHeight() - this.symbolRenderer.getOccupiedArea().getBBox().getY());
/*     */       } 
/*     */       
/* 196 */       applyBorderBox(this.occupiedArea.getBBox(), true);
/* 197 */       applyMargins(this.occupiedArea.getBBox(), true);
/*     */       
/* 199 */       ListSymbolAlignment listSymbolAlignment = this.parent.<ListSymbolAlignment>getProperty(38, isRtl ? ListSymbolAlignment.LEFT : ListSymbolAlignment.RIGHT);
/*     */       
/* 201 */       float dxPosition = x - this.symbolRenderer.getOccupiedArea().getBBox().getX();
/* 202 */       if (listSymbolAlignment == ListSymbolAlignment.RIGHT) {
/* 203 */         if (!isRtl) {
/* 204 */           dxPosition += this.symbolAreaWidth - this.symbolRenderer.getOccupiedArea().getBBox().getWidth();
/*     */         }
/* 206 */       } else if (listSymbolAlignment == ListSymbolAlignment.LEFT && 
/* 207 */         isRtl) {
/* 208 */         dxPosition -= this.symbolAreaWidth - this.symbolRenderer.getOccupiedArea().getBBox().getWidth();
/*     */       } 
/*     */       
/* 211 */       if (this.symbolRenderer instanceof LineRenderer) {
/* 212 */         if (isRtl) {
/* 213 */           this.symbolRenderer.move(dxPosition - this.symbolRenderer.getOccupiedArea().getBBox().getWidth(), 0.0F);
/*     */         } else {
/* 215 */           this.symbolRenderer.move(dxPosition, 0.0F);
/*     */         } 
/*     */       } else {
/* 218 */         this.symbolRenderer.move(dxPosition, 0.0F);
/*     */       } 
/*     */       
/* 221 */       if (this.symbolRenderer.getOccupiedArea().getBBox().getRight() > this.parent.getOccupiedArea().getBBox().getLeft()) {
/* 222 */         beginElementOpacityApplying(drawContext);
/* 223 */         this.symbolRenderer.draw(drawContext);
/* 224 */         endElementOpacityApplying(drawContext);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public IRenderer getNextRenderer() {
/* 231 */     return new ListItemRenderer((ListItem)this.modelElement);
/*     */   }
/*     */ 
/*     */   
/*     */   protected AbstractRenderer createSplitRenderer(int layoutResult) {
/* 236 */     ListItemRenderer splitRenderer = (ListItemRenderer)getNextRenderer();
/* 237 */     splitRenderer.parent = this.parent;
/* 238 */     splitRenderer.modelElement = this.modelElement;
/* 239 */     splitRenderer.occupiedArea = this.occupiedArea;
/* 240 */     splitRenderer.isLastRendererForModelElement = false;
/* 241 */     if (layoutResult == 2) {
/* 242 */       splitRenderer.symbolRenderer = this.symbolRenderer;
/* 243 */       splitRenderer.symbolAreaWidth = this.symbolAreaWidth;
/*     */     } 
/* 245 */     splitRenderer.addAllProperties(getOwnProperties());
/* 246 */     return splitRenderer;
/*     */   }
/*     */ 
/*     */   
/*     */   protected AbstractRenderer createOverflowRenderer(int layoutResult) {
/* 251 */     ListItemRenderer overflowRenderer = (ListItemRenderer)getNextRenderer();
/* 252 */     overflowRenderer.parent = this.parent;
/* 253 */     overflowRenderer.modelElement = this.modelElement;
/* 254 */     if (layoutResult == 3) {
/* 255 */       overflowRenderer.symbolRenderer = this.symbolRenderer;
/* 256 */       overflowRenderer.symbolAreaWidth = this.symbolAreaWidth;
/*     */     } 
/* 258 */     overflowRenderer.addAllProperties(getOwnProperties());
/* 259 */     return overflowRenderer;
/*     */   }
/*     */   
/*     */   private void applyListSymbolPosition() {
/* 263 */     if (this.symbolRenderer != null) {
/* 264 */       ListSymbolPosition symbolPosition = (ListSymbolPosition)ListRenderer.getListItemOrListProperty(this, this.parent, 83);
/* 265 */       if (symbolPosition == ListSymbolPosition.INSIDE) {
/* 266 */         boolean isRtl = BaseDirection.RIGHT_TO_LEFT.equals(getProperty(7));
/* 267 */         if (this.childRenderers.size() > 0 && this.childRenderers.get(0) instanceof ParagraphRenderer) {
/* 268 */           ParagraphRenderer paragraphRenderer = (ParagraphRenderer)this.childRenderers.get(0);
/* 269 */           Float symbolIndent = getPropertyAsFloat(39);
/*     */           
/* 271 */           if (this.symbolRenderer instanceof LineRenderer) {
/* 272 */             if (symbolIndent != null) {
/* 273 */               ((IRenderer)this.symbolRenderer.getChildRenderers().get(1)).setProperty(isRtl ? 44 : 45, UnitValue.createPointValue(symbolIndent.floatValue()));
/*     */             }
/* 275 */             for (IRenderer childRenderer : this.symbolRenderer.getChildRenderers()) {
/* 276 */               paragraphRenderer.childRenderers.add(0, childRenderer);
/*     */             }
/*     */           } else {
/* 279 */             if (symbolIndent != null) {
/* 280 */               this.symbolRenderer.setProperty(isRtl ? 44 : 45, UnitValue.createPointValue(symbolIndent.floatValue()));
/*     */             }
/* 282 */             paragraphRenderer.childRenderers.add(0, this.symbolRenderer);
/*     */           } 
/* 284 */           this.symbolAddedInside = true;
/* 285 */         } else if (this.childRenderers.size() > 0 && this.childRenderers.get(0) instanceof ImageRenderer) {
/* 286 */           Paragraph p = new Paragraph();
/* 287 */           p.getAccessibilityProperties().setRole(null);
/* 288 */           IRenderer paragraphRenderer = ((Paragraph)p.setMargin(0.0F)).createRendererSubTree();
/* 289 */           Float symbolIndent = getPropertyAsFloat(39);
/* 290 */           if (symbolIndent != null) {
/* 291 */             this.symbolRenderer.setProperty(45, UnitValue.createPointValue(symbolIndent.floatValue()));
/*     */           }
/* 293 */           paragraphRenderer.addChild(this.symbolRenderer);
/* 294 */           paragraphRenderer.addChild(this.childRenderers.get(0));
/* 295 */           this.childRenderers.set(0, paragraphRenderer);
/* 296 */           this.symbolAddedInside = true;
/*     */         } 
/* 298 */         if (!this.symbolAddedInside) {
/* 299 */           Paragraph p = new Paragraph();
/* 300 */           p.getAccessibilityProperties().setRole(null);
/* 301 */           IRenderer paragraphRenderer = ((Paragraph)p.setMargin(0.0F)).createRendererSubTree();
/* 302 */           Float symbolIndent = getPropertyAsFloat(39);
/* 303 */           if (symbolIndent != null) {
/* 304 */             this.symbolRenderer.setProperty(45, UnitValue.createPointValue(symbolIndent.floatValue()));
/*     */           }
/* 306 */           paragraphRenderer.addChild(this.symbolRenderer);
/* 307 */           this.childRenderers.add(0, paragraphRenderer);
/* 308 */           this.symbolAddedInside = true;
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private boolean isListSymbolEmpty(IRenderer listSymbolRenderer) {
/* 315 */     if (listSymbolRenderer instanceof TextRenderer)
/* 316 */       return (((TextRenderer)listSymbolRenderer).getText().toString().length() == 0); 
/* 317 */     if (listSymbolRenderer instanceof LineRenderer) {
/* 318 */       return (((TextRenderer)listSymbolRenderer.getChildRenderers().get(1)).getText().toString().length() == 0);
/*     */     }
/* 320 */     return false;
/*     */   }
/*     */   
/*     */   private float[] calculateAscenderDescender() {
/* 324 */     PdfFont listItemFont = resolveFirstPdfFont();
/* 325 */     UnitValue fontSize = getPropertyAsUnitValue(24);
/* 326 */     if (listItemFont != null && fontSize != null) {
/* 327 */       if (!fontSize.isPointValue()) {
/* 328 */         Logger logger = LoggerFactory.getLogger(ListItemRenderer.class);
/* 329 */         logger.error(MessageFormatUtil.format("Property {0} in percents is not supported", new Object[] { Integer.valueOf(24) }));
/*     */       } 
/* 331 */       float[] ascenderDescender = TextRenderer.calculateAscenderDescender(listItemFont);
/* 332 */       return new float[] { fontSize.getValue() * ascenderDescender[0] / 1000.0F, fontSize.getValue() * ascenderDescender[1] / 1000.0F };
/*     */     } 
/* 334 */     return new float[] { 0.0F, 0.0F };
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/layout/renderer/ListItemRenderer.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */