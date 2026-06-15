package org.taskmanagement.userservice.mapper;

import org.mapstruct.*;
import org.taskmanagement.userservice.dto.request.AccountCreationDetails;
import org.taskmanagement.userservice.dto.request.UpdateUserDetailsDto;
import org.taskmanagement.userservice.dto.response.UserDetailsDto;
import org.taskmanagement.userservice.entity.User;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserMapper{
    User toUser(AccountCreationDetails userDto);
    @Mapping(source = "phone", target = "phone")
    UserDetailsDto toUserDetailsDto(User user);

    @BeanMapping(nullValuePropertyMappingStrategy =  NullValuePropertyMappingStrategy.IGNORE)
    void updateUserDetails(UpdateUserDetailsDto userDetailsDto, @MappingTarget User user);
}
