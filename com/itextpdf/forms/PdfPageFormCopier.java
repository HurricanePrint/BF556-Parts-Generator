/*     */ package com.itextpdf.forms;
/*     */ 
/*     */ import com.itextpdf.forms.fields.PdfFormField;
/*     */ import com.itextpdf.io.util.MessageFormatUtil;
/*     */ import com.itextpdf.kernel.pdf.IPdfPageExtraCopier;
/*     */ import com.itextpdf.kernel.pdf.PdfArray;
/*     */ import com.itextpdf.kernel.pdf.PdfDictionary;
/*     */ import com.itextpdf.kernel.pdf.PdfDocument;
/*     */ import com.itextpdf.kernel.pdf.PdfName;
/*     */ import com.itextpdf.kernel.pdf.PdfObject;
/*     */ import com.itextpdf.kernel.pdf.PdfPage;
/*     */ import com.itextpdf.kernel.pdf.PdfString;
/*     */ import com.itextpdf.kernel.pdf.annot.PdfAnnotation;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashSet;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import org.slf4j.Logger;
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
/*     */ public class PdfPageFormCopier
/*     */   implements IPdfPageExtraCopier
/*     */ {
/*     */   private PdfAcroForm formFrom;
/*     */   private PdfAcroForm formTo;
/*     */   private PdfDocument documentFrom;
/*     */   private PdfDocument documentTo;
/*  82 */   private static Logger logger = LoggerFactory.getLogger(PdfPageFormCopier.class);
/*     */ 
/*     */   
/*     */   public void copy(PdfPage fromPage, PdfPage toPage) {
/*  86 */     if (this.documentFrom != fromPage.getDocument()) {
/*  87 */       this.documentFrom = fromPage.getDocument();
/*  88 */       this.formFrom = PdfAcroForm.getAcroForm(this.documentFrom, false);
/*     */     } 
/*  90 */     if (this.documentTo != toPage.getDocument()) {
/*  91 */       this.documentTo = toPage.getDocument();
/*  92 */       this.formTo = PdfAcroForm.getAcroForm(this.documentTo, true);
/*     */     } 
/*     */     
/*  95 */     if (this.formFrom == null) {
/*     */       return;
/*     */     }
/*     */ 
/*     */     
/* 100 */     List<PdfName> excludedKeys = new ArrayList<>();
/* 101 */     excludedKeys.add(PdfName.Fields);
/* 102 */     excludedKeys.add(PdfName.DR);
/*     */     
/* 104 */     PdfDictionary dict = ((PdfDictionary)this.formFrom.getPdfObject()).copyTo(this.documentTo, excludedKeys, false);
/* 105 */     ((PdfDictionary)this.formTo.getPdfObject()).mergeDifferent(dict);
/*     */     
/* 107 */     Map<String, PdfFormField> fieldsFrom = this.formFrom.getFormFields();
/* 108 */     if (fieldsFrom.size() <= 0) {
/*     */       return;
/*     */     }
/* 111 */     Map<String, PdfFormField> fieldsTo = this.formTo.getFormFields();
/*     */     
/* 113 */     List<PdfAnnotation> annots = toPage.getAnnotations();
/*     */     
/* 115 */     for (PdfAnnotation annot : annots) {
/* 116 */       if (!annot.getSubtype().equals(PdfName.Widget)) {
/*     */         continue;
/*     */       }
/* 119 */       copyField(toPage, fieldsFrom, fieldsTo, annot);
/*     */     } 
/*     */   }
/*     */   
/*     */   private PdfFormField makeFormField(PdfObject fieldDict) {
/* 124 */     PdfFormField field = PdfFormField.makeFormField(fieldDict, this.documentTo);
/* 125 */     if (field == null) {
/* 126 */       logger.warn(MessageFormatUtil.format("Cannot create form field from a given PDF object: {0}", new Object[] { fieldDict
/* 127 */               .getIndirectReference() }));
/*     */     }
/* 129 */     return field;
/*     */   }
/*     */ 
/*     */   
/*     */   private void copyField(PdfPage toPage, Map<String, PdfFormField> fieldsFrom, Map<String, PdfFormField> fieldsTo, PdfAnnotation currentAnnot) {
/* 134 */     PdfDictionary parent = ((PdfDictionary)currentAnnot.getPdfObject()).getAsDictionary(PdfName.Parent);
/* 135 */     if (parent != null) {
/* 136 */       PdfFormField parentField = getParentField(parent, this.documentTo);
/* 137 */       if (parentField == null) {
/*     */         return;
/*     */       }
/* 140 */       PdfString parentName = parentField.getFieldName();
/* 141 */       if (parentName == null) {
/*     */         return;
/*     */       }
/* 144 */       copyParentFormField(toPage, fieldsTo, currentAnnot, parentField);
/*     */     } else {
/* 146 */       PdfString annotName = ((PdfDictionary)currentAnnot.getPdfObject()).getAsString(PdfName.T);
/* 147 */       String annotNameString = null;
/* 148 */       if (annotName != null) {
/* 149 */         annotNameString = annotName.toUnicodeString();
/*     */       }
/* 151 */       if (annotNameString != null && fieldsFrom.containsKey(annotNameString)) {
/* 152 */         PdfFormField field = makeFormField(currentAnnot.getPdfObject());
/* 153 */         if (field == null) {
/*     */           return;
/*     */         }
/* 156 */         if (fieldsTo.get(annotNameString) != null) {
/* 157 */           field = mergeFieldsWithTheSameName(field);
/*     */         }
/*     */         
/* 160 */         this.formTo.addField(field, toPage);
/* 161 */         field.updateDefaultAppearance();
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void copyParentFormField(PdfPage toPage, Map<String, PdfFormField> fieldsTo, PdfAnnotation annot, PdfFormField parentField) {
/* 168 */     PdfString parentName = parentField.getFieldName();
/* 169 */     if (!fieldsTo.containsKey(parentName.toUnicodeString())) {
/* 170 */       PdfFormField field = createParentFieldCopy((PdfDictionary)annot.getPdfObject(), this.documentTo);
/* 171 */       PdfArray kids = field.getKids();
/* 172 */       ((PdfDictionary)field.getPdfObject()).remove(PdfName.Kids);
/* 173 */       this.formTo.addField(field, toPage);
/* 174 */       ((PdfDictionary)field.getPdfObject()).put(PdfName.Kids, (PdfObject)kids);
/*     */     } else {
/* 176 */       PdfFormField field = makeFormField(annot.getPdfObject());
/* 177 */       if (field == null) {
/*     */         return;
/*     */       }
/* 180 */       PdfString fieldName = field.getFieldName();
/* 181 */       if (fieldName != null) {
/* 182 */         PdfFormField existingField = fieldsTo.get(fieldName.toUnicodeString());
/* 183 */         if (existingField != null) {
/* 184 */           PdfFormField mergedField = mergeFieldsWithTheSameName(field);
/* 185 */           this.formTo.getFormFields().put(mergedField.getFieldName().toUnicodeString(), mergedField);
/*     */         } else {
/* 187 */           HashSet<String> existingFields = new HashSet<>();
/* 188 */           getAllFieldNames(this.formTo.getFields(), existingFields);
/* 189 */           addChildToExistingParent((PdfDictionary)annot.getPdfObject(), existingFields, fieldsTo);
/*     */         }
/*     */       
/*     */       }
/* 193 */       else if (!parentField.getKids().contains(field.getPdfObject())) {
/* 194 */         HashSet<String> existingFields = new HashSet<>();
/* 195 */         getAllFieldNames(this.formTo.getFields(), existingFields);
/* 196 */         addChildToExistingParent((PdfDictionary)annot.getPdfObject(), existingFields);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private PdfFormField mergeFieldsWithTheSameName(PdfFormField newField) {
/* 203 */     String fullFieldName = newField.getFieldName().toUnicodeString();
/* 204 */     PdfString fieldName = ((PdfDictionary)newField.getPdfObject()).getAsString(PdfName.T);
/*     */     
/* 206 */     logger.warn(MessageFormatUtil.format("The document already has field {0}. Annotations of the fields with this name will be added to the existing one as children. If you want to have separate fields, please, rename them manually before copying.", new Object[] { fullFieldName }));
/*     */     
/* 208 */     PdfFormField existingField = this.formTo.getField(fullFieldName);
/* 209 */     if (existingField.isFlushed()) {
/* 210 */       int index = 0;
/*     */       while (true) {
/* 212 */         index++;
/* 213 */         newField.setFieldName(fieldName.toUnicodeString() + "_#" + index);
/* 214 */         fullFieldName = newField.getFieldName().toUnicodeString();
/* 215 */         if (this.formTo.getField(fullFieldName) == null)
/* 216 */           return newField; 
/*     */       } 
/* 218 */     }  ((PdfDictionary)newField.getPdfObject()).remove(PdfName.T);
/* 219 */     ((PdfDictionary)newField.getPdfObject()).remove(PdfName.P);
/*     */     
/* 221 */     this.formTo.getFields().remove(existingField.getPdfObject());
/*     */     
/* 223 */     PdfArray kids = existingField.getKids();
/* 224 */     if (kids != null && !kids.isEmpty()) {
/* 225 */       existingField.addKid(newField);
/* 226 */       return existingField;
/*     */     } 
/*     */     
/* 229 */     ((PdfDictionary)existingField.getPdfObject()).remove(PdfName.T);
/* 230 */     ((PdfDictionary)existingField.getPdfObject()).remove(PdfName.P);
/* 231 */     PdfFormField mergedField = PdfFormField.createEmptyField(this.documentTo);
/* 232 */     mergedField
/* 233 */       .put(PdfName.FT, (PdfObject)existingField.getFormType())
/* 234 */       .put(PdfName.T, (PdfObject)fieldName);
/* 235 */     PdfDictionary parent = existingField.getParent();
/* 236 */     if (parent != null) {
/* 237 */       mergedField.put(PdfName.Parent, (PdfObject)parent);
/* 238 */       PdfArray parentKids = parent.getAsArray(PdfName.Kids);
/* 239 */       for (int i = 0; i < parentKids.size(); i++) {
/* 240 */         PdfObject obj = parentKids.get(i);
/* 241 */         if (obj == existingField.getPdfObject()) {
/* 242 */           parentKids.set(i, mergedField.getPdfObject());
/*     */           break;
/*     */         } 
/*     */       } 
/*     */     } 
/* 247 */     kids = existingField.getKids();
/* 248 */     if (kids != null) {
/* 249 */       mergedField.put(PdfName.Kids, (PdfObject)kids);
/*     */     }
/*     */     
/* 252 */     mergedField.addKid(existingField).addKid(newField);
/* 253 */     PdfObject value = ((PdfDictionary)existingField.getPdfObject()).get(PdfName.V);
/* 254 */     if (value != null) {
/* 255 */       mergedField.put(PdfName.V, ((PdfDictionary)existingField.getPdfObject()).get(PdfName.V));
/*     */     }
/* 257 */     return mergedField;
/*     */   }
/*     */   
/*     */   private static PdfFormField getParentField(PdfDictionary parent, PdfDocument pdfDoc) {
/* 261 */     PdfDictionary parentOfParent = parent.getAsDictionary(PdfName.Parent);
/* 262 */     if (parentOfParent != null) {
/* 263 */       return getParentField(parentOfParent, pdfDoc);
/*     */     }
/*     */     
/* 266 */     return PdfFormField.makeFormField((PdfObject)parent, pdfDoc);
/*     */   }
/*     */   
/*     */   private PdfFormField createParentFieldCopy(PdfDictionary fieldDic, PdfDocument pdfDoc) {
/* 270 */     PdfDictionary parent = fieldDic.getAsDictionary(PdfName.Parent);
/* 271 */     PdfFormField field = PdfFormField.makeFormField((PdfObject)fieldDic, pdfDoc);
/*     */     
/* 273 */     if (parent != null) {
/* 274 */       field = createParentFieldCopy(parent, pdfDoc);
/* 275 */       PdfArray kids = (PdfArray)parent.get(PdfName.Kids);
/* 276 */       if (kids == null) {
/* 277 */         parent.put(PdfName.Kids, (PdfObject)new PdfArray((PdfObject)fieldDic));
/*     */       } else {
/* 279 */         kids.add((PdfObject)fieldDic);
/*     */       } 
/*     */     } 
/*     */     
/* 283 */     return field;
/*     */   }
/*     */   
/*     */   private void addChildToExistingParent(PdfDictionary fieldDic, Set<String> existingFields) {
/* 287 */     PdfDictionary parent = fieldDic.getAsDictionary(PdfName.Parent);
/* 288 */     if (parent == null) {
/*     */       return;
/*     */     }
/*     */     
/* 292 */     PdfString parentName = parent.getAsString(PdfName.T);
/* 293 */     if (parentName != null) {
/* 294 */       String name = parentName.toUnicodeString();
/* 295 */       if (existingFields.contains(name)) {
/* 296 */         PdfArray kids = parent.getAsArray(PdfName.Kids);
/* 297 */         kids.add((PdfObject)fieldDic);
/*     */       } else {
/* 299 */         parent.put(PdfName.Kids, (PdfObject)new PdfArray((PdfObject)fieldDic));
/* 300 */         addChildToExistingParent(parent, existingFields);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void addChildToExistingParent(PdfDictionary fieldDic, Set<String> existingFields, Map<String, PdfFormField> fieldsTo) {
/* 307 */     PdfDictionary parent = fieldDic.getAsDictionary(PdfName.Parent);
/* 308 */     if (parent == null) {
/*     */       return;
/*     */     }
/*     */     
/* 312 */     PdfString parentName = parent.getAsString(PdfName.T);
/* 313 */     if (parentName != null) {
/* 314 */       String name = parentName.toUnicodeString();
/* 315 */       if (existingFields.contains(name)) {
/* 316 */         PdfArray kids = parent.getAsArray(PdfName.Kids);
/* 317 */         for (PdfObject kid : kids) {
/* 318 */           if (((PdfDictionary)kid).get(PdfName.T).equals(fieldDic.get(PdfName.T))) {
/* 319 */             PdfFormField kidField = makeFormField(kid);
/* 320 */             PdfFormField field = makeFormField((PdfObject)fieldDic);
/* 321 */             if (kidField == null || field == null) {
/*     */               continue;
/*     */             }
/* 324 */             fieldsTo.put(kidField.getFieldName().toUnicodeString(), kidField);
/* 325 */             PdfFormField mergedField = mergeFieldsWithTheSameName(field);
/* 326 */             this.formTo.getFormFields().put(mergedField.getFieldName().toUnicodeString(), mergedField);
/*     */             return;
/*     */           } 
/*     */         } 
/* 330 */         kids.add((PdfObject)fieldDic);
/*     */       } else {
/* 332 */         parent.put(PdfName.Kids, (PdfObject)new PdfArray((PdfObject)fieldDic));
/* 333 */         addChildToExistingParent(parent, existingFields);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private void getAllFieldNames(PdfArray fields, Set<String> existingFields) {
/* 339 */     for (PdfObject field : fields) {
/* 340 */       if (field.isFlushed()) {
/*     */         continue;
/*     */       }
/* 343 */       PdfDictionary dic = (PdfDictionary)field;
/* 344 */       PdfString name = dic.getAsString(PdfName.T);
/* 345 */       if (name != null) {
/* 346 */         existingFields.add(name.toUnicodeString());
/*     */       }
/* 348 */       PdfArray kids = dic.getAsArray(PdfName.Kids);
/* 349 */       if (kids != null)
/* 350 */         getAllFieldNames(kids, existingFields); 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/forms/PdfPageFormCopier.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */