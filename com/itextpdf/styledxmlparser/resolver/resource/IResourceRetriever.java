package com.itextpdf.styledxmlparser.resolver.resource;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public interface IResourceRetriever {
  InputStream getInputStreamByUrl(URL paramURL) throws IOException;
  
  byte[] getByteArrayByUrl(URL paramURL) throws IOException;
}


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/styledxmlparser/resolver/resource/IResourceRetriever.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */