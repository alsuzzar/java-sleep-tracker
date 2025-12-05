package ru.yandex.practicum.sleeptracker.functions;

import ru.yandex.practicum.sleeptracker.SleepAnalysisResult;
import ru.yandex.practicum.sleeptracker.SleepingSession;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.function.Function;

public class SleeplessNightsFunc implements Function<List<SleepingSession>, SleepAnalysisResult<?>> {

    @Override
    public SleepAnalysisResult<?> apply(List<SleepingSession> listOfSessions) {
        if (listOfSessions == null || listOfSessions.isEmpty()) {
            return new SleepAnalysisResult<>("Количество бессонных ночей", 0);
        }

        LocalDateTime firstStart = listOfSessions.get(0).getStart();
        LocalDateTime lastEnd = listOfSessions.get(listOfSessions.size() - 1).getEnd();

        LocalDate startNightDate;
        if (firstStart.getHour() >= 12) {
            startNightDate = firstStart.toLocalDate().plusDays(1);
        } else {
            startNightDate = firstStart.toLocalDate().minusDays(1);
        }

        LocalDate endNightDate = lastEnd.toLocalDate();

        int sleeplessCount = 0;

        for (LocalDate d = startNightDate; !d.isAfter(endNightDate); d = d.plusDays(1)) {
            LocalDateTime nightStart = d.atStartOfDay();
            LocalDateTime nightEnd = d.atTime(LocalTime.of(6, 0));

            if (nightEnd.isBefore(firstStart) || nightEnd.equals(firstStart)) {
                continue;
            }
            if (nightStart.isAfter(lastEnd) || nightStart.equals(lastEnd)) {
                continue;
            }

            boolean intersects = listOfSessions.stream().anyMatch(s ->
                    s.getEnd().isAfter(nightStart) && s.getStart().isBefore(nightEnd)
            );

            if (!intersects) {
                sleeplessCount++;
            }
        }

        return new SleepAnalysisResult<>("Количество бессонных ночей", sleeplessCount);
    }
}
