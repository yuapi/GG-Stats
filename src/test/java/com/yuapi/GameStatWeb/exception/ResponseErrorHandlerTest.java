package com.yuapi.GameStatWeb.exception;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withStatus;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class ResponseErrorHandlerTest {

    @Autowired
    private RestTemplate restTemplate;

    private MockRestServiceServer mockServer;

    @BeforeEach
    public void setUp() { mockServer = MockRestServiceServer.createServer(restTemplate); }

    @Test
    public void testClientException() {
        mockServer.expect(requestTo("/client-error"))
                        .andRespond(withStatus(HttpStatusCode.valueOf(400))
                                .contentType(MediaType.APPLICATION_JSON)
                                .body("{\"error\": \"Bad Request\"}"));

        assertThrows(ClientException.class, () -> {
            restTemplate.getForObject("/client-error", String.class);
        });

        mockServer.verify();
    }

    @Test
    public void testServerException() {
        mockServer.expect(requestTo("/server-error"))
                .andRespond(withStatus(HttpStatusCode.valueOf(500))
                        .contentType(MediaType.APPLICATION_JSON)
                        .body("{\"error\": \"Internal Server Error\"}"));

        assertThrows(ServerException.class, () -> {
            restTemplate.getForObject("/server-error", String.class);
        });

        mockServer.verify();
    }
}
