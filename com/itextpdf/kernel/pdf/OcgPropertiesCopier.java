/*     */ package com.itextpdf.kernel.pdf;
/*     */ 
/*     */ import com.itextpdf.io.util.MessageFormatUtil;
/*     */ import com.itextpdf.kernel.pdf.annot.PdfAnnotation;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashSet;
/*     */ import java.util.LinkedHashSet;
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
/*     */ final class OcgPropertiesCopier
/*     */ {
/*  62 */   private static final Logger LOGGER = LoggerFactory.getLogger(OcgPropertiesCopier.class);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void copyOCGProperties(PdfDocument fromDocument, PdfDocument toDocument, Map<PdfPage, PdfPage> page2page) {
/*     */     try {
/*  72 */       PdfDictionary toOcProperties = toDocument.getCatalog().getPdfObject().getAsDictionary(PdfName.OCProperties);
/*     */       
/*  74 */       Set<PdfIndirectReference> fromOcgsToCopy = getAllUsedNonFlushedOCGs(page2page, toOcProperties);
/*  75 */       if (fromOcgsToCopy.isEmpty()) {
/*     */         return;
/*     */       }
/*     */ 
/*     */ 
/*     */       
/*  81 */       toOcProperties = toDocument.getCatalog().fillAndGetOcPropertiesDictionary();
/*     */       
/*  83 */       PdfDictionary fromOcProperties = fromDocument.getCatalog().getPdfObject().getAsDictionary(PdfName.OCProperties);
/*     */       
/*  85 */       copyOCGs(fromOcgsToCopy, toOcProperties, toDocument);
/*     */       
/*  87 */       copyDDictionary(fromOcgsToCopy, fromOcProperties.getAsDictionary(PdfName.D), toOcProperties, toDocument);
/*     */     }
/*  89 */     catch (Exception ex) {
/*  90 */       LOGGER.error(MessageFormatUtil.format("OCG copying caused the following exception: {0}.", new Object[] { ex.toString() }));
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private static Set<PdfIndirectReference> getAllUsedNonFlushedOCGs(Map<PdfPage, PdfPage> page2page, PdfDictionary toOcProperties) {
/*  96 */     Set<PdfIndirectReference> fromUsedOcgs = new LinkedHashSet<>();
/*     */     
/*  98 */     PdfPage[] fromPages = (PdfPage[])page2page.keySet().toArray((Object[])new PdfPage[0]);
/*  99 */     PdfPage[] toPages = (PdfPage[])page2page.values().toArray((Object[])new PdfPage[0]);
/* 100 */     for (int i = 0; i < toPages.length; i++) {
/* 101 */       PdfPage fromPage = fromPages[i];
/* 102 */       PdfPage toPage = toPages[i];
/*     */ 
/*     */       
/* 105 */       List<PdfAnnotation> toAnnotations = toPage.getAnnotations();
/* 106 */       List<PdfAnnotation> fromAnnotations = fromPage.getAnnotations();
/* 107 */       for (int j = 0; j < toAnnotations.size(); j++) {
/* 108 */         if (!((PdfAnnotation)toAnnotations.get(j)).isFlushed()) {
/* 109 */           PdfDictionary toAnnotDict = (PdfDictionary)((PdfAnnotation)toAnnotations.get(j)).getPdfObject();
/* 110 */           PdfDictionary fromAnnotDict = (PdfDictionary)((PdfAnnotation)fromAnnotations.get(j)).getPdfObject();
/* 111 */           PdfAnnotation toAnnot = toAnnotations.get(j);
/* 112 */           PdfAnnotation fromAnnot = fromAnnotations.get(j);
/* 113 */           if (!toAnnotDict.isFlushed()) {
/* 114 */             getUsedNonFlushedOCGsFromOcDict(toAnnotDict.getAsDictionary(PdfName.OC), fromAnnotDict
/* 115 */                 .getAsDictionary(PdfName.OC), fromUsedOcgs, toOcProperties);
/*     */             
/* 117 */             getUsedNonFlushedOCGsFromXObject(toAnnot.getNormalAppearanceObject(), fromAnnot
/* 118 */                 .getNormalAppearanceObject(), fromUsedOcgs, toOcProperties);
/* 119 */             getUsedNonFlushedOCGsFromXObject(toAnnot.getRolloverAppearanceObject(), fromAnnot
/* 120 */                 .getRolloverAppearanceObject(), fromUsedOcgs, toOcProperties);
/* 121 */             getUsedNonFlushedOCGsFromXObject(toAnnot.getDownAppearanceObject(), fromAnnot
/* 122 */                 .getDownAppearanceObject(), fromUsedOcgs, toOcProperties);
/*     */           } 
/*     */         } 
/*     */       } 
/*     */       
/* 127 */       PdfDictionary toResources = toPage.getPdfObject().getAsDictionary(PdfName.Resources);
/* 128 */       PdfDictionary fromResources = fromPage.getPdfObject().getAsDictionary(PdfName.Resources);
/* 129 */       getUsedNonFlushedOCGsFromResources(toResources, fromResources, fromUsedOcgs, toOcProperties);
/*     */     } 
/* 131 */     return fromUsedOcgs;
/*     */   }
/*     */ 
/*     */   
/*     */   private static void getUsedNonFlushedOCGsFromResources(PdfDictionary toResources, PdfDictionary fromResources, Set<PdfIndirectReference> fromUsedOcgs, PdfDictionary toOcProperties) {
/* 136 */     if (toResources != null && !toResources.isFlushed()) {
/*     */       
/* 138 */       PdfDictionary toProperties = toResources.getAsDictionary(PdfName.Properties);
/* 139 */       PdfDictionary fromProperties = fromResources.getAsDictionary(PdfName.Properties);
/* 140 */       if (toProperties != null && !toProperties.isFlushed()) {
/* 141 */         for (PdfName name : toProperties.keySet()) {
/* 142 */           PdfObject toCurrObj = toProperties.get(name);
/* 143 */           PdfObject fromCurrObj = fromProperties.get(name);
/* 144 */           getUsedNonFlushedOCGsFromOcDict(toCurrObj, fromCurrObj, fromUsedOcgs, toOcProperties);
/*     */         } 
/*     */       }
/*     */ 
/*     */       
/* 149 */       PdfDictionary toXObject = toResources.getAsDictionary(PdfName.XObject);
/* 150 */       PdfDictionary fromXObject = fromResources.getAsDictionary(PdfName.XObject);
/* 151 */       getUsedNonFlushedOCGsFromXObject(toXObject, fromXObject, fromUsedOcgs, toOcProperties);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private static void getUsedNonFlushedOCGsFromXObject(PdfDictionary toXObject, PdfDictionary fromXObject, Set<PdfIndirectReference> fromUsedOcgs, PdfDictionary toOcProperties) {
/* 157 */     if (toXObject != null && !toXObject.isFlushed()) {
/* 158 */       if (toXObject.isStream() && !toXObject.isFlushed()) {
/* 159 */         PdfStream toStream = (PdfStream)toXObject;
/* 160 */         PdfStream fromStream = (PdfStream)fromXObject;
/* 161 */         getUsedNonFlushedOCGsFromOcDict(toStream.getAsDictionary(PdfName.OC), fromStream
/* 162 */             .getAsDictionary(PdfName.OC), fromUsedOcgs, toOcProperties);
/* 163 */         getUsedNonFlushedOCGsFromResources(toStream.getAsDictionary(PdfName.Resources), fromStream
/* 164 */             .getAsDictionary(PdfName.Resources), fromUsedOcgs, toOcProperties);
/*     */       } else {
/* 166 */         for (PdfName name : toXObject.keySet()) {
/* 167 */           PdfObject toCurrObj = toXObject.get(name);
/* 168 */           PdfObject fromCurrObj = fromXObject.get(name);
/* 169 */           if (toCurrObj.isStream() && !toCurrObj.isFlushed()) {
/* 170 */             PdfStream toStream = (PdfStream)toCurrObj;
/* 171 */             PdfStream fromStream = (PdfStream)fromCurrObj;
/* 172 */             getUsedNonFlushedOCGsFromXObject(toStream, fromStream, fromUsedOcgs, toOcProperties);
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private static void getUsedNonFlushedOCGsFromOcDict(PdfObject toObj, PdfObject fromObj, Set<PdfIndirectReference> fromUsedOcgs, PdfDictionary toOcProperties) {
/* 181 */     if (toObj != null && toObj.isDictionary() && !toObj.isFlushed()) {
/* 182 */       PdfDictionary toCurrDict = (PdfDictionary)toObj;
/* 183 */       PdfDictionary fromCurrDict = (PdfDictionary)fromObj;
/* 184 */       PdfName typeName = toCurrDict.getAsName(PdfName.Type);
/* 185 */       if (PdfName.OCG.equals(typeName) && !ocgAlreadyInOCGs(toCurrDict.getIndirectReference(), toOcProperties)) {
/* 186 */         fromUsedOcgs.add(fromCurrDict.getIndirectReference());
/* 187 */       } else if (PdfName.OCMD.equals(typeName)) {
/* 188 */         PdfArray toOcgs = null;
/* 189 */         PdfArray fromOcgs = null;
/* 190 */         if (toCurrDict.getAsDictionary(PdfName.OCGs) != null) {
/* 191 */           toOcgs = new PdfArray();
/* 192 */           toOcgs.add(toCurrDict.getAsDictionary(PdfName.OCGs));
/*     */           
/* 194 */           fromOcgs = new PdfArray();
/* 195 */           fromOcgs.add(fromCurrDict.getAsDictionary(PdfName.OCGs));
/* 196 */         } else if (toCurrDict.getAsArray(PdfName.OCGs) != null) {
/* 197 */           toOcgs = toCurrDict.getAsArray(PdfName.OCGs);
/* 198 */           fromOcgs = fromCurrDict.getAsArray(PdfName.OCGs);
/*     */         } 
/*     */         
/* 201 */         if (toOcgs != null && !toOcgs.isFlushed()) {
/* 202 */           for (int i = 0; i < toOcgs.size(); i++) {
/* 203 */             getUsedNonFlushedOCGsFromOcDict(toOcgs.get(i), fromOcgs.get(i), fromUsedOcgs, toOcProperties);
/*     */           }
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private static void copyOCGs(Set<PdfIndirectReference> fromOcgsToCopy, PdfDictionary toOcProperties, PdfDocument toDocument) {
/* 211 */     Set<String> layerNames = new HashSet<>();
/* 212 */     if (toOcProperties.getAsArray(PdfName.OCGs) != null) {
/* 213 */       PdfArray toOcgs = toOcProperties.getAsArray(PdfName.OCGs);
/* 214 */       for (PdfObject toOcgObj : toOcgs) {
/* 215 */         if (toOcgObj.isDictionary()) {
/* 216 */           layerNames.add(((PdfDictionary)toOcgObj).getAsString(PdfName.Name).toUnicodeString());
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 221 */     boolean hasConflictingNames = false;
/* 222 */     for (PdfIndirectReference fromOcgRef : fromOcgsToCopy) {
/* 223 */       PdfDictionary toOcg = (PdfDictionary)fromOcgRef.getRefersTo().copyTo(toDocument, false);
/*     */       
/* 225 */       String currentLayerName = toOcg.getAsString(PdfName.Name).toUnicodeString();
/*     */       
/* 227 */       if (layerNames.contains(currentLayerName)) {
/* 228 */         hasConflictingNames = true;
/* 229 */         int i = 0;
/* 230 */         while (layerNames.contains(currentLayerName + "_" + i)) {
/* 231 */           i++;
/*     */         }
/* 233 */         currentLayerName = currentLayerName + "_" + i;
/* 234 */         toOcg.put(PdfName.Name, new PdfString(currentLayerName, "UnicodeBig"));
/*     */       } 
/*     */       
/* 237 */       if (toOcProperties.getAsArray(PdfName.OCGs) == null) {
/* 238 */         toOcProperties.put(PdfName.OCGs, new PdfArray());
/*     */       }
/* 240 */       toOcProperties.getAsArray(PdfName.OCGs).add(toOcg);
/*     */     } 
/*     */     
/* 243 */     if (hasConflictingNames) {
/* 244 */       LOGGER.warn("Document has conflicting names for optional content groups. Groups with conflicting names will be renamed");
/*     */     }
/*     */   }
/*     */   
/*     */   private static boolean ocgAlreadyInOCGs(PdfIndirectReference toOcgRef, PdfDictionary toOcProperties) {
/* 249 */     if (toOcProperties == null) {
/* 250 */       return false;
/*     */     }
/* 252 */     PdfArray toOcgs = toOcProperties.getAsArray(PdfName.OCGs);
/* 253 */     if (toOcgs != null) {
/* 254 */       for (PdfObject toOcg : toOcgs) {
/* 255 */         if (toOcgRef.equals(toOcg.getIndirectReference())) {
/* 256 */           return true;
/*     */         }
/*     */       } 
/*     */     }
/* 260 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   private static void copyDDictionary(Set<PdfIndirectReference> fromOcgsToCopy, PdfDictionary fromDDict, PdfDictionary toOcProperties, PdfDocument toDocument) {
/* 265 */     if (toOcProperties.getAsDictionary(PdfName.D) == null) {
/* 266 */       toOcProperties.put(PdfName.D, new PdfDictionary());
/*     */     }
/*     */     
/* 269 */     PdfDictionary toDDict = toOcProperties.getAsDictionary(PdfName.D);
/*     */ 
/*     */ 
/*     */     
/* 273 */     toDDict.remove(PdfName.Creator);
/*     */     
/* 275 */     copyDArrayField(PdfName.ON, fromOcgsToCopy, fromDDict, toDDict, toDocument);
/* 276 */     copyDArrayField(PdfName.OFF, fromOcgsToCopy, fromDDict, toDDict, toDocument);
/*     */ 
/*     */     
/* 279 */     copyDArrayField(PdfName.Order, fromOcgsToCopy, fromDDict, toDDict, toDocument);
/*     */     
/* 281 */     copyDArrayField(PdfName.RBGroups, fromOcgsToCopy, fromDDict, toDDict, toDocument);
/* 282 */     copyDArrayField(PdfName.Locked, fromOcgsToCopy, fromDDict, toDDict, toDocument);
/*     */   }
/*     */ 
/*     */   
/*     */   private static void attemptToAddObjectToArray(Set<PdfIndirectReference> fromOcgsToCopy, PdfObject fromObj, PdfArray toArray, PdfDocument toDocument) {
/* 287 */     PdfIndirectReference fromObjRef = fromObj.getIndirectReference();
/* 288 */     if (fromObjRef != null && fromOcgsToCopy.contains(fromObjRef)) {
/* 289 */       toArray.add(fromObj.copyTo(toDocument, false));
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private static void copyDArrayField(PdfName fieldToCopy, Set<PdfIndirectReference> fromOcgsToCopy, PdfDictionary fromDict, PdfDictionary toDict, PdfDocument toDocument) {
/* 295 */     if (fromDict.getAsArray(fieldToCopy) == null) {
/*     */       return;
/*     */     }
/* 298 */     PdfArray fromArray = fromDict.getAsArray(fieldToCopy);
/*     */     
/* 300 */     if (toDict.getAsArray(fieldToCopy) == null) {
/* 301 */       toDict.put(fieldToCopy, new PdfArray());
/*     */     }
/* 303 */     PdfArray toArray = toDict.getAsArray(fieldToCopy);
/*     */     
/* 305 */     Set<PdfIndirectReference> toOcgsToCopy = new HashSet<>();
/* 306 */     for (PdfIndirectReference fromRef : fromOcgsToCopy) {
/* 307 */       toOcgsToCopy.add(fromRef.getRefersTo().copyTo(toDocument, false).getIndirectReference());
/*     */     }
/* 309 */     if (PdfName.Order.equals(fieldToCopy)) {
/*     */       
/* 311 */       List<Integer> removeIndex = new ArrayList<>(); int i;
/* 312 */       for (i = 0; i < toArray.size(); i++) {
/* 313 */         PdfObject toOrderItem = toArray.get(i);
/* 314 */         if (orderBranchContainsSetElements(toOrderItem, toArray, i, toOcgsToCopy, null, null)) {
/* 315 */           removeIndex.add(Integer.valueOf(i));
/*     */         }
/*     */       } 
/* 318 */       for (i = removeIndex.size() - 1; i > -1; i--) {
/* 319 */         toArray.remove(((Integer)removeIndex.get(i)).intValue());
/*     */       }
/*     */       
/* 322 */       PdfArray toOcgs = toDocument.getCatalog().getPdfObject().getAsDictionary(PdfName.OCProperties).getAsArray(PdfName.OCGs);
/*     */       
/* 324 */       for (int j = 0; j < fromArray.size(); j++) {
/* 325 */         PdfObject fromOrderItem = fromArray.get(j);
/* 326 */         if (orderBranchContainsSetElements(fromOrderItem, fromArray, j, fromOcgsToCopy, toOcgs, toDocument)) {
/* 327 */           toArray.add(fromOrderItem.copyTo(toDocument, false));
/*     */         
/*     */         }
/*     */       
/*     */       }
/*     */     
/*     */     }
/* 334 */     else if (PdfName.RBGroups.equals(fieldToCopy)) {
/*     */       
/* 336 */       for (int i = toArray.size() - 1; i > -1; i--) {
/* 337 */         PdfArray toRbGroup = (PdfArray)toArray.get(i);
/* 338 */         for (PdfObject toRbGroupItemObj : toRbGroup) {
/* 339 */           if (toOcgsToCopy.contains(toRbGroupItemObj.getIndirectReference())) {
/* 340 */             toArray.remove(i);
/*     */             
/*     */             break;
/*     */           } 
/*     */         } 
/*     */       } 
/*     */       
/* 347 */       for (PdfObject fromRbGroupObj : fromArray) {
/* 348 */         PdfArray fromRbGroup = (PdfArray)fromRbGroupObj;
/* 349 */         for (PdfObject fromRbGroupItemObj : fromRbGroup) {
/* 350 */           if (fromOcgsToCopy.contains(fromRbGroupItemObj.getIndirectReference())) {
/* 351 */             toArray.add(fromRbGroup.copyTo(toDocument, false));
/*     */           }
/*     */         }
/*     */       
/*     */       }
/*     */     
/*     */     }
/*     */     else {
/*     */       
/* 360 */       for (PdfObject fromObj : fromArray) {
/* 361 */         attemptToAddObjectToArray(fromOcgsToCopy, fromObj, toArray, toDocument);
/*     */       }
/*     */     } 
/*     */     
/* 365 */     if (toArray.isEmpty()) {
/* 366 */       toDict.remove(fieldToCopy);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private static boolean orderBranchContainsSetElements(PdfObject arrayObj, PdfArray array, int currentIndex, Set<PdfIndirectReference> ocgs, PdfArray toOcgs, PdfDocument toDocument) {
/* 372 */     if (arrayObj.isDictionary()) {
/* 373 */       if (ocgs.contains(arrayObj.getIndirectReference())) {
/* 374 */         return true;
/*     */       }
/* 376 */       if (currentIndex < array.size() - 1 && array.get(currentIndex + 1).isArray()) {
/* 377 */         PdfArray nextArray = array.getAsArray(currentIndex + 1);
/* 378 */         if (!nextArray.get(0).isString()) {
/* 379 */           boolean result = orderBranchContainsSetElements(nextArray, array, currentIndex + 1, ocgs, toOcgs, toDocument);
/*     */           
/* 381 */           if (result && toOcgs != null && !ocgs.contains(arrayObj.getIndirectReference()))
/*     */           {
/*     */ 
/*     */             
/* 385 */             toOcgs.add(arrayObj.copyTo(toDocument, false));
/*     */           }
/*     */           
/* 388 */           return result;
/*     */         }
/*     */       
/*     */       } 
/* 392 */     } else if (arrayObj.isArray()) {
/* 393 */       PdfArray arrayItem = (PdfArray)arrayObj;
/* 394 */       for (int i = 0; i < arrayItem.size(); i++) {
/* 395 */         PdfObject obj = arrayItem.get(i);
/* 396 */         if (orderBranchContainsSetElements(obj, arrayItem, i, ocgs, toOcgs, toDocument)) {
/* 397 */           return true;
/*     */         }
/*     */       } 
/*     */       
/* 401 */       if (!arrayItem.isEmpty() && !arrayItem.get(0).isString() && 
/* 402 */         currentIndex > 0 && array.get(currentIndex - 1).isDictionary()) {
/* 403 */         PdfDictionary previousDict = (PdfDictionary)array.get(currentIndex - 1);
/* 404 */         return ocgs.contains(previousDict.getIndirectReference());
/*     */       } 
/*     */     } 
/*     */     
/* 408 */     return false;
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/kernel/pdf/OcgPropertiesCopier.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */