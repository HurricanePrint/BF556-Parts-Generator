package com.itextpdf.kernel.pdf.tagging;

import com.itextpdf.kernel.pdf.PdfName;
import java.util.List;

public interface IStructureNode {
  IStructureNode getParent();
  
  List<IStructureNode> getKids();
  
  PdfName getRole();
}


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/kernel/pdf/tagging/IStructureNode.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */