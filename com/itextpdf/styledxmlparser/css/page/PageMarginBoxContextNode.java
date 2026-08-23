/*     */ package com.itextpdf.styledxmlparser.css.page;
/*     */ 
/*     */ import com.itextpdf.kernel.geom.Rectangle;
/*     */ import com.itextpdf.styledxmlparser.css.CssContextNode;
/*     */ import com.itextpdf.styledxmlparser.node.INode;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class PageMarginBoxContextNode
/*     */   extends CssContextNode
/*     */ {
/*     */   public static final String PAGE_MARGIN_BOX_TAG = "_064ef03_page-margin-box";
/*     */   private String marginBoxName;
/*     */   private Rectangle pageMarginBoxRectangle;
/*     */   private Rectangle containingBlockForMarginBox;
/*     */   
/*     */   public PageMarginBoxContextNode(INode parentNode, String marginBoxName) {
/*  70 */     super(parentNode);
/*  71 */     this.marginBoxName = marginBoxName;
/*  72 */     if (!(parentNode instanceof PageContextNode)) {
/*  73 */       throw new IllegalArgumentException("Page-margin-box context node shall have a page context node as parent.");
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getMarginBoxName() {
/*  83 */     return this.marginBoxName;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setPageMarginBoxRectangle(Rectangle pageMarginBoxRectangle) {
/*  91 */     this.pageMarginBoxRectangle = pageMarginBoxRectangle;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Rectangle getPageMarginBoxRectangle() {
/*  99 */     return this.pageMarginBoxRectangle;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setContainingBlockForMarginBox(Rectangle containingBlockForMarginBox) {
/* 109 */     this.containingBlockForMarginBox = containingBlockForMarginBox;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Rectangle getContainingBlockForMarginBox() {
/* 117 */     return this.containingBlockForMarginBox;
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/styledxmlparser/css/page/PageMarginBoxContextNode.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */