package com.eventum.backend.dependencies; /*******************************************************************************
 * Copyright 2019 Greyskies. All Rights Reserved.
 ******************************************************************************/

import java.util.Collection;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringEscapeUtils;

public final class StringUtils extends org.apache.commons.lang.StringUtils {

  private static final String DOUBLE_QUOTES = "\"";
  public static final String UNDERSCORE = "_";
  public static final String DASH = "-";
  public static final String SPACE = " ";
  public static final String COMMA = ",";
  public static final String TAB_INDENTATION = "\t";
  public static final String NEW_LINE_SEPARATOR = "\n";

  private StringUtils() {}

  public static String splitCamelCase(String s) {
    return join(splitByCharacterTypeCamelCase(s), " ");
  }

  public static boolean compareString(String string1, String string2) {
    return equals(string1, string2);
  }

  public static String escapeSQLSearchQuery(String searchQuery) {
    return StringEscapeUtils.escapeSql(searchQuery).replace("%", "\\%").replace("_", "\\_");
  }

  public static String generateValidName(String name) {
    String specialChars = "[\\s:\"*+/\\|?#><_-]";
    String resultName = name.replaceAll(specialChars, "").toLowerCase();
    if (resultName.length() == 0) {
      resultName = "temp-" + System.currentTimeMillis();
    }
    return resultName;
  }

  public static String convertSetToCommaSeparatedString(Set<Long> items) {
    return join(items.stream().map(item -> DOUBLE_QUOTES + item + DOUBLE_QUOTES).collect(Collectors.toList()), COMMA);
  }

  public static <T> String joinCollectionItems(Collection<T> collection, Function<T, String> mapFunction, String delimiter) {
    return joinCollectionLimitItems(collection, mapFunction, delimiter, collection.size());
  }

  public static <T> String joinCollectionLimitItems(Collection<T> collection, Function<T, String> mapFunction, String delimiter, Integer limit) {
    if (CollectionUtils.isEmpty(collection)) {
      return EMPTY;
    }
    StringBuilder sb = new StringBuilder();
    String joinedItems = collection.stream().limit(limit).map(mapFunction).collect(Collectors.joining(delimiter));
    String moreItems = limit < collection.size() ? String.format(" + %d more", collection.size() - limit) : StringUtils.EMPTY;
    return sb.append(joinedItems).append(moreItems).toString();
  }
}
