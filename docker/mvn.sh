#!/bin/bash

#-DskipDockerBuild ���� build ����
#-DskipDockerTag ���� tag ����
#-DskipDockerPush ���� push ����
#-DskipDocker ���������׶�
mvn  -Dmaven.test.skip=true -P local clean package



docker network create -d bridge my-net

docker run --name my_zookeeper -d --network my-net zookeeper:latest

docker run  -itd  --name official-account-demo --network my-net -p 80:8080 official-account-demo:latest

