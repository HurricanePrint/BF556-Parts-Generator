package com.itextpdf.signatures;

import java.security.GeneralSecurityException;
import java.security.MessageDigest;

public interface ITSAClient {
  int getTokenSizeEstimate();
  
  MessageDigest getMessageDigest() throws GeneralSecurityException;
  
  byte[] getTimeStampToken(byte[] paramArrayOfbyte) throws Exception;
}


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/signatures/ITSAClient.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */