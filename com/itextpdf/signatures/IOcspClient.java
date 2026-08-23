package com.itextpdf.signatures;

import java.security.cert.X509Certificate;

public interface IOcspClient {
  byte[] getEncoded(X509Certificate paramX509Certificate1, X509Certificate paramX509Certificate2, String paramString);
}


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/signatures/IOcspClient.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */