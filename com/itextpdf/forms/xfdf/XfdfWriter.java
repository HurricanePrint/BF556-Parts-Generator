/*     */ package com.itextpdf.forms.xfdf;
/*     */ 
/*     */ import com.itextpdf.kernel.pdf.PdfName;
/*     */ import java.io.OutputStream;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.List;
/*     */ import javax.xml.parsers.ParserConfigurationException;
/*     */ import javax.xml.transform.TransformerException;
/*     */ import org.slf4j.Logger;
/*     */ import org.slf4j.LoggerFactory;
/*     */ import org.w3c.dom.Document;
/*     */ import org.w3c.dom.Element;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class XfdfWriter
/*     */ {
/*     */   private OutputStream outputStream;
/*  62 */   private static Logger logger = LoggerFactory.getLogger(XfdfWriter.class);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   XfdfWriter(OutputStream outputStream) {
/*  69 */     this.outputStream = outputStream;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void write(XfdfObject xfdfObject) throws TransformerException, ParserConfigurationException {
/*  77 */     writeDom(xfdfObject);
/*     */   }
/*     */ 
/*     */   
/*     */   static void addField(FieldObject fieldObject, Element parentElement, Document document, List<FieldObject> fieldList) {
/*  82 */     List<FieldObject> childrenFields = findChildrenFields(fieldObject, fieldList);
/*     */     
/*  84 */     Element field = document.createElement("field");
/*  85 */     field.setAttribute("name", fieldObject.getName());
/*     */ 
/*     */     
/*  88 */     if (!childrenFields.isEmpty()) {
/*  89 */       for (FieldObject childField : childrenFields) {
/*  90 */         addField(childField, field, document, fieldList);
/*     */       }
/*     */     }
/*  93 */     else if (fieldObject.getValue() != null && !fieldObject.getValue().isEmpty()) {
/*  94 */       Element value = document.createElement("value");
/*  95 */       value.setTextContent(fieldObject.getValue());
/*  96 */       field.appendChild(value);
/*     */     } else {
/*  98 */       logger.info("Field has no value.");
/*     */     } 
/*     */     
/* 101 */     parentElement.appendChild(field);
/*     */   }
/*     */ 
/*     */   
/*     */   private void writeDom(XfdfObject xfdfObject) throws ParserConfigurationException, TransformerException {
/* 106 */     Document document = XfdfFileUtils.createNewXfdfDocument();
/*     */ 
/*     */     
/* 109 */     Element root = document.createElement("xfdf");
/* 110 */     document.appendChild(root);
/*     */ 
/*     */     
/* 113 */     if (xfdfObject.getFields() != null && xfdfObject.getFields().getFieldList() != null && 
/* 114 */       !xfdfObject.getFields().getFieldList().isEmpty()) {
/* 115 */       Element fields = document.createElement("fields");
/* 116 */       root.appendChild(fields);
/* 117 */       List<FieldObject> fieldList = xfdfObject.getFields().getFieldList();
/* 118 */       for (FieldObject fieldObject : fieldList) {
/* 119 */         if (fieldObject.getParent() == null) {
/* 120 */           addField(fieldObject, fields, document, fieldList);
/*     */         }
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 126 */     if (xfdfObject.getAnnots() != null && xfdfObject.getAnnots().getAnnotsList() != null && 
/* 127 */       !xfdfObject.getAnnots().getAnnotsList().isEmpty()) {
/* 128 */       Element annots = document.createElement("annots");
/* 129 */       root.appendChild(annots);
/*     */       
/* 131 */       for (AnnotObject annotObject : xfdfObject.getAnnots().getAnnotsList()) {
/* 132 */         addAnnot(annotObject, annots, document);
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 137 */     if (xfdfObject.getF() != null) {
/* 138 */       Element f = document.createElement("f");
/* 139 */       addFAttributes(xfdfObject.getF(), f);
/* 140 */       root.appendChild(f);
/*     */     } 
/*     */ 
/*     */     
/* 144 */     if (xfdfObject.getIds() != null) {
/* 145 */       Element ids = document.createElement("ids");
/* 146 */       addIdsAttributes(xfdfObject.getIds(), ids);
/* 147 */       root.appendChild(ids);
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 152 */     XfdfFileUtils.saveXfdfDocumentToFile(document, this.outputStream);
/*     */   }
/*     */   
/*     */   private static void addIdsAttributes(IdsObject idsObject, Element ids) {
/* 156 */     if (idsObject.getOriginal() != null) {
/* 157 */       ids.setAttribute("original", idsObject.getOriginal());
/*     */     }
/* 159 */     if (idsObject.getModified() != null) {
/* 160 */       ids.setAttribute("modified", idsObject.getModified());
/*     */     }
/*     */   }
/*     */   
/*     */   private static void addFAttributes(FObject fObject, Element f) {
/* 165 */     if (fObject.getHref() != null) {
/* 166 */       f.setAttribute("href", fObject.getHref());
/*     */     }
/*     */   }
/*     */   
/*     */   private static List<FieldObject> findChildrenFields(FieldObject field, List<FieldObject> fieldList) {
/* 171 */     List<FieldObject> childrenFields = new ArrayList<>();
/* 172 */     for (FieldObject currentField : fieldList) {
/* 173 */       if (currentField.getParent() != null && currentField.getParent().getName().equalsIgnoreCase(field.getName())) {
/* 174 */         childrenFields.add(currentField);
/*     */       }
/*     */     } 
/* 177 */     return childrenFields;
/*     */   }
/*     */   
/*     */   private static void addAnnot(AnnotObject annotObject, Element annots, Document document) {
/* 181 */     if (annotObject.getName() == null) {
/*     */       return;
/*     */     }
/* 184 */     Element annot = document.createElement(annotObject.getName());
/*     */     
/* 186 */     for (AttributeObject attr : annotObject.getAttributes()) {
/* 187 */       annot.setAttribute(attr.getName(), attr.getValue());
/*     */     }
/*     */     
/* 190 */     if (annotObject.getPopup() != null) {
/* 191 */       Element popup = document.createElement("popup");
/* 192 */       addPopup(annotObject.getPopup(), popup, annot);
/*     */     } 
/*     */     
/* 195 */     if (annotObject.getContents() != null) {
/* 196 */       Element contents = document.createElement("contents");
/* 197 */       contents.setTextContent(annotObject.getContents().toString().replace('\r', '\n'));
/* 198 */       annot.appendChild(contents);
/*     */     } 
/*     */     
/* 201 */     if (annotObject.getAppearance() != null) {
/* 202 */       Element appearance = document.createElement("appearance");
/* 203 */       appearance.setTextContent(annotObject.getAppearance());
/* 204 */       annot.appendChild(appearance);
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 213 */     if ("link".equalsIgnoreCase(annotObject.getName())) {
/* 214 */       if (annotObject.getDestination() != null) {
/* 215 */         addDest(annotObject.getDestination(), annot, document);
/* 216 */       } else if (annotObject.getAction() != null) {
/* 217 */         Element onActivation = document.createElement("OnActivation");
/* 218 */         addActionObject(annotObject.getAction(), onActivation, document);
/* 219 */         annot.appendChild(onActivation);
/*     */       } else {
/* 221 */         logger.error("Dest and OnActivation elements are both missing");
/*     */       } 
/*     */       
/* 224 */       if (annotObject.getBorderStyleAlt() != null) {
/* 225 */         addBorderStyleAlt(annotObject.getBorderStyleAlt(), annot, document);
/*     */       }
/*     */     } 
/*     */     
/* 229 */     if ("freetext".equalsIgnoreCase(annotObject.getName())) {
/* 230 */       String defaultAppearanceString = annotObject.getDefaultAppearance();
/* 231 */       if (defaultAppearanceString != null) {
/* 232 */         Element defaultAppearance = document.createElement("defaultappearance");
/* 233 */         defaultAppearance.setTextContent(defaultAppearanceString);
/* 234 */         annot.appendChild(defaultAppearance);
/*     */       } 
/* 236 */       String defaultStyleString = annotObject.getDefaultStyle();
/* 237 */       if (defaultStyleString != null) {
/* 238 */         Element defaultStyle = document.createElement("defaultstyle");
/* 239 */         defaultStyle.setTextContent(defaultStyleString);
/* 240 */         annot.appendChild(defaultStyle);
/*     */       } 
/*     */     } 
/*     */     
/* 244 */     annots.appendChild(annot);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static void addBorderStyleAlt(BorderStyleAltObject borderStyleAltObject, Element annot, Document document) {
/* 250 */     Element borderStyleAlt = document.createElement("BorderStyleAlt");
/*     */ 
/*     */     
/* 253 */     borderStyleAlt.setAttribute("HCornerRadius", XfdfObjectUtils.convertFloatToString(borderStyleAltObject.getHCornerRadius()));
/* 254 */     borderStyleAlt.setAttribute("VCornerRadius", XfdfObjectUtils.convertFloatToString(borderStyleAltObject.getVCornerRadius()));
/* 255 */     borderStyleAlt.setAttribute("Width", XfdfObjectUtils.convertFloatToString(borderStyleAltObject.getWidth()));
/*     */ 
/*     */     
/* 258 */     if (borderStyleAltObject.getDashPattern() != null)
/*     */     {
/* 260 */       borderStyleAlt.setAttribute("DashPattern", Arrays.toString(borderStyleAltObject.getDashPattern()));
/*     */     }
/*     */     
/* 263 */     if (borderStyleAltObject.getContent() != null) {
/* 264 */       borderStyleAlt.setTextContent(borderStyleAltObject.getContent());
/*     */     }
/* 266 */     annot.appendChild(borderStyleAlt);
/*     */   }
/*     */   
/*     */   private static void addXYZ(FitObject xyzObject, Element dest, Document document) {
/* 270 */     Element xyz = document.createElement("XYZ");
/*     */ 
/*     */     
/* 273 */     xyz.setAttribute("Page", String.valueOf(xyzObject.getPage()));
/* 274 */     xyz.setAttribute("Left", XfdfObjectUtils.convertFloatToString(xyzObject.getLeft()));
/* 275 */     xyz.setAttribute("Bottom", XfdfObjectUtils.convertFloatToString(xyzObject.getBottom()));
/* 276 */     xyz.setAttribute("Right", XfdfObjectUtils.convertFloatToString(xyzObject.getRight()));
/* 277 */     xyz.setAttribute("Top", XfdfObjectUtils.convertFloatToString(xyzObject.getTop()));
/*     */     
/* 279 */     dest.appendChild(xyz);
/*     */   }
/*     */   
/*     */   private static void addFit(FitObject fitObject, Element dest, Document document) {
/* 283 */     Element fit = document.createElement("Fit");
/*     */ 
/*     */     
/* 286 */     fit.setAttribute("Page", String.valueOf(fitObject.getPage()));
/*     */     
/* 288 */     dest.appendChild(fit);
/*     */   }
/*     */   
/*     */   private static void addFitB(FitObject fitBObject, Element dest, Document document) {
/* 292 */     Element fitB = document.createElement("FitB");
/*     */ 
/*     */     
/* 295 */     fitB.setAttribute("Page", String.valueOf(fitBObject.getPage()));
/*     */     
/* 297 */     dest.appendChild(fitB);
/*     */   }
/*     */   
/*     */   private static void addFitBH(FitObject fitBHObject, Element dest, Document document) {
/* 301 */     Element fitBH = document.createElement("FitBH");
/*     */ 
/*     */     
/* 304 */     fitBH.setAttribute("Page", String.valueOf(fitBHObject.getPage()));
/* 305 */     fitBH.setAttribute("Top", XfdfObjectUtils.convertFloatToString(fitBHObject.getTop()));
/*     */     
/* 307 */     dest.appendChild(fitBH);
/*     */   }
/*     */   
/*     */   private static void addFitBV(FitObject fitBVObject, Element dest, Document document) {
/* 311 */     Element fitBV = document.createElement("FitBV");
/*     */ 
/*     */     
/* 314 */     fitBV.setAttribute("Page", String.valueOf(fitBVObject.getPage()));
/* 315 */     fitBV.setAttribute("Left", XfdfObjectUtils.convertFloatToString(fitBVObject.getLeft()));
/*     */     
/* 317 */     dest.appendChild(fitBV);
/*     */   }
/*     */   
/*     */   private static void addFitH(FitObject fitHObject, Element dest, Document document) {
/* 321 */     Element fitH = document.createElement("FitH");
/*     */ 
/*     */     
/* 324 */     fitH.setAttribute("Page", String.valueOf(fitHObject.getPage()));
/* 325 */     fitH.setAttribute("Top", XfdfObjectUtils.convertFloatToString(fitHObject.getTop()));
/*     */     
/* 327 */     dest.appendChild(fitH);
/*     */   }
/*     */   
/*     */   private static void addFitR(FitObject fitRObject, Element dest, Document document) {
/* 331 */     Element fitR = document.createElement("FitR");
/*     */ 
/*     */     
/* 334 */     fitR.setAttribute("Page", String.valueOf(fitRObject.getPage()));
/* 335 */     fitR.setAttribute("Left", XfdfObjectUtils.convertFloatToString(fitRObject.getLeft()));
/* 336 */     fitR.setAttribute("Bottom", XfdfObjectUtils.convertFloatToString(fitRObject.getBottom()));
/* 337 */     fitR.setAttribute("Right", XfdfObjectUtils.convertFloatToString(fitRObject.getRight()));
/* 338 */     fitR.setAttribute("Top", XfdfObjectUtils.convertFloatToString(fitRObject.getTop()));
/*     */     
/* 340 */     dest.appendChild(fitR);
/*     */   }
/*     */   
/*     */   private static void addFitV(FitObject fitVObject, Element dest, Document document) {
/* 344 */     Element fitV = document.createElement("FitV");
/*     */ 
/*     */     
/* 347 */     fitV.setAttribute("Page", String.valueOf(fitVObject.getPage()));
/* 348 */     fitV.setAttribute("Left", XfdfObjectUtils.convertFloatToString(fitVObject.getLeft()));
/*     */     
/* 350 */     dest.appendChild(fitV);
/*     */   }
/*     */   
/*     */   private static void addDest(DestObject destObject, Element annot, Document document) {
/* 354 */     Element dest = document.createElement("Dest");
/*     */     
/* 356 */     if (destObject.getName() != null) {
/* 357 */       Element named = document.createElement("Named");
/* 358 */       named.setAttribute("name", destObject.getName());
/* 359 */       dest.appendChild(named);
/* 360 */     } else if (destObject.getXyz() != null) {
/* 361 */       addXYZ(destObject.getXyz(), dest, document);
/* 362 */     } else if (destObject.getFit() != null) {
/* 363 */       addFit(destObject.getFit(), dest, document);
/* 364 */     } else if (destObject.getFitB() != null) {
/* 365 */       addFitB(destObject.getFitB(), dest, document);
/* 366 */     } else if (destObject.getFitBH() != null) {
/* 367 */       addFitBH(destObject.getFitBH(), dest, document);
/* 368 */     } else if (destObject.getFitBV() != null) {
/* 369 */       addFitBV(destObject.getFitBV(), dest, document);
/* 370 */     } else if (destObject.getFitH() != null) {
/* 371 */       addFitH(destObject.getFitH(), dest, document);
/* 372 */     } else if (destObject.getFitR() != null) {
/* 373 */       addFitR(destObject.getFitR(), dest, document);
/* 374 */     } else if (destObject.getFitV() != null) {
/* 375 */       addFitV(destObject.getFitV(), dest, document);
/*     */     } 
/*     */     
/* 378 */     annot.appendChild(dest);
/*     */   }
/*     */ 
/*     */   
/*     */   private static void addActionObject(ActionObject actionObject, Element onActivation, Document document) {
/* 383 */     Element action = document.createElement("Action");
/* 384 */     if (actionObject.getUri() != null) {
/* 385 */       Element uri = document.createElement("URI");
/*     */ 
/*     */       
/* 388 */       uri.setAttribute("Name", actionObject.getUri().getValue());
/* 389 */       if (actionObject.isMap()) {
/* 390 */         uri.setAttribute("IsMap", "true");
/*     */       } else {
/* 392 */         uri.setAttribute("IsMap", "false");
/*     */       } 
/* 394 */       action.appendChild(uri);
/*     */     }
/* 396 */     else if (PdfName.GoTo.equals(actionObject.getType())) {
/* 397 */       Element goTo = document.createElement("GoTo");
/*     */       
/* 399 */       addDest(actionObject.getDestination(), goTo, document);
/*     */       
/* 401 */       action.appendChild(goTo);
/* 402 */     } else if (PdfName.GoToR.equals(actionObject.getType())) {
/*     */       
/* 404 */       Element goToR = document.createElement("GoToR");
/*     */       
/* 406 */       if (actionObject.getDestination() != null) {
/* 407 */         addDest(actionObject.getDestination(), goToR, document);
/* 408 */       } else if (actionObject.getFileOriginalName() != null) {
/* 409 */         Element file = document.createElement("File");
/* 410 */         file.setAttribute("OriginalName", actionObject.getFileOriginalName());
/* 411 */         goToR.appendChild(file);
/*     */       } else {
/* 413 */         logger.error("Dest or File elements are missing.");
/*     */       } 
/*     */       
/* 416 */       action.appendChild(goToR);
/*     */     }
/* 418 */     else if (PdfName.Named.equals(actionObject.getType())) {
/* 419 */       Element named = document.createElement("Named");
/* 420 */       named.setAttribute("Name", actionObject.getNameAction().getValue());
/*     */       
/* 422 */       action.appendChild(named);
/*     */     }
/* 424 */     else if (PdfName.Launch.equals(actionObject.getType())) {
/* 425 */       Element launch = document.createElement("Launch");
/* 426 */       if (actionObject.getFileOriginalName() != null) {
/* 427 */         Element file = document.createElement("File");
/* 428 */         file.setAttribute("OriginalName", actionObject.getFileOriginalName());
/* 429 */         launch.appendChild(file);
/*     */       } else {
/* 431 */         logger.error("File element is missing");
/*     */       } 
/* 433 */       if (actionObject.isNewWindow()) {
/* 434 */         launch.setAttribute("NewWindow", "true");
/*     */       }
/* 436 */       action.appendChild(launch);
/*     */     } 
/*     */     
/* 439 */     onActivation.appendChild(action);
/*     */   }
/*     */   
/*     */   private static void addPopup(AnnotObject popupAnnotObject, Element popup, Element annot) {
/* 443 */     for (AttributeObject attr : popupAnnotObject.getAttributes()) {
/* 444 */       popup.setAttribute(attr.getName(), attr.getValue());
/*     */     }
/* 446 */     annot.appendChild(popup);
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/forms/xfdf/XfdfWriter.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */