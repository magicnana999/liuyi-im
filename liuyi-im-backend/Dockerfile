FROM openjdk:8-jre-alpine
MAINTAINER https://github.com/magicnana999
ARG JAR_FILE
ADD target/${JAR_FILE} /app/app.jar
EXPOSE 33011
ENV TZ=Asia/Shanghai
ENV JAVA_OPTS="-server -Xmx1g -Xms1g -Xmn256m -Xss256K"
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone
ENTRYPOINT java ${JAVA_OPTS} -Djava.security.egd=file:/dev/./urandom -jar /app/app.jar