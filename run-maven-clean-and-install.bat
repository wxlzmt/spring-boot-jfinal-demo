echo off

@rem 此命令将工作目录切换到批处理文件所在目录
cd /d %~dp0

title maven 打包 spring-boot-jfinal-demo

@rem echo 静默输出,不报错即代表成功!

@rem set EXT_PARAM=-q

set EXT_PARAM=

call mvn clean install %EXT_PARAM% -f pom.xml

pause
