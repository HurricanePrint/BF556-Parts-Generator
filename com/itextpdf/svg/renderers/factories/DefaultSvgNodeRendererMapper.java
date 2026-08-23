/*     */ package com.itextpdf.svg.renderers.factories;
/*     */ 
/*     */ import com.itextpdf.svg.SvgConstants;
/*     */ import com.itextpdf.svg.renderers.ISvgNodeRenderer;
/*     */ import com.itextpdf.svg.renderers.impl.CircleSvgNodeRenderer;
/*     */ import com.itextpdf.svg.renderers.impl.ClipPathSvgNodeRenderer;
/*     */ import com.itextpdf.svg.renderers.impl.DefsSvgNodeRenderer;
/*     */ import com.itextpdf.svg.renderers.impl.EllipseSvgNodeRenderer;
/*     */ import com.itextpdf.svg.renderers.impl.GroupSvgNodeRenderer;
/*     */ import com.itextpdf.svg.renderers.impl.ImageSvgNodeRenderer;
/*     */ import com.itextpdf.svg.renderers.impl.LineSvgNodeRenderer;
/*     */ import com.itextpdf.svg.renderers.impl.LinearGradientSvgNodeRenderer;
/*     */ import com.itextpdf.svg.renderers.impl.MarkerSvgNodeRenderer;
/*     */ import com.itextpdf.svg.renderers.impl.PathSvgNodeRenderer;
/*     */ import com.itextpdf.svg.renderers.impl.PolygonSvgNodeRenderer;
/*     */ import com.itextpdf.svg.renderers.impl.PolylineSvgNodeRenderer;
/*     */ import com.itextpdf.svg.renderers.impl.RectangleSvgNodeRenderer;
/*     */ import com.itextpdf.svg.renderers.impl.StopSvgNodeRenderer;
/*     */ import com.itextpdf.svg.renderers.impl.SvgTagSvgNodeRenderer;
/*     */ import com.itextpdf.svg.renderers.impl.SymbolSvgNodeRenderer;
/*     */ import com.itextpdf.svg.renderers.impl.TextSvgBranchRenderer;
/*     */ import com.itextpdf.svg.renderers.impl.TextSvgTSpanBranchRenderer;
/*     */ import com.itextpdf.svg.renderers.impl.UseSvgNodeRenderer;
/*     */ import java.util.Collection;
/*     */ import java.util.HashMap;
/*     */ import java.util.HashSet;
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
/*     */ 
/*     */ 
/*     */ @Deprecated
/*     */ public class DefaultSvgNodeRendererMapper
/*     */   implements ISvgNodeRendererMapper
/*     */ {
/*     */   public Map<String, Class<? extends ISvgNodeRenderer>> getMapping() {
/*  89 */     Map<String, Class<? extends ISvgNodeRenderer>> result = new HashMap<>();
/*     */     
/*  91 */     result.put("circle", CircleSvgNodeRenderer.class);
/*  92 */     result.put(SvgConstants.Tags.CLIP_PATH, ClipPathSvgNodeRenderer.class);
/*  93 */     result.put("defs", DefsSvgNodeRenderer.class);
/*  94 */     result.put("ellipse", EllipseSvgNodeRenderer.class);
/*  95 */     result.put("g", GroupSvgNodeRenderer.class);
/*  96 */     result.put("image", ImageSvgNodeRenderer.class);
/*  97 */     result.put("line", LineSvgNodeRenderer.class);
/*  98 */     result.put(SvgConstants.Tags.LINEAR_GRADIENT, LinearGradientSvgNodeRenderer.class);
/*  99 */     result.put("marker", MarkerSvgNodeRenderer.class);
/* 100 */     result.put("path", PathSvgNodeRenderer.class);
/* 101 */     result.put("polygon", PolygonSvgNodeRenderer.class);
/* 102 */     result.put("polyline", PolylineSvgNodeRenderer.class);
/* 103 */     result.put("rect", RectangleSvgNodeRenderer.class);
/* 104 */     result.put("stop", StopSvgNodeRenderer.class);
/* 105 */     result.put("svg", SvgTagSvgNodeRenderer.class);
/* 106 */     result.put("symbol", SymbolSvgNodeRenderer.class);
/* 107 */     result.put("text", TextSvgBranchRenderer.class);
/* 108 */     result.put("tspan", TextSvgTSpanBranchRenderer.class);
/* 109 */     result.put("use", UseSvgNodeRenderer.class);
/*     */     
/* 111 */     return result;
/*     */   }
/*     */ 
/*     */   
/*     */   public Collection<String> getIgnoredTags() {
/* 116 */     Collection<String> ignored = new HashSet<>();
/*     */ 
/*     */     
/* 119 */     ignored.add("a");
/* 120 */     ignored.add("altGlyph");
/* 121 */     ignored.add("altGlyphDef");
/* 122 */     ignored.add("altGlyphItem");
/*     */     
/* 124 */     ignored.add("color-profile");
/*     */     
/* 126 */     ignored.add("desc");
/*     */     
/* 128 */     ignored.add("feBlend");
/* 129 */     ignored.add("feColorMatrix");
/* 130 */     ignored.add("feComponentTransfer");
/* 131 */     ignored.add("feComposite");
/* 132 */     ignored.add("feConvolveMatrix");
/* 133 */     ignored.add("feDiffuseLighting");
/* 134 */     ignored.add("feDisplacementMap");
/* 135 */     ignored.add("feDistantLight");
/* 136 */     ignored.add("feFlood");
/* 137 */     ignored.add("feFuncA");
/* 138 */     ignored.add("feFuncB");
/* 139 */     ignored.add("feFuncG");
/* 140 */     ignored.add("feFuncR");
/* 141 */     ignored.add("feGaussianBlur");
/* 142 */     ignored.add("feImage");
/* 143 */     ignored.add("feMerge");
/* 144 */     ignored.add("feMergeNode");
/* 145 */     ignored.add("feMorphology");
/* 146 */     ignored.add("feOffset");
/* 147 */     ignored.add("fePointLight");
/* 148 */     ignored.add("feSpecularLighting");
/* 149 */     ignored.add("feSpotLight");
/* 150 */     ignored.add("feTile");
/* 151 */     ignored.add("feTurbulence");
/* 152 */     ignored.add("filter");
/* 153 */     ignored.add("font");
/* 154 */     ignored.add("font-face");
/* 155 */     ignored.add("font-face-format");
/* 156 */     ignored.add("font-face-name");
/* 157 */     ignored.add("font-face-src");
/* 158 */     ignored.add("font-face-uri");
/* 159 */     ignored.add("foreignObject");
/*     */     
/* 161 */     ignored.add("glyph");
/* 162 */     ignored.add("glyphRef");
/*     */     
/* 164 */     ignored.add("hkern");
/*     */     
/* 166 */     ignored.add("mask");
/* 167 */     ignored.add("metadata");
/* 168 */     ignored.add("missing-glyph");
/*     */     
/* 170 */     ignored.add("pattern");
/*     */     
/* 172 */     ignored.add("radialGradient");
/*     */     
/* 174 */     ignored.add("style");
/*     */     
/* 176 */     ignored.add("title");
/*     */     
/* 178 */     return ignored;
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/svg/renderers/factories/DefaultSvgNodeRendererMapper.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */