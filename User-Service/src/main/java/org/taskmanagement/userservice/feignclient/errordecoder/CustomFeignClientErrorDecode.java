package org.taskmanagement.userservice.feignclient.errordecoder;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import feign.Response;
import feign.codec.ErrorDecoder;

import java.io.InputStream;
import java.util.Map;

public class CustomFeignClientErrorDecode implements ErrorDecoder {
    private final ObjectMapper objectMapper = new ObjectMapper();
    @Override
    public Exception decode(String s, Response response) {
        String error = "";
        try(InputStream inputStream = response.body().asInputStream();){
            if(response.body() != null) {
                
                Map<String,Object> errorMap = objectMapper.readValue(inputStream, new TypeReference<Map<String, Object>>() {});
                error = (String) errorMap.get("error");
                System.out.println(error);
            }
        }catch (Exception e){
            return   new RuntimeException("Error decoding feign response");
        }
        return switch (response.status()) {
            case 400 -> new RuntimeException("Bad Request: " + error);
            case 401 -> new RuntimeException("Unauthorized: " + error);
            case 404 -> new RuntimeException("Resource Not Found: " + error);
            case 500 -> new RuntimeException("Internal Server Error: " + error);
            default -> new RuntimeException("Feign Client Error: " + error);
        };
    }
}
