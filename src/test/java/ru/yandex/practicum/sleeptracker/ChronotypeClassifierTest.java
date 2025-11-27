package ru.yandex.practicum.sleeptracker;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ChronotypeClassifierTest {

    @Test
    void testSovaClassification() {
        List<SleepingSession> sessions = List.of(
                new SleepingSession(LocalDateTime.of(2025, 11, 26, 23, 30),
                        LocalDateTime.of(2025, 11, 27, 10, 0),
                        SleepQuality.GOOD),
                new SleepingSession(LocalDateTime.of(2025, 11, 27, 0, 15),
                        LocalDateTime.of(2025, 11, 27, 9, 30),
                        SleepQuality.NORMAL)
        );
        assertEquals(Chronotype.SOVA, ChronotypeClassifier.classifyUser(sessions));
    }

    @Test
    void testJavoronokClassification() {
        List<SleepingSession> sessions = List.of(
                new SleepingSession(LocalDateTime.of(2025, 11, 26, 21, 0),
                        LocalDateTime.of(2025, 11, 27, 6, 30),
                        SleepQuality.GOOD)
        );
        assertEquals(Chronotype.JAVORONOK, ChronotypeClassifier.classifyUser(sessions));
    }

    @Test
    void testGolubClassification() {
        List<SleepingSession> sessions = List.of(
                new SleepingSession(LocalDateTime.of(2025, 11, 26, 22, 30),
                        LocalDateTime.of(2025, 11, 27, 8, 0),
                        SleepQuality.GOOD)
        );
        assertEquals(Chronotype.GOLUB, ChronotypeClassifier.classifyUser(sessions));
    }

    @Test
    void testIgnoreDaytimeSleep() {
        List<SleepingSession> sessions = List.of(
                new SleepingSession(LocalDateTime.of(2025, 11, 26, 14, 0),
                        LocalDateTime.of(2025, 11, 26, 15, 0),
                        SleepQuality.GOOD), // дневной сон
                new SleepingSession(LocalDateTime.of(2025, 11, 26, 21, 0),
                        LocalDateTime.of(2025, 11, 27, 6, 30),
                        SleepQuality.GOOD)
        );
        assertEquals(Chronotype.JAVORONOK, ChronotypeClassifier.classifyUser(sessions));
    }

    @Test
    void testTieResults() {
        List<SleepingSession> sessions = List.of(
                new SleepingSession(LocalDateTime.of(2025, 11, 26, 23, 30),
                        LocalDateTime.of(2025, 11, 27, 10, 0),
                        SleepQuality.GOOD),  // Сова
                new SleepingSession(LocalDateTime.of(2025, 11, 27, 21, 0),
                        LocalDateTime.of(2025, 11, 28, 6, 30),
                        SleepQuality.GOOD)   // Жаворонок
        );
        assertEquals(Chronotype.GOLUB, ChronotypeClassifier.classifyUser(sessions));
    }
}
