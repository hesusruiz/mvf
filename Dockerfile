FROM docker.io/eclipse-temurin:19 AS app-env

WORKDIR /app

EXPOSE 7000 7001 7002 7003 7004 7010

ENTRYPOINT ["/app/build/install/wsrv2/bin/wsrv2"]
