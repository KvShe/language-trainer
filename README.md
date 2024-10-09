## Training language
### Spring Boot, Spring Web, Spring AOP
#### made:
- реализованы функции:
    - implementation in Spring:
        - реализовано меню на главной странице
        - реализация CRUD:
            - create
            - read
            - update
            - delete
        - показать список всех слов
- добавлена база данных PostgreSQL
- добавлено логирование в консоль через Spring AOP & Lombok (пока для класса WordService)
- добавлен таймер для измерения времени выполнения метода (пока для класса WordService)

---

#### to-do list:

- implement functions:
  - проверка правильного написания слова
  - игнорируется регистр (upper or low) при проверке слова
  - При добавлении слова - убирать пробелы в начале и в конце
  - Убрать двойные пробелы при вводе слова
  - Убрать знаки препинания при вводе
  - Защита от случайного нажатия Enter в пустом поле ввода
- Банк слов, которые давно не проверялись
- add в меню кнопку выключения приложения
- add pagination
- add sorts:
    - by english
    - by russian
- add words:
    - implement - осуществлять
    - execute - выполнять
    - successfully - успешно
    - unable - неспособный
    - properties - характеристики
    - util - использовать
    - example - пример
    - override - переопределить
    - accept - принимать
    - accessed - доступ
    - everything - всё
    - sender - отправитель
    - invoke - вызывать
    - scope - рамки (зона видимости)
    - lazy - ленивый
    - property - свойство
    - surname - фамилия
    - whose - чей
    - from - от, из, с
    - to - к, в, до
    - explicit - явный
    - notification - уведомление
    - each - каждый
    - unit - блок, единица
    - resolve - решать, разрешить
    - sender - отправитель
    - completed - завершённый
    - receiver - получатель
    - quantity - количество
    - response - ответ
    - denied - отклонён
    - duration - продолжительность