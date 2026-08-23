/*    */ package com.itextpdf.svg.renderers.path;
/*    */ 
/*    */ import com.itextpdf.svg.renderers.path.impl.PathShapeMapper;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class SvgPathShapeFactory
/*    */ {
/*    */   public static IPathShape createPathShape(String name) {
/* 64 */     return (IPathShape)(new PathShapeMapper()).getMapping().get(name);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static int getArgumentCount(String name) {
/* 74 */     Map<String, Integer> map = (new PathShapeMapper()).getArgumentCount();
/* 75 */     if (map.containsKey(name.toUpperCase())) {
/* 76 */       return ((Integer)map.get(name.toUpperCase())).intValue();
/*    */     }
/* 78 */     return -1;
/*    */   }
/*    */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/svg/renderers/path/SvgPathShapeFactory.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */