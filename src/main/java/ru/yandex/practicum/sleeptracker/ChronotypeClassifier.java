package ru.yandex.practicum.sleeptracker;

import java.time.LocalTime;
import java.util.List;

public class ChronotypeClassifier {

    public static Chronotype classifyUser(List<SleepingSession> sessions) {
        int sovaCount = 0;
        int javoronokCount = 0;
        int golubCount = 0;

        for (SleepingSession session : sessions) {
            LocalTime sleepTime = session.getStart().toLocalTime();
            LocalTime wakeTime = session.getEnd().toLocalTime();

            // Игнорируем дневные сессии: считаем только ночные (начало после 18:00)
            if (sleepTime.isBefore(LocalTime.of(18, 0))) continue;

            if (sleepTime.isAfter(LocalTime.of(23, 0)) && wakeTime.isAfter(LocalTime.of(9, 0))) {
                sovaCount++;
            } else if (sleepTime.isBefore(LocalTime.of(22, 0)) && wakeTime.isBefore(LocalTime.of(7, 0))) {
                javoronokCount++;
            } else {
                golubCount++;
            }
        }

        if (sovaCount > javoronokCount && sovaCount > golubCount) return Chronotype.SOVA;
        if (javoronokCount > sovaCount && javoronokCount > golubCount) return Chronotype.JAVORONOK;
        return Chronotype.GOLUB;
    }
}
