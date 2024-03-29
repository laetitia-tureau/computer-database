package com.excilys.formation.java.cdb.servlets;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.excilys.formation.java.cdb.dtos.ComputerDTO;
import com.excilys.formation.java.cdb.mappers.ComputerMapper;
import com.excilys.formation.java.cdb.models.Computer;
import com.excilys.formation.java.cdb.services.ComputerService;
import com.excilys.formation.java.cdb.services.Pagination;
import com.excilys.formation.java.cdb.services.SearchCriteria;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletResponse;

@Controller
public class ListComputerServlet {

    private ComputerService computerService;
    private ComputerMapper computerMapper;
    private static final long serialVersionUID = 1L;

    /** Creates a servlet to list and search computers.
     * @param cmptService a computer service
     * @param cmptMapper a computer mapper
     */
    public ListComputerServlet(ComputerService cmptService, ComputerMapper cmptMapper) {
        this.computerService = cmptService;
        this.computerMapper = cmptMapper;
    }

    @GetMapping("/computer/list")
    protected ModelAndView getComputers(@RequestParam(value = "currentPage", required = false) String indexCurrentPage,
                                        @RequestParam(value = "itemsPerPage", required = false) String itemsPerPage,
                                        @RequestParam(value = "search", required = false) String search,
                                        @RequestParam(value = "order", required = false) String order,
                                        @RequestParam(value = "sort", required = false) String sort) {
        if (StringUtils.isNotBlank(search)) {
            search.trim();
        }
        SearchCriteria criteria = new SearchCriteria(order, sort, search);
        Pagination page = this.getPage(indexCurrentPage, itemsPerPage, search);
        List<ComputerDTO> computerList = this.getComputersPerPage(page, criteria);
        List<Integer> maxTotalOfPages = this.getMaxItemsPerPage(page);

        Map<String, Object> attributeList = new HashMap<>();
        attributeList.put("computerList", computerList);
        attributeList.put("pagination", page);
        attributeList.put("maxTotalOfPages", maxTotalOfPages);
        attributeList.put("criteria", criteria);

        String url = this.setUrl(search, order, sort);
        if (url.length() > 1) {
            attributeList.put("url", url + "&");
        }
        //request.getRequestDispatcher("/WEB-INF/views/dashboard.jsp").forward(request, response);
        return new ModelAndView("dashboard").addAllObjects(attributeList);
    }

    /**
     * Retrieve computers encapsulated in a dto.
     * @param criteria a search by criteria service
     * @param page a pagination service
     * @return list of computers dto
     */
    public List<ComputerDTO> getComputersPerPage(Pagination page, SearchCriteria criteria) {
        Pagination pagination = this.getPage(String.valueOf(page.getCurrentPage()),
                String.valueOf(page.getItemsPerPage()), criteria.getItemName());
        criteria.setLimit(
                pagination.getItemsPerPage() * (pagination.getCurrentPage() - 1) + "," + pagination.getItemsPerPage());
        List<Computer> computerList = computerService.findByCriteria(criteria);

        return computerList.stream().map(c -> {
            return computerMapper.mapFromModelToDTO(c);
        }).collect(Collectors.toList());
    }

    /**
     * Retrieve a Pagination.
     * @param indexCurrentPage number of current page
     * @param itemsPerPage max number of items per page
     * @param search search criteria
     * @return a pagination service
     */
    public Pagination getPage(String indexCurrentPage, String itemsPerPage, String search) {
        Pagination page = new Pagination(0);
        if (StringUtils.isBlank(search)) {
            page.setTotalItems(computerService.getComputers().size());
        } else {
            SearchCriteria criteria = new SearchCriteria();
            criteria.setItemName(search);
            page.setTotalItems(computerService.findByCriteria(criteria).size());
        }

        if (indexCurrentPage != null && StringUtils.isNumeric(indexCurrentPage)) {
            page.setCurrentPage(Integer.parseInt(indexCurrentPage));
        }
        if (itemsPerPage != null && StringUtils.isNumeric(itemsPerPage)) {
            page.setItemsPerPage(Integer.parseInt(itemsPerPage));
        }
        return page;
    }

    /**
     * Retrieve the limit of items per page.
     * @param page a pagination service
     * @return a list of all limits
     */
    public List<Integer> getMaxItemsPerPage(Pagination page) {
        int totalItem = page.getTotalItems();
        int pageLimitMin = new Pagination(Pagination.PageLimit.MIN.getLimit(), 0, totalItem).getTotalOfPages();
        int pageLimitMid = new Pagination(Pagination.PageLimit.MID.getLimit(), 0, totalItem).getTotalOfPages();
        int pageLimitMax = new Pagination(Pagination.PageLimit.MAX.getLimit(), 0, totalItem).getTotalOfPages();
        return Arrays.asList(pageLimitMin, pageLimitMid, pageLimitMax);
    }

    /**
     * Build an url with given criteria.
     * @param search the search criteria
     * @param order the order criteria
     * @param sort the sort criteria
     * @return the resulting url
     */
    public String setUrl(String search, String order, String sort) {
        String url = "?";
        if (StringUtils.isNotBlank(search)) {
            url += "search=" + search;
        }
        if (StringUtils.isNotBlank(sort)) {
            if (url.length() > 1) {
                url += "&";
            }
            url += "sort=" + sort;
        }
        if (StringUtils.isNotBlank(order)) {
            if (url.length() > 1) {
                url += "&";
            }
            url += "order=" + order;
        }
        return url;
    }

    @PostMapping("/computer/delete")
    protected RedirectView deleteComputer(@RequestParam(value = "selection") String selection,
                                          HttpServletResponse response) {
        if (!StringUtils.isBlank(selection)) {
            Arrays.asList(selection.split(",")).stream().forEach(id -> computerService.deleteComputer(Long.parseLong(id)));
        }
        return new RedirectView("/computer/list", true);
    }
}
