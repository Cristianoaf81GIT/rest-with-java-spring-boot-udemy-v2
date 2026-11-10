# ---------- etapa 1: build ----------
FROM eclipse-temurin:21-jdk AS build
WORKDIR /workspace

# 1) copia so o necessario para resolver dependencias -> essa camada fica em cache
COPY .mvn/ .mvn/
COPY mvnw pom.xml ./
RUN chmod +x mvnw && ./mvnw -B -ntp dependency:go-offline

# 2) copia o codigo e empacota
COPY src/ src/
RUN ./mvnw -B -ntp clean package -DskipTests

# ---------- etapa 2: runtime ----------
FROM eclipse-temurin:21-jre
WORKDIR /app

RUN useradd --system --uid 1001 appuser \
    && mkdir -p /app/files \
    && chown -R appuser:appuser /app

COPY --from=build --chown=appuser:appuser /workspace/target/*.jar app.jar

USER appuser
EXPOSE 80
ENTRYPOINT [ "java", "-jar", "app.jar" ]
