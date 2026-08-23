/*    */ package com.itextpdf.kernel.pdf.colorspace;
/*    */ 
/*    */ import com.itextpdf.kernel.pdf.PdfName;
/*    */ import com.itextpdf.kernel.pdf.PdfObject;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class PdfDeviceCs
/*    */   extends PdfColorSpace
/*    */ {
/*    */   private static final long serialVersionUID = 6884911248656287064L;
/*    */   
/*    */   protected boolean isWrappedObjectMustBeIndirect() {
/* 54 */     return false;
/*    */   }
/*    */   
/*    */   protected PdfDeviceCs(PdfName pdfObject) {
/* 58 */     super((PdfObject)pdfObject);
/*    */   }
/*    */   
/*    */   public static class Gray
/*    */     extends PdfDeviceCs {
/*    */     private static final long serialVersionUID = 2722906212276665191L;
/*    */     
/*    */     public Gray() {
/* 66 */       super(PdfName.DeviceGray);
/*    */     }
/*    */ 
/*    */     
/*    */     public int getNumberOfComponents() {
/* 71 */       return 1;
/*    */     }
/*    */   }
/*    */   
/*    */   public static class Rgb
/*    */     extends PdfDeviceCs {
/*    */     private static final long serialVersionUID = -1605044540582561428L;
/*    */     
/*    */     public Rgb() {
/* 80 */       super(PdfName.DeviceRGB);
/*    */     }
/*    */ 
/*    */     
/*    */     public int getNumberOfComponents() {
/* 85 */       return 3;
/*    */     }
/*    */   }
/*    */   
/*    */   public static class Cmyk
/*    */     extends PdfDeviceCs {
/*    */     private static final long serialVersionUID = 2615036909699704719L;
/*    */     
/*    */     public Cmyk() {
/* 94 */       super(PdfName.DeviceCMYK);
/*    */     }
/*    */ 
/*    */     
/*    */     public int getNumberOfComponents() {
/* 99 */       return 4;
/*    */     }
/*    */   }
/*    */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/kernel/pdf/colorspace/PdfDeviceCs.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */