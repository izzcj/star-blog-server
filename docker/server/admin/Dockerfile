FROM eclipse-temurin:21-jre-jammy

WORKDIR /home/star-blog-admin

ENV TZ=Asia/Shanghai

RUN apt-get update \
    && apt-get install -y tzdata \
    && ln -snf /usr/share/zoneinfo/$TZ /etc/localtime \
    && echo $TZ > /etc/timezone \
    && rm -rf /var/lib/apt/lists/*

COPY star-blog-admin-1.0.0.jar star-blog-admin.jar

EXPOSE 9091

ENTRYPOINT ["java", "--add-opens", "java.base/java.lang=ALL-UNNAMED", "--add-opens", "java.base/java.util=ALL-UNNAMED", "--add-opens", "java.base/sun.net.util=ALL-UNNAMED", "--add-opens", "java.base/java.lang.reflect=ALL-UNNAMED", "-jar", "star-blog-admin.jar", "--spring.profiles.active=prod"]
