package ru.yandex.practicum.sleeptracker;

public class SleepAnalysisResult<T> {
    private String message;
    private T value;

    public SleepAnalysisResult(String message, T value) {
        this.message = message;
        this.value = value;
    }

    public String getMessage() {
        return message;
    }

    public T getValue() {
        return value;
    }
}
