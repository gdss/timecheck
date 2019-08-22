package com.gdss.timecheck;

import com.gdss.timecheck.services.MirrorComponent;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class MirrorComponentTests {

    @Mock
    MirrorComponent mirrorComponent;

    @Test
    public void whenWorkLessThan4Hours_thenRestNoNecessary() {
        when(mirrorComponent.verifyRestedInterval(any(), any())).thenCallRealMethod();

        Duration workedHours = Duration.of(3, ChronoUnit.HOURS);

        boolean noRested = mirrorComponent.verifyRestedInterval(workedHours, Duration.ZERO);
        assertTrue(noRested);
    }

    @Test
    public void whenWorkBetween4And6Hours_thenRest15Minutes() {
        when(mirrorComponent.verifyRestedInterval(any(), any())).thenCallRealMethod();

        Duration workedHours = Duration.of(5, ChronoUnit.HOURS);

        boolean restedFourteenMinutes = mirrorComponent.verifyRestedInterval(workedHours, Duration.of(14, ChronoUnit.MINUTES));
        assertFalse(restedFourteenMinutes);

        boolean restedFifteenMinutes = mirrorComponent.verifyRestedInterval(workedHours, Duration.of(15, ChronoUnit.MINUTES));
        assertTrue(restedFifteenMinutes);

        boolean restedSixteenMinutes = mirrorComponent.verifyRestedInterval(workedHours, Duration.of(16, ChronoUnit.MINUTES));
        assertTrue(restedSixteenMinutes);
    }

    @Test
    public void whenWorkMoreThan6Hours_thenRest60Minutes() {
        when(mirrorComponent.verifyRestedInterval(any(), any())).thenCallRealMethod();

        Duration workedHours = Duration.of(7, ChronoUnit.HOURS);

        boolean restedFiftyMinutes = mirrorComponent.verifyRestedInterval(workedHours, Duration.of(50, ChronoUnit.MINUTES));
        assertFalse(restedFiftyMinutes);

        boolean restedSixtyMinutes = mirrorComponent.verifyRestedInterval(workedHours, Duration.of(60, ChronoUnit.MINUTES));
        assertTrue(restedSixtyMinutes);

        boolean restedSeventyMinutes = mirrorComponent.verifyRestedInterval(workedHours, Duration.of(70, ChronoUnit.MINUTES));
        assertTrue(restedSeventyMinutes);
    }

    @Test
    public void whenWork60Minutes_thenReturn60Minutes() {
        when(mirrorComponent.getWorkedHours(any(), any())).thenCallRealMethod();

        LocalDate date = LocalDate.of(2019, 8, 23);
        assertEquals(DayOfWeek.FRIDAY, date.getDayOfWeek());

        List<LocalTime> timeList = new ArrayList<>();
        timeList.add(LocalTime.of(8, 0));
        timeList.add(LocalTime.of(9, 0));

        Duration duration = mirrorComponent.getWorkedHours(date, timeList);
        assertEquals(60, duration.toMinutes());
    }

    @Test
    public void whenWork60MinutesBefore6AM_thenReturn72Minutes() {
        when(mirrorComponent.getWorkedHours(any(), any())).thenCallRealMethod();

        LocalDate date = LocalDate.of(2019, 8, 23);
        assertEquals(DayOfWeek.FRIDAY, date.getDayOfWeek());

        List<LocalTime> timeList = new ArrayList<>();
        timeList.add(LocalTime.of(3, 0));
        timeList.add(LocalTime.of(4, 0));

        Duration duration = mirrorComponent.getWorkedHours(date, timeList);
        assertEquals(72, duration.toMinutes());
    }

    @Test
    public void whenWork60MinutesAfter22PM_thenReturn72Minutes() {
        when(mirrorComponent.getWorkedHours(any(), any())).thenCallRealMethod();

        LocalDate date = LocalDate.of(2019, 8, 23);
        assertEquals(DayOfWeek.FRIDAY, date.getDayOfWeek());

        List<LocalTime> timeList = new ArrayList<>();
        timeList.add(LocalTime.of(22, 30));
        timeList.add(LocalTime.of(23, 30));

        Duration duration = mirrorComponent.getWorkedHours(date, timeList);
        assertEquals(72, duration.toMinutes());
    }

    @Test
    public void whenWork60MinutesOnSaturday_thenReturn90Minutes() {
        when(mirrorComponent.getWorkedHours(any(), any())).thenCallRealMethod();

        LocalDate date = LocalDate.of(2019, 8, 24);
        assertEquals(DayOfWeek.SATURDAY, date.getDayOfWeek());

        List<LocalTime> timeList = new ArrayList<>();
        timeList.add(LocalTime.of(8, 0));
        timeList.add(LocalTime.of(9, 0));

        Duration duration = mirrorComponent.getWorkedHours(date, timeList);
        assertEquals(90, duration.toMinutes());
    }

    @Test
    public void whenWork60MinutesOnSunday_thenReturn120Minutes() {
        when(mirrorComponent.getWorkedHours(any(), any())).thenCallRealMethod();

        LocalDate date = LocalDate.of(2019, 8, 25);
        assertEquals(DayOfWeek.SUNDAY, date.getDayOfWeek());

        List<LocalTime> timeList = new ArrayList<>();
        timeList.add(LocalTime.of(8, 0));
        timeList.add(LocalTime.of(9, 0));

        Duration duration = mirrorComponent.getWorkedHours(date, timeList);
        assertEquals(120, duration.toMinutes());
    }

}
