/*    */ package com.itextpdf.kernel.pdf.canvas.parser.listener;
/*    */ 
/*    */ import com.itextpdf.kernel.pdf.canvas.parser.EventType;
/*    */ import com.itextpdf.kernel.pdf.canvas.parser.data.IEventData;
/*    */ import com.itextpdf.kernel.pdf.canvas.parser.data.TextRenderInfo;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class GlyphEventListener
/*    */   implements IEventListener
/*    */ {
/*    */   protected final IEventListener delegate;
/*    */   
/*    */   public GlyphEventListener(IEventListener delegate) {
/* 65 */     this.delegate = delegate;
/*    */   }
/*    */ 
/*    */   
/*    */   public void eventOccurred(IEventData data, EventType type) {
/* 70 */     if (type.equals(EventType.RENDER_TEXT)) {
/* 71 */       TextRenderInfo textRenderInfo = (TextRenderInfo)data;
/* 72 */       for (TextRenderInfo glyphRenderInfo : textRenderInfo.getCharacterRenderInfos()) {
/* 73 */         this.delegate.eventOccurred((IEventData)glyphRenderInfo, type);
/*    */       }
/*    */     } else {
/* 76 */       this.delegate.eventOccurred(data, type);
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public Set<EventType> getSupportedEvents() {
/* 82 */     return this.delegate.getSupportedEvents();
/*    */   }
/*    */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/kernel/pdf/canvas/parser/listener/GlyphEventListener.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */