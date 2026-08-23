/*     */ package com.itextpdf.signatures;
/*     */ 
/*     */ import com.itextpdf.forms.PdfAcroForm;
/*     */ import com.itextpdf.forms.fields.PdfFormField;
/*     */ import com.itextpdf.io.image.ImageData;
/*     */ import com.itextpdf.kernel.colors.Color;
/*     */ import com.itextpdf.kernel.font.PdfFont;
/*     */ import com.itextpdf.kernel.font.PdfFontFactory;
/*     */ import com.itextpdf.kernel.geom.Rectangle;
/*     */ import com.itextpdf.kernel.pdf.PdfDocument;
/*     */ import com.itextpdf.kernel.pdf.PdfName;
/*     */ import com.itextpdf.kernel.pdf.PdfStream;
/*     */ import com.itextpdf.kernel.pdf.annot.PdfWidgetAnnotation;
/*     */ import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
/*     */ import com.itextpdf.kernel.pdf.xobject.PdfFormXObject;
/*     */ import com.itextpdf.kernel.pdf.xobject.PdfXObject;
/*     */ import com.itextpdf.layout.Canvas;
/*     */ import com.itextpdf.layout.element.IBlockElement;
/*     */ import com.itextpdf.layout.element.Paragraph;
/*     */ import com.itextpdf.layout.layout.LayoutArea;
/*     */ import com.itextpdf.layout.layout.LayoutContext;
/*     */ import com.itextpdf.layout.layout.LayoutResult;
/*     */ import com.itextpdf.layout.renderer.IRenderer;
/*     */ import java.io.IOException;
/*     */ import java.security.cert.Certificate;
/*     */ import java.security.cert.X509Certificate;
/*     */ import java.util.Calendar;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class PdfSignatureAppearance
/*     */ {
/*     */   private static final float TOP_SECTION = 0.3F;
/*     */   private static final float MARGIN = 2.0F;
/*     */   private PdfDocument document;
/*  93 */   private int page = 1;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Rectangle rect;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Rectangle pageRect;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private PdfFormXObject n0;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private PdfFormXObject n2;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private PdfFormXObject topLayer;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 124 */   private RenderingMode renderingMode = RenderingMode.DESCRIPTION;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 129 */   private String reason = "";
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 134 */   private String reasonCaption = "Reason: ";
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 139 */   private String location = "";
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 144 */   private String locationCaption = "Location: ";
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 149 */   private String signatureCreator = "";
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 154 */   private String contact = "";
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Calendar signDate;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Certificate signCertificate;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 169 */   private ImageData signatureGraphic = null;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private ImageData image;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private float imageScale;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private String layer2Text;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private PdfFont layer2Font;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 194 */   private float layer2FontSize = 0.0F;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Color layer2FontColor;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private String fieldName;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean reuseAppearance = false;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected PdfSignatureAppearance(PdfDocument document, Rectangle pageRect, int pageNumber) {
/* 220 */     this.document = document;
/* 221 */     this.pageRect = new Rectangle(pageRect);
/* 222 */     this.rect = new Rectangle(pageRect.getWidth(), pageRect.getHeight());
/* 223 */     this.page = pageNumber;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getPageNumber() {
/* 234 */     return this.page;
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
/*     */   public PdfSignatureAppearance setPageNumber(int pageNumber) {
/* 246 */     this.page = pageNumber;
/* 247 */     setPageRect(this.pageRect);
/* 248 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Rectangle getPageRect() {
/* 259 */     return this.pageRect;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PdfSignatureAppearance setPageRect(Rectangle pageRect) {
/* 270 */     this.pageRect = new Rectangle(pageRect);
/* 271 */     this.rect = new Rectangle(pageRect.getWidth(), pageRect.getHeight());
/* 272 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PdfFormXObject getLayer0() {
/* 281 */     if (this.n0 == null) {
/* 282 */       this.n0 = new PdfFormXObject(this.rect);
/* 283 */       this.n0.makeIndirect(this.document);
/*     */     } 
/*     */     
/* 286 */     return this.n0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PdfFormXObject getLayer2() {
/* 295 */     if (this.n2 == null) {
/* 296 */       this.n2 = new PdfFormXObject(this.rect);
/* 297 */       this.n2.makeIndirect(this.document);
/*     */     } 
/*     */     
/* 300 */     return this.n2;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public RenderingMode getRenderingMode() {
/* 309 */     return this.renderingMode;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PdfSignatureAppearance setRenderingMode(RenderingMode renderingMode) {
/* 318 */     this.renderingMode = renderingMode;
/* 319 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getReason() {
/* 328 */     return this.reason;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PdfSignatureAppearance setReason(String reason) {
/* 337 */     this.reason = reason;
/* 338 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PdfSignatureAppearance setReasonCaption(String reasonCaption) {
/* 347 */     this.reasonCaption = reasonCaption;
/* 348 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getLocation() {
/* 357 */     return this.location;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PdfSignatureAppearance setLocation(String location) {
/* 366 */     this.location = location;
/* 367 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PdfSignatureAppearance setLocationCaption(String locationCaption) {
/* 376 */     this.locationCaption = locationCaption;
/* 377 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getSignatureCreator() {
/* 386 */     return this.signatureCreator;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PdfSignatureAppearance setSignatureCreator(String signatureCreator) {
/* 395 */     this.signatureCreator = signatureCreator;
/* 396 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getContact() {
/* 405 */     return this.contact;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PdfSignatureAppearance setContact(String contact) {
/* 414 */     this.contact = contact;
/* 415 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PdfSignatureAppearance setCertificate(Certificate signCertificate) {
/* 425 */     this.signCertificate = signCertificate;
/* 426 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Certificate getCertificate() {
/* 435 */     return this.signCertificate;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ImageData getSignatureGraphic() {
/* 444 */     return this.signatureGraphic;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PdfSignatureAppearance setSignatureGraphic(ImageData signatureGraphic) {
/* 453 */     this.signatureGraphic = signatureGraphic;
/* 454 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PdfSignatureAppearance setReuseAppearance(boolean reuseAppearance) {
/* 463 */     this.reuseAppearance = reuseAppearance;
/* 464 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ImageData getImage() {
/* 475 */     return this.image;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PdfSignatureAppearance setImage(ImageData image) {
/* 484 */     this.image = image;
/* 485 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getImageScale() {
/* 494 */     return this.imageScale;
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
/*     */   public PdfSignatureAppearance setImageScale(float imageScale) {
/* 506 */     this.imageScale = imageScale;
/* 507 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PdfSignatureAppearance setLayer2Text(String text) {
/* 517 */     this.layer2Text = text;
/* 518 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getLayer2Text() {
/* 527 */     return this.layer2Text;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PdfFont getLayer2Font() {
/* 536 */     return this.layer2Font;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PdfSignatureAppearance setLayer2Font(PdfFont layer2Font) {
/* 545 */     this.layer2Font = layer2Font;
/* 546 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PdfSignatureAppearance setLayer2FontSize(float fontSize) {
/* 555 */     this.layer2FontSize = fontSize;
/* 556 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getLayer2FontSize() {
/* 565 */     return this.layer2FontSize;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PdfSignatureAppearance setLayer2FontColor(Color color) {
/* 574 */     this.layer2FontColor = color;
/* 575 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Color getLayer2FontColor() {
/* 584 */     return this.layer2FontColor;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isInvisible() {
/* 593 */     return (this.rect == null || this.rect.getWidth() == 0.0F || this.rect.getHeight() == 0.0F);
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
/*     */   protected PdfFormXObject getAppearance() throws IOException {
/* 605 */     if (isInvisible()) {
/* 606 */       PdfFormXObject appearance = new PdfFormXObject(new Rectangle(0.0F, 0.0F));
/* 607 */       appearance.makeIndirect(this.document);
/* 608 */       return appearance;
/*     */     } 
/*     */     
/* 611 */     if (this.n0 == null && !this.reuseAppearance) {
/* 612 */       createBlankN0();
/*     */     }
/*     */     
/* 615 */     if (this.n2 == null) {
/* 616 */       String text; PdfFont font; String signedBy; float imgWidth, imgHeight, multiplierH, multiplierW, multiplier, x, y; this.n2 = new PdfFormXObject(this.rect);
/* 617 */       this.n2.makeIndirect(this.document);
/*     */       
/* 619 */       PdfCanvas pdfCanvas = new PdfCanvas(this.n2, this.document);
/* 620 */       int rotation = this.document.getPage(this.page).getRotation();
/*     */       
/* 622 */       if (rotation == 90) {
/* 623 */         pdfCanvas.concatMatrix(0.0D, 1.0D, -1.0D, 0.0D, this.rect.getWidth(), 0.0D);
/* 624 */       } else if (rotation == 180) {
/* 625 */         pdfCanvas.concatMatrix(-1.0D, 0.0D, 0.0D, -1.0D, this.rect.getWidth(), this.rect.getHeight());
/* 626 */       } else if (rotation == 270) {
/* 627 */         pdfCanvas.concatMatrix(0.0D, -1.0D, 1.0D, 0.0D, 0.0D, this.rect.getHeight());
/*     */       } 
/*     */       
/* 630 */       Rectangle rotatedRect = rotateRectangle(this.rect, this.document.getPage(this.page).getRotation());
/*     */ 
/*     */ 
/*     */       
/* 634 */       if (this.layer2Text == null) {
/* 635 */         StringBuilder buf = new StringBuilder();
/* 636 */         buf.append("Digitally signed by ");
/* 637 */         String name = null;
/* 638 */         CertificateInfo.X500Name x500name = CertificateInfo.getSubjectFields((X509Certificate)this.signCertificate);
/* 639 */         if (x500name != null) {
/* 640 */           name = x500name.getField("CN");
/* 641 */           if (name == null)
/* 642 */             name = x500name.getField("E"); 
/*     */         } 
/* 644 */         if (name == null)
/* 645 */           name = ""; 
/* 646 */         buf.append(name).append('\n');
/* 647 */         buf.append("Date: ").append(SignUtils.dateToString(this.signDate));
/* 648 */         if (this.reason != null)
/* 649 */           buf.append('\n').append(this.reasonCaption).append(this.reason); 
/* 650 */         if (this.location != null)
/* 651 */           buf.append('\n').append(this.locationCaption).append(this.location); 
/* 652 */         text = buf.toString();
/*     */       } else {
/* 654 */         text = this.layer2Text;
/*     */       } 
/*     */       
/* 657 */       if (this.image != null) {
/* 658 */         if (this.imageScale == 0.0F) {
/* 659 */           pdfCanvas = new PdfCanvas(this.n2, this.document);
/* 660 */           pdfCanvas.addImage(this.image, rotatedRect.getWidth(), 0.0F, 0.0F, rotatedRect.getHeight(), 0.0F, 0.0F);
/*     */         } else {
/* 662 */           float usableScale = this.imageScale;
/*     */           
/* 664 */           if (this.imageScale < 0.0F) {
/* 665 */             usableScale = Math.min(rotatedRect.getWidth() / this.image.getWidth(), rotatedRect.getHeight() / this.image.getHeight());
/*     */           }
/*     */           
/* 668 */           float w = this.image.getWidth() * usableScale;
/* 669 */           float h = this.image.getHeight() * usableScale;
/* 670 */           float f1 = (rotatedRect.getWidth() - w) / 2.0F;
/* 671 */           float f2 = (rotatedRect.getHeight() - h) / 2.0F;
/*     */           
/* 673 */           pdfCanvas = new PdfCanvas(this.n2, this.document);
/* 674 */           pdfCanvas.addImage(this.image, w, 0.0F, 0.0F, h, f1, f2);
/*     */         } 
/*     */       }
/*     */ 
/*     */ 
/*     */       
/* 680 */       if (this.layer2Font == null) {
/* 681 */         font = PdfFontFactory.createFont();
/*     */       } else {
/* 683 */         font = this.layer2Font;
/*     */       } 
/*     */       
/* 686 */       Rectangle dataRect = null;
/* 687 */       Rectangle signatureRect = null;
/*     */       
/* 689 */       if (this.renderingMode == RenderingMode.NAME_AND_DESCRIPTION || (this.renderingMode == RenderingMode.GRAPHIC_AND_DESCRIPTION && this.signatureGraphic != null)) {
/*     */         
/* 691 */         if (rotatedRect.getHeight() > rotatedRect.getWidth()) {
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 696 */           signatureRect = new Rectangle(2.0F, rotatedRect.getHeight() / 2.0F, rotatedRect.getWidth() - 4.0F, rotatedRect.getHeight() / 2.0F);
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 701 */           dataRect = new Rectangle(2.0F, 2.0F, rotatedRect.getWidth() - 4.0F, rotatedRect.getHeight() / 2.0F - 4.0F);
/*     */ 
/*     */         
/*     */         }
/*     */         else {
/*     */ 
/*     */           
/* 708 */           signatureRect = new Rectangle(2.0F, 2.0F, rotatedRect.getWidth() / 2.0F - 4.0F, rotatedRect.getHeight() - 4.0F);
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 713 */           dataRect = new Rectangle(rotatedRect.getWidth() / 2.0F + 1.0F, 2.0F, rotatedRect.getWidth() / 2.0F - 2.0F, rotatedRect.getHeight() - 4.0F);
/*     */         } 
/* 715 */       } else if (this.renderingMode == RenderingMode.GRAPHIC) {
/* 716 */         if (this.signatureGraphic == null) {
/* 717 */           throw new IllegalStateException("A signature image must be present when rendering mode is graphic. Use setSignatureGraphic()");
/*     */         }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 724 */         signatureRect = new Rectangle(2.0F, 2.0F, rotatedRect.getWidth() - 4.0F, rotatedRect.getHeight() - 4.0F);
/*     */       
/*     */       }
/*     */       else {
/*     */ 
/*     */         
/* 730 */         dataRect = new Rectangle(2.0F, 2.0F, rotatedRect.getWidth() - 4.0F, rotatedRect.getHeight() * 0.7F - 4.0F);
/*     */       } 
/*     */       
/* 733 */       switch (this.renderingMode) {
/*     */         case NAME_AND_DESCRIPTION:
/* 735 */           signedBy = CertificateInfo.getSubjectFields((X509Certificate)this.signCertificate).getField("CN");
/*     */           
/* 737 */           if (signedBy == null) {
/* 738 */             signedBy = CertificateInfo.getSubjectFields((X509Certificate)this.signCertificate).getField("E");
/*     */           }
/*     */           
/* 741 */           if (signedBy == null) {
/* 742 */             signedBy = "";
/*     */           }
/*     */           
/* 745 */           addTextToCanvas(signedBy, font, signatureRect);
/*     */           break;
/*     */         case GRAPHIC_AND_DESCRIPTION:
/* 748 */           if (this.signatureGraphic == null) {
/* 749 */             throw new IllegalStateException("A signature image must be present when rendering mode is graphic and description. Use setSignatureGraphic()");
/*     */           }
/*     */           
/* 752 */           imgWidth = this.signatureGraphic.getWidth();
/*     */           
/* 754 */           if (imgWidth == 0.0F) {
/* 755 */             imgWidth = signatureRect.getWidth();
/*     */           }
/*     */           
/* 758 */           imgHeight = this.signatureGraphic.getHeight();
/*     */           
/* 760 */           if (imgHeight == 0.0F) {
/* 761 */             imgHeight = signatureRect.getHeight();
/*     */           }
/*     */           
/* 764 */           multiplierH = signatureRect.getWidth() / this.signatureGraphic.getWidth();
/* 765 */           multiplierW = signatureRect.getHeight() / this.signatureGraphic.getHeight();
/* 766 */           multiplier = Math.min(multiplierH, multiplierW);
/* 767 */           imgWidth *= multiplier;
/* 768 */           imgHeight *= multiplier;
/*     */           
/* 770 */           x = signatureRect.getRight() - imgWidth;
/* 771 */           y = signatureRect.getBottom() + (signatureRect.getHeight() - imgHeight) / 2.0F;
/*     */           
/* 773 */           pdfCanvas = new PdfCanvas(this.n2, this.document);
/* 774 */           pdfCanvas.addImage(this.signatureGraphic, imgWidth, 0.0F, 0.0F, imgHeight, x, y);
/*     */           break;
/*     */         
/*     */         case GRAPHIC:
/* 778 */           imgWidth = this.signatureGraphic.getWidth();
/*     */           
/* 780 */           if (imgWidth == 0.0F) {
/* 781 */             imgWidth = signatureRect.getWidth();
/*     */           }
/*     */           
/* 784 */           imgHeight = this.signatureGraphic.getHeight();
/*     */           
/* 786 */           if (imgHeight == 0.0F) {
/* 787 */             imgHeight = signatureRect.getHeight();
/*     */           }
/*     */           
/* 790 */           multiplierH = signatureRect.getWidth() / this.signatureGraphic.getWidth();
/* 791 */           multiplierW = signatureRect.getHeight() / this.signatureGraphic.getHeight();
/* 792 */           multiplier = Math.min(multiplierH, multiplierW);
/* 793 */           imgWidth *= multiplier;
/* 794 */           imgHeight *= multiplier;
/*     */           
/* 796 */           x = signatureRect.getLeft() + (signatureRect.getWidth() - imgWidth) / 2.0F;
/* 797 */           y = signatureRect.getBottom() + (signatureRect.getHeight() - imgHeight) / 2.0F;
/*     */           
/* 799 */           pdfCanvas = new PdfCanvas(this.n2, this.document);
/* 800 */           pdfCanvas.addImage(this.signatureGraphic, imgWidth, 0.0F, 0.0F, imgHeight, x, y);
/*     */           break;
/*     */       } 
/*     */       
/* 804 */       if (this.renderingMode != RenderingMode.GRAPHIC) {
/* 805 */         addTextToCanvas(text, font, dataRect);
/*     */       }
/*     */     } 
/*     */     
/* 809 */     Rectangle rotated = new Rectangle(this.rect);
/*     */     
/* 811 */     if (this.topLayer == null) {
/* 812 */       this.topLayer = new PdfFormXObject(rotated);
/* 813 */       this.topLayer.makeIndirect(this.document);
/*     */       
/* 815 */       if (this.reuseAppearance) {
/* 816 */         PdfAcroForm acroForm = PdfAcroForm.getAcroForm(this.document, true);
/* 817 */         PdfFormField field = acroForm.getField(this.fieldName);
/* 818 */         PdfStream stream = ((PdfWidgetAnnotation)field.getWidgets().get(0)).getAppearanceDictionary().getAsStream(PdfName.N);
/* 819 */         PdfFormXObject xobj = new PdfFormXObject(stream);
/*     */         
/* 821 */         if (stream != null) {
/* 822 */           this.topLayer.getResources().addForm(xobj, new PdfName("n0"));
/* 823 */           PdfCanvas pdfCanvas = new PdfCanvas(this.topLayer, this.document);
/* 824 */           pdfCanvas.addXObject((PdfXObject)xobj, 1.0F, 0.0F, 0.0F, 1.0F, 0.0F, 0.0F);
/*     */         } else {
/* 826 */           this.reuseAppearance = false;
/*     */           
/* 828 */           if (this.n0 == null) {
/* 829 */             createBlankN0();
/*     */           }
/*     */         } 
/*     */       } 
/*     */       
/* 834 */       if (!this.reuseAppearance) {
/* 835 */         this.topLayer.getResources().addForm(this.n0, new PdfName("n0"));
/* 836 */         PdfCanvas pdfCanvas = new PdfCanvas(this.topLayer, this.document);
/* 837 */         pdfCanvas.addXObject((PdfXObject)this.n0, 1.0F, 0.0F, 0.0F, 1.0F, 0.0F, 0.0F);
/*     */       } 
/*     */       
/* 840 */       this.topLayer.getResources().addForm(this.n2, new PdfName("n2"));
/* 841 */       PdfCanvas canvas1 = new PdfCanvas(this.topLayer, this.document);
/* 842 */       canvas1.addXObject((PdfXObject)this.n2, 1.0F, 0.0F, 0.0F, 1.0F, 0.0F, 0.0F);
/*     */     } 
/*     */     
/* 845 */     PdfFormXObject napp = new PdfFormXObject(rotated);
/* 846 */     napp.makeIndirect(this.document);
/* 847 */     napp.getResources().addForm(this.topLayer, new PdfName("FRM"));
/*     */     
/* 849 */     PdfCanvas canvas = new PdfCanvas(napp, this.document);
/* 850 */     canvas.addXObject((PdfXObject)this.topLayer, 0.0F, 0.0F);
/*     */     
/* 852 */     return napp;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected Calendar getSignDate() {
/* 861 */     return this.signDate;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected PdfSignatureAppearance setSignDate(Calendar signDate) {
/* 870 */     this.signDate = signDate;
/* 871 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected PdfSignatureAppearance setFieldName(String fieldName) {
/* 880 */     this.fieldName = fieldName;
/* 881 */     return this;
/*     */   }
/*     */   
/*     */   private static Rectangle rotateRectangle(Rectangle rect, int angle) {
/* 885 */     if (0 == angle / 90 % 2) {
/* 886 */       return new Rectangle(rect.getWidth(), rect.getHeight());
/*     */     }
/* 888 */     return new Rectangle(rect.getHeight(), rect.getWidth());
/*     */   }
/*     */ 
/*     */   
/*     */   private void createBlankN0() {
/* 893 */     this.n0 = new PdfFormXObject(new Rectangle(100.0F, 100.0F));
/* 894 */     this.n0.makeIndirect(this.document);
/*     */     
/* 896 */     PdfCanvas canvas = new PdfCanvas(this.n0, this.document);
/* 897 */     canvas.writeLiteral("% DSBlank\n");
/*     */   }
/*     */ 
/*     */   
/*     */   private void addTextToCanvas(String text, PdfFont font, Rectangle dataRect) {
/* 902 */     PdfCanvas canvas = new PdfCanvas(this.n2, this.document);
/* 903 */     Paragraph paragraph = ((Paragraph)((Paragraph)(new Paragraph(text)).setFont(font)).setMargin(0.0F)).setMultipliedLeading(0.9F);
/* 904 */     Canvas layoutCanvas = new Canvas(canvas, dataRect);
/* 905 */     paragraph.setFontColor(this.layer2FontColor);
/* 906 */     if (this.layer2FontSize == 0.0F) {
/* 907 */       applyCopyFittingFontSize(paragraph, dataRect, (IRenderer)layoutCanvas.getRenderer());
/*     */     } else {
/* 909 */       paragraph.setFontSize(this.layer2FontSize);
/*     */     } 
/* 911 */     layoutCanvas.add((IBlockElement)paragraph);
/*     */   }
/*     */   
/*     */   private void applyCopyFittingFontSize(Paragraph paragraph, Rectangle rect, IRenderer parentRenderer) {
/* 915 */     IRenderer renderer = paragraph.createRendererSubTree().setParent(parentRenderer);
/* 916 */     LayoutContext layoutContext = new LayoutContext(new LayoutArea(1, rect));
/* 917 */     float lFontSize = 0.1F, rFontSize = 100.0F;
/* 918 */     int numberOfIterations = 15;
/* 919 */     for (int i = 0; i < numberOfIterations; i++) {
/* 920 */       float mFontSize = (lFontSize + rFontSize) / 2.0F;
/* 921 */       paragraph.setFontSize(mFontSize);
/* 922 */       LayoutResult result = renderer.layout(layoutContext);
/* 923 */       if (result.getStatus() == 1) {
/* 924 */         lFontSize = mFontSize;
/*     */       } else {
/* 926 */         rFontSize = mFontSize;
/*     */       } 
/*     */     } 
/* 929 */     paragraph.setFontSize(lFontSize);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public enum RenderingMode
/*     */   {
/* 939 */     DESCRIPTION,
/*     */ 
/*     */ 
/*     */     
/* 943 */     NAME_AND_DESCRIPTION,
/*     */ 
/*     */ 
/*     */     
/* 947 */     GRAPHIC_AND_DESCRIPTION,
/*     */ 
/*     */ 
/*     */     
/* 951 */     GRAPHIC;
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/signatures/PdfSignatureAppearance.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */