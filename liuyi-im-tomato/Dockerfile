FROM openjdk:8-jre-alpine
MAINTAINER https://github.com/magicnana999
ARG JAR_FILE
ADD target/${JAR_FILE} /app/app.jar
EXPOSE 33012
ENV TZ=Asia/Shanghai
ENV JAVA_OPTS="-server -Xmx1g -Xms1g -Xmn256m -Xss256K"
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone
ENTRYPOINT java ${JAVA_OPTS} -Djava.security.egd=file:/dev/./urandom -jar /app/app.jar

# 还没想好怎么指定gc和dump的目录位置，${HOSTNAME}貌似是在容器启动时才有，构建的时候没有，只能在shell中启动并且指定了。以后再说吧～