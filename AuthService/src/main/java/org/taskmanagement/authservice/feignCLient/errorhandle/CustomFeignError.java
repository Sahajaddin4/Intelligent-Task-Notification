package org.taskmanagement.authservice.feignCLient.errorhandle;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import feign.Response;
import feign.codec.ErrorDecoder;
import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.NotFoundException;
import org.taskmanagement.authservice.exception.custom.UnAuthorizedException;
import org.taskmanagement.authservice.exception.custom.UserAlreadyExistsException;

import java.io.InputStream;
import java.util.Map;

public class CustomFeignError  implements ErrorDecoder {
    private final ObjectMapper objectMapper = new ObjectMapper();
    @Override
    public Exception decode(String s, Response response) {
        String error = "";
        System.out.println("Response:" +response.body());
        if(response.body() != null) {
        try( InputStream inputStream = response.body().asInputStream();){
                Map<String,Object> errorMap = objectMapper.readValue(inputStream, new TypeReference<Map<String, Object>>() {});
//            System.out.println(errorMap);
                if (errorMap != null) {
                    if (errorMap.containsKey("error")) {
                        error = String.valueOf(errorMap.get("error"));
                    } else if (errorMap.containsKey("message")) {
                        error = String.valueOf(errorMap.get("message"));
                    }
                }

        }catch (Exception e){
            return   new RuntimeException("Error decoding feign response");
        }
        }
        return switch (response.status()) {
            case 400 -> new BadRequestException( error);
            case 401 -> new UnAuthorizedException( error);
            case 404 -> new NotFoundException(error);
            case 500 -> new RuntimeException(error);
            case 409 -> new UserAlreadyExistsException(error);
            default -> new RuntimeException("Feign Client Error: " + error);
        };
    }
}