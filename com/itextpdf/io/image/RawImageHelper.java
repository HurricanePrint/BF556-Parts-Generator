/*     */ package com.itextpdf.io.image;
/*     */ 
/*     */ import com.itextpdf.io.IOException;
/*     */ import com.itextpdf.io.codec.CCITTG4Encoder;
/*     */ import com.itextpdf.io.codec.TIFFFaxDecoder;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
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
/*     */ public final class RawImageHelper
/*     */ {
/*     */   public static void updateImageAttributes(RawImageData image, Map<String, Object> additional) {
/*  56 */     if (!image.isRawImage()) {
/*  57 */       throw new IllegalArgumentException("Raw image expected.");
/*     */     }
/*  59 */     int colorSpace = image.getColorSpace();
/*  60 */     int typeCCITT = image.getTypeCcitt();
/*  61 */     if (typeCCITT > 255) {
/*  62 */       if (!image.isMask())
/*  63 */         image.setColorSpace(1); 
/*  64 */       image.setBpc(1);
/*  65 */       image.setFilter("CCITTFaxDecode");
/*  66 */       int k = typeCCITT - 257;
/*  67 */       Map<String, Object> decodeparms = new HashMap<>();
/*  68 */       if (k != 0)
/*  69 */         decodeparms.put("K", Integer.valueOf(k)); 
/*  70 */       if ((colorSpace & 0x1) != 0)
/*  71 */         decodeparms.put("BlackIs1", Boolean.valueOf(true)); 
/*  72 */       if ((colorSpace & 0x2) != 0)
/*  73 */         decodeparms.put("EncodedByteAlign", Boolean.valueOf(true)); 
/*  74 */       if ((colorSpace & 0x4) != 0)
/*  75 */         decodeparms.put("EndOfLine", Boolean.valueOf(true)); 
/*  76 */       if ((colorSpace & 0x8) != 0)
/*  77 */         decodeparms.put("EndOfBlock", Boolean.valueOf(false)); 
/*  78 */       decodeparms.put("Columns", Float.valueOf(image.getWidth()));
/*  79 */       decodeparms.put("Rows", Float.valueOf(image.getHeight()));
/*  80 */       image.decodeParms = decodeparms;
/*     */     } else {
/*  82 */       switch (colorSpace) {
/*     */         case 1:
/*  84 */           if (image.isInverted())
/*  85 */             image.decode = new float[] { 1.0F, 0.0F }; 
/*     */           break;
/*     */         case 3:
/*  88 */           if (image.isInverted()) {
/*  89 */             image.decode = new float[] { 1.0F, 0.0F, 1.0F, 0.0F, 1.0F, 0.0F };
/*     */           }
/*     */           break;
/*     */         default:
/*  93 */           if (image.isInverted())
/*  94 */             image.decode = new float[] { 1.0F, 0.0F, 1.0F, 0.0F, 1.0F, 0.0F, 1.0F, 0.0F };  break;
/*     */       } 
/*  96 */       if (additional != null) {
/*  97 */         image.setImageAttributes(additional);
/*     */       }
/*  99 */       if (image.isMask() && (image.getBpc() == 1 || image.getBpc() > 8))
/* 100 */         image.setColorSpace(-1); 
/* 101 */       if (image.isDeflated()) {
/* 102 */         image.setFilter("FlateDecode");
/*     */       }
/*     */     } 
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
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected static void updateRawImageParameters(RawImageData image, int width, int height, int components, int bpc, byte[] data) {
/* 120 */     image.setHeight(height);
/* 121 */     image.setWidth(width);
/* 122 */     if (components != 1 && components != 3 && components != 4)
/* 123 */       throw new IOException("Components must be 1, 3 or 4."); 
/* 124 */     if (bpc != 1 && bpc != 2 && bpc != 4 && bpc != 8)
/* 125 */       throw new IOException("Bits per component must be 1, 2, 4 or 8."); 
/* 126 */     image.setColorSpace(components);
/* 127 */     image.setBpc(bpc);
/* 128 */     image.data = data;
/*     */   }
/*     */ 
/*     */   
/*     */   protected static void updateRawImageParameters(RawImageData image, int width, int height, int components, int bpc, byte[] data, int[] transparency) {
/* 133 */     if (transparency != null && transparency.length != components * 2)
/* 134 */       throw new IOException("Transparency length must be equal to 2 with CCITT images"); 
/* 135 */     if (components == 1 && bpc == 1) {
/* 136 */       byte[] g4 = CCITTG4Encoder.compress(data, width, height);
/* 137 */       updateRawImageParameters(image, width, height, false, 256, 1, g4, transparency);
/*     */     } else {
/*     */       
/* 140 */       updateRawImageParameters(image, width, height, components, bpc, data);
/* 141 */       image.setTransparency(transparency);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected static void updateRawImageParameters(RawImageData image, int width, int height, boolean reverseBits, int typeCCITT, int parameters, byte[] data, int[] transparency) {
/* 147 */     if (transparency != null && transparency.length != 2)
/* 148 */       throw new IOException("Transparency length must be equal to 2 with CCITT images"); 
/* 149 */     updateCcittImageParameters(image, width, height, reverseBits, typeCCITT, parameters, data);
/* 150 */     image.setTransparency(transparency);
/*     */   }
/*     */   
/*     */   protected static void updateCcittImageParameters(RawImageData image, int width, int height, boolean reverseBits, int typeCcitt, int parameters, byte[] data) {
/* 154 */     if (typeCcitt != 256 && typeCcitt != 257 && typeCcitt != 258)
/* 155 */       throw new IOException("CCITT compression type must be CCITTG4, CCITTG3_1D or CCITTG3_2D."); 
/* 156 */     if (reverseBits)
/* 157 */       TIFFFaxDecoder.reverseBits(data); 
/* 158 */     image.setHeight(height);
/* 159 */     image.setWidth(width);
/* 160 */     image.setColorSpace(parameters);
/* 161 */     image.setTypeCcitt(typeCcitt);
/* 162 */     image.data = data;
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/io/image/RawImageHelper.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */