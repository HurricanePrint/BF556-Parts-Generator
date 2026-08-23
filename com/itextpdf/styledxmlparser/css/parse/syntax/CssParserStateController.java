/*     */ package com.itextpdf.styledxmlparser.css.parse.syntax;
/*     */ 
/*     */ import com.itextpdf.io.util.MessageFormatUtil;
/*     */ import com.itextpdf.styledxmlparser.css.CssDeclaration;
/*     */ import com.itextpdf.styledxmlparser.css.CssNestedAtRule;
/*     */ import com.itextpdf.styledxmlparser.css.CssNestedAtRuleFactory;
/*     */ import com.itextpdf.styledxmlparser.css.CssRuleSet;
/*     */ import com.itextpdf.styledxmlparser.css.CssSemicolonAtRule;
/*     */ import com.itextpdf.styledxmlparser.css.CssStatement;
/*     */ import com.itextpdf.styledxmlparser.css.CssStyleSheet;
/*     */ import com.itextpdf.styledxmlparser.css.parse.CssDeclarationValueTokenizer;
/*     */ import com.itextpdf.styledxmlparser.css.parse.CssRuleSetParser;
/*     */ import com.itextpdf.styledxmlparser.css.util.CssUtils;
/*     */ import com.itextpdf.styledxmlparser.resolver.resource.UriResolver;
/*     */ import java.net.MalformedURLException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.Collections;
/*     */ import java.util.HashSet;
/*     */ import java.util.List;
/*     */ import java.util.Set;
/*     */ import java.util.Stack;
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
/*     */ public final class CssParserStateController
/*     */ {
/*     */   private IParserState currentState;
/*     */   private boolean isCurrentRuleSupported = true;
/*     */   private IParserState previousActiveState;
/*  84 */   private StringBuilder buffer = new StringBuilder();
/*     */ 
/*     */   
/*     */   private String currentSelector;
/*     */ 
/*     */   
/*     */   private CssStyleSheet styleSheet;
/*     */ 
/*     */   
/*     */   private Stack<CssNestedAtRule> nestedAtRules;
/*     */ 
/*     */   
/*     */   private Stack<List<CssDeclaration>> storedPropertiesWithoutSelector;
/*     */ 
/*     */   
/*  99 */   private static final Set<String> SUPPORTED_RULES = Collections.unmodifiableSet(new HashSet<>(Arrays.asList(new String[] { "media", "page", "top-left-corner", "top-left", "top-center", "top-right", "top-right-corner", "bottom-left-corner", "bottom-left", "bottom-center", "bottom-right", "bottom-right-corner", "left-top", "left-middle", "left-bottom", "right-top", "right-middle", "right-bottom", "font-face" })));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 109 */   private static final Set<String> CONDITIONAL_GROUP_RULES = Collections.unmodifiableSet(new HashSet<>(Arrays.asList(new String[] { "media" })));
/*     */ 
/*     */ 
/*     */   
/*     */   private final IParserState commentStartState;
/*     */ 
/*     */ 
/*     */   
/*     */   private final IParserState commendEndState;
/*     */ 
/*     */ 
/*     */   
/*     */   private final IParserState commendInnerState;
/*     */ 
/*     */   
/*     */   private final IParserState unknownState;
/*     */ 
/*     */   
/*     */   private final IParserState ruleState;
/*     */ 
/*     */   
/*     */   private final IParserState propertiesState;
/*     */ 
/*     */   
/*     */   private final IParserState conditionalGroupAtRuleBlockState;
/*     */ 
/*     */   
/*     */   private final IParserState atRuleBlockState;
/*     */ 
/*     */   
/*     */   private UriResolver uriResolver;
/*     */ 
/*     */ 
/*     */   
/*     */   public CssParserStateController() {
/* 144 */     this("");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public CssParserStateController(String baseUrl) {
/* 153 */     if (baseUrl != null && baseUrl.length() > 0) {
/* 154 */       this.uriResolver = new UriResolver(baseUrl);
/*     */     }
/* 156 */     this.styleSheet = new CssStyleSheet();
/* 157 */     this.nestedAtRules = new Stack<>();
/* 158 */     this.storedPropertiesWithoutSelector = new Stack<>();
/*     */     
/* 160 */     this.commentStartState = new CommentStartState(this);
/* 161 */     this.commendEndState = new CommentEndState(this);
/* 162 */     this.commendInnerState = new CommentInnerState(this);
/* 163 */     this.unknownState = new UnknownState(this);
/* 164 */     this.ruleState = new RuleState(this);
/* 165 */     this.propertiesState = new PropertiesState(this);
/* 166 */     this.atRuleBlockState = new AtRuleBlockState(this);
/* 167 */     this.conditionalGroupAtRuleBlockState = new ConditionalGroupAtRuleBlockState(this);
/*     */     
/* 169 */     this.currentState = this.unknownState;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void process(char ch) {
/* 178 */     this.currentState.process(ch);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public CssStyleSheet getParsingResult() {
/* 187 */     return this.styleSheet;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void appendToBuffer(char ch) {
/* 196 */     this.buffer.append(ch);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   String getBufferContents() {
/* 205 */     return this.buffer.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void resetBuffer() {
/* 212 */     this.buffer.setLength(0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void enterPreviousActiveState() {
/* 219 */     setState(this.previousActiveState);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void enterCommentStartState() {
/* 226 */     saveActiveState();
/* 227 */     setState(this.commentStartState);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void enterCommentEndState() {
/* 234 */     setState(this.commendEndState);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void enterCommentInnerState() {
/* 241 */     setState(this.commendInnerState);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void enterRuleState() {
/* 248 */     setState(this.ruleState);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void enterUnknownStateIfNestedBlocksFinished() {
/* 255 */     if (this.nestedAtRules.size() == 0) {
/* 256 */       setState(this.unknownState);
/*     */     } else {
/* 258 */       enterRuleStateBasedOnItsType();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void enterRuleStateBasedOnItsType() {
/* 266 */     if (currentAtRuleIsConditionalGroupRule()) {
/* 267 */       enterConditionalGroupAtRuleBlockState();
/*     */     } else {
/* 269 */       enterAtRuleBlockState();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void enterUnknownState() {
/* 277 */     setState(this.unknownState);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void enterAtRuleBlockState() {
/* 284 */     setState(this.atRuleBlockState);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void enterConditionalGroupAtRuleBlockState() {
/* 291 */     setState(this.conditionalGroupAtRuleBlockState);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void enterPropertiesState() {
/* 298 */     setState(this.propertiesState);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void storeCurrentSelector() {
/* 305 */     this.currentSelector = this.buffer.toString();
/* 306 */     this.buffer.setLength(0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void storeCurrentProperties() {
/* 313 */     if (this.isCurrentRuleSupported) {
/* 314 */       processProperties(this.currentSelector, this.buffer.toString());
/*     */     }
/* 316 */     this.currentSelector = null;
/* 317 */     this.buffer.setLength(0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void storeCurrentPropertiesWithoutSelector() {
/* 324 */     if (this.isCurrentRuleSupported) {
/* 325 */       processProperties(this.buffer.toString());
/*     */     }
/* 327 */     this.buffer.setLength(0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void storeSemicolonAtRule() {
/* 334 */     if (this.isCurrentRuleSupported) {
/* 335 */       processSemicolonAtRule(this.buffer.toString());
/*     */     }
/* 337 */     this.buffer.setLength(0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void finishAtRuleBlock() {
/* 344 */     List<CssDeclaration> storedProps = this.storedPropertiesWithoutSelector.pop();
/* 345 */     CssNestedAtRule atRule = this.nestedAtRules.pop();
/* 346 */     if (this.isCurrentRuleSupported) {
/* 347 */       processFinishedAtRuleBlock(atRule);
/* 348 */       if (!storedProps.isEmpty()) {
/* 349 */         atRule.addBodyCssDeclarations(storedProps);
/*     */       }
/*     */     } 
/* 352 */     this.isCurrentRuleSupported = isCurrentRuleSupported();
/* 353 */     this.buffer.setLength(0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void pushBlockPrecedingAtRule() {
/* 360 */     this.nestedAtRules.push(CssNestedAtRuleFactory.createNestedRule(this.buffer.toString()));
/* 361 */     this.storedPropertiesWithoutSelector.push(new ArrayList<>());
/* 362 */     this.isCurrentRuleSupported = isCurrentRuleSupported();
/* 363 */     this.buffer.setLength(0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void saveActiveState() {
/* 370 */     this.previousActiveState = this.currentState;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void setState(IParserState state) {
/* 379 */     this.currentState = state;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void processProperties(String selector, String properties) {
/* 389 */     List<CssRuleSet> ruleSets = CssRuleSetParser.parseRuleSet(selector, properties);
/* 390 */     for (CssRuleSet ruleSet : ruleSets) {
/* 391 */       normalizeDeclarationURIs(ruleSet.getNormalDeclarations());
/* 392 */       normalizeDeclarationURIs(ruleSet.getImportantDeclarations());
/*     */     } 
/* 394 */     for (CssRuleSet ruleSet : ruleSets) {
/* 395 */       if (this.nestedAtRules.size() == 0) {
/* 396 */         this.styleSheet.addStatement((CssStatement)ruleSet); continue;
/*     */       } 
/* 398 */       ((CssNestedAtRule)this.nestedAtRules.peek()).addStatementToBody((CssStatement)ruleSet);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void processProperties(String properties) {
/* 409 */     if (this.storedPropertiesWithoutSelector.size() > 0) {
/* 410 */       List<CssDeclaration> cssDeclarations = CssRuleSetParser.parsePropertyDeclarations(properties);
/* 411 */       normalizeDeclarationURIs(cssDeclarations);
/* 412 */       ((List<CssDeclaration>)this.storedPropertiesWithoutSelector.peek()).addAll(cssDeclarations);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void normalizeDeclarationURIs(List<CssDeclaration> declarations) {
/* 423 */     if (this.uriResolver == null) {
/*     */       return;
/*     */     }
/* 426 */     for (CssDeclaration declaration : declarations) {
/* 427 */       if (declaration.getExpression().contains("url(")) {
/* 428 */         CssDeclarationValueTokenizer tokenizer = new CssDeclarationValueTokenizer(declaration.getExpression());
/*     */         
/* 430 */         StringBuilder normalizedDeclaration = new StringBuilder(); CssDeclarationValueTokenizer.Token token;
/* 431 */         while ((token = tokenizer.getNextValidToken()) != null) {
/*     */           String strToAppend;
/* 433 */           if (token.getType() == CssDeclarationValueTokenizer.TokenType.FUNCTION && token.getValue().startsWith("url(")) {
/* 434 */             String url = token.getValue().trim();
/* 435 */             url = url.substring(4, url.length() - 1).trim();
/* 436 */             if (CssUtils.isBase64Data(url)) {
/* 437 */               strToAppend = token.getValue().trim();
/*     */             } else {
/* 439 */               if ((url.startsWith("'") && url.endsWith("'")) || (url.startsWith("\"") && url.endsWith("\""))) {
/* 440 */                 url = url.substring(1, url.length() - 1);
/*     */               }
/* 442 */               url = url.trim();
/* 443 */               String finalUrl = url;
/*     */               try {
/* 445 */                 finalUrl = this.uriResolver.resolveAgainstBaseUri(url).toExternalForm();
/* 446 */               } catch (MalformedURLException malformedURLException) {}
/*     */               
/* 448 */               strToAppend = MessageFormatUtil.format("url({0})", new Object[] { finalUrl });
/*     */             } 
/*     */           } else {
/* 451 */             strToAppend = token.getValue();
/*     */           } 
/* 453 */           if (normalizedDeclaration.length() > 0) {
/* 454 */             normalizedDeclaration.append(' ');
/*     */           }
/* 456 */           normalizedDeclaration.append(strToAppend);
/*     */         } 
/* 458 */         declaration.setExpression(normalizedDeclaration.toString());
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void processSemicolonAtRule(String ruleStr) {
/* 469 */     CssSemicolonAtRule atRule = new CssSemicolonAtRule(ruleStr);
/* 470 */     this.styleSheet.addStatement((CssStatement)atRule);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void processFinishedAtRuleBlock(CssNestedAtRule atRule) {
/* 479 */     if (this.nestedAtRules.size() != 0) {
/* 480 */       ((CssNestedAtRule)this.nestedAtRules.peek()).addStatementToBody((CssStatement)atRule);
/*     */     } else {
/* 482 */       this.styleSheet.addStatement((CssStatement)atRule);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean isCurrentRuleSupported() {
/* 492 */     boolean isSupported = (this.nestedAtRules.isEmpty() || SUPPORTED_RULES.contains(((CssNestedAtRule)this.nestedAtRules.peek()).getRuleName()));
/* 493 */     if (!isSupported) {
/* 494 */       LoggerFactory.getLogger(getClass()).error(MessageFormatUtil.format("The rule @{0} is unsupported. All selectors in this rule will be ignored.", new Object[] { ((CssNestedAtRule)this.nestedAtRules.peek()).getRuleName() }));
/*     */     }
/* 496 */     return isSupported;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean currentAtRuleIsConditionalGroupRule() {
/* 505 */     return (!this.isCurrentRuleSupported || (this.nestedAtRules.size() > 0 && CONDITIONAL_GROUP_RULES.contains(((CssNestedAtRule)this.nestedAtRules.peek()).getRuleName())));
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/styledxmlparser/css/parse/syntax/CssParserStateController.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */