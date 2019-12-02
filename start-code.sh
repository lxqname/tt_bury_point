#!/bin/bash
cd `dirname $0`

# 环境参数
inv_pre=$1

# 当前主机名
host_name=`hostname`

if [ "$inv_pre" = "dev" ] && [ "$host_name" != "VM_0_6_centos" ]; then
	echo "Illegal environment and host name!"
	echo "当前主机名不是开发环境！请确认环境及命令。"
	exit -1
elif [ "$inv_pre" = "test" ] && [ "$host_name" != "app" ]; then
	echo "Illegal environment and host name!"
	echo "当前主机名不是测试环境！请确认环境及命令。"
	exit -1
elif  `hostname|grep '^UATapp[1234567]'  &>/dev/null` ; [ $? -ne 0 ] &&  [ "$inv_pre" = "uat" ]; then
	echo "Illegal environment and host name!"
	echo "当前主机名不是测试环境！请确认环境及命令。"
	exit -1
elif  `hostname|grep '^app[1234567]'  &>/dev/null` ; [ $? -ne 0 ] &&  [ "$inv_pre" = "prod" ]; then
	echo "Illegal environment and host name!"
	echo "当前主机名不是生产环境！请确认环境及命令。"
	exit -1
fi


# mysql数据库 ip及端口
mysql_addr=""

# mysql数据库账户名
mysql_user=""

# mysql数据库密码
mysql_pass=""

# skywalking的 ip及端口
skywalking_addr=""

# skywalking的应用目录
skywalking_dir=""

# zookeeper的 ip及端口
zookeeper_addr=""

# 定时任务的 ip及端口（逗号分隔）
elasticJob_server=""

# 开发环境
if [ "$inv_pre" = "dev" ]; then
	mysql_addr="10.0.0.17:3306"
	mysql_user="root"
	mysql_pass="cdpwy123"
	zookeeper_addr="10.0.0.17:2181"
	elasticJob_server="10.0.0.17:2181"
 	mem=200

#test环境
elif [ "$inv_pre" = "test" ]; then
	mysql_addr="10.16.0.10:8060"
	mysql_user="deepexi"
	mysql_pass='gGjMT$9UXJvNPUc71118'
	zookeeper_addr="10.16.0.10:21811"
	elasticJob_server="10.16.0.10:21811"
	mem=200

# 测试环境
elif [ "$inv_pre" = "uat" ]; then
	mysql_addr="10.16.0.26:3308"
	mysql_user="deepexi"
	mysql_pass='gGjMT$9UXJvNPUc7#019'
	skywalking_addr="10.16.0.27:11800"
	skywalking_dir="/data/skywalking"
	zookeeper_addr="10.16.0.25:21811"
	elasticJob_server="10.16.0.25:21811"
	mem=400

# 生产环境
elif [ "$inv_pre" = "prod" ]; then
	mysql_addr="10.16.0.11:3308"
	mysql_user="deepexi"
	mysql_pass="UbqX7Fsgoa*WdpgLw8ef"
	skywalking_addr="10.16.0.15:11800"
	skywalking_dir="/data/skywalking"
	zookeeper_addr="10.16.0.9:21812?backup=10.16.0.23:21812,10.16.0.24:21812"
	elasticJob_server="10.16.0.9:21812,10.16.0.23:21812,10.16.0.24:21812"
	mem=400
# 环境参数错误
else
	echo "Illegal shell param for invoriment as dev, test,uat, or prod!"
	echo "运行命令缺少环境参数，请补充完整，如：sh startup.sh dev"
	exit -1
fi

img_mvn="maven:3.3.3-jdk-8"                 # docker image of maven
m2_cache=~/.m2                              # the local maven cache dir
proj_home=$PWD                              # the project root dir
img_output="deepexi/tt-bury-point-center"         # output image bury-point

git pull  # should use git clone https://name:pwd@xxx.git

echo "use docker maven"
docker run --rm \
   -v $m2_cache:/root/.m2 \
   -v $proj_home:/usr/src/mymaven \
   -w /usr/src/mymaven $img_mvn mvn clean package -U

sudo mv $proj_home/tt-bury-point-center-provider/target/tt-bury-point-center-provider-*.jar $proj_home/tt-bury-point-center-provider/target/demo.jar # 兼容所有sh脚本
docker build -t $img_output .

mkdir -p $PWD/logs
chmod 777 $PWD/logs

# 删除容器
docker rm -f tt-bury-point-center &> /dev/null

version=`date "+%Y%m%d%H"`

spring_datasource_url=jdbc:mysql://${mysql_addr}/${inv_pre}_tt_bury_point_center?useUnicode=true\&characterEncoding=utf-8\&useSSL=false\&serverTimezone=Asia\/Shanghai


# 启动镜像 uat和prod环境使用
if [ "$inv_pre" = "uat" ]  ||  [  "$inv_pre" = "prod" ]; then
docker run -d --restart=on-failure:5 --privileged=true \
    --net=host \
    --dns 114.114.114.114 \
    --env 'TZ=Asia/Shanghai' \
    -w /home \
    -v /usr/share/zoneinfo/Asia/Shanghai:/etc/localtime:ro \
    -v $PWD/logs:/home/logs \
    -v ${skywalking_dir}/agent:/home/agent \
    -e SW_AGENT_NAME=bury-point-center  \
    -e SW_AGENT_COLLECTOR_BACKEND_SERVICES=${skywalking_addr} \
    --name tt-bury-point-center deepexi/tt-bury-point-center \
    java \
        -Xmx${mem}m \
        -Xms${mem}m \
        -Djava.security.egd=file:/${inv_pre}/./urandom \
        -Duser.timezone=Asia/Shanghai \
        -XX:+PrintGCDateStamps \
        -XX:+PrintGCTimeStamps \
        -XX:+PrintGCDetails \
        -XX:+UseG1GC \
        -XX:+HeapDumpOnOutOfMemoryError \
        -Xloggc:logs/gc_$version.log \
        -javaagent:/home/agent/skywalking-agent.jar  \
        -jar /home/demo.jar \
        --spring.profiles.active=${inv_pre} \
        --spring.datasource.url=$spring_datasource_url \
        --spring.datasource.username=${mysql_user} \
        --spring.datasource.password=${mysql_pass} \
        --dubbo.registry.address=zookeeper://${zookeeper_addr} \
		--elasticJob.regCenter.serverList=${elasticJob_server}

# 启动镜像 dev和test环境使用
elif [ "$inv_pre" = "dev" ]  ||  [  "$inv_pre" = "test" ]; then
docker run -d --restart=on-failure:5 --privileged=true \
    --net=host \
    --dns 114.114.114.114 \
    --env 'TZ=Asia/Shanghai' \
    -w /home \
    -v /usr/share/zoneinfo/Asia/Shanghai:/etc/localtime:ro \
    -v $PWD/logs:/home/logs \
    -e SW_AGENT_NAME=bury-point-center  \
    --name tt-bury-point-center deepexi/tt-bury-point-center \
    java \
        -Xmx${mem}m \
        -Xms${mem}m \
        -Djava.security.egd=file:/${inv_pre}/./urandom \
        -Duser.timezone=Asia/Shanghai \
        -XX:+PrintGCDateStamps \
        -XX:+PrintGCTimeStamps \
        -XX:+PrintGCDetails \
        -XX:+UseG1GC \
        -XX:+HeapDumpOnOutOfMemoryError \
        -Xloggc:logs/gc_$version.log \
        -jar /home/demo.jar \
        --spring.profiles.active=${inv_pre} \
        --spring.datasource.url=$spring_datasource_url \
        --spring.datasource.username=${mysql_user} \
        --spring.datasource.password=${mysql_pass} \
        --dubbo.registry.address=zookeeper://${zookeeper_addr} \
		--elasticJob.regCenter.serverList=${elasticJob_server}
fi
