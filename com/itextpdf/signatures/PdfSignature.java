/*     */ package com.itextpdf.signatures;
/*     */ 
/*     */ import com.itextpdf.kernel.pdf.PdfArray;
/*     */ import com.itextpdf.kernel.pdf.PdfDate;
/*     */ import com.itextpdf.kernel.pdf.PdfDictionary;
/*     */ import com.itextpdf.kernel.pdf.PdfName;
/*     */ import com.itextpdf.kernel.pdf.PdfNumber;
/*     */ import com.itextpdf.kernel.pdf.PdfObject;
/*     */ import com.itextpdf.kernel.pdf.PdfObjectWrapper;
/*     */ import com.itextpdf.kernel.pdf.PdfString;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class PdfSignature
/*     */   extends PdfObjectWrapper<PdfDictionary>
/*     */ {
/*     */   public PdfSignature() {
/*  67 */     super((PdfObject)new PdfDictionary());
/*  68 */     put(PdfName.Type, (PdfObject)PdfName.Sig);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PdfSignature(PdfName filter, PdfName subFilter) {
/*  77 */     this();
/*  78 */     put(PdfName.Filter, (PdfObject)filter);
/*  79 */     put(PdfName.SubFilter, (PdfObject)subFilter);
/*     */   }
/*     */   
/*     */   public PdfSignature(PdfDictionary sigDictionary) {
/*  83 */     super((PdfObject)sigDictionary);
/*  84 */     PdfString contents = ((PdfDictionary)getPdfObject()).getAsString(PdfName.Contents);
/*  85 */     if (contents != null) {
/*  86 */       contents.markAsUnencryptedObject();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PdfName getSubFilter() {
/*  95 */     return ((PdfDictionary)getPdfObject()).getAsName(PdfName.SubFilter);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PdfName getType() {
/* 105 */     return ((PdfDictionary)getPdfObject()).getAsName(PdfName.Type);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setByteRange(int[] range) {
/* 114 */     PdfArray array = new PdfArray();
/*     */     
/* 116 */     for (int k = 0; k < range.length; k++) {
/* 117 */       array.add((PdfObject)new PdfNumber(range[k]));
/*     */     }
/*     */     
/* 120 */     put(PdfName.ByteRange, (PdfObject)array);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PdfArray getByteRange() {
/* 128 */     return ((PdfDictionary)getPdfObject()).getAsArray(PdfName.ByteRange);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setContents(byte[] contents) {
/* 137 */     PdfString contentsString = (new PdfString(contents)).setHexWriting(true);
/* 138 */     contentsString.markAsUnencryptedObject();
/* 139 */     put(PdfName.Contents, (PdfObject)contentsString);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PdfString getContents() {
/* 146 */     return ((PdfDictionary)getPdfObject()).getAsString(PdfName.Contents);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setCert(byte[] cert) {
/* 155 */     put(PdfName.Cert, (PdfObject)new PdfString(cert));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PdfString getCert() {
/* 162 */     return ((PdfDictionary)getPdfObject()).getAsString(PdfName.Cert);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setName(String name) {
/* 171 */     put(PdfName.Name, (PdfObject)new PdfString(name, "UnicodeBig"));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getName() {
/* 179 */     PdfString nameStr = ((PdfDictionary)getPdfObject()).getAsString(PdfName.Name);
/* 180 */     PdfName nameName = ((PdfDictionary)getPdfObject()).getAsName(PdfName.Name);
/* 181 */     if (nameStr != null) {
/* 182 */       return nameStr.toUnicodeString();
/*     */     }
/* 184 */     return (nameName != null) ? nameName.getValue() : null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setDate(PdfDate date) {
/* 194 */     put(PdfName.M, date.getPdfObject());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PdfString getDate() {
/* 202 */     return ((PdfDictionary)getPdfObject()).getAsString(PdfName.M);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setLocation(String location) {
/* 211 */     put(PdfName.Location, (PdfObject)new PdfString(location, "UnicodeBig"));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getLocation() {
/* 219 */     PdfString locationStr = ((PdfDictionary)getPdfObject()).getAsString(PdfName.Location);
/* 220 */     return (locationStr != null) ? locationStr.toUnicodeString() : null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setReason(String reason) {
/* 229 */     put(PdfName.Reason, (PdfObject)new PdfString(reason, "UnicodeBig"));
/*     */   }
/*     */   
/*     */   public String getReason() {
/* 233 */     PdfString reasonStr = ((PdfDictionary)getPdfObject()).getAsString(PdfName.Reason);
/* 234 */     return (reasonStr != null) ? reasonStr.toUnicodeString() : null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setSignatureCreator(String signatureCreator) {
/* 244 */     if (signatureCreator != null) {
/* 245 */       getPdfSignatureBuildProperties().setSignatureCreator(signatureCreator);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setContact(String contactInfo) {
/* 255 */     put(PdfName.ContactInfo, (PdfObject)new PdfString(contactInfo, "UnicodeBig"));
/*     */   }
/*     */   
/*     */   public PdfSignature put(PdfName key, PdfObject value) {
/* 259 */     ((PdfDictionary)getPdfObject()).put(key, value);
/* 260 */     setModified();
/* 261 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean isWrappedObjectMustBeIndirect() {
/* 266 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private PdfSignatureBuildProperties getPdfSignatureBuildProperties() {
/* 276 */     PdfDictionary buildPropDict = ((PdfDictionary)getPdfObject()).getAsDictionary(PdfName.Prop_Build);
/*     */     
/* 278 */     if (buildPropDict == null) {
/* 279 */       buildPropDict = new PdfDictionary();
/* 280 */       put(PdfName.Prop_Build, (PdfObject)buildPropDict);
/*     */     } 
/*     */     
/* 283 */     return new PdfSignatureBuildProperties(buildPropDict);
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/signatures/PdfSignature.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */