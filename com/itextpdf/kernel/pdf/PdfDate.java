/*     */ package com.itextpdf.kernel.pdf;
/*     */ 
/*     */ import java.util.Calendar;
/*     */ import java.util.GregorianCalendar;
/*     */ import java.util.SimpleTimeZone;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class PdfDate
/*     */   extends PdfObjectWrapper<PdfString>
/*     */ {
/*     */   private static final long serialVersionUID = -7424858548790000216L;
/*  68 */   private static final int[] DATE_SPACE = new int[] { 1, 4, 0, 2, 2, -1, 5, 2, 0, 11, 2, 0, 12, 2, 0, 13, 2, 0 };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PdfDate(Calendar d) {
/*  77 */     super(new PdfString(generateStringByCalendar(d)));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PdfDate() {
/*  84 */     this(new GregorianCalendar());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getW3CDate() {
/*  92 */     return getW3CDate(getPdfObject().getValue());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String getW3CDate(String d) {
/* 101 */     if (d.startsWith("D:"))
/* 102 */       d = d.substring(2); 
/* 103 */     StringBuilder sb = new StringBuilder();
/* 104 */     if (d.length() < 4) {
/* 105 */       return "0000";
/*     */     }
/* 107 */     sb.append(d.substring(0, 4));
/* 108 */     d = d.substring(4);
/* 109 */     if (d.length() < 2) {
/* 110 */       return sb.toString();
/*     */     }
/* 112 */     sb.append('-').append(d.substring(0, 2));
/* 113 */     d = d.substring(2);
/* 114 */     if (d.length() < 2) {
/* 115 */       return sb.toString();
/*     */     }
/* 117 */     sb.append('-').append(d.substring(0, 2));
/* 118 */     d = d.substring(2);
/* 119 */     if (d.length() < 2) {
/* 120 */       return sb.toString();
/*     */     }
/* 122 */     sb.append('T').append(d.substring(0, 2));
/* 123 */     d = d.substring(2);
/* 124 */     if (d.length() < 2) {
/* 125 */       sb.append(":00Z");
/* 126 */       return sb.toString();
/*     */     } 
/*     */     
/* 129 */     sb.append(':').append(d.substring(0, 2));
/* 130 */     d = d.substring(2);
/* 131 */     if (d.length() < 2) {
/* 132 */       sb.append('Z');
/* 133 */       return sb.toString();
/*     */     } 
/*     */     
/* 136 */     sb.append(':').append(d.substring(0, 2));
/* 137 */     d = d.substring(2);
/* 138 */     if (d.startsWith("-") || d.startsWith("+")) {
/* 139 */       String sign = d.substring(0, 1);
/* 140 */       d = d.substring(1);
/* 141 */       if (d.length() >= 2) {
/* 142 */         String h = d.substring(0, 2);
/* 143 */         String m = "00";
/* 144 */         if (d.length() > 2) {
/* 145 */           d = d.substring(3);
/* 146 */           if (d.length() >= 2)
/* 147 */             m = d.substring(0, 2); 
/*     */         } 
/* 149 */         sb.append(sign).append(h).append(':').append(m);
/* 150 */         return sb.toString();
/*     */       } 
/*     */     } 
/* 153 */     sb.append('Z');
/* 154 */     return sb.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Calendar decode(String s) {
/*     */     try {
/*     */       GregorianCalendar calendar;
/* 165 */       if (s.startsWith("D:")) {
/* 166 */         s = s.substring(2);
/*     */       }
/* 168 */       int slen = s.length();
/* 169 */       int idx = s.indexOf('Z');
/* 170 */       if (idx >= 0) {
/* 171 */         slen = idx;
/* 172 */         calendar = new GregorianCalendar(new SimpleTimeZone(0, "ZPDF"));
/*     */       } else {
/*     */         
/* 175 */         int sign = 1;
/* 176 */         idx = s.indexOf('+');
/* 177 */         if (idx < 0) {
/* 178 */           idx = s.indexOf('-');
/* 179 */           if (idx >= 0)
/* 180 */             sign = -1; 
/*     */         } 
/* 182 */         if (idx < 0) {
/* 183 */           calendar = new GregorianCalendar();
/*     */         } else {
/* 185 */           int offset = Integer.parseInt(s.substring(idx + 1, idx + 3)) * 60;
/* 186 */           if (idx + 5 < s.length())
/* 187 */             offset += Integer.parseInt(s.substring(idx + 4, idx + 6)); 
/* 188 */           calendar = new GregorianCalendar(new SimpleTimeZone(offset * sign * 60000, "ZPDF"));
/* 189 */           slen = idx;
/*     */         } 
/*     */       } 
/* 192 */       calendar.clear();
/* 193 */       idx = 0;
/* 194 */       for (int k = 0; k < DATE_SPACE.length && 
/* 195 */         idx < slen; k += 3) {
/*     */         
/* 197 */         calendar.set(DATE_SPACE[k], Integer.parseInt(s.substring(idx, idx + DATE_SPACE[k + 1])) + DATE_SPACE[k + 2]);
/* 198 */         idx += DATE_SPACE[k + 1];
/*     */       } 
/* 200 */       return calendar;
/*     */     }
/* 202 */     catch (Exception e) {
/* 203 */       return null;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean isWrappedObjectMustBeIndirect() {
/* 209 */     return false;
/*     */   }
/*     */   
/*     */   private static String generateStringByCalendar(Calendar d) {
/* 213 */     StringBuilder date = new StringBuilder("D:");
/* 214 */     date.append(setLength(d.get(1), 4));
/* 215 */     date.append(setLength(d.get(2) + 1, 2));
/* 216 */     date.append(setLength(d.get(5), 2));
/* 217 */     date.append(setLength(d.get(11), 2));
/* 218 */     date.append(setLength(d.get(12), 2));
/* 219 */     date.append(setLength(d.get(13), 2));
/* 220 */     int timezone = (d.get(15) + d.get(16)) / 3600000;
/* 221 */     if (timezone == 0) {
/* 222 */       date.append('Z');
/*     */     }
/* 224 */     else if (timezone < 0) {
/* 225 */       date.append('-');
/* 226 */       timezone = -timezone;
/*     */     } else {
/*     */       
/* 229 */       date.append('+');
/*     */     } 
/* 231 */     if (timezone != 0) {
/* 232 */       date.append(setLength(timezone, 2)).append('\'');
/* 233 */       int zone = Math.abs((d.get(15) + d.get(16)) / 60000) - timezone * 60;
/* 234 */       date.append(setLength(zone, 2)).append('\'');
/*     */     } 
/* 236 */     return date.toString();
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
/*     */   private static String setLength(int i, int length) {
/* 249 */     StringBuilder tmp = new StringBuilder();
/* 250 */     tmp.append(i);
/* 251 */     while (tmp.length() < length) {
/* 252 */       tmp.insert(0, "0");
/*     */     }
/* 254 */     tmp.setLength(length);
/* 255 */     return tmp.toString();
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/kernel/pdf/PdfDate.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */