package com.excilys.formation.java.cdb.servlets;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.excilys.formation.java.cdb.controllers.CompanyController;
import com.excilys.formation.java.cdb.controllers.ComputerController;
import com.excilys.formation.java.cdb.dtos.ComputerDTO;
import com.excilys.formation.java.cdb.exceptions.MyPersistenceException;
import com.excilys.formation.java.cdb.models.Company;

@Controller
@RequestMapping("/computer/edit")
public class EditComputerServlet extends HttpServlet {

    @Autowired
    private ComputerController computerController;

    @Autowired
    private CompanyController companyController;

    private static final long serialVersionUID = 1L;
    private static Logger log = Logger.getLogger(EditComputerServlet.class);

    @Override
    @GetMapping
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<Company> companyList = companyController.getCompanies();
        request.setAttribute("companyList", companyList);
        if (request.getParameter("id") != null) {
            String computerId = request.getParameter("id");
            Optional<ComputerDTO> computer = computerController.findComputer(computerId);
            request.setAttribute("computer", computer.get());
        }
        request.getRequestDispatcher("/WEB-INF/views/editComputer.jsp").forward(request, response);
    }

    @Override
    @PostMapping
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String computerName = request.getParameter("computerName");
        String introduced = request.getParameter("introduced");
        String discontinued = request.getParameter("discontinued");
        String companyId = request.getParameter("companyId");
        String computerId = request.getParameter("id");

        try {
            if (computerId != null) {
                computerController.updateComputer(computerId, computerName, introduced, discontinued, companyId);
                request.setAttribute("success", computerName + " was successfully updated !");
            } else {
                computerController.createComputer(computerName, introduced, discontinued, companyId);
                request.setAttribute("success", computerName + " was successfully added !");
            }
            doGet(request, response);
        } catch (MyPersistenceException ex) {
            log.error(ex.getMessage());
            request.setAttribute("error", ex.getMessage());
            doGet(request, response);
        }
    }
}
