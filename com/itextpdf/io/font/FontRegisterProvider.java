/*     */ package com.itextpdf.io.font;
/*     */ 
/*     */ import com.itextpdf.io.IOException;
/*     */ import com.itextpdf.io.util.FileUtil;
/*     */ import com.itextpdf.io.util.MessageFormatUtil;
/*     */ import java.io.IOException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
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
/*     */ class FontRegisterProvider
/*     */ {
/*  68 */   private static final Logger LOGGER = LoggerFactory.getLogger(FontRegisterProvider.class);
/*     */ 
/*     */ 
/*     */   
/*  72 */   private final Map<String, String> fontNames = new HashMap<>();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  77 */   private final Map<String, List<String>> fontFamilies = new HashMap<>();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   FontRegisterProvider() {
/*  83 */     registerStandardFonts();
/*  84 */     registerStandardFontFamilies();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   FontProgram getFont(String fontName, int style) throws IOException {
/*  95 */     return getFont(fontName, style, true);
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
/*     */   FontProgram getFont(String fontName, int style, boolean cached) throws IOException {
/* 109 */     if (fontName == null)
/* 110 */       return null; 
/* 111 */     String lowerCaseFontName = fontName.toLowerCase();
/*     */     
/* 113 */     List<String> family = !lowerCaseFontName.equalsIgnoreCase("Times-Roman") ? this.fontFamilies.get(lowerCaseFontName) : this.fontFamilies.get("Times".toLowerCase());
/* 114 */     if (family != null) {
/* 115 */       synchronized (family) {
/*     */         
/* 117 */         int s = (style == -1) ? 0 : style;
/* 118 */         for (String f : family) {
/* 119 */           String lcf = f.toLowerCase();
/* 120 */           int fs = 0;
/* 121 */           if (lcf.contains("bold")) fs |= 0x1; 
/* 122 */           if (lcf.contains("italic") || lcf.contains("oblique")) fs |= 0x2; 
/* 123 */           if ((s & 0x3) == fs) {
/* 124 */             fontName = f;
/*     */             break;
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     }
/* 130 */     return getFontProgram(fontName, cached);
/*     */   }
/*     */   
/*     */   protected void registerStandardFonts() {
/* 134 */     this.fontNames.put("Courier".toLowerCase(), "Courier");
/* 135 */     this.fontNames.put("Courier-Bold".toLowerCase(), "Courier-Bold");
/* 136 */     this.fontNames.put("Courier-Oblique".toLowerCase(), "Courier-Oblique");
/* 137 */     this.fontNames.put("Courier-BoldOblique".toLowerCase(), "Courier-BoldOblique");
/* 138 */     this.fontNames.put("Helvetica".toLowerCase(), "Helvetica");
/* 139 */     this.fontNames.put("Helvetica-Bold".toLowerCase(), "Helvetica-Bold");
/* 140 */     this.fontNames.put("Helvetica-Oblique".toLowerCase(), "Helvetica-Oblique");
/* 141 */     this.fontNames.put("Helvetica-BoldOblique".toLowerCase(), "Helvetica-BoldOblique");
/* 142 */     this.fontNames.put("Symbol".toLowerCase(), "Symbol");
/* 143 */     this.fontNames.put("Times-Roman".toLowerCase(), "Times-Roman");
/* 144 */     this.fontNames.put("Times-Bold".toLowerCase(), "Times-Bold");
/* 145 */     this.fontNames.put("Times-Italic".toLowerCase(), "Times-Italic");
/* 146 */     this.fontNames.put("Times-BoldItalic".toLowerCase(), "Times-BoldItalic");
/* 147 */     this.fontNames.put("ZapfDingbats".toLowerCase(), "ZapfDingbats");
/*     */   }
/*     */ 
/*     */   
/*     */   protected void registerStandardFontFamilies() {
/* 152 */     List<String> family = new ArrayList<>();
/* 153 */     family.add("Courier");
/* 154 */     family.add("Courier-Bold");
/* 155 */     family.add("Courier-Oblique");
/* 156 */     family.add("Courier-BoldOblique");
/* 157 */     this.fontFamilies.put("Courier".toLowerCase(), family);
/* 158 */     family = new ArrayList<>();
/* 159 */     family.add("Helvetica");
/* 160 */     family.add("Helvetica-Bold");
/* 161 */     family.add("Helvetica-Oblique");
/* 162 */     family.add("Helvetica-BoldOblique");
/* 163 */     this.fontFamilies.put("Helvetica".toLowerCase(), family);
/* 164 */     family = new ArrayList<>();
/* 165 */     family.add("Symbol");
/* 166 */     this.fontFamilies.put("Symbol".toLowerCase(), family);
/* 167 */     family = new ArrayList<>();
/* 168 */     family.add("Times-Roman");
/* 169 */     family.add("Times-Bold");
/* 170 */     family.add("Times-Italic");
/* 171 */     family.add("Times-BoldItalic");
/* 172 */     this.fontFamilies.put("Times".toLowerCase(), family);
/* 173 */     family = new ArrayList<>();
/* 174 */     family.add("ZapfDingbats");
/* 175 */     this.fontFamilies.put("ZapfDingbats".toLowerCase(), family);
/*     */   }
/*     */   
/*     */   protected FontProgram getFontProgram(String fontName, boolean cached) throws IOException {
/* 179 */     FontProgram fontProgram = null;
/* 180 */     fontName = this.fontNames.get(fontName.toLowerCase());
/* 181 */     if (fontName != null) {
/* 182 */       fontProgram = FontProgramFactory.createFont(fontName, cached);
/*     */     }
/* 184 */     return fontProgram;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void registerFontFamily(String familyName, String fullName, String path) {
/*     */     List<String> family;
/* 195 */     if (path != null) {
/* 196 */       this.fontNames.put(fullName, path);
/*     */     }
/* 198 */     synchronized (this.fontFamilies) {
/* 199 */       family = this.fontFamilies.get(familyName);
/* 200 */       if (family == null) {
/* 201 */         family = new ArrayList<>();
/* 202 */         this.fontFamilies.put(familyName, family);
/*     */       } 
/*     */     } 
/* 205 */     synchronized (family) {
/* 206 */       if (!family.contains(fullName)) {
/* 207 */         int fullNameLength = fullName.length();
/* 208 */         boolean inserted = false;
/* 209 */         for (int j = 0; j < family.size(); j++) {
/* 210 */           if (((String)family.get(j)).length() >= fullNameLength) {
/* 211 */             family.add(j, fullName);
/* 212 */             inserted = true;
/*     */             break;
/*     */           } 
/*     */         } 
/* 216 */         if (!inserted) {
/* 217 */           family.add(fullName);
/* 218 */           String newFullName = fullName.toLowerCase();
/* 219 */           if (newFullName.endsWith("regular")) {
/*     */             
/* 221 */             newFullName = newFullName.substring(0, newFullName.length() - 7).trim();
/*     */             
/* 223 */             family.add(0, fullName.substring(0, newFullName.length()));
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void registerFont(String path) {
/* 237 */     registerFont(path, null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void registerFont(String path, String alias) {
/*     */     try {
/* 248 */       if (path.toLowerCase().endsWith(".ttf") || path.toLowerCase().endsWith(".otf") || path.toLowerCase().indexOf(".ttc,") > 0) {
/* 249 */         FontProgramDescriptor descriptor = FontProgramDescriptorFactory.fetchDescriptor(path);
/* 250 */         this.fontNames.put(descriptor.getFontNameLowerCase(), path);
/* 251 */         if (alias != null) {
/* 252 */           String lcAlias = alias.toLowerCase();
/* 253 */           this.fontNames.put(lcAlias, path);
/* 254 */           if (lcAlias.endsWith("regular"))
/*     */           {
/* 256 */             saveCopyOfRegularFont(lcAlias, path);
/*     */           }
/*     */         } 
/*     */         
/* 260 */         for (String name : descriptor.getFullNameAllLangs()) {
/* 261 */           this.fontNames.put(name, path);
/* 262 */           if (name.endsWith("regular"))
/*     */           {
/* 264 */             saveCopyOfRegularFont(name, path);
/*     */           }
/*     */         } 
/*     */         
/* 268 */         if (descriptor.getFamilyNameEnglishOpenType() != null) {
/* 269 */           for (String fullName : descriptor.getFullNamesEnglishOpenType()) {
/* 270 */             registerFontFamily(descriptor.getFamilyNameEnglishOpenType(), fullName, null);
/*     */           }
/*     */         }
/* 273 */       } else if (path.toLowerCase().endsWith(".ttc")) {
/* 274 */         TrueTypeCollection ttc = new TrueTypeCollection(path);
/* 275 */         for (int i = 0; i < ttc.getTTCSize(); i++) {
/* 276 */           String fullPath = path + "," + i;
/* 277 */           if (alias != null) {
/* 278 */             registerFont(fullPath, alias + "," + i);
/*     */           } else {
/* 280 */             registerFont(fullPath);
/*     */           } 
/*     */         } 
/* 283 */       } else if (path.toLowerCase().endsWith(".afm") || path.toLowerCase().endsWith(".pfm")) {
/* 284 */         FontProgramDescriptor descriptor = FontProgramDescriptorFactory.fetchDescriptor(path);
/* 285 */         registerFontFamily(descriptor.getFamilyNameLowerCase(), descriptor.getFullNameLowerCase(), null);
/* 286 */         this.fontNames.put(descriptor.getFontNameLowerCase(), path);
/* 287 */         this.fontNames.put(descriptor.getFullNameLowerCase(), path);
/*     */       } 
/* 289 */       LOGGER.trace(MessageFormatUtil.format("Registered {0}", new Object[] { path }));
/* 290 */     } catch (IOException e) {
/* 291 */       throw new IOException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   boolean saveCopyOfRegularFont(String regularFontName, String path) {
/* 300 */     String alias = regularFontName.substring(0, regularFontName.length() - 7).trim();
/* 301 */     if (!this.fontNames.containsKey(alias)) {
/* 302 */       this.fontNames.put(alias, path);
/* 303 */       return true;
/*     */     } 
/* 305 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   int registerFontDirectory(String dir) {
/* 315 */     return registerFontDirectory(dir, false);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   int registerFontDirectory(String dir, boolean scanSubdirectories) {
/* 326 */     LOGGER.debug(MessageFormatUtil.format("Registering directory {0}, looking for fonts", new Object[] { dir }));
/* 327 */     int count = 0;
/*     */     try {
/* 329 */       String[] files = FileUtil.listFilesInDirectory(dir, scanSubdirectories);
/* 330 */       if (files == null)
/* 331 */         return 0; 
/* 332 */       for (String file : files) {
/*     */         try {
/* 334 */           String suffix = (file.length() < 4) ? null : file.substring(file.length() - 4).toLowerCase();
/* 335 */           if (".afm".equals(suffix) || ".pfm".equals(suffix)) {
/*     */             
/* 337 */             String pfb = file.substring(0, file.length() - 4) + ".pfb";
/* 338 */             if (FileUtil.fileExists(pfb)) {
/* 339 */               registerFont(file, null);
/* 340 */               count++;
/*     */             } 
/* 342 */           } else if (".ttf".equals(suffix) || ".otf".equals(suffix) || ".ttc".equals(suffix)) {
/* 343 */             registerFont(file, null);
/* 344 */             count++;
/*     */           } 
/* 346 */         } catch (Exception exception) {}
/*     */       }
/*     */     
/*     */     }
/* 350 */     catch (Exception exception) {}
/*     */ 
/*     */     
/* 353 */     return count;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   int registerSystemFontDirectories() {
/* 363 */     int count = 0;
/*     */     
/* 365 */     String[] withSubDirs = { FileUtil.getFontsDir(), "/usr/share/X11/fonts", "/usr/X/lib/X11/fonts", "/usr/openwin/lib/X11/fonts", "/usr/share/fonts", "/usr/X11R6/lib/X11/fonts" };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 372 */     for (String directory : withSubDirs) {
/* 373 */       count += registerFontDirectory(directory, true);
/*     */     }
/*     */     
/* 376 */     String[] withoutSubDirs = { "/Library/Fonts", "/System/Library/Fonts" };
/*     */ 
/*     */ 
/*     */     
/* 380 */     for (String directory : withoutSubDirs) {
/* 381 */       count += registerFontDirectory(directory, false);
/*     */     }
/*     */     
/* 384 */     return count;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   Set<String> getRegisteredFonts() {
/* 393 */     return this.fontNames.keySet();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   Set<String> getRegisteredFontFamilies() {
/* 402 */     return this.fontFamilies.keySet();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   boolean isRegisteredFont(String fontname) {
/* 412 */     return this.fontNames.containsKey(fontname.toLowerCase());
/*     */   }
/*     */   
/*     */   public void clearRegisteredFonts() {
/* 416 */     this.fontNames.clear();
/* 417 */     registerStandardFonts();
/*     */   }
/*     */   
/*     */   public void clearRegisteredFontFamilies() {
/* 421 */     this.fontFamilies.clear();
/* 422 */     registerStandardFontFamilies();
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/io/font/FontRegisterProvider.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */