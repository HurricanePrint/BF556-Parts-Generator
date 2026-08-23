/*     */ package com.itextpdf.svg.renderers.impl;
/*     */ 
/*     */ import com.itextpdf.kernel.font.PdfFont;
/*     */ import com.itextpdf.kernel.font.PdfFontFactory;
/*     */ import com.itextpdf.kernel.geom.AffineTransform;
/*     */ import com.itextpdf.kernel.geom.Point;
/*     */ import com.itextpdf.kernel.geom.Rectangle;
/*     */ import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
/*     */ import com.itextpdf.layout.font.FontCharacteristics;
/*     */ import com.itextpdf.layout.font.FontInfo;
/*     */ import com.itextpdf.layout.font.FontProvider;
/*     */ import com.itextpdf.layout.font.FontSet;
/*     */ import com.itextpdf.styledxmlparser.css.util.CssUtils;
/*     */ import com.itextpdf.svg.exceptions.SvgProcessingException;
/*     */ import com.itextpdf.svg.renderers.ISvgNodeRenderer;
/*     */ import com.itextpdf.svg.renderers.SvgDrawContext;
/*     */ import com.itextpdf.svg.utils.SvgCssUtils;
/*     */ import com.itextpdf.svg.utils.SvgTextUtil;
/*     */ import com.itextpdf.svg.utils.TextRectangle;
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
/*     */ public class TextSvgBranchRenderer
/*     */   extends AbstractSvgNodeRenderer
/*     */   implements ISvgTextNodeRenderer, ISvgTextNodeHelper
/*     */ {
/*  80 */   protected static final AffineTransform TEXTFLIP = new AffineTransform(1.0D, 0.0D, 0.0D, -1.0D, 0.0D, 0.0D);
/*     */ 
/*     */ 
/*     */   
/*     */   private static final float DEFAULT_FONT_SIZE = 12.0F;
/*     */ 
/*     */   
/*  87 */   private final List<ISvgTextNodeRenderer> children = new ArrayList<>();
/*     */   
/*     */   protected boolean performRootTransformations;
/*     */   
/*     */   private PdfFont font;
/*     */   
/*     */   private float fontSize;
/*     */   private boolean moveResolved;
/*     */   private float xMove;
/*     */   private float yMove;
/*     */   private boolean posResolved;
/*     */   private float[] xPos;
/*     */   private float[] yPos;
/*     */   private boolean whiteSpaceProcessed = false;
/*     */   
/*     */   public TextSvgBranchRenderer() {
/* 103 */     this.performRootTransformations = true;
/* 104 */     this.moveResolved = false;
/* 105 */     this.posResolved = false;
/*     */   }
/*     */ 
/*     */   
/*     */   public ISvgNodeRenderer createDeepCopy() {
/* 110 */     TextSvgBranchRenderer copy = new TextSvgBranchRenderer();
/* 111 */     deepCopyAttributesAndStyles(copy);
/* 112 */     deepCopyChildren(copy);
/* 113 */     return copy;
/*     */   }
/*     */ 
/*     */   
/*     */   public final void addChild(ISvgTextNodeRenderer child) {
/* 118 */     if (child != null) {
/* 119 */       this.children.add(child);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public final List<ISvgTextNodeRenderer> getChildren() {
/* 125 */     return Collections.unmodifiableList(this.children);
/*     */   }
/*     */ 
/*     */   
/*     */   public float getTextContentLength(float parentFontSize, PdfFont font) {
/* 130 */     return 0.0F;
/*     */   }
/*     */ 
/*     */   
/*     */   public float[] getRelativeTranslation() {
/* 135 */     if (!this.moveResolved) resolveTextMove(); 
/* 136 */     return new float[] { this.xMove, this.yMove };
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean containsRelativeMove() {
/* 141 */     if (!this.moveResolved) resolveTextMove(); 
/* 142 */     boolean isNullMove = (CssUtils.compareFloats(0.0F, this.xMove) && CssUtils.compareFloats(0.0F, this.yMove));
/* 143 */     return !isNullMove;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean containsAbsolutePositionChange() {
/* 148 */     if (!this.posResolved) resolveTextPosition(); 
/* 149 */     return ((this.xPos != null && this.xPos.length > 0) || (this.yPos != null && this.yPos.length > 0));
/*     */   }
/*     */ 
/*     */   
/*     */   public float[][] getAbsolutePositionChanges() {
/* 154 */     if (!this.posResolved) resolveTextPosition(); 
/* 155 */     return new float[][] { this.xPos, this.yPos };
/*     */   }
/*     */   
/*     */   public void markWhiteSpaceProcessed() {
/* 159 */     this.whiteSpaceProcessed = true;
/*     */   }
/*     */ 
/*     */   
/*     */   public TextRectangle getTextRectangle(SvgDrawContext context, Point basePoint) {
/* 164 */     if (this.attributesAndStyles != null) {
/* 165 */       resolveFontSize();
/* 166 */       resolveFont(context);
/* 167 */       double x = 0.0D, y = 0.0D;
/* 168 */       if (getAbsolutePositionChanges()[0] != null) {
/* 169 */         x = getAbsolutePositionChanges()[0][0];
/* 170 */       } else if (basePoint != null) {
/* 171 */         x = basePoint.getX();
/*     */       } 
/* 173 */       if (getAbsolutePositionChanges()[1] != null) {
/* 174 */         y = getAbsolutePositionChanges()[1][0];
/* 175 */       } else if (basePoint != null) {
/* 176 */         y = basePoint.getY();
/*     */       } 
/* 178 */       basePoint = new Point(x, y);
/* 179 */       basePoint.translate(getRelativeTranslation()[0], getRelativeTranslation()[1]);
/* 180 */       Rectangle commonRect = null;
/* 181 */       for (ISvgTextNodeRenderer child : getChildren()) {
/* 182 */         if (child instanceof ISvgTextNodeHelper) {
/*     */           
/* 184 */           TextRectangle rectangle = ((ISvgTextNodeHelper)child).getTextRectangle(context, basePoint);
/* 185 */           basePoint = rectangle.getTextBaseLineRightPoint();
/* 186 */           commonRect = Rectangle.getCommonRectangle(new Rectangle[] { commonRect, (Rectangle)rectangle });
/*     */         } 
/*     */       } 
/* 189 */       if (commonRect != null) {
/* 190 */         return new TextRectangle(commonRect.getX(), commonRect.getY(), commonRect.getWidth(), commonRect
/* 191 */             .getHeight(), (float)basePoint.getY());
/*     */       }
/*     */     } 
/* 194 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   protected Rectangle getObjectBoundingBox(SvgDrawContext context) {
/* 199 */     return (Rectangle)getTextRectangle(context, (Point)null);
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
/*     */   protected void doDraw(SvgDrawContext context) {
/* 211 */     if (getChildren().size() > 0) {
/* 212 */       PdfCanvas currentCanvas = context.getCurrentCanvas();
/* 213 */       if (this.performRootTransformations) {
/* 214 */         AffineTransform rootTf; currentCanvas.beginText();
/*     */ 
/*     */         
/* 217 */         if (containsAbsolutePositionChange()) {
/* 218 */           rootTf = getTextTransform(getAbsolutePositionChanges(), context);
/*     */         } else {
/* 220 */           rootTf = new AffineTransform(TEXTFLIP);
/*     */         } 
/* 222 */         currentCanvas.setTextMatrix(rootTf);
/*     */         
/* 224 */         context.resetTextMove();
/*     */         
/* 226 */         if (containsRelativeMove()) {
/* 227 */           float[] rootMove = getRelativeTranslation();
/* 228 */           context.addTextMove(rootMove[0], -rootMove[1]);
/*     */         } 
/*     */         
/* 231 */         if (!this.whiteSpaceProcessed) {
/* 232 */           SvgTextUtil.processWhiteSpace(this, true);
/*     */         }
/*     */       } 
/* 235 */       applyTextRenderingMode(currentCanvas);
/*     */       
/* 237 */       if (this.attributesAndStyles != null) {
/* 238 */         resolveFontSize();
/* 239 */         resolveFont(context);
/* 240 */         currentCanvas.setFontAndSize(this.font, this.fontSize);
/* 241 */         for (ISvgTextNodeRenderer c : this.children) {
/* 242 */           float childLength = c.getTextContentLength(this.fontSize, this.font);
/* 243 */           if (c.containsAbsolutePositionChange()) {
/*     */             
/* 245 */             float[][] absolutePositions = c.getAbsolutePositionChanges();
/* 246 */             AffineTransform newTransform = getTextTransform(absolutePositions, context);
/*     */             
/* 248 */             context.setLastTextTransform(newTransform);
/*     */             
/* 250 */             currentCanvas.setTextMatrix(newTransform);
/*     */             
/* 252 */             context.resetTextMove();
/*     */           } 
/*     */ 
/*     */           
/* 256 */           float textAnchorCorrection = getTextAnchorAlignmentCorrection(childLength);
/* 257 */           if (!CssUtils.compareFloats(0.0F, textAnchorCorrection)) {
/* 258 */             context.addTextMove(textAnchorCorrection, 0.0F);
/*     */           }
/*     */           
/* 261 */           if (c.containsRelativeMove()) {
/* 262 */             float[] childMove = c.getRelativeTranslation();
/* 263 */             context.addTextMove(childMove[0], -childMove[1]);
/*     */           } 
/* 265 */           currentCanvas.saveState();
/* 266 */           c.draw(context);
/*     */           
/* 268 */           context.addTextMove(childLength, 0.0F);
/* 269 */           currentCanvas.restoreState();
/*     */           
/* 271 */           if (!context.getLastTextTransform().isIdentity()) {
/* 272 */             currentCanvas.setTextMatrix(context.getLastTextTransform());
/*     */           }
/*     */         } 
/*     */         
/* 276 */         if (this.performRootTransformations) {
/* 277 */           currentCanvas.endText();
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private void resolveTextMove() {
/* 284 */     if (this.attributesAndStyles != null) {
/* 285 */       String xRawValue = this.attributesAndStyles.get("dx");
/* 286 */       String yRawValue = this.attributesAndStyles.get("dy");
/*     */       
/* 288 */       List<String> xValuesList = SvgCssUtils.splitValueList(xRawValue);
/* 289 */       List<String> yValuesList = SvgCssUtils.splitValueList(yRawValue);
/*     */       
/* 291 */       this.xMove = 0.0F;
/* 292 */       this.yMove = 0.0F;
/*     */       
/* 294 */       if (!xValuesList.isEmpty()) {
/* 295 */         this.xMove = CssUtils.parseAbsoluteLength(xValuesList.get(0));
/*     */       }
/*     */       
/* 298 */       if (!yValuesList.isEmpty()) {
/* 299 */         this.yMove = CssUtils.parseAbsoluteLength(yValuesList.get(0));
/*     */       }
/* 301 */       this.moveResolved = true;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private FontInfo resolveFontName(String fontFamily, String fontWeight, String fontStyle, FontProvider provider, FontSet tempFonts) {
/* 307 */     boolean isBold = (fontWeight != null && "bold".equalsIgnoreCase(fontWeight));
/* 308 */     boolean isItalic = (fontStyle != null && "italic".equalsIgnoreCase(fontStyle));
/*     */     
/* 310 */     FontCharacteristics fontCharacteristics = new FontCharacteristics();
/* 311 */     List<String> stringArrayList = new ArrayList<>();
/* 312 */     stringArrayList.add(fontFamily);
/* 313 */     fontCharacteristics.setBoldFlag(isBold);
/* 314 */     fontCharacteristics.setItalicFlag(isItalic);
/*     */     
/* 316 */     return provider.getFontSelector(stringArrayList, fontCharacteristics, tempFonts).bestMatch();
/*     */   }
/*     */   
/*     */   void resolveFont(SvgDrawContext context) {
/* 320 */     FontProvider provider = context.getFontProvider();
/* 321 */     FontSet tempFonts = context.getTempFonts();
/* 322 */     this.font = null;
/* 323 */     if (!provider.getFontSet().isEmpty() || (tempFonts != null && !tempFonts.isEmpty())) {
/* 324 */       String fontFamily = this.attributesAndStyles.get("font-family");
/* 325 */       String fontWeight = this.attributesAndStyles.get("font-weight");
/* 326 */       String fontStyle = this.attributesAndStyles.get("font-style");
/*     */       
/* 328 */       fontFamily = (fontFamily != null) ? fontFamily.trim() : "";
/* 329 */       FontInfo fontInfo = resolveFontName(fontFamily, fontWeight, fontStyle, provider, tempFonts);
/*     */       
/* 331 */       this.font = provider.getPdfFont(fontInfo, tempFonts);
/*     */     } 
/* 333 */     if (this.font == null) {
/*     */       
/*     */       try {
/*     */         
/* 337 */         this.font = PdfFontFactory.createFont();
/* 338 */       } catch (IOException e) {
/* 339 */         throw new SvgProcessingException("The font wasn't found.", e);
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   void resolveFontSize() {
/* 346 */     this.fontSize = SvgTextUtil.resolveFontSize(this, 12.0F);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   PdfFont getFont() {
/* 356 */     return this.font;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   float getFontSize() {
/* 366 */     return this.fontSize;
/*     */   }
/*     */   
/*     */   private void resolveTextPosition() {
/* 370 */     if (this.attributesAndStyles != null) {
/* 371 */       String xRawValue = this.attributesAndStyles.get("x");
/* 372 */       String yRawValue = this.attributesAndStyles.get("y");
/*     */       
/* 374 */       this.xPos = getPositionsFromString(xRawValue);
/* 375 */       this.yPos = getPositionsFromString(yRawValue);
/*     */       
/* 377 */       this.posResolved = true;
/*     */     } 
/*     */   }
/*     */   
/*     */   private static float[] getPositionsFromString(String rawValuesString) {
/* 382 */     float[] result = null;
/* 383 */     List<String> valuesList = SvgCssUtils.splitValueList(rawValuesString);
/* 384 */     if (!valuesList.isEmpty()) {
/* 385 */       result = new float[valuesList.size()];
/* 386 */       for (int i = 0; i < valuesList.size(); i++) {
/* 387 */         result[i] = CssUtils.parseAbsoluteLength((String)valuesList.get(i));
/*     */       }
/*     */     } 
/*     */     
/* 391 */     return result;
/*     */   }
/*     */   
/*     */   private static AffineTransform getTextTransform(float[][] absolutePositions, SvgDrawContext context) {
/* 395 */     AffineTransform tf = new AffineTransform();
/*     */     
/* 397 */     if (absolutePositions[0] == null && absolutePositions[1] != null) {
/* 398 */       (new float[1])[0] = context.getTextMove()[0]; absolutePositions[0] = new float[1];
/*     */     } 
/*     */     
/* 401 */     if (absolutePositions[1] == null) {
/* 402 */       (new float[1])[0] = 0.0F; absolutePositions[1] = new float[1];
/*     */     } 
/* 404 */     tf.concatenate(TEXTFLIP);
/* 405 */     tf.concatenate(AffineTransform.getTranslateInstance(absolutePositions[0][0], -absolutePositions[1][0]));
/*     */     
/* 407 */     return tf;
/*     */   }
/*     */ 
/*     */   
/*     */   private void applyTextRenderingMode(PdfCanvas currentCanvas) {
/* 412 */     if (this.doStroke && this.doFill) {
/* 413 */       currentCanvas.setTextRenderingMode(2);
/*     */     }
/* 415 */     else if (this.doStroke) {
/* 416 */       currentCanvas.setTextRenderingMode(1);
/*     */     } else {
/* 418 */       currentCanvas.setTextRenderingMode(0);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void deepCopyChildren(TextSvgBranchRenderer deepCopy) {
/* 424 */     for (ISvgTextNodeRenderer child : this.children) {
/* 425 */       ISvgTextNodeRenderer newChild = (ISvgTextNodeRenderer)child.createDeepCopy();
/* 426 */       child.setParent(deepCopy);
/* 427 */       deepCopy.addChild(newChild);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private float getTextAnchorAlignmentCorrection(float childContentLength) {
/* 434 */     float textAnchorXCorrection = 0.0F;
/* 435 */     if (this.attributesAndStyles != null && this.attributesAndStyles.containsKey("text-anchor")) {
/* 436 */       String textAnchorValue = getAttribute("text-anchor");
/*     */       
/* 438 */       if ("middle".equals(textAnchorValue) && 
/* 439 */         this.xPos != null && this.xPos.length > 0) {
/* 440 */         textAnchorXCorrection -= childContentLength / 2.0F;
/*     */       }
/*     */ 
/*     */       
/* 444 */       if ("end".equals(textAnchorValue) && 
/* 445 */         this.xPos != null && this.xPos.length > 0) {
/* 446 */         textAnchorXCorrection -= childContentLength;
/*     */       }
/*     */     } 
/*     */     
/* 450 */     return textAnchorXCorrection;
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/svg/renderers/impl/TextSvgBranchRenderer.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */