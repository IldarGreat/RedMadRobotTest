 <h1>Bulletin board</h1>
  Backend application based on spring boot for creating ads by authorized users <br>
  <h2>Table of content</h2>
  <ul type="circle">
    <li><a href="#howinstall">How install and run</a></li>
    <li><a href="#howuse">How use</a></li>
    <li> </li>
    <li> </li>
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
  After starting the project, you can look at the documentation at /api/documentation 
  And page will loks like:
  <img href = "https://vk.com/photo255877337_457247159">
