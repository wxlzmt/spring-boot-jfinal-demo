echo off

@rem ���������Ŀ¼�л����������ļ�����Ŀ¼
cd /d %~dp0

title maven ��� spring-boot-jfinal-demo

@rem echo ��Ĭ���,����������ɹ�!

@rem set EXT_PARAM=-q

set EXT_PARAM=

call mvn clean install %EXT_PARAM% -f pom.xml

pause
