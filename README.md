# RESTFUL API with DOCKER and MySQL

Time Taken : 17 Hrs

Built with Love By:
Devang Sharma (CISCO)

## 1.Creating Network in Docker

We are creating a Network with name my_network.

```bash
$ docker network create my_network
```

## 2.Installation of MySQL inside Docker

For this project we are using mysql version 8 and running in my_network.

```bash
$ docker run --network my_network --name docker-mysql -e MYSQL_ROOT_PASSWORD=root -d mysql:8
```

here the name of the image is docker_mysql and user is root and password is root.  
We will use this credential in the Java application to connect to this mysql.

we need a database go ahead and create a database with name restapi.

## 3.Logging Inside MySQL from Interactive Shell

find out the container ID of mysql container run in previous state.

```bash
$ docker ps

$ docker exec -it b5438asdhf64 bash

@root> mysql -u root -p
root

create database restapi;
use restapi;
```



we will be needing a database restapi in future.

## 4.Configuring application.properties file in spring boot
We must put the configuration data here so that spring application can use the credentials to connect to the mysql container inside docker.

```bash
spring.datasource.url = jdbc:mysql://docker_mysql:3306/restapi?allowPublicKeyRetrieval=true&useSSL=false
spring.datasource.username = root
spring.datasource.password = root
spring.jpa.properties.hibernate.dialect = org.hibernate.dialect.MySQL5InnoDBDialect
spring.jpa.hibernate.ddl-auto = create

```
In place of localhost or ip we put the name of the mysql container ( docker_mysql) and once we run the jar of this application inside docker container in my_network network created previosly it will check the DNS and find out the ip of our mysql container to connect.



## 5.Building Docker Images from Dockerfile
There is a file inside .infra/docker with name Dockerfile that has the script for building the docker file we will build the docker image with the help of this file.

```bash
$ docker build -t todorestapi -f .infra/docker/Dockerfile .
```
Here the name of the image is todorest.  
We will need this when running the application.

## 6.Running the Docker images of the spring application.

```bash
$ docker run --network my_network -p 8080:8080 todorestapi
```
here todorestapi is the name of the images created previously.

## 7.Try Hitting
```bash
http://localhost:8080/api/v1
```

with POST and see the data in the interactive logging shell in step 3. 
