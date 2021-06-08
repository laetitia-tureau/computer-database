package com.excilys.formation.java.cdb.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.excilys.formation.java.cdb.controllers.ComputerController;
import com.excilys.formation.java.cdb.dtos.ComputerDTO;
import com.excilys.formation.java.cdb.services.Pagination;
import com.excilys.formation.java.cdb.services.SearchCriteria;

@Controller
@RequestMapping("/computer/list")
public class ListComputerServlet extends HttpServlet {

    @Autowired
    private ComputerController computerController;

    private static final long serialVersionUID = 1L;
    private static Logger log = Logger.getLogger(ListComputerServlet.class);

    @Override
    @GetMapping
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String indexCurrentPage = request.getParameter("currentPage");
        String itemsPerPage = request.getParameter("itemsPerPage");
        String search = request.getParameter("search");
        if (StringUtils.isNotBlank(search)) {
            search.trim();
        }
        String order = request.getParameter("order");
        String sort = request.getParameter("sort");

        SearchCriteria criteria = new SearchCriteria(order, sort, search);

        Pagination page = computerController.getPage(indexCurrentPage, itemsPerPage, search);
        List<ComputerDTO> computerList = computerController.getComputersPerPage(page, criteria);
        List<Integer> maxTotalOfPages = ComputerController.getMaxItemsPerPage(page);

        request.setAttribute("computerList", computerList);
        request.setAttribute("pagination", page);
        request.setAttribute("maxTotalOfPages", maxTotalOfPages);
        request.setAttribute("criteria", criteria);
        /*  request.setAttribute("search", search);
            request.setAttribute("order", order);
            request.setAttribute("sort", sort);  */

        String url = ComputerController.setUrl(search, order, sort);
        if (url.length() > 1) {
            request.setAttribute("url", url + "&");
        }
        request.getRequestDispatcher("/WEB-INF/views/dashboard.jsp").forward(request, response);
    }

}
