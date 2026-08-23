/*     */ package com.itextpdf.kernel.pdf;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class PdfPages
/*     */   extends PdfObjectWrapper<PdfDictionary>
/*     */ {
/*     */   private static final long serialVersionUID = 404629033132277362L;
/*     */   private int from;
/*     */   private PdfNumber count;
/*     */   private final PdfArray kids;
/*     */   private final PdfPages parent;
/*     */   
/*     */   public PdfPages(int from, PdfDocument pdfDocument, PdfPages parent) {
/*  55 */     super(new PdfDictionary());
/*  56 */     if (pdfDocument.getWriter() != null) {
/*  57 */       getPdfObject().makeIndirect(pdfDocument);
/*     */     }
/*  59 */     setForbidRelease();
/*  60 */     this.from = from;
/*  61 */     this.count = new PdfNumber(0);
/*  62 */     this.kids = new PdfArray();
/*  63 */     this.parent = parent;
/*  64 */     getPdfObject().put(PdfName.Type, PdfName.Pages);
/*  65 */     getPdfObject().put(PdfName.Kids, this.kids);
/*  66 */     getPdfObject().put(PdfName.Count, this.count);
/*  67 */     if (parent != null) {
/*  68 */       getPdfObject().put(PdfName.Parent, this.parent.getPdfObject());
/*     */     }
/*     */   }
/*     */   
/*     */   public PdfPages(int from, PdfDocument pdfDocument) {
/*  73 */     this(from, pdfDocument, (PdfPages)null);
/*     */   }
/*     */   
/*     */   public PdfPages(int from, int maxCount, PdfDictionary pdfObject, PdfPages parent) {
/*  77 */     super(pdfObject);
/*  78 */     setForbidRelease();
/*  79 */     this.from = from;
/*  80 */     this.count = pdfObject.getAsNumber(PdfName.Count);
/*  81 */     this.parent = parent;
/*  82 */     if (this.count == null) {
/*  83 */       this.count = new PdfNumber(1);
/*  84 */       pdfObject.put(PdfName.Count, this.count);
/*  85 */     } else if (maxCount < this.count.intValue()) {
/*  86 */       this.count.setValue(maxCount);
/*     */     } 
/*  88 */     this.kids = pdfObject.getAsArray(PdfName.Kids);
/*  89 */     pdfObject.put(PdfName.Type, PdfName.Pages);
/*     */   }
/*     */   
/*     */   public void addPage(PdfDictionary page) {
/*  93 */     this.kids.add(page);
/*  94 */     incrementCount();
/*  95 */     page.put(PdfName.Parent, getPdfObject());
/*  96 */     page.setModified();
/*     */   }
/*     */   
/*     */   public boolean addPage(int index, PdfPage pdfPage) {
/* 100 */     if (index < this.from || index > this.from + getCount())
/* 101 */       return false; 
/* 102 */     this.kids.add(index - this.from, pdfPage.getPdfObject());
/* 103 */     pdfPage.getPdfObject().put(PdfName.Parent, getPdfObject());
/* 104 */     pdfPage.setModified();
/* 105 */     incrementCount();
/* 106 */     return true;
/*     */   }
/*     */   
/*     */   public boolean removePage(int pageNum) {
/* 110 */     if (pageNum < this.from || pageNum >= this.from + getCount())
/* 111 */       return false; 
/* 112 */     decrementCount();
/* 113 */     this.kids.remove(pageNum - this.from);
/* 114 */     return true;
/*     */   }
/*     */   
/*     */   public void addPages(PdfPages pdfPages) {
/* 118 */     this.kids.add(pdfPages.getPdfObject());
/* 119 */     this.count.setValue(this.count.intValue() + pdfPages.getCount());
/* 120 */     pdfPages.getPdfObject().put(PdfName.Parent, getPdfObject());
/* 121 */     pdfPages.setModified();
/* 122 */     setModified();
/*     */   }
/*     */ 
/*     */   
/*     */   public void removeFromParent() {
/* 127 */     if (this.parent != null) {
/* 128 */       assert getCount() == 0;
/* 129 */       this.parent.kids.remove(getPdfObject().getIndirectReference());
/* 130 */       if (this.parent.getCount() == 0) {
/* 131 */         this.parent.removeFromParent();
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   public int getFrom() {
/* 137 */     return this.from;
/*     */   }
/*     */   
/*     */   public int getCount() {
/* 141 */     return this.count.intValue();
/*     */   }
/*     */   
/*     */   public void correctFrom(int correction) {
/* 145 */     this.from += correction;
/*     */   }
/*     */   
/*     */   public PdfArray getKids() {
/* 149 */     return getPdfObject().getAsArray(PdfName.Kids);
/*     */   }
/*     */   
/*     */   public PdfPages getParent() {
/* 153 */     return this.parent;
/*     */   }
/*     */   
/*     */   public void incrementCount() {
/* 157 */     this.count.increment();
/* 158 */     setModified();
/* 159 */     if (this.parent != null)
/* 160 */       this.parent.incrementCount(); 
/*     */   }
/*     */   
/*     */   public void decrementCount() {
/* 164 */     this.count.decrement();
/* 165 */     setModified();
/* 166 */     if (this.parent != null)
/* 167 */       this.parent.decrementCount(); 
/*     */   }
/*     */   
/*     */   public int compareTo(int index) {
/* 171 */     if (index < this.from)
/* 172 */       return 1; 
/* 173 */     if (index >= this.from + getCount())
/* 174 */       return -1; 
/* 175 */     return 0;
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean isWrappedObjectMustBeIndirect() {
/* 180 */     return true;
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/kernel/pdf/PdfPages.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */