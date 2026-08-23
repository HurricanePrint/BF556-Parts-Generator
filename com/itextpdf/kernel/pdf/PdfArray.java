/*     */ package com.itextpdf.kernel.pdf;
/*     */ 
/*     */ import com.itextpdf.kernel.PdfException;
/*     */ import com.itextpdf.kernel.geom.Rectangle;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class PdfArray
/*     */   extends PdfObject
/*     */   implements Iterable<PdfObject>
/*     */ {
/*     */   private static final long serialVersionUID = 1617495612878046869L;
/*     */   protected List<PdfObject> list;
/*     */   
/*     */   public PdfArray() {
/*  69 */     this.list = new ArrayList<>();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PdfArray(PdfObject obj) {
/*  79 */     this();
/*  80 */     this.list.add(obj);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PdfArray(PdfArray arr) {
/*  89 */     this();
/*  90 */     this.list.addAll(arr.list);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PdfArray(Rectangle rectangle) {
/* 100 */     this.list = new ArrayList<>(4);
/* 101 */     add(new PdfNumber(rectangle.getLeft()));
/* 102 */     add(new PdfNumber(rectangle.getBottom()));
/* 103 */     add(new PdfNumber(rectangle.getRight()));
/* 104 */     add(new PdfNumber(rectangle.getTop()));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PdfArray(List<? extends PdfObject> objects) {
/* 113 */     this.list = new ArrayList<>(objects.size());
/* 114 */     for (PdfObject element : objects) {
/* 115 */       add(element);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PdfArray(float[] numbers) {
/* 124 */     this.list = new ArrayList<>(numbers.length);
/* 125 */     for (float f : numbers) {
/* 126 */       this.list.add(new PdfNumber(f));
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PdfArray(double[] numbers) {
/* 136 */     this.list = new ArrayList<>(numbers.length);
/* 137 */     for (double f : numbers) {
/* 138 */       this.list.add(new PdfNumber(f));
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PdfArray(int[] numbers) {
/* 148 */     this.list = new ArrayList<>(numbers.length); int arrayOfInt[], i; byte b;
/* 149 */     for (arrayOfInt = numbers, i = arrayOfInt.length, b = 0; b < i; ) { float f = arrayOfInt[b];
/* 150 */       this.list.add(new PdfNumber(f));
/*     */       b++; }
/*     */   
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PdfArray(boolean[] values) {
/* 160 */     this.list = new ArrayList<>(values.length);
/* 161 */     for (boolean b : values) {
/* 162 */       this.list.add(PdfBoolean.valueOf(b));
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
/*     */   public PdfArray(List<String> strings, boolean asNames) {
/* 174 */     this.list = new ArrayList<>(strings.size());
/* 175 */     for (String s : strings) {
/* 176 */       this.list.add(asNames ? new PdfName(s) : new PdfString(s));
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PdfArray(Iterable<? extends PdfObject> objects, int initialCapacity) {
/* 187 */     this.list = new ArrayList<>(initialCapacity);
/* 188 */     for (PdfObject element : objects)
/* 189 */       add(element); 
/*     */   }
/*     */   
/*     */   public int size() {
/* 193 */     return this.list.size();
/*     */   }
/*     */   
/*     */   public boolean isEmpty() {
/* 197 */     return (this.list.size() == 0);
/*     */   }
/*     */   
/*     */   public boolean contains(PdfObject o) {
/* 201 */     if (this.list.contains(o))
/* 202 */       return true; 
/* 203 */     if (o == null)
/* 204 */       return false; 
/* 205 */     for (PdfObject pdfObject : this) {
/* 206 */       if (PdfObject.equalContent(o, pdfObject)) {
/* 207 */         return true;
/*     */       }
/*     */     } 
/* 210 */     return false;
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
/*     */   public Iterator<PdfObject> iterator() {
/* 223 */     return new PdfArrayDirectIterator(this.list);
/*     */   }
/*     */   
/*     */   public void add(PdfObject pdfObject) {
/* 227 */     this.list.add(pdfObject);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void add(int index, PdfObject element) {
/* 238 */     this.list.add(index, element);
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
/*     */   public PdfObject set(int index, PdfObject element) {
/* 250 */     return this.list.set(index, element);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addAll(Collection<PdfObject> c) {
/* 260 */     this.list.addAll(c);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addAll(PdfArray a) {
/* 270 */     if (a != null) addAll(a.list);
/*     */   
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PdfObject get(int index) {
/* 280 */     return get(index, true);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void remove(int index) {
/* 290 */     this.list.remove(index);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void remove(PdfObject o) {
/* 300 */     if (this.list.remove(o))
/*     */       return; 
/* 302 */     if (o == null)
/*     */       return; 
/* 304 */     for (PdfObject pdfObject : this.list) {
/* 305 */       if (PdfObject.equalContent(o, pdfObject)) {
/* 306 */         this.list.remove(pdfObject);
/*     */         break;
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public void clear() {
/* 313 */     this.list.clear();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int indexOf(PdfObject o) {
/* 324 */     if (o == null)
/* 325 */       return this.list.indexOf(null); 
/* 326 */     int index = 0;
/* 327 */     for (PdfObject pdfObject : this) {
/* 328 */       if (PdfObject.equalContent(o, pdfObject)) {
/* 329 */         return index;
/*     */       }
/* 331 */       index++;
/*     */     } 
/* 333 */     return -1;
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
/*     */   public List<PdfObject> subList(int fromIndex, int toIndex) {
/* 345 */     return this.list.subList(fromIndex, toIndex);
/*     */   }
/*     */ 
/*     */   
/*     */   public byte getType() {
/* 350 */     return 1;
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 355 */     String string = "[";
/* 356 */     for (PdfObject entry : this.list) {
/* 357 */       PdfIndirectReference indirectReference = entry.getIndirectReference();
/* 358 */       string = string + ((indirectReference == null) ? entry.toString() : indirectReference.toString()) + " ";
/*     */     } 
/* 360 */     string = string + "]";
/* 361 */     return string;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PdfObject get(int index, boolean asDirect) {
/* 370 */     if (!asDirect) {
/* 371 */       return this.list.get(index);
/*     */     }
/* 373 */     PdfObject obj = this.list.get(index);
/* 374 */     if (obj.getType() == 5) {
/* 375 */       return ((PdfIndirectReference)obj).getRefersTo(true);
/*     */     }
/* 377 */     return obj;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PdfArray getAsArray(int index) {
/* 388 */     PdfObject direct = get(index, true);
/* 389 */     if (direct != null && direct.getType() == 1)
/* 390 */       return (PdfArray)direct; 
/* 391 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PdfDictionary getAsDictionary(int index) {
/* 402 */     PdfObject direct = get(index, true);
/* 403 */     if (direct != null && direct.getType() == 3)
/* 404 */       return (PdfDictionary)direct; 
/* 405 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PdfStream getAsStream(int index) {
/* 416 */     PdfObject direct = get(index, true);
/* 417 */     if (direct != null && direct.getType() == 9)
/* 418 */       return (PdfStream)direct; 
/* 419 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PdfNumber getAsNumber(int index) {
/* 430 */     PdfObject direct = get(index, true);
/* 431 */     if (direct != null && direct.getType() == 8)
/* 432 */       return (PdfNumber)direct; 
/* 433 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PdfName getAsName(int index) {
/* 444 */     PdfObject direct = get(index, true);
/* 445 */     if (direct != null && direct.getType() == 6)
/* 446 */       return (PdfName)direct; 
/* 447 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PdfString getAsString(int index) {
/* 458 */     PdfObject direct = get(index, true);
/* 459 */     if (direct != null && direct.getType() == 10)
/* 460 */       return (PdfString)direct; 
/* 461 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PdfBoolean getAsBoolean(int index) {
/* 471 */     PdfObject direct = get(index, true);
/* 472 */     if (direct != null && direct.getType() == 2)
/* 473 */       return (PdfBoolean)direct; 
/* 474 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Rectangle toRectangle() {
/*     */     try {
/* 486 */       float x1 = getAsNumber(0).floatValue();
/* 487 */       float y1 = getAsNumber(1).floatValue();
/* 488 */       float x2 = getAsNumber(2).floatValue();
/* 489 */       float y2 = getAsNumber(3).floatValue();
/*     */ 
/*     */       
/* 492 */       float llx = Math.min(x1, x2);
/* 493 */       float lly = Math.min(y1, y2);
/* 494 */       float urx = Math.max(x1, x2);
/* 495 */       float ury = Math.max(y1, y2);
/*     */       
/* 497 */       return new Rectangle(llx, lly, urx - llx, ury - lly);
/* 498 */     } catch (Exception e) {
/* 499 */       throw new PdfException("Cannot convert PdfArray to Rectangle.", e, this);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float[] toFloatArray() {
/*     */     try {
/* 511 */       float[] rslt = new float[size()];
/* 512 */       for (int k = 0; k < rslt.length; k++) {
/* 513 */         rslt[k] = getAsNumber(k).floatValue();
/*     */       }
/* 515 */       return rslt;
/* 516 */     } catch (Exception e) {
/* 517 */       throw new PdfException("Cannot convert PdfArray to an array of floats.", e, this);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double[] toDoubleArray() {
/*     */     try {
/* 529 */       double[] rslt = new double[size()];
/* 530 */       for (int k = 0; k < rslt.length; k++) {
/* 531 */         rslt[k] = getAsNumber(k).doubleValue();
/*     */       }
/* 533 */       return rslt;
/* 534 */     } catch (Exception e) {
/* 535 */       throw new PdfException("Cannot convert PdfArray to an array of doubles.", e, this);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long[] toLongArray() {
/*     */     try {
/* 547 */       long[] rslt = new long[size()];
/* 548 */       for (int k = 0; k < rslt.length; k++) {
/* 549 */         rslt[k] = getAsNumber(k).longValue();
/*     */       }
/* 551 */       return rslt;
/* 552 */     } catch (Exception e) {
/* 553 */       throw new PdfException("Cannot convert PdfArray to an array of longs.", e, this);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int[] toIntArray() {
/*     */     try {
/* 565 */       int[] rslt = new int[size()];
/* 566 */       for (int k = 0; k < rslt.length; k++) {
/* 567 */         rslt[k] = getAsNumber(k).intValue();
/*     */       }
/* 569 */       return rslt;
/* 570 */     } catch (Exception e) {
/* 571 */       throw new PdfException("Cannot convert PdfArray to an array of integers.", e, this);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean[] toBooleanArray() {
/* 582 */     boolean[] rslt = new boolean[size()];
/*     */     
/* 584 */     for (int k = 0; k < rslt.length; k++) {
/* 585 */       PdfBoolean tmp = getAsBoolean(k);
/* 586 */       if (tmp == null) {
/* 587 */         throw new PdfException("Cannot convert PdfArray to an array of booleans", this);
/*     */       }
/* 589 */       rslt[k] = tmp.getValue();
/*     */     } 
/* 591 */     return rslt;
/*     */   }
/*     */ 
/*     */   
/*     */   protected PdfObject newInstance() {
/* 596 */     return new PdfArray();
/*     */   }
/*     */ 
/*     */   
/*     */   protected void copyContent(PdfObject from, PdfDocument document) {
/* 601 */     super.copyContent(from, document);
/* 602 */     PdfArray array = (PdfArray)from;
/* 603 */     for (PdfObject entry : array.list) {
/* 604 */       add(entry.processCopying(document, false));
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void releaseContent() {
/* 612 */     this.list = null;
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/kernel/pdf/PdfArray.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */