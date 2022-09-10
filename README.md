 <h1>Bulletin board</h1>
  Backend application based on spring boot for creating ads by authorized users <br>
  <h2>Table of content</h2>
  <ul type="circle">
    <li><a href="#howinstall">How install and run</a></li>
    <li><a href="#howuse">How use</a></li>
    <li><a href="#techologies">Techologies</a></li>
    <li><a href="#problems">Problems</a></li>
    <li><a href="#testtext">Test task text</a></li>
  </ul>
  <h2 id="howinstall">How install and run</h2>
  Beforehand you must have <a href="https://docs.master.dockerproject.org/desktop/install/windows-install/">docker</a>
  and
  jdk 17+ <br>
  <pre>
docker-compose up --build 
mvn compile & package
java -jar red_mad_robot_test-0.0.1-SNAPSHOT.jar
</pre>
  With this command you install the postgresql db, generate the required classes and run the project
  <h2 id = "howuse">How use</h2>
  After starting the project, you can look at the documentation at /api/documentation <br>
  And page will loks like: <br> <br>
  <img src = "https://user-images.githubusercontent.com/90307025/189490329-44d933da-4b29-4728-955a-b4862492cc33.png" alt = "Open API example">
  <img src = "https://user-images.githubusercontent.com/90307025/189490287-b48be709-3499-4cc4-ad9d-90bdec025ede.png" alt = "Open API example">
  If you want to see raw Swagger yml file, then follow the <a href = "https://github.com/IldarGreat/RedMadRobotTest/blob/main/src/main/resources/static/dist/openapi_bulletin_board.yml">path</a>
<h2 id = "techologies">Techologies</h2>
Technology used in the project:
<ol>
    <li>Intelij IDEA 2022.2.1 Ultimate</li>
    <li>JDK 18 correto</li>
    <li>Spring boot 2.7.3</li>
    <li>Maven 3.8.1</li>
    <li>PostgreSQL</li>
    <li>Testcontainers</li>
    <li>Lombook,mupstract,jjwt,liquibase</li>
    <li>Docker compose</li>
  </ol>
<h2 id = "problems">Problems</h2>
 <ol>
    <li><strong>3 days to complete project</strong> <br> I had a lack of time to complete the test task, everything writing in a harry and not all the planned functionality was implemented e.gs.<br>1)user communication, but you can see a <a href = "https://github.com/IldarGreat/RedMadRobotTest/issues/5">issue</a> about it<br>2)Displaying code coverage. I wanted to connect jacoco agent</li>
    <li><strong>JVM</strong><br>My dev environment(IDEA) cannot connect to the postgres db through drivers on my computer, it immediately starts to crash. I found only one person with a similar problem on <a href = "https://youtrack.jetbrains.com/issue/DBE-16172">youtrake</a> and he just changing jvm to version 16 and it help him, but not for me</li>
    <li><strong>Roles</strong><br>The text of the task says to write roles to users, although I don't understand why,because all users are equal</li>
  </ol>
  <h2 id = "testtext">Test task text</h2>
  <pre>
  Тестовое задание

Задание: разработать backend-сервер на Spring для функционирования доски объявлений.

Функционал, который требуется реализовать:
Регистрация и аутентификация пользователя в личном кабинете:
пользователь при регистрации должен указать роль, email и пароль;
аутентификацию реализовать через вход по email и паролю.
В личном кабинете пользователь может создать объявление и разместить его на доске объявлений в общем списке. Объявление содержит название, описание, контакты продавца и изображения.
Доска объявлений в данном случае - это список всех объявлений с многочисленными фильтрами (продумать максимально возможные варианты фильтров на своё усмотрение), который отображается на главной странице сервиса.
Пользователь может как размещать свои объявления, так и совершать сделки в рамках других объявлений.
Объявления имеют 2 статуса - активное и снятое с публикации.
Продумать и реализовать вариант коммуникации между покупателем и продавцом во время совершения сделки.
Для всех методов необходимо реализовать API-методы с документацией на Swagger.
Покрыть весь функционал тестами. Желательно использовать TDD при разработке.

Дополнительные требования:
Сделать обертку исходного кода в docker-образ (добавить в корневую директорию файл Dockerfile, docker-compose.yml при необходимости).
В readme файл разместить текст данного задания, а, также, инструкцию по развертыванию проекта и основные команды для запуска.
Исходный код выложить на github.com в публичный репозиторий.
При создании коммитов писать осмысленные названия.
Использовать инструмент тестового покрытия для отображения % покрытия исходного кода тестами.
Для проверки кода дополнительно подключить линтер на выбор.
  </pre>
