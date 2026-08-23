/*     */ package com.itextpdf.kernel.pdf.tagutils;
/*     */ 
/*     */ import com.itextpdf.kernel.pdf.PdfArray;
/*     */ import com.itextpdf.kernel.pdf.PdfDictionary;
/*     */ import com.itextpdf.kernel.pdf.PdfName;
/*     */ import com.itextpdf.kernel.pdf.PdfObject;
/*     */ import com.itextpdf.kernel.pdf.PdfString;
/*     */ import com.itextpdf.kernel.pdf.tagging.PdfNamespace;
/*     */ import com.itextpdf.kernel.pdf.tagging.PdfStructElem;
/*     */ import com.itextpdf.kernel.pdf.tagging.PdfStructTreeRoot;
/*     */ import com.itextpdf.kernel.pdf.tagging.PdfStructureAttributes;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
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
/*     */ class BackedAccessibilityProperties
/*     */   extends AccessibilityProperties
/*     */ {
/*     */   private static final long serialVersionUID = 4080083623525383278L;
/*     */   private TagTreePointer pointerToBackingElem;
/*     */   
/*     */   BackedAccessibilityProperties(TagTreePointer pointerToBackingElem) {
/*  68 */     this.pointerToBackingElem = new TagTreePointer(pointerToBackingElem);
/*     */   }
/*     */ 
/*     */   
/*     */   public String getRole() {
/*  73 */     return getBackingElem().getRole().getValue();
/*     */   }
/*     */ 
/*     */   
/*     */   public AccessibilityProperties setRole(String role) {
/*  78 */     getBackingElem().setRole(PdfStructTreeRoot.convertRoleToPdfName(role));
/*  79 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getLanguage() {
/*  84 */     return toUnicodeString(getBackingElem().getLang());
/*     */   }
/*     */ 
/*     */   
/*     */   public AccessibilityProperties setLanguage(String language) {
/*  89 */     getBackingElem().setLang(new PdfString(language, "UnicodeBig"));
/*  90 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getActualText() {
/*  95 */     return toUnicodeString(getBackingElem().getActualText());
/*     */   }
/*     */ 
/*     */   
/*     */   public AccessibilityProperties setActualText(String actualText) {
/* 100 */     getBackingElem().setActualText(new PdfString(actualText, "UnicodeBig"));
/* 101 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getAlternateDescription() {
/* 106 */     return toUnicodeString(getBackingElem().getAlt());
/*     */   }
/*     */ 
/*     */   
/*     */   public AccessibilityProperties setAlternateDescription(String alternateDescription) {
/* 111 */     getBackingElem().setAlt(new PdfString(alternateDescription, "UnicodeBig"));
/* 112 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getExpansion() {
/* 117 */     return toUnicodeString(getBackingElem().getE());
/*     */   }
/*     */ 
/*     */   
/*     */   public AccessibilityProperties setExpansion(String expansion) {
/* 122 */     getBackingElem().setE(new PdfString(expansion, "UnicodeBig"));
/* 123 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public AccessibilityProperties addAttributes(PdfStructureAttributes attributes) {
/* 128 */     return addAttributes(-1, attributes);
/*     */   }
/*     */   
/*     */   public AccessibilityProperties addAttributes(int index, PdfStructureAttributes attributes) {
/* 132 */     if (attributes == null) {
/* 133 */       return this;
/*     */     }
/*     */     
/* 136 */     PdfObject attributesObject = getBackingElem().getAttributes(false);
/*     */     
/* 138 */     PdfObject combinedAttributes = AccessibilityPropertiesToStructElem.combineAttributesList(attributesObject, index, 
/* 139 */         Collections.singletonList(attributes), ((PdfDictionary)
/* 140 */         getBackingElem().getPdfObject()).getAsNumber(PdfName.R));
/* 141 */     getBackingElem().setAttributes(combinedAttributes);
/* 142 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public AccessibilityProperties clearAttributes() {
/* 147 */     ((PdfDictionary)getBackingElem().getPdfObject()).remove(PdfName.A);
/* 148 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public List<PdfStructureAttributes> getAttributesList() {
/* 153 */     ArrayList<PdfStructureAttributes> attributesList = new ArrayList<>();
/* 154 */     PdfObject elemAttributesObj = getBackingElem().getAttributes(false);
/* 155 */     if (elemAttributesObj != null) {
/* 156 */       if (elemAttributesObj.isDictionary()) {
/* 157 */         attributesList.add(new PdfStructureAttributes((PdfDictionary)elemAttributesObj));
/* 158 */       } else if (elemAttributesObj.isArray()) {
/* 159 */         PdfArray attributesArray = (PdfArray)elemAttributesObj;
/* 160 */         for (PdfObject attributeObj : attributesArray) {
/* 161 */           if (attributeObj.isDictionary()) {
/* 162 */             attributesList.add(new PdfStructureAttributes((PdfDictionary)attributeObj));
/*     */           }
/*     */         } 
/*     */       } 
/*     */     }
/* 167 */     return attributesList;
/*     */   }
/*     */ 
/*     */   
/*     */   public AccessibilityProperties setPhoneme(String phoneme) {
/* 172 */     getBackingElem().setPhoneme(new PdfString(phoneme));
/* 173 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getPhoneme() {
/* 178 */     return toUnicodeString(getBackingElem().getPhoneme());
/*     */   }
/*     */ 
/*     */   
/*     */   public AccessibilityProperties setPhoneticAlphabet(String phoneticAlphabet) {
/* 183 */     getBackingElem().setPhoneticAlphabet(PdfStructTreeRoot.convertRoleToPdfName(phoneticAlphabet));
/* 184 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getPhoneticAlphabet() {
/* 189 */     return getBackingElem().getPhoneticAlphabet().getValue();
/*     */   }
/*     */   
/*     */   public AccessibilityProperties setNamespace(PdfNamespace namespace) {
/* 193 */     getBackingElem().setNamespace(namespace);
/* 194 */     this.pointerToBackingElem.getContext().ensureNamespaceRegistered(namespace);
/* 195 */     return this;
/*     */   }
/*     */   
/*     */   public PdfNamespace getNamespace() {
/* 199 */     return getBackingElem().getNamespace();
/*     */   }
/*     */ 
/*     */   
/*     */   public AccessibilityProperties addRef(TagTreePointer treePointer) {
/* 204 */     getBackingElem().addRef(treePointer.getCurrentStructElem());
/* 205 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public List<TagTreePointer> getRefsList() {
/* 210 */     List<TagTreePointer> refsList = new ArrayList<>();
/* 211 */     for (PdfStructElem ref : getBackingElem().getRefsList()) {
/* 212 */       refsList.add(new TagTreePointer(ref, this.pointerToBackingElem.getDocument()));
/*     */     }
/* 214 */     return Collections.unmodifiableList(refsList);
/*     */   }
/*     */ 
/*     */   
/*     */   public AccessibilityProperties clearRefs() {
/* 219 */     ((PdfDictionary)getBackingElem().getPdfObject()).remove(PdfName.Ref);
/* 220 */     return this;
/*     */   }
/*     */   
/*     */   private PdfStructElem getBackingElem() {
/* 224 */     return this.pointerToBackingElem.getCurrentStructElem();
/*     */   }
/*     */   
/*     */   private String toUnicodeString(PdfString pdfString) {
/* 228 */     return (pdfString != null) ? pdfString.toUnicodeString() : null;
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/kernel/pdf/tagutils/BackedAccessibilityProperties.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */