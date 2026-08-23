/*    */ package com.itextpdf.kernel.colors;
/*    */ 
/*    */ import com.itextpdf.kernel.pdf.colorspace.PdfColorSpace;
/*    */ import com.itextpdf.kernel.pdf.colorspace.PdfSpecialCs;
/*    */ import com.itextpdf.kernel.pdf.function.PdfFunction;
/*    */ import java.util.Arrays;
/*    */ import java.util.List;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class DeviceN
/*    */   extends Color
/*    */ {
/*    */   private static final long serialVersionUID = -2623878574830631842L;
/*    */   
/*    */   public DeviceN(PdfSpecialCs.DeviceN cs) {
/* 58 */     this(cs, getDefaultColorants(cs.getNumberOfComponents()));
/*    */   }
/*    */   
/*    */   public DeviceN(PdfSpecialCs.DeviceN cs, float[] value) {
/* 62 */     super((PdfColorSpace)cs, value);
/*    */   }
/*    */   
/*    */   public DeviceN(List<String> names, PdfColorSpace alternateCs, PdfFunction tintTransform, float[] value) {
/* 66 */     this(new PdfSpecialCs.DeviceN(names, alternateCs, tintTransform), value);
/*    */   }
/*    */   
/*    */   private static float[] getDefaultColorants(int numOfColorants) {
/* 70 */     float[] colorants = new float[numOfColorants];
/* 71 */     Arrays.fill(colorants, 1.0F);
/* 72 */     return colorants;
/*    */   }
/*    */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/kernel/colors/DeviceN.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */