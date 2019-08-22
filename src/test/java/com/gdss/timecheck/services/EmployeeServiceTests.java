package com.gdss.timecheck.services;

import com.gdss.timecheck.models.Employee;
import com.gdss.timecheck.repositories.EmployeeRepository;
import com.gdss.timecheck.services.EmployeeService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class EmployeeServiceTests {

    private static final String EMPLOYEE_NAME = "Geyson";
    private static final String EMPLOYEE_PIS = "123456789";

    @Mock
    EmployeeRepository employeeRepository;

    @InjectMocks
    EmployeeService employeeService;

    @Spy
    Employee employeeMock = new Employee();

    @Before
    public void init() {
        employeeMock.setId(1L);
        employeeMock.setName(EMPLOYEE_NAME);
        employeeMock.setPis(EMPLOYEE_PIS);
    }

    @Test
    public void whenSave_thenReturnEmployee() {
        when(employeeRepository.save(any())).thenReturn(employeeMock);

        Employee employee = employeeService.save(employeeMock);
        assertEquals(EMPLOYEE_NAME, employee.getName());
        assertEquals(EMPLOYEE_PIS, employee.getPis());
    }

    @Test
    public void whenFindByPis_thenReturnEmployee() {
        when(employeeRepository.findByPis(EMPLOYEE_PIS)).thenReturn(employeeMock);

        Employee employee = employeeService.findByPis(EMPLOYEE_PIS);
        assertEquals(EMPLOYEE_NAME, employee.getName());
        assertEquals(EMPLOYEE_PIS, employee.getPis());
    }

    @Test
    public void whenFindByInvalidPis_thenReturnNull() {
        Employee employee = employeeService.findByPis(any());
        assertNull(employee);
    }

}
