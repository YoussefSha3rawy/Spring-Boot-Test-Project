package com.eventum.backend.dependencies;

import org.apache.logging.log4j.ThreadContext;

public class LoggingUtils {

  public static void putThreadContext(String className) {
    ThreadContext.put(LoggingConstants.EVENT_TYPE_KEYWORD, className);

  }

  public static void removeThreadContext() {
    ThreadContext.remove(LoggingConstants.EVENT_TYPE_KEYWORD);
  }

}
