package com.excilys.formation.java.cdb.services;

/**
 * Service to paginate.
 * @author Laetitia Tureau
 */
public class Pagination {

    private int itemsPerPage;
    private int currentPage;
    private int totalItems;

    // show 25 item per page by default
    private static final int DEFAULT_LIMIT = 10;
    private static final int DEFAULT_PAGE = 1;

    /**
     * Creates an object to paginate.
     * @param total numbers of items to paginate
     */
    public Pagination(int total) {
        this.itemsPerPage = DEFAULT_LIMIT;
        this.currentPage = DEFAULT_PAGE;
        this.totalItems = total;
    }

    /**
     * Creates an object to paginate.
     * @param limit numbers of items per page
     * @param page current page
     * @param total numbers of items to paginate
     */
    public Pagination(int limit, int page, int total) {
        this.itemsPerPage = limit;
        this.currentPage = page;
        this.totalItems = total;
    }

    public int getItemsPerPage() {
        return itemsPerPage;
    }

    public void setItemsPerPage(int limit) {
        this.itemsPerPage = limit;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int page) {
        this.currentPage = page;
    }

    public int getTotalItems() {
        return totalItems;
    }

    public void setTotalItems(int totalItem) {
        this.totalItems = totalItem;
    }

    public int getTotalOfPages() {
        return (int) Math.ceil(((double) this.totalItems / (double) this.itemsPerPage));
    }

    /** cursor to the next page.*/
    public void next() {
        if (this.currentPage < this.getTotalOfPages()) {
            this.currentPage++;
        }
    }

    /** cursor to the previous page.*/
    public void prev() {
        if (this.currentPage > 1) {
            this.currentPage--;
        }
    }

    @Override
    public String toString() {
        return "Pagination [itemsPerPage=" + itemsPerPage + ", currentPage=" + currentPage + ", totalItems="
                + totalItems + "]";
    }

    public enum PageLimit {
        MIN(10), MID(50), MAX(100);

        public Integer limit;

        /**
         * Creates a PageLimlit enum with given items limit.
         * @param max limit of items per page
         */
        PageLimit(Integer max) {
            this.limit = max;
        }

        public Integer getLimit() {
            return this.limit;
        }
    }
}
