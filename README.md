# MySimpleSpringBootBlog

## *EN*
#### Link to the previous project based on the standard Spring Framework: https://github.com/AlexForGH/MySimpleSpringBlog.git
A simple project to demonstrate basic development capabilities using the Spring framework
using Spring Boot.
Technology stack: Spring Framework, Spring Boot, H2DB, HTML, Thymeleaf, Junit5, Mockito.

### Application features:
    - adding posts
    - editing posts
    - deleting posts
    - searching posts by tags
    - adding comments to posts
    - editing comments to posts
    - deleting comments to posts

### Application deployment:
    - Before you begin, you'll need:
            - Java (JRE) (version 23 was used during project development)
    1. Using an IDE (IntelliJIdea was used during project development):
            - clone the repository
            - open the project in the IDE
            - run the command to build the executable Uber-Jar: ./mvnw clean package spring-boot:repackage
            - in the resulting target directory, run the executable
              file: ./MySimpleSpringBootBlog-0.0.1-SNAPSHOT.jar
            - open a browser at http://localhost:8080/posts
            - the application's start page will open
    2. Without an IDE
            - clone the repository
            - in the root directory Run the command to build the executable from the project folder.
              Uber-Jar: ./mvnw clean package spring-boot:repackage
            - in the resulting target directory, run the executable:
              ./MySimpleSpringBootBlog-0.0.1-SNAPSHOT.jar
            - open a browser and navigate to http://localhost:8080/posts
            - the application's start page will open.

### Testing the application:
    1. Using an IDE (IntelliJIdea was used during project development):
        - right-click on the test folder and select "Run Tests in 'MySimpleSpringBootBlog'"
        - all project tests will run.
    2. Without an IDE:
        - open a terminal in the project folder and enter the command: ./mvnw test
        - all project tests will run.


## *RU*
#### Ссылка на предыдущий проект на основе стандартного Spring Framework: https://github.com/AlexForGH/MySimpleSpringBlog.git
Простой проект для демонстрации базовых возможностей разработки с использованием фреймворка Spring
с использованием Spring Boot.
Технологический стек: Spring Framework, Spring Boot, H2DB, HTML, Thymeleaf, Junit5, Mockito.

### Возможности приложения:
    - добавление постов
    - редактирование постов
    - удаление постов
    - поиск постов по тегам
    - добавление комментариев к постам
    - редактирование комментариев к постам
    - удаление комментариев к постам

### Развертывание приложения:
    - Перед началом работы необходимы:
            - Java (JRE) (при разработке проекта использовалась версия 23)
    1. Через IDE (при разработке проекта использовалась IntelliJIdea):
            - клонировать репозиторий
            - открыть проект в IDE
            - выполнить команду для сборки исполняемого Uber-Jar: ./mvnw clean package spring-boot:repackage
            - в появившейся директории target выполнить запуск исполняемого 
              файла: ./MySimpleSpringBootBlog-0.0.1-SNAPSHOT.jar
            - зайти в браузер по адресу http://localhost:8080/posts
            - откроется стартовая страница приложения
    2. Без использования IDE
            - клонировать репозиторий
            - в корне папки проекта выполнить команду для сборки исполняемого
              Uber-Jar: ./mvnw clean package spring-boot:repackage
            - в появившейся директории target выполнить запуск исполняемого
              файла: ./MySimpleSpringBootBlog-0.0.1-SNAPSHOT.jar
            - зайти в браузер по адресу http://localhost:8080/posts
            - откроется стартовая страница приложения

### Тестирование приложения:
    1. Через IDE (при разработке проекта использовалась IntelliJIdea):
            - на папке test нажать ПКМ и выбрать пункт "Run Tests in 'MySimpleSpringBootBlog'"
            - запустятся все тесты проекта
    2. Без использования IDE
            - в папке проекта вызвать терминал и ввести команду: ./mvnw test
            - запустятся все тесты проекта

