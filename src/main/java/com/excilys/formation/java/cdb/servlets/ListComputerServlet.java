package com.excilys.formation.java.cdb.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.excilys.formation.java.cdb.controllers.ComputerController;
import com.excilys.formation.java.cdb.dtos.ComputerDTO;
import com.excilys.formation.java.cdb.services.Pagination;

@WebServlet(name = "ListComputerServlet", urlPatterns = { "/computer/list" })
public class ListComputerServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static Logger log = Logger.getLogger(ListComputerServlet.class);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String pageNumber = request.getParameter("page");
        String perPage = request.getParameter("perPage");

        Pagination page = ComputerController.getPage(pageNumber, perPage);
        List<ComputerDTO> computerList = ComputerController.getComputersPerPage(page);
        List<Integer> maxTotalPage = ComputerController.getMaxPagePerLimit(10, 50, 100);
        request.setAttribute("computerList", computerList);
        request.setAttribute("pagination", page);
        request.setAttribute("maxTotalPage", maxTotalPage);
        this.getServletContext().getRequestDispatcher("/WEB-INF/views/dashboard.jsp").forward(request, response);
    }

}
