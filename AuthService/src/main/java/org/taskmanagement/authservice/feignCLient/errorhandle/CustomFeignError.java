package org.taskmanagement.authservice.feignCLient.errorhandle;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import feign.Response;
import feign.codec.ErrorDecoder;
import lombok.extern.slf4j.Slf4j;
import org.taskmanagement.authservice.exception.custom.BadRequestException;
import org.taskmanagement.authservice.exception.custom.ResourceNotFoundException;
import org.taskmanagement.authservice.exception.custom.UnAuthorizedException;
import org.taskmanagement.authservice.exception.custom.UserAlreadyExistsException;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Map;

@Slf4j
public class CustomFeignError implements ErrorDecoder {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public Exception decode(String methodKey, Response response) {

        String errorMessage = "Unknown error";

        if (response.body() != null) {
            try (InputStream inputStream = response.body().asInputStream()) {

                // Read response body as String
                String responseBody = new String(
                        inputStream.readAllBytes(),
                        StandardCharsets.UTF_8
                );

                // Log complete error response
                log.error(
                        "Feign call failed. Method={}, Status={}, Body={}",
                        methodKey,
                        response.status(),
                        responseBody
                );

                // Try parsing JSON response
                try {
                    Map<String, Object> errorMap = objectMapper.readValue(
                            responseBody,
                            new TypeReference<Map<String, Object>>() {}
                    );

                    if (errorMap.containsKey("error")) {
                        errorMessage = String.valueOf(errorMap.get("error"));
                    } else if (errorMap.containsKey("message")) {
                        errorMessage = String.valueOf(errorMap.get("message"));
                    } else {
                        errorMessage = responseBody;
                    }

                } catch (Exception jsonException) {
                    // Response is not JSON
                    errorMessage = responseBody;
                }

            } catch (Exception e) {

                log.error(
                        "Error decoding Feign response. Method={}, Status={}",
                        methodKey,
                        response.status(),
                        e
                );

                return new RuntimeException("Error decoding feign response", e);
            }
        }

        return switch (response.status()) {
            case 400 -> new BadRequestException(errorMessage);
            case 401 -> new UnAuthorizedException(errorMessage);
            case 404 -> new ResourceNotFoundException(errorMessage);
            case 409 -> new UserAlreadyExistsException(errorMessage);
            case 500 -> new RuntimeException(errorMessage);
            default -> new RuntimeException(
                    "Feign Client Error [" +
                            response.status() +
                            "]: " +
                            errorMessage
            );
        };
    }
}