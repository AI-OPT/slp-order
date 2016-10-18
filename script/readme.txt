1.编译打包
#一定要先clean
gradle clean
#然后再打包
gradle build -x test
2.生成镜像
#在本地生成带私服前缀的镜像(每次打镜像前版本号要更新)
docker build -t 10.19.13.19:5000/slp-order:v1.0 .   
#将镜像推送到私服
docker push 10.19.13.19:5000/slp-order:v1.0

3. 运行镜像
#--net=host  表示为主机(host)模式  去掉该配置，默认为桥接(bridge)模式
#-e 代表需要设置的环境变量
docker run -d --name slp-order -p 10887:10887 -e "REST_REGISTRY_ADDR=10.19.13.13:29181" -e "REST_PORT=10887" -e "CONTEXT_PATH=slp-order" -e "SDK_MODE=0" -e "CCS_NAME=aiopt-ch-order" -e "ZK_ADDR=10.19.13.13:29181" 
-e "OFC_RETURN_CREATE_URL=http://124.207.3.100:8083/opaas/http/srv_slp_externalorderrefund_create" -e "OFC_ORDER_CREATE_URL=http://124.207.3.100:8083/opaas/http/srv_slp_externalorder_create" 
-e "OFC_QUERY_URL=http://124.207.3.100:8083/opaas/http/srv_slp_externalorder_query" -e "OFC_APPKEY=3a83ed361ebce978731b736328a97ea8"
-e "USER_QUERY_URL=http://124.207.3.100:8083/opaas/http/srv_up_user_getuserinfobyopenid_qry" -e "OFC_SIGN=b5e94a7c37a1ab6ea72a5b21bc8d4a9ed382067adbf57f96a206e9d7a6848933" 
-e "INTEGRAL_RATE_URL=http://124.207.3.100:8083/opaas/http/srv_up_integral_rate_qry" 10.19.13.18:5000/slp-order:v1.0 
#查看镜像启动日志
docker logs slp-order
#进入容器，查看镜像内部的情况
docker exec -it slp-order /bin/bash
#删除运行的容器
docker rm -fv slp-order

#=============更新日志========================#
*2016-09-23
1）初始版本

