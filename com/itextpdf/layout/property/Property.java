/*     */ package com.itextpdf.layout.property;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class Property
/*     */ {
/*     */   public static final int ACTION = 1;
/*     */   public static final int APPEARANCE_STREAM_LAYOUT = 82;
/*     */   public static final int AREA_BREAK_TYPE = 2;
/*     */   public static final int AUTO_SCALE = 3;
/*     */   public static final int AUTO_SCALE_HEIGHT = 4;
/*     */   public static final int AUTO_SCALE_WIDTH = 5;
/*     */   public static final int BACKGROUND = 6;
/*     */   public static final int BACKGROUND_IMAGE = 90;
/*     */   public static final int BASE_DIRECTION = 7;
/*     */   public static final int BOLD_SIMULATION = 8;
/*     */   public static final int BORDER = 9;
/*     */   public static final int BORDER_BOTTOM = 10;
/*     */   public static final int BORDER_BOTTOM_LEFT_RADIUS = 113;
/*     */   public static final int BORDER_BOTTOM_RIGHT_RADIUS = 112;
/*     */   public static final int BORDER_COLLAPSE = 114;
/*     */   public static final int BORDER_LEFT = 11;
/*     */   public static final int BORDER_RADIUS = 101;
/*     */   public static final int BORDER_RIGHT = 12;
/*     */   public static final int BORDER_TOP = 13;
/*     */   public static final int BORDER_TOP_LEFT_RADIUS = 110;
/*     */   public static final int BORDER_TOP_RIGHT_RADIUS = 111;
/*     */   public static final int BOTTOM = 14;
/*     */   public static final int BOX_SIZING = 105;
/*     */   public static final int CAPTION_SIDE = 119;
/*     */   public static final int CHARACTER_SPACING = 15;
/*     */   public static final int CLEAR = 100;
/*     */   public static final int COLLAPSING_MARGINS = 89;
/*     */   public static final int COLSPAN = 16;
/*     */   public static final int DESTINATION = 17;
/*     */   public static final int FILL_AVAILABLE_AREA = 86;
/*     */   public static final int FILL_AVAILABLE_AREA_ON_SPLIT = 87;
/*     */   public static final int FIRST_LINE_INDENT = 18;
/*     */   public static final int FLOAT = 99;
/*     */   public static final int FLUSH_ON_DRAW = 19;
/*     */   public static final int FONT = 20;
/*     */   public static final int FONT_COLOR = 21;
/*     */   public static final int FONT_KERNING = 22;
/*     */   public static final int FONT_STYLE = 94;
/*     */   public static final int FONT_WEIGHT = 95;
/*     */   public static final int FONT_SCRIPT = 23;
/*     */   public static final int FONT_PROVIDER = 91;
/*     */   public static final int FONT_SET = 98;
/*     */   public static final int FONT_SIZE = 24;
/*     */   public static final int FORCED_PLACEMENT = 26;
/*     */   public static final int FULL = 25;
/*     */   public static final int HEIGHT = 27;
/*     */   public static final int HORIZONTAL_ALIGNMENT = 28;
/*     */   public static final int HORIZONTAL_BORDER_SPACING = 115;
/*     */   public static final int HORIZONTAL_SCALING = 29;
/*     */   public static final int HYPHENATION = 30;
/*     */   public static final int IGNORE_FOOTER = 96;
/*     */   public static final int IGNORE_HEADER = 97;
/*     */   public static final int ITALIC_SIMULATION = 31;
/*     */   public static final int KEEP_TOGETHER = 32;
/*     */   public static final int KEEP_WITH_NEXT = 81;
/*     */   public static final int LEADING = 33;
/*     */   public static final int LEFT = 34;
/*     */   public static final int LINE_DRAWER = 35;
/*     */   public static final int LINE_HEIGHT = 124;
/*     */   public static final int LINK_ANNOTATION = 88;
/*     */   public static final int LIST_START = 36;
/*     */   public static final int LIST_SYMBOL = 37;
/*     */   public static final int LIST_SYMBOL_ALIGNMENT = 38;
/*     */   public static final int LIST_SYMBOL_INDENT = 39;
/*     */   public static final int LIST_SYMBOL_ORDINAL_VALUE = 120;
/*     */   public static final int LIST_SYMBOL_PRE_TEXT = 41;
/*     */   public static final int LIST_SYMBOL_POSITION = 83;
/*     */   public static final int LIST_SYMBOL_POST_TEXT = 42;
/*     */   public static final int LIST_SYMBOLS_INITIALIZED = 40;
/*     */   public static final int MARGIN_BOTTOM = 43;
/*     */   public static final int MARGIN_LEFT = 44;
/*     */   public static final int MARGIN_RIGHT = 45;
/*     */   public static final int MARGIN_TOP = 46;
/*     */   public static final int MAX_HEIGHT = 84;
/*     */   public static final int MAX_WIDTH = 79;
/*     */   public static final int MIN_HEIGHT = 85;
/*     */   public static final int MIN_WIDTH = 80;
/*     */   public static final int NO_SOFT_WRAP_INLINE = 118;
/*     */   public static final int OBJECT_FIT = 125;
/*     */   public static final int OPACITY = 92;
/*     */   public static final int ORPHANS_CONTROL = 121;
/*     */   public static final int OUTLINE = 106;
/*     */   public static final int OUTLINE_OFFSET = 107;
/*     */   @Deprecated
/*     */   public static final int OVERFLOW = 102;
/*     */   public static final int OVERFLOW_X = 103;
/*     */   public static final int OVERFLOW_Y = 104;
/*     */   public static final int PADDING_BOTTOM = 47;
/*     */   public static final int PADDING_LEFT = 48;
/*     */   public static final int PADDING_RIGHT = 49;
/*     */   public static final int PADDING_TOP = 50;
/*     */   public static final int PAGE_NUMBER = 51;
/*     */   public static final int POSITION = 52;
/*     */   public static final int RENDERING_MODE = 123;
/*     */   public static final int RIGHT = 54;
/*     */   public static final int ROTATION_ANGLE = 55;
/*     */   public static final int ROTATION_INITIAL_HEIGHT = 56;
/*     */   public static final int ROTATION_INITIAL_WIDTH = 57;
/*     */   public static final int ROTATION_POINT_X = 58;
/*     */   public static final int ROTATION_POINT_Y = 59;
/*     */   public static final int ROWSPAN = 60;
/*     */   public static final int SPACING_RATIO = 61;
/*     */   public static final int SPLIT_CHARACTERS = 62;
/*     */   public static final int STROKE_COLOR = 63;
/*     */   public static final int STROKE_WIDTH = 64;
/*     */   public static final int SKEW = 65;
/*     */   public static final int TABLE_LAYOUT = 93;
/*     */   public static final int TAB_ANCHOR = 66;
/*     */   public static final int TAB_DEFAULT = 67;
/*     */   public static final int TAB_LEADER = 68;
/*     */   public static final int TAB_STOPS = 69;
/*     */   public static final int TAGGING_HELPER = 108;
/*     */   public static final int TAGGING_HINT_KEY = 109;
/*     */   public static final int TEXT_ALIGNMENT = 70;
/*     */   public static final int TEXT_RENDERING_MODE = 71;
/*     */   public static final int TEXT_RISE = 72;
/*     */   public static final int TOP = 73;
/*     */   public static final int TRANSFORM = 53;
/*     */   public static final int TYPOGRAPHY_CONFIG = 117;
/*     */   public static final int UNDERLINE = 74;
/*     */   public static final int VERTICAL_ALIGNMENT = 75;
/*     */   public static final int VERTICAL_BORDER_SPACING = 116;
/*     */   public static final int VERTICAL_SCALING = 76;
/*     */   public static final int WIDOWS_CONTROL = 122;
/*     */   public static final int WIDTH = 77;
/*     */   public static final int WORD_SPACING = 78;
/* 228 */   private static final boolean[] INHERITED_PROPERTIES = new boolean[126];
/*     */   static {
/* 230 */     INHERITED_PROPERTIES[82] = true;
/* 231 */     INHERITED_PROPERTIES[7] = true;
/* 232 */     INHERITED_PROPERTIES[8] = true;
/* 233 */     INHERITED_PROPERTIES[119] = true;
/* 234 */     INHERITED_PROPERTIES[15] = true;
/* 235 */     INHERITED_PROPERTIES[89] = true;
/* 236 */     INHERITED_PROPERTIES[18] = true;
/* 237 */     INHERITED_PROPERTIES[20] = true;
/* 238 */     INHERITED_PROPERTIES[21] = true;
/* 239 */     INHERITED_PROPERTIES[22] = true;
/* 240 */     INHERITED_PROPERTIES[91] = true;
/* 241 */     INHERITED_PROPERTIES[98] = true;
/* 242 */     INHERITED_PROPERTIES[23] = true;
/* 243 */     INHERITED_PROPERTIES[24] = true;
/* 244 */     INHERITED_PROPERTIES[94] = true;
/* 245 */     INHERITED_PROPERTIES[95] = true;
/* 246 */     INHERITED_PROPERTIES[26] = true;
/* 247 */     INHERITED_PROPERTIES[30] = true;
/* 248 */     INHERITED_PROPERTIES[31] = true;
/* 249 */     INHERITED_PROPERTIES[32] = true;
/* 250 */     INHERITED_PROPERTIES[33] = true;
/* 251 */     INHERITED_PROPERTIES[118] = true;
/* 252 */     INHERITED_PROPERTIES[121] = true;
/* 253 */     INHERITED_PROPERTIES[61] = true;
/* 254 */     INHERITED_PROPERTIES[62] = true;
/* 255 */     INHERITED_PROPERTIES[63] = true;
/* 256 */     INHERITED_PROPERTIES[64] = true;
/* 257 */     INHERITED_PROPERTIES[70] = true;
/* 258 */     INHERITED_PROPERTIES[71] = true;
/* 259 */     INHERITED_PROPERTIES[72] = true;
/* 260 */     INHERITED_PROPERTIES[74] = true;
/* 261 */     INHERITED_PROPERTIES[122] = true;
/* 262 */     INHERITED_PROPERTIES[78] = true;
/* 263 */     INHERITED_PROPERTIES[108] = true;
/* 264 */     INHERITED_PROPERTIES[117] = true;
/* 265 */     INHERITED_PROPERTIES[123] = true;
/* 266 */     INHERITED_PROPERTIES[124] = true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static final int MAX_INHERITED_PROPERTY_ID = 125;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean isPropertyInherited(int property) {
/* 279 */     return (property >= 0 && property <= 125 && INHERITED_PROPERTIES[property]);
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/layout/property/Property.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */