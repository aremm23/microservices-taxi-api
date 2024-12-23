package com.artsem.api.driverservice.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@Data
@ConfigurationProperties(prefix = "openapi")
public class OpenAPIProperties {

    private ServerProperties server;
    private ContactProperties contact;
    private InfoProperties info;

    @Data
    public static class ServerProperties {
        private String url;
        private String description;
    }

    @Data
    public static class ContactProperties {
        private String name;
        private String email;
    }

    @Data
    public static class InfoProperties {
        private String title;
        private String version;
        private String description;
    }
}