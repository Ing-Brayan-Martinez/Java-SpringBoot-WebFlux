#para saber a que red, nos vamos a conectar
docker network ls

docker network inspect

#para lanzar la base de datos postgresql
docker pull postgres:14

docker run -d -p 5432:5432 --hostname postgresql --network bridge \
     -e POSTGRES_PASSWORD=postgres --name postgresql postgres:14

docker exec -it postgresql bash

#para lanzar la base de datos sql server
docker pull mcr.microsoft.com/mssql/server:2019-latest

docker run -d -p 1433:1433 --hostname mssql-server --network bridge \
-e "ACCEPT_EULA=Y" -e "SA_PASSWORD=123456" -e "MSSQL_PID=Express" \
 --name mssql-server mcr.microsoft.com/mssql/server:2019-latest

docker exec -it mssql-server bash

docker exec -it mssql-server /opt/mssql-tools/bin/sqlcmd -S localhost -U sa -P 123456

#para construir la imagen
docker build . --tag java-springboot-webflux:0.1

#para ver como quedo la imagen
docker image ls

#para verificar la imagen, se debe estar autenticado con algún proveedor de escáner de imagenes
docker scan -f Dockerfile java-springboot-webflux:01

#para correr la imagen
docker run -d -p 8080:8080 --hostname java-springboot-webflux --network bridge \
  --name java-springboot-webflux java-springboot-webflux:0.1

#para saber como quedaron los contenedores
docker ps
