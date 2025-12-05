package ru.yandex.practicum.sleeptracker;

import org.junit.jupiter.api.Test;
import ru.yandex.practicum.sleeptracker.functions.BadSessionsCountFunc;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BadSessionsCountFuncTest {

    List<SleepingSession> sessionListTest;

    @Test
    void shouldCountBadSessions() {
        LocalDateTime start1 = LocalDateTime.of(2025, 10, 1, 22, 15);
        LocalDateTime end1 = LocalDateTime.of(2025, 10, 2, 8, 0);
        SleepingSession sessionTest1 = new SleepingSession(start1, end1, SleepQuality.GOOD);

        LocalDateTime start11 = LocalDateTime.of(2025, 10, 3, 23, 0);
        LocalDateTime end11 = LocalDateTime.of(2025, 10, 4, 5, 0);
        SleepingSession sessionTest11 = new SleepingSession(start11, end11, SleepQuality.BAD);

        LocalDateTime start2 = LocalDateTime.of(2025, 10, 2, 23, 15);
        LocalDateTime end2 = LocalDateTime.of(2025, 10, 3, 8, 0);
        SleepingSession sessionTest2 = new SleepingSession(start2, end2, SleepQuality.NORMAL);

        LocalDateTime start4 = LocalDateTime.of(2025, 10, 3, 23, 30);
        LocalDateTime end4 = LocalDateTime.of(2025, 10, 4, 6, 20);
        SleepingSession sessionTest4 = new SleepingSession(start4, end4, SleepQuality.BAD);

        sessionListTest = List.of(sessionTest1, sessionTest11, sessionTest2, sessionTest4);

        BadSessionsCountFunc badSessionCountFuncTest = new BadSessionsCountFunc();
        SleepAnalysisResult<?> result = badSessionCountFuncTest.apply(sessionListTest);
        assertEquals(2, result.getValue());
        assertEquals("Количество плохих сессий", result.getMessage());
    }

    @Test
    void shouldHandleEmptyList() {
        LocalDateTime start1 = LocalDateTime.of(2025, 10, 1, 22, 15);
        LocalDateTime end1 = LocalDateTime.of(2025, 10, 2, 8, 0);
        SleepingSession sessionTest1 = new SleepingSession(start1, end1, SleepQuality.GOOD);

        LocalDateTime start2 = LocalDateTime.of(2025, 10, 2, 23, 15);
        LocalDateTime end2 = LocalDateTime.of(2025, 10, 3, 8, 0);
        SleepingSession sessionTest2 = new SleepingSession(start2, end2, SleepQuality.NORMAL);

        LocalDateTime start4 = LocalDateTime.of(2025, 10, 3, 23, 30);
        LocalDateTime end4 = LocalDateTime.of(2025, 10, 4, 10, 20);
        SleepingSession sessionTest4 = new SleepingSession(start4, end4, SleepQuality.GOOD);

        sessionListTest = List.of(sessionTest1, sessionTest2, sessionTest4);
        BadSessionsCountFunc badSessionCountFuncTest = new BadSessionsCountFunc();
        SleepAnalysisResult<?> result = badSessionCountFuncTest.apply(sessionListTest);
        assertEquals(0, result.getValue());
        assertEquals("Количество плохих сессий", result.getMessage());
    }
}
