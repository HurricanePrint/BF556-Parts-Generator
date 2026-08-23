/*      */ package com.itextpdf.io.font;
/*      */ 
/*      */ import com.itextpdf.io.IOException;
/*      */ import com.itextpdf.io.source.RandomAccessFileOrArray;
/*      */ import com.itextpdf.io.source.RandomAccessSourceFactory;
/*      */ import java.io.IOException;
/*      */ import java.util.LinkedList;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class CFFFont
/*      */ {
/*   54 */   static final String[] operatorNames = new String[] { "version", "Notice", "FullName", "FamilyName", "Weight", "FontBBox", "BlueValues", "OtherBlues", "FamilyBlues", "FamilyOtherBlues", "StdHW", "StdVW", "UNKNOWN_12", "UniqueID", "XUID", "charset", "Encoding", "CharStrings", "Private", "Subrs", "defaultWidthX", "nominalWidthX", "UNKNOWN_22", "UNKNOWN_23", "UNKNOWN_24", "UNKNOWN_25", "UNKNOWN_26", "UNKNOWN_27", "UNKNOWN_28", "UNKNOWN_29", "UNKNOWN_30", "UNKNOWN_31", "Copyright", "isFixedPitch", "ItalicAngle", "UnderlinePosition", "UnderlineThickness", "PaintType", "CharstringType", "FontMatrix", "StrokeWidth", "BlueScale", "BlueShift", "BlueFuzz", "StemSnapH", "StemSnapV", "ForceBold", "UNKNOWN_12_15", "UNKNOWN_12_16", "LanguageGroup", "ExpansionFactor", "initialRandomSeed", "SyntheticBase", "PostScript", "BaseFontName", "BaseFontBlend", "UNKNOWN_12_24", "UNKNOWN_12_25", "UNKNOWN_12_26", "UNKNOWN_12_27", "UNKNOWN_12_28", "UNKNOWN_12_29", "ROS", "CIDFontVersion", "CIDFontRevision", "CIDFontType", "CIDCount", "UIDBase", "FDArray", "FDSelect", "FontName" };
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*   75 */   static final String[] standardStrings = new String[] { ".notdef", "space", "exclam", "quotedbl", "numbersign", "dollar", "percent", "ampersand", "quoteright", "parenleft", "parenright", "asterisk", "plus", "comma", "hyphen", "period", "slash", "zero", "one", "two", "three", "four", "five", "six", "seven", "eight", "nine", "colon", "semicolon", "less", "equal", "greater", "question", "at", "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z", "bracketleft", "backslash", "bracketright", "asciicircum", "underscore", "quoteleft", "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z", "braceleft", "bar", "braceright", "asciitilde", "exclamdown", "cent", "sterling", "fraction", "yen", "florin", "section", "currency", "quotesingle", "quotedblleft", "guillemotleft", "guilsinglleft", "guilsinglright", "fi", "fl", "endash", "dagger", "daggerdbl", "periodcentered", "paragraph", "bullet", "quotesinglbase", "quotedblbase", "quotedblright", "guillemotright", "ellipsis", "perthousand", "questiondown", "grave", "acute", "circumflex", "tilde", "macron", "breve", "dotaccent", "dieresis", "ring", "cedilla", "hungarumlaut", "ogonek", "caron", "emdash", "AE", "ordfeminine", "Lslash", "Oslash", "OE", "ordmasculine", "ae", "dotlessi", "lslash", "oslash", "oe", "germandbls", "onesuperior", "logicalnot", "mu", "trademark", "Eth", "onehalf", "plusminus", "Thorn", "onequarter", "divide", "brokenbar", "degree", "thorn", "threequarters", "twosuperior", "registered", "minus", "eth", "multiply", "threesuperior", "copyright", "Aacute", "Acircumflex", "Adieresis", "Agrave", "Aring", "Atilde", "Ccedilla", "Eacute", "Ecircumflex", "Edieresis", "Egrave", "Iacute", "Icircumflex", "Idieresis", "Igrave", "Ntilde", "Oacute", "Ocircumflex", "Odieresis", "Ograve", "Otilde", "Scaron", "Uacute", "Ucircumflex", "Udieresis", "Ugrave", "Yacute", "Ydieresis", "Zcaron", "aacute", "acircumflex", "adieresis", "agrave", "aring", "atilde", "ccedilla", "eacute", "ecircumflex", "edieresis", "egrave", "iacute", "icircumflex", "idieresis", "igrave", "ntilde", "oacute", "ocircumflex", "odieresis", "ograve", "otilde", "scaron", "uacute", "ucircumflex", "udieresis", "ugrave", "yacute", "ydieresis", "zcaron", "exclamsmall", "Hungarumlautsmall", "dollaroldstyle", "dollarsuperior", "ampersandsmall", "Acutesmall", "parenleftsuperior", "parenrightsuperior", "twodotenleader", "onedotenleader", "zerooldstyle", "oneoldstyle", "twooldstyle", "threeoldstyle", "fouroldstyle", "fiveoldstyle", "sixoldstyle", "sevenoldstyle", "eightoldstyle", "nineoldstyle", "commasuperior", "threequartersemdash", "periodsuperior", "questionsmall", "asuperior", "bsuperior", "centsuperior", "dsuperior", "esuperior", "isuperior", "lsuperior", "msuperior", "nsuperior", "osuperior", "rsuperior", "ssuperior", "tsuperior", "ff", "ffi", "ffl", "parenleftinferior", "parenrightinferior", "Circumflexsmall", "hyphensuperior", "Gravesmall", "Asmall", "Bsmall", "Csmall", "Dsmall", "Esmall", "Fsmall", "Gsmall", "Hsmall", "Ismall", "Jsmall", "Ksmall", "Lsmall", "Msmall", "Nsmall", "Osmall", "Psmall", "Qsmall", "Rsmall", "Ssmall", "Tsmall", "Usmall", "Vsmall", "Wsmall", "Xsmall", "Ysmall", "Zsmall", "colonmonetary", "onefitted", "rupiah", "Tildesmall", "exclamdownsmall", "centoldstyle", "Lslashsmall", "Scaronsmall", "Zcaronsmall", "Dieresissmall", "Brevesmall", "Caronsmall", "Dotaccentsmall", "Macronsmall", "figuredash", "hypheninferior", "Ogoneksmall", "Ringsmall", "Cedillasmall", "questiondownsmall", "oneeighth", "threeeighths", "fiveeighths", "seveneighths", "onethird", "twothirds", "zerosuperior", "foursuperior", "fivesuperior", "sixsuperior", "sevensuperior", "eightsuperior", "ninesuperior", "zeroinferior", "oneinferior", "twoinferior", "threeinferior", "fourinferior", "fiveinferior", "sixinferior", "seveninferior", "eightinferior", "nineinferior", "centinferior", "dollarinferior", "periodinferior", "commainferior", "Agravesmall", "Aacutesmall", "Acircumflexsmall", "Atildesmall", "Adieresissmall", "Aringsmall", "AEsmall", "Ccedillasmall", "Egravesmall", "Eacutesmall", "Ecircumflexsmall", "Edieresissmall", "Igravesmall", "Iacutesmall", "Icircumflexsmall", "Idieresissmall", "Ethsmall", "Ntildesmall", "Ogravesmall", "Oacutesmall", "Ocircumflexsmall", "Otildesmall", "Odieresissmall", "OEsmall", "Oslashsmall", "Ugravesmall", "Uacutesmall", "Ucircumflexsmall", "Udieresissmall", "Yacutesmall", "Thornsmall", "Ydieresissmall", "001.000", "001.001", "001.002", "001.003", "Black", "Bold", "Book", "Light", "Medium", "Regular", "Roman", "Semibold" };
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   int nextIndexOffset;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected String key;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getString(char sid) {
/*  149 */     if (sid < standardStrings.length) return standardStrings[sid]; 
/*  150 */     if (sid >= standardStrings.length + this.stringOffsets.length - 1) return null; 
/*  151 */     int j = sid - standardStrings.length;
/*      */     
/*  153 */     int p = getPosition();
/*  154 */     seek(this.stringOffsets[j]);
/*  155 */     StringBuffer s = new StringBuffer();
/*  156 */     for (int k = this.stringOffsets[j]; k < this.stringOffsets[j + 1]; k++) {
/*  157 */       s.append(getCard8());
/*      */     }
/*  159 */     seek(p);
/*  160 */     return s.toString();
/*      */   }
/*      */   
/*      */   char getCard8() {
/*      */     try {
/*  165 */       byte i = this.buf.readByte();
/*  166 */       return (char)(i & 0xFF);
/*      */     }
/*  168 */     catch (Exception e) {
/*  169 */       throw new IOException("I/O exception.", e);
/*      */     } 
/*      */   }
/*      */   
/*      */   char getCard16() {
/*      */     try {
/*  175 */       return this.buf.readChar();
/*  176 */     } catch (IOException e) {
/*  177 */       throw new IOException("I/O exception.", e);
/*      */     } 
/*      */   }
/*      */   
/*      */   int getOffset(int offSize) {
/*  182 */     int offset = 0;
/*  183 */     for (int i = 0; i < offSize; i++) {
/*  184 */       offset *= 256;
/*  185 */       offset += getCard8();
/*      */     } 
/*  187 */     return offset;
/*      */   }
/*      */   
/*      */   void seek(int offset) {
/*      */     try {
/*  192 */       this.buf.seek(offset);
/*  193 */     } catch (IOException e) {
/*  194 */       throw new IOException("I/O exception.", e);
/*      */     } 
/*      */   }
/*      */   
/*      */   short getShort() {
/*      */     try {
/*  200 */       return this.buf.readShort();
/*  201 */     } catch (IOException e) {
/*  202 */       throw new IOException("I/O exception.", e);
/*      */     } 
/*      */   }
/*      */   
/*      */   int getInt() {
/*      */     try {
/*  208 */       return this.buf.readInt();
/*  209 */     } catch (IOException e) {
/*  210 */       throw new IOException("I/O exception.", e);
/*      */     } 
/*      */   }
/*      */   
/*      */   int getPosition() {
/*      */     try {
/*  216 */       return (int)this.buf.getPosition();
/*  217 */     } catch (IOException e) {
/*  218 */       throw new IOException("I/O exception.", e);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   int[] getIndex(int nextIndexOffset) {
/*  229 */     seek(nextIndexOffset);
/*  230 */     int count = getCard16();
/*  231 */     int[] offsets = new int[count + 1];
/*      */     
/*  233 */     if (count == 0) {
/*  234 */       offsets[0] = -1;
/*      */       
/*  236 */       nextIndexOffset += 2;
/*  237 */       return offsets;
/*      */     } 
/*      */     
/*  240 */     int indexOffSize = getCard8();
/*      */     
/*  242 */     for (int j = 0; j <= count; j++)
/*      */     {
/*  244 */       offsets[j] = nextIndexOffset + 2 + 1 + (count + 1) * indexOffSize - 1 + 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  252 */         getOffset(indexOffSize);
/*      */     }
/*      */     
/*  255 */     return offsets;
/*      */   }
/*      */ 
/*      */   
/*  259 */   protected Object[] args = new Object[48];
/*  260 */   protected int arg_count = 0; protected RandomAccessFileOrArray buf; private int offSize;
/*      */   
/*      */   protected void getDictItem() {
/*  263 */     for (int i = 0; i < this.arg_count; ) { this.args[i] = null; i++; }
/*  264 */      this.arg_count = 0;
/*  265 */     this.key = null;
/*  266 */     boolean gotKey = false;
/*      */     
/*  268 */     while (!gotKey) {
/*  269 */       char b0 = getCard8();
/*  270 */       if (b0 == '\035') {
/*  271 */         int item = getInt();
/*  272 */         this.args[this.arg_count] = Integer.valueOf(item);
/*  273 */         this.arg_count++;
/*      */         
/*      */         continue;
/*      */       } 
/*  277 */       if (b0 == '\034') {
/*  278 */         short item = getShort();
/*  279 */         this.args[this.arg_count] = Integer.valueOf(item);
/*  280 */         this.arg_count++;
/*      */         
/*      */         continue;
/*      */       } 
/*  284 */       if (b0 >= ' ' && b0 <= 'ö') {
/*  285 */         this.args[this.arg_count] = Integer.valueOf(b0 - 139);
/*  286 */         this.arg_count++;
/*      */         
/*      */         continue;
/*      */       } 
/*  290 */       if (b0 >= '÷' && b0 <= 'ú') {
/*  291 */         char b1 = getCard8();
/*  292 */         short item = (short)((b0 - 247) * 256 + b1 + 108);
/*  293 */         this.args[this.arg_count] = Integer.valueOf(item);
/*  294 */         this.arg_count++;
/*      */         
/*      */         continue;
/*      */       } 
/*  298 */       if (b0 >= 'û' && b0 <= 'þ') {
/*  299 */         char b1 = getCard8();
/*  300 */         short item = (short)(-(b0 - 251) * 256 - b1 - 108);
/*  301 */         this.args[this.arg_count] = Integer.valueOf(item);
/*  302 */         this.arg_count++;
/*      */         
/*      */         continue;
/*      */       } 
/*  306 */       if (b0 == '\036') {
/*  307 */         StringBuilder item = new StringBuilder("");
/*  308 */         boolean done = false;
/*  309 */         char buffer = Character.MIN_VALUE;
/*  310 */         byte avail = 0;
/*  311 */         int nibble = 0;
/*  312 */         while (!done) {
/*      */           
/*  314 */           if (avail == 0) { buffer = getCard8(); avail = 2; }
/*  315 */            if (avail == 1) { nibble = buffer / 16; avail = (byte)(avail - 1); }
/*  316 */            if (avail == 2) { nibble = buffer % 16; avail = (byte)(avail - 1); }
/*  317 */            switch (nibble) { case 10:
/*  318 */               item.append("."); continue;
/*  319 */             case 11: item.append("E"); continue;
/*  320 */             case 12: item.append("E-"); continue;
/*  321 */             case 14: item.append("-"); continue;
/*  322 */             case 15: done = true; continue; }
/*      */           
/*  324 */           if (nibble >= 0 && nibble <= 9) {
/*  325 */             item.append(nibble); continue;
/*      */           } 
/*  327 */           item.append("<NIBBLE ERROR: ").append(nibble).append('>');
/*  328 */           done = true;
/*      */         } 
/*      */ 
/*      */ 
/*      */         
/*  333 */         this.args[this.arg_count] = item.toString();
/*  334 */         this.arg_count++;
/*      */         
/*      */         continue;
/*      */       } 
/*  338 */       if (b0 <= '\025') {
/*  339 */         gotKey = true;
/*  340 */         if (b0 != '\f') { this.key = operatorNames[b0]; continue; }
/*  341 */          this.key = operatorNames[32 + getCard8()];
/*      */       } 
/*      */     } 
/*      */   }
/*      */   protected int nameIndexOffset; protected int topdictIndexOffset;
/*      */   protected int stringIndexOffset;
/*      */   protected int gsubrIndexOffset;
/*      */   protected int[] nameOffsets;
/*      */   protected int[] topdictOffsets;
/*      */   protected int[] stringOffsets;
/*      */   protected int[] gsubrOffsets;
/*      */   protected Font[] fonts;
/*      */   
/*  354 */   protected static abstract class Item { protected int myOffset = -1;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void increment(int[] currentOffset) {
/*  362 */       this.myOffset = currentOffset[0];
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void emit(byte[] buffer) {}
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void xref() {} }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected static abstract class OffsetItem
/*      */     extends Item
/*      */   {
/*      */     public int value;
/*      */ 
/*      */ 
/*      */     
/*      */     public void set(int offset) {
/*  387 */       this.value = offset;
/*      */     }
/*      */   }
/*      */   
/*      */   protected static final class RangeItem
/*      */     extends Item {
/*      */     public int offset;
/*      */     public int length;
/*      */     private RandomAccessFileOrArray buf;
/*      */     
/*      */     public RangeItem(RandomAccessFileOrArray buf, int offset, int length) {
/*  398 */       this.offset = offset;
/*  399 */       this.length = length;
/*  400 */       this.buf = buf;
/*      */     }
/*      */     
/*      */     public void increment(int[] currentOffset) {
/*  404 */       super.increment(currentOffset);
/*  405 */       currentOffset[0] = currentOffset[0] + this.length;
/*      */     }
/*      */ 
/*      */     
/*      */     public void emit(byte[] buffer) {
/*      */       try {
/*  411 */         this.buf.seek(this.offset);
/*  412 */         for (int i = this.myOffset; i < this.myOffset + this.length; i++)
/*  413 */           buffer[i] = this.buf.readByte(); 
/*  414 */       } catch (IOException e) {
/*  415 */         throw new IOException("I/O exception.", e);
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   protected static final class IndexOffsetItem
/*      */     extends OffsetItem
/*      */   {
/*      */     public final int size;
/*      */ 
/*      */     
/*      */     public IndexOffsetItem(int size, int value)
/*      */     {
/*  429 */       this.size = size; this.value = value; } public IndexOffsetItem(int size) {
/*  430 */       this.size = size;
/*      */     }
/*      */     
/*      */     public void increment(int[] currentOffset) {
/*  434 */       super.increment(currentOffset);
/*  435 */       currentOffset[0] = currentOffset[0] + this.size;
/*      */     }
/*      */     
/*      */     public void emit(byte[] buffer) {
/*  439 */       if (this.size >= 1 && this.size <= 4)
/*  440 */         for (int i = 0; i < this.size; i++)
/*  441 */           buffer[this.myOffset + i] = (byte)(this.value >>> this.size - 1 - i << 3 & 0xFF);  
/*      */     }
/*      */   }
/*      */   
/*      */   protected static final class IndexBaseItem
/*      */     extends Item {}
/*      */   
/*      */   protected static final class IndexMarkerItem
/*      */     extends Item
/*      */   {
/*      */     private CFFFont.OffsetItem offItem;
/*      */     private CFFFont.IndexBaseItem indexBase;
/*      */     
/*      */     public IndexMarkerItem(CFFFont.OffsetItem offItem, CFFFont.IndexBaseItem indexBase) {
/*  455 */       this.offItem = offItem;
/*  456 */       this.indexBase = indexBase;
/*      */     }
/*      */ 
/*      */     
/*      */     public void xref() {
/*  461 */       this.offItem.set(this.myOffset - this.indexBase.myOffset + 1);
/*      */     }
/*      */   }
/*      */   
/*      */   protected static final class SubrMarkerItem
/*      */     extends Item
/*      */   {
/*      */     private CFFFont.OffsetItem offItem;
/*      */     private CFFFont.IndexBaseItem indexBase;
/*      */     
/*      */     public SubrMarkerItem(CFFFont.OffsetItem offItem, CFFFont.IndexBaseItem indexBase) {
/*  472 */       this.offItem = offItem;
/*  473 */       this.indexBase = indexBase;
/*      */     }
/*      */ 
/*      */     
/*      */     public void xref() {
/*  478 */       this.offItem.set(this.myOffset - this.indexBase.myOffset);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected static final class DictOffsetItem
/*      */     extends OffsetItem
/*      */   {
/*  488 */     public final int size = 5;
/*      */ 
/*      */     
/*      */     public void increment(int[] currentOffset) {
/*  492 */       super.increment(currentOffset);
/*  493 */       currentOffset[0] = currentOffset[0] + this.size;
/*      */     }
/*      */ 
/*      */     
/*      */     public void emit(byte[] buffer) {
/*  498 */       if (this.size == 5) {
/*  499 */         buffer[this.myOffset] = 29;
/*  500 */         buffer[this.myOffset + 1] = (byte)(this.value >>> 24 & 0xFF);
/*  501 */         buffer[this.myOffset + 2] = (byte)(this.value >>> 16 & 0xFF);
/*  502 */         buffer[this.myOffset + 3] = (byte)(this.value >>> 8 & 0xFF);
/*  503 */         buffer[this.myOffset + 4] = (byte)(this.value >>> 0 & 0xFF);
/*      */       } 
/*      */     }
/*      */   }
/*      */   
/*      */   protected static final class UInt24Item
/*      */     extends Item {
/*      */     public int value;
/*      */     
/*      */     public UInt24Item(int value) {
/*  513 */       this.value = value;
/*      */     }
/*      */     
/*      */     public void increment(int[] currentOffset) {
/*  517 */       super.increment(currentOffset);
/*  518 */       currentOffset[0] = currentOffset[0] + 3;
/*      */     }
/*      */ 
/*      */     
/*      */     public void emit(byte[] buffer) {
/*  523 */       buffer[this.myOffset + 0] = (byte)(this.value >>> 16 & 0xFF);
/*  524 */       buffer[this.myOffset + 1] = (byte)(this.value >>> 8 & 0xFF);
/*  525 */       buffer[this.myOffset + 2] = (byte)(this.value >>> 0 & 0xFF);
/*      */     }
/*      */   }
/*      */   
/*      */   protected static final class UInt32Item
/*      */     extends Item {
/*      */     public int value;
/*      */     
/*      */     public UInt32Item(int value) {
/*  534 */       this.value = value;
/*      */     }
/*      */     
/*      */     public void increment(int[] currentOffset) {
/*  538 */       super.increment(currentOffset);
/*  539 */       currentOffset[0] = currentOffset[0] + 4;
/*      */     }
/*      */ 
/*      */     
/*      */     public void emit(byte[] buffer) {
/*  544 */       buffer[this.myOffset + 0] = (byte)(this.value >>> 24 & 0xFF);
/*  545 */       buffer[this.myOffset + 1] = (byte)(this.value >>> 16 & 0xFF);
/*  546 */       buffer[this.myOffset + 2] = (byte)(this.value >>> 8 & 0xFF);
/*  547 */       buffer[this.myOffset + 3] = (byte)(this.value >>> 0 & 0xFF);
/*      */     }
/*      */   }
/*      */   
/*      */   protected static final class UInt16Item
/*      */     extends Item {
/*      */     public char value;
/*      */     
/*      */     public UInt16Item(char value) {
/*  556 */       this.value = value;
/*      */     }
/*      */     
/*      */     public void increment(int[] currentOffset) {
/*  560 */       super.increment(currentOffset);
/*  561 */       currentOffset[0] = currentOffset[0] + 2;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void emit(byte[] buffer) {
/*  569 */       buffer[this.myOffset + 0] = (byte)(this.value >> 8 & 0xFF);
/*  570 */       buffer[this.myOffset + 1] = (byte)(this.value >> 0 & 0xFF);
/*      */     }
/*      */   }
/*      */   
/*      */   protected static final class UInt8Item
/*      */     extends Item {
/*      */     public char value;
/*      */     
/*      */     public UInt8Item(char value) {
/*  579 */       this.value = value;
/*      */     }
/*      */     
/*      */     public void increment(int[] currentOffset) {
/*  583 */       super.increment(currentOffset);
/*  584 */       currentOffset[0] = currentOffset[0] + 1;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public void emit(byte[] buffer) {
/*  590 */       buffer[this.myOffset + 0] = (byte)(this.value & 0xFF);
/*      */     } }
/*      */   
/*      */   protected static final class StringItem extends Item { public String s;
/*      */     
/*      */     public StringItem(String s) {
/*  596 */       this.s = s;
/*      */     }
/*      */     
/*      */     public void increment(int[] currentOffset) {
/*  600 */       super.increment(currentOffset);
/*  601 */       currentOffset[0] = currentOffset[0] + this.s.length();
/*      */     }
/*      */     
/*      */     public void emit(byte[] buffer) {
/*  605 */       for (int i = 0; i < this.s.length(); i++) {
/*  606 */         buffer[this.myOffset + i] = (byte)(this.s.charAt(i) & 0xFF);
/*      */       }
/*      */     } }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected static final class DictNumberItem
/*      */     extends Item
/*      */   {
/*      */     public final int value;
/*      */     
/*  618 */     public int size = 5; public DictNumberItem(int value) {
/*  619 */       this.value = value;
/*      */     }
/*      */     public void increment(int[] currentOffset) {
/*  622 */       super.increment(currentOffset);
/*  623 */       currentOffset[0] = currentOffset[0] + this.size;
/*      */     }
/*      */ 
/*      */     
/*      */     public void emit(byte[] buffer) {
/*  628 */       if (this.size == 5) {
/*  629 */         buffer[this.myOffset] = 29;
/*  630 */         buffer[this.myOffset + 1] = (byte)(this.value >>> 24 & 0xFF);
/*  631 */         buffer[this.myOffset + 2] = (byte)(this.value >>> 16 & 0xFF);
/*  632 */         buffer[this.myOffset + 3] = (byte)(this.value >>> 8 & 0xFF);
/*  633 */         buffer[this.myOffset + 4] = (byte)(this.value >>> 0 & 0xFF);
/*      */       } 
/*      */     }
/*      */   }
/*      */   
/*      */   protected static final class MarkerItem
/*      */     extends Item
/*      */   {
/*      */     CFFFont.OffsetItem p;
/*      */     
/*      */     public MarkerItem(CFFFont.OffsetItem pointerToMarker) {
/*  644 */       this.p = pointerToMarker;
/*      */     }
/*      */     public void xref() {
/*  647 */       this.p.set(this.myOffset);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected RangeItem getEntireIndexRange(int indexOffset) {
/*  658 */     seek(indexOffset);
/*  659 */     int count = getCard16();
/*  660 */     if (count == 0) {
/*  661 */       return new RangeItem(this.buf, indexOffset, 2);
/*      */     }
/*  663 */     int indexOffSize = getCard8();
/*  664 */     seek(indexOffset + 2 + 1 + count * indexOffSize);
/*  665 */     int size = getOffset(indexOffSize) - 1;
/*  666 */     return new RangeItem(this.buf, indexOffset, 3 + (count + 1) * indexOffSize + size);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public byte[] getCID(String fontName) {
/*      */     int j;
/*  688 */     for (j = 0; j < this.fonts.length && 
/*  689 */       !fontName.equals((this.fonts[j]).name); j++);
/*  690 */     if (j == this.fonts.length) return null;
/*      */     
/*  692 */     LinkedList<Item> l = new LinkedList<>();
/*      */ 
/*      */ 
/*      */     
/*  696 */     seek(0);
/*      */     
/*  698 */     int major = getCard8();
/*  699 */     int minor = getCard8();
/*  700 */     int hdrSize = getCard8();
/*  701 */     int offSize = getCard8();
/*  702 */     this.nextIndexOffset = hdrSize;
/*      */     
/*  704 */     l.addLast(new RangeItem(this.buf, 0, hdrSize));
/*      */     
/*  706 */     int nglyphs = -1, nstrings = -1;
/*  707 */     if (!(this.fonts[j]).isCID) {
/*      */       
/*  709 */       seek((this.fonts[j]).charstringsOffset);
/*  710 */       nglyphs = getCard16();
/*  711 */       seek(this.stringIndexOffset);
/*  712 */       nstrings = getCard16() + standardStrings.length;
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  719 */     l.addLast(new UInt16Item('\001'));
/*      */     
/*  721 */     l.addLast(new UInt8Item('\001'));
/*      */     
/*  723 */     l.addLast(new UInt8Item('\001'));
/*  724 */     l.addLast(new UInt8Item((char)(1 + (this.fonts[j]).name.length())));
/*  725 */     l.addLast(new StringItem((this.fonts[j]).name));
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  730 */     l.addLast(new UInt16Item('\001'));
/*      */     
/*  732 */     l.addLast(new UInt8Item('\002'));
/*      */     
/*  734 */     l.addLast(new UInt16Item('\001'));
/*  735 */     OffsetItem topdictIndex1Ref = new IndexOffsetItem(2);
/*  736 */     l.addLast(topdictIndex1Ref);
/*  737 */     IndexBaseItem topdictBase = new IndexBaseItem();
/*  738 */     l.addLast(topdictBase);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  750 */     OffsetItem charsetRef = new DictOffsetItem();
/*  751 */     OffsetItem charstringsRef = new DictOffsetItem();
/*  752 */     OffsetItem fdarrayRef = new DictOffsetItem();
/*  753 */     OffsetItem fdselectRef = new DictOffsetItem();
/*      */     
/*  755 */     if (!(this.fonts[j]).isCID) {
/*      */ 
/*      */       
/*  758 */       l.addLast(new DictNumberItem(nstrings));
/*  759 */       l.addLast(new DictNumberItem(nstrings + 1));
/*  760 */       l.addLast(new DictNumberItem(0));
/*  761 */       l.addLast(new UInt8Item('\f'));
/*  762 */       l.addLast(new UInt8Item('\036'));
/*      */ 
/*      */       
/*  765 */       l.addLast(new DictNumberItem(nglyphs));
/*  766 */       l.addLast(new UInt8Item('\f'));
/*  767 */       l.addLast(new UInt8Item('"'));
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  774 */     l.addLast(fdarrayRef);
/*  775 */     l.addLast(new UInt8Item('\f'));
/*  776 */     l.addLast(new UInt8Item('$'));
/*      */ 
/*      */     
/*  779 */     l.addLast(fdselectRef);
/*  780 */     l.addLast(new UInt8Item('\f'));
/*  781 */     l.addLast(new UInt8Item('%'));
/*      */ 
/*      */     
/*  784 */     l.addLast(charsetRef);
/*  785 */     l.addLast(new UInt8Item('\017'));
/*      */ 
/*      */     
/*  788 */     l.addLast(charstringsRef);
/*  789 */     l.addLast(new UInt8Item('\021'));
/*      */     
/*  791 */     seek(this.topdictOffsets[j]);
/*  792 */     while (getPosition() < this.topdictOffsets[j + 1]) {
/*  793 */       int p1 = getPosition();
/*  794 */       getDictItem();
/*  795 */       int p2 = getPosition();
/*  796 */       if ("Encoding".equals(this.key) || "Private"
/*  797 */         .equals(this.key) || "FDSelect"
/*  798 */         .equals(this.key) || "FDArray"
/*  799 */         .equals(this.key) || "charset"
/*  800 */         .equals(this.key) || "CharStrings"
/*  801 */         .equals(this.key)) {
/*      */         continue;
/*      */       }
/*      */ 
/*      */       
/*  806 */       l.addLast(new RangeItem(this.buf, p1, p2 - p1));
/*      */     } 
/*      */ 
/*      */     
/*  810 */     l.addLast(new IndexMarkerItem(topdictIndex1Ref, topdictBase));
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  816 */     if ((this.fonts[j]).isCID) {
/*  817 */       l.addLast(getEntireIndexRange(this.stringIndexOffset));
/*      */     } else {
/*  819 */       byte stringsIndexOffSize; String fdFontName = (this.fonts[j]).name + "-OneRange";
/*  820 */       if (fdFontName.length() > 127)
/*  821 */         fdFontName = fdFontName.substring(0, 127); 
/*  822 */       String extraStrings = "AdobeIdentity" + fdFontName;
/*      */       
/*  824 */       int origStringsLen = this.stringOffsets[this.stringOffsets.length - 1] - this.stringOffsets[0];
/*      */       
/*  826 */       int stringsBaseOffset = this.stringOffsets[0] - 1;
/*      */ 
/*      */       
/*  829 */       if (origStringsLen + extraStrings.length() <= 255) { stringsIndexOffSize = 1; }
/*  830 */       else if (origStringsLen + extraStrings.length() <= 65535) { stringsIndexOffSize = 2; }
/*  831 */       else if (origStringsLen + extraStrings.length() <= 16777215) { stringsIndexOffSize = 3; }
/*  832 */       else { stringsIndexOffSize = 4; }
/*      */ 
/*      */       
/*  835 */       l.addLast(new UInt16Item((char)(this.stringOffsets.length - 1 + 3)));
/*      */       
/*  837 */       l.addLast(new UInt8Item((char)stringsIndexOffSize));
/*  838 */       for (int stringOffset : this.stringOffsets) {
/*  839 */         l.addLast(new IndexOffsetItem(stringsIndexOffSize, stringOffset - stringsBaseOffset));
/*      */       }
/*  841 */       int currentStringsOffset = this.stringOffsets[this.stringOffsets.length - 1] - stringsBaseOffset;
/*      */ 
/*      */       
/*  844 */       currentStringsOffset += "Adobe".length();
/*  845 */       l.addLast(new IndexOffsetItem(stringsIndexOffSize, currentStringsOffset));
/*  846 */       currentStringsOffset += "Identity".length();
/*  847 */       l.addLast(new IndexOffsetItem(stringsIndexOffSize, currentStringsOffset));
/*  848 */       currentStringsOffset += fdFontName.length();
/*  849 */       l.addLast(new IndexOffsetItem(stringsIndexOffSize, currentStringsOffset));
/*      */       
/*  851 */       l.addLast(new RangeItem(this.buf, this.stringOffsets[0], origStringsLen));
/*  852 */       l.addLast(new StringItem(extraStrings));
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  857 */     l.addLast(getEntireIndexRange(this.gsubrIndexOffset));
/*      */ 
/*      */ 
/*      */     
/*  861 */     if (!(this.fonts[j]).isCID) {
/*      */ 
/*      */ 
/*      */       
/*  865 */       l.addLast(new MarkerItem(fdselectRef));
/*      */       
/*  867 */       l.addLast(new UInt8Item('\003'));
/*      */       
/*  869 */       l.addLast(new UInt16Item('\001'));
/*      */ 
/*      */       
/*  872 */       l.addLast(new UInt16Item(false));
/*      */       
/*  874 */       l.addLast(new UInt8Item(false));
/*      */ 
/*      */       
/*  877 */       l.addLast(new UInt16Item((char)nglyphs));
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  882 */       l.addLast(new MarkerItem(charsetRef));
/*      */       
/*  884 */       l.addLast(new UInt8Item('\002'));
/*      */ 
/*      */       
/*  887 */       l.addLast(new UInt16Item('\001'));
/*      */       
/*  889 */       l.addLast(new UInt16Item((char)(nglyphs - 1)));
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  894 */       l.addLast(new MarkerItem(fdarrayRef));
/*  895 */       l.addLast(new UInt16Item('\001'));
/*      */       
/*  897 */       l.addLast(new UInt8Item('\001'));
/*      */       
/*  899 */       l.addLast(new UInt8Item('\001'));
/*      */       
/*  901 */       OffsetItem privateIndex1Ref = new IndexOffsetItem(1);
/*  902 */       l.addLast(privateIndex1Ref);
/*  903 */       IndexBaseItem privateBase = new IndexBaseItem();
/*  904 */       l.addLast(privateBase);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  913 */       l.addLast(new DictNumberItem((this.fonts[j]).privateLength));
/*  914 */       OffsetItem privateRef = new DictOffsetItem();
/*  915 */       l.addLast(privateRef);
/*      */       
/*  917 */       l.addLast(new UInt8Item('\022'));
/*      */       
/*  919 */       l.addLast(new IndexMarkerItem(privateIndex1Ref, privateBase));
/*      */ 
/*      */ 
/*      */       
/*  923 */       l.addLast(new MarkerItem(privateRef));
/*      */ 
/*      */ 
/*      */       
/*  927 */       l.addLast(new RangeItem(this.buf, (this.fonts[j]).privateOffset, (this.fonts[j]).privateLength));
/*  928 */       if ((this.fonts[j]).privateSubrs >= 0)
/*      */       {
/*  930 */         l.addLast(getEntireIndexRange((this.fonts[j]).privateSubrs));
/*      */       }
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  936 */     l.addLast(new MarkerItem(charstringsRef));
/*  937 */     l.addLast(getEntireIndexRange((this.fonts[j]).charstringsOffset));
/*      */ 
/*      */ 
/*      */     
/*  941 */     int[] currentOffset = new int[1];
/*  942 */     currentOffset[0] = 0;
/*      */     
/*  944 */     for (Item item : l) {
/*  945 */       item.increment(currentOffset);
/*      */     }
/*      */     
/*  948 */     for (Item item : l) {
/*  949 */       item.xref();
/*      */     }
/*      */     
/*  952 */     int size = currentOffset[0];
/*  953 */     byte[] b = new byte[size];
/*      */     
/*  955 */     for (Item item : l) {
/*  956 */       item.emit(b);
/*      */     }
/*      */     
/*  959 */     return b;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isCID(String fontName) {
/*  965 */     for (int j = 0; j < this.fonts.length; j++) {
/*  966 */       if (fontName.equals((this.fonts[j]).name)) return (this.fonts[j]).isCID; 
/*  967 */     }  return false;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean exists(String fontName) {
/*  972 */     for (int j = 0; j < this.fonts.length; j++) {
/*  973 */       if (fontName.equals((this.fonts[j]).name)) return true; 
/*  974 */     }  return false;
/*      */   }
/*      */ 
/*      */   
/*      */   public String[] getNames() {
/*  979 */     String[] names = new String[this.fonts.length];
/*  980 */     for (int i = 0; i < this.fonts.length; i++)
/*  981 */       names[i] = (this.fonts[i]).name; 
/*  982 */     return names;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected final class Font
/*      */   {
/*      */     public String name;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public String fullName;
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean isCID = false;
/*      */ 
/*      */ 
/*      */     
/* 1004 */     public int privateOffset = -1;
/*      */     
/* 1006 */     public int privateLength = -1;
/* 1007 */     public int privateSubrs = -1;
/* 1008 */     public int charstringsOffset = -1;
/* 1009 */     public int encodingOffset = -1;
/* 1010 */     public int charsetOffset = -1;
/*      */     
/* 1012 */     public int fdarrayOffset = -1;
/*      */     
/* 1014 */     public int fdselectOffset = -1;
/*      */     
/*      */     public int[] fdprivateOffsets;
/*      */     
/*      */     public int[] fdprivateLengths;
/*      */     public int[] fdprivateSubrs;
/*      */     public int nglyphs;
/*      */     public int nstrings;
/*      */     public int CharsetLength;
/*      */     public int[] charstringsOffsets;
/*      */     public int[] charset;
/*      */     public int[] FDSelect;
/*      */     public int FDSelectLength;
/*      */     public int FDSelectFormat;
/* 1028 */     public int CharstringType = 2;
/*      */     
/*      */     public int FDArrayCount;
/*      */     
/*      */     public int FDArrayOffsize;
/*      */     
/*      */     public int[] FDArrayOffsets;
/*      */     public int[] PrivateSubrsOffset;
/*      */     public int[][] PrivateSubrsOffsetsArray;
/*      */     public int[] SubrsOffsets;
/*      */   }
/* 1039 */   RandomAccessSourceFactory rasFactory = new RandomAccessSourceFactory();
/*      */ 
/*      */   
/*      */   public CFFFont(byte[] cff) {
/* 1043 */     this.buf = new RandomAccessFileOrArray(this.rasFactory.createSource(cff));
/* 1044 */     seek(0);
/*      */ 
/*      */     
/* 1047 */     int major = getCard8();
/* 1048 */     int minor = getCard8();
/*      */ 
/*      */ 
/*      */     
/* 1052 */     int hdrSize = getCard8();
/*      */     
/* 1054 */     this.offSize = getCard8();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1060 */     this.nameIndexOffset = hdrSize;
/* 1061 */     this.nameOffsets = getIndex(this.nameIndexOffset);
/* 1062 */     this.topdictIndexOffset = this.nameOffsets[this.nameOffsets.length - 1];
/* 1063 */     this.topdictOffsets = getIndex(this.topdictIndexOffset);
/* 1064 */     this.stringIndexOffset = this.topdictOffsets[this.topdictOffsets.length - 1];
/* 1065 */     this.stringOffsets = getIndex(this.stringIndexOffset);
/* 1066 */     this.gsubrIndexOffset = this.stringOffsets[this.stringOffsets.length - 1];
/* 1067 */     this.gsubrOffsets = getIndex(this.gsubrIndexOffset);
/*      */     
/* 1069 */     this.fonts = new Font[this.nameOffsets.length - 1];
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     int j;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1083 */     for (j = 0; j < this.nameOffsets.length - 1; j++) {
/* 1084 */       this.fonts[j] = new Font();
/* 1085 */       seek(this.nameOffsets[j]);
/* 1086 */       (this.fonts[j]).name = "";
/* 1087 */       for (int k = this.nameOffsets[j]; k < this.nameOffsets[j + 1]; k++) {
/* 1088 */         (this.fonts[j]).name += getCard8();
/*      */       }
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1112 */     for (j = 0; j < this.topdictOffsets.length - 1; j++) {
/* 1113 */       seek(this.topdictOffsets[j]);
/* 1114 */       while (getPosition() < this.topdictOffsets[j + 1]) {
/* 1115 */         getDictItem();
/* 1116 */         if (this.key == "FullName") {
/*      */           
/* 1118 */           (this.fonts[j]).fullName = getString((char)((Integer)this.args[0]).intValue()); continue;
/*      */         } 
/* 1120 */         if (this.key == "ROS") {
/* 1121 */           (this.fonts[j]).isCID = true; continue;
/* 1122 */         }  if (this.key == "Private") {
/* 1123 */           (this.fonts[j]).privateLength = ((Integer)this.args[0]).intValue();
/* 1124 */           (this.fonts[j]).privateOffset = ((Integer)this.args[1]).intValue(); continue;
/*      */         } 
/* 1126 */         if (this.key == "charset") {
/* 1127 */           (this.fonts[j]).charsetOffset = ((Integer)this.args[0]).intValue();
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*      */           continue;
/*      */         } 
/*      */ 
/*      */ 
/*      */         
/* 1137 */         if (this.key == "CharStrings") {
/* 1138 */           (this.fonts[j]).charstringsOffset = ((Integer)this.args[0]).intValue();
/*      */ 
/*      */           
/* 1141 */           int p = getPosition();
/* 1142 */           (this.fonts[j]).charstringsOffsets = getIndex((this.fonts[j]).charstringsOffset);
/* 1143 */           seek(p); continue;
/* 1144 */         }  if (this.key == "FDArray") {
/* 1145 */           (this.fonts[j]).fdarrayOffset = ((Integer)this.args[0]).intValue(); continue;
/* 1146 */         }  if (this.key == "FDSelect") {
/* 1147 */           (this.fonts[j]).fdselectOffset = ((Integer)this.args[0]).intValue(); continue;
/* 1148 */         }  if (this.key == "CharstringType") {
/* 1149 */           (this.fonts[j]).CharstringType = ((Integer)this.args[0]).intValue();
/*      */         }
/*      */       } 
/*      */       
/* 1153 */       if ((this.fonts[j]).privateOffset >= 0) {
/*      */         
/* 1155 */         seek((this.fonts[j]).privateOffset);
/* 1156 */         while (getPosition() < (this.fonts[j]).privateOffset + (this.fonts[j]).privateLength) {
/* 1157 */           getDictItem();
/* 1158 */           if (this.key == "Subrs")
/*      */           {
/*      */             
/* 1161 */             (this.fonts[j]).privateSubrs = ((Integer)this.args[0]).intValue() + (this.fonts[j]).privateOffset;
/*      */           }
/*      */         } 
/*      */       } 
/*      */       
/* 1166 */       if ((this.fonts[j]).fdarrayOffset >= 0) {
/* 1167 */         int[] fdarrayOffsets = getIndex((this.fonts[j]).fdarrayOffset);
/*      */         
/* 1169 */         (this.fonts[j]).fdprivateOffsets = new int[fdarrayOffsets.length - 1];
/* 1170 */         (this.fonts[j]).fdprivateLengths = new int[fdarrayOffsets.length - 1];
/*      */ 
/*      */ 
/*      */         
/* 1174 */         for (int k = 0; k < fdarrayOffsets.length - 1; k++) {
/* 1175 */           seek(fdarrayOffsets[k]);
/* 1176 */           while (getPosition() < fdarrayOffsets[k + 1]) {
/* 1177 */             getDictItem();
/* 1178 */             if (this.key == "Private") {
/* 1179 */               (this.fonts[j]).fdprivateLengths[k] = ((Integer)this.args[0]).intValue();
/* 1180 */               (this.fonts[j]).fdprivateOffsets[k] = ((Integer)this.args[1]).intValue();
/*      */             } 
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   void ReadEncoding(int nextIndexOffset) {
/* 1193 */     seek(nextIndexOffset);
/* 1194 */     int format = getCard8();
/*      */   }
/*      */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/io/font/CFFFont.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */