package ru.kvshe.languagetrainer.service;

public interface LessonObserver {
    int increaseCorrectAnswers();

    int increaseWrongAnswers();

    int getPercentageOfCorrectAnswers();

    void clean();
}
