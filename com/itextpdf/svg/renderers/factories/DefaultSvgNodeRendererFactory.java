/*     */ package com.itextpdf.svg.renderers.factories;
/*     */ 
/*     */ import com.itextpdf.io.util.MessageFormatUtil;
/*     */ import com.itextpdf.styledxmlparser.node.IElementNode;
/*     */ import com.itextpdf.svg.exceptions.SvgProcessingException;
/*     */ import com.itextpdf.svg.renderers.ISvgNodeRenderer;
/*     */ import java.util.Collection;
/*     */ import java.util.HashMap;
/*     */ import java.util.HashSet;
/*     */ import java.util.Map;
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
/*     */ public class DefaultSvgNodeRendererFactory
/*     */   implements ISvgNodeRendererFactory
/*     */ {
/*  67 */   private Map<String, Class<? extends ISvgNodeRenderer>> rendererMap = new HashMap<>();
/*  68 */   private Collection<String> ignoredTags = new HashSet<>();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DefaultSvgNodeRendererFactory() {
/*  74 */     this(new DefaultSvgNodeRendererMapper());
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
/*     */   @Deprecated
/*     */   public DefaultSvgNodeRendererFactory(ISvgNodeRendererMapper mapper) {
/*  89 */     if (mapper != null) {
/*  90 */       this.rendererMap.putAll(mapper.getMapping());
/*  91 */       this.ignoredTags.addAll(mapper.getIgnoredTags());
/*     */     } else {
/*  93 */       ISvgNodeRendererMapper defaultMapper = new DefaultSvgNodeRendererMapper();
/*  94 */       this.rendererMap.putAll(defaultMapper.getMapping());
/*  95 */       this.ignoredTags.addAll(defaultMapper.getIgnoredTags());
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public ISvgNodeRenderer createSvgNodeRendererForTag(IElementNode tag, ISvgNodeRenderer parent) {
/*     */     ISvgNodeRenderer result;
/* 103 */     if (tag == null) {
/* 104 */       throw new SvgProcessingException("Tag parameter must not be null");
/*     */     }
/*     */     
/*     */     try {
/* 108 */       Class<? extends ISvgNodeRenderer> clazz = this.rendererMap.get(tag.name());
/*     */       
/* 110 */       if (clazz == null) {
/* 111 */         Logger logger = LoggerFactory.getLogger(getClass());
/* 112 */         logger.warn(MessageFormatUtil.format("Could not find implementation for tag {0}", new Object[] { tag.name() }));
/* 113 */         return null;
/*     */       } 
/*     */       
/* 116 */       result = ((Class<ISvgNodeRenderer>)this.rendererMap.get(tag.name())).newInstance();
/* 117 */     } catch (ReflectiveOperationException ex) {
/* 118 */       throw (new SvgProcessingException("Could not instantiate Renderer for tag {0}", ex)).setMessageParams(new Object[] { tag.name() });
/*     */     } 
/*     */ 
/*     */     
/* 122 */     if (parent != null && !(result instanceof com.itextpdf.svg.renderers.INoDrawSvgNodeRenderer) && !(parent instanceof com.itextpdf.svg.renderers.impl.DefsSvgNodeRenderer)) {
/* 123 */       result.setParent(parent);
/*     */     }
/*     */     
/* 126 */     return result;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isTagIgnored(IElementNode tag) {
/* 131 */     return this.ignoredTags.contains(tag.name());
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/svg/renderers/factories/DefaultSvgNodeRendererFactory.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */