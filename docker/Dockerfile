FROM java:8
#使用docker inspect -f {{".Config.Labels.maintainer"}} 容器名查看
LABEL email="13757578545@163.com" \
      author="pxisgod" \
      github-address=""
ARG APP_PATH_ARG="/app/app"
ARG LOG_PATH_ARG="/app/log"
ENV APP_PATH=$APP_PATH_ARG
ENV LOG_PATH=$LOG_PATH_ARG
ENV JAVA_BIN_HOME "/usr/lib/jvm/java-8-openjdk-amd64/bin"
RUN mkdir -p $APP_PATH_ARG
RUN mkdir -p $LOG_PATH_ARG
COPY --chown=root:root officialAccountDemo-release.tar.gz $APP_PATH_ARG
RUN cd $APP_PATH_ARG \
    && tar -zxvf officialAccountDemo-release.tar.gz -C . \
    && rm -rf officialAccountDemo-release.tar.gz \
    && chmod u+x remote/bin/*.sh
VOLUME $LOG_PATH_ARG
EXPOSE 8080
WORKDIR $APP_PATH_ARG/remote/bin
ENTRYPOINT ["start.sh"]
CMD ["local"]



