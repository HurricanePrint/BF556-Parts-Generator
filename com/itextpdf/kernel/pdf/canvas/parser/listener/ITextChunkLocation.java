package com.itextpdf.kernel.pdf.canvas.parser.listener;

import com.itextpdf.kernel.geom.Vector;

public interface ITextChunkLocation {
  float distParallelEnd();
  
  float distParallelStart();
  
  int distPerpendicular();
  
  float getCharSpaceWidth();
  
  Vector getEndLocation();
  
  Vector getStartLocation();
  
  int orientationMagnitude();
  
  boolean sameLine(ITextChunkLocation paramITextChunkLocation);
  
  float distanceFromEndOf(ITextChunkLocation paramITextChunkLocation);
  
  boolean isAtWordBoundary(ITextChunkLocation paramITextChunkLocation);
}


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/kernel/pdf/canvas/parser/listener/ITextChunkLocation.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */