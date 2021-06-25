package com.excilys.formation.java.cdb.models;

import org.springframework.data.domain.Sort;

public class SearchCriteria {
    private Sort.Direction order;
    private SortColumn sort;
    private String limit;
    private String itemName;

    public enum SortColumn {
        ID("id"), NAME("name"), INTRO("introduced"), DIST("discontinued"), COMPANY("manufacturer");

        private String sqlRequest;

        /**
         * Creates a SortColumn enum.
         * @param value sqlResquest
         */
        SortColumn(String value) {
            this.sqlRequest = value;
        }

        /**
         * Retrieve sqlRequest field.
         * @return sqlRequest
         */
        public String getRequest() {
            return this.sqlRequest;
        }

        /**
         * Creates a SortColumn with given query.
         * @param value requested
         * @return a matching SortColumn or SortColumn.ID as default
         */
        public static SortColumn of(String value) {
            SortColumn sort;
            switch (value) {
            case "computer.id":
                sort = SortColumn.ID;
                break;
            case "computer.name":
                sort = SortColumn.NAME;
                break;
            case "computer.introduced":
                sort = SortColumn.INTRO;
                break;
            case "computer.discontinued":
                sort = SortColumn.DIST;
                break;
            case "company.name":
                sort = SortColumn.COMPANY;
                break;
            default:
                sort = SortColumn.ID;
            }
            return sort;
        }
    }

    /**
     * Creates an object that contains search criteria.
     */
    public SearchCriteria() {
        this.order = Sort.DEFAULT_DIRECTION;
        this.sort = SortColumn.ID;
    }

    /**
     * Creates an object that contains search criteria.
     * @param reqOrder order criteria
     * @param reqSort sort criteria
     * @param reqName search criteria
     */
    public SearchCriteria(Sort.Direction reqOrder, SortColumn reqSort, String reqName) {
        this.order = reqOrder;
        this.sort = reqSort;
        this.itemName = reqName;
    }

    public Sort.Direction getOrder() {
        return order;
    }

    public void setOrder(Sort.Direction reqOrder) {
        this.order = reqOrder;
    }

    public SortColumn getSort() {
        return sort;
    }

    public void setSort(SortColumn reqSort) {
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
