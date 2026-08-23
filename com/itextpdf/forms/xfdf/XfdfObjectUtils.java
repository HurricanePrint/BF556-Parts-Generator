/*     */ package com.itextpdf.forms.xfdf;
/*     */ 
/*     */ import com.itextpdf.io.source.ByteUtils;
/*     */ import com.itextpdf.kernel.colors.Color;
/*     */ import com.itextpdf.kernel.geom.Rectangle;
/*     */ import com.itextpdf.kernel.pdf.PdfArray;
/*     */ import com.itextpdf.kernel.pdf.annot.PdfAnnotation;
/*     */ import com.itextpdf.kernel.pdf.colorspace.PdfColorSpace;
/*     */ import com.itextpdf.kernel.pdf.colorspace.PdfDeviceCs;
/*     */ import java.nio.charset.StandardCharsets;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.StringTokenizer;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ final class XfdfObjectUtils
/*     */ {
/*     */   static Rectangle convertRectFromString(String rectString) {
/*  69 */     String delims = ",";
/*  70 */     StringTokenizer st = new StringTokenizer(rectString, delims);
/*  71 */     List<String> coordsList = new ArrayList<>();
/*     */     
/*  73 */     while (st.hasMoreTokens()) {
/*  74 */       coordsList.add(st.nextToken());
/*     */     }
/*     */     
/*  77 */     if (coordsList.size() == 2)
/*  78 */       return new Rectangle(Float.parseFloat(coordsList.get(0)), Float.parseFloat(coordsList.get(1))); 
/*  79 */     if (coordsList.size() == 4) {
/*  80 */       return new Rectangle(Float.parseFloat(coordsList.get(0)), Float.parseFloat(coordsList.get(1)), 
/*  81 */           Float.parseFloat(coordsList.get(2)), Float.parseFloat(coordsList.get(3)));
/*     */     }
/*     */     
/*  84 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static PdfArray convertFringeFromString(String fringeString) {
/*  92 */     String delims = ",";
/*  93 */     StringTokenizer st = new StringTokenizer(fringeString, delims);
/*  94 */     List<String> fringeList = new ArrayList<>();
/*     */     
/*  96 */     while (st.hasMoreTokens()) {
/*  97 */       fringeList.add(st.nextToken());
/*     */     }
/*  99 */     float[] fringe = new float[4];
/*     */     
/* 101 */     if (fringeList.size() == 4) {
/* 102 */       for (int i = 0; i < 4; i++) {
/* 103 */         fringe[i] = Float.parseFloat((String)fringeList.get(i));
/*     */       }
/*     */     }
/*     */     
/* 107 */     return new PdfArray(fringe);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static String convertRectToString(Rectangle rect) {
/* 114 */     return convertFloatToString(rect.getX()) + ", " + 
/* 115 */       convertFloatToString(rect.getY()) + ", " + 
/* 116 */       convertFloatToString(rect.getX() + rect.getWidth()) + ", " + 
/* 117 */       convertFloatToString(rect.getY() + rect.getHeight());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static String convertFloatToString(float coord) {
/* 124 */     return new String(ByteUtils.getIsoBytes(coord), StandardCharsets.UTF_8);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static float[] convertQuadPointsFromCoordsString(String coordsString) {
/* 132 */     String delims = ",";
/* 133 */     StringTokenizer st = new StringTokenizer(coordsString, delims);
/* 134 */     List<String> quadPointsList = new ArrayList<>();
/*     */     
/* 136 */     while (st.hasMoreTokens()) {
/* 137 */       quadPointsList.add(st.nextToken());
/*     */     }
/*     */     
/* 140 */     if (quadPointsList.size() == 8) {
/* 141 */       float[] quadPoints = new float[8];
/* 142 */       for (int i = 0; i < 8; i++) {
/* 143 */         quadPoints[i] = Float.parseFloat((String)quadPointsList.get(i));
/*     */       }
/* 145 */       return quadPoints;
/*     */     } 
/* 147 */     return new float[0];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static String convertQuadPointsToCoordsString(float[] quadPoints) {
/* 154 */     StringBuilder stb = new StringBuilder(floatToPaddedString(quadPoints[0]));
/*     */     
/* 156 */     for (int i = 1; i < 8; i++) {
/* 157 */       stb.append(", ").append(floatToPaddedString(quadPoints[i]));
/*     */     }
/* 159 */     return stb.toString();
/*     */   }
/*     */   
/*     */   private static String floatToPaddedString(float number) {
/* 163 */     return new String(ByteUtils.getIsoBytes(number), StandardCharsets.UTF_8);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static int convertFlagsFromString(String flagsString) {
/* 171 */     int result = 0;
/*     */     
/* 173 */     String delims = ",";
/* 174 */     StringTokenizer st = new StringTokenizer(flagsString, delims);
/* 175 */     List<String> flagsList = new ArrayList<>();
/* 176 */     while (st.hasMoreTokens()) {
/* 177 */       flagsList.add(st.nextToken().toLowerCase());
/*     */     }
/*     */     
/* 180 */     Map<String, Integer> flagMap = new HashMap<>();
/* 181 */     flagMap.put("invisible", Integer.valueOf(1));
/* 182 */     flagMap.put("hidden", Integer.valueOf(2));
/* 183 */     flagMap.put("print", Integer.valueOf(4));
/* 184 */     flagMap.put("nozoom", Integer.valueOf(8));
/* 185 */     flagMap.put("norotate", Integer.valueOf(16));
/* 186 */     flagMap.put("noview", Integer.valueOf(32));
/* 187 */     flagMap.put("readonly", Integer.valueOf(64));
/* 188 */     flagMap.put("locked", Integer.valueOf(128));
/* 189 */     flagMap.put("togglenoview", Integer.valueOf(256));
/*     */     
/* 191 */     for (String flag : flagsList) {
/* 192 */       if (flagMap.containsKey(flag))
/*     */       {
/* 194 */         result += ((Integer)flagMap.get(flag)).intValue();
/*     */       }
/*     */     } 
/* 197 */     return result;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static String convertFlagsToString(PdfAnnotation pdfAnnotation) {
/* 204 */     List<String> flagsList = new ArrayList<>();
/* 205 */     StringBuilder stb = new StringBuilder();
/*     */     
/* 207 */     if (pdfAnnotation.hasFlag(1)) {
/* 208 */       flagsList.add("invisible");
/*     */     }
/* 210 */     if (pdfAnnotation.hasFlag(2)) {
/* 211 */       flagsList.add("hidden");
/*     */     }
/* 213 */     if (pdfAnnotation.hasFlag(4)) {
/* 214 */       flagsList.add("print");
/*     */     }
/* 216 */     if (pdfAnnotation.hasFlag(8)) {
/* 217 */       flagsList.add("nozoom");
/*     */     }
/* 219 */     if (pdfAnnotation.hasFlag(16)) {
/* 220 */       flagsList.add("norotate");
/*     */     }
/* 222 */     if (pdfAnnotation.hasFlag(32)) {
/* 223 */       flagsList.add("noview");
/*     */     }
/* 225 */     if (pdfAnnotation.hasFlag(64)) {
/* 226 */       flagsList.add("readonly");
/*     */     }
/* 228 */     if (pdfAnnotation.hasFlag(128)) {
/* 229 */       flagsList.add("locked");
/*     */     }
/* 231 */     if (pdfAnnotation.hasFlag(256)) {
/* 232 */       flagsList.add("togglenoview");
/*     */     }
/*     */     
/* 235 */     for (String flag : flagsList) {
/* 236 */       stb.append(flag).append(",");
/*     */     }
/*     */     
/* 239 */     String result = stb.toString();
/* 240 */     return (result.length() > 0) ? result.substring(0, result.length() - 1) : null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static String convertColorToString(float[] colors) {
/* 247 */     if (colors.length == 3) {
/* 248 */       return "#" + convertColorFloatToHex(colors[0]) + convertColorFloatToHex(colors[1]) + convertColorFloatToHex(colors[2]);
/*     */     }
/* 250 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static String convertColorToString(Color color) {
/* 257 */     float[] colors = color.getColorValue();
/* 258 */     if (colors != null && colors.length == 3) {
/* 259 */       return "#" + convertColorFloatToHex(colors[0]) + convertColorFloatToHex(colors[1]) + convertColorFloatToHex(colors[2]);
/*     */     }
/* 261 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static String convertColorFloatToHex(float colorFloat) {
/* 268 */     String result = "0" + Integer.toHexString((int)((colorFloat * 255.0F) + 0.5D)).toUpperCase();
/* 269 */     return result.substring(result.length() - 2);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static String convertIdToHexString(String idString) {
/* 276 */     StringBuilder stb = new StringBuilder();
/* 277 */     char[] stringSymbols = idString.toCharArray();
/* 278 */     for (char ch : stringSymbols) {
/* 279 */       stb.append(Integer.toHexString(ch).toUpperCase());
/*     */     }
/* 281 */     return stb.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static Color convertColorFromString(String hexColor) {
/* 288 */     return Color.makeColor((PdfColorSpace)new PdfDeviceCs.Rgb(), convertColorFloatsFromString(hexColor));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static float[] convertColorFloatsFromString(String colorHexString) {
/* 295 */     float[] result = new float[3];
/* 296 */     String colorString = colorHexString.substring(colorHexString.indexOf('#') + 1);
/* 297 */     if (colorString.length() == 6) {
/* 298 */       for (int i = 0; i < 3; i++) {
/* 299 */         result[i] = Integer.parseInt(colorString.substring(i * 2, 2 + i * 2), 16);
/*     */       }
/*     */     }
/* 302 */     return result;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static String convertVerticesToString(float[] vertices) {
/* 309 */     if (vertices.length <= 0) {
/* 310 */       return null;
/*     */     }
/* 312 */     StringBuilder stb = new StringBuilder();
/* 313 */     stb.append(vertices[0]);
/* 314 */     for (int i = 1; i < vertices.length; i++) {
/* 315 */       stb.append(", ").append(vertices[i]);
/*     */     }
/* 317 */     return stb.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static String convertFringeToString(float[] fringeArray) {
/* 325 */     if (fringeArray.length != 4) {
/* 326 */       return null;
/*     */     }
/* 328 */     StringBuilder stb = new StringBuilder();
/* 329 */     stb.append(fringeArray[0]);
/* 330 */     for (int i = 1; i < 4; i++) {
/* 331 */       stb.append(", ").append(fringeArray[i]);
/*     */     }
/* 333 */     return stb.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static float[] convertVerticesFromString(String verticesString) {
/* 340 */     String delims = ",;";
/* 341 */     StringTokenizer st = new StringTokenizer(verticesString, delims);
/* 342 */     List<String> verticesList = new ArrayList<>();
/*     */     
/* 344 */     while (st.hasMoreTokens()) {
/* 345 */       verticesList.add(st.nextToken());
/*     */     }
/* 347 */     float[] vertices = new float[verticesList.size()];
/* 348 */     for (int i = 0; i < verticesList.size(); i++) {
/* 349 */       vertices[i] = Float.parseFloat((String)verticesList.get(i));
/*     */     }
/* 351 */     return vertices;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static String convertLineStartToString(float[] line) {
/* 360 */     if (line.length == 4) {
/* 361 */       return line[0] + "," + line[1];
/*     */     }
/* 363 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static String convertLineEndToString(float[] line) {
/* 372 */     if (line.length == 4) {
/* 373 */       return line[2] + "," + line[3];
/*     */     }
/* 375 */     return null;
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/forms/xfdf/XfdfObjectUtils.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */