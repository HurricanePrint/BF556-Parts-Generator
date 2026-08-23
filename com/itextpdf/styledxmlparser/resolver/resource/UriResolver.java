/*     */ package com.itextpdf.styledxmlparser.resolver.resource;
/*     */ 
/*     */ import com.itextpdf.io.util.MessageFormatUtil;
/*     */ import java.net.MalformedURLException;
/*     */ import java.net.URI;
/*     */ import java.net.URL;
/*     */ import java.nio.file.Files;
/*     */ import java.nio.file.Path;
/*     */ import java.nio.file.Paths;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class UriResolver
/*     */ {
/*     */   private URL baseUrl;
/*     */   private boolean isLocalBaseUri;
/*     */   
/*     */   public UriResolver(String baseUri) {
/*  75 */     if (baseUri == null) throw new IllegalArgumentException("baseUri"); 
/*  76 */     resolveBaseUrlOrPath(baseUri);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getBaseUri() {
/*  85 */     return this.baseUrl.toExternalForm();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public URL resolveAgainstBaseUri(String uriString) throws MalformedURLException {
/*  96 */     URL resolvedUrl = null;
/*  97 */     uriString = uriString.trim();
/*     */     
/*  99 */     uriString = UriEncodeUtil.encode(uriString);
/* 100 */     if (this.isLocalBaseUri && 
/* 101 */       !uriString.startsWith("file:")) {
/*     */       try {
/* 103 */         Path path = Paths.get(uriString, new String[0]);
/*     */ 
/*     */ 
/*     */         
/* 107 */         if (path.isAbsolute()) {
/* 108 */           resolvedUrl = path.toUri().toURL();
/*     */         }
/* 110 */       } catch (Exception exception) {}
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 115 */     if (resolvedUrl == null) {
/* 116 */       resolvedUrl = new URL(this.baseUrl, uriString);
/*     */     }
/* 118 */     return resolvedUrl;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isLocalBaseUri() {
/* 127 */     return this.isLocalBaseUri;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void resolveBaseUrlOrPath(String base) {
/* 136 */     base = base.trim();
/* 137 */     this.baseUrl = baseUriAsUrl(UriEncodeUtil.encode(base));
/* 138 */     if (this.baseUrl == null) {
/* 139 */       this.baseUrl = uriAsFileUrl(base);
/*     */     }
/*     */     
/* 142 */     if (this.baseUrl == null) {
/* 143 */       throw new IllegalArgumentException(MessageFormatUtil.format("Invalid base URI: {0}", new Object[] { base }));
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private URL baseUriAsUrl(String baseUriString) {
/* 154 */     URL baseAsUrl = null;
/*     */     try {
/* 156 */       URI baseUri = new URI(baseUriString);
/* 157 */       if (baseUri.isAbsolute()) {
/* 158 */         baseAsUrl = baseUri.toURL();
/*     */         
/* 160 */         if ("file".equals(baseUri.getScheme())) {
/* 161 */           this.isLocalBaseUri = true;
/*     */         }
/*     */       } 
/* 164 */     } catch (Exception exception) {}
/*     */     
/* 166 */     return baseAsUrl;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private URL uriAsFileUrl(String baseUriString) {
/* 176 */     URL baseAsFileUrl = null;
/*     */     try {
/* 178 */       Path path = Paths.get(baseUriString, new String[0]);
/* 179 */       if (isPathRooted(path, baseUriString)) {
/* 180 */         String str = "file:///" + encode(path, path.toAbsolutePath().normalize().toString());
/* 181 */         baseAsFileUrl = (new URI(str)).toURL();
/*     */       } else {
/* 183 */         String str = encode(path, baseUriString);
/* 184 */         URL base = Paths.get("", new String[0]).toUri().toURL();
/* 185 */         baseAsFileUrl = new URL(base, str);
/*     */       } 
/* 187 */       this.isLocalBaseUri = true;
/* 188 */     } catch (Exception exception) {}
/*     */ 
/*     */     
/* 191 */     return baseAsFileUrl;
/*     */   }
/*     */   
/*     */   private String encode(Path path, String str) {
/* 195 */     str = str.replace("\\", "/");
/* 196 */     str = UriEncodeUtil.encode(str);
/* 197 */     if (Files.isDirectory(path, new java.nio.file.LinkOption[0]) && !str.endsWith("/")) {
/* 198 */       str = str + "/";
/*     */     }
/* 200 */     str = str.replaceFirst("/*\\\\*", "");
/* 201 */     return str;
/*     */   }
/*     */   
/*     */   private boolean isPathRooted(Path path, String str) {
/* 205 */     return (path.isAbsolute() || str.startsWith("/"));
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/styledxmlparser/resolver/resource/UriResolver.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */