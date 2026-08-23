/*     */ package com.itextpdf.kernel.pdf.canvas.wmf;
/*     */ 
/*     */ import com.itextpdf.io.font.FontEncoding;
/*     */ import com.itextpdf.io.font.FontProgram;
/*     */ import com.itextpdf.io.font.FontProgramFactory;
/*     */ import java.io.IOException;
/*     */ import java.io.UnsupportedEncodingException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class MetaFont
/*     */   extends MetaObject
/*     */ {
/*  59 */   static final String[] fontNames = new String[] { "Courier", "Courier-Bold", "Courier-Oblique", "Courier-BoldOblique", "Helvetica", "Helvetica-Bold", "Helvetica-Oblique", "Helvetica-BoldOblique", "Times-Roman", "Times-Bold", "Times-Italic", "Times-BoldItalic", "Symbol", "ZapfDingbats" };
/*     */   
/*     */   static final int MARKER_BOLD = 1;
/*     */   
/*     */   static final int MARKER_ITALIC = 2;
/*     */   
/*     */   static final int MARKER_COURIER = 0;
/*     */   
/*     */   static final int MARKER_HELVETICA = 4;
/*     */   
/*     */   static final int MARKER_TIMES = 8;
/*     */   
/*     */   static final int MARKER_SYMBOL = 12;
/*     */   
/*     */   static final int DEFAULT_PITCH = 0;
/*     */   static final int FIXED_PITCH = 1;
/*     */   static final int VARIABLE_PITCH = 2;
/*     */   static final int FF_DONTCARE = 0;
/*     */   static final int FF_ROMAN = 1;
/*     */   static final int FF_SWISS = 2;
/*     */   static final int FF_MODERN = 3;
/*     */   static final int FF_SCRIPT = 4;
/*     */   static final int FF_DECORATIVE = 5;
/*     */   static final int BOLDTHRESHOLD = 600;
/*     */   static final int NAME_SIZE = 32;
/*     */   static final int ETO_OPAQUE = 2;
/*     */   static final int ETO_CLIPPED = 4;
/*     */   int height;
/*     */   float angle;
/*     */   int bold;
/*     */   int italic;
/*     */   boolean underline;
/*     */   boolean strikeout;
/*     */   int charset;
/*     */   int pitchAndFamily;
/*  94 */   String faceName = "arial";
/*  95 */   FontProgram font = null;
/*  96 */   FontEncoding encoding = null;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public MetaFont() {
/* 102 */     super(3);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void init(InputMeta in) throws IOException {
/* 112 */     this.height = Math.abs(in.readShort());
/* 113 */     in.skip(2);
/* 114 */     this.angle = (float)(in.readShort() / 1800.0D * Math.PI);
/* 115 */     in.skip(2);
/* 116 */     this.bold = (in.readShort() >= 600) ? 1 : 0;
/* 117 */     this.italic = (in.readByte() != 0) ? 2 : 0;
/* 118 */     this.underline = (in.readByte() != 0);
/* 119 */     this.strikeout = (in.readByte() != 0);
/* 120 */     this.charset = in.readByte();
/* 121 */     in.skip(3);
/* 122 */     this.pitchAndFamily = in.readByte();
/* 123 */     byte[] name = new byte[32];
/*     */     int k;
/* 125 */     for (k = 0; k < 32; k++) {
/* 126 */       int c = in.readByte();
/* 127 */       if (c == 0) {
/*     */         break;
/*     */       }
/* 130 */       name[k] = (byte)c;
/*     */     } 
/*     */     try {
/* 133 */       this.faceName = new String(name, 0, k, "Cp1252");
/* 134 */     } catch (UnsupportedEncodingException e) {
/* 135 */       this.faceName = new String(name, 0, k);
/*     */     } 
/* 137 */     this.faceName = this.faceName.toLowerCase();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public FontProgram getFont() throws IOException {
/*     */     String fontName;
/* 147 */     if (this.font != null)
/* 148 */       return this.font; 
/* 149 */     FontProgram ff2 = FontProgramFactory.createRegisteredFont(this.faceName, ((this.italic != 0) ? 2 : 0) | ((this.bold != 0) ? 1 : 0));
/* 150 */     this.encoding = FontEncoding.createFontEncoding("Cp1252");
/* 151 */     this.font = ff2;
/* 152 */     if (this.font != null) {
/* 153 */       return this.font;
/*     */     }
/* 155 */     if (this.faceName.contains("courier") || this.faceName.contains("terminal") || this.faceName
/* 156 */       .contains("fixedsys")) {
/* 157 */       fontName = fontNames[0 + this.italic + this.bold];
/*     */     }
/* 159 */     else if (this.faceName.contains("ms sans serif") || this.faceName.contains("arial") || this.faceName
/* 160 */       .contains("system")) {
/* 161 */       fontName = fontNames[4 + this.italic + this.bold];
/*     */     }
/* 163 */     else if (this.faceName.contains("arial black")) {
/* 164 */       fontName = fontNames[4 + this.italic + 1];
/*     */     }
/* 166 */     else if (this.faceName.contains("times") || this.faceName.contains("ms serif") || this.faceName
/* 167 */       .contains("roman")) {
/* 168 */       fontName = fontNames[8 + this.italic + this.bold];
/*     */     }
/* 170 */     else if (this.faceName.contains("symbol")) {
/* 171 */       fontName = fontNames[12];
/*     */     } else {
/*     */       
/* 174 */       int pitch = this.pitchAndFamily & 0x3;
/* 175 */       int family = this.pitchAndFamily >> 4 & 0x7;
/* 176 */       switch (family) {
/*     */         case 3:
/* 178 */           fontName = fontNames[0 + this.italic + this.bold];
/*     */           break;
/*     */         case 1:
/* 181 */           fontName = fontNames[8 + this.italic + this.bold];
/*     */           break;
/*     */         case 2:
/*     */         case 4:
/*     */         case 5:
/* 186 */           fontName = fontNames[4 + this.italic + this.bold];
/*     */           break;
/*     */         
/*     */         default:
/* 190 */           switch (pitch) {
/*     */             case 1:
/* 192 */               fontName = fontNames[0 + this.italic + this.bold];
/*     */               break;
/*     */           } 
/* 195 */           fontName = fontNames[4 + this.italic + this.bold];
/*     */           break;
/*     */       } 
/*     */ 
/*     */     
/*     */     } 
/*     */     try {
/* 202 */       this.font = FontProgramFactory.createFont(fontName);
/* 203 */       this.encoding = FontEncoding.createFontEncoding("Cp1252");
/*     */     }
/* 205 */     catch (IOException e) {
/* 206 */       throw new RuntimeException(e.getMessage(), e);
/*     */     } 
/*     */     
/* 209 */     return this.font;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public FontEncoding getEncoding() {
/* 218 */     return this.encoding;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getAngle() {
/* 227 */     return this.angle;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isUnderline() {
/* 236 */     return this.underline;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isStrikeout() {
/* 245 */     return this.strikeout;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getFontSize(MetaState state) {
/* 255 */     return Math.abs(state.transformY(this.height) - state.transformY(0)) * WmfImageHelper.wmfFontCorrection;
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/kernel/pdf/canvas/wmf/MetaFont.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */