/*    */ package com.itextpdf.kernel.pdf.canvas.parser.filter;
/*    */ 
/*    */ import com.itextpdf.kernel.geom.LineSegment;
/*    */ import com.itextpdf.kernel.geom.Rectangle;
/*    */ import com.itextpdf.kernel.geom.Vector;
/*    */ import com.itextpdf.kernel.pdf.canvas.parser.EventType;
/*    */ import com.itextpdf.kernel.pdf.canvas.parser.data.IEventData;
/*    */ import com.itextpdf.kernel.pdf.canvas.parser.data.TextRenderInfo;
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
/*    */ public class TextRegionEventFilter
/*    */   implements IEventFilter
/*    */ {
/*    */   private final Rectangle filterRect;
/*    */   
/*    */   public TextRegionEventFilter(Rectangle filterRect) {
/* 66 */     this.filterRect = filterRect;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean accept(IEventData data, EventType type) {
/* 71 */     if (type.equals(EventType.RENDER_TEXT)) {
/* 72 */       TextRenderInfo renderInfo = (TextRenderInfo)data;
/*    */       
/* 74 */       LineSegment segment = renderInfo.getBaseline();
/* 75 */       Vector startPoint = segment.getStartPoint();
/* 76 */       Vector endPoint = segment.getEndPoint();
/*    */       
/* 78 */       float x1 = startPoint.get(0);
/* 79 */       float y1 = startPoint.get(1);
/* 80 */       float x2 = endPoint.get(0);
/* 81 */       float y2 = endPoint.get(1);
/*    */       
/* 83 */       return (this.filterRect == null || this.filterRect.intersectsLine(x1, y1, x2, y2));
/*    */     } 
/* 85 */     return false;
/*    */   }
/*    */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/kernel/pdf/canvas/parser/filter/TextRegionEventFilter.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */