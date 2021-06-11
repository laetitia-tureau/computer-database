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
        companyService.setCompanyInstance(companyDao);
        trueCompany = new Company.CompanyBuilder().id(FIND_COMPANY_BY_ID).name("Apple Inc.").build();
    }

    @Test
    public void testFindCompanyByIdShouldReturnCompany() {
        when(companyDao.findById(FIND_COMPANY_BY_ID)).thenReturn(Optional.of(trueCompany));
        Assertions.assertEquals(companyService.findById(FIND_COMPANY_BY_ID), trueCompany);
        verify(companyDao).findById(FIND_COMPANY_BY_ID);
    }

    @Test
    public void testFindCompanyByIdShouldThrowException() {
        // (use something else than exceptionRule as its deprecated)
        /* when(companyDao.findById(FAKE_COMPANY_ID)).thenReturn(Optional.ofNullable(null));
        exceptionRule.expect(CustomException.class);
        exceptionRule.expectMessage(FAKE_COMPANY_ID + CustomException.TEXT_ER_NOT_FOUND);
        companyService.findById(FAKE_COMPANY_ID);
        verify(companyDao).findById(FAKE_COMPANY_ID);*/
    }

    @Test
    public void testGetListCompaniesShouldReturnNotEmptyCompanyList() {
        List<Company> companies = new ArrayList<>(Arrays.asList(trueCompany, trueCompany));
        when(companyDao.getAllCompanies()).thenReturn(companies);
        List<Company> companyList = companyService.getCompanies();
        assertFalse(companyList.isEmpty());
        verify(companyDao).getAllCompanies();
    }

}
