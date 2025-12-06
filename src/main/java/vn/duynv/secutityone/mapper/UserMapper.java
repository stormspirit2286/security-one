package vn.duynv.secutityone.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import vn.duynv.secutityone.modal.User;
import vn.duynv.secutityone.payload.request.UserDto;
import vn.duynv.secutityone.payload.response.UserResponse;

@Mapper(componentModel = "spring")
public interface UserMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "role", ignore= true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    User toEntity(UserDto userDto);

    UserResponse toResponse(User user);
}
