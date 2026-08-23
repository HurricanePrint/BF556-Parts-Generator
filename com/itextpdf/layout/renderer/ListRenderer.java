/*     */ package com.itextpdf.layout.renderer;
/*     */ 
/*     */ import com.itextpdf.io.util.MessageFormatUtil;
/*     */ import com.itextpdf.io.util.TextUtil;
/*     */ import com.itextpdf.kernel.font.PdfFontFactory;
/*     */ import com.itextpdf.kernel.geom.Rectangle;
/*     */ import com.itextpdf.kernel.numbering.EnglishAlphabetNumbering;
/*     */ import com.itextpdf.kernel.numbering.GreekAlphabetNumbering;
/*     */ import com.itextpdf.kernel.numbering.RomanNumbering;
/*     */ import com.itextpdf.layout.element.IElement;
/*     */ import com.itextpdf.layout.element.Image;
/*     */ import com.itextpdf.layout.element.List;
/*     */ import com.itextpdf.layout.element.Text;
/*     */ import com.itextpdf.layout.layout.LayoutArea;
/*     */ import com.itextpdf.layout.layout.LayoutContext;
/*     */ import com.itextpdf.layout.layout.LayoutResult;
/*     */ import com.itextpdf.layout.minmaxwidth.MinMaxWidth;
/*     */ import com.itextpdf.layout.minmaxwidth.MinMaxWidthUtils;
/*     */ import com.itextpdf.layout.property.BaseDirection;
/*     */ import com.itextpdf.layout.property.IListSymbolFactory;
/*     */ import com.itextpdf.layout.property.ListNumberingType;
/*     */ import com.itextpdf.layout.property.ListSymbolPosition;
/*     */ import com.itextpdf.layout.property.UnitValue;
/*     */ import com.itextpdf.layout.tagging.LayoutTaggingHelper;
/*     */ import java.io.IOException;
/*     */ import java.util.ArrayList;
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
/*     */ 
/*     */ 
/*     */ public class ListRenderer
/*     */   extends BlockRenderer
/*     */ {
/*     */   public ListRenderer(List modelElement) {
/*  85 */     super((IElement)modelElement);
/*     */   }
/*     */ 
/*     */   
/*     */   public LayoutResult layout(LayoutContext layoutContext) {
/*  90 */     LayoutResult errorResult = initializeListSymbols(layoutContext);
/*  91 */     if (errorResult != null) {
/*  92 */       return errorResult;
/*     */     }
/*  94 */     LayoutResult result = super.layout(layoutContext);
/*     */     
/*  96 */     if (Boolean.TRUE.equals(getPropertyAsBoolean(26)) && null != result.getCauseOfNothing()) {
/*  97 */       if (1 == result.getStatus()) {
/*  98 */         result = correctListSplitting(this, (IRenderer)null, result.getCauseOfNothing(), result.getOccupiedArea());
/*  99 */       } else if (2 == result.getStatus()) {
/* 100 */         result = correctListSplitting(result.getSplitRenderer(), result.getOverflowRenderer(), result.getCauseOfNothing(), result.getOccupiedArea());
/*     */       } 
/*     */     }
/* 103 */     return result;
/*     */   }
/*     */ 
/*     */   
/*     */   public IRenderer getNextRenderer() {
/* 108 */     return new ListRenderer((List)this.modelElement);
/*     */   }
/*     */ 
/*     */   
/*     */   protected AbstractRenderer createSplitRenderer(int layoutResult) {
/* 113 */     AbstractRenderer splitRenderer = super.createSplitRenderer(layoutResult);
/* 114 */     splitRenderer.addAllProperties(getOwnProperties());
/* 115 */     splitRenderer.setProperty(40, Boolean.TRUE);
/* 116 */     return splitRenderer;
/*     */   }
/*     */ 
/*     */   
/*     */   protected AbstractRenderer createOverflowRenderer(int layoutResult) {
/* 121 */     AbstractRenderer overflowRenderer = super.createOverflowRenderer(layoutResult);
/* 122 */     overflowRenderer.addAllProperties(getOwnProperties());
/* 123 */     overflowRenderer.setProperty(40, Boolean.TRUE);
/* 124 */     return overflowRenderer;
/*     */   }
/*     */ 
/*     */   
/*     */   public MinMaxWidth getMinMaxWidth() {
/* 129 */     LayoutResult errorResult = initializeListSymbols(new LayoutContext(new LayoutArea(1, new Rectangle(MinMaxWidthUtils.getInfWidth(), 1000000.0F))));
/* 130 */     if (errorResult != null) {
/* 131 */       return MinMaxWidthUtils.countDefaultMinMaxWidth(this);
/*     */     }
/* 133 */     return super.getMinMaxWidth();
/*     */   }
/*     */   
/*     */   protected IRenderer makeListSymbolRenderer(int index, IRenderer renderer) {
/* 137 */     IRenderer symbolRenderer = createListSymbolRenderer(index, renderer);
/*     */     
/* 139 */     if (symbolRenderer != null) {
/* 140 */       symbolRenderer.setProperty(74, Boolean.valueOf(false));
/*     */     }
/* 142 */     return symbolRenderer;
/*     */   }
/*     */   
/*     */   static Object getListItemOrListProperty(IRenderer listItem, IRenderer list, int propertyId) {
/* 146 */     return listItem.hasProperty(propertyId) ? listItem.getProperty(propertyId) : list.getProperty(propertyId);
/*     */   }
/*     */   
/*     */   private IRenderer createListSymbolRenderer(int index, IRenderer renderer) {
/* 150 */     Object defaultListSymbol = getListItemOrListProperty(renderer, this, 37);
/* 151 */     if (defaultListSymbol instanceof Text)
/* 152 */       return surroundTextBullet(new TextRenderer((Text)defaultListSymbol)); 
/* 153 */     if (defaultListSymbol instanceof Image)
/* 154 */       return new ImageRenderer((Image)defaultListSymbol); 
/* 155 */     if (defaultListSymbol instanceof ListNumberingType) {
/* 156 */       String numberText; IRenderer textRenderer; ListNumberingType numberingType = (ListNumberingType)defaultListSymbol;
/*     */       
/* 158 */       switch (numberingType) {
/*     */         case DECIMAL:
/* 160 */           numberText = String.valueOf(index);
/*     */           break;
/*     */         case DECIMAL_LEADING_ZERO:
/* 163 */           numberText = ((index < 10) ? "0" : "") + String.valueOf(index);
/*     */           break;
/*     */         case ROMAN_LOWER:
/* 166 */           numberText = RomanNumbering.toRomanLowerCase(index);
/*     */           break;
/*     */         case ROMAN_UPPER:
/* 169 */           numberText = RomanNumbering.toRomanUpperCase(index);
/*     */           break;
/*     */         case ENGLISH_LOWER:
/* 172 */           numberText = EnglishAlphabetNumbering.toLatinAlphabetNumberLowerCase(index);
/*     */           break;
/*     */         case ENGLISH_UPPER:
/* 175 */           numberText = EnglishAlphabetNumbering.toLatinAlphabetNumberUpperCase(index);
/*     */           break;
/*     */         case GREEK_LOWER:
/* 178 */           numberText = GreekAlphabetNumbering.toGreekAlphabetNumber(index, false, true);
/*     */           break;
/*     */         case GREEK_UPPER:
/* 181 */           numberText = GreekAlphabetNumbering.toGreekAlphabetNumber(index, true, true);
/*     */           break;
/*     */         case ZAPF_DINGBATS_1:
/* 184 */           numberText = TextUtil.charToString((char)(index + 171));
/*     */           break;
/*     */         case ZAPF_DINGBATS_2:
/* 187 */           numberText = TextUtil.charToString((char)(index + 181));
/*     */           break;
/*     */         case ZAPF_DINGBATS_3:
/* 190 */           numberText = TextUtil.charToString((char)(index + 191));
/*     */           break;
/*     */         case ZAPF_DINGBATS_4:
/* 193 */           numberText = TextUtil.charToString((char)(index + 201));
/*     */           break;
/*     */         default:
/* 196 */           throw new IllegalStateException();
/*     */       } 
/* 198 */       Text textElement = new Text(getListItemOrListProperty(renderer, this, 41) + numberText + getListItemOrListProperty(renderer, this, 42));
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 203 */       if (numberingType == ListNumberingType.GREEK_LOWER || numberingType == ListNumberingType.GREEK_UPPER || numberingType == ListNumberingType.ZAPF_DINGBATS_1 || numberingType == ListNumberingType.ZAPF_DINGBATS_2 || numberingType == ListNumberingType.ZAPF_DINGBATS_3 || numberingType == ListNumberingType.ZAPF_DINGBATS_4) {
/*     */ 
/*     */ 
/*     */         
/* 207 */         final String constantFont = (numberingType == ListNumberingType.GREEK_LOWER || numberingType == ListNumberingType.GREEK_UPPER) ? "Symbol" : "ZapfDingbats";
/*     */ 
/*     */         
/* 210 */         textRenderer = new TextRenderer(textElement)
/*     */           {
/*     */             public void draw(DrawContext drawContext) {
/*     */               try {
/* 214 */                 setProperty(20, PdfFontFactory.createFont(constantFont));
/* 215 */               } catch (IOException iOException) {}
/*     */               
/* 217 */               super.draw(drawContext);
/*     */             }
/*     */           };
/*     */         try {
/* 221 */           textRenderer.setProperty(20, PdfFontFactory.createFont(constantFont));
/* 222 */         } catch (IOException iOException) {}
/*     */       } else {
/*     */         
/* 225 */         textRenderer = new TextRenderer(textElement);
/*     */       } 
/* 227 */       return surroundTextBullet(textRenderer);
/* 228 */     }  if (defaultListSymbol instanceof IListSymbolFactory)
/* 229 */       return surroundTextBullet(((IListSymbolFactory)defaultListSymbol).createSymbol(index, this, renderer).createRendererSubTree()); 
/* 230 */     if (defaultListSymbol == null) {
/* 231 */       return null;
/*     */     }
/* 233 */     throw new IllegalStateException();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private LineRenderer surroundTextBullet(IRenderer bulletRenderer) {
/* 240 */     LineRenderer lineRenderer = new LineRenderer();
/* 241 */     Text zeroWidthJoiner = new Text("‍");
/* 242 */     zeroWidthJoiner.getAccessibilityProperties().setRole("Artifact");
/* 243 */     TextRenderer zeroWidthJoinerRenderer = new TextRenderer(zeroWidthJoiner);
/* 244 */     lineRenderer.addChild(zeroWidthJoinerRenderer);
/* 245 */     lineRenderer.addChild(bulletRenderer);
/* 246 */     lineRenderer.addChild(zeroWidthJoinerRenderer);
/* 247 */     return lineRenderer;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private LayoutResult correctListSplitting(IRenderer splitRenderer, IRenderer overflowRenderer, IRenderer causeOfNothing, LayoutArea occupiedArea) {
/* 271 */     int firstNotRendered = ((IRenderer)splitRenderer.getChildRenderers().get(0)).getChildRenderers().indexOf(causeOfNothing);
/*     */     
/* 273 */     if (-1 == firstNotRendered) {
/* 274 */       return new LayoutResult((null == overflowRenderer) ? 1 : 2, occupiedArea, splitRenderer, overflowRenderer, this);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 279 */     IRenderer firstListItemRenderer = splitRenderer.getChildRenderers().get(0);
/*     */     
/* 281 */     ListRenderer newOverflowRenderer = (ListRenderer)createOverflowRenderer(2);
/* 282 */     newOverflowRenderer.deleteOwnProperty(26);
/*     */     
/* 284 */     newOverflowRenderer.childRenderers.add(((ListItemRenderer)firstListItemRenderer).createOverflowRenderer(2));
/* 285 */     newOverflowRenderer.childRenderers.addAll(splitRenderer.getChildRenderers().subList(1, splitRenderer.getChildRenderers().size()));
/*     */ 
/*     */     
/* 288 */     List<IRenderer> childrenStillRemainingToRender = new ArrayList<>(firstListItemRenderer.getChildRenderers().subList(firstNotRendered + 1, firstListItemRenderer.getChildRenderers().size()));
/*     */ 
/*     */     
/* 291 */     splitRenderer.getChildRenderers().removeAll(splitRenderer.getChildRenderers().subList(1, splitRenderer.getChildRenderers().size()));
/*     */     
/* 293 */     if (0 != childrenStillRemainingToRender.size()) {
/* 294 */       ((IRenderer)newOverflowRenderer.getChildRenderers().get(0)).getChildRenderers().addAll(childrenStillRemainingToRender);
/* 295 */       ((IRenderer)splitRenderer.getChildRenderers().get(0)).getChildRenderers().removeAll(childrenStillRemainingToRender);
/* 296 */       ((IRenderer)newOverflowRenderer.getChildRenderers().get(0)).setProperty(44, ((IRenderer)splitRenderer.getChildRenderers().get(0)).getProperty(44));
/*     */     } else {
/* 298 */       newOverflowRenderer.childRenderers.remove(0);
/*     */     } 
/*     */     
/* 301 */     if (null != overflowRenderer) {
/* 302 */       newOverflowRenderer.childRenderers.addAll(overflowRenderer.getChildRenderers());
/*     */     }
/*     */     
/* 305 */     if (0 != newOverflowRenderer.childRenderers.size()) {
/* 306 */       return new LayoutResult(2, occupiedArea, splitRenderer, newOverflowRenderer, this);
/*     */     }
/* 308 */     return new LayoutResult(1, occupiedArea, null, null, this);
/*     */   }
/*     */ 
/*     */   
/*     */   private LayoutResult initializeListSymbols(LayoutContext layoutContext) {
/* 313 */     if (!hasOwnProperty(40)) {
/* 314 */       List<IRenderer> symbolRenderers = new ArrayList<>();
/* 315 */       int listItemNum = ((Integer)getProperty(36, Integer.valueOf(1))).intValue();
/* 316 */       for (int i = 0; i < this.childRenderers.size(); i++) {
/* 317 */         ((IRenderer)this.childRenderers.get(i)).setParent(this);
/* 318 */         listItemNum = (((IRenderer)this.childRenderers.get(i)).getProperty(120) != null) ? ((Integer)((IRenderer)this.childRenderers.get(i)).getProperty(120)).intValue() : listItemNum;
/* 319 */         IRenderer currentSymbolRenderer = makeListSymbolRenderer(listItemNum, this.childRenderers.get(i));
/* 320 */         if (BaseDirection.RIGHT_TO_LEFT.equals(getProperty(7))) {
/* 321 */           currentSymbolRenderer.setProperty(7, BaseDirection.RIGHT_TO_LEFT);
/*     */         }
/* 323 */         LayoutResult listSymbolLayoutResult = null;
/* 324 */         if (currentSymbolRenderer != null) {
/* 325 */           listItemNum++;
/* 326 */           currentSymbolRenderer.setParent(this.childRenderers.get(i));
/* 327 */           listSymbolLayoutResult = currentSymbolRenderer.layout(layoutContext);
/* 328 */           currentSymbolRenderer.setParent((IRenderer)null);
/*     */         } 
/* 330 */         ((IRenderer)this.childRenderers.get(i)).setParent((IRenderer)null);
/* 331 */         boolean isForcedPlacement = Boolean.TRUE.equals(getPropertyAsBoolean(26));
/* 332 */         boolean listSymbolNotFit = (listSymbolLayoutResult != null && listSymbolLayoutResult.getStatus() != 1);
/*     */         
/* 334 */         if (listSymbolNotFit && isForcedPlacement) {
/* 335 */           currentSymbolRenderer = null;
/*     */         }
/* 337 */         symbolRenderers.add(currentSymbolRenderer);
/* 338 */         if (listSymbolNotFit && !isForcedPlacement) {
/* 339 */           return new LayoutResult(3, null, null, this, listSymbolLayoutResult.getCauseOfNothing());
/*     */         }
/*     */       } 
/*     */       
/* 343 */       float maxSymbolWidth = 0.0F;
/* 344 */       for (int j = 0; j < this.childRenderers.size(); j++) {
/* 345 */         IRenderer symbolRenderer = symbolRenderers.get(j);
/* 346 */         if (symbolRenderer != null) {
/* 347 */           IRenderer listItemRenderer = this.childRenderers.get(j);
/* 348 */           if ((ListSymbolPosition)getListItemOrListProperty(listItemRenderer, this, 83) != ListSymbolPosition.INSIDE) {
/* 349 */             maxSymbolWidth = Math.max(maxSymbolWidth, symbolRenderer.getOccupiedArea().getBBox().getWidth());
/*     */           }
/*     */         } 
/*     */       } 
/*     */       
/* 354 */       Float symbolIndent = getPropertyAsFloat(39);
/* 355 */       listItemNum = 0;
/* 356 */       for (IRenderer childRenderer : this.childRenderers) {
/* 357 */         childRenderer.setParent(this);
/* 358 */         childRenderer.deleteOwnProperty(44);
/* 359 */         UnitValue marginLeftUV = childRenderer.<UnitValue>getProperty(44, UnitValue.createPointValue(0.0F));
/* 360 */         if (!marginLeftUV.isPointValue()) {
/* 361 */           Logger logger = LoggerFactory.getLogger(ListRenderer.class);
/* 362 */           logger.error(MessageFormatUtil.format("Property {0} in percents is not supported", new Object[] { Integer.valueOf(44) }));
/*     */         } 
/* 364 */         float calculatedMargin = marginLeftUV.getValue();
/* 365 */         if ((ListSymbolPosition)getListItemOrListProperty(childRenderer, this, 83) == ListSymbolPosition.DEFAULT) {
/* 366 */           calculatedMargin += maxSymbolWidth + ((symbolIndent != null) ? symbolIndent.floatValue() : 0.0F);
/*     */         }
/* 368 */         childRenderer.setProperty(44, UnitValue.createPointValue(calculatedMargin));
/* 369 */         IRenderer symbolRenderer = symbolRenderers.get(listItemNum++);
/* 370 */         ((ListItemRenderer)childRenderer).addSymbolRenderer(symbolRenderer, maxSymbolWidth);
/* 371 */         if (symbolRenderer != null) {
/* 372 */           LayoutTaggingHelper taggingHelper = getProperty(108);
/* 373 */           if (taggingHelper != null) {
/* 374 */             if (symbolRenderer instanceof LineRenderer) {
/* 375 */               taggingHelper.setRoleHint(symbolRenderer.getChildRenderers().get(1), "Lbl"); continue;
/*     */             } 
/* 377 */             taggingHelper.setRoleHint(symbolRenderer, "Lbl");
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 383 */     return null;
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/layout/renderer/ListRenderer.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */