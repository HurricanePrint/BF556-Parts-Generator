/*      */ package com.itextpdf.layout.renderer;
/*      */ 
/*      */ import com.itextpdf.io.font.FontMetrics;
/*      */ import com.itextpdf.io.font.otf.Glyph;
/*      */ import com.itextpdf.io.font.otf.GlyphLine;
/*      */ import com.itextpdf.io.util.EnumUtil;
/*      */ import com.itextpdf.io.util.MessageFormatUtil;
/*      */ import com.itextpdf.io.util.TextUtil;
/*      */ import com.itextpdf.kernel.colors.Color;
/*      */ import com.itextpdf.kernel.font.PdfFont;
/*      */ import com.itextpdf.kernel.geom.Rectangle;
/*      */ import com.itextpdf.kernel.pdf.canvas.CanvasArtifact;
/*      */ import com.itextpdf.kernel.pdf.canvas.CanvasTag;
/*      */ import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
/*      */ import com.itextpdf.kernel.pdf.tagutils.TagTreePointer;
/*      */ import com.itextpdf.layout.borders.Border;
/*      */ import com.itextpdf.layout.element.IElement;
/*      */ import com.itextpdf.layout.element.Text;
/*      */ import com.itextpdf.layout.font.FontCharacteristics;
/*      */ import com.itextpdf.layout.font.FontFamilySplitter;
/*      */ import com.itextpdf.layout.font.FontProvider;
/*      */ import com.itextpdf.layout.font.FontSelectorStrategy;
/*      */ import com.itextpdf.layout.font.FontSet;
/*      */ import com.itextpdf.layout.hyphenation.Hyphenation;
/*      */ import com.itextpdf.layout.hyphenation.HyphenationConfig;
/*      */ import com.itextpdf.layout.layout.LayoutArea;
/*      */ import com.itextpdf.layout.layout.LayoutContext;
/*      */ import com.itextpdf.layout.layout.LayoutResult;
/*      */ import com.itextpdf.layout.layout.TextLayoutResult;
/*      */ import com.itextpdf.layout.minmaxwidth.MinMaxWidth;
/*      */ import com.itextpdf.layout.minmaxwidth.MinMaxWidthUtils;
/*      */ import com.itextpdf.layout.property.BaseDirection;
/*      */ import com.itextpdf.layout.property.FloatPropertyValue;
/*      */ import com.itextpdf.layout.property.FontKerning;
/*      */ import com.itextpdf.layout.property.OverflowPropertyValue;
/*      */ import com.itextpdf.layout.property.RenderingMode;
/*      */ import com.itextpdf.layout.property.TransparentColor;
/*      */ import com.itextpdf.layout.property.Underline;
/*      */ import com.itextpdf.layout.property.UnitValue;
/*      */ import com.itextpdf.layout.splitting.ISplitCharacters;
/*      */ import com.itextpdf.layout.tagging.LayoutTaggingHelper;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Arrays;
/*      */ import java.util.Collection;
/*      */ import java.util.Collections;
/*      */ import java.util.Iterator;
/*      */ import java.util.List;
/*      */ import org.slf4j.Logger;
/*      */ import org.slf4j.LoggerFactory;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class TextRenderer
/*      */   extends AbstractRenderer
/*      */   implements ILeafElementRenderer
/*      */ {
/*      */   protected static final float TEXT_SPACE_COEFF = 1000.0F;
/*      */   static final float TYPO_ASCENDER_SCALE_COEFF = 1.2F;
/*      */   private static final float ITALIC_ANGLE = 0.21256F;
/*      */   private static final float BOLD_SIMULATION_STROKE_COEFF = 0.033333335F;
/*      */   protected float yLineOffset;
/*      */   private PdfFont font;
/*      */   protected GlyphLine text;
/*      */   protected GlyphLine line;
/*      */   protected String strToBeConverted;
/*      */   protected boolean otfFeaturesApplied = false;
/*  122 */   protected float tabAnchorCharacterPosition = -1.0F;
/*      */ 
/*      */ 
/*      */   
/*      */   protected List<int[]> reversedRanges;
/*      */ 
/*      */   
/*      */   protected GlyphLine savedWordBreakAtLineEnding;
/*      */ 
/*      */   
/*      */   private List<Integer> specialScriptsWordBreakPoints;
/*      */ 
/*      */   
/*  135 */   private int specialScriptFirstNotFittingIndex = -1;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public TextRenderer(Text textElement) {
/*  143 */     this(textElement, textElement.getText());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public TextRenderer(Text textElement, String text) {
/*  154 */     super((IElement)textElement);
/*  155 */     this.strToBeConverted = text;
/*      */   }
/*      */   
/*      */   protected TextRenderer(TextRenderer other) {
/*  159 */     super(other);
/*  160 */     this.text = other.text;
/*  161 */     this.line = other.line;
/*  162 */     this.font = other.font;
/*  163 */     this.yLineOffset = other.yLineOffset;
/*  164 */     this.strToBeConverted = other.strToBeConverted;
/*  165 */     this.otfFeaturesApplied = other.otfFeaturesApplied;
/*  166 */     this.tabAnchorCharacterPosition = other.tabAnchorCharacterPosition;
/*  167 */     this.reversedRanges = other.reversedRanges;
/*  168 */     this.specialScriptsWordBreakPoints = other.specialScriptsWordBreakPoints;
/*      */   }
/*      */   
/*      */   public LayoutResult layout(LayoutContext layoutContext) {
/*      */     AbstractWidthHandler widthHandler;
/*  173 */     updateFontAndText();
/*      */     
/*  175 */     LayoutArea area = layoutContext.getArea();
/*  176 */     Rectangle layoutBox = area.getBBox().clone();
/*      */     
/*  178 */     boolean noSoftWrap = Boolean.TRUE.equals(this.parent.getOwnProperty(118));
/*      */     
/*  180 */     OverflowPropertyValue overflowX = (OverflowPropertyValue)this.parent.getProperty(103);
/*      */     
/*  182 */     List<Rectangle> floatRendererAreas = layoutContext.getFloatRendererAreas();
/*  183 */     FloatPropertyValue floatPropertyValue = getProperty(99);
/*      */     
/*  185 */     if (FloatingHelper.isRendererFloating(this, floatPropertyValue)) {
/*  186 */       FloatingHelper.adjustFloatedBlockLayoutBox(this, layoutBox, null, floatRendererAreas, floatPropertyValue, overflowX);
/*      */     }
/*      */     
/*  189 */     UnitValue[] margins = getMargins();
/*  190 */     applyMargins(layoutBox, margins, false);
/*  191 */     Border[] borders = getBorders();
/*  192 */     applyBorderBox(layoutBox, borders, false);
/*      */     
/*  194 */     UnitValue[] paddings = getPaddings();
/*  195 */     applyPaddings(layoutBox, paddings, false);
/*      */     
/*  197 */     MinMaxWidth countedMinMaxWidth = new MinMaxWidth(area.getBBox().getWidth() - layoutBox.getWidth());
/*      */     
/*  199 */     if (noSoftWrap) {
/*  200 */       widthHandler = new SumSumWidthHandler(countedMinMaxWidth);
/*      */     } else {
/*  202 */       widthHandler = new MaxSumWidthHandler(countedMinMaxWidth);
/*      */     } 
/*      */     
/*  205 */     this.occupiedArea = new LayoutArea(area.getPageNumber(), new Rectangle(layoutBox.getX(), layoutBox.getY() + layoutBox.getHeight(), 0.0F, 0.0F));
/*      */     
/*  207 */     boolean anythingPlaced = false;
/*      */     
/*  209 */     int currentTextPos = this.text.start;
/*  210 */     UnitValue fontSize = getPropertyAsUnitValue(24);
/*  211 */     if (!fontSize.isPointValue()) {
/*  212 */       Logger logger = LoggerFactory.getLogger(TextRenderer.class);
/*  213 */       logger.error(MessageFormatUtil.format("Property {0} in percents is not supported", new Object[] { Integer.valueOf(24) }));
/*      */     } 
/*  215 */     float textRise = getPropertyAsFloat(72).floatValue();
/*  216 */     Float characterSpacing = getPropertyAsFloat(15);
/*  217 */     Float wordSpacing = getPropertyAsFloat(78);
/*  218 */     float hScale = ((Float)getProperty(29, Float.valueOf(1.0F))).floatValue();
/*  219 */     ISplitCharacters splitCharacters = getProperty(62);
/*  220 */     float italicSkewAddition = Boolean.TRUE.equals(getPropertyAsBoolean(31)) ? (0.21256F * fontSize.getValue()) : 0.0F;
/*  221 */     float boldSimulationAddition = Boolean.TRUE.equals(getPropertyAsBoolean(8)) ? (0.033333335F * fontSize.getValue()) : 0.0F;
/*      */     
/*  223 */     this.line = new GlyphLine(this.text);
/*  224 */     this.line.start = this.line.end = -1;
/*      */     
/*  226 */     float ascender = 0.0F;
/*  227 */     float descender = 0.0F;
/*      */     
/*  229 */     float currentLineAscender = 0.0F;
/*  230 */     float currentLineDescender = 0.0F;
/*  231 */     float currentLineHeight = 0.0F;
/*  232 */     int initialLineTextPos = currentTextPos;
/*  233 */     float currentLineWidth = 0.0F;
/*  234 */     int previousCharPos = -1;
/*      */     
/*  236 */     RenderingMode mode = getProperty(123);
/*  237 */     float[] ascenderDescender = calculateAscenderDescender(this.font, mode);
/*  238 */     ascender = ascenderDescender[0];
/*  239 */     descender = ascenderDescender[1];
/*  240 */     if (RenderingMode.HTML_MODE.equals(mode)) {
/*  241 */       currentLineAscender = ascenderDescender[0];
/*  242 */       currentLineDescender = ascenderDescender[1];
/*  243 */       currentLineHeight = (currentLineAscender - currentLineDescender) * fontSize.getValue() / 1000.0F + textRise;
/*      */     } 
/*      */     
/*  246 */     this.savedWordBreakAtLineEnding = null;
/*  247 */     Glyph wordBreakGlyphAtLineEnding = null;
/*      */     
/*  249 */     Character tabAnchorCharacter = getProperty(66);
/*      */     
/*  251 */     TextLayoutResult result = null;
/*      */ 
/*      */ 
/*      */     
/*  255 */     OverflowPropertyValue overflowY = !layoutContext.isClippedHeight() ? OverflowPropertyValue.FIT : (OverflowPropertyValue)this.parent.getProperty(104);
/*      */ 
/*      */     
/*  258 */     boolean isSplitForcedByNewLine = false;
/*      */     
/*  260 */     boolean forcePartialSplitOnFirstChar = false;
/*      */     
/*  262 */     boolean ignoreNewLineSymbol = false;
/*      */     
/*  264 */     boolean crlf = false;
/*      */     
/*  266 */     HyphenationConfig hyphenationConfig = getProperty(30);
/*      */ 
/*      */     
/*  269 */     int firstPrintPos = currentTextPos;
/*  270 */     while (firstPrintPos < this.text.end && noPrint(this.text.get(firstPrintPos))) {
/*  271 */       firstPrintPos++;
/*      */     }
/*      */     
/*  274 */     while (currentTextPos < this.text.end) {
/*  275 */       if (noPrint(this.text.get(currentTextPos))) {
/*  276 */         if (this.line.start == -1) {
/*  277 */           this.line.start = currentTextPos;
/*      */         }
/*  279 */         this.line.end = Math.max(this.line.end, currentTextPos + 1);
/*  280 */         currentTextPos++;
/*      */         
/*      */         continue;
/*      */       } 
/*  284 */       int nonBreakablePartEnd = this.text.end - 1;
/*  285 */       float nonBreakablePartFullWidth = 0.0F;
/*  286 */       float nonBreakablePartWidthWhichDoesNotExceedAllowedWidth = 0.0F;
/*  287 */       float nonBreakablePartMaxAscender = 0.0F;
/*  288 */       float nonBreakablePartMaxDescender = 0.0F;
/*  289 */       float nonBreakablePartMaxHeight = 0.0F;
/*  290 */       int firstCharacterWhichExceedsAllowedWidth = -1;
/*  291 */       float nonBreakingHyphenRelatedChunkWidth = 0.0F;
/*  292 */       int nonBreakingHyphenRelatedChunkStart = -1;
/*  293 */       float beforeNonBreakingHyphenRelatedChunkMaxAscender = 0.0F;
/*  294 */       float beforeNonBreakingHyphenRelatedChunkMaxDescender = 0.0F;
/*      */       
/*  296 */       for (int ind = currentTextPos; ind < this.text.end; ind++) {
/*  297 */         if (TextUtil.isNewLine(this.text.get(ind))) {
/*  298 */           wordBreakGlyphAtLineEnding = this.text.get(ind);
/*  299 */           isSplitForcedByNewLine = true;
/*  300 */           firstCharacterWhichExceedsAllowedWidth = ind + 1;
/*  301 */           if (ind != firstPrintPos) {
/*  302 */             ignoreNewLineSymbol = true;
/*      */           } else {
/*      */             
/*  305 */             forcePartialSplitOnFirstChar = true;
/*      */           } 
/*      */           
/*  308 */           if (this.line.start == -1) {
/*  309 */             this.line.start = currentTextPos;
/*      */           }
/*      */           
/*  312 */           crlf = TextUtil.isCarriageReturnFollowedByLineFeed(this.text, currentTextPos);
/*      */           
/*  314 */           if (crlf) {
/*  315 */             currentTextPos++;
/*      */           }
/*      */           
/*  318 */           this.line.end = Math.max(this.line.end, firstCharacterWhichExceedsAllowedWidth - 1);
/*      */           
/*      */           break;
/*      */         } 
/*  322 */         Glyph currentGlyph = this.text.get(ind);
/*  323 */         if (noPrint(currentGlyph)) {
/*  324 */           if (ind + 1 == this.text.end || (splitCharacters
/*  325 */             .isSplitCharacter(this.text, ind + 1) && 
/*  326 */             TextUtil.isSpaceOrWhitespace(this.text.get(ind + 1)))) {
/*  327 */             nonBreakablePartEnd = ind;
/*      */             
/*      */             break;
/*      */           } 
/*      */         } else {
/*  332 */           if (tabAnchorCharacter != null && tabAnchorCharacter.charValue() == this.text.get(ind).getUnicode()) {
/*  333 */             this.tabAnchorCharacterPosition = currentLineWidth + nonBreakablePartFullWidth;
/*  334 */             tabAnchorCharacter = null;
/*      */           } 
/*      */           
/*  337 */           float glyphWidth = getCharWidth(currentGlyph, fontSize.getValue(), Float.valueOf(hScale), characterSpacing, wordSpacing) / 1000.0F;
/*  338 */           float xAdvance = (previousCharPos != -1) ? this.text.get(previousCharPos).getXAdvance() : 0.0F;
/*  339 */           if (xAdvance != 0.0F) {
/*  340 */             xAdvance = scaleXAdvance(xAdvance, fontSize.getValue(), Float.valueOf(hScale)) / 1000.0F;
/*      */           }
/*      */           
/*  343 */           if ((!noSoftWrap && nonBreakablePartFullWidth + glyphWidth + xAdvance + italicSkewAddition + boldSimulationAddition > layoutBox
/*  344 */             .getWidth() - currentLineWidth && firstCharacterWhichExceedsAllowedWidth == -1) || ind == this.specialScriptFirstNotFittingIndex) {
/*      */ 
/*      */             
/*  347 */             firstCharacterWhichExceedsAllowedWidth = ind;
/*  348 */             if (TextUtil.isSpaceOrWhitespace(this.text.get(ind))) {
/*  349 */               wordBreakGlyphAtLineEnding = currentGlyph;
/*  350 */               if (ind == firstPrintPos) {
/*  351 */                 forcePartialSplitOnFirstChar = true;
/*  352 */                 firstCharacterWhichExceedsAllowedWidth = ind + 1;
/*      */                 
/*      */                 break;
/*      */               } 
/*      */             } 
/*      */           } 
/*  358 */           if (null != hyphenationConfig) {
/*  359 */             if (glyphBelongsToNonBreakingHyphenRelatedChunk(this.text, ind)) {
/*  360 */               if (-1 == nonBreakingHyphenRelatedChunkStart) {
/*  361 */                 beforeNonBreakingHyphenRelatedChunkMaxAscender = nonBreakablePartMaxAscender;
/*  362 */                 beforeNonBreakingHyphenRelatedChunkMaxDescender = nonBreakablePartMaxDescender;
/*  363 */                 nonBreakingHyphenRelatedChunkStart = ind;
/*      */               } 
/*  365 */               nonBreakingHyphenRelatedChunkWidth += glyphWidth + xAdvance;
/*      */             } else {
/*  367 */               nonBreakingHyphenRelatedChunkStart = -1;
/*  368 */               nonBreakingHyphenRelatedChunkWidth = 0.0F;
/*      */             } 
/*      */           }
/*  371 */           if (firstCharacterWhichExceedsAllowedWidth == -1) {
/*  372 */             nonBreakablePartWidthWhichDoesNotExceedAllowedWidth += glyphWidth + xAdvance;
/*      */           }
/*  374 */           nonBreakablePartFullWidth += glyphWidth + xAdvance;
/*      */           
/*  376 */           nonBreakablePartMaxAscender = Math.max(nonBreakablePartMaxAscender, ascender);
/*  377 */           nonBreakablePartMaxDescender = Math.min(nonBreakablePartMaxDescender, descender);
/*  378 */           nonBreakablePartMaxHeight = (nonBreakablePartMaxAscender - nonBreakablePartMaxDescender) * fontSize.getValue() / 1000.0F + textRise;
/*      */           
/*  380 */           previousCharPos = ind;
/*      */           
/*  382 */           if (!noSoftWrap && nonBreakablePartFullWidth + italicSkewAddition + boldSimulationAddition > layoutBox
/*  383 */             .getWidth() && (0.0F == nonBreakingHyphenRelatedChunkWidth || ind + 1 == this.text.end || 
/*  384 */             !glyphBelongsToNonBreakingHyphenRelatedChunk(this.text, ind + 1)) && 
/*  385 */             isOverflowFit(overflowX)) {
/*      */             break;
/*      */           }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*  393 */           boolean endOfWordBelongingToSpecialScripts = (textContainsSpecialScriptGlyphs(true) && findPossibleBreaksSplitPosition(this.specialScriptsWordBreakPoints, ind + 1, true) >= 0);
/*      */           
/*  395 */           if (ind + 1 == this.text.end || splitCharacters.isSplitCharacter(this.text, ind) || (splitCharacters
/*  396 */             .isSplitCharacter(this.text, ind + 1) && 
/*  397 */             TextUtil.isSpaceOrWhitespace(this.text.get(ind + 1))) || endOfWordBelongingToSpecialScripts) {
/*  398 */             nonBreakablePartEnd = ind;
/*      */             break;
/*      */           } 
/*      */         } 
/*      */       } 
/*  403 */       if (firstCharacterWhichExceedsAllowedWidth == -1) {
/*      */         
/*  405 */         if (this.line.start == -1) {
/*  406 */           this.line.start = currentTextPos;
/*      */         }
/*  408 */         this.line.end = Math.max(this.line.end, nonBreakablePartEnd + 1);
/*  409 */         currentLineAscender = Math.max(currentLineAscender, nonBreakablePartMaxAscender);
/*  410 */         currentLineDescender = Math.min(currentLineDescender, nonBreakablePartMaxDescender);
/*  411 */         currentLineHeight = Math.max(currentLineHeight, nonBreakablePartMaxHeight);
/*  412 */         currentTextPos = nonBreakablePartEnd + 1;
/*  413 */         currentLineWidth += nonBreakablePartFullWidth;
/*  414 */         widthHandler.updateMinChildWidth(nonBreakablePartWidthWhichDoesNotExceedAllowedWidth + italicSkewAddition + boldSimulationAddition);
/*  415 */         widthHandler.updateMaxChildWidth(nonBreakablePartWidthWhichDoesNotExceedAllowedWidth + italicSkewAddition + boldSimulationAddition);
/*  416 */         anythingPlaced = true;
/*      */         continue;
/*      */       } 
/*  419 */       if (Math.max(currentLineHeight, nonBreakablePartMaxHeight) > layoutBox.getHeight() && isOverflowFit(overflowY)) {
/*  420 */         applyPaddings(this.occupiedArea.getBBox(), paddings, true);
/*  421 */         applyBorderBox(this.occupiedArea.getBBox(), borders, true);
/*  422 */         applyMargins(this.occupiedArea.getBBox(), margins, true);
/*      */         
/*  424 */         if (this.line.start == -1) {
/*  425 */           this.line.start = currentTextPos;
/*      */         }
/*  427 */         this.line.end = Math.max(this.line.end, firstCharacterWhichExceedsAllowedWidth);
/*      */         
/*  429 */         TextRenderer[] splitResult = split(initialLineTextPos);
/*  430 */         return (LayoutResult)new TextLayoutResult(3, this.occupiedArea, splitResult[0], splitResult[1], this);
/*      */       } 
/*      */ 
/*      */       
/*  434 */       boolean wordSplit = false;
/*  435 */       boolean hyphenationApplied = false;
/*      */       
/*  437 */       if (hyphenationConfig != null) {
/*  438 */         if (-1 == nonBreakingHyphenRelatedChunkStart) {
/*  439 */           int[] wordBounds = getWordBoundsForHyphenation(this.text, currentTextPos, this.text.end, Math.max(currentTextPos, firstCharacterWhichExceedsAllowedWidth - 1));
/*  440 */           if (wordBounds != null) {
/*  441 */             String word = this.text.toUnicodeString(wordBounds[0], wordBounds[1]);
/*  442 */             Hyphenation hyph = hyphenationConfig.hyphenate(word);
/*  443 */             if (hyph != null) {
/*  444 */               for (int i = hyph.length() - 1; i >= 0; i--) {
/*  445 */                 String pre = hyph.getPreHyphenText(i);
/*  446 */                 String pos = hyph.getPostHyphenText(i);
/*      */                 
/*  448 */                 float currentHyphenationChoicePreTextWidth = getGlyphLineWidth(convertToGlyphLine(this.text.toUnicodeString(currentTextPos, wordBounds[0]) + pre + hyphenationConfig.getHyphenSymbol()), fontSize.getValue(), hScale, characterSpacing, wordSpacing);
/*  449 */                 if (currentLineWidth + currentHyphenationChoicePreTextWidth + italicSkewAddition + boldSimulationAddition <= layoutBox.getWidth()) {
/*  450 */                   hyphenationApplied = true;
/*      */                   
/*  452 */                   if (this.line.start == -1) {
/*  453 */                     this.line.start = currentTextPos;
/*      */                   }
/*  455 */                   this.line.end = Math.max(this.line.end, wordBounds[0] + pre.length());
/*  456 */                   GlyphLine lineCopy = this.line.copy(this.line.start, this.line.end);
/*  457 */                   lineCopy.add(this.font.getGlyph(hyphenationConfig.getHyphenSymbol()));
/*  458 */                   lineCopy.end++;
/*  459 */                   this.line = lineCopy;
/*      */ 
/*      */                   
/*  462 */                   currentLineAscender = Math.max(currentLineAscender, nonBreakablePartMaxAscender);
/*  463 */                   currentLineDescender = Math.min(currentLineDescender, nonBreakablePartMaxDescender);
/*  464 */                   currentLineHeight = Math.max(currentLineHeight, nonBreakablePartMaxHeight);
/*      */                   
/*  466 */                   currentLineWidth += currentHyphenationChoicePreTextWidth;
/*  467 */                   widthHandler.updateMinChildWidth(currentHyphenationChoicePreTextWidth + italicSkewAddition + boldSimulationAddition);
/*  468 */                   widthHandler.updateMaxChildWidth(currentHyphenationChoicePreTextWidth + italicSkewAddition + boldSimulationAddition);
/*      */                   
/*  470 */                   currentTextPos = wordBounds[0] + pre.length();
/*      */                   
/*      */                   break;
/*      */                 } 
/*      */               } 
/*      */             }
/*      */           } 
/*  477 */         } else if (this.text.start == nonBreakingHyphenRelatedChunkStart) {
/*  478 */           nonBreakingHyphenRelatedChunkWidth = 0.0F;
/*  479 */           firstCharacterWhichExceedsAllowedWidth = previousCharPos + 1;
/*      */         } else {
/*  481 */           firstCharacterWhichExceedsAllowedWidth = nonBreakingHyphenRelatedChunkStart;
/*  482 */           nonBreakablePartFullWidth -= nonBreakingHyphenRelatedChunkWidth;
/*  483 */           nonBreakablePartMaxAscender = beforeNonBreakingHyphenRelatedChunkMaxAscender;
/*  484 */           nonBreakablePartMaxDescender = beforeNonBreakingHyphenRelatedChunkMaxDescender;
/*      */         } 
/*      */       }
/*      */ 
/*      */       
/*  489 */       boolean specialScriptWordSplit = (textContainsSpecialScriptGlyphs(true) && !isSplitForcedByNewLine);
/*      */       
/*  491 */       if ((nonBreakablePartFullWidth > layoutBox.getWidth() && !anythingPlaced && !hyphenationApplied) || forcePartialSplitOnFirstChar || -1 != nonBreakingHyphenRelatedChunkStart || specialScriptWordSplit) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  498 */         if (this.line.start == -1) {
/*  499 */           this.line.start = currentTextPos;
/*      */         }
/*  501 */         if (!crlf) {
/*  502 */           currentTextPos = (forcePartialSplitOnFirstChar || isOverflowFit(overflowX) || specialScriptWordSplit) ? firstCharacterWhichExceedsAllowedWidth : (nonBreakablePartEnd + 1);
/*      */         }
/*  504 */         this.line.end = Math.max(this.line.end, currentTextPos);
/*  505 */         wordSplit = (!forcePartialSplitOnFirstChar && this.text.end != currentTextPos);
/*  506 */         if (wordSplit || (!forcePartialSplitOnFirstChar && !isOverflowFit(overflowX))) {
/*  507 */           currentLineAscender = Math.max(currentLineAscender, nonBreakablePartMaxAscender);
/*  508 */           currentLineDescender = Math.min(currentLineDescender, nonBreakablePartMaxDescender);
/*  509 */           currentLineHeight = Math.max(currentLineHeight, nonBreakablePartMaxHeight);
/*  510 */           currentLineWidth += nonBreakablePartWidthWhichDoesNotExceedAllowedWidth;
/*  511 */           widthHandler.updateMinChildWidth(nonBreakablePartWidthWhichDoesNotExceedAllowedWidth + italicSkewAddition + boldSimulationAddition);
/*  512 */           widthHandler.updateMaxChildWidth(nonBreakablePartWidthWhichDoesNotExceedAllowedWidth + italicSkewAddition + boldSimulationAddition);
/*      */         } else {
/*      */           
/*  515 */           currentLineAscender = ascender;
/*  516 */           currentLineDescender = descender;
/*  517 */           currentLineHeight = (currentLineAscender - currentLineDescender) * fontSize.getValue() / 1000.0F + textRise;
/*  518 */           currentLineWidth += getCharWidth(this.line.get(this.line.start), fontSize.getValue(), Float.valueOf(hScale), characterSpacing, wordSpacing) / 1000.0F;
/*      */         } 
/*      */       } 
/*  521 */       if (this.line.end <= this.line.start) {
/*  522 */         return (LayoutResult)new TextLayoutResult(3, this.occupiedArea, null, this, this);
/*      */       }
/*  524 */       result = (new TextLayoutResult(2, this.occupiedArea, null, null)).setWordHasBeenSplit(wordSplit);
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  532 */     boolean isPlacingForcedWhileNothing = false;
/*  533 */     if (currentLineHeight > layoutBox.getHeight()) {
/*  534 */       if (!Boolean.TRUE.equals(getPropertyAsBoolean(26)) && isOverflowFit(overflowY)) {
/*  535 */         applyPaddings(this.occupiedArea.getBBox(), paddings, true);
/*  536 */         applyBorderBox(this.occupiedArea.getBBox(), borders, true);
/*  537 */         applyMargins(this.occupiedArea.getBBox(), margins, true);
/*  538 */         return (LayoutResult)new TextLayoutResult(3, this.occupiedArea, null, this, this);
/*      */       } 
/*  540 */       isPlacingForcedWhileNothing = true;
/*      */     } 
/*      */ 
/*      */     
/*  544 */     this.yLineOffset = currentLineAscender * fontSize.getValue() / 1000.0F;
/*      */     
/*  546 */     this.occupiedArea.getBBox().moveDown(currentLineHeight);
/*  547 */     this.occupiedArea.getBBox().setHeight(this.occupiedArea.getBBox().getHeight() + currentLineHeight);
/*      */     
/*  549 */     this.occupiedArea.getBBox().setWidth(Math.max(this.occupiedArea.getBBox().getWidth(), currentLineWidth));
/*  550 */     layoutBox.setHeight(area.getBBox().getHeight() - currentLineHeight);
/*      */     
/*  552 */     this.occupiedArea.getBBox().setWidth(this.occupiedArea.getBBox().getWidth() + italicSkewAddition + boldSimulationAddition);
/*      */     
/*  554 */     applyPaddings(this.occupiedArea.getBBox(), paddings, true);
/*  555 */     applyBorderBox(this.occupiedArea.getBBox(), borders, true);
/*  556 */     applyMargins(this.occupiedArea.getBBox(), margins, true);
/*      */     
/*  558 */     increaseYLineOffset(paddings, borders, margins);
/*      */     
/*  560 */     if (result == null) {
/*  561 */       result = new TextLayoutResult(1, this.occupiedArea, null, null, isPlacingForcedWhileNothing ? this : null);
/*      */     } else {
/*      */       TextRenderer[] split;
/*  564 */       if (ignoreNewLineSymbol || crlf) {
/*      */         
/*  566 */         split = splitIgnoreFirstNewLine(currentTextPos);
/*      */       } else {
/*  568 */         split = split(currentTextPos);
/*      */       } 
/*  570 */       result.setSplitForcedByNewline(isSplitForcedByNewLine);
/*  571 */       result.setSplitRenderer(split[0]);
/*  572 */       if (wordBreakGlyphAtLineEnding != null) {
/*  573 */         split[0].saveWordBreakIfNotYetSaved(wordBreakGlyphAtLineEnding);
/*      */       }
/*      */ 
/*      */       
/*  577 */       if ((split[1]).text.start != (split[1]).text.end) {
/*  578 */         result.setOverflowRenderer(split[1]);
/*      */       } else {
/*      */         
/*  581 */         result.setStatus(1);
/*      */       } 
/*      */     } 
/*      */     
/*  585 */     if (FloatingHelper.isRendererFloating(this, floatPropertyValue)) {
/*  586 */       if (result.getStatus() == 1) {
/*  587 */         if (this.occupiedArea.getBBox().getWidth() > 0.0F) {
/*  588 */           floatRendererAreas.add(this.occupiedArea.getBBox());
/*      */         }
/*  590 */       } else if (result.getStatus() == 2) {
/*  591 */         floatRendererAreas.add(result.getSplitRenderer().getOccupiedArea().getBBox());
/*      */       } 
/*      */     }
/*      */     
/*  595 */     result.setMinMaxWidth(countedMinMaxWidth);
/*  596 */     return (LayoutResult)result;
/*      */   }
/*      */   
/*      */   private void increaseYLineOffset(UnitValue[] paddings, Border[] borders, UnitValue[] margins) {
/*  600 */     this.yLineOffset += (paddings[0] != null) ? paddings[0].getValue() : 0.0F;
/*  601 */     this.yLineOffset += (borders[0] != null) ? borders[0].getWidth() : 0.0F;
/*  602 */     this.yLineOffset += (margins[0] != null) ? margins[0].getValue() : 0.0F;
/*      */   }
/*      */   
/*      */   public void applyOtf() {
/*  606 */     updateFontAndText();
/*  607 */     Character.UnicodeScript script = getProperty(23);
/*  608 */     if (!this.otfFeaturesApplied && TypographyUtils.isPdfCalligraphAvailable() && this.text.start < this.text.end) {
/*  609 */       if (hasOtfFont()) {
/*  610 */         Object typographyConfig = getProperty(117);
/*  611 */         Collection<Character.UnicodeScript> supportedScripts = null;
/*  612 */         if (typographyConfig != null) {
/*  613 */           supportedScripts = TypographyUtils.getSupportedScripts(typographyConfig);
/*      */         }
/*  615 */         if (supportedScripts == null) {
/*  616 */           supportedScripts = TypographyUtils.getSupportedScripts();
/*      */         }
/*  618 */         List<ScriptRange> scriptsRanges = new ArrayList<>();
/*  619 */         if (script != null) {
/*  620 */           scriptsRanges.add(new ScriptRange(script, this.text.end));
/*      */         } else {
/*      */           
/*  623 */           ScriptRange currRange = new ScriptRange(null, this.text.end);
/*  624 */           scriptsRanges.add(currRange);
/*  625 */           for (int i = this.text.start; i < this.text.end; i++) {
/*  626 */             int unicode = this.text.get(i).getUnicode();
/*  627 */             if (unicode > -1) {
/*  628 */               Character.UnicodeScript glyphScript = Character.UnicodeScript.of(unicode);
/*  629 */               if (!Character.UnicodeScript.COMMON.equals(glyphScript) && !Character.UnicodeScript.UNKNOWN.equals(glyphScript) && 
/*  630 */                 !Character.UnicodeScript.INHERITED.equals(glyphScript))
/*      */               {
/*      */                 
/*  633 */                 if (glyphScript != currRange.script) {
/*  634 */                   if (currRange.script == null) {
/*  635 */                     currRange.script = glyphScript;
/*      */                   } else {
/*  637 */                     currRange.rangeEnd = i;
/*  638 */                     currRange = new ScriptRange(glyphScript, this.text.end);
/*  639 */                     scriptsRanges.add(currRange);
/*      */                   } 
/*      */                 }
/*      */               }
/*      */             } 
/*      */           } 
/*      */         } 
/*  646 */         int delta = 0;
/*  647 */         int origTextStart = this.text.start;
/*  648 */         int origTextEnd = this.text.end;
/*  649 */         int shapingRangeStart = this.text.start;
/*  650 */         for (ScriptRange scriptsRange : scriptsRanges) {
/*  651 */           if (scriptsRange.script == null || !supportedScripts.contains(EnumUtil.throwIfNull(scriptsRange.script))) {
/*      */             continue;
/*      */           }
/*  654 */           scriptsRange.rangeEnd += delta;
/*  655 */           this.text.start = shapingRangeStart;
/*  656 */           this.text.end = scriptsRange.rangeEnd;
/*      */           
/*  658 */           if ((scriptsRange.script == Character.UnicodeScript.ARABIC || scriptsRange.script == Character.UnicodeScript.HEBREW) && this.parent instanceof LineRenderer)
/*      */           {
/*      */ 
/*      */ 
/*      */ 
/*      */             
/*  664 */             setProperty(7, BaseDirection.DEFAULT_BIDI);
/*      */           }
/*  666 */           TypographyUtils.applyOtfScript(this.font.getFontProgram(), this.text, scriptsRange.script, typographyConfig);
/*      */           
/*  668 */           delta += this.text.end - scriptsRange.rangeEnd;
/*  669 */           scriptsRange.rangeEnd = shapingRangeStart = this.text.end;
/*      */         } 
/*  671 */         this.text.start = origTextStart;
/*  672 */         this.text.end = origTextEnd + delta;
/*      */       } 
/*      */       
/*  675 */       FontKerning fontKerning = getProperty(22, FontKerning.NO);
/*  676 */       if (fontKerning == FontKerning.YES) {
/*  677 */         TypographyUtils.applyKerning(this.font.getFontProgram(), this.text);
/*      */       }
/*      */       
/*  680 */       this.otfFeaturesApplied = true;
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public void draw(DrawContext drawContext) {
/*  686 */     if (this.occupiedArea == null) {
/*  687 */       Logger logger = LoggerFactory.getLogger(TextRenderer.class);
/*  688 */       logger.error(MessageFormatUtil.format("Occupied area has not been initialized. {0}", new Object[] { "Drawing won't be performed." }));
/*      */       
/*      */       return;
/*      */     } 
/*      */     
/*  693 */     boolean isTagged = drawContext.isTaggingEnabled();
/*  694 */     LayoutTaggingHelper taggingHelper = null;
/*  695 */     boolean isArtifact = false;
/*  696 */     TagTreePointer tagPointer = null;
/*  697 */     if (isTagged) {
/*  698 */       taggingHelper = getProperty(108);
/*  699 */       if (taggingHelper == null) {
/*  700 */         isArtifact = true;
/*      */       } else {
/*  702 */         isArtifact = taggingHelper.isArtifact(this);
/*  703 */         if (!isArtifact) {
/*  704 */           tagPointer = taggingHelper.useAutoTaggingPointerAndRememberItsPosition(this);
/*  705 */           if (taggingHelper.createTag(this, tagPointer)) {
/*  706 */             tagPointer.getProperties().addAttributes(0, AccessibleAttributesApplier.getLayoutAttributes(this, tagPointer));
/*      */           }
/*      */         } 
/*      */       } 
/*      */     } 
/*      */     
/*  712 */     super.draw(drawContext);
/*      */     
/*  714 */     boolean isRelativePosition = isRelativePosition();
/*  715 */     if (isRelativePosition) {
/*  716 */       applyRelativePositioningTranslation(false);
/*      */     }
/*      */     
/*  719 */     float leftBBoxX = getInnerAreaBBox().getX();
/*      */     
/*  721 */     if (this.line.end > this.line.start || this.savedWordBreakAtLineEnding != null) {
/*  722 */       UnitValue fontSize = getPropertyAsUnitValue(24);
/*  723 */       if (!fontSize.isPointValue()) {
/*  724 */         Logger logger = LoggerFactory.getLogger(TextRenderer.class);
/*  725 */         logger.error(MessageFormatUtil.format("Property {0} in percents is not supported", new Object[] { Integer.valueOf(24) }));
/*      */       } 
/*  727 */       TransparentColor fontColor = getPropertyAsTransparentColor(21);
/*  728 */       Integer textRenderingMode = getProperty(71);
/*  729 */       Float textRise = getPropertyAsFloat(72);
/*  730 */       Float characterSpacing = getPropertyAsFloat(15);
/*  731 */       Float wordSpacing = getPropertyAsFloat(78);
/*  732 */       Float horizontalScaling = getProperty(29);
/*  733 */       float[] skew = getProperty(65);
/*  734 */       boolean italicSimulation = Boolean.TRUE.equals(getPropertyAsBoolean(31));
/*  735 */       boolean boldSimulation = Boolean.TRUE.equals(getPropertyAsBoolean(8));
/*  736 */       Float strokeWidth = null;
/*      */       
/*  738 */       if (boldSimulation) {
/*  739 */         textRenderingMode = Integer.valueOf(2);
/*  740 */         strokeWidth = Float.valueOf(fontSize.getValue() / 30.0F);
/*      */       } 
/*      */       
/*  743 */       PdfCanvas canvas = drawContext.getCanvas();
/*  744 */       if (isTagged) {
/*  745 */         if (isArtifact) {
/*  746 */           canvas.openTag((CanvasTag)new CanvasArtifact());
/*      */         } else {
/*  748 */           canvas.openTag(tagPointer.getTagReference());
/*      */         } 
/*      */       }
/*  751 */       beginElementOpacityApplying(drawContext);
/*  752 */       canvas.saveState().beginText().setFontAndSize(this.font, fontSize.getValue());
/*      */       
/*  754 */       if (skew != null && skew.length == 2) {
/*  755 */         canvas.setTextMatrix(1.0F, skew[0], skew[1], 1.0F, leftBBoxX, getYLine());
/*  756 */       } else if (italicSimulation) {
/*  757 */         canvas.setTextMatrix(1.0F, 0.0F, 0.21256F, 1.0F, leftBBoxX, getYLine());
/*      */       } else {
/*  759 */         canvas.moveText(leftBBoxX, getYLine());
/*      */       } 
/*      */       
/*  762 */       if (textRenderingMode.intValue() != 0) {
/*  763 */         canvas.setTextRenderingMode(textRenderingMode.intValue());
/*      */       }
/*  765 */       if (textRenderingMode.intValue() == 1 || textRenderingMode.intValue() == 2) {
/*  766 */         if (strokeWidth == null) {
/*  767 */           strokeWidth = getPropertyAsFloat(64);
/*      */         }
/*  769 */         if (strokeWidth != null && strokeWidth.floatValue() != 1.0F) {
/*  770 */           canvas.setLineWidth(strokeWidth.floatValue());
/*      */         }
/*  772 */         Color strokeColor = getPropertyAsColor(63);
/*  773 */         if (strokeColor == null && fontColor != null) {
/*  774 */           strokeColor = fontColor.getColor();
/*      */         }
/*  776 */         if (strokeColor != null) {
/*  777 */           canvas.setStrokeColor(strokeColor);
/*      */         }
/*      */       } 
/*  780 */       if (fontColor != null) {
/*  781 */         canvas.setFillColor(fontColor.getColor());
/*  782 */         fontColor.applyFillTransparency(canvas);
/*      */       } 
/*  784 */       if (textRise != null && textRise.floatValue() != 0.0F) {
/*  785 */         canvas.setTextRise(textRise.floatValue());
/*      */       }
/*  787 */       if (characterSpacing != null && characterSpacing.floatValue() != 0.0F) {
/*  788 */         canvas.setCharacterSpacing(characterSpacing.floatValue());
/*      */       }
/*  790 */       if (wordSpacing != null && wordSpacing.floatValue() != 0.0F) {
/*  791 */         if (this.font instanceof com.itextpdf.kernel.font.PdfType0Font) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*  797 */           for (int gInd = this.line.start; gInd < this.line.end; gInd++) {
/*  798 */             if (TextUtil.isUni0020(this.line.get(gInd))) {
/*  799 */               short advance = (short)(int)(1000.0F * wordSpacing.floatValue() / fontSize.getValue());
/*  800 */               Glyph copy = new Glyph(this.line.get(gInd));
/*  801 */               copy.setXAdvance(advance);
/*  802 */               this.line.set(gInd, copy);
/*      */             } 
/*      */           } 
/*      */         } else {
/*  806 */           canvas.setWordSpacing(wordSpacing.floatValue());
/*      */         } 
/*      */       }
/*  809 */       if (horizontalScaling != null && horizontalScaling.floatValue() != 1.0F) {
/*  810 */         canvas.setHorizontalScaling(horizontalScaling.floatValue() * 100.0F);
/*      */       }
/*      */       
/*  813 */       GlyphLine.IGlyphLineFilter filter = new GlyphLine.IGlyphLineFilter()
/*      */         {
/*      */           public boolean accept(Glyph glyph) {
/*  816 */             return !TextRenderer.noPrint(glyph);
/*      */           }
/*      */         };
/*      */       
/*  820 */       boolean appearanceStreamLayout = Boolean.TRUE.equals(getPropertyAsBoolean(82));
/*      */       
/*  822 */       if (getReversedRanges() != null) {
/*  823 */         boolean writeReversedChars = !appearanceStreamLayout;
/*  824 */         ArrayList<Integer> removedIds = new ArrayList<>();
/*  825 */         for (int i = this.line.start; i < this.line.end; i++) {
/*  826 */           if (!filter.accept(this.line.get(i))) {
/*  827 */             removedIds.add(Integer.valueOf(i));
/*      */           }
/*      */         } 
/*  830 */         for (int[] range : getReversedRanges()) {
/*  831 */           updateRangeBasedOnRemovedCharacters(removedIds, range);
/*      */         }
/*  833 */         this.line = this.line.filter(filter);
/*  834 */         if (writeReversedChars) {
/*  835 */           canvas.showText(this.line, (new ReversedCharsIterator(this.reversedRanges, this.line))
/*  836 */               .setUseReversed(true));
/*      */         } else {
/*  838 */           canvas.showText(this.line);
/*      */         } 
/*      */       } else {
/*  841 */         if (appearanceStreamLayout) {
/*  842 */           this.line.setActualText(this.line.start, this.line.end, null);
/*      */         }
/*  844 */         canvas.showText(this.line.filter(filter));
/*      */       } 
/*  846 */       if (this.savedWordBreakAtLineEnding != null) {
/*  847 */         canvas.showText(this.savedWordBreakAtLineEnding);
/*      */       }
/*      */       
/*  850 */       canvas.endText().restoreState();
/*  851 */       endElementOpacityApplying(drawContext);
/*      */       
/*  853 */       Object underlines = getProperty(74);
/*  854 */       if (underlines instanceof List) {
/*  855 */         for (Object underline : underlines) {
/*  856 */           if (underline instanceof Underline) {
/*  857 */             drawSingleUnderline((Underline)underline, fontColor, canvas, fontSize.getValue(), italicSimulation ? 0.21256F : 0.0F);
/*      */           }
/*      */         } 
/*  860 */       } else if (underlines instanceof Underline) {
/*  861 */         drawSingleUnderline((Underline)underlines, fontColor, canvas, fontSize.getValue(), italicSimulation ? 0.21256F : 0.0F);
/*      */       } 
/*      */       
/*  864 */       if (isTagged) {
/*  865 */         canvas.closeTag();
/*      */       }
/*      */     } 
/*      */     
/*  869 */     if (isRelativePosition) {
/*  870 */       applyRelativePositioningTranslation(false);
/*      */     }
/*      */     
/*  873 */     if (isTagged && !isArtifact) {
/*  874 */       if (this.isLastRendererForModelElement) {
/*  875 */         taggingHelper.finishTaggingHint(this);
/*      */       }
/*  877 */       taggingHelper.restoreAutoTaggingPointerPosition(this);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void trimFirst() {
/*  886 */     updateFontAndText();
/*      */     
/*  888 */     if (this.text != null) {
/*      */       Glyph glyph;
/*  890 */       while (this.text.start < this.text.end && 
/*  891 */         TextUtil.isWhitespace(glyph = this.text.get(this.text.start)) && !TextUtil.isNewLine(glyph)) {
/*  892 */         this.text.start++;
/*      */       }
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  904 */     if (textContainsSpecialScriptGlyphs(true) && ((Integer)this.specialScriptsWordBreakPoints
/*  905 */       .get(0)).intValue() == this.text.start) {
/*  906 */       if (this.specialScriptsWordBreakPoints.size() == 1) {
/*  907 */         this.specialScriptsWordBreakPoints.set(0, Integer.valueOf(-1));
/*      */       } else {
/*  909 */         this.specialScriptsWordBreakPoints.remove(0);
/*      */       } 
/*      */     }
/*      */   }
/*      */   
/*      */   float trimLast() {
/*  915 */     float trimmedSpace = 0.0F;
/*      */     
/*  917 */     if (this.line.end <= 0) {
/*  918 */       return trimmedSpace;
/*      */     }
/*  920 */     UnitValue fontSize = getPropertyAsUnitValue(24);
/*  921 */     if (!fontSize.isPointValue()) {
/*  922 */       Logger logger = LoggerFactory.getLogger(TextRenderer.class);
/*  923 */       logger.error(MessageFormatUtil.format("Property {0} in percents is not supported", new Object[] { Integer.valueOf(24) }));
/*      */     } 
/*  925 */     Float characterSpacing = getPropertyAsFloat(15);
/*  926 */     Float wordSpacing = getPropertyAsFloat(78);
/*  927 */     float hScale = getPropertyAsFloat(29, Float.valueOf(1.0F)).floatValue();
/*      */     
/*  929 */     int firstNonSpaceCharIndex = this.line.end - 1;
/*  930 */     while (firstNonSpaceCharIndex >= this.line.start) {
/*  931 */       Glyph currentGlyph = this.line.get(firstNonSpaceCharIndex);
/*  932 */       if (!TextUtil.isWhitespace(currentGlyph)) {
/*      */         break;
/*      */       }
/*  935 */       saveWordBreakIfNotYetSaved(currentGlyph);
/*      */       
/*  937 */       float currentCharWidth = getCharWidth(currentGlyph, fontSize.getValue(), Float.valueOf(hScale), characterSpacing, wordSpacing) / 1000.0F;
/*  938 */       float xAdvance = (firstNonSpaceCharIndex > this.line.start) ? (scaleXAdvance(this.line.get(firstNonSpaceCharIndex - 1).getXAdvance(), fontSize.getValue(), Float.valueOf(hScale)) / 1000.0F) : 0.0F;
/*  939 */       trimmedSpace += currentCharWidth - xAdvance;
/*  940 */       this.occupiedArea.getBBox().setWidth(this.occupiedArea.getBBox().getWidth() - currentCharWidth);
/*      */       
/*  942 */       firstNonSpaceCharIndex--;
/*      */     } 
/*      */     
/*  945 */     this.line.end = firstNonSpaceCharIndex + 1;
/*      */     
/*  947 */     return trimmedSpace;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public float getAscent() {
/*  957 */     return this.yLineOffset;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public float getDescent() {
/*  967 */     return -(getOccupiedAreaBBox().getHeight() - this.yLineOffset - getPropertyAsFloat(72).floatValue());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public float getYLine() {
/*  977 */     return this.occupiedArea.getBBox().getY() + this.occupiedArea.getBBox().getHeight() - this.yLineOffset - getPropertyAsFloat(72).floatValue();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void moveYLineTo(float y) {
/*  986 */     float curYLine = getYLine();
/*  987 */     float delta = y - curYLine;
/*  988 */     this.occupiedArea.getBBox().setY(this.occupiedArea.getBBox().getY() + delta);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setText(String text) {
/*  998 */     this.strToBeConverted = text;
/*      */     
/* 1000 */     updateFontAndText();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   public void setText(GlyphLine text, int leftPos, int rightPos) {
/* 1013 */     GlyphLine newText = new GlyphLine(text);
/* 1014 */     newText.start = leftPos;
/* 1015 */     newText.end = rightPos;
/* 1016 */     if (this.font != null) {
/* 1017 */       newText = TextPreprocessingUtil.replaceSpecialWhitespaceGlyphs(newText, this.font);
/*      */     }
/* 1019 */     setProcessedGlyphLineAndFont(newText, this.font);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setText(GlyphLine text, PdfFont font) {
/* 1029 */     GlyphLine newText = new GlyphLine(text);
/* 1030 */     newText = TextPreprocessingUtil.replaceSpecialWhitespaceGlyphs(newText, font);
/* 1031 */     setProcessedGlyphLineAndFont(newText, font);
/*      */   }
/*      */   
/*      */   public GlyphLine getText() {
/* 1035 */     updateFontAndText();
/* 1036 */     return this.text;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int length() {
/* 1045 */     return (this.text == null) ? 0 : (this.text.end - this.text.start);
/*      */   }
/*      */ 
/*      */   
/*      */   public String toString() {
/* 1050 */     return (this.line != null) ? this.line.toString() : null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int charAt(int pos) {
/* 1060 */     return this.text.get(pos + this.text.start).getUnicode();
/*      */   }
/*      */   
/*      */   public float getTabAnchorCharacterPosition() {
/* 1064 */     return this.tabAnchorCharacterPosition;
/*      */   }
/*      */ 
/*      */   
/*      */   public IRenderer getNextRenderer() {
/* 1069 */     return new TextRenderer((Text)this.modelElement);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static float[] calculateAscenderDescender(PdfFont font) {
/* 1080 */     return calculateAscenderDescender(font, RenderingMode.DEFAULT_LAYOUT_MODE);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static float[] calculateAscenderDescender(PdfFont font, RenderingMode mode) {
/*      */     float ascender, descender;
/* 1092 */     FontMetrics fontMetrics = font.getFontProgram().getFontMetrics();
/*      */ 
/*      */     
/* 1095 */     float usedTypoAscenderScaleCoeff = 1.2F;
/* 1096 */     if (RenderingMode.HTML_MODE.equals(mode) && !(font instanceof com.itextpdf.kernel.font.PdfType1Font)) {
/* 1097 */       usedTypoAscenderScaleCoeff = 1.0F;
/*      */     }
/* 1099 */     if (fontMetrics.getWinAscender() == 0 || fontMetrics.getWinDescender() == 0 || (fontMetrics
/* 1100 */       .getTypoAscender() == fontMetrics.getWinAscender() && fontMetrics
/* 1101 */       .getTypoDescender() == fontMetrics.getWinDescender())) {
/* 1102 */       ascender = fontMetrics.getTypoAscender() * usedTypoAscenderScaleCoeff;
/* 1103 */       descender = fontMetrics.getTypoDescender() * usedTypoAscenderScaleCoeff;
/*      */     } else {
/* 1105 */       ascender = fontMetrics.getWinAscender();
/* 1106 */       descender = fontMetrics.getWinDescender();
/*      */     } 
/* 1108 */     return new float[] { ascender, descender };
/*      */   }
/*      */   
/*      */   List<int[]> getReversedRanges() {
/* 1112 */     return this.reversedRanges;
/*      */   }
/*      */   
/*      */   List<int[]> initReversedRanges() {
/* 1116 */     if (this.reversedRanges == null) {
/* 1117 */       this.reversedRanges = (List)new ArrayList<>();
/*      */     }
/* 1119 */     return this.reversedRanges;
/*      */   }
/*      */   
/*      */   TextRenderer removeReversedRanges() {
/* 1123 */     this.reversedRanges = null;
/* 1124 */     return this;
/*      */   }
/*      */   
/*      */   private TextRenderer[] splitIgnoreFirstNewLine(int currentTextPos) {
/* 1128 */     if (TextUtil.isCarriageReturnFollowedByLineFeed(this.text, currentTextPos)) {
/* 1129 */       return split(currentTextPos + 2);
/*      */     }
/* 1131 */     return split(currentTextPos + 1);
/*      */   }
/*      */ 
/*      */   
/*      */   private GlyphLine convertToGlyphLine(String text) {
/* 1136 */     return this.font.createGlyphLine(text);
/*      */   }
/*      */   
/*      */   private boolean hasOtfFont() {
/* 1140 */     return (this.font instanceof com.itextpdf.kernel.font.PdfType0Font && this.font.getFontProgram() instanceof com.itextpdf.io.font.TrueTypeFont);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   boolean textContainsSpecialScriptGlyphs(boolean analyzeSpecialScriptsWordBreakPointsOnly) {
/* 1161 */     if (this.specialScriptsWordBreakPoints != null) {
/* 1162 */       return !this.specialScriptsWordBreakPoints.isEmpty();
/*      */     }
/*      */     
/* 1165 */     if (analyzeSpecialScriptsWordBreakPointsOnly) {
/* 1166 */       return false;
/*      */     }
/*      */     
/* 1169 */     for (int i = this.text.start; i < this.text.end; i++) {
/* 1170 */       int unicode = this.text.get(i).getUnicode();
/* 1171 */       if (unicode > -1) {
/* 1172 */         if (codePointIsOfSpecialScript(unicode)) {
/* 1173 */           return true;
/*      */         }
/*      */       } else {
/* 1176 */         char[] chars = this.text.get(i).getChars();
/* 1177 */         if (chars != null) {
/* 1178 */           for (char ch : chars) {
/* 1179 */             if (codePointIsOfSpecialScript(ch)) {
/* 1180 */               return true;
/*      */             }
/*      */           } 
/*      */         }
/*      */       } 
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 1189 */     this.specialScriptsWordBreakPoints = new ArrayList<>();
/*      */     
/* 1191 */     return false;
/*      */   }
/*      */   
/*      */   void setSpecialScriptsWordBreakPoints(List<Integer> specialScriptsWordBreakPoints) {
/* 1195 */     this.specialScriptsWordBreakPoints = specialScriptsWordBreakPoints;
/*      */   }
/*      */   
/*      */   List<Integer> getSpecialScriptsWordBreakPoints() {
/* 1199 */     return this.specialScriptsWordBreakPoints;
/*      */   }
/*      */   
/*      */   void setSpecialScriptFirstNotFittingIndex(int lastFittingIndex) {
/* 1203 */     this.specialScriptFirstNotFittingIndex = lastFittingIndex;
/*      */   }
/*      */ 
/*      */   
/*      */   protected Rectangle getBackgroundArea(Rectangle occupiedAreaWithMargins) {
/* 1208 */     float textRise = getPropertyAsFloat(72).floatValue();
/* 1209 */     return occupiedAreaWithMargins.moveUp(textRise).decreaseHeight(textRise);
/*      */   }
/*      */ 
/*      */   
/*      */   protected Float getFirstYLineRecursively() {
/* 1214 */     return Float.valueOf(getYLine());
/*      */   }
/*      */ 
/*      */   
/*      */   protected Float getLastYLineRecursively() {
/* 1219 */     return Float.valueOf(getYLine());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected int lineLength() {
/* 1228 */     return (this.line.end > 0) ? (this.line.end - this.line.start) : 0;
/*      */   }
/*      */   
/*      */   protected int baseCharactersCount() {
/* 1232 */     int count = 0;
/* 1233 */     for (int i = this.line.start; i < this.line.end; i++) {
/* 1234 */       Glyph glyph = this.line.get(i);
/* 1235 */       if (!glyph.hasPlacement()) {
/* 1236 */         count++;
/*      */       }
/*      */     } 
/* 1239 */     return count;
/*      */   }
/*      */ 
/*      */   
/*      */   public MinMaxWidth getMinMaxWidth() {
/* 1244 */     TextLayoutResult result = (TextLayoutResult)layout(new LayoutContext(new LayoutArea(1, new Rectangle(MinMaxWidthUtils.getInfWidth(), 1000000.0F))));
/* 1245 */     return result.getMinMaxWidth();
/*      */   }
/*      */   
/*      */   protected int getNumberOfSpaces() {
/* 1249 */     if (this.line.end <= 0)
/* 1250 */       return 0; 
/* 1251 */     int spaces = 0;
/* 1252 */     for (int i = this.line.start; i < this.line.end; i++) {
/* 1253 */       Glyph currentGlyph = this.line.get(i);
/* 1254 */       if (currentGlyph.getUnicode() == 32) {
/* 1255 */         spaces++;
/*      */       }
/*      */     } 
/* 1258 */     return spaces;
/*      */   }
/*      */   
/*      */   protected TextRenderer createSplitRenderer() {
/* 1262 */     return (TextRenderer)getNextRenderer();
/*      */   }
/*      */   
/*      */   protected TextRenderer createOverflowRenderer() {
/* 1266 */     return (TextRenderer)getNextRenderer();
/*      */   }
/*      */   
/*      */   protected TextRenderer[] split(int initialOverflowTextPos) {
/* 1270 */     TextRenderer splitRenderer = createSplitRenderer();
/* 1271 */     GlyphLine newText = new GlyphLine(this.text);
/* 1272 */     newText.start = this.text.start;
/* 1273 */     newText.end = initialOverflowTextPos;
/* 1274 */     splitRenderer.setProcessedGlyphLineAndFont(newText, this.font);
/* 1275 */     splitRenderer.line = this.line;
/* 1276 */     splitRenderer.occupiedArea = this.occupiedArea.clone();
/* 1277 */     splitRenderer.parent = this.parent;
/* 1278 */     splitRenderer.yLineOffset = this.yLineOffset;
/* 1279 */     splitRenderer.otfFeaturesApplied = this.otfFeaturesApplied;
/* 1280 */     splitRenderer.isLastRendererForModelElement = false;
/* 1281 */     splitRenderer.addAllProperties(getOwnProperties());
/*      */     
/* 1283 */     TextRenderer overflowRenderer = createOverflowRenderer();
/* 1284 */     newText = new GlyphLine(this.text);
/* 1285 */     newText.start = initialOverflowTextPos;
/* 1286 */     newText.end = this.text.end;
/* 1287 */     overflowRenderer.setProcessedGlyphLineAndFont(newText, this.font);
/* 1288 */     overflowRenderer.otfFeaturesApplied = this.otfFeaturesApplied;
/* 1289 */     overflowRenderer.parent = this.parent;
/* 1290 */     overflowRenderer.addAllProperties(getOwnProperties());
/*      */     
/* 1292 */     if (this.specialScriptsWordBreakPoints != null) {
/* 1293 */       if (this.specialScriptsWordBreakPoints.isEmpty()) {
/* 1294 */         splitRenderer.setSpecialScriptsWordBreakPoints(new ArrayList<>());
/* 1295 */         overflowRenderer.setSpecialScriptsWordBreakPoints(new ArrayList<>());
/* 1296 */       } else if (((Integer)this.specialScriptsWordBreakPoints.get(0)).intValue() == -1) {
/* 1297 */         List<Integer> split = new ArrayList<>(1);
/* 1298 */         split.add(Integer.valueOf(-1));
/* 1299 */         splitRenderer.setSpecialScriptsWordBreakPoints(split);
/*      */         
/* 1301 */         List<Integer> overflow = new ArrayList<>(1);
/* 1302 */         overflow.add(Integer.valueOf(-1));
/* 1303 */         overflowRenderer.setSpecialScriptsWordBreakPoints(overflow);
/*      */       } else {
/* 1305 */         int splitIndex = findPossibleBreaksSplitPosition(this.specialScriptsWordBreakPoints, initialOverflowTextPos, false);
/*      */ 
/*      */         
/* 1308 */         if (splitIndex > -1) {
/* 1309 */           splitRenderer.setSpecialScriptsWordBreakPoints(this.specialScriptsWordBreakPoints
/* 1310 */               .subList(0, splitIndex + 1));
/*      */         } else {
/* 1312 */           List<Integer> split = new ArrayList<>(1);
/* 1313 */           split.add(Integer.valueOf(-1));
/* 1314 */           splitRenderer.setSpecialScriptsWordBreakPoints(split);
/*      */         } 
/*      */         
/* 1317 */         if (splitIndex + 1 < this.specialScriptsWordBreakPoints.size()) {
/* 1318 */           overflowRenderer.setSpecialScriptsWordBreakPoints(this.specialScriptsWordBreakPoints
/* 1319 */               .subList(splitIndex + 1, this.specialScriptsWordBreakPoints.size()));
/*      */         } else {
/* 1321 */           List<Integer> split = new ArrayList<>(1);
/* 1322 */           split.add(Integer.valueOf(-1));
/* 1323 */           overflowRenderer.setSpecialScriptsWordBreakPoints(split);
/*      */         } 
/*      */       } 
/*      */     }
/*      */     
/* 1328 */     return new TextRenderer[] { splitRenderer, overflowRenderer };
/*      */   }
/*      */   
/*      */   protected void drawSingleUnderline(Underline underline, TransparentColor fontStrokeColor, PdfCanvas canvas, float fontSize, float italicAngleTan) {
/* 1332 */     TransparentColor underlineColor = (underline.getColor() != null) ? new TransparentColor(underline.getColor(), underline.getOpacity()) : fontStrokeColor;
/* 1333 */     canvas.saveState();
/*      */     
/* 1335 */     if (underlineColor != null) {
/* 1336 */       canvas.setStrokeColor(underlineColor.getColor());
/* 1337 */       underlineColor.applyStrokeTransparency(canvas);
/*      */     } 
/* 1339 */     canvas.setLineCapStyle(underline.getLineCapStyle());
/* 1340 */     float underlineThickness = underline.getThickness(fontSize);
/* 1341 */     if (underlineThickness != 0.0F) {
/* 1342 */       canvas.setLineWidth(underlineThickness);
/* 1343 */       float yLine = getYLine();
/* 1344 */       float underlineYPosition = underline.getYPosition(fontSize) + yLine;
/* 1345 */       float italicWidthSubstraction = 0.5F * fontSize * italicAngleTan;
/* 1346 */       Rectangle innerAreaBbox = getInnerAreaBBox();
/* 1347 */       canvas.moveTo(innerAreaBbox.getX(), underlineYPosition)
/* 1348 */         .lineTo((innerAreaBbox.getX() + innerAreaBbox.getWidth() - italicWidthSubstraction), underlineYPosition)
/* 1349 */         .stroke();
/*      */     } 
/*      */     
/* 1352 */     canvas.restoreState();
/*      */   }
/*      */   
/*      */   protected float calculateLineWidth() {
/* 1356 */     UnitValue fontSize = getPropertyAsUnitValue(24);
/* 1357 */     if (!fontSize.isPointValue()) {
/* 1358 */       Logger logger = LoggerFactory.getLogger(TextRenderer.class);
/* 1359 */       logger.error(MessageFormatUtil.format("Property {0} in percents is not supported", new Object[] { Integer.valueOf(24) }));
/*      */     } 
/* 1361 */     return getGlyphLineWidth(this.line, fontSize.getValue(), 
/* 1362 */         getPropertyAsFloat(29, Float.valueOf(1.0F)).floatValue(), 
/* 1363 */         getPropertyAsFloat(15), getPropertyAsFloat(78));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected boolean resolveFonts(List<IRenderer> addTo) {
/* 1373 */     Object font = getProperty(20);
/* 1374 */     if (font instanceof PdfFont) {
/* 1375 */       addTo.add(this);
/* 1376 */       return false;
/* 1377 */     }  if (font instanceof String || font instanceof String[]) {
/* 1378 */       if (font instanceof String) {
/*      */         
/* 1380 */         Logger logger = LoggerFactory.getLogger(AbstractRenderer.class);
/* 1381 */         logger.warn("The \"Property.FONT\" property with values of String type is deprecated, use String[] as property value type instead.");
/* 1382 */         List<String> splitFontFamily = FontFamilySplitter.splitFontFamily((String)font);
/* 1383 */         font = splitFontFamily.toArray(new String[splitFontFamily.size()]);
/*      */       } 
/* 1385 */       FontProvider provider = getProperty(91);
/* 1386 */       FontSet fontSet = getProperty(98);
/* 1387 */       if (provider.getFontSet().isEmpty() && (fontSet == null || fontSet.isEmpty())) {
/* 1388 */         throw new IllegalStateException("FontProvider and FontSet are empty. Cannot resolve font family name (see ElementPropertyContainer#setFontFamily) without initialized FontProvider (see RootElement#setFontProvider).");
/*      */       }
/* 1390 */       FontCharacteristics fc = createFontCharacteristics();
/* 1391 */       FontSelectorStrategy strategy = provider.getStrategy(this.strToBeConverted, Arrays.asList((String[])font), fc, fontSet);
/*      */       
/* 1393 */       if (null == this.strToBeConverted || this.strToBeConverted.isEmpty()) {
/* 1394 */         addTo.add(this);
/*      */       } else {
/* 1396 */         while (!strategy.endOfText()) {
/* 1397 */           GlyphLine nextGlyphs = new GlyphLine(strategy.nextGlyphs());
/* 1398 */           PdfFont currentFont = strategy.getCurrentFont();
/* 1399 */           GlyphLine newGlyphs = TextPreprocessingUtil.replaceSpecialWhitespaceGlyphs(nextGlyphs, currentFont);
/* 1400 */           TextRenderer textRenderer = createCopy(newGlyphs, currentFont);
/* 1401 */           addTo.add(textRenderer);
/*      */         } 
/*      */       } 
/* 1404 */       return true;
/*      */     } 
/* 1406 */     throw new IllegalStateException("Invalid FONT property value type.");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   protected void setGlyphLineAndFont(GlyphLine gl, PdfFont font) {
/* 1417 */     setProcessedGlyphLineAndFont(gl, font);
/*      */   }
/*      */   
/*      */   protected void setProcessedGlyphLineAndFont(GlyphLine gl, PdfFont font) {
/* 1421 */     this.text = gl;
/* 1422 */     this.font = font;
/* 1423 */     this.otfFeaturesApplied = false;
/* 1424 */     this.strToBeConverted = null;
/* 1425 */     this.specialScriptsWordBreakPoints = null;
/* 1426 */     setProperty(20, font);
/*      */   }
/*      */   
/*      */   protected TextRenderer createCopy(GlyphLine gl, PdfFont font) {
/* 1430 */     TextRenderer copy = new TextRenderer(this);
/* 1431 */     copy.setProcessedGlyphLineAndFont(gl, font);
/* 1432 */     return copy;
/*      */   }
/*      */   
/*      */   static void updateRangeBasedOnRemovedCharacters(ArrayList<Integer> removedIds, int[] range) {
/* 1436 */     int shift = numberOfElementsLessThan(removedIds, range[0]);
/* 1437 */     range[0] = range[0] - shift;
/* 1438 */     shift = numberOfElementsLessThanOrEqual(removedIds, range[1]);
/* 1439 */     range[1] = range[1] - shift;
/*      */   }
/*      */ 
/*      */   
/*      */   PdfFont resolveFirstPdfFont(String[] font, FontProvider provider, FontCharacteristics fc, FontSet additionalFonts) {
/* 1444 */     FontSelectorStrategy strategy = provider.getStrategy(this.strToBeConverted, Arrays.asList(font), fc, additionalFonts);
/*      */ 
/*      */ 
/*      */     
/* 1448 */     while (!strategy.endOfText()) {
/* 1449 */       List<Glyph> resolvedGlyphs = strategy.nextGlyphs();
/* 1450 */       PdfFont currentFont = strategy.getCurrentFont();
/* 1451 */       for (Glyph glyph : resolvedGlyphs) {
/* 1452 */         if (currentFont.containsGlyph(glyph.getUnicode())) {
/* 1453 */           return currentFont;
/*      */         }
/*      */       } 
/*      */     } 
/* 1457 */     return super.resolveFirstPdfFont(font, provider, fc, additionalFonts);
/*      */   }
/*      */   
/*      */   private static int numberOfElementsLessThan(ArrayList<Integer> numbers, int n) {
/* 1461 */     int x = Collections.binarySearch((List)numbers, Integer.valueOf(n));
/* 1462 */     if (x >= 0) {
/* 1463 */       return x;
/*      */     }
/* 1465 */     return -x - 1;
/*      */   }
/*      */ 
/*      */   
/*      */   private static int numberOfElementsLessThanOrEqual(ArrayList<Integer> numbers, int n) {
/* 1470 */     int x = Collections.binarySearch((List)numbers, Integer.valueOf(n));
/* 1471 */     if (x >= 0) {
/* 1472 */       return x + 1;
/*      */     }
/* 1474 */     return -x - 1;
/*      */   }
/*      */ 
/*      */   
/*      */   private static boolean noPrint(Glyph g) {
/* 1479 */     if (!g.hasValidUnicode()) {
/* 1480 */       return false;
/*      */     }
/* 1482 */     int c = g.getUnicode();
/* 1483 */     return TextUtil.isNonPrintable(c);
/*      */   }
/*      */   
/*      */   private static boolean glyphBelongsToNonBreakingHyphenRelatedChunk(GlyphLine text, int ind) {
/* 1487 */     return (TextUtil.isNonBreakingHyphen(text.get(ind)) || (ind + 1 < text.end && TextUtil.isNonBreakingHyphen(text.get(ind + 1))) || (ind - 1 >= text.start && TextUtil.isNonBreakingHyphen(text.get(ind - 1))));
/*      */   }
/*      */   
/*      */   private float getCharWidth(Glyph g, float fontSize, Float hScale, Float characterSpacing, Float wordSpacing) {
/* 1491 */     if (hScale == null) {
/* 1492 */       hScale = Float.valueOf(1.0F);
/*      */     }
/* 1494 */     float resultWidth = g.getWidth() * fontSize * hScale.floatValue();
/* 1495 */     if (characterSpacing != null) {
/* 1496 */       resultWidth += characterSpacing.floatValue() * hScale.floatValue() * 1000.0F;
/*      */     }
/* 1498 */     if (wordSpacing != null && g.getUnicode() == 32) {
/* 1499 */       resultWidth += wordSpacing.floatValue() * hScale.floatValue() * 1000.0F;
/*      */     }
/* 1501 */     return resultWidth;
/*      */   }
/*      */   
/*      */   private float scaleXAdvance(float xAdvance, float fontSize, Float hScale) {
/* 1505 */     return xAdvance * fontSize * hScale.floatValue();
/*      */   }
/*      */   
/*      */   private float getGlyphLineWidth(GlyphLine glyphLine, float fontSize, float hScale, Float characterSpacing, Float wordSpacing) {
/* 1509 */     float width = 0.0F;
/* 1510 */     for (int i = glyphLine.start; i < glyphLine.end; i++) {
/* 1511 */       if (!noPrint(glyphLine.get(i))) {
/* 1512 */         float charWidth = getCharWidth(glyphLine.get(i), fontSize, Float.valueOf(hScale), characterSpacing, wordSpacing);
/* 1513 */         width += charWidth;
/* 1514 */         float xAdvance = (i != glyphLine.start) ? scaleXAdvance(glyphLine.get(i - 1).getXAdvance(), fontSize, Float.valueOf(hScale)) : 0.0F;
/* 1515 */         width += xAdvance;
/*      */       } 
/*      */     } 
/* 1518 */     return width / 1000.0F;
/*      */   }
/*      */   
/*      */   private int[] getWordBoundsForHyphenation(GlyphLine text, int leftTextPos, int rightTextPos, int wordMiddleCharPos) {
/* 1522 */     while (wordMiddleCharPos >= leftTextPos && !isGlyphPartOfWordForHyphenation(text.get(wordMiddleCharPos)) && 
/* 1523 */       !TextUtil.isUni0020(text.get(wordMiddleCharPos))) {
/* 1524 */       wordMiddleCharPos--;
/*      */     }
/* 1526 */     if (wordMiddleCharPos >= leftTextPos) {
/* 1527 */       int left = wordMiddleCharPos;
/* 1528 */       while (left >= leftTextPos && isGlyphPartOfWordForHyphenation(text.get(left))) {
/* 1529 */         left--;
/*      */       }
/* 1531 */       int right = wordMiddleCharPos;
/* 1532 */       while (right < rightTextPos && isGlyphPartOfWordForHyphenation(text.get(right))) {
/* 1533 */         right++;
/*      */       }
/* 1535 */       return new int[] { left + 1, right };
/*      */     } 
/* 1537 */     return null;
/*      */   }
/*      */ 
/*      */   
/*      */   private boolean isGlyphPartOfWordForHyphenation(Glyph g) {
/* 1542 */     return (Character.isLetter((char)g.getUnicode()) || 173 == g
/*      */ 
/*      */       
/* 1545 */       .getUnicode());
/*      */   }
/*      */   
/*      */   private void updateFontAndText() {
/* 1549 */     if (this.strToBeConverted != null) {
/*      */       PdfFont newFont;
/*      */       try {
/* 1552 */         newFont = getPropertyAsFont(20);
/* 1553 */       } catch (ClassCastException cce) {
/* 1554 */         newFont = resolveFirstPdfFont();
/* 1555 */         if (!this.strToBeConverted.isEmpty()) {
/* 1556 */           Logger logger = LoggerFactory.getLogger(TextRenderer.class);
/* 1557 */           logger.error("The \"Property.FONT\" property must be a PdfFont object in this context.");
/*      */         } 
/*      */       } 
/* 1560 */       GlyphLine newText = newFont.createGlyphLine(this.strToBeConverted);
/* 1561 */       newText = TextPreprocessingUtil.replaceSpecialWhitespaceGlyphs(newText, newFont);
/* 1562 */       setProcessedGlyphLineAndFont(newText, newFont);
/*      */     } 
/*      */   }
/*      */   
/*      */   private void saveWordBreakIfNotYetSaved(Glyph wordBreak) {
/* 1567 */     if (this.savedWordBreakAtLineEnding == null) {
/* 1568 */       if (TextUtil.isNewLine(wordBreak))
/*      */       {
/*      */         
/* 1571 */         wordBreak = this.font.getGlyph(32);
/*      */       }
/*      */       
/* 1574 */       this.savedWordBreakAtLineEnding = new GlyphLine(Collections.singletonList(wordBreak));
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static int findPossibleBreaksSplitPosition(List<Integer> list, int textStartBasedInitialOverflowTextPos, boolean amongPresentOnly) {
/* 1585 */     int low = 0;
/* 1586 */     int high = list.size() - 1;
/*      */     
/* 1588 */     while (low <= high) {
/* 1589 */       int middle = low + high >>> 1;
/* 1590 */       if (((Integer)list.get(middle)).compareTo(Integer.valueOf(textStartBasedInitialOverflowTextPos)) < 0) {
/* 1591 */         low = middle + 1; continue;
/* 1592 */       }  if (((Integer)list.get(middle)).compareTo(Integer.valueOf(textStartBasedInitialOverflowTextPos)) > 0) {
/* 1593 */         high = middle - 1; continue;
/*      */       } 
/* 1595 */       return middle;
/*      */     } 
/*      */     
/* 1598 */     if (!amongPresentOnly && low > 0) {
/* 1599 */       return low - 1;
/*      */     }
/* 1601 */     return -1;
/*      */   }
/*      */   
/*      */   static boolean codePointIsOfSpecialScript(int codePoint) {
/* 1605 */     Character.UnicodeScript glyphScript = Character.UnicodeScript.of(codePoint);
/* 1606 */     return (Character.UnicodeScript.THAI == glyphScript || Character.UnicodeScript.KHMER == glyphScript || Character.UnicodeScript.LAO == glyphScript || Character.UnicodeScript.MYANMAR == glyphScript);
/*      */   }
/*      */ 
/*      */   
/*      */   private static class ReversedCharsIterator
/*      */     implements Iterator<GlyphLine.GlyphLinePart>
/*      */   {
/*      */     private List<Integer> outStart;
/*      */     private List<Integer> outEnd;
/*      */     private List<Boolean> reversed;
/* 1616 */     private int currentInd = 0;
/*      */     private boolean useReversed;
/*      */     
/*      */     public ReversedCharsIterator(List<int[]> reversedRange, GlyphLine line) {
/* 1620 */       this.outStart = new ArrayList<>();
/* 1621 */       this.outEnd = new ArrayList<>();
/* 1622 */       this.reversed = new ArrayList<>();
/* 1623 */       if (reversedRange != null) {
/* 1624 */         if (((int[])reversedRange.get(0))[0] > 0) {
/* 1625 */           this.outStart.add(Integer.valueOf(0));
/* 1626 */           this.outEnd.add(Integer.valueOf(((int[])reversedRange.get(0))[0]));
/* 1627 */           this.reversed.add(Boolean.valueOf(false));
/*      */         } 
/* 1629 */         for (int i = 0; i < reversedRange.size(); i++) {
/* 1630 */           int[] range = reversedRange.get(i);
/* 1631 */           this.outStart.add(Integer.valueOf(range[0]));
/* 1632 */           this.outEnd.add(Integer.valueOf(range[1] + 1));
/* 1633 */           this.reversed.add(Boolean.valueOf(true));
/* 1634 */           if (i != reversedRange.size() - 1) {
/* 1635 */             this.outStart.add(Integer.valueOf(range[1] + 1));
/* 1636 */             this.outEnd.add(Integer.valueOf(((int[])reversedRange.get(i + 1))[0]));
/* 1637 */             this.reversed.add(Boolean.valueOf(false));
/*      */           } 
/*      */         } 
/* 1640 */         int lastIndex = ((int[])reversedRange.get(reversedRange.size() - 1))[1];
/* 1641 */         if (lastIndex < line.size() - 1) {
/* 1642 */           this.outStart.add(Integer.valueOf(lastIndex + 1));
/* 1643 */           this.outEnd.add(Integer.valueOf(line.size()));
/* 1644 */           this.reversed.add(Boolean.valueOf(false));
/*      */         } 
/*      */       } else {
/* 1647 */         this.outStart.add(Integer.valueOf(line.start));
/* 1648 */         this.outEnd.add(Integer.valueOf(line.end));
/* 1649 */         this.reversed.add(Boolean.valueOf(false));
/*      */       } 
/*      */     }
/*      */     
/*      */     public ReversedCharsIterator setUseReversed(boolean useReversed) {
/* 1654 */       this.useReversed = useReversed;
/* 1655 */       return this;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean hasNext() {
/* 1660 */       return (this.currentInd < this.outStart.size());
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public GlyphLine.GlyphLinePart next() {
/* 1666 */       GlyphLine.GlyphLinePart part = (new GlyphLine.GlyphLinePart(((Integer)this.outStart.get(this.currentInd)).intValue(), ((Integer)this.outEnd.get(this.currentInd)).intValue())).setReversed((this.useReversed && ((Boolean)this.reversed.get(this.currentInd)).booleanValue()));
/* 1667 */       this.currentInd++;
/* 1668 */       return part;
/*      */     }
/*      */ 
/*      */     
/*      */     public void remove() {
/* 1673 */       throw new IllegalStateException("Operation not supported");
/*      */     }
/*      */   }
/*      */   
/*      */   private static class ScriptRange
/*      */   {
/*      */     Character.UnicodeScript script;
/*      */     int rangeEnd;
/*      */     
/*      */     ScriptRange(Character.UnicodeScript script, int rangeEnd) {
/* 1683 */       this.script = script;
/* 1684 */       this.rangeEnd = rangeEnd;
/*      */     }
/*      */   }
/*      */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/layout/renderer/TextRenderer.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */