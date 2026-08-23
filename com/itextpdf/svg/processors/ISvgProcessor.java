package com.itextpdf.svg.processors;

import com.itextpdf.styledxmlparser.node.INode;
import com.itextpdf.svg.exceptions.SvgProcessingException;

public interface ISvgProcessor {
  ISvgProcessorResult process(INode paramINode, ISvgConverterProperties paramISvgConverterProperties) throws SvgProcessingException;
}


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/svg/processors/ISvgProcessor.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */