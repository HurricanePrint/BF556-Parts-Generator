/*     */ package com.itextpdf.kernel.font;
/*     */ 
/*     */ import com.itextpdf.io.font.FontEncoding;
/*     */ import com.itextpdf.io.font.FontNames;
/*     */ import com.itextpdf.io.font.FontProgram;
/*     */ import com.itextpdf.io.font.FontProgramFactory;
/*     */ import com.itextpdf.io.font.TrueTypeFont;
/*     */ import com.itextpdf.io.font.Type1Font;
/*     */ import com.itextpdf.io.font.constants.StandardFonts;
/*     */ import com.itextpdf.io.font.otf.Glyph;
/*     */ import com.itextpdf.kernel.PdfException;
/*     */ import com.itextpdf.kernel.pdf.PdfDictionary;
/*     */ import com.itextpdf.kernel.pdf.PdfName;
/*     */ import com.itextpdf.kernel.pdf.PdfObject;
/*     */ import com.itextpdf.kernel.pdf.PdfStream;
/*     */ import java.io.IOException;
/*     */ import java.util.Set;
/*     */ import java.util.SortedSet;
/*     */ import java.util.TreeSet;
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
/*     */ public class PdfTrueTypeFont
/*     */   extends PdfSimpleFont<TrueTypeFont>
/*     */ {
/*     */   private static final long serialVersionUID = -8152778382960290571L;
/*     */   
/*     */   PdfTrueTypeFont(TrueTypeFont ttf, String encoding, boolean embedded) {
/*  75 */     setFontProgram(ttf);
/*  76 */     this.embedded = embedded;
/*  77 */     FontNames fontNames = ttf.getFontNames();
/*  78 */     if (embedded && !fontNames.allowEmbedding()) {
/*  79 */       throw (new PdfException("{0} cannot be embedded due to licensing restrictions."))
/*  80 */         .setMessageParams(new Object[] { fontNames.getFontName() });
/*     */     }
/*  82 */     if ((encoding == null || encoding.length() == 0) && ttf.isFontSpecific()) {
/*  83 */       encoding = "FontSpecific";
/*     */     }
/*  85 */     if (encoding != null && "FontSpecific".toLowerCase().equals(encoding.toLowerCase())) {
/*  86 */       this.fontEncoding = FontEncoding.createFontSpecificEncoding();
/*     */     } else {
/*  88 */       this.fontEncoding = FontEncoding.createFontEncoding(encoding);
/*     */     } 
/*     */   }
/*     */   
/*     */   PdfTrueTypeFont(PdfDictionary fontDictionary) {
/*  93 */     super(fontDictionary);
/*  94 */     this.newFont = false;
/*  95 */     this.subset = false;
/*  96 */     this.fontEncoding = DocFontEncoding.createDocFontEncoding(fontDictionary.get(PdfName.Encoding), this.toUnicode);
/*     */     
/*  98 */     PdfName baseFontName = fontDictionary.getAsName(PdfName.BaseFont);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 105 */     if (baseFontName != null && StandardFonts.isStandardFont(baseFontName.getValue()) && 
/* 106 */       !fontDictionary.containsKey(PdfName.FontDescriptor) && !fontDictionary.containsKey(PdfName.Widths)) {
/*     */       try {
/* 108 */         this.fontProgram = FontProgramFactory.createFont(baseFontName.getValue(), true);
/* 109 */       } catch (IOException e) {
/* 110 */         throw new PdfException("I/O exception while creating Font", e);
/*     */       } 
/*     */     } else {
/* 113 */       this.fontProgram = (FontProgram)DocTrueTypeFont.createFontProgram(fontDictionary, this.fontEncoding, this.toUnicode);
/*     */     } 
/*     */     
/* 116 */     this.embedded = (this.fontProgram instanceof IDocFontProgram && ((IDocFontProgram)this.fontProgram).getFontFile() != null);
/*     */   }
/*     */ 
/*     */   
/*     */   public Glyph getGlyph(int unicode) {
/* 121 */     if (this.fontEncoding.canEncode(unicode)) {
/* 122 */       Glyph glyph = getFontProgram().getGlyph(this.fontEncoding.getUnicodeDifference(unicode));
/*     */       
/* 124 */       if (glyph == null && (glyph = this.notdefGlyphs.get(Integer.valueOf(unicode))) == null) {
/* 125 */         Glyph notdef = getFontProgram().getGlyphByCode(0);
/* 126 */         if (notdef != null) {
/* 127 */           glyph = new Glyph(getFontProgram().getGlyphByCode(0), unicode);
/* 128 */           this.notdefGlyphs.put(Integer.valueOf(unicode), glyph);
/*     */         } 
/*     */       } 
/* 131 */       return glyph;
/*     */     } 
/* 133 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean containsGlyph(int unicode) {
/* 138 */     if (this.fontEncoding.isFontSpecific()) {
/* 139 */       return (this.fontProgram.getGlyphByCode(unicode) != null);
/*     */     }
/* 141 */     return (this.fontEncoding.canEncode(unicode) && 
/* 142 */       getFontProgram().getGlyph(this.fontEncoding.getUnicodeDifference(unicode)) != null);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void flush() {
/* 148 */     if (isFlushed()) {
/*     */       return;
/*     */     }
/* 151 */     ensureUnderlyingObjectHasIndirectReference();
/*     */     
/* 153 */     if (this.newFont) {
/*     */       PdfName subtype;
/*     */       String fontName;
/* 156 */       if (((TrueTypeFont)getFontProgram()).isCff()) {
/* 157 */         subtype = PdfName.Type1;
/* 158 */         fontName = this.fontProgram.getFontNames().getFontName();
/*     */       } else {
/* 160 */         subtype = PdfName.TrueType;
/* 161 */         fontName = updateSubsetPrefix(this.fontProgram.getFontNames().getFontName(), this.subset, this.embedded);
/*     */       } 
/* 163 */       flushFontData(fontName, subtype);
/*     */     } 
/* 165 */     super.flush();
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
/*     */   @Deprecated
/*     */   protected void addRangeUni(Set<Integer> longTag) {
/* 178 */     ((TrueTypeFont)getFontProgram()).updateUsedGlyphs((SortedSet)longTag, this.subset, this.subsetRanges);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void addFontStream(PdfDictionary fontDescriptor) {
/* 183 */     if (this.embedded) {
/*     */       PdfName fontFileName;
/*     */       PdfStream fontStream;
/* 186 */       if (this.fontProgram instanceof IDocFontProgram) {
/* 187 */         fontFileName = ((IDocFontProgram)this.fontProgram).getFontFileName();
/* 188 */         fontStream = ((IDocFontProgram)this.fontProgram).getFontFile();
/* 189 */       } else if (((TrueTypeFont)getFontProgram()).isCff()) {
/* 190 */         fontFileName = PdfName.FontFile3;
/*     */         try {
/* 192 */           byte[] fontStreamBytes = ((TrueTypeFont)getFontProgram()).getFontStreamBytes();
/* 193 */           fontStream = getPdfFontStream(fontStreamBytes, new int[] { fontStreamBytes.length });
/* 194 */           fontStream.put(PdfName.Subtype, (PdfObject)new PdfName("Type1C"));
/* 195 */         } catch (PdfException e) {
/* 196 */           Logger logger = LoggerFactory.getLogger(PdfTrueTypeFont.class);
/* 197 */           logger.error(e.getMessage());
/* 198 */           fontStream = null;
/*     */         } 
/*     */       } else {
/* 201 */         fontFileName = PdfName.FontFile2;
/* 202 */         SortedSet<Integer> glyphs = new TreeSet<>();
/* 203 */         for (int k = 0; k < this.shortTag.length; k++) {
/* 204 */           if (this.shortTag[k] != 0) {
/* 205 */             int uni = this.fontEncoding.getUnicode(k);
/* 206 */             Glyph glyph = (uni > -1) ? this.fontProgram.getGlyph(uni) : this.fontProgram.getGlyphByCode(k);
/* 207 */             if (glyph != null) {
/* 208 */               glyphs.add(Integer.valueOf(glyph.getCode()));
/*     */             }
/*     */           } 
/*     */         } 
/* 212 */         ((TrueTypeFont)getFontProgram()).updateUsedGlyphs(glyphs, this.subset, this.subsetRanges);
/*     */         
/*     */         try {
/*     */           byte[] fontStreamBytes;
/* 216 */           if (this.subset || ((TrueTypeFont)getFontProgram()).getDirectoryOffset() > 0) {
/* 217 */             fontStreamBytes = ((TrueTypeFont)getFontProgram()).getSubset(glyphs, this.subset);
/*     */           } else {
/* 219 */             fontStreamBytes = ((TrueTypeFont)getFontProgram()).getFontStreamBytes();
/*     */           } 
/* 221 */           fontStream = getPdfFontStream(fontStreamBytes, new int[] { fontStreamBytes.length });
/* 222 */         } catch (PdfException e) {
/* 223 */           Logger logger = LoggerFactory.getLogger(PdfTrueTypeFont.class);
/* 224 */           logger.error(e.getMessage());
/* 225 */           fontStream = null;
/*     */         } 
/*     */       } 
/* 228 */       if (fontStream != null) {
/* 229 */         fontDescriptor.put(fontFileName, (PdfObject)fontStream);
/* 230 */         if (fontStream.getIndirectReference() != null) {
/* 231 */           fontStream.flush();
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean isBuiltInFont() {
/* 242 */     return (this.fontProgram instanceof Type1Font && ((Type1Font)this.fontProgram).isBuiltInFont());
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/kernel/font/PdfTrueTypeFont.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */