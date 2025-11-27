package ru.yandex.practicum.sleeptracker;

import java.time.LocalDateTime;
import java.time.LocalTime;

public class SleepingSession {

    protected LocalDateTime start;
    protected LocalDateTime end;
    protected SleepQuality sleepQuality;

    public SleepingSession(LocalDateTime start,
                           LocalDateTime end,
                           SleepQuality sleepQuality) {
        this.start = start;
        this.end = end;
        this.sleepQuality = sleepQuality;
    }

    public LocalDateTime getStart() {
        return start;
    }

    public LocalDateTime getEnd() {
        return end;
    }

    public SleepQuality getSleepQuality() {
        return sleepQuality;
    }

    /**
     * Проверка, является ли сессия ночным сном
     * Дневные сессии считаем те, что начались до 18:00
     */
    public boolean isNightSleep() {
        return start.toLocalTime().isAfter(LocalTime.of(18, 0));
    }

    /**
     * Время начала сна для анализа хронотипа
     */
    public LocalTime getSleepStartTime() {
        return start.toLocalTime();
    }

    /**
     * Время пробуждения для анализа хронотипа
     */
    public LocalTime getSleepEndTime() {
        return end.toLocalTime();
    }

    @Override
    public int hashCode() {
        return start.hashCode() ^ end.hashCode() ^ sleepQuality.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof SleepingSession)) return false;
        SleepingSession other = (SleepingSession) obj;
        return start.equals(other.start)
                && end.equals(other.end)
                && sleepQuality == other.sleepQuality;
    }

    @Override
    public String toString() {
        return "SleepingSession{" +
                "start=" + start +
                ", end=" + end +
                ", sleepQuality=" + sleepQuality +
                '}';
    }
}
