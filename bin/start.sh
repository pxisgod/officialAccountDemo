#!/bin/bash
################

################
time=`date '+%Y%m%d'`
user=`whoami`
serviceId="official-account-demo"

isRun()
{
   pid=`ps -ef | grep $serviceId | grep $user | grep -v grep | awk '{print $2}'`
   if [[ ${pid}X != X ]]; then
         echo "service is ruing."
         echo "exit."
         exit 0
   fi
}
isRun

appName=remote
V_HOME=$APP_PATH/$appName

for i in $V_HOME/lib/*.jar;
do
  jarFile=${jarFile}:$i;
done

export JAVA_OPTS="-server -Xmx2048m -Xms1024m -Xmn512m  -XX:PermSize=1024m -XX:MaxPermSize=2048m"
export log_path=$LOG_PATH
export log_name=$HOSTNAME
export CLASSPATH="$V_HOME/config:${jarFile}"

$JAVA_BIN_HOME/java $JAVA_OPTS \
        -Dservice.id=${serviceId}
        -Dspring.profiles.active=$1 \
        -Dserver.port=8080 \
        -Dfile.encoding=utf-8 \
        com.px.oad.Application
