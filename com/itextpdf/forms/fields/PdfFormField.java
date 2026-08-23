/*      */ package com.itextpdf.forms.fields;
/*      */ 
/*      */ import com.itextpdf.forms.util.DrawingUtil;
/*      */ import com.itextpdf.io.codec.Base64;
/*      */ import com.itextpdf.io.font.PdfEncodings;
/*      */ import com.itextpdf.io.image.ImageData;
/*      */ import com.itextpdf.io.image.ImageDataFactory;
/*      */ import com.itextpdf.io.source.OutputStream;
/*      */ import com.itextpdf.io.source.PdfTokenizer;
/*      */ import com.itextpdf.io.source.RandomAccessFileOrArray;
/*      */ import com.itextpdf.io.source.RandomAccessSourceFactory;
/*      */ import com.itextpdf.io.util.MessageFormatUtil;
/*      */ import com.itextpdf.kernel.PdfException;
/*      */ import com.itextpdf.kernel.colors.Color;
/*      */ import com.itextpdf.kernel.colors.ColorConstants;
/*      */ import com.itextpdf.kernel.colors.DeviceCmyk;
/*      */ import com.itextpdf.kernel.colors.DeviceGray;
/*      */ import com.itextpdf.kernel.colors.DeviceRgb;
/*      */ import com.itextpdf.kernel.font.PdfFont;
/*      */ import com.itextpdf.kernel.font.PdfFontFactory;
/*      */ import com.itextpdf.kernel.geom.Matrix;
/*      */ import com.itextpdf.kernel.geom.Rectangle;
/*      */ import com.itextpdf.kernel.pdf.PdfAConformanceLevel;
/*      */ import com.itextpdf.kernel.pdf.PdfArray;
/*      */ import com.itextpdf.kernel.pdf.PdfDictionary;
/*      */ import com.itextpdf.kernel.pdf.PdfDocument;
/*      */ import com.itextpdf.kernel.pdf.PdfName;
/*      */ import com.itextpdf.kernel.pdf.PdfNumber;
/*      */ import com.itextpdf.kernel.pdf.PdfObject;
/*      */ import com.itextpdf.kernel.pdf.PdfObjectWrapper;
/*      */ import com.itextpdf.kernel.pdf.PdfOutputStream;
/*      */ import com.itextpdf.kernel.pdf.PdfPage;
/*      */ import com.itextpdf.kernel.pdf.PdfResources;
/*      */ import com.itextpdf.kernel.pdf.PdfStream;
/*      */ import com.itextpdf.kernel.pdf.PdfString;
/*      */ import com.itextpdf.kernel.pdf.action.PdfAction;
/*      */ import com.itextpdf.kernel.pdf.annot.PdfAnnotation;
/*      */ import com.itextpdf.kernel.pdf.annot.PdfWidgetAnnotation;
/*      */ import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
/*      */ import com.itextpdf.kernel.pdf.xobject.PdfFormXObject;
/*      */ import com.itextpdf.kernel.pdf.xobject.PdfImageXObject;
/*      */ import com.itextpdf.kernel.pdf.xobject.PdfXObject;
/*      */ import com.itextpdf.layout.Canvas;
/*      */ import com.itextpdf.layout.Style;
/*      */ import com.itextpdf.layout.element.Div;
/*      */ import com.itextpdf.layout.element.IBlockElement;
/*      */ import com.itextpdf.layout.element.Paragraph;
/*      */ import com.itextpdf.layout.element.Text;
/*      */ import com.itextpdf.layout.layout.LayoutArea;
/*      */ import com.itextpdf.layout.layout.LayoutContext;
/*      */ import com.itextpdf.layout.layout.LayoutResult;
/*      */ import com.itextpdf.layout.property.BoxSizingPropertyValue;
/*      */ import com.itextpdf.layout.property.Leading;
/*      */ import com.itextpdf.layout.property.OverflowPropertyValue;
/*      */ import com.itextpdf.layout.property.TextAlignment;
/*      */ import com.itextpdf.layout.property.TransparentColor;
/*      */ import com.itextpdf.layout.property.VerticalAlignment;
/*      */ import com.itextpdf.layout.renderer.IRenderer;
/*      */ import java.io.ByteArrayOutputStream;
/*      */ import java.io.IOException;
/*      */ import java.io.OutputStream;
/*      */ import java.nio.charset.StandardCharsets;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Arrays;
/*      */ import java.util.Collections;
/*      */ import java.util.LinkedHashSet;
/*      */ import java.util.List;
/*      */ import java.util.Map;
/*      */ import java.util.Set;
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
/*      */ public class PdfFormField
/*      */   extends PdfObjectWrapper<PdfDictionary>
/*      */ {
/*  134 */   public static final int FF_MULTILINE = makeFieldFlag(13);
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  139 */   public static final int FF_PASSWORD = makeFieldFlag(14);
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   public static final int DEFAULT_FONT_SIZE = 12;
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   public static final int MIN_FONT_SIZE = 4;
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   public static final int DA_FONT = 0;
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   public static final int DA_SIZE = 1;
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   public static final int DA_COLOR = 2;
/*      */ 
/*      */   
/*      */   public static final int ALIGN_LEFT = 0;
/*      */ 
/*      */   
/*      */   public static final int ALIGN_CENTER = 1;
/*      */ 
/*      */   
/*      */   public static final int ALIGN_RIGHT = 2;
/*      */ 
/*      */   
/*      */   public static final int TYPE_CHECK = 1;
/*      */ 
/*      */   
/*      */   public static final int TYPE_CIRCLE = 2;
/*      */ 
/*      */   
/*      */   public static final int TYPE_CROSS = 3;
/*      */ 
/*      */   
/*      */   public static final int TYPE_DIAMOND = 4;
/*      */ 
/*      */   
/*      */   public static final int TYPE_SQUARE = 5;
/*      */ 
/*      */   
/*      */   public static final int TYPE_STAR = 6;
/*      */ 
/*      */   
/*      */   public static final int HIDDEN = 1;
/*      */ 
/*      */   
/*      */   public static final int VISIBLE_BUT_DOES_NOT_PRINT = 2;
/*      */ 
/*      */   
/*      */   public static final int HIDDEN_BUT_PRINTABLE = 3;
/*      */ 
/*      */   
/*      */   public static final int VISIBLE = 4;
/*      */ 
/*      */   
/*  203 */   public static final int FF_READ_ONLY = makeFieldFlag(1);
/*  204 */   public static final int FF_REQUIRED = makeFieldFlag(2);
/*  205 */   public static final int FF_NO_EXPORT = makeFieldFlag(3);
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   public static final float X_OFFSET = 2.0F;
/*      */ 
/*      */   
/*  213 */   protected static String[] typeChars = new String[] { "4", "l", "8", "u", "n", "H" };
/*      */   
/*      */   protected String text;
/*      */   protected ImageData img;
/*      */   protected PdfFont font;
/*  218 */   protected float fontSize = -1.0F;
/*      */   protected Color color;
/*      */   protected int checkType;
/*  221 */   protected float borderWidth = 1.0F;
/*      */   protected Color backgroundColor;
/*      */   protected Color borderColor;
/*  224 */   protected int rotation = 0;
/*      */ 
/*      */   
/*      */   protected PdfFormXObject form;
/*      */ 
/*      */   
/*      */   protected PdfAConformanceLevel pdfAConformanceLevel;
/*      */ 
/*      */ 
/*      */   
/*      */   public PdfFormField(PdfDictionary pdfObject) {
/*  235 */     super((PdfObject)pdfObject);
/*  236 */     ensureObjectIsAddedToDocument((PdfObject)pdfObject);
/*  237 */     setForbidRelease();
/*  238 */     retrieveStyles();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected PdfFormField(PdfDocument pdfDocument) {
/*  247 */     this((PdfDictionary)(new PdfDictionary()).makeIndirect(pdfDocument));
/*  248 */     PdfName formType = getFormType();
/*  249 */     if (formType != null) {
/*  250 */       put(PdfName.FT, (PdfObject)formType);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected PdfFormField(PdfWidgetAnnotation widget, PdfDocument pdfDocument) {
/*  261 */     this((PdfDictionary)(new PdfDictionary()).makeIndirect(pdfDocument));
/*  262 */     widget.makeIndirect(pdfDocument);
/*  263 */     addKid(widget);
/*  264 */     put(PdfName.FT, (PdfObject)getFormType());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int makeFieldFlag(int bitPosition) {
/*  275 */     return 1 << bitPosition - 1;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static PdfFormField createEmptyField(PdfDocument doc) {
/*  286 */     return createEmptyField(doc, (PdfAConformanceLevel)null);
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
/*      */   public static PdfFormField createEmptyField(PdfDocument doc, PdfAConformanceLevel pdfAConformanceLevel) {
/*  298 */     PdfFormField field = new PdfFormField(doc);
/*  299 */     field.pdfAConformanceLevel = pdfAConformanceLevel;
/*  300 */     return field;
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
/*      */   public static PdfButtonFormField createButton(PdfDocument doc, Rectangle rect, int flags) {
/*  315 */     return createButton(doc, rect, flags, (PdfAConformanceLevel)null);
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
/*      */   public static PdfButtonFormField createButton(PdfDocument doc, Rectangle rect, int flags, PdfAConformanceLevel pdfAConformanceLevel) {
/*  331 */     PdfWidgetAnnotation annot = new PdfWidgetAnnotation(rect);
/*  332 */     PdfButtonFormField field = new PdfButtonFormField(annot, doc);
/*  333 */     field.pdfAConformanceLevel = pdfAConformanceLevel;
/*  334 */     if (null != pdfAConformanceLevel) {
/*  335 */       annot.setFlag(4);
/*      */     }
/*  337 */     field.setFieldFlags(flags);
/*  338 */     return field;
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
/*      */   public static PdfButtonFormField createButton(PdfDocument doc, int flags) {
/*  352 */     return createButton(doc, flags, (PdfAConformanceLevel)null);
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
/*      */   public static PdfButtonFormField createButton(PdfDocument doc, int flags, PdfAConformanceLevel pdfAConformanceLevel) {
/*  367 */     PdfButtonFormField field = new PdfButtonFormField(doc);
/*  368 */     field.pdfAConformanceLevel = pdfAConformanceLevel;
/*  369 */     field.setFieldFlags(flags);
/*  370 */     return field;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static PdfTextFormField createText(PdfDocument doc) {
/*  381 */     return createText(doc, (PdfAConformanceLevel)null);
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
/*      */   public static PdfTextFormField createText(PdfDocument doc, PdfAConformanceLevel pdfAConformanceLevel) {
/*  393 */     PdfTextFormField textFormField = new PdfTextFormField(doc);
/*  394 */     textFormField.pdfAConformanceLevel = pdfAConformanceLevel;
/*  395 */     return textFormField;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static PdfTextFormField createText(PdfDocument doc, Rectangle rect) {
/*  406 */     PdfWidgetAnnotation annot = new PdfWidgetAnnotation(rect);
/*  407 */     return new PdfTextFormField(annot, doc);
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
/*      */   public static PdfTextFormField createText(PdfDocument doc, Rectangle rect, String name) {
/*  421 */     return createText(doc, rect, name, "");
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
/*      */   public static PdfTextFormField createText(PdfDocument doc, Rectangle rect, String name, String value) {
/*  436 */     return createText(doc, rect, name, value, (PdfFont)null, -1.0F);
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
/*      */   public static PdfTextFormField createText(PdfDocument doc, Rectangle rect, String name, String value, PdfFont font, float fontSize) {
/*  452 */     return createText(doc, rect, name, value, font, fontSize, false);
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
/*      */   public static PdfTextFormField createText(PdfDocument doc, Rectangle rect, String name, String value, PdfFont font, float fontSize, boolean multiline) {
/*  469 */     return createText(doc, rect, name, value, font, fontSize, multiline, (PdfAConformanceLevel)null);
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
/*      */   public static PdfTextFormField createText(PdfDocument doc, Rectangle rect, String name, String value, PdfFont font, float fontSize, boolean multiline, PdfAConformanceLevel pdfAConformanceLevel) {
/*  487 */     PdfWidgetAnnotation annot = new PdfWidgetAnnotation(rect);
/*  488 */     PdfTextFormField field = new PdfTextFormField(annot, doc);
/*      */     
/*  490 */     field.pdfAConformanceLevel = pdfAConformanceLevel;
/*  491 */     if (null != pdfAConformanceLevel) {
/*  492 */       annot.setFlag(4);
/*      */     }
/*      */     
/*  495 */     field.updateFontAndFontSize(font, fontSize);
/*  496 */     field.setMultiline(multiline);
/*  497 */     field.setFieldName(name);
/*  498 */     field.setValue(value);
/*      */     
/*  500 */     return field;
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
/*      */   public static PdfTextFormField createMultilineText(PdfDocument doc, Rectangle rect, String name, String value, PdfFont font, float fontSize) {
/*  516 */     return createText(doc, rect, name, value, font, fontSize, true);
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
/*      */   public static PdfTextFormField createMultilineText(PdfDocument doc, Rectangle rect, String name, String value) {
/*  531 */     return createText(doc, rect, name, value, (PdfFont)null, -1.0F, true);
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
/*      */   public static PdfChoiceFormField createChoice(PdfDocument doc, int flags) {
/*  544 */     return createChoice(doc, flags, (PdfAConformanceLevel)null);
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
/*      */   public static PdfChoiceFormField createChoice(PdfDocument doc, int flags, PdfAConformanceLevel pdfAConformanceLevel) {
/*  558 */     PdfChoiceFormField field = new PdfChoiceFormField(doc);
/*  559 */     field.pdfAConformanceLevel = pdfAConformanceLevel;
/*  560 */     field.setFieldFlags(flags);
/*  561 */     return field;
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
/*      */   public static PdfChoiceFormField createChoice(PdfDocument doc, Rectangle rect, int flags) {
/*  576 */     PdfWidgetAnnotation annot = new PdfWidgetAnnotation(rect);
/*  577 */     PdfChoiceFormField field = new PdfChoiceFormField(annot, doc);
/*  578 */     field.setFieldFlags(flags);
/*  579 */     return field;
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
/*      */   public static PdfChoiceFormField createChoice(PdfDocument doc, Rectangle rect, String name, String value, PdfArray options, int flags) {
/*  598 */     return createChoice(doc, rect, name, value, (PdfFont)null, -1.0F, options, flags);
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
/*      */   public static PdfChoiceFormField createChoice(PdfDocument doc, Rectangle rect, String name, String value, PdfArray options, int flags, PdfFont font, PdfAConformanceLevel pdfAConformanceLevel) {
/*  619 */     return createChoice(doc, rect, name, value, font, 12.0F, options, flags, pdfAConformanceLevel);
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
/*      */   public static PdfChoiceFormField createChoice(PdfDocument doc, Rectangle rect, String name, String value, PdfFont font, float fontSize, PdfArray options, int flags) {
/*  640 */     return createChoice(doc, rect, name, value, font, fontSize, options, flags, (PdfAConformanceLevel)null);
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
/*      */   public static PdfChoiceFormField createChoice(PdfDocument doc, Rectangle rect, String name, String value, PdfFont font, float fontSize, PdfArray options, int flags, PdfAConformanceLevel pdfAConformanceLevel) {
/*  662 */     PdfWidgetAnnotation annot = new PdfWidgetAnnotation(rect);
/*  663 */     PdfFormField field = new PdfChoiceFormField(annot, doc);
/*  664 */     field.pdfAConformanceLevel = pdfAConformanceLevel;
/*  665 */     if (null != pdfAConformanceLevel) {
/*  666 */       annot.setFlag(4);
/*      */     }
/*      */     
/*  669 */     field.updateFontAndFontSize(font, fontSize);
/*  670 */     field.put(PdfName.Opt, (PdfObject)options);
/*  671 */     field.setFieldFlags(flags);
/*  672 */     field.setFieldName(name);
/*  673 */     ((PdfChoiceFormField)field).setListSelected(new String[] { value }, false);
/*  674 */     if ((flags & PdfChoiceFormField.FF_COMBO) == 0) {
/*  675 */       value = optionsArrayToString(options);
/*      */     }
/*      */     
/*  678 */     PdfFormXObject xObject = new PdfFormXObject(new Rectangle(0.0F, 0.0F, rect.getWidth(), rect.getHeight()));
/*  679 */     field.drawChoiceAppearance(rect, field.fontSize, value, xObject, 0);
/*  680 */     annot.setNormalAppearance((PdfDictionary)xObject.getPdfObject());
/*      */     
/*  682 */     return (PdfChoiceFormField)field;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static PdfSignatureFormField createSignature(PdfDocument doc) {
/*  692 */     return createSignature(doc, (PdfAConformanceLevel)null);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static PdfSignatureFormField createSignature(PdfDocument doc, PdfAConformanceLevel pdfAConformanceLevel) {
/*  703 */     PdfSignatureFormField signatureFormField = new PdfSignatureFormField(doc);
/*  704 */     signatureFormField.pdfAConformanceLevel = pdfAConformanceLevel;
/*  705 */     return signatureFormField;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static PdfSignatureFormField createSignature(PdfDocument doc, Rectangle rect) {
/*  716 */     return createSignature(doc, rect, (PdfAConformanceLevel)null);
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
/*      */   public static PdfSignatureFormField createSignature(PdfDocument doc, Rectangle rect, PdfAConformanceLevel pdfAConformanceLevel) {
/*  728 */     PdfWidgetAnnotation annot = new PdfWidgetAnnotation(rect);
/*  729 */     PdfSignatureFormField signatureFormField = new PdfSignatureFormField(annot, doc);
/*  730 */     signatureFormField.pdfAConformanceLevel = pdfAConformanceLevel;
/*  731 */     if (null != pdfAConformanceLevel) {
/*  732 */       annot.setFlag(4);
/*      */     }
/*  734 */     return signatureFormField;
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
/*      */   public static PdfButtonFormField createRadioGroup(PdfDocument doc, String name, String value) {
/*  746 */     return createRadioGroup(doc, name, value, (PdfAConformanceLevel)null);
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
/*      */   public static PdfButtonFormField createRadioGroup(PdfDocument doc, String name, String value, PdfAConformanceLevel pdfAConformanceLevel) {
/*  759 */     PdfButtonFormField radio = createButton(doc, PdfButtonFormField.FF_RADIO);
/*  760 */     radio.setFieldName(name);
/*  761 */     radio.put(PdfName.V, (PdfObject)new PdfName(value));
/*  762 */     radio.pdfAConformanceLevel = pdfAConformanceLevel;
/*  763 */     return radio;
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
/*      */   public static PdfFormField createRadioButton(PdfDocument doc, Rectangle rect, PdfButtonFormField radioGroup, String value) {
/*  777 */     PdfWidgetAnnotation annot = new PdfWidgetAnnotation(rect);
/*  778 */     PdfFormField radio = new PdfButtonFormField(annot, doc);
/*      */     
/*  780 */     String name = radioGroup.getValue().toString().substring(1);
/*  781 */     if (name.equals(value)) {
/*  782 */       annot.setAppearanceState(new PdfName(value));
/*      */     } else {
/*  784 */       annot.setAppearanceState(new PdfName("Off"));
/*      */     } 
/*  786 */     radio.drawRadioAppearance(rect.getWidth(), rect.getHeight(), value);
/*  787 */     radioGroup.addKid(radio);
/*  788 */     return radio;
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
/*      */   public static PdfFormField createRadioButton(PdfDocument doc, Rectangle rect, PdfButtonFormField radioGroup, String value, PdfAConformanceLevel pdfAConformanceLevel) {
/*  803 */     PdfWidgetAnnotation annot = new PdfWidgetAnnotation(rect);
/*  804 */     PdfFormField radio = new PdfButtonFormField(annot, doc);
/*  805 */     radio.pdfAConformanceLevel = pdfAConformanceLevel;
/*  806 */     if (null != pdfAConformanceLevel) {
/*  807 */       annot.setFlag(4);
/*      */     }
/*      */     
/*  810 */     String name = radioGroup.getValue().toString().substring(1);
/*  811 */     if (name.equals(value)) {
/*  812 */       annot.setAppearanceState(new PdfName(value));
/*      */     } else {
/*  814 */       annot.setAppearanceState(new PdfName("Off"));
/*      */     } 
/*  816 */     radio.drawRadioAppearance(rect.getWidth(), rect.getHeight(), value);
/*      */     
/*  818 */     radioGroup.addKid(radio);
/*  819 */     return radio;
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
/*      */   public static PdfButtonFormField createPushButton(PdfDocument doc, Rectangle rect, String name, String caption) {
/*      */     PdfButtonFormField field;
/*      */     try {
/*  834 */       field = createPushButton(doc, rect, name, caption, PdfFontFactory.createFont(), 12.0F);
/*  835 */     } catch (IOException e) {
/*  836 */       throw new PdfException(e);
/*      */     } 
/*  838 */     return field;
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
/*      */   public static PdfButtonFormField createPushButton(PdfDocument doc, Rectangle rect, String name, String caption, PdfFont font, float fontSize) {
/*  854 */     return createPushButton(doc, rect, name, caption, font, fontSize, (PdfAConformanceLevel)null);
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
/*      */   public static PdfButtonFormField createPushButton(PdfDocument doc, Rectangle rect, String name, String caption, PdfFont font, float fontSize, PdfAConformanceLevel pdfAConformanceLevel) {
/*  871 */     PdfWidgetAnnotation annot = new PdfWidgetAnnotation(rect);
/*  872 */     PdfButtonFormField field = new PdfButtonFormField(annot, doc);
/*  873 */     field.pdfAConformanceLevel = pdfAConformanceLevel;
/*  874 */     if (null != pdfAConformanceLevel) {
/*  875 */       annot.setFlag(4);
/*      */     }
/*  877 */     field.setPushButton(true);
/*  878 */     field.setFieldName(name);
/*  879 */     field.text = caption;
/*  880 */     field.updateFontAndFontSize(font, fontSize);
/*  881 */     field.backgroundColor = ColorConstants.LIGHT_GRAY;
/*      */     
/*  883 */     PdfFormXObject xObject = field.drawPushButtonAppearance(rect.getWidth(), rect.getHeight(), caption, font, fontSize);
/*  884 */     annot.setNormalAppearance((PdfDictionary)xObject.getPdfObject());
/*      */     
/*  886 */     PdfDictionary mk = new PdfDictionary();
/*  887 */     mk.put(PdfName.CA, (PdfObject)new PdfString(caption));
/*  888 */     mk.put(PdfName.BG, (PdfObject)new PdfArray(field.backgroundColor.getColorValue()));
/*  889 */     annot.setAppearanceCharacteristics(mk);
/*      */     
/*  891 */     if (pdfAConformanceLevel != null) {
/*  892 */       createPushButtonAppearanceState((PdfDictionary)annot.getPdfObject());
/*      */     }
/*      */     
/*  895 */     return field;
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
/*      */   public static PdfButtonFormField createCheckBox(PdfDocument doc, Rectangle rect, String name, String value) {
/*  908 */     return createCheckBox(doc, rect, name, value, 3);
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
/*      */   public static PdfButtonFormField createCheckBox(PdfDocument doc, Rectangle rect, String name, String value, int checkType) {
/*  922 */     return createCheckBox(doc, rect, name, value, checkType, (PdfAConformanceLevel)null);
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
/*      */   public static PdfButtonFormField createCheckBox(PdfDocument doc, Rectangle rect, String name, String value, int checkType, PdfAConformanceLevel pdfAConformanceLevel) {
/*  938 */     PdfWidgetAnnotation annot = new PdfWidgetAnnotation(rect);
/*  939 */     PdfButtonFormField check = new PdfButtonFormField(annot, doc);
/*  940 */     check.pdfAConformanceLevel = pdfAConformanceLevel;
/*  941 */     check.setFontSize(0);
/*  942 */     check.setCheckType(checkType);
/*  943 */     check.setFieldName(name);
/*  944 */     check.put(PdfName.V, (PdfObject)new PdfName(value));
/*  945 */     annot.setAppearanceState(new PdfName(value));
/*      */     
/*  947 */     if (pdfAConformanceLevel != null) {
/*  948 */       check.drawPdfA2CheckAppearance(rect.getWidth(), rect.getHeight(), "Off".equals(value) ? "Yes" : value, checkType);
/*  949 */       annot.setFlag(4);
/*      */     } else {
/*  951 */       check.drawCheckAppearance(rect.getWidth(), rect.getHeight(), "Off".equals(value) ? "Yes" : value);
/*      */     } 
/*      */     
/*  954 */     return check;
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
/*      */   public static PdfChoiceFormField createComboBox(PdfDocument doc, Rectangle rect, String name, String value, String[][] options) {
/*      */     try {
/*  971 */       return createComboBox(doc, rect, name, value, options, PdfFontFactory.createFont(), (PdfAConformanceLevel)null);
/*  972 */     } catch (IOException e) {
/*  973 */       throw new PdfException(e);
/*      */     } 
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
/*      */   public static PdfChoiceFormField createComboBox(PdfDocument doc, Rectangle rect, String name, String value, String[][] options, PdfFont font, PdfAConformanceLevel pdfAConformanceLevel) {
/*  992 */     return createChoice(doc, rect, name, value, processOptions(options), PdfChoiceFormField.FF_COMBO, font, pdfAConformanceLevel);
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
/*      */   public static PdfChoiceFormField createComboBox(PdfDocument doc, Rectangle rect, String name, String value, String[] options) {
/* 1007 */     return createComboBox(doc, rect, name, value, options, (PdfFont)null, (PdfAConformanceLevel)null);
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
/*      */   public static PdfChoiceFormField createComboBox(PdfDocument doc, Rectangle rect, String name, String value, String[] options, PdfFont font, PdfAConformanceLevel pdfAConformanceLevel) {
/* 1024 */     return createChoice(doc, rect, name, value, processOptions(options), PdfChoiceFormField.FF_COMBO, font, pdfAConformanceLevel);
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
/*      */   public static PdfChoiceFormField createList(PdfDocument doc, Rectangle rect, String name, String value, String[][] options) {
/* 1040 */     return createList(doc, rect, name, value, options, (PdfFont)null, (PdfAConformanceLevel)null);
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
/*      */   public static PdfChoiceFormField createList(PdfDocument doc, Rectangle rect, String name, String value, String[][] options, PdfFont font, PdfAConformanceLevel pdfAConformanceLevel) {
/* 1058 */     return createChoice(doc, rect, name, value, processOptions(options), 0, font, pdfAConformanceLevel);
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
/*      */   public static PdfChoiceFormField createList(PdfDocument doc, Rectangle rect, String name, String value, String[] options) {
/* 1073 */     return createList(doc, rect, name, value, options, (PdfFont)null, (PdfAConformanceLevel)null);
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
/*      */   public static PdfChoiceFormField createList(PdfDocument doc, Rectangle rect, String name, String value, String[] options, PdfFont font, PdfAConformanceLevel pdfAConformanceLevel) {
/* 1090 */     return createChoice(doc, rect, name, value, processOptions(options), 0, font, pdfAConformanceLevel);
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
/*      */   public static PdfFormField makeFormField(PdfObject pdfObject, PdfDocument document) {
/* 1104 */     if (pdfObject.isDictionary()) {
/*      */       PdfFormField field;
/* 1106 */       PdfDictionary dictionary = (PdfDictionary)pdfObject;
/* 1107 */       PdfName formType = dictionary.getAsName(PdfName.FT);
/* 1108 */       if (PdfName.Tx.equals(formType)) {
/* 1109 */         field = new PdfTextFormField(dictionary);
/* 1110 */       } else if (PdfName.Btn.equals(formType)) {
/* 1111 */         field = new PdfButtonFormField(dictionary);
/* 1112 */       } else if (PdfName.Ch.equals(formType)) {
/* 1113 */         field = new PdfChoiceFormField(dictionary);
/* 1114 */       } else if (PdfName.Sig.equals(formType)) {
/* 1115 */         field = new PdfSignatureFormField(dictionary);
/*      */       } else {
/* 1117 */         field = new PdfFormField(dictionary);
/*      */       } 
/* 1119 */       field.makeIndirect(document);
/*      */       
/* 1121 */       if (document != null && document.getReader() != null && document.getReader().getPdfAConformanceLevel() != null) {
/* 1122 */         field.pdfAConformanceLevel = document.getReader().getPdfAConformanceLevel();
/*      */       }
/* 1124 */       return field;
/*      */     } 
/*      */     
/* 1127 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PdfName getFormType() {
/* 1137 */     PdfName formType = ((PdfDictionary)getPdfObject()).getAsName(PdfName.FT);
/* 1138 */     if (formType == null) {
/* 1139 */       return getTypeFromParent((PdfDictionary)getPdfObject());
/*      */     }
/* 1141 */     return formType;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PdfFormField setValue(String value) {
/* 1151 */     PdfName formType = getFormType();
/* 1152 */     boolean autoGenerateAppearance = (!PdfName.Btn.equals(formType) || !getFieldFlag(PdfButtonFormField.FF_RADIO));
/* 1153 */     return setValue(value, autoGenerateAppearance);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PdfFormField setValue(String value, boolean generateAppearance) {
/* 1164 */     PdfName formType = getFormType();
/* 1165 */     if (formType == null || !PdfName.Btn.equals(formType)) {
/* 1166 */       PdfArray kids = getKids();
/* 1167 */       if (kids != null) {
/* 1168 */         for (PdfObject kid : kids) {
/* 1169 */           if (kid.isDictionary() && ((PdfDictionary)kid).getAsString(PdfName.T) != null) {
/* 1170 */             PdfFormField field = new PdfFormField((PdfDictionary)kid);
/* 1171 */             field.setValue(value);
/* 1172 */             if (field.getDefaultAppearance() == null) {
/* 1173 */               field.font = this.font;
/* 1174 */               field.fontSize = this.fontSize;
/* 1175 */               field.color = this.color;
/*      */             } 
/*      */           } 
/*      */         } 
/*      */       }
/* 1180 */       if (PdfName.Ch.equals(formType)) {
/* 1181 */         if (this instanceof PdfChoiceFormField) {
/* 1182 */           ((PdfChoiceFormField)this).setListSelected(new String[] { value }, false);
/*      */         } else {
/* 1184 */           PdfChoiceFormField choice = new PdfChoiceFormField((PdfDictionary)getPdfObject());
/* 1185 */           choice.setListSelected(new String[] { value }, false);
/*      */         } 
/*      */       } else {
/* 1188 */         put(PdfName.V, (PdfObject)new PdfString(value, "UnicodeBig"));
/*      */       } 
/* 1190 */     } else if (PdfName.Btn.equals(formType)) {
/* 1191 */       if (getFieldFlag(PdfButtonFormField.FF_PUSH_BUTTON)) {
/*      */         try {
/* 1193 */           this.img = ImageDataFactory.create(Base64.decode(value));
/* 1194 */         } catch (Exception e) {
/* 1195 */           this.text = value;
/*      */         } 
/*      */       } else {
/* 1198 */         put(PdfName.V, (PdfObject)new PdfName(value));
/* 1199 */         for (PdfWidgetAnnotation widget : getWidgets()) {
/*      */           
/* 1201 */           List<String> states = Arrays.asList((new PdfFormField((PdfDictionary)widget.getPdfObject())).getAppearanceStates());
/* 1202 */           if (states.contains(value)) {
/* 1203 */             widget.setAppearanceState(new PdfName(value)); continue;
/*      */           } 
/* 1205 */           widget.setAppearanceState(new PdfName("Off"));
/*      */         } 
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/* 1211 */     if (generateAppearance) {
/* 1212 */       regenerateField();
/*      */     }
/*      */     
/* 1215 */     setModified();
/* 1216 */     return this;
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
/*      */   public PdfFormField setValue(String value, PdfFont font, float fontSize) {
/* 1228 */     updateFontAndFontSize(font, fontSize);
/* 1229 */     return setValue(value);
/*      */   }
/*      */   
/*      */   private void updateFontAndFontSize(PdfFont font, float fontSize) {
/* 1233 */     if (font == null) {
/* 1234 */       font = getDocument().getDefaultFont();
/*      */     }
/* 1236 */     this.font = font;
/* 1237 */     if (fontSize < 0.0F) {
/* 1238 */       fontSize = 12.0F;
/*      */     }
/* 1240 */     this.fontSize = fontSize;
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
/*      */   public PdfFormField setValue(String value, String display) {
/* 1253 */     if (display == null) {
/* 1254 */       return setValue(value);
/*      */     }
/* 1256 */     setValue(display, true);
/* 1257 */     PdfName formType = getFormType();
/* 1258 */     if (PdfName.Btn.equals(formType)) {
/* 1259 */       if ((getFieldFlags() & PdfButtonFormField.FF_PUSH_BUTTON) != 0) {
/* 1260 */         this.text = value;
/*      */       } else {
/* 1262 */         put(PdfName.V, (PdfObject)new PdfName(value));
/*      */       } 
/*      */     } else {
/* 1265 */       put(PdfName.V, (PdfObject)new PdfString(value, "UnicodeBig"));
/*      */     } 
/* 1267 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PdfFormField setParent(PdfFormField parent) {
/* 1277 */     return put(PdfName.Parent, parent.getPdfObject());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PdfDictionary getParent() {
/* 1286 */     return ((PdfDictionary)getPdfObject()).getAsDictionary(PdfName.Parent);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PdfArray getKids() {
/* 1295 */     return ((PdfDictionary)getPdfObject()).getAsArray(PdfName.Kids);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PdfFormField addKid(PdfFormField kid) {
/* 1306 */     kid.setParent(this);
/* 1307 */     PdfArray kids = getKids();
/* 1308 */     if (kids == null) {
/* 1309 */       kids = new PdfArray();
/*      */     }
/* 1311 */     kids.add(kid.getPdfObject());
/*      */     
/* 1313 */     return put(PdfName.Kids, (PdfObject)kids);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PdfFormField addKid(PdfWidgetAnnotation kid) {
/* 1324 */     kid.setParent(getPdfObject());
/* 1325 */     PdfArray kids = getKids();
/* 1326 */     if (kids == null) {
/* 1327 */       kids = new PdfArray();
/*      */     }
/* 1329 */     kids.add(kid.getPdfObject());
/* 1330 */     return put(PdfName.Kids, (PdfObject)kids);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PdfFormField setFieldName(String name) {
/* 1340 */     return put(PdfName.T, (PdfObject)new PdfString(name));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PdfString getFieldName() {
/* 1349 */     String parentName = "";
/* 1350 */     PdfDictionary parent = getParent();
/* 1351 */     if (parent != null) {
/* 1352 */       PdfFormField parentField = makeFormField((PdfObject)getParent(), getDocument());
/* 1353 */       PdfString pName = parentField.getFieldName();
/* 1354 */       if (pName != null) {
/* 1355 */         parentName = pName.toUnicodeString() + ".";
/*      */       }
/*      */     } 
/* 1358 */     PdfString name = ((PdfDictionary)getPdfObject()).getAsString(PdfName.T);
/* 1359 */     if (name != null) {
/* 1360 */       name = new PdfString(parentName + name.toUnicodeString(), "UnicodeBig");
/*      */     }
/* 1362 */     return name;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PdfFormField setAlternativeName(String name) {
/* 1373 */     return put(PdfName.TU, (PdfObject)new PdfString(name));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PdfString getAlternativeName() {
/* 1383 */     return ((PdfDictionary)getPdfObject()).getAsString(PdfName.TU);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PdfFormField setMappingName(String name) {
/* 1394 */     return put(PdfName.TM, (PdfObject)new PdfString(name));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PdfString getMappingName() {
/* 1404 */     return ((PdfDictionary)getPdfObject()).getAsString(PdfName.TM);
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
/*      */   public boolean getFieldFlag(int flag) {
/* 1416 */     return ((getFieldFlags() & flag) != 0);
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
/*      */   public PdfFormField setFieldFlag(int flag) {
/* 1429 */     return setFieldFlag(flag, true);
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
/*      */   public PdfFormField setFieldFlag(int flag, boolean value) {
/* 1444 */     int flags = getFieldFlags();
/*      */     
/* 1446 */     if (value) {
/* 1447 */       flags |= flag;
/*      */     } else {
/* 1449 */       flags &= flag ^ 0xFFFFFFFF;
/*      */     } 
/*      */     
/* 1452 */     return setFieldFlags(flags);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isMultiline() {
/* 1461 */     return getFieldFlag(FF_MULTILINE);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isPassword() {
/* 1471 */     return getFieldFlag(FF_PASSWORD);
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
/*      */   public PdfFormField setFieldFlags(int flags) {
/* 1483 */     int oldFlags = getFieldFlags();
/* 1484 */     put(PdfName.Ff, (PdfObject)new PdfNumber(flags));
/* 1485 */     if (((oldFlags ^ flags) & PdfTextFormField.FF_COMB) != 0 && PdfName.Tx
/* 1486 */       .equals(getFormType()) && (new PdfTextFormField((PdfDictionary)getPdfObject())).getMaxLen() != 0)
/* 1487 */       regenerateField(); 
/* 1488 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getFieldFlags() {
/* 1497 */     PdfNumber f = ((PdfDictionary)getPdfObject()).getAsNumber(PdfName.Ff);
/* 1498 */     if (f != null) {
/* 1499 */       return f.intValue();
/*      */     }
/* 1501 */     PdfDictionary parent = getParent();
/* 1502 */     if (parent != null) {
/* 1503 */       return (new PdfFormField(parent)).getFieldFlags();
/*      */     }
/* 1505 */     return 0;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PdfObject getValue() {
/* 1516 */     if (((PdfDictionary)getPdfObject()).get(PdfName.T) == null && getParent() != null) {
/* 1517 */       return getParent().get(PdfName.V);
/*      */     }
/* 1519 */     return ((PdfDictionary)getPdfObject()).get(PdfName.V);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getValueAsString() {
/* 1528 */     PdfObject value = getValue();
/* 1529 */     if (value == null)
/* 1530 */       return ""; 
/* 1531 */     if (value instanceof PdfStream)
/* 1532 */       return new String(((PdfStream)value).getBytes(), StandardCharsets.UTF_8); 
/* 1533 */     if (value instanceof PdfName)
/* 1534 */       return ((PdfName)value).getValue(); 
/* 1535 */     if (value instanceof PdfString) {
/* 1536 */       return ((PdfString)value).toUnicodeString();
/*      */     }
/* 1538 */     return "";
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PdfFormField setDefaultValue(PdfObject value) {
/* 1549 */     return put(PdfName.DV, value);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PdfObject getDefaultValue() {
/* 1558 */     return ((PdfDictionary)getPdfObject()).get(PdfName.DV);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PdfFormField setAdditionalAction(PdfName key, PdfAction action) {
/* 1569 */     PdfAction.setAdditionalAction(this, key, action);
/* 1570 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PdfDictionary getAdditionalAction() {
/* 1579 */     return ((PdfDictionary)getPdfObject()).getAsDictionary(PdfName.AA);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PdfFormField setOptions(PdfArray options) {
/* 1590 */     return put(PdfName.Opt, (PdfObject)options);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PdfArray getOptions() {
/* 1600 */     return ((PdfDictionary)getPdfObject()).getAsArray(PdfName.Opt);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public List<PdfWidgetAnnotation> getWidgets() {
/* 1610 */     List<PdfWidgetAnnotation> widgets = new ArrayList<>();
/*      */     
/* 1612 */     PdfName subType = ((PdfDictionary)getPdfObject()).getAsName(PdfName.Subtype);
/* 1613 */     if (subType != null && subType.equals(PdfName.Widget)) {
/* 1614 */       widgets.add((PdfWidgetAnnotation)PdfAnnotation.makeAnnotation(getPdfObject()));
/*      */     }
/*      */     
/* 1617 */     PdfArray kids = getKids();
/* 1618 */     if (kids != null) {
/* 1619 */       for (int i = 0; i < kids.size(); i++) {
/* 1620 */         PdfObject kid = kids.get(i);
/* 1621 */         subType = ((PdfDictionary)kid).getAsName(PdfName.Subtype);
/* 1622 */         if (subType != null && subType.equals(PdfName.Widget)) {
/* 1623 */           widgets.add((PdfWidgetAnnotation)PdfAnnotation.makeAnnotation(kid));
/*      */         }
/*      */       } 
/*      */     }
/*      */     
/* 1628 */     return widgets;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PdfString getDefaultAppearance() {
/* 1638 */     PdfString defaultAppearance = ((PdfDictionary)getPdfObject()).getAsString(PdfName.DA);
/* 1639 */     if (defaultAppearance == null) {
/* 1640 */       PdfDictionary parent = getParent();
/* 1641 */       if (parent != null)
/*      */       {
/*      */         
/* 1644 */         if (parent.containsKey(PdfName.FT)) {
/* 1645 */           defaultAppearance = parent.getAsString(PdfName.DA);
/*      */         }
/*      */       }
/*      */     } 
/*      */     
/* 1650 */     if (defaultAppearance == null) {
/* 1651 */       defaultAppearance = (PdfString)getAcroFormKey(PdfName.DA, 10);
/*      */     }
/* 1653 */     return defaultAppearance;
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
/*      */   @Deprecated
/*      */   public PdfFormField setDefaultAppearance(String defaultAppearance) {
/* 1666 */     byte[] b = defaultAppearance.getBytes(StandardCharsets.UTF_8);
/* 1667 */     for (int k = 0; k < b.length; k++) {
/* 1668 */       if (b[k] == 10)
/* 1669 */         b[k] = 32; 
/*      */     } 
/* 1671 */     put(PdfName.DA, (PdfObject)new PdfString(new String(b, StandardCharsets.UTF_8)));
/* 1672 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void updateDefaultAppearance() {
/* 1681 */     if (hasDefaultAppearance()) {
/* 1682 */       assert this.font != null;
/*      */       
/* 1684 */       PdfDictionary defaultResources = (PdfDictionary)getAcroFormObject(PdfName.DR, 3);
/* 1685 */       if (defaultResources == null) {
/*      */         
/* 1687 */         addAcroFormToCatalog();
/* 1688 */         defaultResources = new PdfDictionary();
/* 1689 */         putAcroFormObject(PdfName.DR, (PdfObject)defaultResources);
/*      */       } 
/* 1691 */       PdfDictionary fontResources = defaultResources.getAsDictionary(PdfName.Font);
/* 1692 */       if (fontResources == null) {
/* 1693 */         fontResources = new PdfDictionary();
/* 1694 */         defaultResources.put(PdfName.Font, (PdfObject)fontResources);
/*      */       } 
/* 1696 */       PdfName fontName = getFontNameFromDR(fontResources, this.font.getPdfObject());
/* 1697 */       if (fontName == null) {
/* 1698 */         fontName = getUniqueFontNameForDR(fontResources);
/* 1699 */         fontResources.put(fontName, this.font.getPdfObject());
/* 1700 */         fontResources.setModified();
/*      */       } 
/*      */       
/* 1703 */       put(PdfName.DA, (PdfObject)generateDefaultAppearance(fontName, this.fontSize, this.color));
/*      */       
/* 1705 */       getDocument().addFont(this.font);
/*      */     } else {
/* 1707 */       ((PdfDictionary)getPdfObject()).remove(PdfName.DA);
/* 1708 */       setModified();
/*      */     } 
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
/*      */   public Integer getJustification() {
/* 1721 */     Integer justification = ((PdfDictionary)getPdfObject()).getAsInt(PdfName.Q);
/* 1722 */     if (justification == null && getParent() != null) {
/* 1723 */       justification = getParent().getAsInt(PdfName.Q);
/*      */     }
/* 1725 */     return justification;
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
/*      */   public PdfFormField setJustification(int justification) {
/* 1738 */     put(PdfName.Q, (PdfObject)new PdfNumber(justification));
/* 1739 */     regenerateField();
/* 1740 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PdfString getDefaultStyle() {
/* 1749 */     return ((PdfDictionary)getPdfObject()).getAsString(PdfName.DS);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PdfFormField setDefaultStyle(PdfString defaultStyleString) {
/* 1759 */     put(PdfName.DS, (PdfObject)defaultStyleString);
/* 1760 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PdfObject getRichText() {
/* 1770 */     return ((PdfDictionary)getPdfObject()).get(PdfName.RV);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PdfFormField setRichText(PdfObject richText) {
/* 1781 */     put(PdfName.RV, richText);
/* 1782 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public float getFontSize() {
/* 1791 */     return this.fontSize;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PdfFont getFont() {
/* 1801 */     return this.font;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Color getColor() {
/* 1810 */     return this.color;
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
/*      */   public PdfFormField setFont(PdfFont font) {
/* 1823 */     updateFontAndFontSize(font, this.fontSize);
/* 1824 */     regenerateField();
/* 1825 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PdfFormField setFontSize(float fontSize) {
/* 1836 */     updateFontAndFontSize(this.font, fontSize);
/* 1837 */     regenerateField();
/* 1838 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PdfFormField setFontSize(int fontSize) {
/* 1849 */     setFontSize(fontSize);
/* 1850 */     return this;
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
/*      */   @Deprecated
/*      */   public PdfFormField setFontAndSize(PdfFont font, int fontSize) {
/* 1864 */     updateFontAndFontSize(font, fontSize);
/* 1865 */     regenerateField();
/* 1866 */     return this;
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
/*      */   public PdfFormField setFontAndSize(PdfFont font, float fontSize) {
/* 1878 */     updateFontAndFontSize(font, fontSize);
/* 1879 */     regenerateField();
/* 1880 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PdfFormField setBackgroundColor(Color backgroundColor) {
/* 1891 */     this.backgroundColor = backgroundColor;
/*      */     
/* 1893 */     List<PdfWidgetAnnotation> kids = getWidgets();
/* 1894 */     for (PdfWidgetAnnotation kid : kids) {
/* 1895 */       PdfDictionary mk = kid.getAppearanceCharacteristics();
/* 1896 */       if (mk == null) {
/* 1897 */         mk = new PdfDictionary();
/*      */       }
/* 1899 */       if (backgroundColor == null) {
/* 1900 */         mk.remove(PdfName.BG);
/*      */       } else {
/* 1902 */         mk.put(PdfName.BG, (PdfObject)new PdfArray(backgroundColor.getColorValue()));
/*      */       } 
/* 1904 */       kid.setAppearanceCharacteristics(mk);
/*      */     } 
/* 1906 */     regenerateField();
/* 1907 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PdfFormField setRotation(int degRotation) {
/* 1918 */     if (degRotation % 90 != 0) {
/* 1919 */       throw new IllegalArgumentException("degRotation.must.be.a.multiple.of.90");
/*      */     }
/* 1921 */     degRotation %= 360;
/* 1922 */     if (degRotation < 0) {
/* 1923 */       degRotation += 360;
/*      */     }
/*      */     
/* 1926 */     this.rotation = degRotation;
/*      */     
/* 1928 */     PdfDictionary mk = ((PdfWidgetAnnotation)getWidgets().get(0)).getAppearanceCharacteristics();
/* 1929 */     if (mk == null) {
/* 1930 */       mk = new PdfDictionary();
/* 1931 */       put(PdfName.MK, (PdfObject)mk);
/*      */     } 
/* 1933 */     mk.put(PdfName.R, (PdfObject)new PdfNumber(degRotation));
/*      */     
/* 1935 */     this.rotation = degRotation;
/* 1936 */     regenerateField();
/* 1937 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PdfFormField setAction(PdfAction action) {
/* 1947 */     List<PdfWidgetAnnotation> widgets = getWidgets();
/* 1948 */     if (widgets != null) {
/* 1949 */       for (PdfWidgetAnnotation widget : widgets) {
/* 1950 */         widget.setAction(action);
/*      */       }
/*      */     }
/* 1953 */     return this;
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
/*      */   public PdfFormField setCheckType(int checkType) {
/* 1965 */     if (checkType < 1 || checkType > 6) {
/* 1966 */       checkType = 3;
/*      */     }
/* 1968 */     this.checkType = checkType;
/* 1969 */     this.text = typeChars[checkType - 1];
/* 1970 */     if (this.pdfAConformanceLevel != null) {
/* 1971 */       return this;
/*      */     }
/*      */     try {
/* 1974 */       this.font = PdfFontFactory.createFont("ZapfDingbats");
/* 1975 */     } catch (IOException e) {
/* 1976 */       throw new PdfException(e);
/*      */     } 
/* 1978 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PdfFormField setVisibility(int visibility) {
/* 1989 */     switch (visibility) {
/*      */       case 1:
/* 1991 */         put(PdfName.F, (PdfObject)new PdfNumber(6));
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
/*      */       case 2:
/* 2003 */         return this;
/*      */       case 3:
/*      */         put(PdfName.F, (PdfObject)new PdfNumber(36));
/*      */     } 
/*      */     put(PdfName.F, (PdfObject)new PdfNumber(4));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean regenerateField() {
/* 2014 */     boolean result = true;
/* 2015 */     updateDefaultAppearance();
/* 2016 */     for (PdfWidgetAnnotation widget : getWidgets()) {
/* 2017 */       PdfFormField field = new PdfFormField((PdfDictionary)widget.getPdfObject());
/* 2018 */       copyParamsToKids(field);
/* 2019 */       result &= field.regenerateWidget(getValueAsString());
/*      */     } 
/* 2021 */     return result;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public float getBorderWidth() {
/* 2032 */     PdfDictionary bs = ((PdfWidgetAnnotation)getWidgets().get(0)).getBorderStyle();
/* 2033 */     if (bs != null) {
/* 2034 */       PdfNumber w = bs.getAsNumber(PdfName.W);
/* 2035 */       if (w != null) {
/* 2036 */         this.borderWidth = w.floatValue();
/*      */       }
/*      */     } 
/* 2039 */     return this.borderWidth;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PdfFormField setBorderWidth(float borderWidth) {
/* 2049 */     PdfDictionary bs = ((PdfWidgetAnnotation)getWidgets().get(0)).getBorderStyle();
/* 2050 */     if (bs == null) {
/* 2051 */       bs = new PdfDictionary();
/* 2052 */       put(PdfName.BS, (PdfObject)bs);
/*      */     } 
/* 2054 */     bs.put(PdfName.W, (PdfObject)new PdfNumber(borderWidth));
/* 2055 */     this.borderWidth = borderWidth;
/* 2056 */     regenerateField();
/* 2057 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public PdfFormField setBorderStyle(PdfDictionary style) {
/* 2062 */     ((PdfWidgetAnnotation)getWidgets().get(0)).setBorderStyle(style);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2068 */     regenerateField();
/* 2069 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PdfFormField setBorderColor(Color color) {
/* 2079 */     this.borderColor = color;
/*      */     
/* 2081 */     List<PdfWidgetAnnotation> kids = getWidgets();
/* 2082 */     for (PdfWidgetAnnotation kid : kids) {
/* 2083 */       PdfDictionary mk = kid.getAppearanceCharacteristics();
/* 2084 */       if (mk == null) {
/* 2085 */         mk = new PdfDictionary();
/*      */       }
/* 2087 */       if (this.borderColor == null) {
/* 2088 */         mk.remove(PdfName.BC);
/*      */       } else {
/* 2090 */         mk.put(PdfName.BC, (PdfObject)new PdfArray(this.borderColor.getColorValue()));
/*      */       } 
/* 2092 */       kid.setAppearanceCharacteristics(mk);
/*      */     } 
/* 2094 */     regenerateField();
/* 2095 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PdfFormField setColor(Color color) {
/* 2105 */     this.color = color;
/* 2106 */     regenerateField();
/* 2107 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PdfFormField setReadOnly(boolean readOnly) {
/* 2117 */     return setFieldFlag(FF_READ_ONLY, readOnly);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isReadOnly() {
/* 2126 */     return getFieldFlag(FF_READ_ONLY);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PdfFormField setRequired(boolean required) {
/* 2136 */     return setFieldFlag(FF_REQUIRED, required);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isRequired() {
/* 2145 */     return getFieldFlag(FF_REQUIRED);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PdfFormField setNoExport(boolean noExport) {
/* 2155 */     return setFieldFlag(FF_NO_EXPORT, noExport);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isNoExport() {
/* 2164 */     return getFieldFlag(FF_NO_EXPORT);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PdfFormField setPage(int pageNum) {
/* 2174 */     List<PdfWidgetAnnotation> widgets = getWidgets();
/* 2175 */     if (widgets.size() > 0) {
/* 2176 */       PdfAnnotation annot = (PdfAnnotation)widgets.get(0);
/* 2177 */       if (annot != null) {
/* 2178 */         annot.setPage(getDocument().getPage(pageNum));
/*      */       }
/*      */     } 
/* 2181 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String[] getAppearanceStates() {
/* 2190 */     Set<String> names = new LinkedHashSet<>();
/* 2191 */     PdfString stringOpt = ((PdfDictionary)getPdfObject()).getAsString(PdfName.Opt);
/* 2192 */     if (stringOpt != null) {
/* 2193 */       names.add(stringOpt.toUnicodeString());
/*      */     } else {
/* 2195 */       PdfArray arrayOpt = ((PdfDictionary)getPdfObject()).getAsArray(PdfName.Opt);
/* 2196 */       if (arrayOpt != null) {
/* 2197 */         for (PdfObject pdfObject : arrayOpt) {
/* 2198 */           PdfString valStr = null;
/* 2199 */           if (pdfObject.isArray()) {
/* 2200 */             valStr = ((PdfArray)pdfObject).getAsString(1);
/* 2201 */           } else if (pdfObject.isString()) {
/* 2202 */             valStr = (PdfString)pdfObject;
/*      */           } 
/* 2204 */           if (valStr != null) {
/* 2205 */             names.add(valStr.toUnicodeString());
/*      */           }
/*      */         } 
/*      */       }
/*      */     } 
/*      */     
/* 2211 */     PdfDictionary dic = (PdfDictionary)getPdfObject();
/* 2212 */     dic = dic.getAsDictionary(PdfName.AP);
/* 2213 */     if (dic != null) {
/* 2214 */       dic = dic.getAsDictionary(PdfName.N);
/* 2215 */       if (dic != null) {
/* 2216 */         for (PdfName state : dic.keySet()) {
/* 2217 */           names.add(state.getValue());
/*      */         }
/*      */       }
/*      */     } 
/*      */     
/* 2222 */     PdfArray kids = getKids();
/* 2223 */     if (kids != null) {
/* 2224 */       for (PdfObject kid : kids) {
/* 2225 */         PdfFormField fld = new PdfFormField((PdfDictionary)kid);
/* 2226 */         String[] states = fld.getAppearanceStates();
/* 2227 */         Collections.addAll(names, states);
/*      */       } 
/*      */     }
/* 2230 */     return names.<String>toArray(new String[names.size()]);
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
/*      */   public PdfFormField setAppearance(PdfName appearanceType, String appearanceState, PdfStream appearanceStream) {
/*      */     PdfDictionary dic;
/* 2249 */     PdfWidgetAnnotation widget = getWidgets().get(0);
/*      */     
/* 2251 */     if (widget != null) {
/* 2252 */       dic = (PdfDictionary)widget.getPdfObject();
/*      */     } else {
/* 2254 */       dic = (PdfDictionary)getPdfObject();
/*      */     } 
/* 2256 */     PdfDictionary ap = dic.getAsDictionary(PdfName.AP);
/* 2257 */     if (ap != null) {
/* 2258 */       PdfDictionary appearanceDictionary = ap.getAsDictionary(appearanceType);
/* 2259 */       if (appearanceDictionary == null) {
/* 2260 */         ap.put(appearanceType, (PdfObject)appearanceStream);
/*      */       } else {
/* 2262 */         appearanceDictionary.put(new PdfName(appearanceState), (PdfObject)appearanceStream);
/*      */       } 
/*      */     } 
/*      */     
/* 2266 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PdfFormField setFontSizeAutoScale() {
/* 2275 */     this.fontSize = 0.0F;
/* 2276 */     regenerateField();
/* 2277 */     return this;
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
/*      */   public PdfFormField put(PdfName key, PdfObject value) {
/* 2290 */     ((PdfDictionary)getPdfObject()).put(key, value);
/* 2291 */     setModified();
/* 2292 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PdfFormField remove(PdfName key) {
/* 2302 */     ((PdfDictionary)getPdfObject()).remove(key);
/* 2303 */     setModified();
/* 2304 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void release() {
/* 2312 */     unsetForbidRelease();
/* 2313 */     ((PdfDictionary)getPdfObject()).release();
/*      */   }
/*      */ 
/*      */   
/*      */   protected boolean isWrappedObjectMustBeIndirect() {
/* 2318 */     return true;
/*      */   }
/*      */   
/*      */   protected PdfDocument getDocument() {
/* 2322 */     return ((PdfDictionary)getPdfObject()).getIndirectReference().getDocument();
/*      */   }
/*      */   
/*      */   protected Rectangle getRect(PdfDictionary field) {
/* 2326 */     PdfArray rect = field.getAsArray(PdfName.Rect);
/* 2327 */     if (rect == null) {
/* 2328 */       PdfArray kids = field.getAsArray(PdfName.Kids);
/* 2329 */       if (kids == null) {
/* 2330 */         throw new PdfException("Wrong form field. Add annotation to the field.");
/*      */       }
/* 2332 */       rect = ((PdfDictionary)kids.get(0)).getAsArray(PdfName.Rect);
/*      */     } 
/*      */     
/* 2335 */     return (rect != null) ? rect.toRectangle() : null;
/*      */   }
/*      */   
/*      */   protected static PdfArray processOptions(String[][] options) {
/* 2339 */     PdfArray array = new PdfArray();
/* 2340 */     for (String[] option : options) {
/* 2341 */       PdfArray subArray = new PdfArray((PdfObject)new PdfString(option[0], "UnicodeBig"));
/* 2342 */       subArray.add((PdfObject)new PdfString(option[1], "UnicodeBig"));
/* 2343 */       array.add((PdfObject)subArray);
/*      */     } 
/* 2345 */     return array;
/*      */   }
/*      */   
/*      */   protected static PdfArray processOptions(String[] options) {
/* 2349 */     PdfArray array = new PdfArray();
/* 2350 */     for (String option : options) {
/* 2351 */       array.add((PdfObject)new PdfString(option, "UnicodeBig"));
/*      */     }
/* 2353 */     return array;
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
/*      */   @Deprecated
/*      */   protected String generateDefaultAppearanceString(PdfFont font, float fontSize, Color color, PdfResources res) {
/* 2369 */     PdfStream stream = new PdfStream();
/* 2370 */     PdfCanvas canvas = new PdfCanvas(stream, res, getDocument());
/* 2371 */     canvas.setFontAndSize(font, fontSize);
/* 2372 */     if (color != null)
/* 2373 */       canvas.setColor(color, true); 
/* 2374 */     return new String(stream.getBytes(), StandardCharsets.UTF_8);
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
/*      */   @Deprecated
/*      */   protected Object[] getFontAndSize(PdfDictionary asNormal) throws IOException {
/* 2388 */     return new Object[] { getFont(), Float.valueOf(getFontSize()) };
/*      */   }
/*      */   
/*      */   protected static Object[] splitDAelements(String da) {
/* 2392 */     PdfTokenizer tk = new PdfTokenizer(new RandomAccessFileOrArray((new RandomAccessSourceFactory()).createSource(PdfEncodings.convertToBytes(da, null))));
/* 2393 */     List<String> stack = new ArrayList<>();
/* 2394 */     Object[] ret = new Object[3];
/*      */     try {
/* 2396 */       while (tk.nextToken()) {
/* 2397 */         if (tk.getTokenType() == PdfTokenizer.TokenType.Comment)
/*      */           continue; 
/* 2399 */         if (tk.getTokenType() == PdfTokenizer.TokenType.Other) {
/* 2400 */           switch (tk.getStringValue()) {
/*      */             case "Tf":
/* 2402 */               if (stack.size() >= 2) {
/* 2403 */                 ret[0] = stack.get(stack.size() - 2);
/* 2404 */                 ret[1] = new Float(stack.get(stack.size() - 1));
/*      */               } 
/*      */               continue;
/*      */             case "g":
/* 2408 */               if (stack.size() >= 1) {
/* 2409 */                 float gray = (new Float(stack.get(stack.size() - 1))).floatValue();
/* 2410 */                 if (gray != 0.0F) {
/* 2411 */                   ret[2] = new DeviceGray(gray);
/*      */                 }
/*      */               } 
/*      */               continue;
/*      */             case "rg":
/* 2416 */               if (stack.size() >= 3) {
/* 2417 */                 float red = (new Float(stack.get(stack.size() - 3))).floatValue();
/* 2418 */                 float green = (new Float(stack.get(stack.size() - 2))).floatValue();
/* 2419 */                 float blue = (new Float(stack.get(stack.size() - 1))).floatValue();
/* 2420 */                 ret[2] = new DeviceRgb(red, green, blue);
/*      */               } 
/*      */               continue;
/*      */             case "k":
/* 2424 */               if (stack.size() >= 4) {
/* 2425 */                 float cyan = (new Float(stack.get(stack.size() - 4))).floatValue();
/* 2426 */                 float magenta = (new Float(stack.get(stack.size() - 3))).floatValue();
/* 2427 */                 float yellow = (new Float(stack.get(stack.size() - 2))).floatValue();
/* 2428 */                 float black = (new Float(stack.get(stack.size() - 1))).floatValue();
/* 2429 */                 ret[2] = new DeviceCmyk(cyan, magenta, yellow, black);
/*      */               } 
/*      */               continue;
/*      */           } 
/* 2433 */           stack.clear();
/*      */           
/*      */           continue;
/*      */         } 
/* 2437 */         stack.add(tk.getStringValue());
/*      */       }
/*      */     
/* 2440 */     } catch (Exception exception) {}
/*      */ 
/*      */     
/* 2443 */     return ret;
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
/*      */   protected void drawTextAppearance(Rectangle rect, PdfFont font, float fontSize, String value, PdfFormXObject appearance) {
/* 2456 */     PdfStream stream = (PdfStream)(new PdfStream()).makeIndirect(getDocument());
/* 2457 */     PdfResources resources = appearance.getResources();
/* 2458 */     PdfCanvas canvas = new PdfCanvas(stream, resources, getDocument());
/*      */     
/* 2460 */     float height = rect.getHeight();
/* 2461 */     float width = rect.getWidth();
/* 2462 */     PdfFormXObject xObject = new PdfFormXObject(new Rectangle(0.0F, 0.0F, width, height));
/* 2463 */     drawBorder(canvas, xObject, width, height);
/* 2464 */     if (isPassword()) {
/* 2465 */       value = obfuscatePassword(value);
/*      */     }
/*      */     
/* 2468 */     canvas
/* 2469 */       .beginVariableText()
/* 2470 */       .saveState()
/* 2471 */       .endPath();
/*      */     
/* 2473 */     TextAlignment textAlignment = convertJustificationToTextAlignment();
/* 2474 */     float x = 0.0F;
/* 2475 */     if (textAlignment == TextAlignment.RIGHT) {
/* 2476 */       x = rect.getWidth();
/* 2477 */     } else if (textAlignment == TextAlignment.CENTER) {
/* 2478 */       x = rect.getWidth() / 2.0F;
/*      */     } 
/*      */     
/* 2481 */     Canvas modelCanvas = new Canvas(canvas, new Rectangle(0.0F, -height, 0.0F, 2.0F * height));
/* 2482 */     modelCanvas.setProperty(82, Boolean.valueOf(true));
/*      */     
/* 2484 */     Style paragraphStyle = (Style)((Style)(new Style()).setFont(font)).setFontSize(fontSize);
/* 2485 */     paragraphStyle.setProperty(33, new Leading(2, 1.0F));
/* 2486 */     if (this.color != null) {
/* 2487 */       paragraphStyle.setProperty(21, new TransparentColor(this.color));
/*      */     }
/* 2489 */     int maxLen = (new PdfTextFormField((PdfDictionary)getPdfObject())).getMaxLen();
/*      */     
/* 2491 */     if (getFieldFlag(PdfTextFormField.FF_COMB) && 0 != maxLen) {
/* 2492 */       int start; float widthPerCharacter = width / maxLen;
/* 2493 */       int numberOfCharacters = Math.min(maxLen, value.length());
/*      */ 
/*      */       
/* 2496 */       switch (textAlignment) {
/*      */         case RIGHT:
/* 2498 */           start = maxLen - numberOfCharacters;
/*      */           break;
/*      */         case CENTER:
/* 2501 */           start = (maxLen - numberOfCharacters) / 2;
/*      */           break;
/*      */         default:
/* 2504 */           start = 0; break;
/*      */       } 
/* 2506 */       float startOffset = widthPerCharacter * (start + 0.5F);
/* 2507 */       for (int i = 0; i < numberOfCharacters; i++) {
/* 2508 */         modelCanvas.showTextAligned((Paragraph)(new Paragraph(value.substring(i, i + 1))).addStyle(paragraphStyle), startOffset + widthPerCharacter * i, rect
/* 2509 */             .getHeight() / 2.0F, TextAlignment.CENTER, VerticalAlignment.MIDDLE);
/*      */       }
/*      */     } else {
/* 2512 */       if (getFieldFlag(PdfTextFormField.FF_COMB)) {
/* 2513 */         Logger logger = LoggerFactory.getLogger(PdfFormField.class);
/* 2514 */         logger.error(MessageFormatUtil.format("The Comb flag may be set only if the MaxLen entry is present in the text field dictionary and if the Multiline, Password, and FileSelect flags are clear.", new Object[0]));
/*      */       } 
/* 2516 */       modelCanvas.showTextAligned((Paragraph)((Paragraph)createParagraphForTextFieldValue(value).addStyle(paragraphStyle)).setPaddings(0.0F, 2.0F, 0.0F, 2.0F), x, rect
/* 2517 */           .getHeight() / 2.0F, textAlignment, VerticalAlignment.MIDDLE);
/*      */     } 
/* 2519 */     canvas
/* 2520 */       .restoreState()
/* 2521 */       .endVariableText();
/*      */     
/* 2523 */     ((PdfStream)appearance.getPdfObject()).setData(stream.getBytes());
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
/*      */   @Deprecated
/*      */   protected void drawMultiLineTextAppearance(Rectangle rect, PdfFont font, float fontSize, String value, PdfFormXObject appearance) {
/* 2538 */     drawMultiLineTextAppearance(rect, font, value, appearance);
/*      */   }
/*      */   
/*      */   protected void drawMultiLineTextAppearance(Rectangle rect, PdfFont font, String value, PdfFormXObject appearance) {
/* 2542 */     PdfStream stream = (PdfStream)(new PdfStream()).makeIndirect(getDocument());
/* 2543 */     PdfResources resources = appearance.getResources();
/* 2544 */     PdfCanvas canvas = new PdfCanvas(stream, resources, getDocument());
/*      */     
/* 2546 */     float width = rect.getWidth();
/* 2547 */     float height = rect.getHeight();
/*      */     
/* 2549 */     drawBorder(canvas, appearance, width, height);
/* 2550 */     canvas.beginVariableText();
/*      */     
/* 2552 */     Rectangle areaRect = new Rectangle(0.0F, 0.0F, width, height);
/* 2553 */     Canvas modelCanvas = new Canvas(canvas, areaRect);
/* 2554 */     modelCanvas.setProperty(82, Boolean.valueOf(true));
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2559 */     Paragraph paragraph = ((Paragraph)((Paragraph)((Paragraph)createParagraphForTextFieldValue(value).setFont(font)).setMargin(0.0F)).setPadding(3.0F)).setMultipliedLeading(1.0F);
/* 2560 */     if (this.fontSize == 0.0F) {
/* 2561 */       paragraph.setFontSize(approximateFontSizeToFitMultiLine(paragraph, areaRect, (IRenderer)modelCanvas.getRenderer()));
/*      */     } else {
/* 2563 */       paragraph.setFontSize(this.fontSize);
/*      */     } 
/* 2565 */     paragraph.setProperty(26, Boolean.valueOf(true));
/* 2566 */     paragraph.setTextAlignment(convertJustificationToTextAlignment());
/*      */     
/* 2568 */     if (this.color != null) {
/* 2569 */       paragraph.setFontColor(this.color);
/*      */     }
/*      */     
/* 2572 */     paragraph.setHeight(height - 1.0E-5F);
/* 2573 */     paragraph.setProperty(105, BoxSizingPropertyValue.BORDER_BOX);
/* 2574 */     paragraph.setProperty(103, OverflowPropertyValue.FIT);
/* 2575 */     paragraph.setProperty(104, OverflowPropertyValue.HIDDEN);
/* 2576 */     modelCanvas.add((IBlockElement)paragraph);
/* 2577 */     canvas.endVariableText();
/*      */     
/* 2579 */     ((PdfStream)appearance.getPdfObject()).setData(stream.getBytes());
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
/*      */   private void drawChoiceAppearance(Rectangle rect, float fontSize, String value, PdfFormXObject appearance, int topIndex) {
/* 2591 */     PdfStream stream = (PdfStream)(new PdfStream()).makeIndirect(getDocument());
/* 2592 */     PdfResources resources = appearance.getResources();
/* 2593 */     PdfCanvas canvas = new PdfCanvas(stream, resources, getDocument());
/*      */     
/* 2595 */     float width = rect.getWidth();
/* 2596 */     float height = rect.getHeight();
/* 2597 */     float widthBorder = 6.0F;
/* 2598 */     float heightBorder = 2.0F;
/*      */     
/* 2600 */     List<String> strings = this.font.splitString(value, fontSize, width - widthBorder);
/*      */     
/* 2602 */     drawBorder(canvas, appearance, width, height);
/* 2603 */     canvas
/* 2604 */       .beginVariableText()
/* 2605 */       .saveState()
/* 2606 */       .rectangle(3.0D, 3.0D, (width - widthBorder), (height - heightBorder))
/* 2607 */       .clip()
/* 2608 */       .endPath();
/*      */     
/* 2610 */     Canvas modelCanvas = new Canvas(canvas, new Rectangle(3.0F, 0.0F, Math.max(0.0F, width - widthBorder), Math.max(0.0F, height - heightBorder)));
/* 2611 */     modelCanvas.setProperty(82, Boolean.valueOf(true));
/* 2612 */     Div div = new Div();
/* 2613 */     if (getFieldFlag(PdfChoiceFormField.FF_COMBO)) {
/* 2614 */       div.setVerticalAlignment(VerticalAlignment.MIDDLE);
/*      */     }
/* 2616 */     div.setHeight(Math.max(0.0F, height - heightBorder));
/* 2617 */     for (int index = 0; index < strings.size(); index++) {
/* 2618 */       Boolean isFull = modelCanvas.getRenderer().getPropertyAsBoolean(25);
/* 2619 */       if (Boolean.TRUE.equals(isFull)) {
/*      */         break;
/*      */       }
/*      */       
/* 2623 */       Paragraph paragraph = ((Paragraph)((Paragraph)((Paragraph)(new Paragraph(strings.get(index))).setFont(this.font)).setFontSize(fontSize)).setMargins(0.0F, 0.0F, 0.0F, 0.0F)).setMultipliedLeading(1.0F);
/* 2624 */       paragraph.setProperty(26, Boolean.valueOf(true));
/* 2625 */       paragraph.setTextAlignment(convertJustificationToTextAlignment());
/*      */       
/* 2627 */       if (this.color != null) {
/* 2628 */         paragraph.setFontColor(this.color);
/*      */       }
/* 2630 */       if (!getFieldFlag(PdfChoiceFormField.FF_COMBO)) {
/* 2631 */         PdfArray indices = ((PdfDictionary)getPdfObject()).getAsArray(PdfName.I);
/* 2632 */         if (indices == null && getKids() == null && getParent() != null) {
/* 2633 */           indices = getParent().getAsArray(PdfName.I);
/*      */         }
/* 2635 */         if (indices != null && indices.size() > 0) {
/* 2636 */           for (PdfObject ind : indices) {
/* 2637 */             if (!ind.isNumber())
/*      */               continue; 
/* 2639 */             if (((PdfNumber)ind).getValue() == (index + topIndex)) {
/* 2640 */               paragraph.setBackgroundColor((Color)new DeviceRgb(10, 36, 106));
/* 2641 */               paragraph.setFontColor(ColorConstants.LIGHT_GRAY);
/*      */             } 
/*      */           } 
/*      */         }
/*      */       } 
/* 2646 */       div.add((IBlockElement)paragraph);
/*      */     } 
/* 2648 */     modelCanvas.add((IBlockElement)div);
/* 2649 */     canvas
/* 2650 */       .restoreState()
/* 2651 */       .endVariableText();
/*      */     
/* 2653 */     ((PdfStream)appearance.getPdfObject()).setData(stream.getBytes());
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
/*      */   protected void drawBorder(PdfCanvas canvas, PdfFormXObject xObject, float width, float height) {
/* 2665 */     canvas.saveState();
/* 2666 */     float borderWidth = getBorderWidth();
/* 2667 */     PdfDictionary bs = ((PdfWidgetAnnotation)getWidgets().get(0)).getBorderStyle();
/* 2668 */     if (borderWidth < 0.0F) {
/* 2669 */       borderWidth = 0.0F;
/*      */     }
/*      */     
/* 2672 */     if (this.backgroundColor != null) {
/* 2673 */       canvas
/* 2674 */         .setFillColor(this.backgroundColor)
/* 2675 */         .rectangle(0.0D, 0.0D, width, height)
/* 2676 */         .fill();
/*      */     }
/*      */     
/* 2679 */     if (borderWidth > 0.0F && this.borderColor != null) {
/* 2680 */       borderWidth = Math.max(1.0F, borderWidth);
/* 2681 */       canvas
/* 2682 */         .setStrokeColor(this.borderColor)
/* 2683 */         .setLineWidth(borderWidth);
/* 2684 */       if (bs != null) {
/* 2685 */         PdfName borderType = bs.getAsName(PdfName.S);
/* 2686 */         if (borderType != null && borderType.equals(PdfName.D)) {
/* 2687 */           PdfArray dashArray = bs.getAsArray(PdfName.D);
/* 2688 */           int unitsOn = (dashArray != null) ? ((dashArray.size() > 0) ? ((dashArray.getAsNumber(0) != null) ? dashArray.getAsNumber(0).intValue() : 3) : 3) : 3;
/* 2689 */           int unitsOff = (dashArray != null) ? ((dashArray.size() > 1) ? ((dashArray.getAsNumber(1) != null) ? dashArray.getAsNumber(1).intValue() : unitsOn) : unitsOn) : unitsOn;
/* 2690 */           canvas.setLineDash(unitsOn, unitsOff, 0.0F);
/*      */         } 
/*      */       } 
/* 2693 */       canvas
/* 2694 */         .rectangle(0.0D, 0.0D, width, height)
/* 2695 */         .stroke();
/*      */     } 
/*      */     
/* 2698 */     applyRotation(xObject, height, width);
/* 2699 */     canvas.restoreState();
/*      */   }
/*      */   
/*      */   protected void drawRadioBorder(PdfCanvas canvas, PdfFormXObject xObject, float width, float height) {
/* 2703 */     canvas.saveState();
/* 2704 */     float borderWidth = getBorderWidth();
/* 2705 */     float cx = width / 2.0F;
/* 2706 */     float cy = height / 2.0F;
/* 2707 */     if (borderWidth < 0.0F) {
/* 2708 */       borderWidth = 0.0F;
/*      */     }
/*      */     
/* 2711 */     float r = (Math.min(width, height) - borderWidth) / 2.0F;
/*      */     
/* 2713 */     if (this.backgroundColor != null) {
/* 2714 */       canvas
/* 2715 */         .setFillColor(this.backgroundColor)
/* 2716 */         .circle(cx, cy, (r + borderWidth / 2.0F))
/* 2717 */         .fill();
/*      */     }
/*      */     
/* 2720 */     if (borderWidth > 0.0F && this.borderColor != null) {
/* 2721 */       borderWidth = Math.max(1.0F, borderWidth);
/* 2722 */       canvas
/* 2723 */         .setStrokeColor(this.borderColor)
/* 2724 */         .setLineWidth(borderWidth)
/* 2725 */         .circle(cx, cy, r)
/* 2726 */         .stroke();
/*      */     } 
/*      */     
/* 2729 */     applyRotation(xObject, height, width);
/* 2730 */     canvas.restoreState();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void drawRadioAppearance(float width, float height, String value) {
/* 2741 */     Rectangle rect = new Rectangle(0.0F, 0.0F, width, height);
/* 2742 */     PdfWidgetAnnotation widget = getWidgets().get(0);
/* 2743 */     widget.setNormalAppearance(new PdfDictionary());
/*      */ 
/*      */     
/* 2746 */     PdfFormXObject xObjectOn = new PdfFormXObject(rect);
/* 2747 */     if (value != null) {
/* 2748 */       PdfStream streamOn = (PdfStream)(new PdfStream()).makeIndirect(getDocument());
/* 2749 */       PdfCanvas canvasOn = new PdfCanvas(streamOn, new PdfResources(), getDocument());
/*      */       
/* 2751 */       drawRadioBorder(canvasOn, xObjectOn, width, height);
/* 2752 */       drawRadioField(canvasOn, width, height, true);
/*      */       
/* 2754 */       ((PdfStream)xObjectOn.getPdfObject()).getOutputStream().writeBytes(streamOn.getBytes());
/* 2755 */       widget.getNormalAppearanceObject().put(new PdfName(value), xObjectOn.getPdfObject());
/*      */     } 
/*      */ 
/*      */     
/* 2759 */     PdfStream streamOff = (PdfStream)(new PdfStream()).makeIndirect(getDocument());
/* 2760 */     PdfCanvas canvasOff = new PdfCanvas(streamOff, new PdfResources(), getDocument());
/* 2761 */     PdfFormXObject xObjectOff = new PdfFormXObject(rect);
/*      */     
/* 2763 */     drawRadioBorder(canvasOff, xObjectOff, width, height);
/*      */     
/* 2765 */     ((PdfStream)xObjectOff.getPdfObject()).getOutputStream().writeBytes(streamOff.getBytes());
/* 2766 */     widget.getNormalAppearanceObject().put(new PdfName("Off"), xObjectOff.getPdfObject());
/*      */     
/* 2768 */     if (this.pdfAConformanceLevel != null && ("2"
/* 2769 */       .equals(this.pdfAConformanceLevel.getPart()) || "3".equals(this.pdfAConformanceLevel.getPart()))) {
/* 2770 */       xObjectOn.getResources();
/* 2771 */       xObjectOff.getResources();
/*      */     } 
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
/*      */   @Deprecated
/*      */   protected void drawPdfA1RadioAppearance(float width, float height, String value) {
/* 2785 */     PdfStream stream = (PdfStream)(new PdfStream()).makeIndirect(getDocument());
/* 2786 */     PdfCanvas canvas = new PdfCanvas(stream, new PdfResources(), getDocument());
/* 2787 */     Rectangle rect = new Rectangle(0.0F, 0.0F, width, height);
/* 2788 */     PdfFormXObject xObject = new PdfFormXObject(rect);
/*      */     
/* 2790 */     drawBorder(canvas, xObject, width, height);
/* 2791 */     drawRadioField(canvas, rect.getWidth(), rect.getHeight(), !"Off".equals(value));
/*      */     
/* 2793 */     PdfDictionary normalAppearance = new PdfDictionary();
/* 2794 */     normalAppearance.put(new PdfName(value), xObject.getPdfObject());
/*      */     
/* 2796 */     PdfWidgetAnnotation widget = getWidgets().get(0);
/*      */     
/* 2798 */     ((PdfStream)xObject.getPdfObject()).getOutputStream().writeBytes(stream.getBytes());
/* 2799 */     widget.setNormalAppearance(normalAppearance);
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
/*      */   protected void drawRadioField(PdfCanvas canvas, float width, float height, boolean on) {
/* 2811 */     canvas.saveState();
/* 2812 */     if (on) {
/* 2813 */       canvas.resetFillColorRgb();
/* 2814 */       DrawingUtil.drawCircle(canvas, width / 2.0F, height / 2.0F, Math.min(width, height) / 4.0F);
/*      */     } 
/* 2816 */     canvas.restoreState();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void drawCheckAppearance(float width, float height, String onStateName) {
/* 2827 */     Rectangle rect = new Rectangle(0.0F, 0.0F, width, height);
/*      */     
/* 2829 */     PdfStream streamOn = (PdfStream)(new PdfStream()).makeIndirect(getDocument());
/* 2830 */     PdfCanvas canvasOn = new PdfCanvas(streamOn, new PdfResources(), getDocument());
/* 2831 */     PdfFormXObject xObjectOn = new PdfFormXObject(rect);
/* 2832 */     drawBorder(canvasOn, xObjectOn, width, height);
/* 2833 */     drawCheckBox(canvasOn, width, height, this.fontSize, true);
/* 2834 */     ((PdfStream)xObjectOn.getPdfObject()).getOutputStream().writeBytes(streamOn.getBytes());
/* 2835 */     xObjectOn.getResources().addFont(getDocument(), getFont());
/*      */ 
/*      */     
/* 2838 */     PdfStream streamOff = (PdfStream)(new PdfStream()).makeIndirect(getDocument());
/* 2839 */     PdfCanvas canvasOff = new PdfCanvas(streamOff, new PdfResources(), getDocument());
/* 2840 */     PdfFormXObject xObjectOff = new PdfFormXObject(rect);
/* 2841 */     drawBorder(canvasOff, xObjectOff, width, height);
/* 2842 */     drawCheckBox(canvasOff, width, height, this.fontSize, false);
/* 2843 */     ((PdfStream)xObjectOff.getPdfObject()).getOutputStream().writeBytes(streamOff.getBytes());
/* 2844 */     xObjectOff.getResources().addFont(getDocument(), getFont());
/*      */     
/* 2846 */     PdfDictionary normalAppearance = new PdfDictionary();
/* 2847 */     normalAppearance.put(new PdfName(onStateName), xObjectOn.getPdfObject());
/* 2848 */     normalAppearance.put(new PdfName("Off"), xObjectOff.getPdfObject());
/*      */     
/* 2850 */     PdfDictionary mk = new PdfDictionary();
/* 2851 */     mk.put(PdfName.CA, (PdfObject)new PdfString(this.text));
/*      */     
/* 2853 */     PdfWidgetAnnotation widget = getWidgets().get(0);
/* 2854 */     widget.put(PdfName.MK, (PdfObject)mk);
/* 2855 */     widget.setNormalAppearance(normalAppearance);
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
/*      */   protected void drawPdfA2CheckAppearance(float width, float height, String onStateName, int checkType) {
/* 2870 */     this.checkType = checkType;
/* 2871 */     Rectangle rect = new Rectangle(0.0F, 0.0F, width, height);
/*      */     
/* 2873 */     PdfStream streamOn = (PdfStream)(new PdfStream()).makeIndirect(getDocument());
/* 2874 */     PdfCanvas canvasOn = new PdfCanvas(streamOn, new PdfResources(), getDocument());
/* 2875 */     PdfFormXObject xObjectOn = new PdfFormXObject(rect);
/* 2876 */     xObjectOn.getResources();
/*      */     
/* 2878 */     drawBorder(canvasOn, xObjectOn, width, height);
/* 2879 */     drawPdfACheckBox(canvasOn, width, height, true);
/* 2880 */     ((PdfStream)xObjectOn.getPdfObject()).getOutputStream().writeBytes(streamOn.getBytes());
/*      */     
/* 2882 */     PdfStream streamOff = (PdfStream)(new PdfStream()).makeIndirect(getDocument());
/* 2883 */     PdfCanvas canvasOff = new PdfCanvas(streamOff, new PdfResources(), getDocument());
/* 2884 */     PdfFormXObject xObjectOff = new PdfFormXObject(rect);
/* 2885 */     xObjectOff.getResources();
/*      */     
/* 2887 */     drawBorder(canvasOff, xObjectOff, width, height);
/* 2888 */     ((PdfStream)xObjectOff.getPdfObject()).getOutputStream().writeBytes(streamOff.getBytes());
/*      */     
/* 2890 */     PdfDictionary normalAppearance = new PdfDictionary();
/* 2891 */     normalAppearance.put(new PdfName(onStateName), xObjectOn.getPdfObject());
/* 2892 */     normalAppearance.put(new PdfName("Off"), xObjectOff.getPdfObject());
/*      */     
/* 2894 */     PdfDictionary mk = new PdfDictionary();
/* 2895 */     mk.put(PdfName.CA, (PdfObject)new PdfString(this.text));
/*      */     
/* 2897 */     PdfWidgetAnnotation widget = getWidgets().get(0);
/* 2898 */     widget.put(PdfName.MK, (PdfObject)mk);
/* 2899 */     widget.setNormalAppearance(normalAppearance);
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
/*      */   @Deprecated
/*      */   protected void drawPdfA1CheckAppearance(float width, float height, String selectedValue, int checkType) {
/* 2914 */     PdfStream stream = (PdfStream)(new PdfStream()).makeIndirect(getDocument());
/* 2915 */     PdfCanvas canvas = new PdfCanvas(stream, new PdfResources(), getDocument());
/* 2916 */     Rectangle rect = new Rectangle(0.0F, 0.0F, width, height);
/* 2917 */     PdfFormXObject xObject = new PdfFormXObject(rect);
/*      */     
/* 2919 */     this.checkType = checkType;
/* 2920 */     drawBorder(canvas, xObject, width, height);
/* 2921 */     drawPdfACheckBox(canvas, width, height, !"Off".equals(selectedValue));
/*      */     
/* 2923 */     ((PdfStream)xObject.getPdfObject()).getOutputStream().writeBytes(stream.getBytes());
/*      */     
/* 2925 */     PdfDictionary normalAppearance = new PdfDictionary();
/* 2926 */     normalAppearance.put(new PdfName(selectedValue), xObject.getPdfObject());
/*      */     
/* 2928 */     PdfDictionary mk = new PdfDictionary();
/* 2929 */     mk.put(PdfName.CA, (PdfObject)new PdfString(this.text));
/*      */     
/* 2931 */     PdfWidgetAnnotation widget = getWidgets().get(0);
/* 2932 */     widget.put(PdfName.MK, (PdfObject)mk);
/* 2933 */     widget.setNormalAppearance(normalAppearance);
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
/*      */   protected PdfFormXObject drawPushButtonAppearance(float width, float height, String text, PdfFont font, float fontSize) {
/* 2948 */     PdfStream stream = (PdfStream)(new PdfStream()).makeIndirect(getDocument());
/* 2949 */     PdfCanvas canvas = new PdfCanvas(stream, new PdfResources(), getDocument());
/*      */     
/* 2951 */     PdfFormXObject xObject = new PdfFormXObject(new Rectangle(0.0F, 0.0F, width, height));
/* 2952 */     drawBorder(canvas, xObject, width, height);
/*      */     
/* 2954 */     if (this.img != null) {
/* 2955 */       PdfImageXObject imgXObj = new PdfImageXObject(this.img);
/* 2956 */       canvas.addXObject((PdfXObject)imgXObj, width - this.borderWidth, 0.0F, 0.0F, height - this.borderWidth, this.borderWidth / 2.0F, this.borderWidth / 2.0F);
/* 2957 */       xObject.getResources().addImage(imgXObj);
/* 2958 */     } else if (this.form != null) {
/* 2959 */       canvas.addXObject((PdfXObject)this.form, (height - this.borderWidth) / this.form.getHeight(), 0.0F, 0.0F, (height - this.borderWidth) / this.form.getHeight(), this.borderWidth / 2.0F, this.borderWidth / 2.0F);
/* 2960 */       xObject.getResources().addForm(this.form);
/*      */     } else {
/* 2962 */       drawButton(canvas, 0.0F, 0.0F, width, height, text, font, fontSize);
/* 2963 */       xObject.getResources().addFont(getDocument(), font);
/*      */     } 
/* 2965 */     ((PdfStream)xObject.getPdfObject()).getOutputStream().writeBytes(stream.getBytes());
/*      */     
/* 2967 */     return xObject;
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
/*      */   @Deprecated
/*      */   protected PdfFormXObject drawPushButtonAppearance(float width, float height, String text, PdfFont font, PdfName fontName, float fontSize) {
/* 2985 */     return drawPushButtonAppearance(width, height, text, font, fontSize);
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
/*      */   protected void drawButton(PdfCanvas canvas, float x, float y, float width, float height, String text, PdfFont font, float fontSize) {
/* 3001 */     if (this.color == null) {
/* 3002 */       this.color = ColorConstants.BLACK;
/*      */     }
/* 3004 */     if (text == null) {
/* 3005 */       text = "";
/*      */     }
/*      */ 
/*      */     
/* 3009 */     Paragraph paragraph = (Paragraph)((Paragraph)((Paragraph)((Paragraph)(new Paragraph(text)).setFont(font)).setFontSize(fontSize)).setMargin(0.0F)).setMultipliedLeading(1.0F).setVerticalAlignment(VerticalAlignment.MIDDLE);
/* 3010 */     Canvas modelCanvas = new Canvas(canvas, new Rectangle(0.0F, -height, width, 2.0F * height));
/* 3011 */     modelCanvas.setProperty(82, Boolean.valueOf(true));
/* 3012 */     modelCanvas.showTextAligned(paragraph, width / 2.0F, height / 2.0F, TextAlignment.CENTER, VerticalAlignment.MIDDLE);
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
/*      */   protected void drawCheckBox(PdfCanvas canvas, float width, float height, float fontSize, boolean on) {
/* 3025 */     if (!on) {
/*      */       return;
/*      */     }
/*      */     
/* 3029 */     if (this.checkType == 3) {
/* 3030 */       DrawingUtil.drawCross(canvas, width, height, this.borderWidth);
/*      */       return;
/*      */     } 
/* 3033 */     PdfFont ufont = getFont();
/* 3034 */     if (fontSize <= 0.0F)
/*      */     {
/* 3036 */       fontSize = approximateFontSizeToFitSingleLine(ufont, new Rectangle(width, height), this.text, 0.1F);
/*      */     }
/*      */     
/* 3039 */     canvas
/* 3040 */       .beginText()
/* 3041 */       .setFontAndSize(ufont, fontSize)
/* 3042 */       .resetFillColorRgb()
/* 3043 */       .setTextMatrix((width - ufont.getWidth(this.text, fontSize)) / 2.0F, (height - ufont.getAscent(this.text, fontSize)) / 2.0F)
/* 3044 */       .showText(this.text)
/* 3045 */       .endText();
/*      */   }
/*      */   
/*      */   protected void drawPdfACheckBox(PdfCanvas canvas, float width, float height, boolean on) {
/* 3049 */     if (!on) {
/*      */       return;
/*      */     }
/* 3052 */     switch (this.checkType) {
/*      */       case 1:
/* 3054 */         DrawingUtil.drawPdfACheck(canvas, width, height);
/*      */         break;
/*      */       case 2:
/* 3057 */         DrawingUtil.drawPdfACircle(canvas, width, height);
/*      */         break;
/*      */       case 3:
/* 3060 */         DrawingUtil.drawPdfACross(canvas, width, height);
/*      */         break;
/*      */       case 4:
/* 3063 */         DrawingUtil.drawPdfADiamond(canvas, width, height);
/*      */         break;
/*      */       case 5:
/* 3066 */         DrawingUtil.drawPdfASquare(canvas, width, height);
/*      */         break;
/*      */       case 6:
/* 3069 */         DrawingUtil.drawPdfAStar(canvas, width, height);
/*      */         break;
/*      */     } 
/*      */   }
/*      */   
/*      */   private String getRadioButtonValue() {
/* 3075 */     for (String state : getAppearanceStates()) {
/* 3076 */       if (!"Off".equals(state)) {
/* 3077 */         return state;
/*      */       }
/*      */     } 
/* 3080 */     return null;
/*      */   }
/*      */   
/*      */   private float getFontSize(PdfArray bBox, String value) {
/* 3084 */     assert !isMultiline();
/* 3085 */     if (this.fontSize == 0.0F) {
/* 3086 */       if (bBox == null || value == null || value.isEmpty()) {
/* 3087 */         return 12.0F;
/*      */       }
/* 3089 */       return approximateFontSizeToFitSingleLine(this.font, bBox.toRectangle(), value, 4.0F);
/*      */     } 
/*      */     
/* 3092 */     return this.fontSize;
/*      */   }
/*      */   
/*      */   private float approximateFontSizeToFitMultiLine(Paragraph paragraph, Rectangle rect, IRenderer parentRenderer) {
/* 3096 */     IRenderer renderer = paragraph.createRendererSubTree().setParent(parentRenderer);
/* 3097 */     LayoutContext layoutContext = new LayoutContext(new LayoutArea(1, rect));
/* 3098 */     float lFontSize = 4.0F, rFontSize = 12.0F;
/*      */     
/* 3100 */     paragraph.setFontSize(12.0F);
/* 3101 */     if (renderer.layout(layoutContext).getStatus() != 1) {
/* 3102 */       int numberOfIterations = 6;
/* 3103 */       for (int i = 0; i < 6; i++) {
/* 3104 */         float mFontSize = (lFontSize + rFontSize) / 2.0F;
/* 3105 */         paragraph.setFontSize(mFontSize);
/* 3106 */         LayoutResult result = renderer.layout(layoutContext);
/* 3107 */         if (result.getStatus() == 1) {
/* 3108 */           lFontSize = mFontSize;
/*      */         } else {
/* 3110 */           rFontSize = mFontSize;
/*      */         } 
/*      */       } 
/*      */     } else {
/* 3114 */       lFontSize = 12.0F;
/*      */     } 
/* 3116 */     return lFontSize;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private float approximateFontSizeToFitSingleLine(PdfFont localFont, Rectangle bBox, String value, float minValue) {
/* 3122 */     float height = bBox.getHeight() - this.borderWidth * 2.0F;
/* 3123 */     int[] fontBbox = localFont.getFontProgram().getFontMetrics().getBbox();
/* 3124 */     float fs = height / (fontBbox[2] - fontBbox[1]) * 1000.0F;
/*      */     
/* 3126 */     float baseWidth = localFont.getWidth(value, 1.0F);
/* 3127 */     if (baseWidth != 0.0F) {
/* 3128 */       float availableWidth = Math.max(bBox.getWidth() - this.borderWidth * 2.0F, 0.0F);
/*      */       
/* 3130 */       float absMaxPadding = 4.0F;
/*      */       
/* 3132 */       float relativePaddingForSmallSizes = 0.15F;
/*      */       
/* 3134 */       if (availableWidth * relativePaddingForSmallSizes < absMaxPadding) {
/* 3135 */         availableWidth -= availableWidth * relativePaddingForSmallSizes * 2.0F;
/*      */       } else {
/* 3137 */         availableWidth -= absMaxPadding * 2.0F;
/*      */       } 
/* 3139 */       fs = Math.min(fs, availableWidth / baseWidth);
/*      */     } 
/* 3141 */     return Math.max(fs, minValue);
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
/*      */   private float calculateTranslationHeightAfterFieldRot(Rectangle bBox, double pageRotation, double relFieldRotation) {
/* 3155 */     if (relFieldRotation == 0.0D) {
/* 3156 */       return 0.0F;
/*      */     }
/* 3158 */     if (pageRotation == 0.0D) {
/* 3159 */       if (relFieldRotation == 1.5707963267948966D) {
/* 3160 */         return bBox.getHeight();
/*      */       }
/* 3162 */       if (relFieldRotation == Math.PI) {
/* 3163 */         return bBox.getHeight();
/*      */       }
/*      */     } 
/*      */     
/* 3167 */     if (pageRotation == -1.5707963267948966D) {
/* 3168 */       if (relFieldRotation == -1.5707963267948966D) {
/* 3169 */         return bBox.getWidth() - bBox.getHeight();
/*      */       }
/* 3171 */       if (relFieldRotation == 1.5707963267948966D) {
/* 3172 */         return bBox.getHeight();
/*      */       }
/* 3174 */       if (relFieldRotation == Math.PI) {
/* 3175 */         return bBox.getWidth();
/*      */       }
/*      */     } 
/*      */     
/* 3179 */     if (pageRotation == -3.141592653589793D) {
/* 3180 */       if (relFieldRotation == -3.141592653589793D) {
/* 3181 */         return bBox.getHeight();
/*      */       }
/* 3183 */       if (relFieldRotation == -1.5707963267948966D) {
/* 3184 */         return bBox.getHeight() - bBox.getWidth();
/*      */       }
/*      */       
/* 3187 */       if (relFieldRotation == 1.5707963267948966D) {
/* 3188 */         return bBox.getWidth();
/*      */       }
/*      */     } 
/* 3191 */     if (pageRotation == -4.71238898038469D) {
/* 3192 */       if (relFieldRotation == -4.71238898038469D) {
/* 3193 */         return bBox.getWidth();
/*      */       }
/* 3195 */       if (relFieldRotation == -3.141592653589793D) {
/* 3196 */         return bBox.getWidth();
/*      */       }
/*      */     } 
/*      */     
/* 3200 */     return 0.0F;
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
/*      */   private float calculateTranslationWidthAfterFieldRot(Rectangle bBox, double pageRotation, double relFieldRotation) {
/* 3214 */     if (relFieldRotation == 0.0D) {
/* 3215 */       return 0.0F;
/*      */     }
/* 3217 */     if (pageRotation == 0.0D && (relFieldRotation == Math.PI || relFieldRotation == 4.71238898038469D)) {
/* 3218 */       return bBox.getWidth();
/*      */     }
/* 3220 */     if (pageRotation == -1.5707963267948966D && (
/* 3221 */       relFieldRotation == -1.5707963267948966D || relFieldRotation == Math.PI)) {
/* 3222 */       return bBox.getHeight();
/*      */     }
/*      */ 
/*      */     
/* 3226 */     if (pageRotation == -3.141592653589793D) {
/* 3227 */       if (relFieldRotation == -3.141592653589793D) {
/* 3228 */         return bBox.getWidth();
/*      */       }
/* 3230 */       if (relFieldRotation == -1.5707963267948966D) {
/* 3231 */         return bBox.getHeight();
/*      */       }
/* 3233 */       if (relFieldRotation == 1.5707963267948966D) {
/* 3234 */         return -1.0F * (bBox.getHeight() - bBox.getWidth());
/*      */       }
/*      */     } 
/* 3237 */     if (pageRotation == -4.71238898038469D) {
/* 3238 */       if (relFieldRotation == -4.71238898038469D) {
/* 3239 */         return -1.0F * (bBox.getWidth() - bBox.getHeight());
/*      */       }
/* 3241 */       if (relFieldRotation == -3.141592653589793D) {
/* 3242 */         return bBox.getHeight();
/*      */       }
/* 3244 */       if (relFieldRotation == -1.5707963267948966D) {
/* 3245 */         return bBox.getWidth();
/*      */       }
/*      */     } 
/* 3248 */     return 0.0F;
/*      */   }
/*      */   
/*      */   private boolean hasDefaultAppearance() {
/* 3252 */     PdfName type = getFormType();
/* 3253 */     return (type == PdfName.Tx || type == PdfName.Ch || (type == PdfName.Btn && (
/*      */       
/* 3255 */       getFieldFlags() & PdfButtonFormField.FF_PUSH_BUTTON) != 0));
/*      */   }
/*      */   
/*      */   private PdfName getUniqueFontNameForDR(PdfDictionary fontResources) {
/* 3259 */     int indexer = 1;
/* 3260 */     Set<PdfName> fontNames = fontResources.keySet();
/*      */     
/*      */     while (true) {
/* 3263 */       PdfName uniqueName = new PdfName("F" + indexer++);
/* 3264 */       if (!fontNames.contains(uniqueName))
/* 3265 */         return uniqueName; 
/*      */     } 
/*      */   }
/*      */   private PdfName getFontNameFromDR(PdfDictionary fontResources, PdfObject font) {
/* 3269 */     for (Map.Entry<PdfName, PdfObject> drFont : (Iterable<Map.Entry<PdfName, PdfObject>>)fontResources.entrySet()) {
/* 3270 */       if (drFont.getValue() == font) {
/* 3271 */         return drFont.getKey();
/*      */       }
/*      */     } 
/* 3274 */     return null;
/*      */   }
/*      */   
/*      */   private PdfObject getAcroFormObject(PdfName key, int type) {
/* 3278 */     PdfObject acroFormObject = null;
/* 3279 */     PdfDictionary acroFormDictionary = ((PdfDictionary)getDocument().getCatalog().getPdfObject()).getAsDictionary(PdfName.AcroForm);
/* 3280 */     if (acroFormDictionary != null) {
/* 3281 */       acroFormObject = acroFormDictionary.get(key);
/*      */     }
/* 3283 */     return (acroFormObject != null && acroFormObject.getType() == type) ? acroFormObject : null;
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
/*      */   private void putAcroFormObject(PdfName acroFormKey, PdfObject acroFormObject) {
/* 3298 */     ((PdfDictionary)getDocument().getCatalog().getPdfObject()).getAsDictionary(PdfName.AcroForm).put(acroFormKey, acroFormObject);
/*      */   }
/*      */   
/*      */   private void addAcroFormToCatalog() {
/* 3302 */     if (((PdfDictionary)getDocument().getCatalog().getPdfObject()).getAsDictionary(PdfName.AcroForm) == null) {
/* 3303 */       PdfDictionary acroform = new PdfDictionary();
/* 3304 */       acroform.makeIndirect(getDocument());
/*      */       
/* 3306 */       acroform.put(PdfName.Fields, (PdfObject)new PdfArray());
/* 3307 */       getDocument().getCatalog().put(PdfName.AcroForm, (PdfObject)acroform);
/*      */     } 
/*      */   }
/*      */   
/*      */   private PdfObject getAcroFormKey(PdfName key, int type) {
/* 3312 */     PdfObject acroFormKey = null;
/* 3313 */     PdfDocument document = getDocument();
/* 3314 */     if (document != null) {
/* 3315 */       PdfDictionary acroFormDictionary = ((PdfDictionary)document.getCatalog().getPdfObject()).getAsDictionary(PdfName.AcroForm);
/* 3316 */       if (acroFormDictionary != null) {
/* 3317 */         acroFormKey = acroFormDictionary.get(key);
/*      */       }
/*      */     } 
/* 3320 */     return (acroFormKey != null && acroFormKey.getType() == type) ? acroFormKey : null;
/*      */   }
/*      */   
/*      */   private TextAlignment convertJustificationToTextAlignment() {
/* 3324 */     Integer justification = getJustification();
/* 3325 */     if (justification == null) {
/* 3326 */       justification = Integer.valueOf(0);
/*      */     }
/* 3328 */     TextAlignment textAlignment = TextAlignment.LEFT;
/* 3329 */     if (justification.intValue() == 2) {
/* 3330 */       textAlignment = TextAlignment.RIGHT;
/* 3331 */     } else if (justification.intValue() == 1) {
/* 3332 */       textAlignment = TextAlignment.CENTER;
/*      */     } 
/* 3334 */     return textAlignment;
/*      */   }
/*      */   
/*      */   private PdfName getTypeFromParent(PdfDictionary field) {
/* 3338 */     PdfDictionary parent = field.getAsDictionary(PdfName.Parent);
/* 3339 */     PdfName formType = field.getAsName(PdfName.FT);
/* 3340 */     if (parent != null) {
/* 3341 */       formType = parent.getAsName(PdfName.FT);
/* 3342 */       if (formType == null) {
/* 3343 */         formType = getTypeFromParent(parent);
/*      */       }
/*      */     } 
/* 3346 */     return formType;
/*      */   }
/*      */   
/*      */   private String obfuscatePassword(String text) {
/* 3350 */     char[] pchar = new char[text.length()];
/* 3351 */     for (int i = 0; i < text.length(); i++)
/* 3352 */       pchar[i] = '*'; 
/* 3353 */     return new String(pchar);
/*      */   }
/*      */   
/*      */   private void applyRotation(PdfFormXObject xObject, float height, float width) {
/* 3357 */     switch (this.rotation) {
/*      */       case 90:
/* 3359 */         xObject.put(PdfName.Matrix, (PdfObject)new PdfArray(new float[] { 0.0F, 1.0F, -1.0F, 0.0F, height, 0.0F }));
/*      */         break;
/*      */       case 180:
/* 3362 */         xObject.put(PdfName.Matrix, (PdfObject)new PdfArray(new float[] { -1.0F, 0.0F, 0.0F, -1.0F, width, height }));
/*      */         break;
/*      */       case 270:
/* 3365 */         xObject.put(PdfName.Matrix, (PdfObject)new PdfArray(new float[] { 0.0F, -1.0F, 1.0F, 0.0F, 0.0F, width }));
/*      */         break;
/*      */     } 
/*      */   }
/*      */   
/*      */   private PdfObject getValueFromAppearance(PdfObject appearanceDict, PdfName key) {
/* 3371 */     if (appearanceDict instanceof PdfDictionary) {
/* 3372 */       return ((PdfDictionary)appearanceDict).get(key);
/*      */     }
/* 3374 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void retrieveStyles() {
/* 3381 */     PdfName subType = ((PdfDictionary)getPdfObject()).getAsName(PdfName.Subtype);
/* 3382 */     if (subType != null && subType.equals(PdfName.Widget)) {
/* 3383 */       PdfDictionary appearanceCharacteristics = ((PdfDictionary)getPdfObject()).getAsDictionary(PdfName.MK);
/* 3384 */       if (appearanceCharacteristics != null) {
/* 3385 */         this.backgroundColor = appearancePropToColor(appearanceCharacteristics, PdfName.BG);
/* 3386 */         Color extractedBorderColor = appearancePropToColor(appearanceCharacteristics, PdfName.BC);
/* 3387 */         if (extractedBorderColor != null)
/* 3388 */           this.borderColor = extractedBorderColor; 
/*      */       } 
/*      */     } 
/* 3391 */     PdfString defaultAppearance = getDefaultAppearance();
/* 3392 */     if (defaultAppearance != null) {
/* 3393 */       Object[] fontData = splitDAelements(defaultAppearance.getValue());
/* 3394 */       if (fontData[1] != null && fontData[0] != null) {
/* 3395 */         this.color = (Color)fontData[2];
/* 3396 */         this.fontSize = ((Float)fontData[1]).floatValue();
/* 3397 */         this.font = resolveFontName((String)fontData[0]);
/*      */       } 
/*      */     } 
/*      */     
/* 3401 */     updateFontAndFontSize(this.font, this.fontSize);
/*      */   }
/*      */   
/*      */   private PdfFont resolveFontName(String fontName) {
/* 3405 */     PdfDictionary defaultResources = (PdfDictionary)getAcroFormObject(PdfName.DR, 3);
/* 3406 */     PdfDictionary defaultFontDic = (defaultResources != null) ? defaultResources.getAsDictionary(PdfName.Font) : null;
/* 3407 */     if (fontName != null && defaultFontDic != null) {
/* 3408 */       PdfDictionary daFontDict = defaultFontDic.getAsDictionary(new PdfName(fontName));
/* 3409 */       if (daFontDict != null) {
/* 3410 */         return getDocument().getFont(daFontDict);
/*      */       }
/*      */     } 
/* 3413 */     return null;
/*      */   }
/*      */   
/*      */   private Color appearancePropToColor(PdfDictionary appearanceCharacteristics, PdfName property) {
/* 3417 */     PdfArray colorData = appearanceCharacteristics.getAsArray(property);
/* 3418 */     if (colorData != null) {
/* 3419 */       float[] backgroundFloat = new float[colorData.size()];
/* 3420 */       for (int i = 0; i < colorData.size(); i++)
/* 3421 */         backgroundFloat[i] = colorData.getAsNumber(i).floatValue(); 
/* 3422 */       switch (colorData.size()) {
/*      */         case 0:
/* 3424 */           return null;
/*      */         case 1:
/* 3426 */           return (Color)new DeviceGray(backgroundFloat[0]);
/*      */         case 3:
/* 3428 */           return (Color)new DeviceRgb(backgroundFloat[0], backgroundFloat[1], backgroundFloat[2]);
/*      */         case 4:
/* 3430 */           return (Color)new DeviceCmyk(backgroundFloat[0], backgroundFloat[1], backgroundFloat[2], backgroundFloat[3]);
/*      */       } 
/*      */     } 
/* 3433 */     return null;
/*      */   }
/*      */   
/*      */   private void regeneratePushButtonField() {
/* 3437 */     PdfDictionary widget = (PdfDictionary)getPdfObject();
/*      */     
/* 3439 */     Rectangle rect = getRect(widget);
/* 3440 */     PdfDictionary apDic = widget.getAsDictionary(PdfName.AP);
/*      */     
/* 3442 */     if (apDic == null) {
/* 3443 */       put(PdfName.AP, (PdfObject)(apDic = new PdfDictionary()));
/*      */     }
/* 3445 */     PdfFormXObject appearance = drawPushButtonAppearance(rect.getWidth(), rect.getHeight(), this.text, this.font, 
/* 3446 */         getFontSize(widget.getAsArray(PdfName.Rect), this.text));
/*      */     
/* 3448 */     apDic.put(PdfName.N, appearance.getPdfObject());
/*      */     
/* 3450 */     if (this.pdfAConformanceLevel != null) {
/* 3451 */       createPushButtonAppearanceState(widget);
/*      */     }
/*      */   }
/*      */   
/*      */   private void regenerateRadioButtonField() {
/* 3456 */     Rectangle rect = getRect((PdfDictionary)getPdfObject());
/* 3457 */     String value = getRadioButtonValue();
/* 3458 */     if (rect != null && !"".equals(value)) {
/* 3459 */       drawRadioAppearance(rect.getWidth(), rect.getHeight(), value);
/*      */     }
/*      */   }
/*      */   
/*      */   private void regenerateCheckboxField(String value) {
/* 3464 */     Rectangle rect = getRect((PdfDictionary)getPdfObject());
/* 3465 */     setCheckType(this.checkType);
/*      */     
/* 3467 */     PdfWidgetAnnotation widget = (PdfWidgetAnnotation)PdfAnnotation.makeAnnotation(getPdfObject());
/*      */     
/* 3469 */     if (this.pdfAConformanceLevel != null) {
/* 3470 */       drawPdfA2CheckAppearance(rect.getWidth(), rect.getHeight(), "Off".equals(value) ? "Yes" : value, this.checkType);
/* 3471 */       widget.setFlag(4);
/*      */     } else {
/* 3473 */       drawCheckAppearance(rect.getWidth(), rect.getHeight(), "Off".equals(value) ? "Yes" : value);
/*      */     } 
/*      */     
/* 3476 */     if (widget.getNormalAppearanceObject() != null && widget.getNormalAppearanceObject().containsKey(new PdfName(value))) {
/* 3477 */       widget.setAppearanceState(new PdfName(value));
/*      */     } else {
/* 3479 */       widget.setAppearanceState(new PdfName("Off"));
/*      */     } 
/*      */   }
/*      */   private boolean regenerateTextAndChoiceField(String value, PdfName type) {
/*      */     PdfArray matrix;
/* 3484 */     PdfPage page = PdfWidgetAnnotation.makeAnnotation(getPdfObject()).getPage();
/* 3485 */     PdfArray bBox = ((PdfDictionary)getPdfObject()).getAsArray(PdfName.Rect);
/*      */ 
/*      */     
/* 3488 */     int pageRotation = 0;
/* 3489 */     if (page != null) {
/* 3490 */       pageRotation = page.getRotation();
/*      */       
/* 3492 */       pageRotation *= -1;
/*      */     } 
/*      */     
/* 3495 */     if (pageRotation % 90 == 0) {
/*      */       
/* 3497 */       double angle = (pageRotation % 360);
/*      */       
/* 3499 */       angle = degreeToRadians(angle);
/* 3500 */       Rectangle initialBboxRectangle = bBox.toRectangle();
/*      */       
/* 3502 */       Rectangle rect = initialBboxRectangle.clone();
/*      */       
/* 3504 */       double translationWidth = 0.0D;
/* 3505 */       double translationHeight = 0.0D;
/* 3506 */       if (angle >= -3.141592653589793D && angle <= -1.5707963267948966D) {
/* 3507 */         translationWidth = rect.getWidth();
/*      */       }
/* 3509 */       if (angle <= -3.141592653589793D) {
/* 3510 */         translationHeight = rect.getHeight();
/*      */       }
/*      */ 
/*      */       
/* 3514 */       matrix = new PdfArray(new double[] { Math.cos(angle), -Math.sin(angle), Math.sin(angle), Math.cos(angle), translationWidth, translationHeight });
/*      */       
/* 3516 */       if (angle % 1.5707963267948966D == 0.0D && angle % Math.PI != 0.0D) {
/* 3517 */         rect.setWidth(initialBboxRectangle.getHeight());
/* 3518 */         rect.setHeight(initialBboxRectangle.getWidth());
/*      */       } 
/*      */       
/* 3521 */       rect.setX(rect.getX() + (float)translationWidth);
/* 3522 */       rect.setY(rect.getY() + (float)translationHeight);
/*      */       
/* 3524 */       bBox = new PdfArray(rect);
/*      */     } else {
/*      */       
/* 3527 */       Logger logger = LoggerFactory.getLogger(PdfFormField.class);
/* 3528 */       logger.error("Encounterd a page rotation that was not a multiple of 90°/ (Pi/2) when generating default appearances for form fields");
/* 3529 */       matrix = new PdfArray(new double[] { 1.0D, 0.0D, 0.0D, 1.0D, 0.0D, 0.0D });
/*      */     } 
/*      */     
/* 3532 */     float fieldRotation = 0.0F;
/* 3533 */     if (((PdfDictionary)getPdfObject()).getAsDictionary(PdfName.MK) != null && ((PdfDictionary)
/* 3534 */       getPdfObject()).getAsDictionary(PdfName.MK).get(PdfName.R) != null) {
/* 3535 */       fieldRotation = ((PdfDictionary)getPdfObject()).getAsDictionary(PdfName.MK).getAsFloat(PdfName.R).floatValue();
/*      */       
/* 3537 */       fieldRotation += pageRotation;
/*      */     } 
/* 3539 */     if (fieldRotation % 90.0F == 0.0F) {
/* 3540 */       Rectangle initialBboxRectangle = bBox.toRectangle();
/*      */       
/* 3542 */       double angle = (fieldRotation % 360.0F);
/*      */       
/* 3544 */       angle = degreeToRadians(angle);
/*      */       
/* 3546 */       double translationWidth = calculateTranslationWidthAfterFieldRot(initialBboxRectangle, degreeToRadians(pageRotation), angle);
/* 3547 */       double translationHeight = calculateTranslationHeightAfterFieldRot(initialBboxRectangle, degreeToRadians(pageRotation), angle);
/*      */ 
/*      */       
/* 3550 */       Matrix currentMatrix = new Matrix(matrix.getAsNumber(0).floatValue(), matrix.getAsNumber(1).floatValue(), matrix.getAsNumber(2).floatValue(), matrix.getAsNumber(3).floatValue(), matrix.getAsNumber(4).floatValue(), matrix.getAsNumber(5).floatValue());
/* 3551 */       Matrix toConcatenate = new Matrix((float)Math.cos(angle), (float)-Math.sin(angle), (float)Math.sin(angle), (float)Math.cos(angle), (float)translationWidth, (float)translationHeight);
/* 3552 */       currentMatrix = currentMatrix.multiply(toConcatenate);
/* 3553 */       matrix = new PdfArray(new float[] { currentMatrix.get(0), currentMatrix.get(1), currentMatrix.get(3), currentMatrix.get(4), currentMatrix.get(6), currentMatrix.get(7) });
/*      */ 
/*      */       
/* 3556 */       Rectangle rect = initialBboxRectangle.clone();
/*      */       
/* 3558 */       if (angle % 1.5707963267948966D == 0.0D && angle % Math.PI != 0.0D) {
/* 3559 */         rect.setWidth(initialBboxRectangle.getHeight());
/* 3560 */         rect.setHeight(initialBboxRectangle.getWidth());
/*      */       } 
/* 3562 */       rect.setX(rect.getX() + (float)translationWidth);
/* 3563 */       rect.setY(rect.getY() + (float)translationHeight);
/*      */       
/* 3565 */       bBox = new PdfArray(rect);
/*      */     } 
/*      */     
/* 3568 */     Rectangle bboxRectangle = bBox.toRectangle();
/* 3569 */     PdfFormXObject appearance = new PdfFormXObject(new Rectangle(0.0F, 0.0F, bboxRectangle.getWidth(), bboxRectangle.getHeight()));
/* 3570 */     appearance.put(PdfName.Matrix, (PdfObject)matrix);
/*      */     
/* 3572 */     if (PdfName.Tx.equals(type)) {
/* 3573 */       if (isMultiline()) {
/* 3574 */         drawMultiLineTextAppearance(bboxRectangle, this.font, value, appearance);
/*      */       } else {
/* 3576 */         drawTextAppearance(bboxRectangle, this.font, getFontSize(bBox, value), value, appearance);
/*      */       } 
/*      */     } else {
/* 3579 */       int topIndex = 0;
/* 3580 */       if (!getFieldFlag(PdfChoiceFormField.FF_COMBO)) {
/* 3581 */         PdfNumber topIndexNum = ((PdfDictionary)getPdfObject()).getAsNumber(PdfName.TI);
/* 3582 */         if (topIndexNum == null && getParent() != null) {
/* 3583 */           topIndexNum = getParent().getAsNumber(PdfName.TI);
/*      */         }
/* 3585 */         PdfArray options = getOptions();
/* 3586 */         if (null == options && getParent() != null) {
/* 3587 */           options = getParent().getAsArray(PdfName.Opt);
/*      */         }
/* 3589 */         if (null != options) {
/* 3590 */           topIndex = (null != topIndexNum) ? topIndexNum.intValue() : 0;
/*      */           
/* 3592 */           PdfArray visibleOptions = (topIndex > 0) ? new PdfArray(options.subList(topIndex, options.size())) : (PdfArray)options.clone();
/* 3593 */           value = optionsArrayToString(visibleOptions);
/*      */         } 
/*      */       } 
/* 3596 */       drawChoiceAppearance(bboxRectangle, getFontSize(bBox, value), value, appearance, topIndex);
/*      */     } 
/* 3598 */     PdfDictionary ap = new PdfDictionary();
/* 3599 */     ap.put(PdfName.N, appearance.getPdfObject());
/* 3600 */     ap.setModified();
/* 3601 */     put(PdfName.AP, (PdfObject)ap);
/*      */     
/* 3603 */     return true;
/*      */   }
/*      */   
/*      */   private void copyParamsToKids(PdfFormField child) {
/* 3607 */     if (child.checkType <= 0 || child.checkType > 5) {
/* 3608 */       child.checkType = this.checkType;
/*      */     }
/* 3610 */     if (child.getDefaultAppearance() == null) {
/* 3611 */       child.font = this.font;
/* 3612 */       child.fontSize = this.fontSize;
/*      */     } 
/* 3614 */     if (child.color == null) {
/* 3615 */       child.color = this.color;
/*      */     }
/* 3617 */     if (child.text == null) {
/* 3618 */       child.text = this.text;
/*      */     }
/* 3620 */     if (child.img == null) {
/* 3621 */       child.img = this.img;
/*      */     }
/* 3623 */     if (child.borderWidth == 1.0F) {
/* 3624 */       child.borderWidth = this.borderWidth;
/*      */     }
/* 3626 */     if (child.backgroundColor == null) {
/* 3627 */       child.backgroundColor = this.backgroundColor;
/*      */     }
/* 3629 */     if (child.borderColor == null) {
/* 3630 */       child.borderColor = this.borderColor;
/*      */     }
/* 3632 */     if (child.rotation == 0) {
/* 3633 */       child.rotation = this.rotation;
/*      */     }
/* 3635 */     if (child.pdfAConformanceLevel == null) {
/* 3636 */       child.pdfAConformanceLevel = this.pdfAConformanceLevel;
/*      */     }
/* 3638 */     if (child.form == null) {
/* 3639 */       child.form = this.form;
/*      */     }
/*      */   }
/*      */   
/*      */   private boolean regenerateWidget(String value) {
/* 3644 */     PdfName type = getFormType();
/*      */     
/* 3646 */     if (PdfName.Tx.equals(type) || PdfName.Ch.equals(type))
/* 3647 */       return regenerateTextAndChoiceField(value, type); 
/* 3648 */     if (PdfName.Btn.equals(type)) {
/* 3649 */       if (getFieldFlag(PdfButtonFormField.FF_PUSH_BUTTON)) {
/* 3650 */         regeneratePushButtonField();
/* 3651 */       } else if (getFieldFlag(PdfButtonFormField.FF_RADIO)) {
/* 3652 */         regenerateRadioButtonField();
/*      */       } else {
/* 3654 */         regenerateCheckboxField(value);
/*      */       } 
/* 3656 */       return true;
/*      */     } 
/* 3658 */     return false;
/*      */   }
/*      */   
/*      */   private static String optionsArrayToString(PdfArray options) {
/* 3662 */     StringBuilder sb = new StringBuilder();
/* 3663 */     for (PdfObject obj : options) {
/* 3664 */       if (obj.isString()) {
/* 3665 */         sb.append(((PdfString)obj).toUnicodeString()).append('\n'); continue;
/* 3666 */       }  if (obj.isArray()) {
/* 3667 */         PdfObject element = ((PdfArray)obj).get(1);
/* 3668 */         if (element.isString())
/* 3669 */           sb.append(((PdfString)element).toUnicodeString()).append('\n'); 
/*      */         continue;
/*      */       } 
/* 3672 */       sb.append('\n');
/*      */     } 
/*      */ 
/*      */     
/* 3676 */     sb.deleteCharAt(sb.length() - 1);
/* 3677 */     return sb.toString();
/*      */   }
/*      */   
/*      */   private static double degreeToRadians(double angle) {
/* 3681 */     return Math.PI * angle / 180.0D;
/*      */   }
/*      */   
/*      */   private static PdfString generateDefaultAppearance(PdfName font, float fontSize, Color textColor) {
/* 3685 */     assert font != null;
/*      */     
/* 3687 */     ByteArrayOutputStream output = new ByteArrayOutputStream();
/* 3688 */     PdfOutputStream pdfStream = new PdfOutputStream((OutputStream)new OutputStream(output));
/* 3689 */     byte[] g = { 103 };
/* 3690 */     byte[] rg = { 114, 103 };
/* 3691 */     byte[] k = { 107 };
/* 3692 */     byte[] Tf = { 84, 102 };
/*      */     
/* 3694 */     ((PdfOutputStream)((PdfOutputStream)((PdfOutputStream)pdfStream.write((PdfObject)font)
/* 3695 */       .writeSpace())
/* 3696 */       .writeFloat(fontSize)).writeSpace())
/* 3697 */       .writeBytes(Tf);
/*      */     
/* 3699 */     if (textColor != null) {
/* 3700 */       if (textColor instanceof DeviceGray) {
/* 3701 */         ((PdfOutputStream)((PdfOutputStream)((PdfOutputStream)pdfStream.writeSpace())
/* 3702 */           .writeFloats(textColor.getColorValue()))
/* 3703 */           .writeSpace())
/* 3704 */           .writeBytes(g);
/* 3705 */       } else if (textColor instanceof DeviceRgb) {
/* 3706 */         ((PdfOutputStream)((PdfOutputStream)((PdfOutputStream)pdfStream.writeSpace())
/* 3707 */           .writeFloats(textColor.getColorValue()))
/* 3708 */           .writeSpace())
/* 3709 */           .writeBytes(rg);
/* 3710 */       } else if (textColor instanceof DeviceCmyk) {
/* 3711 */         ((PdfOutputStream)((PdfOutputStream)((PdfOutputStream)pdfStream.writeSpace())
/* 3712 */           .writeFloats(textColor.getColorValue()))
/* 3713 */           .writeSpace())
/* 3714 */           .writeBytes(k);
/*      */       } else {
/* 3716 */         Logger logger = LoggerFactory.getLogger(PdfFormField.class);
/* 3717 */         logger.error("Unsupported color in FormField's DA");
/*      */       } 
/*      */     }
/* 3720 */     return new PdfString(output.toByteArray());
/*      */   }
/*      */   
/*      */   private static boolean isWidgetAnnotation(PdfDictionary pdfObject) {
/* 3724 */     return (pdfObject != null && PdfName.Widget.equals(pdfObject.getAsName(PdfName.Subtype)));
/*      */   }
/*      */   
/*      */   private static void createPushButtonAppearanceState(PdfDictionary widget) {
/* 3728 */     PdfDictionary appearances = widget.getAsDictionary(PdfName.AP);
/* 3729 */     PdfStream normalAppearanceStream = appearances.getAsStream(PdfName.N);
/* 3730 */     if (normalAppearanceStream != null) {
/* 3731 */       PdfName stateName = widget.getAsName(PdfName.AS);
/* 3732 */       if (stateName == null) {
/* 3733 */         stateName = new PdfName("push");
/*      */       }
/* 3735 */       widget.put(PdfName.AS, (PdfObject)stateName);
/* 3736 */       PdfDictionary normalAppearance = new PdfDictionary();
/* 3737 */       normalAppearance.put(stateName, (PdfObject)normalAppearanceStream);
/* 3738 */       appearances.put(PdfName.N, (PdfObject)normalAppearance);
/*      */     } 
/*      */   }
/*      */   
/*      */   private static Paragraph createParagraphForTextFieldValue(String value) {
/* 3743 */     Text text = new Text(value);
/* 3744 */     text.setNextRenderer((IRenderer)new FormFieldValueNonTrimmingTextRenderer(text));
/* 3745 */     return new Paragraph(text);
/*      */   }
/*      */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/forms/fields/PdfFormField.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */