package com.gdss.timecheck;

import com.gdss.timecheck.exceptions.MinuteClockinException;
import com.gdss.timecheck.models.Clockin;
import com.gdss.timecheck.models.Employee;
import com.gdss.timecheck.repositories.ClockinRepository;
import com.gdss.timecheck.services.ClockinService;
import com.gdss.timecheck.services.EmployeeService;
import com.gdss.timecheck.wrappers.ClockinRequest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.LocalDateTime;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ClockinServiceTests {

    @Mock
    ClockinRepository clockinRepository;

    @Mock
    EmployeeService employeeService;

    @InjectMocks
    ClockinService clockinService;

    @Test
    public void whenCreate_thenReturnClockin() throws MinuteClockinException {
        Employee employeeMock = new Employee();
        employeeMock.setName("Geyson");
        employeeMock.setPis("123456789");
        when(employeeService.findByPis(any())).thenReturn(employeeMock);

        LocalDateTime now = LocalDateTime.now();

        Clockin clockinMock = new Clockin();
        clockinMock.setEmployee(employeeMock);
        clockinMock.setDate(now.toLocalDate());
        clockinMock.setTime(now.toLocalTime());
        when(clockinRepository.save(any())).thenReturn(clockinMock);

        ClockinRequest clockinRequest = new ClockinRequest();
        clockinRequest.setPis("123456789");
        clockinRequest.setDateTime(now);
        Clockin clockin = clockinService.create(clockinRequest);

        assertEquals(employeeMock.getName(), clockin.getEmployee().getName());
        assertEquals(clockinMock.getDate(), clockin.getDate());
        assertEquals(clockinMock.getTime(), clockin.getTime());
    }


}
