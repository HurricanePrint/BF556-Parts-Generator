/*     */ package com.itextpdf.kernel.pdf;
/*     */ 
/*     */ import com.itextpdf.kernel.PdfException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class PdfViewerPreferences
/*     */   extends PdfObjectWrapper<PdfDictionary>
/*     */ {
/*     */   private static final long serialVersionUID = -6885879361985241602L;
/*     */   
/*     */   public enum PdfViewerPreferencesConstants
/*     */   {
/*  56 */     USE_NONE,
/*     */ 
/*     */ 
/*     */     
/*  60 */     USE_OUTLINES,
/*     */ 
/*     */ 
/*     */     
/*  64 */     USE_THUMBS,
/*     */ 
/*     */ 
/*     */     
/*  68 */     USE_OC,
/*     */ 
/*     */ 
/*     */     
/*  72 */     LEFT_TO_RIGHT,
/*     */ 
/*     */ 
/*     */     
/*  76 */     RIGHT_TO_LEFT,
/*     */ 
/*     */ 
/*     */     
/*  80 */     MEDIA_BOX,
/*     */ 
/*     */ 
/*     */     
/*  84 */     CROP_BOX,
/*     */ 
/*     */ 
/*     */     
/*  88 */     BLEED_BOX,
/*     */ 
/*     */ 
/*     */     
/*  92 */     TRIM_BOX,
/*     */ 
/*     */ 
/*     */     
/*  96 */     ART_BOX,
/*     */ 
/*     */ 
/*     */     
/* 100 */     VIEW_AREA,
/*     */ 
/*     */ 
/*     */     
/* 104 */     VIEW_CLIP,
/*     */ 
/*     */ 
/*     */     
/* 108 */     PRINT_AREA,
/*     */ 
/*     */ 
/*     */     
/* 112 */     PRINT_CLIP,
/*     */ 
/*     */ 
/*     */     
/* 116 */     NONE,
/*     */ 
/*     */ 
/*     */     
/* 120 */     APP_DEFAULT,
/*     */ 
/*     */ 
/*     */     
/* 124 */     SIMPLEX,
/*     */ 
/*     */ 
/*     */     
/* 128 */     DUPLEX_FLIP_SHORT_EDGE,
/*     */ 
/*     */ 
/*     */     
/* 132 */     DUPLEX_FLIP_LONG_EDGE;
/*     */   }
/*     */   
/*     */   public PdfViewerPreferences() {
/* 136 */     this(new PdfDictionary());
/*     */   }
/*     */   
/*     */   public PdfViewerPreferences(PdfDictionary pdfObject) {
/* 140 */     super(pdfObject);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PdfViewerPreferences setHideToolbar(boolean hideToolbar) {
/* 150 */     return put(PdfName.HideToolbar, PdfBoolean.valueOf(hideToolbar));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PdfViewerPreferences setHideMenubar(boolean hideMenubar) {
/* 160 */     return put(PdfName.HideMenubar, PdfBoolean.valueOf(hideMenubar));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PdfViewerPreferences setHideWindowUI(boolean hideWindowUI) {
/* 170 */     return put(PdfName.HideWindowUI, PdfBoolean.valueOf(hideWindowUI));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PdfViewerPreferences setFitWindow(boolean fitWindow) {
/* 180 */     return put(PdfName.FitWindow, PdfBoolean.valueOf(fitWindow));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PdfViewerPreferences setCenterWindow(boolean centerWindow) {
/* 190 */     return put(PdfName.CenterWindow, PdfBoolean.valueOf(centerWindow));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PdfViewerPreferences setDisplayDocTitle(boolean displayDocTitle) {
/* 200 */     return put(PdfName.DisplayDocTitle, PdfBoolean.valueOf(displayDocTitle));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PdfViewerPreferences setNonFullScreenPageMode(PdfViewerPreferencesConstants nonFullScreenPageMode) {
/* 211 */     switch (nonFullScreenPageMode) {
/*     */       case USE_NONE:
/* 213 */         put(PdfName.NonFullScreenPageMode, PdfName.UseNone);
/*     */         break;
/*     */       case USE_OUTLINES:
/* 216 */         put(PdfName.NonFullScreenPageMode, PdfName.UseOutlines);
/*     */         break;
/*     */       case USE_THUMBS:
/* 219 */         put(PdfName.NonFullScreenPageMode, PdfName.UseThumbs);
/*     */         break;
/*     */       case USE_OC:
/* 222 */         put(PdfName.NonFullScreenPageMode, PdfName.UseOC);
/*     */         break;
/*     */     } 
/*     */     
/* 226 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PdfViewerPreferences setDirection(PdfViewerPreferencesConstants direction) {
/* 236 */     switch (direction) {
/*     */       case LEFT_TO_RIGHT:
/* 238 */         put(PdfName.Direction, PdfName.L2R);
/*     */         break;
/*     */       case RIGHT_TO_LEFT:
/* 241 */         put(PdfName.Direction, PdfName.R2L);
/*     */         break;
/*     */     } 
/*     */     
/* 245 */     return this;
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
/*     */   public PdfViewerPreferences setViewArea(PdfViewerPreferencesConstants pageBoundary) {
/* 257 */     return setPageBoundary(PdfViewerPreferencesConstants.VIEW_AREA, pageBoundary);
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
/*     */   public PdfViewerPreferences setViewClip(PdfViewerPreferencesConstants pageBoundary) {
/* 269 */     return setPageBoundary(PdfViewerPreferencesConstants.VIEW_CLIP, pageBoundary);
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
/*     */   public PdfViewerPreferences setPrintArea(PdfViewerPreferencesConstants pageBoundary) {
/* 281 */     return setPageBoundary(PdfViewerPreferencesConstants.PRINT_AREA, pageBoundary);
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
/*     */   public PdfViewerPreferences setPrintClip(PdfViewerPreferencesConstants pageBoundary) {
/* 293 */     return setPageBoundary(PdfViewerPreferencesConstants.PRINT_CLIP, pageBoundary);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PdfViewerPreferences setPrintScaling(PdfViewerPreferencesConstants printScaling) {
/* 304 */     switch (printScaling) {
/*     */       case NONE:
/* 306 */         put(PdfName.PrintScaling, PdfName.None);
/*     */         break;
/*     */       case APP_DEFAULT:
/* 309 */         put(PdfName.PrintScaling, PdfName.AppDefault);
/*     */         break;
/*     */     } 
/*     */ 
/*     */     
/* 314 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PdfViewerPreferences setDuplex(PdfViewerPreferencesConstants duplex) {
/* 325 */     switch (duplex) {
/*     */       case SIMPLEX:
/* 327 */         put(PdfName.Duplex, PdfName.Simplex);
/*     */         break;
/*     */       case DUPLEX_FLIP_SHORT_EDGE:
/* 330 */         put(PdfName.Duplex, PdfName.DuplexFlipShortEdge);
/*     */         break;
/*     */       case DUPLEX_FLIP_LONG_EDGE:
/* 333 */         put(PdfName.Duplex, PdfName.DuplexFlipLongEdge);
/*     */         break;
/*     */     } 
/*     */     
/* 337 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PdfViewerPreferences setPickTrayByPDFSize(boolean pickTrayByPdfSize) {
/* 347 */     return put(PdfName.PickTrayByPDFSize, PdfBoolean.valueOf(pickTrayByPdfSize));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PdfViewerPreferences setPrintPageRange(int[] printPageRange) {
/* 357 */     return put(PdfName.PrintPageRange, new PdfArray(printPageRange));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PdfViewerPreferences setNumCopies(int numCopies) {
/* 367 */     return put(PdfName.NumCopies, new PdfNumber(numCopies));
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
/*     */   public PdfViewerPreferences setEnforce(PdfArray enforce) {
/* 379 */     for (int i = 0; i < enforce.size(); i++) {
/* 380 */       PdfName curEnforce = enforce.getAsName(i);
/* 381 */       if (curEnforce == null)
/* 382 */         throw new IllegalArgumentException("Enforce array shall contain PdfName entries"); 
/* 383 */       if (PdfName.PrintScaling.equals(curEnforce)) {
/*     */ 
/*     */         
/* 386 */         PdfName curPrintScaling = getPdfObject().getAsName(PdfName.PrintScaling);
/* 387 */         if (curPrintScaling == null || PdfName.AppDefault.equals(curPrintScaling)) {
/* 388 */           throw new PdfException("/PrintScaling shall may appear in the Enforce array only if the corresponding entry in the viewer preferences dictionary specifies a valid value other than AppDefault");
/*     */         }
/*     */       } 
/*     */     } 
/* 392 */     return put(PdfName.Enforce, enforce);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PdfArray getEnforce() {
/* 403 */     return getPdfObject().getAsArray(PdfName.Enforce);
/*     */   }
/*     */   
/*     */   public PdfViewerPreferences put(PdfName key, PdfObject value) {
/* 407 */     getPdfObject().put(key, value);
/* 408 */     setModified();
/* 409 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean isWrappedObjectMustBeIndirect() {
/* 414 */     return false;
/*     */   }
/*     */   
/*     */   private PdfViewerPreferences setPageBoundary(PdfViewerPreferencesConstants viewerPreferenceType, PdfViewerPreferencesConstants pageBoundary) {
/* 418 */     PdfName type = null;
/* 419 */     switch (viewerPreferenceType) {
/*     */       case VIEW_AREA:
/* 421 */         type = PdfName.ViewArea;
/*     */         break;
/*     */       case VIEW_CLIP:
/* 424 */         type = PdfName.ViewClip;
/*     */         break;
/*     */       case PRINT_AREA:
/* 427 */         type = PdfName.PrintArea;
/*     */         break;
/*     */       case PRINT_CLIP:
/* 430 */         type = PdfName.PrintClip;
/*     */         break;
/*     */     } 
/*     */     
/* 434 */     if (type != null) {
/* 435 */       switch (pageBoundary) {
/*     */         case MEDIA_BOX:
/* 437 */           put(type, PdfName.MediaBox);
/*     */           break;
/*     */         case CROP_BOX:
/* 440 */           put(type, PdfName.CropBox);
/*     */           break;
/*     */         case BLEED_BOX:
/* 443 */           put(type, PdfName.BleedBox);
/*     */           break;
/*     */         case TRIM_BOX:
/* 446 */           put(type, PdfName.TrimBox);
/*     */           break;
/*     */         case ART_BOX:
/* 449 */           put(type, PdfName.ArtBox);
/*     */           break;
/*     */       } 
/*     */ 
/*     */     
/*     */     }
/* 455 */     return this;
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/kernel/pdf/PdfViewerPreferences.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */