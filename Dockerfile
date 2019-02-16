FROM openjdk:8-jre-alpine

COPY wait-for.sh wait-for
COPY build/libs/lt-*.jar lt4j.jar

FROM java:openjdk-8u111-alpine
RUN apk --no-cache add curl
CMD ./wait-for -t $TIMEOUT $HOST:$PORT -- \
        java ${JAVA_OPTS} -jar lt4j.jar


#java -server
#  -Djava.security.egd=file:/dev/./urandom
#  -XX:+UnlockExperimentalVMOptions -XX:+UseCGroupMemoryLimitForHeap
#  -Xms32M -Xmx256M -Xss1M
#  -XX:+TieredCompilation -XX:TieredStopAtLevel=1 # <- When memory lower than 300MB
#  -XX:+UseParallelGC -XX:+UseNUMA -jar app.jar