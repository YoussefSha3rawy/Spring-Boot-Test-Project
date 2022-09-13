package com.eventum.backend.dependencies;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/*******************************************************************************
 * Copyright 2019 Greyskies. All Rights Reserved.
 ******************************************************************************/
public class TableListingUtils {
  public static Map<String, Object> generateResultMap(List tableData, Long count) {
    Map<String, Object> result = new HashMap<>();
    result.put(TableListingParamsDto.DATA_KEY, tableData);
    result.put(TableListingParamsDto.COUNT_KEY, count);
    return result;
  }
}
