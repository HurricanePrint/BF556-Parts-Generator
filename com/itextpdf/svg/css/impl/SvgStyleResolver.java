/*     */ package com.itextpdf.svg.css.impl;
/*     */ 
/*     */ import com.itextpdf.io.util.ResourceUtil;
/*     */ import com.itextpdf.styledxmlparser.css.CssDeclaration;
/*     */ import com.itextpdf.styledxmlparser.css.CssFontFaceRule;
/*     */ import com.itextpdf.styledxmlparser.css.CssStatement;
/*     */ import com.itextpdf.styledxmlparser.css.CssStyleSheet;
/*     */ import com.itextpdf.styledxmlparser.css.ICssResolver;
/*     */ import com.itextpdf.styledxmlparser.css.media.CssMediaRule;
/*     */ import com.itextpdf.styledxmlparser.css.media.MediaDeviceDescription;
/*     */ import com.itextpdf.styledxmlparser.css.parse.CssRuleSetParser;
/*     */ import com.itextpdf.styledxmlparser.css.parse.CssStyleSheetParser;
/*     */ import com.itextpdf.styledxmlparser.css.resolve.AbstractCssContext;
/*     */ import com.itextpdf.styledxmlparser.css.resolve.CssInheritance;
/*     */ import com.itextpdf.styledxmlparser.css.resolve.IStyleInheritance;
/*     */ import com.itextpdf.styledxmlparser.css.util.CssUtils;
/*     */ import com.itextpdf.styledxmlparser.node.IAttribute;
/*     */ import com.itextpdf.styledxmlparser.node.IDataNode;
/*     */ import com.itextpdf.styledxmlparser.node.IElementNode;
/*     */ import com.itextpdf.styledxmlparser.node.INode;
/*     */ import com.itextpdf.styledxmlparser.node.IStylesContainer;
/*     */ import com.itextpdf.styledxmlparser.node.ITextNode;
/*     */ import com.itextpdf.styledxmlparser.resolver.resource.ResourceResolver;
/*     */ import com.itextpdf.styledxmlparser.util.StyleUtil;
/*     */ import com.itextpdf.svg.processors.ISvgConverterProperties;
/*     */ import com.itextpdf.svg.processors.impl.SvgConverterProperties;
/*     */ import com.itextpdf.svg.processors.impl.SvgProcessorContext;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.net.MalformedURLException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.HashSet;
/*     */ import java.util.LinkedList;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SvgStyleResolver
/*     */   implements ICssResolver
/*     */ {
/*     */   private CssStyleSheet css;
/*     */   private static final String DEFAULT_CSS_PATH = "com/itextpdf/svg/default.css";
/*     */   private MediaDeviceDescription deviceDescription;
/* 105 */   private List<CssFontFaceRule> fonts = new ArrayList<>();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private ResourceResolver resourceResolver;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public SvgStyleResolver(InputStream defaultCssStream) throws IOException {
/* 121 */     this(defaultCssStream, new SvgProcessorContext((ISvgConverterProperties)new SvgConverterProperties()));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public SvgStyleResolver() {
/* 132 */     this(new SvgProcessorContext((ISvgConverterProperties)new SvgConverterProperties()));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SvgStyleResolver(InputStream defaultCssStream, SvgProcessorContext context) throws IOException {
/* 142 */     this.css = CssStyleSheetParser.parse(defaultCssStream);
/* 143 */     this.resourceResolver = context.getResourceResolver();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SvgStyleResolver(SvgProcessorContext context) {
/* 152 */     try (InputStream defaultCss = ResourceUtil.getResourceStream("com/itextpdf/svg/default.css")) {
/* 153 */       this.css = CssStyleSheetParser.parse(defaultCss);
/* 154 */     } catch (IOException e) {
/* 155 */       Logger logger = LoggerFactory.getLogger(getClass());
/* 156 */       logger.warn("Error loading the default CSS. Initializing an empty style sheet.", e);
/* 157 */       this.css = new CssStyleSheet();
/*     */     } 
/* 159 */     this.resourceResolver = context.getResourceResolver();
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
/*     */   public SvgStyleResolver(INode rootNode, SvgProcessorContext context) {
/* 171 */     this.deviceDescription = context.getDeviceDescription();
/* 172 */     this.resourceResolver = context.getResourceResolver();
/* 173 */     collectCssDeclarations(rootNode, this.resourceResolver);
/* 174 */     collectFonts();
/*     */   }
/*     */ 
/*     */   
/*     */   public Map<String, String> resolveStyles(INode node, AbstractCssContext context) {
/* 179 */     Map<String, String> styles = resolveNativeStyles(node, context);
/*     */ 
/*     */     
/* 182 */     if (node.parentNode() instanceof IStylesContainer) {
/* 183 */       IStylesContainer parentNode = (IStylesContainer)node.parentNode();
/* 184 */       Map<String, String> parentStyles = parentNode.getStyles();
/*     */       
/* 186 */       if (parentStyles == null && !(node.parentNode() instanceof com.itextpdf.styledxmlparser.node.IDocumentNode)) {
/* 187 */         Logger logger = LoggerFactory.getLogger(SvgStyleResolver.class);
/* 188 */         logger.error("Element parent styles are not resolved. Styles for current element might be incorrect.");
/*     */       } 
/*     */       
/* 191 */       Set<IStyleInheritance> inheritanceRules = new HashSet<>();
/* 192 */       inheritanceRules.add(new CssInheritance());
/* 193 */       inheritanceRules.add(new SvgAttributeInheritance());
/*     */       
/* 195 */       if (parentStyles != null) {
/* 196 */         for (Map.Entry<String, String> entry : parentStyles.entrySet()) {
/* 197 */           String parentFontSizeString = parentStyles.get("font-size");
/* 198 */           if (parentFontSizeString == null) {
/* 199 */             parentFontSizeString = "0";
/*     */           }
/*     */           
/* 202 */           styles = StyleUtil.mergeParentStyleDeclaration(styles, entry.getKey(), entry.getValue(), parentFontSizeString, inheritanceRules);
/*     */         } 
/*     */       }
/*     */     } 
/* 206 */     return styles;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Map<String, String> resolveNativeStyles(INode node, AbstractCssContext cssContext) {
/* 217 */     Map<String, String> styles = new HashMap<>();
/*     */ 
/*     */     
/* 220 */     List<CssDeclaration> styleSheetDeclarations = this.css.getCssDeclarations(node, MediaDeviceDescription.createDefault());
/* 221 */     for (CssDeclaration ssd : styleSheetDeclarations) {
/* 222 */       styles.put(ssd.getProperty(), ssd.getExpression());
/*     */     }
/*     */ 
/*     */     
/* 226 */     if (node instanceof IElementNode) {
/* 227 */       IElementNode eNode = (IElementNode)node;
/* 228 */       for (IAttribute attr : eNode.getAttributes()) {
/* 229 */         processAttribute(attr, styles);
/*     */       }
/*     */     } 
/* 232 */     return styles;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void processXLink(IAttribute attr, Map<String, String> attributesMap) {
/* 243 */     String xlinkValue = attr.getValue();
/* 244 */     if (!isStartedWithHash(xlinkValue)) {
/*     */       try {
/* 246 */         xlinkValue = this.resourceResolver.resolveAgainstBaseUri(attr.getValue()).toExternalForm();
/* 247 */       } catch (MalformedURLException mue) {
/* 248 */         Logger logger = LoggerFactory.getLogger(SvgStyleResolver.class);
/* 249 */         logger.error("Unable to resolve image path with given base URI ({0}) and image source path ({1})", mue);
/*     */       } 
/*     */     }
/* 252 */     attributesMap.put(attr.getKey(), xlinkValue);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean isStartedWithHash(String s) {
/* 262 */     return (s != null && s.startsWith("#"));
/*     */   }
/*     */   
/*     */   private void collectCssDeclarations(INode rootNode, ResourceResolver resourceResolver) {
/* 266 */     this.css = new CssStyleSheet();
/* 267 */     LinkedList<INode> q = new LinkedList<>();
/* 268 */     if (rootNode != null) {
/* 269 */       q.add(rootNode);
/*     */     }
/* 271 */     while (!q.isEmpty()) {
/* 272 */       INode currentNode = q.pop();
/* 273 */       if (currentNode instanceof IElementNode) {
/* 274 */         IElementNode headChildElement = (IElementNode)currentNode;
/* 275 */         if ("style".equals(headChildElement.name())) {
/* 276 */           if (!currentNode.childNodes().isEmpty() && (currentNode.childNodes().get(0) instanceof IDataNode || currentNode
/* 277 */             .childNodes().get(0) instanceof ITextNode)) {
/*     */             String styleData;
/* 279 */             if (currentNode.childNodes().get(0) instanceof IDataNode) {
/* 280 */               styleData = ((IDataNode)currentNode.childNodes().get(0)).getWholeData();
/*     */             } else {
/* 282 */               styleData = ((ITextNode)currentNode.childNodes().get(0)).wholeText();
/*     */             } 
/* 284 */             CssStyleSheet styleSheet = CssStyleSheetParser.parse(styleData);
/*     */ 
/*     */             
/* 287 */             this.css.appendCssStyleSheet(styleSheet);
/*     */           }
/*     */         
/* 290 */         } else if (CssUtils.isStyleSheetLink(headChildElement)) {
/* 291 */           String styleSheetUri = headChildElement.getAttribute("href");
/* 292 */           try (InputStream stream = resourceResolver.retrieveResourceAsInputStream(styleSheetUri)) {
/* 293 */             if (stream != null) {
/* 294 */               CssStyleSheet styleSheet = CssStyleSheetParser.parse(stream, resourceResolver
/* 295 */                   .resolveAgainstBaseUri(styleSheetUri).toExternalForm());
/* 296 */               this.css.appendCssStyleSheet(styleSheet);
/*     */             } 
/* 298 */           } catch (Exception exc) {
/* 299 */             Logger logger = LoggerFactory.getLogger(SvgStyleResolver.class);
/* 300 */             logger.error("Unable to process external css file", exc);
/*     */           } 
/*     */         } 
/*     */       } 
/* 304 */       for (INode child : currentNode.childNodes()) {
/* 305 */         if (child instanceof IElementNode) {
/* 306 */           q.add(child);
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List<CssFontFaceRule> getFonts() {
/* 318 */     return new ArrayList<>(this.fonts);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void collectFonts() {
/* 325 */     for (CssStatement cssStatement : this.css.getStatements()) {
/* 326 */       collectFonts(cssStatement);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void collectFonts(CssStatement cssStatement) {
/* 336 */     if (cssStatement instanceof CssFontFaceRule) {
/* 337 */       this.fonts.add((CssFontFaceRule)cssStatement);
/* 338 */     } else if (cssStatement instanceof CssMediaRule && ((CssMediaRule)cssStatement)
/* 339 */       .matchMediaDevice(this.deviceDescription)) {
/* 340 */       for (CssStatement cssSubStatement : ((CssMediaRule)cssStatement).getStatements()) {
/* 341 */         collectFonts(cssSubStatement);
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   private void processAttribute(IAttribute attr, Map<String, String> styles) {
/*     */     Map<String, String> parsed;
/* 348 */     switch (attr.getKey()) {
/*     */       case "style":
/* 350 */         parsed = parseStylesFromStyleAttribute(attr.getValue());
/* 351 */         for (Map.Entry<String, String> style : parsed.entrySet()) {
/* 352 */           styles.put(style.getKey(), style.getValue());
/*     */         }
/*     */         return;
/*     */       case "xlink:href":
/* 356 */         processXLink(attr, styles);
/*     */         return;
/*     */     } 
/* 359 */     styles.put(attr.getKey(), attr.getValue());
/*     */   }
/*     */ 
/*     */   
/*     */   private Map<String, String> parseStylesFromStyleAttribute(String style) {
/* 364 */     Map<String, String> parsed = new HashMap<>();
/* 365 */     List<CssDeclaration> declarations = CssRuleSetParser.parsePropertyDeclarations(style);
/* 366 */     for (CssDeclaration declaration : declarations) {
/* 367 */       parsed.put(declaration.getProperty(), declaration.getExpression());
/*     */     }
/* 369 */     return parsed;
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/svg/css/impl/SvgStyleResolver.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */