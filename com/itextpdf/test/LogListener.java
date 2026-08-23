/*     */ package com.itextpdf.test;
/*     */ 
/*     */ import ch.qos.logback.classic.Level;
/*     */ import ch.qos.logback.classic.Logger;
/*     */ import ch.qos.logback.classic.LoggerContext;
/*     */ import ch.qos.logback.classic.spi.ILoggingEvent;
/*     */ import ch.qos.logback.classic.spi.IThrowableProxy;
/*     */ import ch.qos.logback.classic.spi.StackTraceElementProxy;
/*     */ import ch.qos.logback.core.Appender;
/*     */ import ch.qos.logback.core.read.ListAppender;
/*     */ import com.itextpdf.test.annotations.LogMessage;
/*     */ import com.itextpdf.test.annotations.LogMessages;
/*     */ import java.util.HashSet;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import org.junit.rules.TestWatcher;
/*     */ import org.junit.runner.Description;
/*     */ import org.slf4j.ILoggerFactory;
/*     */ import org.slf4j.Logger;
/*     */ import org.slf4j.LoggerFactory;
/*     */ import org.slf4j.helpers.SubstituteLoggerFactory;
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
/*     */ public class LogListener
/*     */   extends TestWatcher
/*     */ {
/*     */   private static final String ROOT_ITEXT_PACKAGE = "com.itextpdf";
/*  73 */   private final CustomListAppender<ILoggingEvent> listAppender = new CustomListAppender<>();
/*     */   
/*  75 */   private final ILoggerFactory lc = LoggerFactory.getILoggerFactory();
/*     */   
/*     */   private Map<Logger, Map<String, Appender<ILoggingEvent>>> appenders;
/*     */ 
/*     */   
/*     */   protected void starting(Description description) {
/*  81 */     before(description);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void finished(Description description) {
/*  86 */     checkLogMessages(description);
/*  87 */     after();
/*     */   }
/*     */   
/*     */   private int contains(LogMessage loggingStatement) {
/*  91 */     List<ILoggingEvent> list = this.listAppender.list;
/*  92 */     int index = 0;
/*  93 */     for (ILoggingEvent event : list) {
/*  94 */       if (isLevelCompatible(loggingStatement.logLevel(), event.getLevel()) && 
/*     */         
/*  96 */         LoggerHelper.equalsMessageByTemplate(event.getFormattedMessage(), loggingStatement.messageTemplate())) {
/*  97 */         index++;
/*     */       }
/*     */     } 
/* 100 */     return index;
/*     */   }
/*     */   
/*     */   private boolean isLevelCompatible(int logMessageLevel, Level eventLevel) {
/* 104 */     switch (logMessageLevel) {
/*     */       case 3000:
/* 106 */         return eventLevel.isGreaterOrEqual(Level.WARN);
/*     */       case 2000:
/* 108 */         return (eventLevel == Level.ERROR);
/*     */       case 1000:
/* 110 */         return (eventLevel == Level.WARN);
/*     */       case 0:
/* 112 */         return (eventLevel == Level.INFO);
/*     */       case -1000:
/* 114 */         return (eventLevel == Level.DEBUG);
/*     */     } 
/* 116 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getSize() {
/* 121 */     return this.listAppender.list.size();
/*     */   }
/*     */   
/*     */   private void before(Description description) {
/* 125 */     this.listAppender.clear();
/*     */     
/* 127 */     LogMessages logMessages = LoggerHelper.<LogMessages>getTestAnnotation(description, LogMessages.class);
/* 128 */     if (logMessages != null) {
/* 129 */       Set<String> expectedTemplates = new HashSet<>();
/* 130 */       LogMessage[] messages = logMessages.messages();
/* 131 */       for (LogMessage logMessage : messages) {
/* 132 */         expectedTemplates.add(logMessage.messageTemplate());
/*     */       }
/* 134 */       this.listAppender.setExpectedTemplates(expectedTemplates);
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 139 */     this.appenders = LoggerHelper.getAllAppendersMap((LoggerContext)this.lc);
/* 140 */     resetLoggingContext();
/* 141 */     addAppenderToPackage();
/* 142 */     this.listAppender.start();
/*     */   }
/*     */   
/*     */   private void after() {
/* 146 */     this.listAppender.stop();
/* 147 */     resetLoggingContext();
/* 148 */     LoggerHelper.restoreAppenders(this.appenders);
/*     */   }
/*     */   
/*     */   private void addAppenderToPackage() {
/* 152 */     Logger logger = LoggerFactory.getLogger("com.itextpdf");
/* 153 */     if (logger instanceof Logger) {
/* 154 */       ((Logger)logger).addAppender((Appender)this.listAppender);
/*     */     }
/*     */   }
/*     */   
/*     */   private void resetLoggingContext() {
/* 159 */     if (this.lc instanceof LoggerContext) {
/* 160 */       ((LoggerContext)this.lc).reset();
/* 161 */     } else if (this.lc instanceof SubstituteLoggerFactory) {
/* 162 */       ((SubstituteLoggerFactory)this.lc).clear();
/*     */     } 
/*     */   }
/*     */   
/*     */   private void checkLogMessages(Description description) {
/* 167 */     LogMessages logMessages = LoggerHelper.<LogMessages>getTestAnnotation(description, LogMessages.class);
/* 168 */     int checkedMessages = 0;
/* 169 */     if (logMessages != null) {
/* 170 */       LogMessage[] messages = logMessages.messages();
/* 171 */       for (LogMessage logMessage : messages) {
/* 172 */         int foundCount = contains(logMessage);
/* 173 */         if (foundCount != logMessage.count() && !logMessages.ignore()) {
/* 174 */           LoggerHelper.failWrongMessageCount(logMessage.count(), foundCount, logMessage.messageTemplate(), description);
/*     */         } else {
/*     */           
/* 177 */           checkedMessages += foundCount;
/*     */         } 
/*     */       } 
/*     */     } 
/* 181 */     if (getSize() > checkedMessages)
/* 182 */       LoggerHelper.failWrongTotalCount(getSize(), checkedMessages, description); 
/*     */   }
/*     */   
/*     */   private class CustomListAppender<E>
/*     */     extends ListAppender<ILoggingEvent>
/*     */   {
/* 188 */     private Set<String> expectedTemplates = new HashSet<>();
/*     */     
/*     */     public void setExpectedTemplates(Set<String> expectedTemplates) {
/* 191 */       this.expectedTemplates.clear();
/* 192 */       this.expectedTemplates.addAll(expectedTemplates);
/*     */     }
/*     */     
/*     */     public void clear() {
/* 196 */       this.list.clear();
/* 197 */       this.expectedTemplates.clear();
/*     */     }
/*     */     
/*     */     protected void append(ILoggingEvent e) {
/* 201 */       System.out.println(e.getLoggerName() + " " + e.getLevel() + " " + e.getMessage());
/* 202 */       printStackTraceIfAny(e);
/* 203 */       if (e.getLevel().isGreaterOrEqual(Level.WARN) || isExpectedMessage(e.getMessage())) {
/* 204 */         this.list.add(e);
/*     */       }
/*     */     }
/*     */     
/*     */     private boolean isExpectedMessage(String message) {
/* 209 */       if (message != null) {
/* 210 */         for (String template : this.expectedTemplates) {
/* 211 */           if (LoggerHelper.equalsMessageByTemplate(message, template)) {
/* 212 */             return true;
/*     */           }
/*     */         } 
/*     */       }
/* 216 */       return false;
/*     */     }
/*     */     
/*     */     private void printStackTraceIfAny(ILoggingEvent e) {
/* 220 */       IThrowableProxy throwableProxy = e.getThrowableProxy();
/* 221 */       if (throwableProxy != null) {
/* 222 */         System.out.println(throwableProxy.getMessage());
/* 223 */         for (StackTraceElementProxy el : throwableProxy.getStackTraceElementProxyArray())
/* 224 */           System.out.println("\t" + el); 
/*     */       } 
/*     */     }
/*     */     
/*     */     private CustomListAppender() {}
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/test/LogListener.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */