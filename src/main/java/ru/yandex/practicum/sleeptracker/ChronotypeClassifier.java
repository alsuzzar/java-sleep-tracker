package ru.yandex.practicum.sleeptracker;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class ChronotypeClassifier {

    public static Chronotype classifyUser(List<SleepingSession> sessions) {
        if (sessions.isEmpty()) return Chronotype.PIGEON;

        List<SleepingSession> sortedSessions = sessions.stream()
                .sorted((s1, s2) -> s1.getStart().compareTo(s2.getStart()))
                .toList();

        LocalDateTime firstSessionStart = sortedSessions.get(0).getStart();
        LocalDateTime lastSessionEnd = sortedSessions.get(sortedSessions.size() - 1).getEnd();

        LocalDate startNightDate = firstSessionStart.toLocalTime().isBefore(LocalTime.NOON)
                ? firstSessionStart.toLocalDate().minusDays(1)
                : firstSessionStart.toLocalDate();
        LocalDate endNightDate = lastSessionEnd.toLocalDate();

        // Генерируем список ночей
        List<LocalDate> nights = startNightDate.datesUntil(endNightDate.plusDays(1))
                .toList();

        List<Chronotype> nightTypes = nights.stream()
                .map(night -> {
                    LocalDateTime nightStart = night.atTime(0, 0);
                    LocalDateTime nightEnd = night.atTime(6, 0);

                    return sortedSessions.stream()
                            .filter(s -> s.getEnd().isAfter(nightStart) && s.getStart().isBefore(nightEnd))
                            .findFirst()
                            .map(session -> {
                                LocalTime sleepTime = session.getStart().toLocalTime();
                                LocalTime wakeTime = session.getEnd().toLocalTime();

                                if (sleepTime.isAfter(LocalTime.of(23, 0)) && wakeTime.isAfter(LocalTime.of(9, 0))) {
                                    return Chronotype.OWL;
                                } else if (sleepTime.isBefore(LocalTime.of(22, 0)) && wakeTime.isBefore(LocalTime.of(7, 0))) {
                                    return Chronotype.LARK;
                                } else {
                                    return Chronotype.PIGEON;
                                }
                            })
                            .orElse(null); // бессонная ночь
                })
                .filter(type -> type != null) // исключаем бессонные ночи
                .toList();

        if (nightTypes.isEmpty()) return Chronotype.PIGEON;

        Map<Chronotype, Long> counts = nightTypes.stream()
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

        long maxCount = counts.values().stream().max(Long::compare).get();

        List<Chronotype> maxTypes = counts.entrySet().stream()
                .filter(entry -> entry.getValue() == maxCount)
                .map(Map.Entry::getKey)
                .toList();

        return maxTypes.size() > 1 ? Chronotype.PIGEON : maxTypes.get(0);
    }
}
