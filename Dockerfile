# In /data folder the DB is stored, set volume to this directory to persist storage outside of container

FROM gradle:jdk8-alpine as builder

MAINTAINER Semen Tenishchev <tenischev.semen@gmail.com>

ARG host_proxy=spbsrv-proxy.t-systems.ru
ARG port_proxy=3128

ENV http_proxy=http://$host_proxy:$port_proxy \
    https_proxy=http://$host_proxy:$port_proxy \
    HTTP_PROXY=http://$host_proxy:$port_proxy \
    HTTPS_PROXY=http://$host_proxy:$port_proxy

COPY build.gradle /home/gradle/build.gradle
COPY src/ /home/gradle/src/

RUN echo "systemProp.http.proxyHost=$host_proxy" >> gradle.properties && \
    echo "systemProp.http.proxyPort=$port_proxy" >> gradle.properties && \
    echo "systemProp.https.proxyHost=$host_proxy" >> gradle.properties && \
    echo "systemProp.https.proxyPort=$port_proxy" >> gradle.properties

RUN gradle bootJar

FROM bellsoft/liberica-openjdk-alpine-musl

ARG USER=news-storage

RUN adduser -D -u 1001 $USER

COPY --chown=1001 run.sh /run.sh
COPY --chown=1001 --from=builder /home/gradle/build/libs/news-storage*.jar /app/news-storage.jar

RUN chmod 755 /run.sh && chmod 755 /app/news-storage.jar && mkdir /data && chown $USER /data

USER $USER

CMD ["/run.sh"]