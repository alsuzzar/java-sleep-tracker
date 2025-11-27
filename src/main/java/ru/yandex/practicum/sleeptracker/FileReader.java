package ru.yandex.practicum.sleeptracker;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

public class FileReader {

    public static List<SleepingSession> loadFromFile(File file) {
        List<String> lines;
        try {
            lines = Files.readAllLines(file.toPath());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        List<SleepingSession> listOfSessions = lines.stream()
                .filter(line -> !line.isBlank())
                .map(FileReader::fromString)
                .collect(Collectors.toList());
        return listOfSessions;
    }

    public static SleepingSession fromString(String value) {
        String[] parts = value.split(";");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yy HH:mm");
        LocalDateTime start = LocalDateTime.parse(parts[0], formatter);
        LocalDateTime end = LocalDateTime.parse(parts[1], formatter);
        SleepQuality sleepQuality = SleepQuality.valueOf(parts[2]);
        return new SleepingSession(start,
                end,
                sleepQuality);
    }
}
