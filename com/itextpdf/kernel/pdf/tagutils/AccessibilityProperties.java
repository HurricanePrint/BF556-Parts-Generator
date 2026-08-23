/*     */ package com.itextpdf.kernel.pdf.tagutils;
/*     */ 
/*     */ import com.itextpdf.kernel.pdf.tagging.PdfNamespace;
/*     */ import com.itextpdf.kernel.pdf.tagging.PdfStructureAttributes;
/*     */ import java.io.Serializable;
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
/*     */ public abstract class AccessibilityProperties
/*     */   implements Serializable
/*     */ {
/*     */   public String getRole() {
/*  53 */     return null;
/*     */   }
/*     */   
/*     */   public AccessibilityProperties setRole(String role) {
/*  57 */     return this;
/*     */   }
/*     */   
/*     */   public String getLanguage() {
/*  61 */     return null;
/*     */   }
/*     */   
/*     */   public AccessibilityProperties setLanguage(String language) {
/*  65 */     return this;
/*     */   }
/*     */   
/*     */   public String getActualText() {
/*  69 */     return null;
/*     */   }
/*     */   
/*     */   public AccessibilityProperties setActualText(String actualText) {
/*  73 */     return this;
/*     */   }
/*     */   
/*     */   public String getAlternateDescription() {
/*  77 */     return null;
/*     */   }
/*     */   
/*     */   public AccessibilityProperties setAlternateDescription(String alternateDescription) {
/*  81 */     return this;
/*     */   }
/*     */   
/*     */   public String getExpansion() {
/*  85 */     return null;
/*     */   }
/*     */   
/*     */   public AccessibilityProperties setExpansion(String expansion) {
/*  89 */     return this;
/*     */   }
/*     */   
/*     */   public String getPhoneme() {
/*  93 */     return null;
/*     */   }
/*     */   
/*     */   public AccessibilityProperties setPhoneme(String phoneme) {
/*  97 */     return this;
/*     */   }
/*     */   
/*     */   public String getPhoneticAlphabet() {
/* 101 */     return null;
/*     */   }
/*     */   
/*     */   public AccessibilityProperties setPhoneticAlphabet(String phoneticAlphabet) {
/* 105 */     return this;
/*     */   }
/*     */   
/*     */   public PdfNamespace getNamespace() {
/* 109 */     return null;
/*     */   }
/*     */   
/*     */   public AccessibilityProperties setNamespace(PdfNamespace namespace) {
/* 113 */     return this;
/*     */   }
/*     */   
/*     */   public AccessibilityProperties addRef(TagTreePointer treePointer) {
/* 117 */     return this;
/*     */   }
/*     */   
/*     */   public List<TagTreePointer> getRefsList() {
/* 121 */     return Collections.emptyList();
/*     */   }
/*     */   
/*     */   public AccessibilityProperties clearRefs() {
/* 125 */     return this;
/*     */   }
/*     */   
/*     */   public AccessibilityProperties addAttributes(PdfStructureAttributes attributes) {
/* 129 */     return this;
/*     */   }
/*     */   
/*     */   public AccessibilityProperties addAttributes(int index, PdfStructureAttributes attributes) {
/* 133 */     return this;
/*     */   }
/*     */   
/*     */   public AccessibilityProperties clearAttributes() {
/* 137 */     return this;
/*     */   }
/*     */   
/*     */   public List<PdfStructureAttributes> getAttributesList() {
/* 141 */     return Collections.emptyList();
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/kernel/pdf/tagutils/AccessibilityProperties.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */