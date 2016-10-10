#!/bin/sh

#env
APP_HOME=/dubbo-service
#APP_NAME为各中心的名字，如订单中心记为changhong.order,

APP_NAME="changhong.order"
APP_PARM="aiopt.order.name=${APP_NAME}"

#此处的端口要与dubbo.properties中的端口对应，
#如，商品中心的为：slp.order.dubbo.port
PROCESS_PARM="slp.order.dubbo.port=${REST_PORT}"

for file in ${APP_HOME}/libs/**/*.jar;
do CP=${CP}:$file;
done

DUBBO_CONFIG_PATH=${APP_HOME}/config
LOG_PATH=${APP_HOME}/logs/dubbo-service-${REST_PORT}.log
CLASSPATH="${CP}"
CLASSPATH="${DUBBO_CONFIG_PATH}:${CLASSPATH}"
export CLASSPATH

MEM_ARGS="-Xms256m -Xmx512m -XX:PermSize=64M -XX:MaxPermSize=128M"
JAVA_OPTIONS="-Dfile.encoding=UTF-8 -Djava.net.preferIPv4Stack=true -Dsun.net.inetaddr.ttl=10 -Ddubbo.provider.timeout=20000 -Djava.security.egd=file:/dev/./urandom"

echo "TTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTT"
START_CMD="${MEM_ARGS} -D${APP_PARM} -D${PROCESS_PARM}  ${JAVA_OPTIONS} com.ai.opt.sdk.appserver.DubboServiceStart  >> $LOG_PATH & 2 > 1 &"

echo ${JAVA_HOME}
echo ${CLASSPATH}
echo ${DUBBO_PORT}
echo ${START_CMD}

sed -i "s/paas.sdk.mode=.*/paas.sdk.mode=${SDK_MODE}/g" ${APP_HOME}/config/paas/paas-conf.properties
sed -i "s/ccs.appname=.*/ccs.appname=${CCS_NAME}/g" ${APP_HOME}/config/paas/paas-conf.properties
sed -i "s/ccs.zk_address=.*/ccs.zk_address=${ZK_ADDR}/g" ${APP_HOME}/config/paas/paas-conf.properties
#用户信息
sed -i "s/user.appkey=.*/user.appkey=${USER_APPKEY}/g" ${APP_HOME}/config/remote_url.properties
sed -i "s/user.query.url=.*/user.query.url=${USER_QUERY_URL}/g" ${APP_HOME}/config/remote_url.properties
#物流信息
sed -i "s/ofc.appkey=.*/ofc.appkey=${OFC_APPKEY}/g" ${APP_HOME}/config/remote_url.properties
sed -i "s/ofc.query.url=.*/ofc.query.url=${OFC_QUERY_URL}/g" ${APP_HOME}/config/remote_url.properties
sed -i "s/ofc.order.create.url=.*/ofc.order.create.url=${OFC_ORDER_CREATE_URL}/g" ${APP_HOME}/config/remote_url.properties
sed -i "s/ofc.return.create.url=.*/ofc.return.create.url=${OFC_RETURN_CREATE_URL}/g" ${APP_HOME}/config/remote_url.properties




#====通过环境变量，替换配置文件中的信息 开始====

#此处的信息要与dubbo.properties中对应
sed -i "s/dubbo.registry.address=.*/dubbo.registry.address=${REST_REGISTRY_ADDR}/g" ${APP_HOME}/config/dubbo/dubbo.properties

#此处的端口要与dubbo.properties中的端口对应，
#如，商品中心的为：slp.order.dubbo.port
sed -i "s/slp.order.dubbo.port=.*/slp.order.dubbo.port=${REST_PORT}/g" ${APP_HOME}/config/dubbo/dubbo.properties

#此处的信息要与dubbo.properties中对应
sed -i "s/dubbo.protocol.contextpath=.*/dubbo.protocol.contextpath=${CONTEXT_PATH}/g" ${APP_HOME}/config/dubbo/dubbo.properties
#====通过环境变量，替换配置文件中的信息 结束====

echo "-------------------${APP_NAME} dubbo service start --------------------"
java ${START_CMD}
echo "${APP_NAME} dubbo server started!! logs at $LOG_PATH"