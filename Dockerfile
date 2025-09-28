#imagen base que tiene  el jdk 
#utiliza una distro ligera (alpine) contine lo necesario para ejecutar java
#primera etapa de build donde se nesecita de maven para compilar a un jar
FROM maven:3.9-eclipse-temurin AS build

#difinir el espacio de trabajo en la crapeta app , si no exite esta carpeta docker la crea
WORKDIR /app

#copiar el codigo fuente a el espacio  de trabajo 
COPY . .

#compilar el proyecto a un jar
#run se ejecuta solo en la contrucción de la imagen
RUN mvn clean package -DskipTests

#segunda etapa para la ejecución
FROM eclipse-temurin:17-alpine

#difinir app como especio de trabajo
WORKDIR /app

#copiar el jar de la etapa anterior a el espacio de trabajo
#se renombra el jar como app.jar
COPY --from=build /app/target/**.jar app.jar

#exponer el puerto para que sea accesible
EXPOSE 8080

#ejecutar el jar cuando se inicie el contenedor
#utilizar el programa de java indicando que va a ejecutar un jar con nombre app.jar
ENTRYPOINT [ "java","-jar","app.jar" ]