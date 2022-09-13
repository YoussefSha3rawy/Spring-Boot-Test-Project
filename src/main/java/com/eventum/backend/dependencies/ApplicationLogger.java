package com.eventum.backend.dependencies; /*******************************************************************************
 * Copyright 2019 Greyskies. All Rights Reserved.
 ******************************************************************************/

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.LoggerContext;

public class ApplicationLogger {

  public static void logError(Throwable th, Class<?> clazz) {
    logError("", th, clazz);
  }

  public static void logError(String message, Class<?> clazz) {
    logError(message, null, clazz);
  }

  public static void logError(String message, Throwable th, Class<?> clazz) {
    putThreadContext();
    StringWriter sw = new StringWriter();
    if (th != null) {
      th.printStackTrace(new PrintWriter(sw));
    }
    LogManager.getLogger(clazz).error(message == null || message.isEmpty() ? sw.toString() : message + "\n" + sw.toString());
    LoggingUtils.removeThreadContext();
  }

  public static void logInfo(String message, Class<?> clazz) {
    putThreadContext();
    LogManager.getLogger(clazz).info(message);
    LoggingUtils.removeThreadContext();
  }

  public static void logFatal(Throwable th, Class<?> clazz) {
    logFatal(null, th, clazz);
  }

  public static void logFatal(String message, Throwable th, Class<?> clazz) {
    putThreadContext();
    StringWriter sw = new StringWriter();
    th.printStackTrace(new PrintWriter(sw));
    LogManager.getLogger(clazz).fatal(message == null || message.isEmpty() ? sw.toString() : message + "\n" + sw.toString());
    LoggingUtils.removeThreadContext();
  }

  public static void logDebug(Object obj, Class<?> clazz) {
    putThreadContext();
    LogManager.getLogger(clazz).debug(obj);
    LoggingUtils.removeThreadContext();
  }

  public static void setConfigurationFilePath(String path) throws IOException {
    LoggerContext ctx = (LoggerContext) LogManager.getContext(false);
    ctx.setConfigLocation(new File(path).toURI());
  }

  private static void putThreadContext() {
    LoggingUtils.putThreadContext(ApplicationLogger.class.getSimpleName());
  }

}
