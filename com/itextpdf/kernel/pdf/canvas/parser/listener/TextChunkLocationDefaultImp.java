/*     */ package com.itextpdf.kernel.pdf.canvas.parser.listener;
/*     */ 
/*     */ import com.itextpdf.kernel.geom.LineSegment;
/*     */ import com.itextpdf.kernel.geom.Vector;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class TextChunkLocationDefaultImp
/*     */   implements ITextChunkLocation
/*     */ {
/*     */   private static final float DIACRITICAL_MARKS_ALLOWED_VERTICAL_DEVIATION = 2.0F;
/*     */   private final Vector startLocation;
/*     */   private final Vector endLocation;
/*     */   private final Vector orientationVector;
/*     */   private final int orientationMagnitude;
/*     */   private final int distPerpendicular;
/*     */   private final float distParallelStart;
/*     */   private final float distParallelEnd;
/*     */   private final float charSpaceWidth;
/*     */   
/*     */   public TextChunkLocationDefaultImp(Vector startLocation, Vector endLocation, float charSpaceWidth) {
/*  87 */     this.startLocation = startLocation;
/*  88 */     this.endLocation = endLocation;
/*  89 */     this.charSpaceWidth = charSpaceWidth;
/*     */     
/*  91 */     Vector oVector = endLocation.subtract(startLocation);
/*  92 */     if (oVector.length() == 0.0F) {
/*  93 */       oVector = new Vector(1.0F, 0.0F, 0.0F);
/*     */     }
/*  95 */     this.orientationVector = oVector.normalize();
/*  96 */     this.orientationMagnitude = (int)(Math.atan2(this.orientationVector.get(1), this.orientationVector.get(0)) * 1000.0D);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 101 */     Vector origin = new Vector(0.0F, 0.0F, 1.0F);
/* 102 */     this.distPerpendicular = (int)startLocation.subtract(origin).cross(this.orientationVector).get(2);
/*     */     
/* 104 */     this.distParallelStart = this.orientationVector.dot(startLocation);
/* 105 */     this.distParallelEnd = this.orientationVector.dot(endLocation);
/*     */   }
/*     */ 
/*     */   
/*     */   public int orientationMagnitude() {
/* 110 */     return this.orientationMagnitude;
/*     */   }
/*     */   
/*     */   public int distPerpendicular() {
/* 114 */     return this.distPerpendicular;
/*     */   }
/*     */   
/*     */   public float distParallelStart() {
/* 118 */     return this.distParallelStart;
/*     */   }
/*     */   
/*     */   public float distParallelEnd() {
/* 122 */     return this.distParallelEnd;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Vector getStartLocation() {
/* 129 */     return this.startLocation;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Vector getEndLocation() {
/* 136 */     return this.endLocation;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getCharSpaceWidth() {
/* 143 */     return this.charSpaceWidth;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean sameLine(ITextChunkLocation as) {
/* 151 */     if (orientationMagnitude() != as.orientationMagnitude()) {
/* 152 */       return false;
/*     */     }
/* 154 */     float distPerpendicularDiff = (distPerpendicular() - as.distPerpendicular());
/* 155 */     if (distPerpendicularDiff == 0.0F) {
/* 156 */       return true;
/*     */     }
/* 158 */     LineSegment mySegment = new LineSegment(this.startLocation, this.endLocation);
/* 159 */     LineSegment otherSegment = new LineSegment(as.getStartLocation(), as.getEndLocation());
/* 160 */     return (Math.abs(distPerpendicularDiff) <= 2.0F && (mySegment.getLength() == 0.0F || otherSegment.getLength() == 0.0F));
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
/*     */   public float distanceFromEndOf(ITextChunkLocation other) {
/* 173 */     return distParallelStart() - other.distParallelEnd();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isAtWordBoundary(ITextChunkLocation previous) {
/* 179 */     if (this.startLocation.equals(this.endLocation) || previous.getEndLocation().equals(previous.getStartLocation())) {
/* 180 */       return false;
/*     */     }
/*     */     
/* 183 */     float dist = distanceFromEndOf(previous);
/*     */     
/* 185 */     if (dist < 0.0F) {
/* 186 */       dist = previous.distanceFromEndOf(this);
/*     */ 
/*     */       
/* 189 */       if (dist < 0.0F) {
/* 190 */         return false;
/*     */       }
/*     */     } 
/* 193 */     return (dist > getCharSpaceWidth() / 2.0F);
/*     */   }
/*     */   
/*     */   static boolean containsMark(ITextChunkLocation baseLocation, ITextChunkLocation markLocation) {
/* 197 */     return (baseLocation.getStartLocation().get(0) <= markLocation.getStartLocation().get(0) && baseLocation.getEndLocation().get(0) >= markLocation.getEndLocation().get(0) && 
/* 198 */       Math.abs(baseLocation.distPerpendicular() - markLocation.distPerpendicular()) <= 2.0F);
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/kernel/pdf/canvas/parser/listener/TextChunkLocationDefaultImp.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */