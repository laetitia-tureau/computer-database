package com.excilys.formation.java.cdb.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.excilys.formation.java.cdb.controllers.ComputerController;

@Controller
@RequestMapping("/computer/delete")
public class DeleteComputerServlet extends HttpServlet {

    @Autowired
    private ComputerController computerController;

    private static final long serialVersionUID = 1L;

    @Override
    @PostMapping
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String idList = request.getParameter("selection");
        computerController.deleteComputer(idList);
        response.sendRedirect(request.getContextPath() + "/computer/list");
    }
}
