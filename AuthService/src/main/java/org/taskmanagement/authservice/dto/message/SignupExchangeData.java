package org.taskmanagement.authservice.dto.message;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@AllArgsConstructor
@Getter
@Setter
@ToString
public class SignupExchangeData implements Serializable {
    String name;
    String email;
}
