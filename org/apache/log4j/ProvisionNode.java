/*    */ package org.apache.log4j;
/*    */ 
/*    */ import java.util.Vector;
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
/*    */ class ProvisionNode
/*    */   extends Vector
/*    */ {
/*    */   private static final long serialVersionUID = -4479121426311014469L;
/*    */   
/*    */   ProvisionNode(Logger logger) {
/* 27 */     addElement((E)logger);
/*    */   }
/*    */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/org/apache/log4j/ProvisionNode.class
 * Java compiler version: 4 (48.0)
 * JD-Core Version:       1.1.3
 */