/*    */ package com.itextpdf.layout.renderer;
/*    */ 
/*    */ import com.itextpdf.io.util.MessageFormatUtil;
/*    */ import com.itextpdf.layout.element.Link;
/*    */ import com.itextpdf.layout.element.Text;
/*    */ import org.slf4j.Logger;
/*    */ import org.slf4j.LoggerFactory;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class LinkRenderer
/*    */   extends TextRenderer
/*    */ {
/*    */   public LinkRenderer(Link link) {
/* 60 */     this(link, link.getText());
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public LinkRenderer(Link linkElement, String text) {
/* 71 */     super((Text)linkElement, text);
/*    */   }
/*    */ 
/*    */   
/*    */   public void draw(DrawContext drawContext) {
/* 76 */     if (this.occupiedArea == null) {
/* 77 */       Logger logger = LoggerFactory.getLogger(LinkRenderer.class);
/* 78 */       logger.error(MessageFormatUtil.format("Occupied area has not been initialized. {0}", new Object[] { "Drawing won't be performed." }));
/*    */       return;
/*    */     } 
/* 81 */     super.draw(drawContext);
/*    */     
/* 83 */     boolean isRelativePosition = isRelativePosition();
/* 84 */     if (isRelativePosition) {
/* 85 */       applyRelativePositioningTranslation(false);
/*    */     }
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public IRenderer getNextRenderer() {
/* 93 */     return new LinkRenderer((Link)this.modelElement);
/*    */   }
/*    */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/layout/renderer/LinkRenderer.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */