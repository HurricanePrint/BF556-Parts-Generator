package org.apache.log4j.xml;

import java.util.Properties;
import org.w3c.dom.Element;

public interface UnrecognizedElementHandler {
  boolean parseUnrecognizedElement(Element paramElement, Properties paramProperties) throws Exception;
}


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/org/apache/log4j/xml/UnrecognizedElementHandler.class
 * Java compiler version: 4 (48.0)
 * JD-Core Version:       1.1.3
 */