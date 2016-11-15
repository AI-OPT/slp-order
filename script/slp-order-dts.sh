#!/bin/sh

PROCESS_NAME="SlpOrderDtsMain"
PROCESS_PARM="slp.order.dts.main=$PROCESS_NAME"
CUR_USER=`whoami`
RUNNER_PRODUCT_NAME=slp.order.dts
BIN_PATH=${HOME}/bin/slp-order
LOG_PATH=$HOME/logs/slp-logs/slp-order-dts.log
CLASSPATH="${CLASSPATH}"
MEM_ARGS="-Xms256m -Xmx512m -XX:PermSize=64M -XX:MaxPermSize=128M"

. "${BIN_PATH}/setEnv.sh"

if [ "$1" = "start" ]; then
        echo "开始启动DTS"
        PROCESS_ALIVE_STATUS=$($HOME/sbin/monitor_process.sh $PROCESS_NAME $PROCESS_PARM)
        if [ "$PROCESS_ALIVE_STATUS" = "PROCESS_EXIST" ];
        then
        echo " $RUNNER_PRODUCT_NAME DtsMain server had already started!! process param is [$PROCESS_PARM]"
        exit 0;
        fi


        
        ${JAVA_HOME}/bin/java ${MEM_ARGS} -D${PROCESS_PARM}  ${JAVA_OPTIONS} com.ai.slp.order.dts.main.SlpOrderDtsMain >> $LOG_PATH & 2>&1&
        echo "$RUNNER_PRODUCT_NAME DtsMain server started!! logs at $LOG_PATH"
else if [ "$1" = "stop" ]; then
        echo "开始停止DTS"
        PROCESS_NUMBER=0
        ps -ef|grep $PROCESS_NAME |grep ${CUR_USER} | grep $PROCESS_PARM | grep java | grep -v grep | awk '{print $2}' |while read pid
        do
        PROCESS_NUMBER=PROCESS_NUMBER+1
        kill ${pid} 2>&1 >/dev/null
        echo "stopped $RUNNER_PRODUCT_NAME DtsMain server process name :${PROCESS_NAME},process param:${PROCESS_PARM},PID:${pid} "
        done
        if [ "$PROCESS_NUMBER" = 0 ];
        then
                echo "$RUNNER_PRODUCT_NAME DtsMain server not exists. process name :${PROCESS_NAME},process param:${PROCESS_PARM}"
        fi
        exit 0;
else if [ "$1" = "monitor" ]; then
        PROCESS_ALIVE_STATUS=$($HOME/sbin/monitor_process.sh $PROCESS_NAME $PROCESS_PARM)
        if [ "$PROCESS_ALIVE_STATUS" = "PROCESS_EXIST" ];
        then
        echo "$RUNNER_PRODUCT_NAME DtsMain server started!! process param is [$PROCESS_PARM]"
        exit 0;
        else
                echo "$RUNNER_PRODUCT_NAME DtsMain server not exists. process name :${PROCESS_NAME},process param:${PROCESS_PARM}"
                exit 0;
        fi
else
                        echo "ERROR: Please input a correct argument,such as: start or stop or monitor"
        exit 1
fi
fi
fi
