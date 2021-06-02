package com.excilys.formation.java.cdb.services;

public class ComputerServiceTest {
    // legal calls with invalid data : la date entrée ne correspond pas à une date
    // (fait par mapper)
    /*
    @Mock
    Computer.ComputerBuilder builderMock;
    
    ComputerService computerService;
    
    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        builderMock = new Computer.ComputerBuilder(1L, "Lenovo").introduced(null).discontinued(null);
        computerService = new ComputerService();
    }
    
    @Test
    public void updateDateBefore1971() {
        //fail
    }
    
    @Test
    public void updateIntroducedDateWithAnteriorDiscontinuedDate() {
        //fail date inc > date dis
    }
    
    @Test
    public void updateIntroducedDateWhenNoData() {
        Computer computerMock = builderMock.build();
        Timestamp timestamp = new Timestamp(new Date().getTime());
        Optional<Computer> optional = computerService.updateIntroduced(timestamp, computerMock.getId());
        assertEquals(timestamp.toLocalDateTime().toLocalDate(), optional.get().getIntroduced());
    }
    
    @Test
    public void updateIntroducedDateWithLaterDiscontinuedDate() {
        //inc < dis
        //valid
    }
    
    @Test
    public void updateIntroducedDateWithNoDiscontinuedDate() {
        //dis null
        //valid
    }
    
    @Test
    public void updateDiscontinuedDateWithNoIntroducedDate() {
        //fail inc null
    }
    
    @Test
    public void updateDiscontinuedDateWithLaterIntroducedDate() {
        //fail date dis < date inc
    }
    
    @Test
    public void updateDiscontinuedDateWithAnteriorIntroducedDate() {
        //dis null before & inc !null || dis > inc
        //valid
    }*/
}