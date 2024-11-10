package ru.kvshe.languagetrainer.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Интерфейс для наблюдателя уроков. Этот интерфейс используется для отслеживания прогресса и статистики пользователя во время прохождения урока.
 * Он предоставляет методы для увеличения количества правильных и неправильных ответов, расчёта процента правильных ответов, очистки данных и вычисления общего прогресса.
 */
public interface LessonObserver {
    Map<Long, List<Integer>> progressMap = new HashMap<>();
    /**
     * Увеличивает количество правильных ответов на 1.
     */
    void increaseCorrectAnswers();

    /**
     * Увеличивает количество неправильных ответов на 1.
     */
    void increaseWrongAnswers();

    /**
     * Возвращает процент правильных ответов.
     *
     * @return процент правильных ответов в формате целого числа
     */
    int getPercentageOfCorrectAnswers();

    /**
     * Очищает все данные статистики текущего урока (например, количество правильных и неправильных ответов).
     */
    void clean();

    /**
     * Вычисляет процент прогресса на основе общего количества слов.
     *
     * @param quantityWords общее количество слов в уроке
     * @return процент прогресса, основанный на количестве слов
     */
    int getProgressPercentage(int quantityWords);
}
