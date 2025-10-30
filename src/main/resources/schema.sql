create table if not exists posts (
        id bigserial primary key,
        title varchar(256) not null,
        imagePath varchar(512) not null,
        likesCount integer not null default 0,
        text varchar(1024) not null,
        tags varchar(1024) not null
);

create table if not exists comments (
        id bigserial primary key,
        post_id bigserial not null references posts(id) on delete cascade,
        content varchar(256) not null
);

insert into posts(title, imagePath, likesCount, text, tags) values (
                                                                       'Как стать программистом с нуля',
                                                                       '/images/programmer.jpg',
                                                                       156,
                                                                       'Программирование - это не только знание языков, но и умение решать задачи. Начните с основ алгоритмов и структур данных, затем выберите язык, который вам интересен. Практикуйтесь ежедневно, решайте задачи на LeetCode и создавайте собственные проекты.',
                                                                       'programming,career,education,java,python'
                                                                   );

insert into posts(title, imagePath, likesCount, text, tags) values (
                                                                       'Менеджмент в IT: как управлять разработчиками',
                                                                       '/images/manager.jpg',
                                                                       42,
                                                                       'Эффективный менеджер в IT должен понимать технические аспекты проекта, уметь ставить четкие задачи и создавать комфортную атмосферу в команде. Важно балансировать между бизнес-требованиями и техническими возможностями.',
                                                                       'management,leadership,team,agile,scrum'
                                                                   );

insert into posts(title, imagePath, likesCount, text, tags) values (
                                                                       'DevOps практики для ускорения разработки',
                                                                       '/images/devops.jpg',
                                                                       89,
                                                                       'DevOps объединяет разработку и эксплуатацию, позволяя быстрее выпускать качественные продукты. CI/CD пайплайны, контейнеризация Docker, оркестрация Kubernetes - ключевые технологии современного DevOps.',
                                                                       'devops,docker,kubernetes,ci-cd,automation'
                                                                   );

insert into posts(title, imagePath, likesCount, text, tags) values (
                                                                       'Изучаем Spring Framework на практике',
                                                                       '/images/spring.jpg',
                                                                       203,
                                                                       'Spring Framework значительно упрощает разработку enterprise приложений на Java. В этой статье рассмотрим создание REST API с использованием Spring Boot, Spring Data JPA и Spring Security.',
                                                                       'java,spring,framework,rest-api,backend'
                                                                   );

insert into posts(title, imagePath, likesCount, text, tags) values (
                                                                       'Базы данных: SQL vs NoSQL',
                                                                       '/images/database.jpg',
                                                                       127,
                                                                       'Выбор между реляционными и нереляционными базами данных зависит от требований проекта. SQL базы отлично подходят для структурированных данных, в то время как NoSQL лучше справляется с большими объемами неструктурированной информации.',
                                                                       'database,sql,nosql,mysql,mongodb,performance'
                                                                   );

insert into posts(title, imagePath, likesCount, text, tags) values (
                                                                       'Микросервисная архитектура: плюсы и минусы',
                                                                       '/images/microservices.jpg',
                                                                       94,
                                                                       'Микросервисы позволяют разбить монолитное приложение на небольшие независимые сервисы. Это улучшает масштабируемость и ускоряет разработку, но добавляет сложности в управление и мониторинг.',
                                                                       'microservices,architecture,scalability,docker,kubernetes'
                                                                   );

insert into posts(title, imagePath, likesCount, text, tags) values (
                                                                       'Тестирование в Java: JUnit и Mockito',
                                                                       '/images/testing.jpg',
                                                                       78,
                                                                       'Качественное тестирование - залог стабильного приложения. Рассмотрим создание unit-тестов с JUnit 5, мокирование зависимостей с Mockito и лучшие практики тестового покрытия.',
                                                                       'java,testing,junit,mockito,unit-tests'
                                                                   );

insert into posts(title, imagePath, likesCount, text, tags) values (
                                                                       'Оптимизация SQL запросов для разработчиков',
                                                                       '/images/sql-optimization.jpg',
                                                                       112,
                                                                       'Медленные SQL запросы могут значительно снизить производительность приложения. Учимся использовать EXPLAIN, создавать правильные индексы и избегать N+1 проблемы в запросах.',
                                                                       'sql,optimization,performance,database,indexing'
                                                                   );

insert into posts(title, imagePath, likesCount, text, tags) values (
                                                                       'Docker для Java разработчиков',
                                                                       '/images/docker-java.jpg',
                                                                       145,
                                                                       'Контейнеризация приложений с Docker упрощает развертывание и обеспечивает consistency между средами. Собираем Docker образ для Spring Boot приложения и настраиваем multi-stage build.',
                                                                       'docker,java,spring-boot,containers,devops'
                                                                   );

insert into posts(title, imagePath, likesCount, text, tags) values (
                                                                       'REST API: лучшие практики проектирования',
                                                                       '/images/rest-api.jpg',
                                                                       168,
                                                                       'Создание качественного REST API требует соблюдения определенных принципов: правильное использование HTTP методов, версионирование API, пагинация, обработка ошибок и документация.',
                                                                       'rest,api,design,http,spring,backend'
                                                                   );

insert into posts(title, imagePath, likesCount, text, tags) values (
                                                                       'Карьера в Data Science: с чего начать',
                                                                       '/images/data-science.jpg',
                                                                       91,
                                                                       'Data Science объединяет статистику, программирование и предметную область. Изучайте Python, библиотеки pandas и scikit-learn, решайте реальные задачи на Kaggle и building portfolio проектов.',
                                                                       'data-science,python,machine-learning,career,analytics'
                                                                   );

insert into posts(title, imagePath, likesCount, text, tags) values (
                                                                       'React vs Angular: что выбрать в 2024',
                                                                       '/images/frontend-frameworks.jpg',
                                                                       134,
                                                                       'Оба фреймворка имеют свои преимущества. React предлагает большую гибкость и экосистему, в то время как Angular предоставляет полноценный framework с встроенными решениями.',
                                                                       'react,angular,frontend,javascript,comparison'
                                                                   );

insert into posts(title, imagePath, likesCount, text, tags) values (
                                                                       'Kubernetes для оркестрации контейнеров',
                                                                       '/images/kubernetes.jpg',
                                                                       76,
                                                                       'Kubernetes стал стандартом для управления контейнеризированными приложениями. Изучаем основные концепции: pods, services, deployments и настраиваем кластер для Spring Boot приложения.',
                                                                       'kubernetes,devops,containers,orchestration,docker'
                                                                   );

insert into posts(title, imagePath, likesCount, text, tags) values (
                                                                       'Spring Security: аутентификация и авторизация',
                                                                       '/images/spring-security.jpg',
                                                                       118,
                                                                       'Безопасность веб-приложений критически важна. Настраиваем JWT аутентификацию, ролевую модель доступа и защищаем REST endpoints с помощью Spring Security.',
                                                                       'spring,security,jwt,authentication,authorization'
                                                                   );

insert into posts(title, imagePath, likesCount, text, tags) values (
                                                                       'Оптимизация производительности Java приложений',
                                                                       '/images/java-performance.jpg',
                                                                       87,
                                                                       'Профилирование и оптимизация Java приложений: использование профайлеров, настройка JVM параметров, оптимизация работы с памятью и многопоточностью.',
                                                                       'java,performance,optimization,jvm,profiling'
                                                                   );

insert into posts(title, imagePath, likesCount, text, tags) values (
                                                                       'Agile методологии: Scrum и Kanban',
                                                                       '/images/agile.jpg',
                                                                       63,
                                                                       'Гибкие методологии разработки помогают командам эффективно работать в условиях изменяющихся требований. Разбираем различия между Scrum и Kanban, роли и артефакты.',
                                                                       'agile,scrum,kanban,project-management,team'
                                                                   );

insert into posts(title, imagePath, likesCount, text, tags) values (
                                                                       'GraphQL как альтернатива REST',
                                                                       '/images/graphql.jpg',
                                                                       102,
                                                                       'GraphQL предоставляет клиентам возможность запрашивать именно те данные, которые им нужны. Сравниваем с REST, рассматриваем схему GraphQL и реализацию на Java.',
                                                                       'graphql,api,rest,comparison,spring'
                                                                   );

insert into posts(title, imagePath, likesCount, text, tags) values (
                                                                       'Мониторинг приложений с Prometheus и Grafana',
                                                                       '/images/monitoring.jpg',
                                                                       71,
                                                                       'Система мониторинга позволяет отслеживать здоровье приложения в реальном времени. Настраиваем сбор метрик с Prometheus и создаем дашборды в Grafana.',
                                                                       'monitoring,prometheus,grafana,devops,metrics'
                                                                   );

-- Вставляем реальные комментарии
insert into comments(post_id, content) values (
                                                  1,
                                                  'Отличная статья! Какой язык посоветуете для начала: Java или Python?'
                                              );

insert into comments(post_id, content) values (
                                                  1,
                                                  'Спасибо за структурированную информацию. Добавлю, что важно не только учить синтаксис, но и понимать алгоритмы.'
                                              );

insert into comments(post_id, content) values (
                                                  1,
                                                  'Для начинающих рекомендую начать с Python - синтаксис проще, можно быстрее увидеть результаты.'
                                              );

insert into comments(post_id, content) values (
                                                  3,
                                                  'Отличный обзор DevOps практик! Не хватает информации про инфраструктуру как код с Terraform.'
                                              );

insert into comments(post_id, content) values (
                                                  3,
                                                  'В нашей компании после внедрения CI/CD время выпуска релизов сократилось с 2 недель до 1 дня!'
                                              );

insert into comments(post_id, content) values (
                                                  4,
                                                  'Spring Boot действительно упрощает разработку. Жду статью про Spring Cloud!'
                                              );

insert into comments(post_id, content) values (
                                                  4,
                                                  'Было бы здорово добавить примеры кода с обработкой исключений в REST контроллерах.'
                                              );

insert into comments(post_id, content) values (
                                                  5,
                                                  'Интересное сравнение! В нашем проекте используем PostgreSQL для транзакций и MongoDB для аналитики.'
                                              );

insert into comments(post_id, content) values (
                                                  6,
                                                  'Микросервисы - это мощно, но не забывайте про сложность отладки распределенных систем!'
                                              );

insert into comments(post_id, content) values (
                                                  8,
                                                  'EXPLAIN - отличный инструмент! Добавлю, что важно следить за статистикой таблиц для оптимизатора запросов.'
                                              );

insert into comments(post_id, content) values (
                                                  10,
                                                  'Для версионирования API рекомендую использовать URL versioning вместо header versioning - более прозрачно для клиентов.'
                                              );

insert into comments(post_id, content) values (
                                                  12,
                                                  'После работы с обоими фреймворками, выбрал React за огромное сообщество и готовые компоненты.'
                                              );

insert into comments(post_id, content) values (
                                                  14,
                                                  'Spring Security + JWT = отличная комбинация для защиты API. Важно не забывать про refresh tokens!'
                                              );

insert into comments(post_id, content) values (
                                                  16,
                                                  'Scrum отлично работает для продуктов с меняющимися требованиями, а Kanban - для поддержки и оперативных задач.'
                                              );

insert into comments(post_id, content) values (
                                                  18,
                                                  'Prometheus + Grafana - must have для любого продакшн приложения. Особенно важно мониторить бизнес-метрики.'
                                              );