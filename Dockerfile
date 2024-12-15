# Dockerfile
FROM eclipse-temurin:17-jdk

# 安装 Maven
RUN apt-get update && apt-get install -y maven

# 安装 Node.js
RUN apt-get install -y curl
RUN curl -fsSL https://deb.nodesource.com/setup_18.x | bash -
RUN apt-get install -y nodejs

# 安装 Git 和其他工具
RUN apt-get install -y git vim curl wget

# 设置工作目录
WORKDIR /workspace

# 配置 Maven 镜像源（可选，使用阿里云加速）
RUN mkdir -p /root/.m2 \
    && echo '<?xml version="1.0" encoding="UTF-8"?>\
    <settings xmlns="http://maven.apache.org/SETTINGS/1.0.0"\
    xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"\
    xsi:schemaLocation="http://maven.apache.org/SETTINGS/1.0.0\
    https://maven.apache.org/xsd/settings-1.0.0.xsd">\
    <mirrors>\
    <mirror>\
    <id>aliyunmaven</id>\
    <mirrorOf>central</mirrorOf>\
    <name>阿里云公共仓库</name>\
    <url>https://maven.aliyun.com/repository/public</url>\
    </mirror>\
    </mirrors>\
    </settings>' > /root/.m2/settings.xml