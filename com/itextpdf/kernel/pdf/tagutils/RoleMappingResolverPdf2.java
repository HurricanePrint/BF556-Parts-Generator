/*     */ package com.itextpdf.kernel.pdf.tagutils;
/*     */ 
/*     */ import com.itextpdf.kernel.pdf.PdfArray;
/*     */ import com.itextpdf.kernel.pdf.PdfDictionary;
/*     */ import com.itextpdf.kernel.pdf.PdfDocument;
/*     */ import com.itextpdf.kernel.pdf.PdfName;
/*     */ import com.itextpdf.kernel.pdf.PdfObject;
/*     */ import com.itextpdf.kernel.pdf.tagging.PdfNamespace;
/*     */ import com.itextpdf.kernel.pdf.tagging.PdfStructTreeRoot;
/*     */ import com.itextpdf.kernel.pdf.tagging.StandardNamespaces;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class RoleMappingResolverPdf2
/*     */   implements IRoleMappingResolver
/*     */ {
/*     */   private static final long serialVersionUID = -564649110244365255L;
/*     */   private PdfName currRole;
/*     */   private PdfNamespace currNamespace;
/*     */   private PdfNamespace defaultNamespace;
/*     */   
/*     */   RoleMappingResolverPdf2(String role, PdfNamespace namespace, PdfDocument document) {
/*  66 */     this.currRole = PdfStructTreeRoot.convertRoleToPdfName(role);
/*  67 */     this.currNamespace = namespace;
/*     */     
/*  69 */     String defaultNsName = StandardNamespaces.getDefault();
/*  70 */     PdfDictionary defaultNsRoleMap = document.getStructTreeRoot().getRoleMap();
/*  71 */     this.defaultNamespace = (new PdfNamespace(defaultNsName)).setNamespaceRoleMap(defaultNsRoleMap);
/*     */     
/*  73 */     if (this.currNamespace == null) {
/*  74 */       this.currNamespace = this.defaultNamespace;
/*     */     }
/*     */   }
/*     */   
/*     */   public String getRole() {
/*  79 */     return this.currRole.getValue();
/*     */   }
/*     */   
/*     */   public PdfNamespace getNamespace() {
/*  83 */     return this.currNamespace;
/*     */   }
/*     */   
/*     */   public boolean currentRoleIsStandard() {
/*  87 */     String roleStrVal = this.currRole.getValue();
/*     */     
/*  89 */     boolean stdRole17 = ("http://iso.org/pdf/ssn".equals(this.currNamespace.getNamespaceName()) && StandardNamespaces.roleBelongsToStandardNamespace(roleStrVal, "http://iso.org/pdf/ssn"));
/*     */     
/*  91 */     boolean stdRole20 = ("http://iso.org/pdf2/ssn".equals(this.currNamespace.getNamespaceName()) && StandardNamespaces.roleBelongsToStandardNamespace(roleStrVal, "http://iso.org/pdf2/ssn"));
/*  92 */     return (stdRole17 || stdRole20);
/*     */   }
/*     */   
/*     */   public boolean currentRoleShallBeMappedToStandard() {
/*  96 */     return (!currentRoleIsStandard() && !StandardNamespaces.isKnownDomainSpecificNamespace(this.currNamespace));
/*     */   }
/*     */   
/*     */   public boolean resolveNextMapping() {
/* 100 */     PdfObject mapping = null;
/* 101 */     PdfDictionary currNsRoleMap = this.currNamespace.getNamespaceRoleMap();
/* 102 */     if (currNsRoleMap != null) {
/* 103 */       mapping = currNsRoleMap.get(this.currRole);
/*     */     }
/*     */     
/* 106 */     if (mapping == null) {
/* 107 */       return false;
/*     */     }
/*     */     
/* 110 */     boolean mappingWasResolved = false;
/* 111 */     if (mapping.isName()) {
/* 112 */       this.currRole = (PdfName)mapping;
/* 113 */       this.currNamespace = this.defaultNamespace;
/* 114 */       mappingWasResolved = true;
/* 115 */     } else if (mapping.isArray()) {
/* 116 */       PdfName mappedRole = null;
/* 117 */       PdfDictionary mappedNsDict = null;
/*     */       
/* 119 */       PdfArray mappingArr = (PdfArray)mapping;
/* 120 */       if (mappingArr.size() > 1) {
/* 121 */         mappedRole = mappingArr.getAsName(0);
/* 122 */         mappedNsDict = mappingArr.getAsDictionary(1);
/*     */       } 
/* 124 */       mappingWasResolved = (mappedRole != null && mappedNsDict != null);
/* 125 */       if (mappingWasResolved) {
/* 126 */         this.currRole = mappedRole;
/* 127 */         this.currNamespace = new PdfNamespace(mappedNsDict);
/*     */       } 
/*     */     } 
/* 130 */     return mappingWasResolved;
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/kernel/pdf/tagutils/RoleMappingResolverPdf2.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */