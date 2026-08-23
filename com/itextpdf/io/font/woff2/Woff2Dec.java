/*      */ package com.itextpdf.io.font.woff2;
/*      */ 
/*      */ import com.itextpdf.io.codec.brotli.dec.BrotliInputStream;
/*      */ import com.itextpdf.io.util.MessageFormatUtil;
/*      */ import java.io.ByteArrayInputStream;
/*      */ import java.io.IOException;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Arrays;
/*      */ import java.util.Collections;
/*      */ import java.util.HashMap;
/*      */ import java.util.List;
/*      */ import java.util.Map;
/*      */ import java.util.TreeMap;
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
/*      */ class Woff2Dec
/*      */ {
/*      */   private static final int kGlyfOnCurve = 1;
/*      */   private static final int kGlyfXShort = 2;
/*      */   private static final int kGlyfYShort = 4;
/*      */   private static final int kGlyfRepeat = 8;
/*      */   private static final int kGlyfThisXIsSame = 16;
/*      */   private static final int kGlyfThisYIsSame = 32;
/*      */   private static final int FLAG_ARG_1_AND_2_ARE_WORDS = 1;
/*      */   private static final int FLAG_WE_HAVE_A_SCALE = 8;
/*      */   private static final int FLAG_MORE_COMPONENTS = 32;
/*      */   private static final int FLAG_WE_HAVE_AN_X_AND_Y_SCALE = 64;
/*      */   private static final int FLAG_WE_HAVE_A_TWO_BY_TWO = 128;
/*      */   private static final int FLAG_WE_HAVE_INSTRUCTIONS = 256;
/*      */   private static final int kCheckSumAdjustmentOffset = 8;
/*      */   private static final int kEndPtsOfContoursOffset = 10;
/*      */   private static final int kCompositeGlyphBegin = 10;
/*      */   private static final int kDefaultGlyphBuf = 5120;
/*      */   private static final float kMaxPlausibleCompressionRatio = 100.0F;
/*      */   
/*      */   private static class TtcFont
/*      */   {
/*      */     public int flavor;
/*      */     public int dst_offset;
/*      */     public int header_checksum;
/*      */     public short[] table_indices;
/*      */     
/*      */     private TtcFont() {}
/*      */   }
/*      */   
/*      */   private static class Woff2Header
/*      */   {
/*      */     public int flavor;
/*      */     public int header_version;
/*      */     public short num_tables;
/*      */     public long compressed_offset;
/*      */     public int compressed_length;
/*      */     public int uncompressed_size;
/*      */     public Woff2Common.Table[] tables;
/*      */     public Woff2Dec.TtcFont[] ttc_fonts;
/*      */     
/*      */     private Woff2Header() {}
/*      */   }
/*      */   
/*      */   private static class Woff2FontInfo
/*      */   {
/*      */     public short num_glyphs;
/*      */     public short index_format;
/*      */     public short num_hmetrics;
/*      */     public short[] x_mins;
/*      */     
/*      */     private Woff2FontInfo() {}
/*      */     
/*  114 */     public Map<Integer, Integer> table_entry_by_tag = new HashMap<>();
/*      */   }
/*      */   
/*      */   private static class RebuildMetadata {
/*      */     int header_checksum;
/*      */     Woff2Dec.Woff2FontInfo[] font_infos;
/*      */     
/*      */     private RebuildMetadata() {}
/*      */     
/*  123 */     Map<Woff2Dec.TableChecksumInfo, Integer> checksums = new HashMap<>();
/*      */   }
/*      */   
/*      */   private static class TableChecksumInfo {
/*      */     public int tag;
/*      */     public int offset;
/*      */     
/*      */     public TableChecksumInfo(int tag, int offset) {
/*  131 */       this.tag = tag;
/*  132 */       this.offset = offset;
/*      */     }
/*      */ 
/*      */     
/*      */     public int hashCode() {
/*  137 */       return (new Integer(this.tag)).hashCode() * 13 + (new Integer(this.offset)).hashCode();
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean equals(Object o) {
/*  142 */       if (this == o) return true; 
/*  143 */       if (o instanceof TableChecksumInfo) {
/*  144 */         TableChecksumInfo info = (TableChecksumInfo)o;
/*  145 */         return (this.tag == info.tag && this.offset == info.offset);
/*      */       } 
/*  147 */       return false;
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   private static int withSign(int flag, int baseval) {
/*  153 */     return ((flag & 0x1) != 0) ? baseval : -baseval;
/*      */   }
/*      */   
/*      */   private static int tripletDecode(byte[] data, int flags_in_offset, int in_offset, int in_size, int n_points, Woff2Common.Point[] result) {
/*  157 */     int x = 0;
/*  158 */     int y = 0;
/*      */     
/*  160 */     if (n_points > in_size) {
/*  161 */       throw new FontCompressionException("Reconstructing woff2 glyph exception");
/*      */     }
/*  163 */     int triplet_index = 0;
/*      */     
/*  165 */     for (int i = 0; i < n_points; i++) {
/*  166 */       int n_data_bytes, dx, dy, flag = JavaUnsignedUtil.asU8(data[i + flags_in_offset]);
/*  167 */       boolean on_curve = (flag >> 7 == 0);
/*  168 */       flag &= 0x7F;
/*      */       
/*  170 */       if (flag < 84) {
/*  171 */         n_data_bytes = 1;
/*  172 */       } else if (flag < 120) {
/*  173 */         n_data_bytes = 2;
/*  174 */       } else if (flag < 124) {
/*  175 */         n_data_bytes = 3;
/*      */       } else {
/*  177 */         n_data_bytes = 4;
/*      */       } 
/*  179 */       if (triplet_index + n_data_bytes > in_size || triplet_index + n_data_bytes < triplet_index)
/*      */       {
/*  181 */         throw new FontCompressionException("Reconstructing woff2 glyph exception");
/*      */       }
/*      */       
/*  184 */       if (flag < 10) {
/*  185 */         dx = 0;
/*  186 */         dy = withSign(flag, ((flag & 0xE) << 7) + JavaUnsignedUtil.asU8(data[in_offset + triplet_index]));
/*  187 */       } else if (flag < 20) {
/*  188 */         dx = withSign(flag, ((flag - 10 & 0xE) << 7) + JavaUnsignedUtil.asU8(data[in_offset + triplet_index]));
/*  189 */         dy = 0;
/*  190 */       } else if (flag < 84) {
/*  191 */         int b0 = flag - 20;
/*  192 */         int b1 = JavaUnsignedUtil.asU8(data[in_offset + triplet_index]);
/*  193 */         dx = withSign(flag, 1 + (b0 & 0x30) + (b1 >> 4));
/*  194 */         dy = withSign(flag >> 1, 1 + ((b0 & 0xC) << 2) + (b1 & 0xF));
/*  195 */       } else if (flag < 120) {
/*  196 */         int b0 = flag - 84;
/*  197 */         dx = withSign(flag, 1 + (b0 / 12 << 8) + JavaUnsignedUtil.asU8(data[in_offset + triplet_index]));
/*  198 */         dy = withSign(flag >> 1, 1 + (b0 % 12 >> 2 << 8) + 
/*  199 */             JavaUnsignedUtil.asU8(data[in_offset + triplet_index + 1]));
/*  200 */       } else if (flag < 124) {
/*  201 */         int b2 = JavaUnsignedUtil.asU8(data[in_offset + triplet_index + 1]);
/*  202 */         dx = withSign(flag, (JavaUnsignedUtil.asU8(data[in_offset + triplet_index]) << 4) + (b2 >> 4));
/*  203 */         dy = withSign(flag >> 1, ((b2 & 0xF) << 8) + JavaUnsignedUtil.asU8(data[in_offset + triplet_index + 2]));
/*      */       } else {
/*  205 */         dx = withSign(flag, (JavaUnsignedUtil.asU8(data[in_offset + triplet_index]) << 8) + JavaUnsignedUtil.asU8(data[in_offset + triplet_index + 1]));
/*  206 */         dy = withSign(flag >> 1, (
/*  207 */             JavaUnsignedUtil.asU8(data[in_offset + triplet_index + 2]) << 8) + JavaUnsignedUtil.asU8(data[in_offset + triplet_index + 3]));
/*      */       } 
/*  209 */       triplet_index += n_data_bytes;
/*      */       
/*  211 */       x += dx;
/*  212 */       y += dy;
/*  213 */       result[i] = new Woff2Common.Point(x, y, on_curve);
/*      */     } 
/*  215 */     return triplet_index;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static int storePoints(int n_points, Woff2Common.Point[] points, int n_contours, int instruction_length, byte[] dst, int dst_size) {
/*  225 */     int flag_offset = 10 + 2 * n_contours + 2 + instruction_length;
/*      */     
/*  227 */     int last_flag = -1;
/*  228 */     int repeat_count = 0;
/*  229 */     int last_x = 0;
/*  230 */     int last_y = 0;
/*  231 */     int x_bytes = 0;
/*  232 */     int y_bytes = 0;
/*      */     
/*  234 */     for (int i = 0; i < n_points; i++) {
/*  235 */       Woff2Common.Point point = points[i];
/*  236 */       int flag = point.on_curve ? 1 : 0;
/*  237 */       int dx = point.x - last_x;
/*  238 */       int dy = point.y - last_y;
/*  239 */       if (dx == 0) {
/*  240 */         flag |= 0x10;
/*  241 */       } else if (dx > -256 && dx < 256) {
/*  242 */         flag |= 0x2 | ((dx > 0) ? 16 : 0);
/*  243 */         x_bytes++;
/*      */       } else {
/*  245 */         x_bytes += 2;
/*      */       } 
/*  247 */       if (dy == 0) {
/*  248 */         flag |= 0x20;
/*  249 */       } else if (dy > -256 && dy < 256) {
/*  250 */         flag |= 0x4 | ((dy > 0) ? 32 : 0);
/*  251 */         y_bytes++;
/*      */       } else {
/*  253 */         y_bytes += 2;
/*      */       } 
/*      */       
/*  256 */       if (flag == last_flag && repeat_count != 255) {
/*  257 */         dst[flag_offset - 1] = (byte)(dst[flag_offset - 1] | 0x8);
/*  258 */         repeat_count++;
/*      */       } else {
/*  260 */         if (repeat_count != 0) {
/*  261 */           if (flag_offset >= dst_size) {
/*  262 */             throw new FontCompressionException("Reconstructing woff2 glyph's point exception");
/*      */           }
/*  264 */           dst[flag_offset++] = (byte)repeat_count;
/*      */         } 
/*  266 */         if (flag_offset >= dst_size) {
/*  267 */           throw new FontCompressionException("Reconstructing woff2 glyph's point exception");
/*      */         }
/*  269 */         dst[flag_offset++] = (byte)flag;
/*  270 */         repeat_count = 0;
/*      */       } 
/*  272 */       last_x = point.x;
/*  273 */       last_y = point.y;
/*  274 */       last_flag = flag;
/*      */     } 
/*      */     
/*  277 */     if (repeat_count != 0) {
/*  278 */       if (flag_offset >= dst_size) {
/*  279 */         throw new FontCompressionException("Reconstructing woff2 glyph's point exception");
/*      */       }
/*  281 */       dst[flag_offset++] = (byte)repeat_count;
/*      */     } 
/*  283 */     int xy_bytes = x_bytes + y_bytes;
/*  284 */     if (xy_bytes < x_bytes || flag_offset + xy_bytes < flag_offset || flag_offset + xy_bytes > dst_size)
/*      */     {
/*      */       
/*  287 */       throw new FontCompressionException("Reconstructing woff2 glyph's point exception");
/*      */     }
/*      */     
/*  290 */     int x_offset = flag_offset;
/*  291 */     int y_offset = flag_offset + x_bytes;
/*  292 */     last_x = 0;
/*  293 */     last_y = 0;
/*  294 */     for (int j = 0; j < n_points; j++) {
/*  295 */       int dx = (points[j]).x - last_x;
/*  296 */       if (dx != 0)
/*      */       {
/*  298 */         if (dx > -256 && dx < 256) {
/*  299 */           dst[x_offset++] = (byte)Math.abs(dx);
/*      */         } else {
/*      */           
/*  302 */           x_offset = StoreBytes.storeU16(dst, x_offset, dx);
/*      */         }  } 
/*  304 */       last_x += dx;
/*  305 */       int dy = (points[j]).y - last_y;
/*  306 */       if (dy != 0)
/*      */       {
/*  308 */         if (dy > -256 && dy < 256) {
/*  309 */           dst[y_offset++] = (byte)Math.abs(dy);
/*      */         } else {
/*  311 */           y_offset = StoreBytes.storeU16(dst, y_offset, dy);
/*      */         }  } 
/*  313 */       last_y += dy;
/*      */     } 
/*  315 */     int glyph_size = y_offset;
/*  316 */     return glyph_size;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static void computeBbox(int n_points, Woff2Common.Point[] points, byte[] dst) {
/*  323 */     int x_min = 0;
/*  324 */     int y_min = 0;
/*  325 */     int x_max = 0;
/*  326 */     int y_max = 0;
/*      */     
/*  328 */     if (n_points > 0) {
/*  329 */       x_min = (points[0]).x;
/*  330 */       x_max = (points[0]).x;
/*  331 */       y_min = (points[0]).y;
/*  332 */       y_max = (points[0]).y;
/*      */     } 
/*  334 */     for (int i = 1; i < n_points; i++) {
/*  335 */       int x = (points[i]).x;
/*  336 */       int y = (points[i]).y;
/*  337 */       x_min = Math.min(x, x_min);
/*  338 */       x_max = Math.max(x, x_max);
/*  339 */       y_min = Math.min(y, y_min);
/*  340 */       y_max = Math.max(y, y_max);
/*      */     } 
/*  342 */     int offset = 2;
/*  343 */     offset = StoreBytes.storeU16(dst, offset, x_min);
/*  344 */     offset = StoreBytes.storeU16(dst, offset, y_min);
/*  345 */     offset = StoreBytes.storeU16(dst, offset, x_max);
/*  346 */     offset = StoreBytes.storeU16(dst, offset, y_max);
/*      */   }
/*      */   
/*      */   private static CompositeGlyphInfo sizeOfComposite(Buffer composite_stream) {
/*      */     int i;
/*  351 */     composite_stream = new Buffer(composite_stream);
/*  352 */     int start_offset = composite_stream.getOffset();
/*  353 */     boolean we_have_instructions = false;
/*      */     
/*  355 */     int flags = 32;
/*  356 */     while ((flags & 0x20) != 0) {
/*  357 */       flags = JavaUnsignedUtil.asU16(composite_stream.readShort());
/*  358 */       i = we_have_instructions | (((flags & 0x100) != 0) ? 1 : 0);
/*  359 */       int arg_size = 2;
/*  360 */       if ((flags & 0x1) != 0) {
/*  361 */         arg_size += 4;
/*      */       } else {
/*  363 */         arg_size += 2;
/*      */       } 
/*  365 */       if ((flags & 0x8) != 0) {
/*  366 */         arg_size += 2;
/*  367 */       } else if ((flags & 0x40) != 0) {
/*  368 */         arg_size += 4;
/*  369 */       } else if ((flags & 0x80) != 0) {
/*  370 */         arg_size += 8;
/*      */       } 
/*  372 */       composite_stream.skip(arg_size);
/*      */     } 
/*      */     
/*  375 */     int size = composite_stream.getOffset() - start_offset;
/*  376 */     int j = i;
/*      */     
/*  378 */     return new CompositeGlyphInfo(size, j);
/*      */   }
/*      */   
/*      */   private static class CompositeGlyphInfo {
/*      */     public int size;
/*      */     public boolean have_instructions;
/*      */     
/*      */     public CompositeGlyphInfo(int size, boolean have_instructions) {
/*  386 */       this.size = size;
/*  387 */       this.have_instructions = have_instructions;
/*      */     }
/*      */   }
/*      */   
/*      */   private static void pad4(Woff2Out out) {
/*  392 */     byte[] zeroes = { 0, 0, 0 };
/*  393 */     if (out.size() + 3 < out.size()) {
/*  394 */       throw new FontCompressionException("woff2 padding overflow exception");
/*      */     }
/*  396 */     int pad_bytes = Round.round4(out.size()) - out.size();
/*  397 */     if (pad_bytes > 0) {
/*  398 */       out.write(zeroes, 0, pad_bytes);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static int storeLoca(int[] loca_values, int index_format, Woff2Out out) {
/*  406 */     long loca_size = loca_values.length;
/*  407 */     long offset_size = (index_format != 0) ? 4L : 2L;
/*  408 */     if (loca_size << 2L >> 2L != loca_size) {
/*  409 */       throw new FontCompressionException("woff2 loca table content size overflow exception");
/*      */     }
/*  411 */     byte[] loca_content = new byte[(int)(loca_size * offset_size)];
/*  412 */     int offset = 0;
/*  413 */     for (int i = 0; i < loca_values.length; i++) {
/*  414 */       int value = loca_values[i];
/*  415 */       if (index_format != 0) {
/*  416 */         offset = StoreBytes.storeU32(loca_content, offset, value);
/*      */       } else {
/*  418 */         offset = StoreBytes.storeU16(loca_content, offset, value >> 1);
/*      */       } 
/*      */     } 
/*  421 */     int checksum = Woff2Common.computeULongSum(loca_content, 0, loca_content.length);
/*  422 */     out.write(loca_content, 0, loca_content.length);
/*  423 */     return checksum;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static Checksums reconstructGlyf(byte[] data, int data_offset, Woff2Common.Table glyf_table, int glyph_checksum, Woff2Common.Table loca_table, int loca_checksum, Woff2FontInfo info, Woff2Out out) {
/*  431 */     int kNumSubStreams = 7;
/*  432 */     Buffer file = new Buffer(data, data_offset, glyf_table.transform_length);
/*      */     
/*  434 */     ArrayList<StreamInfo> substreams = new ArrayList<>(7);
/*  435 */     int glyf_start = out.size();
/*      */ 
/*      */     
/*  438 */     int version = file.readInt();
/*  439 */     info.num_glyphs = file.readShort();
/*  440 */     info.index_format = file.readShort();
/*      */     
/*  442 */     int offset = 36;
/*  443 */     if (offset > glyf_table.transform_length) {
/*  444 */       throw new FontCompressionException("Reconstructing woff2 glyf table exception");
/*      */     }
/*      */     
/*  447 */     for (int i = 0; i < 7; i++) {
/*      */       
/*  449 */       int substream_size = file.readInt();
/*  450 */       if (substream_size > glyf_table.transform_length - offset) {
/*  451 */         throw new FontCompressionException("Reconstructing woff2 glyf table exception");
/*      */       }
/*  453 */       substreams.add(new StreamInfo(data_offset + offset, substream_size));
/*  454 */       offset += substream_size;
/*      */     } 
/*  456 */     Buffer n_contour_stream = new Buffer(data, ((StreamInfo)substreams.get(0)).offset, ((StreamInfo)substreams.get(0)).length);
/*  457 */     Buffer n_points_stream = new Buffer(data, ((StreamInfo)substreams.get(1)).offset, ((StreamInfo)substreams.get(1)).length);
/*  458 */     Buffer flag_stream = new Buffer(data, ((StreamInfo)substreams.get(2)).offset, ((StreamInfo)substreams.get(2)).length);
/*  459 */     Buffer glyph_stream = new Buffer(data, ((StreamInfo)substreams.get(3)).offset, ((StreamInfo)substreams.get(3)).length);
/*  460 */     Buffer composite_stream = new Buffer(data, ((StreamInfo)substreams.get(4)).offset, ((StreamInfo)substreams.get(4)).length);
/*  461 */     Buffer bbox_stream = new Buffer(data, ((StreamInfo)substreams.get(5)).offset, ((StreamInfo)substreams.get(5)).length);
/*  462 */     Buffer instruction_stream = new Buffer(data, ((StreamInfo)substreams.get(6)).offset, ((StreamInfo)substreams.get(6)).length);
/*      */     
/*  464 */     int[] loca_values = new int[JavaUnsignedUtil.asU16(info.num_glyphs) + 1];
/*  465 */     ArrayList<Integer> n_points_vec = new ArrayList<>();
/*  466 */     Woff2Common.Point[] points = new Woff2Common.Point[0];
/*  467 */     int points_size = 0;
/*  468 */     int bbox_bitmap_offset = bbox_stream.getInitialOffset();
/*      */     
/*  470 */     int bitmap_length = JavaUnsignedUtil.asU16(info.num_glyphs) + 31 >> 5 << 2;
/*  471 */     bbox_stream.skip(bitmap_length);
/*      */ 
/*      */     
/*  474 */     int glyph_buf_size = 5120;
/*  475 */     byte[] glyph_buf = new byte[glyph_buf_size];
/*      */     
/*  477 */     info.x_mins = new short[JavaUnsignedUtil.asU16(info.num_glyphs)];
/*  478 */     for (int j = 0; j < JavaUnsignedUtil.asU16(info.num_glyphs); j++) {
/*  479 */       int glyph_size = 0;
/*  480 */       int n_contours = 0;
/*  481 */       boolean have_bbox = false;
/*  482 */       byte[] bitmap = new byte[bitmap_length];
/*  483 */       System.arraycopy(data, bbox_bitmap_offset, bitmap, 0, bitmap_length);
/*  484 */       if ((data[bbox_bitmap_offset + (j >> 3)] & 128 >> (j & 0x7)) != 0) {
/*  485 */         have_bbox = true;
/*      */       }
/*  487 */       n_contours = JavaUnsignedUtil.asU16(n_contour_stream.readShort());
/*      */       
/*  489 */       if (n_contours == 65535) {
/*      */         
/*  491 */         boolean have_instructions = false;
/*  492 */         int instruction_size = 0;
/*  493 */         if (!have_bbox)
/*      */         {
/*  495 */           throw new FontCompressionException("Reconstructing woff2 glyf table exception");
/*      */         }
/*      */ 
/*      */         
/*  499 */         CompositeGlyphInfo compositeGlyphInfo = sizeOfComposite(composite_stream);
/*  500 */         have_instructions = compositeGlyphInfo.have_instructions;
/*  501 */         int composite_size = compositeGlyphInfo.size;
/*  502 */         if (have_instructions) {
/*  503 */           instruction_size = VariableLength.read255UShort(glyph_stream);
/*      */         }
/*      */         
/*  506 */         int size_needed = 12 + composite_size + instruction_size;
/*  507 */         if (glyph_buf_size < size_needed) {
/*  508 */           glyph_buf = new byte[size_needed];
/*  509 */           glyph_buf_size = size_needed;
/*      */         } 
/*      */         
/*  512 */         glyph_size = StoreBytes.storeU16(glyph_buf, glyph_size, n_contours);
/*  513 */         bbox_stream.read(glyph_buf, glyph_size, 8);
/*  514 */         glyph_size += 8;
/*      */         
/*  516 */         composite_stream.read(glyph_buf, glyph_size, composite_size);
/*  517 */         glyph_size += composite_size;
/*  518 */         if (have_instructions) {
/*  519 */           glyph_size = StoreBytes.storeU16(glyph_buf, glyph_size, instruction_size);
/*  520 */           instruction_stream.read(glyph_buf, glyph_size, instruction_size);
/*  521 */           glyph_size += instruction_size;
/*      */         } 
/*  523 */       } else if (n_contours > 0) {
/*      */         
/*  525 */         n_points_vec.clear();
/*  526 */         int total_n_points = 0;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  532 */         for (int k = 0; k < n_contours; k++) {
/*  533 */           int n_points_contour = VariableLength.read255UShort(n_points_stream);
/*  534 */           n_points_vec.add(Integer.valueOf(n_points_contour));
/*  535 */           if (total_n_points + n_points_contour < total_n_points) {
/*  536 */             throw new FontCompressionException("Reconstructing woff2 glyf table exception");
/*      */           }
/*  538 */           total_n_points += n_points_contour;
/*      */         } 
/*  540 */         int flag_size = total_n_points;
/*  541 */         if (flag_size > flag_stream.getLength() - flag_stream.getOffset()) {
/*  542 */           throw new FontCompressionException("Reconstructing woff2 glyf table exception");
/*      */         }
/*  544 */         int flags_buf_offset = flag_stream.getInitialOffset() + flag_stream.getOffset();
/*  545 */         int triplet_buf_offset = glyph_stream.getInitialOffset() + glyph_stream.getOffset();
/*  546 */         int triplet_size = glyph_stream.getLength() - glyph_stream.getOffset();
/*  547 */         int triplet_bytes_consumed = 0;
/*  548 */         if (points_size < total_n_points) {
/*  549 */           points_size = total_n_points;
/*  550 */           points = new Woff2Common.Point[points_size];
/*      */         } 
/*  552 */         triplet_bytes_consumed = tripletDecode(data, flags_buf_offset, triplet_buf_offset, triplet_size, total_n_points, points);
/*      */ 
/*      */         
/*  555 */         flag_stream.skip(flag_size);
/*  556 */         glyph_stream.skip(triplet_bytes_consumed);
/*      */         
/*  558 */         int instruction_size = VariableLength.read255UShort(glyph_stream);
/*      */         
/*  560 */         if (total_n_points >= 134217728 || instruction_size >= 1073741824) {
/*  561 */           throw new FontCompressionException("Reconstructing woff2 glyf table exception");
/*      */         }
/*  563 */         int size_needed = 12 + 2 * n_contours + 5 * total_n_points + instruction_size;
/*      */         
/*  565 */         if (glyph_buf_size < size_needed) {
/*  566 */           glyph_buf = new byte[size_needed];
/*  567 */           glyph_buf_size = size_needed;
/*      */         } 
/*      */         
/*  570 */         glyph_size = StoreBytes.storeU16(glyph_buf, glyph_size, n_contours);
/*  571 */         if (have_bbox) {
/*  572 */           bbox_stream.read(glyph_buf, glyph_size, 8);
/*      */         } else {
/*  574 */           computeBbox(total_n_points, points, glyph_buf);
/*      */         } 
/*  576 */         glyph_size = 10;
/*  577 */         int end_point = -1;
/*  578 */         for (int contour_ix = 0; contour_ix < n_contours; contour_ix++) {
/*  579 */           end_point += ((Integer)n_points_vec.get(contour_ix)).intValue();
/*  580 */           if (end_point >= 65536) {
/*  581 */             throw new FontCompressionException("Reconstructing woff2 glyf table exception");
/*      */           }
/*  583 */           glyph_size = StoreBytes.storeU16(glyph_buf, glyph_size, end_point);
/*      */         } 
/*      */         
/*  586 */         glyph_size = StoreBytes.storeU16(glyph_buf, glyph_size, instruction_size);
/*  587 */         instruction_stream.read(glyph_buf, glyph_size, instruction_size);
/*  588 */         glyph_size += instruction_size;
/*      */         
/*  590 */         glyph_size = storePoints(total_n_points, points, n_contours, instruction_size, glyph_buf, glyph_buf_size);
/*      */       } 
/*      */       
/*  593 */       loca_values[j] = out.size() - glyf_start;
/*  594 */       out.write(glyph_buf, 0, glyph_size);
/*      */ 
/*      */       
/*  597 */       pad4(out);
/*      */       
/*  599 */       glyph_checksum += Woff2Common.computeULongSum(glyph_buf, 0, glyph_size);
/*      */ 
/*      */       
/*  602 */       if (n_contours > 0) {
/*  603 */         Buffer x_min_buf = new Buffer(glyph_buf, 2, 2);
/*  604 */         info.x_mins[j] = x_min_buf.readShort();
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/*  609 */     glyf_table.dst_length = out.size() - glyf_table.dst_offset;
/*  610 */     loca_table.dst_offset = out.size();
/*      */     
/*  612 */     loca_values[JavaUnsignedUtil.asU16(info.num_glyphs)] = glyf_table.dst_length;
/*  613 */     loca_checksum = storeLoca(loca_values, info.index_format, out);
/*  614 */     loca_table.dst_length = out.size() - loca_table.dst_offset;
/*      */     
/*  616 */     return new Checksums(loca_checksum, glyph_checksum);
/*      */   }
/*      */   
/*      */   private static class Checksums {
/*      */     public int loca_checksum;
/*      */     public int glyph_checksum;
/*      */     
/*      */     public Checksums(int loca_checksum, int glyph_checksum) {
/*  624 */       this.loca_checksum = loca_checksum;
/*  625 */       this.glyph_checksum = glyph_checksum;
/*      */     }
/*      */   }
/*      */   
/*      */   private static class StreamInfo {
/*      */     public int offset;
/*      */     public int length;
/*      */     
/*      */     public StreamInfo(int offset, int length) {
/*  634 */       this.offset = offset;
/*  635 */       this.length = length;
/*      */     }
/*      */   }
/*      */   
/*      */   private static Woff2Common.Table findTable(ArrayList<Woff2Common.Table> tables, int tag) {
/*  640 */     for (Woff2Common.Table table : tables) {
/*  641 */       if (table.tag == tag) {
/*  642 */         return table;
/*      */       }
/*      */     } 
/*  645 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static short readNumHMetrics(byte[] data, int offset, int data_length) {
/*  651 */     Buffer buffer = new Buffer(data, offset, data_length);
/*  652 */     buffer.skip(34);
/*  653 */     short result = buffer.readShort();
/*  654 */     return result;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static int reconstructTransformedHmtx(byte[] transformed_buf, int transformed_offset, int transformed_size, int num_glyphs, int num_hmetrics, short[] x_mins, Woff2Out out) {
/*  664 */     Buffer hmtx_buff_in = new Buffer(transformed_buf, transformed_offset, transformed_size);
/*      */     
/*  666 */     int hmtx_flags = JavaUnsignedUtil.asU8(hmtx_buff_in.readByte());
/*      */ 
/*      */ 
/*      */     
/*  670 */     boolean has_proportional_lsbs = ((hmtx_flags & 0x1) == 0);
/*  671 */     boolean has_monospace_lsbs = ((hmtx_flags & 0x2) == 0);
/*      */ 
/*      */     
/*  674 */     if (has_proportional_lsbs && has_monospace_lsbs) {
/*  675 */       throw new FontCompressionException("Reconstructing woff2 hmtx table exception");
/*      */     }
/*      */     
/*  678 */     if (x_mins == null || x_mins.length != num_glyphs) {
/*  679 */       throw new FontCompressionException("Reconstructing woff2 hmtx table exception");
/*      */     }
/*      */ 
/*      */     
/*  683 */     if (num_hmetrics > num_glyphs) {
/*  684 */       throw new FontCompressionException("Reconstructing woff2 hmtx table exception");
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*  689 */     if (num_hmetrics < 1) {
/*  690 */       throw new FontCompressionException("Reconstructing woff2 hmtx table exception");
/*      */     }
/*      */     
/*  693 */     short[] advance_widths = new short[num_hmetrics]; int i;
/*  694 */     for (i = 0; i < num_hmetrics; i++) {
/*      */       
/*  696 */       short advance_width = hmtx_buff_in.readShort();
/*  697 */       advance_widths[i] = advance_width;
/*      */     } 
/*      */     
/*  700 */     short[] lsbs = new short[num_glyphs];
/*  701 */     for (i = 0; i < num_hmetrics; i++) {
/*      */       short lsb;
/*  703 */       if (has_proportional_lsbs) {
/*  704 */         lsb = hmtx_buff_in.readShort();
/*      */       } else {
/*  706 */         lsb = x_mins[i];
/*      */       } 
/*  708 */       lsbs[i] = lsb;
/*      */     } 
/*      */     
/*  711 */     for (i = num_hmetrics; i < num_glyphs; i++) {
/*      */       short lsb;
/*  713 */       if (has_monospace_lsbs) {
/*  714 */         lsb = hmtx_buff_in.readShort();
/*      */       } else {
/*  716 */         lsb = x_mins[i];
/*      */       } 
/*  718 */       lsbs[i] = lsb;
/*      */     } 
/*      */ 
/*      */     
/*  722 */     int hmtx_output_size = 2 * num_glyphs + 2 * num_hmetrics;
/*  723 */     byte[] hmtx_table = new byte[hmtx_output_size];
/*  724 */     int dst_offset = 0;
/*  725 */     for (int j = 0; j < num_glyphs; j++) {
/*  726 */       if (j < num_hmetrics) {
/*  727 */         dst_offset = StoreBytes.storeU16(hmtx_table, dst_offset, advance_widths[j]);
/*      */       }
/*  729 */       dst_offset = StoreBytes.storeU16(hmtx_table, dst_offset, lsbs[j]);
/*      */     } 
/*      */     
/*  732 */     int checksum = Woff2Common.computeULongSum(hmtx_table, 0, hmtx_output_size);
/*  733 */     out.write(hmtx_table, 0, hmtx_output_size);
/*      */     
/*  735 */     return checksum;
/*      */   }
/*      */   
/*      */   private static void woff2Uncompress(byte[] dst_buf, int dst_offset, int dst_length, byte[] src_buf, int src_offset, int src_length) {
/*  739 */     int remain = dst_length;
/*      */     try {
/*  741 */       BrotliInputStream stream = new BrotliInputStream(new ByteArrayInputStream(src_buf, src_offset, src_length));
/*  742 */       while (remain > 0) {
/*  743 */         int read = stream.read(dst_buf, dst_offset, dst_length);
/*  744 */         if (read < 0) {
/*  745 */           throw new FontCompressionException("Woff2 brotli decoding exception");
/*      */         }
/*  747 */         remain -= read;
/*      */       } 
/*      */       
/*  750 */       if (stream.read() != -1) {
/*  751 */         throw new FontCompressionException("Woff2 brotli decoding exception");
/*      */       }
/*  753 */     } catch (IOException any) {
/*  754 */       throw new FontCompressionException("Woff2 brotli decoding exception");
/*      */     } 
/*  756 */     if (remain != 0) {
/*  757 */       throw new FontCompressionException("Woff2 brotli decoding exception");
/*      */     }
/*      */   }
/*      */   
/*      */   private static void readTableDirectory(Buffer file, Woff2Common.Table[] tables, int num_tables) {
/*  762 */     int src_offset = 0;
/*  763 */     for (int i = 0; i < num_tables; i++) {
/*  764 */       int tag; Woff2Common.Table table = new Woff2Common.Table();
/*  765 */       tables[i] = table;
/*  766 */       int flag_byte = JavaUnsignedUtil.asU8(file.readByte());
/*      */       
/*  768 */       if ((flag_byte & 0x3F) == 63) {
/*  769 */         tag = file.readInt();
/*      */       } else {
/*  771 */         tag = TableTags.kKnownTags[flag_byte & 0x3F];
/*      */       } 
/*  773 */       int flags = 0;
/*  774 */       int xform_version = flag_byte >> 6 & 0x3;
/*      */ 
/*      */       
/*  777 */       if (tag == 1735162214 || tag == 1819239265) {
/*  778 */         if (xform_version == 0) {
/*  779 */           flags |= 0x100;
/*      */         }
/*  781 */       } else if (xform_version != 0) {
/*  782 */         flags |= 0x100;
/*      */       } 
/*  784 */       flags |= xform_version;
/*      */       
/*  786 */       int dst_length = VariableLength.readBase128(file);
/*  787 */       int transform_length = dst_length;
/*  788 */       if ((flags & 0x100) != 0) {
/*  789 */         transform_length = VariableLength.readBase128(file);
/*  790 */         if (tag == 1819239265 && transform_length != 0) {
/*  791 */           throw new FontCompressionException("Reading woff2 tables directory exception");
/*      */         }
/*      */       } 
/*  794 */       if (src_offset + transform_length < src_offset) {
/*  795 */         throw new FontCompressionException("Reading woff2 tables directory exception");
/*      */       }
/*  797 */       table.src_offset = src_offset;
/*  798 */       table.src_length = transform_length;
/*  799 */       src_offset += transform_length;
/*      */       
/*  801 */       table.tag = tag;
/*  802 */       table.flags = flags;
/*  803 */       table.transform_length = transform_length;
/*  804 */       table.dst_length = dst_length;
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private static int storeOffsetTable(byte[] result, int offset, int flavor, int num_tables) {
/*  810 */     offset = StoreBytes.storeU32(result, offset, flavor);
/*  811 */     offset = StoreBytes.storeU16(result, offset, num_tables);
/*  812 */     int max_pow2 = 0;
/*  813 */     while (1 << max_pow2 + 1 <= num_tables) {
/*  814 */       max_pow2++;
/*      */     }
/*  816 */     int output_search_range = 1 << max_pow2 << 4;
/*  817 */     offset = StoreBytes.storeU16(result, offset, output_search_range);
/*  818 */     offset = StoreBytes.storeU16(result, offset, max_pow2);
/*      */     
/*  820 */     offset = StoreBytes.storeU16(result, offset, (num_tables << 4) - output_search_range);
/*  821 */     return offset;
/*      */   }
/*      */   
/*      */   private static int storeTableEntry(byte[] result, int offset, int tag) {
/*  825 */     offset = StoreBytes.storeU32(result, offset, tag);
/*  826 */     offset = StoreBytes.storeU32(result, offset, 0);
/*  827 */     offset = StoreBytes.storeU32(result, offset, 0);
/*  828 */     offset = StoreBytes.storeU32(result, offset, 0);
/*  829 */     return offset;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static long computeOffsetToFirstTable(Woff2Header hdr) {
/*  835 */     long offset = (12 + 16 * hdr.num_tables);
/*      */     
/*  837 */     if (hdr.header_version != 0) {
/*  838 */       offset = (Woff2Common.collectionHeaderSize(hdr.header_version, hdr.ttc_fonts.length) + 12 * hdr.ttc_fonts.length);
/*      */       
/*  840 */       for (TtcFont ttc_font : hdr.ttc_fonts) {
/*  841 */         offset += (16 * ttc_font.table_indices.length);
/*      */       }
/*      */     } 
/*  844 */     return offset;
/*      */   }
/*      */   
/*      */   private static ArrayList<Woff2Common.Table> tables(Woff2Header hdr, int font_index) {
/*  848 */     ArrayList<Woff2Common.Table> tables = new ArrayList<>();
/*  849 */     if (hdr.header_version != 0) {
/*  850 */       for (short index : (hdr.ttc_fonts[font_index]).table_indices) {
/*  851 */         tables.add(hdr.tables[JavaUnsignedUtil.asU16(index)]);
/*      */       }
/*      */     } else {
/*  854 */       for (Woff2Common.Table table : hdr.tables) {
/*  855 */         tables.add(table);
/*      */       }
/*      */     } 
/*  858 */     return tables;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static void reconstructFont(byte[] transformed_buf, int transformed_buf_offset, int transformed_buf_size, RebuildMetadata metadata, Woff2Header hdr, int font_index, Woff2Out out) {
/*  868 */     int dest_offset = out.size();
/*  869 */     byte[] table_entry = new byte[12];
/*  870 */     Woff2FontInfo info = metadata.font_infos[font_index];
/*  871 */     ArrayList<Woff2Common.Table> tables = tables(hdr, font_index);
/*      */ 
/*      */     
/*  874 */     if (((findTable(tables, 1735162214) != null) ? true : false) != ((findTable(tables, 1819239265) != null) ? true : false)) {
/*  875 */       throw new FontCompressionException("Reconstructing woff2 table directory exception");
/*      */     }
/*      */     
/*  878 */     int font_checksum = metadata.header_checksum;
/*  879 */     if (hdr.header_version != 0) {
/*  880 */       font_checksum = (hdr.ttc_fonts[font_index]).header_checksum;
/*      */     }
/*      */     
/*  883 */     int loca_checksum = 0;
/*  884 */     for (int i = 0; i < tables.size(); i++) {
/*  885 */       Woff2Common.Table table = tables.get(i);
/*      */       
/*  887 */       TableChecksumInfo checksum_key = new TableChecksumInfo(table.tag, table.src_offset);
/*  888 */       boolean reused = metadata.checksums.containsKey(checksum_key);
/*  889 */       if (font_index == 0 && reused) {
/*  890 */         throw new FontCompressionException("Reconstructing woff2 table directory exception");
/*      */       }
/*      */ 
/*      */ 
/*      */       
/*  895 */       if (table.src_offset + table.src_length > transformed_buf_size) {
/*  896 */         throw new FontCompressionException("Reconstructing woff2 table directory exception");
/*      */       }
/*      */       
/*  899 */       if (table.tag == 1751672161) {
/*  900 */         info.num_hmetrics = readNumHMetrics(transformed_buf, transformed_buf_offset + table.src_offset, table.src_length);
/*      */       }
/*      */       
/*  903 */       int checksum = 0;
/*  904 */       if (!reused) {
/*  905 */         if ((table.flags & 0x100) != 256) {
/*  906 */           if (table.tag == 1751474532) {
/*  907 */             if (table.src_length < 12) {
/*  908 */               throw new FontCompressionException("Reconstructing woff2 table directory exception");
/*      */             }
/*      */             
/*  911 */             StoreBytes.storeU32(transformed_buf, transformed_buf_offset + table.src_offset + 8, 0);
/*      */           } 
/*  913 */           table.dst_offset = dest_offset;
/*  914 */           checksum = Woff2Common.computeULongSum(transformed_buf, transformed_buf_offset + table.src_offset, table.src_length);
/*  915 */           out.write(transformed_buf, transformed_buf_offset + table.src_offset, table.src_length);
/*      */         }
/*  917 */         else if (table.tag == 1735162214) {
/*  918 */           table.dst_offset = dest_offset;
/*      */           
/*  920 */           Woff2Common.Table loca_table = findTable(tables, 1819239265);
/*      */           
/*  922 */           Checksums resultChecksum = reconstructGlyf(transformed_buf, transformed_buf_offset + table.src_offset, table, checksum, loca_table, loca_checksum, info, out);
/*  923 */           checksum = resultChecksum.glyph_checksum;
/*  924 */           loca_checksum = resultChecksum.loca_checksum;
/*  925 */         } else if (table.tag == 1819239265) {
/*      */           
/*  927 */           checksum = loca_checksum;
/*  928 */         } else if (table.tag == 1752003704) {
/*  929 */           table.dst_offset = dest_offset;
/*      */           
/*  931 */           checksum = reconstructTransformedHmtx(transformed_buf, transformed_buf_offset + table.src_offset, table.src_length, 
/*      */               
/*  933 */               JavaUnsignedUtil.asU16(info.num_glyphs), JavaUnsignedUtil.asU16(info.num_hmetrics), info.x_mins, out);
/*      */         } else {
/*      */           
/*  936 */           throw new FontCompressionException("Reconstructing woff2 table directory exception");
/*      */         } 
/*      */         
/*  939 */         metadata.checksums.put(checksum_key, Integer.valueOf(checksum));
/*      */       } else {
/*  941 */         checksum = ((Integer)metadata.checksums.get(checksum_key)).intValue();
/*      */       } 
/*  943 */       font_checksum += checksum;
/*      */ 
/*      */       
/*  946 */       StoreBytes.storeU32(table_entry, 0, checksum);
/*  947 */       StoreBytes.storeU32(table_entry, 4, table.dst_offset);
/*  948 */       StoreBytes.storeU32(table_entry, 8, table.dst_length);
/*  949 */       out.write(table_entry, 0, ((Integer)info.table_entry_by_tag.get(Integer.valueOf(table.tag))).intValue() + 4, 12);
/*      */ 
/*      */       
/*  952 */       font_checksum += Woff2Common.computeULongSum(table_entry, 0, 12);
/*      */       
/*  954 */       pad4(out);
/*      */       
/*  956 */       if (table.dst_offset + table.dst_length > out.size()) {
/*  957 */         throw new FontCompressionException("Reconstructing woff2 table directory exception");
/*      */       }
/*  959 */       dest_offset = out.size();
/*      */     } 
/*      */ 
/*      */     
/*  963 */     Woff2Common.Table head_table = findTable(tables, 1751474532);
/*  964 */     if (head_table != null) {
/*  965 */       if (head_table.dst_length < 12) {
/*  966 */         throw new FontCompressionException("Reconstructing woff2 table directory exception");
/*      */       }
/*  968 */       byte[] checksum_adjustment = new byte[4];
/*  969 */       StoreBytes.storeU32(checksum_adjustment, 0, -1313820742 - font_checksum);
/*  970 */       out.write(checksum_adjustment, 0, head_table.dst_offset + 8, 4);
/*      */     } 
/*      */   }
/*      */   
/*      */   private static void readWoff2Header(byte[] data, int length, Woff2Header hdr) {
/*  975 */     Buffer file = new Buffer(data, 0, length);
/*      */ 
/*      */     
/*  978 */     int signature = file.readInt();
/*  979 */     if (signature != 2001684018) {
/*  980 */       throw new FontCompressionException("Incorrect woff2 signature");
/*      */     }
/*  982 */     hdr.flavor = file.readInt();
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  987 */     int reported_length = file.readInt();
/*  988 */     assert reported_length > 0;
/*      */     
/*  990 */     if (length != reported_length) {
/*  991 */       throw new FontCompressionException("Reading woff2 header exception");
/*      */     }
/*      */     
/*  994 */     hdr.num_tables = file.readShort();
/*  995 */     if (hdr.num_tables == 0) {
/*  996 */       throw new FontCompressionException("Reading woff2 header exception");
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1002 */     file.skip(6);
/*      */     
/* 1004 */     hdr.compressed_length = file.readInt();
/* 1005 */     assert hdr.compressed_length >= 0;
/*      */ 
/*      */ 
/*      */     
/* 1009 */     file.skip(4);
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1014 */     int meta_offset = file.readInt();
/* 1015 */     assert meta_offset >= 0;
/* 1016 */     int meta_length = file.readInt();
/* 1017 */     assert meta_length >= 0;
/* 1018 */     int meta_length_orig = file.readInt();
/* 1019 */     assert meta_length_orig >= 0;
/* 1020 */     if (meta_offset != 0 && (
/* 1021 */       meta_offset >= length || length - meta_offset < meta_length)) {
/* 1022 */       throw new FontCompressionException("Reading woff2 header exception");
/*      */     }
/*      */ 
/*      */ 
/*      */     
/* 1027 */     int priv_offset = file.readInt();
/* 1028 */     assert priv_offset >= 0;
/* 1029 */     int priv_length = file.readInt();
/* 1030 */     assert priv_length >= 0;
/*      */     
/* 1032 */     if (priv_offset != 0 && (
/* 1033 */       priv_offset >= length || length - priv_offset < priv_length)) {
/* 1034 */       throw new FontCompressionException("Reading woff2 header exception");
/*      */     }
/*      */     
/* 1037 */     hdr.tables = new Woff2Common.Table[hdr.num_tables];
/* 1038 */     readTableDirectory(file, hdr.tables, hdr.num_tables);
/*      */ 
/*      */     
/* 1041 */     Woff2Common.Table last_table = hdr.tables[hdr.tables.length - 1];
/* 1042 */     hdr.uncompressed_size = last_table.src_offset + last_table.src_length;
/* 1043 */     assert hdr.uncompressed_size > 0;
/* 1044 */     if (hdr.uncompressed_size < last_table.src_offset) {
/* 1045 */       throw new FontCompressionException("Reading woff2 header exception");
/*      */     }
/*      */     
/* 1048 */     hdr.header_version = 0;
/*      */     
/* 1050 */     if (hdr.flavor == 1953784678) {
/* 1051 */       hdr.header_version = file.readInt();
/* 1052 */       if (hdr.header_version != 65536 && hdr.header_version != 131072) {
/* 1053 */         throw new FontCompressionException("Reading collection woff2 header exception");
/*      */       }
/*      */       
/* 1056 */       int num_fonts = VariableLength.read255UShort(file);
/* 1057 */       hdr.ttc_fonts = new TtcFont[num_fonts];
/*      */       
/* 1059 */       for (int i = 0; i < num_fonts; i++) {
/* 1060 */         TtcFont ttc_font = new TtcFont();
/* 1061 */         hdr.ttc_fonts[i] = ttc_font;
/*      */         
/* 1063 */         int num_tables = VariableLength.read255UShort(file);
/* 1064 */         ttc_font.flavor = file.readInt();
/*      */         
/* 1066 */         ttc_font.table_indices = new short[num_tables];
/*      */         
/* 1068 */         Woff2Common.Table glyf_table = null;
/* 1069 */         Woff2Common.Table loca_table = null;
/*      */         
/* 1071 */         for (int j = 0; j < num_tables; j++) {
/*      */           
/* 1073 */           int table_idx = VariableLength.read255UShort(file);
/* 1074 */           if (table_idx >= hdr.tables.length) {
/* 1075 */             throw new FontCompressionException("Reading collection woff2 header exception");
/*      */           }
/* 1077 */           ttc_font.table_indices[j] = (short)table_idx;
/*      */           
/* 1079 */           Woff2Common.Table table = hdr.tables[table_idx];
/* 1080 */           if (table.tag == 1819239265) {
/* 1081 */             loca_table = table;
/*      */           }
/* 1083 */           if (table.tag == 1735162214) {
/* 1084 */             glyf_table = table;
/*      */           }
/*      */         } 
/*      */ 
/*      */         
/* 1089 */         if (((glyf_table == null) ? true : false) != ((loca_table == null) ? true : false)) {
/* 1090 */           throw new FontCompressionException("Reading collection woff2 header exception");
/*      */         }
/*      */       } 
/*      */     } 
/*      */     
/* 1095 */     long first_table_offset = computeOffsetToFirstTable(hdr);
/*      */     
/* 1097 */     hdr.compressed_offset = file.getOffset();
/*      */     
/* 1099 */     if (hdr.compressed_offset > 2147483647L) {
/* 1100 */       throw new FontCompressionException("Reading woff2 header exception");
/*      */     }
/* 1102 */     long src_offset = Round.round4(hdr.compressed_offset + hdr.compressed_length);
/* 1103 */     long dst_offset = first_table_offset;
/*      */     
/* 1105 */     if (src_offset > length) {
/* 1106 */       throw new FontCompressionException("Reading woff2 header exception");
/*      */     }
/* 1108 */     if (meta_offset != 0) {
/* 1109 */       if (src_offset != meta_offset) {
/* 1110 */         throw new FontCompressionException("Reading woff2 header exception");
/*      */       }
/* 1112 */       src_offset = Round.round4(meta_offset + meta_length);
/*      */       
/* 1114 */       if (src_offset > 2147483647L) {
/* 1115 */         throw new FontCompressionException("Reading woff2 header exception");
/*      */       }
/*      */     } 
/*      */     
/* 1119 */     if (priv_offset != 0) {
/* 1120 */       if (src_offset != priv_offset) {
/* 1121 */         throw new FontCompressionException("Reading woff2 header exception");
/*      */       }
/* 1123 */       src_offset = Round.round4(priv_offset + priv_length);
/*      */       
/* 1125 */       if (src_offset > 2147483647L) {
/* 1126 */         throw new FontCompressionException("Reading woff2 header exception");
/*      */       }
/*      */     } 
/*      */     
/* 1130 */     if (src_offset != Round.round4(length)) {
/* 1131 */       throw new FontCompressionException("Reading woff2 header exception");
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static void writeHeaders(byte[] data, int length, RebuildMetadata metadata, Woff2Header hdr, Woff2Out out) {
/* 1138 */     long firstTableOffset = computeOffsetToFirstTable(hdr);
/* 1139 */     assert firstTableOffset <= 2147483647L;
/* 1140 */     byte[] output = new byte[(int)firstTableOffset];
/*      */ 
/*      */     
/* 1143 */     List<Woff2Common.Table> sorted_tables = new ArrayList<>(Arrays.asList(hdr.tables));
/*      */     
/* 1145 */     if (hdr.header_version != 0) {
/*      */       
/* 1147 */       for (TtcFont ttc_font : hdr.ttc_fonts) {
/* 1148 */         Map<Integer, Short> sorted_index_by_tag = new TreeMap<>();
/* 1149 */         for (short table_index : ttc_font.table_indices) {
/* 1150 */           sorted_index_by_tag.put(Integer.valueOf((hdr.tables[table_index]).tag), Short.valueOf(table_index));
/*      */         }
/* 1152 */         short index = 0;
/* 1153 */         for (Map.Entry<Integer, Short> i : sorted_index_by_tag.entrySet()) {
/* 1154 */           index = (short)(index + 1); ttc_font.table_indices[index] = ((Short)i.getValue()).shortValue();
/*      */         } 
/*      */       } 
/*      */     } else {
/*      */       
/* 1159 */       Collections.sort(sorted_tables);
/*      */     } 
/*      */ 
/*      */     
/* 1163 */     byte[] result = output;
/* 1164 */     int offset = 0;
/* 1165 */     if (hdr.header_version != 0) {
/*      */       
/* 1167 */       offset = StoreBytes.storeU32(result, offset, hdr.flavor);
/* 1168 */       offset = StoreBytes.storeU32(result, offset, hdr.header_version);
/* 1169 */       offset = StoreBytes.storeU32(result, offset, hdr.ttc_fonts.length);
/*      */       
/* 1171 */       int offset_table = offset; int i;
/* 1172 */       for (i = 0; i < hdr.ttc_fonts.length; i++) {
/* 1173 */         offset = StoreBytes.storeU32(result, offset, 0);
/*      */       }
/*      */       
/* 1176 */       if (hdr.header_version == 131072) {
/* 1177 */         offset = StoreBytes.storeU32(result, offset, 0);
/* 1178 */         offset = StoreBytes.storeU32(result, offset, 0);
/* 1179 */         offset = StoreBytes.storeU32(result, offset, 0);
/*      */       } 
/*      */ 
/*      */       
/* 1183 */       metadata.font_infos = new Woff2FontInfo[hdr.ttc_fonts.length];
/* 1184 */       for (i = 0; i < hdr.ttc_fonts.length; i++) {
/* 1185 */         TtcFont ttc_font = hdr.ttc_fonts[i];
/*      */ 
/*      */         
/* 1188 */         offset_table = StoreBytes.storeU32(result, offset_table, offset);
/*      */ 
/*      */         
/* 1191 */         ttc_font.dst_offset = offset;
/* 1192 */         offset = storeOffsetTable(result, offset, ttc_font.flavor, ttc_font.table_indices.length);
/*      */         
/* 1194 */         metadata.font_infos[i] = new Woff2FontInfo();
/* 1195 */         for (short table_index : ttc_font.table_indices) {
/* 1196 */           int tag = (hdr.tables[table_index]).tag;
/* 1197 */           (metadata.font_infos[i]).table_entry_by_tag.put(Integer.valueOf(tag), Integer.valueOf(offset));
/* 1198 */           offset = storeTableEntry(result, offset, tag);
/*      */         } 
/*      */         
/* 1201 */         ttc_font.header_checksum = Woff2Common.computeULongSum(output, ttc_font.dst_offset, offset - ttc_font.dst_offset);
/*      */       } 
/*      */     } else {
/* 1204 */       metadata.font_infos = new Woff2FontInfo[1];
/* 1205 */       offset = storeOffsetTable(result, offset, hdr.flavor, hdr.num_tables);
/* 1206 */       metadata.font_infos[0] = new Woff2FontInfo();
/* 1207 */       for (int i = 0; i < hdr.num_tables; i++) {
/* 1208 */         (metadata.font_infos[0]).table_entry_by_tag.put(Integer.valueOf(((Woff2Common.Table)sorted_tables.get(i)).tag), Integer.valueOf(offset));
/* 1209 */         offset = storeTableEntry(result, offset, ((Woff2Common.Table)sorted_tables.get(i)).tag);
/*      */       } 
/*      */     } 
/*      */     
/* 1213 */     out.write(output, 0, output.length);
/* 1214 */     metadata.header_checksum = Woff2Common.computeULongSum(output, 0, output.length);
/*      */   }
/*      */ 
/*      */   
/*      */   public static int computeWoff2FinalSize(byte[] data, int length) {
/* 1219 */     Buffer file = new Buffer(data, 0, length);
/* 1220 */     file.skip(16);
/* 1221 */     return file.readInt();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void convertWoff2ToTtf(byte[] data, int length, Woff2Out out) {
/* 1228 */     RebuildMetadata metadata = new RebuildMetadata();
/* 1229 */     Woff2Header hdr = new Woff2Header();
/* 1230 */     readWoff2Header(data, length, hdr);
/*      */     
/* 1232 */     writeHeaders(data, length, metadata, hdr, out);
/*      */     
/* 1234 */     float compression_ratio = hdr.uncompressed_size / length;
/* 1235 */     if (compression_ratio > 100.0F) {
/* 1236 */       throw new FontCompressionException(MessageFormatUtil.format("Implausible compression ratio {0}", new Object[] { Float.valueOf(compression_ratio) }));
/*      */     }
/*      */     
/* 1239 */     byte[] uncompressed_buf = new byte[hdr.uncompressed_size];
/* 1240 */     woff2Uncompress(uncompressed_buf, 0, hdr.uncompressed_size, data, (int)hdr.compressed_offset, hdr.compressed_length);
/*      */     
/* 1242 */     for (int i = 0; i < metadata.font_infos.length; i++)
/* 1243 */       reconstructFont(uncompressed_buf, 0, hdr.uncompressed_size, metadata, hdr, i, out); 
/*      */   }
/*      */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/io/font/woff2/Woff2Dec.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */