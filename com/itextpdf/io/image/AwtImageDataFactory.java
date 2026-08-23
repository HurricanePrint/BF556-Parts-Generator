/*     */ package com.itextpdf.io.image;
/*     */ 
/*     */ import java.awt.Color;
/*     */ import java.awt.Image;
/*     */ import java.awt.image.BufferedImage;
/*     */ import java.awt.image.PixelGrabber;
/*     */ import java.io.IOException;
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
/*     */ class AwtImageDataFactory
/*     */ {
/*     */   public static ImageData create(Image image, Color color) throws IOException {
/*  58 */     return create(image, color, false);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ImageData create(Image image, Color color, boolean forceBW) throws IOException {
/*  69 */     if (image instanceof BufferedImage) {
/*  70 */       BufferedImage bi = (BufferedImage)image;
/*  71 */       if (bi.getType() == 12 && bi.getColorModel().getPixelSize() == 1) {
/*  72 */         forceBW = true;
/*     */       }
/*     */     } 
/*     */     
/*  76 */     PixelGrabber pg = new PixelGrabber(image, 0, 0, -1, -1, true);
/*     */     try {
/*  78 */       pg.grabPixels();
/*  79 */     } catch (InterruptedException e) {
/*  80 */       throw new IOException("Java.awt.image was interrupted. Waiting for pixels");
/*     */     } 
/*  82 */     if ((pg.getStatus() & 0x80) != 0) {
/*  83 */       throw new IOException("Java.awt.image fetch aborted or errored");
/*     */     }
/*  85 */     int w = pg.getWidth();
/*  86 */     int h = pg.getHeight();
/*  87 */     int[] pixels = (int[])pg.getPixels();
/*  88 */     if (forceBW) {
/*  89 */       int byteWidth = w / 8 + (((w & 0x7) != 0) ? 1 : 0);
/*  90 */       byte[] arrayOfByte = new byte[byteWidth * h];
/*     */       
/*  92 */       int i = 0;
/*  93 */       int j = h * w;
/*  94 */       int transColor = 1;
/*  95 */       if (color != null)
/*     */       {
/*  97 */         transColor = (color.getRed() + color.getGreen() + color.getBlue() < 384) ? 0 : 1;
/*     */       }
/*  99 */       int[] arrayOfInt = null;
/* 100 */       int cbyte = 128;
/* 101 */       int wMarker = 0;
/* 102 */       int currByte = 0;
/* 103 */       if (color != null) {
/* 104 */         for (int k = 0; k < j; k++) {
/* 105 */           int alpha = pixels[k] >> 24 & 0xFF;
/* 106 */           if (alpha < 250) {
/* 107 */             if (transColor == 1) {
/* 108 */               currByte |= cbyte;
/*     */             }
/* 110 */           } else if ((pixels[k] & 0x888) != 0) {
/* 111 */             currByte |= cbyte;
/*     */           } 
/* 113 */           cbyte >>= 1;
/* 114 */           if (cbyte == 0 || wMarker + 1 >= w) {
/* 115 */             arrayOfByte[i++] = (byte)currByte;
/* 116 */             cbyte = 128;
/* 117 */             currByte = 0;
/*     */           } 
/* 119 */           wMarker++;
/* 120 */           if (wMarker >= w)
/* 121 */             wMarker = 0; 
/*     */         } 
/*     */       } else {
/* 124 */         for (int k = 0; k < j; k++) {
/* 125 */           if (arrayOfInt == null) {
/* 126 */             int alpha = pixels[k] >> 24 & 0xFF;
/* 127 */             if (alpha == 0) {
/* 128 */               arrayOfInt = new int[2];
/*     */               
/* 130 */               arrayOfInt[1] = ((pixels[k] & 0x888) != 0) ? 255 : 0; arrayOfInt[0] = ((pixels[k] & 0x888) != 0) ? 255 : 0;
/*     */             } 
/*     */           } 
/* 133 */           if ((pixels[k] & 0x888) != 0)
/* 134 */             currByte |= cbyte; 
/* 135 */           cbyte >>= 1;
/* 136 */           if (cbyte == 0 || wMarker + 1 >= w) {
/* 137 */             arrayOfByte[i++] = (byte)currByte;
/* 138 */             cbyte = 128;
/* 139 */             currByte = 0;
/*     */           } 
/* 141 */           wMarker++;
/* 142 */           if (wMarker >= w)
/* 143 */             wMarker = 0; 
/*     */         } 
/*     */       } 
/* 146 */       return ImageDataFactory.create(w, h, 1, 1, arrayOfByte, arrayOfInt);
/*     */     } 
/* 148 */     byte[] pixelsByte = new byte[w * h * 3];
/* 149 */     byte[] smask = null;
/*     */     
/* 151 */     int index = 0;
/* 152 */     int size = h * w;
/* 153 */     int red = 255;
/* 154 */     int green = 255;
/* 155 */     int blue = 255;
/* 156 */     if (color != null) {
/* 157 */       red = color.getRed();
/* 158 */       green = color.getGreen();
/* 159 */       blue = color.getBlue();
/*     */     } 
/* 161 */     int[] transparency = null;
/* 162 */     if (color != null) {
/* 163 */       for (int j = 0; j < size; j++) {
/* 164 */         int alpha = pixels[j] >> 24 & 0xFF;
/* 165 */         if (alpha < 250) {
/* 166 */           pixelsByte[index++] = (byte)red;
/* 167 */           pixelsByte[index++] = (byte)green;
/* 168 */           pixelsByte[index++] = (byte)blue;
/*     */         } else {
/* 170 */           pixelsByte[index++] = (byte)(pixels[j] >> 16 & 0xFF);
/* 171 */           pixelsByte[index++] = (byte)(pixels[j] >> 8 & 0xFF);
/* 172 */           pixelsByte[index++] = (byte)(pixels[j] & 0xFF);
/*     */         } 
/*     */       } 
/*     */     } else {
/* 176 */       int transparentPixel = 0;
/* 177 */       smask = new byte[w * h];
/* 178 */       boolean shades = false;
/* 179 */       for (int j = 0; j < size; j++) {
/* 180 */         byte alpha = smask[j] = (byte)(pixels[j] >> 24 & 0xFF);
/*     */         
/* 182 */         if (!shades) {
/* 183 */           if (alpha != 0 && alpha != -1) {
/* 184 */             shades = true;
/* 185 */           } else if (transparency == null) {
/* 186 */             if (alpha == 0) {
/* 187 */               transparentPixel = pixels[j] & 0xFFFFFF;
/* 188 */               transparency = new int[6];
/* 189 */               transparency[1] = transparentPixel >> 16 & 0xFF; transparency[0] = transparentPixel >> 16 & 0xFF;
/* 190 */               transparency[3] = transparentPixel >> 8 & 0xFF; transparency[2] = transparentPixel >> 8 & 0xFF;
/* 191 */               transparency[5] = transparentPixel & 0xFF; transparency[4] = transparentPixel & 0xFF;
/*     */ 
/*     */ 
/*     */ 
/*     */               
/* 196 */               for (int prevPixel = 0; prevPixel < j; prevPixel++) {
/* 197 */                 if ((pixels[prevPixel] & 0xFFFFFF) == transparentPixel) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */                   
/* 203 */                   shades = true;
/*     */                   break;
/*     */                 } 
/*     */               } 
/*     */             } 
/* 208 */           } else if ((pixels[j] & 0xFFFFFF) != transparentPixel && alpha == 0) {
/* 209 */             shades = true;
/* 210 */           } else if ((pixels[j] & 0xFFFFFF) == transparentPixel && alpha != 0) {
/* 211 */             shades = true;
/*     */           } 
/*     */         }
/* 214 */         pixelsByte[index++] = (byte)(pixels[j] >> 16 & 0xFF);
/* 215 */         pixelsByte[index++] = (byte)(pixels[j] >> 8 & 0xFF);
/* 216 */         pixelsByte[index++] = (byte)(pixels[j] & 0xFF);
/*     */       } 
/* 218 */       if (shades) {
/* 219 */         transparency = null;
/*     */       } else {
/* 221 */         smask = null;
/*     */       } 
/* 223 */     }  ImageData img = ImageDataFactory.create(w, h, 3, 8, pixelsByte, transparency);
/* 224 */     if (smask != null) {
/* 225 */       ImageData sm = ImageDataFactory.create(w, h, 1, 8, smask, null);
/* 226 */       sm.makeMask();
/* 227 */       img.setImageMask(sm);
/*     */     } 
/* 229 */     return img;
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/io/image/AwtImageDataFactory.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */