package com.itextpdf.kernel.counter.data;

import com.itextpdf.kernel.counter.event.IEvent;
import com.itextpdf.kernel.counter.event.IMetaInfo;

public interface IEventDataFactory<T, V extends EventData<T>> {
  V create(IEvent paramIEvent, IMetaInfo paramIMetaInfo);
}


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/kernel/counter/data/IEventDataFactory.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */