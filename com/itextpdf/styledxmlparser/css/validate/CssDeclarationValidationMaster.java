/*     */ package com.itextpdf.styledxmlparser.css.validate;
/*     */ 
/*     */ import com.itextpdf.styledxmlparser.css.CommonCssConstants;
/*     */ import com.itextpdf.styledxmlparser.css.CssDeclaration;
/*     */ import com.itextpdf.styledxmlparser.css.validate.impl.datatype.ArrayDataTypeValidator;
/*     */ import com.itextpdf.styledxmlparser.css.validate.impl.datatype.CssBackgroundValidator;
/*     */ import com.itextpdf.styledxmlparser.css.validate.impl.datatype.CssBlendModeValidator;
/*     */ import com.itextpdf.styledxmlparser.css.validate.impl.datatype.CssColorValidator;
/*     */ import com.itextpdf.styledxmlparser.css.validate.impl.datatype.CssEnumValidator;
/*     */ import com.itextpdf.styledxmlparser.css.validate.impl.datatype.CssNumericValueValidator;
/*     */ import com.itextpdf.styledxmlparser.css.validate.impl.datatype.CssQuotesValidator;
/*     */ import com.itextpdf.styledxmlparser.css.validate.impl.datatype.CssTransformValidator;
/*     */ import com.itextpdf.styledxmlparser.css.validate.impl.declaration.MultiTypeDeclarationValidator;
/*     */ import com.itextpdf.styledxmlparser.css.validate.impl.declaration.SingleTypeDeclarationValidator;
/*     */ import java.util.HashMap;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class CssDeclarationValidationMaster
/*     */ {
/*     */   static {
/*  74 */     MultiTypeDeclarationValidator multiTypeDeclarationValidator = new MultiTypeDeclarationValidator(new ICssDataTypeValidator[] { (ICssDataTypeValidator)new CssEnumValidator(new String[] { "transparent", "initial", "inherit", "currentcolor" }), (ICssDataTypeValidator)new CssColorValidator() });
/*     */   }
/*     */ 
/*     */   
/*  78 */   private static final Map<String, ICssDeclarationValidator> DEFAULT_VALIDATORS = new HashMap<>(); static {
/*  79 */     DEFAULT_VALIDATORS.put("background-color", multiTypeDeclarationValidator);
/*  80 */     DEFAULT_VALIDATORS.put("color", multiTypeDeclarationValidator);
/*  81 */     DEFAULT_VALIDATORS.put("border-color", multiTypeDeclarationValidator);
/*  82 */     DEFAULT_VALIDATORS.put("border-bottom-color", multiTypeDeclarationValidator);
/*  83 */     DEFAULT_VALIDATORS.put("border-top-color", multiTypeDeclarationValidator);
/*  84 */     DEFAULT_VALIDATORS.put("border-left-color", multiTypeDeclarationValidator);
/*  85 */     DEFAULT_VALIDATORS.put("border-right-color", multiTypeDeclarationValidator);
/*  86 */     DEFAULT_VALIDATORS.put("float", new SingleTypeDeclarationValidator((ICssDataTypeValidator)new CssEnumValidator(new String[] { "left", "right", "none", "inherit", "center" })));
/*     */ 
/*     */     
/*  89 */     DEFAULT_VALIDATORS.put("page-break-before", new SingleTypeDeclarationValidator((ICssDataTypeValidator)new CssEnumValidator(new String[] { "auto", "always", "avoid", "left", "right" })));
/*     */ 
/*     */     
/*  92 */     DEFAULT_VALIDATORS.put("page-break-after", new SingleTypeDeclarationValidator((ICssDataTypeValidator)new CssEnumValidator(new String[] { "auto", "always", "avoid", "left", "right" })));
/*     */ 
/*     */     
/*  95 */     DEFAULT_VALIDATORS.put("quotes", new MultiTypeDeclarationValidator(new ICssDataTypeValidator[] { (ICssDataTypeValidator)new CssEnumValidator(new String[] { "initial", "inherit", "none" }), (ICssDataTypeValidator)new CssQuotesValidator() }));
/*     */ 
/*     */ 
/*     */     
/*  99 */     DEFAULT_VALIDATORS.put("transform", new SingleTypeDeclarationValidator((ICssDataTypeValidator)new CssTransformValidator()));
/*     */ 
/*     */     
/* 102 */     CssEnumValidator enumValidator = new CssEnumValidator(new String[] { "larger", "smaller" });
/*     */     
/* 104 */     enumValidator.addAllowedValues(CommonCssConstants.FONT_ABSOLUTE_SIZE_KEYWORDS_VALUES.keySet());
/* 105 */     DEFAULT_VALIDATORS.put("font-size", new MultiTypeDeclarationValidator(new ICssDataTypeValidator[] { (ICssDataTypeValidator)new CssNumericValueValidator(true, false), (ICssDataTypeValidator)enumValidator }));
/*     */     
/* 107 */     DEFAULT_VALIDATORS.put("word-spacing", new SingleTypeDeclarationValidator((ICssDataTypeValidator)new CssNumericValueValidator(false, true)));
/*     */     
/* 109 */     DEFAULT_VALIDATORS.put("letter-spacing", new SingleTypeDeclarationValidator((ICssDataTypeValidator)new CssNumericValueValidator(false, true)));
/*     */     
/* 111 */     DEFAULT_VALIDATORS.put("text-indent", new SingleTypeDeclarationValidator((ICssDataTypeValidator)new CssNumericValueValidator(true, false)));
/*     */     
/* 113 */     DEFAULT_VALIDATORS.put("line-height", new SingleTypeDeclarationValidator((ICssDataTypeValidator)new CssNumericValueValidator(true, true)));
/*     */     
/* 115 */     DEFAULT_VALIDATORS.put("background-repeat", new SingleTypeDeclarationValidator((ICssDataTypeValidator)new CssBackgroundValidator("background-repeat")));
/*     */     
/* 117 */     DEFAULT_VALIDATORS.put("background-image", new SingleTypeDeclarationValidator((ICssDataTypeValidator)new CssBackgroundValidator("background-image")));
/*     */     
/* 119 */     DEFAULT_VALIDATORS.put("background-position-x", new SingleTypeDeclarationValidator((ICssDataTypeValidator)new CssBackgroundValidator("background-position-x")));
/*     */     
/* 121 */     DEFAULT_VALIDATORS.put("background-position-y", new SingleTypeDeclarationValidator((ICssDataTypeValidator)new CssBackgroundValidator("background-position-y")));
/*     */     
/* 123 */     DEFAULT_VALIDATORS.put("background-size", new SingleTypeDeclarationValidator((ICssDataTypeValidator)new CssBackgroundValidator("background-size")));
/*     */     
/* 125 */     DEFAULT_VALIDATORS.put("background-clip", new SingleTypeDeclarationValidator((ICssDataTypeValidator)new CssBackgroundValidator("background-clip")));
/*     */     
/* 127 */     DEFAULT_VALIDATORS.put("background-origin", new SingleTypeDeclarationValidator((ICssDataTypeValidator)new CssBackgroundValidator("background-origin")));
/*     */     
/* 129 */     DEFAULT_VALIDATORS.put("background-blend-mode", new SingleTypeDeclarationValidator((ICssDataTypeValidator)new ArrayDataTypeValidator((ICssDataTypeValidator)new CssBlendModeValidator())));
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
/*     */   public static boolean checkDeclaration(CssDeclaration declaration) {
/* 146 */     ICssDeclarationValidator validator = DEFAULT_VALIDATORS.get(declaration.getProperty());
/* 147 */     return (validator == null || validator.isValid(declaration));
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/styledxmlparser/css/validate/CssDeclarationValidationMaster.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */