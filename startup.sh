#!/usr/bin/env bash

## chang here
CURRENT_DATE_TIME=`date "+%Y-%m-%d_%H-%M-%S"`
SERVICE_DIR=./sculptor-boot-biz/target
SERVICE_NAME=sculptor-boot-biz
SPRING_PROFILES_ACTIVE=dev
HEAP_DUMP_PATH=./${SERVICE_NAME}_${CURRENT_DATE_TIME}.hprof

case "$1" in
	start)
		procedure=`ps -ef | grep -w "${SERVICE_NAME}" |grep -w "java"| grep -v "grep" | awk '{print $2}'`
		if [ "${procedure}" = "" ];
		then
			echo "start ..."
			if [ "$2" != "" ];
			then
				SPRING_PROFILES_ACTIVE=$2
			fi
			echo "spring.profiles.active=${SPRING_PROFILES_ACTIVE}"
			exec nohup java -Xms524m -Xmx1024m -XX:MetaspaceSize=124m -XX:MaxMetaspaceSize=224M -XX:+HeapDumpOnOutOfMemoryError -XX:HeapDumpPath=${HEAP_DUMP_PATH} -jar ${SERVICE_DIR}/${SERVICE_NAME}\.jar --spring.profiles.active=${SPRING_PROFILES_ACTIVE} > log &
			echo "start success"
		else
			echo "${SERVICE_NAME} is start"
		fi
		;;

	stop)
		procedure=`ps -ef | grep -w "${SERVICE_NAME}" |grep -w "java"| grep -v "grep" | awk '{print $2}'`
		if [ "${procedure}" = "" ];
		then
			echo "${SERVICE_NAME} is stop"
		else
			kill -9 ${procedure}
			sleep 1
			argprocedure=`ps -ef | grep -w "${SERVICE_NAME}" |grep -w "java"| grep -v "grep" | awk '{print $2}'`
			if [ "${argprocedure}" = "" ];
			then
				echo "${SERVICE_NAME} stop success"
			else
				kill -9 ${argprocedure}
				echo "${SERVICE_NAME} stop error"
			fi
		fi
		;;

	restart)
		./$0 stop
		sleep 1
		./$0 start $2
		;;

	*)
		echo "usage: $0 [start|stop|restart] [dev|test|prod]"
		;;
esac
