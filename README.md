## Language Training

### Spring Boot, Spring Web, Spring Jpa, Spring AOP, Swagger (SpringDoc OpenAPI)

#### made:

- implemented functions:
    - show меню на главной странице
    - implemented crud
    - show список всех добавленных слов
    - check правильного написания слова
        - ignored регистр (upper or low) при проверке слова
        - delete пробелы в начале и в конце
        - delete двойные пробелы при вводе слова
        - delete знаки препинания при вводе
        - update field lastUse при правильном ответе
        - protection от случайного нажатия Enter в пустом поле ввода
    - sorts words:
        - by english
        - by russian
- add postgreSQL
- add логирование в консоль через Spring AOP & Lombok
    - WordService
- add таймер для измерения времени выполнения метода
    - WordService

---

#### to-do list:

- add банк слов, которые давно не проверялись
- add в меню кнопку выключения приложения
- add pagination
    - ленивая загрузка или infinite scroll
        - new data loading automatically, когда пользователь прокручивает страницу вниз
    - add elasticsearch - db for logs
    - add logback - библиотека для логирования
- add validator (hibernate, jacarta) - ограничение на вводимое слово по длине символов (255)
- add words:
    - util - использовать
    - tool - инструмент
    - example - пример
    - override - переопределить
    - accept - принимать
    - everything - всё
    - invoke - вызывать
    - scope - рамки (зона видимости)
    - lazy - ленивый
    - surname - фамилия
    - whose - чей
    - from - от, из, с
    - to - к, в, до
    - explicit - явный
    - notification - уведомление
    - each - каждый
    - unit - блок, единица
    - resolve - решать, разрешить
    - completed - завершённый
    - quantity - количество
    - denied - отклонён
    - duration - продолжительность
    - confirmation - подтверждений
    - protection - защита