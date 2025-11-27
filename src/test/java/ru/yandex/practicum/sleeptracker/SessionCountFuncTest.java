package ru.yandex.practicum.sleeptracker;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.sleeptracker.functions.SessionCountFunc;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SessionCountFuncTest {

    List<SleepingSession> sessionListTest;

    @BeforeEach
    void setUp() {
        LocalDateTime start1 = LocalDateTime.of(2025, 10, 1, 22, 15);
        LocalDateTime end1 = LocalDateTime.of(2025, 10, 2, 8, 0);
        SleepingSession sessionTest1 = new SleepingSession(start1, end1, SleepQuality.GOOD);

        LocalDateTime start2 = LocalDateTime.of(2025, 10, 2, 23, 15);
        LocalDateTime end2 = LocalDateTime.of(2025, 10, 3, 8, 0);
        SleepingSession sessionTest2 = new SleepingSession(start2, end2, SleepQuality.NORMAL);

        LocalDateTime start3 = LocalDateTime.of(2025, 10, 3, 23, 30);
        LocalDateTime end3 = LocalDateTime.of(2025, 10, 4, 6, 20);
        SleepingSession sessionTest3 = new SleepingSession(start3, end3, SleepQuality.BAD);

        sessionListTest = List.of(sessionTest1, sessionTest2, sessionTest3);
    }

    @Test
    void shouldCountSessions() {
        SessionCountFunc sessionCountFuncTest = new SessionCountFunc();
        SleepAnalysisResult<?> result = sessionCountFuncTest.apply(sessionListTest);
        assertEquals(3, result.getValue());
        assertEquals("Всего сессий сна", result.getMessage());
    }

    @Test
    void shouldHandleEmptyList() {
        SessionCountFunc sessionCountFuncTest = new SessionCountFunc();
        List<SleepingSession> emptyList = List.of();
        SleepAnalysisResult<?> result = sessionCountFuncTest.apply(emptyList);
        assertEquals(0, result.getValue());
        assertEquals("Всего сессий сна", result.getMessage());
    }
}
