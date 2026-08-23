/*     */ package com.itextpdf.kernel.pdf.action;
/*     */ 
/*     */ import com.itextpdf.kernel.PdfException;
/*     */ import com.itextpdf.kernel.pdf.PdfArray;
/*     */ import com.itextpdf.kernel.pdf.PdfBoolean;
/*     */ import com.itextpdf.kernel.pdf.PdfDictionary;
/*     */ import com.itextpdf.kernel.pdf.PdfName;
/*     */ import com.itextpdf.kernel.pdf.PdfNumber;
/*     */ import com.itextpdf.kernel.pdf.PdfObject;
/*     */ import com.itextpdf.kernel.pdf.PdfObjectWrapper;
/*     */ import com.itextpdf.kernel.pdf.PdfStream;
/*     */ import com.itextpdf.kernel.pdf.PdfString;
/*     */ import com.itextpdf.kernel.pdf.annot.PdfAnnotation;
/*     */ import com.itextpdf.kernel.pdf.filespec.PdfFileSpec;
/*     */ import com.itextpdf.kernel.pdf.filespec.PdfStringFS;
/*     */ import com.itextpdf.kernel.pdf.navigation.PdfDestination;
/*     */ import com.itextpdf.kernel.pdf.navigation.PdfExplicitRemoteGoToDestination;
/*     */ import com.itextpdf.kernel.pdf.navigation.PdfStringDestination;
/*     */ import java.util.List;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class PdfAction
/*     */   extends PdfObjectWrapper<PdfDictionary>
/*     */ {
/*     */   private static final long serialVersionUID = -3945353673249710860L;
/*     */   public static final int SUBMIT_EXCLUDE = 1;
/*     */   public static final int SUBMIT_INCLUDE_NO_VALUE_FIELDS = 2;
/*     */   public static final int SUBMIT_HTML_FORMAT = 4;
/*     */   public static final int SUBMIT_HTML_GET = 8;
/*     */   public static final int SUBMIT_COORDINATES = 16;
/*     */   public static final int SUBMIT_XFDF = 32;
/*     */   public static final int SUBMIT_INCLUDE_APPEND_SAVES = 64;
/*     */   public static final int SUBMIT_INCLUDE_ANNOTATIONS = 128;
/*     */   public static final int SUBMIT_PDF = 256;
/*     */   public static final int SUBMIT_CANONICAL_FORMAT = 512;
/*     */   public static final int SUBMIT_EXCL_NON_USER_ANNOTS = 1024;
/*     */   public static final int SUBMIT_EXCL_F_KEY = 2048;
/*     */   public static final int SUBMIT_EMBED_FORM = 8196;
/*     */   public static final int RESET_EXCLUDE = 1;
/*     */   
/*     */   public PdfAction() {
/* 139 */     this(new PdfDictionary());
/* 140 */     put(PdfName.Type, (PdfObject)PdfName.Action);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PdfAction(PdfDictionary pdfObject) {
/* 150 */     super((PdfObject)pdfObject);
/* 151 */     markObjectAsIndirect(getPdfObject());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static PdfAction createGoTo(PdfDestination destination) {
/* 161 */     validateNotRemoteDestination(destination);
/* 162 */     return (new PdfAction()).put(PdfName.S, (PdfObject)PdfName.GoTo).put(PdfName.D, destination.getPdfObject());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static PdfAction createGoTo(String destination) {
/* 172 */     return createGoTo((PdfDestination)new PdfStringDestination(destination));
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
/*     */   public static PdfAction createGoToR(PdfFileSpec fileSpec, PdfDestination destination, boolean newWindow) {
/* 184 */     return createGoToR(fileSpec, destination).put(PdfName.NewWindow, (PdfObject)PdfBoolean.valueOf(newWindow));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static PdfAction createGoToR(PdfFileSpec fileSpec, PdfDestination destination) {
/* 195 */     validateRemoteDestination(destination);
/* 196 */     return (new PdfAction()).put(PdfName.S, (PdfObject)PdfName.GoToR).put(PdfName.F, fileSpec.getPdfObject())
/* 197 */       .put(PdfName.D, destination.getPdfObject());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static PdfAction createGoToR(String filename, int pageNum) {
/* 208 */     return createGoToR(filename, pageNum, false);
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
/*     */   public static PdfAction createGoToR(String filename, int pageNum, boolean newWindow) {
/* 220 */     return createGoToR((PdfFileSpec)new PdfStringFS(filename), (PdfDestination)PdfExplicitRemoteGoToDestination.createFitH(pageNum, 10000.0F), newWindow);
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
/*     */   public static PdfAction createGoToR(String filename, String destination, boolean newWindow) {
/* 232 */     return createGoToR((PdfFileSpec)new PdfStringFS(filename), (PdfDestination)new PdfStringDestination(destination), newWindow);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static PdfAction createGoToR(String filename, String destination) {
/* 243 */     return createGoToR(filename, destination, false);
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
/*     */ 
/*     */ 
/*     */   
/*     */   public static PdfAction createGoToE(PdfDestination destination, boolean newWindow, PdfTarget targetDictionary) {
/* 258 */     return createGoToE((PdfFileSpec)null, destination, newWindow, targetDictionary);
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
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static PdfAction createGoToE(PdfFileSpec fileSpec, PdfDestination destination, boolean newWindow, PdfTarget targetDictionary) {
/* 274 */     PdfAction action = (new PdfAction()).put(PdfName.S, (PdfObject)PdfName.GoToE).put(PdfName.NewWindow, (PdfObject)PdfBoolean.valueOf(newWindow));
/* 275 */     if (fileSpec != null) {
/* 276 */       action.put(PdfName.F, fileSpec.getPdfObject());
/*     */     }
/* 278 */     if (destination != null) {
/* 279 */       validateRemoteDestination(destination);
/* 280 */       action.put(PdfName.D, destination.getPdfObject());
/*     */     } else {
/* 282 */       LoggerFactory.getLogger(PdfAction.class).warn("No destination in the target was specified for action. Destination entry is mandatory for embedded go-to actions.");
/*     */     } 
/* 284 */     if (targetDictionary != null) {
/* 285 */       action.put(PdfName.T, targetDictionary.getPdfObject());
/*     */     }
/* 287 */     return action;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static PdfAction createLaunch(PdfFileSpec fileSpec, boolean newWindow) {
/* 298 */     return createLaunch(fileSpec).put(PdfName.NewWindow, (PdfObject)new PdfBoolean(newWindow));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static PdfAction createLaunch(PdfFileSpec fileSpec) {
/* 308 */     PdfAction action = (new PdfAction()).put(PdfName.S, (PdfObject)PdfName.Launch);
/* 309 */     if (fileSpec != null) {
/* 310 */       action.put(PdfName.F, fileSpec.getPdfObject());
/*     */     }
/* 312 */     return action;
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
/*     */ 
/*     */ 
/*     */   
/*     */   public static PdfAction createThread(PdfFileSpec fileSpec, PdfObject destinationThread, PdfObject bead) {
/* 327 */     PdfAction action = (new PdfAction()).put(PdfName.S, (PdfObject)PdfName.Launch).put(PdfName.D, destinationThread).put(PdfName.B, bead);
/* 328 */     if (fileSpec != null) {
/* 329 */       action.put(PdfName.F, fileSpec.getPdfObject());
/*     */     }
/* 331 */     return action;
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
/*     */   
/*     */   public static PdfAction createThread(PdfFileSpec fileSpec) {
/* 344 */     return createThread(fileSpec, (PdfObject)null, (PdfObject)null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static PdfAction createURI(String uri) {
/* 354 */     return createURI(uri, false);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static PdfAction createURI(String uri, boolean isMap) {
/* 365 */     return (new PdfAction()).put(PdfName.S, (PdfObject)PdfName.URI).put(PdfName.URI, (PdfObject)new PdfString(uri)).put(PdfName.IsMap, (PdfObject)PdfBoolean.valueOf(isMap));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static PdfAction createSound(PdfStream sound) {
/* 375 */     return (new PdfAction()).put(PdfName.S, (PdfObject)PdfName.Sound).put(PdfName.Sound, (PdfObject)sound);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static PdfAction createSound(PdfStream sound, float volume, boolean synchronous, boolean repeat, boolean mix) {
/* 393 */     if (volume < -1.0F || volume > 1.0F) {
/* 394 */       throw new IllegalArgumentException("volume");
/*     */     }
/* 396 */     return (new PdfAction()).put(PdfName.S, (PdfObject)PdfName.Sound).put(PdfName.Sound, (PdfObject)sound)
/* 397 */       .put(PdfName.Volume, (PdfObject)new PdfNumber(volume)).put(PdfName.Synchronous, (PdfObject)PdfBoolean.valueOf(synchronous))
/* 398 */       .put(PdfName.Repeat, (PdfObject)PdfBoolean.valueOf(repeat)).put(PdfName.Mix, (PdfObject)PdfBoolean.valueOf(mix));
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
/*     */ 
/*     */   
/*     */   public static PdfAction createMovie(PdfAnnotation annotation, String title, PdfName operation) {
/* 412 */     PdfAction action = (new PdfAction()).put(PdfName.S, (PdfObject)PdfName.Movie).put(PdfName.T, (PdfObject)new PdfString(title)).put(PdfName.Operation, (PdfObject)operation);
/* 413 */     if (annotation != null) {
/* 414 */       action.put(PdfName.Annotation, annotation.getPdfObject());
/*     */     }
/* 416 */     return action;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static PdfAction createHide(PdfAnnotation annotation, boolean hidden) {
/* 427 */     return (new PdfAction()).put(PdfName.S, (PdfObject)PdfName.Hide).put(PdfName.T, annotation.getPdfObject())
/* 428 */       .put(PdfName.H, (PdfObject)PdfBoolean.valueOf(hidden));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static PdfAction createHide(PdfAnnotation[] annotations, boolean hidden) {
/* 439 */     return (new PdfAction()).put(PdfName.S, (PdfObject)PdfName.Hide).put(PdfName.T, (PdfObject)getPdfArrayFromAnnotationsList(annotations))
/* 440 */       .put(PdfName.H, (PdfObject)PdfBoolean.valueOf(hidden));
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
/*     */   public static PdfAction createHide(String text, boolean hidden) {
/* 452 */     return (new PdfAction()).put(PdfName.S, (PdfObject)PdfName.Hide).put(PdfName.T, (PdfObject)new PdfString(text))
/* 453 */       .put(PdfName.H, (PdfObject)PdfBoolean.valueOf(hidden));
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
/*     */   public static PdfAction createHide(String[] text, boolean hidden) {
/* 465 */     return (new PdfAction()).put(PdfName.S, (PdfObject)PdfName.Hide).put(PdfName.T, (PdfObject)getArrayFromStringList(text))
/* 466 */       .put(PdfName.H, (PdfObject)PdfBoolean.valueOf(hidden));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static PdfAction createNamed(PdfName namedAction) {
/* 477 */     return (new PdfAction()).put(PdfName.S, (PdfObject)PdfName.Named).put(PdfName.N, (PdfObject)namedAction);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static PdfAction createSetOcgState(List<PdfActionOcgState> states) {
/* 487 */     return createSetOcgState(states, false);
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
/*     */   public static PdfAction createSetOcgState(List<PdfActionOcgState> states, boolean preserveRb) {
/* 499 */     PdfArray stateArr = new PdfArray();
/* 500 */     for (PdfActionOcgState state : states)
/* 501 */       stateArr.addAll(state.getObjectList()); 
/* 502 */     return (new PdfAction()).put(PdfName.S, (PdfObject)PdfName.SetOCGState).put(PdfName.State, (PdfObject)stateArr).put(PdfName.PreserveRB, (PdfObject)PdfBoolean.valueOf(preserveRb));
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
/*     */   
/*     */   public static PdfAction createRendition(String file, PdfFileSpec fileSpec, String mimeType, PdfAnnotation screenAnnotation) {
/* 515 */     return (new PdfAction()).put(PdfName.S, (PdfObject)PdfName.Rendition)
/* 516 */       .put(PdfName.OP, (PdfObject)new PdfNumber(0)).put(PdfName.AN, screenAnnotation.getPdfObject())
/* 517 */       .put(PdfName.R, (new PdfRendition(file, fileSpec, mimeType)).getPdfObject());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static PdfAction createJavaScript(String javaScript) {
/* 527 */     return (new PdfAction()).put(PdfName.S, (PdfObject)PdfName.JavaScript).put(PdfName.JS, (PdfObject)new PdfString(javaScript));
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
/*     */ 
/*     */ 
/*     */   
/*     */   public static PdfAction createSubmitForm(String file, Object[] names, int flags) {
/* 542 */     PdfAction action = new PdfAction();
/* 543 */     action.put(PdfName.S, (PdfObject)PdfName.SubmitForm);
/*     */     
/* 545 */     PdfDictionary urlFileSpec = new PdfDictionary();
/* 546 */     urlFileSpec.put(PdfName.F, (PdfObject)new PdfString(file));
/* 547 */     urlFileSpec.put(PdfName.FS, (PdfObject)PdfName.URL);
/* 548 */     action.put(PdfName.F, (PdfObject)urlFileSpec);
/*     */     
/* 550 */     if (names != null) {
/* 551 */       action.put(PdfName.Fields, (PdfObject)buildArray(names));
/*     */     }
/* 553 */     action.put(PdfName.Flags, (PdfObject)new PdfNumber(flags));
/* 554 */     return action;
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
/*     */   
/*     */   public static PdfAction createResetForm(Object[] names, int flags) {
/* 567 */     PdfAction action = new PdfAction();
/* 568 */     action.put(PdfName.S, (PdfObject)PdfName.ResetForm);
/* 569 */     if (names != null) {
/* 570 */       action.put(PdfName.Fields, (PdfObject)buildArray(names));
/*     */     }
/* 572 */     action.put(PdfName.Flags, (PdfObject)new PdfNumber(flags));
/* 573 */     return action;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void setAdditionalAction(PdfObjectWrapper<PdfDictionary> wrapper, PdfName key, PdfAction action) {
/*     */     PdfDictionary dic;
/* 585 */     PdfObject obj = ((PdfDictionary)wrapper.getPdfObject()).get(PdfName.AA);
/* 586 */     boolean aaExists = (obj != null && obj.isDictionary());
/* 587 */     if (aaExists) {
/* 588 */       dic = (PdfDictionary)obj;
/*     */     } else {
/* 590 */       dic = new PdfDictionary();
/*     */     } 
/* 592 */     dic.put(key, action.getPdfObject());
/* 593 */     dic.setModified();
/* 594 */     ((PdfDictionary)wrapper.getPdfObject()).put(PdfName.AA, (PdfObject)dic);
/* 595 */     if (!aaExists || !dic.isIndirect()) {
/* 596 */       ((PdfDictionary)wrapper.getPdfObject()).setModified();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void next(PdfAction nextAction) {
/* 606 */     PdfObject currentNextAction = ((PdfDictionary)getPdfObject()).get(PdfName.Next);
/* 607 */     if (currentNextAction == null) {
/* 608 */       put(PdfName.Next, nextAction.getPdfObject());
/* 609 */     } else if (currentNextAction.isDictionary()) {
/* 610 */       PdfArray array = new PdfArray(currentNextAction);
/* 611 */       array.add(nextAction.getPdfObject());
/* 612 */       put(PdfName.Next, (PdfObject)array);
/*     */     } else {
/* 614 */       ((PdfArray)currentNextAction).add(nextAction.getPdfObject());
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
/*     */   
/*     */   public PdfAction put(PdfName key, PdfObject value) {
/* 627 */     ((PdfDictionary)getPdfObject()).put(key, value);
/* 628 */     setModified();
/* 629 */     return this;
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
/*     */   public void flush() {
/* 641 */     super.flush();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean isWrappedObjectMustBeIndirect() {
/* 649 */     return true;
/*     */   }
/*     */   
/*     */   private static PdfArray getPdfArrayFromAnnotationsList(PdfAnnotation[] wrappers) {
/* 653 */     PdfArray arr = new PdfArray();
/* 654 */     for (PdfAnnotation wrapper : wrappers) {
/* 655 */       arr.add(wrapper.getPdfObject());
/*     */     }
/* 657 */     return arr;
/*     */   }
/*     */   
/*     */   private static PdfArray getArrayFromStringList(String[] strings) {
/* 661 */     PdfArray arr = new PdfArray();
/* 662 */     for (String string : strings) {
/* 663 */       arr.add((PdfObject)new PdfString(string));
/*     */     }
/* 665 */     return arr;
/*     */   }
/*     */   
/*     */   private static PdfArray buildArray(Object[] names) {
/* 669 */     PdfArray array = new PdfArray();
/* 670 */     for (Object obj : names) {
/* 671 */       if (obj instanceof String) {
/* 672 */         array.add((PdfObject)new PdfString((String)obj));
/* 673 */       } else if (obj instanceof PdfAnnotation) {
/* 674 */         array.add(((PdfAnnotation)obj).getPdfObject());
/*     */       } else {
/* 676 */         throw new PdfException("The array must contain string or PDFAnnotation");
/*     */       } 
/*     */     } 
/* 679 */     return array;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void validateRemoteDestination(PdfDestination destination) {
/* 687 */     if (destination instanceof com.itextpdf.kernel.pdf.navigation.PdfExplicitDestination) {
/* 688 */       PdfObject firstObj = ((PdfArray)destination.getPdfObject()).get(0);
/* 689 */       if (firstObj.isDictionary()) {
/* 690 */         throw new IllegalArgumentException("Explicit destinations shall specify page number in remote go-to actions instead of page dictionary");
/*     */       }
/* 692 */     } else if (destination instanceof com.itextpdf.kernel.pdf.navigation.PdfStructureDestination) {
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 697 */       PdfObject firstObj = ((PdfArray)destination.getPdfObject()).get(0);
/* 698 */       if (firstObj.isDictionary()) {
/* 699 */         PdfDictionary structElemObj = (PdfDictionary)firstObj;
/* 700 */         PdfString id = structElemObj.getAsString(PdfName.ID);
/* 701 */         if (id == null) {
/* 702 */           throw new IllegalArgumentException("Structure destinations shall specify structure element ID in remote go-to actions. Structure element that has no ID is specified instead");
/*     */         }
/* 704 */         LoggerFactory.getLogger(PdfAction.class).warn("Structure destinations shall specify structure element ID in remote go-to actions. Structure element has been replaced with its ID in the structure destination");
/* 705 */         ((PdfArray)destination.getPdfObject()).set(0, (PdfObject)id);
/* 706 */         destination.getPdfObject().setModified();
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static void validateNotRemoteDestination(PdfDestination destination) {
/* 713 */     if (destination instanceof PdfExplicitRemoteGoToDestination) {
/* 714 */       LoggerFactory.getLogger(PdfAction.class).warn("When destination's not associated with a Remote or Embedded Go-To action, it shall specify page dictionary instead of page number. Otherwise destination might be considered invalid");
/* 715 */     } else if (destination instanceof com.itextpdf.kernel.pdf.navigation.PdfExplicitDestination) {
/*     */ 
/*     */       
/* 718 */       PdfObject firstObj = ((PdfArray)destination.getPdfObject()).get(0);
/* 719 */       if (firstObj.isNumber())
/* 720 */         LoggerFactory.getLogger(PdfAction.class).warn("When destination's not associated with a Remote or Embedded Go-To action, it shall specify page dictionary instead of page number. Otherwise destination might be considered invalid"); 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/kernel/pdf/action/PdfAction.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */