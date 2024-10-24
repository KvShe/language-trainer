## Language Training

### Spring Boot, Spring Web, Spring AOP, Swagger (SpringDoc OpenAPI)

#### made:

- implemented functions:
    - меню на главной странице
    - CRUD
    - показать список всех слов
    - проверка правильного написания слова
        - игнорируется регистр (upper or low) при проверке слова
        - При добавлении слова - убирать пробелы в начале и в конце
        - Убрать двойные пробелы при вводе слова
        - Убрать знаки препинания при вводе
- add postgreSQL
- add логирование в консоль через Spring AOP & Lombok
    - WordService
- add таймер для измерения времени выполнения метода
    - WordService

---

#### to-do list:

- implement functions:
    - Защита (protection) от случайного нажатия Enter в пустом поле ввода
    - update field lastUse при правильном ответе
- Банк слов, которые давно не проверялись
- add в меню кнопку выключения приложения
- add pagination
    - ленивая загрузка или инфинит скролл (infinite scroll)
        - при этом новые данные загружаются автоматически,
        - когда пользователь прокручивает страницу вниз
    - add elasticsearch - db for logs
    - add logback - библиотека для логирования
- add sorts:
    - by english
    - by russian
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