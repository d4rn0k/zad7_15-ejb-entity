@echo off

set GLASSFISH=C:\glassfish4\glassfish

cd src\

del TbModelEntity_.java TbCustomerEntity_.java TbInsuranceEntity_.java 

echo KOMPILACJA:
javac ^
-d . ^
-classpath ^
%GLASSFISH%\lib\javaee.jar;^
%GLASSFISH%\lib\gf-client.jar;. ^
*.java ^
-Xlint

cls
echo Uruchomienie:
java -classpath ^
%GLASSFISH%\lib\;^
%GLASSFISH%\lib\javaee.jar;^
%GLASSFISH%\lib\appserv-rt.jar;. ^
Client daneTestowe.txt

pause