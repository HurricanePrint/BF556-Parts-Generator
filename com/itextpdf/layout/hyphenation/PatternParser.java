/*     */ package com.itextpdf.layout.hyphenation;
/*     */ 
/*     */ import com.itextpdf.io.util.ResourceUtil;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.FileNotFoundException;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.StringReader;
/*     */ import java.util.ArrayList;
/*     */ import javax.xml.parsers.SAXParser;
/*     */ import javax.xml.parsers.SAXParserFactory;
/*     */ import org.xml.sax.Attributes;
/*     */ import org.xml.sax.EntityResolver;
/*     */ import org.xml.sax.InputSource;
/*     */ import org.xml.sax.SAXException;
/*     */ import org.xml.sax.SAXParseException;
/*     */ import org.xml.sax.XMLReader;
/*     */ import org.xml.sax.helpers.DefaultHandler;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class PatternParser
/*     */   extends DefaultHandler
/*     */ {
/*     */   private XMLReader parser;
/*     */   private int currElement;
/*     */   private IPatternConsumer consumer;
/*     */   private StringBuilder token;
/*     */   private ArrayList exception;
/*     */   private char hyphenChar;
/*     */   private String errMsg;
/*     */   private boolean hasClasses;
/*     */   static final int ELEM_CLASSES = 1;
/*     */   static final int ELEM_EXCEPTIONS = 2;
/*     */   static final int ELEM_PATTERNS = 3;
/*     */   static final int ELEM_HYPHEN = 4;
/*     */   
/*     */   private PatternParser() {
/*  65 */     this.token = new StringBuilder();
/*  66 */     this.parser = createParser();
/*  67 */     this.parser.setContentHandler(this);
/*  68 */     this.parser.setErrorHandler(this);
/*     */ 
/*     */     
/*  71 */     this.hyphenChar = '-';
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PatternParser(IPatternConsumer consumer) throws HyphenationException {
/*  80 */     this();
/*  81 */     this.consumer = consumer;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void parse(String filename) throws HyphenationException, FileNotFoundException {
/*  91 */     parse(new FileInputStream(filename), filename);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void parse(InputStream stream, String name) throws HyphenationException {
/* 102 */     InputSource source = new InputSource(stream);
/* 103 */     source.setSystemId(name);
/*     */     try {
/* 105 */       this.parser.parse(source);
/* 106 */     } catch (FileNotFoundException fnfe) {
/* 107 */       throw new HyphenationException("File not found: " + fnfe.getMessage());
/* 108 */     } catch (IOException ioe) {
/* 109 */       throw new HyphenationException(ioe.getMessage());
/* 110 */     } catch (SAXException e) {
/* 111 */       throw new HyphenationException(this.errMsg);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static XMLReader createParser() {
/*     */     try {
/* 121 */       SAXParserFactory factory = SAXParserFactory.newInstance();
/* 122 */       factory.setNamespaceAware(true);
/* 123 */       factory.setValidating(false);
/* 124 */       factory.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);
/* 125 */       SAXParser saxParser = factory.newSAXParser();
/* 126 */       XMLReader xmlReader = saxParser.getXMLReader();
/* 127 */       xmlReader.setEntityResolver(new SafeEmptyEntityResolver());
/* 128 */       return xmlReader;
/* 129 */     } catch (Exception e) {
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 134 */       throw new RuntimeException("Couldn't create XMLReader: " + e.getMessage());
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private String readToken(StringBuilder chars) {
/* 140 */     boolean space = false;
/*     */     int i;
/* 142 */     for (i = 0; i < chars.length() && 
/* 143 */       Character.isWhitespace(chars.charAt(i)); i++) {
/* 144 */       space = true;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 149 */     if (space) {
/*     */       
/* 151 */       for (int j = i; j < chars.length(); j++) {
/* 152 */         chars.setCharAt(j - i, chars.charAt(j));
/*     */       }
/* 154 */       chars.setLength(chars.length() - i);
/* 155 */       if (this.token.length() > 0) {
/* 156 */         String word = this.token.toString();
/* 157 */         this.token.setLength(0);
/* 158 */         return word;
/*     */       } 
/*     */     } 
/* 161 */     space = false;
/* 162 */     for (i = 0; i < chars.length(); i++) {
/* 163 */       if (Character.isWhitespace(chars.charAt(i))) {
/* 164 */         space = true;
/*     */         break;
/*     */       } 
/*     */     } 
/* 168 */     this.token.append(chars.toString().substring(0, i));
/*     */     
/* 170 */     for (int countr = i; countr < chars.length(); countr++) {
/* 171 */       chars.setCharAt(countr - i, chars.charAt(countr));
/*     */     }
/* 173 */     chars.setLength(chars.length() - i);
/* 174 */     if (space) {
/* 175 */       String word = this.token.toString();
/* 176 */       this.token.setLength(0);
/* 177 */       return word;
/*     */     } 
/* 179 */     this.token.append(chars);
/* 180 */     return null;
/*     */   }
/*     */   
/*     */   private static String getPattern(String word) {
/* 184 */     StringBuilder pat = new StringBuilder();
/* 185 */     int len = word.length();
/* 186 */     for (int i = 0; i < len; i++) {
/* 187 */       if (!Character.isDigit(word.charAt(i))) {
/* 188 */         pat.append(word.charAt(i));
/*     */       }
/*     */     } 
/* 191 */     return pat.toString();
/*     */   }
/*     */   
/*     */   private ArrayList normalizeException(ArrayList ex) {
/* 195 */     ArrayList<String> res = new ArrayList();
/* 196 */     for (int i = 0; i < ex.size(); i++) {
/* 197 */       Object item = ex.get(i);
/* 198 */       if (item instanceof String) {
/* 199 */         String str = (String)item;
/* 200 */         StringBuilder buf = new StringBuilder();
/* 201 */         for (int j = 0; j < str.length(); j++) {
/* 202 */           char c = str.charAt(j);
/* 203 */           if (c != this.hyphenChar) {
/* 204 */             buf.append(c);
/*     */           } else {
/* 206 */             res.add(buf.toString());
/* 207 */             buf.setLength(0);
/* 208 */             char[] h = new char[1];
/* 209 */             h[0] = this.hyphenChar;
/*     */ 
/*     */             
/* 212 */             res.add(new Hyphen(new String(h), null, null));
/*     */           } 
/*     */         } 
/* 215 */         if (buf.length() > 0) {
/* 216 */           res.add(buf.toString());
/*     */         }
/*     */       } else {
/* 219 */         res.add(item);
/*     */       } 
/*     */     } 
/* 222 */     return res;
/*     */   }
/*     */   
/*     */   private String getExceptionWord(ArrayList ex) {
/* 226 */     StringBuilder res = new StringBuilder();
/* 227 */     for (int i = 0; i < ex.size(); i++) {
/* 228 */       Object item = ex.get(i);
/* 229 */       if (item instanceof String) {
/* 230 */         res.append((String)item);
/*     */       }
/* 232 */       else if (((Hyphen)item).noBreak != null) {
/* 233 */         res.append(((Hyphen)item).noBreak);
/*     */       } 
/*     */     } 
/*     */     
/* 237 */     return res.toString();
/*     */   }
/*     */   
/*     */   private static String getInterletterValues(String pat) {
/* 241 */     StringBuilder il = new StringBuilder();
/*     */ 
/*     */     
/* 244 */     String word = pat + "a";
/* 245 */     int len = word.length();
/* 246 */     for (int i = 0; i < len; i++) {
/* 247 */       char c = word.charAt(i);
/* 248 */       if (Character.isDigit(c)) {
/* 249 */         il.append(c);
/* 250 */         i++;
/*     */       } else {
/* 252 */         il.append('0');
/*     */       } 
/*     */     } 
/* 255 */     return il.toString();
/*     */   }
/*     */ 
/*     */   
/*     */   protected void getExternalClasses() throws SAXException {
/* 260 */     XMLReader mainParser = this.parser;
/* 261 */     this.parser = createParser();
/* 262 */     this.parser.setContentHandler(this);
/* 263 */     this.parser.setErrorHandler(this);
/* 264 */     InputStream stream = ResourceUtil.getResourceStream("com/itextpdf/hyph/external/classes.xml");
/* 265 */     InputSource source = new InputSource(stream);
/*     */     try {
/* 267 */       this.parser.parse(source);
/* 268 */     } catch (IOException ioe) {
/* 269 */       throw new SAXException(ioe.getMessage());
/*     */     } finally {
/* 271 */       this.parser = mainParser;
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
/*     */   
/*     */   public void startElement(String uri, String local, String raw, Attributes attrs) throws SAXException {
/* 285 */     if (local.equals("hyphen-char")) {
/* 286 */       String h = attrs.getValue("value");
/* 287 */       if (h != null && h.length() == 1) {
/* 288 */         this.hyphenChar = h.charAt(0);
/*     */       }
/* 290 */     } else if (local.equals("classes")) {
/* 291 */       this.currElement = 1;
/* 292 */     } else if (local.equals("patterns")) {
/* 293 */       if (!this.hasClasses) {
/* 294 */         getExternalClasses();
/*     */       }
/* 296 */       this.currElement = 3;
/* 297 */     } else if (local.equals("exceptions")) {
/* 298 */       if (!this.hasClasses) {
/* 299 */         getExternalClasses();
/*     */       }
/* 301 */       this.currElement = 2;
/* 302 */       this.exception = new ArrayList();
/* 303 */     } else if (local.equals("hyphen")) {
/* 304 */       if (this.token.length() > 0) {
/* 305 */         this.exception.add(this.token.toString());
/*     */       }
/* 307 */       this.exception.add(new Hyphen(attrs.getValue("pre"), attrs
/* 308 */             .getValue("no"), attrs
/* 309 */             .getValue("post")));
/* 310 */       this.currElement = 4;
/*     */     } 
/* 312 */     this.token.setLength(0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void endElement(String uri, String local, String raw) {
/* 320 */     if (this.token.length() > 0) {
/* 321 */       String word = this.token.toString();
/* 322 */       switch (this.currElement) {
/*     */         case 1:
/* 324 */           this.consumer.addClass(word);
/*     */           break;
/*     */         case 2:
/* 327 */           this.exception.add(word);
/* 328 */           this.exception = normalizeException(this.exception);
/* 329 */           this.consumer.addException(getExceptionWord(this.exception), (ArrayList)this.exception
/* 330 */               .clone());
/*     */           break;
/*     */         case 3:
/* 333 */           this.consumer.addPattern(getPattern(word), 
/* 334 */               getInterletterValues(word));
/*     */           break;
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 342 */       if (this.currElement != 4) {
/* 343 */         this.token.setLength(0);
/*     */       }
/*     */     } 
/* 346 */     if (this.currElement == 1) {
/* 347 */       this.hasClasses = true;
/*     */     }
/* 349 */     if (this.currElement == 4) {
/* 350 */       this.currElement = 2;
/*     */     } else {
/* 352 */       this.currElement = 0;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void characters(char[] ch, int start, int length) {
/* 361 */     StringBuilder chars = new StringBuilder(length);
/* 362 */     chars.append(ch, start, length);
/* 363 */     String word = readToken(chars);
/* 364 */     while (word != null) {
/*     */       
/* 366 */       switch (this.currElement) {
/*     */         case 1:
/* 368 */           this.consumer.addClass(word);
/*     */           break;
/*     */         case 2:
/* 371 */           this.exception.add(word);
/* 372 */           this.exception = normalizeException(this.exception);
/* 373 */           this.consumer.addException(getExceptionWord(this.exception), (ArrayList)this.exception
/* 374 */               .clone());
/* 375 */           this.exception.clear();
/*     */           break;
/*     */         case 3:
/* 378 */           this.consumer.addPattern(getPattern(word), 
/* 379 */               getInterletterValues(word));
/*     */           break;
/*     */       } 
/*     */ 
/*     */       
/* 384 */       word = readToken(chars);
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
/*     */   public void warning(SAXParseException ex) {
/* 397 */     this
/* 398 */       .errMsg = "[Warning] " + getLocationString(ex) + ": " + ex.getMessage();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void error(SAXParseException ex) {
/* 405 */     this.errMsg = "[Error] " + getLocationString(ex) + ": " + ex.getMessage();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void fatalError(SAXParseException ex) throws SAXException {
/* 412 */     this
/* 413 */       .errMsg = "[Fatal Error] " + getLocationString(ex) + ": " + ex.getMessage();
/* 414 */     throw ex;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private String getLocationString(SAXParseException ex) {
/* 421 */     StringBuilder str = new StringBuilder();
/*     */     
/* 423 */     String systemId = ex.getSystemId();
/* 424 */     if (systemId != null) {
/* 425 */       int index = systemId.lastIndexOf('/');
/* 426 */       if (index != -1) {
/* 427 */         systemId = systemId.substring(index + 1);
/*     */       }
/* 429 */       str.append(systemId);
/*     */     } 
/* 431 */     str.append(':');
/* 432 */     str.append(ex.getLineNumber());
/* 433 */     str.append(':');
/* 434 */     str.append(ex.getColumnNumber());
/*     */ 
/*     */     
/* 437 */     return str.toString();
/*     */   }
/*     */   
/*     */   private static class SafeEmptyEntityResolver
/*     */     implements EntityResolver {
/*     */     public InputSource resolveEntity(String publicId, String systemId) throws SAXException, IOException {
/* 443 */       return new InputSource(new StringReader(""));
/*     */     }
/*     */     
/*     */     private SafeEmptyEntityResolver() {}
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/layout/hyphenation/PatternParser.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */