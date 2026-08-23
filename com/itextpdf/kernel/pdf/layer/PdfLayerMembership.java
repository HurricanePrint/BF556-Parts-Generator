/*     */ package com.itextpdf.kernel.pdf.layer;
/*     */ 
/*     */ import com.itextpdf.kernel.pdf.PdfArray;
/*     */ import com.itextpdf.kernel.pdf.PdfDictionary;
/*     */ import com.itextpdf.kernel.pdf.PdfDocument;
/*     */ import com.itextpdf.kernel.pdf.PdfIndirectReference;
/*     */ import com.itextpdf.kernel.pdf.PdfName;
/*     */ import com.itextpdf.kernel.pdf.PdfObject;
/*     */ import com.itextpdf.kernel.pdf.PdfObjectWrapper;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
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
/*     */ public class PdfLayerMembership
/*     */   extends PdfObjectWrapper<PdfDictionary>
/*     */   implements IPdfOCG
/*     */ {
/*     */   private static final long serialVersionUID = -597407628148657784L;
/*     */   
/*     */   public PdfLayerMembership(PdfDocument doc) {
/*  79 */     super((PdfObject)new PdfDictionary());
/*  80 */     makeIndirect(doc);
/*  81 */     ((PdfDictionary)getPdfObject()).put(PdfName.Type, (PdfObject)PdfName.OCMD);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PdfLayerMembership(PdfDictionary membershipDictionary) {
/*  90 */     super((PdfObject)membershipDictionary);
/*  91 */     ensureObjectIsAddedToDocument((PdfObject)membershipDictionary);
/*  92 */     if (!PdfName.OCMD.equals(membershipDictionary.getAsName(PdfName.Type))) {
/*  93 */       throw new IllegalArgumentException("Invalid membershipDictionary.");
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Collection<PdfLayer> getLayers() {
/* 102 */     PdfObject layers = ((PdfDictionary)getPdfObject()).get(PdfName.OCGs);
/* 103 */     if (layers instanceof PdfDictionary) {
/* 104 */       List<PdfLayer> list = new ArrayList<>();
/* 105 */       list.add(new PdfLayer((PdfDictionary)((PdfDictionary)layers).makeIndirect(getDocument())));
/* 106 */       return list;
/*     */     } 
/* 108 */     if (layers instanceof PdfArray) {
/* 109 */       List<PdfLayer> layerList = new ArrayList<>();
/* 110 */       for (int ind = 0; ind < ((PdfArray)layers).size(); ind++) {
/* 111 */         layerList.add(new PdfLayer(((PdfArray)((PdfArray)layers).makeIndirect(getDocument())).getAsDictionary(ind)));
/*     */       }
/* 113 */       return layerList;
/*     */     } 
/* 115 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addLayer(PdfLayer layer) {
/* 123 */     PdfArray layers = ((PdfDictionary)getPdfObject()).getAsArray(PdfName.OCGs);
/* 124 */     if (layers == null) {
/* 125 */       layers = new PdfArray();
/* 126 */       ((PdfDictionary)getPdfObject()).put(PdfName.OCGs, (PdfObject)layers);
/*     */     } 
/* 128 */     layers.add(layer.getPdfObject());
/* 129 */     layers.setModified();
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
/*     */   public void setVisibilityPolicy(PdfName visibilityPolicy) {
/* 143 */     if (visibilityPolicy == null || (!PdfName.AllOn.equals(visibilityPolicy) && !PdfName.AnyOn.equals(visibilityPolicy) && 
/* 144 */       !PdfName.AnyOff.equals(visibilityPolicy) && !PdfName.AllOff.equals(visibilityPolicy)))
/* 145 */       throw new IllegalArgumentException("Argument: visibilityPolicy"); 
/* 146 */     ((PdfDictionary)getPdfObject()).put(PdfName.P, (PdfObject)visibilityPolicy);
/* 147 */     ((PdfDictionary)getPdfObject()).setModified();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PdfName getVisibilityPolicy() {
/* 155 */     PdfName visibilityPolicy = ((PdfDictionary)getPdfObject()).getAsName(PdfName.P);
/* 156 */     if (visibilityPolicy == null || (!visibilityPolicy.equals(PdfName.AllOn) && !visibilityPolicy.equals(PdfName.AllOff) && 
/* 157 */       !visibilityPolicy.equals(PdfName.AnyOn) && !visibilityPolicy.equals(PdfName.AnyOff)))
/* 158 */       return PdfName.AnyOn; 
/* 159 */     return visibilityPolicy;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setVisibilityExpression(PdfVisibilityExpression visibilityExpression) {
/* 170 */     ((PdfDictionary)getPdfObject()).put(PdfName.VE, visibilityExpression.getPdfObject());
/* 171 */     ((PdfDictionary)getPdfObject()).setModified();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PdfVisibilityExpression getVisibilityExpression() {
/* 179 */     PdfArray ve = ((PdfDictionary)getPdfObject()).getAsArray(PdfName.VE);
/* 180 */     return (ve != null) ? new PdfVisibilityExpression(ve) : null;
/*     */   }
/*     */ 
/*     */   
/*     */   public PdfIndirectReference getIndirectReference() {
/* 185 */     return ((PdfDictionary)getPdfObject()).getIndirectReference();
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean isWrappedObjectMustBeIndirect() {
/* 190 */     return true;
/*     */   }
/*     */   
/*     */   protected PdfDocument getDocument() {
/* 194 */     return ((PdfDictionary)getPdfObject()).getIndirectReference().getDocument();
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/kernel/pdf/layer/PdfLayerMembership.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */