package com.jb.bookshareauthorizationserver.mapper;

import com.jb.bookshareauthorizationserver.data.entity.UserEntity;
import com.jb.bookshareauthorizationserver.model.UserRegisterRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MapstructConfig.class)
public interface UserRegisterRequestToUserEntityMapper {

    @Mapping(target = "disabled", constant = "false")
    @Mapping(source = "plainPassword", target = "encodedPassword", qualifiedByName = {"passwordEncodingHelper", "encodePassword"})
    UserEntity map(UserRegisterRequest request);
}
