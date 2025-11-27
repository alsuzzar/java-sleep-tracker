package ru.yandex.practicum.sleeptracker;

import ru.yandex.practicum.sleeptracker.functions.*;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class SleepTrackerApp {

    public static void main(String[] args) {

        if (args.length == 0) {
            System.out.println("Ошибка: укажите путь к файлу при запуске программы.");
            return;
        }
        String filePath = args[0];
        File file = new File(filePath);

        List<SleepingSession> listOfSessions = FileReader.loadFromFile(file);
        List<Function<List<SleepingSession>, SleepAnalysisResult<?>>> functions = new ArrayList<>();
        functions.add(new SessionCountFunc());
        functions.add(new SessionDurationMinFunc());
        functions.add(new SessionDurationMaxFunc());
        functions.add(new SessionDurationAvgFunc());
        functions.add(new BadSessionsCountFunc());
        functions.add(new SleeplessNightsFunc());
        functions.add(new ChronotypeFunc());

        for (Function<List<SleepingSession>, SleepAnalysisResult<?>> func : functions) {
            SleepAnalysisResult<?> result = func.apply(listOfSessions);
            System.out.println(result.getMessage() + ": " + result.getValue());
        }
    }
}