package org.example.controller;

import org.example.controller.dto.UserCreateRequest;
import org.example.controller.dto.UserResponse;
import org.example.controller.dto.UserUpdateRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.time.LocalDate;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class UserControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @Test
    void getUserTest() {
        // create user
        UserResponse createResponse = createUser();

        // get user
        UserResponse getResponse = webTestClient.get()
                .uri("api/v1/users/" + createResponse.id)
                .exchange()
                .expectStatus().isOk()
                .returnResult(UserResponse.class)
                .getResponseBody()
                .blockFirst();

        assertEquals(createResponse.id, getResponse.id);
        assertEquals(createResponse.login, getResponse.login);
        assertEquals(createResponse.name, getResponse.name);
        assertEquals(createResponse.lastName, getResponse.lastName);
        assertEquals(createResponse.creationDate, getResponse.creationDate);
    }

    @Test
    void createUserTest() {
        createUser();
    }

    @Test
    void updateUserTest() {
        // create user
        UserResponse createResponse = createUser();

        // update user
        UserUpdateRequest updateRequest = new UserUpdateRequest();
        updateRequest.name = "testNameAfterUpdate";
        updateRequest.lastName = "testLastNameAfterUpdate";

        UserResponse updateResponse = webTestClient.put()
                .uri("api/v1/users/" + createResponse.id)
                .bodyValue(updateRequest)
                .exchange()
                .expectStatus().isOk()
                .returnResult(UserResponse.class)
                .getResponseBody()
                .blockFirst();

        assertEquals(createResponse.id, updateResponse.id);
        assertEquals(createResponse.login, updateResponse.login);
        assertEquals(createResponse.creationDate, updateResponse.creationDate);

        assertEquals(updateRequest.name, updateResponse.name);
        assertEquals(updateRequest.lastName, updateResponse.lastName);
    }

    @Test
    void deleteUserTest() {
        // create user
        UserResponse createResponse = createUser();

        // delete user
        webTestClient.delete()
                .uri("api/v1/users/" + createResponse.id)
                .exchange()
                .expectStatus().isOk();

        // get user
        webTestClient.get()
                .uri("api/v1/users/" + createResponse.id)
                .exchange()
                .expectStatus().isNotFound();
    }

    private UserResponse createUser() {
        String postfix = UUID.randomUUID().toString();

        UserCreateRequest request = new UserCreateRequest();
        request.login = "testLogin_" + postfix;
        request.name = "testName_" + postfix;
        request.lastName = "testLastName_" + postfix;

        UserResponse response = webTestClient.post()
                .uri("api/v1/users")
                .bodyValue(request)
                .exchange()
                .expectStatus().isOk()
                .returnResult(UserResponse.class)
                .getResponseBody()
                .blockFirst();

        assertNotNull(response.id);
        assertEquals(request.login, response.login);
        assertEquals(request.name, response.name);
        assertEquals(request.lastName, response.lastName);
        assertEquals(LocalDate.now(), response.creationDate.toLocalDate());

        return response;
    }
}
