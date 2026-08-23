/*     */ package com.itextpdf.forms.xfdf;
/*     */ 
/*     */ import com.itextpdf.kernel.pdf.PdfDocument;
/*     */ import java.io.FileOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.OutputStream;
/*     */ import java.util.List;
/*     */ import javax.xml.parsers.ParserConfigurationException;
/*     */ import javax.xml.transform.TransformerException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class XfdfObject
/*     */ {
/*     */   private FObject f;
/*     */   private IdsObject ids;
/*     */   private FieldsObject fields;
/*     */   private AnnotsObject annots;
/*     */   private List<AttributeObject> attributes;
/*     */   
/*     */   public FObject getF() {
/*  96 */     return this.f;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setF(FObject f) {
/* 106 */     this.f = f;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IdsObject getIds() {
/* 116 */     return this.ids;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setIds(IdsObject ids) {
/* 126 */     this.ids = ids;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public FieldsObject getFields() {
/* 135 */     return this.fields;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setFields(FieldsObject fields) {
/* 144 */     this.fields = fields;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public AnnotsObject getAnnots() {
/* 151 */     return this.annots;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setAnnots(AnnotsObject annots) {
/* 158 */     this.annots = annots;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List<AttributeObject> getAttributes() {
/* 165 */     return this.attributes;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setAttributes(List<AttributeObject> attributes) {
/* 172 */     this.attributes = attributes;
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
/*     */   public void mergeToPdf(PdfDocument pdfDocument, String pdfDocumentName) {
/* 184 */     XfdfReader reader = new XfdfReader();
/* 185 */     reader.mergeXfdfIntoPdf(this, pdfDocument, pdfDocumentName);
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
/*     */   public void writeToFile(String filename) throws IOException, TransformerException, ParserConfigurationException {
/* 197 */     try (OutputStream os = new FileOutputStream(filename)) {
/* 198 */       writeToFile(os);
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
/*     */   public void writeToFile(OutputStream os) throws TransformerException, ParserConfigurationException {
/* 210 */     XfdfWriter writer = new XfdfWriter(os);
/* 211 */     writer.write(this);
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/forms/xfdf/XfdfObject.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */