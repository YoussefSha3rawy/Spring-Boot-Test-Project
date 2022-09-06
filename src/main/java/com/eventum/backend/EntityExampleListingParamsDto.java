/*******************************************************************************
 * Copyright 2019 Greyskies. All Rights Reserved.
 ******************************************************************************/
package com.eventum.backend;

public class EntityExampleListingParamsDto extends SearchQueryListingParamsDto {

    private static final String DEFAULT_SORTING_COLUMN_NAME = "name";

    @Override public String getDefaultSortingColumnName() {
        return DEFAULT_SORTING_COLUMN_NAME;
    }
}
