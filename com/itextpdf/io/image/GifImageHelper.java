/*     */ package com.itextpdf.io.image;
/*     */ 
/*     */ import com.itextpdf.io.IOException;
/*     */ import com.itextpdf.io.font.PdfEncodings;
/*     */ import com.itextpdf.io.util.StreamUtil;
/*     */ import java.io.ByteArrayInputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.net.URL;
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
/*     */ public final class GifImageHelper
/*     */ {
/*     */   static final int MAX_STACK_SIZE = 4096;
/*     */   
/*     */   private static class GifParameters
/*     */   {
/*     */     InputStream input;
/*     */     boolean gctFlag;
/*     */     int bgIndex;
/*     */     int bgColor;
/*     */     int pixelAspect;
/*     */     boolean lctFlag;
/*     */     boolean interlace;
/*     */     int lctSize;
/*     */     int ix;
/*     */     int iy;
/*     */     int iw;
/*     */     int ih;
/*     */     byte[] block;
/*     */     int blockSize;
/*     */     int dispose;
/*     */     boolean transparency;
/*     */     int delay;
/*     */     int transIndex;
/*     */     short[] prefix;
/*     */     byte[] suffix;
/*     */     byte[] pixelStack;
/*     */     byte[] pixels;
/*     */     byte[] m_out;
/*     */     int m_bpc;
/*     */     int m_gbpc;
/*     */     byte[] m_global_table;
/*     */     byte[] m_local_table;
/*     */     byte[] m_curr_table;
/*     */     int m_line_stride;
/*     */     byte[] fromData;
/*     */     URL fromUrl;
/*     */     int currentFrame;
/*     */     GifImageData image;
/*     */     
/*     */     public GifParameters(GifImageData image) {
/*  89 */       this.block = new byte[256];
/*     */       
/*  91 */       this.blockSize = 0;
/*     */ 
/*     */ 
/*     */       
/*  95 */       this.dispose = 0;
/*     */       
/*  97 */       this.transparency = false;
/*     */       
/*  99 */       this.delay = 0;
/*     */       this.image = image;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void processImage(GifImageData image) {
/* 128 */     processImage(image, -1);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void processImage(GifImageData image, int lastFrameNumber) {
/* 137 */     GifParameters gif = new GifParameters(image);
/*     */     
/*     */     try {
/* 140 */       if (image.getData() == null) {
/* 141 */         image.loadData();
/*     */       }
/* 143 */       InputStream gifStream = new ByteArrayInputStream(image.getData());
/* 144 */       process(gifStream, gif, lastFrameNumber);
/* 145 */     } catch (IOException e) {
/* 146 */       throw new IOException("GIF image exception.", e);
/*     */     } 
/*     */   }
/*     */   
/*     */   private static void process(InputStream stream, GifParameters gif, int lastFrameNumber) throws IOException {
/* 151 */     gif.input = stream;
/* 152 */     readHeader(gif);
/* 153 */     readContents(gif, lastFrameNumber);
/* 154 */     if (gif.currentFrame <= lastFrameNumber) {
/* 155 */       throw (new IOException("Cannot find frame number {0} (zero-based)")).setMessageParams(new Object[] { Integer.valueOf(lastFrameNumber) });
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void readHeader(GifParameters gif) throws IOException {
/* 163 */     StringBuilder id = new StringBuilder("");
/* 164 */     for (int i = 0; i < 6; i++)
/* 165 */       id.append((char)gif.input.read()); 
/* 166 */     if (!id.toString().startsWith("GIF8")) {
/* 167 */       throw new IOException("GIF signature not found.");
/*     */     }
/*     */     
/* 170 */     readLSD(gif);
/* 171 */     if (gif.gctFlag) {
/* 172 */       gif.m_global_table = readColorTable(gif.m_gbpc, gif);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void readLSD(GifParameters gif) throws IOException {
/* 182 */     gif.image.setLogicalWidth(readShort(gif));
/* 183 */     gif.image.setLogicalHeight(readShort(gif));
/*     */ 
/*     */     
/* 186 */     int packed = gif.input.read();
/*     */     
/* 188 */     gif.gctFlag = ((packed & 0x80) != 0);
/* 189 */     gif.m_gbpc = (packed & 0x7) + 1;
/*     */     
/* 191 */     gif.bgIndex = gif.input.read();
/*     */     
/* 193 */     gif.pixelAspect = gif.input.read();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static int readShort(GifParameters gif) throws IOException {
/* 201 */     return gif.input.read() | gif.input.read() << 8;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static int readBlock(GifParameters gif) throws IOException {
/* 210 */     gif.blockSize = gif.input.read();
/* 211 */     if (gif.blockSize <= 0) {
/* 212 */       return gif.blockSize = 0;
/*     */     }
/* 214 */     gif.blockSize = gif.input.read(gif.block, 0, gif.blockSize);
/*     */     
/* 216 */     return gif.blockSize;
/*     */   }
/*     */   
/*     */   private static byte[] readColorTable(int bpc, GifParameters gif) throws IOException {
/* 220 */     int ncolors = 1 << bpc;
/* 221 */     int nbytes = 3 * ncolors;
/* 222 */     bpc = newBpc(bpc);
/* 223 */     byte[] table = new byte[(1 << bpc) * 3];
/* 224 */     StreamUtil.readFully(gif.input, table, 0, nbytes);
/* 225 */     return table;
/*     */   }
/*     */ 
/*     */   
/*     */   private static int newBpc(int bpc) {
/* 230 */     switch (bpc) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       case 1:
/*     */       case 2:
/*     */       case 4:
/* 240 */         return bpc;
/*     */       case 3:
/*     */         return 4;
/*     */     } 
/*     */     return 8; } private static void readContents(GifParameters gif, int lastFrameNumber) throws IOException {
/* 245 */     boolean done = false;
/* 246 */     gif.currentFrame = 0;
/* 247 */     while (!done) {
/* 248 */       int code = gif.input.read();
/* 249 */       switch (code) {
/*     */         
/*     */         case 44:
/* 252 */           readFrame(gif);
/* 253 */           if (gif.currentFrame == lastFrameNumber) {
/* 254 */             done = true;
/*     */           }
/* 256 */           gif.currentFrame++;
/*     */           continue;
/*     */         
/*     */         case 33:
/* 260 */           code = gif.input.read();
/* 261 */           switch (code) {
/*     */             
/*     */             case 249:
/* 264 */               readGraphicControlExt(gif);
/*     */               continue;
/*     */             
/*     */             case 255:
/* 268 */               readBlock(gif);
/*     */               
/* 270 */               skip(gif);
/*     */               continue;
/*     */           } 
/*     */           
/* 274 */           skip(gif);
/*     */           continue;
/*     */       } 
/*     */       
/* 278 */       done = true;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void readFrame(GifParameters gif) throws IOException {
/* 289 */     gif.ix = readShort(gif);
/* 290 */     gif.iy = readShort(gif);
/* 291 */     gif.iw = readShort(gif);
/* 292 */     gif.ih = readShort(gif);
/*     */     
/* 294 */     int packed = gif.input.read();
/*     */     
/* 296 */     gif.lctFlag = ((packed & 0x80) != 0);
/*     */     
/* 298 */     gif.interlace = ((packed & 0x40) != 0);
/*     */ 
/*     */ 
/*     */     
/* 302 */     gif.lctSize = 2 << (packed & 0x7);
/* 303 */     gif.m_bpc = newBpc(gif.m_gbpc);
/* 304 */     if (gif.lctFlag) {
/*     */       
/* 306 */       gif.m_curr_table = readColorTable((packed & 0x7) + 1, gif);
/* 307 */       gif.m_bpc = newBpc((packed & 0x7) + 1);
/*     */     } else {
/*     */       
/* 310 */       gif.m_curr_table = gif.m_global_table;
/*     */     } 
/* 312 */     if (gif.transparency && gif.transIndex >= gif.m_curr_table.length / 3) {
/* 313 */       gif.transparency = false;
/*     */     }
/* 315 */     if (gif.transparency && gif.m_bpc == 1) {
/* 316 */       byte[] tp = new byte[12];
/* 317 */       System.arraycopy(gif.m_curr_table, 0, tp, 0, 6);
/* 318 */       gif.m_curr_table = tp;
/* 319 */       gif.m_bpc = 2;
/*     */     } 
/*     */     
/* 322 */     boolean skipZero = decodeImageData(gif);
/* 323 */     if (!skipZero) {
/* 324 */       skip(gif);
/*     */     }
/*     */     try {
/* 327 */       Object[] colorspace = new Object[4];
/* 328 */       colorspace[0] = "/Indexed";
/* 329 */       colorspace[1] = "/DeviceRGB";
/* 330 */       int len = gif.m_curr_table.length;
/* 331 */       colorspace[2] = Integer.valueOf(len / 3 - 1);
/* 332 */       colorspace[3] = PdfEncodings.convertToString(gif.m_curr_table, null);
/* 333 */       Map<String, Object> ad = new HashMap<>();
/* 334 */       ad.put("ColorSpace", colorspace);
/* 335 */       RawImageData img = new RawImageData(gif.m_out, ImageType.GIF);
/* 336 */       RawImageHelper.updateRawImageParameters(img, gif.iw, gif.ih, 1, gif.m_bpc, gif.m_out);
/* 337 */       RawImageHelper.updateImageAttributes(img, ad);
/* 338 */       gif.image.addFrame(img);
/* 339 */       if (gif.transparency) {
/* 340 */         img.setTransparency(new int[] { gif.transIndex, gif.transIndex });
/*     */       }
/* 342 */     } catch (Exception e) {
/* 343 */       throw new IOException("GIF image exception.", e);
/*     */     } 
/*     */   }
/*     */   
/*     */   private static boolean decodeImageData(GifParameters gif) throws IOException {
/* 348 */     int NullCode = -1;
/* 349 */     int npix = gif.iw * gif.ih;
/*     */ 
/*     */     
/* 352 */     boolean skipZero = false;
/*     */     
/* 354 */     if (gif.prefix == null)
/* 355 */       gif.prefix = new short[4096]; 
/* 356 */     if (gif.suffix == null)
/* 357 */       gif.suffix = new byte[4096]; 
/* 358 */     if (gif.pixelStack == null) {
/* 359 */       gif.pixelStack = new byte[4097];
/*     */     }
/* 361 */     gif.m_line_stride = (gif.iw * gif.m_bpc + 7) / 8;
/* 362 */     gif.m_out = new byte[gif.m_line_stride * gif.ih];
/* 363 */     int pass = 1;
/* 364 */     int inc = gif.interlace ? 8 : 1;
/* 365 */     int line = 0;
/* 366 */     int xpos = 0;
/*     */ 
/*     */ 
/*     */     
/* 370 */     int data_size = gif.input.read();
/* 371 */     int clear = 1 << data_size;
/* 372 */     int end_of_information = clear + 1;
/* 373 */     int available = clear + 2;
/* 374 */     int old_code = NullCode;
/* 375 */     int code_size = data_size + 1;
/* 376 */     int code_mask = (1 << code_size) - 1; int code;
/* 377 */     for (code = 0; code < clear; code++) {
/* 378 */       gif.prefix[code] = 0;
/* 379 */       gif.suffix[code] = (byte)code;
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 384 */     int bi = 0, top = bi, first = top, count = first, bits = count, datum = bits;
/*     */     int i;
/* 386 */     label75: for (i = 0; i < npix; ) {
/* 387 */       if (top == 0) {
/* 388 */         if (bits < code_size) {
/*     */           
/* 390 */           if (count == 0) {
/*     */             
/* 392 */             count = readBlock(gif);
/* 393 */             if (count <= 0) {
/* 394 */               skipZero = true;
/*     */               break;
/*     */             } 
/* 397 */             bi = 0;
/*     */           } 
/* 399 */           datum += (gif.block[bi] & 0xFF) << bits;
/* 400 */           bits += 8;
/* 401 */           bi++;
/* 402 */           count--;
/*     */ 
/*     */           
/*     */           continue;
/*     */         } 
/*     */         
/* 408 */         code = datum & code_mask;
/* 409 */         datum >>= code_size;
/* 410 */         bits -= code_size;
/*     */ 
/*     */ 
/*     */         
/* 414 */         if (code > available || code == end_of_information)
/*     */           break; 
/* 416 */         if (code == clear) {
/*     */           
/* 418 */           code_size = data_size + 1;
/* 419 */           code_mask = (1 << code_size) - 1;
/* 420 */           available = clear + 2;
/* 421 */           old_code = NullCode;
/*     */           continue;
/*     */         } 
/* 424 */         if (old_code == NullCode) {
/* 425 */           gif.pixelStack[top++] = gif.suffix[code];
/* 426 */           old_code = code;
/* 427 */           first = code;
/*     */           continue;
/*     */         } 
/* 430 */         int in_code = code;
/* 431 */         if (code == available) {
/* 432 */           gif.pixelStack[top++] = (byte)first;
/* 433 */           code = old_code;
/*     */         } 
/* 435 */         while (code > clear) {
/* 436 */           gif.pixelStack[top++] = gif.suffix[code];
/* 437 */           code = gif.prefix[code];
/*     */         } 
/* 439 */         first = gif.suffix[code] & 0xFF;
/*     */ 
/*     */ 
/*     */         
/* 443 */         if (available >= 4096)
/*     */           break; 
/* 445 */         gif.pixelStack[top++] = (byte)first;
/* 446 */         gif.prefix[available] = (short)old_code;
/* 447 */         gif.suffix[available] = (byte)first;
/* 448 */         available++;
/* 449 */         if ((available & code_mask) == 0 && available < 4096) {
/* 450 */           code_size++;
/* 451 */           code_mask += available;
/*     */         } 
/* 453 */         old_code = in_code;
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 458 */       top--;
/* 459 */       i++;
/*     */       
/* 461 */       setPixel(xpos, line, gif.pixelStack[top], gif);
/* 462 */       xpos++;
/* 463 */       if (xpos >= gif.iw) {
/* 464 */         xpos = 0;
/* 465 */         line += inc;
/* 466 */         if (line >= gif.ih) {
/* 467 */           if (gif.interlace)
/*     */             while (true) {
/* 469 */               pass++;
/* 470 */               switch (pass) {
/*     */                 case 2:
/* 472 */                   line = 4;
/*     */                   break;
/*     */                 case 3:
/* 475 */                   line = 2;
/* 476 */                   inc = 4;
/*     */                   break;
/*     */                 case 4:
/* 479 */                   line = 1;
/* 480 */                   inc = 2;
/*     */                   break;
/*     */                 
/*     */                 default:
/* 484 */                   line = gif.ih - 1;
/* 485 */                   inc = 0; break;
/*     */               } 
/* 487 */               if (line < gif.ih) {
/*     */                 continue label75;
/*     */               }
/*     */             }  
/* 491 */           line = gif.ih - 1;
/* 492 */           inc = 0;
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 497 */     return skipZero;
/*     */   }
/*     */ 
/*     */   
/*     */   private static void setPixel(int x, int y, int v, GifParameters gif) {
/* 502 */     if (gif.m_bpc == 8) {
/* 503 */       int pos = x + gif.iw * y;
/* 504 */       gif.m_out[pos] = (byte)v;
/*     */     } else {
/*     */       
/* 507 */       int pos = gif.m_line_stride * y + x / 8 / gif.m_bpc;
/* 508 */       int vout = v << 8 - gif.m_bpc * x % 8 / gif.m_bpc - gif.m_bpc;
/* 509 */       gif.m_out[pos] = (byte)(gif.m_out[pos] | (byte)vout);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void readGraphicControlExt(GifParameters gif) throws IOException {
/* 518 */     gif.input.read();
/*     */     
/* 520 */     int packed = gif.input.read();
/*     */     
/* 522 */     gif.dispose = (packed & 0x1C) >> 2;
/* 523 */     if (gif.dispose == 0)
/*     */     {
/* 525 */       gif.dispose = 1;
/*     */     }
/* 527 */     gif.transparency = ((packed & 0x1) != 0);
/*     */     
/* 529 */     gif.delay = readShort(gif) * 10;
/*     */     
/* 531 */     gif.transIndex = gif.input.read();
/*     */     
/* 533 */     gif.input.read();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void skip(GifParameters gif) throws IOException {
/*     */     do {
/* 542 */       readBlock(gif);
/* 543 */     } while (gif.blockSize > 0);
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/io/image/GifImageHelper.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */