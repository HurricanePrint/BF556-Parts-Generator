package com.itextpdf.io.source;

import java.io.IOException;

public interface IRandomAccessSource {
  int get(long paramLong) throws IOException;
  
  int get(long paramLong, byte[] paramArrayOfbyte, int paramInt1, int paramInt2) throws IOException;
  
  long length();
  
  void close() throws IOException;
}


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/io/source/IRandomAccessSource.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */