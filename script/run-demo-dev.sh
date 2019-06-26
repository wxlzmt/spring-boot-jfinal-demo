#!/bin/sh
#日志文件所在目录
logpath=/home/spring/logs/
#日志文件名
logfilename=jfcms.log
#jar文件
jarfile=jfcms.jar

active=dev

#清理缓存目录
CACHE_FOLDER=/home/spring/tmp/tomcat.jfcms/
rm -rf $CACHE_FOLDER

#想要附加的命令行参数
extCommandLineParam="--server.tomcat.basedir=$CACHE_FOLDER $@"

#-------以下内容原封不动,无需修改----------------

PID=$(ps -ef | grep $jarfile | grep -v grep | awk '{ print $2 }')
#-n str1 当串的长度大于0时为真(串非空) 
#-z str1 当串的长度为0时为真(空串) 
if [ -n "$PID" ]; then
    echo -e "\033[31m 系统已存在正在运行的同名程序进程,请勿重复运行! \033[0m"
    exit 0
fi

if [ ! -d $logpath ];then
  echo "日志所在目录不存在,将自动创建目录: $logpath"
  mkdir -p $logpath
fi
logfile=$logpath$logfilename

if [ ! -f $logfile ];then
  #创建新的空文件
  touch $logfile
fi

echo "日志文件所在位置: $logfile"
echo -e "\033[32m已使用nohup执行命令:\033[0m java -jar $jarfile --spring.profiles.active=$active $extCommandLineParam > $logfile 2>&1 &"
nohup java -jar $jarfile --spring.profiles.active=$active $extCommandLineParam > $logfile 2>&1 &

#注意:使用nohup运行之后,如果想关掉进程,
#先使用 ps-ef|grep java 查询pid , 然后使用 kill -15 pid
#echo "建议使用 kill -15 pid 停止进程,不要使用 kill -9 pid ,否则会导致服务无法正常从注册中心下线!"

echo "如果需要访问日志,请执行: tail -f $logfile"
