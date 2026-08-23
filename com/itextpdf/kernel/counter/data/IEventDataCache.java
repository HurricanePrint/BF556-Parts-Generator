package com.itextpdf.kernel.counter.data;

import java.util.List;

public interface IEventDataCache<T, V extends EventData<T>> {
  void put(V paramV);
  
  V retrieveNext();
  
  List<V> clear();
}


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/kernel/counter/data/IEventDataCache.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */