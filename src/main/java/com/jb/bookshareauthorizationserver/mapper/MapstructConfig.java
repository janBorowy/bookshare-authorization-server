package com.jb.bookshareauthorizationserver.mapper;

import org.mapstruct.MapperConfig;
import org.mapstruct.MappingConstants;

@MapperConfig(
        componentModel = MappingConstants.ComponentModel.SPRING,
        uses = {
                PasswordEncodingHelper.class
        }
)
public interface MapstructConfig {
}
