package com.excilys.formation.java.cdb.services;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.excilys.formation.java.cdb.models.Computer;

public class ComputerServiceTest {
    // legal calls with invalid data : la date entrée ne correspond pas à une date
    // (fait par mapper)

    @Mock
    Computer.ComputerBuilder builderMock;

    ComputerService computerService;

    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void updateDateBefore1971() {
        // fail
    }

    @Test
    public void updateIntroducedDateWithAnteriorDiscontinuedDate() {
        // fail date inc > date dis
    }

    @Test
    public void updateIntroducedDateWhenNoData() {
    }

    @Test
    public void updateIntroducedDateWithLaterDiscontinuedDate() {
        // valid inc < dis
    }

    @Test
    public void updateIntroducedDateWithNoDiscontinuedDate() {
        // valid dis null
    }

    @Test
    public void updateDiscontinuedDateWithNoIntroducedDate() {
        // fail inc null
    }

    @Test
    public void updateDiscontinuedDateWithLaterIntroducedDate() {
        // fail date dis < date inc
    }

    @Test
    public void updateDiscontinuedDateWithAnteriorIntroducedDate() {
        // valid if inc != null && dis > inc
    }
}