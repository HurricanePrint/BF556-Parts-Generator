/*     */ package com.itextpdf.styledxmlparser.jsoup.nodes;
/*     */ 
/*     */ import com.itextpdf.styledxmlparser.jsoup.SerializationException;
/*     */ import com.itextpdf.styledxmlparser.jsoup.helper.Validate;
/*     */ import java.io.IOException;
/*     */ import java.util.Arrays;
/*     */ import java.util.Map;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Attribute
/*     */   implements Map.Entry<String, String>, Cloneable
/*     */ {
/*  57 */   private static final String[] booleanAttributes = new String[] { "allowfullscreen", "async", "autofocus", "checked", "compact", "declare", "default", "defer", "disabled", "formnovalidate", "hidden", "inert", "ismap", "itemscope", "multiple", "muted", "nohref", "noresize", "noshade", "novalidate", "nowrap", "open", "readonly", "required", "reversed", "seamless", "selected", "sortable", "truespeed", "typemustmatch" };
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private String key;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private String value;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Attribute(String key, String value) {
/*  74 */     Validate.notEmpty(key);
/*  75 */     Validate.notNull(value);
/*  76 */     this.key = key.trim().toLowerCase();
/*  77 */     this.value = value;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getKey() {
/*  85 */     return this.key;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setKey(String key) {
/*  93 */     Validate.notEmpty(key);
/*  94 */     this.key = key.trim().toLowerCase();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getValue() {
/* 102 */     return this.value;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String setValue(String value) {
/* 110 */     Validate.notNull(value);
/* 111 */     String old = this.value;
/* 112 */     this.value = value;
/* 113 */     return old;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String html() {
/* 121 */     StringBuilder accum = new StringBuilder();
/*     */     
/*     */     try {
/* 124 */       html(accum, (new Document("")).outputSettings());
/* 125 */     } catch (IOException exception) {
/* 126 */       throw new SerializationException(exception);
/*     */     } 
/* 128 */     return accum.toString();
/*     */   }
/*     */   
/*     */   protected void html(Appendable accum, Document.OutputSettings out) throws IOException {
/* 132 */     accum.append(this.key);
/* 133 */     if (!shouldCollapseAttribute(out)) {
/* 134 */       accum.append("=\"");
/* 135 */       Entities.escape(accum, this.value, out, true, false, false);
/* 136 */       accum.append('"');
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 146 */     return html();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Attribute createFromEncoded(String unencodedKey, String encodedValue) {
/* 156 */     String value = Entities.unescape(encodedValue, true);
/* 157 */     return new Attribute(unencodedKey, value);
/*     */   }
/*     */   
/*     */   protected boolean isDataAttribute() {
/* 161 */     return (this.key.startsWith("data-") && this.key.length() > "data-".length());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected final boolean shouldCollapseAttribute(Document.OutputSettings out) {
/* 171 */     return (("".equals(this.value) || this.value.equalsIgnoreCase(this.key)) && out
/* 172 */       .syntax() == Document.OutputSettings.Syntax.html && 
/* 173 */       isBooleanAttribute());
/*     */   }
/*     */   
/*     */   protected boolean isBooleanAttribute() {
/* 177 */     return (Arrays.binarySearch((Object[])booleanAttributes, this.key) >= 0);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object o) {
/* 182 */     if (this == o) return true; 
/* 183 */     if (!(o instanceof Attribute)) return false;
/*     */     
/* 185 */     Attribute attribute = (Attribute)o;
/*     */     
/* 187 */     if ((this.key != null) ? !this.key.equals(attribute.key) : (attribute.key != null)) return false; 
/* 188 */     if ((this.value != null) ? !this.value.equals(attribute.value) : (attribute.value != null)) return false;
/*     */   
/*     */   }
/*     */   
/*     */   public int hashCode() {
/* 193 */     int result = (this.key != null) ? this.key.hashCode() : 0;
/* 194 */     result = 31 * result + ((this.value != null) ? this.value.hashCode() : 0);
/* 195 */     return result;
/*     */   }
/*     */ 
/*     */   
/*     */   public Object clone() {
/*     */     try {
/* 201 */       return super.clone();
/* 202 */     } catch (CloneNotSupportedException e) {
/* 203 */       throw new RuntimeException(e);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/styledxmlparser/jsoup/nodes/Attribute.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */