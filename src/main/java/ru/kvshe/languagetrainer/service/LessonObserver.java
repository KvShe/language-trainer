package ru.kvshe.languagetrainer.service;

public interface LessonObserver {
    void increaseCorrectAnswers();

    void increaseWrongAnswers();

    int getPercentageOfCorrectAnswers();

    void clean();

    int getProgressPercentage(int quantityWords);
}
