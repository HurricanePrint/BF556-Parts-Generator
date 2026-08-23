/*      */ package com.itextpdf.kernel.pdf.canvas.parser;
/*      */ 
/*      */ import com.itextpdf.io.source.PdfTokenizer;
/*      */ import com.itextpdf.io.source.RandomAccessFileOrArray;
/*      */ import com.itextpdf.io.source.RandomAccessSourceFactory;
/*      */ import com.itextpdf.io.util.MessageFormatUtil;
/*      */ import com.itextpdf.kernel.PdfException;
/*      */ import com.itextpdf.kernel.colors.CalGray;
/*      */ import com.itextpdf.kernel.colors.CalRgb;
/*      */ import com.itextpdf.kernel.colors.Color;
/*      */ import com.itextpdf.kernel.colors.DeviceCmyk;
/*      */ import com.itextpdf.kernel.colors.DeviceGray;
/*      */ import com.itextpdf.kernel.colors.DeviceN;
/*      */ import com.itextpdf.kernel.colors.DeviceRgb;
/*      */ import com.itextpdf.kernel.colors.IccBased;
/*      */ import com.itextpdf.kernel.colors.Indexed;
/*      */ import com.itextpdf.kernel.colors.Lab;
/*      */ import com.itextpdf.kernel.colors.PatternColor;
/*      */ import com.itextpdf.kernel.colors.Separation;
/*      */ import com.itextpdf.kernel.font.PdfFont;
/*      */ import com.itextpdf.kernel.font.PdfFontFactory;
/*      */ import com.itextpdf.kernel.geom.Matrix;
/*      */ import com.itextpdf.kernel.geom.Path;
/*      */ import com.itextpdf.kernel.pdf.PdfArray;
/*      */ import com.itextpdf.kernel.pdf.PdfDictionary;
/*      */ import com.itextpdf.kernel.pdf.PdfIndirectReference;
/*      */ import com.itextpdf.kernel.pdf.PdfLiteral;
/*      */ import com.itextpdf.kernel.pdf.PdfName;
/*      */ import com.itextpdf.kernel.pdf.PdfNumber;
/*      */ import com.itextpdf.kernel.pdf.PdfObject;
/*      */ import com.itextpdf.kernel.pdf.PdfPage;
/*      */ import com.itextpdf.kernel.pdf.PdfResources;
/*      */ import com.itextpdf.kernel.pdf.PdfStream;
/*      */ import com.itextpdf.kernel.pdf.PdfString;
/*      */ import com.itextpdf.kernel.pdf.canvas.CanvasTag;
/*      */ import com.itextpdf.kernel.pdf.canvas.parser.data.AbstractRenderInfo;
/*      */ import com.itextpdf.kernel.pdf.canvas.parser.data.ClippingPathInfo;
/*      */ import com.itextpdf.kernel.pdf.canvas.parser.data.IEventData;
/*      */ import com.itextpdf.kernel.pdf.canvas.parser.data.ImageRenderInfo;
/*      */ import com.itextpdf.kernel.pdf.canvas.parser.data.PathRenderInfo;
/*      */ import com.itextpdf.kernel.pdf.canvas.parser.data.TextRenderInfo;
/*      */ import com.itextpdf.kernel.pdf.canvas.parser.listener.IEventListener;
/*      */ import com.itextpdf.kernel.pdf.canvas.parser.util.PdfCanvasParser;
/*      */ import com.itextpdf.kernel.pdf.colorspace.PdfCieBasedCs;
/*      */ import com.itextpdf.kernel.pdf.colorspace.PdfColorSpace;
/*      */ import com.itextpdf.kernel.pdf.colorspace.PdfPattern;
/*      */ import com.itextpdf.kernel.pdf.colorspace.PdfSpecialCs;
/*      */ import com.itextpdf.kernel.pdf.extgstate.PdfExtGState;
/*      */ import java.io.IOException;
/*      */ import java.lang.ref.WeakReference;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Arrays;
/*      */ import java.util.Collection;
/*      */ import java.util.Collections;
/*      */ import java.util.HashMap;
/*      */ import java.util.List;
/*      */ import java.util.Map;
/*      */ import java.util.Set;
/*      */ import java.util.Stack;
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
/*      */ public class PdfCanvasProcessor
/*      */ {
/*      */   public static final String DEFAULT_OPERATOR = "DefaultOperator";
/*      */   protected final IEventListener eventListener;
/*      */   protected final Set<EventType> supportedEvents;
/*  123 */   protected Path currentPath = new Path();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected boolean isClip;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected int clippingRule;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private Map<String, IContentOperator> operators;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private Stack<PdfResources> resourcesStack;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  153 */   private final Stack<ParserGraphicsState> gsStack = new Stack<>();
/*      */ 
/*      */ 
/*      */   
/*      */   private Matrix textMatrix;
/*      */ 
/*      */   
/*      */   private Matrix textLineMatrix;
/*      */ 
/*      */   
/*      */   private Map<PdfName, IXObjectDoHandler> xobjectDoHandlers;
/*      */ 
/*      */   
/*  166 */   private Map<Integer, WeakReference<PdfFont>> cachedFonts = new HashMap<>();
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  171 */   private Stack<CanvasTag> markedContentStack = new Stack<>();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PdfCanvasProcessor(IEventListener eventListener) {
/*  180 */     this.eventListener = eventListener;
/*  181 */     this.supportedEvents = eventListener.getSupportedEvents();
/*  182 */     this.operators = new HashMap<>();
/*  183 */     populateOperators();
/*  184 */     this.xobjectDoHandlers = new HashMap<>();
/*  185 */     populateXObjectDoHandlers();
/*  186 */     reset();
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
/*      */   public PdfCanvasProcessor(IEventListener eventListener, Map<String, IContentOperator> additionalContentOperators) {
/*  199 */     this(eventListener);
/*  200 */     for (Map.Entry<String, IContentOperator> entry : additionalContentOperators.entrySet()) {
/*  201 */       registerContentOperator(entry.getKey(), entry.getValue());
/*      */     }
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
/*      */   public IXObjectDoHandler registerXObjectDoHandler(PdfName xobjectSubType, IXObjectDoHandler handler) {
/*  216 */     return this.xobjectDoHandlers.put(xobjectSubType, handler);
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
/*      */   public IContentOperator registerContentOperator(String operatorString, IContentOperator operator) {
/*  230 */     return this.operators.put(operatorString, operator);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Collection<String> getRegisteredOperatorStrings() {
/*  239 */     return new ArrayList<>(this.operators.keySet());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void reset() {
/*  246 */     this.gsStack.removeAllElements();
/*  247 */     this.gsStack.push(new ParserGraphicsState());
/*  248 */     this.textMatrix = null;
/*  249 */     this.textLineMatrix = null;
/*  250 */     this.resourcesStack = new Stack<>();
/*  251 */     this.isClip = false;
/*  252 */     this.currentPath = new Path();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ParserGraphicsState getGraphicsState() {
/*  261 */     return this.gsStack.peek();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void processContent(byte[] contentBytes, PdfResources resources) {
/*  272 */     if (resources == null) {
/*  273 */       throw new PdfException("Resources cannot be null.");
/*      */     }
/*  275 */     this.resourcesStack.push(resources);
/*  276 */     PdfTokenizer tokeniser = new PdfTokenizer(new RandomAccessFileOrArray((new RandomAccessSourceFactory()).createSource(contentBytes)));
/*  277 */     PdfCanvasParser ps = new PdfCanvasParser(tokeniser, resources);
/*  278 */     List<PdfObject> operands = new ArrayList<>();
/*      */     try {
/*  280 */       while (ps.parse(operands).size() > 0) {
/*  281 */         PdfLiteral operator = (PdfLiteral)operands.get(operands.size() - 1);
/*  282 */         invokeOperator(operator, operands);
/*      */       } 
/*  284 */     } catch (IOException e) {
/*  285 */       throw new PdfException("Cannot parse content stream.", e);
/*      */     } 
/*      */     
/*  288 */     this.resourcesStack.pop();
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
/*      */   public void processPageContent(PdfPage page) {
/*  300 */     initClippingPath(page);
/*  301 */     ParserGraphicsState gs = getGraphicsState();
/*  302 */     eventOccurred((IEventData)new ClippingPathInfo(gs, gs.getClippingPath(), gs.getCtm()), EventType.CLIP_PATH_CHANGED);
/*  303 */     processContent(page.getContentBytes(), page.getResources());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public IEventListener getEventListener() {
/*  313 */     return this.eventListener;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void populateOperators() {
/*  320 */     registerContentOperator("DefaultOperator", new IgnoreOperator());
/*      */     
/*  322 */     registerContentOperator("q", new PushGraphicsStateOperator());
/*  323 */     registerContentOperator("Q", new PopGraphicsStateOperator());
/*  324 */     registerContentOperator("cm", new ModifyCurrentTransformationMatrixOperator());
/*      */     
/*  326 */     registerContentOperator("Do", new DoOperator());
/*      */     
/*  328 */     registerContentOperator("BMC", new BeginMarkedContentOperator());
/*  329 */     registerContentOperator("BDC", new BeginMarkedContentDictionaryOperator());
/*  330 */     registerContentOperator("EMC", new EndMarkedContentOperator());
/*      */     
/*  332 */     if (this.supportedEvents == null || this.supportedEvents.contains(EventType.RENDER_TEXT) || this.supportedEvents
/*  333 */       .contains(EventType.RENDER_PATH) || this.supportedEvents
/*  334 */       .contains(EventType.CLIP_PATH_CHANGED)) {
/*      */       
/*  336 */       registerContentOperator("g", new SetGrayFillOperator());
/*  337 */       registerContentOperator("G", new SetGrayStrokeOperator());
/*  338 */       registerContentOperator("rg", new SetRGBFillOperator());
/*  339 */       registerContentOperator("RG", new SetRGBStrokeOperator());
/*  340 */       registerContentOperator("k", new SetCMYKFillOperator());
/*  341 */       registerContentOperator("K", new SetCMYKStrokeOperator());
/*  342 */       registerContentOperator("cs", new SetColorSpaceFillOperator());
/*  343 */       registerContentOperator("CS", new SetColorSpaceStrokeOperator());
/*  344 */       registerContentOperator("sc", new SetColorFillOperator());
/*  345 */       registerContentOperator("SC", new SetColorStrokeOperator());
/*  346 */       registerContentOperator("scn", new SetColorFillOperator());
/*  347 */       registerContentOperator("SCN", new SetColorStrokeOperator());
/*  348 */       registerContentOperator("gs", new ProcessGraphicsStateResourceOperator());
/*      */     } 
/*      */     
/*  351 */     if (this.supportedEvents == null || this.supportedEvents.contains(EventType.RENDER_IMAGE)) {
/*  352 */       registerContentOperator("EI", new EndImageOperator());
/*      */     }
/*      */     
/*  355 */     if (this.supportedEvents == null || this.supportedEvents.contains(EventType.RENDER_TEXT) || this.supportedEvents
/*  356 */       .contains(EventType.BEGIN_TEXT) || this.supportedEvents
/*  357 */       .contains(EventType.END_TEXT)) {
/*  358 */       registerContentOperator("BT", new BeginTextOperator());
/*  359 */       registerContentOperator("ET", new EndTextOperator());
/*      */     } 
/*      */     
/*  362 */     if (this.supportedEvents == null || this.supportedEvents.contains(EventType.RENDER_TEXT)) {
/*  363 */       SetTextCharacterSpacingOperator tcOperator = new SetTextCharacterSpacingOperator();
/*  364 */       registerContentOperator("Tc", tcOperator);
/*  365 */       SetTextWordSpacingOperator twOperator = new SetTextWordSpacingOperator();
/*  366 */       registerContentOperator("Tw", twOperator);
/*  367 */       registerContentOperator("Tz", new SetTextHorizontalScalingOperator());
/*  368 */       SetTextLeadingOperator tlOperator = new SetTextLeadingOperator();
/*  369 */       registerContentOperator("TL", tlOperator);
/*  370 */       registerContentOperator("Tf", new SetTextFontOperator());
/*  371 */       registerContentOperator("Tr", new SetTextRenderModeOperator());
/*  372 */       registerContentOperator("Ts", new SetTextRiseOperator());
/*      */       
/*  374 */       TextMoveStartNextLineOperator tdOperator = new TextMoveStartNextLineOperator();
/*  375 */       registerContentOperator("Td", tdOperator);
/*  376 */       registerContentOperator("TD", new TextMoveStartNextLineWithLeadingOperator(tdOperator, tlOperator));
/*  377 */       registerContentOperator("Tm", new TextSetTextMatrixOperator());
/*  378 */       TextMoveNextLineOperator tstarOperator = new TextMoveNextLineOperator(tdOperator);
/*  379 */       registerContentOperator("T*", tstarOperator);
/*      */       
/*  381 */       ShowTextOperator tjOperator = new ShowTextOperator();
/*  382 */       registerContentOperator("Tj", tjOperator);
/*  383 */       MoveNextLineAndShowTextOperator tickOperator = new MoveNextLineAndShowTextOperator(tstarOperator, tjOperator);
/*  384 */       registerContentOperator("'", tickOperator);
/*  385 */       registerContentOperator("\"", new MoveNextLineAndShowTextWithSpacingOperator(twOperator, tcOperator, tickOperator));
/*  386 */       registerContentOperator("TJ", new ShowTextArrayOperator());
/*      */     } 
/*      */     
/*  389 */     if (this.supportedEvents == null || this.supportedEvents.contains(EventType.CLIP_PATH_CHANGED) || this.supportedEvents
/*  390 */       .contains(EventType.RENDER_PATH)) {
/*  391 */       registerContentOperator("w", new SetLineWidthOperator());
/*  392 */       registerContentOperator("J", new SetLineCapOperator());
/*  393 */       registerContentOperator("j", new SetLineJoinOperator());
/*  394 */       registerContentOperator("M", new SetMiterLimitOperator());
/*  395 */       registerContentOperator("d", new SetLineDashPatternOperator());
/*      */       
/*  397 */       int fillStroke = 3;
/*  398 */       registerContentOperator("m", new MoveToOperator());
/*  399 */       registerContentOperator("l", new LineToOperator());
/*  400 */       registerContentOperator("c", new CurveOperator());
/*  401 */       registerContentOperator("v", new CurveFirstPointDuplicatedOperator());
/*  402 */       registerContentOperator("y", new CurveFourhPointDuplicatedOperator());
/*  403 */       registerContentOperator("h", new CloseSubpathOperator());
/*  404 */       registerContentOperator("re", new RectangleOperator());
/*  405 */       registerContentOperator("S", new PaintPathOperator(1, -1, false));
/*  406 */       registerContentOperator("s", new PaintPathOperator(1, -1, true));
/*  407 */       registerContentOperator("f", new PaintPathOperator(2, 1, false));
/*  408 */       registerContentOperator("F", new PaintPathOperator(2, 1, false));
/*  409 */       registerContentOperator("f*", new PaintPathOperator(2, 2, false));
/*  410 */       registerContentOperator("B", new PaintPathOperator(fillStroke, 1, false));
/*  411 */       registerContentOperator("B*", new PaintPathOperator(fillStroke, 2, false));
/*  412 */       registerContentOperator("b", new PaintPathOperator(fillStroke, 1, true));
/*  413 */       registerContentOperator("b*", new PaintPathOperator(fillStroke, 2, true));
/*  414 */       registerContentOperator("n", new PaintPathOperator(0, -1, false));
/*  415 */       registerContentOperator("W", new ClipPathOperator(1));
/*  416 */       registerContentOperator("W*", new ClipPathOperator(2));
/*      */     } 
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
/*      */   protected void paintPath(int operation, int rule) {
/*  430 */     ParserGraphicsState gs = getGraphicsState();
/*  431 */     PathRenderInfo renderInfo = new PathRenderInfo(this.markedContentStack, gs, this.currentPath, operation, rule, this.isClip, this.clippingRule);
/*  432 */     eventOccurred((IEventData)renderInfo, EventType.RENDER_PATH);
/*      */     
/*  434 */     if (this.isClip) {
/*  435 */       this.isClip = false;
/*  436 */       gs.clip(this.currentPath, this.clippingRule);
/*  437 */       eventOccurred((IEventData)new ClippingPathInfo(gs, gs.getClippingPath(), gs.getCtm()), EventType.CLIP_PATH_CHANGED);
/*      */     } 
/*      */     
/*  440 */     this.currentPath = new Path();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void invokeOperator(PdfLiteral operator, List<PdfObject> operands) {
/*  450 */     IContentOperator op = this.operators.get(operator.toString());
/*  451 */     if (op == null) {
/*  452 */       op = this.operators.get("DefaultOperator");
/*      */     }
/*  454 */     op.invoke(this, operator, operands);
/*      */   }
/*      */   
/*      */   protected PdfStream getXObjectStream(PdfName xobjectName) {
/*  458 */     PdfDictionary xobjects = getResources().getResource(PdfName.XObject);
/*  459 */     return xobjects.getAsStream(xobjectName);
/*      */   }
/*      */   
/*      */   protected PdfResources getResources() {
/*  463 */     return this.resourcesStack.peek();
/*      */   }
/*      */   
/*      */   protected void populateXObjectDoHandlers() {
/*  467 */     registerXObjectDoHandler(PdfName.Default, new IgnoreXObjectDoHandler());
/*  468 */     registerXObjectDoHandler(PdfName.Form, new FormXObjectDoHandler());
/*      */     
/*  470 */     if (this.supportedEvents == null || this.supportedEvents
/*  471 */       .contains(EventType.RENDER_IMAGE)) {
/*  472 */       registerXObjectDoHandler(PdfName.Image, new ImageXObjectDoHandler());
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected PdfFont getFont(PdfDictionary fontDict) {
/*  483 */     if (fontDict.getIndirectReference() == null) {
/*  484 */       return PdfFontFactory.createFont(fontDict);
/*      */     }
/*  486 */     int n = fontDict.getIndirectReference().getObjNumber();
/*  487 */     WeakReference<PdfFont> fontRef = this.cachedFonts.get(Integer.valueOf(n));
/*  488 */     PdfFont font = (fontRef == null) ? null : fontRef.get();
/*  489 */     if (font == null) {
/*  490 */       font = PdfFontFactory.createFont(fontDict);
/*  491 */       this.cachedFonts.put(Integer.valueOf(n), new WeakReference<>(font));
/*      */     } 
/*  493 */     return font;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void beginMarkedContent(PdfName tag, PdfDictionary dict) {
/*  504 */     this.markedContentStack.push((new CanvasTag(tag)).setProperties(dict));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void endMarkedContent() {
/*  511 */     this.markedContentStack.pop();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void beginText() {
/*  518 */     eventOccurred(null, EventType.BEGIN_TEXT);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void endText() {
/*  525 */     eventOccurred(null, EventType.END_TEXT);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void eventOccurred(IEventData data, EventType type) {
/*  535 */     if (this.supportedEvents == null || this.supportedEvents.contains(type)) {
/*  536 */       this.eventListener.eventOccurred(data, type);
/*      */     }
/*  538 */     if (data instanceof AbstractRenderInfo) {
/*  539 */       ((AbstractRenderInfo)data).releaseGraphicsState();
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void displayPdfString(PdfString string) {
/*  549 */     TextRenderInfo renderInfo = new TextRenderInfo(string, getGraphicsState(), this.textMatrix, this.markedContentStack);
/*  550 */     this.textMatrix = (new Matrix(renderInfo.getUnscaledWidth(), 0.0F)).multiply(this.textMatrix);
/*  551 */     eventOccurred((IEventData)renderInfo, EventType.RENDER_TEXT);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void displayXObject(PdfName resourceName) {
/*  560 */     PdfStream xobjectStream = getXObjectStream(resourceName);
/*  561 */     PdfName subType = xobjectStream.getAsName(PdfName.Subtype);
/*  562 */     IXObjectDoHandler handler = this.xobjectDoHandlers.get(subType);
/*      */     
/*  564 */     if (handler == null) {
/*  565 */       handler = this.xobjectDoHandlers.get(PdfName.Default);
/*      */     }
/*      */     
/*  568 */     handler.handleXObject(this, this.markedContentStack, xobjectStream, resourceName);
/*      */   }
/*      */   
/*      */   private void displayImage(Stack<CanvasTag> canvasTagHierarchy, PdfStream imageStream, PdfName resourceName, boolean isInline) {
/*  572 */     PdfDictionary colorSpaceDic = getResources().getResource(PdfName.ColorSpace);
/*  573 */     ImageRenderInfo renderInfo = new ImageRenderInfo(canvasTagHierarchy, getGraphicsState(), getGraphicsState().getCtm(), imageStream, resourceName, colorSpaceDic, isInline);
/*      */     
/*  575 */     eventOccurred((IEventData)renderInfo, EventType.RENDER_IMAGE);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void applyTextAdjust(float tj) {
/*  584 */     float adjustBy = -tj / 1000.0F * getGraphicsState().getFontSize() * getGraphicsState().getHorizontalScaling() / 100.0F;
/*      */     
/*  586 */     this.textMatrix = (new Matrix(adjustBy, 0.0F)).multiply(this.textMatrix);
/*      */   }
/*      */   
/*      */   private void initClippingPath(PdfPage page) {
/*  590 */     Path clippingPath = new Path();
/*  591 */     clippingPath.rectangle(page.getCropBox());
/*  592 */     getGraphicsState().setClippingPath(clippingPath);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static class IgnoreOperator
/*      */     implements IContentOperator
/*      */   {
/*      */     private IgnoreOperator() {}
/*      */ 
/*      */ 
/*      */     
/*      */     public void invoke(PdfCanvasProcessor processor, PdfLiteral operator, List<PdfObject> operands) {}
/*      */   }
/*      */ 
/*      */   
/*      */   private static class ShowTextArrayOperator
/*      */     implements IContentOperator
/*      */   {
/*      */     private ShowTextArrayOperator() {}
/*      */ 
/*      */     
/*      */     public void invoke(PdfCanvasProcessor processor, PdfLiteral operator, List<PdfObject> operands) {
/*  615 */       PdfArray array = (PdfArray)operands.get(0);
/*  616 */       float tj = 0.0F;
/*  617 */       for (PdfObject entryObj : array) {
/*  618 */         if (entryObj instanceof PdfString) {
/*  619 */           processor.displayPdfString((PdfString)entryObj);
/*  620 */           tj = 0.0F; continue;
/*      */         } 
/*  622 */         tj = ((PdfNumber)entryObj).floatValue();
/*  623 */         processor.applyTextAdjust(tj);
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static class MoveNextLineAndShowTextWithSpacingOperator
/*      */     implements IContentOperator
/*      */   {
/*      */     private final PdfCanvasProcessor.SetTextWordSpacingOperator setTextWordSpacing;
/*      */ 
/*      */     
/*      */     private final PdfCanvasProcessor.SetTextCharacterSpacingOperator setTextCharacterSpacing;
/*      */ 
/*      */     
/*      */     private final PdfCanvasProcessor.MoveNextLineAndShowTextOperator moveNextLineAndShowText;
/*      */ 
/*      */ 
/*      */     
/*      */     public MoveNextLineAndShowTextWithSpacingOperator(PdfCanvasProcessor.SetTextWordSpacingOperator setTextWordSpacing, PdfCanvasProcessor.SetTextCharacterSpacingOperator setTextCharacterSpacing, PdfCanvasProcessor.MoveNextLineAndShowTextOperator moveNextLineAndShowText) {
/*  645 */       this.setTextWordSpacing = setTextWordSpacing;
/*  646 */       this.setTextCharacterSpacing = setTextCharacterSpacing;
/*  647 */       this.moveNextLineAndShowText = moveNextLineAndShowText;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void invoke(PdfCanvasProcessor processor, PdfLiteral operator, List<PdfObject> operands) {
/*  654 */       PdfNumber aw = (PdfNumber)operands.get(0);
/*  655 */       PdfNumber ac = (PdfNumber)operands.get(1);
/*  656 */       PdfString string = (PdfString)operands.get(2);
/*      */       
/*  658 */       List<PdfObject> twOperands = new ArrayList<>(1);
/*  659 */       twOperands.add(0, aw);
/*  660 */       this.setTextWordSpacing.invoke(processor, null, twOperands);
/*      */       
/*  662 */       List<PdfObject> tcOperands = new ArrayList<>(1);
/*  663 */       tcOperands.add(0, ac);
/*  664 */       this.setTextCharacterSpacing.invoke(processor, null, tcOperands);
/*      */       
/*  666 */       List<PdfObject> tickOperands = new ArrayList<>(1);
/*  667 */       tickOperands.add(0, string);
/*  668 */       this.moveNextLineAndShowText.invoke(processor, null, tickOperands);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static class MoveNextLineAndShowTextOperator
/*      */     implements IContentOperator
/*      */   {
/*      */     private final PdfCanvasProcessor.TextMoveNextLineOperator textMoveNextLine;
/*      */ 
/*      */     
/*      */     private final PdfCanvasProcessor.ShowTextOperator showText;
/*      */ 
/*      */ 
/*      */     
/*      */     public MoveNextLineAndShowTextOperator(PdfCanvasProcessor.TextMoveNextLineOperator textMoveNextLine, PdfCanvasProcessor.ShowTextOperator showText) {
/*  686 */       this.textMoveNextLine = textMoveNextLine;
/*  687 */       this.showText = showText;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void invoke(PdfCanvasProcessor processor, PdfLiteral operator, List<PdfObject> operands) {
/*  694 */       this.textMoveNextLine.invoke(processor, null, new ArrayList<>(0));
/*  695 */       this.showText.invoke(processor, null, operands);
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   private static class ShowTextOperator
/*      */     implements IContentOperator
/*      */   {
/*      */     private ShowTextOperator() {}
/*      */ 
/*      */     
/*      */     public void invoke(PdfCanvasProcessor processor, PdfLiteral operator, List<PdfObject> operands) {
/*  707 */       PdfString string = (PdfString)operands.get(0);
/*      */       
/*  709 */       processor.displayPdfString(string);
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   private static class TextMoveNextLineOperator
/*      */     implements IContentOperator
/*      */   {
/*      */     private final PdfCanvasProcessor.TextMoveStartNextLineOperator moveStartNextLine;
/*      */ 
/*      */     
/*      */     public TextMoveNextLineOperator(PdfCanvasProcessor.TextMoveStartNextLineOperator moveStartNextLine) {
/*  721 */       this.moveStartNextLine = moveStartNextLine;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void invoke(PdfCanvasProcessor processor, PdfLiteral operator, List<PdfObject> operands) {
/*  728 */       List<PdfObject> tdoperands = new ArrayList<>(2);
/*  729 */       tdoperands.add(0, new PdfNumber(0));
/*  730 */       tdoperands.add(1, new PdfNumber(-processor.getGraphicsState().getLeading()));
/*  731 */       this.moveStartNextLine.invoke(processor, null, tdoperands);
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   private static class TextSetTextMatrixOperator
/*      */     implements IContentOperator
/*      */   {
/*      */     private TextSetTextMatrixOperator() {}
/*      */ 
/*      */     
/*      */     public void invoke(PdfCanvasProcessor processor, PdfLiteral operator, List<PdfObject> operands) {
/*  743 */       float a = ((PdfNumber)operands.get(0)).floatValue();
/*  744 */       float b = ((PdfNumber)operands.get(1)).floatValue();
/*  745 */       float c = ((PdfNumber)operands.get(2)).floatValue();
/*  746 */       float d = ((PdfNumber)operands.get(3)).floatValue();
/*  747 */       float e = ((PdfNumber)operands.get(4)).floatValue();
/*  748 */       float f = ((PdfNumber)operands.get(5)).floatValue();
/*      */       
/*  750 */       processor.textLineMatrix = new Matrix(a, b, c, d, e, f);
/*  751 */       processor.textMatrix = processor.textLineMatrix;
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   private static class TextMoveStartNextLineWithLeadingOperator
/*      */     implements IContentOperator
/*      */   {
/*      */     private final PdfCanvasProcessor.TextMoveStartNextLineOperator moveStartNextLine;
/*      */     private final PdfCanvasProcessor.SetTextLeadingOperator setTextLeading;
/*      */     
/*      */     public TextMoveStartNextLineWithLeadingOperator(PdfCanvasProcessor.TextMoveStartNextLineOperator moveStartNextLine, PdfCanvasProcessor.SetTextLeadingOperator setTextLeading) {
/*  763 */       this.moveStartNextLine = moveStartNextLine;
/*  764 */       this.setTextLeading = setTextLeading;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void invoke(PdfCanvasProcessor processor, PdfLiteral operator, List<PdfObject> operands) {
/*  771 */       float ty = ((PdfNumber)operands.get(1)).floatValue();
/*      */       
/*  773 */       List<PdfObject> tlOperands = new ArrayList<>(1);
/*  774 */       tlOperands.add(0, new PdfNumber(-ty));
/*  775 */       this.setTextLeading.invoke(processor, null, tlOperands);
/*  776 */       this.moveStartNextLine.invoke(processor, null, operands);
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   private static class TextMoveStartNextLineOperator
/*      */     implements IContentOperator
/*      */   {
/*      */     private TextMoveStartNextLineOperator() {}
/*      */ 
/*      */     
/*      */     public void invoke(PdfCanvasProcessor processor, PdfLiteral operator, List<PdfObject> operands) {
/*  788 */       float tx = ((PdfNumber)operands.get(0)).floatValue();
/*  789 */       float ty = ((PdfNumber)operands.get(1)).floatValue();
/*      */       
/*  791 */       Matrix translationMatrix = new Matrix(tx, ty);
/*  792 */       processor.textMatrix = translationMatrix.multiply(processor.textLineMatrix);
/*  793 */       processor.textLineMatrix = processor.textMatrix;
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   private static class SetTextFontOperator
/*      */     implements IContentOperator
/*      */   {
/*      */     private SetTextFontOperator() {}
/*      */ 
/*      */     
/*      */     public void invoke(PdfCanvasProcessor processor, PdfLiteral operator, List<PdfObject> operands) {
/*  805 */       PdfName fontResourceName = (PdfName)operands.get(0);
/*  806 */       float size = ((PdfNumber)operands.get(1)).floatValue();
/*      */       
/*  808 */       PdfDictionary fontsDictionary = processor.getResources().getResource(PdfName.Font);
/*  809 */       PdfDictionary fontDict = fontsDictionary.getAsDictionary(fontResourceName);
/*  810 */       PdfFont font = null;
/*  811 */       font = processor.getFont(fontDict);
/*      */       
/*  813 */       processor.getGraphicsState().setFont(font);
/*  814 */       processor.getGraphicsState().setFontSize(size);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static class SetTextRenderModeOperator
/*      */     implements IContentOperator
/*      */   {
/*      */     private SetTextRenderModeOperator() {}
/*      */ 
/*      */     
/*      */     public void invoke(PdfCanvasProcessor processor, PdfLiteral operator, List<PdfObject> operands) {
/*  827 */       PdfNumber render = (PdfNumber)operands.get(0);
/*  828 */       processor.getGraphicsState().setTextRenderingMode(render.intValue());
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   private static class SetTextRiseOperator
/*      */     implements IContentOperator
/*      */   {
/*      */     private SetTextRiseOperator() {}
/*      */ 
/*      */     
/*      */     public void invoke(PdfCanvasProcessor processor, PdfLiteral operator, List<PdfObject> operands) {
/*  840 */       PdfNumber rise = (PdfNumber)operands.get(0);
/*  841 */       processor.getGraphicsState().setTextRise(rise.floatValue());
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   private static class SetTextLeadingOperator
/*      */     implements IContentOperator
/*      */   {
/*      */     private SetTextLeadingOperator() {}
/*      */ 
/*      */     
/*      */     public void invoke(PdfCanvasProcessor processor, PdfLiteral operator, List<PdfObject> operands) {
/*  853 */       PdfNumber leading = (PdfNumber)operands.get(0);
/*  854 */       processor.getGraphicsState().setLeading(leading.floatValue());
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   private static class SetTextHorizontalScalingOperator
/*      */     implements IContentOperator
/*      */   {
/*      */     private SetTextHorizontalScalingOperator() {}
/*      */ 
/*      */     
/*      */     public void invoke(PdfCanvasProcessor processor, PdfLiteral operator, List<PdfObject> operands) {
/*  866 */       PdfNumber scale = (PdfNumber)operands.get(0);
/*  867 */       processor.getGraphicsState().setHorizontalScaling(scale.floatValue());
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   private static class SetTextCharacterSpacingOperator
/*      */     implements IContentOperator
/*      */   {
/*      */     private SetTextCharacterSpacingOperator() {}
/*      */ 
/*      */     
/*      */     public void invoke(PdfCanvasProcessor processor, PdfLiteral operator, List<PdfObject> operands) {
/*  879 */       PdfNumber charSpace = (PdfNumber)operands.get(0);
/*  880 */       processor.getGraphicsState().setCharSpacing(charSpace.floatValue());
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   private static class SetTextWordSpacingOperator
/*      */     implements IContentOperator
/*      */   {
/*      */     private SetTextWordSpacingOperator() {}
/*      */ 
/*      */     
/*      */     public void invoke(PdfCanvasProcessor processor, PdfLiteral operator, List<PdfObject> operands) {
/*  892 */       PdfNumber wordSpace = (PdfNumber)operands.get(0);
/*  893 */       processor.getGraphicsState().setWordSpacing(wordSpace.floatValue());
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   private static class ProcessGraphicsStateResourceOperator
/*      */     implements IContentOperator
/*      */   {
/*      */     private ProcessGraphicsStateResourceOperator() {}
/*      */     
/*      */     public void invoke(PdfCanvasProcessor processor, PdfLiteral operator, List<PdfObject> operands) {
/*      */       PdfStream pdfStream;
/*  905 */       PdfName dictionaryName = (PdfName)operands.get(0);
/*  906 */       PdfDictionary extGState = processor.getResources().getResource(PdfName.ExtGState);
/*  907 */       if (extGState == null)
/*  908 */         throw (new PdfException("Resources do not contain ExtGState entry. Unable to process operator {0}.")).setMessageParams(new Object[] { operator }); 
/*  909 */       PdfDictionary gsDic = extGState.getAsDictionary(dictionaryName);
/*  910 */       if (gsDic == null) {
/*  911 */         pdfStream = extGState.getAsStream(dictionaryName);
/*  912 */         if (pdfStream == null)
/*  913 */           throw (new PdfException("{0} is an unknown graphics state dictionary.")).setMessageParams(new Object[] { dictionaryName }); 
/*      */       } 
/*  915 */       PdfArray fontParameter = pdfStream.getAsArray(PdfName.Font);
/*  916 */       if (fontParameter != null) {
/*  917 */         PdfFont font = processor.getFont(fontParameter.getAsDictionary(0));
/*  918 */         float size = fontParameter.getAsNumber(1).floatValue();
/*      */         
/*  920 */         processor.getGraphicsState().setFont(font);
/*  921 */         processor.getGraphicsState().setFontSize(size);
/*      */       } 
/*  923 */       PdfExtGState pdfExtGState = new PdfExtGState(pdfStream.clone(Collections.singletonList(PdfName.Font)));
/*  924 */       processor.getGraphicsState().updateFromExtGState(pdfExtGState);
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   private static class PushGraphicsStateOperator
/*      */     implements IContentOperator
/*      */   {
/*      */     private PushGraphicsStateOperator() {}
/*      */ 
/*      */     
/*      */     public void invoke(PdfCanvasProcessor processor, PdfLiteral operator, List<PdfObject> operands) {
/*  936 */       ParserGraphicsState gs = processor.gsStack.peek();
/*  937 */       ParserGraphicsState copy = new ParserGraphicsState(gs);
/*  938 */       processor.gsStack.push(copy);
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   private static class ModifyCurrentTransformationMatrixOperator
/*      */     implements IContentOperator
/*      */   {
/*      */     private ModifyCurrentTransformationMatrixOperator() {}
/*      */ 
/*      */     
/*      */     public void invoke(PdfCanvasProcessor processor, PdfLiteral operator, List<PdfObject> operands) {
/*  950 */       float a = ((PdfNumber)operands.get(0)).floatValue();
/*  951 */       float b = ((PdfNumber)operands.get(1)).floatValue();
/*  952 */       float c = ((PdfNumber)operands.get(2)).floatValue();
/*  953 */       float d = ((PdfNumber)operands.get(3)).floatValue();
/*  954 */       float e = ((PdfNumber)operands.get(4)).floatValue();
/*  955 */       float f = ((PdfNumber)operands.get(5)).floatValue();
/*  956 */       Matrix matrix = new Matrix(a, b, c, d, e, f);
/*      */       try {
/*  958 */         processor.getGraphicsState().updateCtm(matrix);
/*  959 */       } catch (PdfException exception) {
/*  960 */         if (!(exception.getCause() instanceof com.itextpdf.kernel.geom.NoninvertibleTransformException)) {
/*  961 */           throw exception;
/*      */         }
/*  963 */         Logger logger = LoggerFactory.getLogger(PdfCanvasProcessor.class);
/*  964 */         logger.error(MessageFormatUtil.format("Failed to process a transformation matrix which is noninvertible. Some content may be placed not as expected.", new Object[0]));
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static Color getColor(PdfColorSpace pdfColorSpace, List<PdfObject> operands, PdfResources resources) {
/*      */     PdfObject pdfObject;
/*  975 */     if (pdfColorSpace.getPdfObject().isIndirectReference()) {
/*  976 */       pdfObject = ((PdfIndirectReference)pdfColorSpace.getPdfObject()).getRefersTo();
/*      */     } else {
/*  978 */       pdfObject = pdfColorSpace.getPdfObject();
/*      */     } 
/*      */     
/*  981 */     if (pdfObject.isName()) {
/*  982 */       if (PdfName.DeviceGray.equals(pdfObject))
/*  983 */         return (Color)new DeviceGray(getColorants(operands)[0]); 
/*  984 */       if (PdfName.Pattern.equals(pdfObject) && 
/*  985 */         operands.get(0) instanceof PdfName) {
/*  986 */         PdfPattern pattern = resources.getPattern((PdfName)operands.get(0));
/*  987 */         if (pattern != null) {
/*  988 */           return (Color)new PatternColor(pattern);
/*      */         }
/*      */       } 
/*      */       
/*  992 */       if (PdfName.DeviceRGB.equals(pdfObject)) {
/*  993 */         float[] c = getColorants(operands);
/*  994 */         return (Color)new DeviceRgb(c[0], c[1], c[2]);
/*  995 */       }  if (PdfName.DeviceCMYK.equals(pdfObject)) {
/*  996 */         float[] c = getColorants(operands);
/*  997 */         return (Color)new DeviceCmyk(c[0], c[1], c[2], c[3]);
/*      */       } 
/*  999 */     } else if (pdfObject.isArray()) {
/* 1000 */       PdfArray array = (PdfArray)pdfObject;
/* 1001 */       PdfName csType = array.getAsName(0);
/* 1002 */       if (PdfName.CalGray.equals(csType))
/* 1003 */         return (Color)new CalGray((PdfCieBasedCs.CalGray)pdfColorSpace, getColorants(operands)[0]); 
/* 1004 */       if (PdfName.CalRGB.equals(csType))
/* 1005 */         return (Color)new CalRgb((PdfCieBasedCs.CalRgb)pdfColorSpace, getColorants(operands)); 
/* 1006 */       if (PdfName.Lab.equals(csType))
/* 1007 */         return (Color)new Lab((PdfCieBasedCs.Lab)pdfColorSpace, getColorants(operands)); 
/* 1008 */       if (PdfName.ICCBased.equals(csType))
/* 1009 */         return (Color)new IccBased((PdfCieBasedCs.IccBased)pdfColorSpace, getColorants(operands)); 
/* 1010 */       if (PdfName.Indexed.equals(csType))
/* 1011 */         return (Color)new Indexed(pdfColorSpace, (int)getColorants(operands)[0]); 
/* 1012 */       if (PdfName.Separation.equals(csType))
/* 1013 */         return (Color)new Separation((PdfSpecialCs.Separation)pdfColorSpace, getColorants(operands)[0]); 
/* 1014 */       if (PdfName.DeviceN.equals(csType))
/* 1015 */         return (Color)new DeviceN((PdfSpecialCs.DeviceN)pdfColorSpace, getColorants(operands)); 
/* 1016 */       if (PdfName.Pattern.equals(csType)) {
/* 1017 */         List<PdfObject> underlyingOperands = new ArrayList<>(operands);
/* 1018 */         PdfObject patternName = underlyingOperands.remove(operands.size() - 2);
/* 1019 */         PdfColorSpace underlyingCs = ((PdfSpecialCs.UncoloredTilingPattern)pdfColorSpace).getUnderlyingColorSpace();
/* 1020 */         if (patternName instanceof PdfName) {
/* 1021 */           PdfPattern pattern = resources.getPattern((PdfName)patternName);
/* 1022 */           if (pattern instanceof PdfPattern.Tiling && !((PdfPattern.Tiling)pattern).isColored()) {
/* 1023 */             return (Color)new PatternColor((PdfPattern.Tiling)pattern, underlyingCs, getColorants(underlyingOperands));
/*      */           }
/*      */         } 
/*      */       } 
/*      */     } 
/*      */     
/* 1029 */     Logger logger = LoggerFactory.getLogger(PdfCanvasProcessor.class);
/* 1030 */     logger.warn(MessageFormatUtil.format("Unable to parse color {0} within {1} color space", new Object[] {
/* 1031 */             Arrays.toString(operands.toArray()), pdfColorSpace.getPdfObject()
/*      */           }));
/* 1033 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static Color getColor(int nOperands, List<PdfObject> operands) {
/* 1040 */     float[] c = new float[nOperands];
/* 1041 */     for (int i = 0; i < nOperands; i++) {
/* 1042 */       c[i] = ((PdfNumber)operands.get(i)).floatValue();
/*      */     }
/*      */     
/* 1045 */     switch (nOperands) {
/*      */       case 1:
/* 1047 */         return (Color)new DeviceGray(c[0]);
/*      */       case 3:
/* 1049 */         return (Color)new DeviceRgb(c[0], c[1], c[2]);
/*      */       case 4:
/* 1051 */         return (Color)new DeviceCmyk(c[0], c[1], c[2], c[3]);
/*      */     } 
/* 1053 */     return null;
/*      */   }
/*      */   
/*      */   private static float[] getColorants(List<PdfObject> operands) {
/* 1057 */     float[] c = new float[operands.size() - 1];
/* 1058 */     for (int i = 0; i < operands.size() - 1; i++) {
/* 1059 */       c[i] = ((PdfNumber)operands.get(i)).floatValue();
/*      */     }
/* 1061 */     return c;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected static class PopGraphicsStateOperator
/*      */     implements IContentOperator
/*      */   {
/*      */     public void invoke(PdfCanvasProcessor processor, PdfLiteral operator, List<PdfObject> operands) {
/* 1072 */       processor.gsStack.pop();
/* 1073 */       ParserGraphicsState gs = processor.getGraphicsState();
/* 1074 */       processor.eventOccurred((IEventData)new ClippingPathInfo(gs, gs.getClippingPath(), gs.getCtm()), EventType.CLIP_PATH_CHANGED);
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   private static class SetGrayFillOperator
/*      */     implements IContentOperator
/*      */   {
/*      */     private SetGrayFillOperator() {}
/*      */ 
/*      */     
/*      */     public void invoke(PdfCanvasProcessor processor, PdfLiteral operator, List<PdfObject> operands) {
/* 1086 */       processor.getGraphicsState().setFillColor(PdfCanvasProcessor.getColor(1, operands));
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   private static class SetGrayStrokeOperator
/*      */     implements IContentOperator
/*      */   {
/*      */     private SetGrayStrokeOperator() {}
/*      */ 
/*      */     
/*      */     public void invoke(PdfCanvasProcessor processor, PdfLiteral operator, List<PdfObject> operands) {
/* 1098 */       processor.getGraphicsState().setStrokeColor(PdfCanvasProcessor.getColor(1, operands));
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   private static class SetRGBFillOperator
/*      */     implements IContentOperator
/*      */   {
/*      */     private SetRGBFillOperator() {}
/*      */ 
/*      */     
/*      */     public void invoke(PdfCanvasProcessor processor, PdfLiteral operator, List<PdfObject> operands) {
/* 1110 */       processor.getGraphicsState().setFillColor(PdfCanvasProcessor.getColor(3, operands));
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   private static class SetRGBStrokeOperator
/*      */     implements IContentOperator
/*      */   {
/*      */     private SetRGBStrokeOperator() {}
/*      */ 
/*      */     
/*      */     public void invoke(PdfCanvasProcessor processor, PdfLiteral operator, List<PdfObject> operands) {
/* 1122 */       processor.getGraphicsState().setStrokeColor(PdfCanvasProcessor.getColor(3, operands));
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   private static class SetCMYKFillOperator
/*      */     implements IContentOperator
/*      */   {
/*      */     private SetCMYKFillOperator() {}
/*      */ 
/*      */     
/*      */     public void invoke(PdfCanvasProcessor processor, PdfLiteral operator, List<PdfObject> operands) {
/* 1134 */       processor.getGraphicsState().setFillColor(PdfCanvasProcessor.getColor(4, operands));
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   private static class SetCMYKStrokeOperator
/*      */     implements IContentOperator
/*      */   {
/*      */     private SetCMYKStrokeOperator() {}
/*      */ 
/*      */     
/*      */     public void invoke(PdfCanvasProcessor processor, PdfLiteral operator, List<PdfObject> operands) {
/* 1146 */       processor.getGraphicsState().setStrokeColor(PdfCanvasProcessor.getColor(4, operands));
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   private static class SetColorSpaceFillOperator
/*      */     implements IContentOperator
/*      */   {
/*      */     private SetColorSpaceFillOperator() {}
/*      */ 
/*      */     
/*      */     public void invoke(PdfCanvasProcessor processor, PdfLiteral operator, List<PdfObject> operands) {
/* 1158 */       PdfColorSpace pdfColorSpace = determineColorSpace((PdfName)operands.get(0), processor);
/* 1159 */       processor.getGraphicsState().setFillColor(Color.makeColor(pdfColorSpace));
/*      */     }
/*      */     
/*      */     static PdfColorSpace determineColorSpace(PdfName colorSpace, PdfCanvasProcessor processor) {
/* 1163 */       PdfColorSpace pdfColorSpace = null;
/* 1164 */       if (PdfColorSpace.directColorSpaces.contains(colorSpace)) {
/* 1165 */         pdfColorSpace = PdfColorSpace.makeColorSpace((PdfObject)colorSpace);
/*      */       } else {
/* 1167 */         PdfResources pdfResources = processor.getResources();
/* 1168 */         PdfDictionary resourceColorSpace = ((PdfDictionary)pdfResources.getPdfObject()).getAsDictionary(PdfName.ColorSpace);
/* 1169 */         pdfColorSpace = PdfColorSpace.makeColorSpace(resourceColorSpace.get(colorSpace));
/*      */       } 
/*      */       
/* 1172 */       return pdfColorSpace;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static class SetColorSpaceStrokeOperator
/*      */     implements IContentOperator
/*      */   {
/*      */     private SetColorSpaceStrokeOperator() {}
/*      */ 
/*      */     
/*      */     public void invoke(PdfCanvasProcessor processor, PdfLiteral operator, List<PdfObject> operands) {
/* 1185 */       PdfColorSpace pdfColorSpace = PdfCanvasProcessor.SetColorSpaceFillOperator.determineColorSpace((PdfName)operands.get(0), processor);
/* 1186 */       processor.getGraphicsState().setStrokeColor(Color.makeColor(pdfColorSpace));
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   private static class SetColorFillOperator
/*      */     implements IContentOperator
/*      */   {
/*      */     private SetColorFillOperator() {}
/*      */ 
/*      */     
/*      */     public void invoke(PdfCanvasProcessor processor, PdfLiteral operator, List<PdfObject> operands) {
/* 1198 */       processor.getGraphicsState().setFillColor(PdfCanvasProcessor.getColor(processor.getGraphicsState().getFillColor().getColorSpace(), operands, processor.getResources()));
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   private static class SetColorStrokeOperator
/*      */     implements IContentOperator
/*      */   {
/*      */     private SetColorStrokeOperator() {}
/*      */ 
/*      */     
/*      */     public void invoke(PdfCanvasProcessor processor, PdfLiteral operator, List<PdfObject> operands) {
/* 1210 */       processor.getGraphicsState().setStrokeColor(PdfCanvasProcessor.getColor(processor.getGraphicsState().getStrokeColor().getColorSpace(), operands, processor.getResources()));
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   private static class BeginTextOperator
/*      */     implements IContentOperator
/*      */   {
/*      */     private BeginTextOperator() {}
/*      */ 
/*      */     
/*      */     public void invoke(PdfCanvasProcessor processor, PdfLiteral operator, List<PdfObject> operands) {
/* 1222 */       processor.textMatrix = new Matrix();
/* 1223 */       processor.textLineMatrix = processor.textMatrix;
/* 1224 */       processor.beginText();
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   private static class EndTextOperator
/*      */     implements IContentOperator
/*      */   {
/*      */     private EndTextOperator() {}
/*      */ 
/*      */     
/*      */     public void invoke(PdfCanvasProcessor processor, PdfLiteral operator, List<PdfObject> operands) {
/* 1236 */       processor.textMatrix = null;
/* 1237 */       processor.textLineMatrix = null;
/* 1238 */       processor.endText();
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static class BeginMarkedContentOperator
/*      */     implements IContentOperator
/*      */   {
/*      */     private BeginMarkedContentOperator() {}
/*      */ 
/*      */     
/*      */     public void invoke(PdfCanvasProcessor processor, PdfLiteral operator, List<PdfObject> operands) {
/* 1251 */       processor.beginMarkedContent((PdfName)operands.get(0), null);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static class BeginMarkedContentDictionaryOperator
/*      */     implements IContentOperator
/*      */   {
/*      */     private BeginMarkedContentDictionaryOperator() {}
/*      */ 
/*      */ 
/*      */     
/*      */     public void invoke(PdfCanvasProcessor processor, PdfLiteral operator, List<PdfObject> operands) {
/* 1266 */       PdfObject properties = operands.get(1);
/*      */       
/* 1268 */       processor.beginMarkedContent((PdfName)operands.get(0), getPropertiesDictionary(properties, processor.getResources()));
/*      */     }
/*      */     
/*      */     PdfDictionary getPropertiesDictionary(PdfObject operand1, PdfResources resources) {
/* 1272 */       if (operand1.isDictionary()) {
/* 1273 */         return (PdfDictionary)operand1;
/*      */       }
/* 1275 */       PdfName dictionaryName = (PdfName)operand1;
/* 1276 */       PdfDictionary properties = resources.getResource(PdfName.Properties);
/* 1277 */       if (null == properties) {
/* 1278 */         Logger logger = LoggerFactory.getLogger(PdfCanvasProcessor.class);
/* 1279 */         logger.warn(MessageFormatUtil.format("The PDF contains a BDC operator which refers to a not existing Property dictionary: {0}.", new Object[] { PdfName.Properties }));
/* 1280 */         return null;
/*      */       } 
/* 1282 */       PdfDictionary propertiesDictionary = properties.getAsDictionary(dictionaryName);
/* 1283 */       if (null == propertiesDictionary) {
/* 1284 */         Logger logger = LoggerFactory.getLogger(PdfCanvasProcessor.class);
/* 1285 */         logger.warn(MessageFormatUtil.format("The PDF contains a BDC operator which refers to a not existing Property dictionary: {0}.", new Object[] { dictionaryName }));
/* 1286 */         return null;
/*      */       } 
/* 1288 */       return properties.getAsDictionary(dictionaryName);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static class EndMarkedContentOperator
/*      */     implements IContentOperator
/*      */   {
/*      */     private EndMarkedContentOperator() {}
/*      */ 
/*      */     
/*      */     public void invoke(PdfCanvasProcessor processor, PdfLiteral operator, List<PdfObject> operands) {
/* 1301 */       processor.endMarkedContent();
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   private static class DoOperator
/*      */     implements IContentOperator
/*      */   {
/*      */     private DoOperator() {}
/*      */ 
/*      */     
/*      */     public void invoke(PdfCanvasProcessor processor, PdfLiteral operator, List<PdfObject> operands) {
/* 1313 */       PdfName resourceName = (PdfName)operands.get(0);
/* 1314 */       processor.displayXObject(resourceName);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static class EndImageOperator
/*      */     implements IContentOperator
/*      */   {
/*      */     private EndImageOperator() {}
/*      */ 
/*      */ 
/*      */     
/*      */     public void invoke(PdfCanvasProcessor processor, PdfLiteral operator, List<PdfObject> operands) {
/* 1329 */       PdfStream imageStream = (PdfStream)operands.get(0);
/* 1330 */       processor.displayImage(processor.markedContentStack, imageStream, null, true);
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   private static class SetLineWidthOperator
/*      */     implements IContentOperator
/*      */   {
/*      */     private SetLineWidthOperator() {}
/*      */ 
/*      */     
/*      */     public void invoke(PdfCanvasProcessor processor, PdfLiteral oper, List<PdfObject> operands) {
/* 1342 */       float lineWidth = ((PdfNumber)operands.get(0)).floatValue();
/* 1343 */       processor.getGraphicsState().setLineWidth(lineWidth);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static class SetLineCapOperator
/*      */     implements IContentOperator
/*      */   {
/*      */     private SetLineCapOperator() {}
/*      */ 
/*      */     
/*      */     public void invoke(PdfCanvasProcessor processor, PdfLiteral oper, List<PdfObject> operands) {
/* 1356 */       int lineCap = ((PdfNumber)operands.get(0)).intValue();
/* 1357 */       processor.getGraphicsState().setLineCapStyle(lineCap);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static class SetLineJoinOperator
/*      */     implements IContentOperator
/*      */   {
/*      */     private SetLineJoinOperator() {}
/*      */ 
/*      */     
/*      */     public void invoke(PdfCanvasProcessor processor, PdfLiteral oper, List<PdfObject> operands) {
/* 1370 */       int lineJoin = ((PdfNumber)operands.get(0)).intValue();
/* 1371 */       processor.getGraphicsState().setLineJoinStyle(lineJoin);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static class SetMiterLimitOperator
/*      */     implements IContentOperator
/*      */   {
/*      */     private SetMiterLimitOperator() {}
/*      */ 
/*      */     
/*      */     public void invoke(PdfCanvasProcessor processor, PdfLiteral oper, List<PdfObject> operands) {
/* 1384 */       float miterLimit = ((PdfNumber)operands.get(0)).floatValue();
/* 1385 */       processor.getGraphicsState().setMiterLimit(miterLimit);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static class SetLineDashPatternOperator
/*      */     implements IContentOperator
/*      */   {
/*      */     private SetLineDashPatternOperator() {}
/*      */ 
/*      */     
/*      */     public void invoke(PdfCanvasProcessor processor, PdfLiteral oper, List<PdfObject> operands) {
/* 1398 */       processor.getGraphicsState().setDashPattern(new PdfArray(Arrays.asList(new PdfObject[] { operands.get(0), operands.get(1) })));
/*      */     }
/*      */   }
/*      */   
/*      */   private static class FormXObjectDoHandler
/*      */     implements IXObjectDoHandler
/*      */   {
/*      */     private FormXObjectDoHandler() {}
/*      */     
/*      */     public void handleXObject(PdfCanvasProcessor processor, Stack<CanvasTag> canvasTagHierarchy, PdfStream xObjectStream, PdfName xObjectName) {
/*      */       PdfResources resources;
/* 1409 */       PdfDictionary resourcesDic = xObjectStream.getAsDictionary(PdfName.Resources);
/*      */       
/* 1411 */       if (resourcesDic == null) {
/* 1412 */         resources = processor.getResources();
/*      */       } else {
/* 1414 */         resources = new PdfResources(resourcesDic);
/*      */       } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1421 */       byte[] contentBytes = xObjectStream.getBytes();
/* 1422 */       PdfArray matrix = xObjectStream.getAsArray(PdfName.Matrix);
/*      */       
/* 1424 */       (new PdfCanvasProcessor.PushGraphicsStateOperator()).invoke(processor, null, null);
/*      */       
/* 1426 */       if (matrix != null) {
/* 1427 */         float a = matrix.getAsNumber(0).floatValue();
/* 1428 */         float b = matrix.getAsNumber(1).floatValue();
/* 1429 */         float c = matrix.getAsNumber(2).floatValue();
/* 1430 */         float d = matrix.getAsNumber(3).floatValue();
/* 1431 */         float e = matrix.getAsNumber(4).floatValue();
/* 1432 */         float f = matrix.getAsNumber(5).floatValue();
/* 1433 */         Matrix formMatrix = new Matrix(a, b, c, d, e, f);
/* 1434 */         processor.getGraphicsState().updateCtm(formMatrix);
/*      */       } 
/*      */       
/* 1437 */       processor.processContent(contentBytes, resources);
/*      */       
/* 1439 */       (new PdfCanvasProcessor.PopGraphicsStateOperator()).invoke(processor, null, null);
/*      */     }
/*      */   }
/*      */   
/*      */   private static class ImageXObjectDoHandler
/*      */     implements IXObjectDoHandler
/*      */   {
/*      */     private ImageXObjectDoHandler() {}
/*      */     
/*      */     public void handleXObject(PdfCanvasProcessor processor, Stack<CanvasTag> canvasTagHierarchy, PdfStream xObjectStream, PdfName resourceName) {
/* 1449 */       processor.displayImage(canvasTagHierarchy, xObjectStream, resourceName, false);
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   private static class IgnoreXObjectDoHandler
/*      */     implements IXObjectDoHandler
/*      */   {
/*      */     private IgnoreXObjectDoHandler() {}
/*      */ 
/*      */     
/*      */     public void handleXObject(PdfCanvasProcessor processor, Stack<CanvasTag> canvasTagHierarchy, PdfStream xObjectStream, PdfName xObjectName) {}
/*      */   }
/*      */ 
/*      */   
/*      */   private static class MoveToOperator
/*      */     implements IContentOperator
/*      */   {
/*      */     private MoveToOperator() {}
/*      */ 
/*      */     
/*      */     public void invoke(PdfCanvasProcessor processor, PdfLiteral operator, List<PdfObject> operands) {
/* 1471 */       float x = ((PdfNumber)operands.get(0)).floatValue();
/* 1472 */       float y = ((PdfNumber)operands.get(1)).floatValue();
/* 1473 */       processor.currentPath.moveTo(x, y);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static class LineToOperator
/*      */     implements IContentOperator
/*      */   {
/*      */     private LineToOperator() {}
/*      */ 
/*      */     
/*      */     public void invoke(PdfCanvasProcessor processor, PdfLiteral operator, List<PdfObject> operands) {
/* 1486 */       float x = ((PdfNumber)operands.get(0)).floatValue();
/* 1487 */       float y = ((PdfNumber)operands.get(1)).floatValue();
/* 1488 */       processor.currentPath.lineTo(x, y);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static class CurveOperator
/*      */     implements IContentOperator
/*      */   {
/*      */     private CurveOperator() {}
/*      */ 
/*      */     
/*      */     public void invoke(PdfCanvasProcessor processor, PdfLiteral operator, List<PdfObject> operands) {
/* 1501 */       float x1 = ((PdfNumber)operands.get(0)).floatValue();
/* 1502 */       float y1 = ((PdfNumber)operands.get(1)).floatValue();
/* 1503 */       float x2 = ((PdfNumber)operands.get(2)).floatValue();
/* 1504 */       float y2 = ((PdfNumber)operands.get(3)).floatValue();
/* 1505 */       float x3 = ((PdfNumber)operands.get(4)).floatValue();
/* 1506 */       float y3 = ((PdfNumber)operands.get(5)).floatValue();
/* 1507 */       processor.currentPath.curveTo(x1, y1, x2, y2, x3, y3);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static class CurveFirstPointDuplicatedOperator
/*      */     implements IContentOperator
/*      */   {
/*      */     private CurveFirstPointDuplicatedOperator() {}
/*      */ 
/*      */     
/*      */     public void invoke(PdfCanvasProcessor processor, PdfLiteral operator, List<PdfObject> operands) {
/* 1520 */       float x2 = ((PdfNumber)operands.get(0)).floatValue();
/* 1521 */       float y2 = ((PdfNumber)operands.get(1)).floatValue();
/* 1522 */       float x3 = ((PdfNumber)operands.get(2)).floatValue();
/* 1523 */       float y3 = ((PdfNumber)operands.get(3)).floatValue();
/* 1524 */       processor.currentPath.curveTo(x2, y2, x3, y3);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static class CurveFourhPointDuplicatedOperator
/*      */     implements IContentOperator
/*      */   {
/*      */     private CurveFourhPointDuplicatedOperator() {}
/*      */ 
/*      */     
/*      */     public void invoke(PdfCanvasProcessor processor, PdfLiteral operator, List<PdfObject> operands) {
/* 1537 */       float x1 = ((PdfNumber)operands.get(0)).floatValue();
/* 1538 */       float y1 = ((PdfNumber)operands.get(1)).floatValue();
/* 1539 */       float x3 = ((PdfNumber)operands.get(2)).floatValue();
/* 1540 */       float y3 = ((PdfNumber)operands.get(3)).floatValue();
/* 1541 */       processor.currentPath.curveFromTo(x1, y1, x3, y3);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static class CloseSubpathOperator
/*      */     implements IContentOperator
/*      */   {
/*      */     private CloseSubpathOperator() {}
/*      */ 
/*      */     
/*      */     public void invoke(PdfCanvasProcessor processor, PdfLiteral operator, List<PdfObject> operands) {
/* 1554 */       processor.currentPath.closeSubpath();
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static class RectangleOperator
/*      */     implements IContentOperator
/*      */   {
/*      */     private RectangleOperator() {}
/*      */ 
/*      */     
/*      */     public void invoke(PdfCanvasProcessor processor, PdfLiteral operator, List<PdfObject> operands) {
/* 1567 */       float x = ((PdfNumber)operands.get(0)).floatValue();
/* 1568 */       float y = ((PdfNumber)operands.get(1)).floatValue();
/* 1569 */       float w = ((PdfNumber)operands.get(2)).floatValue();
/* 1570 */       float h = ((PdfNumber)operands.get(3)).floatValue();
/* 1571 */       processor.currentPath.rectangle(x, y, w, h);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static class PaintPathOperator
/*      */     implements IContentOperator
/*      */   {
/*      */     private int operation;
/*      */ 
/*      */ 
/*      */     
/*      */     private int rule;
/*      */ 
/*      */ 
/*      */     
/*      */     private boolean close;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public PaintPathOperator(int operation, int rule, boolean close) {
/* 1595 */       this.operation = operation;
/* 1596 */       this.rule = rule;
/* 1597 */       this.close = close;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void invoke(PdfCanvasProcessor processor, PdfLiteral operator, List<PdfObject> operands) {
/* 1604 */       if (this.close) {
/* 1605 */         processor.currentPath.closeSubpath();
/*      */       }
/*      */       
/* 1608 */       processor.paintPath(this.operation, this.rule);
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   private static class ClipPathOperator
/*      */     implements IContentOperator
/*      */   {
/*      */     private int rule;
/*      */ 
/*      */     
/*      */     public ClipPathOperator(int rule) {
/* 1620 */       this.rule = rule;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void invoke(PdfCanvasProcessor processor, PdfLiteral operator, List<PdfObject> operands) {
/* 1627 */       processor.isClip = true;
/* 1628 */       processor.clippingRule = this.rule;
/*      */     }
/*      */   }
/*      */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/kernel/pdf/canvas/parser/PdfCanvasProcessor.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */