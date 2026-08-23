/*     */ package com.itextpdf.io.image;
/*     */ 
/*     */ import com.itextpdf.io.IOException;
/*     */ import com.itextpdf.io.colors.IccProfile;
/*     */ import com.itextpdf.io.util.MessageFormatUtil;
/*     */ import com.itextpdf.io.util.StreamUtil;
/*     */ import java.io.ByteArrayInputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import org.slf4j.Logger;
/*     */ import org.slf4j.LoggerFactory;
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
/*     */ class JpegImageHelper
/*     */ {
/*     */   private static final int NOT_A_MARKER = -1;
/*     */   private static final int VALID_MARKER = 0;
/*  73 */   private static final int[] VALID_MARKERS = new int[] { 192, 193, 194 };
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static final int UNSUPPORTED_MARKER = 1;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  83 */   private static final int[] UNSUPPORTED_MARKERS = new int[] { 195, 197, 198, 199, 200, 201, 202, 203, 205, 206, 207 };
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static final int NOPARAM_MARKER = 2;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  93 */   private static final int[] NOPARAM_MARKERS = new int[] { 208, 209, 210, 211, 212, 213, 214, 215, 216, 1 };
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static final int M_APP0 = 224;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static final int M_APP2 = 226;
/*     */ 
/*     */ 
/*     */   
/*     */   private static final int M_APPE = 238;
/*     */ 
/*     */ 
/*     */   
/*     */   private static final int M_APPD = 237;
/*     */ 
/*     */ 
/*     */   
/* 115 */   private static final byte[] JFIF_ID = new byte[] { 74, 70, 73, 70, 0 };
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 120 */   private static final byte[] PS_8BIM_RESO = new byte[] { 56, 66, 73, 77, 3, -19 };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void processImage(ImageData image) {
/* 129 */     if (image.getOriginalType() != ImageType.JPEG)
/* 130 */       throw new IllegalArgumentException("JPEG image expected"); 
/* 131 */     InputStream jpegStream = null;
/*     */     try {
/*     */       String errorID;
/* 134 */       if (image.getData() == null) {
/* 135 */         image.loadData();
/* 136 */         errorID = image.getUrl().toString();
/*     */       } else {
/* 138 */         errorID = "Byte array";
/*     */       } 
/* 140 */       jpegStream = new ByteArrayInputStream(image.getData());
/* 141 */       image.imageSize = (image.getData()).length;
/* 142 */       processParameters(jpegStream, errorID, image);
/* 143 */     } catch (IOException e) {
/* 144 */       throw new IOException("JPEG image exception.", e);
/*     */     } finally {
/* 146 */       if (jpegStream != null) {
/*     */         try {
/* 148 */           jpegStream.close();
/* 149 */         } catch (IOException iOException) {}
/*     */       }
/*     */     } 
/* 152 */     updateAttributes(image);
/*     */   }
/*     */   
/*     */   private static void updateAttributes(ImageData image) {
/* 156 */     image.filter = "DCTDecode";
/* 157 */     if (image.getColorTransform() == 0) {
/* 158 */       Map<String, Object> decodeParms = new HashMap<>();
/* 159 */       decodeParms.put("ColorTransform", Integer.valueOf(0));
/* 160 */       image.decodeParms = decodeParms;
/*     */     } 
/* 162 */     if (image.getColorSpace() != 1 && image.getColorSpace() != 3 && image.isInverted()) {
/* 163 */       image.decode = new float[] { 1.0F, 0.0F, 1.0F, 0.0F, 1.0F, 0.0F, 1.0F, 0.0F };
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void processParameters(InputStream jpegStream, String errorID, ImageData image) throws IOException {
/* 174 */     byte[][] icc = (byte[][])null;
/* 175 */     if (jpegStream.read() != 255 || jpegStream.read() != 216) {
/* 176 */       throw (new IOException("{0} is not a valid jpeg file.")).setMessageParams(new Object[] { errorID });
/*     */     }
/* 178 */     boolean firstPass = true;
/*     */     
/*     */     while (true) {
/* 181 */       int v = jpegStream.read();
/* 182 */       if (v < 0)
/* 183 */         throw new IOException("Premature EOF while reading JPEG."); 
/* 184 */       if (v == 255) {
/* 185 */         int marker = jpegStream.read();
/* 186 */         if (firstPass && marker == 224) {
/* 187 */           firstPass = false;
/* 188 */           int len = getShort(jpegStream);
/* 189 */           if (len < 16) {
/* 190 */             StreamUtil.skip(jpegStream, (len - 2));
/*     */             continue;
/*     */           } 
/* 193 */           byte[] bcomp = new byte[JFIF_ID.length];
/* 194 */           int r = jpegStream.read(bcomp);
/* 195 */           if (r != bcomp.length)
/* 196 */             throw (new IOException("{0} corrupted jfif marker.")).setMessageParams(new Object[] { errorID }); 
/* 197 */           boolean found = true;
/* 198 */           for (int k = 0; k < bcomp.length; k++) {
/* 199 */             if (bcomp[k] != JFIF_ID[k]) {
/* 200 */               found = false;
/*     */               break;
/*     */             } 
/*     */           } 
/* 204 */           if (!found) {
/* 205 */             StreamUtil.skip(jpegStream, (len - 2 - bcomp.length));
/*     */             continue;
/*     */           } 
/* 208 */           StreamUtil.skip(jpegStream, 2L);
/* 209 */           int units = jpegStream.read();
/* 210 */           int dx = getShort(jpegStream);
/* 211 */           int dy = getShort(jpegStream);
/* 212 */           if (units == 1) {
/* 213 */             image.setDpi(dx, dy);
/* 214 */           } else if (units == 2) {
/* 215 */             image.setDpi((int)(dx * 2.54F + 0.5F), (int)(dy * 2.54F + 0.5F));
/*     */           } 
/* 217 */           StreamUtil.skip(jpegStream, (len - 2 - bcomp.length - 7));
/*     */           continue;
/*     */         } 
/* 220 */         if (marker == 238) {
/* 221 */           int len = getShort(jpegStream) - 2;
/* 222 */           byte[] byteappe = new byte[len];
/* 223 */           for (int k = 0; k < len; k++) {
/* 224 */             byteappe[k] = (byte)jpegStream.read();
/*     */           }
/* 226 */           if (byteappe.length >= 12) {
/* 227 */             String appe = new String(byteappe, 0, 5, "ISO-8859-1");
/* 228 */             if (appe.equals("Adobe")) {
/* 229 */               image.setInverted(true);
/*     */             }
/*     */           } 
/*     */           continue;
/*     */         } 
/* 234 */         if (marker == 226) {
/* 235 */           int len = getShort(jpegStream) - 2;
/* 236 */           byte[] byteapp2 = new byte[len];
/* 237 */           for (int k = 0; k < len; k++) {
/* 238 */             byteapp2[k] = (byte)jpegStream.read();
/*     */           }
/* 240 */           if (byteapp2.length >= 14) {
/* 241 */             String app2 = new String(byteapp2, 0, 11, "ISO-8859-1");
/* 242 */             if (app2.equals("ICC_PROFILE")) {
/* 243 */               int order = byteapp2[12] & 0xFF;
/* 244 */               int count = byteapp2[13] & 0xFF;
/*     */               
/* 246 */               if (order < 1)
/* 247 */                 order = 1; 
/* 248 */               if (count < 1)
/* 249 */                 count = 1; 
/* 250 */               if (icc == null)
/* 251 */                 icc = new byte[count][]; 
/* 252 */               icc[order - 1] = byteapp2;
/*     */             } 
/*     */           } 
/*     */           continue;
/*     */         } 
/* 257 */         if (marker == 237) {
/* 258 */           int len = getShort(jpegStream) - 2;
/* 259 */           byte[] byteappd = new byte[len]; int k;
/* 260 */           for (k = 0; k < len; k++) {
/* 261 */             byteappd[k] = (byte)jpegStream.read();
/*     */           }
/*     */ 
/*     */           
/* 265 */           for (k = 0; k < len - PS_8BIM_RESO.length; k++) {
/* 266 */             boolean found = true;
/* 267 */             for (int j = 0; j < PS_8BIM_RESO.length; j++) {
/* 268 */               if (byteappd[k + j] != PS_8BIM_RESO[j]) {
/* 269 */                 found = false;
/*     */                 break;
/*     */               } 
/*     */             } 
/* 273 */             if (found) {
/*     */               break;
/*     */             }
/*     */           } 
/* 277 */           k += PS_8BIM_RESO.length;
/* 278 */           if (k < len - PS_8BIM_RESO.length) {
/*     */ 
/*     */             
/* 281 */             byte namelength = byteappd[k];
/*     */             
/* 283 */             namelength = (byte)(namelength + 1);
/*     */             
/* 285 */             if (namelength % 2 == 1) {
/* 286 */               namelength = (byte)(namelength + 1);
/*     */             }
/* 288 */             k += namelength;
/*     */             
/* 290 */             int resosize = (byteappd[k] << 24) + (byteappd[k + 1] << 16) + (byteappd[k + 2] << 8) + byteappd[k + 3];
/*     */             
/* 292 */             if (resosize != 16) {
/*     */               continue;
/*     */             }
/*     */ 
/*     */             
/* 297 */             k += 4;
/* 298 */             int dx = (byteappd[k] << 8) + (byteappd[k + 1] & 0xFF);
/* 299 */             k += 2;
/*     */             
/* 301 */             k += 2;
/* 302 */             int unitsx = (byteappd[k] << 8) + (byteappd[k + 1] & 0xFF);
/* 303 */             k += 2;
/*     */             
/* 305 */             k += 2;
/* 306 */             int dy = (byteappd[k] << 8) + (byteappd[k + 1] & 0xFF);
/* 307 */             k += 2;
/*     */             
/* 309 */             k += 2;
/* 310 */             int unitsy = (byteappd[k] << 8) + (byteappd[k + 1] & 0xFF);
/*     */             
/* 312 */             if (unitsx == 1 || unitsx == 2) {
/* 313 */               dx = (unitsx == 2) ? (int)(dx * 2.54F + 0.5F) : dx;
/*     */               
/* 315 */               if (image.getDpiX() != 0 && image.getDpiX() != dx) {
/* 316 */                 Logger logger = LoggerFactory.getLogger(JpegImageHelper.class);
/* 317 */                 logger.debug(MessageFormatUtil.format("Inconsistent metadata (dpiX: {0} vs {1})", new Object[] { Integer.valueOf(image.getDpiX()), Integer.valueOf(dx) }));
/*     */               } else {
/* 319 */                 image.setDpi(dx, image.getDpiY());
/*     */               } 
/*     */             } 
/* 322 */             if (unitsy == 1 || unitsy == 2) {
/* 323 */               dy = (unitsy == 2) ? (int)(dy * 2.54F + 0.5F) : dy;
/*     */               
/* 325 */               if (image.getDpiY() != 0 && image.getDpiY() != dy) {
/* 326 */                 Logger logger = LoggerFactory.getLogger(JpegImageHelper.class);
/* 327 */                 logger.debug(MessageFormatUtil.format("Inconsistent metadata (dpiY: {0} vs {1})", new Object[] { Integer.valueOf(image.getDpiY()), Integer.valueOf(dy) })); continue;
/*     */               } 
/* 329 */               image.setDpi(image.getDpiX(), dx);
/*     */             } 
/*     */           } 
/*     */           
/*     */           continue;
/*     */         } 
/* 335 */         firstPass = false;
/* 336 */         int markertype = marker(marker);
/* 337 */         if (markertype == 0) {
/* 338 */           StreamUtil.skip(jpegStream, 2L);
/* 339 */           if (jpegStream.read() != 8) {
/* 340 */             throw (new IOException("{0} must have 8 bits per component.")).setMessageParams(new Object[] { errorID });
/*     */           }
/* 342 */           image.setHeight(getShort(jpegStream));
/* 343 */           image.setWidth(getShort(jpegStream));
/* 344 */           image.setColorSpace(jpegStream.read());
/* 345 */           image.setBpc(8); break;
/*     */         } 
/* 347 */         if (markertype == 1)
/* 348 */           throw (new IOException("{0} unsupported jpeg marker {1}.")).setMessageParams(new Object[] { errorID, Integer.toString(marker) }); 
/* 349 */         if (markertype != 2) {
/* 350 */           StreamUtil.skip(jpegStream, (getShort(jpegStream) - 2));
/*     */         }
/*     */       } 
/*     */     } 
/* 354 */     if (icc != null) {
/* 355 */       int total = 0;
/* 356 */       for (int k = 0; k < icc.length; k++) {
/* 357 */         if (icc[k] == null) {
/* 358 */           icc = (byte[][])null;
/*     */           return;
/*     */         } 
/* 361 */         total += (icc[k]).length - 14;
/*     */       } 
/* 363 */       byte[] ficc = new byte[total];
/* 364 */       total = 0;
/* 365 */       for (int i = 0; i < icc.length; i++) {
/* 366 */         System.arraycopy(icc[i], 14, ficc, total, (icc[i]).length - 14);
/* 367 */         total += (icc[i]).length - 14;
/*     */       } 
/*     */       try {
/* 370 */         image.setProfile(IccProfile.getInstance(ficc, image.getColorSpace()));
/* 371 */       } catch (IllegalArgumentException illegalArgumentException) {}
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
/*     */   private static int getShort(InputStream jpegStream) throws IOException {
/* 385 */     return (jpegStream.read() << 8) + jpegStream.read();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static int marker(int marker) {
/*     */     int i;
/* 395 */     for (i = 0; i < VALID_MARKERS.length; i++) {
/* 396 */       if (marker == VALID_MARKERS[i]) {
/* 397 */         return 0;
/*     */       }
/*     */     } 
/* 400 */     for (i = 0; i < NOPARAM_MARKERS.length; i++) {
/* 401 */       if (marker == NOPARAM_MARKERS[i]) {
/* 402 */         return 2;
/*     */       }
/*     */     } 
/* 405 */     for (i = 0; i < UNSUPPORTED_MARKERS.length; i++) {
/* 406 */       if (marker == UNSUPPORTED_MARKERS[i]) {
/* 407 */         return 1;
/*     */       }
/*     */     } 
/* 410 */     return -1;
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/io/image/JpegImageHelper.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */