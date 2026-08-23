/*     */ package com.itextpdf.kernel.font;
/*     */ 
/*     */ import com.itextpdf.io.font.FontEncoding;
/*     */ import com.itextpdf.io.font.FontProgram;
/*     */ import com.itextpdf.io.font.Type1Font;
/*     */ import com.itextpdf.io.font.otf.Glyph;
/*     */ import com.itextpdf.kernel.pdf.PdfDictionary;
/*     */ import com.itextpdf.kernel.pdf.PdfName;
/*     */ import com.itextpdf.kernel.pdf.PdfNumber;
/*     */ import com.itextpdf.kernel.pdf.PdfObject;
/*     */ import com.itextpdf.kernel.pdf.PdfStream;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class PdfType1Font
/*     */   extends PdfSimpleFont<Type1Font>
/*     */ {
/*     */   private static final long serialVersionUID = 7009919945291639441L;
/*     */   
/*     */   PdfType1Font(Type1Font type1Font, String encoding, boolean embedded) {
/*  60 */     setFontProgram(type1Font);
/*  61 */     this.embedded = (embedded && !type1Font.isBuiltInFont());
/*  62 */     if ((encoding == null || encoding.length() == 0) && type1Font.isFontSpecific()) {
/*  63 */       encoding = "FontSpecific";
/*     */     }
/*  65 */     if (encoding != null && "FontSpecific".toLowerCase().equals(encoding.toLowerCase())) {
/*  66 */       this.fontEncoding = FontEncoding.createFontSpecificEncoding();
/*     */     } else {
/*  68 */       this.fontEncoding = FontEncoding.createFontEncoding(encoding);
/*     */     } 
/*     */   }
/*     */   
/*     */   PdfType1Font(Type1Font type1Font, String encoding) {
/*  73 */     this(type1Font, encoding, false);
/*     */   }
/*     */   
/*     */   PdfType1Font(PdfDictionary fontDictionary) {
/*  77 */     super(fontDictionary);
/*  78 */     this.newFont = false;
/*     */ 
/*     */ 
/*     */     
/*  82 */     this.fontEncoding = DocFontEncoding.createDocFontEncoding(fontDictionary.get(PdfName.Encoding), this.toUnicode);
/*  83 */     this.fontProgram = (FontProgram)DocType1Font.createFontProgram(fontDictionary, this.fontEncoding, this.toUnicode);
/*     */     
/*  85 */     if (this.fontProgram instanceof IDocFontProgram) {
/*  86 */       this.embedded = (((IDocFontProgram)this.fontProgram).getFontFile() != null);
/*     */     }
/*  88 */     this.subset = false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isSubset() {
/*  93 */     return this.subset;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setSubset(boolean subset) {
/*  98 */     this.subset = subset;
/*     */   }
/*     */ 
/*     */   
/*     */   public void flush() {
/* 103 */     if (isFlushed())
/* 104 */       return;  ensureUnderlyingObjectHasIndirectReference();
/* 105 */     if (this.newFont) {
/* 106 */       flushFontData(this.fontProgram.getFontNames().getFontName(), PdfName.Type1);
/*     */     }
/* 108 */     super.flush();
/*     */   }
/*     */ 
/*     */   
/*     */   public Glyph getGlyph(int unicode) {
/* 113 */     if (this.fontEncoding.canEncode(unicode)) {
/*     */       Glyph glyph;
/* 115 */       if (this.fontEncoding.isFontSpecific()) {
/* 116 */         glyph = getFontProgram().getGlyphByCode(unicode);
/*     */       } else {
/* 118 */         glyph = getFontProgram().getGlyph(this.fontEncoding.getUnicodeDifference(unicode));
/* 119 */         if (glyph == null && (glyph = this.notdefGlyphs.get(Integer.valueOf(unicode))) == null) {
/*     */ 
/*     */           
/* 122 */           glyph = new Glyph(-1, 0, unicode);
/* 123 */           this.notdefGlyphs.put(Integer.valueOf(unicode), glyph);
/*     */         } 
/*     */       } 
/* 126 */       return glyph;
/*     */     } 
/* 128 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean containsGlyph(int unicode) {
/* 133 */     if (this.fontEncoding.canEncode(unicode)) {
/* 134 */       if (this.fontEncoding.isFontSpecific()) {
/* 135 */         return (getFontProgram().getGlyphByCode(unicode) != null);
/*     */       }
/* 137 */       return (getFontProgram().getGlyph(this.fontEncoding.getUnicodeDifference(unicode)) != null);
/*     */     } 
/*     */     
/* 140 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean isBuiltInFont() {
/* 149 */     return ((Type1Font)getFontProgram()).isBuiltInFont();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void addFontStream(PdfDictionary fontDescriptor) {
/* 158 */     if (this.embedded)
/* 159 */       if (this.fontProgram instanceof IDocFontProgram) {
/* 160 */         IDocFontProgram docType1Font = (IDocFontProgram)this.fontProgram;
/* 161 */         fontDescriptor.put(docType1Font.getFontFileName(), (PdfObject)docType1Font
/* 162 */             .getFontFile());
/* 163 */         docType1Font.getFontFile().flush();
/* 164 */         if (docType1Font.getSubtype() != null) {
/* 165 */           fontDescriptor.put(PdfName.Subtype, (PdfObject)docType1Font.getSubtype());
/*     */         }
/*     */       } else {
/* 168 */         byte[] fontStreamBytes = ((Type1Font)getFontProgram()).getFontStreamBytes();
/* 169 */         if (fontStreamBytes != null) {
/* 170 */           PdfStream fontStream = new PdfStream(fontStreamBytes);
/* 171 */           int[] fontStreamLengths = ((Type1Font)getFontProgram()).getFontStreamLengths();
/* 172 */           for (int k = 0; k < fontStreamLengths.length; k++) {
/* 173 */             fontStream.put(new PdfName("Length" + (k + 1)), (PdfObject)new PdfNumber(fontStreamLengths[k]));
/*     */           }
/* 175 */           fontDescriptor.put(PdfName.FontFile, (PdfObject)fontStream);
/* 176 */           if (makeObjectIndirect((PdfObject)fontStream))
/* 177 */             fontStream.flush(); 
/*     */         } 
/*     */       }  
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/kernel/font/PdfType1Font.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */