package ru.yandex.practicum.sleeptracker;

import org.junit.jupiter.api.Test;
import ru.yandex.practicum.sleeptracker.functions.SleeplessNightsFunc;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SleeplessNightsFuncTest {

    @Test
    void shouldCountZeroInsomniaNightsWhenAllNightsHaveSleep() {
        List<SleepingSession> sessions = List.of(
                new SleepingSession(
                        LocalDateTime.of(2025, 10, 1, 23, 0),
                        LocalDateTime.of(2025, 10, 2, 7, 0),
                        SleepQuality.GOOD
                ),
                new SleepingSession(
                        LocalDateTime.of(2025, 10, 2, 22, 0),
                        LocalDateTime.of(2025, 10, 3, 6, 0),
                        SleepQuality.NORMAL
                )
        );

        SleeplessNightsFunc func = new SleeplessNightsFunc();
        SleepAnalysisResult<?> result = func.apply(sessions);

        assertEquals(0, result.getValue());
    }

    @Test
    void shouldCountOneInsomniaNight() {
        List<SleepingSession> sessions = List.of(
                new SleepingSession(
                        LocalDateTime.of(2025, 10, 1, 7, 0),
                        LocalDateTime.of(2025, 10, 1, 11, 0),
                        SleepQuality.NORMAL
                ), // сон днём → ночь 1 считается «бессонной»
                new SleepingSession(
                        LocalDateTime.of(2025, 10, 2, 23, 0),
                        LocalDateTime.of(2025, 10, 3, 7, 0),
                        SleepQuality.GOOD
                ) // ночь 2 — нормальная
        );

        SleeplessNightsFunc func = new SleeplessNightsFunc();
        SleepAnalysisResult<?> result = func.apply(sessions);

        assertEquals(1, result.getValue());
    }

    @Test
    void shouldHandleLoggingAcrossMonths() {
        List<SleepingSession> sessions = List.of(
                new SleepingSession(
                        LocalDateTime.of(2025, 9, 30, 23, 30),
                        LocalDateTime.of(2025, 10, 1, 5, 0),
                        SleepQuality.GOOD
                ),
                new SleepingSession(
                        LocalDateTime.of(2025, 10, 2, 7, 0),
                        LocalDateTime.of(2025, 10, 2, 10, 0),
                        SleepQuality.NORMAL
                ) // ночь 2 — бессонная
        );

        SleeplessNightsFunc func = new SleeplessNightsFunc();
        SleepAnalysisResult<?> result = func.apply(sessions);

        assertEquals(1, result.getValue());
    }

    @Test
    void shouldReturnZeroIfNoSessions() {
        List<SleepingSession> sessions = List.of();

        SleeplessNightsFunc func = new SleeplessNightsFunc();
        SleepAnalysisResult<?> result = func.apply(sessions);

        assertEquals(0, result.getValue()); // нет сессий → нет логирования → нет ночей
    }
}
