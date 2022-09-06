/*******************************************************************************
 * Copyright 2019 Greyskies. All Rights Reserved.
 ******************************************************************************/
package com.eventum.backend;

public abstract class SearchQueryListingParamsDto extends TableListingParamsDto {

    private String searchQuery = "";

    public String getSearchQuery() {
        return searchQuery;
    }

    public void setSearchQuery(String searchQuery) {
        this.searchQuery = searchQuery;
    }

}
