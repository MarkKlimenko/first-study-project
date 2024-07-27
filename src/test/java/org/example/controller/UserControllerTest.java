package org.example.controller;

import org.example.controller.dto.UserCreateRequest;
import org.example.controller.dto.UserResponse;
import org.example.controller.dto.UserUpdateRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class UserControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @Test
    void getUserTest() {
        // create user
        UserCreateRequest createRequest = new UserCreateRequest();
        createRequest.login = "testLoginGet";
        createRequest.name = "testNameGet";
        createRequest.lastName = "testLastNameGet";

        UserResponse createResponse = createUser(createRequest);

        // get user
        UserResponse getResponse = webTestClient.get()
                .uri("api/v1/users/" + createResponse.id)
                .exchange()
                .expectStatus().isOk()
                .returnResult(UserResponse.class)
                .getResponseBody()
                .blockFirst();

        assertEquals(createResponse.id, getResponse.id);
        assertEquals(createRequest.login, getResponse.login);
        assertEquals(createRequest.name, getResponse.name);
        assertEquals(createRequest.lastName, getResponse.lastName);
        assertEquals(createResponse.creationDate, getResponse.creationDate);
    }

    @Test
    void createUserTest() {
        UserCreateRequest request = new UserCreateRequest();
        request.login = "testLoginCreate";
        request.name = "testNameCreate";
        request.lastName = "testLastNameCreate";

        UserResponse response = createUser(request);

        assertNotNull(response.id);
        assertEquals(request.login, response.login);
        assertEquals(request.name, response.name);
        assertEquals(request.lastName, response.lastName);
        assertEquals(LocalDate.now(), response.creationDate.toLocalDate());
    }

    @Test
    void updateUserTest() {
        // create user
        UserCreateRequest createRequest = new UserCreateRequest();
        createRequest.login = "testLoginUpdate";
        createRequest.name = "testNameUpdate";
        createRequest.lastName = "testLastNameUpdate";

        UserResponse createResponse = createUser(createRequest);

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
        assertEquals(createRequest.login, updateResponse.login);
        assertEquals(createResponse.creationDate, updateResponse.creationDate);

        assertEquals(updateRequest.name, updateResponse.name);
        assertEquals(updateRequest.lastName, updateResponse.lastName);
    }

    @Test
    void deleteUserTest() {
        // create user
        UserCreateRequest createRequest = new UserCreateRequest();
        createRequest.login = "testLoginDelete";
        createRequest.name = "testNameDelete";
        createRequest.lastName = "testLastNameDelete";

        UserResponse createResponse = createUser(createRequest);

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

    private UserResponse createUser(UserCreateRequest request) {
        UserResponse response = webTestClient.post()
                .uri("api/v1/users")
                .bodyValue(request)
                .exchange()
                .expectStatus().isOk()
                .returnResult(UserResponse.class)
                .getResponseBody()
                .blockFirst();

        return response;
    }
}
