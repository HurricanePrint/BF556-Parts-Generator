package com.itextpdf.kernel.pdf.canvas.parser.filter;

import com.itextpdf.kernel.pdf.canvas.parser.EventType;
import com.itextpdf.kernel.pdf.canvas.parser.data.IEventData;

public interface IEventFilter {
  boolean accept(IEventData paramIEventData, EventType paramEventType);
}


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/kernel/pdf/canvas/parser/filter/IEventFilter.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */