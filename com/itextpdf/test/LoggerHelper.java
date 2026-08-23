/*     */ package com.itextpdf.test;
/*     */ 
/*     */ import ch.qos.logback.classic.Logger;
/*     */ import ch.qos.logback.classic.LoggerContext;
/*     */ import ch.qos.logback.classic.spi.ILoggingEvent;
/*     */ import ch.qos.logback.core.Appender;
/*     */ import java.lang.annotation.Annotation;
/*     */ import java.text.MessageFormat;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.Map;
/*     */ import java.util.regex.Pattern;
/*     */ import org.junit.Assert;
/*     */ import org.junit.runner.Description;
/*     */ import org.slf4j.LoggerFactory;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class LoggerHelper
/*     */ {
/*     */   static <T extends Annotation> T getTestAnnotation(Description description, Class<T> annotationClass) {
/*  65 */     Annotation annotation = description.getAnnotation(annotationClass);
/*  66 */     if (annotation == null) {
/*  67 */       annotation = description.getTestClass().getAnnotation(annotationClass);
/*     */     }
/*  69 */     return (T)annotation;
/*     */   }
/*     */   
/*     */   static void failWrongMessageCount(int expected, int actual, String messageTemplate, Description description) {
/*  73 */     Assert.fail(MessageFormat.format("{0}:{1} Expected to find {2}, but found {3} messages with the following content: \"{4}\"", new Object[] { description
/*  74 */             .getClassName(), description.getMethodName(), Integer.valueOf(expected), Integer.valueOf(actual), messageTemplate }));
/*     */   }
/*     */   
/*     */   static void failWrongTotalCount(int expected, int actual, Description description) {
/*  78 */     Assert.fail(MessageFormat.format("{0}.{1}: The test does not check the message logging - {2} messages", new Object[] { description
/*  79 */             .getClassName(), description
/*  80 */             .getMethodName(), 
/*  81 */             Integer.valueOf(expected - actual) }));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static boolean equalsMessageByTemplate(String message, String template) {
/*  89 */     if (template.contains("{") && template.contains("}")) {
/*  90 */       String templateWithoutParameters = Pattern.quote(template).replace("''", "'").replaceAll("\\{[0-9]+?}", "\\\\E(.)*?\\\\Q");
/*  91 */       Pattern p = Pattern.compile(templateWithoutParameters, 32);
/*  92 */       return p.matcher(message).matches();
/*     */     } 
/*  94 */     return message.contains(template);
/*     */   }
/*     */ 
/*     */   
/*     */   public static void restoreAppenders(Map<Logger, Map<String, Appender<ILoggingEvent>>> appenders) {
/*  99 */     for (Logger logger : appenders.keySet()) {
/* 100 */       Map<String, Appender<ILoggingEvent>> appenderMap = appenders.get(logger);
/* 101 */       Logger currentLogger = (Logger)LoggerFactory.getLogger(logger.getName());
/* 102 */       for (String appenderName : appenderMap.keySet()) {
/* 103 */         currentLogger.addAppender(appenderMap.get(appenderName));
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   public static Map<Logger, Map<String, Appender<ILoggingEvent>>> getAllAppendersMap(LoggerContext loggerContext) {
/* 109 */     Map<Logger, Map<String, Appender<ILoggingEvent>>> resultMap = new HashMap<>();
/* 110 */     for (Logger logger : loggerContext.getLoggerList()) {
/* 111 */       Map<String, Appender<ILoggingEvent>> appendersMap = new HashMap<>();
/*     */       
/* 113 */       Iterator<Appender<ILoggingEvent>> appenderIterator = logger.iteratorForAppenders();
/* 114 */       while (appenderIterator.hasNext()) {
/* 115 */         Appender<ILoggingEvent> appender = appenderIterator.next();
/* 116 */         appendersMap.put(appender.getName(), appender);
/*     */       } 
/*     */       
/* 119 */       resultMap.put(logger, appendersMap);
/*     */     } 
/*     */     
/* 122 */     return resultMap;
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/test/LoggerHelper.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */