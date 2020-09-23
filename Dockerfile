FROM java:8
MAINTAINER bingo
Add csdn-manger-0.0.1-SNAPSHOT.jar csdn.jar

EXPOSE 8083
ENTRYPOINT ["java","-jar","/csdn.jar","--debug","--spring.config.location=/data/java/config/application-manger.yml","--spring.profiles.active = manger"]