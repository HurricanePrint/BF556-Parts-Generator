/*    */ package com.itextpdf.svg.renderers.path.impl;
/*    */ 
/*    */ import com.itextpdf.svg.renderers.path.IPathShape;
/*    */ import com.itextpdf.svg.renderers.path.IPathShapeMapper;
/*    */ import java.util.HashMap;
/*    */ import java.util.Map;
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
/*    */ public class PathShapeMapper
/*    */   implements IPathShapeMapper
/*    */ {
/*    */   public Map<String, IPathShape> getMapping() {
/* 61 */     Map<String, IPathShape> result = new HashMap<>();
/* 62 */     result.put("L", new LineTo());
/* 63 */     result.put("l", new LineTo(true));
/* 64 */     result.put("V", new VerticalLineTo());
/* 65 */     result.put("v", new VerticalLineTo(true));
/* 66 */     result.put("H", new HorizontalLineTo());
/* 67 */     result.put("h", new HorizontalLineTo(true));
/* 68 */     result.put("Z", new ClosePath());
/* 69 */     result.put("Z".toLowerCase(), new ClosePath());
/* 70 */     result.put("M", new MoveTo());
/* 71 */     result.put("m", new MoveTo(true));
/* 72 */     result.put("C", new CurveTo());
/* 73 */     result.put("c", new CurveTo(true));
/* 74 */     result.put("S", new SmoothSCurveTo());
/* 75 */     result.put("s", new SmoothSCurveTo(true));
/* 76 */     result.put("Q", new QuadraticCurveTo());
/* 77 */     result.put("q", new QuadraticCurveTo(true));
/* 78 */     result.put("T", new QuadraticSmoothCurveTo());
/* 79 */     result.put("t", new QuadraticSmoothCurveTo(true));
/* 80 */     result.put("A", new EllipticalCurveTo());
/* 81 */     result.put("a", new EllipticalCurveTo(true));
/* 82 */     return result;
/*    */   }
/*    */ 
/*    */   
/*    */   public Map<String, Integer> getArgumentCount() {
/* 87 */     Map<String, Integer> result = new HashMap<>();
/* 88 */     result.put("L", Integer.valueOf(2));
/* 89 */     result.put("V", Integer.valueOf(1));
/* 90 */     result.put("H", Integer.valueOf(1));
/* 91 */     result.put("Z", Integer.valueOf(0));
/* 92 */     result.put("M", Integer.valueOf(2));
/* 93 */     result.put("C", Integer.valueOf(6));
/* 94 */     result.put("S", Integer.valueOf(4));
/* 95 */     result.put("Q", Integer.valueOf(4));
/* 96 */     result.put("T", Integer.valueOf(2));
/* 97 */     result.put("A", Integer.valueOf(7));
/* 98 */     return result;
/*    */   }
/*    */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/svg/renderers/path/impl/PathShapeMapper.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */