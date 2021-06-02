package com.excilys.formation.java.cdb.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.excilys.formation.java.cdb.controllers.ComputerController;

@WebServlet(name = "DeleteComputerServlet", urlPatterns = { "/computer/delete" })
public class DeleteComputerServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static Logger log = Logger.getLogger(DeleteComputerServlet.class);

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String idList = request.getParameter("selection");
        ComputerController.deleteComputer(idList);
        response.sendRedirect(request.getContextPath() + "/computer/list");
    }
}
