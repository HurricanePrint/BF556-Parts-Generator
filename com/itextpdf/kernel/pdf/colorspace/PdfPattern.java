/*     */ package com.itextpdf.kernel.pdf.colorspace;
/*     */ 
/*     */ import com.itextpdf.kernel.geom.Rectangle;
/*     */ import com.itextpdf.kernel.pdf.PdfArray;
/*     */ import com.itextpdf.kernel.pdf.PdfDictionary;
/*     */ import com.itextpdf.kernel.pdf.PdfName;
/*     */ import com.itextpdf.kernel.pdf.PdfNumber;
/*     */ import com.itextpdf.kernel.pdf.PdfObject;
/*     */ import com.itextpdf.kernel.pdf.PdfObjectWrapper;
/*     */ import com.itextpdf.kernel.pdf.PdfResources;
/*     */ import com.itextpdf.kernel.pdf.PdfStream;
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
/*     */ 
/*     */ public abstract class PdfPattern
/*     */   extends PdfObjectWrapper<PdfDictionary>
/*     */ {
/*     */   private static final long serialVersionUID = -6771280634868639993L;
/*     */   
/*     */   protected PdfPattern(PdfDictionary pdfObject) {
/*  67 */     super((PdfObject)pdfObject);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static PdfPattern getPatternInstance(PdfDictionary pdfObject) {
/*  77 */     PdfNumber type = pdfObject.getAsNumber(PdfName.PatternType);
/*  78 */     if (type.intValue() == 1 && pdfObject instanceof PdfStream)
/*  79 */       return new Tiling((PdfStream)pdfObject); 
/*  80 */     if (type.intValue() == 2)
/*  81 */       return new Shading(pdfObject); 
/*  82 */     throw new IllegalArgumentException("pdfObject");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PdfArray getMatrix() {
/*  94 */     return ((PdfDictionary)getPdfObject()).getAsArray(PdfName.Matrix);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setMatrix(PdfArray matrix) {
/* 106 */     ((PdfDictionary)getPdfObject()).put(PdfName.Matrix, (PdfObject)matrix);
/* 107 */     setModified();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void flush() {
/* 119 */     super.flush();
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean isWrappedObjectMustBeIndirect() {
/* 124 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static class Tiling
/*     */     extends PdfPattern
/*     */   {
/*     */     private static final long serialVersionUID = 1450379837955897673L;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 139 */     private PdfResources resources = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public static class PaintType
/*     */     {
/*     */       public static final int COLORED = 1;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       public static final int UNCOLORED = 2;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public static class TilingType
/*     */     {
/*     */       public static final int CONSTANT_SPACING = 1;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       public static final int NO_DISTORTION = 2;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       public static final int CONSTANT_SPACING_AND_FASTER_TILING = 3;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Tiling(PdfStream pdfObject) {
/* 187 */       super((PdfDictionary)pdfObject);
/*     */     }
/*     */     
/*     */     public Tiling(float width, float height) {
/* 191 */       this(width, height, true);
/*     */     }
/*     */     
/*     */     public Tiling(float width, float height, boolean colored) {
/* 195 */       this(new Rectangle(width, height), colored);
/*     */     }
/*     */     
/*     */     public Tiling(Rectangle bbox) {
/* 199 */       this(bbox, true);
/*     */     }
/*     */     
/*     */     public Tiling(Rectangle bbox, boolean colored) {
/* 203 */       this(bbox, bbox.getWidth(), bbox.getHeight(), colored);
/*     */     }
/*     */     
/*     */     public Tiling(float width, float height, float xStep, float yStep) {
/* 207 */       this(width, height, xStep, yStep, true);
/*     */     }
/*     */     
/*     */     public Tiling(float width, float height, float xStep, float yStep, boolean colored) {
/* 211 */       this(new Rectangle(width, height), xStep, yStep, colored);
/*     */     }
/*     */     
/*     */     public Tiling(Rectangle bbox, float xStep, float yStep) {
/* 215 */       this(bbox, xStep, yStep, true);
/*     */     }
/*     */ 
/*     */     
/*     */     public Tiling(Rectangle bbox, float xStep, float yStep, boolean colored) {
/* 220 */       super((PdfDictionary)new PdfStream());
/* 221 */       ((PdfDictionary)getPdfObject()).put(PdfName.Type, (PdfObject)PdfName.Pattern);
/* 222 */       ((PdfDictionary)getPdfObject()).put(PdfName.PatternType, (PdfObject)new PdfNumber(1));
/* 223 */       ((PdfDictionary)getPdfObject()).put(PdfName.PaintType, (PdfObject)new PdfNumber(colored ? 1 : 2));
/* 224 */       ((PdfDictionary)getPdfObject()).put(PdfName.TilingType, (PdfObject)new PdfNumber(1));
/* 225 */       ((PdfDictionary)getPdfObject()).put(PdfName.BBox, (PdfObject)new PdfArray(bbox));
/* 226 */       ((PdfDictionary)getPdfObject()).put(PdfName.XStep, (PdfObject)new PdfNumber(xStep));
/* 227 */       ((PdfDictionary)getPdfObject()).put(PdfName.YStep, (PdfObject)new PdfNumber(yStep));
/* 228 */       this.resources = new PdfResources();
/* 229 */       ((PdfDictionary)getPdfObject()).put(PdfName.Resources, this.resources.getPdfObject());
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean isColored() {
/* 238 */       return (((PdfDictionary)getPdfObject()).getAsNumber(PdfName.PaintType).intValue() == 1);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void setColored(boolean colored) {
/* 248 */       ((PdfDictionary)getPdfObject()).put(PdfName.PaintType, (PdfObject)new PdfNumber(colored ? 1 : 2));
/* 249 */       setModified();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public int getTilingType() {
/* 258 */       return ((PdfDictionary)getPdfObject()).getAsNumber(PdfName.TilingType).intValue();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void setTilingType(int tilingType) {
/* 268 */       if (tilingType != 1 && tilingType != 2 && tilingType != 3)
/*     */       {
/* 270 */         throw new IllegalArgumentException("tilingType"); } 
/* 271 */       ((PdfDictionary)getPdfObject()).put(PdfName.TilingType, (PdfObject)new PdfNumber(tilingType));
/* 272 */       setModified();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Rectangle getBBox() {
/* 281 */       return ((PdfDictionary)getPdfObject()).getAsArray(PdfName.BBox).toRectangle();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void setBBox(Rectangle bbox) {
/* 290 */       ((PdfDictionary)getPdfObject()).put(PdfName.BBox, (PdfObject)new PdfArray(bbox));
/* 291 */       setModified();
/*     */     }
/*     */     
/*     */     public float getXStep() {
/* 295 */       return ((PdfDictionary)getPdfObject()).getAsNumber(PdfName.XStep).floatValue();
/*     */     }
/*     */     
/*     */     public void setXStep(float xStep) {
/* 299 */       ((PdfDictionary)getPdfObject()).put(PdfName.XStep, (PdfObject)new PdfNumber(xStep));
/* 300 */       setModified();
/*     */     }
/*     */     
/*     */     public float getYStep() {
/* 304 */       return ((PdfDictionary)getPdfObject()).getAsNumber(PdfName.YStep).floatValue();
/*     */     }
/*     */     
/*     */     public void setYStep(float yStep) {
/* 308 */       ((PdfDictionary)getPdfObject()).put(PdfName.YStep, (PdfObject)new PdfNumber(yStep));
/* 309 */       setModified();
/*     */     }
/*     */     
/*     */     public PdfResources getResources() {
/* 313 */       if (this.resources == null) {
/* 314 */         PdfDictionary resourcesDict = ((PdfDictionary)getPdfObject()).getAsDictionary(PdfName.Resources);
/* 315 */         if (resourcesDict == null) {
/* 316 */           resourcesDict = new PdfDictionary();
/* 317 */           ((PdfDictionary)getPdfObject()).put(PdfName.Resources, (PdfObject)resourcesDict);
/*     */         } 
/* 319 */         this.resources = new PdfResources(resourcesDict);
/*     */       } 
/* 321 */       return this.resources;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void flush() {
/* 329 */       this.resources = null;
/* 330 */       super.flush();
/*     */     }
/*     */   }
/*     */   
/*     */   public static class Shading
/*     */     extends PdfPattern {
/*     */     private static final long serialVersionUID = -4289411438737403786L;
/*     */     
/*     */     public Shading(PdfDictionary pdfObject) {
/* 339 */       super(pdfObject);
/*     */     }
/*     */     
/*     */     public Shading(PdfShading shading) {
/* 343 */       super(new PdfDictionary());
/* 344 */       ((PdfDictionary)getPdfObject()).put(PdfName.Type, (PdfObject)PdfName.Pattern);
/* 345 */       ((PdfDictionary)getPdfObject()).put(PdfName.PatternType, (PdfObject)new PdfNumber(2));
/* 346 */       ((PdfDictionary)getPdfObject()).put(PdfName.Shading, shading.getPdfObject());
/*     */     }
/*     */     
/*     */     public PdfDictionary getShading() {
/* 350 */       return (PdfDictionary)((PdfDictionary)getPdfObject()).get(PdfName.Shading);
/*     */     }
/*     */     
/*     */     public void setShading(PdfShading shading) {
/* 354 */       ((PdfDictionary)getPdfObject()).put(PdfName.Shading, shading.getPdfObject());
/* 355 */       setModified();
/*     */     }
/*     */     
/*     */     public void setShading(PdfDictionary shading) {
/* 359 */       ((PdfDictionary)getPdfObject()).put(PdfName.Shading, (PdfObject)shading);
/* 360 */       setModified();
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/kernel/pdf/colorspace/PdfPattern.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */