package ru.yandex.practicum.sleeptracker;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FileReader {

    public static List<SleepingSession> loadFromFile(File file) {
        try (Stream<String> lines = Files.lines(file.toPath())) {
            return lines
                    .filter(line -> !line.isBlank())
                    .map(FileReader::fromString)
                    .collect(Collectors.toList());
        } catch (IOException e) {
            throw new RuntimeException("Ошибка при чтении файла: " + file.getPath(), e);
        }
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
