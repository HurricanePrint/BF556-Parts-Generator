/*      */ package com.itextpdf.kernel.pdf.canvas;
/*      */ 
/*      */ import com.itextpdf.io.font.otf.ActualTextIterator;
/*      */ import com.itextpdf.io.font.otf.Glyph;
/*      */ import com.itextpdf.io.font.otf.GlyphLine;
/*      */ import com.itextpdf.io.image.ImageData;
/*      */ import com.itextpdf.io.image.ImageType;
/*      */ import com.itextpdf.io.source.ByteUtils;
/*      */ import com.itextpdf.io.util.StreamUtil;
/*      */ import com.itextpdf.kernel.PdfException;
/*      */ import com.itextpdf.kernel.colors.Color;
/*      */ import com.itextpdf.kernel.colors.PatternColor;
/*      */ import com.itextpdf.kernel.font.PdfFont;
/*      */ import com.itextpdf.kernel.geom.AffineTransform;
/*      */ import com.itextpdf.kernel.geom.Rectangle;
/*      */ import com.itextpdf.kernel.geom.Vector;
/*      */ import com.itextpdf.kernel.pdf.IsoKey;
/*      */ import com.itextpdf.kernel.pdf.PdfArray;
/*      */ import com.itextpdf.kernel.pdf.PdfDictionary;
/*      */ import com.itextpdf.kernel.pdf.PdfDocument;
/*      */ import com.itextpdf.kernel.pdf.PdfName;
/*      */ import com.itextpdf.kernel.pdf.PdfNumber;
/*      */ import com.itextpdf.kernel.pdf.PdfObject;
/*      */ import com.itextpdf.kernel.pdf.PdfOutputStream;
/*      */ import com.itextpdf.kernel.pdf.PdfPage;
/*      */ import com.itextpdf.kernel.pdf.PdfResources;
/*      */ import com.itextpdf.kernel.pdf.PdfStream;
/*      */ import com.itextpdf.kernel.pdf.PdfString;
/*      */ import com.itextpdf.kernel.pdf.PdfVersion;
/*      */ import com.itextpdf.kernel.pdf.canvas.wmf.WmfImageHelper;
/*      */ import com.itextpdf.kernel.pdf.colorspace.PdfColorSpace;
/*      */ import com.itextpdf.kernel.pdf.colorspace.PdfDeviceCs;
/*      */ import com.itextpdf.kernel.pdf.colorspace.PdfPattern;
/*      */ import com.itextpdf.kernel.pdf.colorspace.PdfShading;
/*      */ import com.itextpdf.kernel.pdf.colorspace.PdfSpecialCs;
/*      */ import com.itextpdf.kernel.pdf.extgstate.PdfExtGState;
/*      */ import com.itextpdf.kernel.pdf.layer.IPdfOCG;
/*      */ import com.itextpdf.kernel.pdf.layer.PdfLayer;
/*      */ import com.itextpdf.kernel.pdf.tagutils.TagReference;
/*      */ import com.itextpdf.kernel.pdf.xobject.PdfFormXObject;
/*      */ import com.itextpdf.kernel.pdf.xobject.PdfImageXObject;
/*      */ import com.itextpdf.kernel.pdf.xobject.PdfXObject;
/*      */ import java.io.OutputStream;
/*      */ import java.io.Serializable;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Iterator;
/*      */ import java.util.List;
/*      */ import java.util.Map;
/*      */ import java.util.Stack;
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
/*      */ public class PdfCanvas
/*      */   implements Serializable
/*      */ {
/*  107 */   private static final byte[] B = ByteUtils.getIsoBytes("B\n");
/*  108 */   private static final byte[] b = ByteUtils.getIsoBytes("b\n");
/*  109 */   private static final byte[] BDC = ByteUtils.getIsoBytes("BDC\n");
/*  110 */   private static final byte[] BI = ByteUtils.getIsoBytes("BI\n");
/*  111 */   private static final byte[] BMC = ByteUtils.getIsoBytes("BMC\n");
/*  112 */   private static final byte[] BStar = ByteUtils.getIsoBytes("B*\n");
/*  113 */   private static final byte[] bStar = ByteUtils.getIsoBytes("b*\n");
/*  114 */   private static final byte[] BT = ByteUtils.getIsoBytes("BT\n");
/*  115 */   private static final byte[] c = ByteUtils.getIsoBytes("c\n");
/*  116 */   private static final byte[] cm = ByteUtils.getIsoBytes("cm\n");
/*  117 */   private static final byte[] cs = ByteUtils.getIsoBytes("cs\n");
/*  118 */   private static final byte[] CS = ByteUtils.getIsoBytes("CS\n");
/*  119 */   private static final byte[] d = ByteUtils.getIsoBytes("d\n");
/*  120 */   private static final byte[] Do = ByteUtils.getIsoBytes("Do\n");
/*  121 */   private static final byte[] EI = ByteUtils.getIsoBytes("EI\n");
/*  122 */   private static final byte[] EMC = ByteUtils.getIsoBytes("EMC\n");
/*  123 */   private static final byte[] ET = ByteUtils.getIsoBytes("ET\n");
/*  124 */   private static final byte[] f = ByteUtils.getIsoBytes("f\n");
/*  125 */   private static final byte[] fStar = ByteUtils.getIsoBytes("f*\n");
/*  126 */   private static final byte[] G = ByteUtils.getIsoBytes("G\n");
/*  127 */   private static final byte[] g = ByteUtils.getIsoBytes("g\n");
/*  128 */   private static final byte[] gs = ByteUtils.getIsoBytes("gs\n");
/*  129 */   private static final byte[] h = ByteUtils.getIsoBytes("h\n");
/*  130 */   private static final byte[] i = ByteUtils.getIsoBytes("i\n");
/*  131 */   private static final byte[] ID = ByteUtils.getIsoBytes("ID\n");
/*  132 */   private static final byte[] j = ByteUtils.getIsoBytes("j\n");
/*  133 */   private static final byte[] J = ByteUtils.getIsoBytes("J\n");
/*  134 */   private static final byte[] K = ByteUtils.getIsoBytes("K\n");
/*  135 */   private static final byte[] k = ByteUtils.getIsoBytes("k\n");
/*  136 */   private static final byte[] l = ByteUtils.getIsoBytes("l\n");
/*  137 */   private static final byte[] m = ByteUtils.getIsoBytes("m\n");
/*  138 */   private static final byte[] M = ByteUtils.getIsoBytes("M\n");
/*  139 */   private static final byte[] n = ByteUtils.getIsoBytes("n\n");
/*  140 */   private static final byte[] q = ByteUtils.getIsoBytes("q\n");
/*  141 */   private static final byte[] Q = ByteUtils.getIsoBytes("Q\n");
/*  142 */   private static final byte[] re = ByteUtils.getIsoBytes("re\n");
/*  143 */   private static final byte[] rg = ByteUtils.getIsoBytes("rg\n");
/*  144 */   private static final byte[] RG = ByteUtils.getIsoBytes("RG\n");
/*  145 */   private static final byte[] ri = ByteUtils.getIsoBytes("ri\n");
/*  146 */   private static final byte[] S = ByteUtils.getIsoBytes("S\n");
/*  147 */   private static final byte[] s = ByteUtils.getIsoBytes("s\n");
/*  148 */   private static final byte[] scn = ByteUtils.getIsoBytes("scn\n");
/*  149 */   private static final byte[] SCN = ByteUtils.getIsoBytes("SCN\n");
/*  150 */   private static final byte[] sh = ByteUtils.getIsoBytes("sh\n");
/*  151 */   private static final byte[] Tc = ByteUtils.getIsoBytes("Tc\n");
/*  152 */   private static final byte[] Td = ByteUtils.getIsoBytes("Td\n");
/*  153 */   private static final byte[] TD = ByteUtils.getIsoBytes("TD\n");
/*  154 */   private static final byte[] Tf = ByteUtils.getIsoBytes("Tf\n");
/*  155 */   private static final byte[] TJ = ByteUtils.getIsoBytes("TJ\n");
/*  156 */   private static final byte[] Tj = ByteUtils.getIsoBytes("Tj\n");
/*  157 */   private static final byte[] TL = ByteUtils.getIsoBytes("TL\n");
/*  158 */   private static final byte[] Tm = ByteUtils.getIsoBytes("Tm\n");
/*  159 */   private static final byte[] Tr = ByteUtils.getIsoBytes("Tr\n");
/*  160 */   private static final byte[] Ts = ByteUtils.getIsoBytes("Ts\n");
/*  161 */   private static final byte[] TStar = ByteUtils.getIsoBytes("T*\n");
/*  162 */   private static final byte[] Tw = ByteUtils.getIsoBytes("Tw\n");
/*  163 */   private static final byte[] Tz = ByteUtils.getIsoBytes("Tz\n");
/*  164 */   private static final byte[] v = ByteUtils.getIsoBytes("v\n");
/*  165 */   private static final byte[] W = ByteUtils.getIsoBytes("W\n");
/*  166 */   private static final byte[] w = ByteUtils.getIsoBytes("w\n");
/*  167 */   private static final byte[] WStar = ByteUtils.getIsoBytes("W*\n");
/*  168 */   private static final byte[] y = ByteUtils.getIsoBytes("y\n");
/*      */   
/*  170 */   private static final PdfDeviceCs.Gray gray = new PdfDeviceCs.Gray();
/*  171 */   private static final PdfDeviceCs.Rgb rgb = new PdfDeviceCs.Rgb();
/*  172 */   private static final PdfDeviceCs.Cmyk cmyk = new PdfDeviceCs.Cmyk();
/*  173 */   private static final PdfSpecialCs.Pattern pattern = new PdfSpecialCs.Pattern();
/*      */ 
/*      */   
/*      */   private static final long serialVersionUID = -4706222391732334562L;
/*      */ 
/*      */   
/*      */   private static final float IDENTITY_MATRIX_EPS = 1.0E-4F;
/*      */   
/*  181 */   protected Stack<CanvasGraphicsState> gsStack = new Stack<>();
/*      */ 
/*      */ 
/*      */   
/*  185 */   protected CanvasGraphicsState currentGs = new CanvasGraphicsState();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected PdfStream contentStream;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected PdfResources resources;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected PdfDocument document;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected int mcDepth;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected List<Integer> layerDepth;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PdfCanvas(PdfStream contentStream, PdfResources resources, PdfDocument document) {
/*  218 */     this.contentStream = ensureStreamDataIsReadyToBeProcessed(contentStream);
/*  219 */     this.resources = resources;
/*  220 */     this.document = document;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PdfCanvas(PdfPage page) {
/*  229 */     this(page, ((page.getDocument().getReader() != null && page.getDocument().getWriter() != null && page
/*  230 */         .getContentStreamCount() > 0 && page.getLastContentStream().getLength() > 0) || (page
/*  231 */         .getRotation() != 0 && page.isIgnorePageRotationForContent())));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PdfCanvas(PdfPage page, boolean wrapOldContent) {
/*  242 */     this(getPageStream(page), page.getResources(), page.getDocument());
/*  243 */     if (wrapOldContent) {
/*      */       
/*  245 */       page.newContentStreamBefore().getOutputStream().writeBytes(ByteUtils.getIsoBytes("q\n"));
/*  246 */       this.contentStream.getOutputStream().writeBytes(ByteUtils.getIsoBytes("Q\n"));
/*      */     } 
/*  248 */     if (page.getRotation() != 0 && page.isIgnorePageRotationForContent() && (wrapOldContent || 
/*  249 */       !page.isPageRotationInverseMatrixWritten())) {
/*  250 */       applyRotation(page);
/*  251 */       page.setPageRotationInverseMatrixWritten();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PdfCanvas(PdfFormXObject xObj, PdfDocument document) {
/*  262 */     this((PdfStream)xObj.getPdfObject(), xObj.getResources(), document);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PdfCanvas(PdfDocument doc, int pageNum) {
/*  272 */     this(doc.getPage(pageNum));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PdfResources getResources() {
/*  281 */     return this.resources;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PdfDocument getDocument() {
/*  290 */     return this.document;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void attachContentStream(PdfStream contentStream) {
/*  300 */     this.contentStream = contentStream;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public CanvasGraphicsState getGraphicsState() {
/*  309 */     return this.currentGs;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void release() {
/*  317 */     this.gsStack = null;
/*  318 */     this.currentGs = null;
/*  319 */     this.contentStream = null;
/*  320 */     this.resources = null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PdfCanvas saveState() {
/*  329 */     this.document.checkIsoConformance(Character.valueOf('q'), IsoKey.CANVAS_STACK);
/*  330 */     this.gsStack.push(this.currentGs);
/*  331 */     this.currentGs = new CanvasGraphicsState(this.currentGs);
/*  332 */     this.contentStream.getOutputStream().writeBytes(q);
/*  333 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PdfCanvas restoreState() {
/*  342 */     this.document.checkIsoConformance(Character.valueOf('Q'), IsoKey.CANVAS_STACK);
/*  343 */     if (this.gsStack.isEmpty()) {
/*  344 */       throw new PdfException("Unbalanced save restore state operators.");
/*      */     }
/*  346 */     this.currentGs = this.gsStack.pop();
/*  347 */     this.contentStream.getOutputStream().writeBytes(Q);
/*  348 */     return this;
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
/*      */   public PdfCanvas concatMatrix(double a, double b, double c, double d, double e, double f) {
/*  365 */     this.currentGs.updateCtm((float)a, (float)b, (float)c, (float)d, (float)e, (float)f);
/*  366 */     ((PdfOutputStream)((PdfOutputStream)((PdfOutputStream)((PdfOutputStream)((PdfOutputStream)((PdfOutputStream)((PdfOutputStream)((PdfOutputStream)((PdfOutputStream)((PdfOutputStream)((PdfOutputStream)((PdfOutputStream)this.contentStream.getOutputStream().writeDouble(a)).writeSpace())
/*  367 */       .writeDouble(b)).writeSpace())
/*  368 */       .writeDouble(c)).writeSpace())
/*  369 */       .writeDouble(d)).writeSpace())
/*  370 */       .writeDouble(e)).writeSpace())
/*  371 */       .writeDouble(f)).writeSpace()).writeBytes(cm);
/*  372 */     return this;
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
/*      */   public PdfCanvas concatMatrix(PdfArray array) {
/*  385 */     if (array.size() != 6)
/*      */     {
/*  387 */       return this;
/*      */     }
/*  389 */     for (int i = 0; i < array.size(); i++) {
/*  390 */       if (!array.get(i).isNumber()) {
/*  391 */         return this;
/*      */       }
/*      */     } 
/*  394 */     return concatMatrix(array.getAsNumber(0).doubleValue(), array.getAsNumber(1).doubleValue(), array.getAsNumber(2).doubleValue(), array.getAsNumber(3).doubleValue(), array.getAsNumber(4).doubleValue(), array.getAsNumber(5).doubleValue());
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
/*      */   public PdfCanvas concatMatrix(AffineTransform transform) {
/*  406 */     float[] matrix = new float[6];
/*  407 */     transform.getMatrix(matrix);
/*  408 */     return concatMatrix(matrix[0], matrix[1], matrix[2], matrix[3], matrix[4], matrix[5]);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PdfCanvas beginText() {
/*  417 */     this.contentStream.getOutputStream().writeBytes(BT);
/*  418 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PdfCanvas endText() {
/*  427 */     this.contentStream.getOutputStream().writeBytes(ET);
/*  428 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PdfCanvas beginVariableText() {
/*  437 */     return beginMarkedContent(PdfName.Tx);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PdfCanvas endVariableText() {
/*  446 */     return endMarkedContent();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PdfCanvas setFontAndSize(PdfFont font, float size) {
/*  457 */     this.currentGs.setFontSize(size);
/*  458 */     PdfName fontName = this.resources.addFont(this.document, font);
/*  459 */     this.currentGs.setFont(font);
/*  460 */     ((PdfOutputStream)((PdfOutputStream)((PdfOutputStream)this.contentStream.getOutputStream()
/*  461 */       .write((PdfObject)fontName)
/*  462 */       .writeSpace())
/*  463 */       .writeFloat(size)).writeSpace())
/*  464 */       .writeBytes(Tf);
/*  465 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PdfCanvas moveText(double x, double y) {
/*  476 */     ((PdfOutputStream)((PdfOutputStream)((PdfOutputStream)((PdfOutputStream)this.contentStream.getOutputStream()
/*  477 */       .writeDouble(x))
/*  478 */       .writeSpace())
/*  479 */       .writeDouble(y)).writeSpace())
/*  480 */       .writeBytes(Td);
/*  481 */     return this;
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
/*      */   public PdfCanvas setLeading(float leading) {
/*  495 */     this.currentGs.setLeading(leading);
/*  496 */     ((PdfOutputStream)((PdfOutputStream)this.contentStream.getOutputStream()
/*  497 */       .writeFloat(leading))
/*  498 */       .writeSpace())
/*  499 */       .writeBytes(TL);
/*      */     
/*  501 */     return this;
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
/*      */   public PdfCanvas moveTextWithLeading(float x, float y) {
/*  515 */     this.currentGs.setLeading(-y);
/*  516 */     ((PdfOutputStream)((PdfOutputStream)((PdfOutputStream)((PdfOutputStream)this.contentStream.getOutputStream()
/*  517 */       .writeFloat(x))
/*  518 */       .writeSpace())
/*  519 */       .writeFloat(y))
/*  520 */       .writeSpace())
/*  521 */       .writeBytes(TD);
/*  522 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PdfCanvas newlineText() {
/*  531 */     this.contentStream.getOutputStream()
/*  532 */       .writeBytes(TStar);
/*  533 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PdfCanvas newlineShowText(String text) {
/*  543 */     showTextInt(text);
/*  544 */     ((PdfOutputStream)this.contentStream.getOutputStream()
/*  545 */       .writeByte(39))
/*  546 */       .writeNewLine();
/*  547 */     return this;
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
/*      */   public PdfCanvas newlineShowText(float wordSpacing, float charSpacing, String text) {
/*  559 */     ((PdfOutputStream)((PdfOutputStream)this.contentStream.getOutputStream()
/*  560 */       .writeFloat(wordSpacing))
/*  561 */       .writeSpace())
/*  562 */       .writeFloat(charSpacing);
/*  563 */     showTextInt(text);
/*  564 */     ((PdfOutputStream)this.contentStream.getOutputStream()
/*  565 */       .writeByte(34))
/*  566 */       .writeNewLine();
/*      */ 
/*      */     
/*  569 */     this.currentGs.setCharSpacing(charSpacing);
/*  570 */     this.currentGs.setWordSpacing(wordSpacing);
/*  571 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PdfCanvas setTextRenderingMode(int textRenderingMode) {
/*  581 */     this.currentGs.setTextRenderingMode(textRenderingMode);
/*  582 */     ((PdfOutputStream)((PdfOutputStream)this.contentStream.getOutputStream()
/*  583 */       .writeInteger(textRenderingMode)).writeSpace())
/*  584 */       .writeBytes(Tr);
/*  585 */     return this;
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
/*      */   public PdfCanvas setTextRise(float textRise) {
/*  598 */     this.currentGs.setTextRise(textRise);
/*  599 */     ((PdfOutputStream)((PdfOutputStream)this.contentStream.getOutputStream()
/*  600 */       .writeFloat(textRise)).writeSpace())
/*  601 */       .writeBytes(Ts);
/*  602 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PdfCanvas setWordSpacing(float wordSpacing) {
/*  612 */     this.currentGs.setWordSpacing(wordSpacing);
/*  613 */     ((PdfOutputStream)((PdfOutputStream)this.contentStream.getOutputStream()
/*  614 */       .writeFloat(wordSpacing)).writeSpace())
/*  615 */       .writeBytes(Tw);
/*  616 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PdfCanvas setCharacterSpacing(float charSpacing) {
/*  626 */     this.currentGs.setCharSpacing(charSpacing);
/*  627 */     ((PdfOutputStream)((PdfOutputStream)this.contentStream.getOutputStream()
/*  628 */       .writeFloat(charSpacing)).writeSpace())
/*  629 */       .writeBytes(Tc);
/*  630 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PdfCanvas setHorizontalScaling(float scale) {
/*  640 */     this.currentGs.setHorizontalScaling(scale);
/*  641 */     ((PdfOutputStream)((PdfOutputStream)this.contentStream.getOutputStream()
/*  642 */       .writeFloat(scale))
/*  643 */       .writeSpace())
/*  644 */       .writeBytes(Tz);
/*  645 */     return this;
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
/*      */   public PdfCanvas setTextMatrix(float a, float b, float c, float d, float x, float y) {
/*  660 */     ((PdfOutputStream)((PdfOutputStream)((PdfOutputStream)((PdfOutputStream)((PdfOutputStream)((PdfOutputStream)((PdfOutputStream)((PdfOutputStream)((PdfOutputStream)((PdfOutputStream)((PdfOutputStream)((PdfOutputStream)this.contentStream.getOutputStream()
/*  661 */       .writeFloat(a))
/*  662 */       .writeSpace())
/*  663 */       .writeFloat(b))
/*  664 */       .writeSpace())
/*  665 */       .writeFloat(c))
/*  666 */       .writeSpace())
/*  667 */       .writeFloat(d))
/*  668 */       .writeSpace())
/*  669 */       .writeFloat(x))
/*  670 */       .writeSpace())
/*  671 */       .writeFloat(y)).writeSpace())
/*  672 */       .writeBytes(Tm);
/*  673 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PdfCanvas setTextMatrix(AffineTransform transform) {
/*  683 */     float[] matrix = new float[6];
/*  684 */     transform.getMatrix(matrix);
/*  685 */     return setTextMatrix(matrix[0], matrix[1], matrix[2], matrix[3], matrix[4], matrix[5]);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PdfCanvas setTextMatrix(float x, float y) {
/*  696 */     return setTextMatrix(1.0F, 0.0F, 0.0F, 1.0F, x, y);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PdfCanvas showText(String text) {
/*  706 */     showTextInt(text);
/*  707 */     this.contentStream.getOutputStream().writeBytes(Tj);
/*  708 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PdfCanvas showText(GlyphLine text) {
/*  718 */     return showText(text, (Iterator<GlyphLine.GlyphLinePart>)new ActualTextIterator(text));
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
/*      */   public PdfCanvas showText(GlyphLine text, Iterator<GlyphLine.GlyphLinePart> iterator) {
/*  730 */     this.document.checkIsoConformance(this.currentGs, IsoKey.FONT_GLYPHS, null, this.contentStream);
/*      */     PdfFont font;
/*  732 */     if ((font = this.currentGs.getFont()) == null) {
/*  733 */       throw new PdfException("Font and size must be set before writing any text.", this.currentGs);
/*      */     }
/*  735 */     float fontSize = this.currentGs.getFontSize() / 1000.0F;
/*  736 */     float charSpacing = this.currentGs.getCharSpacing();
/*  737 */     float scaling = this.currentGs.getHorizontalScaling() / 100.0F;
/*  738 */     List<GlyphLine.GlyphLinePart> glyphLineParts = iteratorToList(iterator);
/*  739 */     for (int partIndex = 0; partIndex < glyphLineParts.size(); partIndex++) {
/*  740 */       GlyphLine.GlyphLinePart glyphLinePart = glyphLineParts.get(partIndex);
/*  741 */       if (glyphLinePart.actualText != null) {
/*  742 */         PdfDictionary properties = new PdfDictionary();
/*  743 */         properties.put(PdfName.ActualText, (PdfObject)(new PdfString(glyphLinePart.actualText, "UnicodeBig")).setHexWriting(true));
/*  744 */         beginMarkedContent(PdfName.Span, properties);
/*  745 */       } else if (glyphLinePart.reversed) {
/*  746 */         beginMarkedContent(PdfName.ReversedChars);
/*      */       } 
/*  748 */       int sub = glyphLinePart.start;
/*  749 */       for (int i = glyphLinePart.start; i < glyphLinePart.end; i++) {
/*  750 */         Glyph glyph = text.get(i);
/*  751 */         if (glyph.hasOffsets()) {
/*  752 */           if (i - 1 - sub >= 0) {
/*  753 */             font.writeText(text, sub, i - 1, this.contentStream.getOutputStream());
/*  754 */             this.contentStream.getOutputStream().writeBytes(Tj);
/*  755 */             ((PdfOutputStream)((PdfOutputStream)((PdfOutputStream)((PdfOutputStream)this.contentStream.getOutputStream()
/*  756 */               .writeFloat(getSubrangeWidth(text, sub, i - 1), true))
/*  757 */               .writeSpace())
/*  758 */               .writeFloat(0.0F))
/*  759 */               .writeSpace())
/*  760 */               .writeBytes(Td);
/*      */           } 
/*  762 */           float xPlacement = Float.NaN;
/*  763 */           float yPlacement = Float.NaN;
/*  764 */           if (glyph.hasPlacement()) {
/*      */ 
/*      */             
/*  767 */             float xPlacementAddition = 0.0F;
/*  768 */             int currentGlyphIndex = i;
/*  769 */             Glyph currentGlyph = text.get(i);
/*      */             
/*  771 */             while (currentGlyph != null && currentGlyph.getAnchorDelta() != 0) {
/*  772 */               xPlacementAddition += currentGlyph.getXPlacement();
/*  773 */               if (currentGlyph.getAnchorDelta() == 0) {
/*      */                 break;
/*      */               }
/*  776 */               currentGlyphIndex += currentGlyph.getAnchorDelta();
/*  777 */               currentGlyph = text.get(currentGlyphIndex);
/*      */             } 
/*      */             
/*  780 */             xPlacement = -getSubrangeWidth(text, currentGlyphIndex, i) + xPlacementAddition * fontSize * scaling;
/*      */ 
/*      */ 
/*      */             
/*  784 */             float yPlacementAddition = 0.0F;
/*  785 */             currentGlyphIndex = i;
/*  786 */             currentGlyph = text.get(i);
/*  787 */             while (currentGlyph != null && currentGlyph.getYPlacement() != 0) {
/*  788 */               yPlacementAddition += currentGlyph.getYPlacement();
/*  789 */               if (currentGlyph.getAnchorDelta() == 0) {
/*      */                 break;
/*      */               }
/*  792 */               currentGlyphIndex += currentGlyph.getAnchorDelta();
/*  793 */               currentGlyph = text.get(currentGlyphIndex);
/*      */             } 
/*      */             
/*  796 */             yPlacement = -getSubrangeYDelta(text, currentGlyphIndex, i) + yPlacementAddition * fontSize;
/*      */ 
/*      */             
/*  799 */             ((PdfOutputStream)((PdfOutputStream)((PdfOutputStream)((PdfOutputStream)this.contentStream.getOutputStream()
/*  800 */               .writeFloat(xPlacement, true))
/*  801 */               .writeSpace())
/*  802 */               .writeFloat(yPlacement, true))
/*  803 */               .writeSpace())
/*  804 */               .writeBytes(Td);
/*      */           } 
/*  806 */           font.writeText(text, i, i, this.contentStream.getOutputStream());
/*  807 */           this.contentStream.getOutputStream().writeBytes(Tj);
/*  808 */           if (!Float.isNaN(xPlacement)) {
/*  809 */             ((PdfOutputStream)((PdfOutputStream)((PdfOutputStream)((PdfOutputStream)this.contentStream.getOutputStream()
/*  810 */               .writeFloat(-xPlacement, true))
/*  811 */               .writeSpace())
/*  812 */               .writeFloat(-yPlacement, true))
/*  813 */               .writeSpace())
/*  814 */               .writeBytes(Td);
/*      */           }
/*      */           
/*  817 */           if (glyph.hasAdvance()) {
/*  818 */             ((PdfOutputStream)((PdfOutputStream)((PdfOutputStream)((PdfOutputStream)this.contentStream.getOutputStream()
/*      */               
/*  820 */               .writeFloat((((glyph.hasPlacement() ? 0 : glyph.getWidth()) + glyph.getXAdvance()) * fontSize + charSpacing + getWordSpacingAddition(glyph)) * scaling, true))
/*  821 */               .writeSpace())
/*  822 */               .writeFloat(glyph.getYAdvance() * fontSize, true))
/*  823 */               .writeSpace())
/*  824 */               .writeBytes(Td);
/*      */           }
/*  826 */           sub = i + 1;
/*      */         } 
/*      */       } 
/*  829 */       if (glyphLinePart.end - sub > 0) {
/*  830 */         font.writeText(text, sub, glyphLinePart.end - 1, this.contentStream.getOutputStream());
/*  831 */         this.contentStream.getOutputStream().writeBytes(Tj);
/*      */       } 
/*  833 */       if (glyphLinePart.actualText != null) {
/*  834 */         endMarkedContent();
/*  835 */       } else if (glyphLinePart.reversed) {
/*  836 */         endMarkedContent();
/*      */       } 
/*  838 */       if (glyphLinePart.end > sub && partIndex + 1 < glyphLineParts.size()) {
/*  839 */         ((PdfOutputStream)((PdfOutputStream)((PdfOutputStream)((PdfOutputStream)this.contentStream.getOutputStream()
/*  840 */           .writeFloat(getSubrangeWidth(text, sub, glyphLinePart.end - 1), true))
/*  841 */           .writeSpace())
/*  842 */           .writeFloat(0.0F))
/*  843 */           .writeSpace())
/*  844 */           .writeBytes(Td);
/*      */       }
/*      */     } 
/*  847 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private float getSubrangeWidth(GlyphLine text, int from, int to) {
/*  856 */     float fontSize = this.currentGs.getFontSize() / 1000.0F;
/*  857 */     float charSpacing = this.currentGs.getCharSpacing();
/*  858 */     float scaling = this.currentGs.getHorizontalScaling() / 100.0F;
/*  859 */     float width = 0.0F;
/*  860 */     for (int iter = from; iter <= to; iter++) {
/*  861 */       Glyph glyph = text.get(iter);
/*  862 */       if (!glyph.hasPlacement()) {
/*  863 */         width += (glyph.getWidth() * fontSize + charSpacing + getWordSpacingAddition(glyph)) * scaling;
/*      */       }
/*      */       
/*  866 */       if (iter > from) {
/*  867 */         width += text.get(iter - 1).getXAdvance() * fontSize * scaling;
/*      */       }
/*      */     } 
/*      */     
/*  871 */     return width;
/*      */   }
/*      */   
/*      */   private float getSubrangeYDelta(GlyphLine text, int from, int to) {
/*  875 */     float fontSize = this.currentGs.getFontSize() / 1000.0F;
/*  876 */     float yDelta = 0.0F;
/*  877 */     for (int iter = from; iter < to; iter++) {
/*  878 */       yDelta += text.get(iter).getYAdvance() * fontSize;
/*      */     }
/*  880 */     return yDelta;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private float getWordSpacingAddition(Glyph glyph) {
/*  887 */     return (!(this.currentGs.getFont() instanceof com.itextpdf.kernel.font.PdfType0Font) && glyph.hasValidUnicode() && glyph.getCode() == 32) ? this.currentGs.getWordSpacing() : 0.0F;
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
/*      */   public PdfCanvas showText(PdfArray textArray) {
/*  901 */     this.document.checkIsoConformance(this.currentGs, IsoKey.FONT_GLYPHS, null, this.contentStream);
/*  902 */     if (this.currentGs.getFont() == null)
/*  903 */       throw new PdfException("Font and size must be set before writing any text.", this.currentGs); 
/*  904 */     this.contentStream.getOutputStream().writeBytes(ByteUtils.getIsoBytes("["));
/*  905 */     for (PdfObject obj : textArray) {
/*  906 */       if (obj.isString()) {
/*  907 */         StreamUtil.writeEscapedString((OutputStream)this.contentStream.getOutputStream(), ((PdfString)obj).getValueBytes()); continue;
/*  908 */       }  if (obj.isNumber()) {
/*  909 */         this.contentStream.getOutputStream().writeFloat(((PdfNumber)obj).floatValue());
/*      */       }
/*      */     } 
/*  912 */     this.contentStream.getOutputStream().writeBytes(ByteUtils.getIsoBytes("]"));
/*  913 */     this.contentStream.getOutputStream().writeBytes(TJ);
/*  914 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PdfCanvas moveTo(double x, double y) {
/*  925 */     ((PdfOutputStream)((PdfOutputStream)((PdfOutputStream)((PdfOutputStream)this.contentStream.getOutputStream()
/*  926 */       .writeDouble(x))
/*  927 */       .writeSpace())
/*  928 */       .writeDouble(y)).writeSpace())
/*  929 */       .writeBytes(m);
/*  930 */     return this;
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
/*      */   public PdfCanvas lineTo(double x, double y) {
/*  942 */     ((PdfOutputStream)((PdfOutputStream)((PdfOutputStream)((PdfOutputStream)this.contentStream.getOutputStream()
/*  943 */       .writeDouble(x))
/*  944 */       .writeSpace())
/*  945 */       .writeDouble(y)).writeSpace())
/*  946 */       .writeBytes(l);
/*  947 */     return this;
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
/*      */   public PdfCanvas curveTo(double x1, double y1, double x2, double y2, double x3, double y3) {
/*  962 */     ((PdfOutputStream)((PdfOutputStream)((PdfOutputStream)((PdfOutputStream)((PdfOutputStream)((PdfOutputStream)((PdfOutputStream)((PdfOutputStream)((PdfOutputStream)((PdfOutputStream)((PdfOutputStream)((PdfOutputStream)this.contentStream.getOutputStream()
/*  963 */       .writeDouble(x1))
/*  964 */       .writeSpace())
/*  965 */       .writeDouble(y1))
/*  966 */       .writeSpace())
/*  967 */       .writeDouble(x2))
/*  968 */       .writeSpace())
/*  969 */       .writeDouble(y2))
/*  970 */       .writeSpace())
/*  971 */       .writeDouble(x3))
/*  972 */       .writeSpace())
/*  973 */       .writeDouble(y3))
/*  974 */       .writeSpace())
/*  975 */       .writeBytes(c);
/*  976 */     return this;
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
/*      */   public PdfCanvas curveTo(double x2, double y2, double x3, double y3) {
/*  989 */     ((PdfOutputStream)((PdfOutputStream)((PdfOutputStream)((PdfOutputStream)((PdfOutputStream)((PdfOutputStream)((PdfOutputStream)((PdfOutputStream)this.contentStream.getOutputStream()
/*  990 */       .writeDouble(x2))
/*  991 */       .writeSpace())
/*  992 */       .writeDouble(y2))
/*  993 */       .writeSpace())
/*  994 */       .writeDouble(x3))
/*  995 */       .writeSpace())
/*  996 */       .writeDouble(y3)).writeSpace())
/*  997 */       .writeBytes(v);
/*  998 */     return this;
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
/*      */   public PdfCanvas curveFromTo(double x1, double y1, double x3, double y3) {
/* 1011 */     ((PdfOutputStream)((PdfOutputStream)((PdfOutputStream)((PdfOutputStream)((PdfOutputStream)((PdfOutputStream)((PdfOutputStream)((PdfOutputStream)this.contentStream.getOutputStream()
/* 1012 */       .writeDouble(x1))
/* 1013 */       .writeSpace())
/* 1014 */       .writeDouble(y1))
/* 1015 */       .writeSpace())
/* 1016 */       .writeDouble(x3))
/* 1017 */       .writeSpace())
/* 1018 */       .writeDouble(y3)).writeSpace())
/* 1019 */       .writeBytes(y);
/* 1020 */     return this;
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
/*      */   public PdfCanvas arc(double x1, double y1, double x2, double y2, double startAng, double extent) {
/* 1039 */     List<double[]> ar = bezierArc(x1, y1, x2, y2, startAng, extent);
/* 1040 */     if (ar.isEmpty())
/* 1041 */       return this; 
/* 1042 */     double[] pt = ar.get(0);
/* 1043 */     moveTo(pt[0], pt[1]);
/* 1044 */     for (int i = 0; i < ar.size(); i++) {
/* 1045 */       pt = ar.get(i);
/* 1046 */       curveTo(pt[2], pt[3], pt[4], pt[5], pt[6], pt[7]);
/*      */     } 
/*      */     
/* 1049 */     return this;
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
/*      */   public PdfCanvas ellipse(double x1, double y1, double x2, double y2) {
/* 1062 */     return arc(x1, y1, x2, y2, 0.0D, 360.0D);
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static List<double[]> bezierArc(double x1, double y1, double x2, double y2, double startAng, double extent) {
/*      */     double fragAngle;
/*      */     int Nfrag;
/* 1091 */     if (x1 > x2) {
/* 1092 */       double tmp = x1;
/* 1093 */       x1 = x2;
/* 1094 */       x2 = tmp;
/*      */     } 
/* 1096 */     if (y2 > y1) {
/* 1097 */       double tmp = y1;
/* 1098 */       y1 = y2;
/* 1099 */       y2 = tmp;
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 1104 */     if (Math.abs(extent) <= 90.0D) {
/* 1105 */       fragAngle = extent;
/* 1106 */       Nfrag = 1;
/*      */     } else {
/* 1108 */       Nfrag = (int)Math.ceil(Math.abs(extent) / 90.0D);
/* 1109 */       fragAngle = extent / Nfrag;
/*      */     } 
/* 1111 */     double x_cen = (x1 + x2) / 2.0D;
/* 1112 */     double y_cen = (y1 + y2) / 2.0D;
/* 1113 */     double rx = (x2 - x1) / 2.0D;
/* 1114 */     double ry = (y2 - y1) / 2.0D;
/* 1115 */     double halfAng = fragAngle * Math.PI / 360.0D;
/* 1116 */     double kappa = Math.abs(1.3333333333333333D * (1.0D - Math.cos(halfAng)) / Math.sin(halfAng));
/* 1117 */     List<double[]> pointList = (List)new ArrayList<>();
/* 1118 */     for (int iter = 0; iter < Nfrag; iter++) {
/* 1119 */       double theta0 = (startAng + iter * fragAngle) * Math.PI / 180.0D;
/* 1120 */       double theta1 = (startAng + (iter + 1) * fragAngle) * Math.PI / 180.0D;
/* 1121 */       double cos0 = Math.cos(theta0);
/* 1122 */       double cos1 = Math.cos(theta1);
/* 1123 */       double sin0 = Math.sin(theta0);
/* 1124 */       double sin1 = Math.sin(theta1);
/* 1125 */       if (fragAngle > 0.0D) {
/* 1126 */         pointList.add(new double[] { x_cen + rx * cos0, y_cen - ry * sin0, x_cen + rx * (cos0 - kappa * sin0), y_cen - ry * (sin0 + kappa * cos0), x_cen + rx * (cos1 + kappa * sin1), y_cen - ry * (sin1 - kappa * cos1), x_cen + rx * cos1, y_cen - ry * sin1 });
/*      */ 
/*      */ 
/*      */       
/*      */       }
/*      */       else {
/*      */ 
/*      */ 
/*      */         
/* 1135 */         pointList.add(new double[] { x_cen + rx * cos0, y_cen - ry * sin0, x_cen + rx * (cos0 + kappa * sin0), y_cen - ry * (sin0 - kappa * cos0), x_cen + rx * (cos1 - kappa * sin1), y_cen - ry * (sin1 + kappa * cos1), x_cen + rx * cos1, y_cen - ry * sin1 });
/*      */       } 
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1145 */     return pointList;
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
/*      */   public PdfCanvas rectangle(double x, double y, double width, double height) {
/* 1158 */     ((PdfOutputStream)((PdfOutputStream)((PdfOutputStream)((PdfOutputStream)((PdfOutputStream)((PdfOutputStream)((PdfOutputStream)((PdfOutputStream)this.contentStream.getOutputStream().writeDouble(x))
/* 1159 */       .writeSpace())
/* 1160 */       .writeDouble(y))
/* 1161 */       .writeSpace())
/* 1162 */       .writeDouble(width))
/* 1163 */       .writeSpace())
/* 1164 */       .writeDouble(height))
/* 1165 */       .writeSpace())
/* 1166 */       .writeBytes(re);
/* 1167 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PdfCanvas rectangle(Rectangle rectangle) {
/* 1177 */     return rectangle(rectangle.getX(), rectangle.getY(), rectangle.getWidth(), rectangle.getHeight());
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
/*      */   public PdfCanvas roundRectangle(double x, double y, double width, double height, double radius) {
/* 1191 */     if (width < 0.0D) {
/* 1192 */       x += width;
/* 1193 */       width = -width;
/*      */     } 
/* 1195 */     if (height < 0.0D) {
/* 1196 */       y += height;
/* 1197 */       height = -height;
/*      */     } 
/* 1199 */     if (radius < 0.0D)
/* 1200 */       radius = -radius; 
/* 1201 */     double curv = 0.44769999384880066D;
/* 1202 */     moveTo(x + radius, y);
/* 1203 */     lineTo(x + width - radius, y);
/* 1204 */     curveTo(x + width - radius * 0.44769999384880066D, y, x + width, y + radius * 0.44769999384880066D, x + width, y + radius);
/* 1205 */     lineTo(x + width, y + height - radius);
/* 1206 */     curveTo(x + width, y + height - radius * 0.44769999384880066D, x + width - radius * 0.44769999384880066D, y + height, x + width - radius, y + height);
/* 1207 */     lineTo(x + radius, y + height);
/* 1208 */     curveTo(x + radius * 0.44769999384880066D, y + height, x, y + height - radius * 0.44769999384880066D, x, y + height - radius);
/* 1209 */     lineTo(x, y + radius);
/* 1210 */     curveTo(x, y + radius * 0.44769999384880066D, x + radius * 0.44769999384880066D, y, x + radius, y);
/* 1211 */     return this;
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
/*      */   public PdfCanvas circle(double x, double y, double r) {
/* 1223 */     double curve = 0.552299976348877D;
/* 1224 */     moveTo(x + r, y);
/* 1225 */     curveTo(x + r, y + r * 0.552299976348877D, x + r * 0.552299976348877D, y + r, x, y + r);
/* 1226 */     curveTo(x - r * 0.552299976348877D, y + r, x - r, y + r * 0.552299976348877D, x - r, y);
/* 1227 */     curveTo(x - r, y - r * 0.552299976348877D, x - r * 0.552299976348877D, y - r, x, y - r);
/* 1228 */     curveTo(x + r * 0.552299976348877D, y - r, x + r, y - r * 0.552299976348877D, x + r, y);
/* 1229 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PdfCanvas paintShading(PdfShading shading) {
/* 1239 */     PdfName shadingName = this.resources.addShading(shading);
/* 1240 */     ((PdfOutputStream)this.contentStream.getOutputStream().write((PdfObject)shadingName).writeSpace()).writeBytes(sh);
/* 1241 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PdfCanvas closePath() {
/* 1251 */     this.contentStream.getOutputStream().writeBytes(h);
/* 1252 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PdfCanvas closePathEoFillStroke() {
/* 1261 */     this.contentStream.getOutputStream().writeBytes(bStar);
/* 1262 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PdfCanvas closePathFillStroke() {
/* 1271 */     this.contentStream.getOutputStream().writeBytes(b);
/* 1272 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   public PdfCanvas newPath() {
/* 1282 */     return endPath();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PdfCanvas endPath() {
/* 1291 */     this.contentStream.getOutputStream().writeBytes(n);
/* 1292 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PdfCanvas stroke() {
/* 1301 */     this.contentStream.getOutputStream().writeBytes(S);
/* 1302 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PdfCanvas clip() {
/* 1312 */     this.contentStream.getOutputStream().writeBytes(W);
/* 1313 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PdfCanvas eoClip() {
/* 1323 */     this.contentStream.getOutputStream().writeBytes(WStar);
/* 1324 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PdfCanvas closePathStroke() {
/* 1333 */     this.contentStream.getOutputStream().writeBytes(s);
/* 1334 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PdfCanvas fill() {
/* 1343 */     this.contentStream.getOutputStream().writeBytes(f);
/* 1344 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PdfCanvas fillStroke() {
/* 1353 */     this.contentStream.getOutputStream().writeBytes(B);
/* 1354 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PdfCanvas eoFill() {
/* 1363 */     this.contentStream.getOutputStream().writeBytes(fStar);
/* 1364 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PdfCanvas eoFillStroke() {
/* 1373 */     this.contentStream.getOutputStream().writeBytes(BStar);
/* 1374 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PdfCanvas setLineWidth(float lineWidth) {
/* 1384 */     if (this.currentGs.getLineWidth() == lineWidth) {
/* 1385 */       return this;
/*      */     }
/* 1387 */     this.currentGs.setLineWidth(lineWidth);
/* 1388 */     ((PdfOutputStream)((PdfOutputStream)this.contentStream.getOutputStream()
/* 1389 */       .writeFloat(lineWidth)).writeSpace())
/* 1390 */       .writeBytes(w);
/* 1391 */     return this;
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
/*      */   public PdfCanvas setLineCapStyle(int lineCapStyle) {
/* 1403 */     if (this.currentGs.getLineCapStyle() == lineCapStyle)
/* 1404 */       return this; 
/* 1405 */     this.currentGs.setLineCapStyle(lineCapStyle);
/* 1406 */     ((PdfOutputStream)((PdfOutputStream)this.contentStream.getOutputStream()
/* 1407 */       .writeInteger(lineCapStyle)).writeSpace())
/* 1408 */       .writeBytes(J);
/* 1409 */     return this;
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
/*      */   public PdfCanvas setLineJoinStyle(int lineJoinStyle) {
/* 1421 */     if (this.currentGs.getLineJoinStyle() == lineJoinStyle)
/* 1422 */       return this; 
/* 1423 */     this.currentGs.setLineJoinStyle(lineJoinStyle);
/* 1424 */     ((PdfOutputStream)((PdfOutputStream)this.contentStream.getOutputStream()
/* 1425 */       .writeInteger(lineJoinStyle)).writeSpace())
/* 1426 */       .writeBytes(j);
/* 1427 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PdfCanvas setMiterLimit(float miterLimit) {
/* 1438 */     if (this.currentGs.getMiterLimit() == miterLimit)
/* 1439 */       return this; 
/* 1440 */     this.currentGs.setMiterLimit(miterLimit);
/* 1441 */     ((PdfOutputStream)((PdfOutputStream)this.contentStream.getOutputStream()
/* 1442 */       .writeFloat(miterLimit)).writeSpace())
/* 1443 */       .writeBytes(M);
/* 1444 */     return this;
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
/*      */   public PdfCanvas setLineDash(float phase) {
/* 1459 */     this.currentGs.setDashPattern(getDashPatternArray(phase));
/* 1460 */     ((PdfOutputStream)((PdfOutputStream)((PdfOutputStream)((PdfOutputStream)((PdfOutputStream)this.contentStream.getOutputStream().writeByte(91)).writeByte(93)).writeSpace())
/* 1461 */       .writeFloat(phase)).writeSpace())
/* 1462 */       .writeBytes(d);
/* 1463 */     return this;
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
/*      */   public PdfCanvas setLineDash(float unitsOn, float phase) {
/* 1479 */     this.currentGs.setDashPattern(getDashPatternArray(new float[] { unitsOn }, phase));
/* 1480 */     ((PdfOutputStream)((PdfOutputStream)((PdfOutputStream)((PdfOutputStream)((PdfOutputStream)((PdfOutputStream)this.contentStream.getOutputStream().writeByte(91)).writeFloat(unitsOn)).writeByte(93)).writeSpace())
/* 1481 */       .writeFloat(phase)).writeSpace())
/* 1482 */       .writeBytes(d);
/*      */     
/* 1484 */     return this;
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
/*      */   public PdfCanvas setLineDash(float unitsOn, float unitsOff, float phase) {
/* 1501 */     this.currentGs.setDashPattern(getDashPatternArray(new float[] { unitsOn, unitsOff }, phase));
/* 1502 */     ((PdfOutputStream)((PdfOutputStream)((PdfOutputStream)((PdfOutputStream)((PdfOutputStream)((PdfOutputStream)((PdfOutputStream)((PdfOutputStream)this.contentStream.getOutputStream().writeByte(91)).writeFloat(unitsOn)).writeSpace())
/* 1503 */       .writeFloat(unitsOff)).writeByte(93)).writeSpace())
/* 1504 */       .writeFloat(phase)).writeSpace())
/* 1505 */       .writeBytes(d);
/* 1506 */     return this;
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
/*      */   public PdfCanvas setLineDash(float[] array, float phase) {
/* 1522 */     this.currentGs.setDashPattern(getDashPatternArray(array, phase));
/* 1523 */     PdfOutputStream out = this.contentStream.getOutputStream();
/* 1524 */     out.writeByte(91);
/* 1525 */     for (int iter = 0; iter < array.length; iter++) {
/* 1526 */       out.writeFloat(array[iter]);
/* 1527 */       if (iter < array.length - 1)
/* 1528 */         out.writeSpace(); 
/*      */     } 
/* 1530 */     ((PdfOutputStream)((PdfOutputStream)((PdfOutputStream)((PdfOutputStream)out.writeByte(93)).writeSpace()).writeFloat(phase)).writeSpace()).writeBytes(d);
/* 1531 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PdfCanvas setRenderingIntent(PdfName renderingIntent) {
/* 1542 */     this.document.checkIsoConformance(renderingIntent, IsoKey.RENDERING_INTENT);
/* 1543 */     if (renderingIntent.equals(this.currentGs.getRenderingIntent()))
/* 1544 */       return this; 
/* 1545 */     this.currentGs.setRenderingIntent(renderingIntent);
/* 1546 */     ((PdfOutputStream)this.contentStream.getOutputStream()
/* 1547 */       .write((PdfObject)renderingIntent).writeSpace())
/* 1548 */       .writeBytes(ri);
/* 1549 */     return this;
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
/*      */   public PdfCanvas setFlatnessTolerance(float flatnessTolerance) {
/* 1562 */     if (this.currentGs.getFlatnessTolerance() == flatnessTolerance)
/* 1563 */       return this; 
/* 1564 */     this.currentGs.setFlatnessTolerance(flatnessTolerance);
/* 1565 */     ((PdfOutputStream)((PdfOutputStream)this.contentStream.getOutputStream()
/* 1566 */       .writeFloat(flatnessTolerance)).writeSpace())
/* 1567 */       .writeBytes(i);
/* 1568 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PdfCanvas setFillColor(Color color) {
/* 1578 */     return setColor(color, true);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PdfCanvas setStrokeColor(Color color) {
/* 1588 */     return setColor(color, false);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PdfCanvas setColor(Color color, boolean fill) {
/* 1599 */     if (color instanceof PatternColor) {
/* 1600 */       return setColor(color.getColorSpace(), color.getColorValue(), ((PatternColor)color).getPattern(), fill);
/*      */     }
/* 1602 */     return setColor(color.getColorSpace(), color.getColorValue(), fill);
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
/*      */   public PdfCanvas setColor(PdfColorSpace colorSpace, float[] colorValue, boolean fill) {
/* 1615 */     return setColor(colorSpace, colorValue, null, fill);
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
/*      */   public PdfCanvas setColor(PdfColorSpace colorSpace, float[] colorValue, PdfPattern pattern, boolean fill) {
/* 1628 */     boolean setColorValueOnly = false;
/* 1629 */     Color oldColor = fill ? this.currentGs.getFillColor() : this.currentGs.getStrokeColor();
/* 1630 */     Color newColor = createColor(colorSpace, colorValue, pattern);
/* 1631 */     if (oldColor.equals(newColor)) {
/* 1632 */       return this;
/*      */     }
/* 1634 */     if (fill) {
/* 1635 */       this.currentGs.setFillColor(newColor);
/*      */     } else {
/* 1637 */       this.currentGs.setStrokeColor(newColor);
/*      */     } 
/* 1639 */     if (oldColor.getColorSpace().getPdfObject().equals(colorSpace.getPdfObject())) {
/* 1640 */       setColorValueOnly = true;
/*      */     }
/*      */     
/* 1643 */     if (colorSpace instanceof PdfDeviceCs.Gray) {
/* 1644 */       ((PdfOutputStream)((PdfOutputStream)this.contentStream.getOutputStream().writeFloats(colorValue)).writeSpace()).writeBytes(fill ? g : G);
/* 1645 */     } else if (colorSpace instanceof PdfDeviceCs.Rgb) {
/* 1646 */       ((PdfOutputStream)((PdfOutputStream)this.contentStream.getOutputStream().writeFloats(colorValue)).writeSpace()).writeBytes(fill ? rg : RG);
/* 1647 */     } else if (colorSpace instanceof PdfDeviceCs.Cmyk) {
/* 1648 */       ((PdfOutputStream)((PdfOutputStream)this.contentStream.getOutputStream().writeFloats(colorValue)).writeSpace()).writeBytes(fill ? k : K);
/* 1649 */     } else if (colorSpace instanceof PdfSpecialCs.UncoloredTilingPattern) {
/* 1650 */       ((PdfOutputStream)((PdfOutputStream)((PdfOutputStream)((PdfOutputStream)((PdfOutputStream)((PdfOutputStream)this.contentStream.getOutputStream().write((PdfObject)this.resources.addColorSpace(colorSpace)).writeSpace()).writeBytes(fill ? cs : CS))
/* 1651 */         .writeNewLine()).writeFloats(colorValue)).writeSpace()).write((PdfObject)this.resources.addPattern(pattern)).writeSpace()).writeBytes(fill ? scn : SCN);
/* 1652 */     } else if (colorSpace instanceof PdfSpecialCs.Pattern) {
/* 1653 */       ((PdfOutputStream)((PdfOutputStream)((PdfOutputStream)((PdfOutputStream)this.contentStream.getOutputStream().write((PdfObject)PdfName.Pattern).writeSpace()).writeBytes(fill ? cs : CS))
/* 1654 */         .writeNewLine()).write((PdfObject)this.resources.addPattern(pattern)).writeSpace()).writeBytes(fill ? scn : SCN);
/* 1655 */     } else if (colorSpace.getPdfObject().isIndirect()) {
/* 1656 */       if (!setColorValueOnly) {
/* 1657 */         PdfName name = this.resources.addColorSpace(colorSpace);
/* 1658 */         ((PdfOutputStream)this.contentStream.getOutputStream().write((PdfObject)name).writeSpace()).writeBytes(fill ? cs : CS);
/*      */       } 
/* 1660 */       ((PdfOutputStream)((PdfOutputStream)this.contentStream.getOutputStream().writeFloats(colorValue)).writeSpace()).writeBytes(fill ? scn : SCN);
/*      */     } 
/* 1662 */     this.document.checkIsoConformance(this.currentGs, fill ? IsoKey.FILL_COLOR : IsoKey.STROKE_COLOR, this.resources, this.contentStream);
/* 1663 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PdfCanvas setFillColorGray(float g) {
/* 1673 */     return setColor((PdfColorSpace)gray, new float[] { g }, true);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PdfCanvas setStrokeColorGray(float g) {
/* 1683 */     return setColor((PdfColorSpace)gray, new float[] { g }, false);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PdfCanvas resetFillColorGray() {
/* 1692 */     return setFillColorGray(0.0F);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PdfCanvas resetStrokeColorGray() {
/* 1701 */     return setStrokeColorGray(0.0F);
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
/*      */   public PdfCanvas setFillColorRgb(float r, float g, float b) {
/* 1713 */     return setColor((PdfColorSpace)rgb, new float[] { r, g, b }, true);
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
/*      */   public PdfCanvas setStrokeColorRgb(float r, float g, float b) {
/* 1725 */     return setColor((PdfColorSpace)rgb, new float[] { r, g, b }, false);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PdfCanvas setFillColorShading(PdfPattern.Shading shading) {
/* 1735 */     return setColor((PdfColorSpace)pattern, null, (PdfPattern)shading, true);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PdfCanvas setStrokeColorShading(PdfPattern.Shading shading) {
/* 1745 */     return setColor((PdfColorSpace)pattern, null, (PdfPattern)shading, false);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PdfCanvas resetFillColorRgb() {
/* 1754 */     return resetFillColorGray();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PdfCanvas resetStrokeColorRgb() {
/* 1763 */     return resetStrokeColorGray();
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
/*      */   public PdfCanvas setFillColorCmyk(float c, float m, float y, float k) {
/* 1776 */     return setColor((PdfColorSpace)cmyk, new float[] { c, m, y, k }, true);
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
/*      */   public PdfCanvas setStrokeColorCmyk(float c, float m, float y, float k) {
/* 1789 */     return setColor((PdfColorSpace)cmyk, new float[] { c, m, y, k }, false);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PdfCanvas resetFillColorCmyk() {
/* 1798 */     return setFillColorCmyk(0.0F, 0.0F, 0.0F, 1.0F);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PdfCanvas resetStrokeColorCmyk() {
/* 1807 */     return setStrokeColorCmyk(0.0F, 0.0F, 0.0F, 1.0F);
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
/*      */   public PdfCanvas beginLayer(IPdfOCG layer) {
/* 1821 */     if (layer instanceof PdfLayer && ((PdfLayer)layer).getTitle() != null)
/* 1822 */       throw new IllegalArgumentException("Illegal layer argument."); 
/* 1823 */     if (this.layerDepth == null)
/* 1824 */       this.layerDepth = new ArrayList<>(); 
/* 1825 */     if (layer instanceof com.itextpdf.kernel.pdf.layer.PdfLayerMembership) {
/* 1826 */       this.layerDepth.add(Integer.valueOf(1));
/* 1827 */       addToPropertiesAndBeginLayer(layer);
/* 1828 */     } else if (layer instanceof PdfLayer) {
/* 1829 */       int num = 0;
/* 1830 */       PdfLayer la = (PdfLayer)layer;
/* 1831 */       while (la != null) {
/* 1832 */         if (la.getTitle() == null) {
/* 1833 */           addToPropertiesAndBeginLayer((IPdfOCG)la);
/* 1834 */           num++;
/*      */         } 
/* 1836 */         la = la.getParent();
/*      */       } 
/* 1838 */       this.layerDepth.add(Integer.valueOf(num));
/*      */     } else {
/* 1840 */       throw new UnsupportedOperationException("Unsupported type for operand: layer");
/* 1841 */     }  return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PdfCanvas endLayer() {
/*      */     int num;
/* 1851 */     if (this.layerDepth != null && !this.layerDepth.isEmpty()) {
/* 1852 */       num = ((Integer)this.layerDepth.get(this.layerDepth.size() - 1)).intValue();
/* 1853 */       this.layerDepth.remove(this.layerDepth.size() - 1);
/*      */     } else {
/* 1855 */       throw new PdfException("Unbalanced layer operators.");
/*      */     } 
/* 1857 */     while (num-- > 0)
/* 1858 */       ((PdfOutputStream)this.contentStream.getOutputStream().writeBytes(EMC)).writeNewLine(); 
/* 1859 */     return this;
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
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   public PdfXObject addImage(ImageData image, float a, float b, float c, float d, float e, float f) {
/* 1883 */     return addImageWithTransformationMatrix(image, a, b, c, d, e, f);
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
/*      */   public PdfXObject addImageWithTransformationMatrix(ImageData image, float a, float b, float c, float d, float e, float f) {
/* 1903 */     return addImageWithTransformationMatrix(image, a, b, c, d, e, f, false);
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
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   public PdfXObject addImage(ImageData image, float a, float b, float c, float d, float e, float f, boolean asInline) {
/* 1927 */     return addImageWithTransformationMatrix(image, a, b, c, d, e, f, asInline);
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
/*      */   public PdfXObject addImageWithTransformationMatrix(ImageData image, float a, float b, float c, float d, float e, float f, boolean asInline) {
/* 1948 */     if (image.getOriginalType() == ImageType.WMF) {
/* 1949 */       WmfImageHelper wmf = new WmfImageHelper(image);
/* 1950 */       PdfXObject xObject = wmf.createFormXObject(this.document);
/* 1951 */       addXObject(xObject, a, b, c, d, e, f);
/* 1952 */       return xObject;
/*      */     } 
/* 1954 */     PdfImageXObject imageXObject = new PdfImageXObject(image);
/* 1955 */     if (asInline && image.canImageBeInline()) {
/* 1956 */       addInlineImage(imageXObject, a, b, c, d, e, f);
/* 1957 */       return null;
/*      */     } 
/* 1959 */     addImageWithTransformationMatrix((PdfXObject)imageXObject, a, b, c, d, e, f);
/* 1960 */     return (PdfXObject)imageXObject;
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
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   public PdfXObject addImage(ImageData image, Rectangle rect, boolean asInline) {
/* 1984 */     return addImageFittedIntoRectangle(image, rect, asInline);
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
/*      */   public PdfXObject addImageFittedIntoRectangle(ImageData image, Rectangle rect, boolean asInline) {
/* 2004 */     return addImageWithTransformationMatrix(image, rect.getWidth(), 0.0F, 0.0F, rect.getHeight(), rect
/* 2005 */         .getX(), rect.getY(), asInline);
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
/*      */   @Deprecated
/*      */   public PdfXObject addImage(ImageData image, float x, float y, boolean asInline) {
/* 2021 */     return addImageAt(image, x, y, asInline);
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
/*      */   public PdfXObject addImageAt(ImageData image, float x, float y, boolean asInline) {
/* 2034 */     if (image.getOriginalType() == ImageType.WMF) {
/* 2035 */       WmfImageHelper wmf = new WmfImageHelper(image);
/* 2036 */       PdfXObject xObject = wmf.createFormXObject(this.document);
/* 2037 */       addXObject(xObject, image.getWidth(), 0.0F, 0.0F, image.getHeight(), x, y);
/* 2038 */       return xObject;
/*      */     } 
/* 2040 */     PdfImageXObject imageXObject = new PdfImageXObject(image);
/* 2041 */     if (asInline && image.canImageBeInline()) {
/* 2042 */       addInlineImage(imageXObject, image.getWidth(), 0.0F, 0.0F, image.getHeight(), x, y);
/* 2043 */       return null;
/*      */     } 
/* 2045 */     addImageWithTransformationMatrix((PdfXObject)imageXObject, image.getWidth(), 0.0F, 0.0F, image.getHeight(), x, y);
/* 2046 */     return (PdfXObject)imageXObject;
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
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   public PdfXObject addImage(ImageData image, float x, float y, float width, boolean asInline) {
/* 2070 */     if (image.getOriginalType() == ImageType.WMF) {
/* 2071 */       WmfImageHelper wmf = new WmfImageHelper(image);
/*      */       
/* 2073 */       PdfXObject xObject = wmf.createFormXObject(this.document);
/* 2074 */       addImageWithTransformationMatrix(xObject, width, 0.0F, 0.0F, width, x, y);
/* 2075 */       return xObject;
/*      */     } 
/* 2077 */     PdfImageXObject imageXObject = new PdfImageXObject(image);
/* 2078 */     if (asInline && image.canImageBeInline()) {
/* 2079 */       addInlineImage(imageXObject, width, 0.0F, 0.0F, width / image.getWidth() * image.getHeight(), x, y);
/* 2080 */       return null;
/*      */     } 
/* 2082 */     addImageWithTransformationMatrix((PdfXObject)imageXObject, width, 0.0F, 0.0F, width / image.getWidth() * image.getHeight(), x, y);
/* 2083 */     return (PdfXObject)imageXObject;
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
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   public PdfXObject addImage(ImageData image, float x, float y, float height, boolean asInline, boolean dummy) {
/* 2109 */     return addImageWithTransformationMatrix(image, height / image.getHeight() * image.getWidth(), 0.0F, 0.0F, height, x, y, asInline);
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
/*      */   public PdfCanvas addXObjectWithTransformationMatrix(PdfXObject xObject, float a, float b, float c, float d, float e, float f) {
/* 2130 */     if (xObject instanceof PdfFormXObject)
/* 2131 */       return addFormWithTransformationMatrix((PdfFormXObject)xObject, a, b, c, d, e, f, true); 
/* 2132 */     if (xObject instanceof PdfImageXObject) {
/* 2133 */       return addImageWithTransformationMatrix(xObject, a, b, c, d, e, f);
/*      */     }
/* 2135 */     throw new IllegalArgumentException("PdfFormXObject or PdfImageXObject expected.");
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
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   public PdfCanvas addXObject(PdfXObject xObject, float a, float b, float c, float d, float e, float f) {
/* 2159 */     return addXObjectWithTransformationMatrix(xObject, a, b, c, d, e, f);
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
/*      */   public PdfCanvas addXObjectAt(PdfXObject xObject, float x, float y) {
/* 2171 */     if (xObject instanceof PdfFormXObject)
/* 2172 */       return addFormAt((PdfFormXObject)xObject, x, y); 
/* 2173 */     if (xObject instanceof PdfImageXObject) {
/* 2174 */       return addImageAt((PdfImageXObject)xObject, x, y);
/*      */     }
/* 2176 */     throw new IllegalArgumentException("PdfFormXObject or PdfImageXObject expected.");
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
/*      */   @Deprecated
/*      */   public PdfCanvas addXObject(PdfXObject xObject, float x, float y) {
/* 2192 */     if (xObject instanceof PdfFormXObject)
/* 2193 */       return addForm((PdfFormXObject)xObject, x, y); 
/* 2194 */     if (xObject instanceof PdfImageXObject) {
/* 2195 */       return addImageAt((PdfImageXObject)xObject, x, y);
/*      */     }
/* 2197 */     throw new IllegalArgumentException("PdfFormXObject or PdfImageXObject expected.");
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
/*      */   public PdfCanvas addXObjectFittedIntoRectangle(PdfXObject xObject, Rectangle rect) {
/* 2211 */     if (xObject instanceof PdfFormXObject)
/* 2212 */       return addFormFittedIntoRectangle((PdfFormXObject)xObject, rect); 
/* 2213 */     if (xObject instanceof PdfImageXObject) {
/* 2214 */       return addImageFittedIntoRectangle((PdfImageXObject)xObject, rect);
/*      */     }
/* 2216 */     throw new IllegalArgumentException("PdfFormXObject or PdfImageXObject expected.");
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
/*      */   @Deprecated
/*      */   public PdfCanvas addXObject(PdfXObject xObject, Rectangle rect) {
/* 2234 */     if (xObject instanceof PdfFormXObject)
/* 2235 */       return addForm((PdfFormXObject)xObject, rect); 
/* 2236 */     if (xObject instanceof PdfImageXObject) {
/* 2237 */       return addImageFittedIntoRectangle((PdfImageXObject)xObject, rect);
/*      */     }
/* 2239 */     throw new IllegalArgumentException("PdfFormXObject or PdfImageXObject expected.");
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
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   public PdfCanvas addXObject(PdfXObject xObject, float x, float y, float width) {
/* 2263 */     if (xObject instanceof PdfFormXObject)
/* 2264 */       return addForm((PdfFormXObject)xObject, x, y, width); 
/* 2265 */     if (xObject instanceof PdfImageXObject) {
/* 2266 */       Rectangle rect = PdfXObject.calculateProportionallyFitRectangleWithWidth(xObject, x, y, width);
/* 2267 */       return addXObject(xObject, rect);
/*      */     } 
/* 2269 */     throw new IllegalArgumentException("PdfFormXObject or PdfImageXObject expected.");
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
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   public PdfCanvas addXObject(PdfXObject xObject, float x, float y, float height, boolean dummy) {
/* 2295 */     if (xObject instanceof PdfFormXObject)
/* 2296 */       return addForm((PdfFormXObject)xObject, x, y, height, dummy); 
/* 2297 */     if (xObject instanceof PdfImageXObject) {
/* 2298 */       Rectangle rect = PdfXObject.calculateProportionallyFitRectangleWithHeight(xObject, x, y, height);
/* 2299 */       return addXObject(xObject, rect);
/*      */     } 
/* 2301 */     throw new IllegalArgumentException("PdfFormXObject or PdfImageXObject expected.");
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
/*      */   public PdfCanvas addXObject(PdfXObject xObject) {
/* 2316 */     if (xObject instanceof PdfFormXObject)
/* 2317 */       return addFormWithTransformationMatrix((PdfFormXObject)xObject, 1.0F, 0.0F, 0.0F, 1.0F, 0.0F, 0.0F, false); 
/* 2318 */     if (xObject instanceof PdfImageXObject) {
/* 2319 */       return addImageAt((PdfImageXObject)xObject, 0.0F, 0.0F);
/*      */     }
/* 2321 */     throw new IllegalArgumentException("PdfFormXObject or PdfImageXObject expected.");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PdfCanvas setExtGState(PdfExtGState extGState) {
/* 2332 */     if (!extGState.isFlushed())
/* 2333 */       this.currentGs.updateFromExtGState(extGState, this.document); 
/* 2334 */     PdfName name = this.resources.addExtGState(extGState);
/* 2335 */     ((PdfOutputStream)this.contentStream.getOutputStream().write((PdfObject)name).writeSpace()).writeBytes(gs);
/* 2336 */     this.document.checkIsoConformance(this.currentGs, IsoKey.EXTENDED_GRAPHICS_STATE, null, this.contentStream);
/* 2337 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PdfExtGState setExtGState(PdfDictionary extGState) {
/* 2347 */     PdfExtGState egs = new PdfExtGState(extGState);
/* 2348 */     setExtGState(egs);
/* 2349 */     return egs;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PdfCanvas beginMarkedContent(PdfName tag) {
/* 2359 */     return beginMarkedContent(tag, null);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PdfCanvas beginMarkedContent(PdfName tag, PdfDictionary properties) {
/* 2370 */     this.mcDepth++;
/* 2371 */     PdfOutputStream out = (PdfOutputStream)this.contentStream.getOutputStream().write((PdfObject)tag).writeSpace();
/* 2372 */     if (properties == null) {
/* 2373 */       out.writeBytes(BMC);
/* 2374 */     } else if (properties.getIndirectReference() == null) {
/* 2375 */       ((PdfOutputStream)out.write((PdfObject)properties).writeSpace()).writeBytes(BDC);
/*      */     } else {
/* 2377 */       ((PdfOutputStream)out.write((PdfObject)this.resources.addProperties(properties)).writeSpace()).writeBytes(BDC);
/*      */     } 
/* 2379 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PdfCanvas endMarkedContent() {
/* 2388 */     if (--this.mcDepth < 0)
/* 2389 */       throw new PdfException("Unbalanced begin/end marked content operators."); 
/* 2390 */     this.contentStream.getOutputStream().writeBytes(EMC);
/* 2391 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PdfCanvas openTag(CanvasTag tag) {
/* 2401 */     if (tag.getRole() == null)
/* 2402 */       return this; 
/* 2403 */     return beginMarkedContent(tag.getRole(), tag.getProperties());
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
/*      */   public PdfCanvas openTag(TagReference tagReference) {
/* 2417 */     if (tagReference.getRole() == null)
/* 2418 */       return this; 
/* 2419 */     CanvasTag tag = new CanvasTag(tagReference.getRole());
/* 2420 */     tag.setProperties(tagReference.getProperties())
/* 2421 */       .addProperty(PdfName.MCID, (PdfObject)new PdfNumber(tagReference.createNextMcid()));
/* 2422 */     return openTag(tag);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PdfCanvas closeTag() {
/* 2431 */     return endMarkedContent();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PdfCanvas writeLiteral(String s) {
/* 2441 */     this.contentStream.getOutputStream().writeString(s);
/* 2442 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PdfCanvas writeLiteral(char c) {
/* 2452 */     this.contentStream.getOutputStream().writeInteger(c);
/* 2453 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PdfCanvas writeLiteral(float n) {
/* 2463 */     this.contentStream.getOutputStream().writeFloat(n);
/* 2464 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PdfStream getContentStream() {
/* 2474 */     return this.contentStream;
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
/*      */   protected void addInlineImage(PdfImageXObject imageXObject, float a, float b, float c, float d, float e, float f) {
/* 2489 */     this.document.checkIsoConformance(imageXObject.getPdfObject(), IsoKey.INLINE_IMAGE, this.resources, this.contentStream);
/* 2490 */     saveState();
/* 2491 */     concatMatrix(a, b, c, d, e, f);
/* 2492 */     PdfOutputStream os = this.contentStream.getOutputStream();
/* 2493 */     os.writeBytes(BI);
/* 2494 */     byte[] imageBytes = ((PdfStream)imageXObject.getPdfObject()).getBytes(false);
/* 2495 */     for (Map.Entry<PdfName, PdfObject> entry : (Iterable<Map.Entry<PdfName, PdfObject>>)((PdfStream)imageXObject.getPdfObject()).entrySet()) {
/* 2496 */       PdfName key = entry.getKey();
/* 2497 */       if (!PdfName.Type.equals(key) && !PdfName.Subtype.equals(key) && !PdfName.Length.equals(key)) {
/* 2498 */         os.write((PdfObject)entry.getKey()).writeSpace();
/* 2499 */         os.write(entry.getValue()).writeNewLine();
/*      */       } 
/*      */     } 
/* 2502 */     if (this.document.getPdfVersion().compareTo(PdfVersion.PDF_2_0) >= 0) {
/* 2503 */       os.write((PdfObject)PdfName.Length).writeSpace();
/* 2504 */       os.write((PdfObject)new PdfNumber(imageBytes.length)).writeNewLine();
/*      */     } 
/* 2506 */     os.writeBytes(ID);
/* 2507 */     ((PdfOutputStream)((PdfOutputStream)((PdfOutputStream)os.writeBytes(imageBytes)).writeNewLine()).writeBytes(EI)).writeNewLine();
/* 2508 */     restoreState();
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
/*      */   private PdfCanvas addFormWithTransformationMatrix(PdfFormXObject form, float a, float b, float c, float d, float e, float f, boolean writeIdentityMatrix) {
/* 2528 */     saveState();
/* 2529 */     if (writeIdentityMatrix || !isIdentityMatrix(a, b, c, d, e, f)) {
/* 2530 */       concatMatrix(a, b, c, d, e, f);
/*      */     }
/* 2532 */     PdfName name = this.resources.addForm(form);
/* 2533 */     ((PdfOutputStream)this.contentStream.getOutputStream().write((PdfObject)name).writeSpace()).writeBytes(Do);
/* 2534 */     restoreState();
/* 2535 */     return this;
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
/*      */   @Deprecated
/*      */   private PdfCanvas addForm(PdfFormXObject form, float a, float b, float c, float d, float e, float f) {
/* 2554 */     saveState();
/* 2555 */     concatMatrix(a, b, c, d, e, f);
/* 2556 */     PdfName name = this.resources.addForm(form);
/* 2557 */     ((PdfOutputStream)this.contentStream.getOutputStream().write((PdfObject)name).writeSpace()).writeBytes(Do);
/* 2558 */     restoreState();
/* 2559 */     return this;
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
/*      */   private PdfCanvas addFormAt(PdfFormXObject form, float x, float y) {
/* 2571 */     Rectangle bBox = PdfFormXObject.calculateBBoxMultipliedByMatrix(form);
/* 2572 */     Vector bBoxMin = new Vector(bBox.getLeft(), bBox.getBottom(), 1.0F);
/* 2573 */     Vector bBoxMax = new Vector(bBox.getRight(), bBox.getTop(), 1.0F);
/* 2574 */     Vector rectMin = new Vector(x, y, 1.0F);
/*      */     
/* 2576 */     Vector rectMax = new Vector(x + bBoxMax.get(0) - bBoxMin.get(0), y + bBoxMax.get(1) - bBoxMin.get(1), 1.0F);
/*      */     
/* 2578 */     float[] result = calculateTransformationMatrix(rectMin, rectMax, bBoxMin, bBoxMax);
/* 2579 */     return addFormWithTransformationMatrix(form, result[0], result[1], result[2], result[3], result[4], result[5], false);
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
/*      */   @Deprecated
/*      */   private PdfCanvas addForm(PdfFormXObject form, float x, float y) {
/* 2593 */     return addForm(form, 1.0F, 0.0F, 0.0F, 1.0F, x, y);
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
/*      */   @Deprecated
/*      */   private PdfCanvas addForm(PdfFormXObject form, float x, float y, float width) {
/* 2608 */     PdfArray bbox = ((PdfStream)form.getPdfObject()).getAsArray(PdfName.BBox);
/* 2609 */     if (bbox == null)
/* 2610 */       throw new PdfException("PdfFormXObject has invalid BBox."); 
/* 2611 */     float formWidth = Math.abs(bbox.getAsNumber(2).floatValue() - bbox.getAsNumber(0).floatValue());
/* 2612 */     float formHeight = Math.abs(bbox.getAsNumber(3).floatValue() - bbox.getAsNumber(1).floatValue());
/* 2613 */     return addForm(form, width, 0.0F, 0.0F, width / formWidth * formHeight, x, y);
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
/*      */   @Deprecated
/*      */   private PdfCanvas addForm(PdfFormXObject form, float x, float y, float height, boolean dummy) {
/* 2630 */     PdfArray bbox = ((PdfStream)form.getPdfObject()).getAsArray(PdfName.BBox);
/* 2631 */     if (bbox == null)
/* 2632 */       throw new PdfException("PdfFormXObject has invalid BBox."); 
/* 2633 */     float formWidth = Math.abs(bbox.getAsNumber(2).floatValue() - bbox.getAsNumber(0).floatValue());
/* 2634 */     float formHeight = Math.abs(bbox.getAsNumber(3).floatValue() - bbox.getAsNumber(1).floatValue());
/* 2635 */     return addForm(form, height / formHeight * formWidth, 0.0F, 0.0F, height, x, y);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private PdfCanvas addFormFittedIntoRectangle(PdfFormXObject form, Rectangle rect) {
/* 2646 */     Rectangle bBox = PdfFormXObject.calculateBBoxMultipliedByMatrix(form);
/* 2647 */     Vector bBoxMin = new Vector(bBox.getLeft(), bBox.getBottom(), 1.0F);
/* 2648 */     Vector bBoxMax = new Vector(bBox.getRight(), bBox.getTop(), 1.0F);
/* 2649 */     Vector rectMin = new Vector(rect.getLeft(), rect.getBottom(), 1.0F);
/* 2650 */     Vector rectMax = new Vector(rect.getRight(), rect.getTop(), 1.0F);
/*      */     
/* 2652 */     float[] result = calculateTransformationMatrix(rectMin, rectMax, bBoxMin, bBoxMax);
/* 2653 */     return addFormWithTransformationMatrix(form, result[0], result[1], result[2], result[3], result[4], result[5], false);
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
/*      */   private PdfCanvas addForm(PdfFormXObject form, Rectangle rect) {
/* 2666 */     return addForm(form, rect.getWidth(), 0.0F, 0.0F, rect.getHeight(), rect.getX(), rect.getY());
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
/*      */   private PdfCanvas addImageWithTransformationMatrix(PdfXObject xObject, float a, float b, float c, float d, float e, float f) {
/*      */     PdfName name;
/* 2682 */     saveState();
/* 2683 */     concatMatrix(a, b, c, d, e, f);
/*      */     
/* 2685 */     if (xObject instanceof PdfImageXObject) {
/* 2686 */       name = this.resources.addImage((PdfImageXObject)xObject);
/*      */     } else {
/* 2688 */       name = this.resources.addImage((PdfStream)xObject.getPdfObject());
/*      */     } 
/* 2690 */     ((PdfOutputStream)this.contentStream.getOutputStream().write((PdfObject)name).writeSpace()).writeBytes(Do);
/* 2691 */     restoreState();
/* 2692 */     return this;
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
/*      */   private PdfCanvas addImageAt(PdfImageXObject image, float x, float y) {
/* 2704 */     return addImageWithTransformationMatrix((PdfXObject)image, image.getWidth(), 0.0F, 0.0F, image.getHeight(), x, y);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private PdfCanvas addImageFittedIntoRectangle(PdfImageXObject image, Rectangle rect) {
/* 2715 */     return addImageWithTransformationMatrix((PdfXObject)image, rect.getWidth(), 0.0F, 0.0F, rect.getHeight(), rect.getX(), rect.getY());
/*      */   }
/*      */   
/*      */   private PdfStream ensureStreamDataIsReadyToBeProcessed(PdfStream stream) {
/* 2719 */     if (!stream.isFlushed() && (
/* 2720 */       stream.getOutputStream() == null || stream.containsKey(PdfName.Filter))) {
/*      */       try {
/* 2722 */         stream.setData(stream.getBytes());
/* 2723 */       } catch (Exception exception) {}
/*      */     }
/*      */ 
/*      */ 
/*      */     
/* 2728 */     return stream;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void showTextInt(String text) {
/* 2738 */     this.document.checkIsoConformance(this.currentGs, IsoKey.FONT_GLYPHS, null, this.contentStream);
/* 2739 */     if (this.currentGs.getFont() == null)
/* 2740 */       throw new PdfException("Font and size must be set before writing any text.", this.currentGs); 
/* 2741 */     this.currentGs.getFont().writeText(text, this.contentStream.getOutputStream());
/*      */   }
/*      */   
/*      */   private void addToPropertiesAndBeginLayer(IPdfOCG layer) {
/* 2745 */     PdfName name = this.resources.addProperties(layer.getPdfObject());
/* 2746 */     ((PdfOutputStream)((PdfOutputStream)((PdfOutputStream)this.contentStream.getOutputStream().write((PdfObject)PdfName.OC).writeSpace())
/* 2747 */       .write((PdfObject)name).writeSpace()).writeBytes(BDC)).writeNewLine();
/*      */   }
/*      */   
/*      */   private Color createColor(PdfColorSpace colorSpace, float[] colorValue, PdfPattern pattern) {
/* 2751 */     if (colorSpace instanceof PdfSpecialCs.UncoloredTilingPattern)
/* 2752 */       return (Color)new PatternColor((PdfPattern.Tiling)pattern, ((PdfSpecialCs.UncoloredTilingPattern)colorSpace).getUnderlyingColorSpace(), colorValue); 
/* 2753 */     if (colorSpace instanceof PdfSpecialCs.Pattern) {
/* 2754 */       return (Color)new PatternColor(pattern);
/*      */     }
/* 2756 */     return Color.makeColor(colorSpace, colorValue);
/*      */   }
/*      */   
/*      */   private PdfArray getDashPatternArray(float phase) {
/* 2760 */     return getDashPatternArray(null, phase);
/*      */   }
/*      */   
/*      */   private PdfArray getDashPatternArray(float[] dashArray, float phase) {
/* 2764 */     PdfArray dashPatternArray = new PdfArray();
/* 2765 */     PdfArray dArray = new PdfArray();
/* 2766 */     if (dashArray != null) {
/* 2767 */       for (float fl : dashArray) {
/* 2768 */         dArray.add((PdfObject)new PdfNumber(fl));
/*      */       }
/*      */     }
/* 2771 */     dashPatternArray.add((PdfObject)dArray);
/* 2772 */     dashPatternArray.add((PdfObject)new PdfNumber(phase));
/* 2773 */     return dashPatternArray;
/*      */   }
/*      */   
/*      */   private void applyRotation(PdfPage page) {
/* 2777 */     Rectangle rectangle = page.getPageSizeWithRotation();
/* 2778 */     int rotation = page.getRotation();
/* 2779 */     switch (rotation) {
/*      */       case 90:
/* 2781 */         concatMatrix(0.0D, 1.0D, -1.0D, 0.0D, rectangle.getTop(), 0.0D);
/*      */         break;
/*      */       case 180:
/* 2784 */         concatMatrix(-1.0D, 0.0D, 0.0D, -1.0D, rectangle.getRight(), rectangle.getTop());
/*      */         break;
/*      */       case 270:
/* 2787 */         concatMatrix(0.0D, -1.0D, 1.0D, 0.0D, 0.0D, rectangle.getRight());
/*      */         break;
/*      */     } 
/*      */   }
/*      */   
/*      */   private static PdfStream getPageStream(PdfPage page) {
/* 2793 */     PdfStream stream = page.getLastContentStream();
/* 2794 */     return (stream == null || stream.getOutputStream() == null || stream.containsKey(PdfName.Filter)) ? page.newContentStreamAfter() : stream;
/*      */   }
/*      */   
/*      */   private static <T> List<T> iteratorToList(Iterator<T> iterator) {
/* 2798 */     List<T> list = new ArrayList<>();
/* 2799 */     while (iterator.hasNext()) {
/* 2800 */       list.add(iterator.next());
/*      */     }
/* 2802 */     return list;
/*      */   }
/*      */ 
/*      */   
/*      */   private static float[] calculateTransformationMatrix(Vector expectedMin, Vector expectedMax, Vector actualMin, Vector actualMax) {
/* 2807 */     float[] result = new float[6];
/* 2808 */     result[0] = (expectedMin.get(0) - expectedMax.get(0)) / (actualMin.get(0) - actualMax.get(0));
/* 2809 */     result[1] = 0.0F;
/* 2810 */     result[2] = 0.0F;
/* 2811 */     result[3] = (expectedMin.get(1) - expectedMax.get(1)) / (actualMin.get(1) - actualMax.get(1));
/* 2812 */     result[4] = expectedMin.get(0) - actualMin.get(0) * result[0];
/* 2813 */     result[5] = expectedMin.get(1) - actualMin.get(1) * result[3];
/* 2814 */     return result;
/*      */   }
/*      */   
/*      */   private static boolean isIdentityMatrix(float a, float b, float c, float d, float e, float f) {
/* 2818 */     return (Math.abs(1.0F - a) < 1.0E-4F && Math.abs(b) < 1.0E-4F && Math.abs(c) < 1.0E-4F && 
/* 2819 */       Math.abs(1.0F - d) < 1.0E-4F && Math.abs(e) < 1.0E-4F && Math.abs(f) < 1.0E-4F);
/*      */   }
/*      */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/kernel/pdf/canvas/PdfCanvas.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */