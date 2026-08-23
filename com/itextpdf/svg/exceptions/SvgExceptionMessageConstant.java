package com.itextpdf.svg.exceptions;

public class SvgExceptionMessageConstant {
  public static final String PATH_OBJECT_MUST_HAVE_D_ATTRIBUTE = "A Path object must have an attribute with the name 'd'.";
  
  public static final String COORDINATE_ARRAY_LENGTH_MUST_BY_DIVISIBLE_BY_CURRENT_COORDINATES_ARRAY_LENGTH = "Array of current coordinates must have length that is divisible by the length of the array with current coordinates";
  
  public static final String CURVE_TO_EXPECTS_FOLLOWING_PARAMETERS_GOT_0 = "(x1 y1 x2 y2 x y)+ parameters are expected for curves. Got: {0}";
  
  public static final String INVALID_SMOOTH_CURVE_USE = "The smooth curve operations (S, s, T, t) may not be used as a first operator in path.";
  
  public static final String QUADRATIC_CURVE_TO_EXPECTS_FOLLOWING_PARAMETERS_GOT_0 = "(x1 y1 x y)+ parameters are expected for quadratic curves. Got: {0}";
  
  public static final String MOVE_TO_EXPECTS_FOLLOWING_PARAMETERS_GOT_0 = "(x y)+ parameters are expected for moveTo operator. Got: {0}";
  
  public static final String LINE_TO_EXPECTS_FOLLOWING_PARAMETERS_GOT_0 = "(x y)+ parameters are expected for lineTo operator. Got: {0}";
  
  public static final String COULD_NOT_DETERMINE_MIDDLE_POINT_OF_ELLIPTICAL_ARC = "Could not determine the middle point of the ellipse traced by this elliptical arc";
}


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/svg/exceptions/SvgExceptionMessageConstant.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */