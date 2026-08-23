package com.itextpdf.kernel.pdf.tagutils;

import com.itextpdf.kernel.pdf.tagging.PdfNamespace;
import java.io.Serializable;

public interface IRoleMappingResolver extends Serializable {
  String getRole();
  
  PdfNamespace getNamespace();
  
  boolean currentRoleIsStandard();
  
  boolean currentRoleShallBeMappedToStandard();
  
  boolean resolveNextMapping();
}


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/kernel/pdf/tagutils/IRoleMappingResolver.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */