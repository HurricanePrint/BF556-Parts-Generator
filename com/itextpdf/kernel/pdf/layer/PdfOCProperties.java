/*     */ package com.itextpdf.kernel.pdf.layer;
/*     */ 
/*     */ import com.itextpdf.kernel.pdf.PdfArray;
/*     */ import com.itextpdf.kernel.pdf.PdfDictionary;
/*     */ import com.itextpdf.kernel.pdf.PdfDocument;
/*     */ import com.itextpdf.kernel.pdf.PdfIndirectReference;
/*     */ import com.itextpdf.kernel.pdf.PdfName;
/*     */ import com.itextpdf.kernel.pdf.PdfObject;
/*     */ import com.itextpdf.kernel.pdf.PdfObjectWrapper;
/*     */ import com.itextpdf.kernel.pdf.PdfString;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashSet;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import java.util.TreeMap;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class PdfOCProperties
/*     */   extends PdfObjectWrapper<PdfDictionary>
/*     */ {
/*     */   static final String OC_CONFIG_NAME_PATTERN = "OCConfigName";
/*     */   private static final long serialVersionUID = 1137977454824741350L;
/*  74 */   private List<PdfLayer> layers = new ArrayList<>();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PdfOCProperties(PdfDocument document) {
/*  82 */     this((PdfDictionary)(new PdfDictionary()).makeIndirect(document));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PdfOCProperties(PdfDictionary ocPropertiesDict) {
/*  92 */     super((PdfObject)ocPropertiesDict);
/*  93 */     ensureObjectIsAddedToDocument((PdfObject)ocPropertiesDict);
/*  94 */     readLayersFromDictionary();
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
/*     */   public void addOCGRadioGroup(List<PdfLayer> group) {
/* 107 */     PdfArray ar = new PdfArray();
/* 108 */     for (PdfLayer layer : group) {
/* 109 */       if (layer.getTitle() == null)
/* 110 */         ar.add((PdfObject)((PdfDictionary)layer.getPdfObject()).getIndirectReference()); 
/*     */     } 
/* 112 */     if (ar.size() != 0) {
/* 113 */       PdfDictionary d = ((PdfDictionary)getPdfObject()).getAsDictionary(PdfName.D);
/* 114 */       if (d == null) {
/* 115 */         d = new PdfDictionary();
/* 116 */         ((PdfDictionary)getPdfObject()).put(PdfName.D, (PdfObject)d);
/*     */       } 
/* 118 */       PdfArray radioButtonGroups = d.getAsArray(PdfName.RBGroups);
/* 119 */       if (radioButtonGroups == null) {
/* 120 */         radioButtonGroups = new PdfArray();
/* 121 */         d.put(PdfName.RBGroups, (PdfObject)radioButtonGroups);
/* 122 */         d.setModified();
/*     */       } else {
/* 124 */         radioButtonGroups.setModified();
/*     */       } 
/* 126 */       radioButtonGroups.add((PdfObject)ar);
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
/*     */   public PdfObject fillDictionary() {
/* 138 */     return fillDictionary(true);
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
/*     */   public PdfObject fillDictionary(boolean removeNonDocumentOcgs) {
/* 151 */     PdfArray gr = new PdfArray();
/* 152 */     for (PdfLayer layer : this.layers) {
/* 153 */       if (layer.getTitle() == null)
/* 154 */         gr.add((PdfObject)layer.getIndirectReference()); 
/*     */     } 
/* 156 */     ((PdfDictionary)getPdfObject()).put(PdfName.OCGs, (PdfObject)gr);
/*     */ 
/*     */     
/* 159 */     PdfArray rbGroups = null;
/* 160 */     PdfDictionary d = ((PdfDictionary)getPdfObject()).getAsDictionary(PdfName.D);
/* 161 */     if (d != null) {
/* 162 */       rbGroups = d.getAsArray(PdfName.RBGroups);
/*     */     }
/*     */     
/* 165 */     d = new PdfDictionary();
/* 166 */     if (rbGroups != null) {
/* 167 */       d.put(PdfName.RBGroups, (PdfObject)rbGroups);
/*     */     }
/* 169 */     d.put(PdfName.Name, (PdfObject)new PdfString(createUniqueName(), "UnicodeBig"));
/*     */     
/* 171 */     ((PdfDictionary)getPdfObject()).put(PdfName.D, (PdfObject)d);
/*     */ 
/*     */     
/* 174 */     List<PdfLayer> docOrder = new ArrayList<>(this.layers);
/* 175 */     for (int i = 0; i < docOrder.size(); i++) {
/* 176 */       PdfLayer layer = docOrder.get(i);
/* 177 */       if (layer.getParent() != null) {
/* 178 */         docOrder.remove(layer);
/* 179 */         i--;
/*     */       } 
/*     */     } 
/*     */     
/* 183 */     PdfArray order = new PdfArray();
/* 184 */     for (PdfLayer element : docOrder) {
/* 185 */       PdfLayer layer = element;
/* 186 */       getOCGOrder(order, layer);
/*     */     } 
/* 188 */     d.put(PdfName.Order, (PdfObject)order);
/*     */     
/* 190 */     PdfArray off = new PdfArray();
/* 191 */     for (PdfLayer element : this.layers) {
/* 192 */       PdfLayer layer = element;
/* 193 */       if (layer.getTitle() == null && !layer.isOn())
/* 194 */         off.add((PdfObject)layer.getIndirectReference()); 
/*     */     } 
/* 196 */     if (off.size() > 0) {
/* 197 */       d.put(PdfName.OFF, (PdfObject)off);
/*     */     } else {
/* 199 */       d.remove(PdfName.OFF);
/*     */     } 
/* 201 */     PdfArray locked = new PdfArray();
/* 202 */     for (PdfLayer layer : this.layers) {
/* 203 */       if (layer.getTitle() == null && layer.isLocked())
/* 204 */         locked.add((PdfObject)layer.getIndirectReference()); 
/*     */     } 
/* 206 */     if (locked.size() > 0) {
/* 207 */       d.put(PdfName.Locked, (PdfObject)locked);
/*     */     } else {
/* 209 */       d.remove(PdfName.Locked);
/*     */     } 
/* 211 */     d.remove(PdfName.AS);
/* 212 */     addASEvent(PdfName.View, PdfName.Zoom);
/* 213 */     addASEvent(PdfName.View, PdfName.View);
/* 214 */     addASEvent(PdfName.Print, PdfName.Print);
/* 215 */     addASEvent(PdfName.Export, PdfName.Export);
/*     */     
/* 217 */     if (removeNonDocumentOcgs) {
/* 218 */       removeNotRegisteredOcgs();
/*     */     }
/*     */     
/* 221 */     return getPdfObject();
/*     */   }
/*     */ 
/*     */   
/*     */   public void flush() {
/* 226 */     fillDictionary();
/* 227 */     super.flush();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List<PdfLayer> getLayers() {
/* 237 */     return new ArrayList<>(this.layers);
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean isWrappedObjectMustBeIndirect() {
/* 242 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void registerLayer(PdfLayer layer) {
/* 252 */     if (layer == null)
/* 253 */       throw new IllegalArgumentException("layer argument is null"); 
/* 254 */     this.layers.add(layer);
/*     */   }
/*     */   
/*     */   protected PdfDocument getDocument() {
/* 258 */     return ((PdfDictionary)getPdfObject()).getIndirectReference().getDocument();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void getOCGOrder(PdfArray order, PdfLayer layer) {
/* 266 */     if (!layer.isOnPanel())
/*     */       return; 
/* 268 */     if (layer.getTitle() == null)
/* 269 */       order.add((PdfObject)((PdfDictionary)layer.getPdfObject()).getIndirectReference()); 
/* 270 */     List<PdfLayer> children = layer.getChildren();
/* 271 */     if (children == null)
/*     */       return; 
/* 273 */     PdfArray kids = new PdfArray();
/* 274 */     if (layer.getTitle() != null)
/* 275 */       kids.add((PdfObject)new PdfString(layer.getTitle(), "UnicodeBig")); 
/* 276 */     for (PdfLayer child : children) {
/* 277 */       getOCGOrder(kids, child);
/*     */     }
/* 279 */     if (kids.size() > 0)
/* 280 */       order.add((PdfObject)kids); 
/*     */   }
/*     */   
/*     */   private void removeNotRegisteredOcgs() {
/* 284 */     PdfDictionary dDict = ((PdfDictionary)getPdfObject()).getAsDictionary(PdfName.D);
/*     */     
/* 286 */     PdfDictionary ocProperties = ((PdfDictionary)getDocument().getCatalog().getPdfObject()).getAsDictionary(PdfName.OCProperties);
/* 287 */     Set<PdfIndirectReference> ocgsFromDocument = new HashSet<>();
/* 288 */     if (ocProperties.getAsArray(PdfName.OCGs) != null) {
/* 289 */       PdfArray ocgs = ocProperties.getAsArray(PdfName.OCGs);
/* 290 */       for (PdfObject ocgObj : ocgs) {
/* 291 */         if (ocgObj.isDictionary()) {
/* 292 */           ocgsFromDocument.add(ocgObj.getIndirectReference());
/*     */         }
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 298 */     PdfArray rbGroups = dDict.getAsArray(PdfName.RBGroups);
/* 299 */     if (rbGroups != null) {
/* 300 */       for (PdfObject rbGroupObj : rbGroups) {
/* 301 */         PdfArray rbGroup = (PdfArray)rbGroupObj;
/* 302 */         for (int i = rbGroup.size() - 1; i > -1; i--) {
/* 303 */           if (!ocgsFromDocument.contains(rbGroup.get(i).getIndirectReference())) {
/* 304 */             rbGroup.remove(i);
/*     */           }
/*     */         } 
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void addASEvent(PdfName event, PdfName category) {
/* 315 */     PdfArray arr = new PdfArray();
/* 316 */     for (PdfLayer layer : this.layers) {
/* 317 */       if (layer.getTitle() == null && !((PdfDictionary)layer.getPdfObject()).isFlushed()) {
/* 318 */         PdfDictionary usage = ((PdfDictionary)layer.getPdfObject()).getAsDictionary(PdfName.Usage);
/* 319 */         if (usage != null && usage.get(category) != null)
/* 320 */           arr.add((PdfObject)((PdfDictionary)layer.getPdfObject()).getIndirectReference()); 
/*     */       } 
/*     */     } 
/* 323 */     if (arr.size() == 0)
/*     */       return; 
/* 325 */     PdfDictionary d = ((PdfDictionary)getPdfObject()).getAsDictionary(PdfName.D);
/* 326 */     PdfArray arras = d.getAsArray(PdfName.AS);
/* 327 */     if (arras == null) {
/* 328 */       arras = new PdfArray();
/* 329 */       d.put(PdfName.AS, (PdfObject)arras);
/*     */     } 
/* 331 */     PdfDictionary as = new PdfDictionary();
/* 332 */     as.put(PdfName.Event, (PdfObject)event);
/* 333 */     PdfArray categoryArray = new PdfArray();
/* 334 */     categoryArray.add((PdfObject)category);
/* 335 */     as.put(PdfName.Category, (PdfObject)categoryArray);
/* 336 */     as.put(PdfName.OCGs, (PdfObject)arr);
/* 337 */     arras.add((PdfObject)as);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void readLayersFromDictionary() {
/* 344 */     PdfArray ocgs = ((PdfDictionary)getPdfObject()).getAsArray(PdfName.OCGs);
/* 345 */     if (ocgs == null || ocgs.isEmpty()) {
/*     */       return;
/*     */     }
/* 348 */     Map<PdfIndirectReference, PdfLayer> layerMap = new TreeMap<>();
/* 349 */     for (int ind = 0; ind < ocgs.size(); ind++) {
/* 350 */       PdfLayer currentLayer = new PdfLayer((PdfDictionary)ocgs.getAsDictionary(ind).makeIndirect(getDocument()));
/*     */       
/* 352 */       currentLayer.onPanel = false;
/* 353 */       layerMap.put(currentLayer.getIndirectReference(), currentLayer);
/*     */     } 
/*     */     
/* 356 */     PdfDictionary d = ((PdfDictionary)getPdfObject()).getAsDictionary(PdfName.D);
/* 357 */     if (d != null && !d.isEmpty()) {
/* 358 */       PdfArray off = d.getAsArray(PdfName.OFF);
/* 359 */       if (off != null) {
/* 360 */         for (int i = 0; i < off.size(); i++) {
/* 361 */           PdfObject offLayer = off.get(i, false);
/* 362 */           if (offLayer.isIndirectReference()) {
/* 363 */             ((PdfLayer)layerMap.get(offLayer)).on = false;
/*     */           } else {
/* 365 */             ((PdfLayer)layerMap.get(offLayer.getIndirectReference())).on = false;
/*     */           } 
/*     */         } 
/*     */       }
/*     */       
/* 370 */       PdfArray locked = d.getAsArray(PdfName.Locked);
/* 371 */       if (locked != null) {
/* 372 */         for (int i = 0; i < locked.size(); i++) {
/* 373 */           PdfObject lockedLayer = locked.get(i, false);
/* 374 */           if (lockedLayer.isIndirectReference()) {
/* 375 */             ((PdfLayer)layerMap.get(lockedLayer)).locked = true;
/*     */           } else {
/* 377 */             ((PdfLayer)layerMap.get(lockedLayer.getIndirectReference())).locked = true;
/*     */           } 
/*     */         } 
/*     */       }
/*     */       
/* 382 */       PdfArray orderArray = d.getAsArray(PdfName.Order);
/* 383 */       if (orderArray != null && !orderArray.isEmpty()) {
/* 384 */         readOrderFromDictionary((PdfLayer)null, orderArray, layerMap);
/*     */       }
/*     */     } 
/*     */     
/* 388 */     for (PdfLayer layer : layerMap.values()) {
/* 389 */       if (!layer.isOnPanel()) {
/* 390 */         this.layers.add(layer);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void readOrderFromDictionary(PdfLayer parent, PdfArray orderArray, Map<PdfIndirectReference, PdfLayer> layerMap) {
/* 398 */     for (int i = 0; i < orderArray.size(); i++) {
/* 399 */       PdfObject item = orderArray.get(i);
/* 400 */       if (item.getType() == 3) {
/* 401 */         PdfLayer layer = layerMap.get(item.getIndirectReference());
/* 402 */         if (layer != null) {
/* 403 */           this.layers.add(layer);
/* 404 */           layer.onPanel = true;
/* 405 */           if (parent != null)
/* 406 */             parent.addChild(layer); 
/* 407 */           if (i + 1 < orderArray.size() && orderArray.get(i + 1).getType() == 1) {
/* 408 */             PdfArray nextArray = orderArray.getAsArray(i + 1);
/* 409 */             if (nextArray.size() > 0 && nextArray.get(0).getType() != 10) {
/* 410 */               readOrderFromDictionary(layer, orderArray.getAsArray(i + 1), layerMap);
/* 411 */               i++;
/*     */             } 
/*     */           } 
/*     */         } 
/* 415 */       } else if (item.getType() == 1) {
/* 416 */         PdfArray subArray = (PdfArray)item;
/* 417 */         if (!subArray.isEmpty()) {
/* 418 */           PdfObject firstObj = subArray.get(0);
/* 419 */           if (firstObj.getType() == 10) {
/* 420 */             PdfLayer titleLayer = PdfLayer.createTitleSilent(((PdfString)firstObj).toUnicodeString(), getDocument());
/* 421 */             titleLayer.onPanel = true;
/* 422 */             this.layers.add(titleLayer);
/* 423 */             if (parent != null)
/* 424 */               parent.addChild(titleLayer); 
/* 425 */             readOrderFromDictionary(titleLayer, new PdfArray(subArray.subList(1, subArray.size())), layerMap);
/*     */           } else {
/* 427 */             readOrderFromDictionary(parent, subArray, layerMap);
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */   private String createUniqueName() {
/* 434 */     int uniqueID = 0;
/* 435 */     Set<String> usedNames = new HashSet<>();
/* 436 */     PdfArray configs = ((PdfDictionary)getPdfObject()).getAsArray(PdfName.Configs);
/* 437 */     if (null != configs) {
/* 438 */       for (int i = 0; i < configs.size(); i++) {
/* 439 */         PdfDictionary alternateDictionary = configs.getAsDictionary(i);
/* 440 */         if (null != alternateDictionary && alternateDictionary.containsKey(PdfName.Name)) {
/* 441 */           usedNames.add(alternateDictionary.getAsString(PdfName.Name).toUnicodeString());
/*     */         }
/*     */       } 
/*     */     }
/* 445 */     while (usedNames.contains("OCConfigName" + uniqueID)) {
/* 446 */       uniqueID++;
/*     */     }
/* 448 */     return "OCConfigName" + uniqueID;
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/kernel/pdf/layer/PdfOCProperties.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */