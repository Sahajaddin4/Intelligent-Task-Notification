package org.taskmanagement.authservice.mapper;

import org.mapstruct.*;
import org.taskmanagement.authservice.dto.request.SignupRequestDto;
import org.taskmanagement.authservice.dto.servicedto.request.CreateUserAccount;
import org.taskmanagement.authservice.dto.servicedto.response.UserResponse;
import org.taskmanagement.authservice.entity.Auth;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface AuthMapper {
   SignupRequestDto toAuthRequestDto(Auth auth);
   Auth toAuth(SignupRequestDto signupRequestDtoDto);
   CreateUserAccount toCreateUserAccountUserService(SignupRequestDto createUserAccount);
   Auth toAuthFromUserServiceResponse(UserResponse user);
   UserResponse toUserResponse(Auth auth);
}
