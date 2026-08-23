/*     */ package com.itextpdf.kernel.pdf.canvas.parser.data;
/*     */ 
/*     */ import com.itextpdf.io.font.otf.GlyphLine;
/*     */ import com.itextpdf.kernel.colors.Color;
/*     */ import com.itextpdf.kernel.font.PdfFont;
/*     */ import com.itextpdf.kernel.geom.LineSegment;
/*     */ import com.itextpdf.kernel.geom.Matrix;
/*     */ import com.itextpdf.kernel.geom.Vector;
/*     */ import com.itextpdf.kernel.pdf.PdfName;
/*     */ import com.itextpdf.kernel.pdf.PdfString;
/*     */ import com.itextpdf.kernel.pdf.canvas.CanvasGraphicsState;
/*     */ import com.itextpdf.kernel.pdf.canvas.CanvasTag;
/*     */ import java.io.UnsupportedEncodingException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ import java.util.Stack;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class TextRenderInfo
/*     */   extends AbstractRenderInfo
/*     */ {
/*     */   private final PdfString string;
/*  77 */   private String text = null;
/*     */   private final Matrix textToUserSpaceTransformMatrix;
/*     */   private final Matrix textMatrix;
/*  80 */   private float unscaledWidth = Float.NaN;
/*  81 */   private double[] fontMatrix = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final List<CanvasTag> canvasTagHierarchy;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TextRenderInfo(PdfString str, CanvasGraphicsState gs, Matrix textMatrix, Stack<CanvasTag> canvasTagHierarchy) {
/*  97 */     super(gs);
/*  98 */     this.string = str;
/*  99 */     this.textToUserSpaceTransformMatrix = textMatrix.multiply(gs.getCtm());
/* 100 */     this.textMatrix = textMatrix;
/* 101 */     this.canvasTagHierarchy = Collections.unmodifiableList(new ArrayList<>(canvasTagHierarchy));
/* 102 */     this.fontMatrix = gs.getFont().getFontMatrix();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private TextRenderInfo(TextRenderInfo parent, PdfString str, float horizontalOffset) {
/* 113 */     super(parent.gs);
/* 114 */     this.string = str;
/* 115 */     Matrix offsetMatrix = new Matrix(horizontalOffset, 0.0F);
/* 116 */     this.textToUserSpaceTransformMatrix = offsetMatrix.multiply(parent.textToUserSpaceTransformMatrix);
/* 117 */     this.textMatrix = offsetMatrix.multiply(parent.textMatrix);
/* 118 */     this.canvasTagHierarchy = parent.canvasTagHierarchy;
/* 119 */     this.fontMatrix = parent.gs.getFont().getFontMatrix();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getText() {
/* 126 */     checkGraphicsState();
/* 127 */     if (this.text == null) {
/* 128 */       GlyphLine gl = this.gs.getFont().decodeIntoGlyphLine(this.string);
/* 129 */       if (!isReversedChars()) {
/* 130 */         this.text = gl.toUnicodeString(gl.start, gl.end);
/*     */       } else {
/* 132 */         StringBuilder sb = new StringBuilder(gl.end - gl.start);
/* 133 */         for (int i = gl.end - 1; i >= gl.start; i--) {
/* 134 */           sb.append(gl.get(i).getUnicodeChars());
/*     */         }
/* 136 */         this.text = sb.toString();
/*     */       } 
/*     */     } 
/* 139 */     return this.text;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PdfString getPdfString() {
/* 146 */     return this.string;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Matrix getTextMatrix() {
/* 155 */     return this.textMatrix;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean hasMcid(int mcid) {
/* 166 */     return hasMcid(mcid, false);
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
/*     */   public boolean hasMcid(int mcid, boolean checkTheTopmostLevelOnly) {
/* 178 */     if (checkTheTopmostLevelOnly) {
/* 179 */       if (this.canvasTagHierarchy != null) {
/* 180 */         int infoMcid = getMcid();
/* 181 */         return (infoMcid != -1 && infoMcid == mcid);
/*     */       } 
/*     */     } else {
/* 184 */       for (CanvasTag tag : this.canvasTagHierarchy) {
/* 185 */         if (tag.hasMcid() && 
/* 186 */           tag.getMcid() == mcid)
/* 187 */           return true; 
/*     */       } 
/*     */     } 
/* 190 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMcid() {
/* 199 */     for (CanvasTag tag : this.canvasTagHierarchy) {
/* 200 */       if (tag.hasMcid()) {
/* 201 */         return tag.getMcid();
/*     */       }
/*     */     } 
/* 204 */     return -1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public LineSegment getBaseline() {
/* 214 */     checkGraphicsState();
/* 215 */     return getUnscaledBaselineWithOffset(0.0F + this.gs.getTextRise()).transformBy(this.textToUserSpaceTransformMatrix);
/*     */   }
/*     */   
/*     */   public LineSegment getUnscaledBaseline() {
/* 219 */     checkGraphicsState();
/* 220 */     return getUnscaledBaselineWithOffset(0.0F + this.gs.getTextRise());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public LineSegment getAscentLine() {
/* 230 */     checkGraphicsState();
/* 231 */     return getUnscaledBaselineWithOffset(getAscentDescent()[0] + this.gs.getTextRise()).transformBy(this.textToUserSpaceTransformMatrix);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public LineSegment getDescentLine() {
/* 241 */     checkGraphicsState();
/* 242 */     return getUnscaledBaselineWithOffset(getAscentDescent()[1] + this.gs.getTextRise()).transformBy(this.textToUserSpaceTransformMatrix);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PdfFont getFont() {
/* 251 */     checkGraphicsState();
/* 252 */     return this.gs.getFont();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getRise() {
/* 262 */     checkGraphicsState();
/*     */     
/* 264 */     if (this.gs.getTextRise() == 0.0F) return 0.0F;
/*     */     
/* 266 */     return convertHeightFromTextSpaceToUserSpace(this.gs.getTextRise());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List<TextRenderInfo> getCharacterRenderInfos() {
/* 275 */     checkGraphicsState();
/* 276 */     List<TextRenderInfo> rslt = new ArrayList<>(this.string.getValue().length());
/* 277 */     PdfString[] strings = splitString(this.string);
/* 278 */     float totalWidth = 0.0F;
/* 279 */     for (PdfString str : strings) {
/* 280 */       float[] widthAndWordSpacing = getWidthAndWordSpacing(str);
/* 281 */       TextRenderInfo subInfo = new TextRenderInfo(this, str, totalWidth);
/* 282 */       rslt.add(subInfo);
/* 283 */       totalWidth += (widthAndWordSpacing[0] * this.gs.getFontSize() + this.gs.getCharSpacing() + widthAndWordSpacing[1]) * this.gs.getHorizontalScaling() / 100.0F;
/*     */     } 
/* 285 */     for (TextRenderInfo tri : rslt)
/* 286 */       tri.getUnscaledWidth(); 
/* 287 */     return rslt;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getSingleSpaceWidth() {
/* 294 */     return convertWidthFromTextSpaceToUserSpace(getUnscaledFontSpaceWidth());
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
/*     */   public int getTextRenderMode() {
/* 312 */     checkGraphicsState();
/* 313 */     return this.gs.getTextRenderingMode();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Color getFillColor() {
/* 320 */     checkGraphicsState();
/* 321 */     return this.gs.getFillColor();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Color getStrokeColor() {
/* 328 */     checkGraphicsState();
/* 329 */     return this.gs.getStrokeColor();
/*     */   }
/*     */   
/*     */   public float getFontSize() {
/* 333 */     checkGraphicsState();
/* 334 */     return this.gs.getFontSize();
/*     */   }
/*     */   
/*     */   public float getHorizontalScaling() {
/* 338 */     checkGraphicsState();
/* 339 */     return this.gs.getHorizontalScaling();
/*     */   }
/*     */   
/*     */   public float getCharSpacing() {
/* 343 */     checkGraphicsState();
/* 344 */     return this.gs.getCharSpacing();
/*     */   }
/*     */   
/*     */   public float getWordSpacing() {
/* 348 */     checkGraphicsState();
/* 349 */     return this.gs.getWordSpacing();
/*     */   }
/*     */   
/*     */   public float getLeading() {
/* 353 */     checkGraphicsState();
/* 354 */     return this.gs.getLeading();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getActualText() {
/* 363 */     String lastActualText = null;
/* 364 */     for (CanvasTag tag : this.canvasTagHierarchy) {
/* 365 */       lastActualText = tag.getActualText();
/* 366 */       if (lastActualText != null) {
/*     */         break;
/*     */       }
/*     */     } 
/* 370 */     return lastActualText;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getExpansionText() {
/* 379 */     String expansionText = null;
/* 380 */     for (CanvasTag tag : this.canvasTagHierarchy) {
/* 381 */       expansionText = tag.getExpansionText();
/* 382 */       if (expansionText != null) {
/*     */         break;
/*     */       }
/*     */     } 
/* 386 */     return expansionText;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isReversedChars() {
/* 396 */     for (CanvasTag tag : this.canvasTagHierarchy) {
/* 397 */       if (tag != null && 
/* 398 */         PdfName.ReversedChars.equals(tag.getRole())) {
/* 399 */         return true;
/*     */       }
/*     */     } 
/*     */     
/* 403 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List<CanvasTag> getCanvasTagHierarchy() {
/* 412 */     return this.canvasTagHierarchy;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getUnscaledWidth() {
/* 419 */     if (Float.isNaN(this.unscaledWidth))
/* 420 */       this.unscaledWidth = getPdfStringWidth(this.string, false); 
/* 421 */     return this.unscaledWidth;
/*     */   }
/*     */   
/*     */   private LineSegment getUnscaledBaselineWithOffset(float yOffset) {
/* 425 */     checkGraphicsState();
/*     */ 
/*     */     
/* 428 */     String unicodeStr = this.string.toUnicodeString();
/*     */ 
/*     */     
/* 431 */     float correctedUnscaledWidth = getUnscaledWidth() - (this.gs.getCharSpacing() + ((unicodeStr.length() > 0 && unicodeStr.charAt(unicodeStr.length() - 1) == ' ') ? this.gs.getWordSpacing() : 0.0F)) * this.gs.getHorizontalScaling() / 100.0F;
/*     */     
/* 433 */     return new LineSegment(new Vector(0.0F, yOffset, 1.0F), new Vector(correctedUnscaledWidth, yOffset, 1.0F));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private float convertWidthFromTextSpaceToUserSpace(float width) {
/* 441 */     LineSegment textSpace = new LineSegment(new Vector(0.0F, 0.0F, 1.0F), new Vector(width, 0.0F, 1.0F));
/* 442 */     LineSegment userSpace = textSpace.transformBy(this.textToUserSpaceTransformMatrix);
/* 443 */     return userSpace.getLength();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private float convertHeightFromTextSpaceToUserSpace(float height) {
/* 451 */     LineSegment textSpace = new LineSegment(new Vector(0.0F, 0.0F, 1.0F), new Vector(0.0F, height, 1.0F));
/* 452 */     LineSegment userSpace = textSpace.transformBy(this.textToUserSpaceTransformMatrix);
/* 453 */     return userSpace.getLength();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private float getUnscaledFontSpaceWidth() {
/* 462 */     checkGraphicsState();
/* 463 */     char spaceChar = ' ';
/* 464 */     int charWidth = this.gs.getFont().getWidth(spaceChar);
/* 465 */     if (charWidth == 0) {
/* 466 */       charWidth = this.gs.getFont().getFontProgram().getAvgWidth();
/*     */     }
/* 468 */     float w = (float)(charWidth * this.fontMatrix[0]);
/* 469 */     return (w * this.gs.getFontSize() + this.gs.getCharSpacing() + this.gs.getWordSpacing()) * this.gs.getHorizontalScaling() / 100.0F;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private float getPdfStringWidth(PdfString string, boolean singleCharString) {
/* 479 */     checkGraphicsState();
/* 480 */     if (singleCharString) {
/* 481 */       float[] widthAndWordSpacing = getWidthAndWordSpacing(string);
/* 482 */       return 
/* 483 */         (float)((widthAndWordSpacing[0] * this.gs.getFontSize() + this.gs.getCharSpacing() + widthAndWordSpacing[1]) * this.gs.getHorizontalScaling() / 100.0D);
/*     */     } 
/* 485 */     float totalWidth = 0.0F;
/* 486 */     for (PdfString str : splitString(string)) {
/* 487 */       totalWidth += getPdfStringWidth(str, true);
/*     */     }
/* 489 */     return totalWidth;
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
/*     */   private float[] getWidthAndWordSpacing(PdfString string) {
/* 501 */     checkGraphicsState();
/* 502 */     float[] result = new float[2];
/*     */     
/* 504 */     result[0] = (float)(this.gs.getFont().getContentWidth(string) * this.fontMatrix[0]);
/* 505 */     result[1] = " ".equals(string.getValue()) ? this.gs.getWordSpacing() : 0.0F;
/* 506 */     return result;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private int getCharCode(String string) {
/*     */     try {
/* 517 */       byte[] b = string.getBytes("UTF-16BE");
/* 518 */       int value = 0;
/* 519 */       for (int i = 0; i < b.length - 1; i++) {
/* 520 */         value += b[i] & 0xFF;
/* 521 */         value <<= 8;
/*     */       } 
/* 523 */       if (b.length > 0) {
/* 524 */         value += b[b.length - 1] & 0xFF;
/*     */       }
/* 526 */       return value;
/* 527 */     } catch (UnsupportedEncodingException unsupportedEncodingException) {
/*     */       
/* 529 */       return 0;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private PdfString[] splitString(PdfString string) {
/* 539 */     checkGraphicsState();
/* 540 */     PdfFont font = this.gs.getFont();
/* 541 */     if (font instanceof com.itextpdf.kernel.font.PdfType0Font) {
/*     */       
/* 543 */       List<PdfString> list = new ArrayList<>();
/* 544 */       GlyphLine glyphLine = this.gs.getFont().decodeIntoGlyphLine(string);
/* 545 */       for (int j = glyphLine.start; j < glyphLine.end; j++) {
/* 546 */         list.add(new PdfString(this.gs.getFont().convertToBytes(glyphLine.get(j))));
/*     */       }
/* 548 */       return list.<PdfString>toArray(new PdfString[list.size()]);
/*     */     } 
/*     */     
/* 551 */     PdfString[] strings = new PdfString[string.getValue().length()];
/* 552 */     for (int i = 0; i < string.getValue().length(); i++) {
/* 553 */       strings[i] = new PdfString(string.getValue().substring(i, i + 1), string.getEncoding());
/*     */     }
/* 555 */     return strings;
/*     */   }
/*     */ 
/*     */   
/*     */   private float[] getAscentDescent() {
/* 560 */     checkGraphicsState();
/* 561 */     float ascent = this.gs.getFont().getFontProgram().getFontMetrics().getTypoAscender();
/* 562 */     float descent = this.gs.getFont().getFontProgram().getFontMetrics().getTypoDescender();
/*     */ 
/*     */     
/* 565 */     if (descent > 0.0F) {
/* 566 */       descent = -descent;
/*     */     }
/*     */     
/* 569 */     float scale = (ascent - descent < 700.0F) ? (ascent - descent) : 1000.0F;
/* 570 */     descent = descent / scale * this.gs.getFontSize();
/* 571 */     ascent = ascent / scale * this.gs.getFontSize();
/* 572 */     return new float[] { ascent, descent };
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/kernel/pdf/canvas/parser/data/TextRenderInfo.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */