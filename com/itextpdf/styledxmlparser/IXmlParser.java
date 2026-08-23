package com.itextpdf.styledxmlparser;

import com.itextpdf.styledxmlparser.node.IDocumentNode;
import java.io.IOException;
import java.io.InputStream;

public interface IXmlParser {
  IDocumentNode parse(InputStream paramInputStream, String paramString) throws IOException;
  
  IDocumentNode parse(String paramString);
}


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/styledxmlparser/IXmlParser.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */