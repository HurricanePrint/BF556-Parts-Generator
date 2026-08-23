package com.itextpdf.signatures;

import java.security.GeneralSecurityException;
import java.security.MessageDigest;

public interface IExternalDigest {
  MessageDigest getMessageDigest(String paramString) throws GeneralSecurityException;
}


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/signatures/IExternalDigest.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */