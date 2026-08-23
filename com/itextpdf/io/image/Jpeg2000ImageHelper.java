/*     */ package com.itextpdf.io.image;
/*     */ 
/*     */ import com.itextpdf.io.IOException;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ final class Jpeg2000ImageHelper
/*     */ {
/*     */   private static final int JPIP_JPIP = 1785751920;
/*     */   private static final int JP2_JP = 1783636000;
/*     */   private static final int JP2_IHDR = 1768449138;
/*     */   private static final int JP2_FTYP = 1718909296;
/*     */   private static final int JP2_JP2H = 1785737832;
/*     */   private static final int JP2_COLR = 1668246642;
/*     */   private static final int JP2_JP2C = 1785737827;
/*     */   private static final int JP2_URL = 1970433056;
/*     */   private static final int JP2_DBTL = 1685348972;
/*     */   private static final int JP2_BPCC = 1651532643;
/*     */   private static final int JP2_JP2 = 1785737760;
/*     */   private static final int JPX_JPXB = 1785755746;
/*     */   
/*     */   private static class Jpeg2000Box
/*     */   {
/*     */     int length;
/*     */     int type;
/*     */     
/*     */     private Jpeg2000Box() {}
/*     */   }
/*     */   
/*     */   private static class ZeroBoxSizeException
/*     */     extends IOException
/*     */   {
/*     */     ZeroBoxSizeException(String s) {
/*  62 */       super(s);
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
/*     */   public static void processImage(ImageData image) {
/*  82 */     if (image.getOriginalType() != ImageType.JPEG2000)
/*  83 */       throw new IllegalArgumentException("JPEG2000 image expected"); 
/*  84 */     processParameters((Jpeg2000ImageData)image);
/*  85 */     image.setFilter("JPXDecode");
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void processParameters(Jpeg2000ImageData jp2) {
/*     */     // Byte code:
/*     */     //   0: aload_0
/*     */     //   1: new com/itextpdf/io/image/Jpeg2000ImageData$Parameters
/*     */     //   4: dup
/*     */     //   5: invokespecial <init> : ()V
/*     */     //   8: putfield parameters : Lcom/itextpdf/io/image/Jpeg2000ImageData$Parameters;
/*     */     //   11: aload_0
/*     */     //   12: invokevirtual getData : ()[B
/*     */     //   15: ifnonnull -> 22
/*     */     //   18: aload_0
/*     */     //   19: invokevirtual loadData : ()V
/*     */     //   22: new java/io/ByteArrayInputStream
/*     */     //   25: dup
/*     */     //   26: aload_0
/*     */     //   27: invokevirtual getData : ()[B
/*     */     //   30: invokespecial <init> : ([B)V
/*     */     //   33: astore_1
/*     */     //   34: new com/itextpdf/io/image/Jpeg2000ImageHelper$Jpeg2000Box
/*     */     //   37: dup
/*     */     //   38: aconst_null
/*     */     //   39: invokespecial <init> : (Lcom/itextpdf/io/image/Jpeg2000ImageHelper$1;)V
/*     */     //   42: astore_2
/*     */     //   43: aload_2
/*     */     //   44: iconst_4
/*     */     //   45: aload_1
/*     */     //   46: invokestatic cio_read : (ILjava/io/InputStream;)I
/*     */     //   49: putfield length : I
/*     */     //   52: aload_2
/*     */     //   53: getfield length : I
/*     */     //   56: bipush #12
/*     */     //   58: if_icmpne -> 440
/*     */     //   61: aload_0
/*     */     //   62: getfield parameters : Lcom/itextpdf/io/image/Jpeg2000ImageData$Parameters;
/*     */     //   65: iconst_1
/*     */     //   66: putfield isJp2 : Z
/*     */     //   69: aload_2
/*     */     //   70: iconst_4
/*     */     //   71: aload_1
/*     */     //   72: invokestatic cio_read : (ILjava/io/InputStream;)I
/*     */     //   75: putfield type : I
/*     */     //   78: ldc 1783636000
/*     */     //   80: aload_2
/*     */     //   81: getfield type : I
/*     */     //   84: if_icmpeq -> 97
/*     */     //   87: new com/itextpdf/io/IOException
/*     */     //   90: dup
/*     */     //   91: ldc 'Expected JP marker.'
/*     */     //   93: invokespecial <init> : (Ljava/lang/String;)V
/*     */     //   96: athrow
/*     */     //   97: ldc 218793738
/*     */     //   99: iconst_4
/*     */     //   100: aload_1
/*     */     //   101: invokestatic cio_read : (ILjava/io/InputStream;)I
/*     */     //   104: if_icmpeq -> 117
/*     */     //   107: new com/itextpdf/io/IOException
/*     */     //   110: dup
/*     */     //   111: ldc 'Error with JP marker.'
/*     */     //   113: invokespecial <init> : (Ljava/lang/String;)V
/*     */     //   116: athrow
/*     */     //   117: aload_2
/*     */     //   118: aload_1
/*     */     //   119: invokestatic jp2_read_boxhdr : (Lcom/itextpdf/io/image/Jpeg2000ImageHelper$Jpeg2000Box;Ljava/io/InputStream;)V
/*     */     //   122: ldc 1718909296
/*     */     //   124: aload_2
/*     */     //   125: getfield type : I
/*     */     //   128: if_icmpeq -> 141
/*     */     //   131: new com/itextpdf/io/IOException
/*     */     //   134: dup
/*     */     //   135: ldc 'Expected FTYP marker.'
/*     */     //   137: invokespecial <init> : (Ljava/lang/String;)V
/*     */     //   140: athrow
/*     */     //   141: aload_1
/*     */     //   142: ldc2_w 8
/*     */     //   145: invokestatic skip : (Ljava/io/InputStream;J)V
/*     */     //   148: iconst_4
/*     */     //   149: istore_3
/*     */     //   150: iload_3
/*     */     //   151: aload_2
/*     */     //   152: getfield length : I
/*     */     //   155: iconst_4
/*     */     //   156: idiv
/*     */     //   157: if_icmpge -> 184
/*     */     //   160: iconst_4
/*     */     //   161: aload_1
/*     */     //   162: invokestatic cio_read : (ILjava/io/InputStream;)I
/*     */     //   165: ldc 1785755746
/*     */     //   167: if_icmpne -> 178
/*     */     //   170: aload_0
/*     */     //   171: getfield parameters : Lcom/itextpdf/io/image/Jpeg2000ImageData$Parameters;
/*     */     //   174: iconst_1
/*     */     //   175: putfield isJpxBaseline : Z
/*     */     //   178: iinc #3, 1
/*     */     //   181: goto -> 150
/*     */     //   184: aload_2
/*     */     //   185: aload_1
/*     */     //   186: invokestatic jp2_read_boxhdr : (Lcom/itextpdf/io/image/Jpeg2000ImageHelper$Jpeg2000Box;Ljava/io/InputStream;)V
/*     */     //   189: ldc 1785737832
/*     */     //   191: aload_2
/*     */     //   192: getfield type : I
/*     */     //   195: if_icmpeq -> 234
/*     */     //   198: aload_2
/*     */     //   199: getfield type : I
/*     */     //   202: ldc 1785737827
/*     */     //   204: if_icmpne -> 217
/*     */     //   207: new com/itextpdf/io/IOException
/*     */     //   210: dup
/*     */     //   211: ldc 'Expected JP2H marker.'
/*     */     //   213: invokespecial <init> : (Ljava/lang/String;)V
/*     */     //   216: athrow
/*     */     //   217: aload_1
/*     */     //   218: aload_2
/*     */     //   219: getfield length : I
/*     */     //   222: bipush #8
/*     */     //   224: isub
/*     */     //   225: i2l
/*     */     //   226: invokestatic skip : (Ljava/io/InputStream;J)V
/*     */     //   229: aload_2
/*     */     //   230: aload_1
/*     */     //   231: invokestatic jp2_read_boxhdr : (Lcom/itextpdf/io/image/Jpeg2000ImageHelper$Jpeg2000Box;Ljava/io/InputStream;)V
/*     */     //   234: ldc 1785737832
/*     */     //   236: aload_2
/*     */     //   237: getfield type : I
/*     */     //   240: if_icmpne -> 189
/*     */     //   243: aload_2
/*     */     //   244: aload_1
/*     */     //   245: invokestatic jp2_read_boxhdr : (Lcom/itextpdf/io/image/Jpeg2000ImageHelper$Jpeg2000Box;Ljava/io/InputStream;)V
/*     */     //   248: ldc 1768449138
/*     */     //   250: aload_2
/*     */     //   251: getfield type : I
/*     */     //   254: if_icmpeq -> 267
/*     */     //   257: new com/itextpdf/io/IOException
/*     */     //   260: dup
/*     */     //   261: ldc 'Expected IHDR marker.'
/*     */     //   263: invokespecial <init> : (Ljava/lang/String;)V
/*     */     //   266: athrow
/*     */     //   267: aload_0
/*     */     //   268: iconst_4
/*     */     //   269: aload_1
/*     */     //   270: invokestatic cio_read : (ILjava/io/InputStream;)I
/*     */     //   273: i2f
/*     */     //   274: invokevirtual setHeight : (F)V
/*     */     //   277: aload_0
/*     */     //   278: iconst_4
/*     */     //   279: aload_1
/*     */     //   280: invokestatic cio_read : (ILjava/io/InputStream;)I
/*     */     //   283: i2f
/*     */     //   284: invokevirtual setWidth : (F)V
/*     */     //   287: aload_0
/*     */     //   288: getfield parameters : Lcom/itextpdf/io/image/Jpeg2000ImageData$Parameters;
/*     */     //   291: iconst_2
/*     */     //   292: aload_1
/*     */     //   293: invokestatic cio_read : (ILjava/io/InputStream;)I
/*     */     //   296: putfield numOfComps : I
/*     */     //   299: aload_0
/*     */     //   300: iconst_1
/*     */     //   301: aload_1
/*     */     //   302: invokestatic cio_read : (ILjava/io/InputStream;)I
/*     */     //   305: invokevirtual setBpc : (I)V
/*     */     //   308: aload_1
/*     */     //   309: ldc2_w 3
/*     */     //   312: invokestatic skip : (Ljava/io/InputStream;J)V
/*     */     //   315: aload_2
/*     */     //   316: aload_1
/*     */     //   317: invokestatic jp2_read_boxhdr : (Lcom/itextpdf/io/image/Jpeg2000ImageHelper$Jpeg2000Box;Ljava/io/InputStream;)V
/*     */     //   320: aload_2
/*     */     //   321: getfield type : I
/*     */     //   324: ldc 1651532643
/*     */     //   326: if_icmpne -> 368
/*     */     //   329: aload_0
/*     */     //   330: getfield parameters : Lcom/itextpdf/io/image/Jpeg2000ImageData$Parameters;
/*     */     //   333: aload_2
/*     */     //   334: getfield length : I
/*     */     //   337: bipush #8
/*     */     //   339: isub
/*     */     //   340: newarray byte
/*     */     //   342: putfield bpcBoxData : [B
/*     */     //   345: aload_1
/*     */     //   346: aload_0
/*     */     //   347: getfield parameters : Lcom/itextpdf/io/image/Jpeg2000ImageData$Parameters;
/*     */     //   350: getfield bpcBoxData : [B
/*     */     //   353: iconst_0
/*     */     //   354: aload_2
/*     */     //   355: getfield length : I
/*     */     //   358: bipush #8
/*     */     //   360: isub
/*     */     //   361: invokevirtual read : ([BII)I
/*     */     //   364: pop
/*     */     //   365: goto -> 537
/*     */     //   368: aload_2
/*     */     //   369: getfield type : I
/*     */     //   372: ldc 1668246642
/*     */     //   374: if_icmpne -> 537
/*     */     //   377: aload_0
/*     */     //   378: getfield parameters : Lcom/itextpdf/io/image/Jpeg2000ImageData$Parameters;
/*     */     //   381: getfield colorSpecBoxes : Ljava/util/List;
/*     */     //   384: ifnonnull -> 401
/*     */     //   387: aload_0
/*     */     //   388: getfield parameters : Lcom/itextpdf/io/image/Jpeg2000ImageData$Parameters;
/*     */     //   391: new java/util/ArrayList
/*     */     //   394: dup
/*     */     //   395: invokespecial <init> : ()V
/*     */     //   398: putfield colorSpecBoxes : Ljava/util/List;
/*     */     //   401: aload_0
/*     */     //   402: getfield parameters : Lcom/itextpdf/io/image/Jpeg2000ImageData$Parameters;
/*     */     //   405: getfield colorSpecBoxes : Ljava/util/List;
/*     */     //   408: aload_2
/*     */     //   409: aload_1
/*     */     //   410: invokestatic jp2_read_colr : (Lcom/itextpdf/io/image/Jpeg2000ImageHelper$Jpeg2000Box;Ljava/io/InputStream;)Lcom/itextpdf/io/image/Jpeg2000ImageData$ColorSpecBox;
/*     */     //   413: invokeinterface add : (Ljava/lang/Object;)Z
/*     */     //   418: pop
/*     */     //   419: aload_2
/*     */     //   420: aload_1
/*     */     //   421: invokestatic jp2_read_boxhdr : (Lcom/itextpdf/io/image/Jpeg2000ImageHelper$Jpeg2000Box;Ljava/io/InputStream;)V
/*     */     //   424: goto -> 428
/*     */     //   427: astore_3
/*     */     //   428: ldc 1668246642
/*     */     //   430: aload_2
/*     */     //   431: getfield type : I
/*     */     //   434: if_icmpeq -> 377
/*     */     //   437: goto -> 537
/*     */     //   440: aload_2
/*     */     //   441: getfield length : I
/*     */     //   444: ldc -11534511
/*     */     //   446: if_icmpne -> 527
/*     */     //   449: aload_1
/*     */     //   450: ldc2_w 4
/*     */     //   453: invokestatic skip : (Ljava/io/InputStream;J)V
/*     */     //   456: iconst_4
/*     */     //   457: aload_1
/*     */     //   458: invokestatic cio_read : (ILjava/io/InputStream;)I
/*     */     //   461: istore_3
/*     */     //   462: iconst_4
/*     */     //   463: aload_1
/*     */     //   464: invokestatic cio_read : (ILjava/io/InputStream;)I
/*     */     //   467: istore #4
/*     */     //   469: iconst_4
/*     */     //   470: aload_1
/*     */     //   471: invokestatic cio_read : (ILjava/io/InputStream;)I
/*     */     //   474: istore #5
/*     */     //   476: iconst_4
/*     */     //   477: aload_1
/*     */     //   478: invokestatic cio_read : (ILjava/io/InputStream;)I
/*     */     //   481: istore #6
/*     */     //   483: aload_1
/*     */     //   484: ldc2_w 16
/*     */     //   487: invokestatic skip : (Ljava/io/InputStream;J)V
/*     */     //   490: aload_0
/*     */     //   491: iconst_2
/*     */     //   492: aload_1
/*     */     //   493: invokestatic cio_read : (ILjava/io/InputStream;)I
/*     */     //   496: invokevirtual setColorSpace : (I)V
/*     */     //   499: aload_0
/*     */     //   500: bipush #8
/*     */     //   502: invokevirtual setBpc : (I)V
/*     */     //   505: aload_0
/*     */     //   506: iload #4
/*     */     //   508: iload #6
/*     */     //   510: isub
/*     */     //   511: i2f
/*     */     //   512: invokevirtual setHeight : (F)V
/*     */     //   515: aload_0
/*     */     //   516: iload_3
/*     */     //   517: iload #5
/*     */     //   519: isub
/*     */     //   520: i2f
/*     */     //   521: invokevirtual setWidth : (F)V
/*     */     //   524: goto -> 537
/*     */     //   527: new com/itextpdf/io/IOException
/*     */     //   530: dup
/*     */     //   531: ldc 'Invalid JPEG2000 file.'
/*     */     //   533: invokespecial <init> : (Ljava/lang/String;)V
/*     */     //   536: athrow
/*     */     //   537: goto -> 552
/*     */     //   540: astore_1
/*     */     //   541: new com/itextpdf/io/IOException
/*     */     //   544: dup
/*     */     //   545: ldc 'JPEG2000 image exception.'
/*     */     //   547: aload_1
/*     */     //   548: invokespecial <init> : (Ljava/lang/String;Ljava/lang/Throwable;)V
/*     */     //   551: athrow
/*     */     //   552: return
/*     */     // Line number table:
/*     */     //   Java source line number -> byte code offset
/*     */     //   #92	-> 0
/*     */     //   #94	-> 11
/*     */     //   #95	-> 18
/*     */     //   #97	-> 22
/*     */     //   #98	-> 34
/*     */     //   #99	-> 43
/*     */     //   #100	-> 52
/*     */     //   #101	-> 61
/*     */     //   #102	-> 69
/*     */     //   #103	-> 78
/*     */     //   #104	-> 87
/*     */     //   #106	-> 97
/*     */     //   #107	-> 107
/*     */     //   #110	-> 117
/*     */     //   #111	-> 122
/*     */     //   #112	-> 131
/*     */     //   #114	-> 141
/*     */     //   #115	-> 148
/*     */     //   #116	-> 160
/*     */     //   #117	-> 170
/*     */     //   #115	-> 178
/*     */     //   #121	-> 184
/*     */     //   #123	-> 189
/*     */     //   #124	-> 198
/*     */     //   #125	-> 207
/*     */     //   #127	-> 217
/*     */     //   #128	-> 229
/*     */     //   #130	-> 234
/*     */     //   #131	-> 243
/*     */     //   #132	-> 248
/*     */     //   #133	-> 257
/*     */     //   #135	-> 267
/*     */     //   #136	-> 277
/*     */     //   #137	-> 287
/*     */     //   #138	-> 299
/*     */     //   #139	-> 308
/*     */     //   #140	-> 315
/*     */     //   #141	-> 320
/*     */     //   #142	-> 329
/*     */     //   #143	-> 345
/*     */     //   #144	-> 368
/*     */     //   #146	-> 377
/*     */     //   #147	-> 387
/*     */     //   #148	-> 401
/*     */     //   #150	-> 419
/*     */     //   #153	-> 424
/*     */     //   #151	-> 427
/*     */     //   #154	-> 428
/*     */     //   #156	-> 440
/*     */     //   #157	-> 449
/*     */     //   #158	-> 456
/*     */     //   #159	-> 462
/*     */     //   #160	-> 469
/*     */     //   #161	-> 476
/*     */     //   #162	-> 483
/*     */     //   #163	-> 490
/*     */     //   #164	-> 499
/*     */     //   #165	-> 505
/*     */     //   #166	-> 515
/*     */     //   #167	-> 524
/*     */     //   #168	-> 527
/*     */     //   #172	-> 537
/*     */     //   #170	-> 540
/*     */     //   #171	-> 541
/*     */     //   #173	-> 552
/*     */     // Local variable table:
/*     */     //   start	length	slot	name	descriptor
/*     */     //   150	34	3	i	I
/*     */     //   462	62	3	x1	I
/*     */     //   469	55	4	y1	I
/*     */     //   476	48	5	x0	I
/*     */     //   483	41	6	y0	I
/*     */     //   34	503	1	jpeg2000Stream	Ljava/io/InputStream;
/*     */     //   43	494	2	box	Lcom/itextpdf/io/image/Jpeg2000ImageHelper$Jpeg2000Box;
/*     */     //   541	11	1	e	Ljava/io/IOException;
/*     */     //   0	553	0	jp2	Lcom/itextpdf/io/image/Jpeg2000ImageData;
/*     */     // Exception table:
/*     */     //   from	to	target	type
/*     */     //   11	537	540	java/io/IOException
/*     */     //   419	424	427	com/itextpdf/io/image/Jpeg2000ImageHelper$ZeroBoxSizeException
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static Jpeg2000ImageData.ColorSpecBox jp2_read_colr(Jpeg2000Box box, InputStream jpeg2000Stream) throws IOException {
/* 176 */     int readBytes = 8;
/* 177 */     Jpeg2000ImageData.ColorSpecBox colorSpecBox = new Jpeg2000ImageData.ColorSpecBox();
/* 178 */     for (int i = 0; i < 3; i++) {
/* 179 */       colorSpecBox.add(Integer.valueOf(cio_read(1, jpeg2000Stream)));
/* 180 */       readBytes++;
/*     */     } 
/* 182 */     if (colorSpecBox.getMeth() == 1) {
/* 183 */       colorSpecBox.add(Integer.valueOf(cio_read(4, jpeg2000Stream)));
/* 184 */       readBytes += 4;
/*     */     } else {
/* 186 */       colorSpecBox.add(Integer.valueOf(0));
/*     */     } 
/*     */     
/* 189 */     if (box.length - readBytes > 0) {
/* 190 */       byte[] colorProfile = new byte[box.length - readBytes];
/* 191 */       jpeg2000Stream.read(colorProfile, 0, box.length - readBytes);
/* 192 */       colorSpecBox.setColorProfile(colorProfile);
/*     */     } 
/* 194 */     return colorSpecBox;
/*     */   }
/*     */   
/*     */   private static void jp2_read_boxhdr(Jpeg2000Box box, InputStream jpeg2000Stream) throws IOException {
/* 198 */     box.length = cio_read(4, jpeg2000Stream);
/* 199 */     box.type = cio_read(4, jpeg2000Stream);
/* 200 */     if (box.length == 1) {
/* 201 */       if (cio_read(4, jpeg2000Stream) != 0) {
/* 202 */         throw new IOException("Cannot handle box sizes higher than 2^32.");
/*     */       }
/* 204 */       box.length = cio_read(4, jpeg2000Stream);
/* 205 */       if (box.length == 0)
/* 206 */         throw new IOException("Unsupported box size == 0."); 
/* 207 */     } else if (box.length == 0) {
/* 208 */       throw new ZeroBoxSizeException("Unsupported box size == 0");
/*     */     } 
/*     */   }
/*     */   
/*     */   private static int cio_read(int n, InputStream jpeg2000Stream) throws IOException {
/* 213 */     int v = 0;
/* 214 */     for (int i = n - 1; i >= 0; i--) {
/* 215 */       v += jpeg2000Stream.read() << i << 3;
/*     */     }
/* 217 */     return v;
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/io/image/Jpeg2000ImageHelper.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */