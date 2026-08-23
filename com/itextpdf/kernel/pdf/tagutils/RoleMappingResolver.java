/*    */ package com.itextpdf.kernel.pdf.tagutils;
/*    */ 
/*    */ import com.itextpdf.kernel.pdf.PdfDictionary;
/*    */ import com.itextpdf.kernel.pdf.PdfDocument;
/*    */ import com.itextpdf.kernel.pdf.PdfName;
/*    */ import com.itextpdf.kernel.pdf.tagging.PdfNamespace;
/*    */ import com.itextpdf.kernel.pdf.tagging.PdfStructTreeRoot;
/*    */ import com.itextpdf.kernel.pdf.tagging.StandardNamespaces;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ class RoleMappingResolver
/*    */   implements IRoleMappingResolver
/*    */ {
/*    */   private static final long serialVersionUID = -8911597456631422956L;
/*    */   private PdfName currRole;
/*    */   private PdfDictionary roleMap;
/*    */   
/*    */   RoleMappingResolver(String role, PdfDocument document) {
/* 60 */     this.currRole = PdfStructTreeRoot.convertRoleToPdfName(role);
/* 61 */     this.roleMap = document.getStructTreeRoot().getRoleMap();
/*    */   }
/*    */ 
/*    */   
/*    */   public String getRole() {
/* 66 */     return this.currRole.getValue();
/*    */   }
/*    */ 
/*    */   
/*    */   public PdfNamespace getNamespace() {
/* 71 */     return null;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean currentRoleIsStandard() {
/* 76 */     return StandardNamespaces.roleBelongsToStandardNamespace(this.currRole.getValue(), "http://iso.org/pdf/ssn");
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean currentRoleShallBeMappedToStandard() {
/* 81 */     return !currentRoleIsStandard();
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean resolveNextMapping() {
/* 86 */     PdfName mappedRole = this.roleMap.getAsName(this.currRole);
/* 87 */     if (mappedRole == null) {
/* 88 */       return false;
/*    */     }
/* 90 */     this.currRole = mappedRole;
/* 91 */     return true;
/*    */   }
/*    */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/kernel/pdf/tagutils/RoleMappingResolver.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */