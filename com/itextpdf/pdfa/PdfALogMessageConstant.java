package com.itextpdf.pdfa;

public class PdfALogMessageConstant {
  public static final String PDFA_PAGE_FLUSHING_WAS_NOT_PERFORMED = "Page flushing was not performed. Pages flushing in PDF/A mode works only with explicit calls to PdfPage#flush(boolean) with flushResourcesContentStreams argument set to true";
  
  public static final String PDFA_OBJECT_FLUSHING_WAS_NOT_PERFORMED = "Object flushing was not performed. Object in PDF/A mode can only be flushed if the document is closed or if this object has already been checked for compliance with PDF/A rules.";
}


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/pdfa/PdfALogMessageConstant.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */