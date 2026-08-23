/*     */ package com.itextpdf.kernel.pdf.filters;
/*     */ 
/*     */ import com.itextpdf.kernel.PdfException;
/*     */ import com.itextpdf.kernel.pdf.MemoryLimitsAwareException;
/*     */ import com.itextpdf.kernel.pdf.MemoryLimitsAwareFilter;
/*     */ import com.itextpdf.kernel.pdf.PdfDictionary;
/*     */ import com.itextpdf.kernel.pdf.PdfName;
/*     */ import com.itextpdf.kernel.pdf.PdfNumber;
/*     */ import com.itextpdf.kernel.pdf.PdfObject;
/*     */ import java.io.ByteArrayInputStream;
/*     */ import java.io.ByteArrayOutputStream;
/*     */ import java.io.DataInputStream;
/*     */ import java.io.IOException;
/*     */ import java.util.zip.InflaterInputStream;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class FlateDecodeFilter
/*     */   extends MemoryLimitsAwareFilter
/*     */ {
/*     */   @Deprecated
/*     */   private boolean strictDecoding = false;
/*     */   
/*     */   public FlateDecodeFilter() {
/*  77 */     this(false);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public FlateDecodeFilter(boolean strictDecoding) {
/*  88 */     this.strictDecoding = strictDecoding;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public boolean isStrictDecoding() {
/*  99 */     return this.strictDecoding;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static byte[] flateDecode(byte[] in, boolean strict) {
/* 110 */     return flateDecodeInternal(in, strict, new ByteArrayOutputStream());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static byte[] decodePredictor(byte[] in, PdfObject decodeParams) {
/* 119 */     if (decodeParams == null || decodeParams.getType() != 3) {
/* 120 */       return in;
/*     */     }
/* 122 */     PdfDictionary dic = (PdfDictionary)decodeParams;
/* 123 */     PdfObject obj = dic.get(PdfName.Predictor);
/* 124 */     if (obj == null || obj.getType() != 8) {
/* 125 */       return in;
/*     */     }
/* 127 */     int predictor = ((PdfNumber)obj).intValue();
/* 128 */     if (predictor < 10 && predictor != 2) {
/* 129 */       return in;
/*     */     }
/* 131 */     int width = getNumberOrDefault(dic, PdfName.Columns, 1);
/* 132 */     int colors = getNumberOrDefault(dic, PdfName.Colors, 1);
/* 133 */     int bpc = getNumberOrDefault(dic, PdfName.BitsPerComponent, 8);
/* 134 */     DataInputStream dataStream = new DataInputStream(new ByteArrayInputStream(in));
/* 135 */     ByteArrayOutputStream fout = new ByteArrayOutputStream(in.length);
/* 136 */     int bytesPerPixel = colors * bpc / 8;
/* 137 */     int bytesPerRow = (colors * width * bpc + 7) / 8;
/* 138 */     byte[] curr = new byte[bytesPerRow];
/* 139 */     byte[] prior = new byte[bytesPerRow];
/* 140 */     if (predictor == 2) {
/* 141 */       if (bpc == 8) {
/* 142 */         int numRows = in.length / bytesPerRow;
/* 143 */         for (int row = 0; row < numRows; row++) {
/* 144 */           int rowStart = row * bytesPerRow;
/* 145 */           for (int col = bytesPerPixel; col < bytesPerRow; col++) {
/* 146 */             in[rowStart + col] = (byte)(in[rowStart + col] + in[rowStart + col - bytesPerPixel]);
/*     */           }
/*     */         } 
/*     */       } 
/* 150 */       return in;
/*     */     } 
/*     */     
/*     */     while (true) {
/*     */       int filter, i;
/*     */       
/*     */       try {
/* 157 */         filter = dataStream.read();
/* 158 */         if (filter < 0) {
/* 159 */           return fout.toByteArray();
/*     */         }
/* 161 */         dataStream.readFully(curr, 0, bytesPerRow);
/* 162 */       } catch (Exception e) {
/* 163 */         return fout.toByteArray();
/*     */       } 
/*     */       
/* 166 */       switch (filter) {
/*     */         case 0:
/*     */           break;
/*     */         case 1:
/* 170 */           for (i = bytesPerPixel; i < bytesPerRow; i++) {
/* 171 */             curr[i] = (byte)(curr[i] + curr[i - bytesPerPixel]);
/*     */           }
/*     */           break;
/*     */         case 2:
/* 175 */           for (i = 0; i < bytesPerRow; i++) {
/* 176 */             curr[i] = (byte)(curr[i] + prior[i]);
/*     */           }
/*     */           break;
/*     */         case 3:
/* 180 */           for (i = 0; i < bytesPerPixel; i++) {
/* 181 */             curr[i] = (byte)(curr[i] + (byte)(prior[i] / 2));
/*     */           }
/* 183 */           for (i = bytesPerPixel; i < bytesPerRow; i++) {
/* 184 */             curr[i] = (byte)(curr[i] + (byte)(((curr[i - bytesPerPixel] & 0xFF) + (prior[i] & 0xFF)) / 2));
/*     */           }
/*     */           break;
/*     */         case 4:
/* 188 */           for (i = 0; i < bytesPerPixel; i++) {
/* 189 */             curr[i] = (byte)(curr[i] + prior[i]);
/*     */           }
/*     */           
/* 192 */           for (i = bytesPerPixel; i < bytesPerRow; i++) {
/* 193 */             int ret, a = curr[i - bytesPerPixel] & 0xFF;
/* 194 */             int b = prior[i] & 0xFF;
/* 195 */             int c = prior[i - bytesPerPixel] & 0xFF;
/*     */             
/* 197 */             int p = a + b - c;
/* 198 */             int pa = Math.abs(p - a);
/* 199 */             int pb = Math.abs(p - b);
/* 200 */             int pc = Math.abs(p - c);
/*     */ 
/*     */ 
/*     */             
/* 204 */             if (pa <= pb && pa <= pc) {
/* 205 */               ret = a;
/* 206 */             } else if (pb <= pc) {
/* 207 */               ret = b;
/*     */             } else {
/* 209 */               ret = c;
/*     */             } 
/* 211 */             curr[i] = (byte)(curr[i] + (byte)ret);
/*     */           } 
/*     */           break;
/*     */         
/*     */         default:
/* 216 */           throw new PdfException("PNG filter unknown.");
/*     */       } 
/*     */       try {
/* 219 */         fout.write(curr);
/* 220 */       } catch (IOException iOException) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 226 */       byte[] tmp = prior;
/* 227 */       prior = curr;
/* 228 */       curr = tmp;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public byte[] decode(byte[] b, PdfName filterName, PdfObject decodeParams, PdfDictionary streamDictionary) {
/* 237 */     ByteArrayOutputStream outputStream = enableMemoryLimitsAwareHandler(streamDictionary);
/* 238 */     byte[] res = flateDecodeInternal(b, true, outputStream);
/* 239 */     if (res == null && !this.strictDecoding) {
/* 240 */       outputStream.reset();
/* 241 */       res = flateDecodeInternal(b, false, outputStream);
/*     */     } 
/* 243 */     b = decodePredictor(res, decodeParams);
/* 244 */     return b;
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
/*     */   @Deprecated
/*     */   public FlateDecodeFilter setStrictDecoding(boolean strict) {
/* 257 */     this.strictDecoding = strict;
/* 258 */     return this;
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
/*     */   protected static byte[] flateDecodeInternal(byte[] in, boolean strict, ByteArrayOutputStream out) {
/* 270 */     ByteArrayInputStream stream = new ByteArrayInputStream(in);
/* 271 */     InflaterInputStream zip = new InflaterInputStream(stream);
/* 272 */     byte[] b = new byte[strict ? 4092 : 1];
/*     */     try {
/*     */       int n;
/* 275 */       while ((n = zip.read(b)) >= 0) {
/* 276 */         out.write(b, 0, n);
/*     */       }
/* 278 */       zip.close();
/* 279 */       out.close();
/* 280 */       return out.toByteArray();
/* 281 */     } catch (MemoryLimitsAwareException e) {
/* 282 */       throw e;
/* 283 */     } catch (Exception e) {
/* 284 */       if (strict) {
/* 285 */         return null;
/*     */       }
/* 287 */       return out.toByteArray();
/*     */     } 
/*     */   }
/*     */   
/*     */   private static int getNumberOrDefault(PdfDictionary dict, PdfName key, int defaultInt) {
/* 292 */     int result = defaultInt;
/* 293 */     PdfObject obj = dict.get(key);
/*     */     
/* 295 */     if (obj != null && obj.getType() == 8) {
/* 296 */       result = ((PdfNumber)obj).intValue();
/*     */     }
/* 298 */     return result;
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/kernel/pdf/filters/FlateDecodeFilter.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */