package com.itextpdf.kernel.crypto;

public interface IDecryptor {
  byte[] update(byte[] paramArrayOfbyte, int paramInt1, int paramInt2);
  
  byte[] finish();
}


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/kernel/crypto/IDecryptor.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */