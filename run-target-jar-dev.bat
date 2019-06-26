echo off
cd /d %~dp0
title spring-boot-jfinal-demo
rem �����ӳٻ����������䣬����̾�ż��ֵ����������ѭ����Ϊ��ֵ��  
setlocal enabledelayedexpansion

call java -version
if not %errorlevel% == 0 (
    echo.
    echo java�������!
    echo.
    goto THE_END_OF_THIS_FILE
)
echo.

rem jvm����, OPTS���Խ���java�������   
set OPTS=-Xms512M -Xmx4096M -XX:NewSize=32M -XX:-UseGCOverheadLimit -XX:+UseSerialGC

set JAR_FILE=target\spring-boot-jfinal-demo.jar

set FINAL_EXE_C=java %OPTS% -jar %JAR_FILE% --spring.profiles.active=dev

echo.
echo ִ������:
echo %FINAL_EXE_C%
echo.

%FINAL_EXE_C%

:THE_END_OF_THIS_FILE
pause