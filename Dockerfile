# First stage: complete build environment
FROM maven:3.5.0-jdk-8-alpine AS builder
# 作者信息
MAINTAINER GoodBoy "13682768293@163.com"

# add pom.xml and source code
ADD ./pom.xml pom.xml
ADD ./src src/

#打包
RUN mvn clean package

# Second stage: minimal runtime environment
FROM openjdk:8-jre-alpine

# copy jar from the first stage
COPY --from=builder target/online-practice-1.0-SNAPSHOT.jar online-practice.jar

#设置字符编码
ENV LANG     en_US.UTF-8
ENV LANGUAGE en_US.UTF-8
ENV LC_ALL   en_US.UTF-8

RUN ln -sf /usr/share/zoneinfo/Asia/Shanghai /etc/localtime && echo 'Asia/Shanghai' >/etc/timezone
ENTRYPOINT ["java", "-jar", "online-practice.jar", "--jasypt.encryptor.password=$JASYPT", "--spring.profiles.active=master"]