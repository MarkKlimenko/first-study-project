# first-study-project

## Sources

- https://www.baeldung.com/spring-webflux
- https://spring.io/guides/gs/spring-boot

## Usage

**Get user**

```shell
curl http://localhost:8080/api/v1/users/12345
```

**Create user**

```shell
curl --location 'http://localhost:8080/api/v1/users' \
--header 'Content-Type: application/json' \
--data '{
    "login": "test234",
    "name": "testName",
    "lastName": "testLastName"
}'
```

## Start database

### Download Docker

https://www.docker.com/

### Start Postgres

Official image https://hub.docker.com/_/postgres

```shell
docker run --name sql-postgres-study -p 5432:5432 -e POSTGRES_PASSWORD=postgres -d postgres:15.2

docker start sql-postgres-study
docker stop sql-postgres-study
docker rm sql-postgres-study
```

### Connect to Postgres

```shell
docker exec -it sql-postgres-study bash
psql -U postgres
```

```sql
CREATE DATABASE postgres_study;
\c postgres_study
CREATE ROLE postgres_study WITH LOGIN PASSWORD 'password';
CREATE SCHEMA postgres_study authorization postgres_study;

-- Do in another terminal or in another connection
-- psql -U postgres_study postgres_study
create table example (id integer);
\dt

select * from example;
```

### GUI for Database

https://dbeaver.io/download/

### Useful commands

```sql
alter table example
    add name varchar;

alter table example
    add surname varchar;

-- https://www.postgresql.org/docs/current/sql-insert.html
insert into example (id, name, surname)
values (2, 'Bob', 'Spanch');

select * from example;
```

## Connect to database JDBC

- https://jdbc.postgresql.org/
- https://mvnrepository.com/artifact/org.postgresql/postgresql
- https://jdbc.postgresql.org/documentation/use/

