@echo off

set GLASSFISH=C:\glassfish4\glassfish
cd src\

REM Autogenerowane ?gówno? (wcześniej javac generował dodatkowe pliki:)
REM del TbModelEntity_.java TbCustomerEntity_.java TbInsuranceEntity_.java
del *.class 


echo KOMPILACJA:
javac ^
-d . ^
-classpath ^
%GLASSFISH%\lib\javaee.jar;^
%GLASSFISH%\lib\sqljdbc4.jar; ^
*.java ^
-Xlint


REM cls

echo Uruchomienie:
java -classpath ^
%GLASSFISH%\lib\appserv-rt.jar;^
%GLASSFISH%\lib\javaee.jar;^
%GLASSFISH%\lib\sqljdbc4.jar;. ^
Client "Pan Samochodzik.txt"

pause