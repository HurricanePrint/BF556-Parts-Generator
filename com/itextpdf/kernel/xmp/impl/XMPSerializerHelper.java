/*     */ package com.itextpdf.kernel.xmp.impl;
/*     */ 
/*     */ import com.itextpdf.kernel.xmp.XMPException;
/*     */ import com.itextpdf.kernel.xmp.options.SerializeOptions;
/*     */ import java.io.ByteArrayOutputStream;
/*     */ import java.io.OutputStream;
/*     */ import java.io.UnsupportedEncodingException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class XMPSerializerHelper
/*     */ {
/*     */   public static void serialize(XMPMetaImpl xmp, OutputStream output, SerializeOptions options) throws XMPException {
/*  63 */     options = (options != null) ? options : new SerializeOptions();
/*     */ 
/*     */     
/*  66 */     if (options.getSort())
/*     */     {
/*  68 */       xmp.sort();
/*     */     }
/*  70 */     (new XMPSerializerRDF()).serialize(xmp, output, options);
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
/*     */   public static String serializeToString(XMPMetaImpl xmp, SerializeOptions options) throws XMPException {
/*  89 */     options = (options != null) ? options : new SerializeOptions();
/*  90 */     options.setEncodeUTF16BE(true);
/*     */     
/*  92 */     ByteArrayOutputStream output = new ByteArrayOutputStream(2048);
/*  93 */     serialize(xmp, output, options);
/*     */ 
/*     */     
/*     */     try {
/*  97 */       return output.toString(options.getEncoding());
/*     */     }
/*  99 */     catch (UnsupportedEncodingException e) {
/*     */ 
/*     */ 
/*     */       
/* 103 */       return output.toString();
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
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static byte[] serializeToBuffer(XMPMetaImpl xmp, SerializeOptions options) throws XMPException {
/* 119 */     ByteArrayOutputStream out = new ByteArrayOutputStream(2048);
/* 120 */     serialize(xmp, out, options);
/* 121 */     return out.toByteArray();
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/kernel/xmp/impl/XMPSerializerHelper.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */