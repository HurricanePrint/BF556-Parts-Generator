/*     */ package com.itextpdf.kernel.utils;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ import java.util.regex.Matcher;
/*     */ import java.util.regex.Pattern;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class PageRange
/*     */ {
/*  59 */   private static final Pattern SEQUENCE_PATTERN = Pattern.compile("(\\d+)-(\\d+)?");
/*  60 */   private static final Pattern SINGLE_PAGE_PATTERN = Pattern.compile("(\\d+)");
/*     */   
/*  62 */   private List<IPageRangePart> sequences = new ArrayList<>();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PageRange() {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PageRange(String pageRange) {
/*  85 */     pageRange = pageRange.replaceAll("\\s+", "");
/*  86 */     for (String pageRangePart : pageRange.split(",")) {
/*  87 */       IPageRangePart cond = getRangeObject(pageRangePart);
/*  88 */       if (cond != null) {
/*  89 */         this.sequences.add(cond);
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   private static IPageRangePart getRangeObject(String rangeDef) {
/*  95 */     if (rangeDef.contains("&")) {
/*  96 */       List<IPageRangePart> conditions = new ArrayList<>();
/*  97 */       for (String pageRangeCond : rangeDef.split("&")) {
/*  98 */         IPageRangePart cond = getRangeObject(pageRangeCond);
/*  99 */         if (cond != null) {
/* 100 */           conditions.add(cond);
/*     */         }
/*     */       } 
/* 103 */       if (conditions.size() > 0) {
/* 104 */         return new PageRangePartAnd(conditions.<IPageRangePart>toArray(new IPageRangePart[0]));
/*     */       }
/* 106 */       return null;
/*     */     } 
/*     */     
/*     */     Matcher matcher;
/* 110 */     if ((matcher = SEQUENCE_PATTERN.matcher(rangeDef)).matches()) {
/* 111 */       int start = Integer.parseInt(matcher.group(1));
/* 112 */       if (matcher.group(2) != null) {
/* 113 */         return new PageRangePartSequence(start, Integer.parseInt(matcher.group(2)));
/*     */       }
/* 115 */       return new PageRangePartAfter(start);
/*     */     } 
/* 117 */     if ((matcher = SINGLE_PAGE_PATTERN.matcher(rangeDef)).matches())
/* 118 */       return new PageRangePartSingle(Integer.parseInt(matcher.group(1))); 
/* 119 */     if ("odd".equalsIgnoreCase(rangeDef))
/* 120 */       return PageRangePartOddEven.ODD; 
/* 121 */     if ("even".equalsIgnoreCase(rangeDef)) {
/* 122 */       return PageRangePartOddEven.EVEN;
/*     */     }
/* 124 */     return null;
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
/*     */   public PageRange addPageRangePart(IPageRangePart part) {
/* 136 */     this.sequences.add(part);
/* 137 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PageRange addPageSequence(int startPageNumber, int endPageNumber) {
/* 148 */     return addPageRangePart(new PageRangePartSequence(startPageNumber, endPageNumber));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PageRange addSinglePage(int pageNumber) {
/* 158 */     return addPageRangePart(new PageRangePartSingle(pageNumber));
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
/*     */   public List<Integer> getQualifyingPageNums(int nbPages) {
/* 170 */     List<Integer> allPages = new ArrayList<>();
/* 171 */     for (IPageRangePart sequence : this.sequences) {
/* 172 */       allPages.addAll(sequence.getAllPagesInRange(nbPages));
/*     */     }
/* 174 */     return allPages;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isPageInRange(int pageNumber) {
/* 185 */     for (IPageRangePart sequence : this.sequences) {
/* 186 */       if (sequence.isPageInRange(pageNumber)) {
/* 187 */         return true;
/*     */       }
/*     */     } 
/* 190 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/* 198 */     if (!(obj instanceof PageRange)) {
/* 199 */       return false;
/*     */     }
/*     */     
/* 202 */     PageRange other = (PageRange)obj;
/* 203 */     return this.sequences.equals(other.sequences);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 211 */     int hashCode = 0;
/* 212 */     for (IPageRangePart part : this.sequences) {
/* 213 */       hashCode += part.hashCode();
/*     */     }
/*     */     
/* 216 */     return hashCode;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static interface IPageRangePart
/*     */   {
/*     */     List<Integer> getAllPagesInRange(int param1Int);
/*     */ 
/*     */ 
/*     */     
/*     */     boolean isPageInRange(int param1Int);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static class PageRangePartSingle
/*     */     implements IPageRangePart
/*     */   {
/*     */     private final int page;
/*     */ 
/*     */     
/*     */     public PageRangePartSingle(int page) {
/* 239 */       this.page = page;
/*     */     }
/*     */ 
/*     */     
/*     */     public List<Integer> getAllPagesInRange(int nbPages) {
/* 244 */       if (this.page <= nbPages) {
/* 245 */         return Collections.singletonList(Integer.valueOf(this.page));
/*     */       }
/* 247 */       return Collections.emptyList();
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean isPageInRange(int pageNumber) {
/* 253 */       return (this.page == pageNumber);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean equals(Object obj) {
/* 261 */       if (!(obj instanceof PageRangePartSingle)) {
/* 262 */         return false;
/*     */       }
/*     */       
/* 265 */       PageRangePartSingle other = (PageRangePartSingle)obj;
/* 266 */       return (this.page == other.page);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public int hashCode() {
/* 274 */       return this.page;
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public static class PageRangePartSequence
/*     */     implements IPageRangePart
/*     */   {
/*     */     private final int start;
/*     */     
/*     */     private final int end;
/*     */ 
/*     */     
/*     */     public PageRangePartSequence(int start, int end) {
/* 288 */       this.start = start;
/* 289 */       this.end = end;
/*     */     }
/*     */ 
/*     */     
/*     */     public List<Integer> getAllPagesInRange(int nbPages) {
/* 294 */       List<Integer> allPages = new ArrayList<>();
/* 295 */       for (int pageInRange = this.start; pageInRange <= this.end && pageInRange <= nbPages; pageInRange++) {
/* 296 */         allPages.add(Integer.valueOf(pageInRange));
/*     */       }
/* 298 */       return allPages;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean isPageInRange(int pageNumber) {
/* 303 */       return (this.start <= pageNumber && pageNumber <= this.end);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean equals(Object obj) {
/* 311 */       if (!(obj instanceof PageRangePartSequence)) {
/* 312 */         return false;
/*     */       }
/*     */       
/* 315 */       PageRangePartSequence other = (PageRangePartSequence)obj;
/* 316 */       return (this.start == other.start && this.end == other.end);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public int hashCode() {
/* 324 */       return this.start * 31 + this.end;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static class PageRangePartAfter
/*     */     implements IPageRangePart
/*     */   {
/*     */     private final int start;
/*     */ 
/*     */     
/*     */     public PageRangePartAfter(int start) {
/* 337 */       this.start = start;
/*     */     }
/*     */ 
/*     */     
/*     */     public List<Integer> getAllPagesInRange(int nbPages) {
/* 342 */       List<Integer> allPages = new ArrayList<>();
/* 343 */       for (int pageInRange = this.start; pageInRange <= nbPages; pageInRange++) {
/* 344 */         allPages.add(Integer.valueOf(pageInRange));
/*     */       }
/* 346 */       return allPages;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean isPageInRange(int pageNumber) {
/* 351 */       return (this.start <= pageNumber);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean equals(Object obj) {
/* 359 */       if (!(obj instanceof PageRangePartAfter)) {
/* 360 */         return false;
/*     */       }
/*     */       
/* 363 */       PageRangePartAfter other = (PageRangePartAfter)obj;
/* 364 */       return (this.start == other.start);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public int hashCode() {
/* 372 */       return this.start * 31 + -1;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static class PageRangePartOddEven
/*     */     implements IPageRangePart
/*     */   {
/*     */     private final boolean isOdd;
/*     */     
/*     */     private final int mod;
/*     */     
/* 385 */     public static final PageRangePartOddEven ODD = new PageRangePartOddEven(true);
/* 386 */     public static final PageRangePartOddEven EVEN = new PageRangePartOddEven(false);
/*     */     
/*     */     private PageRangePartOddEven(boolean isOdd) {
/* 389 */       this.isOdd = isOdd;
/* 390 */       if (isOdd) {
/* 391 */         this.mod = 1;
/*     */       } else {
/* 393 */         this.mod = 0;
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public List<Integer> getAllPagesInRange(int nbPages) {
/* 399 */       List<Integer> allPages = new ArrayList<>();
/* 400 */       for (int pageInRange = (this.mod == 0) ? 2 : this.mod; pageInRange <= nbPages; pageInRange += 2) {
/* 401 */         allPages.add(Integer.valueOf(pageInRange));
/*     */       }
/* 403 */       return allPages;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean isPageInRange(int pageNumber) {
/* 408 */       return (pageNumber % 2 == this.mod);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean equals(Object obj) {
/* 416 */       if (!(obj instanceof PageRangePartOddEven)) {
/* 417 */         return false;
/*     */       }
/*     */       
/* 420 */       PageRangePartOddEven other = (PageRangePartOddEven)obj;
/* 421 */       return (this.isOdd == other.isOdd);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public int hashCode() {
/* 429 */       if (this.isOdd) {
/* 430 */         return 127;
/*     */       }
/* 432 */       return 128;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static class PageRangePartAnd
/*     */     implements IPageRangePart
/*     */   {
/* 443 */     private final List<PageRange.IPageRangePart> conditions = new ArrayList<>();
/*     */     
/*     */     public PageRangePartAnd(PageRange.IPageRangePart... conditions) {
/* 446 */       this.conditions.addAll(Arrays.asList(conditions));
/*     */     }
/*     */ 
/*     */     
/*     */     public List<Integer> getAllPagesInRange(int nbPages) {
/* 451 */       List<Integer> allPages = new ArrayList<>();
/* 452 */       if (!this.conditions.isEmpty()) {
/* 453 */         allPages.addAll(((PageRange.IPageRangePart)this.conditions.get(0)).getAllPagesInRange(nbPages));
/*     */       }
/* 455 */       for (PageRange.IPageRangePart cond : this.conditions) {
/* 456 */         allPages.retainAll(cond.getAllPagesInRange(nbPages));
/*     */       }
/* 458 */       return allPages;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean isPageInRange(int pageNumber) {
/* 463 */       for (PageRange.IPageRangePart cond : this.conditions) {
/* 464 */         if (!cond.isPageInRange(pageNumber)) {
/* 465 */           return false;
/*     */         }
/*     */       } 
/* 468 */       return true;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean equals(Object obj) {
/* 476 */       if (!(obj instanceof PageRangePartAnd)) {
/* 477 */         return false;
/*     */       }
/*     */       
/* 480 */       PageRangePartAnd other = (PageRangePartAnd)obj;
/* 481 */       return this.conditions.equals(other.conditions);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public int hashCode() {
/* 489 */       int hashCode = 0;
/* 490 */       for (PageRange.IPageRangePart part : this.conditions) {
/* 491 */         hashCode += part.hashCode();
/*     */       }
/*     */       
/* 494 */       return hashCode;
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/kernel/utils/PageRange.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */