#!/bin/bash

#-DskipDockerBuild ���� build ����
#-DskipDockerTag ���� tag ����
#-DskipDockerPush ���� push ����
#-DskipDocker ���������׶�
mvn  -Dmaven.test.skip=true -P local clean package
