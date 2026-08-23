/*      */ package com.itextpdf.io.font;
/*      */ 
/*      */ import com.itextpdf.io.IOException;
/*      */ import com.itextpdf.io.source.RandomAccessFileOrArray;
/*      */ import com.itextpdf.io.util.GenericArray;
/*      */ import java.io.IOException;
/*      */ import java.util.ArrayList;
/*      */ import java.util.HashSet;
/*      */ import java.util.LinkedList;
/*      */ import java.util.List;
/*      */ import java.util.Set;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class CFFFontSubset
/*      */   extends CFFFont
/*      */ {
/*   72 */   static final String[] SubrsFunctions = new String[] { "RESERVED_0", "hstem", "RESERVED_2", "vstem", "vmoveto", "rlineto", "hlineto", "vlineto", "rrcurveto", "RESERVED_9", "callsubr", "return", "escape", "RESERVED_13", "endchar", "RESERVED_15", "RESERVED_16", "RESERVED_17", "hstemhm", "hintmask", "cntrmask", "rmoveto", "hmoveto", "vstemhm", "rcurveline", "rlinecurve", "vvcurveto", "hhcurveto", "shortint", "callgsubr", "vhcurveto", "hvcurveto" };
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*   82 */   static final String[] SubrsEscapeFuncs = new String[] { "RESERVED_0", "RESERVED_1", "RESERVED_2", "and", "or", "not", "RESERVED_6", "RESERVED_7", "RESERVED_8", "abs", "add", "sub", "div", "RESERVED_13", "neg", "eq", "RESERVED_16", "RESERVED_17", "drop", "RESERVED_19", "put", "get", "ifelse", "random", "mul", "RESERVED_25", "sqrt", "dup", "exch", "index", "roll", "RESERVED_31", "RESERVED_32", "RESERVED_33", "hflex", "flex", "hflex1", "flex1", "RESERVED_REST" };
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static final byte ENDCHAR_OP = 14;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static final byte RETURN_OP = 11;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   Set<Integer> GlyphsUsed;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   List<Integer> glyphsInList;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  108 */   Set<Integer> FDArrayUsed = new HashSet<>();
/*      */ 
/*      */ 
/*      */   
/*      */   GenericArray<Set<Integer>> hSubrsUsed;
/*      */ 
/*      */ 
/*      */   
/*      */   GenericArray<List<Integer>> lSubrsUsed;
/*      */ 
/*      */ 
/*      */   
/*  120 */   Set<Integer> hGSubrsUsed = new HashSet<>();
/*      */ 
/*      */ 
/*      */   
/*  124 */   List<Integer> lGSubrsUsed = new ArrayList<>();
/*      */ 
/*      */ 
/*      */   
/*  128 */   Set<Integer> hSubrsUsedNonCID = new HashSet<>();
/*      */ 
/*      */ 
/*      */   
/*  132 */   List<Integer> lSubrsUsedNonCID = new ArrayList<>();
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   byte[][] NewLSubrsIndex;
/*      */ 
/*      */ 
/*      */   
/*      */   byte[] NewSubrsIndexNonCID;
/*      */ 
/*      */ 
/*      */   
/*      */   byte[] NewGSubrsIndex;
/*      */ 
/*      */ 
/*      */   
/*      */   byte[] NewCharStringsIndex;
/*      */ 
/*      */ 
/*      */   
/*  153 */   int GBias = 0;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   LinkedList<CFFFont.Item> OutputList;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  163 */   int NumOfHints = 0;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public CFFFontSubset(byte[] cff, Set<Integer> GlyphsUsed) {
/*  173 */     super(cff);
/*  174 */     this.GlyphsUsed = GlyphsUsed;
/*      */     
/*  176 */     this.glyphsInList = new ArrayList<>(GlyphsUsed);
/*      */     
/*  178 */     for (int i = 0; i < this.fonts.length; i++) {
/*      */       
/*  180 */       seek((this.fonts[i]).charstringsOffset);
/*  181 */       (this.fonts[i]).nglyphs = getCard16();
/*      */ 
/*      */       
/*  184 */       seek(this.stringIndexOffset);
/*  185 */       (this.fonts[i]).nstrings = getCard16() + standardStrings.length;
/*      */ 
/*      */       
/*  188 */       (this.fonts[i]).charstringsOffsets = getIndex((this.fonts[i]).charstringsOffset);
/*      */ 
/*      */       
/*  191 */       if ((this.fonts[i]).fdselectOffset >= 0) {
/*      */         
/*  193 */         readFDSelect(i);
/*      */         
/*  195 */         BuildFDArrayUsed(i);
/*      */       } 
/*  197 */       if ((this.fonts[i]).isCID)
/*      */       {
/*  199 */         ReadFDArray(i);
/*      */       }
/*  201 */       (this.fonts[i]).CharsetLength = CountCharset((this.fonts[i]).charsetOffset, (this.fonts[i]).nglyphs);
/*      */     } 
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
/*      */   int CountCharset(int Offset, int NumofGlyphs) {
/*  214 */     int Length = 0;
/*  215 */     seek(Offset);
/*      */     
/*  217 */     int format = getCard8();
/*      */     
/*  219 */     switch (format) {
/*      */       case 0:
/*  221 */         Length = 1 + 2 * NumofGlyphs;
/*      */         break;
/*      */       case 1:
/*  224 */         Length = 1 + 3 * CountRange(NumofGlyphs, 1);
/*      */         break;
/*      */       case 2:
/*  227 */         Length = 1 + 4 * CountRange(NumofGlyphs, 2);
/*      */         break;
/*      */     } 
/*      */ 
/*      */     
/*  232 */     return Length;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   int CountRange(int NumofGlyphs, int Type) {
/*  243 */     int num = 0;
/*      */     
/*  245 */     int i = 1;
/*  246 */     while (i < NumofGlyphs) {
/*  247 */       int nLeft; num++;
/*  248 */       char Sid = getCard16();
/*  249 */       if (Type == 1) {
/*  250 */         nLeft = getCard8();
/*      */       } else {
/*  252 */         nLeft = getCard16();
/*  253 */       }  i += nLeft + 1;
/*      */     } 
/*  255 */     return num;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void readFDSelect(int Font) {
/*  266 */     int i, nRanges, l, first, j, NumOfGlyphs = (this.fonts[Font]).nglyphs;
/*  267 */     int[] FDSelect = new int[NumOfGlyphs];
/*      */     
/*  269 */     seek((this.fonts[Font]).fdselectOffset);
/*      */     
/*  271 */     (this.fonts[Font]).FDSelectFormat = getCard8();
/*      */     
/*  273 */     switch ((this.fonts[Font]).FDSelectFormat) {
/*      */ 
/*      */       
/*      */       case 0:
/*  277 */         for (i = 0; i < NumOfGlyphs; i++) {
/*  278 */           FDSelect[i] = getCard8();
/*      */         }
/*      */ 
/*      */         
/*  282 */         (this.fonts[Font]).FDSelectLength = (this.fonts[Font]).nglyphs + 1;
/*      */         break;
/*      */ 
/*      */       
/*      */       case 3:
/*  287 */         nRanges = getCard16();
/*  288 */         l = 0;
/*      */         
/*  290 */         first = getCard16();
/*  291 */         for (j = 0; j < nRanges; j++) {
/*      */           
/*  293 */           int fd = getCard8();
/*      */           
/*  295 */           int last = getCard16();
/*      */           
/*  297 */           int steps = last - first;
/*  298 */           for (int k = 0; k < steps; k++) {
/*  299 */             FDSelect[l] = fd;
/*  300 */             l++;
/*      */           } 
/*      */           
/*  303 */           first = last;
/*      */         } 
/*      */         
/*  306 */         (this.fonts[Font]).FDSelectLength = 3 + nRanges * 3 + 2;
/*      */         break;
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  312 */     (this.fonts[Font]).FDSelect = FDSelect;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void BuildFDArrayUsed(int Font) {
/*  321 */     int[] FDSelect = (this.fonts[Font]).FDSelect;
/*      */     
/*  323 */     for (Integer glyphsInList1 : this.glyphsInList) {
/*      */       
/*  325 */       int glyph = glyphsInList1.intValue();
/*      */       
/*  327 */       int FD = FDSelect[glyph];
/*      */       
/*  329 */       this.FDArrayUsed.add(Integer.valueOf(FD));
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void ReadFDArray(int Font) {
/*  339 */     seek((this.fonts[Font]).fdarrayOffset);
/*  340 */     (this.fonts[Font]).FDArrayCount = getCard16();
/*  341 */     (this.fonts[Font]).FDArrayOffsize = getCard8();
/*      */ 
/*      */     
/*  344 */     if ((this.fonts[Font]).FDArrayOffsize < 4)
/*  345 */       (this.fonts[Font]).FDArrayOffsize++; 
/*  346 */     (this.fonts[Font]).FDArrayOffsets = getIndex((this.fonts[Font]).fdarrayOffset);
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
/*      */   public byte[] Process(String fontName) {
/*      */     try {
/*      */       int j;
/*  361 */       for (j = 0; j < this.fonts.length && 
/*  362 */         !fontName.equals((this.fonts[j]).name); j++);
/*  363 */       if (j == this.fonts.length) return null;
/*      */ 
/*      */       
/*  366 */       if (this.gsubrIndexOffset >= 0) {
/*  367 */         this.GBias = CalcBias(this.gsubrIndexOffset, j);
/*      */       }
/*      */       
/*  370 */       BuildNewCharString(j);
/*      */       
/*  372 */       BuildNewLGSubrs(j);
/*      */       
/*  374 */       return BuildNewFile(j);
/*  375 */     } catch (IOException e) {
/*  376 */       throw new IOException("I/O exception.", e);
/*      */     } finally {
/*      */       try {
/*  379 */         this.buf.close();
/*  380 */       } catch (Exception exception) {}
/*      */     } 
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
/*      */   public byte[] Process() {
/*  393 */     return Process(getNames()[0]);
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
/*      */   protected int CalcBias(int Offset, int Font) {
/*  405 */     seek(Offset);
/*  406 */     int nSubrs = getCard16();
/*      */     
/*  408 */     if ((this.fonts[Font]).CharstringType == 1) {
/*  409 */       return 0;
/*      */     }
/*  411 */     if (nSubrs < 1240)
/*  412 */       return 107; 
/*  413 */     if (nSubrs < 33900) {
/*  414 */       return 1131;
/*      */     }
/*  416 */     return 32768;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void BuildNewCharString(int FontIndex) throws IOException {
/*  426 */     this.NewCharStringsIndex = BuildNewIndex((this.fonts[FontIndex]).charstringsOffsets, this.GlyphsUsed, (byte)14);
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
/*      */   protected void BuildNewLGSubrs(int Font) throws IOException {
/*  440 */     if ((this.fonts[Font]).isCID) {
/*      */ 
/*      */       
/*  443 */       this.hSubrsUsed = new GenericArray((this.fonts[Font]).fdprivateOffsets.length);
/*  444 */       this.lSubrsUsed = new GenericArray((this.fonts[Font]).fdprivateOffsets.length);
/*      */       
/*  446 */       this.NewLSubrsIndex = new byte[(this.fonts[Font]).fdprivateOffsets.length][];
/*      */       
/*  448 */       (this.fonts[Font]).PrivateSubrsOffset = new int[(this.fonts[Font]).fdprivateOffsets.length];
/*      */       
/*  450 */       (this.fonts[Font]).PrivateSubrsOffsetsArray = new int[(this.fonts[Font]).fdprivateOffsets.length][];
/*      */ 
/*      */       
/*  453 */       List<Integer> FDInList = new ArrayList<>(this.FDArrayUsed);
/*      */       
/*  455 */       for (int j = 0; j < FDInList.size(); j++)
/*      */       {
/*  457 */         int FD = ((Integer)FDInList.get(j)).intValue();
/*  458 */         this.hSubrsUsed.set(FD, new HashSet());
/*  459 */         this.lSubrsUsed.set(FD, new ArrayList());
/*      */ 
/*      */         
/*  462 */         BuildFDSubrsOffsets(Font, FD);
/*      */         
/*  464 */         if ((this.fonts[Font]).PrivateSubrsOffset[FD] >= 0)
/*      */         {
/*      */           
/*  467 */           BuildSubrUsed(Font, FD, (this.fonts[Font]).PrivateSubrsOffset[FD], (this.fonts[Font]).PrivateSubrsOffsetsArray[FD], (Set<Integer>)this.hSubrsUsed.get(FD), (List<Integer>)this.lSubrsUsed.get(FD));
/*      */           
/*  469 */           this.NewLSubrsIndex[FD] = BuildNewIndex((this.fonts[Font]).PrivateSubrsOffsetsArray[FD], (Set<Integer>)this.hSubrsUsed.get(FD), (byte)11);
/*      */         }
/*      */       
/*      */       }
/*      */     
/*  474 */     } else if ((this.fonts[Font]).privateSubrs >= 0) {
/*      */       
/*  476 */       (this.fonts[Font]).SubrsOffsets = getIndex((this.fonts[Font]).privateSubrs);
/*      */ 
/*      */       
/*  479 */       BuildSubrUsed(Font, -1, (this.fonts[Font]).privateSubrs, (this.fonts[Font]).SubrsOffsets, this.hSubrsUsedNonCID, this.lSubrsUsedNonCID);
/*      */     } 
/*      */ 
/*      */     
/*  483 */     BuildGSubrsUsed(Font);
/*  484 */     if ((this.fonts[Font]).privateSubrs >= 0)
/*      */     {
/*  486 */       this.NewSubrsIndexNonCID = BuildNewIndex((this.fonts[Font]).SubrsOffsets, this.hSubrsUsedNonCID, (byte)11);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  493 */     this.NewGSubrsIndex = BuildNewIndexAndCopyAllGSubrs(this.gsubrOffsets, (byte)11);
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
/*      */   protected void BuildFDSubrsOffsets(int Font, int FD) {
/*  505 */     (this.fonts[Font]).PrivateSubrsOffset[FD] = -1;
/*      */     
/*  507 */     seek((this.fonts[Font]).fdprivateOffsets[FD]);
/*      */     
/*  509 */     while (getPosition() < (this.fonts[Font]).fdprivateOffsets[FD] + (this.fonts[Font]).fdprivateLengths[FD]) {
/*  510 */       getDictItem();
/*      */       
/*  512 */       if ("Subrs".equals(this.key)) {
/*  513 */         (this.fonts[Font]).PrivateSubrsOffset[FD] = ((Integer)this.args[0]).intValue() + (this.fonts[Font]).fdprivateOffsets[FD];
/*      */       }
/*      */     } 
/*  516 */     if ((this.fonts[Font]).PrivateSubrsOffset[FD] >= 0) {
/*  517 */       (this.fonts[Font]).PrivateSubrsOffsetsArray[FD] = getIndex((this.fonts[Font]).PrivateSubrsOffset[FD]);
/*      */     }
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
/*      */   protected void BuildSubrUsed(int Font, int FD, int SubrOffset, int[] SubrsOffsets, Set<Integer> hSubr, List<Integer> lSubr) {
/*  535 */     int LBias = CalcBias(SubrOffset, Font);
/*      */     
/*      */     int i;
/*  538 */     for (i = 0; i < this.glyphsInList.size(); i++) {
/*  539 */       int glyph = ((Integer)this.glyphsInList.get(i)).intValue();
/*  540 */       int Start = (this.fonts[Font]).charstringsOffsets[glyph];
/*  541 */       int End = (this.fonts[Font]).charstringsOffsets[glyph + 1];
/*      */ 
/*      */       
/*  544 */       if (FD >= 0) {
/*  545 */         EmptyStack();
/*  546 */         this.NumOfHints = 0;
/*      */         
/*  548 */         int GlyphFD = (this.fonts[Font]).FDSelect[glyph];
/*      */         
/*  550 */         if (GlyphFD == FD)
/*      */         {
/*  552 */           ReadASubr(Start, End, this.GBias, LBias, hSubr, lSubr, SubrsOffsets);
/*      */         }
/*      */       } else {
/*      */         
/*  556 */         ReadASubr(Start, End, this.GBias, LBias, hSubr, lSubr, SubrsOffsets);
/*      */       } 
/*      */     } 
/*  559 */     for (i = 0; i < lSubr.size(); i++) {
/*      */       
/*  561 */       int Subr = ((Integer)lSubr.get(i)).intValue();
/*      */       
/*  563 */       if (Subr < SubrsOffsets.length - 1 && Subr >= 0) {
/*      */         
/*  565 */         int Start = SubrsOffsets[Subr];
/*  566 */         int End = SubrsOffsets[Subr + 1];
/*  567 */         ReadASubr(Start, End, this.GBias, LBias, hSubr, lSubr, SubrsOffsets);
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void BuildGSubrsUsed(int Font) {
/*  579 */     int LBias = 0;
/*  580 */     int SizeOfNonCIDSubrsUsed = 0;
/*  581 */     if ((this.fonts[Font]).privateSubrs >= 0) {
/*  582 */       LBias = CalcBias((this.fonts[Font]).privateSubrs, Font);
/*  583 */       SizeOfNonCIDSubrsUsed = this.lSubrsUsedNonCID.size();
/*      */     } 
/*      */ 
/*      */     
/*  587 */     for (int i = 0; i < this.lGSubrsUsed.size(); i++) {
/*      */       
/*  589 */       int Subr = ((Integer)this.lGSubrsUsed.get(i)).intValue();
/*  590 */       if (Subr < this.gsubrOffsets.length - 1 && Subr >= 0) {
/*      */         
/*  592 */         int Start = this.gsubrOffsets[Subr];
/*  593 */         int End = this.gsubrOffsets[Subr + 1];
/*      */         
/*  595 */         if ((this.fonts[Font]).isCID) {
/*  596 */           ReadASubr(Start, End, this.GBias, 0, this.hGSubrsUsed, this.lGSubrsUsed, (int[])null);
/*      */         } else {
/*  598 */           ReadASubr(Start, End, this.GBias, LBias, this.hSubrsUsedNonCID, this.lSubrsUsedNonCID, (this.fonts[Font]).SubrsOffsets);
/*  599 */           if (SizeOfNonCIDSubrsUsed < this.lSubrsUsedNonCID.size()) {
/*  600 */             for (int j = SizeOfNonCIDSubrsUsed; j < this.lSubrsUsedNonCID.size(); j++) {
/*      */               
/*  602 */               int LSubr = ((Integer)this.lSubrsUsedNonCID.get(j)).intValue();
/*  603 */               if (LSubr < (this.fonts[Font]).SubrsOffsets.length - 1 && LSubr >= 0) {
/*      */                 
/*  605 */                 int LStart = (this.fonts[Font]).SubrsOffsets[LSubr];
/*  606 */                 int LEnd = (this.fonts[Font]).SubrsOffsets[LSubr + 1];
/*  607 */                 ReadASubr(LStart, LEnd, this.GBias, LBias, this.hSubrsUsedNonCID, this.lSubrsUsedNonCID, (this.fonts[Font]).SubrsOffsets);
/*      */               } 
/*      */             } 
/*  610 */             SizeOfNonCIDSubrsUsed = this.lSubrsUsedNonCID.size();
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void ReadASubr(int begin, int end, int GBias, int LBias, Set<Integer> hSubr, List<Integer> lSubr, int[] LSubrsOffsets) {
/*  632 */     EmptyStack();
/*  633 */     this.NumOfHints = 0;
/*      */     
/*  635 */     seek(begin);
/*  636 */     while (getPosition() < end) {
/*      */       
/*  638 */       ReadCommand();
/*  639 */       int pos = getPosition();
/*  640 */       Object TopElement = null;
/*  641 */       if (this.arg_count > 0)
/*  642 */         TopElement = this.args[this.arg_count - 1]; 
/*  643 */       int NumOfArgs = this.arg_count;
/*      */       
/*  645 */       HandelStack();
/*  646 */       if (null != this.key) {
/*      */         int SizeOfMask; int i;
/*  648 */         switch (this.key) {
/*      */ 
/*      */           
/*      */           case "callsubr":
/*  652 */             if (NumOfArgs > 0) {
/*      */               
/*  654 */               int Subr = ((Integer)TopElement).intValue() + LBias;
/*      */               
/*  656 */               if (!hSubr.contains(Integer.valueOf(Subr))) {
/*  657 */                 hSubr.add(Integer.valueOf(Subr));
/*  658 */                 lSubr.add(Integer.valueOf(Subr));
/*      */               } 
/*  660 */               CalcHints(LSubrsOffsets[Subr], LSubrsOffsets[Subr + 1], LBias, GBias, LSubrsOffsets);
/*  661 */               seek(pos);
/*      */             } 
/*      */ 
/*      */ 
/*      */           
/*      */           case "callgsubr":
/*  667 */             if (NumOfArgs > 0) {
/*      */               
/*  669 */               int Subr = ((Integer)TopElement).intValue() + GBias;
/*      */               
/*  671 */               if (!this.hGSubrsUsed.contains(Integer.valueOf(Subr))) {
/*  672 */                 this.hGSubrsUsed.add(Integer.valueOf(Subr));
/*  673 */                 this.lGSubrsUsed.add(Integer.valueOf(Subr));
/*      */               } 
/*  675 */               CalcHints(this.gsubrOffsets[Subr], this.gsubrOffsets[Subr + 1], LBias, GBias, LSubrsOffsets);
/*  676 */               seek(pos);
/*      */             } 
/*      */ 
/*      */           
/*      */           case "hstem":
/*      */           case "vstem":
/*      */           case "hstemhm":
/*      */           case "vstemhm":
/*  684 */             this.NumOfHints += NumOfArgs / 2;
/*      */ 
/*      */ 
/*      */           
/*      */           case "hintmask":
/*      */           case "cntrmask":
/*  690 */             this.NumOfHints += NumOfArgs / 2;
/*      */             
/*  692 */             SizeOfMask = this.NumOfHints / 8;
/*  693 */             if (this.NumOfHints % 8 != 0 || SizeOfMask == 0) {
/*  694 */               SizeOfMask++;
/*      */             }
/*  696 */             for (i = 0; i < SizeOfMask; i++) {
/*  697 */               getCard8();
/*      */             }
/*      */         } 
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void HandelStack() {
/*  710 */     int StackHandel = StackOpp();
/*  711 */     if (StackHandel < 2) {
/*      */       
/*  713 */       if (StackHandel == 1) {
/*  714 */         PushStack();
/*      */       }
/*      */       else {
/*      */         
/*  718 */         StackHandel *= -1;
/*  719 */         for (int i = 0; i < StackHandel; i++) {
/*  720 */           PopStack();
/*      */         }
/*      */       }
/*      */     
/*      */     } else {
/*      */       
/*  726 */       EmptyStack();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected int StackOpp() {
/*  735 */     switch (this.key) {
/*      */       case "ifelse":
/*  737 */         return -3;
/*      */       case "roll":
/*      */       case "put":
/*  740 */         return -2;
/*      */       case "callsubr":
/*      */       case "callgsubr":
/*      */       case "add":
/*      */       case "sub":
/*      */       case "div":
/*      */       case "mul":
/*      */       case "drop":
/*      */       case "and":
/*      */       case "or":
/*      */       case "eq":
/*  751 */         return -1;
/*      */       case "abs":
/*      */       case "neg":
/*      */       case "sqrt":
/*      */       case "exch":
/*      */       case "index":
/*      */       case "get":
/*      */       case "not":
/*      */       case "return":
/*  760 */         return 0;
/*      */       case "random":
/*      */       case "dup":
/*  763 */         return 1;
/*      */     } 
/*  765 */     return 2;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void EmptyStack() {
/*  773 */     for (int i = 0; i < this.arg_count; ) { this.args[i] = null; i++; }
/*  774 */      this.arg_count = 0;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void PopStack() {
/*  781 */     if (this.arg_count > 0) {
/*  782 */       this.args[this.arg_count - 1] = null;
/*  783 */       this.arg_count--;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void PushStack() {
/*  791 */     this.arg_count++;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void ReadCommand() {
/*  798 */     this.key = null;
/*  799 */     boolean gotKey = false;
/*      */ 
/*      */     
/*  802 */     while (!gotKey) {
/*      */ 
/*      */       
/*  805 */       char b0 = getCard8();
/*      */ 
/*      */       
/*  808 */       if (b0 == '\034') {
/*      */ 
/*      */ 
/*      */         
/*  812 */         int first = getCard8();
/*  813 */         int second = getCard8();
/*  814 */         this.args[this.arg_count] = Integer.valueOf(first << 8 | second);
/*  815 */         this.arg_count++;
/*      */         
/*      */         continue;
/*      */       } 
/*      */       
/*  820 */       if (b0 >= ' ' && b0 <= 'ö') {
/*      */         
/*  822 */         this.args[this.arg_count] = Integer.valueOf(b0 - 139);
/*  823 */         this.arg_count++;
/*      */         
/*      */         continue;
/*      */       } 
/*      */       
/*  828 */       if (b0 >= '÷' && b0 <= 'ú') {
/*      */         
/*  830 */         int w = getCard8();
/*  831 */         this.args[this.arg_count] = Integer.valueOf((b0 - 247) * 256 + w + 108);
/*  832 */         this.arg_count++;
/*      */         
/*      */         continue;
/*      */       } 
/*      */       
/*  837 */       if (b0 >= 'û' && b0 <= 'þ') {
/*      */         
/*  839 */         int w = getCard8();
/*  840 */         this.args[this.arg_count] = Integer.valueOf(-(b0 - 251) * 256 - w - 108);
/*  841 */         this.arg_count++;
/*      */         
/*      */         continue;
/*      */       } 
/*      */       
/*  846 */       if (b0 == 'ÿ') {
/*      */         
/*  848 */         int first = getCard8();
/*  849 */         int second = getCard8();
/*  850 */         int third = getCard8();
/*  851 */         int fourth = getCard8();
/*  852 */         this.args[this.arg_count] = Integer.valueOf(first << 24 | second << 16 | third << 8 | fourth);
/*  853 */         this.arg_count++;
/*      */         
/*      */         continue;
/*      */       } 
/*      */       
/*  858 */       if (b0 <= '\037' && b0 != '\034') {
/*      */         
/*  860 */         gotKey = true;
/*      */ 
/*      */ 
/*      */         
/*  864 */         if (b0 == '\f') {
/*  865 */           int b1 = getCard8();
/*  866 */           if (b1 > SubrsEscapeFuncs.length - 1)
/*  867 */             b1 = SubrsEscapeFuncs.length - 1; 
/*  868 */           this.key = SubrsEscapeFuncs[b1]; continue;
/*      */         } 
/*  870 */         this.key = SubrsFunctions[b0];
/*      */       } 
/*      */     } 
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
/*      */   protected int CalcHints(int begin, int end, int LBias, int GBias, int[] LSubrsOffsets) {
/*  889 */     seek(begin);
/*  890 */     while (getPosition() < end) {
/*      */       int SizeOfMask, i;
/*  892 */       ReadCommand();
/*  893 */       int pos = getPosition();
/*  894 */       Object TopElement = null;
/*  895 */       if (this.arg_count > 0)
/*  896 */         TopElement = this.args[this.arg_count - 1]; 
/*  897 */       int NumOfArgs = this.arg_count;
/*      */       
/*  899 */       HandelStack();
/*      */       
/*  901 */       switch (this.key) {
/*      */         
/*      */         case "callsubr":
/*  904 */           if (NumOfArgs > 0) {
/*  905 */             assert TopElement instanceof Integer;
/*  906 */             int Subr = ((Integer)TopElement).intValue() + LBias;
/*  907 */             CalcHints(LSubrsOffsets[Subr], LSubrsOffsets[Subr + 1], LBias, GBias, LSubrsOffsets);
/*  908 */             seek(pos);
/*      */           } 
/*      */ 
/*      */         
/*      */         case "callgsubr":
/*  913 */           if (NumOfArgs > 0) {
/*  914 */             assert TopElement instanceof Integer;
/*  915 */             int Subr = ((Integer)TopElement).intValue() + GBias;
/*  916 */             CalcHints(this.gsubrOffsets[Subr], this.gsubrOffsets[Subr + 1], LBias, GBias, LSubrsOffsets);
/*  917 */             seek(pos);
/*      */           } 
/*      */ 
/*      */         
/*      */         case "hstem":
/*      */         case "vstem":
/*      */         case "hstemhm":
/*      */         case "vstemhm":
/*  925 */           this.NumOfHints += NumOfArgs / 2;
/*      */ 
/*      */         
/*      */         case "hintmask":
/*      */         case "cntrmask":
/*  930 */           SizeOfMask = this.NumOfHints / 8;
/*  931 */           if (this.NumOfHints % 8 != 0 || SizeOfMask == 0) {
/*  932 */             SizeOfMask++;
/*      */           }
/*  934 */           for (i = 0; i < SizeOfMask; i++) {
/*  935 */             getCard8();
/*      */           }
/*      */       } 
/*      */     
/*      */     } 
/*  940 */     return this.NumOfHints;
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
/*      */   protected byte[] BuildNewIndex(int[] Offsets, Set<Integer> Used, byte OperatorForUnusedEntries) throws IOException {
/*  955 */     int unusedCount = 0;
/*  956 */     int Offset = 0;
/*  957 */     int[] NewOffsets = new int[Offsets.length];
/*      */     
/*  959 */     for (int i = 0; i < Offsets.length; i++) {
/*  960 */       NewOffsets[i] = Offset;
/*      */ 
/*      */       
/*  963 */       if (Used.contains(Integer.valueOf(i))) {
/*  964 */         Offset += Offsets[i + 1] - Offsets[i];
/*      */       } else {
/*      */         
/*  967 */         unusedCount++;
/*      */       } 
/*      */     } 
/*      */     
/*  971 */     byte[] NewObjects = new byte[Offset + unusedCount];
/*      */     
/*  973 */     int unusedOffset = 0;
/*  974 */     for (int j = 0; j < Offsets.length - 1; j++) {
/*  975 */       int start = NewOffsets[j];
/*  976 */       int end = NewOffsets[j + 1];
/*  977 */       NewOffsets[j] = start + unusedOffset;
/*      */ 
/*      */       
/*  980 */       if (start != end) {
/*      */ 
/*      */         
/*  983 */         this.buf.seek(Offsets[j]);
/*      */         
/*  985 */         this.buf.readFully(NewObjects, start + unusedOffset, end - start);
/*      */       } else {
/*  987 */         NewObjects[start + unusedOffset] = OperatorForUnusedEntries;
/*  988 */         unusedOffset++;
/*      */       } 
/*      */     } 
/*  991 */     NewOffsets[Offsets.length - 1] = NewOffsets[Offsets.length - 1] + unusedOffset;
/*      */     
/*  993 */     return AssembleIndex(NewOffsets, NewObjects);
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
/*      */   protected byte[] BuildNewIndexAndCopyAllGSubrs(int[] Offsets, byte OperatorForUnusedEntries) throws IOException {
/* 1006 */     int unusedCount = 0;
/* 1007 */     int Offset = 0;
/* 1008 */     int[] NewOffsets = new int[Offsets.length];
/*      */     
/* 1010 */     for (int i = 0; i < Offsets.length - 1; i++) {
/* 1011 */       NewOffsets[i] = Offset;
/* 1012 */       Offset += Offsets[i + 1] - Offsets[i];
/*      */     } 
/*      */     
/* 1015 */     NewOffsets[Offsets.length - 1] = Offset;
/* 1016 */     unusedCount++;
/*      */ 
/*      */     
/* 1019 */     byte[] NewObjects = new byte[Offset + unusedCount];
/*      */     
/* 1021 */     int unusedOffset = 0;
/* 1022 */     for (int j = 0; j < Offsets.length - 1; j++) {
/* 1023 */       int start = NewOffsets[j];
/* 1024 */       int end = NewOffsets[j + 1];
/* 1025 */       NewOffsets[j] = start + unusedOffset;
/*      */ 
/*      */       
/* 1028 */       if (start != end) {
/*      */ 
/*      */         
/* 1031 */         this.buf.seek(Offsets[j]);
/*      */         
/* 1033 */         this.buf.readFully(NewObjects, start + unusedOffset, end - start);
/*      */       } else {
/* 1035 */         NewObjects[start + unusedOffset] = OperatorForUnusedEntries;
/* 1036 */         unusedOffset++;
/*      */       } 
/*      */     } 
/* 1039 */     NewOffsets[Offsets.length - 1] = NewOffsets[Offsets.length - 1] + unusedOffset;
/*      */     
/* 1041 */     return AssembleIndex(NewOffsets, NewObjects);
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
/*      */   protected byte[] AssembleIndex(int[] NewOffsets, byte[] NewObjects) {
/*      */     byte Offsize;
/* 1055 */     char Count = (char)(NewOffsets.length - 1);
/*      */     
/* 1057 */     int Size = NewOffsets[NewOffsets.length - 1];
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1062 */     if (Size < 255) {
/* 1063 */       Offsize = 1;
/* 1064 */     } else if (Size < 65535) {
/* 1065 */       Offsize = 2;
/* 1066 */     } else if (Size < 16777215) {
/* 1067 */       Offsize = 3;
/*      */     } else {
/* 1069 */       Offsize = 4;
/*      */     } 
/*      */ 
/*      */     
/* 1073 */     byte[] NewIndex = new byte[3 + Offsize * (Count + 1) + NewObjects.length];
/*      */     
/* 1075 */     int Place = 0;
/*      */ 
/*      */ 
/*      */     
/* 1079 */     NewIndex[Place++] = (byte)(Count >> 8 & 0xFF);
/* 1080 */     NewIndex[Place++] = (byte)(Count & 0xFF);
/*      */     
/* 1082 */     NewIndex[Place++] = Offsize;
/*      */     
/* 1084 */     for (int newOffset : NewOffsets) {
/*      */       
/* 1086 */       int Num = newOffset - NewOffsets[0] + 1;
/*      */       
/* 1088 */       for (int i = Offsize; i > 0; i--) {
/* 1089 */         NewIndex[Place++] = (byte)(Num >>> i - 1 << 3 & 0xFF);
/*      */       }
/*      */     } 
/*      */     
/* 1093 */     for (byte newObject : NewObjects) {
/* 1094 */       NewIndex[Place++] = newObject;
/*      */     }
/*      */     
/* 1097 */     return NewIndex;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected byte[] BuildNewFile(int Font) {
/* 1108 */     this.OutputList = new LinkedList<>();
/*      */ 
/*      */     
/* 1111 */     CopyHeader();
/*      */ 
/*      */     
/* 1114 */     BuildIndexHeader(1, 1, 1);
/* 1115 */     this.OutputList.addLast(new CFFFont.UInt8Item((char)(1 + (this.fonts[Font]).name.length())));
/* 1116 */     this.OutputList.addLast(new CFFFont.StringItem((this.fonts[Font]).name));
/*      */ 
/*      */     
/* 1119 */     BuildIndexHeader(1, 2, 1);
/* 1120 */     CFFFont.OffsetItem topdictIndex1Ref = new CFFFont.IndexOffsetItem(2);
/* 1121 */     this.OutputList.addLast(topdictIndex1Ref);
/* 1122 */     CFFFont.IndexBaseItem topdictBase = new CFFFont.IndexBaseItem();
/* 1123 */     this.OutputList.addLast(topdictBase);
/*      */ 
/*      */     
/* 1126 */     CFFFont.OffsetItem charsetRef = new CFFFont.DictOffsetItem();
/* 1127 */     CFFFont.OffsetItem charstringsRef = new CFFFont.DictOffsetItem();
/* 1128 */     CFFFont.OffsetItem fdarrayRef = new CFFFont.DictOffsetItem();
/* 1129 */     CFFFont.OffsetItem fdselectRef = new CFFFont.DictOffsetItem();
/* 1130 */     CFFFont.OffsetItem privateRef = new CFFFont.DictOffsetItem();
/*      */ 
/*      */     
/* 1133 */     if (!(this.fonts[Font]).isCID) {
/*      */       
/* 1135 */       this.OutputList.addLast(new CFFFont.DictNumberItem((this.fonts[Font]).nstrings));
/* 1136 */       this.OutputList.addLast(new CFFFont.DictNumberItem((this.fonts[Font]).nstrings + 1));
/* 1137 */       this.OutputList.addLast(new CFFFont.DictNumberItem(0));
/* 1138 */       this.OutputList.addLast(new CFFFont.UInt8Item('\f'));
/* 1139 */       this.OutputList.addLast(new CFFFont.UInt8Item('\036'));
/*      */       
/* 1141 */       this.OutputList.addLast(new CFFFont.DictNumberItem((this.fonts[Font]).nglyphs));
/* 1142 */       this.OutputList.addLast(new CFFFont.UInt8Item('\f'));
/* 1143 */       this.OutputList.addLast(new CFFFont.UInt8Item('"'));
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1149 */     seek(this.topdictOffsets[Font]);
/*      */     
/* 1151 */     while (getPosition() < this.topdictOffsets[Font + 1]) {
/* 1152 */       int p1 = getPosition();
/* 1153 */       getDictItem();
/* 1154 */       int p2 = getPosition();
/*      */       
/* 1156 */       if ("Encoding".equals(this.key) || "Private"
/*      */         
/* 1158 */         .equals(this.key) || "FDSelect"
/* 1159 */         .equals(this.key) || "FDArray"
/* 1160 */         .equals(this.key) || "charset"
/* 1161 */         .equals(this.key) || "CharStrings"
/* 1162 */         .equals(this.key)) {
/*      */         continue;
/*      */       }
/*      */       
/* 1166 */       this.OutputList.addLast(new CFFFont.RangeItem(this.buf, p1, p2 - p1));
/*      */     } 
/*      */ 
/*      */     
/* 1170 */     CreateKeys(fdarrayRef, fdselectRef, charsetRef, charstringsRef);
/*      */ 
/*      */     
/* 1173 */     this.OutputList.addLast(new CFFFont.IndexMarkerItem(topdictIndex1Ref, topdictBase));
/*      */ 
/*      */ 
/*      */     
/* 1177 */     if ((this.fonts[Font]).isCID) {
/* 1178 */       this.OutputList.addLast(getEntireIndexRange(this.stringIndexOffset));
/*      */     
/*      */     }
/*      */     else {
/*      */       
/* 1183 */       CreateNewStringIndex(Font);
/*      */     } 
/*      */     
/* 1186 */     this.OutputList.addLast(new CFFFont.RangeItem(new RandomAccessFileOrArray(this.rasFactory.createSource(this.NewGSubrsIndex)), 0, this.NewGSubrsIndex.length));
/*      */ 
/*      */ 
/*      */     
/* 1190 */     if ((this.fonts[Font]).isCID) {
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1195 */       this.OutputList.addLast(new CFFFont.MarkerItem(fdselectRef));
/*      */       
/* 1197 */       if ((this.fonts[Font]).fdselectOffset >= 0) {
/* 1198 */         this.OutputList.addLast(new CFFFont.RangeItem(this.buf, (this.fonts[Font]).fdselectOffset, (this.fonts[Font]).FDSelectLength));
/*      */       } else {
/*      */         
/* 1201 */         CreateFDSelect(fdselectRef, (this.fonts[Font]).nglyphs);
/*      */       } 
/*      */ 
/*      */       
/* 1205 */       this.OutputList.addLast(new CFFFont.MarkerItem(charsetRef));
/* 1206 */       this.OutputList.addLast(new CFFFont.RangeItem(this.buf, (this.fonts[Font]).charsetOffset, (this.fonts[Font]).CharsetLength));
/*      */ 
/*      */ 
/*      */       
/* 1210 */       if ((this.fonts[Font]).fdarrayOffset >= 0) {
/*      */         
/* 1212 */         this.OutputList.addLast(new CFFFont.MarkerItem(fdarrayRef));
/*      */         
/* 1214 */         Reconstruct(Font);
/*      */       } else {
/*      */         
/* 1217 */         CreateFDArray(fdarrayRef, privateRef, Font);
/*      */       }
/*      */     
/*      */     }
/*      */     else {
/*      */       
/* 1223 */       CreateFDSelect(fdselectRef, (this.fonts[Font]).nglyphs);
/*      */       
/* 1225 */       CreateCharset(charsetRef, (this.fonts[Font]).nglyphs);
/*      */       
/* 1227 */       CreateFDArray(fdarrayRef, privateRef, Font);
/*      */     } 
/*      */ 
/*      */     
/* 1231 */     if ((this.fonts[Font]).privateOffset >= 0) {
/*      */       
/* 1233 */       CFFFont.IndexBaseItem PrivateBase = new CFFFont.IndexBaseItem();
/* 1234 */       this.OutputList.addLast(PrivateBase);
/* 1235 */       this.OutputList.addLast(new CFFFont.MarkerItem(privateRef));
/*      */       
/* 1237 */       CFFFont.OffsetItem Subr = new CFFFont.DictOffsetItem();
/*      */       
/* 1239 */       CreateNonCIDPrivate(Font, Subr);
/*      */       
/* 1241 */       CreateNonCIDSubrs(Font, PrivateBase, Subr);
/*      */     } 
/*      */ 
/*      */     
/* 1245 */     this.OutputList.addLast(new CFFFont.MarkerItem(charstringsRef));
/*      */ 
/*      */     
/* 1248 */     this.OutputList.addLast(new CFFFont.RangeItem(new RandomAccessFileOrArray(this.rasFactory.createSource(this.NewCharStringsIndex)), 0, this.NewCharStringsIndex.length));
/*      */ 
/*      */     
/* 1251 */     int[] currentOffset = new int[1];
/* 1252 */     currentOffset[0] = 0;
/*      */     
/* 1254 */     for (CFFFont.Item item : this.OutputList) {
/* 1255 */       item.increment(currentOffset);
/*      */     }
/*      */     
/* 1258 */     for (CFFFont.Item item : this.OutputList) {
/* 1259 */       item.xref();
/*      */     }
/*      */     
/* 1262 */     int size = currentOffset[0];
/* 1263 */     byte[] b = new byte[size];
/*      */ 
/*      */     
/* 1266 */     for (CFFFont.Item item : this.OutputList) {
/* 1267 */       item.emit(b);
/*      */     }
/*      */     
/* 1270 */     return b;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void CopyHeader() {
/* 1277 */     seek(0);
/* 1278 */     int major = getCard8();
/* 1279 */     int minor = getCard8();
/* 1280 */     int hdrSize = getCard8();
/* 1281 */     int offSize = getCard8();
/* 1282 */     this.nextIndexOffset = hdrSize;
/* 1283 */     this.OutputList.addLast(new CFFFont.RangeItem(this.buf, 0, hdrSize));
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
/*      */   protected void BuildIndexHeader(int Count, int Offsize, int First) {
/* 1295 */     this.OutputList.addLast(new CFFFont.UInt16Item((char)Count));
/*      */     
/* 1297 */     this.OutputList.addLast(new CFFFont.UInt8Item((char)Offsize));
/*      */     
/* 1299 */     switch (Offsize) {
/*      */       
/*      */       case 1:
/* 1302 */         this.OutputList.addLast(new CFFFont.UInt8Item((char)First));
/*      */         break;
/*      */       
/*      */       case 2:
/* 1306 */         this.OutputList.addLast(new CFFFont.UInt16Item((char)First));
/*      */         break;
/*      */       
/*      */       case 3:
/* 1310 */         this.OutputList.addLast(new CFFFont.UInt24Item((char)First));
/*      */         break;
/*      */       
/*      */       case 4:
/* 1314 */         this.OutputList.addLast(new CFFFont.UInt32Item((char)First));
/*      */         break;
/*      */     } 
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
/*      */   protected void CreateKeys(CFFFont.OffsetItem fdarrayRef, CFFFont.OffsetItem fdselectRef, CFFFont.OffsetItem charsetRef, CFFFont.OffsetItem charstringsRef) {
/* 1331 */     this.OutputList.addLast(fdarrayRef);
/* 1332 */     this.OutputList.addLast(new CFFFont.UInt8Item('\f'));
/* 1333 */     this.OutputList.addLast(new CFFFont.UInt8Item('$'));
/*      */     
/* 1335 */     this.OutputList.addLast(fdselectRef);
/* 1336 */     this.OutputList.addLast(new CFFFont.UInt8Item('\f'));
/* 1337 */     this.OutputList.addLast(new CFFFont.UInt8Item('%'));
/*      */     
/* 1339 */     this.OutputList.addLast(charsetRef);
/* 1340 */     this.OutputList.addLast(new CFFFont.UInt8Item('\017'));
/*      */     
/* 1342 */     this.OutputList.addLast(charstringsRef);
/* 1343 */     this.OutputList.addLast(new CFFFont.UInt8Item('\021'));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void CreateNewStringIndex(int Font) {
/*      */     byte stringsIndexOffSize;
/* 1353 */     String fdFontName = (this.fonts[Font]).name + "-OneRange";
/* 1354 */     if (fdFontName.length() > 127)
/* 1355 */       fdFontName = fdFontName.substring(0, 127); 
/* 1356 */     String extraStrings = "AdobeIdentity" + fdFontName;
/*      */     
/* 1358 */     int origStringsLen = this.stringOffsets[this.stringOffsets.length - 1] - this.stringOffsets[0];
/*      */     
/* 1360 */     int stringsBaseOffset = this.stringOffsets[0] - 1;
/*      */ 
/*      */     
/* 1363 */     if (origStringsLen + extraStrings.length() <= 255) { stringsIndexOffSize = 1; }
/* 1364 */     else if (origStringsLen + extraStrings.length() <= 65535) { stringsIndexOffSize = 2; }
/* 1365 */     else if (origStringsLen + extraStrings.length() <= 16777215) { stringsIndexOffSize = 3; }
/* 1366 */     else { stringsIndexOffSize = 4; }
/*      */ 
/*      */     
/* 1369 */     this.OutputList.addLast(new CFFFont.UInt16Item((char)(this.stringOffsets.length - 1 + 3)));
/*      */     
/* 1371 */     this.OutputList.addLast(new CFFFont.UInt8Item((char)stringsIndexOffSize));
/* 1372 */     for (int stringOffset : this.stringOffsets) {
/* 1373 */       this.OutputList.addLast(new CFFFont.IndexOffsetItem(stringsIndexOffSize, stringOffset - stringsBaseOffset));
/*      */     }
/* 1375 */     int currentStringsOffset = this.stringOffsets[this.stringOffsets.length - 1] - stringsBaseOffset;
/*      */ 
/*      */     
/* 1378 */     currentStringsOffset += "Adobe".length();
/* 1379 */     this.OutputList.addLast(new CFFFont.IndexOffsetItem(stringsIndexOffSize, currentStringsOffset));
/* 1380 */     currentStringsOffset += "Identity".length();
/* 1381 */     this.OutputList.addLast(new CFFFont.IndexOffsetItem(stringsIndexOffSize, currentStringsOffset));
/* 1382 */     currentStringsOffset += fdFontName.length();
/* 1383 */     this.OutputList.addLast(new CFFFont.IndexOffsetItem(stringsIndexOffSize, currentStringsOffset));
/*      */     
/* 1385 */     this.OutputList.addLast(new CFFFont.RangeItem(this.buf, this.stringOffsets[0], origStringsLen));
/* 1386 */     this.OutputList.addLast(new CFFFont.StringItem(extraStrings));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void CreateFDSelect(CFFFont.OffsetItem fdselectRef, int nglyphs) {
/* 1397 */     this.OutputList.addLast(new CFFFont.MarkerItem(fdselectRef));
/*      */     
/* 1399 */     this.OutputList.addLast(new CFFFont.UInt8Item('\003'));
/*      */     
/* 1401 */     this.OutputList.addLast(new CFFFont.UInt16Item('\001'));
/*      */ 
/*      */     
/* 1404 */     this.OutputList.addLast(new CFFFont.UInt16Item(false));
/*      */     
/* 1406 */     this.OutputList.addLast(new CFFFont.UInt8Item(false));
/*      */ 
/*      */     
/* 1409 */     this.OutputList.addLast(new CFFFont.UInt16Item((char)nglyphs));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void CreateCharset(CFFFont.OffsetItem charsetRef, int nglyphs) {
/* 1420 */     this.OutputList.addLast(new CFFFont.MarkerItem(charsetRef));
/*      */     
/* 1422 */     this.OutputList.addLast(new CFFFont.UInt8Item('\002'));
/*      */     
/* 1424 */     this.OutputList.addLast(new CFFFont.UInt16Item('\001'));
/*      */     
/* 1426 */     this.OutputList.addLast(new CFFFont.UInt16Item((char)(nglyphs - 1)));
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
/*      */   protected void CreateFDArray(CFFFont.OffsetItem fdarrayRef, CFFFont.OffsetItem privateRef, int Font) {
/* 1439 */     this.OutputList.addLast(new CFFFont.MarkerItem(fdarrayRef));
/*      */     
/* 1441 */     BuildIndexHeader(1, 1, 1);
/*      */ 
/*      */     
/* 1444 */     CFFFont.OffsetItem privateIndex1Ref = new CFFFont.IndexOffsetItem(1);
/* 1445 */     this.OutputList.addLast(privateIndex1Ref);
/* 1446 */     CFFFont.IndexBaseItem privateBase = new CFFFont.IndexBaseItem();
/*      */     
/* 1448 */     this.OutputList.addLast(privateBase);
/*      */ 
/*      */     
/* 1451 */     int NewSize = (this.fonts[Font]).privateLength;
/*      */     
/* 1453 */     int OrgSubrsOffsetSize = CalcSubrOffsetSize((this.fonts[Font]).privateOffset, (this.fonts[Font]).privateLength);
/*      */     
/* 1455 */     if (OrgSubrsOffsetSize != 0)
/* 1456 */       NewSize += 5 - OrgSubrsOffsetSize; 
/* 1457 */     this.OutputList.addLast(new CFFFont.DictNumberItem(NewSize));
/* 1458 */     this.OutputList.addLast(privateRef);
/*      */     
/* 1460 */     this.OutputList.addLast(new CFFFont.UInt8Item('\022'));
/*      */     
/* 1462 */     this.OutputList.addLast(new CFFFont.IndexMarkerItem(privateIndex1Ref, privateBase));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   void Reconstruct(int Font) {
/* 1472 */     CFFFont.DictOffsetItem[] arrayOfDictOffsetItem1 = new CFFFont.DictOffsetItem[(this.fonts[Font]).FDArrayOffsets.length - 1];
/* 1473 */     CFFFont.IndexBaseItem[] fdPrivateBase = new CFFFont.IndexBaseItem[(this.fonts[Font]).fdprivateOffsets.length];
/* 1474 */     CFFFont.DictOffsetItem[] arrayOfDictOffsetItem2 = new CFFFont.DictOffsetItem[(this.fonts[Font]).fdprivateOffsets.length];
/*      */     
/* 1476 */     ReconstructFDArray(Font, (CFFFont.OffsetItem[])arrayOfDictOffsetItem1);
/* 1477 */     ReconstructPrivateDict(Font, (CFFFont.OffsetItem[])arrayOfDictOffsetItem1, fdPrivateBase, (CFFFont.OffsetItem[])arrayOfDictOffsetItem2);
/* 1478 */     ReconstructPrivateSubrs(Font, fdPrivateBase, (CFFFont.OffsetItem[])arrayOfDictOffsetItem2);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   void ReconstructFDArray(int Font, CFFFont.OffsetItem[] fdPrivate) {
/* 1489 */     BuildIndexHeader((this.fonts[Font]).FDArrayCount, (this.fonts[Font]).FDArrayOffsize, 1);
/*      */ 
/*      */     
/* 1492 */     CFFFont.IndexOffsetItem[] arrayOfIndexOffsetItem = new CFFFont.IndexOffsetItem[(this.fonts[Font]).FDArrayOffsets.length - 1];
/* 1493 */     for (int i = 0; i < (this.fonts[Font]).FDArrayOffsets.length - 1; i++) {
/* 1494 */       arrayOfIndexOffsetItem[i] = new CFFFont.IndexOffsetItem((this.fonts[Font]).FDArrayOffsize);
/* 1495 */       this.OutputList.addLast(arrayOfIndexOffsetItem[i]);
/*      */     } 
/*      */ 
/*      */     
/* 1499 */     CFFFont.IndexBaseItem fdArrayBase = new CFFFont.IndexBaseItem();
/* 1500 */     this.OutputList.addLast(fdArrayBase);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1506 */     for (int k = 0; k < (this.fonts[Font]).FDArrayOffsets.length - 1; k++) {
/*      */ 
/*      */ 
/*      */       
/* 1510 */       seek((this.fonts[Font]).FDArrayOffsets[k]);
/* 1511 */       while (getPosition() < (this.fonts[Font]).FDArrayOffsets[k + 1]) {
/* 1512 */         int p1 = getPosition();
/* 1513 */         getDictItem();
/* 1514 */         int p2 = getPosition();
/*      */ 
/*      */         
/* 1517 */         if ("Private".equals(this.key)) {
/*      */           
/* 1519 */           int NewSize = ((Integer)this.args[0]).intValue();
/*      */           
/* 1521 */           int OrgSubrsOffsetSize = CalcSubrOffsetSize((this.fonts[Font]).fdprivateOffsets[k], (this.fonts[Font]).fdprivateLengths[k]);
/*      */           
/* 1523 */           if (OrgSubrsOffsetSize != 0) {
/* 1524 */             NewSize += 5 - OrgSubrsOffsetSize;
/*      */           }
/* 1526 */           this.OutputList.addLast(new CFFFont.DictNumberItem(NewSize));
/* 1527 */           fdPrivate[k] = new CFFFont.DictOffsetItem();
/* 1528 */           this.OutputList.addLast(fdPrivate[k]);
/*      */           
/* 1530 */           this.OutputList.addLast(new CFFFont.UInt8Item('\022'));
/*      */           
/* 1532 */           seek(p2);
/*      */           
/*      */           continue;
/*      */         } 
/* 1536 */         this.OutputList.addLast(new CFFFont.RangeItem(this.buf, p1, p2 - p1));
/*      */       } 
/*      */ 
/*      */       
/* 1540 */       this.OutputList.addLast(new CFFFont.IndexMarkerItem(arrayOfIndexOffsetItem[k], fdArrayBase));
/*      */     } 
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
/*      */   void ReconstructPrivateDict(int Font, CFFFont.OffsetItem[] fdPrivate, CFFFont.IndexBaseItem[] fdPrivateBase, CFFFont.OffsetItem[] fdSubrs) {
/* 1558 */     for (int i = 0; i < (this.fonts[Font]).fdprivateOffsets.length; i++) {
/*      */ 
/*      */ 
/*      */       
/* 1562 */       this.OutputList.addLast(new CFFFont.MarkerItem(fdPrivate[i]));
/* 1563 */       fdPrivateBase[i] = new CFFFont.IndexBaseItem();
/* 1564 */       this.OutputList.addLast(fdPrivateBase[i]);
/*      */       
/* 1566 */       seek((this.fonts[Font]).fdprivateOffsets[i]);
/* 1567 */       while (getPosition() < (this.fonts[Font]).fdprivateOffsets[i] + (this.fonts[Font]).fdprivateLengths[i]) {
/* 1568 */         int p1 = getPosition();
/* 1569 */         getDictItem();
/* 1570 */         int p2 = getPosition();
/*      */ 
/*      */         
/* 1573 */         if ("Subrs".equals(this.key)) {
/* 1574 */           fdSubrs[i] = new CFFFont.DictOffsetItem();
/* 1575 */           this.OutputList.addLast(fdSubrs[i]);
/*      */           
/* 1577 */           this.OutputList.addLast(new CFFFont.UInt8Item('\023'));
/*      */           
/*      */           continue;
/*      */         } 
/* 1581 */         this.OutputList.addLast(new CFFFont.RangeItem(this.buf, p1, p2 - p1));
/*      */       } 
/*      */     } 
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
/*      */   void ReconstructPrivateSubrs(int Font, CFFFont.IndexBaseItem[] fdPrivateBase, CFFFont.OffsetItem[] fdSubrs) {
/* 1598 */     for (int i = 0; i < (this.fonts[Font]).fdprivateLengths.length; i++) {
/*      */ 
/*      */       
/* 1601 */       if (fdSubrs[i] != null && (this.fonts[Font]).PrivateSubrsOffset[i] >= 0) {
/* 1602 */         this.OutputList.addLast(new CFFFont.SubrMarkerItem(fdSubrs[i], fdPrivateBase[i]));
/* 1603 */         if (this.NewLSubrsIndex[i] != null) {
/* 1604 */           this.OutputList.addLast(new CFFFont.RangeItem(new RandomAccessFileOrArray(this.rasFactory.createSource(this.NewLSubrsIndex[i])), 0, (this.NewLSubrsIndex[i]).length));
/*      */         }
/*      */       } 
/*      */     } 
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
/*      */   int CalcSubrOffsetSize(int Offset, int Size) {
/* 1619 */     int OffsetSize = 0;
/*      */     
/* 1621 */     seek(Offset);
/*      */     
/* 1623 */     while (getPosition() < Offset + Size) {
/* 1624 */       int p1 = getPosition();
/* 1625 */       getDictItem();
/* 1626 */       int p2 = getPosition();
/*      */       
/* 1628 */       if ("Subrs".equals(this.key))
/*      */       {
/* 1630 */         OffsetSize = p2 - p1 - 1;
/*      */       }
/*      */     } 
/*      */ 
/*      */     
/* 1635 */     return OffsetSize;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected int countEntireIndexRange(int indexOffset) {
/* 1646 */     seek(indexOffset);
/*      */     
/* 1648 */     int count = getCard16();
/*      */     
/* 1650 */     if (count == 0) {
/* 1651 */       return 2;
/*      */     }
/*      */     
/* 1654 */     int indexOffSize = getCard8();
/*      */     
/* 1656 */     seek(indexOffset + 2 + 1 + count * indexOffSize);
/*      */     
/* 1658 */     int size = getOffset(indexOffSize) - 1;
/*      */     
/* 1660 */     return 3 + (count + 1) * indexOffSize + size;
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
/*      */   void CreateNonCIDPrivate(int Font, CFFFont.OffsetItem Subr) {
/* 1673 */     seek((this.fonts[Font]).privateOffset);
/* 1674 */     while (getPosition() < (this.fonts[Font]).privateOffset + (this.fonts[Font]).privateLength) {
/* 1675 */       int p1 = getPosition();
/* 1676 */       getDictItem();
/* 1677 */       int p2 = getPosition();
/*      */ 
/*      */       
/* 1680 */       if ("Subrs".equals(this.key)) {
/* 1681 */         this.OutputList.addLast(Subr);
/*      */         
/* 1683 */         this.OutputList.addLast(new CFFFont.UInt8Item('\023'));
/*      */         
/*      */         continue;
/*      */       } 
/* 1687 */       this.OutputList.addLast(new CFFFont.RangeItem(this.buf, p1, p2 - p1));
/*      */     } 
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
/*      */   void CreateNonCIDSubrs(int Font, CFFFont.IndexBaseItem PrivateBase, CFFFont.OffsetItem Subrs) {
/* 1701 */     this.OutputList.addLast(new CFFFont.SubrMarkerItem(Subrs, PrivateBase));
/*      */     
/* 1703 */     if (this.NewSubrsIndexNonCID != null)
/* 1704 */       this.OutputList.addLast(new CFFFont.RangeItem(new RandomAccessFileOrArray(this.rasFactory.createSource(this.NewSubrsIndexNonCID)), 0, this.NewSubrsIndexNonCID.length)); 
/*      */   }
/*      */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/io/font/CFFFontSubset.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */