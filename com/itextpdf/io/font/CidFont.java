/*     */ package com.itextpdf.io.font;
/*     */ 
/*     */ import com.itextpdf.io.IOException;
/*     */ import com.itextpdf.io.font.cmap.CMapCidUni;
/*     */ import com.itextpdf.io.font.otf.Glyph;
/*     */ import com.itextpdf.io.util.IntHashtable;
/*     */ import java.util.Map;
/*     */ import java.util.Objects;
/*     */ import java.util.Set;
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
/*     */ public class CidFont
/*     */   extends FontProgram
/*     */ {
/*     */   private static final long serialVersionUID = 5444988003799502179L;
/*     */   private String fontName;
/*     */   private int pdfFontFlags;
/*     */   private Set<String> compatibleCmaps;
/*     */   
/*     */   CidFont(String fontName, Set<String> cmaps) {
/*  65 */     this.fontName = fontName;
/*  66 */     this.compatibleCmaps = cmaps;
/*  67 */     this.fontNames = new FontNames();
/*  68 */     initializeCidFontNameAndStyle(fontName);
/*  69 */     Map<String, Object> fontDesc = CidFontProperties.getAllFonts().get(this.fontNames.getFontName());
/*  70 */     if (fontDesc == null) {
/*  71 */       throw (new IOException("There is no such predefined font: {0}")).setMessageParams(new Object[] { fontName });
/*     */     }
/*  73 */     initializeCidFontProperties(fontDesc);
/*     */   }
/*     */   
/*     */   CidFont(String fontName, Set<String> cmaps, Map<String, Object> fontDescription) {
/*  77 */     initializeCidFontNameAndStyle(fontName);
/*  78 */     initializeCidFontProperties(fontDescription);
/*  79 */     this.compatibleCmaps = cmaps;
/*     */   }
/*     */   
/*     */   public boolean compatibleWith(String cmap) {
/*  83 */     if (cmap.equals("Identity-H") || cmap.equals("Identity-V")) {
/*  84 */       return true;
/*     */     }
/*  86 */     return (this.compatibleCmaps != null && this.compatibleCmaps.contains(cmap));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getKerning(Glyph glyph1, Glyph glyph2) {
/*  92 */     return 0;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getPdfFontFlags() {
/*  97 */     return this.pdfFontFlags;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isFontSpecific() {
/* 102 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isBuiltWith(String fontName) {
/* 107 */     return Objects.equals(this.fontName, fontName);
/*     */   }
/*     */   
/*     */   private void initializeCidFontNameAndStyle(String fontName) {
/* 111 */     String nameBase = trimFontStyle(fontName);
/* 112 */     if (nameBase.length() < fontName.length()) {
/* 113 */       this.fontNames.setFontName(fontName);
/* 114 */       this.fontNames.setStyle(fontName.substring(nameBase.length()));
/*     */     } else {
/* 116 */       this.fontNames.setFontName(fontName);
/*     */     } 
/* 118 */     this.fontNames.setFullName(new String[][] { { "", "", "", this.fontNames.getFontName() } });
/*     */   }
/*     */   
/*     */   private void initializeCidFontProperties(Map<String, Object> fontDesc) {
/* 122 */     this.fontIdentification.setPanose((String)fontDesc.get("Panose"));
/* 123 */     this.fontMetrics.setItalicAngle(Integer.parseInt((String)fontDesc.get("ItalicAngle")));
/* 124 */     this.fontMetrics.setCapHeight(Integer.parseInt((String)fontDesc.get("CapHeight")));
/* 125 */     this.fontMetrics.setTypoAscender(Integer.parseInt((String)fontDesc.get("Ascent")));
/* 126 */     this.fontMetrics.setTypoDescender(Integer.parseInt((String)fontDesc.get("Descent")));
/* 127 */     this.fontMetrics.setStemV(Integer.parseInt((String)fontDesc.get("StemV")));
/* 128 */     this.pdfFontFlags = Integer.parseInt((String)fontDesc.get("Flags"));
/* 129 */     String fontBBox = (String)fontDesc.get("FontBBox");
/* 130 */     StringTokenizer tk = new StringTokenizer(fontBBox, " []\r\n\t\f");
/* 131 */     int llx = Integer.parseInt(tk.nextToken());
/* 132 */     int lly = Integer.parseInt(tk.nextToken());
/* 133 */     int urx = Integer.parseInt(tk.nextToken());
/* 134 */     int ury = Integer.parseInt(tk.nextToken());
/* 135 */     this.fontMetrics.updateBbox(llx, lly, urx, ury);
/* 136 */     this.registry = (String)fontDesc.get("Registry");
/* 137 */     String uniMap = getCompatibleUniMap(this.registry);
/* 138 */     if (uniMap != null) {
/* 139 */       IntHashtable metrics = (IntHashtable)fontDesc.get("W");
/* 140 */       CMapCidUni cid2Uni = FontCache.getCid2UniCmap(uniMap);
/* 141 */       this.avgWidth = 0;
/* 142 */       for (int cid : cid2Uni.getCids()) {
/* 143 */         int uni = cid2Uni.lookup(cid);
/* 144 */         int width = metrics.containsKey(cid) ? metrics.get(cid) : 1000;
/* 145 */         Glyph glyph = new Glyph(cid, width, uni);
/* 146 */         this.avgWidth += glyph.getWidth();
/* 147 */         this.codeToGlyph.put(Integer.valueOf(cid), glyph);
/* 148 */         this.unicodeToGlyph.put(Integer.valueOf(uni), glyph);
/*     */       } 
/* 150 */       fixSpaceIssue();
/* 151 */       if (this.codeToGlyph.size() != 0) {
/* 152 */         this.avgWidth /= this.codeToGlyph.size();
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   private static String getCompatibleUniMap(String registry) {
/* 158 */     String uniMap = "";
/* 159 */     for (String name : CidFontProperties.getRegistryNames().get(registry + "_Uni")) {
/* 160 */       uniMap = name;
/* 161 */       if (name.endsWith("H")) {
/*     */         break;
/*     */       }
/*     */     } 
/* 165 */     return uniMap;
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/io/font/CidFont.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */