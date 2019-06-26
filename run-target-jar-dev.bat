echo off
cd /d %~dp0
title spring-boot-jfinal-demo
rem 设置延迟环境变量扩充，即感叹号间的值不会因跳出循环而为空值。  
setlocal enabledelayedexpansion

call java -version
if not %errorlevel% == 0 (
    echo.
    echo java命令不存在!
    echo.
    goto THE_END_OF_THIS_FILE
)
echo.

rem jvm参数, OPTS可以紧跟java命令后面   
set OPTS=-Xms512M -Xmx4096M -XX:NewSize=32M -XX:-UseGCOverheadLimit -XX:+UseSerialGC

set JAR_FILE=target\spring-boot-jfinal-demo.jar

set FINAL_EXE_C=java %OPTS% -jar %JAR_FILE% --spring.profiles.active=dev

echo.
echo 执行命令:
echo %FINAL_EXE_C%
echo.

%FINAL_EXE_C%

:THE_END_OF_THIS_FILE
pause