/*     */ package com.itextpdf.kernel.pdf.canvas.wmf;
/*     */ 
/*     */ import com.itextpdf.io.font.FontProgram;
/*     */ import com.itextpdf.io.image.ImageData;
/*     */ import com.itextpdf.io.image.ImageDataFactory;
/*     */ import com.itextpdf.io.image.ImageType;
/*     */ import com.itextpdf.kernel.PdfException;
/*     */ import com.itextpdf.kernel.colors.Color;
/*     */ import com.itextpdf.kernel.font.PdfFontFactory;
/*     */ import com.itextpdf.kernel.geom.Point;
/*     */ import com.itextpdf.kernel.geom.Rectangle;
/*     */ import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
/*     */ import com.itextpdf.kernel.pdf.xobject.PdfImageXObject;
/*     */ import com.itextpdf.kernel.pdf.xobject.PdfXObject;
/*     */ import java.io.ByteArrayOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ import java.io.UnsupportedEncodingException;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class MetaDo
/*     */ {
/*     */   public static final int META_SETBKCOLOR = 513;
/*     */   public static final int META_SETBKMODE = 258;
/*     */   public static final int META_SETMAPMODE = 259;
/*     */   public static final int META_SETROP2 = 260;
/*     */   public static final int META_SETRELABS = 261;
/*     */   public static final int META_SETPOLYFILLMODE = 262;
/*     */   public static final int META_SETSTRETCHBLTMODE = 263;
/*     */   public static final int META_SETTEXTCHAREXTRA = 264;
/*     */   public static final int META_SETTEXTCOLOR = 521;
/*     */   public static final int META_SETTEXTJUSTIFICATION = 522;
/*     */   public static final int META_SETWINDOWORG = 523;
/*     */   public static final int META_SETWINDOWEXT = 524;
/*     */   public static final int META_SETVIEWPORTORG = 525;
/*     */   public static final int META_SETVIEWPORTEXT = 526;
/*     */   public static final int META_OFFSETWINDOWORG = 527;
/*     */   public static final int META_SCALEWINDOWEXT = 1040;
/*     */   public static final int META_OFFSETVIEWPORTORG = 529;
/*     */   public static final int META_SCALEVIEWPORTEXT = 1042;
/*     */   public static final int META_LINETO = 531;
/*     */   public static final int META_MOVETO = 532;
/*     */   public static final int META_EXCLUDECLIPRECT = 1045;
/*     */   public static final int META_INTERSECTCLIPRECT = 1046;
/*     */   public static final int META_ARC = 2071;
/*     */   public static final int META_ELLIPSE = 1048;
/*     */   public static final int META_FLOODFILL = 1049;
/*     */   public static final int META_PIE = 2074;
/*     */   public static final int META_RECTANGLE = 1051;
/*     */   public static final int META_ROUNDRECT = 1564;
/*     */   public static final int META_PATBLT = 1565;
/*     */   public static final int META_SAVEDC = 30;
/*     */   public static final int META_SETPIXEL = 1055;
/*     */   public static final int META_OFFSETCLIPRGN = 544;
/*     */   public static final int META_TEXTOUT = 1313;
/*     */   public static final int META_BITBLT = 2338;
/*     */   public static final int META_STRETCHBLT = 2851;
/*     */   public static final int META_POLYGON = 804;
/*     */   public static final int META_POLYLINE = 805;
/*     */   public static final int META_ESCAPE = 1574;
/*     */   public static final int META_RESTOREDC = 295;
/*     */   public static final int META_FILLREGION = 552;
/*     */   public static final int META_FRAMEREGION = 1065;
/*     */   public static final int META_INVERTREGION = 298;
/*     */   public static final int META_PAINTREGION = 299;
/*     */   public static final int META_SELECTCLIPREGION = 300;
/*     */   public static final int META_SELECTOBJECT = 301;
/*     */   public static final int META_SETTEXTALIGN = 302;
/*     */   public static final int META_CHORD = 2096;
/*     */   public static final int META_SETMAPPERFLAGS = 561;
/*     */   public static final int META_EXTTEXTOUT = 2610;
/*     */   public static final int META_SETDIBTODEV = 3379;
/*     */   public static final int META_SELECTPALETTE = 564;
/*     */   public static final int META_REALIZEPALETTE = 53;
/*     */   public static final int META_ANIMATEPALETTE = 1078;
/*     */   public static final int META_SETPALENTRIES = 55;
/*     */   public static final int META_POLYPOLYGON = 1336;
/*     */   public static final int META_RESIZEPALETTE = 313;
/*     */   public static final int META_DIBBITBLT = 2368;
/*     */   public static final int META_DIBSTRETCHBLT = 2881;
/*     */   public static final int META_DIBCREATEPATTERNBRUSH = 322;
/*     */   public static final int META_STRETCHDIB = 3907;
/*     */   public static final int META_EXTFLOODFILL = 1352;
/*     */   public static final int META_DELETEOBJECT = 496;
/*     */   public static final int META_CREATEPALETTE = 247;
/*     */   public static final int META_CREATEPATTERNBRUSH = 505;
/*     */   public static final int META_CREATEPENINDIRECT = 762;
/*     */   public static final int META_CREATEFONTINDIRECT = 763;
/*     */   public static final int META_CREATEBRUSHINDIRECT = 764;
/*     */   public static final int META_CREATEREGION = 1791;
/*     */   public PdfCanvas cb;
/*     */   public InputMeta in;
/*     */   int left;
/*     */   int top;
/*     */   int right;
/*     */   int bottom;
/*     */   int inch;
/* 156 */   MetaState state = new MetaState();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public MetaDo(InputStream in, PdfCanvas cb) {
/* 165 */     this.cb = cb;
/* 166 */     this.in = new InputMeta(in);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void readAll() throws IOException {
/* 175 */     if (this.in.readInt() != -1698247209) {
/* 176 */       throw new PdfException("Not a placeable windows metafile.");
/*     */     }
/* 178 */     this.in.readWord();
/* 179 */     this.left = this.in.readShort();
/* 180 */     this.top = this.in.readShort();
/* 181 */     this.right = this.in.readShort();
/* 182 */     this.bottom = this.in.readShort();
/* 183 */     this.inch = this.in.readWord();
/* 184 */     this.state.setScalingX((this.right - this.left) / this.inch * 72.0F);
/* 185 */     this.state.setScalingY((this.bottom - this.top) / this.inch * 72.0F);
/* 186 */     this.state.setOffsetWx(this.left);
/* 187 */     this.state.setOffsetWy(this.top);
/* 188 */     this.state.setExtentWx(this.right - this.left);
/* 189 */     this.state.setExtentWy(this.bottom - this.top);
/* 190 */     this.in.readInt();
/* 191 */     this.in.readWord();
/* 192 */     this.in.skip(18);
/*     */ 
/*     */ 
/*     */     
/* 196 */     this.cb.setLineCapStyle(1);
/* 197 */     this.cb.setLineJoinStyle(1); while (true) {
/*     */       MetaPen pen; MetaBrush brush; MetaFont font; int idx, m, len, numPoly, i; float yend, f1, h, b; int y, count; Color color; int rop; Point p; int i2, sx, lens[], i1; float xend, f2, w, r; int x; byte[] text; int n, srcHeight; Point point1; int i7, sy, i6, j, i5; float ystart, f4, f3, t; int i4, k, i3, srcWidth, i9, i8; float xstart, f6, f5, l; int flag; String s; int ySrc; float f8, f7; int x1, i10, xSrc; float f10, f9; int y1, i11; float destHeight, f11; int x2; float destWidth, f12; int y2; float yDest, cx; byte[] arrayOfByte1; float xDest, cy; int i12; byte[] arrayOfByte2; float arc1; String str1; int i13; float arc2; List<double[]> ar; double[] pt;
/* 199 */       int i14, lenMarker = this.in.getLength();
/* 200 */       int tsize = this.in.readInt();
/* 201 */       if (tsize < 3)
/*     */         break; 
/* 203 */       int function = this.in.readWord();
/* 204 */       switch (function) {
/*     */ 
/*     */         
/*     */         case 247:
/*     */         case 322:
/*     */         case 1791:
/* 210 */           this.state.addMetaObject(new MetaObject());
/*     */           break;
/*     */         
/*     */         case 762:
/* 214 */           pen = new MetaPen();
/* 215 */           pen.init(this.in);
/* 216 */           this.state.addMetaObject(pen);
/*     */           break;
/*     */ 
/*     */         
/*     */         case 764:
/* 221 */           brush = new MetaBrush();
/* 222 */           brush.init(this.in);
/* 223 */           this.state.addMetaObject(brush);
/*     */           break;
/*     */ 
/*     */         
/*     */         case 763:
/* 228 */           font = new MetaFont();
/* 229 */           font.init(this.in);
/* 230 */           this.state.addMetaObject(font);
/*     */           break;
/*     */ 
/*     */         
/*     */         case 301:
/* 235 */           idx = this.in.readWord();
/* 236 */           this.state.selectMetaObject(idx, this.cb);
/*     */           break;
/*     */ 
/*     */         
/*     */         case 496:
/* 241 */           idx = this.in.readWord();
/* 242 */           this.state.deleteMetaObject(idx);
/*     */           break;
/*     */         
/*     */         case 30:
/* 246 */           this.state.saveState(this.cb);
/*     */           break;
/*     */         
/*     */         case 295:
/* 250 */           idx = this.in.readShort();
/* 251 */           this.state.restoreState(idx, this.cb);
/*     */           break;
/*     */         
/*     */         case 523:
/* 255 */           this.state.setOffsetWy(this.in.readShort());
/* 256 */           this.state.setOffsetWx(this.in.readShort());
/*     */           break;
/*     */         case 524:
/* 259 */           this.state.setExtentWy(this.in.readShort());
/* 260 */           this.state.setExtentWx(this.in.readShort());
/*     */           break;
/*     */         
/*     */         case 532:
/* 264 */           m = this.in.readShort();
/* 265 */           p = new Point(this.in.readShort(), m);
/* 266 */           this.state.setCurrentPoint(p);
/*     */           break;
/*     */ 
/*     */         
/*     */         case 531:
/* 271 */           m = this.in.readShort();
/* 272 */           i2 = this.in.readShort();
/* 273 */           point1 = this.state.getCurrentPoint();
/* 274 */           this.cb.moveTo(this.state.transformX((int)point1.getX()), this.state.transformY((int)point1.getY()));
/* 275 */           this.cb.lineTo(this.state.transformX(i2), this.state.transformY(m));
/* 276 */           this.cb.stroke();
/* 277 */           this.state.setCurrentPoint(new Point(i2, m));
/*     */           break;
/*     */ 
/*     */         
/*     */         case 805:
/* 282 */           this.state.setLineJoinPolygon(this.cb);
/* 283 */           len = this.in.readWord();
/* 284 */           i2 = this.in.readShort();
/* 285 */           i7 = this.in.readShort();
/* 286 */           this.cb.moveTo(this.state.transformX(i2), this.state.transformY(i7));
/* 287 */           for (i9 = 1; i9 < len; i9++) {
/* 288 */             i2 = this.in.readShort();
/* 289 */             i7 = this.in.readShort();
/* 290 */             this.cb.lineTo(this.state.transformX(i2), this.state.transformY(i7));
/*     */           } 
/* 292 */           this.cb.stroke();
/*     */           break;
/*     */ 
/*     */         
/*     */         case 804:
/* 297 */           if (isNullStrokeFill(false))
/*     */             break; 
/* 299 */           len = this.in.readWord();
/* 300 */           sx = this.in.readShort();
/* 301 */           sy = this.in.readShort();
/* 302 */           this.cb.moveTo(this.state.transformX(sx), this.state.transformY(sy));
/* 303 */           for (i9 = 1; i9 < len; i9++) {
/* 304 */             int i15 = this.in.readShort();
/* 305 */             int i16 = this.in.readShort();
/* 306 */             this.cb.lineTo(this.state.transformX(i15), this.state.transformY(i16));
/*     */           } 
/* 308 */           this.cb.lineTo(this.state.transformX(sx), this.state.transformY(sy));
/* 309 */           strokeAndFill();
/*     */           break;
/*     */ 
/*     */         
/*     */         case 1336:
/* 314 */           if (isNullStrokeFill(false))
/*     */             break; 
/* 316 */           numPoly = this.in.readWord();
/* 317 */           lens = new int[numPoly];
/* 318 */           for (i6 = 0; i6 < lens.length; i6++)
/* 319 */             lens[i6] = this.in.readWord(); 
/* 320 */           for (j = 0; j < lens.length; j++) {
/* 321 */             int i15 = lens[j];
/* 322 */             int i16 = this.in.readShort();
/* 323 */             int i17 = this.in.readShort();
/* 324 */             this.cb.moveTo(this.state.transformX(i16), this.state.transformY(i17));
/* 325 */             for (int i18 = 1; i18 < i15; i18++) {
/* 326 */               int i19 = this.in.readShort();
/* 327 */               int i20 = this.in.readShort();
/* 328 */               this.cb.lineTo(this.state.transformX(i19), this.state.transformY(i20));
/*     */             } 
/* 330 */             this.cb.lineTo(this.state.transformX(i16), this.state.transformY(i17));
/*     */           } 
/* 332 */           strokeAndFill();
/*     */           break;
/*     */ 
/*     */         
/*     */         case 1048:
/* 337 */           if (isNullStrokeFill(this.state.getLineNeutral()))
/*     */             break; 
/* 339 */           i = this.in.readShort();
/* 340 */           i1 = this.in.readShort();
/* 341 */           i5 = this.in.readShort();
/* 342 */           i8 = this.in.readShort();
/* 343 */           this.cb.arc(this.state.transformX(i8), this.state.transformY(i), this.state.transformX(i1), this.state.transformY(i5), 0.0D, 360.0D);
/* 344 */           strokeAndFill();
/*     */           break;
/*     */ 
/*     */         
/*     */         case 2071:
/* 349 */           if (isNullStrokeFill(this.state.getLineNeutral()))
/*     */             break; 
/* 351 */           yend = this.state.transformY(this.in.readShort());
/* 352 */           xend = this.state.transformX(this.in.readShort());
/* 353 */           ystart = this.state.transformY(this.in.readShort());
/* 354 */           xstart = this.state.transformX(this.in.readShort());
/* 355 */           f8 = this.state.transformY(this.in.readShort());
/* 356 */           f10 = this.state.transformX(this.in.readShort());
/* 357 */           f11 = this.state.transformY(this.in.readShort());
/* 358 */           f12 = this.state.transformX(this.in.readShort());
/* 359 */           cx = (f10 + f12) / 2.0F;
/* 360 */           cy = (f11 + f8) / 2.0F;
/* 361 */           arc1 = getArc(cx, cy, xstart, ystart);
/* 362 */           arc2 = getArc(cx, cy, xend, yend);
/* 363 */           arc2 -= arc1;
/* 364 */           if (arc2 <= 0.0F)
/* 365 */             arc2 += 360.0F; 
/* 366 */           this.cb.arc(f12, f8, f10, f11, arc1, arc2);
/* 367 */           this.cb.stroke();
/*     */           break;
/*     */ 
/*     */         
/*     */         case 2074:
/* 372 */           if (isNullStrokeFill(this.state.getLineNeutral()))
/*     */             break; 
/* 374 */           yend = this.state.transformY(this.in.readShort());
/* 375 */           xend = this.state.transformX(this.in.readShort());
/* 376 */           ystart = this.state.transformY(this.in.readShort());
/* 377 */           xstart = this.state.transformX(this.in.readShort());
/* 378 */           f8 = this.state.transformY(this.in.readShort());
/* 379 */           f10 = this.state.transformX(this.in.readShort());
/* 380 */           f11 = this.state.transformY(this.in.readShort());
/* 381 */           f12 = this.state.transformX(this.in.readShort());
/* 382 */           cx = (f10 + f12) / 2.0F;
/* 383 */           cy = (f11 + f8) / 2.0F;
/* 384 */           arc1 = getArc(cx, cy, xstart, ystart);
/* 385 */           arc2 = getArc(cx, cy, xend, yend);
/* 386 */           arc2 -= arc1;
/* 387 */           if (arc2 <= 0.0F)
/* 388 */             arc2 += 360.0F; 
/* 389 */           ar = PdfCanvas.bezierArc(f12, f8, f10, f11, arc1, arc2);
/* 390 */           if (ar.size() == 0)
/*     */             break; 
/* 392 */           pt = ar.get(0);
/* 393 */           this.cb.moveTo(cx, cy);
/* 394 */           this.cb.lineTo(pt[0], pt[1]);
/* 395 */           for (i14 = 0; i14 < ar.size(); i14++) {
/* 396 */             pt = ar.get(i14);
/* 397 */             this.cb.curveTo(pt[2], pt[3], pt[4], pt[5], pt[6], pt[7]);
/*     */           } 
/* 399 */           this.cb.lineTo(cx, cy);
/* 400 */           strokeAndFill();
/*     */           break;
/*     */ 
/*     */         
/*     */         case 2096:
/* 405 */           if (isNullStrokeFill(this.state.getLineNeutral()))
/*     */             break; 
/* 407 */           yend = this.state.transformY(this.in.readShort());
/* 408 */           xend = this.state.transformX(this.in.readShort());
/* 409 */           ystart = this.state.transformY(this.in.readShort());
/* 410 */           xstart = this.state.transformX(this.in.readShort());
/* 411 */           f8 = this.state.transformY(this.in.readShort());
/* 412 */           f10 = this.state.transformX(this.in.readShort());
/* 413 */           f11 = this.state.transformY(this.in.readShort());
/* 414 */           f12 = this.state.transformX(this.in.readShort());
/* 415 */           cx = (f10 + f12) / 2.0F;
/* 416 */           cy = (f11 + f8) / 2.0F;
/* 417 */           arc1 = getArc(cx, cy, xstart, ystart);
/* 418 */           arc2 = getArc(cx, cy, xend, yend);
/* 419 */           arc2 -= arc1;
/* 420 */           if (arc2 <= 0.0F)
/* 421 */             arc2 += 360.0F; 
/* 422 */           ar = PdfCanvas.bezierArc(f12, f8, f10, f11, arc1, arc2);
/* 423 */           if (ar.size() == 0)
/*     */             break; 
/* 425 */           pt = ar.get(0);
/* 426 */           cx = (float)pt[0];
/* 427 */           cy = (float)pt[1];
/* 428 */           this.cb.moveTo(cx, cy);
/* 429 */           for (i14 = 0; i14 < ar.size(); i14++) {
/* 430 */             pt = ar.get(i14);
/* 431 */             this.cb.curveTo(pt[2], pt[3], pt[4], pt[5], pt[6], pt[7]);
/*     */           } 
/* 433 */           this.cb.lineTo(cx, cy);
/* 434 */           strokeAndFill();
/*     */           break;
/*     */ 
/*     */         
/*     */         case 1051:
/* 439 */           if (isNullStrokeFill(true))
/*     */             break; 
/* 441 */           f1 = this.state.transformY(this.in.readShort());
/* 442 */           f2 = this.state.transformX(this.in.readShort());
/* 443 */           f4 = this.state.transformY(this.in.readShort());
/* 444 */           f6 = this.state.transformX(this.in.readShort());
/* 445 */           this.cb.rectangle(f6, f1, (f2 - f6), (f4 - f1));
/* 446 */           strokeAndFill();
/*     */           break;
/*     */ 
/*     */         
/*     */         case 1564:
/* 451 */           if (isNullStrokeFill(true))
/*     */             break; 
/* 453 */           h = this.state.transformY(0) - this.state.transformY(this.in.readShort());
/* 454 */           w = this.state.transformX(this.in.readShort()) - this.state.transformX(0);
/* 455 */           f3 = this.state.transformY(this.in.readShort());
/* 456 */           f5 = this.state.transformX(this.in.readShort());
/* 457 */           f7 = this.state.transformY(this.in.readShort());
/* 458 */           f9 = this.state.transformX(this.in.readShort());
/* 459 */           this.cb.roundRectangle(f9, f3, (f5 - f9), (f7 - f3), ((h + w) / 4.0F));
/* 460 */           strokeAndFill();
/*     */           break;
/*     */ 
/*     */         
/*     */         case 1046:
/* 465 */           b = this.state.transformY(this.in.readShort());
/* 466 */           r = this.state.transformX(this.in.readShort());
/* 467 */           t = this.state.transformY(this.in.readShort());
/* 468 */           l = this.state.transformX(this.in.readShort());
/* 469 */           this.cb.rectangle(l, b, (r - l), (t - b));
/* 470 */           this.cb.eoClip();
/* 471 */           this.cb.endPath();
/*     */           break;
/*     */ 
/*     */         
/*     */         case 2610:
/* 476 */           y = this.in.readShort();
/* 477 */           x = this.in.readShort();
/* 478 */           i4 = this.in.readWord();
/* 479 */           flag = this.in.readWord();
/* 480 */           x1 = 0;
/* 481 */           y1 = 0;
/* 482 */           x2 = 0;
/* 483 */           y2 = 0;
/* 484 */           if ((flag & 0x6) != 0) {
/* 485 */             x1 = this.in.readShort();
/* 486 */             y1 = this.in.readShort();
/* 487 */             x2 = this.in.readShort();
/* 488 */             y2 = this.in.readShort();
/*     */           } 
/* 490 */           arrayOfByte1 = new byte[i4];
/*     */           
/* 492 */           for (i12 = 0; i12 < i4; i12++) {
/* 493 */             byte c = (byte)this.in.readByte();
/* 494 */             if (c == 0)
/*     */               break; 
/* 496 */             arrayOfByte1[i12] = c;
/*     */           } 
/*     */           
/*     */           try {
/* 500 */             str1 = new String(arrayOfByte1, 0, i12, "Cp1252");
/*     */           }
/* 502 */           catch (UnsupportedEncodingException e) {
/* 503 */             str1 = new String(arrayOfByte1, 0, i12);
/*     */           } 
/* 505 */           outputText(x, y, flag, x1, y1, x2, y2, str1);
/*     */           break;
/*     */ 
/*     */         
/*     */         case 1313:
/* 510 */           count = this.in.readWord();
/* 511 */           text = new byte[count];
/*     */           
/* 513 */           for (k = 0; k < count; k++) {
/* 514 */             byte c = (byte)this.in.readByte();
/* 515 */             if (c == 0)
/*     */               break; 
/* 517 */             text[k] = c;
/*     */           } 
/*     */           
/*     */           try {
/* 521 */             s = new String(text, 0, k, "Cp1252");
/*     */           }
/* 523 */           catch (UnsupportedEncodingException e) {
/* 524 */             s = new String(text, 0, k);
/*     */           } 
/* 526 */           count = count + 1 & 0xFFFE;
/* 527 */           this.in.skip(count - k);
/* 528 */           i10 = this.in.readShort();
/* 529 */           i11 = this.in.readShort();
/* 530 */           outputText(i11, i10, 0, 0, 0, 0, 0, s);
/*     */           break;
/*     */         
/*     */         case 513:
/* 534 */           this.state.setCurrentBackgroundColor(this.in.readColor());
/*     */           break;
/*     */         case 521:
/* 537 */           this.state.setCurrentTextColor(this.in.readColor());
/*     */           break;
/*     */         case 302:
/* 540 */           this.state.setTextAlign(this.in.readWord());
/*     */           break;
/*     */         case 258:
/* 543 */           this.state.setBackgroundMode(this.in.readWord());
/*     */           break;
/*     */         case 262:
/* 546 */           this.state.setPolyFillMode(this.in.readWord());
/*     */           break;
/*     */         
/*     */         case 1055:
/* 550 */           color = this.in.readColor();
/* 551 */           n = this.in.readShort();
/* 552 */           i3 = this.in.readShort();
/* 553 */           this.cb.saveState();
/* 554 */           this.cb.setFillColor(color);
/* 555 */           this.cb.rectangle(this.state.transformX(i3), this.state.transformY(n), 0.20000000298023224D, 0.20000000298023224D);
/* 556 */           this.cb.fill();
/* 557 */           this.cb.restoreState();
/*     */           break;
/*     */         
/*     */         case 2881:
/*     */         case 3907:
/* 562 */           rop = this.in.readInt();
/* 563 */           if (function == 3907) {
/* 564 */             this.in.readWord();
/*     */           }
/* 566 */           srcHeight = this.in.readShort();
/* 567 */           srcWidth = this.in.readShort();
/* 568 */           ySrc = this.in.readShort();
/* 569 */           xSrc = this.in.readShort();
/* 570 */           destHeight = this.state.transformY(this.in.readShort()) - this.state.transformY(0);
/* 571 */           destWidth = this.state.transformX(this.in.readShort()) - this.state.transformX(0);
/* 572 */           yDest = this.state.transformY(this.in.readShort());
/* 573 */           xDest = this.state.transformX(this.in.readShort());
/* 574 */           arrayOfByte2 = new byte[tsize * 2 - this.in.getLength() - lenMarker];
/* 575 */           for (i13 = 0; i13 < arrayOfByte2.length; i13++)
/* 576 */             arrayOfByte2[i13] = (byte)this.in.readByte(); 
/*     */           try {
/* 578 */             this.cb.saveState();
/* 579 */             this.cb.rectangle(xDest, yDest, destWidth, destHeight);
/* 580 */             this.cb.clip();
/* 581 */             this.cb.endPath();
/* 582 */             ImageData bmpImage = ImageDataFactory.createBmp(arrayOfByte2, true);
/* 583 */             PdfImageXObject imageXObject = new PdfImageXObject(bmpImage);
/*     */             
/* 585 */             float width = destWidth * bmpImage.getWidth() / srcWidth;
/* 586 */             float height = -destHeight * bmpImage.getHeight() / srcHeight;
/* 587 */             float f13 = xDest - destWidth * xSrc / srcWidth;
/* 588 */             float f14 = yDest + destHeight * ySrc / srcHeight - height;
/* 589 */             this.cb.addXObject((PdfXObject)imageXObject, new Rectangle(f13, f14, width, height));
/* 590 */             this.cb.restoreState();
/*     */           }
/* 592 */           catch (Exception exception) {}
/*     */           break;
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 598 */       this.in.skip(tsize * 2 - this.in.getLength() - lenMarker);
/*     */     } 
/* 600 */     this.state.cleanup(this.cb);
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
/*     */   public void outputText(int x, int y, int flag, int x1, int y1, int x2, int y2, String text) throws IOException {
/* 618 */     MetaFont font = this.state.getCurrentFont();
/* 619 */     float refX = this.state.transformX(x);
/* 620 */     float refY = this.state.transformY(y);
/* 621 */     float angle = this.state.transformAngle(font.getAngle());
/* 622 */     float sin = (float)Math.sin(angle);
/* 623 */     float cos = (float)Math.cos(angle);
/* 624 */     float fontSize = font.getFontSize(this.state);
/* 625 */     FontProgram fp = font.getFont();
/* 626 */     int align = this.state.getTextAlign();
/*     */     
/* 628 */     int normalizedWidth = 0;
/* 629 */     byte[] bytes = font.encoding.convertToBytes(text);
/* 630 */     for (byte b : bytes) {
/* 631 */       normalizedWidth += fp.getWidth(0xFF & b);
/*     */     }
/* 633 */     float textWidth = fontSize / 1000.0F * normalizedWidth;
/* 634 */     float tx = 0.0F;
/* 635 */     float ty = 0.0F;
/* 636 */     float descender = fp.getFontMetrics().getTypoDescender();
/* 637 */     float ury = fp.getFontMetrics().getBbox()[3];
/* 638 */     this.cb.saveState();
/* 639 */     this.cb.concatMatrix(cos, sin, -sin, cos, refX, refY);
/* 640 */     if ((align & 0x6) == 6) {
/* 641 */       tx = -textWidth / 2.0F;
/* 642 */     } else if ((align & 0x2) == 2) {
/* 643 */       tx = -textWidth;
/* 644 */     }  if ((align & 0x18) == 24) {
/* 645 */       ty = 0.0F;
/* 646 */     } else if ((align & 0x8) == 8) {
/* 647 */       ty = -descender;
/*     */     } else {
/* 649 */       ty = -ury;
/*     */     } 
/*     */     
/* 652 */     if (this.state.getBackgroundMode() == 2) {
/* 653 */       Color color = this.state.getCurrentBackgroundColor();
/* 654 */       this.cb.setFillColor(color);
/* 655 */       this.cb.rectangle(tx, (ty + descender), textWidth, (ury - descender));
/* 656 */       this.cb.fill();
/*     */     } 
/* 658 */     Color textColor = this.state.getCurrentTextColor();
/* 659 */     this.cb.setFillColor(textColor);
/* 660 */     this.cb.beginText();
/* 661 */     this.cb.setFontAndSize(PdfFontFactory.createFont(this.state.getCurrentFont().getFont(), "Cp1252", true), fontSize);
/* 662 */     this.cb.setTextMatrix(tx, ty);
/* 663 */     this.cb.showText(text);
/* 664 */     this.cb.endText();
/* 665 */     if (font.isUnderline()) {
/* 666 */       this.cb.rectangle(tx, (ty - fontSize / 4.0F), textWidth, (fontSize / 15.0F));
/* 667 */       this.cb.fill();
/*     */     } 
/* 669 */     if (font.isStrikeout()) {
/* 670 */       this.cb.rectangle(tx, (ty + fontSize / 3.0F), textWidth, (fontSize / 15.0F));
/* 671 */       this.cb.fill();
/*     */     } 
/* 673 */     this.cb.restoreState();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isNullStrokeFill(boolean isRectangle) {
/* 684 */     MetaPen pen = this.state.getCurrentPen();
/* 685 */     MetaBrush brush = this.state.getCurrentBrush();
/* 686 */     boolean noPen = (pen.getStyle() == 5);
/* 687 */     int style = brush.getStyle();
/* 688 */     boolean isBrush = (style == 0 || (style == 2 && this.state.getBackgroundMode() == 2));
/* 689 */     boolean result = (noPen && !isBrush);
/* 690 */     if (!noPen)
/* 691 */       if (isRectangle) {
/* 692 */         this.state.setLineJoinRectangle(this.cb);
/*     */       } else {
/* 694 */         this.state.setLineJoinPolygon(this.cb);
/*     */       }  
/* 696 */     return result;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void strokeAndFill() {
/* 703 */     MetaPen pen = this.state.getCurrentPen();
/* 704 */     MetaBrush brush = this.state.getCurrentBrush();
/* 705 */     int penStyle = pen.getStyle();
/* 706 */     int brushStyle = brush.getStyle();
/* 707 */     if (penStyle == 5) {
/* 708 */       this.cb.closePath();
/* 709 */       if (this.state.getPolyFillMode() == 1) {
/* 710 */         this.cb.eoFill();
/*     */       } else {
/*     */         
/* 713 */         this.cb.fill();
/*     */       } 
/*     */     } else {
/*     */       
/* 717 */       boolean isBrush = (brushStyle == 0 || (brushStyle == 2 && this.state.getBackgroundMode() == 2));
/* 718 */       if (isBrush) {
/* 719 */         if (this.state.getPolyFillMode() == 1) {
/* 720 */           this.cb.closePathEoFillStroke();
/*     */         } else {
/* 722 */           this.cb.closePathFillStroke();
/*     */         } 
/*     */       } else {
/* 725 */         this.cb.closePathStroke();
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   static float getArc(float xCenter, float yCenter, float xDot, float yDot) {
/* 731 */     double s = Math.atan2((yDot - yCenter), (xDot - xCenter));
/* 732 */     if (s < 0.0D)
/* 733 */       s += 6.283185307179586D; 
/* 734 */     return (float)(s / Math.PI * 180.0D);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static byte[] wrapBMP(ImageData image) throws IOException {
/*     */     byte[] data;
/* 745 */     if (image.getOriginalType() != ImageType.BMP) {
/* 746 */       throw new PdfException("Only BMP can be wrapped in WMF.");
/*     */     }
/*     */ 
/*     */     
/* 750 */     if (image.getData() == null) {
/* 751 */       InputStream imgIn = image.getUrl().openStream();
/* 752 */       ByteArrayOutputStream out = new ByteArrayOutputStream();
/* 753 */       int b = 0;
/* 754 */       while ((b = imgIn.read()) != -1)
/* 755 */         out.write(b); 
/* 756 */       imgIn.close();
/* 757 */       data = out.toByteArray();
/*     */     } else {
/* 759 */       data = image.getData();
/*     */     } 
/* 761 */     int sizeBmpWords = data.length - 14 + 1 >>> 1;
/* 762 */     ByteArrayOutputStream os = new ByteArrayOutputStream();
/*     */     
/* 764 */     writeWord(os, 1);
/* 765 */     writeWord(os, 9);
/* 766 */     writeWord(os, 768);
/*     */     
/* 768 */     writeDWord(os, 36 + sizeBmpWords + 3);
/* 769 */     writeWord(os, 1);
/*     */     
/* 771 */     writeDWord(os, 14 + sizeBmpWords);
/* 772 */     writeWord(os, 0);
/*     */     
/* 774 */     writeDWord(os, 4);
/* 775 */     writeWord(os, 259);
/* 776 */     writeWord(os, 8);
/*     */     
/* 778 */     writeDWord(os, 5);
/* 779 */     writeWord(os, 523);
/* 780 */     writeWord(os, 0);
/* 781 */     writeWord(os, 0);
/*     */     
/* 783 */     writeDWord(os, 5);
/* 784 */     writeWord(os, 524);
/* 785 */     writeWord(os, (int)image.getHeight());
/* 786 */     writeWord(os, (int)image.getWidth());
/*     */     
/* 788 */     writeDWord(os, 13 + sizeBmpWords);
/* 789 */     writeWord(os, 2881);
/* 790 */     writeDWord(os, 13369376);
/* 791 */     writeWord(os, (int)image.getHeight());
/* 792 */     writeWord(os, (int)image.getWidth());
/* 793 */     writeWord(os, 0);
/* 794 */     writeWord(os, 0);
/* 795 */     writeWord(os, (int)image.getHeight());
/* 796 */     writeWord(os, (int)image.getWidth());
/* 797 */     writeWord(os, 0);
/* 798 */     writeWord(os, 0);
/* 799 */     os.write(data, 14, data.length - 14);
/* 800 */     if ((data.length & 0x1) == 1) {
/* 801 */       os.write(0);
/*     */     }
/* 803 */     writeDWord(os, 3);
/* 804 */     writeWord(os, 0);
/* 805 */     os.close();
/* 806 */     return os.toByteArray();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void writeWord(OutputStream os, int v) throws IOException {
/* 817 */     os.write(v & 0xFF);
/* 818 */     os.write(v >>> 8 & 0xFF);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void writeDWord(OutputStream os, int v) throws IOException {
/* 829 */     writeWord(os, v & 0xFFFF);
/* 830 */     writeWord(os, v >>> 16 & 0xFFFF);
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/kernel/pdf/canvas/wmf/MetaDo.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */