/*     */ package com.itextpdf.forms.xfdf;
/*     */ 
/*     */ import com.itextpdf.kernel.geom.Rectangle;
/*     */ import com.itextpdf.kernel.pdf.PdfBoolean;
/*     */ import com.itextpdf.kernel.pdf.PdfIndirectReference;
/*     */ import com.itextpdf.kernel.pdf.PdfName;
/*     */ import com.itextpdf.kernel.pdf.PdfNumber;
/*     */ import com.itextpdf.kernel.pdf.PdfObject;
/*     */ import com.itextpdf.kernel.pdf.PdfString;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class AnnotObject
/*     */ {
/*     */   private String name;
/* 162 */   private List<AttributeObject> attributes = new ArrayList<>();
/*     */   
/*     */   private PdfString contents;
/*     */   
/*     */   private PdfString contentsRichText;
/*     */   
/*     */   private boolean hasPopup;
/*     */   
/*     */   private AnnotObject popup;
/*     */   private ActionObject action;
/*     */   private DestObject destination;
/*     */   
/*     */   public String getName() {
/* 175 */     return this.name;
/*     */   }
/*     */ 
/*     */   
/*     */   private String appearance;
/*     */   
/*     */   private String defaultAppearance;
/*     */   
/*     */   private String defaultStyle;
/*     */   private BorderStyleAltObject borderStyleAlt;
/*     */   private String vertices;
/*     */   private PdfIndirectReference ref;
/*     */   
/*     */   public AnnotObject setName(String name) {
/* 189 */     this.name = name;
/* 190 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List<AttributeObject> getAttributes() {
/* 199 */     return this.attributes;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public AttributeObject getAttribute(String name) {
/* 208 */     for (AttributeObject attr : this.attributes) {
/* 209 */       if (attr.getName().equals(name)) {
/* 210 */         return attr;
/*     */       }
/*     */     } 
/* 213 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getAttributeValue(String name) {
/* 222 */     for (AttributeObject attr : this.attributes) {
/* 223 */       if (attr.getName().equals(name)) {
/* 224 */         return attr.getValue();
/*     */       }
/*     */     } 
/* 227 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public AnnotObject getPopup() {
/* 236 */     return this.popup;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public AnnotObject setPopup(AnnotObject popup) {
/* 246 */     this.popup = popup;
/* 247 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isHasPopup() {
/* 256 */     return this.hasPopup;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public AnnotObject setHasPopup(boolean hasPopup) {
/* 266 */     this.hasPopup = hasPopup;
/* 267 */     return this;
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
/*     */   
/*     */   public PdfString getContents() {
/* 281 */     return this.contents;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public AnnotObject setContents(PdfString contents) {
/* 291 */     this.contents = contents;
/* 292 */     return this;
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
/*     */   
/*     */   public PdfString getContentsRichText() {
/* 306 */     return this.contentsRichText;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public AnnotObject setContentsRichText(PdfString contentsRichRext) {
/* 316 */     this.contentsRichText = contentsRichRext;
/* 317 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ActionObject getAction() {
/* 327 */     return this.action;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public AnnotObject setAction(ActionObject action) {
/* 338 */     this.action = action;
/* 339 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addAttribute(AttributeObject attr) {
/* 347 */     this.attributes.add(attr);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void addAttribute(String name, boolean value) {
/* 354 */     String valueString = value ? "yes" : "no";
/* 355 */     this.attributes.add(new AttributeObject(name, valueString));
/*     */   }
/*     */   
/*     */   void addAttribute(String name, float value) {
/* 359 */     this.attributes.add(new AttributeObject(name, String.valueOf(value)));
/*     */   }
/*     */   
/*     */   void addAttribute(String name, Rectangle value) {
/* 363 */     String stringValue = XfdfObjectUtils.convertRectToString(value);
/* 364 */     this.attributes.add(new AttributeObject(name, stringValue));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void addAttribute(String name, PdfObject valueObject, boolean required) {
/* 374 */     if (valueObject == null) {
/* 375 */       if (required) {
/* 376 */         throw new AttributeNotFoundException(name);
/*     */       }
/*     */       return;
/*     */     } 
/* 380 */     String valueString = null;
/* 381 */     if (valueObject.getType() == 2) {
/* 382 */       valueString = ((PdfBoolean)valueObject).getValue() ? "yes" : "no";
/* 383 */     } else if (valueObject.getType() == 6) {
/* 384 */       valueString = ((PdfName)valueObject).getValue();
/* 385 */     } else if (valueObject.getType() == 8) {
/* 386 */       valueString = String.valueOf(((PdfNumber)valueObject).getValue());
/* 387 */     } else if (valueObject.getType() == 10) {
/* 388 */       valueString = ((PdfString)valueObject).getValue();
/*     */     } 
/*     */     
/* 391 */     this.attributes.add(new AttributeObject(name, valueString));
/*     */   }
/*     */   
/*     */   void addAttribute(String name, PdfObject valueObject) {
/* 395 */     addAttribute(name, valueObject, false);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void addFdfAttributes(int pageNumber) {
/* 402 */     addAttribute(new AttributeObject("page", String.valueOf(pageNumber)));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DestObject getDestination() {
/* 412 */     return this.destination;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public AnnotObject setDestination(DestObject destination) {
/* 423 */     this.destination = destination;
/* 424 */     return this;
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
/*     */   public String getAppearance() {
/* 436 */     return this.appearance;
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
/*     */   public AnnotObject setAppearance(String appearance) {
/* 448 */     this.appearance = appearance;
/* 449 */     return this;
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
/*     */   public String getDefaultAppearance() {
/* 461 */     return this.defaultAppearance;
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
/*     */   public AnnotObject setDefaultAppearance(String defaultAppearance) {
/* 473 */     this.defaultAppearance = defaultAppearance;
/* 474 */     return this;
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
/*     */   public String getDefaultStyle() {
/* 486 */     return this.defaultStyle;
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
/*     */   public AnnotObject setDefaultStyle(String defaultStyle) {
/* 498 */     this.defaultStyle = defaultStyle;
/* 499 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BorderStyleAltObject getBorderStyleAlt() {
/* 510 */     return this.borderStyleAlt;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public AnnotObject setBorderStyleAlt(BorderStyleAltObject borderStyleAlt) {
/* 521 */     this.borderStyleAlt = borderStyleAlt;
/* 522 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getVertices() {
/* 533 */     return this.vertices;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public AnnotObject setVertices(String vertices) {
/* 544 */     this.vertices = vertices;
/* 545 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PdfIndirectReference getRef() {
/* 553 */     return this.ref;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public AnnotObject setRef(PdfIndirectReference ref) {
/* 562 */     this.ref = ref;
/* 563 */     return this;
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/forms/xfdf/AnnotObject.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */