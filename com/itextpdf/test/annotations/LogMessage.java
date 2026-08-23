package com.itextpdf.test.annotations;

public @interface LogMessage {
  String messageTemplate();
  
  int count() default 1;
  
  int logLevel() default 3000;
}


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/test/annotations/LogMessage.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */