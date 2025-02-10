# Usa a imagem do Eclipse Temurin com JDK 21 e Alpine Linux
FROM eclipse-temurin:21-jdk-alpine

# Define o diretório de trabalho dentro do contêiner
WORKDIR /app

# Copia o código-fonte e o arquivo pom.xml para o contêiner
COPY pom.xml .
COPY src ./src

# Instala o Maven e constrói a aplicação
RUN apk add --no-cache maven && \
    mvn clean package -DskipTests

# Copia o arquivo JAR gerado para o diretório de trabalho
RUN cp target/*.jar app.jar

# Expõe a porta que a aplicação vai usar
EXPOSE 8080

# Comando para executar a aplicação
CMD ["java", "-jar", "app.jar"]