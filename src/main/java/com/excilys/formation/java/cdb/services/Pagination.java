package com.excilys.formation.java.cdb.services;

/**
 * Service to paginate.
 * @author Laetitia Tureau
 */
public class Pagination {

    private int limit;
    private int page;
    private int totalItems;

    // show 25 item per page by default
    private static final int DEFAULT_LIMIT = 10;
    private static final int DEFAULT_PAGE = 1;

    /**
     * Creates an object to paginate.
     * @param total numbers of items to paginate
     */
    public Pagination(int total) {
        this.limit = DEFAULT_LIMIT;
        this.page = DEFAULT_PAGE;
        this.totalItems = total;
    }

    /**
     * Creates an object to paginate.
     * @param limit numbers of items per page
     * @param page current page
     * @param total numbers of items to paginate
     */
    public Pagination(int limit, int page, int total) {
        this.limit = limit;
        this.page = page;
        this.totalItems = total;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getTotalItems() {
        return totalItems;
    }

    public void setTotalItems(int totalItem) {
        this.totalItems = totalItem;
    }

    public int getTotalPage() {
        return (int) Math.ceil(((double) this.totalItems / (double) this.limit));
    }

    /** cursor to the next page.*/
    public void next() {
        if (this.page < this.getTotalPage()) {
            this.page++;
        }
    }

    /** cursor to the previous page.*/
    public void prev() {
        if (this.page > 1) {
            this.page--;
        }
    }

    @Override
    public String toString() {
        return "Pagination [limit=" + limit + ", page=" + page + ", totalItem=" + totalItems + "]";
    }
}
