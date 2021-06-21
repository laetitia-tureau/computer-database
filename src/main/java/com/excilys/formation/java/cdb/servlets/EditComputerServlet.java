package com.excilys.formation.java.cdb.servlets;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.excilys.formation.java.cdb.dtos.CompanyDTO;
import com.excilys.formation.java.cdb.mappers.CompanyMapper;
import com.excilys.formation.java.cdb.mappers.ComputerMapper;
import com.excilys.formation.java.cdb.models.Computer;
import com.excilys.formation.java.cdb.services.CompanyService;
import com.excilys.formation.java.cdb.services.ComputerService;
import com.excilys.formation.java.cdb.validator.ComputerValidator;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.excilys.formation.java.cdb.dtos.ComputerDTO;
import com.excilys.formation.java.cdb.exceptions.MyPersistenceException;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

@Controller
public class EditComputerServlet {

    private CompanyService companyService;
    private ComputerService computerService;
    private ComputerMapper computerMapper;
    private CompanyMapper companyMapper;
    private ComputerValidator computerValidator;

    private static final long serialVersionUID = 1L;
    private static Logger log = Logger.getLogger(EditComputerServlet.class);

    /**
     * Creates a servlet for adding and editing a computer.
     * @param cmpService a company service
     * @param cmptService a computer service
     * @param cmptMapper a computer mapper
     * @param cmpMapper a company mapper
     * @param validator a computer validator
     */
    public EditComputerServlet(CompanyService cmpService, ComputerService cmptService,
                                 ComputerMapper cmptMapper, CompanyMapper cmpMapper, ComputerValidator validator) {
        this.companyService = cmpService;
        this.computerService = cmptService;
        this.computerMapper = cmptMapper;
        this.companyMapper = cmpMapper;
        this.computerValidator = validator;
    }

    @GetMapping("/computer/edit")
    protected ModelAndView getView(@RequestParam(value = "id", required = false) String computerId) {
        ComputerDTO computerDTO = new ComputerDTO.ComputerBuilderDTO().build();
        if (computerId != null && StringUtils.isNumeric(computerId)) {
            //TODO could not initialize proxy computer no session
            computerDTO = computerMapper.mapFromModelToDTO(computerService.findById(Long.parseLong(computerId)));
        }
        ModelAndView modelAndView = new ModelAndView("editComputer", "computer", computerDTO);
        List<CompanyDTO> companyDTOList = companyService.getCompanies().stream()
                .map(c -> companyMapper.mapFromModelToDTO(c)).collect(Collectors.toList());
        modelAndView.addObject("companyList", companyDTOList);
        return modelAndView;
    }

    @PostMapping("/computer/edit")
    protected RedirectView editComputer(@ModelAttribute("computer") ComputerDTO computerDTO,
                                        RedirectAttributes redirectAttributes) {
        try {
            String successMessage = " was successfully ";
            if (computerDTO.getId() != null) {
                successMessage += "updated !";
            } else {
                successMessage += "added !";
            }
            computerDTO = this.saveComputer(computerDTO);
            redirectAttributes.addFlashAttribute("success", computerDTO.getName() + successMessage);
            redirectAttributes.addAttribute("id", computerDTO.getId());
        } catch (MyPersistenceException ex) {
            log.error(ex.getMessage());
            redirectAttributes.addFlashAttribute("error", ex.getMessage());
        }
        return new RedirectView("/computer/list", true);
    }

    /**
     * Retrieve a computer with specific id.
     * @param computerId computer's id to find
     * @return An empty Optional if nothing found else a Optional containing a computer
     */
    public Optional<ComputerDTO> findComputer(String computerId) {
        if (computerId != null && StringUtils.isNumeric(computerId)) {
            Computer computer = computerService.findById(Long.parseLong(computerId));
            return Optional.of(computerMapper.mapFromModelToDTO(computer));
        }
        return Optional.empty();
    }

    /**
     * Edit a computer.
     * @param computerDTO to edit a computer
     * @return a saved computerDTO
     */
    public ComputerDTO saveComputer(ComputerDTO computerDTO) {
        computerValidator.validateComputerDTO(computerDTO);
        Computer computer = computerMapper.mapFromDTOtoModel(computerDTO);
        return computerMapper.mapFromModelToDTO(computerService.save(computer));
    }
}
