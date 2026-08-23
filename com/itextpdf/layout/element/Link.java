/*     */ package com.itextpdf.layout.element;
/*     */ 
/*     */ import com.itextpdf.kernel.geom.Rectangle;
/*     */ import com.itextpdf.kernel.pdf.action.PdfAction;
/*     */ import com.itextpdf.kernel.pdf.annot.PdfLinkAnnotation;
/*     */ import com.itextpdf.kernel.pdf.navigation.PdfDestination;
/*     */ import com.itextpdf.kernel.pdf.tagutils.AccessibilityProperties;
/*     */ import com.itextpdf.kernel.pdf.tagutils.DefaultAccessibilityProperties;
/*     */ import com.itextpdf.layout.renderer.IRenderer;
/*     */ import com.itextpdf.layout.renderer.LinkRenderer;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Link
/*     */   extends Text
/*     */ {
/*     */   public Link(String text, PdfLinkAnnotation linkAnnotation) {
/*  72 */     super(text);
/*  73 */     setProperty(88, linkAnnotation);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Link(String text, PdfAction action) {
/*  83 */     this(text, (PdfLinkAnnotation)(new PdfLinkAnnotation(new Rectangle(0.0F, 0.0F, 0.0F, 0.0F))).setAction(action).setFlags(4));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Link(String text, PdfDestination destination) {
/*  93 */     this(text, (PdfLinkAnnotation)(new PdfLinkAnnotation(new Rectangle(0.0F, 0.0F, 0.0F, 0.0F))).setDestination(destination).setFlags(4));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PdfLinkAnnotation getLinkAnnotation() {
/* 101 */     return (PdfLinkAnnotation)getProperty(88);
/*     */   }
/*     */ 
/*     */   
/*     */   public AccessibilityProperties getAccessibilityProperties() {
/* 106 */     if (this.tagProperties == null) {
/* 107 */       this.tagProperties = new DefaultAccessibilityProperties("Link");
/*     */     }
/* 109 */     return (AccessibilityProperties)this.tagProperties;
/*     */   }
/*     */ 
/*     */   
/*     */   protected IRenderer makeNewRenderer() {
/* 114 */     return (IRenderer)new LinkRenderer(this, this.text);
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/layout/element/Link.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */