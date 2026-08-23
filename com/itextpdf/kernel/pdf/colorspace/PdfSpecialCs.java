/*     */ package com.itextpdf.kernel.pdf.colorspace;
/*     */ 
/*     */ import com.itextpdf.kernel.PdfException;
/*     */ import com.itextpdf.kernel.pdf.PdfArray;
/*     */ import com.itextpdf.kernel.pdf.PdfDictionary;
/*     */ import com.itextpdf.kernel.pdf.PdfName;
/*     */ import com.itextpdf.kernel.pdf.PdfNumber;
/*     */ import com.itextpdf.kernel.pdf.PdfObject;
/*     */ import com.itextpdf.kernel.pdf.PdfString;
/*     */ import com.itextpdf.kernel.pdf.function.PdfFunction;
/*     */ import java.util.Arrays;
/*     */ import java.util.List;
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
/*     */ public abstract class PdfSpecialCs
/*     */   extends PdfColorSpace
/*     */ {
/*     */   private static final long serialVersionUID = -2725455900398492836L;
/*     */   
/*     */   protected PdfSpecialCs(PdfArray pdfObject) {
/*  64 */     super((PdfObject)pdfObject);
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
/*  76 */     super.flush();
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean isWrappedObjectMustBeIndirect() {
/*  81 */     return true;
/*     */   }
/*     */   
/*     */   public static class Indexed
/*     */     extends PdfSpecialCs {
/*     */     private static final long serialVersionUID = -1155418938167317916L;
/*     */     
/*     */     public Indexed(PdfArray pdfObject) {
/*  89 */       super(pdfObject);
/*     */     }
/*     */     
/*     */     public Indexed(PdfObject base, int hival, PdfString lookup) {
/*  93 */       this(getIndexedCsArray(base, hival, lookup));
/*     */     }
/*     */ 
/*     */     
/*     */     public int getNumberOfComponents() {
/*  98 */       return 1;
/*     */     }
/*     */     
/*     */     public PdfColorSpace getBaseCs() {
/* 102 */       return makeColorSpace(((PdfArray)getPdfObject()).get(1));
/*     */     }
/*     */     
/*     */     private static PdfArray getIndexedCsArray(PdfObject base, int hival, PdfString lookup) {
/* 106 */       PdfArray indexed = new PdfArray();
/* 107 */       indexed.add((PdfObject)PdfName.Indexed);
/* 108 */       indexed.add(base);
/* 109 */       indexed.add((PdfObject)new PdfNumber(hival));
/* 110 */       indexed.add((PdfObject)lookup.setHexWriting(true));
/* 111 */       return indexed;
/*     */     }
/*     */   }
/*     */   
/*     */   public static class Separation
/*     */     extends PdfSpecialCs
/*     */   {
/*     */     private static final long serialVersionUID = 4259327393838350842L;
/*     */     
/*     */     public Separation(PdfArray pdfObject) {
/* 121 */       super(pdfObject);
/*     */     }
/*     */     
/*     */     public Separation(PdfName name, PdfObject alternateSpace, PdfObject tintTransform) {
/* 125 */       this(getSeparationCsArray(name, alternateSpace, tintTransform));
/*     */     }
/*     */     
/*     */     public Separation(String name, PdfColorSpace alternateSpace, PdfFunction tintTransform) {
/* 129 */       this(new PdfName(name), alternateSpace.getPdfObject(), tintTransform.getPdfObject());
/* 130 */       if (!tintTransform.checkCompatibilityWithColorSpace(alternateSpace)) {
/* 131 */         throw new PdfException("Function is not compatible with ColorSpace.", this);
/*     */       }
/*     */     }
/*     */ 
/*     */     
/*     */     public int getNumberOfComponents() {
/* 137 */       return 1;
/*     */     }
/*     */     
/*     */     public PdfColorSpace getBaseCs() {
/* 141 */       return makeColorSpace(((PdfArray)getPdfObject()).get(2));
/*     */     }
/*     */     
/*     */     public PdfName getName() {
/* 145 */       return ((PdfArray)getPdfObject()).getAsName(1);
/*     */     }
/*     */     
/*     */     private static PdfArray getSeparationCsArray(PdfName name, PdfObject alternateSpace, PdfObject tintTransform) {
/* 149 */       PdfArray separation = new PdfArray();
/* 150 */       separation.add((PdfObject)PdfName.Separation);
/* 151 */       separation.add((PdfObject)name);
/* 152 */       separation.add(alternateSpace);
/* 153 */       separation.add(tintTransform);
/* 154 */       return separation;
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public static class DeviceN
/*     */     extends PdfSpecialCs
/*     */   {
/*     */     private static final long serialVersionUID = 4051693146595260270L;
/* 163 */     protected int numOfComponents = 0;
/*     */     
/*     */     public DeviceN(PdfArray pdfObject) {
/* 166 */       super(pdfObject);
/* 167 */       this.numOfComponents = pdfObject.getAsArray(1).size();
/*     */     }
/*     */     
/*     */     public DeviceN(PdfArray names, PdfObject alternateSpace, PdfObject tintTransform) {
/* 171 */       this(getDeviceNCsArray(names, alternateSpace, tintTransform));
/*     */     }
/*     */     
/*     */     public DeviceN(List<String> names, PdfColorSpace alternateSpace, PdfFunction tintTransform) {
/* 175 */       this(new PdfArray(names, true), alternateSpace.getPdfObject(), tintTransform.getPdfObject());
/* 176 */       if (tintTransform.getInputSize() != getNumberOfComponents() || tintTransform.getOutputSize() != alternateSpace.getNumberOfComponents()) {
/* 177 */         throw new PdfException("Function is not compatible with ColorSpace.", this);
/*     */       }
/*     */     }
/*     */ 
/*     */     
/*     */     public int getNumberOfComponents() {
/* 183 */       return this.numOfComponents;
/*     */     }
/*     */     
/*     */     public PdfColorSpace getBaseCs() {
/* 187 */       return makeColorSpace(((PdfArray)getPdfObject()).get(2));
/*     */     }
/*     */     
/*     */     public PdfArray getNames() {
/* 191 */       return ((PdfArray)getPdfObject()).getAsArray(1);
/*     */     }
/*     */     
/*     */     protected static PdfArray getDeviceNCsArray(PdfArray names, PdfObject alternateSpace, PdfObject tintTransform) {
/* 195 */       PdfArray deviceN = new PdfArray();
/* 196 */       deviceN.add((PdfObject)PdfName.DeviceN);
/* 197 */       deviceN.add((PdfObject)names);
/* 198 */       deviceN.add(alternateSpace);
/* 199 */       deviceN.add(tintTransform);
/* 200 */       return deviceN;
/*     */     }
/*     */   }
/*     */   
/*     */   public static class NChannel
/*     */     extends DeviceN
/*     */   {
/*     */     private static final long serialVersionUID = 5352964946869757972L;
/*     */     
/*     */     public NChannel(PdfArray pdfObject) {
/* 210 */       super(pdfObject);
/*     */     }
/*     */     
/*     */     public NChannel(PdfArray names, PdfObject alternateSpace, PdfObject tintTransform, PdfDictionary attributes) {
/* 214 */       this(getNChannelCsArray(names, alternateSpace, tintTransform, attributes));
/*     */     }
/*     */     
/*     */     public NChannel(List<String> names, PdfColorSpace alternateSpace, PdfFunction tintTransform, PdfDictionary attributes) {
/* 218 */       this(new PdfArray(names, true), alternateSpace.getPdfObject(), tintTransform.getPdfObject(), attributes);
/* 219 */       if (tintTransform.getInputSize() != 1 || tintTransform.getOutputSize() != alternateSpace.getNumberOfComponents()) {
/* 220 */         throw new PdfException("Function is not compatible with ColorSpace.", this);
/*     */       }
/*     */     }
/*     */     
/*     */     protected static PdfArray getNChannelCsArray(PdfArray names, PdfObject alternateSpace, PdfObject tintTransform, PdfDictionary attributes) {
/* 225 */       PdfArray nChannel = getDeviceNCsArray(names, alternateSpace, tintTransform);
/* 226 */       nChannel.add((PdfObject)attributes);
/* 227 */       return nChannel;
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public static class Pattern
/*     */     extends PdfColorSpace
/*     */   {
/*     */     private static final long serialVersionUID = 8057478102447278706L;
/*     */     
/*     */     protected boolean isWrappedObjectMustBeIndirect() {
/* 238 */       return false;
/*     */     }
/*     */     
/*     */     public Pattern() {
/* 242 */       super((PdfObject)PdfName.Pattern);
/*     */     }
/*     */     
/*     */     protected Pattern(PdfObject pdfObj) {
/* 246 */       super(pdfObj);
/*     */     }
/*     */ 
/*     */     
/*     */     public int getNumberOfComponents() {
/* 251 */       return 0;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static class UncoloredTilingPattern
/*     */     extends Pattern
/*     */   {
/*     */     private static final long serialVersionUID = -9030226298201261021L;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void flush() {
/* 268 */       super.flush();
/*     */     }
/*     */ 
/*     */     
/*     */     protected boolean isWrappedObjectMustBeIndirect() {
/* 273 */       return true;
/*     */     }
/*     */     
/*     */     public UncoloredTilingPattern(PdfArray pdfObject) {
/* 277 */       super((PdfObject)pdfObject);
/*     */     }
/*     */     
/*     */     public UncoloredTilingPattern(PdfColorSpace underlyingColorSpace) {
/* 281 */       super((PdfObject)new PdfArray(Arrays.asList(new PdfObject[] { (PdfObject)PdfName.Pattern, underlyingColorSpace.getPdfObject() })));
/*     */     }
/*     */ 
/*     */     
/*     */     public int getNumberOfComponents() {
/* 286 */       return PdfColorSpace.makeColorSpace(((PdfArray)getPdfObject()).get(1)).getNumberOfComponents();
/*     */     }
/*     */     
/*     */     public PdfColorSpace getUnderlyingColorSpace() {
/* 290 */       return PdfColorSpace.makeColorSpace(((PdfArray)getPdfObject()).get(1));
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/kernel/pdf/colorspace/PdfSpecialCs.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */