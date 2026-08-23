/*     */ package com.itextpdf.layout.tagging;
/*     */ 
/*     */ import com.itextpdf.kernel.pdf.tagutils.AccessibilityProperties;
/*     */ import com.itextpdf.kernel.pdf.tagutils.DefaultAccessibilityProperties;
/*     */ import com.itextpdf.layout.IPropertyContainer;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class TaggingDummyElement
/*     */   implements IAccessibleElement, IPropertyContainer
/*     */ {
/*     */   private DefaultAccessibilityProperties properties;
/*     */   private Object id;
/*     */   
/*     */   public TaggingDummyElement(String role) {
/*  56 */     this.properties = new DefaultAccessibilityProperties(role);
/*     */   }
/*     */ 
/*     */   
/*     */   public AccessibilityProperties getAccessibilityProperties() {
/*  61 */     return (AccessibilityProperties)this.properties;
/*     */   }
/*     */ 
/*     */   
/*     */   public <T1> T1 getProperty(int property) {
/*  66 */     if (property == 109) {
/*  67 */       return (T1)this.id;
/*     */     }
/*  69 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setProperty(int property, Object value) {
/*  74 */     if (property == 109) {
/*  75 */       this.id = value;
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean hasProperty(int property) {
/*  81 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean hasOwnProperty(int property) {
/*  86 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ 
/*     */   
/*     */   public <T1> T1 getOwnProperty(int property) {
/*  91 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ 
/*     */   
/*     */   public <T1> T1 getDefaultProperty(int property) {
/*  96 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ 
/*     */   
/*     */   public void deleteOwnProperty(int property) {
/* 101 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/layout/tagging/TaggingDummyElement.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */