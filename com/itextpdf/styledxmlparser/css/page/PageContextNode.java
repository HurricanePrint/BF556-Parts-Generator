/*     */ package com.itextpdf.styledxmlparser.css.page;
/*     */ 
/*     */ import com.itextpdf.styledxmlparser.css.CssContextNode;
/*     */ import com.itextpdf.styledxmlparser.node.INode;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
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
/*     */ public class PageContextNode
/*     */   extends CssContextNode
/*     */ {
/*     */   private String pageTypeName;
/*     */   private List<String> pageClasses;
/*     */   
/*     */   public PageContextNode() {
/*  67 */     this(null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PageContextNode(INode parentNode) {
/*  76 */     super(parentNode);
/*  77 */     this.pageClasses = new ArrayList<>();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PageContextNode addPageClass(String pageClass) {
/*  87 */     this.pageClasses.add(pageClass.toLowerCase());
/*  88 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getPageTypeName() {
/*  97 */     return this.pageTypeName;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PageContextNode setPageTypeName(String pageTypeName) {
/* 107 */     this.pageTypeName = pageTypeName;
/* 108 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List<String> getPageClasses() {
/* 117 */     return Collections.unmodifiableList(this.pageClasses);
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/styledxmlparser/css/page/PageContextNode.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */