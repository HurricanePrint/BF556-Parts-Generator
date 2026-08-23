/*     */ package com.itextpdf.layout.renderer;
/*     */ 
/*     */ import com.itextpdf.io.util.MessageFormatUtil;
/*     */ import com.itextpdf.kernel.colors.Color;
/*     */ import com.itextpdf.kernel.geom.Rectangle;
/*     */ import com.itextpdf.kernel.pdf.PdfArray;
/*     */ import com.itextpdf.kernel.pdf.PdfDictionary;
/*     */ import com.itextpdf.kernel.pdf.PdfName;
/*     */ import com.itextpdf.kernel.pdf.PdfNull;
/*     */ import com.itextpdf.kernel.pdf.PdfNumber;
/*     */ import com.itextpdf.kernel.pdf.PdfObject;
/*     */ import com.itextpdf.kernel.pdf.tagging.PdfNamespace;
/*     */ import com.itextpdf.kernel.pdf.tagging.PdfStructureAttributes;
/*     */ import com.itextpdf.kernel.pdf.tagutils.IRoleMappingResolver;
/*     */ import com.itextpdf.kernel.pdf.tagutils.TagStructureContext;
/*     */ import com.itextpdf.kernel.pdf.tagutils.TagTreePointer;
/*     */ import com.itextpdf.layout.borders.Border;
/*     */ import com.itextpdf.layout.element.Cell;
/*     */ import com.itextpdf.layout.element.Table;
/*     */ import com.itextpdf.layout.property.Background;
/*     */ import com.itextpdf.layout.property.HorizontalAlignment;
/*     */ import com.itextpdf.layout.property.ListNumberingType;
/*     */ import com.itextpdf.layout.property.TextAlignment;
/*     */ import com.itextpdf.layout.property.TransparentColor;
/*     */ import com.itextpdf.layout.property.Underline;
/*     */ import com.itextpdf.layout.property.UnitValue;
/*     */ import java.util.List;
/*     */ import org.slf4j.Logger;
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
/*     */ public class AccessibleAttributesApplier
/*     */ {
/*     */   public static PdfStructureAttributes getLayoutAttributes(AbstractRenderer renderer, TagTreePointer taggingPointer) {
/*  88 */     IRoleMappingResolver resolvedMapping = resolveMappingToStandard(taggingPointer);
/*  89 */     if (resolvedMapping == null) {
/*  90 */       return null;
/*     */     }
/*     */     
/*  93 */     String role = resolvedMapping.getRole();
/*  94 */     int tagType = AccessibleTypes.identifyType(role);
/*  95 */     PdfDictionary attributes = new PdfDictionary();
/*  96 */     attributes.put(PdfName.O, (PdfObject)PdfName.Layout);
/*     */ 
/*     */ 
/*     */     
/* 100 */     applyCommonLayoutAttributes(renderer, attributes);
/* 101 */     if (tagType == AccessibleTypes.BlockLevel) {
/* 102 */       applyBlockLevelLayoutAttributes(role, renderer, attributes);
/*     */     }
/* 104 */     if (tagType == AccessibleTypes.InlineLevel) {
/* 105 */       applyInlineLevelLayoutAttributes(renderer, attributes);
/*     */     }
/*     */     
/* 108 */     if (tagType == AccessibleTypes.Illustration) {
/* 109 */       applyIllustrationLayoutAttributes(renderer, attributes);
/*     */     }
/*     */     
/* 112 */     return (attributes.size() > 1) ? new PdfStructureAttributes(attributes) : null;
/*     */   }
/*     */   
/*     */   public static PdfStructureAttributes getListAttributes(AbstractRenderer renderer, TagTreePointer taggingPointer) {
/* 116 */     IRoleMappingResolver resolvedMapping = null;
/* 117 */     resolvedMapping = resolveMappingToStandard(taggingPointer);
/* 118 */     if (resolvedMapping == null || !"L".equals(resolvedMapping.getRole())) {
/* 119 */       return null;
/*     */     }
/*     */     
/* 122 */     PdfDictionary attributes = new PdfDictionary();
/* 123 */     attributes.put(PdfName.O, (PdfObject)PdfName.List);
/*     */     
/* 125 */     Object listSymbol = renderer.getProperty(37);
/*     */     
/* 127 */     boolean tagStructurePdf2 = isTagStructurePdf2(resolvedMapping.getNamespace());
/* 128 */     if (listSymbol instanceof ListNumberingType) {
/* 129 */       ListNumberingType numberingType = (ListNumberingType)listSymbol;
/* 130 */       attributes.put(PdfName.ListNumbering, (PdfObject)transformNumberingTypeToName(numberingType, tagStructurePdf2));
/* 131 */     } else if (tagStructurePdf2) {
/* 132 */       if (listSymbol instanceof com.itextpdf.layout.property.IListSymbolFactory) {
/* 133 */         attributes.put(PdfName.ListNumbering, (PdfObject)PdfName.Ordered);
/*     */       } else {
/* 135 */         attributes.put(PdfName.ListNumbering, (PdfObject)PdfName.Unordered);
/*     */       } 
/*     */     } 
/*     */     
/* 139 */     return (attributes.size() > 1) ? new PdfStructureAttributes(attributes) : null;
/*     */   }
/*     */   
/*     */   public static PdfStructureAttributes getTableAttributes(AbstractRenderer renderer, TagTreePointer taggingPointer) {
/* 143 */     IRoleMappingResolver resolvedMapping = resolveMappingToStandard(taggingPointer);
/* 144 */     if (resolvedMapping == null || (
/* 145 */       !"TD".equals(resolvedMapping.getRole()) && !"TH".equals(resolvedMapping.getRole()))) {
/* 146 */       return null;
/*     */     }
/*     */     
/* 149 */     PdfDictionary attributes = new PdfDictionary();
/* 150 */     attributes.put(PdfName.O, (PdfObject)PdfName.Table);
/*     */     
/* 152 */     if (renderer.getModelElement() instanceof Cell) {
/* 153 */       Cell cell = (Cell)renderer.getModelElement();
/* 154 */       if (cell.getRowspan() != 1) {
/* 155 */         attributes.put(PdfName.RowSpan, (PdfObject)new PdfNumber(cell.getRowspan()));
/*     */       }
/* 157 */       if (cell.getColspan() != 1) {
/* 158 */         attributes.put(PdfName.ColSpan, (PdfObject)new PdfNumber(cell.getColspan()));
/*     */       }
/*     */     } 
/*     */     
/* 162 */     return (attributes.size() > 1) ? new PdfStructureAttributes(attributes) : null;
/*     */   }
/*     */   
/*     */   private static void applyCommonLayoutAttributes(AbstractRenderer renderer, PdfDictionary attributes) {
/* 166 */     Background background = renderer.<Background>getProperty(6);
/* 167 */     if (background != null && background.getColor() instanceof com.itextpdf.kernel.colors.DeviceRgb) {
/* 168 */       attributes.put(PdfName.BackgroundColor, (PdfObject)new PdfArray(background.getColor().getColorValue()));
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 174 */     if (!(renderer.getModelElement() instanceof Cell)) {
/* 175 */       applyBorderAttributes(renderer, attributes);
/*     */     }
/* 177 */     applyPaddingAttribute(renderer, attributes);
/*     */     
/* 179 */     TransparentColor transparentColor = renderer.getPropertyAsTransparentColor(21);
/* 180 */     if (transparentColor != null && transparentColor.getColor() instanceof com.itextpdf.kernel.colors.DeviceRgb) {
/* 181 */       attributes.put(PdfName.Color, (PdfObject)new PdfArray(transparentColor.getColor().getColorValue()));
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void applyBlockLevelLayoutAttributes(String role, AbstractRenderer renderer, PdfDictionary attributes) {
/* 189 */     UnitValue[] margins = { renderer.getPropertyAsUnitValue(46), renderer.getPropertyAsUnitValue(43), renderer.getPropertyAsUnitValue(44), renderer.getPropertyAsUnitValue(45) };
/*     */ 
/*     */     
/* 192 */     int[] marginsOrder = { 0, 1, 2, 3 };
/*     */     
/* 194 */     UnitValue spaceBefore = margins[marginsOrder[0]];
/* 195 */     if (spaceBefore != null) {
/* 196 */       if (!spaceBefore.isPointValue()) {
/* 197 */         Logger logger = LoggerFactory.getLogger(AccessibleAttributesApplier.class);
/* 198 */         logger.error(MessageFormatUtil.format("Property {0} in percents is not supported", new Object[] { Integer.valueOf(46) }));
/*     */       } 
/* 200 */       if (0.0F != spaceBefore.getValue()) {
/* 201 */         attributes.put(PdfName.SpaceBefore, (PdfObject)new PdfNumber(spaceBefore.getValue()));
/*     */       }
/*     */     } 
/*     */     
/* 205 */     UnitValue spaceAfter = margins[marginsOrder[1]];
/* 206 */     if (spaceAfter != null) {
/* 207 */       if (!spaceAfter.isPointValue()) {
/* 208 */         Logger logger = LoggerFactory.getLogger(AccessibleAttributesApplier.class);
/* 209 */         logger.error(MessageFormatUtil.format("Property {0} in percents is not supported", new Object[] { Integer.valueOf(43) }));
/*     */       } 
/* 211 */       if (0.0F != spaceAfter.getValue()) {
/* 212 */         attributes.put(PdfName.SpaceAfter, (PdfObject)new PdfNumber(spaceAfter.getValue()));
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 217 */     UnitValue startIndent = margins[marginsOrder[2]];
/* 218 */     if (startIndent != null) {
/* 219 */       if (!startIndent.isPointValue()) {
/* 220 */         Logger logger = LoggerFactory.getLogger(AccessibleAttributesApplier.class);
/* 221 */         logger.error(MessageFormatUtil.format("Property {0} in percents is not supported", new Object[] { Integer.valueOf(44) }));
/*     */       } 
/* 223 */       if (0.0F != startIndent.getValue()) {
/* 224 */         attributes.put(PdfName.StartIndent, (PdfObject)new PdfNumber(startIndent.getValue()));
/*     */       }
/*     */     } 
/*     */     
/* 228 */     UnitValue endIndent = margins[marginsOrder[3]];
/* 229 */     if (endIndent != null) {
/* 230 */       if (!endIndent.isPointValue()) {
/* 231 */         Logger logger = LoggerFactory.getLogger(AccessibleAttributesApplier.class);
/* 232 */         logger.error(MessageFormatUtil.format("Property {0} in percents is not supported", new Object[] { Integer.valueOf(45) }));
/*     */       } 
/* 234 */       if (0.0F != endIndent.getValue()) {
/* 235 */         attributes.put(PdfName.EndIndent, (PdfObject)new PdfNumber(endIndent.getValue()));
/*     */       }
/*     */     } 
/*     */     
/* 239 */     Float firstLineIndent = renderer.getPropertyAsFloat(18);
/* 240 */     if (firstLineIndent != null && firstLineIndent.floatValue() != 0.0F) {
/* 241 */       attributes.put(PdfName.TextIndent, (PdfObject)new PdfNumber(firstLineIndent.floatValue()));
/*     */     }
/*     */     
/* 244 */     TextAlignment textAlignment = renderer.<TextAlignment>getProperty(70);
/* 245 */     if (textAlignment != null && 
/*     */       
/* 247 */       !"TH".equals(role) && !"TD".equals(role)) {
/* 248 */       attributes.put(PdfName.TextAlign, (PdfObject)transformTextAlignmentValueToName(textAlignment));
/*     */     }
/*     */ 
/*     */     
/* 252 */     if (renderer.isLastRendererForModelElement) {
/* 253 */       Rectangle bbox = renderer.getOccupiedArea().getBBox();
/* 254 */       attributes.put(PdfName.BBox, (PdfObject)new PdfArray(bbox));
/*     */     } 
/*     */     
/* 257 */     if ("TH".equals(role) || "TD".equals(role) || "Table".equals(role)) {
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 262 */       if (!(renderer instanceof TableRenderer) || ((Table)renderer.getModelElement()).isComplete()) {
/* 263 */         UnitValue width = renderer.<UnitValue>getProperty(77);
/* 264 */         if (width != null && width.isPointValue()) {
/* 265 */           attributes.put(PdfName.Width, (PdfObject)new PdfNumber(width.getValue()));
/*     */         }
/*     */       } 
/* 268 */       UnitValue height = renderer.<UnitValue>getProperty(27);
/* 269 */       if (height != null && height.isPointValue()) {
/* 270 */         attributes.put(PdfName.Height, (PdfObject)new PdfNumber(height.getValue()));
/*     */       }
/*     */     } 
/*     */     
/* 274 */     if ("TH".equals(role) || "TD".equals(role)) {
/* 275 */       HorizontalAlignment horizontalAlignment = renderer.<HorizontalAlignment>getProperty(28);
/* 276 */       if (horizontalAlignment != null) {
/* 277 */         attributes.put(PdfName.BlockAlign, (PdfObject)transformBlockAlignToName(horizontalAlignment));
/*     */       }
/*     */       
/* 280 */       if (textAlignment != null && textAlignment != TextAlignment.JUSTIFIED && textAlignment != TextAlignment.JUSTIFIED_ALL)
/*     */       {
/*     */         
/* 283 */         attributes.put(PdfName.InlineAlign, (PdfObject)transformTextAlignmentValueToName(textAlignment));
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private static void applyInlineLevelLayoutAttributes(AbstractRenderer renderer, PdfDictionary attributes) {
/* 290 */     Float textRise = renderer.getPropertyAsFloat(72);
/* 291 */     if (textRise != null && textRise.floatValue() != 0.0F) {
/* 292 */       attributes.put(PdfName.BaselineShift, (PdfObject)new PdfNumber(textRise.floatValue()));
/*     */     }
/*     */     
/* 295 */     Object underlines = renderer.getProperty(74);
/* 296 */     if (underlines != null) {
/* 297 */       UnitValue fontSize = renderer.getPropertyAsUnitValue(24);
/* 298 */       if (!fontSize.isPointValue()) {
/* 299 */         Logger logger = LoggerFactory.getLogger(AccessibleAttributesApplier.class);
/* 300 */         logger.error(MessageFormatUtil.format("Property {0} in percents is not supported", new Object[] { Integer.valueOf(24) }));
/*     */       } 
/* 302 */       Underline underline = null;
/* 303 */       if (underlines instanceof List && ((List)underlines)
/* 304 */         .size() > 0 && ((List)underlines)
/* 305 */         .get(0) instanceof Underline) {
/*     */         
/* 307 */         underline = ((List<Underline>)underlines).get(0);
/* 308 */       } else if (underlines instanceof Underline) {
/* 309 */         underline = (Underline)underlines;
/*     */       } 
/* 311 */       if (underline != null) {
/* 312 */         attributes.put(PdfName.TextDecorationType, (underline.getYPosition(fontSize.getValue()) > 0.0F) ? (PdfObject)PdfName.LineThrough : (PdfObject)PdfName.Underline);
/* 313 */         if (underline.getColor() instanceof com.itextpdf.kernel.colors.DeviceRgb) {
/* 314 */           attributes.put(PdfName.TextDecorationColor, (PdfObject)new PdfArray(underline.getColor().getColorValue()));
/*     */         }
/*     */         
/* 317 */         attributes.put(PdfName.TextDecorationThickness, (PdfObject)new PdfNumber(underline.getThickness(fontSize.getValue())));
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private static void applyIllustrationLayoutAttributes(AbstractRenderer renderer, PdfDictionary attributes) {
/* 323 */     Rectangle bbox = renderer.getOccupiedArea().getBBox();
/* 324 */     attributes.put(PdfName.BBox, (PdfObject)new PdfArray(bbox));
/*     */     
/* 326 */     UnitValue width = renderer.<UnitValue>getProperty(77);
/* 327 */     if (width != null && width.isPointValue()) {
/* 328 */       attributes.put(PdfName.Width, (PdfObject)new PdfNumber(width.getValue()));
/*     */     } else {
/* 330 */       attributes.put(PdfName.Width, (PdfObject)new PdfNumber(bbox.getWidth()));
/*     */     } 
/*     */     
/* 333 */     UnitValue height = renderer.<UnitValue>getProperty(27);
/* 334 */     if (height != null) {
/* 335 */       attributes.put(PdfName.Height, (PdfObject)new PdfNumber(height.getValue()));
/*     */     } else {
/* 337 */       attributes.put(PdfName.Height, (PdfObject)new PdfNumber(bbox.getHeight()));
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void applyPaddingAttribute(AbstractRenderer renderer, PdfDictionary attributes) {
/*     */     PdfArray pdfArray;
/* 346 */     UnitValue[] paddingsUV = { renderer.getPropertyAsUnitValue(50), renderer.getPropertyAsUnitValue(49), renderer.getPropertyAsUnitValue(47), renderer.getPropertyAsUnitValue(48) };
/*     */ 
/*     */     
/* 349 */     if (!paddingsUV[0].isPointValue()) {
/* 350 */       Logger logger = LoggerFactory.getLogger(AccessibleAttributesApplier.class);
/* 351 */       logger.error(MessageFormatUtil.format("Property {0} in percents is not supported", new Object[] { Integer.valueOf(50) }));
/*     */     } 
/* 353 */     if (!paddingsUV[1].isPointValue()) {
/* 354 */       Logger logger = LoggerFactory.getLogger(AccessibleAttributesApplier.class);
/* 355 */       logger.error(MessageFormatUtil.format("Property {0} in percents is not supported", new Object[] { Integer.valueOf(49) }));
/*     */     } 
/* 357 */     if (!paddingsUV[2].isPointValue()) {
/* 358 */       Logger logger = LoggerFactory.getLogger(AccessibleAttributesApplier.class);
/* 359 */       logger.error(MessageFormatUtil.format("Property {0} in percents is not supported", new Object[] { Integer.valueOf(47) }));
/*     */     } 
/* 361 */     if (!paddingsUV[3].isPointValue()) {
/* 362 */       Logger logger = LoggerFactory.getLogger(AccessibleAttributesApplier.class);
/* 363 */       logger.error(MessageFormatUtil.format("Property {0} in percents is not supported", new Object[] { Integer.valueOf(48) }));
/*     */     } 
/*     */     
/* 366 */     float[] paddings = { paddingsUV[0].getValue(), paddingsUV[1].getValue(), paddingsUV[2].getValue(), paddingsUV[3].getValue() };
/* 367 */     PdfObject padding = null;
/* 368 */     if (paddings[0] == paddings[1] && paddings[0] == paddings[2] && paddings[0] == paddings[3]) {
/* 369 */       if (paddings[0] != 0.0F) {
/* 370 */         PdfNumber pdfNumber = new PdfNumber(paddings[0]);
/*     */       }
/*     */     } else {
/* 373 */       PdfArray paddingArray = new PdfArray();
/*     */ 
/*     */       
/* 376 */       int[] paddingsOrder = { 0, 1, 2, 3 };
/* 377 */       for (int i : paddingsOrder) {
/* 378 */         paddingArray.add((PdfObject)new PdfNumber(paddings[i]));
/*     */       }
/* 380 */       pdfArray = paddingArray;
/*     */     } 
/*     */     
/* 383 */     if (pdfArray != null) {
/* 384 */       attributes.put(PdfName.Padding, (PdfObject)pdfArray);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void applyBorderAttributes(AbstractRenderer renderer, PdfDictionary attributes) {
/* 392 */     boolean specificBorderProperties = (renderer.getProperty(13) != null || renderer.getProperty(12) != null || renderer.getProperty(10) != null || renderer.getProperty(11) != null);
/*     */     
/* 394 */     boolean generalBorderProperties = (!specificBorderProperties && renderer.getProperty(9) != null);
/*     */     
/* 396 */     if (generalBorderProperties) {
/* 397 */       Border generalBorder = renderer.<Border>getProperty(9);
/* 398 */       Color generalBorderColor = generalBorder.getColor();
/* 399 */       int borderType = generalBorder.getType();
/* 400 */       float borderWidth = generalBorder.getWidth();
/*     */       
/* 402 */       if (generalBorderColor instanceof com.itextpdf.kernel.colors.DeviceRgb) {
/* 403 */         attributes.put(PdfName.BorderColor, (PdfObject)new PdfArray(generalBorderColor.getColorValue()));
/* 404 */         attributes.put(PdfName.BorderStyle, (PdfObject)transformBorderTypeToName(borderType));
/* 405 */         attributes.put(PdfName.BorderThickness, (PdfObject)new PdfNumber(borderWidth));
/*     */       } 
/*     */     } 
/*     */     
/* 409 */     if (specificBorderProperties) {
/* 410 */       PdfArray borderColors = new PdfArray();
/* 411 */       PdfArray borderTypes = new PdfArray();
/* 412 */       PdfArray borderWidths = new PdfArray();
/* 413 */       boolean atLeastOneRgb = false;
/* 414 */       Border[] borders = renderer.getBorders();
/*     */       
/* 416 */       boolean allColorsEqual = true;
/* 417 */       boolean allTypesEqual = true;
/* 418 */       boolean allWidthsEqual = true;
/*     */       
/* 420 */       for (int i = 1; i < borders.length; i++) {
/* 421 */         Border border = borders[i];
/* 422 */         if (border != null) {
/* 423 */           if (null == borders[0] || !border.getColor().equals(borders[0].getColor())) {
/* 424 */             allColorsEqual = false;
/*     */           }
/*     */           
/* 427 */           if (null == borders[0] || border.getWidth() != borders[0].getWidth()) {
/* 428 */             allWidthsEqual = false;
/*     */           }
/*     */           
/* 431 */           if (null == borders[0] || border.getType() != borders[0].getType()) {
/* 432 */             allTypesEqual = false;
/*     */           }
/*     */         } 
/*     */       } 
/*     */ 
/*     */       
/* 438 */       int[] borderOrder = { 0, 1, 2, 3 };
/* 439 */       for (int j : borderOrder) {
/* 440 */         if (borders[j] != null) {
/* 441 */           if (borders[j].getColor() instanceof com.itextpdf.kernel.colors.DeviceRgb) {
/* 442 */             borderColors.add((PdfObject)new PdfArray(borders[j].getColor().getColorValue()));
/* 443 */             atLeastOneRgb = true;
/*     */           } else {
/* 445 */             borderColors.add((PdfObject)PdfNull.PDF_NULL);
/*     */           } 
/* 447 */           borderTypes.add((PdfObject)transformBorderTypeToName(borders[j].getType()));
/* 448 */           borderWidths.add((PdfObject)new PdfNumber(borders[j].getWidth()));
/*     */         } else {
/* 450 */           borderColors.add((PdfObject)PdfNull.PDF_NULL);
/* 451 */           borderTypes.add((PdfObject)PdfName.None);
/* 452 */           borderWidths.add((PdfObject)PdfNull.PDF_NULL);
/*     */         } 
/*     */       } 
/*     */       
/* 456 */       if (atLeastOneRgb) {
/* 457 */         if (allColorsEqual) {
/* 458 */           attributes.put(PdfName.BorderColor, borderColors.get(0));
/*     */         } else {
/* 460 */           attributes.put(PdfName.BorderColor, (PdfObject)borderColors);
/*     */         } 
/*     */       }
/*     */       
/* 464 */       if (allTypesEqual) {
/* 465 */         attributes.put(PdfName.BorderStyle, borderTypes.get(0));
/*     */       } else {
/* 467 */         attributes.put(PdfName.BorderStyle, (PdfObject)borderTypes);
/*     */       } 
/*     */       
/* 470 */       if (allWidthsEqual) {
/* 471 */         attributes.put(PdfName.BorderThickness, borderWidths.get(0));
/*     */       } else {
/* 473 */         attributes.put(PdfName.BorderThickness, (PdfObject)borderWidths);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private static IRoleMappingResolver resolveMappingToStandard(TagTreePointer taggingPointer) {
/* 479 */     TagStructureContext tagContext = taggingPointer.getDocument().getTagStructureContext();
/* 480 */     PdfNamespace namespace = taggingPointer.getProperties().getNamespace();
/* 481 */     return tagContext.resolveMappingToStandardOrDomainSpecificRole(taggingPointer.getRole(), namespace);
/*     */   }
/*     */   
/*     */   private static boolean isTagStructurePdf2(PdfNamespace namespace) {
/* 485 */     return (namespace != null && "http://iso.org/pdf2/ssn".equals(namespace.getNamespaceName()));
/*     */   }
/*     */ 
/*     */   
/*     */   private static PdfName transformTextAlignmentValueToName(TextAlignment textAlignment) {
/* 490 */     boolean isLeftToRight = true;
/* 491 */     switch (textAlignment) {
/*     */       case DECIMAL:
/* 493 */         if (isLeftToRight) {
/* 494 */           return PdfName.Start;
/*     */         }
/* 496 */         return PdfName.End;
/*     */       
/*     */       case DECIMAL_LEADING_ZERO:
/* 499 */         return PdfName.Center;
/*     */       case ROMAN_UPPER:
/* 501 */         if (isLeftToRight) {
/* 502 */           return PdfName.End;
/*     */         }
/* 504 */         return PdfName.Start;
/*     */       
/*     */       case ROMAN_LOWER:
/*     */       case ENGLISH_UPPER:
/* 508 */         return PdfName.Justify;
/*     */     } 
/* 510 */     return PdfName.Start;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static PdfName transformBlockAlignToName(HorizontalAlignment horizontalAlignment) {
/* 516 */     boolean isLeftToRight = true;
/* 517 */     switch (horizontalAlignment) {
/*     */       case DECIMAL:
/* 519 */         if (isLeftToRight) {
/* 520 */           return PdfName.Before;
/*     */         }
/* 522 */         return PdfName.After;
/*     */       
/*     */       case DECIMAL_LEADING_ZERO:
/* 525 */         return PdfName.Middle;
/*     */       case ROMAN_UPPER:
/* 527 */         if (isLeftToRight) {
/* 528 */           return PdfName.After;
/*     */         }
/* 530 */         return PdfName.Before;
/*     */     } 
/*     */     
/* 533 */     return PdfName.Before;
/*     */   }
/*     */ 
/*     */   
/*     */   private static PdfName transformBorderTypeToName(int borderType) {
/* 538 */     switch (borderType) {
/*     */       case 0:
/* 540 */         return PdfName.Solid;
/*     */       case 1:
/* 542 */         return PdfName.Dashed;
/*     */       case 2:
/* 544 */         return PdfName.Dotted;
/*     */       case 4:
/* 546 */         return PdfName.Dotted;
/*     */       case 3:
/* 548 */         return PdfName.Double;
/*     */       case 5:
/* 550 */         return PdfName.Groove;
/*     */       case 6:
/* 552 */         return PdfName.Inset;
/*     */       case 7:
/* 554 */         return PdfName.Outset;
/*     */       case 8:
/* 556 */         return PdfName.Ridge;
/*     */     } 
/* 558 */     return PdfName.Solid;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static PdfName transformNumberingTypeToName(ListNumberingType numberingType, boolean isTagStructurePdf2) {
/* 564 */     switch (numberingType) {
/*     */       case DECIMAL:
/*     */       case DECIMAL_LEADING_ZERO:
/* 567 */         return PdfName.Decimal;
/*     */       case ROMAN_UPPER:
/* 569 */         return PdfName.UpperRoman;
/*     */       case ROMAN_LOWER:
/* 571 */         return PdfName.LowerRoman;
/*     */       case ENGLISH_UPPER:
/*     */       case GREEK_UPPER:
/* 574 */         return PdfName.UpperAlpha;
/*     */       case ENGLISH_LOWER:
/*     */       case GREEK_LOWER:
/* 577 */         return PdfName.LowerAlpha;
/*     */     } 
/* 579 */     if (isTagStructurePdf2) {
/* 580 */       return PdfName.Ordered;
/*     */     }
/* 582 */     return PdfName.None;
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/layout/renderer/AccessibleAttributesApplier.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */