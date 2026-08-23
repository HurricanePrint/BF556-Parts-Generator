package com.itextpdf.signatures;

import java.security.GeneralSecurityException;

public interface IExternalSignature {
  String getHashAlgorithm();
  
  String getEncryptionAlgorithm();
  
  byte[] sign(byte[] paramArrayOfbyte) throws GeneralSecurityException;
}


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/signatures/IExternalSignature.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */