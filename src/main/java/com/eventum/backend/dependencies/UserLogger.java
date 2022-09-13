package com.eventum.backend.dependencies; /*******************************************************************************
 * Copyright 2019 Greyskies. All Rights Reserved.
 ******************************************************************************/

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.spi.AbstractLogger;
import org.apache.logging.log4j.spi.ExtendedLoggerWrapper;

public final class UserLogger extends ExtendedLoggerWrapper {

  private static final long serialVersionUID = 1L;

  private static Level INFO = Level.forName("INFO", 30);
  private static Level WARN = Level.forName("WARN", 20);

  public static final String ADD = "Add";
  private static final String IMPORT = "Import";
  private static final String EXPORT = "Export";

  public static final String EDIT = "Edit";
  public static final String DELETE = "Delete";
  public static final String VIEW = "View";
  public static final String ASSIGN = "Assign";
  private static final String DOWNLOAD = "Download";
  private static final String LOGIN_IN = "Login";
  private static final String LOGIN_OUT = "Logout";
  private static final String ML_EXECUTION = "Execute";
  private static final String CP_FIRE_NOW = "Fire Now";
  private static final String RESET = "Reset";
  public static final String WERE_EXPORTED = " were exported";
  private static final String DIFF = "Diff";
  public static final String DOWNLOAD_DIFF = "Download Diff";
  private static final String DOWNLOAD_AUTOMATION_TEMPLATE = "Download automation template";

  public static final String USER_WORK_FLOW = "user workflow";
  public static final String ADMIN_WORK_FLOW = "admin workflow";

  public UserLogger(final Class<?> loggerName) {
    super((AbstractLogger) getLogger(loggerName), getLogger(loggerName).getName(), getLogger(loggerName).getMessageFactory());
  }

  private static Logger getLogger(final Class<?> loggerName) {
    return LogManager.getLogger(loggerName);
  }

  public void logAddingEntity(String username, String entityType, String entityName, String scope, String msg) {
    putThreadContext();
    buildLogMessage(msg, username, entityType, entityName, ADD, scope);
    LoggingUtils.removeThreadContext();
  }

  public void logUpdatingEntity(String username, String entityType, String entityName, String scope) {
    putThreadContext();
    buildLogMessage("", username, entityType, entityName, EDIT, scope);
    LoggingUtils.removeThreadContext();
  }

  public void logDeletingEntity(String username, String entityType, String entityName, String scope) {
    putThreadContext();
    buildLogMessage("", username, entityType, entityName, DELETE, scope);
    LoggingUtils.removeThreadContext();
  }

  public void logResetState(String msg, String username, String entityType, String entityName, String scope) {
    putThreadContext();
    buildLogMessage(msg, username, entityType, entityName, RESET, scope);
    LoggingUtils.removeThreadContext();
  }

  public void logMLExecution(final String msg, String username, String entityType, String entityName, String scope) {
    putThreadContext();
    buildLogMessage(msg, username, entityType, entityName, ML_EXECUTION, scope);
    LoggingUtils.removeThreadContext();
  }

  public void logCollectionProfileFireNow(final String msg, String username, String entityType, String entityName, String scope) {
    putThreadContext();
    buildLogMessage(msg, username, entityType, entityName, CP_FIRE_NOW, scope);
    LoggingUtils.removeThreadContext();
  }

  public void logUserLoggingIn(final String msg, String username, String entityType, String scope) {
    putThreadContext();
    buildLogMessage(msg, username, entityType, "", LOGIN_IN, scope);
    LoggingUtils.removeThreadContext();
  }

  public void logUserLoggingOut(final String msg, String username, String entityType, String scope) {
    putThreadContext();
    buildLogMessage(msg, username, entityType, "", LOGIN_OUT, scope);
    LoggingUtils.removeThreadContext();
  }

  public void logDownloadingReportAction(final String msg, String username, String entityType, String entityName, String scope) {
    putThreadContext();
    buildLogMessage(msg, username, entityType, entityName, DOWNLOAD, scope);
    LoggingUtils.removeThreadContext();
  }

  public void logDownloadingAutomationTemplateAction(final String msg, String username, String entityType, String entityName, String scope) {
    putThreadContext();
    buildLogMessage(msg, username, entityType, entityName, DOWNLOAD_AUTOMATION_TEMPLATE, scope);
    LoggingUtils.removeThreadContext();
  }

  public void logCompareArchivedFilesAction(final String msg, String username, String entityType, String entityName, String scope) {
    putThreadContext();
    buildLogMessage(msg, username, entityType, entityName, DIFF, scope);
    LoggingUtils.removeThreadContext();
  }

  public void logDownloadingDiffArchivedFilesAction(final String msg, String username, String entityType, String entityName, String scope) {
    putThreadContext();
    buildLogMessage(msg, username, entityType, entityName, DOWNLOAD_DIFF, scope);
    LoggingUtils.removeThreadContext();
  }

  public void logListingAllEntities(String username, String entityType, String scope) {
    putThreadContext();
    buildLogMessage("", username, entityType, "", VIEW, scope);
    LoggingUtils.removeThreadContext();
  }

  public void logViewEntity(String msg, String username, String entityType, String entityName, String scope) {
    putThreadContext();
    buildLogMessage(msg, username, entityType, entityName, VIEW, scope);
    LoggingUtils.removeThreadContext();
  }

  public void logImportEntity(String username, String entityType, String entityName, String scope) {
    putThreadContext();
    buildLogMessage("", username, entityType, entityName, IMPORT, scope);
    LoggingUtils.removeThreadContext();
  }

  public void logExportSingleEntity(String username, String entityType, String entityName, String scope) {
    putThreadContext();
    buildLogMessage("", username, entityType, entityName, EXPORT, scope);
    LoggingUtils.removeThreadContext();
  }

  public void logExportMultipleEntities(String username, String entityTypeInExportingMessage, String entityType, String scope, int numberOfEntites) {
    putThreadContext();
    buildLogMessage(ConstructExportEntitiesMessage(entityTypeInExportingMessage, numberOfEntites), username, entityType, "", EXPORT, scope);
    LoggingUtils.removeThreadContext();
  }

  public void logDirectoryActions(String message, String username, String directoryName, String entityType, String action, String scope) {
    putThreadContext();
    buildLogMessage(message, username, entityType, directoryName, action, scope);
    LoggingUtils.removeThreadContext();
  }

  private static String ConstructExportEntitiesMessage(String entityType, int numberOfEntites) {
    return numberOfEntites + StringUtils.SPACE + entityType + WERE_EXPORTED;
  }

  private static void putThreadContext() {
    LoggingUtils.putThreadContext(UserLogger.class.getSimpleName());
  }

  private void buildLogMessage(String msg, String username, String entityType, String entityName, String add, String scope) {
    this.logger.log(INFO, "[" + scope + "] [" + username + "] [" + entityType + "] [" + entityName + "] [" + add + "] [" + msg + "]");
  }

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

  public void logUserActivityWarn(final String msg) {
    logUserActivityWarn(msg, null);
  }

  public void logUserActivityWarn(final String msg, String username) {
    putThreadContext();
    if (username != null) {
      this.logger.log(WARN, "[" + username + "] " + msg);
    } else {
      this.logger.log(WARN, msg);
    }
    LoggingUtils.removeThreadContext();
  }

  public static void setConfigurationFilePath(String path) throws IOException {
    LoggerContext ctx = (LoggerContext) LogManager.getContext(false);
    ctx.setConfigLocation(new File(path).toURI());
  }
}
