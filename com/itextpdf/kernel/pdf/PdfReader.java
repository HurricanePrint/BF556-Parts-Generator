/*      */ package com.itextpdf.kernel.pdf;
/*      */ 
/*      */ import com.itextpdf.io.source.ByteBuffer;
/*      */ import com.itextpdf.io.source.ByteUtils;
/*      */ import com.itextpdf.io.source.IRandomAccessSource;
/*      */ import com.itextpdf.io.source.PdfTokenizer;
/*      */ import com.itextpdf.io.source.RandomAccessFileOrArray;
/*      */ import com.itextpdf.io.source.RandomAccessSourceFactory;
/*      */ import com.itextpdf.io.source.WindowRandomAccessSource;
/*      */ import com.itextpdf.io.util.MessageFormatUtil;
/*      */ import com.itextpdf.kernel.PdfException;
/*      */ import com.itextpdf.kernel.crypto.securityhandler.UnsupportedSecurityHandlerException;
/*      */ import com.itextpdf.kernel.pdf.filters.FilterHandlers;
/*      */ import com.itextpdf.kernel.pdf.filters.IFilterHandler;
/*      */ import java.io.ByteArrayInputStream;
/*      */ import java.io.Closeable;
/*      */ import java.io.File;
/*      */ import java.io.FileNotFoundException;
/*      */ import java.io.IOException;
/*      */ import java.io.InputStream;
/*      */ import java.io.ObjectInputStream;
/*      */ import java.io.ObjectOutputStream;
/*      */ import java.io.Serializable;
/*      */ import java.util.Map;
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
/*      */ public class PdfReader
/*      */   implements Closeable, Serializable
/*      */ {
/*      */   private static final long serialVersionUID = -3584187443691964939L;
/*      */   private static final String endstream1 = "endstream";
/*      */   private static final String endstream2 = "\nendstream";
/*      */   private static final String endstream3 = "\r\nendstream";
/*      */   private static final String endstream4 = "\rendstream";
/*   81 */   private static final byte[] endstream = ByteUtils.getIsoBytes("endstream");
/*   82 */   private static final byte[] endobj = ByteUtils.getIsoBytes("endobj");
/*      */   
/*      */   protected static boolean correctStreamLength = true;
/*      */   
/*      */   private boolean unethicalReading;
/*      */   
/*      */   private boolean memorySavingMode;
/*      */   
/*      */   private PdfIndirectReference currentIndirectReference;
/*      */   
/*      */   private String sourcePath;
/*      */   
/*      */   protected PdfTokenizer tokens;
/*      */   
/*      */   protected PdfEncryption decrypt;
/*      */   
/*      */   protected PdfVersion headerPdfVersion;
/*      */   
/*      */   protected long lastXref;
/*      */   
/*      */   protected long eofPos;
/*      */   
/*      */   protected PdfDictionary trailer;
/*      */   
/*      */   protected PdfDocument pdfDocument;
/*      */   
/*      */   protected PdfAConformanceLevel pdfAConformanceLevel;
/*      */   
/*      */   protected ReaderProperties properties;
/*      */   
/*      */   protected boolean encrypted = false;
/*      */   
/*      */   protected boolean rebuiltXref = false;
/*      */   
/*      */   protected boolean hybridXref = false;
/*      */   
/*      */   protected boolean fixedXref = false;
/*      */   
/*      */   protected boolean xrefStm = false;
/*      */   
/*      */   public PdfReader(IRandomAccessSource byteSource, ReaderProperties properties) throws IOException {
/*  123 */     this.properties = properties;
/*  124 */     this.tokens = getOffsetTokeniser(byteSource);
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
/*      */   public PdfReader(InputStream is, ReaderProperties properties) throws IOException {
/*  136 */     this((new RandomAccessSourceFactory()).createSource(is), properties);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PdfReader(File file) throws FileNotFoundException, IOException {
/*  147 */     this(file.getAbsolutePath());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PdfReader(InputStream is) throws IOException {
/*  158 */     this(is, new ReaderProperties());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PdfReader(String filename, ReaderProperties properties) throws IOException {
/*  169 */     this((new RandomAccessSourceFactory())
/*      */         
/*  171 */         .setForceRead(false)
/*  172 */         .createBestSource(filename), properties);
/*      */ 
/*      */     
/*  175 */     this.sourcePath = filename;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PdfReader(String filename) throws IOException {
/*  185 */     this(filename, new ReaderProperties());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void close() throws IOException {
/*  195 */     this.tokens.close();
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
/*      */   public PdfReader setUnethicalReading(boolean unethicalReading) {
/*  207 */     this.unethicalReading = unethicalReading;
/*  208 */     return this;
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
/*      */   public PdfReader setMemorySavingMode(boolean memorySavingMode) {
/*  222 */     this.memorySavingMode = memorySavingMode;
/*  223 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isCloseStream() {
/*  233 */     return this.tokens.isCloseStream();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setCloseStream(boolean closeStream) {
/*  243 */     this.tokens.setCloseStream(closeStream);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean hasRebuiltXref() {
/*  253 */     if (this.pdfDocument == null || !this.pdfDocument.getXref().isReadingCompleted()) {
/*  254 */       throw new PdfException("The PDF document has not been read yet. Document reading occurs in PdfDocument class constructor");
/*      */     }
/*      */     
/*  257 */     return this.rebuiltXref;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean hasHybridXref() {
/*  268 */     if (this.pdfDocument == null || !this.pdfDocument.getXref().isReadingCompleted()) {
/*  269 */       throw new PdfException("The PDF document has not been read yet. Document reading occurs in PdfDocument class constructor");
/*      */     }
/*      */     
/*  272 */     return this.hybridXref;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean hasXrefStm() {
/*  282 */     if (this.pdfDocument == null || !this.pdfDocument.getXref().isReadingCompleted()) {
/*  283 */       throw new PdfException("The PDF document has not been read yet. Document reading occurs in PdfDocument class constructor");
/*      */     }
/*      */     
/*  286 */     return this.xrefStm;
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
/*      */   public boolean hasFixedXref() {
/*  298 */     if (this.pdfDocument == null || !this.pdfDocument.getXref().isReadingCompleted()) {
/*  299 */       throw new PdfException("The PDF document has not been read yet. Document reading occurs in PdfDocument class constructor");
/*      */     }
/*      */     
/*  302 */     return this.fixedXref;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public long getLastXref() {
/*  312 */     if (this.pdfDocument == null || !this.pdfDocument.getXref().isReadingCompleted()) {
/*  313 */       throw new PdfException("The PDF document has not been read yet. Document reading occurs in PdfDocument class constructor");
/*      */     }
/*      */     
/*  316 */     return this.lastXref;
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
/*      */   public byte[] readStreamBytes(PdfStream stream, boolean decode) throws IOException {
/*  329 */     byte[] b = readStreamBytesRaw(stream);
/*  330 */     if (decode && b != null) {
/*  331 */       return decodeBytes(b, stream);
/*      */     }
/*  333 */     return b;
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
/*      */   public byte[] readStreamBytesRaw(PdfStream stream) throws IOException {
/*  346 */     PdfName type = stream.getAsName(PdfName.Type);
/*  347 */     if (!PdfName.XRefStm.equals(type) && !PdfName.ObjStm.equals(type))
/*  348 */       checkPdfStreamLength(stream); 
/*  349 */     long offset = stream.getOffset();
/*  350 */     if (offset <= 0L)
/*  351 */       return null; 
/*  352 */     int length = stream.getLength();
/*  353 */     if (length <= 0)
/*  354 */       return new byte[0]; 
/*  355 */     RandomAccessFileOrArray file = this.tokens.getSafeFile();
/*  356 */     byte[] bytes = null;
/*      */     try {
/*  358 */       file.seek(stream.getOffset());
/*  359 */       bytes = new byte[length];
/*  360 */       file.readFully(bytes);
/*  361 */       if (this.decrypt != null && !this.decrypt.isEmbeddedFilesOnly()) {
/*  362 */         PdfObject filter = stream.get(PdfName.Filter, true);
/*  363 */         boolean skip = false;
/*  364 */         if (filter != null) {
/*  365 */           if (PdfName.Crypt.equals(filter)) {
/*  366 */             skip = true;
/*  367 */           } else if (filter.getType() == 1) {
/*  368 */             PdfArray filters = (PdfArray)filter;
/*  369 */             for (int k = 0; k < filters.size(); k++) {
/*  370 */               if (!filters.isEmpty() && PdfName.Crypt.equals(filters.get(k, true))) {
/*  371 */                 skip = true;
/*      */                 break;
/*      */               } 
/*      */             } 
/*      */           } 
/*  376 */           filter.release();
/*      */         } 
/*  378 */         if (!skip) {
/*  379 */           this.decrypt.setHashKeyForNextObject(stream.getIndirectReference().getObjNumber(), stream.getIndirectReference().getGenNumber());
/*  380 */           bytes = this.decrypt.decryptByteArray(bytes);
/*      */         } 
/*      */       } 
/*      */     } finally {
/*      */       try {
/*  385 */         file.close();
/*  386 */       } catch (Exception exception) {}
/*      */     } 
/*      */     
/*  389 */     return bytes;
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
/*      */   public InputStream readStream(PdfStream stream, boolean decode) throws IOException {
/*  402 */     byte[] bytes = readStreamBytes(stream, decode);
/*  403 */     return (bytes != null) ? new ByteArrayInputStream(bytes) : null;
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
/*      */   public static byte[] decodeBytes(byte[] b, PdfDictionary streamDictionary) {
/*  415 */     return decodeBytes(b, streamDictionary, FilterHandlers.getDefaultFilterHandlers());
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
/*      */   public static byte[] decodeBytes(byte[] b, PdfDictionary streamDictionary, Map<PdfName, IFilterHandler> filterHandlers) {
/*  428 */     if (b == null) {
/*  429 */       return null;
/*      */     }
/*  431 */     PdfObject filter = streamDictionary.get(PdfName.Filter);
/*  432 */     PdfArray filters = new PdfArray();
/*  433 */     if (filter != null) {
/*  434 */       if (filter.getType() == 6) {
/*  435 */         filters.add(filter);
/*  436 */       } else if (filter.getType() == 1) {
/*  437 */         filters = (PdfArray)filter;
/*      */       } 
/*      */     }
/*      */     
/*  441 */     MemoryLimitsAwareHandler memoryLimitsAwareHandler = null;
/*  442 */     if (null != streamDictionary.getIndirectReference()) {
/*  443 */       memoryLimitsAwareHandler = (streamDictionary.getIndirectReference().getDocument()).memoryLimitsAwareHandler;
/*      */     }
/*      */ 
/*      */     
/*  447 */     boolean memoryLimitsAwarenessRequired = (null != memoryLimitsAwareHandler && memoryLimitsAwareHandler.isMemoryLimitsAwarenessRequiredOnDecompression(filters));
/*      */     
/*  449 */     if (memoryLimitsAwarenessRequired) {
/*  450 */       memoryLimitsAwareHandler.beginDecompressedPdfStreamProcessing();
/*      */     }
/*      */     
/*  453 */     PdfArray dp = new PdfArray();
/*  454 */     PdfObject dpo = streamDictionary.get(PdfName.DecodeParms);
/*  455 */     if (dpo == null || (dpo.getType() != 3 && dpo.getType() != 1)) {
/*  456 */       if (dpo != null) dpo.release(); 
/*  457 */       dpo = streamDictionary.get(PdfName.DP);
/*      */     } 
/*  459 */     if (dpo != null) {
/*  460 */       if (dpo.getType() == 3) {
/*  461 */         dp.add(dpo);
/*  462 */       } else if (dpo.getType() == 1) {
/*  463 */         dp = (PdfArray)dpo;
/*      */       } 
/*  465 */       dpo.release();
/*      */     } 
/*  467 */     for (int j = 0; j < filters.size(); j++) {
/*  468 */       PdfDictionary decodeParams; PdfName filterName = (PdfName)filters.get(j);
/*  469 */       IFilterHandler filterHandler = filterHandlers.get(filterName);
/*  470 */       if (filterHandler == null) {
/*  471 */         throw (new PdfException("Filter {0} is not supported.")).setMessageParams(new Object[] { filterName });
/*      */       }
/*      */       
/*  474 */       if (j < dp.size()) {
/*  475 */         PdfObject dpEntry = dp.get(j, true);
/*  476 */         if (dpEntry == null || dpEntry.getType() == 7) {
/*  477 */           decodeParams = null;
/*  478 */         } else if (dpEntry.getType() == 3) {
/*  479 */           decodeParams = (PdfDictionary)dpEntry;
/*      */         } else {
/*  481 */           throw (new PdfException("Decode parameter type {0} is not supported.")).setMessageParams(new Object[] { dpEntry.getClass().toString() });
/*      */         } 
/*      */       } else {
/*  484 */         decodeParams = null;
/*      */       } 
/*  486 */       b = filterHandler.decode(b, filterName, decodeParams, streamDictionary);
/*  487 */       if (memoryLimitsAwarenessRequired) {
/*  488 */         memoryLimitsAwareHandler.considerBytesOccupiedByDecompressedPdfStream(b.length);
/*      */       }
/*      */     } 
/*  491 */     if (memoryLimitsAwarenessRequired) {
/*  492 */       memoryLimitsAwareHandler.endDecompressedPdfStreamProcessing();
/*      */     }
/*  494 */     return b;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public RandomAccessFileOrArray getSafeFile() {
/*  504 */     return this.tokens.getSafeFile();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public long getFileLength() throws IOException {
/*  514 */     return this.tokens.getSafeFile().length();
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
/*      */   public boolean isOpenedWithFullPermission() {
/*  527 */     if (this.pdfDocument == null || !this.pdfDocument.getXref().isReadingCompleted()) {
/*  528 */       throw new PdfException("The PDF document has not been read yet. Document reading occurs in PdfDocument class constructor");
/*      */     }
/*      */     
/*  531 */     return (!this.encrypted || this.decrypt.isOpenedWithFullPermission() || this.unethicalReading);
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
/*      */   public long getPermissions() {
/*  549 */     if (this.pdfDocument == null || !this.pdfDocument.getXref().isReadingCompleted()) {
/*  550 */       throw new PdfException("The PDF document has not been read yet. Document reading occurs in PdfDocument class constructor");
/*      */     }
/*      */     
/*  553 */     long perm = 0L;
/*  554 */     if (this.encrypted && this.decrypt.getPermissions() != null) {
/*  555 */       perm = this.decrypt.getPermissions().longValue();
/*      */     }
/*  557 */     return perm;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getCryptoMode() {
/*  568 */     if (this.pdfDocument == null || !this.pdfDocument.getXref().isReadingCompleted()) {
/*  569 */       throw new PdfException("The PDF document has not been read yet. Document reading occurs in PdfDocument class constructor");
/*      */     }
/*      */     
/*  572 */     if (this.decrypt == null) {
/*  573 */       return -1;
/*      */     }
/*  575 */     return this.decrypt.getCryptoMode();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PdfAConformanceLevel getPdfAConformanceLevel() {
/*  586 */     return this.pdfAConformanceLevel;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public byte[] computeUserPassword() {
/*  596 */     if (this.pdfDocument == null || !this.pdfDocument.getXref().isReadingCompleted()) {
/*  597 */       throw new PdfException("The PDF document has not been read yet. Document reading occurs in PdfDocument class constructor");
/*      */     }
/*      */     
/*  600 */     if (!this.encrypted || !this.decrypt.isOpenedWithFullPermission()) {
/*  601 */       return null;
/*      */     }
/*      */     
/*  604 */     return this.decrypt.computeUserPassword(this.properties.password);
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
/*      */   public byte[] getOriginalFileId() {
/*  619 */     if (this.pdfDocument == null || !this.pdfDocument.getXref().isReadingCompleted()) {
/*  620 */       throw new PdfException("The PDF document has not been read yet. Document reading occurs in PdfDocument class constructor");
/*      */     }
/*      */     
/*  623 */     PdfArray id = this.trailer.getAsArray(PdfName.ID);
/*  624 */     if (id != null && id.size() == 2) {
/*  625 */       return ByteUtils.getIsoBytes(id.getAsString(0).getValue());
/*      */     }
/*  627 */     return new byte[0];
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
/*      */   public byte[] getModifiedFileId() {
/*  643 */     if (this.pdfDocument == null || !this.pdfDocument.getXref().isReadingCompleted()) {
/*  644 */       throw new PdfException("The PDF document has not been read yet. Document reading occurs in PdfDocument class constructor");
/*      */     }
/*      */     
/*  647 */     PdfArray id = this.trailer.getAsArray(PdfName.ID);
/*  648 */     if (id != null && id.size() == 2) {
/*  649 */       return ByteUtils.getIsoBytes(id.getAsString(1).getValue());
/*      */     }
/*  651 */     return new byte[0];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isEncrypted() {
/*  662 */     if (this.pdfDocument == null || !this.pdfDocument.getXref().isReadingCompleted()) {
/*  663 */       throw new PdfException("The PDF document has not been read yet. Document reading occurs in PdfDocument class constructor");
/*      */     }
/*      */     
/*  666 */     return this.encrypted;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void readPdf() throws IOException {
/*  675 */     String version = this.tokens.checkPdfHeader();
/*      */     try {
/*  677 */       this.headerPdfVersion = PdfVersion.fromString(version);
/*  678 */     } catch (IllegalArgumentException exc) {
/*  679 */       throw new PdfException("PDF version is not valid.", version);
/*      */     } 
/*      */     try {
/*  682 */       readXref();
/*  683 */     } catch (RuntimeException ex) {
/*  684 */       Logger logger = LoggerFactory.getLogger(PdfReader.class);
/*  685 */       logger.error("Error occurred while reading cross reference table. Cross reference table will be rebuilt.", ex);
/*      */       
/*  687 */       rebuildXref();
/*      */     } 
/*  689 */     this.pdfDocument.getXref().markReadingCompleted();
/*  690 */     readDecryptObj();
/*      */   }
/*      */   
/*      */   protected void readObjectStream(PdfStream objectStream) throws IOException {
/*  694 */     int objectStreamNumber = objectStream.getIndirectReference().getObjNumber();
/*  695 */     int first = objectStream.getAsNumber(PdfName.First).intValue();
/*  696 */     int n = objectStream.getAsNumber(PdfName.N).intValue();
/*  697 */     byte[] bytes = readStreamBytes(objectStream, true);
/*  698 */     PdfTokenizer saveTokens = this.tokens;
/*      */     try {
/*  700 */       this.tokens = new PdfTokenizer(new RandomAccessFileOrArray((new RandomAccessSourceFactory()).createSource(bytes)));
/*  701 */       int[] address = new int[n];
/*  702 */       int[] objNumber = new int[n];
/*  703 */       boolean ok = true; int k;
/*  704 */       for (k = 0; k < n; k++) {
/*  705 */         ok = this.tokens.nextToken();
/*  706 */         if (!ok)
/*      */           break; 
/*  708 */         if (this.tokens.getTokenType() != PdfTokenizer.TokenType.Number) {
/*  709 */           ok = false;
/*      */           break;
/*      */         } 
/*  712 */         objNumber[k] = this.tokens.getIntValue();
/*  713 */         ok = this.tokens.nextToken();
/*  714 */         if (!ok)
/*      */           break; 
/*  716 */         if (this.tokens.getTokenType() != PdfTokenizer.TokenType.Number) {
/*  717 */           ok = false;
/*      */           break;
/*      */         } 
/*  720 */         address[k] = this.tokens.getIntValue() + first;
/*      */       } 
/*  722 */       if (!ok)
/*  723 */         throw new PdfException("Error while reading Object Stream."); 
/*  724 */       for (k = 0; k < n; k++) {
/*  725 */         this.tokens.seek(address[k]);
/*  726 */         this.tokens.nextToken();
/*      */         
/*  728 */         PdfIndirectReference reference = this.pdfDocument.getXref().get(objNumber[k]);
/*  729 */         if (reference.refersTo == null && reference.getObjStreamNumber() == objectStreamNumber) {
/*      */           PdfObject obj;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*  739 */           if (this.tokens.getTokenType() == PdfTokenizer.TokenType.Number) {
/*      */ 
/*      */             
/*  742 */             obj = new PdfNumber(this.tokens.getByteContent());
/*      */           } else {
/*  744 */             this.tokens.seek(address[k]);
/*  745 */             obj = readObject(false, true);
/*      */           } 
/*  747 */           reference.setRefersTo(obj);
/*  748 */           obj.setIndirectReference(reference);
/*      */         } 
/*  750 */       }  objectStream.getIndirectReference().setState((short)16);
/*      */     } finally {
/*  752 */       this.tokens = saveTokens;
/*      */     } 
/*      */   }
/*      */   
/*      */   protected PdfObject readObject(PdfIndirectReference reference) {
/*  757 */     return readObject(reference, true);
/*      */   }
/*      */   
/*      */   protected PdfObject readObject(boolean readAsDirect) throws IOException {
/*  761 */     return readObject(readAsDirect, false);
/*      */   }
/*      */   
/*      */   protected PdfObject readReference(boolean readAsDirect) {
/*  765 */     int num = this.tokens.getObjNr();
/*  766 */     if (num < 0) {
/*  767 */       return createPdfNullInstance(readAsDirect);
/*      */     }
/*  769 */     PdfXrefTable table = this.pdfDocument.getXref();
/*  770 */     PdfIndirectReference reference = table.get(num);
/*  771 */     if (reference != null) {
/*  772 */       if (reference.isFree()) {
/*  773 */         Logger logger = LoggerFactory.getLogger(PdfReader.class);
/*  774 */         logger.warn(MessageFormatUtil.format("Invalid indirect reference {0} {1} R", new Object[] { Integer.valueOf(this.tokens.getObjNr()), Integer.valueOf(this.tokens.getGenNr()) }));
/*  775 */         return createPdfNullInstance(readAsDirect);
/*      */       } 
/*  777 */       if (reference.getGenNumber() != this.tokens.getGenNr()) {
/*  778 */         if (this.fixedXref) {
/*  779 */           Logger logger = LoggerFactory.getLogger(PdfReader.class);
/*  780 */           logger.warn(MessageFormatUtil.format("Invalid indirect reference {0} {1} R", new Object[] { Integer.valueOf(this.tokens.getObjNr()), Integer.valueOf(this.tokens.getGenNr()) }));
/*  781 */           return createPdfNullInstance(readAsDirect);
/*      */         } 
/*  783 */         throw new PdfException("Invalid indirect reference {0}.", 
/*  784 */             MessageFormatUtil.format("{0} {1} R", new Object[] { Integer.valueOf(reference.getObjNumber()), Integer.valueOf(reference.getGenNumber()) }));
/*      */       } 
/*      */     } else {
/*      */       
/*  788 */       if (table.isReadingCompleted()) {
/*  789 */         Logger logger = LoggerFactory.getLogger(PdfReader.class);
/*  790 */         logger.warn(MessageFormatUtil.format("Invalid indirect reference {0} {1} R", new Object[] { Integer.valueOf(this.tokens.getObjNr()), Integer.valueOf(this.tokens.getGenNr()) }));
/*  791 */         return createPdfNullInstance(readAsDirect);
/*      */       } 
/*  793 */       reference = table.add((PdfIndirectReference)(new PdfIndirectReference(this.pdfDocument, num, this.tokens
/*  794 */             .getGenNr(), 0L)).setState((short)4));
/*      */     } 
/*      */     
/*  797 */     return reference; } protected PdfObject readObject(boolean readAsDirect, boolean objStm) throws IOException { PdfDictionary dict;
/*      */     PdfString pdfString;
/*      */     long pos;
/*      */     boolean hasNext;
/*  801 */     this.tokens.nextValidToken();
/*  802 */     PdfTokenizer.TokenType type = this.tokens.getTokenType();
/*  803 */     switch (type) {
/*      */       case StartDic:
/*  805 */         dict = readDictionary(objStm);
/*  806 */         pos = this.tokens.getPosition();
/*      */ 
/*      */         
/*      */         do {
/*  810 */           hasNext = this.tokens.nextToken();
/*  811 */         } while (hasNext && this.tokens.getTokenType() == PdfTokenizer.TokenType.Comment);
/*      */         
/*  813 */         if (hasNext && this.tokens.tokenValueEqualsTo(PdfTokenizer.Stream))
/*      */         {
/*      */           while (true) {
/*      */             
/*  817 */             int ch = this.tokens.read();
/*  818 */             if (ch != 32 && ch != 9 && ch != 0 && ch != 12) {
/*  819 */               if (ch != 10)
/*  820 */                 ch = this.tokens.read(); 
/*  821 */               if (ch != 10)
/*  822 */                 this.tokens.backOnePosition(ch); 
/*  823 */               return new PdfStream(this.tokens.getPosition(), dict);
/*      */             } 
/*  825 */           }  }  this.tokens.seek(pos);
/*  826 */         return dict;
/*      */ 
/*      */       
/*      */       case StartArray:
/*  830 */         return readArray(objStm);
/*      */       case Number:
/*  832 */         return new PdfNumber(this.tokens.getByteContent());
/*      */       case String:
/*  834 */         pdfString = new PdfString(this.tokens.getByteContent(), this.tokens.isHexString());
/*  835 */         if (this.encrypted && !this.decrypt.isEmbeddedFilesOnly() && !objStm) {
/*  836 */           pdfString.setDecryption(this.currentIndirectReference.getObjNumber(), this.currentIndirectReference.getGenNumber(), this.decrypt);
/*      */         }
/*  838 */         return pdfString;
/*      */       
/*      */       case Name:
/*  841 */         return readPdfName(readAsDirect);
/*      */       case Ref:
/*  843 */         return readReference(readAsDirect);
/*      */       case EndOfFile:
/*  845 */         throw new PdfException("Unexpected end of file.");
/*      */     } 
/*  847 */     if (this.tokens.tokenValueEqualsTo(PdfTokenizer.Null))
/*  848 */       return createPdfNullInstance(readAsDirect); 
/*  849 */     if (this.tokens.tokenValueEqualsTo(PdfTokenizer.True)) {
/*  850 */       if (readAsDirect) {
/*  851 */         return PdfBoolean.TRUE;
/*      */       }
/*  853 */       return new PdfBoolean(true);
/*      */     } 
/*  855 */     if (this.tokens.tokenValueEqualsTo(PdfTokenizer.False)) {
/*  856 */       if (readAsDirect) {
/*  857 */         return PdfBoolean.FALSE;
/*      */       }
/*  859 */       return new PdfBoolean(false);
/*      */     } 
/*      */     
/*  862 */     return null; }
/*      */ 
/*      */ 
/*      */   
/*      */   protected PdfName readPdfName(boolean readAsDirect) {
/*  867 */     if (readAsDirect) {
/*  868 */       PdfName cachedName = PdfName.staticNames.get(this.tokens.getStringValue());
/*  869 */       if (cachedName != null) {
/*  870 */         return cachedName;
/*      */       }
/*      */     } 
/*  873 */     return new PdfName(this.tokens.getByteContent());
/*      */   }
/*      */   
/*      */   protected PdfDictionary readDictionary(boolean objStm) throws IOException {
/*  877 */     PdfDictionary dic = new PdfDictionary();
/*      */     while (true) {
/*  879 */       this.tokens.nextValidToken();
/*  880 */       if (this.tokens.getTokenType() == PdfTokenizer.TokenType.EndDic)
/*      */         break; 
/*  882 */       if (this.tokens.getTokenType() != PdfTokenizer.TokenType.Name)
/*  883 */         this.tokens.throwError("Dictionary key {0} is not a name.", new Object[] { this.tokens.getStringValue() }); 
/*  884 */       PdfName name = readPdfName(true);
/*  885 */       PdfObject obj = readObject(true, objStm);
/*  886 */       if (obj == null) {
/*  887 */         if (this.tokens.getTokenType() == PdfTokenizer.TokenType.EndDic)
/*  888 */           this.tokens.throwError("unexpected >>.", new Object[0]); 
/*  889 */         if (this.tokens.getTokenType() == PdfTokenizer.TokenType.EndArray)
/*  890 */           this.tokens.throwError("Unexpected close bracket.", new Object[0]); 
/*      */       } 
/*  892 */       dic.put(name, obj);
/*      */     } 
/*  894 */     return dic;
/*      */   }
/*      */   
/*      */   protected PdfArray readArray(boolean objStm) throws IOException {
/*  898 */     PdfArray array = new PdfArray();
/*      */     while (true) {
/*  900 */       PdfObject obj = readObject(true, objStm);
/*  901 */       if (obj == null) {
/*  902 */         if (this.tokens.getTokenType() == PdfTokenizer.TokenType.EndArray)
/*      */           break; 
/*  904 */         if (this.tokens.getTokenType() == PdfTokenizer.TokenType.EndDic)
/*  905 */           this.tokens.throwError("unexpected >>.", new Object[0]); 
/*      */       } 
/*  907 */       array.add(obj);
/*      */     } 
/*  909 */     return array;
/*      */   }
/*      */   
/*      */   protected void readXref() throws IOException {
/*  913 */     this.tokens.seek(this.tokens.getStartxref());
/*  914 */     this.tokens.nextToken();
/*  915 */     if (!this.tokens.tokenValueEqualsTo(PdfTokenizer.Startxref))
/*  916 */       throw new PdfException("PDF startxref not found.", this.tokens); 
/*  917 */     this.tokens.nextToken();
/*  918 */     if (this.tokens.getTokenType() != PdfTokenizer.TokenType.Number)
/*  919 */       throw new PdfException("PDF startxref is not followed by a number.", this.tokens); 
/*  920 */     long startxref = this.tokens.getLongValue();
/*  921 */     this.lastXref = startxref;
/*  922 */     this.eofPos = this.tokens.getPosition();
/*      */     try {
/*  924 */       if (readXrefStream(startxref)) {
/*  925 */         this.xrefStm = true;
/*      */         return;
/*      */       } 
/*  928 */     } catch (Exception exception) {}
/*      */ 
/*      */     
/*  931 */     this.pdfDocument.getXref().clear();
/*      */     
/*  933 */     this.tokens.seek(startxref);
/*  934 */     this.trailer = readXrefSection();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  940 */     PdfDictionary trailer2 = this.trailer;
/*      */     while (true) {
/*  942 */       PdfNumber prev = (PdfNumber)trailer2.get(PdfName.Prev);
/*  943 */       if (prev == null)
/*      */         break; 
/*  945 */       if (prev.longValue() == startxref)
/*  946 */         throw new PdfException("Trailer prev entry points to its own cross reference section."); 
/*  947 */       startxref = prev.longValue();
/*  948 */       this.tokens.seek(startxref);
/*  949 */       trailer2 = readXrefSection();
/*      */     } 
/*      */     
/*  952 */     Integer xrefSize = this.trailer.getAsInt(PdfName.Size);
/*  953 */     if (xrefSize == null) {
/*  954 */       throw new PdfException("Invalid xref table.");
/*      */     }
/*      */   }
/*      */   
/*      */   protected PdfDictionary readXrefSection() throws IOException {
/*  959 */     this.tokens.nextValidToken();
/*  960 */     if (!this.tokens.tokenValueEqualsTo(PdfTokenizer.Xref))
/*  961 */       this.tokens.throwError("xref subsection not found.", new Object[0]); 
/*  962 */     PdfXrefTable xref = this.pdfDocument.getXref();
/*      */     while (true) {
/*  964 */       this.tokens.nextValidToken();
/*  965 */       if (this.tokens.tokenValueEqualsTo(PdfTokenizer.Trailer)) {
/*      */         break;
/*      */       }
/*  968 */       if (this.tokens.getTokenType() != PdfTokenizer.TokenType.Number) {
/*  969 */         this.tokens.throwError("Object number of the first object in this xref subsection not found.", new Object[0]);
/*      */       }
/*  971 */       int start = this.tokens.getIntValue();
/*  972 */       this.tokens.nextValidToken();
/*  973 */       if (this.tokens.getTokenType() != PdfTokenizer.TokenType.Number) {
/*  974 */         this.tokens.throwError("Number of entries in this xref subsection not found.", new Object[0]);
/*      */       }
/*  976 */       int end = this.tokens.getIntValue() + start;
/*  977 */       for (int num = start; num < end; num++) {
/*  978 */         this.tokens.nextValidToken();
/*  979 */         long pos = this.tokens.getLongValue();
/*  980 */         this.tokens.nextValidToken();
/*  981 */         int gen = this.tokens.getIntValue();
/*  982 */         this.tokens.nextValidToken();
/*  983 */         if (pos == 0L && gen == 65535 && num == 1 && start != 0) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*  995 */           num = 0;
/*  996 */           end--;
/*      */           continue;
/*      */         } 
/*  999 */         PdfIndirectReference reference = xref.get(num);
/* 1000 */         boolean refReadingState = (reference != null && reference.checkState((short)4) && reference.getGenNumber() == gen);
/*      */ 
/*      */         
/* 1003 */         boolean refFirstEncountered = (reference == null || (!refReadingState && reference.getDocument() == null));
/*      */         
/* 1005 */         if (refFirstEncountered) {
/* 1006 */           reference = new PdfIndirectReference(this.pdfDocument, num, gen, pos);
/* 1007 */         } else if (refReadingState) {
/* 1008 */           reference.setOffset(pos);
/* 1009 */           reference.clearState((short)4);
/*      */         } else {
/*      */           continue;
/*      */         } 
/*      */         
/* 1014 */         if (this.tokens.tokenValueEqualsTo(PdfTokenizer.N)) {
/* 1015 */           if (pos == 0L) {
/* 1016 */             this.tokens.throwError("file position {0} cross reference entry in this xref subsection.", new Object[0]);
/*      */           }
/* 1018 */         } else if (this.tokens.tokenValueEqualsTo(PdfTokenizer.F)) {
/* 1019 */           if (refFirstEncountered) {
/* 1020 */             reference.setState((short)2);
/*      */           }
/*      */         } else {
/* 1023 */           this.tokens.throwError("Invalid cross reference entry in this xref subsection.", new Object[0]);
/*      */         } 
/*      */         
/* 1026 */         if (refFirstEncountered)
/* 1027 */           xref.add(reference); 
/*      */         continue;
/*      */       } 
/*      */     } 
/* 1031 */     PdfDictionary trailer = (PdfDictionary)readObject(false);
/* 1032 */     PdfObject xrs = trailer.get(PdfName.XRefStm);
/* 1033 */     if (xrs != null && xrs.getType() == 8) {
/* 1034 */       int loc = ((PdfNumber)xrs).intValue();
/*      */       try {
/* 1036 */         readXrefStream(loc);
/* 1037 */         this.xrefStm = true;
/* 1038 */         this.hybridXref = true;
/* 1039 */       } catch (IOException e) {
/* 1040 */         xref.clear();
/* 1041 */         throw e;
/*      */       } 
/*      */     } 
/* 1044 */     return trailer;
/*      */   }
/*      */   
/*      */   protected boolean readXrefStream(long ptr) throws IOException {
/* 1048 */     while (ptr != -1L) {
/* 1049 */       PdfStream xrefStream; PdfArray index; this.tokens.seek(ptr);
/* 1050 */       if (!this.tokens.nextToken()) {
/* 1051 */         return false;
/*      */       }
/* 1053 */       if (this.tokens.getTokenType() != PdfTokenizer.TokenType.Number) {
/* 1054 */         return false;
/*      */       }
/* 1056 */       if (!this.tokens.nextToken() || this.tokens.getTokenType() != PdfTokenizer.TokenType.Number) {
/* 1057 */         return false;
/*      */       }
/* 1059 */       if (!this.tokens.nextToken() || !this.tokens.tokenValueEqualsTo(PdfTokenizer.Obj)) {
/* 1060 */         return false;
/*      */       }
/* 1062 */       PdfXrefTable xref = this.pdfDocument.getXref();
/* 1063 */       PdfObject object = readObject(false);
/*      */       
/* 1065 */       if (object.getType() == 9) {
/* 1066 */         xrefStream = (PdfStream)object;
/* 1067 */         if (!PdfName.XRef.equals(xrefStream.get(PdfName.Type))) {
/* 1068 */           return false;
/*      */         }
/*      */       } else {
/* 1071 */         return false;
/*      */       } 
/* 1073 */       if (this.trailer == null) {
/* 1074 */         this.trailer = new PdfDictionary();
/* 1075 */         this.trailer.putAll(xrefStream);
/* 1076 */         this.trailer.remove(PdfName.DecodeParms);
/* 1077 */         this.trailer.remove(PdfName.Filter);
/* 1078 */         this.trailer.remove(PdfName.Prev);
/* 1079 */         this.trailer.remove(PdfName.Length);
/*      */       } 
/*      */       
/* 1082 */       int size = ((PdfNumber)xrefStream.get(PdfName.Size)).intValue();
/*      */       
/* 1084 */       PdfObject obj = xrefStream.get(PdfName.Index);
/* 1085 */       if (obj == null) {
/* 1086 */         index = new PdfArray();
/* 1087 */         index.add(new PdfNumber(0));
/* 1088 */         index.add(new PdfNumber(size));
/*      */       } else {
/* 1090 */         index = (PdfArray)obj;
/*      */       } 
/* 1092 */       PdfArray w = xrefStream.getAsArray(PdfName.W);
/* 1093 */       long prev = -1L;
/* 1094 */       obj = xrefStream.get(PdfName.Prev);
/* 1095 */       if (obj != null)
/* 1096 */         prev = ((PdfNumber)obj).longValue(); 
/* 1097 */       xref.setCapacity(size);
/* 1098 */       byte[] b = readStreamBytes(xrefStream, true);
/* 1099 */       int bptr = 0;
/* 1100 */       int[] wc = new int[3];
/* 1101 */       for (int k = 0; k < 3; k++) {
/* 1102 */         wc[k] = w.getAsNumber(k).intValue();
/*      */       }
/* 1104 */       for (int idx = 0; idx < index.size(); idx += 2) {
/* 1105 */         int start = index.getAsNumber(idx).intValue();
/* 1106 */         int length = index.getAsNumber(idx + 1).intValue();
/* 1107 */         xref.setCapacity(start + length);
/* 1108 */         while (length-- > 0) {
/* 1109 */           PdfIndirectReference newReference; int type = 1;
/* 1110 */           if (wc[0] > 0) {
/* 1111 */             type = 0;
/* 1112 */             for (int m = 0; m < wc[0]; m++) {
/* 1113 */               type = (type << 8) + (b[bptr++] & 0xFF);
/*      */             }
/*      */           } 
/* 1116 */           long field2 = 0L;
/* 1117 */           for (int i = 0; i < wc[1]; i++) {
/* 1118 */             field2 = (field2 << 8L) + (b[bptr++] & 0xFF);
/*      */           }
/* 1120 */           int field3 = 0;
/* 1121 */           for (int j = 0; j < wc[2]; j++) {
/* 1122 */             field3 = (field3 << 8) + (b[bptr++] & 0xFF);
/*      */           }
/* 1124 */           int base = start;
/*      */           
/* 1126 */           switch (type) {
/*      */             case 0:
/* 1128 */               newReference = (PdfIndirectReference)(new PdfIndirectReference(this.pdfDocument, base, field3, field2)).setState((short)2);
/*      */               break;
/*      */             case 1:
/* 1131 */               newReference = new PdfIndirectReference(this.pdfDocument, base, field3, field2);
/*      */               break;
/*      */             case 2:
/* 1134 */               newReference = new PdfIndirectReference(this.pdfDocument, base, 0, field3);
/* 1135 */               newReference.setObjStreamNumber((int)field2);
/*      */               break;
/*      */             default:
/* 1138 */               throw new PdfException("Invalid xref stream.");
/*      */           } 
/*      */           
/* 1141 */           PdfIndirectReference reference = xref.get(base);
/* 1142 */           boolean refReadingState = (reference != null && reference.checkState((short)4) && reference.getGenNumber() == newReference.getGenNumber());
/*      */ 
/*      */           
/* 1145 */           boolean refFirstEncountered = (reference == null || (!refReadingState && reference.getDocument() == null));
/*      */           
/* 1147 */           if (refFirstEncountered) {
/* 1148 */             xref.add(newReference);
/* 1149 */           } else if (refReadingState) {
/* 1150 */             reference.setOffset(newReference.getOffset());
/* 1151 */             reference.setObjStreamNumber(newReference.getObjStreamNumber());
/* 1152 */             reference.clearState((short)4);
/*      */           } 
/* 1154 */           start++;
/*      */         } 
/*      */       } 
/* 1157 */       ptr = prev;
/*      */     } 
/* 1159 */     return true;
/*      */   }
/*      */   
/*      */   protected void fixXref() throws IOException {
/* 1163 */     this.fixedXref = true;
/* 1164 */     PdfXrefTable xref = this.pdfDocument.getXref();
/* 1165 */     this.tokens.seek(0L);
/* 1166 */     ByteBuffer buffer = new ByteBuffer(24);
/* 1167 */     PdfTokenizer lineTokeniser = new PdfTokenizer(new RandomAccessFileOrArray(new ReusableRandomAccessSource(buffer)));
/*      */     while (true) {
/* 1169 */       long pos = this.tokens.getPosition();
/* 1170 */       buffer.reset();
/*      */ 
/*      */       
/* 1173 */       if (!this.tokens.readLineSegment(buffer, true))
/*      */         break; 
/* 1175 */       if (buffer.get(0) >= 48 && buffer.get(0) <= 57) {
/* 1176 */         int[] obj = PdfTokenizer.checkObjectStart(lineTokeniser);
/* 1177 */         if (obj == null)
/*      */           continue; 
/* 1179 */         int num = obj[0];
/* 1180 */         int gen = obj[1];
/* 1181 */         PdfIndirectReference reference = xref.get(num);
/* 1182 */         if (reference != null && reference.getGenNumber() == gen) {
/* 1183 */           reference.fixOffset(pos);
/*      */         }
/*      */       } 
/*      */     } 
/*      */   }
/*      */   
/*      */   protected void rebuildXref() throws IOException {
/* 1190 */     this.xrefStm = false;
/* 1191 */     this.hybridXref = false;
/* 1192 */     this.rebuiltXref = true;
/* 1193 */     PdfXrefTable xref = this.pdfDocument.getXref();
/* 1194 */     xref.clear();
/* 1195 */     this.tokens.seek(0L);
/* 1196 */     this.trailer = null;
/* 1197 */     ByteBuffer buffer = new ByteBuffer(24);
/* 1198 */     PdfTokenizer lineTokeniser = new PdfTokenizer(new RandomAccessFileOrArray(new ReusableRandomAccessSource(buffer)));
/*      */     while (true) {
/* 1200 */       long pos = this.tokens.getPosition();
/* 1201 */       buffer.reset();
/*      */ 
/*      */       
/* 1204 */       if (!this.tokens.readLineSegment(buffer, true))
/*      */         break; 
/* 1206 */       if (buffer.get(0) == 116) {
/* 1207 */         if (!PdfTokenizer.checkTrailer(buffer))
/*      */           continue; 
/* 1209 */         this.tokens.seek(pos);
/* 1210 */         this.tokens.nextToken();
/* 1211 */         pos = this.tokens.getPosition();
/*      */         try {
/* 1213 */           PdfDictionary dic = (PdfDictionary)readObject(false);
/* 1214 */           if (dic.get(PdfName.Root, false) != null) {
/* 1215 */             this.trailer = dic; continue;
/*      */           } 
/* 1217 */           this.tokens.seek(pos);
/* 1218 */         } catch (Exception e) {
/* 1219 */           this.tokens.seek(pos);
/*      */         }  continue;
/* 1221 */       }  if (buffer.get(0) >= 48 && buffer.get(0) <= 57) {
/* 1222 */         int[] obj = PdfTokenizer.checkObjectStart(lineTokeniser);
/* 1223 */         if (obj == null)
/*      */           continue; 
/* 1225 */         int num = obj[0];
/* 1226 */         int gen = obj[1];
/* 1227 */         if (xref.get(num) == null || xref.get(num).getGenNumber() <= gen) {
/* 1228 */           xref.add(new PdfIndirectReference(this.pdfDocument, num, gen, pos));
/*      */         }
/*      */       } 
/*      */     } 
/* 1232 */     if (this.trailer == null)
/* 1233 */       throw new PdfException("Trailer not found."); 
/*      */   }
/*      */   
/*      */   boolean isMemorySavingMode() {
/* 1237 */     return this.memorySavingMode;
/*      */   }
/*      */   
/*      */   private void readDecryptObj() {
/* 1241 */     if (this.encrypted)
/*      */       return; 
/* 1243 */     PdfDictionary enc = this.trailer.getAsDictionary(PdfName.Encrypt);
/* 1244 */     if (enc == null)
/*      */       return; 
/* 1246 */     this.encrypted = true;
/*      */     
/* 1248 */     PdfName filter = enc.getAsName(PdfName.Filter);
/* 1249 */     if (PdfName.Adobe_PubSec.equals(filter)) {
/* 1250 */       if (this.properties.certificate == null) {
/* 1251 */         throw new PdfException("Certificate is not provided. Document is encrypted with public key certificate, it should be passed to PdfReader constructor with properties. See ReaderProperties#setPublicKeySecurityParams() method.");
/*      */       }
/* 1253 */       this.decrypt = new PdfEncryption(enc, this.properties.certificateKey, this.properties.certificate, this.properties.certificateKeyProvider, this.properties.externalDecryptionProcess);
/*      */     }
/* 1255 */     else if (PdfName.Standard.equals(filter)) {
/* 1256 */       this.decrypt = new PdfEncryption(enc, this.properties.password, getOriginalFileId());
/*      */     } else {
/* 1258 */       throw new UnsupportedSecurityHandlerException(MessageFormatUtil.format("Failed to open the document. Security handler {0} is not supported", new Object[] { filter }));
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
/*      */   private static PdfTokenizer getOffsetTokeniser(IRandomAccessSource byteSource) throws IOException {
/* 1271 */     PdfTokenizer tok = new PdfTokenizer(new RandomAccessFileOrArray(byteSource));
/* 1272 */     int offset = tok.getHeaderOffset();
/* 1273 */     if (offset != 0) {
/* 1274 */       WindowRandomAccessSource windowRandomAccessSource = new WindowRandomAccessSource(byteSource, offset);
/* 1275 */       tok = new PdfTokenizer(new RandomAccessFileOrArray((IRandomAccessSource)windowRandomAccessSource));
/*      */     } 
/* 1277 */     return tok;
/*      */   }
/*      */   
/*      */   private PdfObject readObject(PdfIndirectReference reference, boolean fixXref) {
/* 1281 */     if (reference == null)
/* 1282 */       return null; 
/* 1283 */     if (reference.refersTo != null)
/* 1284 */       return reference.refersTo; 
/*      */     try {
/* 1286 */       this.currentIndirectReference = reference;
/* 1287 */       if (reference.getObjStreamNumber() > 0) {
/*      */         
/* 1289 */         PdfStream objectStream = (PdfStream)this.pdfDocument.getXref().get(reference.getObjStreamNumber()).getRefersTo(false);
/* 1290 */         readObjectStream(objectStream);
/* 1291 */         return reference.refersTo;
/* 1292 */       }  if (reference.getOffset() > 0L) {
/*      */         PdfObject object;
/*      */         try {
/* 1295 */           this.tokens.seek(reference.getOffset());
/* 1296 */           this.tokens.nextValidToken();
/* 1297 */           if (this.tokens.getTokenType() != PdfTokenizer.TokenType.Obj || this.tokens
/* 1298 */             .getObjNr() != reference.getObjNumber() || this.tokens
/* 1299 */             .getGenNr() != reference.getGenNumber()) {
/* 1300 */             this.tokens.throwError("Invalid offset for object {0}.", new Object[] { reference.toString() });
/*      */           }
/* 1302 */           object = readObject(false);
/* 1303 */         } catch (RuntimeException ex) {
/* 1304 */           if (fixXref && reference.getObjStreamNumber() == 0) {
/* 1305 */             fixXref();
/* 1306 */             object = readObject(reference, false);
/*      */           } else {
/* 1308 */             throw ex;
/*      */           } 
/*      */         } 
/* 1311 */         return (object != null) ? object.setIndirectReference(reference) : null;
/*      */       } 
/* 1313 */       return null;
/*      */     }
/* 1315 */     catch (IOException e) {
/* 1316 */       PdfObject object; throw new PdfException("Cannot read PdfObject.", object);
/*      */     } 
/*      */   }
/*      */   
/*      */   private void checkPdfStreamLength(PdfStream pdfStream) throws IOException {
/* 1321 */     if (!correctStreamLength)
/*      */       return; 
/* 1323 */     long fileLength = this.tokens.length();
/* 1324 */     long start = pdfStream.getOffset();
/* 1325 */     boolean calc = false;
/* 1326 */     int streamLength = 0;
/* 1327 */     PdfNumber pdfNumber = pdfStream.getAsNumber(PdfName.Length);
/* 1328 */     if (pdfNumber != null) {
/* 1329 */       streamLength = pdfNumber.intValue();
/* 1330 */       if (streamLength + start > fileLength - 20L) {
/* 1331 */         calc = true;
/*      */       } else {
/* 1333 */         this.tokens.seek(start + streamLength);
/* 1334 */         String line = this.tokens.readString(20);
/* 1335 */         if (!line.startsWith("\nendstream") && !line.startsWith("\r\nendstream") && 
/* 1336 */           !line.startsWith("\rendstream") && !line.startsWith("endstream")) {
/* 1337 */           calc = true;
/*      */         }
/*      */       } 
/*      */     } else {
/* 1341 */       pdfNumber = new PdfNumber(0);
/* 1342 */       pdfStream.put(PdfName.Length, pdfNumber);
/* 1343 */       calc = true;
/*      */     } 
/* 1345 */     if (calc) {
/* 1346 */       long pos; ByteBuffer line = new ByteBuffer(16);
/* 1347 */       this.tokens.seek(start);
/*      */       
/*      */       while (true) {
/* 1350 */         pos = this.tokens.getPosition();
/* 1351 */         line.reset();
/*      */ 
/*      */         
/* 1354 */         if (!this.tokens.readLineSegment(line, false))
/*      */           break; 
/* 1356 */         if (line.startsWith(endstream)) {
/* 1357 */           streamLength = (int)(pos - start); break;
/*      */         } 
/* 1359 */         if (line.startsWith(endobj)) {
/* 1360 */           this.tokens.seek(pos - 16L);
/* 1361 */           String s = this.tokens.readString(16);
/* 1362 */           int index = s.indexOf("endstream");
/* 1363 */           if (index >= 0)
/* 1364 */             pos = pos - 16L + index; 
/* 1365 */           streamLength = (int)(pos - start);
/*      */           break;
/*      */         } 
/*      */       } 
/* 1369 */       this.tokens.seek(pos - 2L);
/* 1370 */       if (this.tokens.read() == 13) {
/* 1371 */         streamLength--;
/*      */       }
/* 1373 */       this.tokens.seek(pos - 1L);
/* 1374 */       if (this.tokens.read() == 10) {
/* 1375 */         streamLength--;
/*      */       }
/* 1377 */       pdfNumber.setValue(streamLength);
/* 1378 */       pdfStream.updateLength(streamLength);
/*      */     } 
/*      */   }
/*      */   
/*      */   private PdfObject createPdfNullInstance(boolean readAsDirect) {
/* 1383 */     if (readAsDirect) {
/* 1384 */       return PdfNull.PDF_NULL;
/*      */     }
/* 1386 */     return new PdfNull();
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
/*      */   private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
/* 1398 */     in.defaultReadObject();
/* 1399 */     if (this.sourcePath != null && this.tokens == null) {
/* 1400 */       this.tokens = getOffsetTokeniser((new RandomAccessSourceFactory()).setForceRead(false).createBestSource(this.sourcePath));
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void writeObject(ObjectOutputStream out) throws IOException {
/* 1411 */     if (this.sourcePath != null) {
/* 1412 */       PdfTokenizer tempTokens = this.tokens;
/* 1413 */       this.tokens = null;
/* 1414 */       out.defaultWriteObject();
/* 1415 */       this.tokens = tempTokens;
/*      */     } else {
/* 1417 */       out.defaultWriteObject();
/*      */     } 
/*      */   }
/*      */   
/*      */   protected static class ReusableRandomAccessSource implements IRandomAccessSource {
/*      */     private ByteBuffer buffer;
/*      */     
/*      */     public ReusableRandomAccessSource(ByteBuffer buffer) {
/* 1425 */       if (buffer == null) throw new IllegalArgumentException("Passed byte buffer can not be null."); 
/* 1426 */       this.buffer = buffer;
/*      */     }
/*      */ 
/*      */     
/*      */     public int get(long offset) {
/* 1431 */       if (offset >= this.buffer.size()) return -1; 
/* 1432 */       return 0xFF & this.buffer.getInternalBuffer()[(int)offset];
/*      */     }
/*      */ 
/*      */     
/*      */     public int get(long offset, byte[] bytes, int off, int len) {
/* 1437 */       if (this.buffer == null) throw new IllegalStateException("Already closed");
/*      */       
/* 1439 */       if (offset >= this.buffer.size()) {
/* 1440 */         return -1;
/*      */       }
/* 1442 */       if (offset + len > this.buffer.size()) {
/* 1443 */         len = (int)(this.buffer.size() - offset);
/*      */       }
/* 1445 */       System.arraycopy(this.buffer.getInternalBuffer(), (int)offset, bytes, off, len);
/*      */       
/* 1447 */       return len;
/*      */     }
/*      */ 
/*      */     
/*      */     public long length() {
/* 1452 */       return this.buffer.size();
/*      */     }
/*      */ 
/*      */     
/*      */     public void close() throws IOException {
/* 1457 */       this.buffer = null;
/*      */     }
/*      */   }
/*      */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/kernel/pdf/PdfReader.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */