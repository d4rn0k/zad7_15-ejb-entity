@echo off

set GLASSFISH=C:\glassfish4\glassfish
cd src\

echo Delete all *.class files
del *.class

echo Compile project *.java files
javac ^
-d . ^
-classpath %GLASSFISH%\lib\javaee.jar;^
%GLASSFISH%\lib\gf-client.jar;^
%GLASSFISH%\lib\;^
%GLASSFISH%\modules\;. ^
IGameRemote.java GameRemote.java ^
-Xlint

echo Start glassfish Server
call %GLASSFISH%\bin\asadmin.bat stop-domain
call %GLASSFISH%\bin\asadmin.bat start-domain

echo Create .jar from .class and move to glassfish domain1 catalog
jar cvf %GLASSFISH%\domains\domain1\autodeploy\ejb-project.jar pl/jrj/game/IGameRemote.class GameRemote.class 

pause