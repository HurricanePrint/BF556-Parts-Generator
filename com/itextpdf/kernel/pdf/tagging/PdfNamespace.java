/*     */ package com.itextpdf.kernel.pdf.tagging;
/*     */ 
/*     */ import com.itextpdf.io.util.MessageFormatUtil;
/*     */ import com.itextpdf.kernel.pdf.PdfArray;
/*     */ import com.itextpdf.kernel.pdf.PdfDictionary;
/*     */ import com.itextpdf.kernel.pdf.PdfName;
/*     */ import com.itextpdf.kernel.pdf.PdfObject;
/*     */ import com.itextpdf.kernel.pdf.PdfObjectWrapper;
/*     */ import com.itextpdf.kernel.pdf.PdfString;
/*     */ import com.itextpdf.kernel.pdf.filespec.PdfFileSpec;
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
/*     */ public class PdfNamespace
/*     */   extends PdfObjectWrapper<PdfDictionary>
/*     */ {
/*     */   private static final long serialVersionUID = -4228596885910641569L;
/*     */   
/*     */   public PdfNamespace(PdfDictionary dictionary) {
/*  73 */     super((PdfObject)dictionary);
/*  74 */     setForbidRelease();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PdfNamespace(String namespaceName) {
/*  83 */     this(new PdfString(namespaceName));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PdfNamespace(PdfString namespaceName) {
/*  92 */     this(new PdfDictionary());
/*  93 */     put(PdfName.Type, (PdfObject)PdfName.Namespace);
/*  94 */     put(PdfName.NS, (PdfObject)namespaceName);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PdfNamespace setNamespaceName(String namespaceName) {
/* 104 */     return setNamespaceName(new PdfString(namespaceName));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PdfNamespace setNamespaceName(PdfString namespaceName) {
/* 114 */     return put(PdfName.NS, (PdfObject)namespaceName);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getNamespaceName() {
/* 123 */     PdfString ns = ((PdfDictionary)getPdfObject()).getAsString(PdfName.NS);
/* 124 */     return (ns != null) ? ns.toUnicodeString() : null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PdfNamespace setSchema(PdfFileSpec fileSpec) {
/* 133 */     return put(PdfName.Schema, fileSpec.getPdfObject());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PdfFileSpec getSchema() {
/* 141 */     PdfObject schemaObject = ((PdfDictionary)getPdfObject()).get(PdfName.Schema);
/* 142 */     return PdfFileSpec.wrapFileSpecObject(schemaObject);
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
/*     */   public PdfNamespace setNamespaceRoleMap(PdfDictionary roleMapNs) {
/* 156 */     return put(PdfName.RoleMapNS, (PdfObject)roleMapNs);
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
/*     */   public PdfDictionary getNamespaceRoleMap() {
/* 169 */     return getNamespaceRoleMap(false);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PdfNamespace addNamespaceRoleMapping(String thisNsRole, String defaultNsRole) {
/* 180 */     PdfObject prevVal = getNamespaceRoleMap(true).put(PdfStructTreeRoot.convertRoleToPdfName(thisNsRole), (PdfObject)PdfStructTreeRoot.convertRoleToPdfName(defaultNsRole));
/* 181 */     logOverwritingOfMappingIfNeeded(thisNsRole, prevVal);
/* 182 */     setModified();
/* 183 */     return this;
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
/*     */   public PdfNamespace addNamespaceRoleMapping(String thisNsRole, String targetNsRole, PdfNamespace targetNs) {
/* 195 */     PdfArray targetMapping = new PdfArray();
/* 196 */     targetMapping.add((PdfObject)PdfStructTreeRoot.convertRoleToPdfName(targetNsRole));
/* 197 */     targetMapping.add(targetNs.getPdfObject());
/* 198 */     PdfObject prevVal = getNamespaceRoleMap(true).put(PdfStructTreeRoot.convertRoleToPdfName(thisNsRole), (PdfObject)targetMapping);
/* 199 */     logOverwritingOfMappingIfNeeded(thisNsRole, prevVal);
/* 200 */     setModified();
/* 201 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean isWrappedObjectMustBeIndirect() {
/* 206 */     return true;
/*     */   }
/*     */   
/*     */   private PdfNamespace put(PdfName key, PdfObject value) {
/* 210 */     ((PdfDictionary)getPdfObject()).put(key, value);
/* 211 */     setModified();
/* 212 */     return this;
/*     */   }
/*     */   
/*     */   private PdfDictionary getNamespaceRoleMap(boolean createIfNotExist) {
/* 216 */     PdfDictionary roleMapNs = ((PdfDictionary)getPdfObject()).getAsDictionary(PdfName.RoleMapNS);
/* 217 */     if (createIfNotExist && roleMapNs == null) {
/* 218 */       roleMapNs = new PdfDictionary();
/* 219 */       put(PdfName.RoleMapNS, (PdfObject)roleMapNs);
/*     */     } 
/* 221 */     return roleMapNs;
/*     */   }
/*     */   
/*     */   private void logOverwritingOfMappingIfNeeded(String thisNsRole, PdfObject prevVal) {
/* 225 */     if (prevVal != null) {
/* 226 */       Logger logger = LoggerFactory.getLogger(PdfNamespace.class);
/* 227 */       String nsNameStr = getNamespaceName();
/* 228 */       if (nsNameStr == null) {
/* 229 */         nsNameStr = "this";
/*     */       }
/* 231 */       logger.warn(MessageFormatUtil.format("Existing mapping for {0} in {1} namespace was overwritten.", new Object[] { thisNsRole, nsNameStr }));
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/kernel/pdf/tagging/PdfNamespace.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */