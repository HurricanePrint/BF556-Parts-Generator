/*     */ package com.itextpdf.styledxmlparser.jsoup.select;
/*     */ 
/*     */ import com.itextpdf.styledxmlparser.jsoup.helper.StringUtil;
/*     */ import com.itextpdf.styledxmlparser.jsoup.helper.Validate;
/*     */ import com.itextpdf.styledxmlparser.jsoup.parser.TokenQueue;
/*     */ import java.util.ArrayList;
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
/*     */ public class QueryParser
/*     */ {
/*  59 */   private static final String[] combinators = new String[] { ",", ">", "+", "~", " " };
/*  60 */   private static final String[] AttributeEvals = new String[] { "=", "!=", "^=", "$=", "*=", "~=" };
/*     */   
/*     */   private TokenQueue tq;
/*     */   private String query;
/*  64 */   private List<Evaluator> evals = new ArrayList<>();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private QueryParser(String query) {
/*  71 */     this.query = query;
/*  72 */     this.tq = new TokenQueue(query);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Evaluator parse(String query) {
/*  81 */     QueryParser p = new QueryParser(query);
/*  82 */     return p.parse();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   Evaluator parse() {
/*  90 */     this.tq.consumeWhitespace();
/*     */     
/*  92 */     if (this.tq.matchesAny(combinators)) {
/*  93 */       this.evals.add(new StructuralEvaluator.Root());
/*  94 */       combinator(this.tq.consume());
/*     */     } else {
/*  96 */       findElements();
/*     */     } 
/*     */     
/*  99 */     while (!this.tq.isEmpty()) {
/*     */       
/* 101 */       boolean seenWhite = this.tq.consumeWhitespace();
/*     */       
/* 103 */       if (this.tq.matchesAny(combinators)) {
/* 104 */         combinator(this.tq.consume()); continue;
/* 105 */       }  if (seenWhite) {
/* 106 */         combinator(' '); continue;
/*     */       } 
/* 108 */       findElements();
/*     */     } 
/*     */ 
/*     */     
/* 112 */     if (this.evals.size() == 1) {
/* 113 */       return this.evals.get(0);
/*     */     }
/* 115 */     return new CombiningEvaluator.And(this.evals);
/*     */   }
/*     */   private void combinator(char combinator) {
/*     */     Evaluator rootEval, currentEval;
/* 119 */     this.tq.consumeWhitespace();
/* 120 */     String subQuery = consumeSubQuery();
/*     */ 
/*     */ 
/*     */     
/* 124 */     Evaluator newEval = parse(subQuery);
/* 125 */     boolean replaceRightMost = false;
/*     */     
/* 127 */     if (this.evals.size() == 1) {
/* 128 */       rootEval = currentEval = this.evals.get(0);
/*     */       
/* 130 */       if (rootEval instanceof CombiningEvaluator.Or && combinator != ',') {
/* 131 */         currentEval = ((CombiningEvaluator.Or)currentEval).rightMostEvaluator();
/* 132 */         replaceRightMost = true;
/*     */       } 
/*     */     } else {
/*     */       
/* 136 */       rootEval = currentEval = new CombiningEvaluator.And(this.evals);
/*     */     } 
/* 138 */     this.evals.clear();
/*     */ 
/*     */     
/* 141 */     if (combinator == '>') {
/* 142 */       currentEval = new CombiningEvaluator.And(new Evaluator[] { newEval, new StructuralEvaluator.ImmediateParent(currentEval) });
/* 143 */     } else if (combinator == ' ') {
/* 144 */       currentEval = new CombiningEvaluator.And(new Evaluator[] { newEval, new StructuralEvaluator.Parent(currentEval) });
/* 145 */     } else if (combinator == '+') {
/* 146 */       currentEval = new CombiningEvaluator.And(new Evaluator[] { newEval, new StructuralEvaluator.ImmediatePreviousSibling(currentEval) });
/* 147 */     } else if (combinator == '~') {
/* 148 */       currentEval = new CombiningEvaluator.And(new Evaluator[] { newEval, new StructuralEvaluator.PreviousSibling(currentEval) });
/* 149 */     } else if (combinator == ',') {
/*     */       CombiningEvaluator.Or or;
/* 151 */       if (currentEval instanceof CombiningEvaluator.Or) {
/* 152 */         or = (CombiningEvaluator.Or)currentEval;
/* 153 */         or.add(newEval);
/*     */       } else {
/* 155 */         or = new CombiningEvaluator.Or();
/* 156 */         or.add(currentEval);
/* 157 */         or.add(newEval);
/*     */       } 
/* 159 */       currentEval = or;
/*     */     } else {
/*     */       
/* 162 */       throw new Selector.SelectorParseException("Unknown combinator: " + combinator, new Object[0]);
/*     */     } 
/* 164 */     if (replaceRightMost)
/* 165 */     { ((CombiningEvaluator.Or)rootEval).replaceRightMostEvaluator(currentEval); }
/* 166 */     else { rootEval = currentEval; }
/* 167 */      this.evals.add(rootEval);
/*     */   }
/*     */   
/*     */   private String consumeSubQuery() {
/* 171 */     StringBuilder sq = new StringBuilder();
/* 172 */     while (!this.tq.isEmpty()) {
/* 173 */       if (this.tq.matches("(")) {
/* 174 */         sq.append("(").append(this.tq.chompBalanced('(', ')')).append(")"); continue;
/* 175 */       }  if (this.tq.matches("[")) {
/* 176 */         sq.append("[").append(this.tq.chompBalanced('[', ']')).append("]"); continue;
/* 177 */       }  if (this.tq.matchesAny(combinators)) {
/*     */         break;
/*     */       }
/* 180 */       sq.append(this.tq.consume());
/*     */     } 
/* 182 */     return sq.toString();
/*     */   }
/*     */   
/*     */   private void findElements() {
/* 186 */     if (this.tq.matchChomp("#")) {
/* 187 */       byId();
/* 188 */     } else if (this.tq.matchChomp(".")) {
/* 189 */       byClass();
/* 190 */     } else if (this.tq.matchesWord()) {
/* 191 */       byTag();
/* 192 */     } else if (this.tq.matches("[")) {
/* 193 */       byAttribute();
/* 194 */     } else if (this.tq.matchChomp("*")) {
/* 195 */       allElements();
/* 196 */     } else if (this.tq.matchChomp(":lt(")) {
/* 197 */       indexLessThan();
/* 198 */     } else if (this.tq.matchChomp(":gt(")) {
/* 199 */       indexGreaterThan();
/* 200 */     } else if (this.tq.matchChomp(":eq(")) {
/* 201 */       indexEquals();
/* 202 */     } else if (this.tq.matches(":has(")) {
/* 203 */       has();
/* 204 */     } else if (this.tq.matches(":contains(")) {
/* 205 */       contains(false);
/* 206 */     } else if (this.tq.matches(":containsOwn(")) {
/* 207 */       contains(true);
/* 208 */     } else if (this.tq.matches(":matches(")) {
/* 209 */       matches(false);
/* 210 */     } else if (this.tq.matches(":matchesOwn(")) {
/* 211 */       matches(true);
/* 212 */     } else if (this.tq.matches(":not(")) {
/* 213 */       not();
/* 214 */     } else if (this.tq.matchChomp(":nth-child(")) {
/* 215 */       cssNthChild(false, false);
/* 216 */     } else if (this.tq.matchChomp(":nth-last-child(")) {
/* 217 */       cssNthChild(true, false);
/* 218 */     } else if (this.tq.matchChomp(":nth-of-type(")) {
/* 219 */       cssNthChild(false, true);
/* 220 */     } else if (this.tq.matchChomp(":nth-last-of-type(")) {
/* 221 */       cssNthChild(true, true);
/* 222 */     } else if (this.tq.matchChomp(":first-child")) {
/* 223 */       this.evals.add(new Evaluator.IsFirstChild());
/* 224 */     } else if (this.tq.matchChomp(":last-child")) {
/* 225 */       this.evals.add(new Evaluator.IsLastChild());
/* 226 */     } else if (this.tq.matchChomp(":first-of-type")) {
/* 227 */       this.evals.add(new Evaluator.IsFirstOfType());
/* 228 */     } else if (this.tq.matchChomp(":last-of-type")) {
/* 229 */       this.evals.add(new Evaluator.IsLastOfType());
/* 230 */     } else if (this.tq.matchChomp(":only-child")) {
/* 231 */       this.evals.add(new Evaluator.IsOnlyChild());
/* 232 */     } else if (this.tq.matchChomp(":only-of-type")) {
/* 233 */       this.evals.add(new Evaluator.IsOnlyOfType());
/* 234 */     } else if (this.tq.matchChomp(":empty")) {
/* 235 */       this.evals.add(new Evaluator.IsEmpty());
/* 236 */     } else if (this.tq.matchChomp(":root")) {
/* 237 */       this.evals.add(new Evaluator.IsRoot());
/*     */     } else {
/* 239 */       throw new Selector.SelectorParseException("Could not parse query ''{0}'': unexpected token at ''{1}''", new Object[] { this.query, this.tq.remainder() });
/*     */     } 
/*     */   }
/*     */   
/*     */   private void byId() {
/* 244 */     String id = this.tq.consumeCssIdentifier();
/* 245 */     Validate.notEmpty(id);
/* 246 */     this.evals.add(new Evaluator.Id(id));
/*     */   }
/*     */   
/*     */   private void byClass() {
/* 250 */     String className = this.tq.consumeCssIdentifier();
/* 251 */     Validate.notEmpty(className);
/* 252 */     this.evals.add(new Evaluator.Class(className.trim().toLowerCase()));
/*     */   }
/*     */   
/*     */   private void byTag() {
/* 256 */     String tagName = this.tq.consumeElementSelector();
/* 257 */     Validate.notEmpty(tagName);
/*     */ 
/*     */     
/* 260 */     if (tagName.contains("|")) {
/* 261 */       tagName = tagName.replace("|", ":");
/*     */     }
/* 263 */     this.evals.add(new Evaluator.Tag(tagName.trim().toLowerCase()));
/*     */   }
/*     */   
/*     */   private void byAttribute() {
/* 267 */     TokenQueue cq = new TokenQueue(this.tq.chompBalanced('[', ']'));
/* 268 */     String key = cq.consumeToAny(AttributeEvals);
/* 269 */     Validate.notEmpty(key);
/* 270 */     cq.consumeWhitespace();
/*     */     
/* 272 */     if (cq.isEmpty()) {
/* 273 */       if (key.startsWith("^")) {
/* 274 */         this.evals.add(new Evaluator.AttributeStarting(key.substring(1)));
/*     */       } else {
/* 276 */         this.evals.add(new Evaluator.Attribute(key));
/*     */       } 
/* 278 */     } else if (cq.matchChomp("=")) {
/* 279 */       this.evals.add(new Evaluator.AttributeWithValue(key, cq.remainder()));
/*     */     }
/* 281 */     else if (cq.matchChomp("!=")) {
/* 282 */       this.evals.add(new Evaluator.AttributeWithValueNot(key, cq.remainder()));
/*     */     }
/* 284 */     else if (cq.matchChomp("^=")) {
/* 285 */       this.evals.add(new Evaluator.AttributeWithValueStarting(key, cq.remainder()));
/*     */     }
/* 287 */     else if (cq.matchChomp("$=")) {
/* 288 */       this.evals.add(new Evaluator.AttributeWithValueEnding(key, cq.remainder()));
/*     */     }
/* 290 */     else if (cq.matchChomp("*=")) {
/* 291 */       this.evals.add(new Evaluator.AttributeWithValueContaining(key, cq.remainder()));
/*     */     }
/* 293 */     else if (cq.matchChomp("~=")) {
/* 294 */       this.evals.add(new Evaluator.AttributeWithValueMatching(key, Pattern.compile(cq.remainder())));
/*     */     } else {
/* 296 */       throw new Selector.SelectorParseException("Could not parse attribute query ''{0}'': unexpected token at ''{1}''", new Object[] { this.query, cq.remainder() });
/*     */     } 
/*     */   }
/*     */   
/*     */   private void allElements() {
/* 301 */     this.evals.add(new Evaluator.AllElements());
/*     */   }
/*     */ 
/*     */   
/*     */   private void indexLessThan() {
/* 306 */     this.evals.add(new Evaluator.IndexLessThan(consumeIndex()));
/*     */   }
/*     */   
/*     */   private void indexGreaterThan() {
/* 310 */     this.evals.add(new Evaluator.IndexGreaterThan(consumeIndex()));
/*     */   }
/*     */   
/*     */   private void indexEquals() {
/* 314 */     this.evals.add(new Evaluator.IndexEquals(consumeIndex()));
/*     */   }
/*     */ 
/*     */   
/* 318 */   private static final Pattern NTH_AB = Pattern.compile("((\\+|-)?(\\d+)?)n(\\s*(\\+|-)?\\s*\\d+)?", 2);
/* 319 */   private static final Pattern NTH_B = Pattern.compile("(\\+|-)?(\\d+)");
/*     */   private void cssNthChild(boolean backwards, boolean ofType) {
/*     */     int a, b;
/* 322 */     String argS = this.tq.chompTo(")").trim().toLowerCase();
/* 323 */     Matcher mAB = NTH_AB.matcher(argS);
/* 324 */     Matcher mB = NTH_B.matcher(argS);
/*     */     
/* 326 */     if ("odd".equals(argS)) {
/* 327 */       a = 2;
/* 328 */       b = 1;
/* 329 */     } else if ("even".equals(argS)) {
/* 330 */       a = 2;
/* 331 */       b = 0;
/* 332 */     } else if (mAB.matches()) {
/* 333 */       a = (mAB.group(3) != null) ? Integer.parseInt(mAB.group(1).replaceFirst("^\\+", "")) : 1;
/* 334 */       b = (mAB.group(4) != null) ? Integer.parseInt(mAB.group(4).replaceFirst("^\\+", "")) : 0;
/* 335 */     } else if (mB.matches()) {
/* 336 */       a = 0;
/* 337 */       b = Integer.parseInt(mB.group().replaceFirst("^\\+", ""));
/*     */     } else {
/* 339 */       throw new Selector.SelectorParseException("Could not parse nth-index ''{0}'': unexpected format", new Object[] { argS });
/*     */     } 
/* 341 */     if (ofType) {
/* 342 */       if (backwards) {
/* 343 */         this.evals.add(new Evaluator.IsNthLastOfType(a, b));
/*     */       } else {
/* 345 */         this.evals.add(new Evaluator.IsNthOfType(a, b));
/*     */       } 
/* 347 */     } else if (backwards) {
/* 348 */       this.evals.add(new Evaluator.IsNthLastChild(a, b));
/*     */     } else {
/* 350 */       this.evals.add(new Evaluator.IsNthChild(a, b));
/*     */     } 
/*     */   }
/*     */   
/*     */   private int consumeIndex() {
/* 355 */     String indexS = this.tq.chompTo(")").trim();
/* 356 */     Validate.isTrue(StringUtil.isNumeric(indexS), "Index must be numeric");
/* 357 */     return Integer.parseInt(indexS);
/*     */   }
/*     */ 
/*     */   
/*     */   private void has() {
/* 362 */     this.tq.consume(":has");
/* 363 */     String subQuery = this.tq.chompBalanced('(', ')');
/* 364 */     Validate.notEmpty(subQuery, ":has(el) subselect must not be empty");
/* 365 */     this.evals.add(new StructuralEvaluator.Has(parse(subQuery)));
/*     */   }
/*     */ 
/*     */   
/*     */   private void contains(boolean own) {
/* 370 */     this.tq.consume(own ? ":containsOwn" : ":contains");
/* 371 */     String searchText = TokenQueue.unescape(this.tq.chompBalanced('(', ')'));
/* 372 */     Validate.notEmpty(searchText, ":contains(text) query must not be empty");
/* 373 */     if (own) {
/* 374 */       this.evals.add(new Evaluator.ContainsOwnText(searchText));
/*     */     } else {
/* 376 */       this.evals.add(new Evaluator.ContainsText(searchText));
/*     */     } 
/*     */   }
/*     */   
/*     */   private void matches(boolean own) {
/* 381 */     this.tq.consume(own ? ":matchesOwn" : ":matches");
/* 382 */     String regex = this.tq.chompBalanced('(', ')');
/* 383 */     Validate.notEmpty(regex, ":matches(regex) query must not be empty");
/*     */     
/* 385 */     if (own) {
/* 386 */       this.evals.add(new Evaluator.MatchesOwn(Pattern.compile(regex)));
/*     */     } else {
/* 388 */       this.evals.add(new Evaluator.Matches(Pattern.compile(regex)));
/*     */     } 
/*     */   }
/*     */   
/*     */   private void not() {
/* 393 */     this.tq.consume(":not");
/* 394 */     String subQuery = this.tq.chompBalanced('(', ')');
/* 395 */     Validate.notEmpty(subQuery, ":not(selector) subselect must not be empty");
/*     */     
/* 397 */     this.evals.add(new StructuralEvaluator.Not(parse(subQuery)));
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/styledxmlparser/jsoup/select/QueryParser.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */