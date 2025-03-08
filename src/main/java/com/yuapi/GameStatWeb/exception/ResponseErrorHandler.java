package com.yuapi.GameStatWeb.exception;

import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.DefaultResponseErrorHandler;

import java.io.IOException;
import java.util.UUID;

public class ResponseErrorHandler  extends DefaultResponseErrorHandler {

    @Override
    public void handleError(ClientHttpResponse response) throws IOException {
        int statusCode = response.getStatusCode().value();
        String traceId = UUID.randomUUID().toString();

        if (response.getStatusCode().is4xxClientError()) {
            throw new ClientException(
                    ClientException.getMessageForStatus(statusCode),
                    statusCode,
                    "CLIENT_ERROR",
                    traceId
            );
        } else if (response.getStatusCode().is5xxServerError()) {
            throw new ServerException(
                    ServerException.getMessageForStatus(statusCode),
                    statusCode,
                    "SERVER_ERROR",
                    traceId
            );
        } else {
            super.handleError(response);
        }
    }
}
