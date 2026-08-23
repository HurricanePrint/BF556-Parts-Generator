/*     */ package com.itextpdf.styledxmlparser.resolver.resource;
/*     */ 
/*     */ import com.itextpdf.io.codec.Base64;
/*     */ import com.itextpdf.io.image.ImageDataFactory;
/*     */ import com.itextpdf.io.util.MessageFormatUtil;
/*     */ import com.itextpdf.io.util.UrlUtil;
/*     */ import com.itextpdf.kernel.pdf.xobject.PdfImageXObject;
/*     */ import com.itextpdf.kernel.pdf.xobject.PdfXObject;
/*     */ import java.io.ByteArrayInputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.net.MalformedURLException;
/*     */ import java.net.URL;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ResourceResolver
/*     */ {
/*     */   public static final String BASE64IDENTIFIER = "base64";
/*     */   public static final String DATA_SCHEMA_PREFIX = "data:";
/*  78 */   private static final Logger logger = LoggerFactory.getLogger(ResourceResolver.class);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private UriResolver uriResolver;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private SimpleImageCache imageCache;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private IResourceRetriever retriever;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ResourceResolver(String baseUri) {
/* 105 */     this(baseUri, null);
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
/*     */   public ResourceResolver(String baseUri, IResourceRetriever retriever) {
/* 121 */     if (baseUri == null) {
/* 122 */       baseUri = "";
/*     */     }
/* 124 */     this.uriResolver = new UriResolver(baseUri);
/* 125 */     this.imageCache = new SimpleImageCache();
/*     */     
/* 127 */     if (retriever == null) {
/* 128 */       this.retriever = new DefaultResourceRetriever();
/*     */     } else {
/* 130 */       this.retriever = retriever;
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
/*     */   public IResourceRetriever getRetriever() {
/* 142 */     return this.retriever;
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
/*     */   public ResourceResolver setRetriever(IResourceRetriever retriever) {
/* 154 */     this.retriever = retriever;
/* 155 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public PdfImageXObject retrieveImage(String src) {
/* 167 */     PdfXObject image = retrieveImageExtended(src);
/* 168 */     if (image instanceof PdfImageXObject) {
/* 169 */       return (PdfImageXObject)image;
/*     */     }
/* 171 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PdfXObject retrieveImageExtended(String src) {
/* 182 */     if (src != null) {
/* 183 */       if (isContains64Mark(src)) {
/* 184 */         PdfXObject pdfXObject = tryResolveBase64ImageSource(src);
/* 185 */         if (pdfXObject != null) {
/* 186 */           return pdfXObject;
/*     */         }
/*     */       } 
/*     */       
/* 190 */       PdfXObject imageXObject = tryResolveUrlImageSource(src);
/* 191 */       if (imageXObject != null) {
/* 192 */         return imageXObject;
/*     */       }
/*     */     } 
/* 195 */     logger.error(MessageFormatUtil.format("Unable to retrieve image with given base URI ({0}) and image source path ({1})", new Object[] { this.uriResolver
/* 196 */             .getBaseUri(), src }));
/* 197 */     return null;
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
/*     */   @Deprecated
/*     */   public InputStream retrieveStyleSheet(String uri) throws IOException {
/* 210 */     return this.retriever.getInputStreamByUrl(this.uriResolver.resolveAgainstBaseUri(uri));
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
/*     */   @Deprecated
/*     */   public byte[] retrieveStream(String src) {
/*     */     try {
/* 226 */       return retrieveBytesFromResource(src);
/* 227 */     } catch (Exception e) {
/* 228 */       logger.error(MessageFormatUtil.format("Unable to retrieve stream with given base URI ({0}) and source path ({1})", new Object[] { this.uriResolver
/* 229 */               .getBaseUri(), src }), e);
/* 230 */       return null;
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
/*     */   public byte[] retrieveBytesFromResource(String src) {
/* 242 */     byte[] bytes = retrieveBytesFromBase64Src(src);
/* 243 */     if (bytes != null) {
/* 244 */       return bytes;
/*     */     }
/*     */     
/*     */     try {
/* 248 */       URL url = this.uriResolver.resolveAgainstBaseUri(src);
/* 249 */       return this.retriever.getByteArrayByUrl(url);
/* 250 */     } catch (Exception e) {
/* 251 */       logger.error(MessageFormatUtil.format("Unable to retrieve stream with given base URI ({0}) and source path ({1})", new Object[] { this.uriResolver
/* 252 */               .getBaseUri(), src }), e);
/* 253 */       return null;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public InputStream retrieveResourceAsInputStream(String src) {
/* 264 */     byte[] bytes = retrieveBytesFromBase64Src(src);
/* 265 */     if (bytes != null) {
/* 266 */       return new ByteArrayInputStream(bytes);
/*     */     }
/*     */     
/*     */     try {
/* 270 */       URL url = this.uriResolver.resolveAgainstBaseUri(src);
/* 271 */       return this.retriever.getInputStreamByUrl(url);
/* 272 */     } catch (Exception e) {
/* 273 */       logger.error(MessageFormatUtil.format("Unable to retrieve stream with given base URI ({0}) and source path ({1})", new Object[] { this.uriResolver
/* 274 */               .getBaseUri(), src }), e);
/* 275 */       return null;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isDataSrc(String src) {
/* 286 */     return (src.startsWith("data:") && src.contains(","));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public URL resolveAgainstBaseUri(String uri) throws MalformedURLException {
/* 297 */     return this.uriResolver.resolveAgainstBaseUri(uri);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void resetCache() {
/* 304 */     this.imageCache.reset();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public boolean isImageTypeSupportedByImageDataFactory(String src) {
/*     */     try {
/* 317 */       URL url = this.uriResolver.resolveAgainstBaseUri(src);
/* 318 */       url = UrlUtil.getFinalURL(url);
/* 319 */       return ImageDataFactory.isSupportedType(this.retriever.getByteArrayByUrl(url));
/* 320 */     } catch (Exception e) {
/* 321 */       return false;
/*     */     } 
/*     */   }
/*     */   protected PdfXObject tryResolveBase64ImageSource(String src) {
/*     */     try {
/*     */       PdfImageXObject pdfImageXObject;
/* 327 */       String fixedSrc = src.replaceAll("\\s", "");
/* 328 */       fixedSrc = fixedSrc.substring(fixedSrc.indexOf("base64") + 7);
/* 329 */       PdfXObject imageXObject = this.imageCache.getImage(fixedSrc);
/* 330 */       if (imageXObject == null) {
/* 331 */         pdfImageXObject = new PdfImageXObject(ImageDataFactory.create(Base64.decode(fixedSrc)));
/* 332 */         this.imageCache.putImage(fixedSrc, (PdfXObject)pdfImageXObject);
/*     */       } 
/*     */       
/* 335 */       return (PdfXObject)pdfImageXObject;
/* 336 */     } catch (Exception exception) {
/*     */       
/* 338 */       return null;
/*     */     } 
/*     */   }
/*     */   protected PdfXObject tryResolveUrlImageSource(String uri) {
/*     */     try {
/* 343 */       URL url = this.uriResolver.resolveAgainstBaseUri(uri);
/* 344 */       url = UrlUtil.getFinalURL(url);
/* 345 */       String imageResolvedSrc = url.toExternalForm();
/* 346 */       PdfXObject imageXObject = this.imageCache.getImage(imageResolvedSrc);
/* 347 */       if (imageXObject == null) {
/* 348 */         imageXObject = createImageByUrl(url);
/* 349 */         if (imageXObject != null) {
/* 350 */           this.imageCache.putImage(imageResolvedSrc, imageXObject);
/*     */         }
/*     */       } 
/* 353 */       return imageXObject;
/* 354 */     } catch (Exception exception) {
/*     */       
/* 356 */       return null;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected PdfXObject createImageByUrl(URL url) throws Exception {
/* 367 */     byte[] bytes = this.retriever.getByteArrayByUrl(url);
/* 368 */     return (bytes == null) ? null : (PdfXObject)new PdfImageXObject(ImageDataFactory.create(bytes));
/*     */   }
/*     */   
/*     */   private byte[] retrieveBytesFromBase64Src(String src) {
/* 372 */     if (isContains64Mark(src)) {
/*     */       try {
/* 374 */         String fixedSrc = src.replaceAll("\\s", "");
/* 375 */         fixedSrc = fixedSrc.substring(fixedSrc.indexOf("base64") + 7);
/* 376 */         return Base64.decode(fixedSrc);
/* 377 */       } catch (Exception exception) {}
/*     */     }
/*     */     
/* 380 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean isContains64Mark(String src) {
/* 391 */     return src.contains("base64");
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/styledxmlparser/resolver/resource/ResourceResolver.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */