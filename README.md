# RMS
Study case project using various technology stack for building Resource Management System

## Overview
The application is using maven multimodule where each module will provides the same functionalities with different technology stack.

```
rms (parent project)
|-- src
|  |-- main
|      |-- docker (docker related configuration e.g. database server)
|      |-- sql (database scripts, sample data)
|-- rms-servlet-web (Servlet, JSP, JDBC technology stack)
|  |-- src
|  |   |-- main
|  |       |-- java (java source file)
|  |       |-- resources (configuration, properties)
|  |       |-- webapp (web specific files, css, js, jsp, html)
|  |-- pom.xml
|-- rms-spring-mvc (planned)
|-- rms-jsf-ejb (planned)
|-- pom.xml
```

## rms-servlet-web
It is implementing MVC pattern using only Servlet and JSP, combine with plain JDBC to handle databae operation.

It uses tomcat7-maven-plugin to spin up embedded tomcat 7, therefore no need to install tomcat 7 on your local machine. 

To run the application, execute maven command `mvn tomcat7:run` and browse http://localhost:8080/rms-servlet-web/index.jsp
