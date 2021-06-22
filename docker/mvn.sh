#!/bin/bash

#-DskipDockerBuild 跳过 build 镜像
#-DskipDockerTag 跳过 tag 镜像
#-DskipDockerPush 跳过 push 镜像
#-DskipDocker 跳过整个阶段
mvn  -Dmaven.test.skip=true -P local clean package
