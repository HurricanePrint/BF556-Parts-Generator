/*     */ package com.itextpdf.styledxmlparser.css.resolve;
/*     */ 
/*     */ import com.itextpdf.io.util.MessageFormatUtil;
/*     */ import com.itextpdf.styledxmlparser.css.parse.CssDeclarationValueTokenizer;
/*     */ import java.util.ArrayList;
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
/*     */ public class CssQuotes
/*     */ {
/*     */   private static final String EMPTY_QUOTE = "";
/*     */   private ArrayList<String> openQuotes;
/*     */   private ArrayList<String> closeQuotes;
/*     */   
/*     */   private CssQuotes(ArrayList<String> openQuotes, ArrayList<String> closeQuotes) {
/*  80 */     this.openQuotes = new ArrayList<>(openQuotes);
/*  81 */     this.closeQuotes = new ArrayList<>(closeQuotes);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static CssQuotes createQuotes(String quotesString, boolean fallbackToDefault) {
/*  92 */     boolean error = false;
/*  93 */     ArrayList<ArrayList<String>> quotes = new ArrayList<>(2);
/*  94 */     quotes.add(new ArrayList<>());
/*  95 */     quotes.add(new ArrayList<>());
/*  96 */     if (quotesString != null) {
/*  97 */       if (quotesString.equals("none")) {
/*  98 */         ((ArrayList<String>)quotes.get(0)).add("");
/*  99 */         ((ArrayList<String>)quotes.get(1)).add("");
/* 100 */         return new CssQuotes(quotes.get(0), quotes.get(1));
/*     */       } 
/* 102 */       CssDeclarationValueTokenizer tokenizer = new CssDeclarationValueTokenizer(quotesString);
/*     */       CssDeclarationValueTokenizer.Token token;
/* 104 */       for (int i = 0; (token = tokenizer.getNextValidToken()) != null; i++) {
/* 105 */         if (token.isString()) {
/* 106 */           ((ArrayList<String>)quotes.get(i % 2)).add(token.getValue());
/*     */         } else {
/* 108 */           error = true;
/*     */           break;
/*     */         } 
/*     */       } 
/* 112 */       if (((ArrayList)quotes.get(0)).size() == ((ArrayList)quotes.get(1)).size() && !((ArrayList)quotes.get(0)).isEmpty() && !error) {
/* 113 */         return new CssQuotes(quotes.get(0), quotes.get(1));
/*     */       }
/* 115 */       LoggerFactory.getLogger(CssQuotes.class).error(MessageFormatUtil.format("Quote property \"{0}\" is invalid. It should contain even number of <string> values.", new Object[] { quotesString }));
/*     */     } 
/*     */     
/* 118 */     return fallbackToDefault ? createDefaultQuotes() : null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static CssQuotes createDefaultQuotes() {
/* 127 */     ArrayList<String> openQuotes = new ArrayList<>();
/* 128 */     ArrayList<String> closeQuotes = new ArrayList<>();
/* 129 */     openQuotes.add("«");
/* 130 */     closeQuotes.add("»");
/* 131 */     return new CssQuotes(openQuotes, closeQuotes);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String resolveQuote(String value, AbstractCssContext context) {
/* 142 */     int depth = context.getQuotesDepth();
/* 143 */     if ("open-quote".equals(value)) {
/* 144 */       increaseDepth(context);
/* 145 */       return getQuote(depth, this.openQuotes);
/* 146 */     }  if ("close-quote".equals(value)) {
/* 147 */       decreaseDepth(context);
/* 148 */       return getQuote(depth - 1, this.closeQuotes);
/* 149 */     }  if ("no-open-quote".equals(value)) {
/* 150 */       increaseDepth(context);
/* 151 */       return "";
/* 152 */     }  if ("no-close-quote".equals(value)) {
/* 153 */       decreaseDepth(context);
/* 154 */       return "";
/*     */     } 
/* 156 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void increaseDepth(AbstractCssContext context) {
/* 165 */     context.setQuotesDepth(context.getQuotesDepth() + 1);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void decreaseDepth(AbstractCssContext context) {
/* 174 */     if (context.getQuotesDepth() > 0) {
/* 175 */       context.setQuotesDepth(context.getQuotesDepth() - 1);
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
/*     */   private String getQuote(int depth, ArrayList<String> quotes) {
/* 187 */     if (depth >= quotes.size()) {
/* 188 */       return quotes.get(quotes.size() - 1);
/*     */     }
/* 190 */     if (depth < 0) {
/* 191 */       return "";
/*     */     }
/* 193 */     return quotes.get(depth);
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/styledxmlparser/css/resolve/CssQuotes.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */