# Soen387-A2-MessageBoard

# Start the server

## Setting up SQL Server

1. Sign into your local MYSQL server and `CREATE DATABASE soen_387` and `USE soen_387`.
1. Run `schema.sql` in your local MySQL server. (MySQL version 8). 
2. Ensure the following user exists: {Root:no pass}
3. Ensure database is deployed using the following params: `jdbc:mysql://localhost:3306/`


## Running the Java Servlet

1. Open pom.xml in BE folder as a intellij project.

2. Configure the project so it will automatically deploy on tomcat instance. Use the following link for help:  https://mkyong.com/intellij/intellij-idea-run-debug-web-application-on-tomcat/

3. Run the project, ensure you see that localhost:8080 is serving the project.


## Running the frontend angular app

1. Change into the FE angular project.

2. Run `npm install` to pull dependencies.

3. Run `ng serve` to run the application. 

Notes: Ensure the angular application to using the proper backend url: (config/environment.ts)


Authors: 

Ajith Thanam
Dimitri Spyropoulos
Muneeb Nezameddin
Renuchan Thambirajah
