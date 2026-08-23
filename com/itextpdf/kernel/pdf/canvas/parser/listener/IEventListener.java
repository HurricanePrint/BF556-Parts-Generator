package com.itextpdf.kernel.pdf.canvas.parser.listener;

import com.itextpdf.kernel.pdf.canvas.parser.EventType;
import com.itextpdf.kernel.pdf.canvas.parser.data.IEventData;
import java.util.Set;

public interface IEventListener {
  void eventOccurred(IEventData paramIEventData, EventType paramEventType);
  
  Set<EventType> getSupportedEvents();
}


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/kernel/pdf/canvas/parser/listener/IEventListener.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */