# RedisMap

## Описание
Этот проект представляет собой класс для работы с Redis, реализующую интерфейс `Map<String, String>` 
с использованием библиотеки Jedis.
Проект предоставляет удобные методы для взаимодействия с Redis, такие как операции чтения, записи и удаления данных,
а также позволяет легко управлять конфигурацией подключения через файл `application.properties`.

## Требования
- Java 17+
- Docker
- Docker Compose
- Jedis (добавлен как зависимость)
## Установка

1. Клонируйте репозиторий:

    ```bash
    git clone https://github.com/A1ekseiPanov/RedisMap.git
    ```

2. Перейдите в директорию проекта:

    ```bash
    cd RedisMap
    ```

3. Запустите Redis с помощью Docker Compose:

    ```bash
    docker-compose up -d
    ```

4. Убедитесь, что Redis успешно запущен:

    ```bash
    docker ps
    ```

5. Откройте проект в вашей IDE.