/*     */ package com.itextpdf.kernel.pdf.layer;
/*     */ 
/*     */ import com.itextpdf.kernel.pdf.PdfArray;
/*     */ import com.itextpdf.kernel.pdf.PdfDictionary;
/*     */ import com.itextpdf.kernel.pdf.PdfDocument;
/*     */ import com.itextpdf.kernel.pdf.PdfIndirectReference;
/*     */ import com.itextpdf.kernel.pdf.PdfName;
/*     */ import com.itextpdf.kernel.pdf.PdfNumber;
/*     */ import com.itextpdf.kernel.pdf.PdfObject;
/*     */ import com.itextpdf.kernel.pdf.PdfObjectWrapper;
/*     */ import com.itextpdf.kernel.pdf.PdfString;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class PdfLayer
/*     */   extends PdfObjectWrapper<PdfDictionary>
/*     */   implements IPdfOCG
/*     */ {
/*     */   private static final long serialVersionUID = -5367953708241595665L;
/*     */   protected String title;
/*     */   protected boolean on = true;
/*     */   protected boolean onPanel = true;
/*     */   protected boolean locked = false;
/*     */   protected PdfLayer parent;
/*     */   protected List<PdfLayer> children;
/*     */   
/*     */   public PdfLayer(PdfDictionary layerDictionary) {
/*  90 */     super((PdfObject)layerDictionary);
/*  91 */     setForbidRelease();
/*  92 */     ensureObjectIsAddedToDocument((PdfObject)layerDictionary);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PdfLayer(String name, PdfDocument document) {
/* 101 */     this(document);
/* 102 */     setName(name);
/* 103 */     document.getCatalog().getOCProperties(true).registerLayer(this);
/*     */   }
/*     */   
/*     */   private PdfLayer(PdfDocument document) {
/* 107 */     super((PdfObject)new PdfDictionary());
/* 108 */     makeIndirect(document);
/* 109 */     ((PdfDictionary)getPdfObject()).put(PdfName.Type, (PdfObject)PdfName.OCG);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static PdfLayer createTitle(String title, PdfDocument document) {
/* 120 */     PdfLayer layer = createTitleSilent(title, document);
/* 121 */     document.getCatalog().getOCProperties(true).registerLayer(layer);
/* 122 */     return layer;
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
/*     */   public static void addOCGRadioGroup(PdfDocument document, List<PdfLayer> group) {
/* 135 */     document.getCatalog().getOCProperties(true).addOCGRadioGroup(group);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addChild(PdfLayer childLayer) {
/* 143 */     if (childLayer.parent != null)
/* 144 */       throw new IllegalArgumentException("Illegal argument: childLayer"); 
/* 145 */     childLayer.parent = this;
/* 146 */     if (this.children == null)
/* 147 */       this.children = new ArrayList<>(); 
/* 148 */     this.children.add(childLayer);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PdfLayer getParent() {
/* 156 */     return this.parent;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setName(String name) {
/* 164 */     ((PdfDictionary)getPdfObject()).put(PdfName.Name, (PdfObject)new PdfString(name, "UnicodeBig"));
/* 165 */     ((PdfDictionary)getPdfObject()).setModified();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isOn() {
/* 173 */     return this.on;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setOn(boolean on) {
/* 181 */     if (this.on != on)
/* 182 */       fetchOCProperties().setModified(); 
/* 183 */     this.on = on;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isLocked() {
/* 192 */     return this.locked;
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
/*     */   public void setLocked(boolean locked) {
/* 204 */     if (isLocked() != locked)
/* 205 */       fetchOCProperties().setModified(); 
/* 206 */     this.locked = locked;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isOnPanel() {
/* 214 */     return this.onPanel;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setOnPanel(boolean onPanel) {
/* 224 */     if (this.on != onPanel)
/* 225 */       fetchOCProperties().setModified(); 
/* 226 */     this.onPanel = onPanel;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Collection<PdfName> getIntents() {
/* 236 */     PdfObject intent = ((PdfDictionary)getPdfObject()).get(PdfName.Intent);
/* 237 */     if (intent instanceof PdfName)
/* 238 */       return Collections.singletonList((PdfName)intent); 
/* 239 */     if (intent instanceof PdfArray) {
/* 240 */       PdfArray intentArr = (PdfArray)intent;
/* 241 */       Collection<PdfName> intentsCollection = new ArrayList<>(intentArr.size());
/* 242 */       for (PdfObject i : intentArr) {
/* 243 */         if (i instanceof PdfName) {
/* 244 */           intentsCollection.add((PdfName)i);
/*     */         }
/*     */       } 
/* 247 */       return intentsCollection;
/*     */     } 
/* 249 */     return Collections.singletonList(PdfName.View);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setIntents(List<PdfName> intents) {
/* 257 */     if (intents == null || intents.size() == 0) {
/* 258 */       ((PdfDictionary)getPdfObject()).remove(PdfName.Intent);
/* 259 */     } else if (intents.size() == 1) {
/* 260 */       ((PdfDictionary)getPdfObject()).put(PdfName.Intent, (PdfObject)intents.get(0));
/*     */     }
/*     */     else {
/*     */       
/* 264 */       PdfArray array = new PdfArray();
/* 265 */       for (PdfName intent : intents) {
/* 266 */         array.add((PdfObject)intent);
/*     */       }
/* 268 */       ((PdfDictionary)getPdfObject()).put(PdfName.Intent, (PdfObject)array);
/*     */     } 
/* 270 */     ((PdfDictionary)getPdfObject()).setModified();
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
/*     */   public void setCreatorInfo(String creator, String subtype) {
/* 283 */     PdfDictionary usage = getUsage();
/* 284 */     PdfDictionary dic = new PdfDictionary();
/* 285 */     dic.put(PdfName.Creator, (PdfObject)new PdfString(creator, "UnicodeBig"));
/* 286 */     dic.put(PdfName.Subtype, (PdfObject)new PdfName(subtype));
/* 287 */     usage.put(PdfName.CreatorInfo, (PdfObject)dic);
/* 288 */     usage.setModified();
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
/*     */   public void setLanguage(String lang, boolean preferred) {
/* 300 */     PdfDictionary usage = getUsage();
/* 301 */     PdfDictionary dic = new PdfDictionary();
/* 302 */     dic.put(PdfName.Lang, (PdfObject)new PdfString(lang, "UnicodeBig"));
/* 303 */     if (preferred)
/* 304 */       dic.put(PdfName.Preferred, (PdfObject)PdfName.ON); 
/* 305 */     usage.put(PdfName.Language, (PdfObject)dic);
/* 306 */     usage.setModified();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setExport(boolean export) {
/* 317 */     PdfDictionary usage = getUsage();
/* 318 */     PdfDictionary dic = new PdfDictionary();
/* 319 */     dic.put(PdfName.ExportState, export ? (PdfObject)PdfName.ON : (PdfObject)PdfName.OFF);
/* 320 */     usage.put(PdfName.Export, (PdfObject)dic);
/* 321 */     usage.setModified();
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
/*     */   public void setZoom(float min, float max) {
/* 334 */     if (min <= 0.0F && max < 0.0F)
/*     */       return; 
/* 336 */     PdfDictionary usage = getUsage();
/* 337 */     PdfDictionary dic = new PdfDictionary();
/* 338 */     if (min > 0.0F)
/* 339 */       dic.put(PdfName.min, (PdfObject)new PdfNumber(min)); 
/* 340 */     if (max >= 0.0F)
/* 341 */       dic.put(PdfName.max, (PdfObject)new PdfNumber(max)); 
/* 342 */     usage.put(PdfName.Zoom, (PdfObject)dic);
/* 343 */     usage.setModified();
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
/*     */   public void setPrint(String subtype, boolean printState) {
/* 355 */     PdfDictionary usage = getUsage();
/* 356 */     PdfDictionary dic = new PdfDictionary();
/* 357 */     dic.put(PdfName.Subtype, (PdfObject)new PdfName(subtype));
/* 358 */     dic.put(PdfName.PrintState, printState ? (PdfObject)PdfName.ON : (PdfObject)PdfName.OFF);
/* 359 */     usage.put(PdfName.Print, (PdfObject)dic);
/* 360 */     usage.setModified();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setView(boolean view) {
/* 369 */     PdfDictionary usage = getUsage();
/* 370 */     PdfDictionary dic = new PdfDictionary();
/* 371 */     dic.put(PdfName.ViewState, view ? (PdfObject)PdfName.ON : (PdfObject)PdfName.OFF);
/* 372 */     usage.put(PdfName.View, (PdfObject)dic);
/* 373 */     usage.setModified();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setUser(String type, String... names) {
/* 384 */     if (type == null || (!"Ind".equals(type) && !"Ttl".equals(type) && !"Org".equals(type)))
/* 385 */       throw new IllegalArgumentException("Illegal type argument"); 
/* 386 */     if (names == null || names.length == 0)
/* 387 */       throw new IllegalArgumentException("Illegal names argument"); 
/* 388 */     PdfDictionary usage = getUsage();
/* 389 */     PdfDictionary dic = new PdfDictionary();
/* 390 */     dic.put(PdfName.Type, (PdfObject)new PdfName(type));
/* 391 */     if (names.length == 1) {
/* 392 */       dic.put(PdfName.Name, (PdfObject)new PdfString(names[0], "UnicodeBig"));
/*     */     } else {
/* 394 */       PdfArray namesArray = new PdfArray();
/* 395 */       for (String name : names) {
/* 396 */         namesArray.add((PdfObject)new PdfString(name, "UnicodeBig"));
/*     */       }
/* 398 */       dic.put(PdfName.Name, (PdfObject)namesArray);
/*     */     } 
/* 400 */     usage.put(PdfName.User, (PdfObject)dic);
/* 401 */     usage.setModified();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setPageElement(String pe) {
/* 410 */     PdfDictionary usage = getUsage();
/* 411 */     PdfDictionary dic = new PdfDictionary();
/* 412 */     dic.put(PdfName.Subtype, (PdfObject)new PdfName(pe));
/* 413 */     usage.put(PdfName.PageElement, (PdfObject)dic);
/* 414 */     usage.setModified();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PdfIndirectReference getIndirectReference() {
/* 422 */     return ((PdfDictionary)getPdfObject()).getIndirectReference();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getTitle() {
/* 431 */     return this.title;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List<PdfLayer> getChildren() {
/* 441 */     return (this.children == null) ? null : new ArrayList<>(this.children);
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean isWrappedObjectMustBeIndirect() {
/* 446 */     return true;
/*     */   }
/*     */   
/*     */   protected PdfDocument getDocument() {
/* 450 */     return ((PdfDictionary)getPdfObject()).getIndirectReference().getDocument();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected static PdfLayer createTitleSilent(String title, PdfDocument document) {
/* 460 */     if (title == null)
/* 461 */       throw new IllegalArgumentException("Invalid title argument"); 
/* 462 */     PdfLayer layer = new PdfLayer(document);
/* 463 */     layer.title = title;
/* 464 */     return layer;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected PdfDictionary getUsage() {
/* 472 */     PdfDictionary usage = ((PdfDictionary)getPdfObject()).getAsDictionary(PdfName.Usage);
/* 473 */     if (usage == null) {
/* 474 */       usage = new PdfDictionary();
/* 475 */       ((PdfDictionary)getPdfObject()).put(PdfName.Usage, (PdfObject)usage);
/*     */     } 
/* 477 */     return usage;
/*     */   }
/*     */   
/*     */   private PdfOCProperties fetchOCProperties() {
/* 481 */     return getDocument().getCatalog().getOCProperties(true);
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/kernel/pdf/layer/PdfLayer.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */