/*     */ package com.itextpdf.kernel.pdf.tagutils;
/*     */ 
/*     */ import com.itextpdf.kernel.pdf.PdfDocument;
/*     */ import com.itextpdf.kernel.pdf.PdfName;
/*     */ import com.itextpdf.kernel.pdf.tagging.IStructureNode;
/*     */ import com.itextpdf.kernel.pdf.tagging.PdfNamespace;
/*     */ import com.itextpdf.kernel.pdf.tagging.PdfStructElem;
/*     */ import java.io.Serializable;
/*     */ import java.text.MessageFormat;
/*     */ import java.util.List;
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
/*     */ class RootTagNormalizer
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = -4392164598496387910L;
/*     */   private TagStructureContext context;
/*     */   private PdfStructElem rootTagElement;
/*     */   private PdfDocument document;
/*     */   
/*     */   RootTagNormalizer(TagStructureContext context, PdfStructElem rootTagElement, PdfDocument document) {
/*  68 */     this.context = context;
/*  69 */     this.rootTagElement = rootTagElement;
/*  70 */     this.document = document;
/*     */   }
/*     */   
/*     */   PdfStructElem makeSingleStandardRootTag(List<IStructureNode> rootKids) {
/*  74 */     this.document.getStructTreeRoot().makeIndirect(this.document);
/*  75 */     if (this.rootTagElement == null) {
/*  76 */       createNewRootTag();
/*     */     } else {
/*  78 */       this.rootTagElement.makeIndirect(this.document);
/*  79 */       this.document.getStructTreeRoot().addKid(this.rootTagElement);
/*  80 */       ensureExistingRootTagIsDocument();
/*     */     } 
/*     */     
/*  83 */     addStructTreeRootKidsToTheRootTag(rootKids);
/*     */     
/*  85 */     return this.rootTagElement;
/*     */   }
/*     */ 
/*     */   
/*     */   private void createNewRootTag() {
/*  90 */     PdfNamespace docDefaultNs = this.context.getDocumentDefaultNamespace();
/*  91 */     IRoleMappingResolver mapping = this.context.resolveMappingToStandardOrDomainSpecificRole("Document", docDefaultNs);
/*  92 */     if (mapping == null || (mapping.currentRoleIsStandard() && !"Document".equals(mapping.getRole()))) {
/*  93 */       logCreatedRootTagHasMappingIssue(docDefaultNs, mapping);
/*     */     }
/*  95 */     this.rootTagElement = this.document.getStructTreeRoot().addKid(new PdfStructElem(this.document, PdfName.Document));
/*  96 */     if (this.context.targetTagStructureVersionIs2()) {
/*  97 */       this.rootTagElement.setNamespace(docDefaultNs);
/*  98 */       this.context.ensureNamespaceRegistered(docDefaultNs);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void ensureExistingRootTagIsDocument() {
/* 104 */     IRoleMappingResolver mapping = this.context.getRoleMappingResolver(this.rootTagElement.getRole().getValue(), this.rootTagElement.getNamespace());
/* 105 */     boolean isDocBeforeResolving = (mapping.currentRoleIsStandard() && "Document".equals(mapping.getRole()));
/*     */     
/* 107 */     mapping = this.context.resolveMappingToStandardOrDomainSpecificRole(this.rootTagElement.getRole().getValue(), this.rootTagElement.getNamespace());
/* 108 */     boolean isDocAfterResolving = (mapping != null && mapping.currentRoleIsStandard() && "Document".equals(mapping.getRole()));
/*     */     
/* 110 */     if (isDocBeforeResolving && !isDocAfterResolving) {
/* 111 */       logCreatedRootTagHasMappingIssue(this.rootTagElement.getNamespace(), mapping);
/* 112 */     } else if (!isDocAfterResolving) {
/* 113 */       wrapAllKidsInTag(this.rootTagElement, this.rootTagElement.getRole(), this.rootTagElement.getNamespace());
/* 114 */       this.rootTagElement.setRole(PdfName.Document);
/* 115 */       if (this.context.targetTagStructureVersionIs2()) {
/* 116 */         this.rootTagElement.setNamespace(this.context.getDocumentDefaultNamespace());
/* 117 */         this.context.ensureNamespaceRegistered(this.context.getDocumentDefaultNamespace());
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private void addStructTreeRootKidsToTheRootTag(List<IStructureNode> rootKids) {
/* 123 */     int originalRootKidsIndex = 0;
/* 124 */     boolean isBeforeOriginalRoot = true;
/* 125 */     for (IStructureNode elem : rootKids) {
/*     */       
/* 127 */       PdfStructElem kid = (PdfStructElem)elem;
/* 128 */       if (kid.getPdfObject() == this.rootTagElement.getPdfObject()) {
/* 129 */         isBeforeOriginalRoot = false;
/*     */ 
/*     */         
/*     */         continue;
/*     */       } 
/*     */ 
/*     */       
/* 136 */       boolean kidIsDocument = PdfName.Document.equals(kid.getRole());
/* 137 */       if (kidIsDocument && kid.getNamespace() != null && this.context.targetTagStructureVersionIs2()) {
/*     */         
/* 139 */         String kidNamespaceName = kid.getNamespace().getNamespaceName();
/* 140 */         kidIsDocument = ("http://iso.org/pdf/ssn".equals(kidNamespaceName) || "http://iso.org/pdf2/ssn".equals(kidNamespaceName));
/*     */       } 
/*     */       
/* 143 */       if (isBeforeOriginalRoot) {
/* 144 */         this.rootTagElement.addKid(originalRootKidsIndex, kid);
/* 145 */         originalRootKidsIndex += kidIsDocument ? kid.getKids().size() : 1;
/*     */       } else {
/* 147 */         this.rootTagElement.addKid(kid);
/*     */       } 
/* 149 */       if (kidIsDocument) {
/* 150 */         removeOldRoot(kid);
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   private void wrapAllKidsInTag(PdfStructElem parent, PdfName wrapTagRole, PdfNamespace wrapTagNs) {
/* 156 */     int kidsNum = parent.getKids().size();
/* 157 */     TagTreePointer tagPointer = new TagTreePointer(parent, this.document);
/* 158 */     tagPointer.addTag(0, wrapTagRole.getValue());
/*     */     
/* 160 */     if (this.context.targetTagStructureVersionIs2()) {
/* 161 */       tagPointer.getProperties().setNamespace(wrapTagNs);
/*     */     }
/*     */     
/* 164 */     TagTreePointer newParentOfKids = new TagTreePointer(tagPointer);
/* 165 */     tagPointer.moveToParent();
/* 166 */     for (int i = 0; i < kidsNum; i++) {
/* 167 */       tagPointer.relocateKid(1, newParentOfKids);
/*     */     }
/*     */   }
/*     */   
/*     */   private void removeOldRoot(PdfStructElem oldRoot) {
/* 172 */     TagTreePointer tagPointer = new TagTreePointer(this.document);
/* 173 */     tagPointer
/* 174 */       .setCurrentStructElem(oldRoot)
/* 175 */       .removeTag();
/*     */   }
/*     */   
/*     */   private void logCreatedRootTagHasMappingIssue(PdfNamespace rootTagOriginalNs, IRoleMappingResolver mapping) {
/* 179 */     String origRootTagNs = "";
/* 180 */     if (rootTagOriginalNs != null && rootTagOriginalNs.getNamespaceName() != null) {
/* 181 */       origRootTagNs = " in \"" + rootTagOriginalNs.getNamespaceName() + "\" namespace";
/*     */     }
/*     */     
/* 184 */     String mappingRole = " to ";
/* 185 */     if (mapping != null) {
/* 186 */       mappingRole = mappingRole + "\"" + mapping.getRole() + "\"";
/* 187 */       if (mapping.getNamespace() != null && !"http://iso.org/pdf/ssn".equals(mapping.getNamespace().getNamespaceName())) {
/* 188 */         mappingRole = mappingRole + " in \"" + mapping.getNamespace().getNamespaceName() + "\" namespace";
/*     */       }
/*     */     } else {
/* 191 */       mappingRole = mappingRole + "not standard role";
/*     */     } 
/*     */     
/* 194 */     Logger logger = LoggerFactory.getLogger(RootTagNormalizer.class);
/* 195 */     logger.warn(MessageFormat.format("Created root tag has role mapping: \"/Document\" role{0} is mapped{1}. Resulting tag structure might have invalid root tag.", new Object[] { origRootTagNs, mappingRole }));
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/kernel/pdf/tagutils/RootTagNormalizer.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */