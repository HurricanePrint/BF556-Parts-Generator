/*     */ package com.itextpdf.kernel.pdf;
/*     */ 
/*     */ import com.itextpdf.kernel.font.PdfFont;
/*     */ import com.itextpdf.kernel.pdf.colorspace.PdfColorSpace;
/*     */ import com.itextpdf.kernel.pdf.colorspace.PdfPattern;
/*     */ import com.itextpdf.kernel.pdf.colorspace.PdfShading;
/*     */ import com.itextpdf.kernel.pdf.extgstate.PdfExtGState;
/*     */ import com.itextpdf.kernel.pdf.xobject.PdfFormXObject;
/*     */ import com.itextpdf.kernel.pdf.xobject.PdfImageXObject;
/*     */ import java.io.Serializable;
/*     */ import java.util.Collections;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import java.util.TreeSet;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class PdfResources
/*     */   extends PdfObjectWrapper<PdfDictionary>
/*     */ {
/*     */   private static final long serialVersionUID = 7160318458835945391L;
/*     */   private static final String F = "F";
/*     */   private static final String Im = "Im";
/*     */   private static final String Fm = "Fm";
/*     */   private static final String Gs = "Gs";
/*     */   private static final String Pr = "Pr";
/*     */   private static final String Cs = "Cs";
/*     */   private static final String P = "P";
/*     */   private static final String Sh = "Sh";
/*  79 */   private Map<PdfObject, PdfName> resourceToName = new HashMap<>();
/*     */   
/*  81 */   private ResourceNameGenerator fontNamesGen = new ResourceNameGenerator(PdfName.Font, "F");
/*  82 */   private ResourceNameGenerator imageNamesGen = new ResourceNameGenerator(PdfName.XObject, "Im");
/*  83 */   private ResourceNameGenerator formNamesGen = new ResourceNameGenerator(PdfName.XObject, "Fm");
/*  84 */   private ResourceNameGenerator egsNamesGen = new ResourceNameGenerator(PdfName.ExtGState, "Gs");
/*  85 */   private ResourceNameGenerator propNamesGen = new ResourceNameGenerator(PdfName.Properties, "Pr");
/*  86 */   private ResourceNameGenerator csNamesGen = new ResourceNameGenerator(PdfName.ColorSpace, "Cs");
/*  87 */   private ResourceNameGenerator patternNamesGen = new ResourceNameGenerator(PdfName.Pattern, "P");
/*  88 */   private ResourceNameGenerator shadingNamesGen = new ResourceNameGenerator(PdfName.Shading, "Sh");
/*     */ 
/*     */   
/*     */   private boolean readOnly = false;
/*     */ 
/*     */   
/*     */   private boolean isModified = false;
/*     */ 
/*     */ 
/*     */   
/*     */   public PdfResources(PdfDictionary pdfObject) {
/*  99 */     super(pdfObject);
/* 100 */     buildResources(pdfObject);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PdfResources() {
/* 107 */     this(new PdfDictionary());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PdfName addFont(PdfDocument pdfDocument, PdfFont font) {
/* 118 */     pdfDocument.addFont(font);
/* 119 */     return addResource((PdfObjectWrapper<PdfObject>)font, this.fontNamesGen);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PdfName addImage(PdfImageXObject image) {
/* 129 */     return addResource((PdfObjectWrapper<PdfObject>)image, this.imageNamesGen);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PdfName addImage(PdfStream image) {
/* 139 */     return addResource(image, this.imageNamesGen);
/*     */   }
/*     */   
/*     */   public PdfImageXObject getImage(PdfName name) {
/* 143 */     PdfStream image = getResource(PdfName.XObject).getAsStream(name);
/* 144 */     return (image != null && PdfName.Image.equals(image.getAsName(PdfName.Subtype))) ? new PdfImageXObject(image) : null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PdfName addForm(PdfFormXObject form) {
/* 154 */     return addResource((PdfObjectWrapper<PdfObject>)form, this.formNamesGen);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PdfName addForm(PdfStream form) {
/* 164 */     return addResource(form, this.formNamesGen);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PdfName addForm(PdfFormXObject form, PdfName name) {
/* 175 */     if (getResourceNames(PdfName.XObject).contains(name)) {
/* 176 */       name = addResource((PdfObjectWrapper<PdfObject>)form, this.formNamesGen);
/*     */     } else {
/* 178 */       addResource(form.getPdfObject(), PdfName.XObject, name);
/*     */     } 
/*     */     
/* 181 */     return name;
/*     */   }
/*     */   
/*     */   public PdfFormXObject getForm(PdfName name) {
/* 185 */     PdfStream form = getResource(PdfName.XObject).getAsStream(name);
/* 186 */     return (form != null && PdfName.Form.equals(form.getAsName(PdfName.Subtype))) ? new PdfFormXObject(form) : null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PdfName addExtGState(PdfExtGState extGState) {
/* 196 */     return addResource((PdfObjectWrapper<PdfObject>)extGState, this.egsNamesGen);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PdfName addExtGState(PdfDictionary extGState) {
/* 206 */     return addResource(extGState, this.egsNamesGen);
/*     */   }
/*     */   
/*     */   public PdfExtGState getPdfExtGState(PdfName name) {
/* 210 */     PdfDictionary dic = getResource(PdfName.ExtGState).getAsDictionary(name);
/* 211 */     return (dic != null) ? new PdfExtGState(dic) : null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PdfName addProperties(PdfDictionary properties) {
/* 221 */     return addResource(properties, this.propNamesGen);
/*     */   }
/*     */   
/*     */   public PdfObject getProperties(PdfName name) {
/* 225 */     return getResourceObject(PdfName.Properties, name);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PdfName addColorSpace(PdfColorSpace cs) {
/* 235 */     return addResource((PdfObjectWrapper<PdfObject>)cs, this.csNamesGen);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PdfName addColorSpace(PdfObject colorSpace) {
/* 245 */     return addResource(colorSpace, this.csNamesGen);
/*     */   }
/*     */   
/*     */   public PdfColorSpace getColorSpace(PdfName name) {
/* 249 */     PdfObject colorSpace = getResourceObject(PdfName.ColorSpace, name);
/* 250 */     return (colorSpace != null) ? PdfColorSpace.makeColorSpace(colorSpace) : null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PdfName addPattern(PdfPattern pattern) {
/* 260 */     return addResource((PdfObjectWrapper<PdfObject>)pattern, this.patternNamesGen);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PdfName addPattern(PdfDictionary pattern) {
/* 270 */     return addResource(pattern, this.patternNamesGen);
/*     */   }
/*     */   
/*     */   public PdfPattern getPattern(PdfName name) {
/* 274 */     PdfObject pattern = getResourceObject(PdfName.Pattern, name);
/* 275 */     return (pattern instanceof PdfDictionary) ? PdfPattern.getPatternInstance((PdfDictionary)pattern) : null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PdfName addShading(PdfShading shading) {
/* 285 */     return addResource((PdfObjectWrapper<PdfObject>)shading, this.shadingNamesGen);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PdfName addShading(PdfDictionary shading) {
/* 295 */     return addResource(shading, this.shadingNamesGen);
/*     */   }
/*     */   
/*     */   public PdfShading getShading(PdfName name) {
/* 299 */     PdfObject shading = getResourceObject(PdfName.Shading, name);
/* 300 */     return (shading instanceof PdfDictionary) ? PdfShading.makeShading((PdfDictionary)shading) : null;
/*     */   }
/*     */   
/*     */   protected boolean isReadOnly() {
/* 304 */     return this.readOnly;
/*     */   }
/*     */   
/*     */   protected void setReadOnly(boolean readOnly) {
/* 308 */     this.readOnly = readOnly;
/*     */   }
/*     */   
/*     */   protected boolean isModified() {
/* 312 */     return this.isModified;
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
/*     */   @Deprecated
/*     */   protected void setModified(boolean isModified) {
/* 326 */     this.isModified = isModified;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PdfObjectWrapper<PdfDictionary> setModified() {
/* 334 */     this.isModified = true;
/* 335 */     return super.setModified();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setDefaultGray(PdfColorSpace defaultCs) {
/* 344 */     addResource(defaultCs.getPdfObject(), PdfName.ColorSpace, PdfName.DefaultGray);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setDefaultRgb(PdfColorSpace defaultCs) {
/* 353 */     addResource(defaultCs.getPdfObject(), PdfName.ColorSpace, PdfName.DefaultRGB);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setDefaultCmyk(PdfColorSpace defaultCs) {
/* 362 */     addResource(defaultCs.getPdfObject(), PdfName.ColorSpace, PdfName.DefaultCMYK);
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
/*     */   public <T extends PdfObject> PdfName getResourceName(PdfObjectWrapper<T> resource) {
/* 376 */     return getResourceName((PdfObject)resource.getPdfObject());
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
/*     */   public PdfName getResourceName(PdfObject resource) {
/* 389 */     PdfName resName = this.resourceToName.get(resource);
/* 390 */     if (resName == null)
/* 391 */       resName = this.resourceToName.get(resource.getIndirectReference()); 
/* 392 */     return resName;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Set<PdfName> getResourceNames() {
/* 401 */     Set<PdfName> names = new TreeSet<>();
/* 402 */     for (PdfName resType : getPdfObject().keySet()) {
/* 403 */       names.addAll(getResourceNames(resType));
/*     */     }
/* 405 */     return names;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PdfArray getProcSet() {
/* 415 */     return getPdfObject().getAsArray(PdfName.ProcSet);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setProcSet(PdfArray array) {
/* 425 */     getPdfObject().put(PdfName.ProcSet, array);
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
/*     */   public Set<PdfName> getResourceNames(PdfName resType) {
/* 437 */     PdfDictionary resourceCategory = getPdfObject().getAsDictionary(resType);
/* 438 */     return (resourceCategory == null) ? Collections.<PdfName>emptySet() : resourceCategory.keySet();
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
/*     */   public PdfDictionary getResource(PdfName resType) {
/* 450 */     return getPdfObject().getAsDictionary(resType);
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
/*     */   public PdfObject getResourceObject(PdfName resType, PdfName resName) {
/* 463 */     PdfDictionary resource = getResource(resType);
/* 464 */     if (resource != null) {
/* 465 */       return resource.get(resName);
/*     */     }
/* 467 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean isWrappedObjectMustBeIndirect() {
/* 472 */     return false;
/*     */   }
/*     */   
/*     */   <T extends PdfObject> PdfName addResource(PdfObjectWrapper<T> resource, ResourceNameGenerator nameGen) {
/* 476 */     return addResource((PdfObject)resource.getPdfObject(), nameGen);
/*     */   }
/*     */   
/*     */   protected void addResource(PdfObject resource, PdfName resType, PdfName resName) {
/* 480 */     if (resType.equals(PdfName.XObject)) {
/* 481 */       checkAndResolveCircularReferences(resource);
/*     */     }
/* 483 */     if (this.readOnly) {
/* 484 */       setPdfObject(getPdfObject().clone(Collections.emptyList()));
/* 485 */       buildResources(getPdfObject());
/* 486 */       this.isModified = true;
/* 487 */       this.readOnly = false;
/*     */     } 
/* 489 */     if (getPdfObject().containsKey(resType) && getPdfObject().getAsDictionary(resType).containsKey(resName)) {
/*     */       return;
/*     */     }
/* 492 */     this.resourceToName.put(resource, resName);
/* 493 */     PdfDictionary resourceCategory = getPdfObject().getAsDictionary(resType);
/* 494 */     if (resourceCategory == null) {
/* 495 */       getPdfObject().put(resType, resourceCategory = new PdfDictionary());
/*     */     } else {
/* 497 */       resourceCategory.setModified();
/*     */     } 
/* 499 */     resourceCategory.put(resName, resource);
/* 500 */     setModified();
/*     */   }
/*     */   
/*     */   PdfName addResource(PdfObject resource, ResourceNameGenerator nameGen) {
/* 504 */     PdfName resName = getResourceName(resource);
/*     */     
/* 506 */     if (resName == null) {
/* 507 */       resName = nameGen.generate(this);
/* 508 */       addResource(resource, nameGen.getResourceType(), resName);
/*     */     } 
/*     */     
/* 511 */     return resName;
/*     */   }
/*     */   
/*     */   protected void buildResources(PdfDictionary dictionary) {
/* 515 */     for (PdfName resourceType : dictionary.keySet()) {
/* 516 */       if (getPdfObject().get(resourceType) == null) {
/* 517 */         getPdfObject().put(resourceType, new PdfDictionary());
/*     */       }
/*     */       
/* 520 */       PdfDictionary resources = dictionary.getAsDictionary(resourceType);
/*     */       
/* 522 */       if (resources == null) {
/*     */         continue;
/*     */       }
/*     */       
/* 526 */       for (PdfName resourceName : resources.keySet()) {
/* 527 */         PdfObject resource = resources.get(resourceName, false);
/* 528 */         this.resourceToName.put(resource, resourceName);
/*     */       } 
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
/*     */   private void checkAndResolveCircularReferences(PdfObject pdfObject) {
/* 541 */     if (pdfObject instanceof PdfDictionary && !pdfObject.isFlushed()) {
/* 542 */       PdfDictionary pdfXObject = (PdfDictionary)pdfObject;
/* 543 */       PdfObject pdfXObjectResources = pdfXObject.get(PdfName.Resources);
/* 544 */       if (pdfXObjectResources != null && pdfXObjectResources.getIndirectReference() != null && 
/* 545 */         pdfXObjectResources.getIndirectReference().equals(getPdfObject().getIndirectReference())) {
/* 546 */         PdfObject cloneResources = getPdfObject().clone();
/* 547 */         cloneResources.makeIndirect(getPdfObject().getIndirectReference().getDocument());
/* 548 */         pdfXObject.put(PdfName.Resources, cloneResources.getIndirectReference());
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static class ResourceNameGenerator
/*     */     implements Serializable
/*     */   {
/*     */     private static final long serialVersionUID = 1729961083476558303L;
/*     */ 
/*     */ 
/*     */     
/*     */     private PdfName resourceType;
/*     */ 
/*     */ 
/*     */     
/*     */     private int counter;
/*     */ 
/*     */ 
/*     */     
/*     */     private String prefix;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public ResourceNameGenerator(PdfName resourceType, String prefix, int seed) {
/* 577 */       this.prefix = prefix;
/* 578 */       this.resourceType = resourceType;
/* 579 */       this.counter = seed;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public ResourceNameGenerator(PdfName resourceType, String prefix) {
/* 590 */       this(resourceType, prefix, 1);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public PdfName getResourceType() {
/* 600 */       return this.resourceType;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public PdfName generate(PdfResources resources) {
/* 610 */       PdfName newName = new PdfName(this.prefix + this.counter++);
/* 611 */       PdfDictionary r = resources.getPdfObject();
/* 612 */       if (r.containsKey(this.resourceType)) {
/* 613 */         while (r.getAsDictionary(this.resourceType).containsKey(newName)) {
/* 614 */           newName = new PdfName(this.prefix + this.counter++);
/*     */         }
/*     */       }
/*     */       
/* 618 */       return newName;
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/kernel/pdf/PdfResources.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */