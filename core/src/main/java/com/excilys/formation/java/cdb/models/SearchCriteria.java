package com.excilys.formation.java.cdb.models;

public class SearchCriteria {
    private String order;
    private String sort;
    private String limit;
    private String itemName;

    private static final String DEFAULT_SORT = "computer.id";
    private static final String DEFAULT_ORDER = "asc";

    /**
     * Creates an object that contains search criteria.
     */
    public SearchCriteria() {
        this.order = DEFAULT_ORDER;
        this.sort = DEFAULT_SORT;
    }

    /**
     * Creates an object that contains search criteria.
     * @param reqOrder order criteria
     * @param reqSort sort criteria
     * @param reqName search criteria
     */
    public SearchCriteria(String reqOrder, String reqSort, String reqName) {
        this.order = reqOrder;
        this.sort = reqSort;
        this.itemName = reqName;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String reqOrder) {
        this.order = reqOrder;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String reqSort) {
        this.sort = reqSort;
    }

    public String getLimit() {
        return limit;
    }

    public void setLimit(String reqLimit) {
        this.limit = reqLimit;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String reqItemName) {
        this.itemName = reqItemName;
    }

}
