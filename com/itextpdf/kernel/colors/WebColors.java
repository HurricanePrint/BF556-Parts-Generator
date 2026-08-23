/*     */ package com.itextpdf.kernel.colors;
/*     */ 
/*     */ import java.util.HashMap;
/*     */ import java.util.StringTokenizer;
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
/*     */ public class WebColors
/*     */   extends HashMap<String, int[]>
/*     */ {
/*     */   private static final long serialVersionUID = 3542523100813372896L;
/*  64 */   public static final WebColors NAMES = new WebColors();
/*     */   
/*     */   private static final double RGB_MAX_VAL = 255.0D;
/*     */   
/*     */   static {
/*  69 */     NAMES.put("aliceblue", new int[] { 240, 248, 255, 255 });
/*  70 */     NAMES.put("antiquewhite", new int[] { 250, 235, 215, 255 });
/*  71 */     NAMES.put("aqua", new int[] { 0, 255, 255, 255 });
/*  72 */     NAMES.put("aquamarine", new int[] { 127, 255, 212, 255 });
/*  73 */     NAMES.put("azure", new int[] { 240, 255, 255, 255 });
/*  74 */     NAMES.put("beige", new int[] { 245, 245, 220, 255 });
/*  75 */     NAMES.put("bisque", new int[] { 255, 228, 196, 255 });
/*  76 */     NAMES.put("black", new int[] { 0, 0, 0, 255 });
/*  77 */     NAMES.put("blanchedalmond", new int[] { 255, 235, 205, 255 });
/*  78 */     NAMES.put("blue", new int[] { 0, 0, 255, 255 });
/*  79 */     NAMES.put("blueviolet", new int[] { 138, 43, 226, 255 });
/*  80 */     NAMES.put("brown", new int[] { 165, 42, 42, 255 });
/*  81 */     NAMES.put("burlywood", new int[] { 222, 184, 135, 255 });
/*  82 */     NAMES.put("cadetblue", new int[] { 95, 158, 160, 255 });
/*  83 */     NAMES.put("chartreuse", new int[] { 127, 255, 0, 255 });
/*  84 */     NAMES.put("chocolate", new int[] { 210, 105, 30, 255 });
/*  85 */     NAMES.put("coral", new int[] { 255, 127, 80, 255 });
/*  86 */     NAMES.put("cornflowerblue", new int[] { 100, 149, 237, 255 });
/*  87 */     NAMES.put("cornsilk", new int[] { 255, 248, 220, 255 });
/*  88 */     NAMES.put("crimson", new int[] { 220, 20, 60, 255 });
/*  89 */     NAMES.put("cyan", new int[] { 0, 255, 255, 255 });
/*  90 */     NAMES.put("darkblue", new int[] { 0, 0, 139, 255 });
/*  91 */     NAMES.put("darkcyan", new int[] { 0, 139, 139, 255 });
/*  92 */     NAMES.put("darkgoldenrod", new int[] { 184, 134, 11, 255 });
/*  93 */     NAMES.put("darkgray", new int[] { 169, 169, 169, 255 });
/*  94 */     NAMES.put("darkgrey", new int[] { 169, 169, 169, 255 });
/*  95 */     NAMES.put("darkgreen", new int[] { 0, 100, 0, 255 });
/*  96 */     NAMES.put("darkkhaki", new int[] { 189, 183, 107, 255 });
/*  97 */     NAMES.put("darkmagenta", new int[] { 139, 0, 139, 255 });
/*  98 */     NAMES.put("darkolivegreen", new int[] { 85, 107, 47, 255 });
/*  99 */     NAMES.put("darkorange", new int[] { 255, 140, 0, 255 });
/* 100 */     NAMES.put("darkorchid", new int[] { 153, 50, 204, 255 });
/* 101 */     NAMES.put("darkred", new int[] { 139, 0, 0, 255 });
/* 102 */     NAMES.put("darksalmon", new int[] { 233, 150, 122, 255 });
/* 103 */     NAMES.put("darkseagreen", new int[] { 143, 188, 143, 255 });
/* 104 */     NAMES.put("darkslateblue", new int[] { 72, 61, 139, 255 });
/* 105 */     NAMES.put("darkslategray", new int[] { 47, 79, 79, 255 });
/* 106 */     NAMES.put("darkslategrey", new int[] { 47, 79, 79, 255 });
/* 107 */     NAMES.put("darkturquoise", new int[] { 0, 206, 209, 255 });
/* 108 */     NAMES.put("darkviolet", new int[] { 148, 0, 211, 255 });
/* 109 */     NAMES.put("deeppink", new int[] { 255, 20, 147, 255 });
/* 110 */     NAMES.put("deepskyblue", new int[] { 0, 191, 255, 255 });
/* 111 */     NAMES.put("dimgray", new int[] { 105, 105, 105, 255 });
/* 112 */     NAMES.put("dimgrey", new int[] { 105, 105, 105, 255 });
/* 113 */     NAMES.put("dodgerblue", new int[] { 30, 144, 255, 255 });
/* 114 */     NAMES.put("firebrick", new int[] { 178, 34, 34, 255 });
/* 115 */     NAMES.put("floralwhite", new int[] { 255, 250, 240, 255 });
/* 116 */     NAMES.put("forestgreen", new int[] { 34, 139, 34, 255 });
/* 117 */     NAMES.put("fuchsia", new int[] { 255, 0, 255, 255 });
/* 118 */     NAMES.put("gainsboro", new int[] { 220, 220, 220, 255 });
/* 119 */     NAMES.put("ghostwhite", new int[] { 248, 248, 255, 255 });
/* 120 */     NAMES.put("gold", new int[] { 255, 215, 0, 255 });
/* 121 */     NAMES.put("goldenrod", new int[] { 218, 165, 32, 255 });
/* 122 */     NAMES.put("gray", new int[] { 128, 128, 128, 255 });
/* 123 */     NAMES.put("grey", new int[] { 128, 128, 128, 255 });
/* 124 */     NAMES.put("green", new int[] { 0, 128, 0, 255 });
/* 125 */     NAMES.put("greenyellow", new int[] { 173, 255, 47, 255 });
/* 126 */     NAMES.put("honeydew", new int[] { 240, 255, 240, 255 });
/* 127 */     NAMES.put("hotpink", new int[] { 255, 105, 180, 255 });
/* 128 */     NAMES.put("indianred", new int[] { 205, 92, 92, 255 });
/* 129 */     NAMES.put("indigo", new int[] { 75, 0, 130, 255 });
/* 130 */     NAMES.put("ivory", new int[] { 255, 255, 240, 255 });
/* 131 */     NAMES.put("khaki", new int[] { 240, 230, 140, 255 });
/* 132 */     NAMES.put("lavender", new int[] { 230, 230, 250, 255 });
/* 133 */     NAMES.put("lavenderblush", new int[] { 255, 240, 245, 255 });
/* 134 */     NAMES.put("lawngreen", new int[] { 124, 252, 0, 255 });
/* 135 */     NAMES.put("lemonchiffon", new int[] { 255, 250, 205, 255 });
/* 136 */     NAMES.put("lightblue", new int[] { 173, 216, 230, 255 });
/* 137 */     NAMES.put("lightcoral", new int[] { 240, 128, 128, 255 });
/* 138 */     NAMES.put("lightcyan", new int[] { 224, 255, 255, 255 });
/* 139 */     NAMES.put("lightgoldenrodyellow", new int[] { 250, 250, 210, 255 });
/* 140 */     NAMES.put("lightgreen", new int[] { 144, 238, 144, 255 });
/* 141 */     NAMES.put("lightgray", new int[] { 211, 211, 211, 255 });
/* 142 */     NAMES.put("lightgrey", new int[] { 211, 211, 211, 255 });
/* 143 */     NAMES.put("lightpink", new int[] { 255, 182, 193, 255 });
/* 144 */     NAMES.put("lightsalmon", new int[] { 255, 160, 122, 255 });
/* 145 */     NAMES.put("lightseagreen", new int[] { 32, 178, 170, 255 });
/* 146 */     NAMES.put("lightskyblue", new int[] { 135, 206, 250, 255 });
/* 147 */     NAMES.put("lightslategray", new int[] { 119, 136, 153, 255 });
/* 148 */     NAMES.put("lightslategrey", new int[] { 119, 136, 153, 255 });
/* 149 */     NAMES.put("lightsteelblue", new int[] { 176, 196, 222, 255 });
/* 150 */     NAMES.put("lightyellow", new int[] { 255, 255, 224, 255 });
/* 151 */     NAMES.put("lime", new int[] { 0, 255, 0, 255 });
/* 152 */     NAMES.put("limegreen", new int[] { 50, 205, 50, 255 });
/* 153 */     NAMES.put("linen", new int[] { 250, 240, 230, 255 });
/* 154 */     NAMES.put("magenta", new int[] { 255, 0, 255, 255 });
/* 155 */     NAMES.put("maroon", new int[] { 128, 0, 0, 255 });
/* 156 */     NAMES.put("mediumaquamarine", new int[] { 102, 205, 170, 255 });
/* 157 */     NAMES.put("mediumblue", new int[] { 0, 0, 205, 255 });
/* 158 */     NAMES.put("mediumorchid", new int[] { 186, 85, 211, 255 });
/* 159 */     NAMES.put("mediumpurple", new int[] { 147, 112, 219, 255 });
/* 160 */     NAMES.put("mediumseagreen", new int[] { 60, 179, 113, 255 });
/* 161 */     NAMES.put("mediumslateblue", new int[] { 123, 104, 238, 255 });
/* 162 */     NAMES.put("mediumspringgreen", new int[] { 0, 250, 154, 255 });
/* 163 */     NAMES.put("mediumturquoise", new int[] { 72, 209, 204, 255 });
/* 164 */     NAMES.put("mediumvioletred", new int[] { 199, 21, 133, 255 });
/* 165 */     NAMES.put("midnightblue", new int[] { 25, 25, 112, 255 });
/* 166 */     NAMES.put("mintcream", new int[] { 245, 255, 250, 255 });
/* 167 */     NAMES.put("mistyrose", new int[] { 255, 228, 225, 255 });
/* 168 */     NAMES.put("moccasin", new int[] { 255, 228, 181, 255 });
/* 169 */     NAMES.put("navajowhite", new int[] { 255, 222, 173, 255 });
/* 170 */     NAMES.put("navy", new int[] { 0, 0, 128, 255 });
/* 171 */     NAMES.put("oldlace", new int[] { 253, 245, 230, 255 });
/* 172 */     NAMES.put("olive", new int[] { 128, 128, 0, 255 });
/* 173 */     NAMES.put("olivedrab", new int[] { 107, 142, 35, 255 });
/* 174 */     NAMES.put("orange", new int[] { 255, 165, 0, 255 });
/* 175 */     NAMES.put("orangered", new int[] { 255, 69, 0, 255 });
/* 176 */     NAMES.put("orchid", new int[] { 218, 112, 214, 255 });
/* 177 */     NAMES.put("palegoldenrod", new int[] { 238, 232, 170, 255 });
/* 178 */     NAMES.put("palegreen", new int[] { 152, 251, 152, 255 });
/* 179 */     NAMES.put("paleturquoise", new int[] { 175, 238, 238, 255 });
/* 180 */     NAMES.put("palevioletred", new int[] { 219, 112, 147, 255 });
/* 181 */     NAMES.put("papayawhip", new int[] { 255, 239, 213, 255 });
/* 182 */     NAMES.put("peachpuff", new int[] { 255, 218, 185, 255 });
/* 183 */     NAMES.put("peru", new int[] { 205, 133, 63, 255 });
/* 184 */     NAMES.put("pink", new int[] { 255, 192, 203, 255 });
/* 185 */     NAMES.put("plum", new int[] { 221, 160, 221, 255 });
/* 186 */     NAMES.put("powderblue", new int[] { 176, 224, 230, 255 });
/* 187 */     NAMES.put("purple", new int[] { 128, 0, 128, 255 });
/* 188 */     NAMES.put("red", new int[] { 255, 0, 0, 255 });
/* 189 */     NAMES.put("rosybrown", new int[] { 188, 143, 143, 255 });
/* 190 */     NAMES.put("royalblue", new int[] { 65, 105, 225, 255 });
/* 191 */     NAMES.put("saddlebrown", new int[] { 139, 69, 19, 255 });
/* 192 */     NAMES.put("salmon", new int[] { 250, 128, 114, 255 });
/* 193 */     NAMES.put("sandybrown", new int[] { 244, 164, 96, 255 });
/* 194 */     NAMES.put("seagreen", new int[] { 46, 139, 87, 255 });
/* 195 */     NAMES.put("seashell", new int[] { 255, 245, 238, 255 });
/* 196 */     NAMES.put("sienna", new int[] { 160, 82, 45, 255 });
/* 197 */     NAMES.put("silver", new int[] { 192, 192, 192, 255 });
/* 198 */     NAMES.put("skyblue", new int[] { 135, 206, 235, 255 });
/* 199 */     NAMES.put("slateblue", new int[] { 106, 90, 205, 255 });
/* 200 */     NAMES.put("slategray", new int[] { 112, 128, 144, 255 });
/* 201 */     NAMES.put("slategrey", new int[] { 112, 128, 144, 255 });
/* 202 */     NAMES.put("snow", new int[] { 255, 250, 250, 255 });
/* 203 */     NAMES.put("springgreen", new int[] { 0, 255, 127, 255 });
/* 204 */     NAMES.put("steelblue", new int[] { 70, 130, 180, 255 });
/* 205 */     NAMES.put("tan", new int[] { 210, 180, 140, 255 });
/* 206 */     NAMES.put("teal", new int[] { 0, 128, 128, 255 });
/* 207 */     NAMES.put("thistle", new int[] { 216, 191, 216, 255 });
/* 208 */     NAMES.put("tomato", new int[] { 255, 99, 71, 255 });
/* 209 */     NAMES.put("transparent", new int[] { 255, 255, 255, 0 });
/* 210 */     NAMES.put("turquoise", new int[] { 64, 224, 208, 255 });
/* 211 */     NAMES.put("violet", new int[] { 238, 130, 238, 255 });
/* 212 */     NAMES.put("wheat", new int[] { 245, 222, 179, 255 });
/* 213 */     NAMES.put("white", new int[] { 255, 255, 255, 255 });
/* 214 */     NAMES.put("whitesmoke", new int[] { 245, 245, 245, 255 });
/* 215 */     NAMES.put("yellow", new int[] { 255, 255, 0, 255 });
/* 216 */     NAMES.put("yellowgreen", new int[] { 154, 205, 50, 255 });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static DeviceRgb getRGBColor(String name) {
/* 227 */     float[] rgbaColor = getRGBAColor(name);
/* 228 */     if (rgbaColor == null) {
/* 229 */       return new DeviceRgb(0, 0, 0);
/*     */     }
/* 231 */     return new DeviceRgb(rgbaColor[0], rgbaColor[1], rgbaColor[2]);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static float[] getRGBAColor(String name) {
/* 242 */     float[] color = null;
/*     */     try {
/* 244 */       String colorName = name.toLowerCase();
/* 245 */       boolean colorStrWithoutHash = missingHashColorFormat(colorName);
/* 246 */       if (colorName.startsWith("#") || colorStrWithoutHash) {
/* 247 */         if (!colorStrWithoutHash)
/*     */         {
/* 249 */           colorName = colorName.substring(1);
/*     */         }
/* 251 */         if (colorName.length() == 3) {
/* 252 */           String red = colorName.substring(0, 1);
/* 253 */           color = new float[] { 0.0F, 0.0F, 0.0F, 1.0F };
/* 254 */           color[0] = (float)(Integer.parseInt(red + red, 16) / 255.0D);
/* 255 */           String green = colorName.substring(1, 2);
/* 256 */           color[1] = (float)(Integer.parseInt(green + green, 16) / 255.0D);
/* 257 */           String blue = colorName.substring(2);
/* 258 */           color[2] = (float)(Integer.parseInt(blue + blue, 16) / 255.0D);
/* 259 */         } else if (colorName.length() == 6) {
/* 260 */           color = new float[] { 0.0F, 0.0F, 0.0F, 1.0F };
/* 261 */           color[0] = (float)(Integer.parseInt(colorName.substring(0, 2), 16) / 255.0D);
/* 262 */           color[1] = (float)(Integer.parseInt(colorName.substring(2, 4), 16) / 255.0D);
/* 263 */           color[2] = (float)(Integer.parseInt(colorName.substring(4), 16) / 255.0D);
/*     */         } else {
/* 265 */           Logger logger = LoggerFactory.getLogger(WebColors.class);
/* 266 */           logger.error("Unknown color format: must be rgb or rrggbb.");
/*     */         } 
/* 268 */       } else if (colorName.startsWith("rgb(")) {
/* 269 */         String delim = "rgb(), \t\r\n\f";
/* 270 */         StringTokenizer tok = new StringTokenizer(colorName, "rgb(), \t\r\n\f");
/* 271 */         color = new float[] { 0.0F, 0.0F, 0.0F, 1.0F };
/* 272 */         parseRGBColors(color, tok);
/* 273 */       } else if (colorName.startsWith("rgba(")) {
/* 274 */         String delim = "rgba(), \t\r\n\f";
/* 275 */         StringTokenizer tok = new StringTokenizer(colorName, "rgba(), \t\r\n\f");
/* 276 */         color = new float[] { 0.0F, 0.0F, 0.0F, 1.0F };
/* 277 */         parseRGBColors(color, tok);
/* 278 */         if (tok.hasMoreTokens()) {
/* 279 */           color[3] = getAlphaChannelValue(tok.nextToken());
/*     */         }
/* 281 */       } else if (NAMES.containsKey(colorName)) {
/* 282 */         int[] intColor = NAMES.get(colorName);
/* 283 */         color = new float[] { 0.0F, 0.0F, 0.0F, 1.0F };
/* 284 */         color[0] = (float)(intColor[0] / 255.0D);
/* 285 */         color[1] = (float)(intColor[1] / 255.0D);
/* 286 */         color[2] = (float)(intColor[2] / 255.0D);
/*     */       } 
/* 288 */     } catch (Exception exc) {
/*     */       
/* 290 */       color = null;
/*     */     } 
/*     */     
/* 293 */     return color;
/*     */   }
/*     */   
/*     */   private static void parseRGBColors(float[] color, StringTokenizer tok) {
/* 297 */     for (int k = 0; k < 3; k++) {
/* 298 */       if (tok.hasMoreTokens()) {
/* 299 */         color[k] = getRGBChannelValue(tok.nextToken());
/* 300 */         color[k] = Math.max(0.0F, color[k]);
/* 301 */         color[k] = Math.min(1.0F, color[k]);
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
/*     */   private static boolean missingHashColorFormat(String colStr) {
/* 316 */     int len = colStr.length();
/* 317 */     if (len == 3 || len == 6) {
/*     */       
/* 319 */       String match = "[0-9a-f]{" + len + "}";
/* 320 */       return colStr.matches(match);
/*     */     } 
/* 322 */     return false;
/*     */   }
/*     */   
/*     */   private static float getRGBChannelValue(String rgbChannel) {
/* 326 */     if (rgbChannel.endsWith("%")) {
/* 327 */       return parsePercentValue(rgbChannel);
/*     */     }
/* 329 */     return (float)(Integer.parseInt(rgbChannel) / 255.0D);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static float getAlphaChannelValue(String rgbChannel) {
/* 336 */     if (rgbChannel.endsWith("%")) {
/* 337 */       alpha = parsePercentValue(rgbChannel);
/*     */     } else {
/* 339 */       alpha = Float.parseFloat(rgbChannel);
/*     */     } 
/* 341 */     float alpha = Math.max(0.0F, alpha);
/* 342 */     alpha = Math.min(1.0F, alpha);
/* 343 */     return alpha;
/*     */   }
/*     */   
/*     */   private static float parsePercentValue(String rgbChannel) {
/* 347 */     return (float)(Float.parseFloat(rgbChannel.substring(0, rgbChannel
/* 348 */           .length() - 1)) / 100.0D);
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/kernel/colors/WebColors.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */