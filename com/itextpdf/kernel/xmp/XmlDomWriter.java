/*     */ package com.itextpdf.kernel.xmp;
/*     */ 
/*     */ import java.io.OutputStream;
/*     */ import java.io.OutputStreamWriter;
/*     */ import java.io.PrintWriter;
/*     */ import java.nio.charset.Charset;
/*     */ import org.w3c.dom.Attr;
/*     */ import org.w3c.dom.Document;
/*     */ import org.w3c.dom.DocumentType;
/*     */ import org.w3c.dom.NamedNodeMap;
/*     */ import org.w3c.dom.Node;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class XmlDomWriter
/*     */ {
/*     */   protected PrintWriter fOut;
/*     */   protected boolean fCanonical;
/*     */   protected boolean fXML11;
/*     */   
/*     */   public XmlDomWriter() {}
/*     */   
/*     */   public XmlDomWriter(boolean canonical) {
/*  66 */     this.fCanonical = canonical;
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
/*     */   public void setCanonical(boolean canonical) {
/*  79 */     this.fCanonical = canonical;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setOutput(OutputStream stream, String encoding) {
/*  90 */     if (encoding == null) {
/*  91 */       encoding = "UTF8";
/*     */     }
/*     */     
/*  94 */     OutputStreamWriter osw = new OutputStreamWriter(stream, Charset.forName(encoding));
/*  95 */     this.fOut = new PrintWriter(osw);
/*     */   }
/*     */ 
/*     */   
/*     */   public void write(Node node) {
/*     */     Document document;
/*     */     DocumentType doctype;
/*     */     Attr[] attrs;
/*     */     String data, publicId;
/*     */     int i;
/*     */     Node child;
/*     */     String systemId, internalSubset;
/* 107 */     if (node == null) {
/*     */       return;
/*     */     }
/*     */     
/* 111 */     short type = node.getNodeType();
/* 112 */     switch (type) {
/*     */       case 9:
/* 114 */         document = (Document)node;
/* 115 */         this.fXML11 = false;
/* 116 */         if (!this.fCanonical) {
/* 117 */           if (this.fXML11) {
/* 118 */             this.fOut.print("<?xml version=\"1.1\" encoding=\"UTF-8\"?>");
/*     */           } else {
/* 120 */             this.fOut.print("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
/*     */           } 
/* 122 */           this.fOut.print("\n");
/* 123 */           this.fOut.flush();
/* 124 */           write(document.getDoctype());
/*     */         } 
/* 126 */         write(document.getDocumentElement());
/*     */         break;
/*     */ 
/*     */       
/*     */       case 10:
/* 131 */         doctype = (DocumentType)node;
/* 132 */         this.fOut.print("<!DOCTYPE ");
/* 133 */         this.fOut.print(doctype.getName());
/* 134 */         publicId = doctype.getPublicId();
/* 135 */         systemId = doctype.getSystemId();
/* 136 */         if (publicId != null) {
/* 137 */           this.fOut.print(" PUBLIC '");
/* 138 */           this.fOut.print(publicId);
/* 139 */           this.fOut.print("' '");
/* 140 */           this.fOut.print(systemId);
/* 141 */           this.fOut.print('\'');
/* 142 */         } else if (systemId != null) {
/* 143 */           this.fOut.print(" SYSTEM '");
/* 144 */           this.fOut.print(systemId);
/* 145 */           this.fOut.print('\'');
/*     */         } 
/* 147 */         internalSubset = doctype.getInternalSubset();
/* 148 */         if (internalSubset != null) {
/* 149 */           this.fOut.println(" [");
/* 150 */           this.fOut.print(internalSubset);
/* 151 */           this.fOut.print(']');
/*     */         } 
/* 153 */         this.fOut.println('>');
/*     */         break;
/*     */ 
/*     */       
/*     */       case 1:
/* 158 */         this.fOut.print('<');
/* 159 */         this.fOut.print(node.getNodeName());
/* 160 */         attrs = sortAttributes(node.getAttributes());
/* 161 */         for (i = 0; i < attrs.length; i++) {
/* 162 */           Attr attr = attrs[i];
/* 163 */           this.fOut.print(' ');
/* 164 */           this.fOut.print(attr.getNodeName());
/* 165 */           this.fOut.print("=\"");
/* 166 */           normalizeAndPrint(attr.getNodeValue(), true);
/* 167 */           this.fOut.print('"');
/*     */         } 
/* 169 */         this.fOut.print('>');
/* 170 */         this.fOut.flush();
/*     */         
/* 172 */         child = node.getFirstChild();
/* 173 */         while (child != null) {
/* 174 */           write(child);
/* 175 */           child = child.getNextSibling();
/*     */         } 
/*     */         break;
/*     */ 
/*     */       
/*     */       case 5:
/* 181 */         if (this.fCanonical) {
/* 182 */           Node node1 = node.getFirstChild();
/* 183 */           while (node1 != null) {
/* 184 */             write(node1);
/* 185 */             node1 = node1.getNextSibling();
/*     */           }  break;
/*     */         } 
/* 188 */         this.fOut.print('&');
/* 189 */         this.fOut.print(node.getNodeName());
/* 190 */         this.fOut.print(';');
/* 191 */         this.fOut.flush();
/*     */         break;
/*     */ 
/*     */ 
/*     */       
/*     */       case 4:
/* 197 */         if (this.fCanonical) {
/* 198 */           normalizeAndPrint(node.getNodeValue(), false);
/*     */         } else {
/* 200 */           this.fOut.print("<![CDATA[");
/* 201 */           this.fOut.print(node.getNodeValue());
/* 202 */           this.fOut.print("]]>");
/*     */         } 
/* 204 */         this.fOut.flush();
/*     */         break;
/*     */ 
/*     */       
/*     */       case 3:
/* 209 */         normalizeAndPrint(node.getNodeValue(), false);
/* 210 */         this.fOut.flush();
/*     */         break;
/*     */ 
/*     */       
/*     */       case 7:
/* 215 */         this.fOut.print("<?");
/* 216 */         this.fOut.print(node.getNodeName());
/* 217 */         data = node.getNodeValue();
/* 218 */         if (data != null && data.length() > 0) {
/* 219 */           this.fOut.print(' ');
/* 220 */           this.fOut.print(data);
/*     */         } 
/* 222 */         this.fOut.print("?>");
/* 223 */         this.fOut.flush();
/*     */         break;
/*     */ 
/*     */       
/*     */       case 8:
/* 228 */         if (!this.fCanonical) {
/* 229 */           this.fOut.print("<!--");
/* 230 */           String comment = node.getNodeValue();
/* 231 */           if (comment != null && comment.length() > 0) {
/* 232 */             this.fOut.print(comment);
/*     */           }
/* 234 */           this.fOut.print("-->");
/* 235 */           this.fOut.flush();
/*     */         } 
/*     */         break;
/*     */     } 
/*     */     
/* 240 */     if (type == 1) {
/* 241 */       this.fOut.print("</");
/* 242 */       this.fOut.print(node.getNodeName());
/* 243 */       this.fOut.print('>');
/* 244 */       this.fOut.flush();
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
/*     */   protected Attr[] sortAttributes(NamedNodeMap attrs) {
/* 257 */     int len = (attrs != null) ? attrs.getLength() : 0;
/* 258 */     Attr[] array = new Attr[len]; int i;
/* 259 */     for (i = 0; i < len; i++) {
/* 260 */       array[i] = (Attr)attrs.item(i);
/*     */     }
/* 262 */     for (i = 0; i < len - 1; i++) {
/* 263 */       String name = array[i].getNodeName();
/* 264 */       int index = i;
/* 265 */       for (int j = i + 1; j < len; j++) {
/* 266 */         String curName = array[j].getNodeName();
/* 267 */         if (curName.compareTo(name) < 0) {
/* 268 */           name = curName;
/* 269 */           index = j;
/*     */         } 
/*     */       } 
/* 272 */       if (index != i) {
/* 273 */         Attr temp = array[i];
/* 274 */         array[i] = array[index];
/* 275 */         array[index] = temp;
/*     */       } 
/*     */     } 
/*     */     
/* 279 */     return array;
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
/*     */   protected void normalizeAndPrint(String s, boolean isAttValue) {
/* 295 */     int len = (s != null) ? s.length() : 0;
/* 296 */     for (int i = 0; i < len; i++) {
/* 297 */       char c = s.charAt(i);
/* 298 */       normalizeAndPrint(c, isAttValue);
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
/*     */   protected void normalizeAndPrint(char c, boolean isAttValue) {
/* 311 */     switch (c) {
/*     */       case '<':
/* 313 */         this.fOut.print("&lt;");
/*     */         return;
/*     */       
/*     */       case '>':
/* 317 */         this.fOut.print("&gt;");
/*     */         return;
/*     */       
/*     */       case '&':
/* 321 */         this.fOut.print("&amp;");
/*     */         return;
/*     */ 
/*     */ 
/*     */       
/*     */       case '"':
/* 327 */         if (isAttValue) {
/* 328 */           this.fOut.print("&quot;");
/*     */         } else {
/* 330 */           this.fOut.print("\"");
/*     */         } 
/*     */         return;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       case '\r':
/* 339 */         this.fOut.print("&#xD;");
/*     */         return;
/*     */       
/*     */       case '\n':
/* 343 */         if (this.fCanonical) {
/* 344 */           this.fOut.print("&#xA;");
/*     */           return;
/*     */         } 
/*     */         break;
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 358 */     if ((this.fXML11 && ((c >= '\001' && c <= '\037' && c != '\t' && c != '\n') || (c >= '' && c <= '') || c == ' ')) || (isAttValue && (c == '\t' || c == '\n'))) {
/*     */ 
/*     */       
/* 361 */       this.fOut.print("&#x");
/* 362 */       this.fOut.print(Integer.toHexString(c).toUpperCase());
/* 363 */       this.fOut.print(";");
/*     */     } else {
/* 365 */       this.fOut.print(c);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/kernel/xmp/XmlDomWriter.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */