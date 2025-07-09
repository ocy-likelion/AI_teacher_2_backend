# 1. OpenJDK 21 기반 빌드 이미지
FROM eclipse-temurin:21-jdk-alpine as builder

# 2. 작업 디렉토리 설정
WORKDIR /app

# 3. Gradle Wrapper 및 빌드 파일 복사
COPY gradlew ./
COPY gradle gradle
COPY build.gradle settings.gradle ./
RUN chmod +x gradlew

# 4. 의존성 미리 다운받아 캐시 사용
RUN ./gradlew dependencies --no-daemon

# 5. 애플리케이션 소스 복사 및 빌드
COPY src src
RUN ./gradlew build --no-daemon

# 6. 실행용 슬림 JRE(Java 21) 이미지
FROM eclipse-temurin:21-jre-alpine

# 7. 실행 디렉토리 설정
WORKDIR /app

# 8. 빌드된 JAR 복사
COPY --from=builder /app/build/libs/*.jar app.jar

# 9. JAR 실행
ENTRYPOINT ["java", "-jar", "app.jar"]
