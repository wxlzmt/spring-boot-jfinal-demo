#!/bin/sh

#jar包名,也可以是war包名
jarfile=jfcms.jar

#以下内容无需修改
PID=$(ps -ef | grep $jarfile | grep -v grep | awk '{ print $2 }')
if [ -z "$PID" ]
then
    echo -e "\033[31m Application is already stopped ! \033[0m"
    exit 0
else
    kill -15 $PID
    echo -e "\033[32m 软杀进程,已执行命令: kill -15 $PID \033[0m"
fi
exit 0
