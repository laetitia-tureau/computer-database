package com.excilys.formation.java.cdb.daos;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;

import com.excilys.formation.java.cdb.models.Company;
import com.excilys.formation.java.cdb.persistence.daos.CompanyDAO;

public class CompanyDAOTest {

    private static CompanyDAO companyInstance;
    // private static Logger log = Logger.getLogger(CompanyDAOTest.class);

    private static final long TOTAL_COMPANY = 42L;
    private static final Long FIND_COMPANY_BY_ID = 13L;
    // private static final int FIRST_COMPANY_PER_PAGE = 1;
    // private static final int LAST_COMPANY_PER_PAGE = 25;

    @BeforeAll
    public static void init() throws Exception {
    }

    public void testFindById() {
        Optional<Company> opt = companyInstance.findById(FIND_COMPANY_BY_ID);
        if (opt.isPresent()) {
            Assertions.assertEquals(FIND_COMPANY_BY_ID, opt.get().getId());
        } else {
            Assertions.fail("Company ID: " + FIND_COMPANY_BY_ID + " not found !");
        }

        opt = companyInstance.findById(TOTAL_COMPANY + 1);
        Assertions.assertFalse(opt.isPresent());
    }

    public void testGetCompanyList() {
        List<Company> companyList = companyInstance.getAllCompanies();
        if (!companyList.isEmpty()) {
            Assertions.assertEquals(TOTAL_COMPANY, companyList.size());
        } else {
            Assertions.fail("Fails to find total number of company !");
        }
    }

    @AfterAll
    public static void terminate() throws Exception {
        companyInstance = null;
        System.setProperty("test", "false");
    }

}
