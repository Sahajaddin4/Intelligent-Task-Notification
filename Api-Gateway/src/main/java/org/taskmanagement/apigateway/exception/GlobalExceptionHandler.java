package org.taskmanagement.apigateway.exception;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.reactive.error.ErrorWebExceptionHandler;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.taskmanagement.apigateway.dto.ErrorResponse;
import org.taskmanagement.apigateway.exception.custom.TokenIsExpired;
import org.taskmanagement.apigateway.exception.custom.TokenIsInvalid;
import org.taskmanagement.apigateway.exception.custom.TokenIsMissing;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;

@Component

public class GlobalExceptionHandler implements ErrorWebExceptionHandler {
    private final ObjectMapper objectMapper ;

    public GlobalExceptionHandler(@Autowired  ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public Mono<Void> handle(ServerWebExchange exchange, Throwable ex) {
        ServerHttpResponse response = exchange.getResponse();
        HttpStatus statusCode;
        ServerHttpRequest request = exchange.getRequest();
        String path = request.getURI().getPath();
        ErrorResponse errorPayload = new ErrorResponse();
        if(response.isCommitted()){
            return Mono.error(ex);
        }
        switch (ex){
            case TokenIsExpired tie->{
                statusCode = HttpStatus.UNAUTHORIZED;

            }
            case TokenIsInvalid tii->{
                statusCode = HttpStatus.BAD_REQUEST;
               
            }
            case TokenIsMissing tim->{
                statusCode = HttpStatus.BAD_REQUEST;
               
            }
            default -> {
                statusCode = HttpStatus.INTERNAL_SERVER_ERROR;

            }
        }
        ErrorResponse errorResponse = new ErrorResponse(ex.getMessage(), statusCode.value(),path , LocalDateTime.now());
        try{
            byte [] bytes = objectMapper.writeValueAsBytes(errorResponse);
            DataBufferFactory bufferFactory = response.bufferFactory();
            DataBuffer dataBuffer = bufferFactory.wrap(bytes);
            response.setStatusCode(statusCode);
            return   response.writeWith(Mono.just(dataBuffer));
        }
        catch (Exception e){
            e.printStackTrace();
            DataBuffer buffer = response.bufferFactory().wrap("{\"error\":\"Internal Server Error\"}".getBytes(StandardCharsets.UTF_8));
            return response.writeWith(Mono.just(buffer));
        }
        
    }
}
