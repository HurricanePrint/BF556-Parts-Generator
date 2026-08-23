/*     */ package com.itextpdf.io.font;
/*     */ 
/*     */ import com.itextpdf.io.source.RandomAccessFileOrArray;
/*     */ import com.itextpdf.io.util.FileUtil;
/*     */ import java.io.IOException;
/*     */ import java.io.OutputStream;
/*     */ import java.io.PrintWriter;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class Pfm2afm
/*     */ {
/*     */   private RandomAccessFileOrArray input;
/*     */   private PrintWriter output;
/*     */   private short vers;
/*     */   private int h_len;
/*     */   private String copyright;
/*     */   private short type;
/*     */   private short points;
/*     */   private short verres;
/*     */   private short horres;
/*     */   private short ascent;
/*     */   private short intleading;
/*     */   private short extleading;
/*     */   private byte italic;
/*     */   private byte uline;
/*     */   private byte overs;
/*     */   private short weight;
/*     */   private byte charset;
/*     */   private short pixwidth;
/*     */   private short pixheight;
/*     */   private byte kind;
/*     */   private short avgwidth;
/*     */   private short maxwidth;
/*     */   private int firstchar;
/*     */   private int lastchar;
/*     */   private byte defchar;
/*     */   private byte brkchar;
/*     */   private short widthby;
/*     */   private int device;
/*     */   private int face;
/*     */   private int bits;
/*     */   private int bitoff;
/*     */   private short extlen;
/*     */   private int psext;
/*     */   private int chartab;
/*     */   private int res1;
/*     */   private int kernpairs;
/*     */   private int res2;
/*     */   private int fontname;
/*     */   private short capheight;
/*     */   private short xheight;
/*     */   private short ascender;
/*     */   private short descender;
/*     */   private boolean isMono;
/*     */   private int[] Win2PSStd;
/*     */   private String[] WinChars;
/*     */   
/*     */   private Pfm2afm(RandomAccessFileOrArray input, OutputStream output) throws IOException {
/* 495 */     this.Win2PSStd = new int[] { 0, 0, 0, 0, 197, 198, 199, 0, 202, 0, 205, 206, 207, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 32, 33, 34, 35, 36, 37, 38, 169, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 58, 59, 60, 61, 62, 63, 64, 65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75, 76, 77, 78, 79, 80, 81, 82, 83, 84, 85, 86, 87, 88, 89, 90, 91, 92, 93, 94, 95, 193, 97, 98, 99, 100, 101, 102, 103, 104, 105, 106, 107, 108, 109, 110, 111, 112, 113, 114, 115, 116, 117, 118, 119, 120, 121, 122, 123, 124, 125, 126, 127, 128, 0, 184, 166, 185, 188, 178, 179, 195, 189, 0, 172, 234, 0, 0, 0, 0, 96, 0, 170, 186, 183, 177, 208, 196, 0, 0, 173, 250, 0, 0, 0, 0, 161, 162, 163, 168, 165, 0, 167, 200, 0, 227, 171, 0, 0, 0, 197, 0, 0, 0, 0, 194, 0, 182, 180, 203, 0, 235, 187, 0, 0, 0, 191, 0, 0, 0, 0, 0, 0, 225, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 233, 0, 0, 0, 0, 0, 0, 251, 0, 0, 0, 0, 0, 0, 241, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 249, 0, 0, 0, 0, 0, 0, 0 };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 542 */     this.WinChars = new String[] { "W00", "W01", "W02", "W03", "macron", "breve", "dotaccent", "W07", "ring", "W09", "W0a", "W0b", "W0c", "W0d", "W0e", "W0f", "hungarumlaut", "ogonek", "caron", "W13", "W14", "W15", "W16", "W17", "W18", "W19", "W1a", "W1b", "W1c", "W1d", "W1e", "W1f", "space", "exclam", "quotedbl", "numbersign", "dollar", "percent", "ampersand", "quotesingle", "parenleft", "parenright", "asterisk", "plus", "comma", "hyphen", "period", "slash", "zero", "one", "two", "three", "four", "five", "six", "seven", "eight", "nine", "colon", "semicolon", "less", "equal", "greater", "question", "at", "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z", "bracketleft", "backslash", "bracketright", "asciicircum", "underscore", "grave", "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z", "braceleft", "bar", "braceright", "asciitilde", "W7f", "euro", "W81", "quotesinglbase", "florin", "quotedblbase", "ellipsis", "dagger", "daggerdbl", "circumflex", "perthousand", "Scaron", "guilsinglleft", "OE", "W8d", "Zcaron", "W8f", "W90", "quoteleft", "quoteright", "quotedblleft", "quotedblright", "bullet", "endash", "emdash", "tilde", "trademark", "scaron", "guilsinglright", "oe", "W9d", "zcaron", "Ydieresis", "reqspace", "exclamdown", "cent", "sterling", "currency", "yen", "brokenbar", "section", "dieresis", "copyright", "ordfeminine", "guillemotleft", "logicalnot", "syllable", "registered", "macron", "degree", "plusminus", "twosuperior", "threesuperior", "acute", "mu", "paragraph", "periodcentered", "cedilla", "onesuperior", "ordmasculine", "guillemotright", "onequarter", "onehalf", "threequarters", "questiondown", "Agrave", "Aacute", "Acircumflex", "Atilde", "Adieresis", "Aring", "AE", "Ccedilla", "Egrave", "Eacute", "Ecircumflex", "Edieresis", "Igrave", "Iacute", "Icircumflex", "Idieresis", "Eth", "Ntilde", "Ograve", "Oacute", "Ocircumflex", "Otilde", "Odieresis", "multiply", "Oslash", "Ugrave", "Uacute", "Ucircumflex", "Udieresis", "Yacute", "Thorn", "germandbls", "agrave", "aacute", "acircumflex", "atilde", "adieresis", "aring", "ae", "ccedilla", "egrave", "eacute", "ecircumflex", "edieresis", "igrave", "iacute", "icircumflex", "idieresis", "eth", "ntilde", "ograve", "oacute", "ocircumflex", "otilde", "odieresis", "divide", "oslash", "ugrave", "uacute", "ucircumflex", "udieresis", "yacute", "thorn", "ydieresis" };
/*     */     this.input = input;
/*     */     this.output = FileUtil.createPrintWriter(output, "ISO-8859-1");
/*     */   }
/*     */   
/*     */   public static void convert(RandomAccessFileOrArray input, OutputStream output) throws IOException {
/*     */     Pfm2afm p = new Pfm2afm(input, output);
/*     */     p.openpfm();
/*     */     p.putheader();
/*     */     p.putchartab();
/*     */     p.putkerntab();
/*     */     p.puttrailer();
/*     */     p.output.flush();
/*     */   }
/*     */   
/*     */   private String readString(int n) throws IOException {
/*     */     byte[] b = new byte[n];
/*     */     this.input.readFully(b);
/*     */     int k;
/*     */     for (k = 0; k < b.length && b[k] != 0; k++);
/*     */     return new String(b, 0, k, "ISO-8859-1");
/*     */   }
/*     */   
/*     */   private String readString() throws IOException {
/*     */     StringBuilder buf = new StringBuilder();
/*     */     while (true) {
/*     */       int c = this.input.read();
/*     */       if (c <= 0)
/*     */         break; 
/*     */       buf.append((char)c);
/*     */     } 
/*     */     return buf.toString();
/*     */   }
/*     */   
/*     */   private void outval(int n) {
/*     */     this.output.print(' ');
/*     */     this.output.print(n);
/*     */   }
/*     */   
/*     */   private void outchar(int code, int width, String name) {
/*     */     this.output.print("C ");
/*     */     outval(code);
/*     */     this.output.print(" ; WX ");
/*     */     outval(width);
/*     */     if (name != null) {
/*     */       this.output.print(" ; N ");
/*     */       this.output.print(name);
/*     */     } 
/*     */     this.output.print(" ;\n");
/*     */   }
/*     */   
/*     */   private void openpfm() throws IOException {
/*     */     this.input.seek(0L);
/*     */     this.vers = this.input.readShortLE();
/*     */     this.h_len = this.input.readIntLE();
/*     */     this.copyright = readString(60);
/*     */     this.type = this.input.readShortLE();
/*     */     this.points = this.input.readShortLE();
/*     */     this.verres = this.input.readShortLE();
/*     */     this.horres = this.input.readShortLE();
/*     */     this.ascent = this.input.readShortLE();
/*     */     this.intleading = this.input.readShortLE();
/*     */     this.extleading = this.input.readShortLE();
/*     */     this.italic = (byte)this.input.read();
/*     */     this.uline = (byte)this.input.read();
/*     */     this.overs = (byte)this.input.read();
/*     */     this.weight = this.input.readShortLE();
/*     */     this.charset = (byte)this.input.read();
/*     */     this.pixwidth = this.input.readShortLE();
/*     */     this.pixheight = this.input.readShortLE();
/*     */     this.kind = (byte)this.input.read();
/*     */     this.avgwidth = this.input.readShortLE();
/*     */     this.maxwidth = this.input.readShortLE();
/*     */     this.firstchar = this.input.read();
/*     */     this.lastchar = this.input.read();
/*     */     this.defchar = (byte)this.input.read();
/*     */     this.brkchar = (byte)this.input.read();
/*     */     this.widthby = this.input.readShortLE();
/*     */     this.device = this.input.readIntLE();
/*     */     this.face = this.input.readIntLE();
/*     */     this.bits = this.input.readIntLE();
/*     */     this.bitoff = this.input.readIntLE();
/*     */     this.extlen = this.input.readShortLE();
/*     */     this.psext = this.input.readIntLE();
/*     */     this.chartab = this.input.readIntLE();
/*     */     this.res1 = this.input.readIntLE();
/*     */     this.kernpairs = this.input.readIntLE();
/*     */     this.res2 = this.input.readIntLE();
/*     */     this.fontname = this.input.readIntLE();
/*     */     if (this.h_len != this.input.length() || this.extlen != 30 || this.fontname < 75 || this.fontname > 512)
/*     */       throw new IOException("not.a.valid.pfm.file"); 
/*     */     this.input.seek((this.psext + 14));
/*     */     this.capheight = this.input.readShortLE();
/*     */     this.xheight = this.input.readShortLE();
/*     */     this.ascender = this.input.readShortLE();
/*     */     this.descender = this.input.readShortLE();
/*     */   }
/*     */   
/*     */   private void putheader() throws IOException {
/*     */     this.output.print("StartFontMetrics 2.0\n");
/*     */     if (this.copyright.length() > 0)
/*     */       this.output.print("Comment " + this.copyright + '\n'); 
/*     */     this.output.print("FontName ");
/*     */     this.input.seek(this.fontname);
/*     */     String fname = readString();
/*     */     this.output.print(fname);
/*     */     this.output.print("\nEncodingScheme ");
/*     */     if (this.charset != 0) {
/*     */       this.output.print("FontSpecific\n");
/*     */     } else {
/*     */       this.output.print("AdobeStandardEncoding\n");
/*     */     } 
/*     */     this.output.print("FullName " + fname.replace('-', ' '));
/*     */     if (this.face != 0) {
/*     */       this.input.seek(this.face);
/*     */       this.output.print("\nFamilyName " + readString());
/*     */     } 
/*     */     this.output.print("\nWeight ");
/*     */     if (this.weight > 475 || fname.toLowerCase().contains("bold")) {
/*     */       this.output.print("Bold");
/*     */     } else if ((this.weight < 325 && this.weight != 0) || fname.toLowerCase().contains("light")) {
/*     */       this.output.print("Light");
/*     */     } else if (fname.toLowerCase().contains("black")) {
/*     */       this.output.print("Black");
/*     */     } else {
/*     */       this.output.print("Medium");
/*     */     } 
/*     */     this.output.print("\nItalicAngle ");
/*     */     if (this.italic != 0 || fname.toLowerCase().contains("italic")) {
/*     */       this.output.print("-12.00");
/*     */     } else {
/*     */       this.output.print("0");
/*     */     } 
/*     */     this.output.print("\nIsFixedPitch ");
/*     */     if ((this.kind & 0x1) == 0 || this.avgwidth == this.maxwidth) {
/*     */       this.output.print("true");
/*     */       this.isMono = true;
/*     */     } else {
/*     */       this.output.print("false");
/*     */       this.isMono = false;
/*     */     } 
/*     */     this.output.print("\nFontBBox");
/*     */     if (this.isMono) {
/*     */       outval(-20);
/*     */     } else {
/*     */       outval(-100);
/*     */     } 
/*     */     outval(-(this.descender + 5));
/*     */     outval(this.maxwidth + 10);
/*     */     outval(this.ascent + 5);
/*     */     this.output.print("\nCapHeight");
/*     */     outval(this.capheight);
/*     */     this.output.print("\nXHeight");
/*     */     outval(this.xheight);
/*     */     this.output.print("\nDescender");
/*     */     outval(-this.descender);
/*     */     this.output.print("\nAscender");
/*     */     outval(this.ascender);
/*     */     this.output.print('\n');
/*     */   }
/*     */   
/*     */   private void putchartab() throws IOException {
/*     */     int count = this.lastchar - this.firstchar + 1;
/*     */     int[] ctabs = new int[count];
/*     */     this.input.seek(this.chartab);
/*     */     for (int k = 0; k < count; k++)
/*     */       ctabs[k] = this.input.readUnsignedShortLE(); 
/*     */     int[] back = new int[256];
/*     */     if (this.charset == 0)
/*     */       for (int i = this.firstchar; i <= this.lastchar; i++) {
/*     */         if (this.Win2PSStd[i] != 0)
/*     */           back[this.Win2PSStd[i]] = i; 
/*     */       }  
/*     */     this.output.print("StartCharMetrics");
/*     */     outval(count);
/*     */     this.output.print('\n');
/*     */     if (this.charset != 0) {
/*     */       for (int i = this.firstchar; i <= this.lastchar; i++) {
/*     */         if (ctabs[i - this.firstchar] != 0)
/*     */           outchar(i, ctabs[i - this.firstchar], null); 
/*     */       } 
/*     */     } else {
/*     */       int i;
/*     */       for (i = 0; i < 256; i++) {
/*     */         int j = back[i];
/*     */         if (j != 0) {
/*     */           outchar(i, ctabs[j - this.firstchar], this.WinChars[j]);
/*     */           ctabs[j - this.firstchar] = 0;
/*     */         } 
/*     */       } 
/*     */       for (i = this.firstchar; i <= this.lastchar; i++) {
/*     */         if (ctabs[i - this.firstchar] != 0)
/*     */           outchar(-1, ctabs[i - this.firstchar], this.WinChars[i]); 
/*     */       } 
/*     */     } 
/*     */     this.output.print("EndCharMetrics\n");
/*     */   }
/*     */   
/*     */   private void putkerntab() throws IOException {
/*     */     if (this.kernpairs == 0)
/*     */       return; 
/*     */     this.input.seek(this.kernpairs);
/*     */     int count = this.input.readUnsignedShortLE();
/*     */     int nzero = 0;
/*     */     int[] kerns = new int[count * 3];
/*     */     int k;
/*     */     for (k = 0; k < kerns.length; ) {
/*     */       kerns[k++] = this.input.read();
/*     */       kerns[k++] = this.input.read();
/*     */       kerns[k++] = this.input.readShortLE();
/*     */       if (this.input.readShortLE() != 0)
/*     */         nzero++; 
/*     */     } 
/*     */     if (nzero == 0)
/*     */       return; 
/*     */     this.output.print("StartKernData\nStartKernPairs");
/*     */     outval(nzero);
/*     */     this.output.print('\n');
/*     */     for (k = 0; k < kerns.length; k += 3) {
/*     */       if (kerns[k + 2] != 0) {
/*     */         this.output.print("KPX ");
/*     */         this.output.print(this.WinChars[kerns[k]]);
/*     */         this.output.print(' ');
/*     */         this.output.print(this.WinChars[kerns[k + 1]]);
/*     */         outval(kerns[k + 2]);
/*     */         this.output.print('\n');
/*     */       } 
/*     */     } 
/*     */     this.output.print("EndKernPairs\nEndKernData\n");
/*     */   }
/*     */   
/*     */   private void puttrailer() {
/*     */     this.output.print("EndFontMetrics\n");
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/io/font/Pfm2afm.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */