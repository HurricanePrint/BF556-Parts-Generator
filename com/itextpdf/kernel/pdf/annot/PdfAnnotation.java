/*      */ package com.itextpdf.kernel.pdf.annot;
/*      */ 
/*      */ import com.itextpdf.kernel.colors.Color;
/*      */ import com.itextpdf.kernel.geom.Rectangle;
/*      */ import com.itextpdf.kernel.pdf.PdfAnnotationBorder;
/*      */ import com.itextpdf.kernel.pdf.PdfArray;
/*      */ import com.itextpdf.kernel.pdf.PdfDictionary;
/*      */ import com.itextpdf.kernel.pdf.PdfDocument;
/*      */ import com.itextpdf.kernel.pdf.PdfIndirectReference;
/*      */ import com.itextpdf.kernel.pdf.PdfName;
/*      */ import com.itextpdf.kernel.pdf.PdfNumber;
/*      */ import com.itextpdf.kernel.pdf.PdfObject;
/*      */ import com.itextpdf.kernel.pdf.PdfObjectWrapper;
/*      */ import com.itextpdf.kernel.pdf.PdfPage;
/*      */ import com.itextpdf.kernel.pdf.PdfString;
/*      */ import com.itextpdf.kernel.pdf.filespec.PdfFileSpec;
/*      */ import com.itextpdf.kernel.pdf.layer.IPdfOCG;
/*      */ import org.slf4j.Logger;
/*      */ import org.slf4j.LoggerFactory;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public abstract class PdfAnnotation
/*      */   extends PdfObjectWrapper<PdfDictionary>
/*      */ {
/*      */   private static final long serialVersionUID = -6555705164241587799L;
/*      */   public static final int INVISIBLE = 1;
/*      */   public static final int HIDDEN = 2;
/*      */   public static final int PRINT = 4;
/*      */   public static final int NO_ZOOM = 8;
/*      */   public static final int NO_ROTATE = 16;
/*      */   public static final int NO_VIEW = 32;
/*      */   public static final int READ_ONLY = 64;
/*      */   public static final int LOCKED = 128;
/*      */   public static final int TOGGLE_NO_VIEW = 256;
/*      */   public static final int LOCKED_CONTENTS = 512;
/*  143 */   public static final PdfName HIGHLIGHT_NONE = PdfName.N;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  149 */   public static final PdfName HIGHLIGHT_INVERT = PdfName.I;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  155 */   public static final PdfName HIGHLIGHT_OUTLINE = PdfName.O;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  161 */   public static final PdfName HIGHLIGHT_PUSH = PdfName.P;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  167 */   public static final PdfName HIGHLIGHT_TOGGLE = PdfName.T;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  173 */   public static final PdfName STYLE_SOLID = PdfName.S;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  178 */   public static final PdfName STYLE_DASHED = PdfName.D;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  183 */   public static final PdfName STYLE_BEVELED = PdfName.B;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  188 */   public static final PdfName STYLE_INSET = PdfName.I;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  193 */   public static final PdfName STYLE_UNDERLINE = PdfName.U;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  200 */   public static final PdfString Marked = new PdfString("Marked");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  206 */   public static final PdfString Unmarked = new PdfString("Unmarked");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  212 */   public static final PdfString Accepted = new PdfString("Accepted");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  218 */   public static final PdfString Rejected = new PdfString("Rejected");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  224 */   public static final PdfString Canceled = new PdfString("Cancelled");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  230 */   public static final PdfString Completed = new PdfString("Completed");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  236 */   public static final PdfString None = new PdfString("None");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  243 */   public static final PdfString MarkedModel = new PdfString("Marked");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  249 */   public static final PdfString ReviewModel = new PdfString("Review");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected PdfPage page;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static PdfAnnotation makeAnnotation(PdfObject pdfObject) {
/*  263 */     PdfAnnotation annotation = null;
/*  264 */     if (pdfObject.isIndirectReference())
/*  265 */       pdfObject = ((PdfIndirectReference)pdfObject).getRefersTo(); 
/*  266 */     if (pdfObject.isDictionary()) {
/*  267 */       PdfDictionary dictionary = (PdfDictionary)pdfObject;
/*  268 */       PdfName subtype = dictionary.getAsName(PdfName.Subtype);
/*  269 */       if (PdfName.Link.equals(subtype)) {
/*  270 */         annotation = new PdfLinkAnnotation((PdfDictionary)pdfObject);
/*  271 */       } else if (PdfName.Popup.equals(subtype)) {
/*  272 */         annotation = new PdfPopupAnnotation((PdfDictionary)pdfObject);
/*  273 */       } else if (PdfName.Widget.equals(subtype)) {
/*  274 */         annotation = new PdfWidgetAnnotation((PdfDictionary)pdfObject);
/*  275 */       } else if (PdfName.Screen.equals(subtype)) {
/*  276 */         annotation = new PdfScreenAnnotation((PdfDictionary)pdfObject);
/*  277 */       } else if (PdfName._3D.equals(subtype)) {
/*  278 */         annotation = new Pdf3DAnnotation((PdfDictionary)pdfObject);
/*  279 */       } else if (PdfName.Highlight.equals(subtype) || PdfName.Underline.equals(subtype) || PdfName.Squiggly.equals(subtype) || PdfName.StrikeOut.equals(subtype)) {
/*  280 */         annotation = new PdfTextMarkupAnnotation((PdfDictionary)pdfObject);
/*  281 */       } else if (PdfName.Caret.equals(subtype)) {
/*  282 */         annotation = new PdfCaretAnnotation((PdfDictionary)pdfObject);
/*  283 */       } else if (PdfName.Text.equals(subtype)) {
/*  284 */         annotation = new PdfTextAnnotation((PdfDictionary)pdfObject);
/*  285 */       } else if (PdfName.Sound.equals(subtype)) {
/*  286 */         annotation = new PdfSoundAnnotation((PdfDictionary)pdfObject);
/*  287 */       } else if (PdfName.Stamp.equals(subtype)) {
/*  288 */         annotation = new PdfStampAnnotation((PdfDictionary)pdfObject);
/*  289 */       } else if (PdfName.FileAttachment.equals(subtype)) {
/*  290 */         annotation = new PdfFileAttachmentAnnotation((PdfDictionary)pdfObject);
/*  291 */       } else if (PdfName.Ink.equals(subtype)) {
/*  292 */         annotation = new PdfInkAnnotation((PdfDictionary)pdfObject);
/*  293 */       } else if (PdfName.PrinterMark.equals(subtype)) {
/*  294 */         annotation = new PdfPrinterMarkAnnotation((PdfDictionary)pdfObject);
/*  295 */       } else if (PdfName.TrapNet.equals(subtype)) {
/*  296 */         annotation = new PdfTrapNetworkAnnotation((PdfDictionary)pdfObject);
/*  297 */       } else if (PdfName.FreeText.equals(subtype)) {
/*  298 */         annotation = new PdfFreeTextAnnotation((PdfDictionary)pdfObject);
/*  299 */       } else if (PdfName.Square.equals(subtype)) {
/*  300 */         annotation = new PdfSquareAnnotation((PdfDictionary)pdfObject);
/*  301 */       } else if (PdfName.Circle.equals(subtype)) {
/*  302 */         annotation = new PdfCircleAnnotation((PdfDictionary)pdfObject);
/*  303 */       } else if (PdfName.Line.equals(subtype)) {
/*  304 */         annotation = new PdfLineAnnotation((PdfDictionary)pdfObject);
/*  305 */       } else if (PdfName.Polygon.equals(subtype)) {
/*  306 */         annotation = new PdfPolygonAnnotation((PdfDictionary)pdfObject);
/*  307 */       } else if (PdfName.PolyLine.equals(subtype)) {
/*  308 */         annotation = new PdfPolylineAnnotation((PdfDictionary)pdfObject);
/*  309 */       } else if (PdfName.Redact.equals(subtype)) {
/*  310 */         annotation = new PdfRedactAnnotation((PdfDictionary)pdfObject);
/*  311 */       } else if (PdfName.Watermark.equals(subtype)) {
/*  312 */         annotation = new PdfWatermarkAnnotation((PdfDictionary)pdfObject);
/*      */       } else {
/*  314 */         annotation = new PdfUnknownAnnotation((PdfDictionary)pdfObject);
/*      */       } 
/*      */     } 
/*  317 */     return annotation;
/*      */   }
/*      */   
/*      */   protected PdfAnnotation(Rectangle rect) {
/*  321 */     this(new PdfDictionary());
/*  322 */     put(PdfName.Rect, (PdfObject)new PdfArray(rect));
/*  323 */     put(PdfName.Subtype, (PdfObject)getSubtype());
/*      */   }
/*      */   
/*      */   protected PdfAnnotation(PdfDictionary pdfObject) {
/*  327 */     super((PdfObject)pdfObject);
/*  328 */     markObjectAsIndirect(getPdfObject());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public abstract PdfName getSubtype();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setLayer(IPdfOCG layer) {
/*  345 */     ((PdfDictionary)getPdfObject()).put(PdfName.OC, (PdfObject)layer.getIndirectReference());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PdfString getContents() {
/*  355 */     return ((PdfDictionary)getPdfObject()).getAsString(PdfName.Contents);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PdfAnnotation setContents(PdfString contents) {
/*  366 */     return put(PdfName.Contents, (PdfObject)contents);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PdfAnnotation setContents(String contents) {
/*  377 */     return setContents(new PdfString(contents, "UnicodeBig"));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PdfDictionary getPageObject() {
/*  387 */     return ((PdfDictionary)getPdfObject()).getAsDictionary(PdfName.P);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PdfPage getPage() {
/*      */     PdfIndirectReference annotationIndirectReference;
/*  397 */     if (this.page == null && (annotationIndirectReference = ((PdfDictionary)getPdfObject()).getIndirectReference()) != null) {
/*  398 */       PdfDocument doc = annotationIndirectReference.getDocument();
/*      */       
/*  400 */       PdfDictionary pageDictionary = getPageObject();
/*  401 */       if (pageDictionary != null) {
/*  402 */         this.page = doc.getPage(pageDictionary);
/*      */       } else {
/*  404 */         for (int i = 1; i <= doc.getNumberOfPages(); i++) {
/*  405 */           PdfPage docPage = doc.getPage(i);
/*  406 */           if (!docPage.isFlushed()) {
/*  407 */             for (PdfAnnotation annot : docPage.getAnnotations()) {
/*  408 */               if (annotationIndirectReference.equals(((PdfDictionary)annot.getPdfObject()).getIndirectReference())) {
/*  409 */                 this.page = docPage;
/*      */                 
/*      */                 break;
/*      */               } 
/*      */             } 
/*      */           }
/*      */         } 
/*      */       } 
/*      */     } 
/*      */     
/*  419 */     return this.page;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PdfAnnotation setPage(PdfPage page) {
/*  432 */     this.page = page;
/*      */     
/*  434 */     return put(PdfName.P, (PdfObject)((PdfDictionary)page.getPdfObject()).getIndirectReference());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PdfString getName() {
/*  445 */     return ((PdfDictionary)getPdfObject()).getAsString(PdfName.NM);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PdfAnnotation setName(PdfString name) {
/*  456 */     return put(PdfName.NM, (PdfObject)name);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PdfString getDate() {
/*  466 */     return ((PdfDictionary)getPdfObject()).getAsString(PdfName.M);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PdfAnnotation setDate(PdfString date) {
/*  477 */     return put(PdfName.M, (PdfObject)date);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getFlags() {
/*  488 */     PdfNumber f = ((PdfDictionary)getPdfObject()).getAsNumber(PdfName.F);
/*  489 */     if (f != null) {
/*  490 */       return f.intValue();
/*      */     }
/*  492 */     return 0;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PdfAnnotation setFlags(int flags) {
/*  504 */     return put(PdfName.F, (PdfObject)new PdfNumber(flags));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PdfAnnotation setFlag(int flag) {
/*  555 */     int flags = getFlags();
/*  556 */     flags |= flag;
/*  557 */     return setFlags(flags);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PdfAnnotation resetFlag(int flag) {
/*  567 */     int flags = getFlags();
/*  568 */     flags &= flag ^ 0xFFFFFFFF;
/*  569 */     return setFlags(flags);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean hasFlag(int flag) {
/*  582 */     if (flag == 0) {
/*  583 */       return false;
/*      */     }
/*  585 */     if ((flag & flag - 1) != 0) {
/*  586 */       throw new IllegalArgumentException("Only one flag must be checked at once.");
/*      */     }
/*      */     
/*  589 */     int flags = getFlags();
/*  590 */     return ((flags & flag) != 0);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PdfDictionary getAppearanceDictionary() {
/*  601 */     return ((PdfDictionary)getPdfObject()).getAsDictionary(PdfName.AP);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PdfDictionary getAppearanceObject(PdfName appearanceType) {
/*  615 */     PdfDictionary ap = getAppearanceDictionary();
/*  616 */     if (ap != null) {
/*  617 */       PdfObject apObject = ap.get(appearanceType);
/*  618 */       if (apObject instanceof PdfDictionary) {
/*  619 */         return (PdfDictionary)apObject;
/*      */       }
/*      */     } 
/*  622 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PdfDictionary getNormalAppearanceObject() {
/*  633 */     return getAppearanceObject(PdfName.N);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PdfDictionary getRolloverAppearanceObject() {
/*  645 */     return getAppearanceObject(PdfName.R);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PdfDictionary getDownAppearanceObject() {
/*  657 */     return getAppearanceObject(PdfName.D);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PdfAnnotation setAppearance(PdfName appearanceType, PdfDictionary appearance) {
/*  670 */     PdfDictionary ap = getAppearanceDictionary();
/*  671 */     if (ap == null) {
/*  672 */       ap = new PdfDictionary();
/*  673 */       ((PdfDictionary)getPdfObject()).put(PdfName.AP, (PdfObject)ap);
/*      */     } 
/*  675 */     ap.put(appearanceType, (PdfObject)appearance);
/*  676 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PdfAnnotation setNormalAppearance(PdfDictionary appearance) {
/*  687 */     return setAppearance(PdfName.N, appearance);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PdfAnnotation setRolloverAppearance(PdfDictionary appearance) {
/*  698 */     return setAppearance(PdfName.R, appearance);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PdfAnnotation setDownAppearance(PdfDictionary appearance) {
/*  709 */     return setAppearance(PdfName.D, appearance);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PdfAnnotation setAppearance(PdfName appearanceType, PdfAnnotationAppearance appearance) {
/*  723 */     return setAppearance(appearanceType, (PdfDictionary)appearance.getPdfObject());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PdfAnnotation setNormalAppearance(PdfAnnotationAppearance appearance) {
/*  735 */     return setAppearance(PdfName.N, appearance);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PdfAnnotation setRolloverAppearance(PdfAnnotationAppearance appearance) {
/*  747 */     return setAppearance(PdfName.R, appearance);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PdfAnnotation setDownAppearance(PdfAnnotationAppearance appearance) {
/*  759 */     return setAppearance(PdfName.D, appearance);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PdfName getAppearanceState() {
/*  770 */     return ((PdfDictionary)getPdfObject()).getAsName(PdfName.AS);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PdfAnnotation setAppearanceState(PdfName as) {
/*  782 */     return put(PdfName.AS, (PdfObject)as);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PdfArray getBorder() {
/*  797 */     return ((PdfDictionary)getPdfObject()).getAsArray(PdfName.Border);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PdfAnnotation setBorder(PdfAnnotationBorder border) {
/*  808 */     return put(PdfName.Border, border.getPdfObject());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PdfAnnotation setBorder(PdfArray border) {
/*  819 */     return put(PdfName.Border, (PdfObject)border);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PdfArray getColorObject() {
/*  840 */     return ((PdfDictionary)getPdfObject()).getAsArray(PdfName.C);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PdfAnnotation setColor(PdfArray color) {
/*  851 */     return put(PdfName.C, (PdfObject)color);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PdfAnnotation setColor(float[] color) {
/*  862 */     return setColor(new PdfArray(color));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PdfAnnotation setColor(Color color) {
/*  874 */     return setColor(new PdfArray(color.getColorValue()));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getStructParentIndex() {
/*  884 */     PdfNumber n = ((PdfDictionary)getPdfObject()).getAsNumber(PdfName.StructParent);
/*  885 */     if (n == null) {
/*  886 */       return -1;
/*      */     }
/*  888 */     return n.intValue();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PdfAnnotation setStructParentIndex(int structParentIndex) {
/*  902 */     return put(PdfName.StructParent, (PdfObject)new PdfNumber(structParentIndex));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PdfAnnotation setTitle(PdfString title) {
/*  912 */     return put(PdfName.T, (PdfObject)title);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PdfString getTitle() {
/*  923 */     return ((PdfDictionary)getPdfObject()).getAsString(PdfName.T);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PdfAnnotation setRectangle(PdfArray array) {
/*  934 */     return put(PdfName.Rect, (PdfObject)array);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PdfArray getRectangle() {
/*  944 */     return ((PdfDictionary)getPdfObject()).getAsArray(PdfName.Rect);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getLang() {
/*  955 */     PdfString lang = ((PdfDictionary)getPdfObject()).getAsString(PdfName.Lang);
/*  956 */     return (lang != null) ? lang.toUnicodeString() : null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PdfAnnotation setLang(String lang) {
/*  968 */     return put(PdfName.Lang, (PdfObject)new PdfString(lang, "UnicodeBig"));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PdfName getBlendMode() {
/*  977 */     return ((PdfDictionary)getPdfObject()).getAsName(PdfName.BM);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PdfAnnotation setBlendMode(PdfName blendMode) {
/*  987 */     return put(PdfName.BM, (PdfObject)blendMode);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public float getNonStrokingOpacity() {
/* 1000 */     PdfNumber nonStrokingOpacity = ((PdfDictionary)getPdfObject()).getAsNumber(PdfName.ca);
/* 1001 */     return (nonStrokingOpacity != null) ? nonStrokingOpacity.floatValue() : 1.0F;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PdfAnnotation setNonStrokingOpacity(float nonStrokingOpacity) {
/* 1015 */     return put(PdfName.ca, (PdfObject)new PdfNumber(nonStrokingOpacity));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public float getStrokingOpacity() {
/* 1027 */     PdfNumber strokingOpacity = ((PdfDictionary)getPdfObject()).getAsNumber(PdfName.CA);
/* 1028 */     return (strokingOpacity != null) ? strokingOpacity.floatValue() : 1.0F;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PdfAnnotation setStrokingOpacity(float strokingOpacity) {
/* 1041 */     return put(PdfName.CA, (PdfObject)new PdfNumber(strokingOpacity));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PdfAnnotation put(PdfName key, PdfObject value) {
/* 1054 */     ((PdfDictionary)getPdfObject()).put(key, value);
/* 1055 */     setModified();
/* 1056 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PdfAnnotation remove(PdfName key) {
/* 1066 */     ((PdfDictionary)getPdfObject()).remove(key);
/* 1067 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void addAssociatedFile(PdfFileSpec fs) {
/* 1082 */     if (null == ((PdfDictionary)fs.getPdfObject()).get(PdfName.AFRelationship)) {
/* 1083 */       Logger logger = LoggerFactory.getLogger(PdfAnnotation.class);
/* 1084 */       logger.error("For associated files their associated file specification dictionaries shall include the AFRelationship key.");
/*      */     } 
/* 1086 */     PdfArray afArray = ((PdfDictionary)getPdfObject()).getAsArray(PdfName.AF);
/* 1087 */     if (afArray == null) {
/* 1088 */       afArray = new PdfArray();
/* 1089 */       put(PdfName.AF, (PdfObject)afArray);
/*      */     } 
/* 1091 */     afArray.add(fs.getPdfObject());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PdfArray getAssociatedFiles(boolean create) {
/* 1101 */     PdfArray afArray = ((PdfDictionary)getPdfObject()).getAsArray(PdfName.AF);
/* 1102 */     if (afArray == null && create) {
/* 1103 */       afArray = new PdfArray();
/* 1104 */       put(PdfName.AF, (PdfObject)afArray);
/*      */     } 
/* 1106 */     return afArray;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void flush() {
/* 1119 */     super.flush();
/*      */   }
/*      */ 
/*      */   
/*      */   protected boolean isWrappedObjectMustBeIndirect() {
/* 1124 */     return true;
/*      */   }
/*      */   
/*      */   static class PdfUnknownAnnotation
/*      */     extends PdfAnnotation
/*      */   {
/*      */     protected PdfUnknownAnnotation(PdfDictionary pdfObject) {
/* 1131 */       super(pdfObject);
/*      */     }
/*      */ 
/*      */     
/*      */     public PdfName getSubtype() {
/* 1136 */       return ((PdfDictionary)getPdfObject()).getAsName(PdfName.Subtype);
/*      */     }
/*      */   }
/*      */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/kernel/pdf/annot/PdfAnnotation.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */