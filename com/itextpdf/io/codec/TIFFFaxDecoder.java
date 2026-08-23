/*      */ package com.itextpdf.io.codec;
/*      */ 
/*      */ import com.itextpdf.io.IOException;
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
/*      */ public class TIFFFaxDecoder
/*      */ {
/*      */   private int bitPointer;
/*      */   private int bytePointer;
/*      */   private byte[] data;
/*      */   private int w;
/*      */   private int h;
/*      */   private int fillOrder;
/*   62 */   private int changingElemSize = 0;
/*      */   
/*      */   private int[] prevChangingElems;
/*      */   
/*      */   private int[] currChangingElems;
/*   67 */   private int lastChangingElement = 0;
/*      */   
/*   69 */   private int compression = 2;
/*      */ 
/*      */   
/*   72 */   private int uncompressedMode = 0;
/*   73 */   private int fillBits = 0;
/*      */   
/*      */   private int oneD;
/*      */   
/*      */   private boolean recoverFromImageError;
/*      */   
/*   79 */   static int[] table1 = new int[] { 0, 1, 3, 7, 15, 31, 63, 127, 255 };
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
/*  109 */   static int[] table2 = new int[] { 0, 128, 192, 224, 240, 248, 252, 254, 255 };
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
/*  140 */   public static byte[] flipTable = new byte[] { 0, Byte.MIN_VALUE, 64, -64, 32, -96, 96, -32, 16, -112, 80, -48, 48, -80, 112, -16, 8, -120, 72, -56, 40, -88, 104, -24, 24, -104, 88, -40, 56, -72, 120, -8, 4, -124, 68, -60, 36, -92, 100, -28, 20, -108, 84, -44, 52, -76, 116, -12, 12, -116, 76, -52, 44, -84, 108, -20, 28, -100, 92, -36, 60, -68, 124, -4, 2, -126, 66, -62, 34, -94, 98, -30, 18, -110, 82, -46, 50, -78, 114, -14, 10, -118, 74, -54, 42, -86, 106, -22, 26, -102, 90, -38, 58, -70, 122, -6, 6, -122, 70, -58, 38, -90, 102, -26, 22, -106, 86, -42, 54, -74, 118, -10, 14, -114, 78, -50, 46, -82, 110, -18, 30, -98, 94, -34, 62, -66, 126, -2, 1, -127, 65, -63, 33, -95, 97, -31, 17, -111, 81, -47, 49, -79, 113, -15, 9, -119, 73, -55, 41, -87, 105, -23, 25, -103, 89, -39, 57, -71, 121, -7, 5, -123, 69, -59, 37, -91, 101, -27, 21, -107, 85, -43, 53, -75, 117, -11, 13, -115, 77, -51, 45, -83, 109, -19, 29, -99, 93, -35, 61, -67, 125, -3, 3, -125, 67, -61, 35, -93, 99, -29, 19, -109, 83, -45, 51, -77, 115, -13, 11, -117, 75, -53, 43, -85, 107, -21, 27, -101, 91, -37, 59, -69, 123, -5, 7, -121, 71, -57, 39, -89, 103, -25, 23, -105, 87, -41, 55, -73, 119, -9, 15, -113, 79, -49, 47, -81, 111, -17, 31, -97, 95, -33, 63, -65, Byte.MAX_VALUE, -1 };
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
/*  176 */   static short[] white = new short[] { 6430, 6400, 6400, 6400, 3225, 3225, 3225, 3225, 944, 944, 944, 944, 976, 976, 976, 976, 1456, 1456, 1456, 1456, 1488, 1488, 1488, 1488, 718, 718, 718, 718, 718, 718, 718, 718, 750, 750, 750, 750, 750, 750, 750, 750, 1520, 1520, 1520, 1520, 1552, 1552, 1552, 1552, 428, 428, 428, 428, 428, 428, 428, 428, 428, 428, 428, 428, 428, 428, 428, 428, 654, 654, 654, 654, 654, 654, 654, 654, 1072, 1072, 1072, 1072, 1104, 1104, 1104, 1104, 1136, 1136, 1136, 1136, 1168, 1168, 1168, 1168, 1200, 1200, 1200, 1200, 1232, 1232, 1232, 1232, 622, 622, 622, 622, 622, 622, 622, 622, 1008, 1008, 1008, 1008, 1040, 1040, 1040, 1040, 44, 44, 44, 44, 44, 44, 44, 44, 44, 44, 44, 44, 44, 44, 44, 44, 396, 396, 396, 396, 396, 396, 396, 396, 396, 396, 396, 396, 396, 396, 396, 396, 1712, 1712, 1712, 1712, 1744, 1744, 1744, 1744, 846, 846, 846, 846, 846, 846, 846, 846, 1264, 1264, 1264, 1264, 1296, 1296, 1296, 1296, 1328, 1328, 1328, 1328, 1360, 1360, 1360, 1360, 1392, 1392, 1392, 1392, 1424, 1424, 1424, 1424, 686, 686, 686, 686, 686, 686, 686, 686, 910, 910, 910, 910, 910, 910, 910, 910, 1968, 1968, 1968, 1968, 2000, 2000, 2000, 2000, 2032, 2032, 2032, 2032, 16, 16, 16, 16, 10257, 10257, 10257, 10257, 12305, 12305, 12305, 12305, 330, 330, 330, 330, 330, 330, 330, 330, 330, 330, 330, 330, 330, 330, 330, 330, 330, 330, 330, 330, 330, 330, 330, 330, 330, 330, 330, 330, 330, 330, 330, 330, 362, 362, 362, 362, 362, 362, 362, 362, 362, 362, 362, 362, 362, 362, 362, 362, 362, 362, 362, 362, 362, 362, 362, 362, 362, 362, 362, 362, 362, 362, 362, 362, 878, 878, 878, 878, 878, 878, 878, 878, 1904, 1904, 1904, 1904, 1936, 1936, 1936, 1936, -18413, -18413, -16365, -16365, -14317, -14317, -10221, -10221, 590, 590, 590, 590, 590, 590, 590, 590, 782, 782, 782, 782, 782, 782, 782, 782, 1584, 1584, 1584, 1584, 1616, 1616, 1616, 1616, 1648, 1648, 1648, 1648, 1680, 1680, 1680, 1680, 814, 814, 814, 814, 814, 814, 814, 814, 1776, 1776, 1776, 1776, 1808, 1808, 1808, 1808, 1840, 1840, 1840, 1840, 1872, 1872, 1872, 1872, 6157, 6157, 6157, 6157, 6157, 6157, 6157, 6157, 6157, 6157, 6157, 6157, 6157, 6157, 6157, 6157, -12275, -12275, -12275, -12275, -12275, -12275, -12275, -12275, -12275, -12275, -12275, -12275, -12275, -12275, -12275, -12275, 14353, 14353, 14353, 14353, 16401, 16401, 16401, 16401, 22547, 22547, 24595, 24595, 20497, 20497, 20497, 20497, 18449, 18449, 18449, 18449, 26643, 26643, 28691, 28691, 30739, 30739, -32749, -32749, -30701, -30701, -28653, -28653, -26605, -26605, -24557, -24557, -22509, -22509, -20461, -20461, 8207, 8207, 8207, 8207, 8207, 8207, 8207, 8207, 72, 72, 72, 72, 72, 72, 72, 72, 72, 72, 72, 72, 72, 72, 72, 72, 72, 72, 72, 72, 72, 72, 72, 72, 72, 72, 72, 72, 72, 72, 72, 72, 72, 72, 72, 72, 72, 72, 72, 72, 72, 72, 72, 72, 72, 72, 72, 72, 72, 72, 72, 72, 72, 72, 72, 72, 72, 72, 72, 72, 72, 72, 72, 72, 104, 104, 104, 104, 104, 104, 104, 104, 104, 104, 104, 104, 104, 104, 104, 104, 104, 104, 104, 104, 104, 104, 104, 104, 104, 104, 104, 104, 104, 104, 104, 104, 104, 104, 104, 104, 104, 104, 104, 104, 104, 104, 104, 104, 104, 104, 104, 104, 104, 104, 104, 104, 104, 104, 104, 104, 104, 104, 104, 104, 104, 104, 104, 104, 4107, 4107, 4107, 4107, 4107, 4107, 4107, 4107, 4107, 4107, 4107, 4107, 4107, 4107, 4107, 4107, 4107, 4107, 4107, 4107, 4107, 4107, 4107, 4107, 4107, 4107, 4107, 4107, 4107, 4107, 4107, 4107, 266, 266, 266, 266, 266, 266, 266, 266, 266, 266, 266, 266, 266, 266, 266, 266, 266, 266, 266, 266, 266, 266, 266, 266, 266, 266, 266, 266, 266, 266, 266, 266, 298, 298, 298, 298, 298, 298, 298, 298, 298, 298, 298, 298, 298, 298, 298, 298, 298, 298, 298, 298, 298, 298, 298, 298, 298, 298, 298, 298, 298, 298, 298, 298, 524, 524, 524, 524, 524, 524, 524, 524, 524, 524, 524, 524, 524, 524, 524, 524, 556, 556, 556, 556, 556, 556, 556, 556, 556, 556, 556, 556, 556, 556, 556, 556, 136, 136, 136, 136, 136, 136, 136, 136, 136, 136, 136, 136, 136, 136, 136, 136, 136, 136, 136, 136, 136, 136, 136, 136, 136, 136, 136, 136, 136, 136, 136, 136, 136, 136, 136, 136, 136, 136, 136, 136, 136, 136, 136, 136, 136, 136, 136, 136, 136, 136, 136, 136, 136, 136, 136, 136, 136, 136, 136, 136, 136, 136, 136, 136, 168, 168, 168, 168, 168, 168, 168, 168, 168, 168, 168, 168, 168, 168, 168, 168, 168, 168, 168, 168, 168, 168, 168, 168, 168, 168, 168, 168, 168, 168, 168, 168, 168, 168, 168, 168, 168, 168, 168, 168, 168, 168, 168, 168, 168, 168, 168, 168, 168, 168, 168, 168, 168, 168, 168, 168, 168, 168, 168, 168, 168, 168, 168, 168, 460, 460, 460, 460, 460, 460, 460, 460, 460, 460, 460, 460, 460, 460, 460, 460, 492, 492, 492, 492, 492, 492, 492, 492, 492, 492, 492, 492, 492, 492, 492, 492, 2059, 2059, 2059, 2059, 2059, 2059, 2059, 2059, 2059, 2059, 2059, 2059, 2059, 2059, 2059, 2059, 2059, 2059, 2059, 2059, 2059, 2059, 2059, 2059, 2059, 2059, 2059, 2059, 2059, 2059, 2059, 2059, 200, 200, 200, 200, 200, 200, 200, 200, 200, 200, 200, 200, 200, 200, 200, 200, 200, 200, 200, 200, 200, 200, 200, 200, 200, 200, 200, 200, 200, 200, 200, 200, 200, 200, 200, 200, 200, 200, 200, 200, 200, 200, 200, 200, 200, 200, 200, 200, 200, 200, 200, 200, 200, 200, 200, 200, 200, 200, 200, 200, 200, 200, 200, 200, 232, 232, 232, 232, 232, 232, 232, 232, 232, 232, 232, 232, 232, 232, 232, 232, 232, 232, 232, 232, 232, 232, 232, 232, 232, 232, 232, 232, 232, 232, 232, 232, 232, 232, 232, 232, 232, 232, 232, 232, 232, 232, 232, 232, 232, 232, 232, 232, 232, 232, 232, 232, 232, 232, 232, 232, 232, 232, 232, 232, 232, 232, 232, 232 };
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
/*  443 */   public static short[] additionalMakeup = new short[] { 28679, 28679, 31752, -32759, -31735, -30711, -29687, -28663, 29703, 29703, 30727, 30727, -27639, -26615, -25591, -24567 };
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  451 */   static short[] initBlack = new short[] { 3226, 6412, 200, 168, 38, 38, 134, 134, 100, 100, 100, 100, 68, 68, 68, 68 };
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  459 */   static short[] twoBitBlack = new short[] { 292, 260, 226, 226 };
/*      */ 
/*      */   
/*  462 */   static short[] black = new short[] { 62, 62, 30, 30, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 3225, 3225, 3225, 3225, 3225, 3225, 3225, 3225, 3225, 3225, 3225, 3225, 3225, 3225, 3225, 3225, 3225, 3225, 3225, 3225, 3225, 3225, 3225, 3225, 3225, 3225, 3225, 3225, 3225, 3225, 3225, 3225, 588, 588, 588, 588, 588, 588, 588, 588, 1680, 1680, 20499, 22547, 24595, 26643, 1776, 1776, 1808, 1808, -24557, -22509, -20461, -18413, 1904, 1904, 1936, 1936, -16365, -14317, 782, 782, 782, 782, 814, 814, 814, 814, -12269, -10221, 10257, 10257, 12305, 12305, 14353, 14353, 16403, 18451, 1712, 1712, 1744, 1744, 28691, 30739, -32749, -30701, -28653, -26605, 2061, 2061, 2061, 2061, 2061, 2061, 2061, 2061, 424, 424, 424, 424, 424, 424, 424, 424, 424, 424, 424, 424, 424, 424, 424, 424, 424, 424, 424, 424, 424, 424, 424, 424, 424, 424, 424, 424, 424, 424, 424, 424, 750, 750, 750, 750, 1616, 1616, 1648, 1648, 1424, 1424, 1456, 1456, 1488, 1488, 1520, 1520, 1840, 1840, 1872, 1872, 1968, 1968, 8209, 8209, 524, 524, 524, 524, 524, 524, 524, 524, 556, 556, 556, 556, 556, 556, 556, 556, 1552, 1552, 1584, 1584, 2000, 2000, 2032, 2032, 976, 976, 1008, 1008, 1040, 1040, 1072, 1072, 1296, 1296, 1328, 1328, 718, 718, 718, 718, 456, 456, 456, 456, 456, 456, 456, 456, 456, 456, 456, 456, 456, 456, 456, 456, 456, 456, 456, 456, 456, 456, 456, 456, 456, 456, 456, 456, 456, 456, 456, 456, 326, 326, 326, 326, 326, 326, 326, 326, 326, 326, 326, 326, 326, 326, 326, 326, 326, 326, 326, 326, 326, 326, 326, 326, 326, 326, 326, 326, 326, 326, 326, 326, 326, 326, 326, 326, 326, 326, 326, 326, 326, 326, 326, 326, 326, 326, 326, 326, 326, 326, 326, 326, 326, 326, 326, 326, 326, 326, 326, 326, 326, 326, 326, 326, 358, 358, 358, 358, 358, 358, 358, 358, 358, 358, 358, 358, 358, 358, 358, 358, 358, 358, 358, 358, 358, 358, 358, 358, 358, 358, 358, 358, 358, 358, 358, 358, 358, 358, 358, 358, 358, 358, 358, 358, 358, 358, 358, 358, 358, 358, 358, 358, 358, 358, 358, 358, 358, 358, 358, 358, 358, 358, 358, 358, 358, 358, 358, 358, 490, 490, 490, 490, 490, 490, 490, 490, 490, 490, 490, 490, 490, 490, 490, 490, 4113, 4113, 6161, 6161, 848, 848, 880, 880, 912, 912, 944, 944, 622, 622, 622, 622, 654, 654, 654, 654, 1104, 1104, 1136, 1136, 1168, 1168, 1200, 1200, 1232, 1232, 1264, 1264, 686, 686, 686, 686, 1360, 1360, 1392, 1392, 12, 12, 12, 12, 12, 12, 12, 12, 390, 390, 390, 390, 390, 390, 390, 390, 390, 390, 390, 390, 390, 390, 390, 390, 390, 390, 390, 390, 390, 390, 390, 390, 390, 390, 390, 390, 390, 390, 390, 390, 390, 390, 390, 390, 390, 390, 390, 390, 390, 390, 390, 390, 390, 390, 390, 390, 390, 390, 390, 390, 390, 390, 390, 390, 390, 390, 390, 390, 390, 390, 390, 390 };
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
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  593 */   static byte[] twoDCodes = new byte[] { 80, 88, 23, 71, 30, 30, 62, 62, 4, 4, 4, 4, 4, 4, 4, 4, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 35, 35, 35, 35, 35, 35, 35, 35, 35, 35, 35, 35, 35, 35, 35, 35, 51, 51, 51, 51, 51, 51, 51, 51, 51, 51, 51, 51, 51, 51, 51, 51, 41, 41, 41, 41, 41, 41, 41, 41, 41, 41, 41, 41, 41, 41, 41, 41, 41, 41, 41, 41, 41, 41, 41, 41, 41, 41, 41, 41, 41, 41, 41, 41, 41, 41, 41, 41, 41, 41, 41, 41, 41, 41, 41, 41, 41, 41, 41, 41, 41, 41, 41, 41, 41, 41, 41, 41, 41, 41, 41, 41, 41, 41, 41, 41 };
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
/*      */   public TIFFFaxDecoder(int fillOrder, int w, int h) {
/*  634 */     this.fillOrder = fillOrder;
/*  635 */     this.w = w;
/*  636 */     this.h = h;
/*      */     
/*  638 */     this.bitPointer = 0;
/*  639 */     this.bytePointer = 0;
/*  640 */     this.prevChangingElems = new int[2 * w];
/*  641 */     this.currChangingElems = new int[2 * w];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void reverseBits(byte[] b) {
/*  650 */     for (int k = 0; k < b.length; k++) {
/*  651 */       b[k] = flipTable[b[k] & 0xFF];
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public void decode1D(byte[] buffer, byte[] compData, int startX, int height) {
/*  657 */     this.data = compData;
/*      */     
/*  659 */     int lineOffset = 0;
/*  660 */     int scanlineStride = (this.w + 7) / 8;
/*      */     
/*  662 */     this.bitPointer = 0;
/*  663 */     this.bytePointer = 0;
/*      */     
/*  665 */     for (int i = 0; i < height; i++) {
/*  666 */       decodeNextScanline(buffer, lineOffset, startX);
/*  667 */       lineOffset += scanlineStride;
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public void decodeNextScanline(byte[] buffer, int lineOffset, int bitOffset) {
/*  673 */     boolean isWhite = true;
/*      */ 
/*      */     
/*  676 */     this.changingElemSize = 0;
/*      */ 
/*      */     
/*  679 */     while (bitOffset < this.w) {
/*  680 */       while (isWhite) {
/*      */         
/*  682 */         int current = nextNBits(10);
/*  683 */         int entry = white[current];
/*      */ 
/*      */         
/*  686 */         int isT = entry & 0x1;
/*  687 */         int bits = entry >>> 1 & 0xF;
/*      */ 
/*      */         
/*  690 */         if (bits == 12) {
/*      */ 
/*      */           
/*  693 */           int twoBits = nextLesserThan8Bits(2);
/*      */ 
/*      */           
/*  696 */           current = current << 2 & 0xC | twoBits;
/*  697 */           entry = additionalMakeup[current];
/*      */ 
/*      */           
/*  700 */           bits = entry >>> 1 & 0x7;
/*      */ 
/*      */           
/*  703 */           int i = entry >>> 4 & 0xFFF;
/*      */ 
/*      */           
/*  706 */           bitOffset += i;
/*      */           
/*  708 */           updatePointer(4 - bits);
/*      */           continue;
/*      */         } 
/*  711 */         if (bits == 0) {
/*  712 */           throw new IOException("Invalid code encountered.");
/*      */         }
/*      */         
/*  715 */         if (bits == 15) {
/*  716 */           throw new IOException("EOL code word encountered in White run.");
/*      */         }
/*      */ 
/*      */         
/*  720 */         int code = entry >>> 5 & 0x7FF;
/*  721 */         bitOffset += code;
/*      */         
/*  723 */         updatePointer(10 - bits);
/*  724 */         if (isT == 0) {
/*  725 */           isWhite = false;
/*  726 */           this.currChangingElems[this.changingElemSize++] = bitOffset;
/*      */         } 
/*      */       } 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  733 */       if (bitOffset == this.w) {
/*  734 */         if (this.compression == 2) {
/*  735 */           advancePointer();
/*      */         }
/*      */         
/*      */         break;
/*      */       } 
/*  740 */       while (!isWhite) {
/*      */         
/*  742 */         int current = nextLesserThan8Bits(4);
/*  743 */         int entry = initBlack[current];
/*      */ 
/*      */         
/*  746 */         int bits = entry >>> 1 & 0xF;
/*  747 */         int code = entry >>> 5 & 0x7FF;
/*      */         
/*  749 */         if (code == 100) {
/*  750 */           current = nextNBits(9);
/*  751 */           entry = black[current];
/*      */ 
/*      */           
/*  754 */           int isT = entry & 0x1;
/*  755 */           bits = entry >>> 1 & 0xF;
/*  756 */           code = entry >>> 5 & 0x7FF;
/*      */           
/*  758 */           if (bits == 12) {
/*      */             
/*  760 */             updatePointer(5);
/*  761 */             current = nextLesserThan8Bits(4);
/*  762 */             entry = additionalMakeup[current];
/*      */ 
/*      */             
/*  765 */             bits = entry >>> 1 & 0x7;
/*      */ 
/*      */             
/*  768 */             code = entry >>> 4 & 0xFFF;
/*      */             
/*  770 */             setToBlack(buffer, lineOffset, bitOffset, code);
/*  771 */             bitOffset += code;
/*      */             
/*  773 */             updatePointer(4 - bits); continue;
/*  774 */           }  if (bits == 15)
/*      */           {
/*      */             
/*  777 */             throw new IOException("EOL code word encountered in White run.");
/*      */           }
/*  779 */           setToBlack(buffer, lineOffset, bitOffset, code);
/*  780 */           bitOffset += code;
/*      */           
/*  782 */           updatePointer(9 - bits);
/*  783 */           if (isT == 0) {
/*  784 */             isWhite = true;
/*  785 */             this.currChangingElems[this.changingElemSize++] = bitOffset;
/*      */           }  continue;
/*      */         } 
/*  788 */         if (code == 200) {
/*      */ 
/*      */           
/*  791 */           current = nextLesserThan8Bits(2);
/*  792 */           entry = twoBitBlack[current];
/*  793 */           code = entry >>> 5 & 0x7FF;
/*  794 */           bits = entry >>> 1 & 0xF;
/*      */           
/*  796 */           setToBlack(buffer, lineOffset, bitOffset, code);
/*  797 */           bitOffset += code;
/*      */           
/*  799 */           updatePointer(2 - bits);
/*  800 */           isWhite = true;
/*  801 */           this.currChangingElems[this.changingElemSize++] = bitOffset;
/*      */           
/*      */           continue;
/*      */         } 
/*  805 */         setToBlack(buffer, lineOffset, bitOffset, code);
/*  806 */         bitOffset += code;
/*      */         
/*  808 */         updatePointer(4 - bits);
/*  809 */         isWhite = true;
/*  810 */         this.currChangingElems[this.changingElemSize++] = bitOffset;
/*      */       } 
/*      */ 
/*      */ 
/*      */       
/*  815 */       if (bitOffset == this.w) {
/*  816 */         if (this.compression == 2) {
/*  817 */           advancePointer();
/*      */         }
/*      */         
/*      */         break;
/*      */       } 
/*      */     } 
/*  823 */     this.currChangingElems[this.changingElemSize++] = bitOffset;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void decode2D(byte[] buffer, byte[] compData, int startX, int height, long tiffT4Options) {
/*  829 */     this.data = compData;
/*  830 */     this.compression = 3;
/*      */     
/*  832 */     this.bitPointer = 0;
/*  833 */     this.bytePointer = 0;
/*      */     
/*  835 */     int scanlineStride = (this.w + 7) / 8;
/*      */ 
/*      */     
/*  838 */     int[] b = new int[2];
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
/*  850 */     this.oneD = (int)(tiffT4Options & 0x1L);
/*  851 */     this.uncompressedMode = (int)((tiffT4Options & 0x2L) >> 1L);
/*  852 */     this.fillBits = (int)((tiffT4Options & 0x4L) >> 2L);
/*      */ 
/*      */     
/*  855 */     if (readEOL(true) != 1) {
/*  856 */       throw new IOException("First scanline must be 1D encoded.");
/*      */     }
/*      */     
/*  859 */     int lineOffset = 0;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  864 */     decodeNextScanline(buffer, lineOffset, startX);
/*  865 */     lineOffset += scanlineStride;
/*      */     
/*  867 */     for (int lines = 1; lines < height; lines++) {
/*      */ 
/*      */ 
/*      */       
/*  871 */       if (readEOL(false) == 0) {
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  876 */         int[] temp = this.prevChangingElems;
/*  877 */         this.prevChangingElems = this.currChangingElems;
/*  878 */         this.currChangingElems = temp;
/*  879 */         int currIndex = 0;
/*      */ 
/*      */         
/*  882 */         int a0 = -1;
/*  883 */         boolean isWhite = true;
/*  884 */         int bitOffset = startX;
/*      */         
/*  886 */         this.lastChangingElement = 0;
/*      */         
/*  888 */         while (bitOffset < this.w) {
/*      */           
/*  890 */           getNextChangingElement(a0, isWhite, b);
/*      */           
/*  892 */           int b1 = b[0];
/*  893 */           int b2 = b[1];
/*      */ 
/*      */           
/*  896 */           int entry = nextLesserThan8Bits(7);
/*      */ 
/*      */           
/*  899 */           entry = twoDCodes[entry] & 0xFF;
/*      */ 
/*      */           
/*  902 */           int code = (entry & 0x78) >>> 3;
/*  903 */           int bits = entry & 0x7;
/*      */           
/*  905 */           if (code == 0) {
/*  906 */             if (!isWhite) {
/*  907 */               setToBlack(buffer, lineOffset, bitOffset, b2 - bitOffset);
/*      */             }
/*      */             
/*  910 */             bitOffset = a0 = b2;
/*      */ 
/*      */             
/*  913 */             updatePointer(7 - bits); continue;
/*  914 */           }  if (code == 1) {
/*      */             
/*  916 */             updatePointer(7 - bits);
/*      */ 
/*      */ 
/*      */             
/*  920 */             if (isWhite) {
/*  921 */               int number = decodeWhiteCodeWord();
/*  922 */               bitOffset += number;
/*  923 */               this.currChangingElems[currIndex++] = bitOffset;
/*      */               
/*  925 */               number = decodeBlackCodeWord();
/*  926 */               setToBlack(buffer, lineOffset, bitOffset, number);
/*  927 */               bitOffset += number;
/*  928 */               this.currChangingElems[currIndex++] = bitOffset;
/*      */             } else {
/*  930 */               int number = decodeBlackCodeWord();
/*  931 */               setToBlack(buffer, lineOffset, bitOffset, number);
/*  932 */               bitOffset += number;
/*  933 */               this.currChangingElems[currIndex++] = bitOffset;
/*      */               
/*  935 */               number = decodeWhiteCodeWord();
/*  936 */               bitOffset += number;
/*  937 */               this.currChangingElems[currIndex++] = bitOffset;
/*      */             } 
/*      */             
/*  940 */             a0 = bitOffset; continue;
/*  941 */           }  if (code <= 8) {
/*      */             
/*  943 */             int a1 = b1 + code - 5;
/*      */             
/*  945 */             this.currChangingElems[currIndex++] = a1;
/*      */ 
/*      */ 
/*      */             
/*  949 */             if (!isWhite) {
/*  950 */               setToBlack(buffer, lineOffset, bitOffset, a1 - bitOffset);
/*      */             }
/*      */             
/*  953 */             bitOffset = a0 = a1;
/*  954 */             isWhite = !isWhite;
/*      */             
/*  956 */             updatePointer(7 - bits); continue;
/*      */           } 
/*  958 */           throw new IOException("Invalid code encountered while decoding 2D group 3 compressed data.");
/*      */         } 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  964 */         this.currChangingElems[currIndex++] = bitOffset;
/*  965 */         this.changingElemSize = currIndex;
/*      */       } else {
/*      */         
/*  968 */         decodeNextScanline(buffer, lineOffset, startX);
/*      */       } 
/*      */       
/*  971 */       lineOffset += scanlineStride;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void decodeT6(byte[] buffer, byte[] compData, int startX, int height, long tiffT6Options) {
/*  980 */     this.data = compData;
/*  981 */     this.compression = 4;
/*      */     
/*  983 */     this.bitPointer = 0;
/*  984 */     this.bytePointer = 0;
/*      */     
/*  986 */     int scanlineStride = (this.w + 7) / 8;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  995 */     int[] b = new int[2];
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1000 */     this.uncompressedMode = (int)((tiffT6Options & 0x2L) >> 1L);
/* 1001 */     this.fillBits = (int)((tiffT6Options & 0x4L) >> 2L);
/*      */ 
/*      */     
/* 1004 */     int[] cce = this.currChangingElems;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1009 */     this.changingElemSize = 0;
/* 1010 */     cce[this.changingElemSize++] = this.w;
/* 1011 */     cce[this.changingElemSize++] = this.w;
/*      */     
/* 1013 */     int lineOffset = 0;
/*      */ 
/*      */     
/* 1016 */     for (int lines = 0; lines < height; lines++) {
/*      */       
/* 1018 */       int a0 = -1;
/* 1019 */       boolean isWhite = true;
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1024 */       int[] temp = this.prevChangingElems;
/* 1025 */       this.prevChangingElems = this.currChangingElems;
/* 1026 */       cce = this.currChangingElems = temp;
/* 1027 */       int currIndex = 0;
/*      */ 
/*      */       
/* 1030 */       int bitOffset = startX;
/*      */       
/* 1032 */       if (this.fillBits == 1)
/*      */       {
/*      */         
/* 1035 */         if (this.bitPointer > 0) {
/* 1036 */           int bitsLeft = 8 - this.bitPointer;
/* 1037 */           if (nextNBits(bitsLeft) != 0) {
/* 1038 */             throw new IOException("Expected trailing zero bits for byte-aligned lines");
/*      */           }
/*      */         } 
/*      */       }
/*      */ 
/*      */       
/* 1044 */       this.lastChangingElement = 0;
/*      */ 
/*      */ 
/*      */       
/* 1048 */       while (bitOffset < this.w && this.bytePointer < this.data.length - 1) {
/*      */ 
/*      */         
/* 1051 */         getNextChangingElement(a0, isWhite, b);
/* 1052 */         int b1 = b[0];
/* 1053 */         int b2 = b[1];
/*      */ 
/*      */         
/* 1056 */         int entry = nextLesserThan8Bits(7);
/*      */ 
/*      */         
/* 1059 */         entry = twoDCodes[entry] & 0xFF;
/*      */ 
/*      */         
/* 1062 */         int code = (entry & 0x78) >>> 3;
/* 1063 */         int bits = entry & 0x7;
/* 1064 */         if (code == 0) {
/*      */ 
/*      */ 
/*      */           
/* 1068 */           if (!isWhite) {
/* 1069 */             setToBlack(buffer, lineOffset, bitOffset, b2 - bitOffset);
/*      */           }
/*      */           
/* 1072 */           bitOffset = a0 = b2;
/*      */ 
/*      */           
/* 1075 */           updatePointer(7 - bits);
/*      */           continue;
/*      */         } 
/* 1078 */         if (code == 1) {
/*      */ 
/*      */           
/* 1081 */           updatePointer(7 - bits);
/*      */ 
/*      */ 
/*      */           
/* 1085 */           if (isWhite) {
/*      */ 
/*      */             
/* 1088 */             int number = decodeWhiteCodeWord();
/* 1089 */             bitOffset += number;
/* 1090 */             cce[currIndex++] = bitOffset;
/*      */             
/* 1092 */             number = decodeBlackCodeWord();
/* 1093 */             setToBlack(buffer, lineOffset, bitOffset, number);
/* 1094 */             bitOffset += number;
/* 1095 */             cce[currIndex++] = bitOffset;
/*      */           }
/*      */           else {
/*      */             
/* 1099 */             int number = decodeBlackCodeWord();
/* 1100 */             setToBlack(buffer, lineOffset, bitOffset, number);
/* 1101 */             bitOffset += number;
/* 1102 */             cce[currIndex++] = bitOffset;
/*      */             
/* 1104 */             number = decodeWhiteCodeWord();
/* 1105 */             bitOffset += number;
/* 1106 */             cce[currIndex++] = bitOffset;
/*      */           } 
/*      */           
/* 1109 */           a0 = bitOffset;
/*      */           continue;
/*      */         } 
/* 1112 */         if (code <= 8) {
/* 1113 */           int a1 = b1 + code - 5;
/* 1114 */           cce[currIndex++] = a1;
/*      */ 
/*      */ 
/*      */           
/* 1118 */           if (!isWhite) {
/* 1119 */             setToBlack(buffer, lineOffset, bitOffset, a1 - bitOffset);
/*      */           }
/*      */           
/* 1122 */           bitOffset = a0 = a1;
/* 1123 */           isWhite = !isWhite;
/*      */           
/* 1125 */           updatePointer(7 - bits); continue;
/* 1126 */         }  if (code == 11) {
/* 1127 */           if (nextLesserThan8Bits(3) != 7) {
/* 1128 */             throw new IOException("Invalid code encountered while decoding 2D group 4 compressed data.");
/*      */           }
/*      */           
/* 1131 */           int zeros = 0;
/* 1132 */           boolean exit = false;
/*      */           
/* 1134 */           while (!exit) {
/* 1135 */             while (nextLesserThan8Bits(1) != 1) {
/* 1136 */               zeros++;
/*      */             }
/*      */             
/* 1139 */             if (zeros > 5) {
/*      */ 
/*      */ 
/*      */               
/* 1143 */               zeros -= 6;
/*      */               
/* 1145 */               if (!isWhite && zeros > 0) {
/* 1146 */                 cce[currIndex++] = bitOffset;
/*      */               }
/*      */ 
/*      */               
/* 1150 */               bitOffset += zeros;
/* 1151 */               if (zeros > 0)
/*      */               {
/* 1153 */                 isWhite = true;
/*      */               }
/*      */ 
/*      */ 
/*      */               
/* 1158 */               if (nextLesserThan8Bits(1) == 0) {
/* 1159 */                 if (!isWhite) {
/* 1160 */                   cce[currIndex++] = bitOffset;
/*      */                 }
/* 1162 */                 isWhite = true;
/*      */               } else {
/* 1164 */                 if (isWhite) {
/* 1165 */                   cce[currIndex++] = bitOffset;
/*      */                 }
/* 1167 */                 isWhite = false;
/*      */               } 
/*      */               
/* 1170 */               exit = true;
/*      */             } 
/*      */             
/* 1173 */             if (zeros == 5) {
/* 1174 */               if (!isWhite) {
/* 1175 */                 cce[currIndex++] = bitOffset;
/*      */               }
/* 1177 */               bitOffset += zeros;
/*      */ 
/*      */               
/* 1180 */               isWhite = true; continue;
/*      */             } 
/* 1182 */             bitOffset += zeros;
/*      */             
/* 1184 */             cce[currIndex++] = bitOffset;
/* 1185 */             setToBlack(buffer, lineOffset, bitOffset, 1);
/* 1186 */             bitOffset++;
/*      */ 
/*      */             
/* 1189 */             isWhite = false;
/*      */           } 
/*      */ 
/*      */ 
/*      */           
/*      */           continue;
/*      */         } 
/*      */ 
/*      */ 
/*      */         
/* 1199 */         bitOffset = this.w;
/* 1200 */         updatePointer(7 - bits);
/*      */       } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1209 */       if (currIndex < cce.length) {
/* 1210 */         cce[currIndex++] = bitOffset;
/*      */       }
/*      */       
/* 1213 */       this.changingElemSize = currIndex;
/*      */       
/* 1215 */       lineOffset += scanlineStride;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private void setToBlack(byte[] buffer, int lineOffset, int bitOffset, int numBits) {
/* 1222 */     int bitNum = 8 * lineOffset + bitOffset;
/* 1223 */     int lastBit = bitNum + numBits;
/*      */     
/* 1225 */     int byteNum = bitNum >> 3;
/*      */ 
/*      */     
/* 1228 */     int shift = bitNum & 0x7;
/* 1229 */     if (shift > 0) {
/* 1230 */       int maskVal = 1 << 7 - shift;
/* 1231 */       byte val = buffer[byteNum];
/* 1232 */       while (maskVal > 0 && bitNum < lastBit) {
/* 1233 */         val = (byte)(val | (byte)maskVal);
/* 1234 */         maskVal >>= 1;
/* 1235 */         bitNum++;
/*      */       } 
/* 1237 */       buffer[byteNum] = val;
/*      */     } 
/*      */ 
/*      */     
/* 1241 */     byteNum = bitNum >> 3;
/* 1242 */     while (bitNum < lastBit - 7) {
/* 1243 */       buffer[byteNum++] = -1;
/* 1244 */       bitNum += 8;
/*      */     } 
/*      */ 
/*      */     
/* 1248 */     while (bitNum < lastBit) {
/* 1249 */       byteNum = bitNum >> 3;
/* 1250 */       if (!this.recoverFromImageError || byteNum < buffer.length)
/*      */       {
/*      */         
/* 1253 */         buffer[byteNum] = (byte)(buffer[byteNum] | (byte)(1 << 7 - (bitNum & 0x7)));
/*      */       }
/* 1255 */       bitNum++;
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private int decodeWhiteCodeWord() {
/* 1261 */     int code = -1;
/* 1262 */     int runLength = 0;
/* 1263 */     boolean isWhite = true;
/*      */     
/* 1265 */     while (isWhite) {
/* 1266 */       int current = nextNBits(10);
/* 1267 */       int entry = white[current];
/*      */ 
/*      */       
/* 1270 */       int isT = entry & 0x1;
/* 1271 */       int bits = entry >>> 1 & 0xF;
/*      */ 
/*      */       
/* 1274 */       if (bits == 12) {
/*      */ 
/*      */         
/* 1277 */         int twoBits = nextLesserThan8Bits(2);
/*      */ 
/*      */         
/* 1280 */         current = current << 2 & 0xC | twoBits;
/* 1281 */         entry = additionalMakeup[current];
/*      */ 
/*      */         
/* 1284 */         bits = entry >>> 1 & 0x7;
/*      */ 
/*      */         
/* 1287 */         code = entry >>> 4 & 0xFFF;
/* 1288 */         runLength += code;
/* 1289 */         updatePointer(4 - bits);
/*      */         continue;
/*      */       } 
/* 1292 */       if (bits == 0) {
/* 1293 */         throw new IOException("Invalid code encountered.");
/*      */       }
/*      */       
/* 1296 */       if (bits == 15) {
/* 1297 */         if (runLength == 0) {
/* 1298 */           isWhite = false; continue;
/*      */         } 
/* 1300 */         throw new IOException("EOL code word encountered in White run.");
/*      */       } 
/*      */ 
/*      */ 
/*      */       
/* 1305 */       code = entry >>> 5 & 0x7FF;
/* 1306 */       runLength += code;
/* 1307 */       updatePointer(10 - bits);
/* 1308 */       if (isT == 0) {
/* 1309 */         isWhite = false;
/*      */       }
/*      */     } 
/*      */ 
/*      */     
/* 1314 */     return runLength;
/*      */   }
/*      */ 
/*      */   
/*      */   private int decodeBlackCodeWord() {
/* 1319 */     int code = -1;
/* 1320 */     int runLength = 0;
/* 1321 */     boolean isWhite = false;
/*      */     
/* 1323 */     while (!isWhite) {
/* 1324 */       int current = nextLesserThan8Bits(4);
/* 1325 */       int entry = initBlack[current];
/*      */ 
/*      */       
/* 1328 */       int isT = entry & 0x1;
/* 1329 */       int bits = entry >>> 1 & 0xF;
/* 1330 */       code = entry >>> 5 & 0x7FF;
/*      */       
/* 1332 */       if (code == 100) {
/* 1333 */         current = nextNBits(9);
/* 1334 */         entry = black[current];
/*      */ 
/*      */         
/* 1337 */         isT = entry & 0x1;
/* 1338 */         bits = entry >>> 1 & 0xF;
/* 1339 */         code = entry >>> 5 & 0x7FF;
/*      */         
/* 1341 */         if (bits == 12) {
/*      */ 
/*      */           
/* 1344 */           updatePointer(5);
/* 1345 */           current = nextLesserThan8Bits(4);
/* 1346 */           entry = additionalMakeup[current];
/*      */ 
/*      */           
/* 1349 */           bits = entry >>> 1 & 0x7;
/*      */ 
/*      */           
/* 1352 */           code = entry >>> 4 & 0xFFF;
/* 1353 */           runLength += code;
/*      */           
/* 1355 */           updatePointer(4 - bits); continue;
/* 1356 */         }  if (bits == 15)
/*      */         {
/*      */           
/* 1359 */           throw new IOException("EOL code word encountered in Black run.");
/*      */         }
/* 1361 */         runLength += code;
/* 1362 */         updatePointer(9 - bits);
/* 1363 */         if (isT == 0)
/* 1364 */           isWhite = true; 
/*      */         continue;
/*      */       } 
/* 1367 */       if (code == 200) {
/*      */ 
/*      */         
/* 1370 */         current = nextLesserThan8Bits(2);
/* 1371 */         entry = twoBitBlack[current];
/* 1372 */         code = entry >>> 5 & 0x7FF;
/* 1373 */         runLength += code;
/* 1374 */         bits = entry >>> 1 & 0xF;
/* 1375 */         updatePointer(2 - bits);
/* 1376 */         isWhite = true;
/*      */         
/*      */         continue;
/*      */       } 
/* 1380 */       runLength += code;
/* 1381 */       updatePointer(4 - bits);
/* 1382 */       isWhite = true;
/*      */     } 
/*      */ 
/*      */     
/* 1386 */     return runLength;
/*      */   }
/*      */   
/*      */   private int readEOL(boolean isFirstEOL) {
/* 1390 */     if (this.fillBits == 0) {
/* 1391 */       int next12Bits = nextNBits(12);
/* 1392 */       if (isFirstEOL && next12Bits == 0)
/*      */       {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1399 */         if (nextNBits(4) == 1) {
/*      */ 
/*      */ 
/*      */           
/* 1403 */           this.fillBits = 1;
/* 1404 */           return 1;
/*      */         } 
/*      */       }
/* 1407 */       if (next12Bits != 1) {
/* 1408 */         throw new IOException("Scanline must begin with EOL code word.");
/*      */       }
/* 1410 */     } else if (this.fillBits == 1) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1416 */       int bitsLeft = 8 - this.bitPointer;
/*      */       
/* 1418 */       if (nextNBits(bitsLeft) != 0) {
/* 1419 */         throw new IOException("All fill bits preceding eol code must be 0.");
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1426 */       if (bitsLeft < 4 && 
/* 1427 */         nextNBits(8) != 0) {
/* 1428 */         throw new IOException("All fill bits preceding eol code must be 0.");
/*      */       }
/*      */ 
/*      */ 
/*      */       
/*      */       int n;
/*      */ 
/*      */       
/* 1436 */       while ((n = nextNBits(8)) != 1) {
/*      */         
/* 1438 */         if (n != 0) {
/* 1439 */           throw new IOException("All fill bits preceding eol code must be 0.");
/*      */         }
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/* 1445 */     if (this.oneD == 0) {
/* 1446 */       return 1;
/*      */     }
/*      */ 
/*      */     
/* 1450 */     return nextLesserThan8Bits(1);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private void getNextChangingElement(int a0, boolean isWhite, int[] ret) {
/* 1456 */     int[] pce = this.prevChangingElems;
/* 1457 */     int ces = this.changingElemSize;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1462 */     int start = (this.lastChangingElement > 0) ? (this.lastChangingElement - 1) : 0;
/* 1463 */     if (isWhite) {
/*      */ 
/*      */       
/* 1466 */       start &= 0xFFFFFFFE;
/*      */     }
/*      */     else {
/*      */       
/* 1470 */       start |= 0x1;
/*      */     } 
/*      */     
/* 1473 */     int i = start;
/* 1474 */     for (; i < ces; i += 2) {
/* 1475 */       int temp = pce[i];
/* 1476 */       if (temp > a0) {
/* 1477 */         this.lastChangingElement = i;
/* 1478 */         ret[0] = temp;
/*      */         
/*      */         break;
/*      */       } 
/*      */     } 
/* 1483 */     if (i + 1 < ces) {
/* 1484 */       ret[1] = pce[i + 1];
/*      */     }
/*      */   }
/*      */   
/*      */   private int nextNBits(int bitsToGet) {
/*      */     byte b, next, next2next;
/* 1490 */     int l = this.data.length - 1;
/* 1491 */     int bp = this.bytePointer;
/*      */     
/* 1493 */     if (this.fillOrder == 1) {
/* 1494 */       b = this.data[bp];
/*      */       
/* 1496 */       if (bp == l) {
/* 1497 */         next = 0;
/* 1498 */         next2next = 0;
/* 1499 */       } else if (bp + 1 == l) {
/* 1500 */         next = this.data[bp + 1];
/* 1501 */         next2next = 0;
/*      */       } else {
/* 1503 */         next = this.data[bp + 1];
/* 1504 */         next2next = this.data[bp + 2];
/*      */       } 
/* 1506 */     } else if (this.fillOrder == 2) {
/* 1507 */       b = flipTable[this.data[bp] & 0xFF];
/*      */       
/* 1509 */       if (bp == l) {
/* 1510 */         next = 0;
/* 1511 */         next2next = 0;
/* 1512 */       } else if (bp + 1 == l) {
/* 1513 */         next = flipTable[this.data[bp + 1] & 0xFF];
/* 1514 */         next2next = 0;
/*      */       } else {
/* 1516 */         next = flipTable[this.data[bp + 1] & 0xFF];
/* 1517 */         next2next = flipTable[this.data[bp + 2] & 0xFF];
/*      */       } 
/*      */     } else {
/* 1520 */       throw new IOException("TIFF_FILL_ORDER tag must be either 1 or 2.");
/*      */     } 
/*      */     
/* 1523 */     int bitsLeft = 8 - this.bitPointer;
/* 1524 */     int bitsFromNextByte = bitsToGet - bitsLeft;
/* 1525 */     int bitsFromNext2NextByte = 0;
/* 1526 */     if (bitsFromNextByte > 8) {
/* 1527 */       bitsFromNext2NextByte = bitsFromNextByte - 8;
/* 1528 */       bitsFromNextByte = 8;
/*      */     } 
/*      */     
/* 1531 */     this.bytePointer++;
/*      */     
/* 1533 */     int i1 = (b & table1[bitsLeft]) << bitsToGet - bitsLeft;
/* 1534 */     int i2 = (next & table2[bitsFromNextByte]) >>> 8 - bitsFromNextByte;
/*      */ 
/*      */     
/* 1537 */     if (bitsFromNext2NextByte != 0) {
/* 1538 */       i2 <<= bitsFromNext2NextByte;
/* 1539 */       int i3 = (next2next & table2[bitsFromNext2NextByte]) >>> 8 - bitsFromNext2NextByte;
/*      */       
/* 1541 */       i2 |= i3;
/* 1542 */       this.bytePointer++;
/* 1543 */       this.bitPointer = bitsFromNext2NextByte;
/*      */     }
/* 1545 */     else if (bitsFromNextByte == 8) {
/* 1546 */       this.bitPointer = 0;
/* 1547 */       this.bytePointer++;
/*      */     } else {
/* 1549 */       this.bitPointer = bitsFromNextByte;
/*      */     } 
/*      */ 
/*      */     
/* 1553 */     return i1 | i2;
/*      */   }
/*      */   private int nextLesserThan8Bits(int bitsToGet) {
/*      */     int i1;
/* 1557 */     byte b = 0, next = 0;
/* 1558 */     int l = this.data.length - 1;
/* 1559 */     int bp = this.bytePointer;
/*      */     
/* 1561 */     if (this.fillOrder == 1) {
/* 1562 */       b = this.data[bp];
/* 1563 */       if (bp == l) {
/* 1564 */         next = 0;
/*      */       } else {
/* 1566 */         next = this.data[bp + 1];
/*      */       } 
/* 1568 */     } else if (this.fillOrder == 2) {
/* 1569 */       if (!this.recoverFromImageError || bp < this.data.length) {
/*      */ 
/*      */         
/* 1572 */         b = flipTable[this.data[bp] & 0xFF];
/* 1573 */         if (bp == l) {
/* 1574 */           next = 0;
/*      */         } else {
/* 1576 */           next = flipTable[this.data[bp + 1] & 0xFF];
/*      */         } 
/*      */       } 
/*      */     } else {
/* 1580 */       throw new IOException("TIFF_FILL_ORDER tag must be either 1 or 2.");
/*      */     } 
/*      */     
/* 1583 */     int bitsLeft = 8 - this.bitPointer;
/* 1584 */     int bitsFromNextByte = bitsToGet - bitsLeft;
/*      */     
/* 1586 */     int shift = bitsLeft - bitsToGet;
/*      */     
/* 1588 */     if (shift >= 0) {
/* 1589 */       i1 = (b & table1[bitsLeft]) >>> shift;
/* 1590 */       this.bitPointer += bitsToGet;
/* 1591 */       if (this.bitPointer == 8) {
/* 1592 */         this.bitPointer = 0;
/* 1593 */         this.bytePointer++;
/*      */       } 
/*      */     } else {
/* 1596 */       i1 = (b & table1[bitsLeft]) << -shift;
/* 1597 */       int i2 = (next & table2[bitsFromNextByte]) >>> 8 - bitsFromNextByte;
/*      */       
/* 1599 */       i1 |= i2;
/* 1600 */       this.bytePointer++;
/* 1601 */       this.bitPointer = bitsFromNextByte;
/*      */     } 
/*      */     
/* 1604 */     return i1;
/*      */   }
/*      */ 
/*      */   
/*      */   private void updatePointer(int bitsToMoveBack) {
/* 1609 */     int i = this.bitPointer - bitsToMoveBack;
/*      */     
/* 1611 */     if (i < 0) {
/* 1612 */       this.bytePointer--;
/* 1613 */       this.bitPointer = 8 + i;
/*      */     } else {
/* 1615 */       this.bitPointer = i;
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private boolean advancePointer() {
/* 1621 */     if (this.bitPointer != 0) {
/* 1622 */       this.bytePointer++;
/* 1623 */       this.bitPointer = 0;
/*      */     } 
/*      */     
/* 1626 */     return true;
/*      */   }
/*      */   
/*      */   public void setRecoverFromImageError(boolean recoverFromImageError) {
/* 1630 */     this.recoverFromImageError = recoverFromImageError;
/*      */   }
/*      */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/io/codec/TIFFFaxDecoder.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */