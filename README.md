# first-study-project

## Sources
- https://www.baeldung.com/spring-webflux
- https://spring.io/guides/gs/spring-boot

## Usage

*Get user*

```shell
curl http://localhost:8080/api/v1/users/12345
```

*Create user*

```shell
curl --location 'http://localhost:8080/api/v1/users' \
--header 'Content-Type: application/json' \
--data '{
    "login": "test234",
    "name": "testName",
    "lastName": "testLastName"
}'
```

TEST
