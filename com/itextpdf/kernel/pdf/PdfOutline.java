/*     */ package com.itextpdf.kernel.pdf;
/*     */ 
/*     */ import com.itextpdf.kernel.colors.Color;
/*     */ import com.itextpdf.kernel.pdf.action.PdfAction;
/*     */ import com.itextpdf.kernel.pdf.navigation.PdfDestination;
/*     */ import java.io.Serializable;
/*     */ import java.util.ArrayList;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class PdfOutline
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 5730874960685950376L;
/*  64 */   public static int FLAG_ITALIC = 1;
/*     */ 
/*     */ 
/*     */   
/*  68 */   public static int FLAG_BOLD = 2;
/*     */   
/*  70 */   private List<PdfOutline> children = new ArrayList<>();
/*     */ 
/*     */   
/*     */   private String title;
/*     */   
/*     */   private PdfDictionary content;
/*     */   
/*     */   private PdfDestination destination;
/*     */   
/*     */   private PdfOutline parent;
/*     */   
/*     */   private PdfDocument pdfDoc;
/*     */ 
/*     */   
/*     */   PdfOutline(String title, PdfDictionary content, PdfDocument pdfDocument) {
/*  85 */     this.title = title;
/*  86 */     this.content = content;
/*  87 */     this.pdfDoc = pdfDocument;
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
/*     */   PdfOutline(String title, PdfDictionary content, PdfOutline parent) {
/*  99 */     this.title = title;
/* 100 */     this.content = content;
/* 101 */     this.parent = parent;
/* 102 */     this.pdfDoc = parent.pdfDoc;
/* 103 */     content.makeIndirect(parent.pdfDoc);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   PdfOutline(PdfDocument doc) {
/* 112 */     this.content = new PdfDictionary();
/* 113 */     this.content.put(PdfName.Type, PdfName.Outlines);
/* 114 */     this.pdfDoc = doc;
/* 115 */     this.content.makeIndirect(doc);
/* 116 */     doc.getCatalog().addRootOutline(this);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getTitle() {
/* 125 */     return this.title;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setTitle(String title) {
/* 135 */     this.title = title;
/* 136 */     this.content.put(PdfName.Title, new PdfString(title, "UnicodeBig"));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setColor(Color color) {
/* 145 */     this.content.put(PdfName.C, new PdfArray(color.getColorValue()));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setStyle(int style) {
/* 154 */     if (style == FLAG_BOLD || style == FLAG_ITALIC) {
/* 155 */       this.content.put(PdfName.F, new PdfNumber(style));
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PdfDictionary getContent() {
/* 165 */     return this.content;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List<PdfOutline> getAllChildren() {
/* 174 */     return this.children;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PdfOutline getParent() {
/* 183 */     return this.parent;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PdfDestination getDestination() {
/* 192 */     return this.destination;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addDestination(PdfDestination destination) {
/* 201 */     setDestination(destination);
/* 202 */     this.content.put(PdfName.Dest, destination.getPdfObject());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addAction(PdfAction action) {
/* 211 */     this.content.put(PdfName.A, action.getPdfObject());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setOpen(boolean open) {
/* 221 */     if (!open) {
/* 222 */       this.content.put(PdfName.Count, new PdfNumber(-1));
/* 223 */     } else if (this.children.size() > 0) {
/* 224 */       this.content.put(PdfName.Count, new PdfNumber(this.children.size()));
/*     */     } else {
/* 226 */       this.content.remove(PdfName.Count);
/*     */     } 
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
/*     */   public PdfOutline addOutline(String title, int position) {
/* 239 */     if (position == -1)
/* 240 */       position = this.children.size(); 
/* 241 */     PdfDictionary dictionary = new PdfDictionary();
/* 242 */     PdfOutline outline = new PdfOutline(title, dictionary, this);
/* 243 */     dictionary.put(PdfName.Title, new PdfString(title, "UnicodeBig"));
/* 244 */     dictionary.put(PdfName.Parent, this.content);
/* 245 */     if (this.children.size() > 0) {
/* 246 */       if (position != 0) {
/* 247 */         PdfDictionary prevContent = ((PdfOutline)this.children.get(position - 1)).getContent();
/* 248 */         dictionary.put(PdfName.Prev, prevContent);
/* 249 */         prevContent.put(PdfName.Next, dictionary);
/*     */       } 
/* 251 */       if (position != this.children.size()) {
/* 252 */         PdfDictionary nextContent = ((PdfOutline)this.children.get(position)).getContent();
/* 253 */         dictionary.put(PdfName.Next, nextContent);
/* 254 */         nextContent.put(PdfName.Prev, dictionary);
/*     */       } 
/*     */     } 
/*     */     
/* 258 */     if (position == 0)
/* 259 */       this.content.put(PdfName.First, dictionary); 
/* 260 */     if (position == this.children.size()) {
/* 261 */       this.content.put(PdfName.Last, dictionary);
/*     */     }
/* 263 */     PdfNumber count = this.content.getAsNumber(PdfName.Count);
/* 264 */     if (count == null || count.getValue() != -1.0D) {
/* 265 */       this.content.put(PdfName.Count, new PdfNumber(this.children.size() + 1));
/*     */     }
/* 267 */     this.children.add(position, outline);
/*     */     
/* 269 */     return outline;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PdfOutline addOutline(String title) {
/* 280 */     return addOutline(title, -1);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PdfOutline addOutline(PdfOutline outline) {
/* 291 */     PdfOutline newOutline = addOutline(outline.getTitle());
/* 292 */     newOutline.addDestination(outline.getDestination());
/*     */     
/* 294 */     List<PdfOutline> children = outline.getAllChildren();
/* 295 */     for (PdfOutline child : children) {
/* 296 */       newOutline.addOutline(child);
/*     */     }
/*     */     
/* 299 */     return newOutline;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void removeOutline() {
/* 306 */     if (!this.pdfDoc.hasOutlines() || isOutlineRoot()) {
/* 307 */       this.pdfDoc.getCatalog().remove(PdfName.Outlines);
/*     */       return;
/*     */     } 
/* 310 */     PdfOutline parent = this.parent;
/* 311 */     List<PdfOutline> children = parent.children;
/* 312 */     children.remove(this);
/* 313 */     PdfDictionary parentContent = parent.content;
/* 314 */     if (children.size() > 0) {
/* 315 */       parentContent.put(PdfName.First, ((PdfOutline)children.get(0)).content);
/* 316 */       parentContent.put(PdfName.Last, ((PdfOutline)children.get(children.size() - 1)).content);
/*     */     } else {
/* 318 */       parent.removeOutline();
/*     */       
/*     */       return;
/*     */     } 
/* 322 */     PdfDictionary next = this.content.getAsDictionary(PdfName.Next);
/* 323 */     PdfDictionary prev = this.content.getAsDictionary(PdfName.Prev);
/* 324 */     if (prev != null) {
/* 325 */       if (next != null) {
/* 326 */         prev.put(PdfName.Next, next);
/* 327 */         next.put(PdfName.Prev, prev);
/*     */       } else {
/* 329 */         prev.remove(PdfName.Next);
/*     */       } 
/* 331 */     } else if (next != null) {
/* 332 */       next.remove(PdfName.Prev);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void clear() {
/* 340 */     this.children.clear();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void setDestination(PdfDestination destination) {
/* 349 */     this.destination = destination;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private PdfDictionary getOutlineRoot() {
/* 358 */     if (!this.pdfDoc.hasOutlines()) {
/* 359 */       return null;
/*     */     }
/* 361 */     return this.pdfDoc.getCatalog().getPdfObject().getAsDictionary(PdfName.Outlines);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean isOutlineRoot() {
/* 371 */     PdfDictionary outlineRoot = getOutlineRoot();
/* 372 */     return (outlineRoot == this.content);
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/kernel/pdf/PdfOutline.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */