package ru.yandex.practicum.sleeptracker.functions;

import ru.yandex.practicum.sleeptracker.SleepAnalysisResult;
import ru.yandex.practicum.sleeptracker.SleepingSession;

import java.time.Duration;
import java.util.List;
import java.util.function.Function;

public class SessionDurationAvgFunc implements Function<List<SleepingSession>, SleepAnalysisResult<?>> {

    @Override
    public SleepAnalysisResult<?> apply(List<SleepingSession> listOfSessions) {
        double avg = listOfSessions.stream()
                .mapToDouble(s -> Duration.between(s.getStart(), s.getEnd()).toMinutes())
                .average()
                .orElse(0L);
        return new SleepAnalysisResult<>("Средняя продолжительность сессии (мин)", avg);
    }
}
