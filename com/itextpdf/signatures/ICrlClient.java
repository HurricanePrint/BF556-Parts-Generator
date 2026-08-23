package com.itextpdf.signatures;

import java.security.cert.X509Certificate;
import java.util.Collection;

public interface ICrlClient {
  Collection<byte[]> getEncoded(X509Certificate paramX509Certificate, String paramString);
}


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/signatures/ICrlClient.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */