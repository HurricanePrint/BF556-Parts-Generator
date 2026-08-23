/*     */ package com.itextpdf.layout.renderer;
/*     */ 
/*     */ import com.itextpdf.io.util.MessageFormatUtil;
/*     */ import com.itextpdf.kernel.geom.Rectangle;
/*     */ import com.itextpdf.kernel.pdf.canvas.CanvasArtifact;
/*     */ import com.itextpdf.kernel.pdf.canvas.CanvasTag;
/*     */ import com.itextpdf.kernel.pdf.canvas.draw.ILineDrawer;
/*     */ import com.itextpdf.layout.element.IElement;
/*     */ import com.itextpdf.layout.element.Tab;
/*     */ import com.itextpdf.layout.layout.LayoutArea;
/*     */ import com.itextpdf.layout.layout.LayoutContext;
/*     */ import com.itextpdf.layout.layout.LayoutResult;
/*     */ import com.itextpdf.layout.property.UnitValue;
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
/*     */ public class TabRenderer
/*     */   extends AbstractRenderer
/*     */ {
/*     */   public TabRenderer(Tab tab) {
/*  68 */     super((IElement)tab);
/*     */   }
/*     */ 
/*     */   
/*     */   public LayoutResult layout(LayoutContext layoutContext) {
/*  73 */     LayoutArea area = layoutContext.getArea();
/*  74 */     Float width = retrieveWidth(area.getBBox().getWidth());
/*  75 */     UnitValue height = getProperty(85);
/*  76 */     this
/*  77 */       .occupiedArea = new LayoutArea(area.getPageNumber(), new Rectangle(area.getBBox().getX(), area.getBBox().getY() + area.getBBox().getHeight(), width.floatValue(), height.getValue()));
/*     */     
/*  79 */     return new LayoutResult(1, this.occupiedArea, null, null);
/*     */   }
/*     */ 
/*     */   
/*     */   public void draw(DrawContext drawContext) {
/*  84 */     if (this.occupiedArea == null) {
/*  85 */       Logger logger = LoggerFactory.getLogger(TabRenderer.class);
/*  86 */       logger.error(MessageFormatUtil.format("Occupied area has not been initialized. {0}", new Object[] { "Drawing won't be performed." }));
/*     */       return;
/*     */     } 
/*  89 */     ILineDrawer leader = getProperty(68);
/*  90 */     if (leader == null) {
/*     */       return;
/*     */     }
/*  93 */     boolean isTagged = drawContext.isTaggingEnabled();
/*  94 */     if (isTagged) {
/*  95 */       drawContext.getCanvas().openTag((CanvasTag)new CanvasArtifact());
/*     */     }
/*     */     
/*  98 */     beginElementOpacityApplying(drawContext);
/*  99 */     leader.draw(drawContext.getCanvas(), this.occupiedArea.getBBox());
/* 100 */     endElementOpacityApplying(drawContext);
/*     */     
/* 102 */     if (isTagged) {
/* 103 */       drawContext.getCanvas().closeTag();
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public IRenderer getNextRenderer() {
/* 109 */     return new TabRenderer((Tab)this.modelElement);
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/layout/renderer/TabRenderer.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */