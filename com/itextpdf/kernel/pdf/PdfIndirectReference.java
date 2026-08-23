/*     */ package com.itextpdf.kernel.pdf;
/*     */ 
/*     */ import com.itextpdf.io.util.MessageFormatUtil;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class PdfIndirectReference
/*     */   extends PdfObject
/*     */   implements Comparable<PdfIndirectReference>
/*     */ {
/*     */   private static final long serialVersionUID = -8293603068792908601L;
/*     */   private static final int LENGTH_OF_INDIRECTS_CHAIN = 31;
/*     */   protected final int objNr;
/*     */   protected int genNr;
/*  66 */   protected PdfObject refersTo = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  72 */   protected int objectStreamNumber = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  78 */   protected long offsetOrIndex = 0L;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  83 */   protected PdfDocument pdfDocument = null;
/*     */   
/*     */   protected PdfIndirectReference(PdfDocument doc, int objNr) {
/*  86 */     this(doc, objNr, 0);
/*     */   }
/*     */ 
/*     */   
/*     */   protected PdfIndirectReference(PdfDocument doc, int objNr, int genNr) {
/*  91 */     this.pdfDocument = doc;
/*  92 */     this.objNr = objNr;
/*  93 */     this.genNr = genNr;
/*     */   }
/*     */ 
/*     */   
/*     */   protected PdfIndirectReference(PdfDocument doc, int objNr, int genNr, long offset) {
/*  98 */     this.pdfDocument = doc;
/*  99 */     this.objNr = objNr;
/* 100 */     this.genNr = genNr;
/* 101 */     this.offsetOrIndex = offset;
/* 102 */     assert offset >= 0L;
/*     */   }
/*     */   
/*     */   public int getObjNumber() {
/* 106 */     return this.objNr;
/*     */   }
/*     */   
/*     */   public int getGenNumber() {
/* 110 */     return this.genNr;
/*     */   }
/*     */   
/*     */   public PdfObject getRefersTo() {
/* 114 */     return getRefersTo(true);
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
/*     */   public PdfObject getRefersTo(boolean recursively) {
/* 127 */     if (!recursively) {
/* 128 */       if (this.refersTo == null && !checkState((short)1) && !checkState((short)8) && !checkState((short)2) && getReader() != null) {
/* 129 */         this.refersTo = getReader().readObject(this);
/*     */       }
/* 131 */       return this.refersTo;
/*     */     } 
/* 133 */     PdfObject currentRefersTo = getRefersTo(false);
/* 134 */     for (int i = 0; i < 31 && 
/* 135 */       currentRefersTo instanceof PdfIndirectReference; i++) {
/* 136 */       currentRefersTo = ((PdfIndirectReference)currentRefersTo).getRefersTo(false);
/*     */     }
/*     */ 
/*     */     
/* 140 */     return currentRefersTo;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void setRefersTo(PdfObject refersTo) {
/* 145 */     this.refersTo = refersTo;
/*     */   }
/*     */   
/*     */   public int getObjStreamNumber() {
/* 149 */     return this.objectStreamNumber;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long getOffset() {
/* 158 */     return (this.objectStreamNumber == 0) ? this.offsetOrIndex : -1L;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getIndex() {
/* 167 */     return (this.objectStreamNumber == 0) ? -1 : (int)this.offsetOrIndex;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object o) {
/* 172 */     if (this == o) return true; 
/* 173 */     if (o == null || getClass() != o.getClass()) return false;
/*     */     
/* 175 */     PdfIndirectReference that = (PdfIndirectReference)o;
/*     */     
/* 177 */     return (this.objNr == that.objNr && this.genNr == that.genNr);
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 182 */     int result = this.objNr;
/* 183 */     result = 31 * result + this.genNr;
/* 184 */     return result;
/*     */   }
/*     */ 
/*     */   
/*     */   public int compareTo(PdfIndirectReference o) {
/* 189 */     if (this.objNr == o.objNr) {
/* 190 */       if (this.genNr == o.genNr)
/* 191 */         return 0; 
/* 192 */       return (this.genNr > o.genNr) ? 1 : -1;
/*     */     } 
/* 194 */     return (this.objNr > o.objNr) ? 1 : -1;
/*     */   }
/*     */ 
/*     */   
/*     */   public byte getType() {
/* 199 */     return 5;
/*     */   }
/*     */   
/*     */   public PdfDocument getDocument() {
/* 203 */     return this.pdfDocument;
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
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setFree() {
/* 220 */     getDocument().getXref().freeReference(this);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isFree() {
/* 230 */     return checkState((short)2);
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 235 */     StringBuilder states = new StringBuilder(" ");
/* 236 */     if (checkState((short)2)) {
/* 237 */       states.append("Free; ");
/*     */     }
/* 239 */     if (checkState((short)8)) {
/* 240 */       states.append("Modified; ");
/*     */     }
/* 242 */     if (checkState((short)32)) {
/* 243 */       states.append("MustBeFlushed; ");
/*     */     }
/* 245 */     if (checkState((short)4)) {
/* 246 */       states.append("Reading; ");
/*     */     }
/* 248 */     if (checkState((short)1)) {
/* 249 */       states.append("Flushed; ");
/*     */     }
/* 251 */     if (checkState((short)16)) {
/* 252 */       states.append("OriginalObjectStream; ");
/*     */     }
/* 254 */     if (checkState((short)128)) {
/* 255 */       states.append("ForbidRelease; ");
/*     */     }
/* 257 */     if (checkState((short)256)) {
/* 258 */       states.append("ReadOnly; ");
/*     */     }
/* 260 */     return MessageFormatUtil.format("{0} {1} R{2}", new Object[] { Integer.toString(getObjNumber()), Integer.toString(getGenNumber()), states.substring(0, states.length() - 1) });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected PdfWriter getWriter() {
/* 269 */     if (getDocument() != null)
/* 270 */       return getDocument().getWriter(); 
/* 271 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected PdfReader getReader() {
/* 280 */     if (getDocument() != null)
/* 281 */       return getDocument().getReader(); 
/* 282 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   protected PdfObject newInstance() {
/* 287 */     return PdfNull.PDF_NULL;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void copyContent(PdfObject from, PdfDocument document) {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected PdfObject setState(short state) {
/* 300 */     return super.setState(state);
/*     */   }
/*     */   
/*     */   void setObjStreamNumber(int objectStreamNumber) {
/* 304 */     this.objectStreamNumber = objectStreamNumber;
/*     */   }
/*     */   
/*     */   void setIndex(long index) {
/* 308 */     this.offsetOrIndex = index;
/*     */   }
/*     */   
/*     */   void setOffset(long offset) {
/* 312 */     this.offsetOrIndex = offset;
/* 313 */     this.objectStreamNumber = 0;
/*     */   }
/*     */   
/*     */   void fixOffset(long offset) {
/* 317 */     if (!isFree())
/* 318 */       this.offsetOrIndex = offset; 
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/kernel/pdf/PdfIndirectReference.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */