package ru.yandex.practicum.sleeptracker;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.sleeptracker.functions.SessionDurationAvgFunc;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SessionDurationAvgFuncTest {

    List<SleepingSession> sessionListTest;

    @BeforeEach
    void setUp() {
        LocalDateTime start1 = LocalDateTime.of(2025, 10, 1, 22, 0);
        LocalDateTime end1 = LocalDateTime.of(2025, 10, 2, 8, 0);
        SleepingSession sessionTest1 = new SleepingSession(start1, end1, SleepQuality.GOOD);

        LocalDateTime start2 = LocalDateTime.of(2025, 10, 2, 23, 0);
        LocalDateTime end2 = LocalDateTime.of(2025, 10, 3, 8, 0);
        SleepingSession sessionTest2 = new SleepingSession(start2, end2, SleepQuality.NORMAL);

        LocalDateTime start3 = LocalDateTime.of(2025, 10, 3, 23, 0);
        LocalDateTime end3 = LocalDateTime.of(2025, 10, 4, 7, 0);
        SleepingSession sessionTest3 = new SleepingSession(start3, end3, SleepQuality.BAD);

        sessionListTest = List.of(sessionTest1, sessionTest2, sessionTest3);
    }

    @Test
    void shouldCalcAvgSessionDuration() {
        SessionDurationAvgFunc sessionDurationAvgFuncTest = new SessionDurationAvgFunc();

        SleepAnalysisResult<?> result = sessionDurationAvgFuncTest.apply(sessionListTest);

        assertEquals(540.0, result.getValue());
        assertEquals("Средняя продолжительность сессии (мин)",
                result.getMessage());
    }

    @Test
    void shouldHandleEmptyList() {
        SessionDurationAvgFunc sessionDurationAvgFunc = new SessionDurationAvgFunc();
        List<SleepingSession> emptyList = List.of();
        SleepAnalysisResult<?> result = sessionDurationAvgFunc.apply(emptyList);

        assertEquals("Средняя продолжительность сессии (мин)", result.getMessage());
        assertEquals(0.0, result.getValue());
    }

}