/*     */ package com.itextpdf.kernel.pdf.function;
/*     */ 
/*     */ import com.itextpdf.kernel.pdf.PdfArray;
/*     */ import com.itextpdf.kernel.pdf.PdfDictionary;
/*     */ import com.itextpdf.kernel.pdf.PdfName;
/*     */ import com.itextpdf.kernel.pdf.PdfNumber;
/*     */ import com.itextpdf.kernel.pdf.PdfObject;
/*     */ import com.itextpdf.kernel.pdf.PdfObjectWrapper;
/*     */ import com.itextpdf.kernel.pdf.PdfStream;
/*     */ import com.itextpdf.kernel.pdf.colorspace.PdfColorSpace;
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
/*     */ public class PdfFunction
/*     */   extends PdfObjectWrapper<PdfObject>
/*     */ {
/*     */   private static final long serialVersionUID = -4689848231547125520L;
/*     */   
/*     */   public PdfFunction(PdfObject pdfObject) {
/*  63 */     super(pdfObject);
/*     */   }
/*     */   
/*     */   public int getType() {
/*  67 */     return ((PdfDictionary)getPdfObject()).getAsInt(PdfName.FunctionType).intValue();
/*     */   }
/*     */   
/*     */   public boolean checkCompatibilityWithColorSpace(PdfColorSpace alternateSpace) {
/*  71 */     return true;
/*     */   }
/*     */   
/*     */   public int getInputSize() {
/*  75 */     return ((PdfDictionary)getPdfObject()).getAsArray(PdfName.Domain).size() / 2;
/*     */   }
/*     */   
/*     */   public int getOutputSize() {
/*  79 */     PdfArray range = ((PdfDictionary)getPdfObject()).getAsArray(PdfName.Range);
/*  80 */     return (range == null) ? 0 : (range.size() / 2);
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
/*  92 */     super.flush();
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean isWrappedObjectMustBeIndirect() {
/*  97 */     return true;
/*     */   }
/*     */   
/*     */   public static class Type0
/*     */     extends PdfFunction {
/*     */     private static final long serialVersionUID = 72188160295017639L;
/*     */     
/*     */     public Type0(PdfStream pdfObject) {
/* 105 */       super((PdfObject)pdfObject);
/*     */     }
/*     */     
/*     */     public Type0(PdfArray domain, PdfArray range, PdfArray size, PdfNumber bitsPerSample, byte[] samples) {
/* 109 */       this(domain, range, size, bitsPerSample, (PdfNumber)null, (PdfArray)null, (PdfArray)null, samples);
/*     */     }
/*     */     
/*     */     public Type0(PdfArray domain, PdfArray range, PdfArray size, PdfNumber bitsPerSample, PdfNumber order, PdfArray encode, PdfArray decode, byte[] samples) {
/* 113 */       this(makeType0(domain, range, size, bitsPerSample, order, encode, decode, samples));
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean checkCompatibilityWithColorSpace(PdfColorSpace alternateSpace) {
/* 118 */       return (getInputSize() == 1 && getOutputSize() == alternateSpace.getNumberOfComponents());
/*     */     }
/*     */     
/*     */     private static PdfStream makeType0(PdfArray domain, PdfArray range, PdfArray size, PdfNumber bitsPerSample, PdfNumber order, PdfArray encode, PdfArray decode, byte[] samples) {
/* 122 */       PdfStream stream = new PdfStream(samples);
/* 123 */       stream.put(PdfName.FunctionType, (PdfObject)new PdfNumber(0));
/* 124 */       stream.put(PdfName.Domain, (PdfObject)domain);
/* 125 */       stream.put(PdfName.Range, (PdfObject)range);
/* 126 */       stream.put(PdfName.Size, (PdfObject)size);
/* 127 */       stream.put(PdfName.BitsPerSample, (PdfObject)bitsPerSample);
/* 128 */       if (order != null)
/* 129 */         stream.put(PdfName.Order, (PdfObject)order); 
/* 130 */       if (encode != null)
/* 131 */         stream.put(PdfName.Encode, (PdfObject)encode); 
/* 132 */       if (decode != null)
/* 133 */         stream.put(PdfName.Decode, (PdfObject)decode); 
/* 134 */       return stream;
/*     */     }
/*     */   }
/*     */   
/*     */   public static class Type2
/*     */     extends PdfFunction {
/*     */     private static final long serialVersionUID = -4680660755798263091L;
/*     */     
/*     */     public Type2(PdfDictionary pdfObject) {
/* 143 */       super((PdfObject)pdfObject);
/*     */     }
/*     */     
/*     */     public Type2(PdfArray domain, PdfArray range, PdfNumber n) {
/* 147 */       this(domain, range, null, null, n);
/*     */     }
/*     */     
/*     */     public Type2(PdfArray domain, PdfArray range, PdfArray c0, PdfArray c1, PdfNumber n) {
/* 151 */       this(makeType2(domain, range, c0, c1, n));
/*     */     }
/*     */     
/*     */     private static PdfDictionary makeType2(PdfArray domain, PdfArray range, PdfArray c0, PdfArray c1, PdfNumber n) {
/* 155 */       PdfDictionary dictionary = new PdfDictionary();
/* 156 */       dictionary.put(PdfName.FunctionType, (PdfObject)new PdfNumber(2));
/* 157 */       dictionary.put(PdfName.Domain, (PdfObject)domain);
/* 158 */       if (range != null)
/* 159 */         dictionary.put(PdfName.Range, (PdfObject)range); 
/* 160 */       if (c0 != null)
/* 161 */         dictionary.put(PdfName.C0, (PdfObject)c0); 
/* 162 */       if (c1 != null)
/* 163 */         dictionary.put(PdfName.C1, (PdfObject)c1); 
/* 164 */       dictionary.put(PdfName.N, (PdfObject)n);
/* 165 */       return dictionary;
/*     */     }
/*     */   }
/*     */   
/*     */   public static class Type3
/*     */     extends PdfFunction {
/*     */     private static final long serialVersionUID = 3257795209767645155L;
/*     */     
/*     */     public Type3(PdfDictionary pdfObject) {
/* 174 */       super((PdfObject)pdfObject);
/*     */     }
/*     */     
/*     */     public Type3(PdfArray domain, PdfArray range, PdfArray functions, PdfArray bounds, PdfArray encode) {
/* 178 */       this(makeType3(domain, range, functions, bounds, encode));
/*     */     }
/*     */     
/*     */     public Type3(PdfArray domain, PdfArray range, List<PdfFunction> functions, PdfArray bounds, PdfArray encode) {
/* 182 */       this(domain, range, getFunctionsArray(functions), bounds, encode);
/*     */     }
/*     */     
/*     */     private static PdfDictionary makeType3(PdfArray domain, PdfArray range, PdfArray functions, PdfArray bounds, PdfArray encode) {
/* 186 */       PdfDictionary dictionary = new PdfDictionary();
/* 187 */       dictionary.put(PdfName.FunctionType, (PdfObject)new PdfNumber(3));
/* 188 */       dictionary.put(PdfName.Domain, (PdfObject)domain);
/* 189 */       if (range != null) {
/* 190 */         dictionary.put(PdfName.Range, (PdfObject)range);
/*     */       }
/* 192 */       dictionary.put(PdfName.Functions, (PdfObject)functions);
/* 193 */       dictionary.put(PdfName.Bounds, (PdfObject)bounds);
/* 194 */       dictionary.put(PdfName.Encode, (PdfObject)encode);
/* 195 */       return dictionary;
/*     */     }
/*     */     
/*     */     private static PdfArray getFunctionsArray(List<PdfFunction> functions) {
/* 199 */       PdfArray array = new PdfArray();
/* 200 */       for (PdfFunction function : functions)
/* 201 */         array.add(function.getPdfObject()); 
/* 202 */       return array;
/*     */     }
/*     */   }
/*     */   
/*     */   public static class Type4
/*     */     extends PdfFunction {
/*     */     private static final long serialVersionUID = -5415624427845744618L;
/*     */     
/*     */     public Type4(PdfStream pdfObject) {
/* 211 */       super((PdfObject)pdfObject);
/*     */     }
/*     */     
/*     */     public Type4(PdfArray domain, PdfArray range, byte[] ps) {
/* 215 */       this(makeType4(domain, range, ps));
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean checkCompatibilityWithColorSpace(PdfColorSpace alternateSpace) {
/* 220 */       return (getInputSize() == 1 && getOutputSize() == alternateSpace.getNumberOfComponents());
/*     */     }
/*     */     
/*     */     private static PdfStream makeType4(PdfArray domain, PdfArray range, byte[] ps) {
/* 224 */       PdfStream stream = new PdfStream(ps);
/* 225 */       stream.put(PdfName.FunctionType, (PdfObject)new PdfNumber(4));
/* 226 */       stream.put(PdfName.Domain, (PdfObject)domain);
/* 227 */       stream.put(PdfName.Range, (PdfObject)range);
/* 228 */       return stream;
/*     */     }
/*     */   }
/*     */   
/*     */   public static PdfFunction makeFunction(PdfDictionary pdfObject) {
/* 233 */     switch (pdfObject.getType()) {
/*     */       case 0:
/* 235 */         return new Type0((PdfStream)pdfObject);
/*     */       case 2:
/* 237 */         return new Type2(pdfObject);
/*     */       case 3:
/* 239 */         return new Type3(pdfObject);
/*     */       case 4:
/* 241 */         return new Type4((PdfStream)pdfObject);
/*     */     } 
/* 243 */     return null;
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/kernel/pdf/function/PdfFunction.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */