/*      */ package com.itextpdf.svg.converter;
/*      */ 
/*      */ import com.itextpdf.io.util.FileUtil;
/*      */ import com.itextpdf.kernel.geom.PageSize;
/*      */ import com.itextpdf.kernel.geom.Rectangle;
/*      */ import com.itextpdf.kernel.pdf.PdfDocument;
/*      */ import com.itextpdf.kernel.pdf.PdfPage;
/*      */ import com.itextpdf.kernel.pdf.PdfWriter;
/*      */ import com.itextpdf.kernel.pdf.WriterProperties;
/*      */ import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
/*      */ import com.itextpdf.kernel.pdf.xobject.PdfFormXObject;
/*      */ import com.itextpdf.kernel.pdf.xobject.PdfXObject;
/*      */ import com.itextpdf.layout.element.Image;
/*      */ import com.itextpdf.styledxmlparser.css.util.CssUtils;
/*      */ import com.itextpdf.styledxmlparser.node.IDocumentNode;
/*      */ import com.itextpdf.styledxmlparser.node.INode;
/*      */ import com.itextpdf.styledxmlparser.node.impl.jsoup.JsoupXmlParser;
/*      */ import com.itextpdf.styledxmlparser.resolver.resource.ResourceResolver;
/*      */ import com.itextpdf.svg.SvgConstants;
/*      */ import com.itextpdf.svg.exceptions.SvgProcessingException;
/*      */ import com.itextpdf.svg.processors.ISvgConverterProperties;
/*      */ import com.itextpdf.svg.processors.ISvgProcessorResult;
/*      */ import com.itextpdf.svg.processors.impl.DefaultSvgProcessor;
/*      */ import com.itextpdf.svg.processors.impl.SvgConverterProperties;
/*      */ import com.itextpdf.svg.processors.impl.SvgProcessorContext;
/*      */ import com.itextpdf.svg.processors.impl.SvgProcessorResult;
/*      */ import com.itextpdf.svg.renderers.ISvgNodeRenderer;
/*      */ import com.itextpdf.svg.renderers.SvgDrawContext;
/*      */ import com.itextpdf.svg.renderers.impl.PdfRootSvgNodeRenderer;
/*      */ import com.itextpdf.svg.utils.SvgCssUtils;
/*      */ import java.io.File;
/*      */ import java.io.FileInputStream;
/*      */ import java.io.FileOutputStream;
/*      */ import java.io.IOException;
/*      */ import java.io.InputStream;
/*      */ import java.io.OutputStream;
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
/*      */ public final class SvgConverter
/*      */ {
/*   98 */   private static final Logger LOGGER = LoggerFactory.getLogger(SvgConverter.class);
/*      */ 
/*      */   
/*      */   private static void checkNull(Object o) {
/*  102 */     if (o == null) {
/*  103 */       throw new SvgProcessingException("Parameters for this method cannot be null.");
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
/*      */   public static void drawOnDocument(String content, PdfDocument document, int pageNo) {
/*  116 */     drawOnDocument(content, document, pageNo, 0.0F, 0.0F);
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
/*      */   public static void drawOnDocument(String content, PdfDocument document, int pageNo, float x, float y) {
/*  130 */     checkNull(document);
/*  131 */     drawOnPage(content, document.getPage(pageNo), x, y);
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
/*      */   public static void drawOnDocument(String content, PdfDocument document, int pageNo, ISvgConverterProperties props) {
/*  144 */     drawOnDocument(content, document, pageNo, 0.0F, 0.0F, props);
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
/*      */   public static void drawOnDocument(String content, PdfDocument document, int pageNo, float x, float y, ISvgConverterProperties props) {
/*  159 */     checkNull(document);
/*  160 */     drawOnPage(content, document.getPage(pageNo), x, y, props);
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
/*      */   public static void drawOnDocument(InputStream stream, PdfDocument document, int pageNo) throws IOException {
/*  173 */     drawOnDocument(stream, document, pageNo, 0.0F, 0.0F);
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
/*      */   public static void drawOnDocument(InputStream stream, PdfDocument document, int pageNo, float x, float y) throws IOException {
/*  188 */     checkNull(document);
/*  189 */     drawOnPage(stream, document.getPage(pageNo), x, y);
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
/*      */   public static void drawOnDocument(InputStream stream, PdfDocument document, int pageNo, ISvgConverterProperties props) throws IOException {
/*  203 */     drawOnDocument(stream, document, pageNo, 0.0F, 0.0F, props);
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
/*      */   public static void drawOnDocument(InputStream stream, PdfDocument document, int pageNo, float x, float y, ISvgConverterProperties props) throws IOException {
/*  219 */     checkNull(document);
/*  220 */     drawOnPage(stream, document.getPage(pageNo), x, y, props);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void drawOnPage(String content, PdfPage page) {
/*  230 */     drawOnPage(content, page, 0.0F, 0.0F);
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
/*      */   public static void drawOnPage(String content, PdfPage page, float x, float y) {
/*  242 */     checkNull(page);
/*  243 */     drawOnCanvas(content, new PdfCanvas(page), x, y);
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
/*      */   public static void drawOnPage(String content, PdfPage page, ISvgConverterProperties props) {
/*  255 */     drawOnPage(content, page, 0.0F, 0.0F, props);
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
/*      */   public static void drawOnPage(String content, PdfPage page, float x, float y, ISvgConverterProperties props) {
/*  268 */     checkNull(page);
/*  269 */     drawOnCanvas(content, new PdfCanvas(page), x, y, props);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void drawOnPage(InputStream stream, PdfPage page) throws IOException {
/*  280 */     drawOnPage(stream, page, 0.0F, 0.0F);
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
/*      */   public static void drawOnPage(InputStream stream, PdfPage page, float x, float y) throws IOException {
/*  293 */     checkNull(page);
/*  294 */     drawOnCanvas(stream, new PdfCanvas(page), x, y);
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
/*      */   public static void drawOnPage(InputStream stream, PdfPage page, ISvgConverterProperties props) throws IOException {
/*  306 */     drawOnPage(stream, page, 0.0F, 0.0F, props);
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
/*      */   public static void drawOnPage(InputStream stream, PdfPage page, float x, float y, ISvgConverterProperties props) throws IOException {
/*  320 */     checkNull(page);
/*  321 */     drawOnCanvas(stream, new PdfCanvas(page), x, y, props);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void drawOnCanvas(String content, PdfCanvas canvas) {
/*  331 */     drawOnCanvas(content, canvas, 0.0F, 0.0F);
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
/*      */   public static void drawOnCanvas(String content, PdfCanvas canvas, float x, float y) {
/*  343 */     checkNull(canvas);
/*  344 */     draw(convertToXObject(content, canvas.getDocument()), canvas, x, y);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void drawOnCanvas(String content, PdfCanvas canvas, ISvgConverterProperties props) {
/*  355 */     drawOnCanvas(content, canvas, 0.0F, 0.0F, props);
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
/*      */   public static void drawOnCanvas(String content, PdfCanvas canvas, float x, float y, ISvgConverterProperties props) {
/*  368 */     checkNull(canvas);
/*  369 */     draw(convertToXObject(content, canvas.getDocument(), props), canvas, x, y);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void drawOnCanvas(InputStream stream, PdfCanvas canvas) throws IOException {
/*  380 */     drawOnCanvas(stream, canvas, 0.0F, 0.0F);
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
/*      */   public static void drawOnCanvas(InputStream stream, PdfCanvas canvas, float x, float y) throws IOException {
/*  393 */     checkNull(canvas);
/*  394 */     draw(convertToXObject(stream, canvas.getDocument()), canvas, x, y);
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
/*      */   public static void drawOnCanvas(InputStream stream, PdfCanvas canvas, ISvgConverterProperties props) throws IOException {
/*  406 */     drawOnCanvas(stream, canvas, 0.0F, 0.0F, props);
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
/*      */   public static void drawOnCanvas(InputStream stream, PdfCanvas canvas, float x, float y, ISvgConverterProperties props) throws IOException {
/*  420 */     checkNull(canvas);
/*  421 */     draw(convertToXObject(stream, canvas.getDocument(), props), canvas, x, y);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void createPdf(File svgFile, File pdfFile) throws IOException {
/*  432 */     createPdf(svgFile, pdfFile, (ISvgConverterProperties)null, (WriterProperties)null);
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
/*      */   public static void createPdf(File svgFile, File pdfFile, ISvgConverterProperties props) throws IOException {
/*  445 */     createPdf(svgFile, pdfFile, props, (WriterProperties)null);
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
/*      */   public static void createPdf(File svgFile, File pdfFile, WriterProperties writerProps) throws IOException {
/*  458 */     createPdf(svgFile, pdfFile, (ISvgConverterProperties)null, writerProps);
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
/*      */   public static void createPdf(File svgFile, File pdfFile, ISvgConverterProperties props, WriterProperties writerProps) throws IOException {
/*      */     SvgConverterProperties svgConverterProperties;
/*  472 */     if (props == null) {
/*  473 */       svgConverterProperties = (new SvgConverterProperties()).setBaseUri(FileUtil.getParentDirectory(svgFile));
/*  474 */     } else if (svgConverterProperties.getBaseUri() == null || svgConverterProperties.getBaseUri().isEmpty()) {
/*  475 */       String baseUri = FileUtil.getParentDirectory(svgFile);
/*  476 */       svgConverterProperties = convertToSvgConverterProps((ISvgConverterProperties)svgConverterProperties, baseUri);
/*      */     } 
/*  478 */     try(FileInputStream fileInputStream = new FileInputStream(svgFile.getAbsolutePath()); 
/*  479 */         FileOutputStream fileOutputStream = new FileOutputStream(pdfFile.getAbsolutePath())) {
/*  480 */       createPdf(fileInputStream, fileOutputStream, (ISvgConverterProperties)svgConverterProperties, writerProps);
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
/*      */   private static SvgConverterProperties convertToSvgConverterProps(ISvgConverterProperties props, String baseUri) {
/*  493 */     return (new SvgConverterProperties()).setBaseUri(baseUri)
/*  494 */       .setMediaDeviceDescription(props.getMediaDeviceDescription())
/*  495 */       .setFontProvider(props.getFontProvider())
/*  496 */       .setCharset(props.getCharset())
/*  497 */       .setRendererFactory(props.getRendererFactory());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void createPdf(InputStream svgStream, OutputStream pdfDest) throws IOException {
/*  508 */     createPdf(svgStream, pdfDest, (ISvgConverterProperties)null, (WriterProperties)null);
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
/*      */   public static void createPdf(InputStream svgStream, OutputStream pdfDest, WriterProperties writerprops) throws IOException {
/*  520 */     createPdf(svgStream, pdfDest, (ISvgConverterProperties)null, writerprops);
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
/*      */   public static void createPdf(InputStream svgStream, OutputStream pdfDest, ISvgConverterProperties props) throws IOException {
/*  532 */     createPdf(svgStream, pdfDest, props, (WriterProperties)null);
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
/*      */   public static void createPdf(InputStream svgStream, OutputStream pdfDest, ISvgConverterProperties props, WriterProperties writerProps) throws IOException {
/*  553 */     if (writerProps == null) {
/*  554 */       writerProps = new WriterProperties();
/*      */     }
/*  556 */     PdfDocument pdfDocument = new PdfDocument(new PdfWriter(pdfDest, writerProps));
/*      */     
/*  558 */     ISvgProcessorResult processorResult = process(parse(svgStream, props), props);
/*  559 */     ISvgNodeRenderer topSvgRenderer = processorResult.getRootRenderer();
/*      */     
/*  561 */     ResourceResolver resourceResolver = getResourceResolver(processorResult, props);
/*  562 */     SvgDrawContext drawContext = new SvgDrawContext(resourceResolver, processorResult.getFontProvider(), processorResult.getRootRenderer());
/*      */     
/*  564 */     drawContext.addNamedObjects(processorResult.getNamedObjects());
/*      */     
/*  566 */     drawContext.setTempFonts(processorResult.getTempFonts());
/*      */     
/*  568 */     checkNull(topSvgRenderer);
/*  569 */     checkNull(pdfDocument);
/*      */ 
/*      */     
/*  572 */     float[] wh = extractWidthAndHeight(topSvgRenderer);
/*  573 */     float width = wh[0];
/*  574 */     float height = wh[1];
/*      */ 
/*      */     
/*  577 */     pdfDocument.setDefaultPageSize(new PageSize(width, height));
/*  578 */     PdfPage page = pdfDocument.addNewPage();
/*  579 */     PdfCanvas pageCanvas = new PdfCanvas(page);
/*      */     
/*  581 */     PdfFormXObject xObject = convertToXObject(topSvgRenderer, pdfDocument, drawContext);
/*      */     
/*  583 */     draw(xObject, pageCanvas);
/*  584 */     pdfDocument.close();
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
/*      */   public static PdfFormXObject convertToXObject(String content, PdfDocument document) {
/*  608 */     return convertToXObject(content, document, (ISvgConverterProperties)null);
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
/*      */   public static PdfFormXObject convertToXObject(String content, PdfDocument document, ISvgConverterProperties props) {
/*  633 */     checkNull(content);
/*  634 */     checkNull(document);
/*      */     
/*  636 */     return convertToXObject(process(parse(content), props), document, props);
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
/*      */   public static PdfFormXObject convertToXObject(InputStream stream, PdfDocument document, ISvgConverterProperties props) throws IOException {
/*  662 */     checkNull(stream);
/*  663 */     checkNull(document);
/*      */     
/*  665 */     return convertToXObject(process(parse(stream, props), props), document, props);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static PdfFormXObject convertToXObject(ISvgProcessorResult processorResult, PdfDocument document, ISvgConverterProperties props) {
/*  671 */     ResourceResolver resourceResolver = getResourceResolver(processorResult, props);
/*      */     
/*  673 */     SvgDrawContext drawContext = new SvgDrawContext(resourceResolver, processorResult.getFontProvider(), processorResult.getRootRenderer());
/*  674 */     drawContext.setTempFonts(processorResult.getTempFonts());
/*  675 */     drawContext.addNamedObjects(processorResult.getNamedObjects());
/*  676 */     return convertToXObject(processorResult.getRootRenderer(), document, drawContext);
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
/*      */   public static PdfFormXObject convertToXObject(InputStream stream, PdfDocument document) throws IOException {
/*  701 */     return convertToXObject(stream, document, (ISvgConverterProperties)null);
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
/*      */   public static Image convertToImage(InputStream stream, PdfDocument document) throws IOException {
/*  726 */     return new Image(convertToXObject(stream, document));
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
/*      */   public static Image convertToImage(InputStream stream, PdfDocument document, ISvgConverterProperties props) throws IOException {
/*  752 */     return new Image(convertToXObject(stream, document, props));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static void draw(PdfFormXObject pdfForm, PdfCanvas canvas) {
/*  759 */     canvas.addXObject((PdfXObject)pdfForm, 0.0F, 0.0F);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static void draw(PdfFormXObject pdfForm, PdfCanvas canvas, float x, float y) {
/*  766 */     canvas.addXObject((PdfXObject)pdfForm, x, y);
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
/*      */   public static PdfFormXObject convertToXObject(ISvgNodeRenderer topSvgRenderer, PdfDocument document) {
/*  791 */     return convertToXObject(topSvgRenderer, document, new SvgDrawContext(null, null));
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
/*      */   private static PdfFormXObject convertToXObject(ISvgNodeRenderer topSvgRenderer, PdfDocument document, SvgDrawContext context) {
/*  815 */     checkNull(topSvgRenderer);
/*  816 */     checkNull(document);
/*  817 */     checkNull(context);
/*      */ 
/*      */     
/*  820 */     float[] wh = extractWidthAndHeight(topSvgRenderer);
/*  821 */     float width = wh[0];
/*  822 */     float height = wh[1];
/*      */     
/*  824 */     PdfFormXObject pdfForm = new PdfFormXObject(new Rectangle(0.0F, 0.0F, width, height));
/*  825 */     PdfCanvas canvas = new PdfCanvas(pdfForm, document);
/*      */     
/*  827 */     context.pushCanvas(canvas);
/*  828 */     PdfRootSvgNodeRenderer pdfRootSvgNodeRenderer = new PdfRootSvgNodeRenderer(topSvgRenderer);
/*  829 */     pdfRootSvgNodeRenderer.draw(context);
/*  830 */     return pdfForm;
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
/*      */   public static ISvgProcessorResult parseAndProcess(InputStream svgStream) throws IOException {
/*  843 */     return parseAndProcess(svgStream, null);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static ISvgProcessorResult parseAndProcess(InputStream svgStream, ISvgConverterProperties props) throws IOException {
/*      */     IDocumentNode iDocumentNode;
/*  855 */     JsoupXmlParser jsoupXmlParser = new JsoupXmlParser();
/*  856 */     String charset = tryToExtractCharset(props);
/*      */     
/*      */     try {
/*  859 */       iDocumentNode = jsoupXmlParser.parse(svgStream, charset);
/*  860 */     } catch (Exception e) {
/*  861 */       throw new SvgProcessingException("Failed to parse InputStream.", e);
/*      */     } 
/*  863 */     return (new DefaultSvgProcessor()).process((INode)iDocumentNode, props);
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
/*      */   public static ISvgProcessorResult process(INode root) {
/*  876 */     return process(root, null);
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
/*      */   public static ISvgProcessorResult process(INode root, ISvgConverterProperties props) {
/*  888 */     checkNull(root);
/*  889 */     return (new DefaultSvgProcessor()).process(root, props);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static INode parse(String content) {
/*  900 */     checkNull(content);
/*  901 */     return (INode)(new JsoupXmlParser()).parse(content);
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
/*      */   public static INode parse(InputStream stream) throws IOException {
/*  914 */     checkNull(stream);
/*  915 */     return parse(stream, null);
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
/*      */   public static INode parse(InputStream stream, ISvgConverterProperties props) throws IOException {
/*  931 */     checkNull(stream);
/*  932 */     JsoupXmlParser jsoupXmlParser = new JsoupXmlParser();
/*  933 */     return (INode)jsoupXmlParser.parse(stream, tryToExtractCharset(props));
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
/*      */   public static float[] extractWidthAndHeight(ISvgNodeRenderer topSvgRenderer) {
/*  945 */     float width, height, res[] = new float[2];
/*  946 */     boolean viewBoxPresent = false;
/*      */ 
/*      */     
/*  949 */     String vbString = topSvgRenderer.getAttribute(SvgConstants.Attributes.VIEWBOX);
/*  950 */     float[] values = { 0.0F, 0.0F, 0.0F, 0.0F };
/*  951 */     if (vbString != null) {
/*  952 */       List<String> valueStrings = SvgCssUtils.splitValueList(vbString);
/*  953 */       values = new float[valueStrings.size()];
/*  954 */       for (int i = 0; i < values.length; i++) {
/*  955 */         values[i] = CssUtils.parseAbsoluteLength((String)valueStrings.get(i));
/*      */       }
/*  957 */       viewBoxPresent = true;
/*      */     } 
/*      */ 
/*      */     
/*  961 */     String wString = topSvgRenderer.getAttribute("width");
/*  962 */     if (wString == null) {
/*  963 */       if (viewBoxPresent) {
/*  964 */         width = values[2];
/*      */       } else {
/*      */         
/*  967 */         LOGGER.warn("Top Svg tag has no defined width attribute and viewbox width is not present, so browser default of 300px is used");
/*      */         
/*  969 */         width = CssUtils.parseAbsoluteLength("300px");
/*      */       } 
/*      */     } else {
/*  972 */       width = CssUtils.parseAbsoluteLength(wString);
/*      */     } 
/*  974 */     String hString = topSvgRenderer.getAttribute("height");
/*  975 */     if (hString == null) {
/*  976 */       if (viewBoxPresent) {
/*  977 */         height = values[3];
/*      */       } else {
/*      */         
/*  980 */         LOGGER.warn("Top Svg tag has no defined height attribute and viewbox height is not present, so browser default of 150px is used");
/*      */         
/*  982 */         height = CssUtils.parseAbsoluteLength("150px");
/*      */       } 
/*      */     } else {
/*  985 */       height = CssUtils.parseAbsoluteLength(hString);
/*      */     } 
/*      */     
/*  988 */     res[0] = width;
/*  989 */     res[1] = height;
/*  990 */     return res;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   static ResourceResolver getResourceResolver(ISvgProcessorResult processorResult, ISvgConverterProperties props) {
/*  996 */     ResourceResolver resourceResolver = null;
/*  997 */     if (processorResult instanceof SvgProcessorResult) {
/*      */       
/*  999 */       SvgProcessorContext context = ((SvgProcessorResult)processorResult).getContext();
/* 1000 */       if (context != null) {
/* 1001 */         resourceResolver = context.getResourceResolver();
/*      */       }
/*      */     } 
/*      */ 
/*      */     
/* 1006 */     if (resourceResolver == null) {
/* 1007 */       resourceResolver = createResourceResolver(props);
/*      */     }
/* 1009 */     return resourceResolver;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static String tryToExtractCharset(ISvgConverterProperties props) {
/* 1019 */     return (props != null) ? props.getCharset() : null;
/*      */   }
/*      */   
/*      */   private static ResourceResolver createResourceResolver(ISvgConverterProperties props) {
/* 1023 */     if (props == null) {
/* 1024 */       return new ResourceResolver(null);
/*      */     }
/*      */ 
/*      */     
/* 1028 */     if (props instanceof SvgConverterProperties) {
/* 1029 */       return new ResourceResolver(props.getBaseUri(), ((SvgConverterProperties)props).getResourceRetriever());
/*      */     }
/* 1031 */     return new ResourceResolver(props.getBaseUri(), null);
/*      */   }
/*      */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/svg/converter/SvgConverter.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */