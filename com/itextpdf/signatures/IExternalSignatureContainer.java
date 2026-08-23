package com.itextpdf.signatures;

import com.itextpdf.kernel.pdf.PdfDictionary;
import java.io.InputStream;
import java.security.GeneralSecurityException;

public interface IExternalSignatureContainer {
  byte[] sign(InputStream paramInputStream) throws GeneralSecurityException;
  
  void modifySigningDictionary(PdfDictionary paramPdfDictionary);
}


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/signatures/IExternalSignatureContainer.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */