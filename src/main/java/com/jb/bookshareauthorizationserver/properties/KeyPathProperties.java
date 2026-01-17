package com.jb.bookshareauthorizationserver.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties("key-path")
public class KeyPathProperties {

    private String authServerPublicKeyFilePath;

    private String authServerPrivateKeyFilePath;

}
