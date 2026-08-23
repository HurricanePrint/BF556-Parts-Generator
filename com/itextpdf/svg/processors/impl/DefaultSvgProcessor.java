/*     */ package com.itextpdf.svg.processors.impl;
/*     */ 
/*     */ import com.itextpdf.styledxmlparser.css.ICssResolver;
/*     */ import com.itextpdf.styledxmlparser.css.resolve.AbstractCssContext;
/*     */ import com.itextpdf.styledxmlparser.node.IElementNode;
/*     */ import com.itextpdf.styledxmlparser.node.INode;
/*     */ import com.itextpdf.styledxmlparser.node.ITextNode;
/*     */ import com.itextpdf.svg.SvgConstants;
/*     */ import com.itextpdf.svg.css.SvgCssContext;
/*     */ import com.itextpdf.svg.css.impl.SvgStyleResolver;
/*     */ import com.itextpdf.svg.exceptions.SvgProcessingException;
/*     */ import com.itextpdf.svg.processors.ISvgConverterProperties;
/*     */ import com.itextpdf.svg.processors.ISvgProcessor;
/*     */ import com.itextpdf.svg.processors.ISvgProcessorResult;
/*     */ import com.itextpdf.svg.processors.impl.font.SvgFontProcessor;
/*     */ import com.itextpdf.svg.renderers.IBranchSvgNodeRenderer;
/*     */ import com.itextpdf.svg.renderers.ISvgNodeRenderer;
/*     */ import com.itextpdf.svg.renderers.factories.DefaultSvgNodeRendererFactory;
/*     */ import com.itextpdf.svg.renderers.factories.ISvgNodeRendererFactory;
/*     */ import com.itextpdf.svg.renderers.impl.ISvgTextNodeRenderer;
/*     */ import com.itextpdf.svg.renderers.impl.LinearGradientSvgNodeRenderer;
/*     */ import com.itextpdf.svg.renderers.impl.TextLeafSvgNodeRenderer;
/*     */ import com.itextpdf.svg.renderers.impl.TextSvgBranchRenderer;
/*     */ import com.itextpdf.svg.utils.SvgTextUtil;
/*     */ import java.util.HashMap;
/*     */ import java.util.LinkedList;
/*     */ import java.util.Map;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class DefaultSvgProcessor
/*     */   implements ISvgProcessor
/*     */ {
/*     */   private ProcessorState processorState;
/*     */   private ICssResolver cssResolver;
/*     */   private ISvgNodeRendererFactory rendererFactory;
/*     */   private Map<String, ISvgNodeRenderer> namedObjects;
/*     */   private SvgCssContext cssContext;
/*     */   private SvgProcessorContext context;
/*     */   
/*     */   public ISvgProcessorResult process(INode root, ISvgConverterProperties converterProps) throws SvgProcessingException {
/*  99 */     if (root == null) {
/* 100 */       throw new SvgProcessingException("Input root value is null");
/*     */     }
/* 102 */     if (converterProps == null) {
/* 103 */       converterProps = new SvgConverterProperties();
/*     */     }
/*     */     
/* 106 */     performSetup(root, converterProps);
/*     */ 
/*     */     
/* 109 */     IElementNode svgRoot = findFirstElement(root, "svg");
/*     */     
/* 111 */     if (svgRoot != null) {
/*     */       
/* 113 */       executeDepthFirstTraversal((INode)svgRoot);
/* 114 */       ISvgNodeRenderer rootSvgRenderer = createResultAndClean();
/* 115 */       return new SvgProcessorResult(this.namedObjects, rootSvgRenderer, this.context);
/*     */     } 
/* 117 */     throw new SvgProcessingException("No root found");
/*     */   }
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public ISvgProcessorResult process(INode root) throws SvgProcessingException {
/* 123 */     return process(root, null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void performSetup(INode root, ISvgConverterProperties converterProps) {
/* 132 */     this.processorState = new ProcessorState();
/* 133 */     if (converterProps.getRendererFactory() != null) {
/* 134 */       this.rendererFactory = converterProps.getRendererFactory();
/*     */     } else {
/* 136 */       this.rendererFactory = (ISvgNodeRendererFactory)new DefaultSvgNodeRendererFactory();
/*     */     } 
/* 138 */     this.context = new SvgProcessorContext(converterProps);
/* 139 */     this.cssResolver = (ICssResolver)new SvgStyleResolver(root, this.context);
/* 140 */     (new SvgFontProcessor(this.context)).addFontFaceFonts(this.cssResolver);
/*     */     
/* 142 */     this.namedObjects = new HashMap<>();
/* 143 */     this.cssContext = new SvgCssContext();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void executeDepthFirstTraversal(INode startingNode) {
/* 153 */     if (startingNode instanceof IElementNode && !this.rendererFactory.isTagIgnored((IElementNode)startingNode)) {
/* 154 */       IElementNode rootElementNode = (IElementNode)startingNode;
/*     */       
/* 156 */       ISvgNodeRenderer startingRenderer = this.rendererFactory.createSvgNodeRendererForTag(rootElementNode, null);
/* 157 */       if (startingRenderer != null) {
/* 158 */         Map<String, String> attributesAndStyles = this.cssResolver.resolveStyles(startingNode, (AbstractCssContext)this.cssContext);
/* 159 */         rootElementNode.setStyles(attributesAndStyles);
/* 160 */         startingRenderer.setAttributesAndStyles(attributesAndStyles);
/* 161 */         this.processorState.push(startingRenderer);
/* 162 */         for (INode rootChild : startingNode.childNodes()) {
/* 163 */           visit(rootChild);
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
/*     */   private ISvgNodeRenderer createResultAndClean() {
/* 175 */     return this.processorState.pop();
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
/*     */   private void visit(INode node) {
/* 190 */     if (node instanceof IElementNode) {
/* 191 */       IElementNode element = (IElementNode)node;
/*     */       
/* 193 */       if (!this.rendererFactory.isTagIgnored(element)) {
/* 194 */         ISvgNodeRenderer parentRenderer = this.processorState.top();
/* 195 */         ISvgNodeRenderer renderer = this.rendererFactory.createSvgNodeRendererForTag(element, parentRenderer);
/* 196 */         if (renderer != null) {
/*     */           Map<String, String> styles;
/* 198 */           if (this.cssResolver instanceof SvgStyleResolver && 
/* 199 */             onlyNativeStylesShouldBeResolved(element)) {
/* 200 */             styles = ((SvgStyleResolver)this.cssResolver).resolveNativeStyles(node, (AbstractCssContext)this.cssContext);
/*     */           } else {
/* 202 */             styles = this.cssResolver.resolveStyles(node, (AbstractCssContext)this.cssContext);
/*     */           } 
/*     */           
/* 205 */           element.setStyles(styles);
/*     */           
/* 207 */           renderer.setAttributesAndStyles(styles);
/*     */           
/* 209 */           String attribute = renderer.getAttribute("id");
/* 210 */           if (attribute != null) {
/* 211 */             this.namedObjects.put(attribute, renderer);
/*     */           }
/*     */           
/* 214 */           if (renderer instanceof com.itextpdf.svg.renderers.impl.StopSvgNodeRenderer) {
/* 215 */             if (parentRenderer instanceof LinearGradientSvgNodeRenderer)
/*     */             {
/*     */               
/* 218 */               ((LinearGradientSvgNodeRenderer)parentRenderer).addChild(renderer);
/*     */             
/*     */             }
/*     */           }
/* 222 */           else if (!(renderer instanceof com.itextpdf.svg.renderers.INoDrawSvgNodeRenderer) && !(parentRenderer instanceof com.itextpdf.svg.renderers.impl.DefsSvgNodeRenderer)) {
/* 223 */             if (parentRenderer instanceof IBranchSvgNodeRenderer) {
/* 224 */               ((IBranchSvgNodeRenderer)parentRenderer).addChild(renderer);
/* 225 */             } else if (parentRenderer instanceof TextSvgBranchRenderer && renderer instanceof ISvgTextNodeRenderer) {
/*     */               
/* 227 */               ((TextSvgBranchRenderer)parentRenderer).addChild((ISvgTextNodeRenderer)renderer);
/*     */             } 
/*     */           } 
/*     */           
/* 231 */           this.processorState.push(renderer);
/*     */         } 
/*     */         
/* 234 */         for (INode childNode : element.childNodes()) {
/* 235 */           visit(childNode);
/*     */         }
/*     */         
/* 238 */         if (renderer != null) {
/* 239 */           this.processorState.pop();
/*     */         }
/*     */       } 
/* 242 */     } else if (processAsText(node)) {
/* 243 */       processText((ITextNode)node);
/*     */     } 
/*     */   }
/*     */   
/*     */   private static boolean onlyNativeStylesShouldBeResolved(IElementNode element) {
/* 248 */     return (!SvgConstants.Tags.LINEAR_GRADIENT.equals(element.name()) && 
/* 249 */       !"marker".equals(element.name()) && 
/* 250 */       isElementNested(element, "defs") && 
/* 251 */       !isElementNested(element, "marker"));
/*     */   }
/*     */   
/*     */   private static boolean isElementNested(IElementNode element, String parentElementNameForSearch) {
/* 255 */     if (!(element.parentNode() instanceof IElementNode)) {
/* 256 */       return false;
/*     */     }
/* 258 */     IElementNode parentElement = (IElementNode)element.parentNode();
/* 259 */     if (parentElement.name().equals(parentElementNameForSearch)) {
/* 260 */       return true;
/*     */     }
/* 262 */     if (element.parentNode() != null) {
/* 263 */       return isElementNested(parentElement, parentElementNameForSearch);
/*     */     }
/* 265 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean processAsText(INode node) {
/* 275 */     return node instanceof ITextNode;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void processText(ITextNode textNode) {
/* 284 */     ISvgNodeRenderer parentRenderer = this.processorState.top();
/*     */     
/* 286 */     if (parentRenderer instanceof TextSvgBranchRenderer) {
/* 287 */       String wholeText = textNode.wholeText();
/* 288 */       if (!"".equals(wholeText) && !SvgTextUtil.isOnlyWhiteSpace(wholeText)) {
/* 289 */         TextLeafSvgNodeRenderer textLeaf = new TextLeafSvgNodeRenderer();
/* 290 */         textLeaf.setParent(parentRenderer);
/* 291 */         textLeaf.setAttribute("text_content", wholeText);
/* 292 */         ((TextSvgBranchRenderer)parentRenderer).addChild((ISvgTextNodeRenderer)textLeaf);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   IElementNode findFirstElement(INode node, String tagName) {
/* 305 */     LinkedList<INode> q = new LinkedList<>();
/* 306 */     q.add(node);
/*     */     
/* 308 */     while (!q.isEmpty()) {
/* 309 */       INode currentNode = q.getFirst();
/* 310 */       q.removeFirst();
/*     */       
/* 312 */       if (currentNode == null) {
/* 313 */         return null;
/*     */       }
/*     */       
/* 316 */       if (currentNode instanceof IElementNode && ((IElementNode)currentNode).name() != null && ((IElementNode)currentNode).name().equals(tagName)) {
/* 317 */         return (IElementNode)currentNode;
/*     */       }
/*     */       
/* 320 */       for (INode child : currentNode.childNodes()) {
/* 321 */         if (child instanceof IElementNode) {
/* 322 */           q.add(child);
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 327 */     return null;
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/svg/processors/impl/DefaultSvgProcessor.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */