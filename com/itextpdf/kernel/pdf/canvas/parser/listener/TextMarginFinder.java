/*    */ package com.itextpdf.kernel.pdf.canvas.parser.listener;
/*    */ 
/*    */ import com.itextpdf.io.util.MessageFormatUtil;
/*    */ import com.itextpdf.kernel.geom.Rectangle;
/*    */ import com.itextpdf.kernel.pdf.canvas.parser.EventType;
/*    */ import com.itextpdf.kernel.pdf.canvas.parser.data.IEventData;
/*    */ import com.itextpdf.kernel.pdf.canvas.parser.data.TextRenderInfo;
/*    */ import java.util.Collections;
/*    */ import java.util.LinkedHashSet;
/*    */ import java.util.Set;
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
/*    */ public class TextMarginFinder
/*    */   implements IEventListener
/*    */ {
/* 61 */   private Rectangle textRectangle = null;
/*    */ 
/*    */   
/*    */   public void eventOccurred(IEventData data, EventType type) {
/* 65 */     if (type == EventType.RENDER_TEXT) {
/* 66 */       TextRenderInfo info = (TextRenderInfo)data;
/* 67 */       if (this.textRectangle == null) {
/* 68 */         this.textRectangle = info.getDescentLine().getBoundingRectangle();
/*    */       } else {
/* 70 */         this.textRectangle = Rectangle.getCommonRectangle(new Rectangle[] { this.textRectangle, info.getDescentLine().getBoundingRectangle() });
/*    */       } 
/* 72 */       this.textRectangle = Rectangle.getCommonRectangle(new Rectangle[] { this.textRectangle, info.getAscentLine().getBoundingRectangle() });
/*    */     } else {
/* 74 */       throw new IllegalStateException(MessageFormatUtil.format("Event type not supported: {0}", new Object[] { type }));
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public Set<EventType> getSupportedEvents() {
/* 80 */     return new LinkedHashSet<>(Collections.singletonList(EventType.RENDER_TEXT));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Rectangle getTextRectangle() {
/* 89 */     return this.textRectangle;
/*    */   }
/*    */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/kernel/pdf/canvas/parser/listener/TextMarginFinder.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */