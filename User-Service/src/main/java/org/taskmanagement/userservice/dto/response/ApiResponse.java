package org.taskmanagement.userservice.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
@Data
@AllArgsConstructor
@NoArgsConstructor
public  class ApiResponse {
     int statusCode;
     String message;
     public ApiResponse(HttpStatus statusCode, String message) {
          this.statusCode = statusCode.value();
          this.message = message;
     }

     public void setStatusCode(HttpStatus statusCode) {
          this.statusCode = statusCode.value();
     }
}
