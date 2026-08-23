/*     */ package com.itextpdf.kernel.pdf.tagging;
/*     */ 
/*     */ import java.util.Arrays;
/*     */ import java.util.HashSet;
/*     */ import java.util.Set;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class StandardNamespaces
/*     */ {
/*  74 */   private static final Set<String> STD_STRUCT_NAMESPACE_1_7_TYPES = new HashSet<>(Arrays.asList(new String[] { "Document", "Part", "Div", "P", "H", "H1", "H2", "H3", "H4", "H5", "H6", "Lbl", "Span", "Link", "Annot", "Form", "Ruby", "RB", "RT", "RP", "Warichu", "WT", "WP", "L", "LI", "LBody", "Table", "TR", "TH", "TD", "THead", "TBody", "TFoot", "Caption", "Figure", "Formula", "Sect", "Art", "BlockQuote", "TOC", "TOCI", "Index", "NonStruct", "Private", "Quote", "Note", "Reference", "BibEntry", "Code" }));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 128 */   private static final Set<String> STD_STRUCT_NAMESPACE_2_0_TYPES = new HashSet<>(Arrays.asList(new String[] { "Document", "DocumentFragment", "Part", "Div", "Aside", "Title", "Sub", "P", "H", "Lbl", "Em", "Strong", "Span", "Link", "Annot", "Form", "Ruby", "RB", "RT", "RP", "Warichu", "WT", "WP", "FENote", "L", "LI", "LBody", "Table", "TR", "TH", "TD", "THead", "TBody", "TFoot", "Caption", "Figure", "Formula", "Artifact" }));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static final String MATH_ML = "http://www.w3.org/1998/Math/MathML";
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static final String PDF_1_7 = "http://iso.org/pdf/ssn";
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static final String PDF_2_0 = "http://iso.org/pdf2/ssn";
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String getDefault() {
/* 179 */     return "http://iso.org/pdf/ssn";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean isKnownDomainSpecificNamespace(PdfNamespace namespace) {
/* 189 */     return "http://www.w3.org/1998/Math/MathML".equals(namespace.getNamespaceName());
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
/*     */   public static boolean roleBelongsToStandardNamespace(String role, String standardNamespaceName) {
/* 201 */     if ("http://iso.org/pdf/ssn".equals(standardNamespaceName))
/* 202 */       return STD_STRUCT_NAMESPACE_1_7_TYPES.contains(role); 
/* 203 */     if ("http://iso.org/pdf2/ssn".equals(standardNamespaceName)) {
/* 204 */       return (STD_STRUCT_NAMESPACE_2_0_TYPES.contains(role) || isHnRole(role));
/*     */     }
/*     */     
/* 207 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean isHnRole(String role) {
/* 218 */     if (role.startsWith("H") && role.length() > 1 && role.charAt(1) != '0') {
/*     */       try {
/* 220 */         return (Integer.parseInt(role.substring(1, role.length())) > 0);
/* 221 */       } catch (Exception exception) {}
/*     */     }
/*     */ 
/*     */     
/* 225 */     return false;
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/kernel/pdf/tagging/StandardNamespaces.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */