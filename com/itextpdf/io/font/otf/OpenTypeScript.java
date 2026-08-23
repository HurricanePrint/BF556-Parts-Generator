/*     */ package com.itextpdf.io.font.otf;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.Serializable;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class OpenTypeScript
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 381398146861429491L;
/*  53 */   public final String DEFAULT_SCRIPT = "DFLT";
/*     */   
/*     */   private OpenTypeFontTableReader openTypeReader;
/*     */   private List<ScriptRecord> records;
/*     */   
/*     */   public OpenTypeScript(OpenTypeFontTableReader openTypeReader, int locationScriptTable) throws IOException {
/*  59 */     this.openTypeReader = openTypeReader;
/*  60 */     this.records = new ArrayList<>();
/*  61 */     openTypeReader.rf.seek(locationScriptTable);
/*  62 */     TagAndLocation[] tagsLocs = openTypeReader.readTagAndLocations(locationScriptTable);
/*  63 */     for (TagAndLocation tagLoc : tagsLocs) {
/*  64 */       readScriptRecord(tagLoc);
/*     */     }
/*     */   }
/*     */   
/*     */   public List<ScriptRecord> getScriptRecords() {
/*  69 */     return this.records;
/*     */   }
/*     */   
/*     */   public LanguageRecord getLanguageRecord(String[] scripts, String language) {
/*  73 */     ScriptRecord scriptFound = null;
/*  74 */     ScriptRecord scriptDefault = null;
/*  75 */     for (ScriptRecord sr : this.records) {
/*  76 */       if ("DFLT".equals(sr.tag)) {
/*  77 */         scriptDefault = sr;
/*     */         break;
/*     */       } 
/*     */     } 
/*  81 */     for (String script : scripts) {
/*  82 */       for (ScriptRecord sr : this.records) {
/*  83 */         if (sr.tag.equals(script)) {
/*  84 */           scriptFound = sr;
/*     */           break;
/*     */         } 
/*  87 */         if ("DFLT".equals(script)) {
/*  88 */           scriptDefault = sr;
/*     */         }
/*     */       } 
/*  91 */       if (scriptFound != null) {
/*     */         break;
/*     */       }
/*     */     } 
/*  95 */     if (scriptFound == null) {
/*  96 */       scriptFound = scriptDefault;
/*     */     }
/*  98 */     if (scriptFound == null) {
/*  99 */       return null;
/*     */     }
/* 101 */     LanguageRecord lang = null;
/* 102 */     for (LanguageRecord lr : scriptFound.languages) {
/* 103 */       if (lr.tag.equals(language)) {
/* 104 */         lang = lr;
/*     */         break;
/*     */       } 
/*     */     } 
/* 108 */     if (lang == null) {
/* 109 */       lang = scriptFound.defaultLanguage;
/*     */     }
/* 111 */     return lang;
/*     */   }
/*     */   
/*     */   private void readScriptRecord(TagAndLocation tagLoc) throws IOException {
/* 115 */     this.openTypeReader.rf.seek(tagLoc.location);
/* 116 */     int locationDefaultLanguage = this.openTypeReader.rf.readUnsignedShort();
/* 117 */     if (locationDefaultLanguage > 0) {
/* 118 */       locationDefaultLanguage += tagLoc.location;
/*     */     }
/* 120 */     TagAndLocation[] tagsLocs = this.openTypeReader.readTagAndLocations(tagLoc.location);
/* 121 */     ScriptRecord srec = new ScriptRecord();
/* 122 */     srec.tag = tagLoc.tag;
/* 123 */     srec.languages = new LanguageRecord[tagsLocs.length];
/* 124 */     for (int k = 0; k < tagsLocs.length; k++) {
/* 125 */       srec.languages[k] = readLanguageRecord(tagsLocs[k]);
/*     */     }
/* 127 */     if (locationDefaultLanguage > 0) {
/* 128 */       TagAndLocation t = new TagAndLocation();
/* 129 */       t.tag = "";
/* 130 */       t.location = locationDefaultLanguage;
/* 131 */       srec.defaultLanguage = readLanguageRecord(t);
/*     */     } 
/* 133 */     this.records.add(srec);
/*     */   }
/*     */   
/*     */   private LanguageRecord readLanguageRecord(TagAndLocation tagLoc) throws IOException {
/* 137 */     LanguageRecord rec = new LanguageRecord();
/*     */     
/* 139 */     this.openTypeReader.rf.seek((tagLoc.location + 2));
/* 140 */     rec.featureRequired = this.openTypeReader.rf.readUnsignedShort();
/* 141 */     int count = this.openTypeReader.rf.readUnsignedShort();
/* 142 */     rec.features = this.openTypeReader.readUShortArray(count);
/* 143 */     rec.tag = tagLoc.tag;
/* 144 */     return rec;
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/io/font/otf/OpenTypeScript.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */