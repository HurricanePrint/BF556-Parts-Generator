/*      */ package com.itextpdf.kernel.pdf.colorspace;
/*      */ 
/*      */ import com.itextpdf.kernel.PdfException;
/*      */ import com.itextpdf.kernel.pdf.PdfArray;
/*      */ import com.itextpdf.kernel.pdf.PdfDictionary;
/*      */ import com.itextpdf.kernel.pdf.PdfName;
/*      */ import com.itextpdf.kernel.pdf.PdfNumber;
/*      */ import com.itextpdf.kernel.pdf.PdfObject;
/*      */ import com.itextpdf.kernel.pdf.PdfObjectWrapper;
/*      */ import com.itextpdf.kernel.pdf.PdfStream;
/*      */ import com.itextpdf.kernel.pdf.function.PdfFunction;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public abstract class PdfShading
/*      */   extends PdfObjectWrapper<PdfDictionary>
/*      */ {
/*      */   private static final long serialVersionUID = 4781809723744243508L;
/*      */   
/*      */   static final class ShadingType
/*      */   {
/*      */     public static final int FUNCTION_BASED = 1;
/*      */     public static final int AXIAL = 2;
/*      */     public static final int RADIAL = 3;
/*      */     public static final int FREE_FORM_GOURAUD_SHADED_TRIANGLE_MESH = 4;
/*      */     public static final int LATTICE_FORM_GOURAUD_SHADED_TRIANGLE_MESH = 5;
/*      */     public static final int COONS_PATCH_MESH = 6;
/*      */     public static final int TENSOR_PRODUCT_PATCH_MESH = 7;
/*      */   }
/*      */   
/*      */   public static PdfShading makeShading(PdfDictionary shadingDictionary) {
/*      */     PdfShading shading;
/*   93 */     if (!shadingDictionary.containsKey(PdfName.ShadingType)) {
/*   94 */       throw new PdfException("Shading type not found.");
/*      */     }
/*   96 */     if (!shadingDictionary.containsKey(PdfName.ColorSpace)) {
/*   97 */       throw new PdfException("ColorSpace not found.");
/*      */     }
/*      */ 
/*      */     
/*  101 */     switch (shadingDictionary.getAsNumber(PdfName.ShadingType).intValue()) {
/*      */       case 1:
/*  103 */         shading = new FunctionBased(shadingDictionary);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  138 */         return shading;case 2: shading = new Axial(shadingDictionary); return shading;case 3: shading = new Radial(shadingDictionary); return shading;case 4: if (!shadingDictionary.isStream()) throw new PdfException("Unexpected shading type.");  shading = new FreeFormGouraudShadedTriangleMesh((PdfStream)shadingDictionary); return shading;case 5: if (!shadingDictionary.isStream()) throw new PdfException("Unexpected shading type.");  shading = new LatticeFormGouraudShadedTriangleMesh((PdfStream)shadingDictionary); return shading;case 6: if (!shadingDictionary.isStream()) throw new PdfException("Unexpected shading type.");  shading = new CoonsPatchMesh((PdfStream)shadingDictionary); return shading;case 7: if (!shadingDictionary.isStream()) throw new PdfException("Unexpected shading type.");  shading = new TensorProductPatchMesh((PdfStream)shadingDictionary); return shading;
/*      */     } 
/*      */     throw new PdfException("Unexpected shading type.");
/*      */   } protected PdfShading(PdfDictionary pdfObject) {
/*  142 */     super((PdfObject)pdfObject);
/*      */   }
/*      */   
/*      */   protected PdfShading(PdfDictionary pdfObject, int type, PdfColorSpace colorSpace) {
/*  146 */     super((PdfObject)pdfObject);
/*  147 */     ((PdfDictionary)getPdfObject()).put(PdfName.ShadingType, (PdfObject)new PdfNumber(type));
/*  148 */     if (colorSpace instanceof PdfSpecialCs.Pattern) {
/*  149 */       throw new IllegalArgumentException("colorSpace");
/*      */     }
/*  151 */     ((PdfDictionary)getPdfObject()).put(PdfName.ColorSpace, colorSpace.getPdfObject());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getShadingType() {
/*  160 */     return ((PdfDictionary)getPdfObject()).getAsInt(PdfName.ShadingType).intValue();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PdfObject getColorSpace() {
/*  169 */     return ((PdfDictionary)getPdfObject()).get(PdfName.ColorSpace);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PdfObject getFunction() {
/*  179 */     return ((PdfDictionary)getPdfObject()).get(PdfName.Function);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setFunction(PdfFunction function) {
/*  189 */     ((PdfDictionary)getPdfObject()).put(PdfName.Function, function.getPdfObject());
/*  190 */     setModified();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setFunction(PdfFunction[] functions) {
/*  200 */     PdfArray arr = new PdfArray();
/*  201 */     for (PdfFunction func : functions) {
/*  202 */       arr.add(func.getPdfObject());
/*      */     }
/*  204 */     ((PdfDictionary)getPdfObject()).put(PdfName.Function, (PdfObject)arr);
/*  205 */     setModified();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void flush() {
/*  217 */     super.flush();
/*      */   }
/*      */ 
/*      */   
/*      */   protected boolean isWrappedObjectMustBeIndirect() {
/*  222 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static class FunctionBased
/*      */     extends PdfShading
/*      */   {
/*      */     private static final long serialVersionUID = -4459197498902558052L;
/*      */ 
/*      */     
/*      */     protected FunctionBased(PdfDictionary pdfDictionary) {
/*  234 */       super(pdfDictionary);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public FunctionBased(PdfColorSpace colorSpace, PdfFunction function) {
/*  244 */       this(colorSpace.getPdfObject(), function);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public FunctionBased(PdfObject colorSpace, PdfFunction function) {
/*  254 */       super(new PdfDictionary(), 1, PdfColorSpace.makeColorSpace(colorSpace));
/*      */       
/*  256 */       setFunction(function);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public PdfArray getDomain() {
/*  266 */       return ((PdfDictionary)getPdfObject()).getAsArray(PdfName.Domain);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void setDomain(float xmin, float xmax, float ymin, float ymax) {
/*  279 */       setDomain(new PdfArray(new float[] { xmin, xmax, ymin, ymax }));
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void setDomain(PdfArray domain) {
/*  289 */       ((PdfDictionary)getPdfObject()).put(PdfName.Domain, (PdfObject)domain);
/*  290 */       setModified();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public PdfArray getMatrix() {
/*  300 */       PdfArray matrix = ((PdfDictionary)getPdfObject()).getAsArray(PdfName.Matrix);
/*  301 */       if (matrix == null) {
/*  302 */         matrix = new PdfArray(new float[] { 1.0F, 0.0F, 0.0F, 1.0F, 0.0F, 0.0F });
/*  303 */         setMatrix(matrix);
/*      */       } 
/*  305 */       return matrix;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void setMatrix(float[] matrix) {
/*  315 */       setMatrix(new PdfArray(matrix));
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void setMatrix(PdfArray matrix) {
/*  325 */       ((PdfDictionary)getPdfObject()).put(PdfName.Matrix, (PdfObject)matrix);
/*  326 */       setModified();
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static class Axial
/*      */     extends PdfShading
/*      */   {
/*      */     private static final long serialVersionUID = 5504688740677023792L;
/*      */ 
/*      */ 
/*      */     
/*      */     protected Axial(PdfDictionary pdfDictionary) {
/*  340 */       super(pdfDictionary);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Axial(PdfColorSpace cs, float x0, float y0, float[] color0, float x1, float y1, float[] color1) {
/*  356 */       super(new PdfDictionary(), 2, cs);
/*      */       
/*  358 */       setCoords(x0, y0, x1, y1);
/*  359 */       PdfFunction.Type2 type2 = new PdfFunction.Type2(new PdfArray(new float[] { 0.0F, 1.0F }, ), null, new PdfArray(color0), new PdfArray(color1), new PdfNumber(1));
/*      */       
/*  361 */       setFunction((PdfFunction)type2);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Axial(PdfColorSpace cs, float x0, float y0, float[] color0, float x1, float y1, float[] color1, boolean[] extend) {
/*  379 */       this(cs, x0, y0, color0, x1, y1, color1);
/*      */       
/*  381 */       if (extend == null || extend.length != 2) {
/*  382 */         throw new IllegalArgumentException("extend");
/*      */       }
/*  384 */       setExtend(extend[0], extend[1]);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Axial(PdfColorSpace cs, PdfArray coords, PdfFunction function) {
/*  397 */       this(cs, coords, (PdfArray)null, function);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Axial(PdfColorSpace cs, PdfArray coords, PdfArray domain, PdfFunction function) {
/*  414 */       super(new PdfDictionary(), 2, cs);
/*  415 */       setCoords(coords);
/*  416 */       if (domain != null) {
/*  417 */         setDomain(domain);
/*      */       }
/*  419 */       setFunction(function);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public PdfArray getCoords() {
/*  430 */       return ((PdfDictionary)getPdfObject()).getAsArray(PdfName.Coords);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void setCoords(float x0, float y0, float x1, float y1) {
/*  442 */       setCoords(new PdfArray(new float[] { x0, y0, x1, y1 }));
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void setCoords(PdfArray coords) {
/*  453 */       ((PdfDictionary)getPdfObject()).put(PdfName.Coords, (PdfObject)coords);
/*  454 */       setModified();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public PdfArray getDomain() {
/*  464 */       PdfArray domain = ((PdfDictionary)getPdfObject()).getAsArray(PdfName.Domain);
/*  465 */       if (domain == null) {
/*  466 */         domain = new PdfArray(new float[] { 0.0F, 1.0F });
/*  467 */         setDomain(domain);
/*      */       } 
/*  469 */       return domain;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void setDomain(float t0, float t1) {
/*  480 */       setDomain(new PdfArray(new float[] { t0, t1 }));
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void setDomain(PdfArray domain) {
/*  490 */       ((PdfDictionary)getPdfObject()).put(PdfName.Domain, (PdfObject)domain);
/*  491 */       setModified();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public PdfArray getExtend() {
/*  501 */       PdfArray extend = ((PdfDictionary)getPdfObject()).getAsArray(PdfName.Extend);
/*  502 */       if (extend == null) {
/*  503 */         extend = new PdfArray(new boolean[] { false, false });
/*  504 */         setExtend(extend);
/*      */       } 
/*  506 */       return extend;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void setExtend(boolean extendStart, boolean extendEnd) {
/*  516 */       setExtend(new PdfArray(new boolean[] { extendStart, extendEnd }));
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void setExtend(PdfArray extend) {
/*  527 */       ((PdfDictionary)getPdfObject()).put(PdfName.Extend, (PdfObject)extend);
/*  528 */       setModified();
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static class Radial
/*      */     extends PdfShading
/*      */   {
/*      */     private static final long serialVersionUID = -5012819396006804845L;
/*      */ 
/*      */ 
/*      */     
/*      */     protected Radial(PdfDictionary pdfDictionary) {
/*  542 */       super(pdfDictionary);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Radial(PdfColorSpace cs, float x0, float y0, float r0, float[] color0, float x1, float y1, float r1, float[] color1) {
/*  564 */       super(new PdfDictionary(), 3, cs);
/*      */       
/*  566 */       setCoords(x0, y0, r0, x1, y1, r1);
/*  567 */       PdfFunction.Type2 type2 = new PdfFunction.Type2(new PdfArray(new float[] { 0.0F, 1.0F }, ), null, new PdfArray(color0), new PdfArray(color1), new PdfNumber(1));
/*      */       
/*  569 */       setFunction((PdfFunction)type2);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Radial(PdfColorSpace cs, float x0, float y0, float r0, float[] color0, float x1, float y1, float r1, float[] color1, boolean[] extend) {
/*  593 */       this(cs, x0, y0, r0, color0, x1, y1, r1, color1);
/*      */       
/*  595 */       if (extend == null || extend.length != 2) {
/*  596 */         throw new IllegalArgumentException("extend");
/*      */       }
/*  598 */       setExtend(extend[0], extend[1]);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Radial(PdfColorSpace cs, PdfArray coords, PdfFunction function) {
/*  615 */       super(new PdfDictionary(), 3, cs);
/*  616 */       setCoords(coords);
/*  617 */       setFunction(function);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public PdfArray getCoords() {
/*  631 */       return ((PdfDictionary)getPdfObject()).getAsArray(PdfName.Coords);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void setCoords(float x0, float y0, float r0, float x1, float y1, float r1) {
/*  649 */       setCoords(new PdfArray(new float[] { x0, y0, r0, x1, y1, r1 }));
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void setCoords(PdfArray coords) {
/*  663 */       ((PdfDictionary)getPdfObject()).put(PdfName.Coords, (PdfObject)coords);
/*  664 */       setModified();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public PdfArray getDomain() {
/*  674 */       PdfArray domain = ((PdfDictionary)getPdfObject()).getAsArray(PdfName.Domain);
/*  675 */       if (domain == null) {
/*  676 */         domain = new PdfArray(new float[] { 0.0F, 1.0F });
/*  677 */         setDomain(domain);
/*      */       } 
/*  679 */       return domain;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void setDomain(float t0, float t1) {
/*  690 */       setDomain(new PdfArray(new float[] { t0, t1 }));
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void setDomain(PdfArray domain) {
/*  700 */       ((PdfDictionary)getPdfObject()).put(PdfName.Domain, (PdfObject)domain);
/*  701 */       setModified();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public PdfArray getExtend() {
/*  711 */       PdfArray extend = ((PdfDictionary)getPdfObject()).getAsArray(PdfName.Extend);
/*  712 */       if (extend == null) {
/*  713 */         extend = new PdfArray(new boolean[] { false, false });
/*  714 */         setExtend(extend);
/*      */       } 
/*  716 */       return extend;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void setExtend(boolean extendStart, boolean extendEnd) {
/*  726 */       setExtend(new PdfArray(new boolean[] { extendStart, extendEnd }));
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void setExtend(PdfArray extend) {
/*  737 */       ((PdfDictionary)getPdfObject()).put(PdfName.Extend, (PdfObject)extend);
/*  738 */       setModified();
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static class FreeFormGouraudShadedTriangleMesh
/*      */     extends PdfShading
/*      */   {
/*      */     private static final long serialVersionUID = -2690557760051875972L;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     protected FreeFormGouraudShadedTriangleMesh(PdfStream pdfStream) {
/*  766 */       super((PdfDictionary)pdfStream);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public FreeFormGouraudShadedTriangleMesh(PdfColorSpace cs, int bitsPerCoordinate, int bitsPerComponent, int bitsPerFlag, float[] decode) {
/*  788 */       this(cs, bitsPerCoordinate, bitsPerComponent, bitsPerFlag, new PdfArray(decode));
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public FreeFormGouraudShadedTriangleMesh(PdfColorSpace cs, int bitsPerCoordinate, int bitsPerComponent, int bitsPerFlag, PdfArray decode) {
/*  810 */       super((PdfDictionary)new PdfStream(), 4, cs);
/*      */       
/*  812 */       setBitsPerCoordinate(bitsPerCoordinate);
/*  813 */       setBitsPerComponent(bitsPerComponent);
/*  814 */       setBitsPerFlag(bitsPerFlag);
/*  815 */       setDecode(decode);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public int getBitsPerCoordinate() {
/*  824 */       return ((PdfDictionary)getPdfObject()).getAsInt(PdfName.BitsPerCoordinate).intValue();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void setBitsPerCoordinate(int bitsPerCoordinate) {
/*  833 */       ((PdfDictionary)getPdfObject()).put(PdfName.BitsPerCoordinate, (PdfObject)new PdfNumber(bitsPerCoordinate));
/*  834 */       setModified();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public int getBitsPerComponent() {
/*  843 */       return ((PdfDictionary)getPdfObject()).getAsInt(PdfName.BitsPerComponent).intValue();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void setBitsPerComponent(int bitsPerComponent) {
/*  852 */       ((PdfDictionary)getPdfObject()).put(PdfName.BitsPerComponent, (PdfObject)new PdfNumber(bitsPerComponent));
/*  853 */       setModified();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public int getBitsPerFlag() {
/*  864 */       return ((PdfDictionary)getPdfObject()).getAsInt(PdfName.BitsPerFlag).intValue();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void setBitsPerFlag(int bitsPerFlag) {
/*  875 */       ((PdfDictionary)getPdfObject()).put(PdfName.BitsPerFlag, (PdfObject)new PdfNumber(bitsPerFlag));
/*  876 */       setModified();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public PdfArray getDecode() {
/*  888 */       return ((PdfDictionary)getPdfObject()).getAsArray(PdfName.Decode);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void setDecode(float[] decode) {
/*  900 */       setDecode(new PdfArray(decode));
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void setDecode(PdfArray decode) {
/*  912 */       ((PdfDictionary)getPdfObject()).put(PdfName.Decode, (PdfObject)decode);
/*  913 */       setModified();
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static class LatticeFormGouraudShadedTriangleMesh
/*      */     extends PdfShading
/*      */   {
/*      */     private static final long serialVersionUID = -8776232978423888214L;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     protected LatticeFormGouraudShadedTriangleMesh(PdfStream pdfStream) {
/*  934 */       super((PdfDictionary)pdfStream);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public LatticeFormGouraudShadedTriangleMesh(PdfColorSpace cs, int bitsPerCoordinate, int bitsPerComponent, int verticesPerRow, float[] decode) {
/*  954 */       this(cs, bitsPerCoordinate, bitsPerComponent, verticesPerRow, new PdfArray(decode));
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public LatticeFormGouraudShadedTriangleMesh(PdfColorSpace cs, int bitsPerCoordinate, int bitsPerComponent, int verticesPerRow, PdfArray decode) {
/*  974 */       super((PdfDictionary)new PdfStream(), 5, cs);
/*      */       
/*  976 */       setBitsPerCoordinate(bitsPerCoordinate);
/*  977 */       setBitsPerComponent(bitsPerComponent);
/*  978 */       setVerticesPerRow(verticesPerRow);
/*  979 */       setDecode(decode);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public int getBitsPerCoordinate() {
/*  988 */       return ((PdfDictionary)getPdfObject()).getAsInt(PdfName.BitsPerCoordinate).intValue();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void setBitsPerCoordinate(int bitsPerCoordinate) {
/*  997 */       ((PdfDictionary)getPdfObject()).put(PdfName.BitsPerCoordinate, (PdfObject)new PdfNumber(bitsPerCoordinate));
/*  998 */       setModified();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public int getBitsPerComponent() {
/* 1007 */       return ((PdfDictionary)getPdfObject()).getAsInt(PdfName.BitsPerComponent).intValue();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void setBitsPerComponent(int bitsPerComponent) {
/* 1016 */       ((PdfDictionary)getPdfObject()).put(PdfName.BitsPerComponent, (PdfObject)new PdfNumber(bitsPerComponent));
/* 1017 */       setModified();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public int getVerticesPerRow() {
/* 1026 */       return ((PdfDictionary)getPdfObject()).getAsInt(PdfName.VerticesPerRow).intValue();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void setVerticesPerRow(int verticesPerRow) {
/* 1036 */       ((PdfDictionary)getPdfObject()).put(PdfName.VerticesPerRow, (PdfObject)new PdfNumber(verticesPerRow));
/* 1037 */       setModified();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public PdfArray getDecode() {
/* 1049 */       return ((PdfDictionary)getPdfObject()).getAsArray(PdfName.Decode);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void setDecode(float[] decode) {
/* 1061 */       setDecode(new PdfArray(decode));
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void setDecode(PdfArray decode) {
/* 1073 */       ((PdfDictionary)getPdfObject()).put(PdfName.Decode, (PdfObject)decode);
/* 1074 */       setModified();
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static class CoonsPatchMesh
/*      */     extends PdfShading
/*      */   {
/*      */     private static final long serialVersionUID = 7296891352801419708L;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     protected CoonsPatchMesh(PdfStream pdfStream) {
/* 1105 */       super((PdfDictionary)pdfStream);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public CoonsPatchMesh(PdfColorSpace cs, int bitsPerCoordinate, int bitsPerComponent, int bitsPerFlag, float[] decode) {
/* 1127 */       this(cs, bitsPerCoordinate, bitsPerComponent, bitsPerFlag, new PdfArray(decode));
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public CoonsPatchMesh(PdfColorSpace cs, int bitsPerCoordinate, int bitsPerComponent, int bitsPerFlag, PdfArray decode) {
/* 1149 */       super((PdfDictionary)new PdfStream(), 6, cs);
/* 1150 */       setBitsPerCoordinate(bitsPerCoordinate);
/* 1151 */       setBitsPerComponent(bitsPerComponent);
/* 1152 */       setBitsPerFlag(bitsPerFlag);
/* 1153 */       setDecode(decode);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public int getBitsPerCoordinate() {
/* 1162 */       return ((PdfDictionary)getPdfObject()).getAsInt(PdfName.BitsPerCoordinate).intValue();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void setBitsPerCoordinate(int bitsPerCoordinate) {
/* 1171 */       ((PdfDictionary)getPdfObject()).put(PdfName.BitsPerCoordinate, (PdfObject)new PdfNumber(bitsPerCoordinate));
/* 1172 */       setModified();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public int getBitsPerComponent() {
/* 1181 */       return ((PdfDictionary)getPdfObject()).getAsInt(PdfName.BitsPerComponent).intValue();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void setBitsPerComponent(int bitsPerComponent) {
/* 1190 */       ((PdfDictionary)getPdfObject()).put(PdfName.BitsPerComponent, (PdfObject)new PdfNumber(bitsPerComponent));
/* 1191 */       setModified();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public int getBitsPerFlag() {
/* 1202 */       return ((PdfDictionary)getPdfObject()).getAsInt(PdfName.BitsPerFlag).intValue();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void setBitsPerFlag(int bitsPerFlag) {
/* 1213 */       ((PdfDictionary)getPdfObject()).put(PdfName.BitsPerFlag, (PdfObject)new PdfNumber(bitsPerFlag));
/* 1214 */       setModified();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public PdfArray getDecode() {
/* 1226 */       return ((PdfDictionary)getPdfObject()).getAsArray(PdfName.Decode);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void setDecode(float[] decode) {
/* 1238 */       setDecode(new PdfArray(decode));
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void setDecode(PdfArray decode) {
/* 1250 */       ((PdfDictionary)getPdfObject()).put(PdfName.Decode, (PdfObject)decode);
/* 1251 */       setModified();
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static class TensorProductPatchMesh
/*      */     extends PdfShading
/*      */   {
/*      */     private static final long serialVersionUID = -2750695839303504742L;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     protected TensorProductPatchMesh(PdfStream pdfStream) {
/* 1269 */       super((PdfDictionary)pdfStream);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public TensorProductPatchMesh(PdfColorSpace cs, int bitsPerCoordinate, int bitsPerComponent, int bitsPerFlag, float[] decode) {
/* 1291 */       this(cs, bitsPerCoordinate, bitsPerComponent, bitsPerFlag, new PdfArray(decode));
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public TensorProductPatchMesh(PdfColorSpace cs, int bitsPerCoordinate, int bitsPerComponent, int bitsPerFlag, PdfArray decode) {
/* 1313 */       super((PdfDictionary)new PdfStream(), 7, cs);
/*      */       
/* 1315 */       setBitsPerCoordinate(bitsPerCoordinate);
/* 1316 */       setBitsPerComponent(bitsPerComponent);
/* 1317 */       setBitsPerFlag(bitsPerFlag);
/* 1318 */       setDecode(decode);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public int getBitsPerCoordinate() {
/* 1327 */       return ((PdfDictionary)getPdfObject()).getAsInt(PdfName.BitsPerCoordinate).intValue();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void setBitsPerCoordinate(int bitsPerCoordinate) {
/* 1336 */       ((PdfDictionary)getPdfObject()).put(PdfName.BitsPerCoordinate, (PdfObject)new PdfNumber(bitsPerCoordinate));
/* 1337 */       setModified();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public int getBitsPerComponent() {
/* 1346 */       return ((PdfDictionary)getPdfObject()).getAsInt(PdfName.BitsPerComponent).intValue();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void setBitsPerComponent(int bitsPerComponent) {
/* 1355 */       ((PdfDictionary)getPdfObject()).put(PdfName.BitsPerComponent, (PdfObject)new PdfNumber(bitsPerComponent));
/* 1356 */       setModified();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public int getBitsPerFlag() {
/* 1367 */       return ((PdfDictionary)getPdfObject()).getAsInt(PdfName.BitsPerFlag).intValue();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void setBitsPerFlag(int bitsPerFlag) {
/* 1378 */       ((PdfDictionary)getPdfObject()).put(PdfName.BitsPerFlag, (PdfObject)new PdfNumber(bitsPerFlag));
/* 1379 */       setModified();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public PdfArray getDecode() {
/* 1391 */       return ((PdfDictionary)getPdfObject()).getAsArray(PdfName.Decode);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void setDecode(float[] decode) {
/* 1403 */       setDecode(new PdfArray(decode));
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void setDecode(PdfArray decode) {
/* 1415 */       ((PdfDictionary)getPdfObject()).put(PdfName.Decode, (PdfObject)decode);
/* 1416 */       setModified();
/*      */     }
/*      */   }
/*      */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/kernel/pdf/colorspace/PdfShading.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */