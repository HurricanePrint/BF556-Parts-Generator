package com.itextpdf.test.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface LogMessages {
  LogMessage[] messages();
  
  boolean ignore() default false;
}


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/test/annotations/LogMessages.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */