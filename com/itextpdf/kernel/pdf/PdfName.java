/*      */ package com.itextpdf.kernel.pdf;
/*      */ 
/*      */ import com.itextpdf.io.source.ByteBuffer;
/*      */ import com.itextpdf.io.source.ByteUtils;
/*      */ import java.nio.charset.StandardCharsets;
/*      */ import java.util.Map;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class PdfName
/*      */   extends PdfPrimitiveObject
/*      */   implements Comparable<PdfName>
/*      */ {
/*      */   private static final long serialVersionUID = 7493154668111961953L;
/*   58 */   private static final byte[] space = ByteUtils.getIsoBytes("#20");
/*      */   
/*   60 */   private static final byte[] percent = ByteUtils.getIsoBytes("#25");
/*      */   
/*   62 */   private static final byte[] leftParenthesis = ByteUtils.getIsoBytes("#28");
/*      */   
/*   64 */   private static final byte[] rightParenthesis = ByteUtils.getIsoBytes("#29");
/*      */   
/*   66 */   private static final byte[] lessThan = ByteUtils.getIsoBytes("#3c");
/*      */   
/*   68 */   private static final byte[] greaterThan = ByteUtils.getIsoBytes("#3e");
/*      */   
/*   70 */   private static final byte[] leftSquare = ByteUtils.getIsoBytes("#5b");
/*      */   
/*   72 */   private static final byte[] rightSquare = ByteUtils.getIsoBytes("#5d");
/*      */   
/*   74 */   private static final byte[] leftCurlyBracket = ByteUtils.getIsoBytes("#7b");
/*      */   
/*   76 */   private static final byte[] rightCurlyBracket = ByteUtils.getIsoBytes("#7d");
/*      */   
/*   78 */   private static final byte[] solidus = ByteUtils.getIsoBytes("#2f");
/*      */   
/*   80 */   private static final byte[] numberSign = ByteUtils.getIsoBytes("#23");
/*      */   
/*   82 */   public static final PdfName _3D = createDirectName("3D");
/*   83 */   public static final PdfName _3DA = createDirectName("3DA");
/*   84 */   public static final PdfName _3DB = createDirectName("3DB");
/*   85 */   public static final PdfName _3DCrossSection = createDirectName("3DCrossSection");
/*   86 */   public static final PdfName _3DD = createDirectName("3DD");
/*   87 */   public static final PdfName _3DI = createDirectName("3DI");
/*   88 */   public static final PdfName _3DV = createDirectName("3DV");
/*   89 */   public static final PdfName _3DView = createDirectName("3DView");
/*   90 */   public static final PdfName a = createDirectName("a");
/*   91 */   public static final PdfName A = createDirectName("A");
/*   92 */   public static final PdfName A85 = createDirectName("A85");
/*   93 */   public static final PdfName AA = createDirectName("AA");
/*   94 */   public static final PdfName AbsoluteColorimetric = createDirectName("AbsoluteColorimetric");
/*   95 */   public static final PdfName AcroForm = createDirectName("AcroForm");
/*   96 */   public static final PdfName Action = createDirectName("Action");
/*   97 */   public static final PdfName ActualText = createDirectName("ActualText");
/*   98 */   public static final PdfName ADBE = createDirectName("ADBE");
/*   99 */   public static final PdfName Adbe_pkcs7_detached = createDirectName("adbe.pkcs7.detached");
/*  100 */   public static final PdfName Adbe_pkcs7_s4 = createDirectName("adbe.pkcs7.s4");
/*  101 */   public static final PdfName Adbe_pkcs7_s5 = createDirectName("adbe.pkcs7.s5");
/*  102 */   public static final PdfName Adbe_pkcs7_sha1 = createDirectName("adbe.pkcs7.sha1");
/*  103 */   public static final PdfName Adbe_x509_rsa_sha1 = createDirectName("adbe.x509.rsa_sha1");
/*  104 */   public static final PdfName Adobe_PPKLite = createDirectName("Adobe.PPKLite");
/*  105 */   public static final PdfName Adobe_PPKMS = createDirectName("Adobe.PPKMS");
/*  106 */   public static final PdfName Adobe_PubSec = createDirectName("Adobe.PubSec");
/*  107 */   public static final PdfName AESV2 = createDirectName("AESV2");
/*  108 */   public static final PdfName AESV3 = createDirectName("AESV3");
/*  109 */   public static final PdfName AF = createDirectName("AF");
/*  110 */   public static final PdfName AFRelationship = createDirectName("AFRelationship");
/*  111 */   public static final PdfName After = createDirectName("After");
/*  112 */   public static final PdfName AHx = createDirectName("AHx");
/*  113 */   public static final PdfName AIS = createDirectName("AIS");
/*  114 */   public static final PdfName Alaw = createDirectName("ALaw");
/*  115 */   public static final PdfName All = createDirectName("All");
/*  116 */   public static final PdfName AllOff = createDirectName("AllOff");
/*  117 */   public static final PdfName AllOn = createDirectName("AllOn");
/*  118 */   public static final PdfName Alt = createDirectName("Alt");
/*  119 */   public static final PdfName Alternate = createDirectName("Alternate");
/*  120 */   public static final PdfName Alternates = createDirectName("Alternates");
/*  121 */   public static final PdfName AlternatePresentations = createDirectName("AlternatePresentations");
/*  122 */   public static final PdfName Alternative = createDirectName("Alternative");
/*  123 */   public static final PdfName AN = createDirectName("AN");
/*  124 */   public static final PdfName And = createDirectName("And");
/*  125 */   public static final PdfName Annot = createDirectName("Annot");
/*  126 */   public static final PdfName Annots = createDirectName("Annots");
/*  127 */   public static final PdfName Annotation = createDirectName("Annotation");
/*  128 */   public static final PdfName AnnotStates = createDirectName("AnnotStates");
/*  129 */   public static final PdfName AnyOff = createDirectName("AnyOff");
/*  130 */   public static final PdfName AnyOn = createDirectName("AnyOn");
/*  131 */   public static final PdfName AP = createDirectName("AP");
/*  132 */   public static final PdfName App = createDirectName("App");
/*  133 */   public static final PdfName AppDefault = createDirectName("AppDefault");
/*  134 */   public static final PdfName ApplicationOctetStream = createDirectName("application/octet-stream");
/*  135 */   public static final PdfName ApplicationPdf = createDirectName("application/pdf");
/*  136 */   public static final PdfName ApplicationXml = createDirectName("application/xml");
/*  137 */   public static final PdfName Approved = createDirectName("Approved");
/*  138 */   public static final PdfName Art = createDirectName("Art");
/*  139 */   public static final PdfName ArtBox = createDirectName("ArtBox");
/*  140 */   public static final PdfName Artifact = createDirectName("Artifact");
/*  141 */   public static final PdfName AS = createDirectName("AS");
/*  142 */   public static final PdfName Ascent = createDirectName("Ascent");
/*  143 */   public static final PdfName ASCII85Decode = createDirectName("ASCII85Decode");
/*  144 */   public static final PdfName ASCIIHexDecode = createDirectName("ASCIIHexDecode");
/*  145 */   public static final PdfName Aside = createDirectName("Aside");
/*  146 */   public static final PdfName AsIs = createDirectName("AsIs");
/*  147 */   public static final PdfName AuthEvent = createDirectName("AuthEvent");
/*  148 */   public static final PdfName Author = createDirectName("Author");
/*  149 */   public static final PdfName B = createDirectName("B");
/*  150 */   public static final PdfName BackgroundColor = createDirectName("BackgroundColor");
/*  151 */   public static final PdfName BaseFont = createDirectName("BaseFont");
/*  152 */   public static final PdfName BaseEncoding = createDirectName("BaseEncoding");
/*  153 */   public static final PdfName BaselineShift = createDirectName("BaselineShift");
/*  154 */   public static final PdfName BaseState = createDirectName("BaseState");
/*  155 */   public static final PdfName BaseVersion = createDirectName("BaseVersion");
/*  156 */   public static final PdfName Bates = createDirectName("Bates");
/*  157 */   public static final PdfName BBox = createDirectName("BBox");
/*  158 */   public static final PdfName BE = createDirectName("BE");
/*  159 */   public static final PdfName Before = createDirectName("Before");
/*  160 */   public static final PdfName BC = createDirectName("BC");
/*  161 */   public static final PdfName BG = createDirectName("BG");
/*  162 */   public static final PdfName BG2 = createDirectName("BG2");
/*  163 */   public static final PdfName BibEntry = createDirectName("BibEntry");
/*  164 */   public static final PdfName BitsPerComponent = createDirectName("BitsPerComponent");
/*  165 */   public static final PdfName BitsPerCoordinate = createDirectName("BitsPerCoordinate");
/*  166 */   public static final PdfName BitsPerFlag = createDirectName("BitsPerFlag");
/*  167 */   public static final PdfName BitsPerSample = createDirectName("BitsPerSample");
/*  168 */   public static final PdfName Bl = createDirectName("Bl");
/*  169 */   public static final PdfName BlackIs1 = createDirectName("BlackIs1");
/*  170 */   public static final PdfName BlackPoint = createDirectName("BlackPoint");
/*  171 */   public static final PdfName BleedBox = createDirectName("BleedBox");
/*  172 */   public static final PdfName Block = createDirectName("Block");
/*  173 */   public static final PdfName BlockAlign = createDirectName("BlockAlign");
/*  174 */   public static final PdfName BlockQuote = createDirectName("BlockQuote");
/*  175 */   public static final PdfName BM = createDirectName("BM");
/*  176 */   public static final PdfName Book = createDirectName("Book");
/*  177 */   public static final PdfName Border = createDirectName("Border");
/*  178 */   public static final PdfName BorderColor = createDirectName("BorderColor");
/*  179 */   public static final PdfName BorderStyle = createDirectName("BorderStyle");
/*  180 */   public static final PdfName BorderThickness = createDirectName("BorderThickness");
/*  181 */   public static final PdfName Both = createDirectName("Both");
/*  182 */   public static final PdfName Bounds = createDirectName("Bounds");
/*  183 */   public static final PdfName BS = createDirectName("BS");
/*  184 */   public static final PdfName Btn = createDirectName("Btn");
/*  185 */   public static final PdfName Butt = createDirectName("Butt");
/*  186 */   public static final PdfName ByteRange = createDirectName("ByteRange");
/*  187 */   public static final PdfName C = createDirectName("C");
/*  188 */   public static final PdfName C0 = createDirectName("C0");
/*  189 */   public static final PdfName C1 = createDirectName("C1");
/*  190 */   public static final PdfName CA = createDirectName("CA");
/*  191 */   public static final PdfName ca = createDirectName("ca");
/*  192 */   public static final PdfName CalGray = createDirectName("CalGray");
/*  193 */   public static final PdfName CalRGB = createDirectName("CalRGB");
/*  194 */   public static final PdfName CapHeight = createDirectName("CapHeight");
/*  195 */   public static final PdfName Cap = createDirectName("Cap");
/*  196 */   public static final PdfName Caption = createDirectName("Caption");
/*  197 */   public static final PdfName Caret = createDirectName("Caret");
/*  198 */   public static final PdfName Catalog = createDirectName("Catalog");
/*  199 */   public static final PdfName Category = createDirectName("Category");
/*  200 */   public static final PdfName CCITTFaxDecode = createDirectName("CCITTFaxDecode");
/*  201 */   public static final PdfName Center = createDirectName("Center");
/*  202 */   public static final PdfName CenterWindow = createDirectName("CenterWindow");
/*  203 */   public static final PdfName Cert = createDirectName("Cert");
/*  204 */   public static final PdfName Certs = createDirectName("Certs");
/*  205 */   public static final PdfName CF = createDirectName("CF");
/*  206 */   public static final PdfName CFM = createDirectName("CFM");
/*  207 */   public static final PdfName Ch = createDirectName("Ch");
/*  208 */   public static final PdfName CI = createDirectName("CI");
/*  209 */   public static final PdfName CIDFontType0 = createDirectName("CIDFontType0");
/*  210 */   public static final PdfName CIDFontType2 = createDirectName("CIDFontType2");
/*  211 */   public static final PdfName CIDSet = createDirectName("CIDSet");
/*  212 */   public static final PdfName CIDSystemInfo = createDirectName("CIDSystemInfo");
/*  213 */   public static final PdfName CIDToGIDMap = createDirectName("CIDToGIDMap");
/*  214 */   public static final PdfName Circle = createDirectName("Circle");
/*  215 */   public static final PdfName CL = createDirectName("CL");
/*  216 */   public static final PdfName ClosedArrow = createDirectName("ClosedArrow");
/*  217 */   public static final PdfName CMapName = createDirectName("CMapName");
/*  218 */   public static final PdfName CO = createDirectName("CO");
/*  219 */   public static final PdfName Code = createDirectName("Code");
/*  220 */   public static final PdfName Collection = createDirectName("Collection");
/*  221 */   public static final PdfName ColSpan = createDirectName("ColSpan");
/*  222 */   public static final PdfName ColumnCount = createDirectName("ColumnCount");
/*  223 */   public static final PdfName ColumnGap = createDirectName("ColumnGap");
/*  224 */   public static final PdfName ColumnWidths = createDirectName("ColumnWidths");
/*  225 */   public static final PdfName ContactInfo = createDirectName("ContactInfo");
/*  226 */   public static final PdfName CharProcs = createDirectName("CharProcs");
/*  227 */   public static final PdfName Color = createDirectName("Color");
/*  228 */   public static final PdfName ColorBurn = createDirectName("ColorBurn");
/*  229 */   public static final PdfName ColorDodge = createDirectName("ColorDodge");
/*  230 */   public static final PdfName Colorants = createDirectName("Colorants");
/*  231 */   public static final PdfName Colors = createDirectName("Colors");
/*  232 */   public static final PdfName ColorSpace = createDirectName("ColorSpace");
/*  233 */   public static final PdfName ColorTransform = createDirectName("ColorTransform");
/*  234 */   public static final PdfName Column = createDirectName("Column");
/*  235 */   public static final PdfName Columns = createDirectName("Columns");
/*  236 */   public static final PdfName Compatible = createDirectName("Compatible");
/*  237 */   public static final PdfName Confidential = createDirectName("Confidential");
/*  238 */   public static final PdfName Configs = createDirectName("Configs");
/*  239 */   public static final PdfName Contents = createDirectName("Contents");
/*  240 */   public static final PdfName Coords = createDirectName("Coords");
/*  241 */   public static final PdfName Count = createDirectName("Count");
/*  242 */   public static final PdfName CP = createDirectName("CP");
/*  243 */   public static final PdfName CRL = createDirectName("CRL");
/*  244 */   public static final PdfName CRLs = createDirectName("CRLs");
/*  245 */   public static final PdfName CreationDate = createDirectName("CreationDate");
/*  246 */   public static final PdfName Creator = createDirectName("Creator");
/*  247 */   public static final PdfName CreatorInfo = createDirectName("CreatorInfo");
/*  248 */   public static final PdfName CropBox = createDirectName("CropBox");
/*  249 */   public static final PdfName Crypt = createDirectName("Crypt");
/*  250 */   public static final PdfName CS = createDirectName("CS");
/*  251 */   public static final PdfName CT = createDirectName("CT");
/*  252 */   public static final PdfName D = createDirectName("D");
/*  253 */   public static final PdfName DA = createDirectName("DA");
/*  254 */   public static final PdfName Darken = createDirectName("Darken");
/*  255 */   public static final PdfName Dashed = createDirectName("Dashed");
/*  256 */   public static final PdfName Data = createDirectName("Data");
/*  257 */   public static final PdfName DCTDecode = createDirectName("DCTDecode");
/*  258 */   public static final PdfName Decimal = createDirectName("Decimal");
/*  259 */   public static final PdfName Decode = createDirectName("Decode");
/*  260 */   public static final PdfName DecodeParms = createDirectName("DecodeParms");
/*  261 */   public static final PdfName Default = createDirectName("Default");
/*  262 */   public static final PdfName DefaultCMYK = createDirectName("DefaultCMYK");
/*  263 */   public static final PdfName DefaultCryptFilter = createDirectName("DefaultCryptFilter");
/*  264 */   public static final PdfName DefaultGray = createDirectName("DefaultGray");
/*  265 */   public static final PdfName DefaultRGB = createDirectName("DefaultRGB");
/*  266 */   public static final PdfName Departmental = createDirectName("Departmental");
/*  267 */   public static final PdfName DescendantFonts = createDirectName("DescendantFonts");
/*  268 */   public static final PdfName Desc = createDirectName("Desc");
/*  269 */   public static final PdfName Descent = createDirectName("Descent");
/*  270 */   public static final PdfName Design = createDirectName("Design");
/*  271 */   public static final PdfName Dest = createDirectName("Dest");
/*  272 */   public static final PdfName DestOutputProfile = createDirectName("DestOutputProfile");
/*  273 */   public static final PdfName Dests = createDirectName("Dests");
/*  274 */   public static final PdfName DeviceCMY = createDirectName("DeviceCMY");
/*  275 */   public static final PdfName DeviceCMYK = createDirectName("DeviceCMYK");
/*  276 */   public static final PdfName DeviceGray = createDirectName("DeviceGray");
/*  277 */   public static final PdfName DeviceN = createDirectName("DeviceN");
/*  278 */   public static final PdfName DeviceRGB = createDirectName("DeviceRGB");
/*  279 */   public static final PdfName DeviceRGBK = createDirectName("DeviceRGBK");
/*  280 */   public static final PdfName Diamond = createDirectName("Diamond");
/*  281 */   public static final PdfName Difference = createDirectName("Difference");
/*  282 */   public static final PdfName Differences = createDirectName("Differences");
/*  283 */   public static final PdfName Div = createDirectName("Div");
/*  284 */   public static final PdfName DigestLocation = createDirectName("DigestLocation");
/*  285 */   public static final PdfName DigestMethod = createDirectName("DigestMethod");
/*  286 */   public static final PdfName DigestValue = createDirectName("DigestValue");
/*  287 */   public static final PdfName Direction = createDirectName("Direction");
/*  288 */   public static final PdfName Disc = createDirectName("Disc");
/*  289 */   public static final PdfName DisplayDocTitle = createDirectName("DisplayDocTitle");
/*  290 */   public static final PdfName DocMDP = createDirectName("DocMDP");
/*  291 */   public static final PdfName DocOpen = createDirectName("DocOpen");
/*  292 */   public static final PdfName DocTimeStamp = createDirectName("DocTimeStamp");
/*  293 */   public static final PdfName Document = createDirectName("Document");
/*  294 */   public static final PdfName DocumentFragment = createDirectName("DocumentFragment");
/*  295 */   public static final PdfName Domain = createDirectName("Domain");
/*  296 */   public static final PdfName Dotted = createDirectName("Dotted");
/*  297 */   public static final PdfName Double = createDirectName("Double");
/*  298 */   public static final PdfName DP = createDirectName("DP");
/*  299 */   public static final PdfName Dp = createDirectName("Dp");
/*  300 */   public static final PdfName DPart = createDirectName("DPart");
/*  301 */   public static final PdfName DR = createDirectName("DR");
/*  302 */   public static final PdfName Draft = createDirectName("Draft");
/*  303 */   public static final PdfName DS = createDirectName("DS");
/*  304 */   public static final PdfName DSS = createDirectName("DSS");
/*  305 */   public static final PdfName Duplex = createDirectName("Duplex");
/*  306 */   public static final PdfName DuplexFlipShortEdge = createDirectName("DuplexFlipShortEdge");
/*  307 */   public static final PdfName DuplexFlipLongEdge = createDirectName("DuplexFlipLongEdge");
/*  308 */   public static final PdfName DV = createDirectName("DV");
/*  309 */   public static final PdfName DW = createDirectName("DW");
/*  310 */   public static final PdfName E = createDirectName("E");
/*  311 */   public static final PdfName EF = createDirectName("EF");
/*  312 */   public static final PdfName EFF = createDirectName("EFF");
/*  313 */   public static final PdfName EFOpen = createDirectName("EFOpen");
/*  314 */   public static final PdfName Em = createDirectName("Em");
/*  315 */   public static final PdfName EmbeddedFile = createDirectName("EmbeddedFile");
/*  316 */   public static final PdfName EmbeddedFiles = createDirectName("EmbeddedFiles");
/*  317 */   public static final PdfName Encode = createDirectName("Encode");
/*  318 */   public static final PdfName EncodedByteAlign = createDirectName("EncodedByteAlign");
/*  319 */   public static final PdfName Encoding = createDirectName("Encoding");
/*  320 */   public static final PdfName Encrypt = createDirectName("Encrypt");
/*  321 */   public static final PdfName EncryptMetadata = createDirectName("EncryptMetadata");
/*  322 */   public static final PdfName EncryptedPayload = createDirectName("EncryptedPayload");
/*  323 */   public static final PdfName End = createDirectName("End");
/*  324 */   public static final PdfName EndIndent = createDirectName("EndIndent");
/*  325 */   public static final PdfName EndOfBlock = createDirectName("EndOfBlock");
/*  326 */   public static final PdfName EndOfLine = createDirectName("EndOfLine");
/*  327 */   public static final PdfName Enforce = createDirectName("Enforce");
/*  328 */   public static final PdfName EP = createDirectName("EP");
/*  329 */   public static final PdfName ESIC = createDirectName("ESIC");
/*  330 */   public static final PdfName ETSI_CAdES_DETACHED = createDirectName("ETSI.CAdES.detached");
/*  331 */   public static final PdfName ETSI_RFC3161 = createDirectName("ETSI.RFC3161");
/*  332 */   public static final PdfName Event = createDirectName("Event");
/*  333 */   public static final PdfName Exclude = createDirectName("Exclude");
/*  334 */   public static final PdfName Exclusion = createDirectName("Exclusion");
/*  335 */   public static final PdfName ExData = createDirectName("ExData");
/*  336 */   public static final PdfName Experimental = createDirectName("Experimental");
/*  337 */   public static final PdfName Expired = createDirectName("Expired");
/*  338 */   public static final PdfName Export = createDirectName("Export");
/*  339 */   public static final PdfName ExportState = createDirectName("ExportState");
/*  340 */   public static final PdfName Extend = createDirectName("Extend");
/*  341 */   public static final PdfName Extends = createDirectName("Extends");
/*  342 */   public static final PdfName Extensions = createDirectName("Extensions");
/*  343 */   public static final PdfName ExtensionLevel = createDirectName("ExtensionLevel");
/*  344 */   public static final PdfName ExtGState = createDirectName("ExtGState");
/*  345 */   public static final PdfName F = createDirectName("F");
/*  346 */   public static final PdfName False = createDirectName("false");
/*  347 */   public static final PdfName Ff = createDirectName("Ff");
/*  348 */   public static final PdfName FieldMDP = createDirectName("FieldMDP");
/*  349 */   public static final PdfName Fields = createDirectName("Fields");
/*  350 */   public static final PdfName Figure = createDirectName("Figure");
/*  351 */   public static final PdfName FileAttachment = createDirectName("FileAttachment");
/*  352 */   public static final PdfName Filespec = createDirectName("Filespec");
/*  353 */   public static final PdfName Filter = createDirectName("Filter");
/*  354 */   public static final PdfName FFilter = createDirectName("FFilter");
/*  355 */   public static final PdfName FDecodeParams = createDirectName("FDecodeParams");
/*  356 */   public static final PdfName FENote = createDirectName("FENote");
/*  357 */   public static final PdfName Final = createDirectName("Final");
/*  358 */   public static final PdfName First = createDirectName("First");
/*  359 */   public static final PdfName FirstChar = createDirectName("FirstChar");
/*  360 */   public static final PdfName FirstPage = createDirectName("FirstPage");
/*  361 */   public static final PdfName Fit = createDirectName("Fit");
/*  362 */   public static final PdfName FitB = createDirectName("FitB");
/*  363 */   public static final PdfName FitBH = createDirectName("FitBH");
/*  364 */   public static final PdfName FitBV = createDirectName("FitBV");
/*  365 */   public static final PdfName FitH = createDirectName("FitH");
/*  366 */   public static final PdfName FitR = createDirectName("FitR");
/*  367 */   public static final PdfName FitV = createDirectName("FitV");
/*  368 */   public static final PdfName FitWindow = createDirectName("FitWindow");
/*  369 */   public static final PdfName FixedPrint = createDirectName("FixedPrint");
/*      */ 
/*      */ 
/*      */   
/*  373 */   public static final PdfName Fl = createDirectName("Fl");
/*      */ 
/*      */ 
/*      */   
/*  377 */   public static final PdfName FL = createDirectName("FL");
/*  378 */   public static final PdfName Flags = createDirectName("Flags");
/*  379 */   public static final PdfName FlateDecode = createDirectName("FlateDecode");
/*  380 */   public static final PdfName Fo = createDirectName("Fo");
/*  381 */   public static final PdfName Font = createDirectName("Font");
/*  382 */   public static final PdfName FontBBox = createDirectName("FontBBox");
/*  383 */   public static final PdfName FontDescriptor = createDirectName("FontDescriptor");
/*  384 */   public static final PdfName FontFamily = createDirectName("FontFamily");
/*  385 */   public static final PdfName FontFauxing = createDirectName("FontFauxing");
/*  386 */   public static final PdfName FontFile = createDirectName("FontFile");
/*  387 */   public static final PdfName FontFile2 = createDirectName("FontFile2");
/*  388 */   public static final PdfName FontFile3 = createDirectName("FontFile3");
/*  389 */   public static final PdfName FontMatrix = createDirectName("FontMatrix");
/*  390 */   public static final PdfName FontName = createDirectName("FontName");
/*  391 */   public static final PdfName FontWeight = createDirectName("FontWeight");
/*  392 */   public static final PdfName FontStretch = createDirectName("FontStretch");
/*  393 */   public static final PdfName Footer = createDirectName("Footer");
/*  394 */   public static final PdfName ForComment = createDirectName("ForComment");
/*  395 */   public static final PdfName Form = createDirectName("Form");
/*  396 */   public static final PdfName FormData = createDirectName("FormData");
/*  397 */   public static final PdfName ForPublicRelease = createDirectName("ForPublicRelease");
/*  398 */   public static final PdfName FormType = createDirectName("FormType");
/*  399 */   public static final PdfName FreeText = createDirectName("FreeText");
/*  400 */   public static final PdfName FreeTextCallout = createDirectName("FreeTextCallout");
/*  401 */   public static final PdfName FreeTextTypeWriter = createDirectName("FreeTextTypeWriter");
/*  402 */   public static final PdfName FS = createDirectName("FS");
/*  403 */   public static final PdfName Formula = createDirectName("Formula");
/*  404 */   public static final PdfName FT = createDirectName("FT");
/*  405 */   public static final PdfName FullScreen = createDirectName("FullScreen");
/*  406 */   public static final PdfName Function = createDirectName("Function");
/*  407 */   public static final PdfName Functions = createDirectName("Functions");
/*  408 */   public static final PdfName FunctionType = createDirectName("FunctionType");
/*  409 */   public static final PdfName Gamma = createDirectName("Gamma");
/*  410 */   public static final PdfName GlyphOrientationVertical = createDirectName("GlyphOrientationVertical");
/*  411 */   public static final PdfName GoTo = createDirectName("GoTo");
/*  412 */   public static final PdfName GoTo3DView = createDirectName("GoTo3DView");
/*  413 */   public static final PdfName GoToDp = createDirectName("GoToDp");
/*  414 */   public static final PdfName GoToE = createDirectName("GoToE");
/*  415 */   public static final PdfName GoToR = createDirectName("GoToR");
/*  416 */   public static final PdfName Graph = createDirectName("Graph");
/*  417 */   public static final PdfName Group = createDirectName("Group");
/*  418 */   public static final PdfName Groove = createDirectName("Groove");
/*  419 */   public static final PdfName GTS_PDFA1 = createDirectName("GTS_PDFA1");
/*  420 */   public static final PdfName H = createDirectName("H");
/*  421 */   public static final PdfName H1 = createDirectName("H1");
/*  422 */   public static final PdfName H2 = createDirectName("H2");
/*  423 */   public static final PdfName H3 = createDirectName("H3");
/*  424 */   public static final PdfName H4 = createDirectName("H4");
/*  425 */   public static final PdfName H5 = createDirectName("H5");
/*  426 */   public static final PdfName H6 = createDirectName("H6");
/*  427 */   public static final PdfName HalftoneType = createDirectName("HalftoneType");
/*  428 */   public static final PdfName HalftoneName = createDirectName("HalftoneName");
/*  429 */   public static final PdfName HardLight = createDirectName("HardLight");
/*  430 */   public static final PdfName Header = createDirectName("Header");
/*  431 */   public static final PdfName Headers = createDirectName("Headers");
/*  432 */   public static final PdfName Height = createDirectName("Height");
/*  433 */   public static final PdfName Hide = createDirectName("Hide");
/*  434 */   public static final PdfName Hidden = createDirectName("Hidden");
/*  435 */   public static final PdfName HideMenubar = createDirectName("HideMenubar");
/*  436 */   public static final PdfName HideToolbar = createDirectName("HideToolbar");
/*  437 */   public static final PdfName HideWindowUI = createDirectName("HideWindowUI");
/*  438 */   public static final PdfName Highlight = createDirectName("Highlight");
/*  439 */   public static final PdfName HT = createDirectName("HT");
/*  440 */   public static final PdfName HTO = createDirectName("HTO");
/*  441 */   public static final PdfName HTP = createDirectName("HTP");
/*  442 */   public static final PdfName Hue = createDirectName("Hue");
/*  443 */   public static final PdfName I = createDirectName("I");
/*  444 */   public static final PdfName IC = createDirectName("IC");
/*  445 */   public static final PdfName ICCBased = createDirectName("ICCBased");
/*  446 */   public static final PdfName ID = createDirectName("ID");
/*  447 */   public static final PdfName IDS = createDirectName("IDS");
/*  448 */   public static final PdfName Identity = createDirectName("Identity");
/*  449 */   public static final PdfName IdentityH = createDirectName("Identity-H");
/*  450 */   public static final PdfName Inset = createDirectName("Inset");
/*  451 */   public static final PdfName Image = createDirectName("Image");
/*  452 */   public static final PdfName ImageMask = createDirectName("ImageMask");
/*  453 */   public static final PdfName ImportData = createDirectName("ImportData");
/*  454 */   public static final PdfName ipa = createDirectName("ipa");
/*  455 */   public static final PdfName Include = createDirectName("Include");
/*  456 */   public static final PdfName Index = createDirectName("Index");
/*  457 */   public static final PdfName Indexed = createDirectName("Indexed");
/*  458 */   public static final PdfName Info = createDirectName("Info");
/*  459 */   public static final PdfName Inline = createDirectName("Inline");
/*  460 */   public static final PdfName InlineAlign = createDirectName("InlineAlign");
/*  461 */   public static final PdfName Ink = createDirectName("Ink");
/*  462 */   public static final PdfName InkList = createDirectName("InkList");
/*  463 */   public static final PdfName Intent = createDirectName("Intent");
/*  464 */   public static final PdfName Interpolate = createDirectName("Interpolate");
/*  465 */   public static final PdfName IRT = createDirectName("IRT");
/*  466 */   public static final PdfName IsMap = createDirectName("IsMap");
/*  467 */   public static final PdfName ItalicAngle = createDirectName("ItalicAngle");
/*  468 */   public static final PdfName IT = createDirectName("IT");
/*  469 */   public static final PdfName JavaScript = createDirectName("JavaScript");
/*  470 */   public static final PdfName JBIG2Decode = createDirectName("JBIG2Decode");
/*  471 */   public static final PdfName JBIG2Globals = createDirectName("JBIG2Globals");
/*  472 */   public static final PdfName JPXDecode = createDirectName("JPXDecode");
/*  473 */   public static final PdfName JS = createDirectName("JS");
/*  474 */   public static final PdfName Justify = createDirectName("Justify");
/*  475 */   public static final PdfName K = createDirectName("K");
/*  476 */   public static final PdfName Keywords = createDirectName("Keywords");
/*  477 */   public static final PdfName Kids = createDirectName("Kids");
/*  478 */   public static final PdfName L2R = createDirectName("L2R");
/*  479 */   public static final PdfName L = createDirectName("L");
/*  480 */   public static final PdfName Lab = createDirectName("Lab");
/*  481 */   public static final PdfName Lang = createDirectName("Lang");
/*  482 */   public static final PdfName Language = createDirectName("Language");
/*  483 */   public static final PdfName Last = createDirectName("Last");
/*  484 */   public static final PdfName LastChar = createDirectName("LastChar");
/*  485 */   public static final PdfName LastModified = createDirectName("LastModified");
/*  486 */   public static final PdfName LastPage = createDirectName("LastPage");
/*  487 */   public static final PdfName Launch = createDirectName("Launch");
/*  488 */   public static final PdfName Layout = createDirectName("Layout");
/*  489 */   public static final PdfName Lbl = createDirectName("Lbl");
/*  490 */   public static final PdfName LBody = createDirectName("LBody");
/*  491 */   public static final PdfName LC = createDirectName("LC");
/*  492 */   public static final PdfName Leading = createDirectName("Leading");
/*  493 */   public static final PdfName LE = createDirectName("LE");
/*  494 */   public static final PdfName Length = createDirectName("Length");
/*  495 */   public static final PdfName Length1 = createDirectName("Length1");
/*  496 */   public static final PdfName LI = createDirectName("LI");
/*  497 */   public static final PdfName Lighten = createDirectName("Lighten");
/*  498 */   public static final PdfName Limits = createDirectName("Limits");
/*  499 */   public static final PdfName Line = createDirectName("Line");
/*  500 */   public static final PdfName LineArrow = createDirectName("LineArrow");
/*  501 */   public static final PdfName LineHeight = createDirectName("LineHeight");
/*  502 */   public static final PdfName LineNum = createDirectName("LineNum");
/*  503 */   public static final PdfName LineThrough = createDirectName("LineThrough");
/*  504 */   public static final PdfName Link = createDirectName("Link");
/*  505 */   public static final PdfName List = createDirectName("List");
/*  506 */   public static final PdfName ListMode = createDirectName("ListMode");
/*  507 */   public static final PdfName ListNumbering = createDirectName("ListNumbering");
/*  508 */   public static final PdfName LJ = createDirectName("LJ");
/*  509 */   public static final PdfName LL = createDirectName("LL");
/*  510 */   public static final PdfName LLE = createDirectName("LLE");
/*  511 */   public static final PdfName LLO = createDirectName("LLO");
/*  512 */   public static final PdfName Lock = createDirectName("Lock");
/*  513 */   public static final PdfName Locked = createDirectName("Locked");
/*  514 */   public static final PdfName Location = createDirectName("Location");
/*  515 */   public static final PdfName LowerAlpha = createDirectName("LowerAlpha");
/*  516 */   public static final PdfName LowerRoman = createDirectName("LowerRoman");
/*  517 */   public static final PdfName Luminosity = createDirectName("Luminosity");
/*  518 */   public static final PdfName LW = createDirectName("LW");
/*  519 */   public static final PdfName LZWDecode = createDirectName("LZWDecode");
/*  520 */   public static final PdfName M = createDirectName("M");
/*  521 */   public static final PdfName MacExpertEncoding = createDirectName("MacExpertEncoding");
/*  522 */   public static final PdfName MacRomanEncoding = createDirectName("MacRomanEncoding");
/*  523 */   public static final PdfName Marked = createDirectName("Marked");
/*  524 */   public static final PdfName MarkInfo = createDirectName("MarkInfo");
/*  525 */   public static final PdfName Markup = createDirectName("Markup");
/*  526 */   public static final PdfName Markup3D = createDirectName("Markup3D");
/*  527 */   public static final PdfName MarkStyle = createDirectName("MarkStyle");
/*  528 */   public static final PdfName Mask = createDirectName("Mask");
/*  529 */   public static final PdfName Matrix = createDirectName("Matrix");
/*  530 */   public static final PdfName max = createDirectName("max");
/*  531 */   public static final PdfName MaxLen = createDirectName("MaxLen");
/*  532 */   public static final PdfName MCD = createDirectName("MCD");
/*  533 */   public static final PdfName MCID = createDirectName("MCID");
/*  534 */   public static final PdfName MCR = createDirectName("MCR");
/*  535 */   public static final PdfName MD5 = createDirectName("MD5");
/*  536 */   public static final PdfName Measure = createDirectName("Measure");
/*  537 */   public static final PdfName MediaBox = createDirectName("MediaBox");
/*  538 */   public static final PdfName MediaClip = createDirectName("MediaClip");
/*  539 */   public static final PdfName Metadata = createDirectName("Metadata");
/*  540 */   public static final PdfName Middle = createDirectName("Middle");
/*  541 */   public static final PdfName min = createDirectName("min");
/*  542 */   public static final PdfName Mix = createDirectName("Mix");
/*  543 */   public static final PdfName MissingWidth = createDirectName("MissingWidth");
/*  544 */   public static final PdfName MK = createDirectName("MK");
/*  545 */   public static final PdfName ML = createDirectName("ML");
/*  546 */   public static final PdfName MMType1 = createDirectName("MMType1");
/*  547 */   public static final PdfName MN = createDirectName("ML");
/*  548 */   public static final PdfName ModDate = createDirectName("ModDate");
/*  549 */   public static final PdfName Movie = createDirectName("Movie");
/*  550 */   public static final PdfName MR = createDirectName("MR");
/*  551 */   public static final PdfName MuLaw = createDirectName("muLaw");
/*  552 */   public static final PdfName Multiply = createDirectName("Multiply");
/*  553 */   public static final PdfName N = createDirectName("N");
/*  554 */   public static final PdfName NA = createDirectName("NA");
/*  555 */   public static final PdfName Name = createDirectName("Name");
/*  556 */   public static final PdfName Named = createDirectName("Named");
/*  557 */   public static final PdfName Names = createDirectName("Names");
/*  558 */   public static final PdfName Namespace = createDirectName("Namespace");
/*  559 */   public static final PdfName Namespaces = createDirectName("Namespaces");
/*  560 */   public static final PdfName NeedAppearances = createDirectName("NeedAppearances");
/*  561 */   public static final PdfName NeedsRendering = createDirectName("NeedsRendering");
/*  562 */   public static final PdfName NewWindow = createDirectName("NewWindow");
/*  563 */   public static final PdfName Next = createDirectName("Next");
/*  564 */   public static final PdfName NextPage = createDirectName("NextPage");
/*  565 */   public static final PdfName NM = createDirectName("NM");
/*  566 */   public static final PdfName NonFullScreenPageMode = createDirectName("NonFullScreenPageMode");
/*  567 */   public static final PdfName None = createDirectName("None");
/*  568 */   public static final PdfName NonStruct = createDirectName("NonStruct");
/*  569 */   public static final PdfName NoOp = createDirectName("NoOp");
/*  570 */   public static final PdfName Normal = createDirectName("Normal");
/*  571 */   public static final PdfName Not = createDirectName("Not");
/*  572 */   public static final PdfName NotApproved = createDirectName("NotApproved");
/*  573 */   public static final PdfName Note = createDirectName("Note");
/*  574 */   public static final PdfName NotForPublicRelease = createDirectName("NotForPublicRelease");
/*  575 */   public static final PdfName NS = createDirectName("NS");
/*  576 */   public static final PdfName NSO = createDirectName("NSO");
/*  577 */   public static final PdfName NumCopies = createDirectName("NumCopies");
/*  578 */   public static final PdfName Nums = createDirectName("Nums");
/*  579 */   public static final PdfName O = createDirectName("O");
/*  580 */   public static final PdfName Obj = createDirectName("Obj");
/*  581 */   public static final PdfName OBJR = createDirectName("OBJR");
/*  582 */   public static final PdfName ObjStm = createDirectName("ObjStm");
/*  583 */   public static final PdfName OC = createDirectName("OC");
/*  584 */   public static final PdfName OCG = createDirectName("OCG");
/*  585 */   public static final PdfName OCGs = createDirectName("OCGs");
/*  586 */   public static final PdfName OCMD = createDirectName("OCMD");
/*  587 */   public static final PdfName OCProperties = createDirectName("OCProperties");
/*  588 */   public static final PdfName OCSP = createDirectName("OCSP");
/*  589 */   public static final PdfName OCSPs = createDirectName("OCSPs");
/*  590 */   public static final PdfName OE = createDirectName("OE");
/*  591 */   public static final PdfName OFF = createDirectName("OFF");
/*  592 */   public static final PdfName ON = createDirectName("ON");
/*  593 */   public static final PdfName OneColumn = createDirectName("OneColumn");
/*  594 */   public static final PdfName OP = createDirectName("OP");
/*  595 */   public static final PdfName op = createDirectName("op");
/*  596 */   public static final PdfName Open = createDirectName("Open");
/*  597 */   public static final PdfName OpenAction = createDirectName("OpenAction");
/*  598 */   public static final PdfName OpenArrow = createDirectName("OpenArrow");
/*  599 */   public static final PdfName Operation = createDirectName("Operation");
/*  600 */   public static final PdfName OPI = createDirectName("OPI");
/*  601 */   public static final PdfName OPM = createDirectName("OPM");
/*  602 */   public static final PdfName Opt = createDirectName("Opt");
/*  603 */   public static final PdfName Or = createDirectName("Or");
/*  604 */   public static final PdfName Order = createDirectName("Order");
/*  605 */   public static final PdfName Ordered = createDirectName("Ordered");
/*  606 */   public static final PdfName Ordering = createDirectName("Ordering");
/*  607 */   public static final PdfName Outlines = createDirectName("Outlines");
/*  608 */   public static final PdfName OutputCondition = createDirectName("OutputCondition");
/*  609 */   public static final PdfName OutputConditionIdentifier = createDirectName("OutputConditionIdentifier");
/*  610 */   public static final PdfName OutputIntent = createDirectName("OutputIntent");
/*  611 */   public static final PdfName OutputIntents = createDirectName("OutputIntents");
/*  612 */   public static final PdfName Outset = createDirectName("Outset");
/*  613 */   public static final PdfName Overlay = createDirectName("Overlay");
/*  614 */   public static final PdfName OverlayText = createDirectName("OverlayText");
/*  615 */   public static final PdfName P = createDirectName("P");
/*  616 */   public static final PdfName PA = createDirectName("PA");
/*  617 */   public static final PdfName Padding = createDirectName("Padding");
/*  618 */   public static final PdfName Page = createDirectName("Page");
/*  619 */   public static final PdfName PageElement = createDirectName("PageElement");
/*  620 */   public static final PdfName PageLabels = createDirectName("PageLabels");
/*  621 */   public static final PdfName PageLayout = createDirectName("PageLayout");
/*  622 */   public static final PdfName PageMode = createDirectName("PageMode");
/*  623 */   public static final PdfName PageNum = createDirectName("PageNum");
/*  624 */   public static final PdfName Pages = createDirectName("Pages");
/*  625 */   public static final PdfName Pagination = createDirectName("Pagination");
/*  626 */   public static final PdfName PaintType = createDirectName("PaintType");
/*  627 */   public static final PdfName Panose = createDirectName("Panose");
/*  628 */   public static final PdfName Paperclip = createDirectName("Paperclip");
/*  629 */   public static final PdfName Params = createDirectName("Params");
/*  630 */   public static final PdfName Parent = createDirectName("Parent");
/*  631 */   public static final PdfName ParentTree = createDirectName("ParentTree");
/*  632 */   public static final PdfName ParentTreeNextKey = createDirectName("ParentTreeNextKey");
/*  633 */   public static final PdfName Part = createDirectName("Part");
/*  634 */   public static final PdfName Path = createDirectName("Path");
/*  635 */   public static final PdfName Pattern = createDirectName("Pattern");
/*  636 */   public static final PdfName PatternType = createDirectName("PatternType");
/*  637 */   public static final PdfName Pause = createDirectName("Pause");
/*  638 */   public static final PdfName Perceptual = createDirectName("Perceptual");
/*  639 */   public static final PdfName Perms = createDirectName("Perms");
/*  640 */   public static final PdfName PC = createDirectName("PC");
/*  641 */   public static final PdfName PCM = createDirectName("PCM");
/*  642 */   public static final PdfName Pdf_Version_1_2 = createDirectName("1.2");
/*  643 */   public static final PdfName Pdf_Version_1_3 = createDirectName("1.3");
/*  644 */   public static final PdfName Pdf_Version_1_4 = createDirectName("1.4");
/*  645 */   public static final PdfName Pdf_Version_1_5 = createDirectName("1.5");
/*  646 */   public static final PdfName Pdf_Version_1_6 = createDirectName("1.6");
/*  647 */   public static final PdfName Pdf_Version_1_7 = createDirectName("1.7");
/*  648 */   public static final PdfName Pg = createDirectName("Pg");
/*  649 */   public static final PdfName PI = createDirectName("PI");
/*  650 */   public static final PdfName PickTrayByPDFSize = createDirectName("PickTrayByPDFSize");
/*  651 */   public static final PdfName Placement = createDirectName("Placement");
/*  652 */   public static final PdfName Play = createDirectName("Play");
/*  653 */   public static final PdfName PO = createDirectName("PO");
/*  654 */   public static final PdfName Polygon = createDirectName("Polygon");
/*  655 */   public static final PdfName PolyLine = createDirectName("PolyLine");
/*  656 */   public static final PdfName Popup = createDirectName("Popup");
/*  657 */   public static final PdfName Predictor = createDirectName("Predictor");
/*  658 */   public static final PdfName Preferred = createDirectName("Preferred");
/*  659 */   public static final PdfName PreserveRB = createDirectName("PreserveRB");
/*  660 */   public static final PdfName PresSteps = createDirectName("PresSteps");
/*  661 */   public static final PdfName Prev = createDirectName("Prev");
/*  662 */   public static final PdfName PrevPage = createDirectName("PrevPage");
/*  663 */   public static final PdfName Print = createDirectName("Print");
/*  664 */   public static final PdfName PrintArea = createDirectName("PrintArea");
/*  665 */   public static final PdfName PrintClip = createDirectName("PrintClip");
/*  666 */   public static final PdfName PrinterMark = createDirectName("PrinterMark");
/*  667 */   public static final PdfName PrintPageRange = createDirectName("PrintPageRange");
/*  668 */   public static final PdfName PrintScaling = createDirectName("PrintScaling");
/*  669 */   public static final PdfName PrintState = createDirectName("PrintState");
/*  670 */   public static final PdfName Private = createDirectName("Private");
/*  671 */   public static final PdfName ProcSet = createDirectName("ProcSet");
/*  672 */   public static final PdfName Producer = createDirectName("Producer");
/*  673 */   public static final PdfName PronunciationLexicon = createDirectName("PronunciationLexicon");
/*  674 */   public static final PdfName Prop_Build = createDirectName("Prop_Build");
/*  675 */   public static final PdfName Properties = createDirectName("Properties");
/*  676 */   public static final PdfName PS = createDirectName("PS");
/*  677 */   public static final PdfName Pushpin = createDirectName("PushPin");
/*  678 */   public static final PdfName PV = createDirectName("PV");
/*  679 */   public static final PdfName Q = createDirectName("Q");
/*  680 */   public static final PdfName Quote = createDirectName("Quote");
/*  681 */   public static final PdfName QuadPoints = createDirectName("QuadPoints");
/*  682 */   public static final PdfName r = createDirectName("r");
/*  683 */   public static final PdfName R = createDirectName("R");
/*  684 */   public static final PdfName R2L = createDirectName("R2L");
/*  685 */   public static final PdfName Range = createDirectName("Range");
/*  686 */   public static final PdfName Raw = createDirectName("Raw");
/*  687 */   public static final PdfName RB = createDirectName("RB");
/*  688 */   public static final PdfName RBGroups = createDirectName("RBGroups");
/*  689 */   public static final PdfName RC = createDirectName("RC");
/*  690 */   public static final PdfName RClosedArrow = createDirectName("RClosedArrow");
/*  691 */   public static final PdfName RD = createDirectName("RD");
/*  692 */   public static final PdfName Reason = createDirectName("Reason");
/*  693 */   public static final PdfName Recipients = createDirectName("Recipients");
/*  694 */   public static final PdfName Rect = createDirectName("Rect");
/*  695 */   public static final PdfName Redact = createDirectName("Redact");
/*  696 */   public static final PdfName Redaction = createDirectName("Redaction");
/*  697 */   public static final PdfName Reference = createDirectName("Reference");
/*  698 */   public static final PdfName Registry = createDirectName("Registry");
/*  699 */   public static final PdfName RegistryName = createDirectName("RegistryName");
/*  700 */   public static final PdfName RelativeColorimetric = createDirectName("RelativeColorimetric");
/*  701 */   public static final PdfName Rendition = createDirectName("Rendition");
/*  702 */   public static final PdfName Renditions = createDirectName("Renditions");
/*  703 */   public static final PdfName Repeat = createDirectName("Repeat");
/*  704 */   public static final PdfName ResetForm = createDirectName("ResetForm");
/*  705 */   public static final PdfName Resume = createDirectName("Resume");
/*  706 */   public static final PdfName Requirement = createDirectName("Requirement");
/*  707 */   public static final PdfName Requirements = createDirectName("Requirements");
/*  708 */   public static final PdfName Resources = createDirectName("Resources");
/*  709 */   public static final PdfName ReversedChars = createDirectName("ReversedChars");
/*  710 */   public static final PdfName Phoneme = createDirectName("Phoneme");
/*  711 */   public static final PdfName PhoneticAlphabet = createDirectName("PhoneticAlphabet");
/*  712 */   public static final PdfName Ref = createDirectName("Ref");
/*  713 */   public static final PdfName RI = createDirectName("RI");
/*  714 */   public static final PdfName RichMedia = createDirectName("RichMedia");
/*  715 */   public static final PdfName Ridge = createDirectName("Ridge");
/*  716 */   public static final PdfName RO = createDirectName("RO");
/*  717 */   public static final PdfName RoleMap = createDirectName("RoleMap");
/*  718 */   public static final PdfName RoleMapNS = createDirectName("RoleMapNS");
/*  719 */   public static final PdfName ROpenArrow = createDirectName("ROpenArrow");
/*  720 */   public static final PdfName Root = createDirectName("Root");
/*  721 */   public static final PdfName Rotate = createDirectName("Rotate");
/*  722 */   public static final PdfName Row = createDirectName("Row");
/*  723 */   public static final PdfName Rows = createDirectName("Rows");
/*  724 */   public static final PdfName RowSpan = createDirectName("RowSpan");
/*  725 */   public static final PdfName RP = createDirectName("RP");
/*  726 */   public static final PdfName RT = createDirectName("RT");
/*  727 */   public static final PdfName Ruby = createDirectName("Ruby");
/*  728 */   public static final PdfName RubyAlign = createDirectName("RubyAlign");
/*  729 */   public static final PdfName RubyPosition = createDirectName("RubyPosition");
/*  730 */   public static final PdfName RunLengthDecode = createDirectName("RunLengthDecode");
/*  731 */   public static final PdfName RV = createDirectName("RV");
/*  732 */   public static final PdfName Stream = createDirectName("Stream");
/*  733 */   public static final PdfName S = createDirectName("S");
/*  734 */   public static final PdfName SA = createDirectName("SA");
/*  735 */   public static final PdfName Saturation = createDirectName("Saturation");
/*  736 */   public static final PdfName Schema = createDirectName("Schema");
/*  737 */   public static final PdfName Scope = createDirectName("Scope");
/*  738 */   public static final PdfName Screen = createDirectName("Screen");
/*  739 */   public static final PdfName SD = createDirectName("SD");
/*  740 */   public static final PdfName Sect = createDirectName("Sect");
/*  741 */   public static final PdfName Separation = createDirectName("Separation");
/*  742 */   public static final PdfName SeparationColorNames = createDirectName("SeparationColorNames");
/*  743 */   public static final PdfName SeparationInfo = createDirectName("SeparationInfo");
/*  744 */   public static final PdfName Shading = createDirectName("Shading");
/*  745 */   public static final PdfName ShadingType = createDirectName("ShadingType");
/*  746 */   public static final PdfName SetOCGState = createDirectName("SetOCGState");
/*  747 */   public static final PdfName SetState = createDirectName("SetState");
/*  748 */   public static final PdfName Short = createDirectName("Short");
/*  749 */   public static final PdfName Sig = createDirectName("Sig");
/*  750 */   public static final PdfName SigFieldLock = createDirectName("SigFieldLock");
/*  751 */   public static final PdfName SigFlags = createDirectName("SigFlags");
/*  752 */   public static final PdfName Signed = createDirectName("Signed");
/*  753 */   public static final PdfName SigRef = createDirectName("SigRef");
/*  754 */   public static final PdfName Simplex = createDirectName("Simplex");
/*  755 */   public static final PdfName SinglePage = createDirectName("SinglePage");
/*  756 */   public static final PdfName Size = createDirectName("Size");
/*  757 */   public static final PdfName Slash = createDirectName("Slash");
/*  758 */   public static final PdfName SM = createDirectName("SM");
/*  759 */   public static final PdfName SMask = createDirectName("SMask");
/*  760 */   public static final PdfName SMaskInData = createDirectName("SMaskInData");
/*  761 */   public static final PdfName SoftLight = createDirectName("SoftLight");
/*  762 */   public static final PdfName Sold = createDirectName("Sold");
/*  763 */   public static final PdfName Solid = createDirectName("Solid");
/*  764 */   public static final PdfName Sort = createDirectName("Sort");
/*  765 */   public static final PdfName Sound = createDirectName("Sound");
/*  766 */   public static final PdfName Source = createDirectName("Source");
/*  767 */   public static final PdfName Span = createDirectName("Span");
/*  768 */   public static final PdfName SpaceBefore = createDirectName("SpaceBefore");
/*  769 */   public static final PdfName SpaceAfter = createDirectName("SpaceAfter");
/*  770 */   public static final PdfName Square = createDirectName("Square");
/*  771 */   public static final PdfName Squiggly = createDirectName("Squiggly");
/*  772 */   public static final PdfName St = createDirectName("St");
/*  773 */   public static final PdfName Stamp = createDirectName("Stamp");
/*  774 */   public static final PdfName StampImage = createDirectName("StampImage");
/*  775 */   public static final PdfName StampSnapshot = createDirectName("StampSnapshot");
/*  776 */   public static final PdfName Standard = createDirectName("Standard");
/*  777 */   public static final PdfName Start = createDirectName("Start");
/*  778 */   public static final PdfName StartIndent = createDirectName("StartIndent");
/*  779 */   public static final PdfName State = createDirectName("State");
/*  780 */   public static final PdfName StateModel = createDirectName("StateModel");
/*  781 */   public static final PdfName StdCF = createDirectName("StdCF");
/*  782 */   public static final PdfName StemV = createDirectName("StemV");
/*  783 */   public static final PdfName StemH = createDirectName("StemH");
/*  784 */   public static final PdfName Stop = createDirectName("Stop");
/*  785 */   public static final PdfName Stm = createDirectName("Stm");
/*  786 */   public static final PdfName StmF = createDirectName("StmF");
/*  787 */   public static final PdfName StrF = createDirectName("StrF");
/*  788 */   public static final PdfName StrikeOut = createDirectName("StrikeOut");
/*  789 */   public static final PdfName Strong = createDirectName("Strong");
/*  790 */   public static final PdfName StructElem = createDirectName("StructElem");
/*  791 */   public static final PdfName StructParent = createDirectName("StructParent");
/*  792 */   public static final PdfName StructParents = createDirectName("StructParents");
/*  793 */   public static final PdfName StructTreeRoot = createDirectName("StructTreeRoot");
/*  794 */   public static final PdfName Style = createDirectName("Style");
/*  795 */   public static final PdfName Sub = createDirectName("Sub");
/*  796 */   public static final PdfName SubFilter = createDirectName("SubFilter");
/*  797 */   public static final PdfName Subj = createDirectName("Subj");
/*  798 */   public static final PdfName Subject = createDirectName("Subject");
/*  799 */   public static final PdfName SubmitForm = createDirectName("SubmitForm");
/*  800 */   public static final PdfName Subtype = createDirectName("Subtype");
/*  801 */   public static final PdfName Subtype2 = createDirectName("Subtype2");
/*  802 */   public static final PdfName Supplement = createDirectName("Supplement");
/*  803 */   public static final PdfName Sy = createDirectName("Sy");
/*  804 */   public static final PdfName Symbol = createDirectName("Symbol");
/*  805 */   public static final PdfName Synchronous = createDirectName("Synchronous");
/*  806 */   public static final PdfName T = createDirectName("T");
/*  807 */   public static final PdfName Tag = createDirectName("Tag");
/*  808 */   public static final PdfName TBorderStyle = createDirectName("TBorderStyle");
/*  809 */   public static final PdfName TA = createDirectName("TA");
/*  810 */   public static final PdfName Table = createDirectName("Table");
/*  811 */   public static final PdfName Tabs = createDirectName("Tabs");
/*  812 */   public static final PdfName TBody = createDirectName("TBody");
/*  813 */   public static final PdfName TD = createDirectName("TD");
/*  814 */   public static final PdfName Templates = createDirectName("Templates");
/*  815 */   public static final PdfName Text = createDirectName("Text");
/*  816 */   public static final PdfName TextAlign = createDirectName("TextAlign");
/*  817 */   public static final PdfName TextDecorationColor = createDirectName("TextDecorationColor");
/*  818 */   public static final PdfName TextDecorationThickness = createDirectName("TextDecorationThickness");
/*  819 */   public static final PdfName TextDecorationType = createDirectName("TextDecorationType");
/*  820 */   public static final PdfName TextIndent = createDirectName("TextIndent");
/*  821 */   public static final PdfName TF = createDirectName("TF");
/*  822 */   public static final PdfName TFoot = createDirectName("TFoot");
/*  823 */   public static final PdfName TH = createDirectName("TH");
/*  824 */   public static final PdfName THead = createDirectName("THead");
/*  825 */   public static final PdfName Thumb = createDirectName("Thumb");
/*  826 */   public static final PdfName TI = createDirectName("TI");
/*  827 */   public static final PdfName TilingType = createDirectName("TilingType");
/*  828 */   public static final PdfName Title = createDirectName("Title");
/*  829 */   public static final PdfName TPadding = createDirectName("TPadding");
/*  830 */   public static final PdfName TrimBox = createDirectName("TrimBox");
/*  831 */   public static final PdfName TK = createDirectName("TK");
/*  832 */   public static final PdfName TM = createDirectName("TM");
/*  833 */   public static final PdfName TOC = createDirectName("TOC");
/*  834 */   public static final PdfName TOCI = createDirectName("TOCI");
/*  835 */   public static final PdfName TP = createDirectName("TP");
/*  836 */   public static final PdfName Toggle = createDirectName("Toggle");
/*  837 */   public static final PdfName Top = createDirectName("Top");
/*  838 */   public static final PdfName TopSecret = createDirectName("TopSecret");
/*  839 */   public static final PdfName ToUnicode = createDirectName("ToUnicode");
/*  840 */   public static final PdfName TR = createDirectName("TR");
/*  841 */   public static final PdfName TR2 = createDirectName("TR2");
/*  842 */   public static final PdfName Trans = createDirectName("Trans");
/*  843 */   public static final PdfName TransformMethod = createDirectName("TransformMethod");
/*  844 */   public static final PdfName TransformParams = createDirectName("TransformParams");
/*  845 */   public static final PdfName Transparency = createDirectName("Transparency");
/*  846 */   public static final PdfName TrapNet = createDirectName("TrapNet");
/*  847 */   public static final PdfName Trapped = createDirectName("Trapped");
/*  848 */   public static final PdfName TrapRegions = createDirectName("TrapRegions");
/*  849 */   public static final PdfName TrapStyles = createDirectName("TrapStyles");
/*  850 */   public static final PdfName True = createDirectName("true");
/*  851 */   public static final PdfName TrueType = createDirectName("TrueType");
/*  852 */   public static final PdfName TU = createDirectName("TU");
/*  853 */   public static final PdfName TwoColumnLeft = createDirectName("TwoColumnLeft");
/*  854 */   public static final PdfName TwoColumnRight = createDirectName("TwoColumnRight");
/*  855 */   public static final PdfName TwoPageLeft = createDirectName("TwoPageLeft");
/*  856 */   public static final PdfName TwoPageRight = createDirectName("TwoPageRight");
/*  857 */   public static final PdfName Tx = createDirectName("Tx");
/*  858 */   public static final PdfName Type = createDirectName("Type");
/*  859 */   public static final PdfName Type0 = createDirectName("Type0");
/*  860 */   public static final PdfName Type1 = createDirectName("Type1");
/*  861 */   public static final PdfName Type3 = createDirectName("Type3");
/*  862 */   public static final PdfName U = createDirectName("U");
/*  863 */   public static final PdfName UCR = createDirectName("UCR");
/*  864 */   public static final PdfName UR3 = createDirectName("UR3");
/*  865 */   public static final PdfName UCR2 = createDirectName("UCR2");
/*  866 */   public static final PdfName UE = createDirectName("UE");
/*  867 */   public static final PdfName UF = createDirectName("UF");
/*  868 */   public static final PdfName Underline = createDirectName("Underline");
/*  869 */   public static final PdfName Unordered = createDirectName("Unordered");
/*  870 */   public static final PdfName Unspecified = createDirectName("Unspecified");
/*  871 */   public static final PdfName UpperAlpha = createDirectName("UpperAlpha");
/*  872 */   public static final PdfName UpperRoman = createDirectName("UpperRoman");
/*  873 */   public static final PdfName URI = createDirectName("URI");
/*  874 */   public static final PdfName URL = createDirectName("URL");
/*  875 */   public static final PdfName URLS = createDirectName("URLS");
/*  876 */   public static final PdfName Usage = createDirectName("Usage");
/*  877 */   public static final PdfName UseAttachments = createDirectName("UseAttachments");
/*  878 */   public static final PdfName UseBlackPtComp = createDirectName("UseBlackPtComp");
/*  879 */   public static final PdfName UseNone = createDirectName("UseNone");
/*  880 */   public static final PdfName UseOC = createDirectName("UseOC");
/*  881 */   public static final PdfName UseOutlines = createDirectName("UseOutlines");
/*  882 */   public static final PdfName UseThumbs = createDirectName("UseThumbs");
/*  883 */   public static final PdfName User = createDirectName("User");
/*  884 */   public static final PdfName UserProperties = createDirectName("UserProperties");
/*  885 */   public static final PdfName UserUnit = createDirectName("UserUnit");
/*  886 */   public static final PdfName V = createDirectName("V");
/*  887 */   public static final PdfName V2 = createDirectName("V2");
/*  888 */   public static final PdfName VE = createDirectName("VE");
/*  889 */   public static final PdfName Version = createDirectName("Version");
/*  890 */   public static final PdfName Vertices = createDirectName("Vertices");
/*  891 */   public static final PdfName VerticesPerRow = createDirectName("VerticesPerRow");
/*  892 */   public static final PdfName View = createDirectName("View");
/*  893 */   public static final PdfName ViewArea = createDirectName("ViewArea");
/*  894 */   public static final PdfName ViewerPreferences = createDirectName("ViewerPreferences");
/*  895 */   public static final PdfName ViewClip = createDirectName("ViewClip");
/*  896 */   public static final PdfName ViewState = createDirectName("ViewState");
/*  897 */   public static final PdfName VisiblePages = createDirectName("VisiblePages");
/*  898 */   public static final PdfName Volatile = createDirectName("Volatile");
/*  899 */   public static final PdfName Volume = createDirectName("Volume");
/*  900 */   public static final PdfName VRI = createDirectName("VRI");
/*  901 */   public static final PdfName W = createDirectName("W");
/*  902 */   public static final PdfName W2 = createDirectName("W2");
/*  903 */   public static final PdfName Warichu = createDirectName("Warichu");
/*  904 */   public static final PdfName Watermark = createDirectName("Watermark");
/*  905 */   public static final PdfName WC = createDirectName("WC");
/*  906 */   public static final PdfName WhitePoint = createDirectName("WhitePoint");
/*  907 */   public static final PdfName Width = createDirectName("Width");
/*  908 */   public static final PdfName Widths = createDirectName("Widths");
/*  909 */   public static final PdfName Widget = createDirectName("Widget");
/*  910 */   public static final PdfName Win = createDirectName("Win");
/*  911 */   public static final PdfName WinAnsiEncoding = createDirectName("WinAnsiEncoding");
/*  912 */   public static final PdfName WritingMode = createDirectName("WritingMode");
/*  913 */   public static final PdfName WP = createDirectName("WP");
/*  914 */   public static final PdfName WS = createDirectName("WS");
/*  915 */   public static final PdfName WT = createDirectName("WT");
/*  916 */   public static final PdfName X = createDirectName("X");
/*  917 */   public static final PdfName x_sampa = createDirectName("x-sampa");
/*  918 */   public static final PdfName XFA = createDirectName("XFA");
/*  919 */   public static final PdfName XML = createDirectName("XML");
/*  920 */   public static final PdfName XObject = createDirectName("XObject");
/*  921 */   public static final PdfName XHeight = createDirectName("XHeight");
/*  922 */   public static final PdfName XRef = createDirectName("XRef");
/*  923 */   public static final PdfName XRefStm = createDirectName("XRefStm");
/*  924 */   public static final PdfName XStep = createDirectName("XStep");
/*  925 */   public static final PdfName XYZ = createDirectName("XYZ");
/*  926 */   public static final PdfName YStep = createDirectName("YStep");
/*  927 */   public static final PdfName ZapfDingbats = createDirectName("ZapfDingbats");
/*  928 */   public static final PdfName zh_Latn_pinyin = createDirectName("zh-Latn-pinyin");
/*  929 */   public static final PdfName zh_Latn_wadegile = createDirectName("zh-Latn-wadegile");
/*  930 */   public static final PdfName Zoom = createDirectName("Zoom");
/*      */ 
/*      */   
/*  933 */   protected String value = null;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  947 */   public static Map<String, PdfName> staticNames = PdfNameLoader.loadNames();
/*      */ 
/*      */   
/*      */   private static PdfName createDirectName(String name) {
/*  951 */     return new PdfName(name, true);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PdfName(String value) {
/*  961 */     assert value != null;
/*  962 */     this.value = value;
/*      */   }
/*      */   
/*      */   private PdfName(String value, boolean directOnly) {
/*  966 */     super(directOnly);
/*  967 */     this.value = value;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PdfName(byte[] content) {
/*  976 */     super(content);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public byte getType() {
/*  985 */     return 6;
/*      */   }
/*      */   
/*      */   public String getValue() {
/*  989 */     if (this.value == null)
/*  990 */       generateValue(); 
/*  991 */     return this.value;
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
/*      */   public int compareTo(PdfName o) {
/* 1003 */     return getValue().compareTo(o.getValue());
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean equals(Object o) {
/* 1008 */     if (this == o) return true; 
/* 1009 */     if (o == null || getClass() != o.getClass()) return false; 
/* 1010 */     PdfName pdfName = (PdfName)o;
/* 1011 */     return (compareTo(pdfName) == 0);
/*      */   }
/*      */ 
/*      */   
/*      */   public int hashCode() {
/* 1016 */     return getValue().hashCode();
/*      */   }
/*      */   
/*      */   protected void generateValue() {
/* 1020 */     StringBuilder buf = new StringBuilder();
/*      */     try {
/* 1022 */       for (int k = 0; k < this.content.length; k++) {
/* 1023 */         char c = (char)this.content[k];
/* 1024 */         if (c == '#') {
/* 1025 */           byte c1 = this.content[k + 1];
/* 1026 */           byte c2 = this.content[k + 2];
/* 1027 */           c = (char)((ByteBuffer.getHex(c1) << 4) + ByteBuffer.getHex(c2));
/* 1028 */           k += 2;
/*      */         } 
/* 1030 */         buf.append(c);
/*      */       } 
/* 1032 */     } catch (IndexOutOfBoundsException indexOutOfBoundsException) {}
/*      */ 
/*      */     
/* 1035 */     this.value = buf.toString();
/*      */   }
/*      */ 
/*      */   
/*      */   protected void generateContent() {
/* 1040 */     int length = this.value.length();
/* 1041 */     ByteBuffer buf = new ByteBuffer(length + 20);
/*      */     
/* 1043 */     char[] chars = this.value.toCharArray();
/* 1044 */     for (int k = 0; k < length; k++) {
/* 1045 */       char c = (char)(chars[k] & 0xFF);
/*      */       
/* 1047 */       switch (c) {
/*      */         case ' ':
/* 1049 */           buf.append(space);
/*      */           break;
/*      */         case '%':
/* 1052 */           buf.append(percent);
/*      */           break;
/*      */         case '(':
/* 1055 */           buf.append(leftParenthesis);
/*      */           break;
/*      */         case ')':
/* 1058 */           buf.append(rightParenthesis);
/*      */           break;
/*      */         case '<':
/* 1061 */           buf.append(lessThan);
/*      */           break;
/*      */         case '>':
/* 1064 */           buf.append(greaterThan);
/*      */           break;
/*      */         case '[':
/* 1067 */           buf.append(leftSquare);
/*      */           break;
/*      */         case ']':
/* 1070 */           buf.append(rightSquare);
/*      */           break;
/*      */         case '{':
/* 1073 */           buf.append(leftCurlyBracket);
/*      */           break;
/*      */         case '}':
/* 1076 */           buf.append(rightCurlyBracket);
/*      */           break;
/*      */         case '/':
/* 1079 */           buf.append(solidus);
/*      */           break;
/*      */         case '#':
/* 1082 */           buf.append(numberSign);
/*      */           break;
/*      */         default:
/* 1085 */           if (c >= ' ' && c <= '~') {
/* 1086 */             buf.append(c); break;
/*      */           } 
/* 1088 */           buf.append(35);
/* 1089 */           if (c < '\020')
/* 1090 */             buf.append(48); 
/* 1091 */           buf.append(Integer.toHexString(c));
/*      */           break;
/*      */       } 
/*      */     
/*      */     } 
/* 1096 */     this.content = buf.toByteArray();
/*      */   }
/*      */ 
/*      */   
/*      */   public String toString() {
/* 1101 */     if (this.content != null) {
/* 1102 */       return "/" + new String(this.content, StandardCharsets.ISO_8859_1);
/*      */     }
/* 1104 */     return "/" + getValue();
/*      */   }
/*      */ 
/*      */   
/*      */   protected PdfObject newInstance() {
/* 1109 */     return new PdfName();
/*      */   }
/*      */ 
/*      */   
/*      */   protected void copyContent(PdfObject from, PdfDocument document) {
/* 1114 */     super.copyContent(from, document);
/* 1115 */     PdfName name = (PdfName)from;
/* 1116 */     this.value = name.value;
/*      */   }
/*      */   
/*      */   private PdfName() {}
/*      */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/kernel/pdf/PdfName.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */