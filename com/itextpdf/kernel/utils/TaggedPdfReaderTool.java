/*     */ package com.itextpdf.kernel.utils;
/*     */ 
/*     */ import com.itextpdf.io.IOException;
/*     */ import com.itextpdf.kernel.PdfException;
/*     */ import com.itextpdf.kernel.pdf.PdfArray;
/*     */ import com.itextpdf.kernel.pdf.PdfDictionary;
/*     */ import com.itextpdf.kernel.pdf.PdfDocument;
/*     */ import com.itextpdf.kernel.pdf.PdfName;
/*     */ import com.itextpdf.kernel.pdf.PdfObject;
/*     */ import com.itextpdf.kernel.pdf.PdfPage;
/*     */ import com.itextpdf.kernel.pdf.PdfString;
/*     */ import com.itextpdf.kernel.pdf.canvas.parser.EventType;
/*     */ import com.itextpdf.kernel.pdf.canvas.parser.PdfCanvasProcessor;
/*     */ import com.itextpdf.kernel.pdf.canvas.parser.data.IEventData;
/*     */ import com.itextpdf.kernel.pdf.canvas.parser.data.TextRenderInfo;
/*     */ import com.itextpdf.kernel.pdf.canvas.parser.listener.IEventListener;
/*     */ import com.itextpdf.kernel.pdf.canvas.parser.listener.ITextExtractionStrategy;
/*     */ import com.itextpdf.kernel.pdf.canvas.parser.listener.LocationTextExtractionStrategy;
/*     */ import com.itextpdf.kernel.pdf.tagging.IStructureNode;
/*     */ import com.itextpdf.kernel.pdf.tagging.PdfMcr;
/*     */ import com.itextpdf.kernel.pdf.tagging.PdfObjRef;
/*     */ import com.itextpdf.kernel.pdf.tagging.PdfStructElem;
/*     */ import com.itextpdf.kernel.pdf.tagging.PdfStructTreeRoot;
/*     */ import java.io.IOException;
/*     */ import java.io.OutputStream;
/*     */ import java.io.OutputStreamWriter;
/*     */ import java.nio.charset.Charset;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class TaggedPdfReaderTool
/*     */ {
/*     */   protected PdfDocument document;
/*     */   protected OutputStreamWriter out;
/*     */   protected String rootTag;
/*  86 */   protected Map<PdfDictionary, Map<Integer, String>> parsedTags = new HashMap<>();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TaggedPdfReaderTool(PdfDocument document) {
/*  93 */     this.document = document;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean isValidCharacterValue(int c) {
/* 103 */     return (c == 9 || c == 10 || c == 13 || (c >= 32 && c <= 55295) || (c >= 57344 && c <= 65533) || (c >= 65536 && c <= 1114111));
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
/*     */   public void convertToXml(OutputStream os) throws IOException {
/* 116 */     convertToXml(os, "UTF-8");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void convertToXml(OutputStream os, String charset) throws IOException {
/* 127 */     this.out = new OutputStreamWriter(os, Charset.forName(charset));
/* 128 */     if (this.rootTag != null) {
/* 129 */       this.out.write("<" + this.rootTag + ">" + System.lineSeparator());
/*     */     }
/*     */     
/* 132 */     PdfStructTreeRoot structTreeRoot = this.document.getStructTreeRoot();
/* 133 */     if (structTreeRoot == null) {
/* 134 */       throw new PdfException("Document doesn't contain StructTreeRoot.");
/*     */     }
/* 136 */     inspectKids(structTreeRoot.getKids());
/* 137 */     if (this.rootTag != null) {
/* 138 */       this.out.write("</" + this.rootTag + ">");
/*     */     }
/* 140 */     this.out.flush();
/* 141 */     this.out.close();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TaggedPdfReaderTool setRootTag(String rootTagName) {
/* 150 */     this.rootTag = rootTagName;
/* 151 */     return this;
/*     */   }
/*     */   
/*     */   protected void inspectKids(List<IStructureNode> kids) {
/* 155 */     if (kids == null) {
/*     */       return;
/*     */     }
/* 158 */     for (IStructureNode kid : kids) {
/* 159 */       inspectKid(kid);
/*     */     }
/*     */   }
/*     */   
/*     */   protected void inspectKid(IStructureNode kid) {
/*     */     try {
/* 165 */       if (kid instanceof PdfStructElem) {
/* 166 */         PdfStructElem structElemKid = (PdfStructElem)kid;
/* 167 */         PdfName s = structElemKid.getRole();
/* 168 */         String tagN = s.getValue();
/* 169 */         String tag = fixTagName(tagN);
/* 170 */         this.out.write("<");
/* 171 */         this.out.write(tag);
/*     */         
/* 173 */         inspectAttributes(structElemKid);
/*     */         
/* 175 */         this.out.write(">" + System.lineSeparator());
/*     */         
/* 177 */         PdfString alt = structElemKid.getAlt();
/*     */         
/* 179 */         if (alt != null) {
/* 180 */           this.out.write("<alt><![CDATA[");
/* 181 */           this.out.write(alt.getValue().replaceAll("[\\000]*", ""));
/* 182 */           this.out.write("]]></alt>" + System.lineSeparator());
/*     */         } 
/*     */         
/* 185 */         inspectKids(structElemKid.getKids());
/* 186 */         this.out.write("</");
/* 187 */         this.out.write(tag);
/* 188 */         this.out.write(">" + System.lineSeparator());
/* 189 */       } else if (kid instanceof PdfMcr) {
/* 190 */         parseTag((PdfMcr)kid);
/*     */       } else {
/* 192 */         this.out.write(" <flushedKid/> ");
/*     */       } 
/* 194 */     } catch (IOException e) {
/* 195 */       throw new IOException("Unknown I/O exception.", e);
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void inspectAttributes(PdfStructElem kid) {
/* 200 */     PdfObject attrObj = kid.getAttributes(false);
/*     */     
/* 202 */     if (attrObj != null) {
/*     */       PdfDictionary attrDict;
/* 204 */       if (attrObj instanceof PdfArray) {
/* 205 */         attrDict = ((PdfArray)attrObj).getAsDictionary(0);
/*     */       } else {
/* 207 */         attrDict = (PdfDictionary)attrObj;
/*     */       } 
/*     */       try {
/* 210 */         for (PdfName key : attrDict.keySet()) {
/* 211 */           this.out.write(32);
/* 212 */           String attrName = key.getValue();
/* 213 */           this.out.write(Character.toLowerCase(attrName.charAt(0)) + attrName.substring(1));
/* 214 */           this.out.write("=\"");
/* 215 */           this.out.write(attrDict.get(key, false).toString());
/* 216 */           this.out.write("\"");
/*     */         } 
/* 218 */       } catch (IOException e) {
/* 219 */         throw new IOException("Unknown I/O exception.", e);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void parseTag(PdfMcr kid) {
/* 225 */     int mcid = kid.getMcid();
/* 226 */     PdfDictionary pageDic = kid.getPageObject();
/*     */     
/* 228 */     String tagContent = "";
/* 229 */     if (mcid != -1) {
/* 230 */       if (!this.parsedTags.containsKey(pageDic)) {
/* 231 */         MarkedContentEventListener listener = new MarkedContentEventListener();
/*     */         
/* 233 */         PdfCanvasProcessor processor = new PdfCanvasProcessor(listener);
/* 234 */         PdfPage page = this.document.getPage(pageDic);
/* 235 */         processor.processContent(page.getContentBytes(), page.getResources());
/*     */         
/* 237 */         this.parsedTags.put(pageDic, listener.getMcidContent());
/*     */       } 
/*     */       
/* 240 */       if (((Map)this.parsedTags.get(pageDic)).containsKey(Integer.valueOf(mcid))) {
/* 241 */         tagContent = (String)((Map)this.parsedTags.get(pageDic)).get(Integer.valueOf(mcid));
/*     */       }
/*     */     } else {
/* 244 */       PdfObjRef objRef = (PdfObjRef)kid;
/* 245 */       PdfDictionary pdfDictionary = objRef.getReferencedObject();
/* 246 */       if (pdfDictionary.isDictionary()) {
/* 247 */         PdfName subtype = pdfDictionary.getAsName(PdfName.Subtype);
/* 248 */         tagContent = subtype.toString();
/*     */       } 
/*     */     } 
/*     */     try {
/* 252 */       this.out.write(escapeXML(tagContent, true));
/* 253 */     } catch (IOException e) {
/* 254 */       throw new IOException("Unknown I/O exception.", e);
/*     */     } 
/*     */   }
/*     */   
/*     */   protected static String fixTagName(String tag) {
/* 259 */     StringBuilder sb = new StringBuilder();
/* 260 */     for (int k = 0; k < tag.length(); k++) {
/* 261 */       char c = tag.charAt(k);
/* 262 */       boolean nameStart = (c == ':' || (c >= 'A' && c <= 'Z') || c == '_' || (c >= 'a' && c <= 'z') || (c >= 'À' && c <= 'Ö') || (c >= 'Ø' && c <= 'ö') || (c >= 'ø' && c <= '˿') || (c >= 'Ͱ' && c <= 'ͽ') || (c >= 'Ϳ' && c <= '῿') || (c >= '‌' && c <= '‍') || (c >= '⁰' && c <= '↏') || (c >= 'Ⰰ' && c <= '⿯') || (c >= '、' && c <= '퟿') || (c >= '豈' && c <= '﷏') || (c >= 'ﷰ' && c <= '�'));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 278 */       boolean nameMiddle = (c == '-' || c == '.' || (c >= '0' && c <= '9') || c == '·' || (c >= '̀' && c <= 'ͯ') || (c >= '‿' && c <= '⁀') || nameStart);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 286 */       if (k == 0) {
/* 287 */         if (!nameStart) {
/* 288 */           c = '_';
/*     */         }
/* 290 */       } else if (!nameMiddle) {
/* 291 */         c = '-';
/*     */       } 
/* 293 */       sb.append(c);
/*     */     } 
/* 295 */     return sb.toString();
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
/*     */   protected static String escapeXML(String s, boolean onlyASCII) {
/* 308 */     char[] cc = s.toCharArray();
/* 309 */     int len = cc.length;
/* 310 */     StringBuilder sb = new StringBuilder();
/* 311 */     for (int k = 0; k < len; k++) {
/* 312 */       int c = cc[k];
/* 313 */       switch (c) {
/*     */         case 60:
/* 315 */           sb.append("&lt;");
/*     */           break;
/*     */         case 62:
/* 318 */           sb.append("&gt;");
/*     */           break;
/*     */         case 38:
/* 321 */           sb.append("&amp;");
/*     */           break;
/*     */         case 34:
/* 324 */           sb.append("&quot;");
/*     */           break;
/*     */         case 39:
/* 327 */           sb.append("&apos;");
/*     */           break;
/*     */         default:
/* 330 */           if (isValidCharacterValue(c)) {
/* 331 */             if (onlyASCII && c > 127) {
/* 332 */               sb.append("&#").append(c).append(';'); break;
/*     */             } 
/* 334 */             sb.append((char)c);
/*     */           }  break;
/*     */       } 
/*     */     } 
/* 338 */     return sb.toString();
/*     */   }
/*     */   
/*     */   private class MarkedContentEventListener implements IEventListener {
/* 342 */     private Map<Integer, ITextExtractionStrategy> contentByMcid = new HashMap<>();
/*     */     
/*     */     public Map<Integer, String> getMcidContent() {
/* 345 */       Map<Integer, String> content = new HashMap<>();
/* 346 */       for (Iterator<Integer> iterator = this.contentByMcid.keySet().iterator(); iterator.hasNext(); ) { int id = ((Integer)iterator.next()).intValue();
/* 347 */         content.put(Integer.valueOf(id), ((ITextExtractionStrategy)this.contentByMcid.get(Integer.valueOf(id))).getResultantText()); }
/*     */       
/* 349 */       return content;
/*     */     }
/*     */     public void eventOccurred(IEventData data, EventType type) {
/*     */       TextRenderInfo textInfo;
/*     */       int mcid;
/* 354 */       switch (type) {
/*     */         case RENDER_TEXT:
/* 356 */           textInfo = (TextRenderInfo)data;
/* 357 */           mcid = textInfo.getMcid();
/* 358 */           if (mcid != -1) {
/* 359 */             LocationTextExtractionStrategy locationTextExtractionStrategy; ITextExtractionStrategy textExtractionStrategy = this.contentByMcid.get(Integer.valueOf(mcid));
/* 360 */             if (textExtractionStrategy == null) {
/* 361 */               locationTextExtractionStrategy = new LocationTextExtractionStrategy();
/* 362 */               this.contentByMcid.put(Integer.valueOf(mcid), locationTextExtractionStrategy);
/*     */             } 
/* 364 */             locationTextExtractionStrategy.eventOccurred(data, type);
/*     */           } 
/*     */           break;
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Set<EventType> getSupportedEvents() {
/* 374 */       return null;
/*     */     }
/*     */     
/*     */     private MarkedContentEventListener() {}
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/kernel/utils/TaggedPdfReaderTool.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */