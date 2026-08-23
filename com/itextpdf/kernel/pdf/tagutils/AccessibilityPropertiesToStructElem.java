/*     */ package com.itextpdf.kernel.pdf.tagutils;
/*     */ 
/*     */ import com.itextpdf.kernel.pdf.PdfArray;
/*     */ import com.itextpdf.kernel.pdf.PdfDictionary;
/*     */ import com.itextpdf.kernel.pdf.PdfName;
/*     */ import com.itextpdf.kernel.pdf.PdfNumber;
/*     */ import com.itextpdf.kernel.pdf.PdfObject;
/*     */ import com.itextpdf.kernel.pdf.PdfString;
/*     */ import com.itextpdf.kernel.pdf.tagging.PdfStructElem;
/*     */ import com.itextpdf.kernel.pdf.tagging.PdfStructureAttributes;
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
/*     */ final class AccessibilityPropertiesToStructElem
/*     */ {
/*     */   static void apply(AccessibilityProperties properties, PdfStructElem elem) {
/*  59 */     if (properties.getActualText() != null) {
/*  60 */       elem.setActualText(new PdfString(properties.getActualText(), "UnicodeBig"));
/*     */     }
/*  62 */     if (properties.getAlternateDescription() != null) {
/*  63 */       elem.setAlt(new PdfString(properties.getAlternateDescription(), "UnicodeBig"));
/*     */     }
/*  65 */     if (properties.getExpansion() != null) {
/*  66 */       elem.setE(new PdfString(properties.getExpansion(), "UnicodeBig"));
/*     */     }
/*  68 */     if (properties.getLanguage() != null) {
/*  69 */       elem.setLang(new PdfString(properties.getLanguage(), "UnicodeBig"));
/*     */     }
/*     */     
/*  72 */     List<PdfStructureAttributes> newAttributesList = properties.getAttributesList();
/*  73 */     if (newAttributesList.size() > 0) {
/*  74 */       PdfObject attributesObject = elem.getAttributes(false);
/*     */       
/*  76 */       PdfObject combinedAttributes = combineAttributesList(attributesObject, -1, newAttributesList, ((PdfDictionary)elem.getPdfObject()).getAsNumber(PdfName.R));
/*  77 */       elem.setAttributes(combinedAttributes);
/*     */     } 
/*     */     
/*  80 */     if (properties.getPhoneme() != null) {
/*  81 */       elem.setPhoneme(new PdfString(properties.getPhoneme(), "UnicodeBig"));
/*     */     }
/*  83 */     if (properties.getPhoneticAlphabet() != null) {
/*  84 */       elem.setPhoneticAlphabet(new PdfName(properties.getPhoneticAlphabet()));
/*     */     }
/*  86 */     if (properties.getNamespace() != null) {
/*  87 */       elem.setNamespace(properties.getNamespace());
/*     */     }
/*  89 */     for (TagTreePointer ref : properties.getRefsList()) {
/*  90 */       elem.addRef(ref.getCurrentStructElem());
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   static PdfObject combineAttributesList(PdfObject attributesObject, int insertIndex, List<PdfStructureAttributes> newAttributesList, PdfNumber revision) {
/*     */     PdfArray pdfArray;
/*  97 */     if (attributesObject instanceof PdfDictionary) {
/*  98 */       PdfArray combinedAttributesArray = new PdfArray();
/*  99 */       combinedAttributesArray.add(attributesObject);
/* 100 */       addNewAttributesToAttributesArray(insertIndex, newAttributesList, revision, combinedAttributesArray);
/* 101 */       pdfArray = combinedAttributesArray;
/* 102 */     } else if (attributesObject instanceof PdfArray) {
/* 103 */       PdfArray combinedAttributesArray = (PdfArray)attributesObject;
/* 104 */       addNewAttributesToAttributesArray(insertIndex, newAttributesList, revision, combinedAttributesArray);
/* 105 */       pdfArray = combinedAttributesArray;
/*     */     }
/* 107 */     else if (newAttributesList.size() == 1) {
/* 108 */       if (insertIndex > 0) {
/* 109 */         throw new IndexOutOfBoundsException();
/*     */       }
/* 111 */       PdfObject combinedAttributes = ((PdfStructureAttributes)newAttributesList.get(0)).getPdfObject();
/*     */     } else {
/* 113 */       pdfArray = new PdfArray();
/* 114 */       addNewAttributesToAttributesArray(insertIndex, newAttributesList, revision, pdfArray);
/*     */     } 
/*     */ 
/*     */     
/* 118 */     return (PdfObject)pdfArray;
/*     */   }
/*     */   
/*     */   private static void addNewAttributesToAttributesArray(int insertIndex, List<PdfStructureAttributes> newAttributesList, PdfNumber revision, PdfArray attributesArray) {
/* 122 */     if (insertIndex < 0) {
/* 123 */       insertIndex = attributesArray.size();
/*     */     }
/* 125 */     if (revision != null) {
/* 126 */       for (PdfStructureAttributes attributes : newAttributesList) {
/* 127 */         attributesArray.add(insertIndex++, attributes.getPdfObject());
/* 128 */         attributesArray.add(insertIndex++, (PdfObject)revision);
/*     */       } 
/*     */     } else {
/* 131 */       for (PdfStructureAttributes newAttribute : newAttributesList)
/* 132 */         attributesArray.add(insertIndex++, newAttribute.getPdfObject()); 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/kernel/pdf/tagutils/AccessibilityPropertiesToStructElem.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */