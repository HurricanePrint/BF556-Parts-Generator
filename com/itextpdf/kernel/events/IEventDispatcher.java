package com.itextpdf.kernel.events;

public interface IEventDispatcher {
  void addEventHandler(String paramString, IEventHandler paramIEventHandler);
  
  void dispatchEvent(Event paramEvent);
  
  void dispatchEvent(Event paramEvent, boolean paramBoolean);
  
  boolean hasEventHandler(String paramString);
  
  void removeEventHandler(String paramString, IEventHandler paramIEventHandler);
  
  void removeAllHandlers();
}


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/kernel/events/IEventDispatcher.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */