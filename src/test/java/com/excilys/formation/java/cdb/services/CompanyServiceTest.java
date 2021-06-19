package com.excilys.formation.java.cdb.services;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.excilys.formation.java.cdb.models.Company;
import com.excilys.formation.java.cdb.persistence.daos.CompanyDAO;

public class CompanyServiceTest {

    // private static Logger log = Logger.getLogger(CompanyServiceTest.class);

    @Mock
    CompanyDAO companyDao;

    @InjectMocks
    CompanyService companyService;

    private Company trueCompany;
    private static final Long FAKE_COMPANY_ID = 1242354L;
    // private static final Long TOTAL_COMPANY = 42L;
    private static final Long FIND_COMPANY_BY_ID = 1L;
    // private static final int LIMIT_PER_PAGE = 3;
    // private static final int PAGE_NUMBER = 1;

    @SuppressWarnings("deprecation")
    @BeforeEach
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testFindCompanyByIdShouldReturnCompany() {
        
    }

    @Test
    public void testFindCompanyByIdShouldThrowException() {
    }

    @Test
    public void testGetListCompaniesShouldReturnNotEmptyCompanyList() {
        
    }

}
