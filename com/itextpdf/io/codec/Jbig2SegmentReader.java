/*     */ package com.itextpdf.io.codec;
/*     */ 
/*     */ import com.itextpdf.io.IOException;
/*     */ import com.itextpdf.io.source.RandomAccessFileOrArray;
/*     */ import java.io.ByteArrayOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.util.Iterator;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import java.util.TreeMap;
/*     */ import java.util.TreeSet;
/*     */ import org.slf4j.Logger;
/*     */ import org.slf4j.LoggerFactory;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Jbig2SegmentReader
/*     */ {
/*     */   public static final int SYMBOL_DICTIONARY = 0;
/*     */   public static final int INTERMEDIATE_TEXT_REGION = 4;
/*     */   public static final int IMMEDIATE_TEXT_REGION = 6;
/*     */   public static final int IMMEDIATE_LOSSLESS_TEXT_REGION = 7;
/*     */   public static final int PATTERN_DICTIONARY = 16;
/*     */   public static final int INTERMEDIATE_HALFTONE_REGION = 20;
/*     */   public static final int IMMEDIATE_HALFTONE_REGION = 22;
/*     */   public static final int IMMEDIATE_LOSSLESS_HALFTONE_REGION = 23;
/*     */   public static final int INTERMEDIATE_GENERIC_REGION = 36;
/*     */   public static final int IMMEDIATE_GENERIC_REGION = 38;
/*     */   public static final int IMMEDIATE_LOSSLESS_GENERIC_REGION = 39;
/*     */   public static final int INTERMEDIATE_GENERIC_REFINEMENT_REGION = 40;
/*     */   public static final int IMMEDIATE_GENERIC_REFINEMENT_REGION = 42;
/*     */   public static final int IMMEDIATE_LOSSLESS_GENERIC_REFINEMENT_REGION = 43;
/*     */   public static final int PAGE_INFORMATION = 48;
/*     */   public static final int END_OF_PAGE = 49;
/*     */   public static final int END_OF_STRIPE = 50;
/*     */   public static final int END_OF_FILE = 51;
/*     */   public static final int PROFILES = 52;
/*     */   public static final int TABLES = 53;
/*     */   public static final int EXTENSION = 62;
/* 115 */   private final Map<Integer, Jbig2Segment> segments = new TreeMap<>();
/* 116 */   private final Map<Integer, Jbig2Page> pages = new TreeMap<>();
/* 117 */   private final Set<Jbig2Segment> globals = new TreeSet<>();
/*     */   private RandomAccessFileOrArray ra;
/*     */   private boolean sequential;
/*     */   private boolean number_of_pages_known;
/* 121 */   private int number_of_pages = -1;
/*     */   
/*     */   private boolean read = false;
/*     */ 
/*     */   
/*     */   public static class Jbig2Segment
/*     */     implements Comparable<Jbig2Segment>
/*     */   {
/*     */     public final int segmentNumber;
/* 130 */     public long dataLength = -1L;
/* 131 */     public int page = -1;
/* 132 */     public int[] referredToSegmentNumbers = null;
/* 133 */     public boolean[] segmentRetentionFlags = null;
/* 134 */     public int type = -1;
/*     */     public boolean deferredNonRetain = false;
/* 136 */     public int countOfReferredToSegments = -1;
/* 137 */     public byte[] data = null;
/* 138 */     public byte[] headerData = null;
/*     */     public boolean page_association_size = false;
/* 140 */     public int page_association_offset = -1;
/*     */     
/*     */     public Jbig2Segment(int segment_number) {
/* 143 */       this.segmentNumber = segment_number;
/*     */     }
/*     */     
/*     */     public int compareTo(Jbig2Segment s) {
/* 147 */       return this.segmentNumber - s.segmentNumber;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static class Jbig2Page
/*     */   {
/*     */     public final int page;
/*     */     
/*     */     private final Jbig2SegmentReader sr;
/*     */     
/* 159 */     private final Map<Integer, Jbig2SegmentReader.Jbig2Segment> segs = new TreeMap<>();
/* 160 */     public int pageBitmapWidth = -1;
/* 161 */     public int pageBitmapHeight = -1;
/*     */     
/*     */     public Jbig2Page(int page, Jbig2SegmentReader sr) {
/* 164 */       this.page = page;
/* 165 */       this.sr = sr;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public byte[] getData(boolean for_embedding) throws IOException {
/* 178 */       ByteArrayOutputStream os = new ByteArrayOutputStream();
/* 179 */       for (Iterator<Integer> iterator = this.segs.keySet().iterator(); iterator.hasNext(); ) { int sn = ((Integer)iterator.next()).intValue();
/* 180 */         Jbig2SegmentReader.Jbig2Segment s = this.segs.get(Integer.valueOf(sn));
/*     */ 
/*     */ 
/*     */         
/* 184 */         if (for_embedding && (s.type == 51 || s.type == 49)) {
/*     */           continue;
/*     */         }
/*     */ 
/*     */         
/* 189 */         if (for_embedding) {
/*     */           
/* 191 */           byte[] headerData_emb = Jbig2SegmentReader.copyByteArray(s.headerData);
/* 192 */           if (s.page_association_size) {
/* 193 */             headerData_emb[s.page_association_offset] = 0;
/* 194 */             headerData_emb[s.page_association_offset + 1] = 0;
/* 195 */             headerData_emb[s.page_association_offset + 2] = 0;
/* 196 */             headerData_emb[s.page_association_offset + 3] = 1;
/*     */           } else {
/* 198 */             headerData_emb[s.page_association_offset] = 1;
/*     */           } 
/* 200 */           os.write(headerData_emb);
/*     */         } else {
/* 202 */           os.write(s.headerData);
/*     */         } 
/* 204 */         os.write(s.data); }
/*     */       
/* 206 */       os.close();
/* 207 */       return os.toByteArray();
/*     */     }
/*     */     
/*     */     public void addSegment(Jbig2SegmentReader.Jbig2Segment s) {
/* 211 */       this.segs.put(Integer.valueOf(s.segmentNumber), s);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public Jbig2SegmentReader(RandomAccessFileOrArray ra) throws IOException {
/* 217 */     this.ra = ra;
/*     */   }
/*     */   
/*     */   public static byte[] copyByteArray(byte[] b) {
/* 221 */     byte[] bc = new byte[b.length];
/* 222 */     System.arraycopy(b, 0, bc, 0, b.length);
/* 223 */     return bc;
/*     */   }
/*     */   
/*     */   public void read() throws IOException {
/* 227 */     if (this.read) {
/* 228 */       throw new IllegalStateException("already.attempted.a.read.on.this.jbig2.file");
/*     */     }
/* 230 */     this.read = true;
/*     */     
/* 232 */     readFileHeader();
/*     */     
/* 234 */     if (this.sequential) {
/*     */       
/*     */       do {
/* 237 */         Jbig2Segment tmp = readHeader();
/* 238 */         readSegment(tmp);
/* 239 */         this.segments.put(Integer.valueOf(tmp.segmentNumber), tmp);
/* 240 */       } while (this.ra.getPosition() < this.ra.length());
/*     */     } else {
/*     */       
/*     */       while (true) {
/*     */         
/* 245 */         Jbig2Segment tmp = readHeader();
/* 246 */         this.segments.put(Integer.valueOf(tmp.segmentNumber), tmp);
/* 247 */         if (tmp.type == 51) {
/* 248 */           for (Iterator<Integer> iterator = this.segments.keySet().iterator(); iterator.hasNext(); ) { int integer = ((Integer)iterator.next()).intValue();
/* 249 */             readSegment(this.segments.get(Integer.valueOf(integer))); }
/*     */           
/*     */           return;
/*     */         } 
/*     */       } 
/*     */     }  } void readSegment(Jbig2Segment s) throws IOException {
/* 255 */     int ptr = (int)this.ra.getPosition();
/*     */     
/* 257 */     if (s.dataLength == 4294967295L) {
/*     */       return;
/*     */     }
/*     */ 
/*     */     
/* 262 */     byte[] data = new byte[(int)s.dataLength];
/* 263 */     this.ra.read(data);
/* 264 */     s.data = data;
/*     */     
/* 266 */     if (s.type == 48) {
/* 267 */       int last = (int)this.ra.getPosition();
/* 268 */       this.ra.seek(ptr);
/* 269 */       int page_bitmap_width = this.ra.readInt();
/* 270 */       int page_bitmap_height = this.ra.readInt();
/* 271 */       this.ra.seek(last);
/* 272 */       Jbig2Page p = this.pages.get(Integer.valueOf(s.page));
/* 273 */       if (p == null) {
/* 274 */         throw (new IOException("Referring to widht or height of a page we haven't seen yet: {0}")).setMessageParams(new Object[] { Integer.valueOf(s.page) });
/*     */       }
/*     */       
/* 277 */       p.pageBitmapWidth = page_bitmap_width;
/* 278 */       p.pageBitmapHeight = page_bitmap_height;
/*     */     } 
/*     */   }
/*     */   
/*     */   Jbig2Segment readHeader() throws IOException {
/* 283 */     int segment_page_association, ptr = (int)this.ra.getPosition();
/*     */     
/* 285 */     int segment_number = this.ra.readInt();
/* 286 */     Jbig2Segment s = new Jbig2Segment(segment_number);
/*     */ 
/*     */     
/* 289 */     int segment_header_flags = this.ra.read();
/* 290 */     boolean deferred_non_retain = ((segment_header_flags & 0x80) == 128);
/* 291 */     s.deferredNonRetain = deferred_non_retain;
/* 292 */     boolean page_association_size = ((segment_header_flags & 0x40) == 64);
/* 293 */     int segment_type = segment_header_flags & 0x3F;
/* 294 */     s.type = segment_type;
/*     */ 
/*     */     
/* 297 */     int referred_to_byte0 = this.ra.read();
/* 298 */     int count_of_referred_to_segments = (referred_to_byte0 & 0xE0) >> 5;
/* 299 */     int[] referred_to_segment_numbers = null;
/* 300 */     boolean[] segment_retention_flags = null;
/*     */     
/* 302 */     if (count_of_referred_to_segments == 7) {
/*     */       
/* 304 */       this.ra.seek(this.ra.getPosition() - 1L);
/* 305 */       count_of_referred_to_segments = this.ra.readInt() & 0x1FFFFFFF;
/* 306 */       segment_retention_flags = new boolean[count_of_referred_to_segments + 1];
/* 307 */       int j = 0;
/* 308 */       int referred_to_current_byte = 0;
/*     */       do {
/* 310 */         int k = j % 8;
/* 311 */         if (k == 0) {
/* 312 */           referred_to_current_byte = this.ra.read();
/*     */         }
/* 314 */         segment_retention_flags[j] = ((1 << k & referred_to_current_byte) >> k == 1);
/* 315 */         ++j;
/* 316 */       } while (j <= count_of_referred_to_segments);
/*     */     }
/* 318 */     else if (count_of_referred_to_segments <= 4) {
/*     */       
/* 320 */       segment_retention_flags = new boolean[count_of_referred_to_segments + 1];
/* 321 */       referred_to_byte0 &= 0x1F;
/* 322 */       for (int j = 0; j <= count_of_referred_to_segments; j++) {
/* 323 */         segment_retention_flags[j] = ((1 << j & referred_to_byte0) >> j == 1);
/*     */       }
/*     */     }
/* 326 */     else if (count_of_referred_to_segments == 5 || count_of_referred_to_segments == 6) {
/* 327 */       throw (new IOException("Count of referred-to segments has forbidden value in the header for segment {0} starting at {1}"))
/* 328 */         .setMessageParams(new Object[] { Integer.valueOf(segment_number), Integer.valueOf(ptr) });
/*     */     } 
/*     */     
/* 331 */     s.segmentRetentionFlags = segment_retention_flags;
/* 332 */     s.countOfReferredToSegments = count_of_referred_to_segments;
/*     */ 
/*     */     
/* 335 */     referred_to_segment_numbers = new int[count_of_referred_to_segments + 1];
/* 336 */     for (int i = 1; i <= count_of_referred_to_segments; i++) {
/* 337 */       if (segment_number <= 256) {
/* 338 */         referred_to_segment_numbers[i] = this.ra.read();
/* 339 */       } else if (segment_number <= 65536) {
/* 340 */         referred_to_segment_numbers[i] = this.ra.readUnsignedShort();
/*     */       } else {
/*     */         
/* 343 */         referred_to_segment_numbers[i] = (int)this.ra.readUnsignedInt();
/*     */       } 
/*     */     } 
/* 346 */     s.referredToSegmentNumbers = referred_to_segment_numbers;
/*     */ 
/*     */ 
/*     */     
/* 350 */     int page_association_offset = (int)this.ra.getPosition() - ptr;
/* 351 */     if (page_association_size) {
/* 352 */       segment_page_association = this.ra.readInt();
/*     */     } else {
/* 354 */       segment_page_association = this.ra.read();
/*     */     } 
/* 356 */     if (segment_page_association < 0) {
/* 357 */       throw (new IOException("Page {0} is invalid for segment {1} starting at {2}"))
/* 358 */         .setMessageParams(new Object[] { Integer.valueOf(segment_page_association), Integer.valueOf(segment_number), Integer.valueOf(ptr) });
/*     */     }
/* 360 */     s.page = segment_page_association;
/*     */     
/* 362 */     s.page_association_size = page_association_size;
/* 363 */     s.page_association_offset = page_association_offset;
/*     */     
/* 365 */     if (segment_page_association > 0 && !this.pages.containsKey(Integer.valueOf(segment_page_association))) {
/* 366 */       this.pages.put(Integer.valueOf(segment_page_association), new Jbig2Page(segment_page_association, this));
/*     */     }
/* 368 */     if (segment_page_association > 0) {
/* 369 */       ((Jbig2Page)this.pages.get(Integer.valueOf(segment_page_association))).addSegment(s);
/*     */     } else {
/* 371 */       this.globals.add(s);
/*     */     } 
/*     */ 
/*     */     
/* 375 */     long segment_data_length = this.ra.readUnsignedInt();
/*     */     
/* 377 */     s.dataLength = segment_data_length;
/*     */     
/* 379 */     int end_ptr = (int)this.ra.getPosition();
/* 380 */     this.ra.seek(ptr);
/* 381 */     byte[] header_data = new byte[end_ptr - ptr];
/* 382 */     this.ra.read(header_data);
/* 383 */     s.headerData = header_data;
/*     */     
/* 385 */     return s;
/*     */   }
/*     */   
/*     */   void readFileHeader() throws IOException {
/* 389 */     this.ra.seek(0L);
/* 390 */     byte[] idstring = new byte[8];
/* 391 */     this.ra.read(idstring);
/*     */     
/* 393 */     byte[] refidstring = { -105, 74, 66, 50, 13, 10, 26, 10 };
/*     */     
/* 395 */     for (int i = 0; i < idstring.length; i++) {
/* 396 */       if (idstring[i] != refidstring[i]) {
/* 397 */         throw (new IOException("File header idstring is not good at byte {0}")).setMessageParams(new Object[] { Integer.valueOf(i) });
/*     */       }
/*     */     } 
/*     */     
/* 401 */     int fileheaderflags = this.ra.read();
/*     */     
/* 403 */     this.sequential = ((fileheaderflags & 0x1) == 1);
/* 404 */     this.number_of_pages_known = ((fileheaderflags & 0x2) == 0);
/*     */     
/* 406 */     if ((fileheaderflags & 0xFC) != 0) {
/* 407 */       throw new IOException("File header flags bits from 2 to 7 should be 0, some not");
/*     */     }
/*     */     
/* 410 */     if (this.number_of_pages_known) {
/* 411 */       this.number_of_pages = this.ra.readInt();
/*     */     }
/*     */   }
/*     */   
/*     */   public int numberOfPages() {
/* 416 */     return this.pages.size();
/*     */   }
/*     */   
/*     */   public int getPageHeight(int i) {
/* 420 */     return ((Jbig2Page)this.pages.get(Integer.valueOf(i))).pageBitmapHeight;
/*     */   }
/*     */   
/*     */   public int getPageWidth(int i) {
/* 424 */     return ((Jbig2Page)this.pages.get(Integer.valueOf(i))).pageBitmapWidth;
/*     */   }
/*     */   
/*     */   public Jbig2Page getPage(int page) {
/* 428 */     return this.pages.get(Integer.valueOf(page));
/*     */   }
/*     */   
/*     */   public byte[] getGlobal(boolean for_embedding) {
/* 432 */     ByteArrayOutputStream os = new ByteArrayOutputStream();
/* 433 */     byte[] streamBytes = null;
/*     */     try {
/* 435 */       for (Jbig2Segment element : this.globals) {
/* 436 */         Jbig2Segment s = element;
/* 437 */         if (for_embedding && (s.type == 51 || s.type == 49)) {
/*     */           continue;
/*     */         }
/*     */         
/* 441 */         os.write(s.headerData);
/* 442 */         os.write(s.data);
/*     */       } 
/*     */       
/* 445 */       if (os.size() > 0) {
/* 446 */         streamBytes = os.toByteArray();
/*     */       }
/* 448 */       os.close();
/* 449 */     } catch (IOException e) {
/* 450 */       Logger logger = LoggerFactory.getLogger(Jbig2SegmentReader.class);
/* 451 */       logger.debug(e.getMessage());
/*     */     } 
/*     */     
/* 454 */     return streamBytes;
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 459 */     if (this.read) {
/* 460 */       return "Jbig2SegmentReader: number of pages: " + numberOfPages();
/*     */     }
/* 462 */     return "Jbig2SegmentReader in indeterminate state.";
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/io/codec/Jbig2SegmentReader.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */