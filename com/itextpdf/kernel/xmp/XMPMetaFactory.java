/*     */ package com.itextpdf.kernel.xmp;
/*     */ 
/*     */ import com.itextpdf.kernel.xmp.impl.XMPMetaImpl;
/*     */ import com.itextpdf.kernel.xmp.impl.XMPMetaParser;
/*     */ import com.itextpdf.kernel.xmp.impl.XMPSchemaRegistryImpl;
/*     */ import com.itextpdf.kernel.xmp.impl.XMPSerializerHelper;
/*     */ import com.itextpdf.kernel.xmp.options.ParseOptions;
/*     */ import com.itextpdf.kernel.xmp.options.SerializeOptions;
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class XMPMetaFactory
/*     */ {
/*  50 */   private static final Object staticLock = new Object();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  55 */   private static XMPSchemaRegistry schema = (XMPSchemaRegistry)new XMPSchemaRegistryImpl();
/*     */ 
/*     */ 
/*     */   
/*  59 */   private static XMPVersionInfo versionInfo = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static XMPSchemaRegistry getSchemaRegistry() {
/*  72 */     return schema;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static XMPMeta create() {
/*  79 */     return (XMPMeta)new XMPMetaImpl();
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
/*     */   public static XMPMeta parse(InputStream in) throws XMPException {
/*  91 */     return parse(in, null);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static XMPMeta parse(InputStream in, ParseOptions options) throws XMPException {
/* 117 */     return XMPMetaParser.parse(in, options);
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
/*     */   public static XMPMeta parseFromString(String packet) throws XMPException {
/* 129 */     return parseFromString(packet, null);
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
/*     */   public static XMPMeta parseFromString(String packet, ParseOptions options) throws XMPException {
/* 143 */     return XMPMetaParser.parse(packet, options);
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
/*     */   public static XMPMeta parseFromBuffer(byte[] buffer) throws XMPException {
/* 155 */     return parseFromBuffer(buffer, null);
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
/*     */   public static XMPMeta parseFromBuffer(byte[] buffer, ParseOptions options) throws XMPException {
/* 169 */     return XMPMetaParser.parse(buffer, options);
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
/*     */   public static void serialize(XMPMeta xmp, OutputStream out) throws XMPException {
/* 181 */     serialize(xmp, out, null);
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
/*     */   public static void serialize(XMPMeta xmp, OutputStream out, SerializeOptions options) throws XMPException {
/* 194 */     assertImplementation(xmp);
/* 195 */     XMPSerializerHelper.serialize((XMPMetaImpl)xmp, out, options);
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
/*     */   public static byte[] serializeToBuffer(XMPMeta xmp, SerializeOptions options) throws XMPException {
/* 208 */     assertImplementation(xmp);
/* 209 */     return XMPSerializerHelper.serializeToBuffer((XMPMetaImpl)xmp, options);
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
/*     */   public static String serializeToString(XMPMeta xmp, SerializeOptions options) throws XMPException {
/* 223 */     assertImplementation(xmp);
/* 224 */     return XMPSerializerHelper.serializeToString((XMPMetaImpl)xmp, options);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void assertImplementation(XMPMeta xmp) {
/* 231 */     if (!(xmp instanceof XMPMetaImpl)) {
/* 232 */       throw new UnsupportedOperationException("The serializing service works onlywith the XMPMeta implementation of this library");
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void reset() {
/* 243 */     schema = (XMPSchemaRegistry)new XMPSchemaRegistryImpl();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static XMPVersionInfo getVersionInfo() {
/* 253 */     synchronized (staticLock) {
/* 254 */       if (versionInfo == null) {
/*     */         try {
/* 256 */           int major = 5;
/* 257 */           int minor = 1;
/* 258 */           int micro = 0;
/* 259 */           int engBuild = 3;
/* 260 */           boolean debug = false;
/*     */ 
/*     */           
/* 263 */           String message = "Adobe XMP Core 5.1.0-jc003";
/*     */ 
/*     */           
/* 266 */           versionInfo = new XMPVersionInfo() {
/*     */               public int getMajor() {
/* 268 */                 return 5;
/*     */               }
/*     */               
/*     */               public int getMinor() {
/* 272 */                 return 1;
/*     */               }
/*     */               
/*     */               public int getMicro() {
/* 276 */                 return 0;
/*     */               }
/*     */               
/*     */               public boolean isDebug() {
/* 280 */                 return false;
/*     */               }
/*     */               
/*     */               public int getBuild() {
/* 284 */                 return 3;
/*     */               }
/*     */               
/*     */               public String getMessage() {
/* 288 */                 return "Adobe XMP Core 5.1.0-jc003";
/*     */               }
/*     */               
/*     */               public String toString() {
/* 292 */                 return "Adobe XMP Core 5.1.0-jc003";
/*     */               }
/*     */             };
/*     */         }
/* 296 */         catch (Throwable e) {
/*     */           
/* 298 */           System.out.println(e);
/*     */         } 
/*     */       }
/* 301 */       return versionInfo;
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/kernel/xmp/XMPMetaFactory.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */