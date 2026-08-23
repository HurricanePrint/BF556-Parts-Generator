/*     */ package com.itextpdf.io.font.otf;
/*     */ 
/*     */ import com.itextpdf.io.util.TextUtil;
/*     */ import java.io.Serializable;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class GlyphLine
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 4689818013371677649L;
/*     */   public int start;
/*     */   public int end;
/*     */   public int idx;
/*     */   protected List<Glyph> glyphs;
/*     */   protected List<ActualText> actualText;
/*     */   
/*     */   public GlyphLine() {
/*  62 */     this.glyphs = new ArrayList<>();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public GlyphLine(List<Glyph> glyphs) {
/*  71 */     this.glyphs = glyphs;
/*  72 */     this.start = 0;
/*  73 */     this.end = glyphs.size();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public GlyphLine(List<Glyph> glyphs, int start, int end) {
/*  84 */     this.glyphs = glyphs;
/*  85 */     this.start = start;
/*  86 */     this.end = end;
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
/*     */   protected GlyphLine(List<Glyph> glyphs, List<ActualText> actualText, int start, int end) {
/*  98 */     this(glyphs, start, end);
/*  99 */     this.actualText = actualText;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public GlyphLine(GlyphLine other) {
/* 108 */     this.glyphs = other.glyphs;
/* 109 */     this.actualText = other.actualText;
/* 110 */     this.start = other.start;
/* 111 */     this.end = other.end;
/* 112 */     this.idx = other.idx;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public GlyphLine(GlyphLine other, int start, int end) {
/* 123 */     this.glyphs = other.glyphs.subList(start, end);
/* 124 */     if (other.actualText != null) {
/* 125 */       this.actualText = other.actualText.subList(start, end);
/*     */     }
/* 127 */     this.start = 0;
/* 128 */     this.end = end - start;
/* 129 */     other.idx -= start;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toUnicodeString(int start, int end) {
/* 140 */     ActualTextIterator iter = new ActualTextIterator(this, start, end);
/* 141 */     StringBuilder str = new StringBuilder();
/* 142 */     while (iter.hasNext()) {
/* 143 */       GlyphLinePart part = iter.next();
/* 144 */       if (part.actualText != null) {
/* 145 */         str.append(part.actualText); continue;
/*     */       } 
/* 147 */       for (int i = part.start; i < part.end; i++) {
/* 148 */         str.append(((Glyph)this.glyphs.get(i)).getUnicodeChars());
/*     */       }
/*     */     } 
/*     */     
/* 152 */     return str.toString();
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 157 */     return toUnicodeString(this.start, this.end);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public GlyphLine copy(int left, int right) {
/* 168 */     GlyphLine glyphLine = new GlyphLine();
/* 169 */     glyphLine.start = 0;
/* 170 */     glyphLine.end = right - left;
/* 171 */     glyphLine.glyphs = new ArrayList<>(this.glyphs.subList(left, right));
/* 172 */     glyphLine.actualText = (this.actualText == null) ? null : new ArrayList<>(this.actualText.subList(left, right));
/* 173 */     return glyphLine;
/*     */   }
/*     */   
/*     */   public Glyph get(int index) {
/* 177 */     return this.glyphs.get(index);
/*     */   }
/*     */   
/*     */   public Glyph set(int index, Glyph glyph) {
/* 181 */     return this.glyphs.set(index, glyph);
/*     */   }
/*     */   
/*     */   public void add(Glyph glyph) {
/* 185 */     this.glyphs.add(glyph);
/* 186 */     if (this.actualText != null) {
/* 187 */       this.actualText.add(null);
/*     */     }
/*     */   }
/*     */   
/*     */   public void add(int index, Glyph glyph) {
/* 192 */     this.glyphs.add(index, glyph);
/* 193 */     if (this.actualText != null) {
/* 194 */       this.actualText.add(index, null);
/*     */     }
/*     */   }
/*     */   
/*     */   public void setGlyphs(List<Glyph> replacementGlyphs) {
/* 199 */     this.glyphs = new ArrayList<>(replacementGlyphs);
/* 200 */     this.start = 0;
/* 201 */     this.end = replacementGlyphs.size();
/* 202 */     this.actualText = null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void add(GlyphLine other) {
/* 213 */     if (other.actualText != null) {
/* 214 */       if (this.actualText == null) {
/* 215 */         this.actualText = new ArrayList<>(this.glyphs.size());
/* 216 */         for (int i = 0; i < this.glyphs.size(); i++) {
/* 217 */           this.actualText.add(null);
/*     */         }
/*     */       } 
/* 220 */       this.actualText.addAll(other.actualText.subList(other.start, other.end));
/*     */     } 
/* 222 */     this.glyphs.addAll(other.glyphs.subList(other.start, other.end));
/* 223 */     if (null != this.actualText) {
/* 224 */       while (this.actualText.size() < this.glyphs.size()) {
/* 225 */         this.actualText.add(null);
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void replaceContent(GlyphLine other) {
/* 236 */     this.glyphs.clear();
/* 237 */     this.glyphs.addAll(other.glyphs);
/* 238 */     if (other.actualText != null) {
/* 239 */       if (this.actualText == null) {
/* 240 */         this.actualText = new ArrayList<>();
/*     */       } else {
/* 242 */         this.actualText.clear();
/*     */       } 
/* 244 */       this.actualText.addAll(other.actualText);
/*     */     } else {
/* 246 */       this.actualText = null;
/*     */     } 
/* 248 */     this.start = other.start;
/* 249 */     this.end = other.end;
/*     */   }
/*     */   
/*     */   public int size() {
/* 253 */     return this.glyphs.size();
/*     */   }
/*     */   
/*     */   public void substituteManyToOne(OpenTypeFontTableReader tableReader, int lookupFlag, int rightPartLen, int substitutionGlyphIndex) {
/* 257 */     OpenTableLookup.GlyphIndexer gidx = new OpenTableLookup.GlyphIndexer();
/* 258 */     gidx.line = this;
/* 259 */     gidx.idx = this.idx;
/*     */     
/* 261 */     StringBuilder chars = new StringBuilder();
/* 262 */     Glyph currentGlyph = this.glyphs.get(this.idx);
/* 263 */     if (currentGlyph.getChars() != null) {
/* 264 */       chars.append(currentGlyph.getChars());
/* 265 */     } else if (currentGlyph.hasValidUnicode()) {
/* 266 */       chars.append(TextUtil.convertFromUtf32(currentGlyph.getUnicode()));
/*     */     } 
/*     */     
/* 269 */     for (int j = 0; j < rightPartLen; j++) {
/* 270 */       gidx.nextGlyph(tableReader, lookupFlag);
/* 271 */       currentGlyph = this.glyphs.get(gidx.idx);
/* 272 */       if (currentGlyph.getChars() != null) {
/* 273 */         chars.append(currentGlyph.getChars());
/* 274 */       } else if (currentGlyph.hasValidUnicode()) {
/* 275 */         chars.append(TextUtil.convertFromUtf32(currentGlyph.getUnicode()));
/*     */       } 
/* 277 */       removeGlyph(gidx.idx--);
/*     */     } 
/* 279 */     char[] newChars = new char[chars.length()];
/* 280 */     chars.getChars(0, chars.length(), newChars, 0);
/* 281 */     Glyph newGlyph = tableReader.getGlyph(substitutionGlyphIndex);
/* 282 */     newGlyph.setChars(newChars);
/* 283 */     this.glyphs.set(this.idx, newGlyph);
/* 284 */     this.end -= rightPartLen;
/*     */   }
/*     */   
/*     */   public void substituteOneToOne(OpenTypeFontTableReader tableReader, int substitutionGlyphIndex) {
/* 288 */     Glyph oldGlyph = this.glyphs.get(this.idx);
/* 289 */     Glyph newGlyph = tableReader.getGlyph(substitutionGlyphIndex);
/* 290 */     if (oldGlyph.getChars() != null) {
/* 291 */       newGlyph.setChars(oldGlyph.getChars());
/* 292 */     } else if (newGlyph.hasValidUnicode()) {
/* 293 */       newGlyph.setChars(TextUtil.convertFromUtf32(newGlyph.getUnicode()));
/* 294 */     } else if (oldGlyph.hasValidUnicode()) {
/* 295 */       newGlyph.setChars(TextUtil.convertFromUtf32(oldGlyph.getUnicode()));
/*     */     } 
/* 297 */     this.glyphs.set(this.idx, newGlyph);
/*     */   }
/*     */ 
/*     */   
/*     */   public void substituteOneToMany(OpenTypeFontTableReader tableReader, int[] substGlyphIds) {
/* 302 */     int substCode = substGlyphIds[0];
/* 303 */     Glyph oldGlyph = this.glyphs.get(this.idx);
/* 304 */     Glyph glyph = tableReader.getGlyph(substCode);
/* 305 */     this.glyphs.set(this.idx, glyph);
/*     */     
/* 307 */     if (substGlyphIds.length > 1) {
/* 308 */       List<Glyph> additionalGlyphs = new ArrayList<>(substGlyphIds.length - 1); int i;
/* 309 */       for (i = 1; i < substGlyphIds.length; i++) {
/* 310 */         substCode = substGlyphIds[i];
/* 311 */         glyph = tableReader.getGlyph(substCode);
/* 312 */         additionalGlyphs.add(glyph);
/*     */       } 
/* 314 */       addAllGlyphs(this.idx + 1, additionalGlyphs);
/* 315 */       if (null != this.actualText) {
/* 316 */         if (null == this.actualText.get(this.idx)) {
/* 317 */           this.actualText.set(this.idx, new ActualText(oldGlyph.getUnicodeString()));
/*     */         }
/* 319 */         for (i = 0; i < additionalGlyphs.size(); i++) {
/* 320 */           this.actualText.set(this.idx + 1 + i, this.actualText.get(this.idx));
/*     */         }
/*     */       } 
/* 323 */       this.idx += substGlyphIds.length - 1;
/* 324 */       this.end += substGlyphIds.length - 1;
/*     */     } 
/*     */   }
/*     */   
/*     */   public GlyphLine filter(IGlyphLineFilter filter) {
/* 329 */     boolean anythingFiltered = false;
/* 330 */     List<Glyph> filteredGlyphs = new ArrayList<>(this.end - this.start);
/* 331 */     List<ActualText> filteredActualText = (this.actualText != null) ? new ArrayList<>(this.end - this.start) : null;
/* 332 */     for (int i = this.start; i < this.end; i++) {
/* 333 */       if (filter.accept(this.glyphs.get(i))) {
/* 334 */         filteredGlyphs.add(this.glyphs.get(i));
/* 335 */         if (filteredActualText != null) {
/* 336 */           filteredActualText.add(this.actualText.get(i));
/*     */         }
/*     */       } else {
/* 339 */         anythingFiltered = true;
/*     */       } 
/*     */     } 
/* 342 */     if (anythingFiltered) {
/* 343 */       return new GlyphLine(filteredGlyphs, filteredActualText, 0, filteredGlyphs.size());
/*     */     }
/* 345 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setActualText(int left, int right, String text) {
/* 350 */     if (this.actualText == null) {
/* 351 */       this.actualText = new ArrayList<>(this.glyphs.size());
/* 352 */       for (int j = 0; j < this.glyphs.size(); j++)
/* 353 */         this.actualText.add(null); 
/*     */     } 
/* 355 */     ActualText actualText = new ActualText(text);
/* 356 */     for (int i = left; i < right; i++) {
/* 357 */       this.actualText.set(i, actualText);
/*     */     }
/*     */   }
/*     */   
/*     */   public Iterator<GlyphLinePart> iterator() {
/* 362 */     return new ActualTextIterator(this);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/* 367 */     if (this == obj) {
/* 368 */       return true;
/*     */     }
/* 370 */     if (obj == null || getClass() != obj.getClass()) {
/* 371 */       return false;
/*     */     }
/* 373 */     GlyphLine other = (GlyphLine)obj;
/* 374 */     if (this.end - this.start != other.end - other.start) {
/* 375 */       return false;
/*     */     }
/* 377 */     if ((this.actualText == null && other.actualText != null) || (this.actualText != null && other.actualText == null)) {
/* 378 */       return false;
/*     */     }
/* 380 */     for (int i = this.start; i < this.end; i++) {
/* 381 */       int otherPos = other.start + i - this.start;
/* 382 */       Glyph myGlyph = get(i);
/* 383 */       Glyph otherGlyph = other.get(otherPos);
/* 384 */       if ((myGlyph == null && otherGlyph != null) || (myGlyph != null && !myGlyph.equals(otherGlyph))) {
/* 385 */         return false;
/*     */       }
/* 387 */       ActualText myAT = (this.actualText == null) ? null : this.actualText.get(i);
/* 388 */       ActualText otherAT = (other.actualText == null) ? null : other.actualText.get(otherPos);
/* 389 */       if ((myAT == null && otherAT != null) || (myAT != null && !myAT.equals(otherAT))) {
/* 390 */         return false;
/*     */       }
/*     */     } 
/* 393 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 398 */     int result = 0;
/* 399 */     result = 31 * result + this.start;
/* 400 */     result = 31 * result + this.end; int i;
/* 401 */     for (i = this.start; i < this.end; i++) {
/* 402 */       result = 31 * result + ((Glyph)this.glyphs.get(i)).hashCode();
/*     */     }
/* 404 */     if (null != this.actualText) {
/* 405 */       for (i = this.start; i < this.end; i++) {
/* 406 */         result = 31 * result;
/* 407 */         if (null != this.actualText.get(i)) {
/* 408 */           result += ((ActualText)this.actualText.get(i)).hashCode();
/*     */         }
/*     */       } 
/*     */     }
/* 412 */     return result;
/*     */   }
/*     */   
/*     */   private void removeGlyph(int index) {
/* 416 */     this.glyphs.remove(index);
/* 417 */     if (this.actualText != null) {
/* 418 */       this.actualText.remove(index);
/*     */     }
/*     */   }
/*     */   
/*     */   private void addAllGlyphs(int index, List<Glyph> additionalGlyphs) {
/* 423 */     this.glyphs.addAll(index, additionalGlyphs);
/* 424 */     if (this.actualText != null)
/* 425 */       for (int i = 0; i < additionalGlyphs.size(); i++) {
/* 426 */         this.actualText.add(index, null);
/*     */       } 
/*     */   }
/*     */   
/*     */   public static interface IGlyphLineFilter
/*     */   {
/*     */     boolean accept(Glyph param1Glyph);
/*     */   }
/*     */   
/*     */   public static class GlyphLinePart
/*     */   {
/*     */     public int start;
/*     */     public int end;
/*     */     public String actualText;
/*     */     public boolean reversed;
/*     */     
/*     */     public GlyphLinePart(int start, int end) {
/* 443 */       this(start, end, null);
/*     */     }
/*     */     
/*     */     public GlyphLinePart(int start, int end, String actualText) {
/* 447 */       this.start = start;
/* 448 */       this.end = end;
/* 449 */       this.actualText = actualText;
/*     */     }
/*     */     
/*     */     public GlyphLinePart setReversed(boolean reversed) {
/* 453 */       this.reversed = reversed;
/* 454 */       return this;
/*     */     }
/*     */   }
/*     */   
/*     */   protected static class ActualText implements Serializable {
/*     */     private static final long serialVersionUID = 5109920013485372966L;
/*     */     public String value;
/*     */     
/*     */     public ActualText(String value) {
/* 463 */       this.value = value;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean equals(Object obj) {
/* 468 */       if (this == obj) {
/* 469 */         return true;
/*     */       }
/* 471 */       if (obj == null || getClass() != obj.getClass()) {
/* 472 */         return false;
/*     */       }
/* 474 */       ActualText other = (ActualText)obj;
/* 475 */       return ((this.value == null && other.value == null) || this.value.equals(other.value));
/*     */     }
/*     */ 
/*     */     
/*     */     public int hashCode() {
/* 480 */       return 31 * this.value.hashCode();
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/io/font/otf/GlyphLine.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */