package com.itextpdf.test.runners;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.CLASS)
@Target({ElementType.METHOD})
public @interface RetryOnFailure {}


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/test/runners/RetryOnFailure.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */