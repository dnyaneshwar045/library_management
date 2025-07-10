@echo off
setlocal

:: Set Java classpath to include JDBC JAR
set CLASSPATH=.;mysql-connector-j-9.2.0.jar

:: Compile Java files
javac DBConnection.java LibraryManagement.java

:: Run the main class
java LibraryManagement

pause
