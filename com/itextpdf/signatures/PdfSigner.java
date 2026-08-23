/*      */ package com.itextpdf.signatures;
/*      */ 
/*      */ import com.itextpdf.forms.PdfAcroForm;
/*      */ import com.itextpdf.forms.PdfSigFieldLock;
/*      */ import com.itextpdf.forms.fields.PdfFormField;
/*      */ import com.itextpdf.forms.fields.PdfSignatureFormField;
/*      */ import com.itextpdf.io.source.ByteBuffer;
/*      */ import com.itextpdf.io.source.IRandomAccessSource;
/*      */ import com.itextpdf.io.source.RASInputStream;
/*      */ import com.itextpdf.io.source.RandomAccessSourceFactory;
/*      */ import com.itextpdf.io.util.DateTimeUtil;
/*      */ import com.itextpdf.io.util.FileUtil;
/*      */ import com.itextpdf.io.util.StreamUtil;
/*      */ import com.itextpdf.kernel.PdfException;
/*      */ import com.itextpdf.kernel.geom.Rectangle;
/*      */ import com.itextpdf.kernel.pdf.PdfAConformanceLevel;
/*      */ import com.itextpdf.kernel.pdf.PdfArray;
/*      */ import com.itextpdf.kernel.pdf.PdfDate;
/*      */ import com.itextpdf.kernel.pdf.PdfDeveloperExtension;
/*      */ import com.itextpdf.kernel.pdf.PdfDictionary;
/*      */ import com.itextpdf.kernel.pdf.PdfDocument;
/*      */ import com.itextpdf.kernel.pdf.PdfLiteral;
/*      */ import com.itextpdf.kernel.pdf.PdfName;
/*      */ import com.itextpdf.kernel.pdf.PdfNumber;
/*      */ import com.itextpdf.kernel.pdf.PdfObject;
/*      */ import com.itextpdf.kernel.pdf.PdfOutputStream;
/*      */ import com.itextpdf.kernel.pdf.PdfPage;
/*      */ import com.itextpdf.kernel.pdf.PdfReader;
/*      */ import com.itextpdf.kernel.pdf.PdfString;
/*      */ import com.itextpdf.kernel.pdf.PdfVersion;
/*      */ import com.itextpdf.kernel.pdf.PdfWriter;
/*      */ import com.itextpdf.kernel.pdf.StampingProperties;
/*      */ import com.itextpdf.kernel.pdf.annot.PdfAnnotation;
/*      */ import com.itextpdf.kernel.pdf.annot.PdfWidgetAnnotation;
/*      */ import com.itextpdf.pdfa.PdfADocument;
/*      */ import java.io.ByteArrayOutputStream;
/*      */ import java.io.EOFException;
/*      */ import java.io.File;
/*      */ import java.io.IOException;
/*      */ import java.io.InputStream;
/*      */ import java.io.OutputStream;
/*      */ import java.io.RandomAccessFile;
/*      */ import java.security.GeneralSecurityException;
/*      */ import java.security.MessageDigest;
/*      */ import java.security.PrivateKey;
/*      */ import java.security.cert.Certificate;
/*      */ import java.security.cert.X509Certificate;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Arrays;
/*      */ import java.util.Calendar;
/*      */ import java.util.Collection;
/*      */ import java.util.HashMap;
/*      */ import java.util.List;
/*      */ import java.util.Map;
/*      */ import org.bouncycastle.asn1.esf.SignaturePolicyIdentifier;
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
/*      */ public class PdfSigner
/*      */ {
/*      */   public static final int NOT_CERTIFIED = 0;
/*      */   public static final int CERTIFIED_NO_CHANGES_ALLOWED = 1;
/*      */   public static final int CERTIFIED_FORM_FILLING = 2;
/*      */   public static final int CERTIFIED_FORM_FILLING_AND_ANNOTATIONS = 3;
/*      */   
/*      */   public static interface ISignatureEvent
/*      */   {
/*      */     void getSignatureDictionary(PdfSignature param1PdfSignature);
/*      */   }
/*      */   
/*      */   public enum CryptoStandard
/*      */   {
/*  116 */     CMS,
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  121 */     CADES;
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
/*  147 */   protected int certificationLevel = 0;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected String fieldName;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected RandomAccessFile raf;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected byte[] bout;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected long[] range;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected PdfDocument document;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected PdfSignature cryptoDictionary;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private PdfName digestMethod;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected ISignatureEvent signatureEvent;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected OutputStream originalOS;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected ByteArrayOutputStream temporaryOS;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected File tempFile;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected Map<PdfName, PdfLiteral> exclusionLocations;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected boolean preClosed = false;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected PdfSigFieldLock fieldLock;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected PdfSignatureAppearance appearance;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected Calendar signDate;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected boolean closed;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   public PdfSigner(PdfReader reader, OutputStream outputStream, boolean append) throws IOException {
/*  242 */     this(reader, outputStream, (String)null, append);
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
/*      */   public PdfSigner(PdfReader reader, OutputStream outputStream, String path, boolean append) throws IOException {
/*  258 */     this(reader, outputStream, path, initStampingProperties(append));
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
/*      */   public PdfSigner(PdfReader reader, OutputStream outputStream, StampingProperties properties) throws IOException {
/*  271 */     this(reader, outputStream, (String)null, properties);
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
/*      */   public PdfSigner(PdfReader reader, OutputStream outputStream, String path, StampingProperties properties) throws IOException {
/*  285 */     StampingProperties localProps = (new StampingProperties(properties)).preserveEncryption();
/*  286 */     if (path == null) {
/*  287 */       this.temporaryOS = new ByteArrayOutputStream();
/*  288 */       this.document = initDocument(reader, new PdfWriter(this.temporaryOS), localProps);
/*      */     } else {
/*  290 */       this.tempFile = FileUtil.createTempFile(path);
/*  291 */       this.document = initDocument(reader, new PdfWriter(FileUtil.getFileOutputStream(this.tempFile)), localProps);
/*      */     } 
/*      */     
/*  294 */     this.originalOS = outputStream;
/*  295 */     this.signDate = DateTimeUtil.getCurrentTimeCalendar();
/*  296 */     this.fieldName = getNewSigFieldName();
/*  297 */     this.appearance = new PdfSignatureAppearance(this.document, new Rectangle(0.0F, 0.0F), 1);
/*  298 */     this.appearance.setSignDate(this.signDate);
/*      */     
/*  300 */     this.closed = false;
/*      */   }
/*      */   
/*      */   protected PdfDocument initDocument(PdfReader reader, PdfWriter writer, StampingProperties properties) {
/*  304 */     PdfAConformanceLevel conformanceLevel = reader.getPdfAConformanceLevel();
/*  305 */     if (null == conformanceLevel) {
/*  306 */       return new PdfDocument(reader, writer, properties);
/*      */     }
/*  308 */     return (PdfDocument)new PdfADocument(reader, writer, properties);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Calendar getSignDate() {
/*  318 */     return this.signDate;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setSignDate(Calendar signDate) {
/*  327 */     this.signDate = signDate;
/*  328 */     this.appearance.setSignDate(signDate);
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
/*      */   public PdfSignatureAppearance getSignatureAppearance() {
/*  350 */     return this.appearance;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getCertificationLevel() {
/*  360 */     return this.certificationLevel;
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
/*      */   public void setCertificationLevel(int certificationLevel) {
/*  375 */     this.certificationLevel = certificationLevel;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getFieldName() {
/*  384 */     return this.fieldName;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PdfSignature getSignatureDictionary() {
/*  394 */     return this.cryptoDictionary;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ISignatureEvent getSignatureEvent() {
/*  403 */     return this.signatureEvent;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setSignatureEvent(ISignatureEvent signatureEvent) {
/*  412 */     this.signatureEvent = signatureEvent;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getNewSigFieldName() {
/*  421 */     PdfAcroForm acroForm = PdfAcroForm.getAcroForm(this.document, true);
/*  422 */     String name = "Signature";
/*  423 */     int step = 1;
/*      */     
/*  425 */     while (acroForm.getField(name + step) != null) {
/*  426 */       step++;
/*      */     }
/*      */     
/*  429 */     return name + step;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setFieldName(String fieldName) {
/*  439 */     if (fieldName != null) {
/*  440 */       if (fieldName.indexOf('.') >= 0) {
/*  441 */         throw new IllegalArgumentException("Field names cannot contain a dot.");
/*      */       }
/*      */       
/*  444 */       PdfAcroForm acroForm = PdfAcroForm.getAcroForm(this.document, true);
/*      */       
/*  446 */       if (acroForm.getField(fieldName) != null) {
/*  447 */         PdfFormField field = acroForm.getField(fieldName);
/*      */         
/*  449 */         if (!PdfName.Sig.equals(field.getFormType())) {
/*  450 */           throw new IllegalArgumentException("Field type is not a signature field type.");
/*      */         }
/*      */         
/*  453 */         if (field.getValue() != null) {
/*  454 */           throw new IllegalArgumentException("Field has been already signed.");
/*      */         }
/*      */         
/*  457 */         this.appearance.setFieldName(fieldName);
/*      */         
/*  459 */         List<PdfWidgetAnnotation> widgets = field.getWidgets();
/*  460 */         if (widgets.size() > 0) {
/*  461 */           PdfWidgetAnnotation widget = widgets.get(0);
/*  462 */           this.appearance.setPageRect(getWidgetRectangle(widget));
/*  463 */           this.appearance.setPageNumber(getWidgetPageNumber(widget));
/*      */         } 
/*      */       } 
/*      */       
/*  467 */       this.fieldName = fieldName;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PdfDocument getDocument() {
/*  477 */     return this.document;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void setDocument(PdfDocument document) {
/*  486 */     this.document = document;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setOriginalOutputStream(OutputStream originalOS) {
/*  495 */     this.originalOS = originalOS;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PdfSigFieldLock getFieldLockDict() {
/*  504 */     return this.fieldLock;
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
/*      */   public void setFieldLockDict(PdfSigFieldLock fieldLock) {
/*  516 */     this.fieldLock = fieldLock;
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
/*      */   public void signDetached(IExternalDigest externalDigest, IExternalSignature externalSignature, Certificate[] chain, Collection<ICrlClient> crlList, IOcspClient ocspClient, ITSAClient tsaClient, int estimatedSize, CryptoStandard sigtype) throws IOException, GeneralSecurityException {
/*  538 */     signDetached(externalDigest, externalSignature, chain, crlList, ocspClient, tsaClient, estimatedSize, sigtype, (SignaturePolicyIdentifier)null);
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
/*      */   public void signDetached(IExternalDigest externalDigest, IExternalSignature externalSignature, Certificate[] chain, Collection<ICrlClient> crlList, IOcspClient ocspClient, ITSAClient tsaClient, int estimatedSize, CryptoStandard sigtype, SignaturePolicyInfo signaturePolicy) throws IOException, GeneralSecurityException {
/*  561 */     signDetached(externalDigest, externalSignature, chain, crlList, ocspClient, tsaClient, estimatedSize, sigtype, signaturePolicy.toSignaturePolicyIdentifier());
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
/*      */   public void signDetached(IExternalDigest externalDigest, IExternalSignature externalSignature, Certificate[] chain, Collection<ICrlClient> crlList, IOcspClient ocspClient, ITSAClient tsaClient, int estimatedSize, CryptoStandard sigtype, SignaturePolicyIdentifier signaturePolicy) throws IOException, GeneralSecurityException {
/*  584 */     if (this.closed) {
/*  585 */       throw new PdfException("This instance of PdfSigner has been already closed.");
/*      */     }
/*      */     
/*  588 */     if (this.certificationLevel > 0 && isDocumentPdf2() && 
/*  589 */       documentContainsCertificationOrApprovalSignatures()) {
/*  590 */       throw new PdfException("Certification signature creation failed. Document shall not contain any certification or approval signatures before signing with certification signature.");
/*      */     }
/*      */ 
/*      */     
/*  594 */     Collection<byte[]> crlBytes = null;
/*  595 */     int i = 0;
/*  596 */     while (crlBytes == null && i < chain.length)
/*  597 */       crlBytes = processCrl(chain[i++], crlList); 
/*  598 */     if (estimatedSize == 0) {
/*  599 */       estimatedSize = 8192;
/*  600 */       if (crlBytes != null) {
/*  601 */         for (byte[] element : crlBytes) {
/*  602 */           estimatedSize += element.length + 10;
/*      */         }
/*      */       }
/*  605 */       if (ocspClient != null)
/*  606 */         estimatedSize += 4192; 
/*  607 */       if (tsaClient != null)
/*  608 */         estimatedSize += 4192; 
/*      */     } 
/*  610 */     PdfSignatureAppearance appearance = getSignatureAppearance();
/*  611 */     appearance.setCertificate(chain[0]);
/*  612 */     if (sigtype == CryptoStandard.CADES && !isDocumentPdf2()) {
/*  613 */       addDeveloperExtension(PdfDeveloperExtension.ESIC_1_7_EXTENSIONLEVEL2);
/*      */     }
/*  615 */     String hashAlgorithm = externalSignature.getHashAlgorithm();
/*  616 */     PdfSignature dic = new PdfSignature(PdfName.Adobe_PPKLite, (sigtype == CryptoStandard.CADES) ? PdfName.ETSI_CAdES_DETACHED : PdfName.Adbe_pkcs7_detached);
/*  617 */     dic.setReason(appearance.getReason());
/*  618 */     dic.setLocation(appearance.getLocation());
/*  619 */     dic.setSignatureCreator(appearance.getSignatureCreator());
/*  620 */     dic.setContact(appearance.getContact());
/*  621 */     dic.setDate(new PdfDate(getSignDate()));
/*  622 */     this.cryptoDictionary = dic;
/*  623 */     this.digestMethod = getHashAlgorithmNameInCompatibleForPdfForm(hashAlgorithm);
/*      */     
/*  625 */     Map<PdfName, Integer> exc = new HashMap<>();
/*  626 */     exc.put(PdfName.Contents, Integer.valueOf(estimatedSize * 2 + 2));
/*  627 */     preClose(exc);
/*      */     
/*  629 */     PdfPKCS7 sgn = new PdfPKCS7((PrivateKey)null, chain, hashAlgorithm, null, externalDigest, false);
/*  630 */     if (signaturePolicy != null) {
/*  631 */       sgn.setSignaturePolicy(signaturePolicy);
/*      */     }
/*  633 */     InputStream data = getRangeStream();
/*  634 */     byte[] hash = DigestAlgorithms.digest(data, SignUtils.getMessageDigest(hashAlgorithm, externalDigest));
/*  635 */     List<byte[]> ocspList = (List)new ArrayList<>();
/*  636 */     if (chain.length > 1 && ocspClient != null) {
/*  637 */       for (int j = 0; j < chain.length - 1; j++) {
/*  638 */         byte[] ocsp = ocspClient.getEncoded((X509Certificate)chain[j], (X509Certificate)chain[j + 1], null);
/*  639 */         if (ocsp != null) {
/*  640 */           ocspList.add(ocsp);
/*      */         }
/*      */       } 
/*      */     }
/*  644 */     byte[] sh = sgn.getAuthenticatedAttributeBytes(hash, sigtype, (Collection<byte[]>)ocspList, crlBytes);
/*  645 */     byte[] extSignature = externalSignature.sign(sh);
/*  646 */     sgn.setExternalDigest(extSignature, null, externalSignature.getEncryptionAlgorithm());
/*      */     
/*  648 */     byte[] encodedSig = sgn.getEncodedPKCS7(hash, sigtype, tsaClient, (Collection<byte[]>)ocspList, crlBytes);
/*      */     
/*  650 */     if (estimatedSize < encodedSig.length) {
/*  651 */       throw new IOException("Not enough space");
/*      */     }
/*  653 */     byte[] paddedSig = new byte[estimatedSize];
/*  654 */     System.arraycopy(encodedSig, 0, paddedSig, 0, encodedSig.length);
/*      */     
/*  656 */     PdfDictionary dic2 = new PdfDictionary();
/*  657 */     dic2.put(PdfName.Contents, (PdfObject)(new PdfString(paddedSig)).setHexWriting(true));
/*  658 */     close(dic2);
/*      */     
/*  660 */     this.closed = true;
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
/*      */   public void signExternalContainer(IExternalSignatureContainer externalSignatureContainer, int estimatedSize) throws GeneralSecurityException, IOException {
/*  676 */     if (this.closed) {
/*  677 */       throw new PdfException("This instance of PdfSigner has been already closed.");
/*      */     }
/*      */     
/*  680 */     PdfSignature dic = new PdfSignature();
/*  681 */     PdfSignatureAppearance appearance = getSignatureAppearance();
/*  682 */     dic.setReason(appearance.getReason());
/*  683 */     dic.setLocation(appearance.getLocation());
/*  684 */     dic.setSignatureCreator(appearance.getSignatureCreator());
/*  685 */     dic.setContact(appearance.getContact());
/*  686 */     dic.setDate(new PdfDate(getSignDate()));
/*  687 */     externalSignatureContainer.modifySigningDictionary((PdfDictionary)dic.getPdfObject());
/*  688 */     this.cryptoDictionary = dic;
/*      */     
/*  690 */     Map<PdfName, Integer> exc = new HashMap<>();
/*  691 */     exc.put(PdfName.Contents, Integer.valueOf(estimatedSize * 2 + 2));
/*  692 */     preClose(exc);
/*      */     
/*  694 */     InputStream data = getRangeStream();
/*  695 */     byte[] encodedSig = externalSignatureContainer.sign(data);
/*      */     
/*  697 */     if (estimatedSize < encodedSig.length) {
/*  698 */       throw new IOException("Not enough space");
/*      */     }
/*  700 */     byte[] paddedSig = new byte[estimatedSize];
/*  701 */     System.arraycopy(encodedSig, 0, paddedSig, 0, encodedSig.length);
/*      */     
/*  703 */     PdfDictionary dic2 = new PdfDictionary();
/*  704 */     dic2.put(PdfName.Contents, (PdfObject)(new PdfString(paddedSig)).setHexWriting(true));
/*  705 */     close(dic2);
/*      */     
/*  707 */     this.closed = true;
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
/*      */   public void timestamp(ITSAClient tsa, String signatureName) throws IOException, GeneralSecurityException {
/*      */     byte[] tsToken;
/*  723 */     if (this.closed) {
/*  724 */       throw new PdfException("This instance of PdfSigner has been already closed.");
/*      */     }
/*      */     
/*  727 */     int contentEstimated = tsa.getTokenSizeEstimate();
/*  728 */     if (!isDocumentPdf2()) {
/*  729 */       addDeveloperExtension(PdfDeveloperExtension.ESIC_1_7_EXTENSIONLEVEL5);
/*      */     }
/*  731 */     setFieldName(signatureName);
/*      */     
/*  733 */     PdfSignature dic = new PdfSignature(PdfName.Adobe_PPKLite, PdfName.ETSI_RFC3161);
/*  734 */     dic.put(PdfName.Type, (PdfObject)PdfName.DocTimeStamp);
/*  735 */     this.cryptoDictionary = dic;
/*      */     
/*  737 */     Map<PdfName, Integer> exc = new HashMap<>();
/*  738 */     exc.put(PdfName.Contents, Integer.valueOf(contentEstimated * 2 + 2));
/*  739 */     preClose(exc);
/*  740 */     InputStream data = getRangeStream();
/*  741 */     MessageDigest messageDigest = tsa.getMessageDigest();
/*  742 */     byte[] buf = new byte[4096];
/*      */     int n;
/*  744 */     while ((n = data.read(buf)) > 0) {
/*  745 */       messageDigest.update(buf, 0, n);
/*      */     }
/*  747 */     byte[] tsImprint = messageDigest.digest();
/*      */     
/*      */     try {
/*  750 */       tsToken = tsa.getTimeStampToken(tsImprint);
/*  751 */     } catch (Exception e) {
/*  752 */       throw new GeneralSecurityException(e.getMessage(), e);
/*      */     } 
/*      */     
/*  755 */     if (contentEstimated + 2 < tsToken.length) {
/*  756 */       throw new IOException("Not enough space");
/*      */     }
/*  758 */     byte[] paddedSig = new byte[contentEstimated];
/*  759 */     System.arraycopy(tsToken, 0, paddedSig, 0, tsToken.length);
/*      */     
/*  761 */     PdfDictionary dic2 = new PdfDictionary();
/*  762 */     dic2.put(PdfName.Contents, (PdfObject)(new PdfString(paddedSig)).setHexWriting(true));
/*  763 */     close(dic2);
/*      */     
/*  765 */     this.closed = true;
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
/*      */   public static void signDeferred(PdfDocument document, String fieldName, OutputStream outs, IExternalSignatureContainer externalSignatureContainer) throws IOException, GeneralSecurityException {
/*  780 */     SignatureUtil signatureUtil = new SignatureUtil(document);
/*  781 */     PdfSignature signature = signatureUtil.getSignature(fieldName);
/*  782 */     if (signature == null) {
/*  783 */       throw (new PdfException("There is no field in the document with such name: {0}.")).setMessageParams(new Object[] { fieldName });
/*      */     }
/*  785 */     if (!signatureUtil.signatureCoversWholeDocument(fieldName)) {
/*  786 */       throw (new PdfException("Signature with name {0} is not the last. It doesn't cover the whole document.")).setMessageParams(new Object[] { fieldName });
/*      */     }
/*      */     
/*  789 */     PdfArray b = signature.getByteRange();
/*  790 */     long[] gaps = b.toLongArray();
/*      */     
/*  792 */     if (b.size() != 4 || gaps[0] != 0L) {
/*  793 */       throw new IllegalArgumentException("Single exclusion space supported");
/*      */     }
/*      */     
/*  796 */     IRandomAccessSource readerSource = document.getReader().getSafeFile().createSourceView();
/*  797 */     RASInputStream rASInputStream = new RASInputStream((new RandomAccessSourceFactory()).createRanged(readerSource, gaps));
/*  798 */     byte[] signedContent = externalSignatureContainer.sign((InputStream)rASInputStream);
/*  799 */     int spaceAvailable = (int)(gaps[2] - gaps[1]) - 2;
/*  800 */     if ((spaceAvailable & 0x1) != 0) {
/*  801 */       throw new IllegalArgumentException("Gap is not a multiple of 2");
/*      */     }
/*  803 */     spaceAvailable /= 2;
/*  804 */     if (spaceAvailable < signedContent.length) {
/*  805 */       throw new PdfException("Available space is not enough for signature.");
/*      */     }
/*  807 */     StreamUtil.copyBytes(readerSource, 0L, gaps[1] + 1L, outs);
/*  808 */     ByteBuffer bb = new ByteBuffer(spaceAvailable * 2);
/*  809 */     for (byte bi : signedContent) {
/*  810 */       bb.appendHex(bi);
/*      */     }
/*  812 */     int remain = (spaceAvailable - signedContent.length) * 2;
/*  813 */     for (int k = 0; k < remain; k++) {
/*  814 */       bb.append((byte)48);
/*      */     }
/*  816 */     byte[] bbArr = bb.toByteArray();
/*  817 */     outs.write(bbArr);
/*  818 */     StreamUtil.copyBytes(readerSource, gaps[2] - 1L, gaps[3] + 1L, outs);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected Collection<byte[]> processCrl(Certificate cert, Collection<ICrlClient> crlList) {
/*  829 */     if (crlList == null)
/*  830 */       return null; 
/*  831 */     List<byte[]> crlBytes = (List)new ArrayList<>();
/*  832 */     for (ICrlClient cc : crlList) {
/*  833 */       if (cc == null)
/*      */         continue; 
/*  835 */       Collection<byte[]> b = cc.getEncoded((X509Certificate)cert, null);
/*  836 */       if (b == null)
/*      */         continue; 
/*  838 */       crlBytes.addAll((Collection)b);
/*      */     } 
/*  840 */     if (crlBytes.size() == 0) {
/*  841 */       return null;
/*      */     }
/*  843 */     return (Collection<byte[]>)crlBytes;
/*      */   }
/*      */   
/*      */   protected void addDeveloperExtension(PdfDeveloperExtension extension) {
/*  847 */     this.document.getCatalog().addDeveloperExtension(extension);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected boolean isPreClosed() {
/*  856 */     return this.preClosed;
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
/*      */   protected void preClose(Map<PdfName, Integer> exclusionSizes) throws IOException {
/*  872 */     if (this.preClosed) {
/*  873 */       throw new PdfException("Document has been already pre closed.");
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*  878 */     this.preClosed = true;
/*  879 */     PdfAcroForm acroForm = PdfAcroForm.getAcroForm(this.document, true);
/*  880 */     SignatureUtil sgnUtil = new SignatureUtil(this.document);
/*  881 */     String name = getFieldName();
/*  882 */     boolean fieldExist = sgnUtil.doesSignatureFieldExist(name);
/*  883 */     acroForm.setSignatureFlags(3);
/*  884 */     PdfSigFieldLock fieldLock = null;
/*      */     
/*  886 */     if (this.cryptoDictionary == null) {
/*  887 */       throw new PdfException("No crypto dictionary defined.");
/*      */     }
/*      */     
/*  890 */     ((PdfDictionary)this.cryptoDictionary.getPdfObject()).makeIndirect(this.document);
/*      */     
/*  892 */     if (fieldExist) {
/*  893 */       PdfSignatureFormField sigField = (PdfSignatureFormField)acroForm.getField(this.fieldName);
/*  894 */       sigField.put(PdfName.V, this.cryptoDictionary.getPdfObject());
/*      */       
/*  896 */       fieldLock = sigField.getSigFieldLockDictionary();
/*      */       
/*  898 */       if (fieldLock == null && this.fieldLock != null) {
/*  899 */         ((PdfDictionary)this.fieldLock.getPdfObject()).makeIndirect(this.document);
/*  900 */         sigField.put(PdfName.Lock, this.fieldLock.getPdfObject());
/*  901 */         fieldLock = this.fieldLock;
/*      */       } 
/*      */       
/*  904 */       sigField.put(PdfName.P, this.document.getPage(this.appearance.getPageNumber()).getPdfObject());
/*  905 */       sigField.put(PdfName.V, this.cryptoDictionary.getPdfObject());
/*  906 */       PdfObject obj = ((PdfDictionary)sigField.getPdfObject()).get(PdfName.F);
/*  907 */       int flags = 0;
/*      */       
/*  909 */       if (obj != null && obj.isNumber()) {
/*  910 */         flags = ((PdfNumber)obj).intValue();
/*      */       }
/*      */       
/*  913 */       flags |= 0x80;
/*  914 */       sigField.put(PdfName.F, (PdfObject)new PdfNumber(flags));
/*  915 */       PdfDictionary ap = new PdfDictionary();
/*  916 */       ap.put(PdfName.N, this.appearance.getAppearance().getPdfObject());
/*  917 */       sigField.put(PdfName.AP, (PdfObject)ap);
/*  918 */       sigField.setModified();
/*      */     } else {
/*  920 */       PdfWidgetAnnotation widget = new PdfWidgetAnnotation(this.appearance.getPageRect());
/*  921 */       widget.setFlags(132);
/*      */       
/*  923 */       PdfSignatureFormField sigField = PdfFormField.createSignature(this.document);
/*  924 */       sigField.setFieldName(name);
/*  925 */       sigField.put(PdfName.V, this.cryptoDictionary.getPdfObject());
/*  926 */       sigField.addKid(widget);
/*      */       
/*  928 */       if (this.fieldLock != null) {
/*  929 */         ((PdfDictionary)this.fieldLock.getPdfObject()).makeIndirect(this.document);
/*  930 */         sigField.put(PdfName.Lock, this.fieldLock.getPdfObject());
/*  931 */         fieldLock = this.fieldLock;
/*      */       } 
/*      */       
/*  934 */       int pagen = this.appearance.getPageNumber();
/*  935 */       widget.setPage(this.document.getPage(pagen));
/*  936 */       PdfDictionary ap = widget.getAppearanceDictionary();
/*      */       
/*  938 */       if (ap == null) {
/*  939 */         ap = new PdfDictionary();
/*  940 */         widget.put(PdfName.AP, (PdfObject)ap);
/*      */       } 
/*      */       
/*  943 */       ap.put(PdfName.N, this.appearance.getAppearance().getPdfObject());
/*  944 */       acroForm.addField((PdfFormField)sigField, this.document.getPage(pagen));
/*      */       
/*  946 */       if (((PdfDictionary)acroForm.getPdfObject()).isIndirect()) {
/*  947 */         acroForm.setModified();
/*      */       }
/*      */       else {
/*      */         
/*  951 */         this.document.getCatalog().setModified();
/*      */       } 
/*      */     } 
/*      */     
/*  955 */     this.exclusionLocations = new HashMap<>();
/*      */     
/*  957 */     PdfLiteral lit = new PdfLiteral(80);
/*  958 */     this.exclusionLocations.put(PdfName.ByteRange, lit);
/*  959 */     this.cryptoDictionary.put(PdfName.ByteRange, (PdfObject)lit);
/*  960 */     for (Map.Entry<PdfName, Integer> entry : exclusionSizes.entrySet()) {
/*  961 */       PdfName key = entry.getKey();
/*  962 */       lit = new PdfLiteral(((Integer)entry.getValue()).intValue());
/*  963 */       this.exclusionLocations.put(key, lit);
/*  964 */       this.cryptoDictionary.put(key, (PdfObject)lit);
/*      */     } 
/*  966 */     if (this.certificationLevel > 0) {
/*  967 */       addDocMDP(this.cryptoDictionary);
/*      */     }
/*  969 */     if (fieldLock != null) {
/*  970 */       addFieldMDP(this.cryptoDictionary, fieldLock);
/*      */     }
/*  972 */     if (this.signatureEvent != null) {
/*  973 */       this.signatureEvent.getSignatureDictionary(this.cryptoDictionary);
/*      */     }
/*      */     
/*  976 */     if (this.certificationLevel > 0) {
/*      */       
/*  978 */       PdfDictionary docmdp = new PdfDictionary();
/*  979 */       docmdp.put(PdfName.DocMDP, this.cryptoDictionary.getPdfObject());
/*  980 */       this.document.getCatalog().put(PdfName.Perms, (PdfObject)docmdp);
/*  981 */       this.document.getCatalog().setModified();
/*      */     } 
/*  983 */     ((PdfDictionary)this.cryptoDictionary.getPdfObject()).flush(false);
/*  984 */     this.document.close();
/*      */     
/*  986 */     this.range = new long[this.exclusionLocations.size() * 2];
/*  987 */     long byteRangePosition = ((PdfLiteral)this.exclusionLocations.get(PdfName.ByteRange)).getPosition();
/*  988 */     this.exclusionLocations.remove(PdfName.ByteRange);
/*  989 */     int idx = 1;
/*  990 */     for (PdfLiteral lit1 : this.exclusionLocations.values()) {
/*  991 */       long n = lit1.getPosition();
/*  992 */       this.range[idx++] = n;
/*  993 */       this.range[idx++] = lit1.getBytesCount() + n;
/*      */     } 
/*  995 */     Arrays.sort(this.range, 1, this.range.length - 1);
/*  996 */     for (int k = 3; k < this.range.length - 2; k += 2) {
/*  997 */       this.range[k] = this.range[k] - this.range[k - 1];
/*      */     }
/*  999 */     if (this.tempFile == null) {
/* 1000 */       this.bout = this.temporaryOS.toByteArray();
/* 1001 */       this.range[this.range.length - 1] = this.bout.length - this.range[this.range.length - 2];
/* 1002 */       ByteArrayOutputStream bos = new ByteArrayOutputStream();
/* 1003 */       PdfOutputStream os = new PdfOutputStream(bos);
/* 1004 */       os.write(91);
/* 1005 */       for (int i = 0; i < this.range.length; i++) {
/* 1006 */         ((PdfOutputStream)os.writeLong(this.range[i])).write(32);
/*      */       }
/* 1008 */       os.write(93);
/* 1009 */       System.arraycopy(bos.toByteArray(), 0, this.bout, (int)byteRangePosition, bos.size());
/*      */     } else {
/*      */       try {
/* 1012 */         this.raf = FileUtil.getRandomAccessFile(this.tempFile);
/* 1013 */         long len = this.raf.length();
/* 1014 */         this.range[this.range.length - 1] = len - this.range[this.range.length - 2];
/* 1015 */         ByteArrayOutputStream bos = new ByteArrayOutputStream();
/* 1016 */         PdfOutputStream os = new PdfOutputStream(bos);
/* 1017 */         os.write(91);
/* 1018 */         for (int i = 0; i < this.range.length; i++) {
/* 1019 */           ((PdfOutputStream)os.writeLong(this.range[i])).write(32);
/*      */         }
/* 1021 */         os.write(93);
/* 1022 */         this.raf.seek(byteRangePosition);
/* 1023 */         this.raf.write(bos.toByteArray(), 0, bos.size());
/* 1024 */       } catch (IOException e) {
/*      */         try {
/* 1026 */           this.raf.close();
/* 1027 */         } catch (Exception exception) {}
/*      */         
/*      */         try {
/* 1030 */           this.tempFile.delete();
/* 1031 */         } catch (Exception exception) {}
/*      */         
/* 1033 */         throw e;
/*      */       } 
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
/*      */   protected InputStream getRangeStream() throws IOException {
/* 1047 */     RandomAccessSourceFactory fac = new RandomAccessSourceFactory();
/* 1048 */     return (InputStream)new RASInputStream(fac.createRanged(getUnderlyingSource(), this.range));
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
/*      */   protected void close(PdfDictionary update) throws IOException {
/*      */     try {
/* 1064 */       if (!this.preClosed)
/* 1065 */         throw new PdfException("Document must be preClosed."); 
/* 1066 */       ByteArrayOutputStream bous = new ByteArrayOutputStream();
/* 1067 */       PdfOutputStream os = new PdfOutputStream(bous);
/*      */       
/* 1069 */       for (PdfName key : update.keySet()) {
/* 1070 */         PdfObject obj = update.get(key);
/* 1071 */         PdfLiteral lit = this.exclusionLocations.get(key);
/* 1072 */         if (lit == null)
/* 1073 */           throw new IllegalArgumentException("The key didn't reserve space in preclose"); 
/* 1074 */         bous.reset();
/* 1075 */         os.write(obj);
/* 1076 */         if (bous.size() > lit.getBytesCount())
/* 1077 */           throw new IllegalArgumentException("The key is too big"); 
/* 1078 */         if (this.tempFile == null) {
/* 1079 */           System.arraycopy(bous.toByteArray(), 0, this.bout, (int)lit.getPosition(), bous.size()); continue;
/*      */         } 
/* 1081 */         this.raf.seek(lit.getPosition());
/* 1082 */         this.raf.write(bous.toByteArray(), 0, bous.size());
/*      */       } 
/*      */       
/* 1085 */       if (update.size() != this.exclusionLocations.size())
/* 1086 */         throw new IllegalArgumentException("The update dictionary has less keys than required"); 
/* 1087 */       if (this.tempFile == null) {
/* 1088 */         this.originalOS.write(this.bout, 0, this.bout.length);
/*      */       }
/* 1090 */       else if (this.originalOS != null) {
/* 1091 */         this.raf.seek(0L);
/* 1092 */         long length = this.raf.length();
/* 1093 */         byte[] buf = new byte[8192];
/* 1094 */         while (length > 0L) {
/* 1095 */           int r = this.raf.read(buf, 0, (int)Math.min(buf.length, length));
/* 1096 */           if (r < 0)
/* 1097 */             throw new EOFException("unexpected eof"); 
/* 1098 */           this.originalOS.write(buf, 0, r);
/* 1099 */           length -= r;
/*      */         } 
/*      */       } 
/*      */     } finally {
/*      */       
/* 1104 */       if (this.tempFile != null) {
/* 1105 */         this.raf.close();
/*      */         
/* 1107 */         if (this.originalOS != null) {
/* 1108 */           this.tempFile.delete();
/*      */         }
/*      */       } 
/*      */       
/* 1112 */       if (this.originalOS != null) {
/*      */         try {
/* 1114 */           this.originalOS.close();
/* 1115 */         } catch (Exception exception) {}
/*      */       }
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
/*      */   protected IRandomAccessSource getUnderlyingSource() throws IOException {
/* 1128 */     RandomAccessSourceFactory fac = new RandomAccessSourceFactory();
/* 1129 */     return (this.raf == null) ? fac.createSource(this.bout) : fac.createSource(this.raf);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void addDocMDP(PdfSignature crypto) {
/* 1139 */     PdfDictionary reference = new PdfDictionary();
/* 1140 */     PdfDictionary transformParams = new PdfDictionary();
/* 1141 */     transformParams.put(PdfName.P, (PdfObject)new PdfNumber(this.certificationLevel));
/* 1142 */     transformParams.put(PdfName.V, (PdfObject)new PdfName("1.2"));
/* 1143 */     transformParams.put(PdfName.Type, (PdfObject)PdfName.TransformParams);
/* 1144 */     reference.put(PdfName.TransformMethod, (PdfObject)PdfName.DocMDP);
/* 1145 */     reference.put(PdfName.Type, (PdfObject)PdfName.SigRef);
/* 1146 */     reference.put(PdfName.TransformParams, (PdfObject)transformParams);
/* 1147 */     setDigestParamToSigRefIfNeeded(reference);
/* 1148 */     reference.put(PdfName.Data, this.document.getTrailer().get(PdfName.Root));
/* 1149 */     PdfArray types = new PdfArray();
/* 1150 */     types.add((PdfObject)reference);
/* 1151 */     crypto.put(PdfName.Reference, (PdfObject)types);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void addFieldMDP(PdfSignature crypto, PdfSigFieldLock fieldLock) {
/* 1162 */     PdfDictionary reference = new PdfDictionary();
/* 1163 */     PdfDictionary transformParams = new PdfDictionary();
/* 1164 */     transformParams.putAll((PdfDictionary)fieldLock.getPdfObject());
/* 1165 */     transformParams.put(PdfName.Type, (PdfObject)PdfName.TransformParams);
/* 1166 */     transformParams.put(PdfName.V, (PdfObject)new PdfName("1.2"));
/* 1167 */     reference.put(PdfName.TransformMethod, (PdfObject)PdfName.FieldMDP);
/* 1168 */     reference.put(PdfName.Type, (PdfObject)PdfName.SigRef);
/* 1169 */     reference.put(PdfName.TransformParams, (PdfObject)transformParams);
/* 1170 */     setDigestParamToSigRefIfNeeded(reference);
/* 1171 */     reference.put(PdfName.Data, this.document.getTrailer().get(PdfName.Root));
/* 1172 */     PdfArray types = ((PdfDictionary)crypto.getPdfObject()).getAsArray(PdfName.Reference);
/* 1173 */     if (types == null) {
/* 1174 */       types = new PdfArray();
/* 1175 */       crypto.put(PdfName.Reference, (PdfObject)types);
/*      */     } 
/*      */     
/* 1178 */     types.add((PdfObject)reference);
/*      */   }
/*      */   
/*      */   protected boolean documentContainsCertificationOrApprovalSignatures() {
/* 1182 */     boolean containsCertificationOrApprovalSignature = false;
/*      */     
/* 1184 */     PdfDictionary urSignature = null;
/* 1185 */     PdfDictionary catalogPerms = ((PdfDictionary)this.document.getCatalog().getPdfObject()).getAsDictionary(PdfName.Perms);
/* 1186 */     if (catalogPerms != null) {
/* 1187 */       urSignature = catalogPerms.getAsDictionary(PdfName.UR3);
/*      */     }
/*      */     
/* 1190 */     PdfAcroForm acroForm = PdfAcroForm.getAcroForm(this.document, false);
/* 1191 */     if (acroForm != null) {
/* 1192 */       for (Map.Entry<String, PdfFormField> entry : (Iterable<Map.Entry<String, PdfFormField>>)acroForm.getFormFields().entrySet()) {
/* 1193 */         PdfDictionary fieldDict = (PdfDictionary)((PdfFormField)entry.getValue()).getPdfObject();
/* 1194 */         if (!PdfName.Sig.equals(fieldDict.get(PdfName.FT)))
/*      */           continue; 
/* 1196 */         PdfDictionary sigDict = fieldDict.getAsDictionary(PdfName.V);
/* 1197 */         if (sigDict == null)
/*      */           continue; 
/* 1199 */         PdfSignature pdfSignature = new PdfSignature(sigDict);
/* 1200 */         if (pdfSignature.getContents() == null || pdfSignature.getByteRange() == null) {
/*      */           continue;
/*      */         }
/*      */         
/* 1204 */         if (!pdfSignature.getType().equals(PdfName.DocTimeStamp) && sigDict != urSignature) {
/* 1205 */           containsCertificationOrApprovalSignature = true;
/*      */           break;
/*      */         } 
/*      */       } 
/*      */     }
/* 1210 */     return containsCertificationOrApprovalSignature;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected Rectangle getWidgetRectangle(PdfWidgetAnnotation widget) {
/* 1220 */     return widget.getRectangle().toRectangle();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected int getWidgetPageNumber(PdfWidgetAnnotation widget) {
/* 1230 */     int pageNumber = 0;
/* 1231 */     PdfDictionary pageDict = ((PdfDictionary)widget.getPdfObject()).getAsDictionary(PdfName.P);
/* 1232 */     if (pageDict != null) {
/* 1233 */       pageNumber = this.document.getPageNumber(pageDict);
/*      */     } else {
/* 1235 */       for (int i = 1; i <= this.document.getNumberOfPages(); i++) {
/* 1236 */         PdfPage page = this.document.getPage(i);
/* 1237 */         if (!page.isFlushed() && 
/* 1238 */           page.containsAnnotation((PdfAnnotation)widget)) {
/* 1239 */           pageNumber = i;
/*      */           
/*      */           break;
/*      */         } 
/*      */       } 
/*      */     } 
/* 1245 */     return pageNumber;
/*      */   }
/*      */   
/*      */   private void setDigestParamToSigRefIfNeeded(PdfDictionary reference) {
/* 1249 */     if (this.document.getPdfVersion().compareTo(PdfVersion.PDF_1_6) < 0) {
/*      */ 
/*      */ 
/*      */       
/* 1253 */       reference.put(PdfName.DigestValue, (PdfObject)new PdfString("aa"));
/* 1254 */       PdfArray loc = new PdfArray();
/* 1255 */       loc.add((PdfObject)new PdfNumber(0));
/* 1256 */       loc.add((PdfObject)new PdfNumber(0));
/* 1257 */       reference.put(PdfName.DigestLocation, (PdfObject)loc);
/* 1258 */       reference.put(PdfName.DigestMethod, (PdfObject)PdfName.MD5);
/*      */     }
/* 1260 */     else if (isDocumentPdf2()) {
/* 1261 */       if (this.digestMethod != null) {
/* 1262 */         reference.put(PdfName.DigestMethod, (PdfObject)this.digestMethod);
/*      */       } else {
/* 1264 */         Logger logger = LoggerFactory.getLogger(PdfSigner.class);
/* 1265 */         logger.error("Unknown digest method. Valid values are MD5, SHA1 SHA256, SHA384, SHA512 and RIPEMD160.");
/*      */       } 
/*      */     } 
/*      */   }
/*      */   
/*      */   private PdfName getHashAlgorithmNameInCompatibleForPdfForm(String hashAlgorithm) {
/* 1271 */     PdfName pdfCompatibleName = null;
/* 1272 */     String hashAlgOid = DigestAlgorithms.getAllowedDigest(hashAlgorithm);
/* 1273 */     if (hashAlgOid != null) {
/* 1274 */       String hashAlgorithmNameInCompatibleForPdfForm = DigestAlgorithms.getDigest(hashAlgOid);
/* 1275 */       if (hashAlgorithmNameInCompatibleForPdfForm != null) {
/* 1276 */         pdfCompatibleName = new PdfName(hashAlgorithmNameInCompatibleForPdfForm);
/*      */       }
/*      */     } 
/* 1279 */     return pdfCompatibleName;
/*      */   }
/*      */   
/*      */   private boolean isDocumentPdf2() {
/* 1283 */     return (this.document.getPdfVersion().compareTo(PdfVersion.PDF_2_0) >= 0);
/*      */   }
/*      */   
/*      */   private static StampingProperties initStampingProperties(boolean append) {
/* 1287 */     StampingProperties properties = new StampingProperties();
/* 1288 */     if (append) {
/* 1289 */       properties.useAppendMode();
/*      */     }
/* 1291 */     return properties;
/*      */   }
/*      */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/signatures/PdfSigner.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */